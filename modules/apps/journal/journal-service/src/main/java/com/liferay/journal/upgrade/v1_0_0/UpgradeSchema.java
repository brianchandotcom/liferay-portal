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

package com.liferay.journal.upgrade.v1_0_0;

import com.liferay.journal.upgrade.v1_0_0.util.JournalArticleTable;
import com.liferay.journal.upgrade.v1_0_0.util.JournalFeedTable;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.UpgradeMVCCVersion;

import java.sql.SQLException;

/**
 * @author Eduardo Garcia
 */
public class UpgradeSchema extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String template = StringUtil.read(
			UpgradeSchema.class.getResourceAsStream("dependencies/update.sql"));

		runSQLTemplateString(template, false, false);

		upgrade(UpgradeMVCCVersion.class);

		try {
			runSQL(
				"alter_column_name JournalArticle structureId " +
					"DDMStructureKey VARCHAR(75) null");
			runSQL(
				"alter_column_name JournalArticle templateId DDMTemplateKey " +
					"VARCHAR(75) null");
			runSQL("alter_column_type JournalArticle description TEXT null");

			runSQL(
				"alter_column_name JournalFeed structureId DDMStructureKey " +
					"TEXT null");
			runSQL(
				"alter_column_name JournalFeed templateId DDMTemplateKey " +
					"TEXT null");
			runSQL(
				"alter_column_name JournalFeed rendererTemplateId " +
					"DDMRendererTemplateKey TEXT null");
			runSQL(
				"alter_column_type JournalFeed targetPortletId VARCHAR(200) " +
					"null");
		}
		catch (SQLException sqle) {

			// JournalArticle

			upgradeTable(
				JournalArticleTable.TABLE_NAME,
				JournalArticleTable.TABLE_COLUMNS,
				JournalArticleTable.TABLE_SQL_CREATE,
				JournalArticleTable.TABLE_SQL_ADD_INDEXES);

			// JournalFeed

			upgradeTable(
				JournalFeedTable.TABLE_NAME, JournalFeedTable.TABLE_COLUMNS,
				JournalFeedTable.TABLE_SQL_CREATE,
				JournalFeedTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}