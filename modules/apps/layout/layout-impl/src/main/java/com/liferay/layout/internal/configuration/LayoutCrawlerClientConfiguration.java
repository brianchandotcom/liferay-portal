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

package com.liferay.layout.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author dnebinger
 */
@ExtendedObjectClassDefinition(
	category = "pages", scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	description = "layout-crawler-client-configuration-description",
	id = "com.liferay.layout.internal.configuration.LayoutCrawlerClientConfiguration",
	localization = "content/Language",
	name = "layout-crawler-client-configuration-name"
)
public interface LayoutCrawlerClientConfiguration {

	@Meta.AD(
		deflt = "false",
		description = "layout-crawler-client-configuration-enabled-description",
		name = "enabled", required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "localhost",
		description = "layout-crawler-client-configuration-hostname-description",
		name = "hostname", required = false
	)
	public String hostName();

	@Meta.AD(
		deflt = "8080",
		description = "layout-crawler-client-configuration-port-description",
		name = "port", required = false
	)
	public int port();

	@Meta.AD(
		deflt = "false",
		description = "layout-crawler-client-configuration-secure-description",
		name = "secure", required = false
	)
	public boolean secure();

}