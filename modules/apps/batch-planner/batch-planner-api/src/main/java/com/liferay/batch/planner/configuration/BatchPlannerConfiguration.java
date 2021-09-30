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

package com.liferay.batch.planner.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Igor Beslic
 */
@ExtendedObjectClassDefinition(category = "batch-engine")
@Meta.OCD(
	id = "com.liferay.batch.planner.configuration.BatchPlannerConfiguration",
	localization = "content/Language", name = "batch-planner-configuration-name"
)
public interface BatchPlannerConfiguration {

	@Meta.AD(
		deflt = "batch_planner_temp",
		name = "batch-planner-temporary-directory", required = false
	)
	public String temporaryDirectory();

}