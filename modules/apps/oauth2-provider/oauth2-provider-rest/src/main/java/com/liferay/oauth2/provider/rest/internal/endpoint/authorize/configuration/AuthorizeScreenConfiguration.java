/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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