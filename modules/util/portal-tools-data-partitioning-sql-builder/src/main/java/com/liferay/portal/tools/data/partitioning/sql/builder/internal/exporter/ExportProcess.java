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

package com.liferay.portal.tools.data.partitioning.sql.builder.internal.exporter;

import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.DBExporter;
import com.liferay.portal.tools.data.partitioning.sql.builder.exporter.context.ExportContext;

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

	public ExportProcess(DBExporter dbExporter) {
		_dbExporter = dbExporter;
	}

	public void export(ExportContext exportContext) throws IOException {
		List<String> partitionedTableNames =
			_dbExporter.getPartitionedTableNames(exportContext.getSchemaName());
		List<String> controlTableNames = _dbExporter.getControlTableNames(
			exportContext.getSchemaName());

		List<Long> companyIds = exportContext.getCompanyIds();

		for (Long companyId : companyIds) {
			_exportCompany(
				companyId, partitionedTableNames, exportContext, true);
			_exportCompany(companyId, controlTableNames, exportContext, false);
		}
	}

	private void _exportCompany(
			long companyId, List<String> tableNames,
			ExportContext exportContext, boolean filterByCompanyId)
		throws IOException {

		String outputFileName =
			exportContext.getSchemaName() + "-" + companyId + "-";

		if (filterByCompanyId) {
			outputFileName += "partitioned";
		}
		else {
			outputFileName += "control";
		}

		outputFileName += ".sql";

		File outputFile = new File(
			exportContext.getOutputDirName(), outputFileName);

		OutputStream outputStream = null;

		if (!exportContext.isWriteFile()) {
			outputStream = new BufferedOutputStream(
				new FileOutputStream(outputFile));
		}

		for (String tableName : tableNames) {
			if (exportContext.isWriteFile()) {
				outputFileName =
					exportContext.getSchemaName() + "-" + companyId +
						"-table-" + tableName + ".sql";

				outputFile = new File(
					exportContext.getOutputDirName(), outputFileName);

				outputStream = new BufferedOutputStream(
					new FileOutputStream(outputFile));
			}

			if (filterByCompanyId) {
				_dbExporter.write(companyId, tableName, outputStream);
			}
			else {
				_dbExporter.write(tableName, outputStream);
			}

			outputStream.flush();

			if (exportContext.isWriteFile()) {
				outputStream.close();
			}
		}

		if (!exportContext.isWriteFile()) {
			outputStream.close();
		}
	}

	private final DBExporter _dbExporter;

}