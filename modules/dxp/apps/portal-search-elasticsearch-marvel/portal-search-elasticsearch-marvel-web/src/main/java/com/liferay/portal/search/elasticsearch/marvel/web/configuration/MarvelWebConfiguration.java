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

package com.liferay.portal.search.elasticsearch.marvel.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Miguel Angelo Caldas Gallindo
 * @author Artur Aquino
 * @author André de Oliveira
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.portal.search.elasticsearch.marvel.web.configuration.MarvelWebConfiguration",
	localization = "content/Language", name = "marvel-web-configuration-name"
)
public interface MarvelWebConfiguration {

	@Meta.AD(
		deflt = "http://localhost:5601", description = "kibana-url-help",
		name = "kibana-url", required = false
	)
	public String kibanaURL();

	@Meta.AD(
		description = "proxy-servlet-log-enable-help",
		name = "proxy-servlet-log-enable", required = false
	)
	public boolean proxyServletLogEnable();

	@Meta.AD(
		deflt = "liferay", description = "shield-password-help",
		name = "shield-password", required = false, type = Meta.Type.Password
	)
	public String shieldPassword();

	@Meta.AD(
		deflt = "liferay", description = "shield-username-help",
		name = "shield-username", required = false
	)
	public String shieldUserName();

}