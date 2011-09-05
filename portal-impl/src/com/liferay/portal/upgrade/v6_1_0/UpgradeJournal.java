/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.upgrade.v6_1_0.util.JournalArticleTable;
import com.liferay.portal.upgrade.v6_1_0.util.JournalStructureTable;
import com.liferay.portal.upgrade.v6_1_0.util.JournalTemplateTable;

/**
 * @author Juan Fernández
 * @author Sergio González
 */
public class UpgradeJournal extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try {
			runSQL("alter_column_type JournalArticle title STRING null");
		}
		catch (Exception e) {
			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				JournalArticleTable.TABLE_NAME,
				JournalArticleTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(JournalArticleTable.TABLE_SQL_CREATE);
			upgradeTable.setIndexesSQL(
				JournalArticleTable.TABLE_SQL_ADD_INDEXES);

			upgradeTable.updateTable();
		}

		try {
			runSQL("alter_column_type JournalStructure name STRING null");
			runSQL(
				"alter_column_type JournalStructure description STRING null");
		}
		catch (Exception e) {
			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				JournalStructureTable.TABLE_NAME,
				JournalStructureTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(JournalStructureTable.TABLE_SQL_CREATE);
			upgradeTable.setIndexesSQL(
				JournalStructureTable.TABLE_SQL_ADD_INDEXES);

			upgradeTable.updateTable();
		}

		try {
			runSQL("alter_column_type JournalTemplate name STRING null");
			runSQL("alter_column_type JournalTemplate description STRING null");
		}
		catch (Exception e) {
			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				JournalTemplateTable.TABLE_NAME,
				JournalTemplateTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(JournalTemplateTable.TABLE_SQL_CREATE);
			upgradeTable.setIndexesSQL(
				JournalTemplateTable.TABLE_SQL_ADD_INDEXES);

			upgradeTable.updateTable();
		}

		upgradeStructureXsd();
	}

	protected void upgradeStructureXsd() throws Exception {
		runSQL(
			"update JournalStructure set xsd = replace(xsd, " +
				"'image_gallery', 'document_library') where xsd like " +
					"'%image_gallery%'");
	}

}