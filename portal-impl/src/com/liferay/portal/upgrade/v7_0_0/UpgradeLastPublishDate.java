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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.BaseUpgradeLastPublishDate;

import java.sql.Connection;

/**
 * @author Levente Hudák
 */
public class UpgradeLastPublishDate extends BaseUpgradeLastPublishDate {

	@Override
	protected void doUpgrade() throws Exception {
		try (Connection con = DataAccess.getUpgradeOptimizedConnection()) {
			upgradeAssetCategoriesAdmin(con);
			upgradeBlogs(con);
			upgradeDocumentLibrary(con);
			upgradeLayoutsAdmin(con);
			upgradeMessageBoards(con);
			upgradeMobileDeviceRules(con);
			upgradeSiteAdmin(con);
		}
	}

	protected void upgradeAssetCategoriesAdmin(Connection con)
		throws Exception {

		runSQL(con, "alter table AssetCategory add lastPublishDate DATE null");

		updateLastPublishDates(con, "147", "AssetCategory");

		runSQL(con, "alter table AssetTag add lastPublishDate DATE null");

		updateLastPublishDates(con, "147", "AssetTag");

		runSQL(
			con, "alter table AssetVocabulary add lastPublishDate DATE null");

		updateLastPublishDates(con, "147", "AssetVocabulary");
	}

	protected void upgradeBlogs(Connection con) throws Exception {
		runSQL(con, "alter table BlogsEntry add lastPublishDate DATE null");

		updateLastPublishDates(con, "33", "BlogsEntry");
	}

	protected void upgradeDocumentLibrary(Connection con) throws Exception {
		runSQL(con, "alter table DLFileEntry add lastPublishDate DATE null");

		updateLastPublishDates(con, "20", "DLFileEntry");

		runSQL(
			con, "alter table DLFileEntryType add lastPublishDate DATE null");

		updateLastPublishDates(con, "20", "DLFileEntryType");

		runSQL(con, "alter table DLFileShortcut add lastPublishDate DATE null");

		updateLastPublishDates(con, "20", "DLFileShortcut");

		runSQL(con, "alter table DLFileVersion add lastPublishDate DATE null");

		updateLastPublishDates(con, "20", "DLFileVersion");

		runSQL(con, "alter table DLFolder add lastPublishDate DATE null");

		updateLastPublishDates(con, "20", "DLFolder");

		runSQL(con, "alter table Repository add lastPublishDate DATE null");

		updateLastPublishDates(con, "20", "Repository");

		runSQL(
			con, "alter table RepositoryEntry add lastPublishDate DATE null");

		updateLastPublishDates(con, "20", "RepositoryEntry");
	}

	protected void upgradeLayoutsAdmin(Connection con) throws Exception {
		runSQL(con, "alter table Layout add lastPublishDate DATE null");

		updateLastPublishDates(con, "88", "Layout");

		runSQL(
			con, "alter table LayoutFriendlyURL add lastPublishDate DATE null");

		updateLastPublishDates(con, "88", "LayoutFriendlyURL");
	}

	protected void upgradeMessageBoards(Connection con) throws Exception {
		runSQL(con, "alter table MBBan add lastPublishDate DATE null");

		updateLastPublishDates(con, "19", "MBBan");

		runSQL(con, "alter table MBCategory add lastPublishDate DATE null");

		updateLastPublishDates(con, "19", "MBCategory");

		runSQL(con, "alter table MBDiscussion add lastPublishDate DATE null");

		updateLastPublishDates(con, "19", "MBDiscussion");

		runSQL(con, "alter table MBMessage add lastPublishDate DATE null");

		updateLastPublishDates(con, "19", "MBMessage");

		runSQL(con, "alter table MBThread add lastPublishDate DATE null");

		updateLastPublishDates(con, "19", "MBThread");

		runSQL(con, "alter table MBThreadFlag add lastPublishDate DATE null");

		updateLastPublishDates(con, "19", "MBThreadFlag");
	}

	protected void upgradeMobileDeviceRules(Connection con) throws Exception {
		runSQL(con, "alter table MDRAction add lastPublishDate DATE null");

		updateLastPublishDates(con, "178", "MDRAction");

		runSQL(con, "alter table MDRRule add lastPublishDate DATE null");

		updateLastPublishDates(con, "178", "MDRRule");

		runSQL(con, "alter table MDRRuleGroup add lastPublishDate DATE null");

		updateLastPublishDates(con, "178", "MDRRuleGroup");

		runSQL(
			con,
			"alter table MDRRuleGroupInstance add lastPublishDate DATE null");

		updateLastPublishDates(con, "178", "MDRRuleGroupInstance");
	}

	protected void upgradeSiteAdmin(Connection con) throws Exception {
		runSQL(con, "alter table Team add lastPublishDate DATE null");

		updateLastPublishDates(con, "134", "Team");
	}

}