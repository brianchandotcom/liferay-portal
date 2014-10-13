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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.events.StartupHelperUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public abstract class BaseUpgradeTableImpl extends Table {

	public BaseUpgradeTableImpl(String tableName) {
		super(tableName);
	}

	public BaseUpgradeTableImpl(String tableName, Object[][] columns) {
		super(tableName, columns);
	}

	public String[] getIndexesSQL() throws Exception {
		return _indexesSQL;
	}

	public boolean isAllowUniqueIndexes() throws Exception {
		return _allowUniqueIndexes;
	}

	public boolean isDeleteTempFile() {
		return _deleteTempFile;
	}

	public void setAllowUniqueIndexes(boolean allowUniqueIndexes)
		throws Exception {

		_allowUniqueIndexes = allowUniqueIndexes;
	}

	@Override
	public void setCreateSQL(String createSQL) throws Exception {
		if (_calledUpdateTable) {
			throw new UpgradeException(
				"setCreateSQL is called after updateTable");
		}

		super.setCreateSQL(createSQL);
	}

	public void setDeleteTempFile(boolean deleteTempFile) {
		_deleteTempFile = deleteTempFile;
	}

	public void setIndexesSQL(String[] indexesSQL) throws Exception {
		_indexesSQL = indexesSQL;
	}

	public void updateTable() throws Exception {
		_calledUpdateTable = true;

		generateTempFile();

		String tempFileName = getTempFileName();

		try {
			DB db = DBFactoryUtil.getDB();

			if (Validator.isNotNull(tempFileName)) {
				String deleteSQL = getDeleteSQL();

				db.runSQL(deleteSQL);
			}

			String createSQL = getCreateSQL();

			if (Validator.isNotNull(createSQL)) {
				db.runSQL("drop table " + getTableName());

				db.runSQL(createSQL);
			}

			populateTable();

			String[] indexesSQL = getIndexesSQL();

			boolean dropIndexes = false;

			for (String indexSQL : indexesSQL) {
				if (!isAllowUniqueIndexes()) {
					if (indexSQL.contains("create unique index")) {
						indexSQL = StringUtil.replace(
							indexSQL, "create unique index ", "create index ");

						dropIndexes = true;
					}
				}

				try {
					db.runSQL(indexSQL);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e.getMessage() + ": " + indexSQL);
					}
				}
			}

			if (dropIndexes) {
				StartupHelperUtil.setDropIndexes(true);
			}
		}
		finally {
			if (Validator.isNotNull(tempFileName) && _deleteTempFile) {
				FileUtil.delete(tempFileName);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseUpgradeTableImpl.class);

	private boolean _allowUniqueIndexes;
	private boolean _calledUpdateTable;
	private boolean _deleteTempFile;
	private String[] _indexesSQL = new String[0];

}