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

package com.liferay.portal.security.sso.opensso.module.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

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
@ConfigurationAdmin(category = "platform")
@Meta.OCD(
	id = "com.liferay.portal.security.sso.opensso.module.configuration.OpenSSOConfiguration",
	localization = "content/Language"
)
public interface OpenSSOConfiguration {

	@Meta.AD(deflt = "mail", required = false)
	public String emailAddressAttr();

	@Meta.AD(deflt = "false", required = false)
	public boolean enabled();

	@Meta.AD(deflt = "givenname", required = false)
	public String firstNameAttr();

	@Meta.AD(
		deflt = "false",
		description = "Set this to true if you want to import the users from LDAP after successful login through OpenSSO. The LDAP settings need to be configured properly in the LDAP section. If this is set to false, the users will be created from OpenSSO provided data.",
		required = false
	)
	public boolean importFromLDAP();

	@Meta.AD(deflt = "sn", required = false)
	public String lastNameAttr();

	@Meta.AD(
		deflt = "http://openssohost.example.com:8080/opensso/UI/Login?goto=http://portalhost.example.com:8080/c/portal/login",
		required = false
	)
	public String loginURL();

	@Meta.AD(
		deflt = "false",
		description = "Set this to true to log a user out of OpenSSO when the portal session expires.",
		required = false
	)
	public boolean logoutOnSessionExpiration();

	@Meta.AD(
		deflt = "http://openssohost.example.com:8080/opensso/UI/Logout?goto=http://portalhost.example.com:8080/web/guest/home",
		required = false
	)
	public String logoutURL();

	@Meta.AD(deflt = "uid", required = false)
	public String screenNameAttr();

	@Meta.AD(
		deflt = "http://openssohost.example.com:8080/opensso", required = false
	)
	public String serviceURL();

}