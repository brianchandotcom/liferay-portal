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
		deflt = "false", id = "cas.auth.enabled", required = false)
	public boolean casAuthEnabled();

	@Meta.AD(
		deflt = "false", id = "cas.import.from.ldap", required = false)
	public boolean casImportFromLDAP();

	@Meta.AD(
		deflt = "https://localhost:8443/cas-web/login", id = "cas.login.url",
		required = false)
	public String casLoginURL();

	@Meta.AD(
		deflt = "http://localhost:8080",
		id = "cas.logout.on.session.expiration", required = false)
	public boolean casLogoutOnSessionExpiration();

	@Meta.AD(
		deflt = "https://localhost:8443/cas-web/logout", id = "cas.logout.url",
		required = false)
	public String casLogoutURL();

	@Meta.AD(
		deflt = "http://localhost:8080", id = "cas.no.such.user.redirect.url",
		required = false)
	public String casNoSuchUserRedirectURL();

	@Meta.AD(
		deflt = "https://localhost:8080", id = "cas.server.name",
		required = false)
	public String casServerName();

	@Meta.AD(
		deflt = "https://localhost:8443/cas-web", id = "cas.server.url",
		required = false)
	public String casServerURL();

	@Meta.AD(
		deflt = "http://localhost:8080", id = "cas.service.url",
		required = false)
	public String casServiceURL();

}