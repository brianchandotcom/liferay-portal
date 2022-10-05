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
 * @author Tamas Molnar
 */
@ExtendedObjectClassDefinition(
	category = "infrastructure",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	description = "layout-set-prototype-configuration-description",
	id = "com.liferay.layout.set.prototype.configuration.LayoutSetPrototypeConfiguration",
	localization = "content/Language",
	name = "layout-set-prototype-configuration-name"
)
public interface LayoutSetPrototypeConfiguration {

	@Meta.AD(
		deflt = "false", description = "trigger-propagation-help",
		name = "trigger-propagation", required = false
	)
	public boolean triggerPropagation();

	@Meta.AD(
		deflt = "true", description = "cancel-propagation-import-task-help",
		name = "cancel-propagation-import-task", required = false
	)
	public boolean cancelPropagationImportTask();

}