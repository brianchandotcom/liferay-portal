/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.constants;

/**
 * @author Thuong Dinh
 */
public class OpenIdConnectWebKeys {

	public static final String OAUTH_CLIENT_ENTRIES = "OAUTH_CLIENT_ENTRIES";

	public static final String OPEN_ID_CONNECT_ACTION_URL =
		"OPEN_ID_CONNECT_ACTION_URL";

	public static final String OPEN_ID_CONNECT_AUTHENTICATING_USER_ID =
		"OPEN_ID_CONNECT_AUTHENTICATING_USER_ID";

	public static final String OPEN_ID_CONNECT_PROVIDER_NAME =
		"OPEN_ID_CONNECT_PROVIDER_NAME";

	public static final String OPEN_ID_CONNECT_PROVIDER_NAMES =
		"OPEN_ID_CONNECT_PROVIDER_NAMES";

	public static final String OPEN_ID_CONNECT_REQUEST_ACTION_NAME =
		"/login/openid_connect_request";

	public static final String OPEN_ID_CONNECT_RESPONSE_ACTION_NAME =
		"/login/openid_connect_response";

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public static final String OPEN_ID_CONNECT_SESSION =
		"OPEN_ID_CONNECT_SESSION";

	public static final String OPEN_ID_CONNECT_SESSION_ID =
		"OPEN_ID_CONNECT_SESSION_ID";

}