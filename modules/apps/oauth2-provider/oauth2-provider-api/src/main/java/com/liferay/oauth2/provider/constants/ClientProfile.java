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

package com.liferay.oauth2.provider.constants;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stian Sigvartsen
 */
public enum ClientProfile {

	HEADLESS_SERVER(4, GrantType.CLIENT_CREDENTIALS, GrantType.JWT_BEARER),
	NATIVE_APPLICATION(
		1, GrantType.AUTHORIZATION_CODE_PKCE, GrantType.JWT_BEARER,
		GrantType.RESOURCE_OWNER_PASSWORD, GrantType.REFRESH_TOKEN),
	OTHER(5, GrantType.values()),
	USER_AGENT_APPLICATION(
		2, GrantType.AUTHORIZATION_CODE_PKCE, GrantType.JWT_BEARER),
	WEB_APPLICATION(
		0, GrantType.AUTHORIZATION_CODE, GrantType.CLIENT_CREDENTIALS,
		GrantType.JWT_BEARER, GrantType.REFRESH_TOKEN,
		GrantType.RESOURCE_OWNER_PASSWORD);

	public Set<GrantType> grantTypes() {
		return _grantTypes;
	}

	public int id() {
		return _id;
	}

	private ClientProfile(int id, GrantType... grantTypes) {
		_id = id;

		_grantTypes = Collections.unmodifiableSet(
			new HashSet<>(Arrays.asList(grantTypes)));
	}

	private final Set<GrantType> _grantTypes;
	private final int _id;

}