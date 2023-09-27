/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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