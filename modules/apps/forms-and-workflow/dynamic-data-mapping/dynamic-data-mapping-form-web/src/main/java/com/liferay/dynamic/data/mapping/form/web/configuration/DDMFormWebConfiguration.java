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

package com.liferay.dynamic.data.mapping.form.web.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Lino Alves
 */
@ExtendedObjectClassDefinition(
	category = "dynamic-data-mapping",
	scope = ExtendedObjectClassDefinition.Scope.GROUP
)
@Meta.OCD(
	id = "com.liferay.dynamic.data.mapping.form.web.configuration.DDMFormWebConfiguration",
	localization = "content/Language", name = "ddm-form-web-configuration-name"
)
public interface DDMFormWebConfiguration {

	@Meta.AD(
		deflt = "1", description = "autosave-interval-description",
		name = "autosave-interval-name", required = false
	)
	public int autosaveInterval();

	@Meta.AD(
		deflt = "descriptive", name = "default-display-view",
		optionLabels = {"Descriptive", "List"},
		optionValues = {"descriptive", "list"}, required = false
	)
	public String defaultDisplayView();

}