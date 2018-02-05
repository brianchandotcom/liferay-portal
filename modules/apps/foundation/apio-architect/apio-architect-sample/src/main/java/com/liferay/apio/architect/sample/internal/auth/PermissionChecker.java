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

import java.util.Objects;

/**
 * This class stores the information about the "Authorization" HTTP request in a
 * {@code ThreadLocal}. And allows to check if the authorization, matches with
 * the fake one stored in the server.
 *
 * @author Alejandro Hernández
 * @review
 */
public class PermissionChecker {

	/**
	 * Checks if the "Authorization" HTTP header matches the system environment
	 * variable {@code APIO_AUTH}.
	 *
	 * @return {@code true} if both the HTTP header and the env. var. are equal;
	 *         {@code false} otherwise
	 * @review
	 */
	public static boolean hasPermission() {
		String authorization = _threadLocal.get();
		String auth = System.getenv("APIO_AUTH");

		if ((authorization == null) || (auth == null)) {
			return false;
		}

		return Objects.equals(authorization, auth);
	}

	public static void setAuthorization(String authorization) {
		_threadLocal.set(authorization);
	}

	private static final ThreadLocal<String> _threadLocal = new ThreadLocal<>();

}