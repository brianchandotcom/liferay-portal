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

package com.liferay.portal.security.sso.cas.constants;

/**
 * @author Brian Greenwald
 */
public class LegacyCASPropsKeys {

	public static final String CAS_AUTH_ENABLED = "cas.auth.enabled";

	public static final String CAS_IMPORT_FROM_LDAP = "cas.import.from.ldap";

	public static final String CAS_LOGIN_URL = "cas.login.url";

	public static final String CAS_LOGOUT_ON_SESSION_EXPIRATION =
		"cas,logout.on.session.expiration";

	public static final String CAS_LOGOUT_URL = "cas.logout.url";

	public static final String CAS_NO_SUCH_USER_REDIRECT_URL =
		"cas.no.such.user.redirect.url";

	public static final String CAS_SERVER_NAME = "cas.server.name";

	public static final String CAS_SERVER_URL = "cas.server.url";

	public static final String CAS_SERVICE_URL = "cas.service.url";

	public static final String[] LEGACY_CAS_KEYS = {
		CAS_AUTH_ENABLED, CAS_IMPORT_FROM_LDAP,
		CAS_LOGOUT_ON_SESSION_EXPIRATION, CAS_LOGOUT_URL, CAS_LOGIN_URL,
		CAS_NO_SUCH_USER_REDIRECT_URL, CAS_SERVER_NAME, CAS_SERVER_URL,
		CAS_SERVICE_URL
	};

}