/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.upgrade.v6_0_0.util.JournalArticleTable;
import com.liferay.portal.upgrade.v6_0_0.util.JournalFeedTable;
import com.liferay.portal.upgrade.v6_0_0.util.JournalTemplateTable;

/**
 * @author Zsigmond Rab
 */
public class UpgradeJournal extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (isSupportsAlterColumnType()) {
			runSQL(
				"alter_column_type JournalArticle smallImageURL STRING null");

			runSQL(
				"alter_column_type JournalFeed targetLayoutFriendlyUrl " +
					"VARCHAR(255) null");

			runSQL(
				"alter_column_type JournalTemplate smallImageURL STRING null");
		}
		else {
			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				JournalArticleTable.TABLE_NAME,
				JournalArticleTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(JournalArticleTable.TABLE_SQL_CREATE);
			upgradeTable.setIndexesSQL(
				JournalArticleTable.TABLE_SQL_ADD_INDEXES);

			upgradeTable.updateTable();

			upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				JournalFeedTable.TABLE_NAME, JournalFeedTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(JournalFeedTable.TABLE_SQL_CREATE);
			upgradeTable.setIndexesSQL(JournalFeedTable.TABLE_SQL_ADD_INDEXES);

			upgradeTable.updateTable();

			upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				JournalTemplateTable.TABLE_NAME,
				JournalTemplateTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(JournalTemplateTable.TABLE_SQL_CREATE);
			upgradeTable.setIndexesSQL(
				JournalTemplateTable.TABLE_SQL_ADD_INDEXES);

			upgradeTable.updateTable();
		}
	}

}