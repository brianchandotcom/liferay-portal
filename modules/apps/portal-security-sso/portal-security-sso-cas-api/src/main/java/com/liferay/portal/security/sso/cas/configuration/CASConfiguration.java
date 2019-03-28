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

package com.liferay.portal.security.sso.cas.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * Defines the configuration property keys and sensible default values.
 *
 * <p>
 * This class also defines the identity of the configuration schema which, among
 * other things, defines the filename (minus the <code>.cfg</code> extension)
 * for setting values via a file.
 * </p>
 *
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "sso")
@Meta.OCD(
	id = "com.liferay.portal.security.sso.cas.configuration.CASConfiguration",
	localization = "content/Language", name = "cas-configuration-name"
)
public interface CASConfiguration {

	@Meta.AD(
		deflt = "false", description = "enabled-help", name = "enabled",
		required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "false", description = "import-from-ldap-help",
		name = "import-from-ldap", required = false
	)
	public boolean importFromLDAP();

	@Meta.AD(
		deflt = "https://localhost:8443/cas-web/login", name = "login-url",
		required = false
	)
	public String loginURL();

	@Meta.AD(
		deflt = "false", description = "logout-on-session-expiration-help",
		name = "logout-on-session-expiration", required = false
	)
	public boolean logoutOnSessionExpiration();

	@Meta.AD(
		deflt = "https://localhost:8443/cas-web/logout", name = "logout-url",
		required = false
	)
	public String logoutURL();

	@Meta.AD(
		deflt = "http://localhost:8080", description = "server-name-help",
		name = "server-name", required = false
	)
	public String serverName();

	@Meta.AD(
		deflt = "https://localhost:8443/cas-web", name = "server-url",
		required = false
	)
	public String serverURL();

	@Meta.AD(name = "service-url", required = false)
	public String serviceURL();

	@Meta.AD(
		deflt = "http://localhost:8080", name = "no-such-user-redirect-url",
		required = false
	)
	public String noSuchUserRedirectURL();

}