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

package com.liferay.portal.tools.shard.builder;

import com.beust.jcommander.Parameter;

import com.liferay.portal.tools.shard.builder.exporter.context.ExportContext;
import com.liferay.portal.tools.shard.builder.internal.validators.CompanyIdsRequiredParameterValidator;
import com.liferay.portal.tools.shard.builder.internal.validators.FileRequiredParameterValidator;
import com.liferay.portal.tools.shard.builder.internal.validators.RequiredParameterValidator;
import com.liferay.portal.tools.shard.builder.internal.validators.WritableFileRequiredParameterValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class MainParameters {

	public String getCompanyIds() {
		return _companyIds;
	}

	public String getOutputDirName() {
		return _outputDirName;
	}

	public String getPropertiesFileName() {
		return _propertiesFileName;
	}

	public String getSchemaName() {
		return _schemaName;
	}

	public boolean isWriteFile() {
		return _writeFile;
	}

	public ExportContext toExportContext() {
		return new ExportContext(
			_getCompanyIds(), _outputDirName, _schemaName, _writeFile);
	}

	private List<Long> _getCompanyIds() {
		List<Long> companyIds = new ArrayList<>();

		for (String companyId : _companyIds.split(",")) {
			companyIds.add(Long.parseLong(companyId));
		}

		return companyIds;
	}

	@Parameter(
		description = "Comma-separated list of company IDs to be exported",
		names = {"-C", "--company-ids"},
		validateWith = CompanyIdsRequiredParameterValidator.class
	)
	private String _companyIds;

	@Parameter(
		description = "Output directory", names = {"-O", "--output-dir"},
		validateWith = WritableFileRequiredParameterValidator.class
	)
	private String _outputDirName;

	@Parameter(
		description = "Properties file with database configuration",
		names = {"-P", "--properties-file"},
		validateWith = FileRequiredParameterValidator.class
	)
	private String _propertiesFileName;

	@Parameter(
		description = "Schema name to be exported",
		names = {"-S", "--schema-name"},
		validateWith = RequiredParameterValidator.class
	)
	private String _schemaName;

	@Parameter(
		description = "Whether to write tables to separate SQL files",
		names = {"-W", "--write-file"}
	)
	private boolean _writeFile;

}