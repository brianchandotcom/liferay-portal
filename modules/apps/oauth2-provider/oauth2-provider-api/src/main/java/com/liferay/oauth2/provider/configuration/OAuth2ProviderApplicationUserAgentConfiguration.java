/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Raymond Augé
 */
@ExtendedObjectClassDefinition(generateUI = false)
@Meta.OCD(
	factory = true,
	id = "com.liferay.oauth2.provider.configuration.OAuth2ProviderApplicationUserAgentConfiguration"
)
public interface OAuth2ProviderApplicationUserAgentConfiguration {

	@Meta.AD(type = Meta.Type.String)
	public String baseURL();

	@Meta.AD(deflt = "", required = false, type = Meta.Type.String)
	public String description();

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Meta.AD(required = false, type = Meta.Type.String)
	public String homePageURL();

	@Meta.AD(deflt = "", required = false, type = Meta.Type.String)
	public String name();

	@Meta.AD(deflt = "", required = false, type = Meta.Type.String)
	public String privacyPolicyURL();

	@Meta.AD(required = false, type = Meta.Type.String)
	public String[] scopes();

}