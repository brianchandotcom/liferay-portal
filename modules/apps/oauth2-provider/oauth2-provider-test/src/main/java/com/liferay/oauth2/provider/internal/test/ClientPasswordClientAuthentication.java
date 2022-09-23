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

package com.liferay.oauth2.provider.internal.test;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author Arthur Chan
 */
public class ClientPasswordClientAuthentication
	implements ClientAuthentication {

	public ClientPasswordClientAuthentication(
		String clientId, String clientSecret) {

		_clientAuthenticationData.add("client_id", clientId);
		_clientAuthenticationData.add("client_secret", clientSecret);
	}

	@Override
	public MultivaluedMap<String, String> getClientAuthenticationParameters() {
		return _clientAuthenticationData;
	}

	private final MultivaluedMap<String, String> _clientAuthenticationData =
		new MultivaluedHashMap<>();

}