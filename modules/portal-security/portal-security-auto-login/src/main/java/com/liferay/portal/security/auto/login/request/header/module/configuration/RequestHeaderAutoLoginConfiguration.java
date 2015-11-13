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

package com.liferay.portal.security.auto.login.request.header.module.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

/**
 * @author Tomas Polesovsky
 */
@ConfigurationAdmin(category = "platform")
@Meta.OCD(
	id = "com.liferay.portal.security.auto.login.request.header.module.configuration.RequestHeaderAutoLoginConfiguration"
)
public interface RequestHeaderAutoLoginConfiguration {

	@Meta.AD(
		deflt = "255.255.255.255",
		description = "Input a list of comma delimited IPs that can automatically authenticate via request headers. Input a blank list to allow any IP to automatically authenticate via request headers. SERVER_IP will be replaced with the IP of the host server.",
		required = false
	)
	public String authHostsAllowed();

	@Meta.AD(deflt = "false", required = false)
	public boolean enabled();

	@Meta.AD(
		deflt = "false",
		description = "Set this to true to automatically import users from LDAP if they do not exist in the portal.",
		required = false
	)
	public boolean importFromLDAP();

}