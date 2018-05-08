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

package com.liferay.apio.architect.sample.internal.auth;

import com.liferay.apio.architect.credentials.Credentials;

import java.util.Objects;

/**
 * Stores information about the HTTP authorization request in a {@code
 * ThreadLocal}. This lets you check if the authorization matches the fake
 * authorization stored in the server.
 *
 * @author Alejandro Hernández
 */
public class PermissionChecker {

	/**
	 * Returns {@code true} if the HTTP authorization header matches the system
	 * environment variable {@code LIFERAY_APIO_AUTH}.
	 *
	 * @return {@code true} if the HTTP authorization header equals {@code
	 *         LIFERAY_APIO_AUTH}; {@code false} otherwise
	 */
	public static boolean hasPermission(Credentials credentials) {
		String authorization = System.getenv("LIFERAY_APIO_AUTH");

		if ((authorization == null) || (credentials == null)) {
			return false;
		}

		return Objects.equals(authorization, credentials.get());
	}

}