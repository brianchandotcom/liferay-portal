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

package com.liferay.portal.security.sso.cas.module.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * Defines the configuration property keys and sensible default values.
 * 
 * This class also defines the identity of the configuration schema which, among
 * other things, defines the filename (minus the .cfg extension) for setting
 * values via a file.
 * 
 * @author Michael C. Han
 */
@ConfigurationAdmin(category = "platform")
@Meta.OCD(
	id = "com.liferay.portal.security.sso.cas.module.configuration.CASConfiguration",
	localization = "content/Language"
)
public interface CASConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "Set this to true to enable CAS single sign on.",
		required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "false",
		description = "A user may be authenticated from CAS and not yet exist in the portal. Set this to true to automatically import users from LDAP if they do not exist in the portal.",
		required = false
	)
	public boolean importFromLDAP();

	@Meta.AD(deflt = "https://localhost:8443/cas-web/login", required = false)
	public String loginURL();

	@Meta.AD(
		deflt = "http://localhost:8080",
		description = "Set this to true to log out the user from CAS when the portal session expires.",
		required = false
	)
	public boolean logoutOnSessionExpiration();

	@Meta.AD(deflt = "https://localhost:8443/cas-web/logout", required = false)
	public String logoutURL();

	@Meta.AD(deflt = "http://localhost:8080", required = false)
	public String noSuchUserRedirectURL();

	@Meta.AD(
		deflt = "https://localhost:8080",
		description = "Setting server name allows deep linking. See LEP-4423.",
		required = false
	)
	public String serverName();

	@Meta.AD(deflt = "https://localhost:8443/cas-web", required = false)
	public String serverURL();

	@Meta.AD(deflt = "http://localhost:8080", required = false)
	public String serviceURL();

}