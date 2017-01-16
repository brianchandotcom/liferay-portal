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

import java.io.OutputStream;

import java.util.List;

/**
 * @author Manuel de la Peña
 */
public class InsertControlExportProcessCommand
	extends BaseExportProcessCommand {

	public InsertControlExportProcessCommand(
		long companyId, DBExporter dbExporter, List<String> tableNames,
		ExportContext exportContext) {

		super(companyId, dbExporter, tableNames, exportContext);
	}

	@Override
	protected String getOutputFileName() {
		return exportContext.getSchemaName() + "-" + companyId + "-control" +
			dbExporter.getOutputFileExtension();
	}

	@Override
	protected String getOutputFileName(String tableName) {
		return exportContext.getSchemaName() + "-" + companyId + "-table-" +
			tableName + dbExporter.getOutputFileExtension();
	}

	@Override
	protected void write(String tableName, OutputStream outputStream) {
		dbExporter.write(tableName, outputStream);
	}

}