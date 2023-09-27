/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.runtime.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Ricardo Couso
 */
@ExtendedObjectClassDefinition(category = "workflow")
@Meta.OCD(
	id = "com.liferay.portal.workflow.kaleo.runtime.internal.configuration.WorkflowTaskScriptConfiguration",
	localization = "content/Language",
	name = "workflow-task-script-configuration-name"
)
public interface WorkflowTaskScriptConfiguration {

	@Meta.AD(
		deflt = "0",
		description = "scripted-assignment-cache-expiration-time-description",
		name = "scripted-assignment-cache-expiration-time-name",
		required = false
	)
	public int scriptedAssignmentCacheExpirationTime();

}