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

package com.liferay.portal.security.sso.facebook.connect.constants;

/**
 * @author Stian Sigvartsen
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public class LegacyFacebookConnectPropsKeys {

	public static final String APP_ID = "facebook.connect.app.id";

	public static final String APP_SECRET = "facebook.connect.app.secret";

	public static final String AUTH_ENABLED = "facebook.auth.enabled";

	public static final String[] FACEBOOK_CONNECT_KEYS = {
		AUTH_ENABLED, APP_ID, APP_SECRET,
		LegacyFacebookConnectPropsKeys.GRAPH_URL,
		LegacyFacebookConnectPropsKeys.OAUTH_AUTH_URL,
		LegacyFacebookConnectPropsKeys.OAUTH_REDIRECT_URL,
		LegacyFacebookConnectPropsKeys.OAUTH_TOKEN_URL,
		LegacyFacebookConnectPropsKeys.VERIFIED_ACCOUNT_REQUIRED
	};

	public static final String GRAPH_URL = "facebook.connect.graph.url";

	public static final String OAUTH_AUTH_URL =
		"facebook.connect.oauth.auth.url";

	public static final String OAUTH_REDIRECT_URL =
		"facebook.connect.oauth.redirect.url";

	public static final String OAUTH_TOKEN_URL =
		"facebook.connect.oauth.token.url";

	public static final String VERIFIED_ACCOUNT_REQUIRED =
		"facebook.connect.verified.account.required";

}