/*
 * Copyright 2010 Proofpoint, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.proofpoint.tracetoken;

import javax.annotation.Nullable;
import java.util.UUID;

public final class TraceTokenManager
{
    private static final ThreadLocal<String> token = new ThreadLocal<>();

    private TraceTokenManager()
    {}

    public static TraceTokenScope registerRequestToken(@Nullable String token)
    {
        String oldToken = TraceTokenManager.token.get();
        TraceTokenManager.token.set(token);
        return new TraceTokenScope(oldToken);
    }

    public static String getCurrentRequestToken()
    {
        return token.get();
    }

    public static String createAndRegisterNewRequestToken()
    {
        String newToken = UUID.randomUUID().toString();
        registerRequestToken(newToken);

        return newToken;
    }

    public static void clearRequestToken()
    {
        token.remove();
    }
}
