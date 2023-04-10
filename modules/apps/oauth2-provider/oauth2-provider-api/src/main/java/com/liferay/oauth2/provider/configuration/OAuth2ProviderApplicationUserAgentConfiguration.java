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

package com.liferay.oauth2.provider.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Raymond Augé
 */
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