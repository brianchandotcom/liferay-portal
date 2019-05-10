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

package com.liferay.portal.security.sso.opensso.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.security.sso.opensso.constants.OpenSSOConfigurationKeys;

import org.osgi.annotation.versioning.ProviderType;

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
	id = "com.liferay.portal.security.sso.opensso.configuration.OpenSSOConfiguration",
	localization = "content/Language", name = "opensso-configuration-name"
)
@ProviderType
public interface OpenSSOConfiguration {

	@Meta.AD(deflt = "false", name = "enabled", required = false)
	public boolean enabled();

	@Meta.AD(
		deflt = OpenSSOConfigurationKeys.VERSION_OPENAM_12, name = "version",
		optionValues = {
			OpenSSOConfigurationKeys.VERSION_OPENAM_12,
			OpenSSOConfigurationKeys.VERSION_OPENAM_13
		},
		required = false
	)
	public String version();

	@Meta.AD(
		deflt = "false", description = "import-from-ldap-description",
		name = "import-from-ldap", required = false
	)
	public boolean importFromLDAP();

	@Meta.AD(
		deflt = "http://openssohost.example.com:8080/opensso/UI/Login?goto=http://portalhost.example.com:8080/c/portal/login",
		name = "login-url", required = false
	)
	public String loginURL();

	@Meta.AD(
		deflt = "false",
		description = "logout-on-session-expiration-description",
		name = "logout-on-session-expiration", required = false
	)
	public boolean logoutOnSessionExpiration();

	@Meta.AD(
		deflt = "http://openssohost.example.com:8080/opensso/UI/Logout?goto=http://portalhost.example.com:8080/web/guest/home",
		name = "logout-url", required = false
	)
	public String logoutURL();

	@Meta.AD(
		deflt = "http://openssohost.example.com:8080/opensso",
		name = "service-url", required = false
	)
	public String serviceURL();

	@Meta.AD(deflt = "uid", name = "screen-name-attr", required = false)
	public String screenNameAttr();

	@Meta.AD(deflt = "mail", name = "email-address-attr", required = false)
	public String emailAddressAttr();

	@Meta.AD(deflt = "givenName", name = "first-name-attr", required = false)
	public String firstNameAttr();

	@Meta.AD(deflt = "sn", name = "last-name-attr", required = false)
	public String lastNameAttr();

}