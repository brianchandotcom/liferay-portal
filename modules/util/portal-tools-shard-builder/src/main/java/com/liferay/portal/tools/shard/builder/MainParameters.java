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

	public String usage() {
		return "Only four argument can be passed: 1) database properties " +
			"file, 2) name of the schema to export, 3) ID of the " +
			"company to export, and 4) target folder where the " +
			"inserts for a schema will be written";
	}

	@Parameter(
		description = "Comma-separated list of company Ids to be exported",
		names = { "-C", "--companies" }
	)
	private String _companies;

	@Parameter(
		description = "Database properties configuration",
		names = { "-P", "--properties" }
	)
	private String _databaseProperties;

	@Parameter(
		description = "Output directory", names = { "-O", "--output-dir" }
	)
	private String _outputDir;

	@Parameter(description = "Schema name", names = { "-S", "--schema-name" })
	private String _schemaName;

}