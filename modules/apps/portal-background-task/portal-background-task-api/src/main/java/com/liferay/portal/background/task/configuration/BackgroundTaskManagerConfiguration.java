/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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