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

package com.liferay.portal.upgrade.v6_0_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.upgrade.util.BlogsEntryUrlTitleUpgradeColumnImpl;
import com.liferay.portal.upgrade.v6_0_0.util.BlogsEntryTable;

/**
 * @author Brian Wing Shun Chan
 */
public class UpgradeBlogs extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeBlogEntryTable();
	}

	protected void upgradeBlogEntryTable() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			try {
				runSQL("drop index IX_E0D90212 on BlogsEntry");
				runSQL("drop index IX_DA53AFD4 on BlogsEntry");
				runSQL("drop index IX_B88E740E on BlogsEntry");

				runSQL("alter table BlogsEntry drop column draft");
			}
			catch (Exception e) {
			}

			UpgradeColumn entryIdColumn = new TempUpgradeColumnImpl("entryId");

			UpgradeColumn titleColumn = new TempUpgradeColumnImpl("title");

			UpgradeColumn urlTitleColumn =
				new BlogsEntryUrlTitleUpgradeColumnImpl(
					entryIdColumn, titleColumn);

			UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
				BlogsEntryTable.TABLE_NAME, BlogsEntryTable.TABLE_COLUMNS,
				entryIdColumn, titleColumn, urlTitleColumn);

			upgradeTable.updateTable();
		}
	}

}