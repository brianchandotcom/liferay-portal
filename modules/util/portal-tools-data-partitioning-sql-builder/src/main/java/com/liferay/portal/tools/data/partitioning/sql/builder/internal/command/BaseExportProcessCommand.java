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

package com.liferay.portal.tools.data.partitioning.sql.builder.internal.command;

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
public abstract class BaseExportProcessCommand implements ExportProcessCommand {

	public BaseExportProcessCommand(
		long companyId, DBExporter dbExporter, List<String> tableNames,
		ExportContext exportContext) {

		this.companyId = companyId;
		this.dbExporter = dbExporter;
		this.tableNames = tableNames;
		this.exportContext = exportContext;
	}

	@Override
	public void export() throws IOException {
		String outputFileName = getOutputFileName();

		File outputFile = new File(
			exportContext.getOutputDirName(), outputFileName);

		OutputStream outputStream = null;

		if (!exportContext.isWriteFile()) {
			outputStream = new BufferedOutputStream(
				new FileOutputStream(outputFile));
		}

		for (String tableName : tableNames) {
			if (exportContext.isWriteFile()) {
				outputFileName = getOutputFileName(tableName);

				outputFile = new File(
					exportContext.getOutputDirName(), outputFileName);

				outputStream = new BufferedOutputStream(
					new FileOutputStream(outputFile));
			}

			write(tableName, outputStream);

			outputStream.flush();

			if (exportContext.isWriteFile()) {
				outputStream.close();
			}
		}

		if (!exportContext.isWriteFile()) {
			outputStream.close();
		}
	}

	protected abstract String getOutputFileName();

	protected abstract String getOutputFileName(String tableName);

	protected abstract void write(String tableName, OutputStream outputStream);

	protected final long companyId;
	protected final DBExporter dbExporter;
	protected final ExportContext exportContext;
	protected final List<String> tableNames;

}