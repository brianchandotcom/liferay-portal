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

package com.liferay.bookmarks.upgrade.v1_0_0;

import com.liferay.bookmarks.upgrade.v1_0_0.util.BookmarksEntryTable;
import com.liferay.bookmarks.upgrade.v1_0_0.util.BookmarksFolderTable;

import java.sql.SQLException;

/**
 * @author Levente Hudák
 */
public class UpgradeLastPublishDate
	extends com.liferay.portal.kernel.upgrade.UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeBookmarksEntry();
		upgradeBookmarksFolder();
	}

	private void upgradeBookmarksEntry() throws Exception {
		try {
			runSQL("alter table BookmarksEntry add lastPublishDate DATE null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				BookmarksEntryTable.TABLE_NAME,
				BookmarksEntryTable.TABLE_COLUMNS,
				BookmarksEntryTable.TABLE_SQL_CREATE,
				BookmarksEntryTable.TABLE_SQL_ADD_INDEXES);
		}
	}

	private void upgradeBookmarksFolder() throws Exception {
		try {
			runSQL("alter table BookmarksFolder add lastPublishDate DATE null");
		}
		catch (SQLException sqle) {
			upgradeTable(
				BookmarksFolderTable.TABLE_NAME,
				BookmarksFolderTable.TABLE_COLUMNS,
				BookmarksFolderTable.TABLE_SQL_CREATE,
				BookmarksFolderTable.TABLE_SQL_ADD_INDEXES);
		}
	}

}