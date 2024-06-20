/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.db.schema.definition.internal.processor;

import com.liferay.portal.db.schema.definition.internal.sql.SQLRecorder;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;

/**
 * @author Mariano Álvaro Sáiz
 */
public class DBSchemaToFilesProcessor {

	public DBSchemaToFilesProcessor(DBType dbType) {
		_dbType = dbType;
	}

	public void processTo(String path) throws Exception {
		new DBSchemaToSQLProcessor(
			_dbType, _sqlRecorder
		).process();

		FileUtil.write(
			new File(path, "indexes.sql"), _sqlRecorder.getIndexesSQL());
		FileUtil.write(
			new File(path, "tables.sql"), _sqlRecorder.getTablesSQL());
	}

	private final DBType _dbType;
	private final SQLRecorder _sqlRecorder = new SQLRecorder();

}