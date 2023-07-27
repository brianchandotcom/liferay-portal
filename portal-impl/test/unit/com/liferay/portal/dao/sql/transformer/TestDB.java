/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.sql.transformer;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.dao.db.BaseDB;
import com.liferay.portal.kernel.dao.db.DBType;

import java.io.IOException;

/**
 * @author Manuel de la Peña
 */
public class TestDB extends BaseDB {

	public TestDB(DBType dbType, int majorVersion, int minorVersion) {
		super(dbType, majorVersion, minorVersion);
	}

	@Override
	public String buildSQL(String template) throws IOException {
		return StringPool.BLANK;
	}

	@Override
	public String getPopulateSQL(String databaseName, String sqlContent) {
		return StringPool.BLANK;
	}

	@Override
	public String getRecreateSQL(String databaseName) {
		return StringPool.BLANK;
	}

	@Override
	protected int[] getSQLTypes() {
		return new int[11];
	}

	@Override
	protected String[] getTemplate() {
		return new String[] {
			"##", "TRUE", "FALSE", "'01/01/1970'", "CURRENT_TIMESTAMP", " BLOB",
			" SBLOB", " BIGDECIMAL", " BOOLEAN", " DATE", " DOUBLE", " INTEGER",
			" LONG", " STRING", " TEXT", " VARCHAR", " IDENTITY",
			"COMMIT_TRANSACTION"
		};
	}

	@Override
	protected String reword(String data) throws IOException {
		return data;
	}

}