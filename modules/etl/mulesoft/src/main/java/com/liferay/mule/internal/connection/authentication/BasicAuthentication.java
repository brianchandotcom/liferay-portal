/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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

package com.liferay.mule.internal.connection.authentication;

import java.nio.charset.StandardCharsets;

import java.util.Base64;

/**
 * @author Matija Petanjek
 */
public class BasicAuthentication implements HttpAuthentication {

	public BasicAuthentication(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public String getAuthorizationHeader() {
		String credentials = username + ":" + password;

		Base64.Encoder encoder = Base64.getEncoder();

		String base64Credentials = encoder.encodeToString(
			credentials.getBytes(StandardCharsets.UTF_8));

		return "Basic " + base64Credentials;
	}

	private final String password;
	private final String username;

}