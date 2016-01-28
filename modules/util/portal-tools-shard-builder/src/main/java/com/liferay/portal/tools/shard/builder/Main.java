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

		ExportContext exportContext = mainParameters.toExportContext();

		shardExporter.export(exportContext);
	}

	private static MainParameters _validate(String[] args) throws Exception {
		MainParameters mainParameters = new MainParameters();

		if ((args == null) || (args.length == 0)) {
			throw new IllegalArgumentException("Arguments cannot be null");
		}

		new JCommander(mainParameters, args);

		return mainParameters;
	}

}