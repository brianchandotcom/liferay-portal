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

package com.liferay.portal.security.sso.facebook.connect.module.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Michael C. Han
 */
@ConfigurationAdmin(category = "platform")
@Meta.OCD(
	id = "com.liferay.portal.security.sso.facebook.connect.module.configuration.FacebookConnectConfiguration"
)
public interface FacebookConnectConfiguration {

	@Meta.AD(deflt = "", required = false)
	public String appId();

	@Meta.AD(deflt = "", required = false)
	public String appSecret();

	@Meta.AD(deflt = "false", required = false)
	public boolean enabled();

	@Meta.AD(deflt = "https://graph.facebook.com", required = false)
	public String graphURL();

	@Meta.AD(
		deflt = "https://graph.facebook.com/oauth/authorize", required = false
	)
	public String oauthAuthURL();

	@Meta.AD(
		deflt = "http://localhost:8080/c/login/facebook_connect_oauth",
		required = false
	)
	public String oauthRedirectURL();

	@Meta.AD(
		deflt = "https://graph.facebook.com/oauth/access_token",
		required = false
	)
	public String oauthTokenURL();

	@Meta.AD(deflt = "false", required = false)
	public boolean verifiedAccountRequired();

}