/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.facebook.connect.constants;

/**
 * @author Stian Sigvartsen
 */
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