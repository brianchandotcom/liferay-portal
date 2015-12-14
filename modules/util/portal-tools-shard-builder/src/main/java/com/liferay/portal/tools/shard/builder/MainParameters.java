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
import com.liferay.portal.tools.shard.builder.internal.validators.CompanyIdsParamValidator;
import com.liferay.portal.tools.shard.builder.internal.validators.FileParamExistsValidator;
import com.liferay.portal.tools.shard.builder.internal.validators.RequiredParamValidator;
import com.liferay.portal.tools.shard.builder.internal.validators.WritableFileParamValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class MainParameters {

	public String getCompanies() {
		return _companies;
	}

	public String getDatabaseProperties() {
		return _databaseProperties;
	}

	public String getOutputDir() {
		return _outputDir;
	}

	public String getSchemaName() {
		return _schemaName;
	}

	public ExportContext toExportContext() {
		return new ExportContext(
			_getCompanyIds(), _outputDir, _schemaName);
	}

	private List<Long> _getCompanyIds() {
		String[] companyIds = _companies.split(",");

		List<Long> list = new ArrayList<>(companyIds.length);

		for (String companyId : companyIds) {
			list.add(Long.parseLong(companyId));
		}

		return list;
	}

	@Parameter(
		description = "Comma-separated list of company Ids to be exported",
		names = { "-C", "--companies" },
		validateWith = CompanyIdsParamValidator.class
	)
	private String _companies;

	@Parameter(
		description = "Database properties configuration",
		names = { "-P", "--properties" },
		validateWith = FileParamExistsValidator.class
	)
	private String _databaseProperties;

	@Parameter(
		description = "Output directory", names = { "-O", "--output-dir" },
		validateWith = WritableFileParamValidator.class
	)
	private String _outputDir;

	@Parameter(
		description = "Schema name", names = { "-S", "--schema-name" },
		validateWith = RequiredParamValidator.class
	)
	private String _schemaName;

}