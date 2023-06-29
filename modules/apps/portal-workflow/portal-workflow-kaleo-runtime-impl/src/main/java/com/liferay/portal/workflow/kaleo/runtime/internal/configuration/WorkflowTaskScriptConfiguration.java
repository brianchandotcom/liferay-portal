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