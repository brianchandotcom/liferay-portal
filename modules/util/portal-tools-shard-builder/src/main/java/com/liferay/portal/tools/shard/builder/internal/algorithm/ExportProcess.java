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

package com.liferay.portal.tools.shard.builder.internal.algorithm;

import com.liferay.portal.tools.shard.builder.exporter.context.ExportContext;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class ExportProcess {

	public ExportProcess(DBProvider dbProvider) {
		_dbProvider = dbProvider;
	}

	public void export(ExportContext exportContext) throws IOException {
		List<String> partitionedTableNames =
			_dbProvider.getPartitionedTableNames(exportContext.getSchemaName());
		List<String> controlTableNames = _dbProvider.getControlTableNames(
			exportContext.getSchemaName());

		List<Long> companyIds = exportContext.getCompanyIds();

		for (Long companyId : companyIds) {
			_exportCompany(companyId, partitionedTableNames, exportContext);
			_exportCompany(companyId, controlTableNames, exportContext);
		}
	}

	private void _exportCompany(
			long companyId, List<String> tableNames,
			ExportContext exportContext)
		throws IOException {

		String outputFileName =
			exportContext.getSchemaName() + "-" + companyId + ".sql";

		File outputFile = new File(
			exportContext.getOutputDirName(), outputFileName);

		OutputStream outputStream = new BufferedOutputStream(
			new FileOutputStream(outputFile));

		for (String tableName : tableNames) {
			_dbProvider.write(tableName, outputStream);
		}

		outputStream.flush();

		outputStream.close();
	}

	private final DBProvider _dbProvider;

}