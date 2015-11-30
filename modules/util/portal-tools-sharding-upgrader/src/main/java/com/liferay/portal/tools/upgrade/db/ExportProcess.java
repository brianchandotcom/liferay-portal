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

package com.liferay.portal.tools.upgrade.db;

import java.io.File;
import java.io.IOException;

import java.sql.SQLException;

import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * @author Manuel de la Peña
 */
public class ExportProcess {

	public ExportProcess(DBProvider dbProvider) {
		_dbProvider = dbProvider;
	}

	public void export(String outputFilePath) throws IOException, SQLException {
		List<String> tableNamesWithoutCompanyId =
			_dbProvider.getTableNamesWithoutCompanyId();

		List<Long> companyIds = _dbProvider.getCompanyIds();

		for (Long companyId : companyIds) {
			_exportCompany(
				companyId, tableNamesWithoutCompanyId, outputFilePath);
		}
	}

	private void _exportCompany(
			long companyId, List<String> tableNamesWithoutCompanyId,
			String outputFilePath)
		throws IOException, SQLException {

		StringBuilder inserts = new StringBuilder();

		for (String tableName : tableNamesWithoutCompanyId) {
			inserts.append(_dbProvider.getTableRows(tableName));
		}

		String schemaName = _dbProvider.getSchemaName();

		String outputFileName = schemaName + "-" + companyId;

		File outputFile = new File(outputFilePath, outputFileName);

		outputFile.createNewFile();

		FileUtils.writeStringToFile(outputFile, inserts.toString());
	}

	private final DBProvider _dbProvider;

}