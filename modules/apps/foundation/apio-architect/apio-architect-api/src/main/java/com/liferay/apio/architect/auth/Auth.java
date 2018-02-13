/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.apio.architect.auth;

import java.util.function.Supplier;

/**
 * Represents the authentication of the current request. Each implementation can
 * decide what to include in it, it may be a token, user ID, etc.
 *
 * @author Alejandro Hernández
 * @review
 */
public interface Auth extends Supplier<String> {
}