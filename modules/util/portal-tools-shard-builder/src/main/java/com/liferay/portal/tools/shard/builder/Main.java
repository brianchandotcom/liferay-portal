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
		_validate(args);

		Properties databaseProperties = PropsReader.read(args[0]);

		ShardExporter shardExporter = ShardExporterFactory.getShardExporter(
			databaseProperties);

		String schema = args[1];

		List<Long> companyIds = Arrays.asList(
			new Long[] {Long.parseLong(args[2])});

		String outputFolder = args[3];

		ExportContext exportContext = new ExportContext(
			companyIds, outputFolder, schema);

		shardExporter.export(exportContext);
	}

	private static void _validate(String[] args) throws Exception {
		if ((args == null) || (args.length == 0)) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		if (args.length != 4) {
			throw new IllegalArgumentException(
				"Only four argument can be passed: 1) database properties " +
					"file, 2) name of the schema to export, 3) ID of the " +
					"company to export, and 4) target folder where the " +
					"inserts for a schema will be written");
		}

		File propertiesFile = new File(args[0]);

		if (!propertiesFile.exists()) {
			throw new FileNotFoundException(
				"Database properties does not exist");
		}

		try {
			Long.parseLong(args[2]);
		}
		catch (NumberFormatException nfe) {
			throw new IllegalArgumentException(
				"CompanyID is not a valid number");
		}

		File folder = new File(args[3]);

		if (!folder.exists()) {
			throw new FileNotFoundException("Output directory does not exist");
		}

		if (!folder.canRead() || !folder.canWrite()) {
			throw new IllegalArgumentException("Output directory is read-only");
		}
	}

}