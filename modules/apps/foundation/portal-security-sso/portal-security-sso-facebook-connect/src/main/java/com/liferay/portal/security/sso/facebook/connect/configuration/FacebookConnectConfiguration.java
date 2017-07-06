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

package com.liferay.portal.security.sso.facebook.connect.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.portal.security.sso.facebook.connect.configuration.FacebookConnectConfiguration",
	localization = "content/Language",
	name = "facebook-connect-configuration-name"
)
public interface FacebookConnectConfiguration {

	@Meta.AD(deflt = "false", required = false)
	public boolean enabled();

	@Meta.AD(
		deflt = "false", name = "require-verified-account", required = false
	)
	public boolean verifiedAccountRequired();

	@Meta.AD(name = "application-id", required = false)
	public String appId();

	@Meta.AD(name = "application-secret", required = false)
	public String appSecret();

	@Meta.AD(deflt = "https://graph.facebook.com", required = false)
	public String graphURL();

	@Meta.AD(
		deflt = "https://graph.facebook.com/oauth/authorize",
		name = "oauth-authentication-url", required = false
	)
	public String oauthAuthURL();

	@Meta.AD(
		deflt = "https://graph.facebook.com/oauth/access_token",
		name = "oauth-token-url", required = false
	)
	public String oauthTokenURL();

	@Meta.AD(
		deflt = "http://localhost:8080/c/portal/facebook_connect_oauth",
		name = "redirect-url", required = false
	)
	public String oauthRedirectURL();

}