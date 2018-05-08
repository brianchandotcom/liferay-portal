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

package com.liferay.portal.security.sso.ntlm.constants;

/**
 * @author Brian Greenwald
 */
public class LegacyNtlmPropsKeys {

	public static final String NTLM_AUTH_DOMAIN = "ntlm.auth.domain";

	public static final String NTLM_AUTH_DOMAIN_CONTROLLER =
		"ntlm.auth.domain.controller";

	public static final String NTLM_AUTH_DOMAIN_CONTROLLER_NAME =
		"ntlm.auth.domain.controller.name";

	public static final String NTLM_AUTH_ENABLED = "ntlm.auth.enabled";

	public static final String[] NTLM_AUTH_KEYS = {
		NTLM_AUTH_DOMAIN, NTLM_AUTH_DOMAIN_CONTROLLER,
		NTLM_AUTH_DOMAIN_CONTROLLER_NAME, NTLM_AUTH_ENABLED,
		LegacyNtlmPropsKeys.NTLM_AUTH_NEGOTIATE_FLAGS,
		LegacyNtlmPropsKeys.NTLM_AUTH_SERVICE_ACCOUNT,
		LegacyNtlmPropsKeys.NTLM_AUTH_SERVICE_PASSWORD
	};

	public static final String NTLM_AUTH_NEGOTIATE_FLAGS =
		"ntlm.auth.negotiate.flags";

	public static final String NTLM_AUTH_SERVICE_ACCOUNT =
		"ntlm.auth.service.account";

	public static final String NTLM_AUTH_SERVICE_PASSWORD =
		"ntlm.auth.service.password";

}