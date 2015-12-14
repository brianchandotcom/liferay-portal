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

import com.beust.jcommander.JCommander;

import com.liferay.portal.tools.shard.builder.exporter.ShardExporter;
import com.liferay.portal.tools.shard.builder.exporter.ShardExporterFactory;
import com.liferay.portal.tools.shard.builder.exporter.context.ExportContext;
import com.liferay.portal.tools.shard.builder.internal.util.PropsReader;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author Manuel de la Peña
 */
public class Main {

	public static void main(String[] args) throws Exception {
		MainParameters mainParameters = _validate(args);

		Properties databaseProperties = PropsReader.read(
			mainParameters.getDatabaseProperties());

		ShardExporter shardExporter = ShardExporterFactory.getShardExporter(
			databaseProperties);

		String schema = mainParameters.getSchemaName();

		List<Long> companyIds = Arrays.asList(
			new Long[] {Long.parseLong(mainParameters.getCompanies())});

		String outputFolder = mainParameters.getOutputDir();

		ExportContext exportContext = new ExportContext(
			companyIds, outputFolder, schema);

		shardExporter.export(exportContext);
	}

	private static MainParameters _validate(String[] args) throws Exception {
		MainParameters mainParameters = new MainParameters();

		if ((args == null) || (args.length == 0)) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		new JCommander(mainParameters, args);

		String companies = mainParameters.getCompanies();
		String databaseProperties = mainParameters.getDatabaseProperties();
		String outputDir = mainParameters.getOutputDir();
		String schemaName = mainParameters.getSchemaName();

		if ((companies == null) || (databaseProperties == null) ||
			(outputDir == null) || (schemaName == null)) {

			throw new IllegalArgumentException(mainParameters.usage());
		}

		if (companies.isEmpty() || databaseProperties.isEmpty() ||
			outputDir.isEmpty() || schemaName.isEmpty()) {

			throw new IllegalArgumentException(mainParameters.usage());
		}

		File propertiesFile = new File(databaseProperties);

		if (!propertiesFile.exists()) {
			throw new FileNotFoundException(
				"Database properties does not exist");
		}

		try {
			Long.parseLong(companies);
		}
		catch (NumberFormatException nfe) {
			throw new IllegalArgumentException(
				"CompanyID is not a valid number");
		}

		File folder = new File(outputDir);

		if (!folder.exists()) {
			throw new FileNotFoundException("Output directory does not exist");
		}

		if (!folder.canRead() || !folder.canWrite()) {
			throw new IllegalArgumentException("Output directory is read-only");
		}

		return mainParameters;
	}

}