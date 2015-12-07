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

package com.liferay.portal.security.sso.token.internal.module.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;
import com.liferay.portal.security.sso.token.security.auth.TokenLocation;

/**
 * @author Michael C. Han
 * @author Mika Koivisto
 */
@ConfigurationAdmin(category = "platform")
@Meta.OCD(
	id = "com.liferay.portal.security.sso.token.internal.module.configuration.TokenConfiguration"
)
public interface TokenConfiguration {

	@Meta.AD(
		deflt ="SMIDENTITY|SMSESSION",
		description = "Set this to the cookie names that must be removed after logout.",
		required = false
	)
	public String[] authenticationCookies();

	@Meta.AD(deflt = "false", required = false)
	public boolean enabled();

	@Meta.AD(
		deflt = "false",
		description = "Set this to true to automatically import users from LDAP if they do not exist in the portal.",
		required = false
	)
	public boolean importFromLDAP();

	@Meta.AD(required = false)
	public String logoutRedirectURL();

	@Meta.AD(
		deflt = "REQUEST_HEADER",
		description = "Set this to the location of the user token.",
		required = false
	)
	public TokenLocation tokenLocation();

	@Meta.AD(
		deflt = "SM_USER",
		description = "Set this to the name of the user token that authenticator passes to the portal.",
		required = false
	)
	public String userTokenName();

}