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

import com.liferay.portal.kernel.upgrade.BaseUpgradeLastPublishDate;
import com.liferay.portal.util.PortletKeys;

/**
 * @author Levente Hudák
 */
public class UpgradeLastPublishDate extends BaseUpgradeLastPublishDate {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeAssetCategoriesAdmin();
		upgradeBlogs();
		upgradeDocumentLibrary();
		upgradeLayoutsAdmin();
		upgradeMessageBoards();
		upgradeMobileDeviceRules();
		upgradeRatings();
		upgradeRolesAdmin();
		upgradeSiteAdmin();
		upgradeUsersAdmin();
	}

	protected void upgradeAssetCategoriesAdmin() throws Exception {
		runSQL("alter table AssetCategory add lastPublishDate DATE null");

		updateLastPublishDates("147", "AssetCategory");

		runSQL("alter table AssetTag add lastPublishDate DATE null");

		updateLastPublishDates("147", "AssetTag");

		runSQL("alter table AssetVocabulary add lastPublishDate DATE null");

		updateLastPublishDates("147", "AssetVocabulary");
	}

	protected void upgradeBlogs() throws Exception {
		runSQL("alter table BlogsEntry add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.BLOGS, "BlogsEntry");
	}

	protected void upgradeDocumentLibrary() throws Exception {
		runSQL("alter table DLFileEntry add lastPublishDate DATE null");

		updateLastPublishDates("20", "DLFileEntry");

		runSQL("alter table DLFileEntryType add lastPublishDate DATE null");

		updateLastPublishDates("20", "DLFileEntryType");

		runSQL("alter table DLFileShortcut add lastPublishDate DATE null");

		updateLastPublishDates("20", "DLFileShortcut");

		runSQL("alter table DLFileVersion add lastPublishDate DATE null");

		updateLastPublishDates("20", "DLFileVersion");

		runSQL("alter table DLFolder add lastPublishDate DATE null");

		updateLastPublishDates("20", "DLFolder");

		runSQL("alter table Repository add lastPublishDate DATE null");

		updateLastPublishDates("20", "Repository");

		runSQL("alter table RepositoryEntry add lastPublishDate DATE null");

		updateLastPublishDates("20", "RepositoryEntry");
	}

	protected void upgradeLayoutsAdmin() throws Exception {
		runSQL("alter table Layout add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.LAYOUTS_ADMIN, "Layout");

		runSQL("alter table LayoutFriendlyURL add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.LAYOUTS_ADMIN, "LayoutFriendlyURL");

		runSQL("alter table LayoutPrototype add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.LAYOUTS_ADMIN, "LayoutPrototype");

		runSQL("alter table LayoutSetPrototype add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.LAYOUTS_ADMIN, "LayoutSetPrototype");
	}

	protected void upgradeMessageBoards() throws Exception {
		runSQL("alter table MBBan add lastPublishDate DATE null");

		updateLastPublishDates("19", "MBBan");

		runSQL("alter table MBCategory add lastPublishDate DATE null");

		updateLastPublishDates("19", "MBCategory");

		runSQL("alter table MBDiscussion add lastPublishDate DATE null");

		updateLastPublishDates("19", "MBDiscussion");

		runSQL("alter table MBMessage add lastPublishDate DATE null");

		updateLastPublishDates("19", "MBMessage");

		runSQL("alter table MBThread add lastPublishDate DATE null");

		updateLastPublishDates("19", "MBThread");

		runSQL("alter table MBThreadFlag add lastPublishDate DATE null");

		updateLastPublishDates("19", "MBThreadFlag");
	}

	protected void upgradeMobileDeviceRules() throws Exception {
		runSQL("alter table MDRAction add lastPublishDate DATE null");

		updateLastPublishDates("178", "MDRAction");

		runSQL("alter table MDRRule add lastPublishDate DATE null");

		updateLastPublishDates("178", "MDRRule");

		runSQL("alter table MDRRuleGroup add lastPublishDate DATE null");

		updateLastPublishDates("178", "MDRRuleGroup");

		runSQL(
			"alter table MDRRuleGroupInstance add lastPublishDate DATE null");

		updateLastPublishDates("178", "MDRRuleGroupInstance");
	}

	protected void upgradeRatings() throws Exception {
		runSQL("alter table RatingsEntry add lastPublishDate DATE null");

		updateLastPublishDates("108", "RatingsEntry");
	}

	protected void upgradeRolesAdmin() throws Exception {
		runSQL("alter table PasswordPolicy add lastPublishDate DATE null");

		updateLastPublishDates("128", "PasswordPolicy");

		runSQL("alter table Role_ add lastPublishDate DATE null");

		updateLastPublishDates("128", "Role_");
	}

	protected void upgradeSiteAdmin() throws Exception {
		runSQL("alter table Team add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.SITE_ADMIN, "Team");
	}

	protected void upgradeUsersAdmin() throws Exception {
		runSQL("alter table Address add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.USERS_ADMIN, "Address");

		runSQL("alter table EmailAddress add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.USERS_ADMIN, "EmailAddress");

		runSQL("alter table Organization_ add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.USERS_ADMIN, "Organization_");

		runSQL("alter table Phone add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.USERS_ADMIN, "Phone");

		runSQL("alter table User_ add lastPublishDate DATE null");

		updateLastPublishDates(PortletKeys.USERS_ADMIN, "User_");
	}

}