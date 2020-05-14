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

package com.liferay.headless.discovery.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Javier Gamarra
 */
@Meta.OCD(
	description = "headless-discovery-description",
	id = "com.liferay.headless.discovery.internal.configuration.HeadlessDiscoveryConfiguration",
	localization = "content/Language",
	name = "headless-discovery-configuration-name"
)
public interface HeadlessDiscoveryConfiguration {

	@Meta.AD(deflt = "true", name = "enable-api-explorer", required = false)
	public boolean enableAPIExplorer();

}