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

package com.liferay.portal.security.sso.google.constants;

/**
 * @author Stian Sigvartsen
 */
public class LegacyGoogleLoginPropsKeys {

	public static final String AUTH_ENABLED = "google-auth-enabled";

	public static final String CLIENT_ID = "google-client-id";

	public static final String CLIENT_SECRET = "google-client-secret";

	public static final String[] GOOGLE_LOGIN_KEYS = {
		AUTH_ENABLED, CLIENT_ID, CLIENT_SECRET
	};

}