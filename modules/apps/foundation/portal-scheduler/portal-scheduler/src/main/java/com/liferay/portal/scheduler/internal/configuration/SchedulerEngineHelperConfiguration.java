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

package com.liferay.portal.scheduler.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	id = "com.liferay.portal.scheduler.internal.configuration.SchedulerEngineHelperConfiguration",
	localization = "content/Language",
	name = "scheduler-engine-helper-configuration-name"
)
public interface SchedulerEngineHelperConfiguration {

	@Meta.AD(
		deflt = "false", description = "audit-scheduler-job-enabled-help",
		name = "audit-scheduler-job-enabled", required = false
	)
	public boolean auditSchedulerJobEnabled();

}