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

package com.liferay.layout.set.prototype.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Vendel Toreki
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	description = "layout-set-prototype-system-configuration-description",
	id = "com.liferay.layout.set.prototype.configuration.LayoutSetPrototypeSystemConfiguration",
	localization = "content/Language",
	name = "layout-set-prototype-system-configuration-name"
)
public interface LayoutSetPrototypeSystemConfiguration {

	@Meta.AD(
		deflt = "group", description = "import-task-isolation-help",
		name = "import-task-isolation", optionLabels = {"Group", "Company"},
		optionValues = {"group", "company"}, required = false
	)
	public String importTaskIsolation();

}