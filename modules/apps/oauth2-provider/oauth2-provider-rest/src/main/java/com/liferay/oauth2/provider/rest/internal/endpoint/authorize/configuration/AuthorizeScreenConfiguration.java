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

package com.liferay.oauth2.provider.rest.internal.endpoint.authorize.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tomas Polesovsky
 */
@ExtendedObjectClassDefinition(
	category = "oauth2", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.oauth2.provider.rest.internal.endpoint.authorize.configuration.AuthorizeScreenConfiguration",
	localization = "content/Language",
	name = "authorize-screen-configuration-name"
)
public interface AuthorizeScreenConfiguration {

	@Meta.AD(
		deflt = "/?p_p_id=com_liferay_oauth2_provider_web_internal_portlet_OAuth2AuthorizePortlet&p_p_state=maximized",
		description = "authorize-screen-url-description",
		id = "authorize.screen.url", name = "authorize-screen-url",
		required = false
	)
	public String authorizeScreenURL();

	@Meta.AD(
		deflt = "/c/portal/login", description = "login-url-description",
		id = "login.url", name = "login-url", required = false
	)
	public String loginURL();

}