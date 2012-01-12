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
import com.liferay.portal.upgrade.v6_0_0.util.PollsChoiceTable;
import com.liferay.portal.upgrade.v6_0_0.util.PollsQuestionTable;

/**
 * @author Julio Camarero Puras
 */
public class UpgradePolls extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (isSupportsAlterColumnType()) {
			runSQL("alter_column_type PollsChoice description STRING null");

			runSQL("alter_column_type PollsQuestion title STRING null");
		}
		else {

			// PollsChoice

			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				PollsChoiceTable.TABLE_NAME, PollsChoiceTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(PollsChoiceTable.TABLE_SQL_CREATE);
			upgradeTable.setIndexesSQL(PollsChoiceTable.TABLE_SQL_ADD_INDEXES);

			upgradeTable.updateTable();

			// PollsQuestion

			upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				PollsQuestionTable.TABLE_NAME,
				PollsQuestionTable.TABLE_COLUMNS);

			upgradeTable.setCreateSQL(PollsQuestionTable.TABLE_SQL_CREATE);
			upgradeTable.setIndexesSQL(
				PollsQuestionTable.TABLE_SQL_ADD_INDEXES);

			upgradeTable.updateTable();
		}
	}

}