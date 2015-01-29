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

package com.liferay.portal.sso.cas.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Michael C. Han
 */
@Meta.OCD(
	id = "com.liferay.portal.sso.cas", localization = "content.Language"
)
public interface CASConfiguration {

	@Meta.AD(
		deflt = "false", id = "enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "false", id = "import.from.ldap", required = false
	)
	public boolean importFromLDAP();

	@Meta.AD(
		deflt = "https://localhost:8443/cas-web/login", id = "login.url",
		required = false
	)
	public String loginURL();

	@Meta.AD(
		deflt = "http://localhost:8080", id = "logout.on.session.expiration",
		required = false
	)
	public boolean logoutOnSessionExpiration();

	@Meta.AD(
		deflt = "https://localhost:8443/cas-web/logout", id = "logout.url",
		required = false
	)
	public String logoutURL();

	@Meta.AD(
		deflt = "http://localhost:8080", id = "no.such.user.redirect.url",
		required = false
	)
	public String noSuchUserRedirectURL();

	@Meta.AD(
		deflt = "https://localhost:8080", id = "server.name", required = false
	)
	public String serverName();

	@Meta.AD(
		deflt = "https://localhost:8443/cas-web", id = "server.url",
		required = false
	)
	public String serverURL();

	@Meta.AD(
		deflt = "http://localhost:8080", id = "service.url", required = false
	)
	public String serviceURL();

}