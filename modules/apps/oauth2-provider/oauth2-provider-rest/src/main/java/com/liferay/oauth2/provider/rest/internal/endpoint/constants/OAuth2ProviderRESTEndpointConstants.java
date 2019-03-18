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

package com.liferay.oauth2.provider.rest.internal.endpoint.constants;

/**
 * @author Tomas Polesovsky
 */
public class OAuth2ProviderRESTEndpointConstants {

	/**
	 * Embraces CXF OAuthConstants naming + RFC grant_type value formats.
	 */
	public static final String AUTHORIZATION_CODE_PKCE_GRANT =
		"authorization_code_pkce";

	public static final String PROPERTY_KEY_CLIENT_FEATURE_PREFIX = "feature.";

	public static final String PROPERTY_KEY_CLIENT_FEATURE_TOKEN_INTROSPECTION =
		"token_introspection";

	public static final String PROPERTY_KEY_CLIENT_FEATURES = "features";

	public static final String PROPERTY_KEY_CLIENT_REMOTE_ADDR =
		"client.remote.addr";

	public static final String PROPERTY_KEY_CLIENT_REMOTE_HOST =
		"client.remote.host";

	public static final String PROPERTY_KEY_COMPANY_ID = "company.id";

}