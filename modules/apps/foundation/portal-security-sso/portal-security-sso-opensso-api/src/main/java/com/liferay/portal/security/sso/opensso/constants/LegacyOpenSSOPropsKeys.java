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

package com.liferay.portal.security.sso.opensso.constants;

/**
 * @author Brian Greenwald
 */
public class LegacyOpenSSOPropsKeys {

	public static final String OPENSSO_AUTH_ENABLED = "open.sso.auth.enabled";

	public static final String OPENSSO_EMAIL_ADDRESS_ATTR =
		"open.sso.email.address.attr";

	public static final String OPENSSO_FIRST_NAME_ATTR =
		"open.sso.first.name.attr";

	public static final String OPENSSO_IMPORT_FROM_LDAP =
		"open.sso.ldap.import.enabled";

	public static final String[] OPENSSO_KEYS = {
		OPENSSO_EMAIL_ADDRESS_ATTR, OPENSSO_AUTH_ENABLED,
		OPENSSO_FIRST_NAME_ATTR, OPENSSO_IMPORT_FROM_LDAP,
		LegacyOpenSSOPropsKeys.OPENSSO_LAST_NAME_ATTR,
		LegacyOpenSSOPropsKeys.OPENSSO_LOGIN_URL,
		LegacyOpenSSOPropsKeys.OPENSSO_LOGOUT_ON_SESSION_EXPIRATION,
		LegacyOpenSSOPropsKeys.OPENSSO_LOGOUT_URL,
		LegacyOpenSSOPropsKeys.OPENSSO_SCREEN_NAME_ATTR,
		LegacyOpenSSOPropsKeys.OPENSSO_SERVICE_URL
	};

	public static final String OPENSSO_LAST_NAME_ATTR =
		"open.sso.last.name.attr";

	public static final String OPENSSO_LOGIN_URL = "open.sso.login.url";

	public static final String OPENSSO_LOGOUT_ON_SESSION_EXPIRATION =
		"open.sso.logout.on.session.expiration";

	public static final String OPENSSO_LOGOUT_URL = "open.sso.logout.url";

	public static final String OPENSSO_SCREEN_NAME_ATTR =
		"open.sso.screen.name.attr";

	public static final String OPENSSO_SERVICE_URL = "open.sso.service.url";

}