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

package com.liferay.batch.engine.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Matija Petanjek
 */
@ExtendedObjectClassDefinition(
	category = "batch-engine",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.batch.engine.configuration.BatchEngineTaskCompanyConfiguration",
	localization = "content/Language",
	name = "batch-engine-task-company-configuration-name"
)
public interface BatchEngineTaskCompanyConfiguration {

	@Meta.AD(deflt = ",", name = "csv-file-column-delimiter", required = false)
	public String csvFileColumnDelimiter();

	@Meta.AD(
		deflt = "100", description = "export-batch-size-description",
		name = "export-batch-size", required = false
	)
	public int exportBatchSize();

	@Meta.AD(
		deflt = "100", description = "import-batch-size-description",
		name = "import-batch-size", required = false
	)
	public int importBatchSize();

}