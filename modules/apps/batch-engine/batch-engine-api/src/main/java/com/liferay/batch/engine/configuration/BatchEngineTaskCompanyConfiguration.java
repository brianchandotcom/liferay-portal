/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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