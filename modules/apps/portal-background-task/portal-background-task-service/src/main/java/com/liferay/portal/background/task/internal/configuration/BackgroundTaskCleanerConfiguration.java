/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.background.task.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Dante Wang
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.portal.background.task.internal.configuration.BackgroundTaskCleanerConfiguration",
	localization = "content/Language",
	name = "background-task-cleaner-configuration-name"
)
public interface BackgroundTaskCleanerConfiguration {

	@Meta.AD(
		deflt = "5", description = "background-task-cleaner-interval-help",
		name = "background-task-cleaner-interval", required = false
	)
	public int interval();

}