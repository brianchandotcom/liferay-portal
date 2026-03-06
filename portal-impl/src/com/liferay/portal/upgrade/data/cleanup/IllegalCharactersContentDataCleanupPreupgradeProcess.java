/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.data.cleanup;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.data.cleanup.DataCleanupPreupgradeProcess;
import com.liferay.portal.kernel.upgrade.data.cleanup.util.DataCleanupLoggingUtil;

import java.sql.PreparedStatement;

/**
 * @author Luis Ortiz
 */
public class IllegalCharactersContentDataCleanupPreupgradeProcess
	extends DataCleanupPreupgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		DBInspector dbInspector = new DBInspector(connection);

		_cleanUp("content", dbInspector, "JournalArticle");
		_cleanUp("data_", dbInspector, "DDMContent");
		_cleanUp("xml", dbInspector, "DDMContent");
	}

	private void _cleanUp(
			String columnName, DBInspector dbInspector, String tableName)
		throws Exception {

		if (dbInspector.hasColumn(tableName, columnName)) {
			_cleanUpIllegalCharacters(columnName, dbInspector, tableName);
			_cleanUpIllegalUnicodeStringNullCharacterSequence(
				columnName, dbInspector, tableName);
		}
	}

	private void _cleanUpIllegalCharacters(
			String columnName, DBInspector dbInspector, String tableName)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		for (int charCode : _ILLEGAL_CHARACTER_CODES) {
			if ((charCode == 0) &&
				((db.getDBType() == DBType.DB2) ||
				 (db.getDBType() == DBType.POSTGRESQL))) {

				continue;
			}

			String charSentence = "CHAR(" + charCode + ")";

			if ((db.getDBType() == DBType.DB2) ||
				(db.getDBType() == DBType.ORACLE) ||
				(db.getDBType() == DBType.POSTGRESQL)) {

				charSentence = "CHR(" + charCode + ")";
			}

			String preColumnModificator = "";

			if ((db.getDBType() == DBType.MARIADB) ||
				(db.getDBType() == DBType.MYSQL)) {

				preColumnModificator = "BINARY ";
			}

			String postColumnModificator = "";

			if (db.getDBType() == DBType.SQLSERVER) {
				postColumnModificator = " COLLATE Latin1_General_100_BIN2";
			}

			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						SQLTransformer.transform(
							StringBundler.concat(
								"update ", tableName, " set ", columnName,
								" = replace(", preColumnModificator, columnName,
								postColumnModificator, ", ", charSentence,
								", '') where INSTR(", preColumnModificator,
								columnName, postColumnModificator, ", ",
								charSentence, ") > 0")))) {

				int count = preparedStatement.executeUpdate();

				DataCleanupLoggingUtil.logUpdate(
					_log, count, dbInspector.normalizeName(tableName),
					dbInspector.normalizeName(columnName), null,
					"it had invalid character " +
						String.format("0x%02X", charCode));
			}
		}
	}

	private void _cleanUpIllegalUnicodeStringNullCharacterSequence(
			String columnName, DBInspector dbInspector, String tableName)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		String likeClause = "%\\\\u0000%";

		if ((db.getDBType() == DBType.DB2) ||
			(db.getDBType() == DBType.ORACLE) ||
			(db.getDBType() == DBType.SQLSERVER)) {

			likeClause = "%\\u0000%";
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"update ", tableName, " set ", columnName, " = replace(",
					columnName, ", ?, ?) where ", columnName, " like ?"))) {

			preparedStatement.setString(1, "\\u0000");
			preparedStatement.setString(2, "");
			preparedStatement.setString(3, likeClause);

			int count = preparedStatement.executeUpdate();

			DataCleanupLoggingUtil.logUpdate(
				_log, count, dbInspector.normalizeName(tableName),
				dbInspector.normalizeName(columnName), null,
				"it had invalid '\\u0000' character sequence");
		}
	}

	private static final int[] _ILLEGAL_CHARACTER_CODES = {
		0, 1, 2, 3, 4, 5, 6, 7, 8, 11, 12, 14, 15, 16, 17, 18, 19, 20, 21, 22,
		23, 24, 25, 26, 27, 28, 29, 30, 31
	};

	private static final Log _log = LogFactoryUtil.getLog(
		IllegalCharactersContentDataCleanupPreupgradeProcess.class);

}