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

package com.liferay.exportimport.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Ha Tang
 */
@ExtendedObjectClassDefinition(category = "infrastructure")
@Meta.OCD(
	description = "export-import-system-configuration-description",
	id = "com.liferay.exportimport.configuration.ExportImportSystemConfiguration",
	localization = "content/Language",
	name = "export-import-system-configuration-name"
)
@ProviderType
public interface ExportImportSystemConfiguration {

	/**
	 * Set the interval in minutes on how often
	 * DeleteExpiredBackgroundTasksSchedulerJobConfiguration will run to check for expired
	 * Export/Import entries.
	 * Default value is 30.
	 */
	@Meta.AD(
		deflt = "30", description = "export-import-clean-up-job-interval-help",
		name = "export-import-clean-up-job-interval", required = false
	)
	public int cleanupJobInterval();

}