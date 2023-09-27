/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Raymond Augé
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		_runSQLTemplate(
			"update-6.1.1-6.2.0.sql", "update-6.1.1-6.2.0-dl.sql",
			"update-6.1.1-6.2.0-expando.sql", "update-6.1.1-6.2.0-group.sql",
			"update-6.1.1-6.2.0-journal.sql", "update-6.1.1-6.2.0-wiki.sql");

		DB db = DBManagerUtil.getDB();

		if (db.getDBType() == DBType.POSTGRESQL) {
			try (LoggingTimer loggingTimer = new LoggingTimer(
					"_upgradeSchemaPostgreSQL")) {

				_upgradeSchemaPostgreSQL();
			}
		}
		else {
			try (LoggingTimer loggingTimer = new LoggingTimer(
					"_upgradeSchemaDefault")) {

				_upgradeSchemaDefault();
			}
		}

		if (hasIndex("Layout", "IX_CED31606")) {
			runSQL("drop index IX_CED31606 on Layout");
		}

		if (hasIndex("ResourcePermission", "IX_8DB864A9")) {
			runSQL("drop index IX_8DB864A9 on ResourcePermission");
		}

		upgrade(new UpgradeMVCCVersion());
	}

	private void _runSQLTemplate(String... sqlFileNames) throws Exception {
		for (String sqlFileName : sqlFileNames) {
			try (LoggingTimer loggingTimer = new LoggingTimer(sqlFileName)) {
				runSQLTemplate(sqlFileName, false);
			}
		}
	}

	private void _upgradeSchemaDefault() throws Exception {
		String[] sqls = {
			"alter table JournalArticle add folderId LONG",
			"alter table JournalArticle add treePath STRING null",
			//
			"update JournalArticle set folderId = 0, treePath = '/'",
			//
			"alter table User_ add ldapServerId LONG",
			//
			"update User_ set ldapServerId = -1"
		};

		runSQL(sqls);
	}

	private void _upgradeSchemaPostgreSQL() throws Exception {
		String[] sqls = {
			"alter table JournalArticle add folderId LONG default 0",
			//
			"alter table JournalArticle alter column folderId drop default",
			//
			"alter table JournalArticle add treePath STRING default '/'",
			//
			"alter table JournalArticle alter column treePath drop default",
			//
			"alter table User_ add ldapServerId LONG default -1",
			//
			"alter table User_ alter column ldapServerId drop default"
		};

		runSQL(sqls);
	}

}