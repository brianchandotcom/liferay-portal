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

package com.liferay.portal.security.sso.token.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.security.sso.token.security.auth.TokenLocation;

/**
 * @author Michael C. Han
 * @author Mika Koivisto
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.portal.security.sso.token.configuration.TokenConfiguration",
	localization = "content/Language", name = "token-configuration-name"
)
public interface TokenConfiguration {

	@Meta.AD(deflt = "false", required = false)
	public boolean enabled();

	@Meta.AD(
		deflt = "false", description = "import-from-ldap-help", required = false
	)
	public boolean importFromLDAP();

	@Meta.AD(
		deflt = "SM_USER", description = "user-token-name-help",
		required = false
	)
	public String userTokenName();

	@Meta.AD(
		deflt = "REQUEST_HEADER", description = "token-location-help",
		required = false
	)
	public TokenLocation tokenLocation();

	@Meta.AD(
		deflt = "SMIDENTITY|SMSESSION",
		description = "authentication-cookies-help", required = false
	)
	public String[] authenticationCookies();

	@Meta.AD(required = false)
	public String logoutRedirectURL();

}