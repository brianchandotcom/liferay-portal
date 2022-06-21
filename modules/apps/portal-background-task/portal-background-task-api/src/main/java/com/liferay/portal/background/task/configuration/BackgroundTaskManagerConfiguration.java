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

package com.liferay.portal.background.task.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Vendel Toreki
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.portal.background.task.configuration.BackgroundTaskManagerConfiguration",
	localization = "content/Language",
	name = "background-task-manager-configuration-name"
)
public interface BackgroundTaskManagerConfiguration {

	@Meta.AD(
		deflt = "5", description = "background-task-workers-core-size-help",
		name = "background-task-workers-core-size", required = false
	)
	public int workersCoreSize();

	@Meta.AD(
		deflt = "10", description = "background-task-workers-max-size-help",
		name = "background-task-workers-max-size", required = false
	)
	public int workersMaxSize();

}