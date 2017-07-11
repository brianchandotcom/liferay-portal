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

package com.liferay.server.admin.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.scheduler.TimeUnit;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.server.admin.web.configuration.PluginRepositoriesConfiguration",
	localization = "content/Language",
	name = "plugin-repositories-configuration-name"
)
public interface PluginRepositoriesConfiguration {

	@Meta.AD(deflt = "true", required = false)
	public boolean enabled();

	@Meta.AD(deflt = "1", required = false)
	public int interval();

	@Meta.AD(deflt = "DAY", required = false)
	public TimeUnit timeUnit();

}