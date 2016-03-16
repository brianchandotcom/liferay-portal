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

package com.liferay.portal.tools.data.partitioning.sql.builder;

import com.beust.jcommander.JCommander;

import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.ShardExporter;
import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.ShardExporterFactory;
import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.context.ExportContext;

/**
 * @author Manuel de la Peña
 */
public class Main {

	public static void main(String[] arguments) throws Exception {
		MainParameters mainParameters = _validate(arguments);

		ShardExporter shardExporter = ShardExporterFactory.getShardExporter();

		ExportContext exportContext = mainParameters.toExportContext();

		shardExporter.export(exportContext);
	}

	private static MainParameters _validate(String[] arguments)
		throws Exception {

		MainParameters mainParameters = new MainParameters();

		if ((arguments == null) || (arguments.length == 0)) {
			throw new IllegalArgumentException("Arguments are null");
		}

		new JCommander(mainParameters, arguments);

		return mainParameters;
	}

}