/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.upgrade.BaseLastPublishDateUpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

/**
 * @author Levente Hudák
 */
public class UpgradeLastPublishDate extends BaseLastPublishDateUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		upgradeAssetCategoriesAdmin();
		upgradeBlogs();
		upgradeDocumentLibrary();
		upgradeLayoutsAdmin();
		upgradeMessageBoards();
		upgradeMobileDeviceRules();
		upgradeSiteAdmin();
		upgradeWebSite();
	}

	protected void upgradeAssetCategoriesAdmin() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			addLastPublishDateColumn("AssetCategory");

			updateLastPublishDates("147", "AssetCategory");

			addLastPublishDateColumn("AssetTag");

			updateLastPublishDates("147", "AssetTag");

			addLastPublishDateColumn("AssetVocabulary");

			updateLastPublishDates("147", "AssetVocabulary");
		}
	}

	protected void upgradeBlogs() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			addLastPublishDateColumn("BlogsEntry");

			updateLastPublishDates("33", "BlogsEntry");
		}
	}

	protected void upgradeDocumentLibrary() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			addLastPublishDateColumn("DLFileEntry");

			updateLastPublishDates("20", "DLFileEntry");

			addLastPublishDateColumn("DLFileEntryType");

			updateLastPublishDates("20", "DLFileEntryType");

			addLastPublishDateColumn("DLFileShortcut");

			updateLastPublishDates("20", "DLFileShortcut");

			addLastPublishDateColumn("DLFileVersion");

			updateLastPublishDates("20", "DLFileVersion");

			addLastPublishDateColumn("DLFolder");

			updateLastPublishDates("20", "DLFolder");

			addLastPublishDateColumn("Repository");

			updateLastPublishDates("20", "Repository");

			addLastPublishDateColumn("RepositoryEntry");

			updateLastPublishDates("20", "RepositoryEntry");
		}
	}

	protected void upgradeLayoutsAdmin() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			addLastPublishDateColumn("Layout");

			updateLastPublishDates("88", "Layout");

			addLastPublishDateColumn("LayoutFriendlyURL");

			updateLastPublishDates("88", "LayoutFriendlyURL");
		}
	}

	protected void upgradeMessageBoards() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			addLastPublishDateColumn("MBBan");

			updateLastPublishDates("19", "MBBan");

			addLastPublishDateColumn("MBCategory");

			updateLastPublishDates("19", "MBCategory");

			addLastPublishDateColumn("MBDiscussion");

			updateLastPublishDates("19", "MBDiscussion");

			addLastPublishDateColumn("MBMessage");

			updateLastPublishDates("19", "MBMessage");

			addLastPublishDateColumn("MBThread");

			updateLastPublishDates("19", "MBThread");

			addLastPublishDateColumn("MBThreadFlag");

			updateLastPublishDates("19", "MBThreadFlag");
		}
	}

	protected void upgradeMobileDeviceRules() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			addLastPublishDateColumn("MDRAction");

			updateLastPublishDates("178", "MDRAction");

			addLastPublishDateColumn("MDRRule");

			updateLastPublishDates("178", "MDRRule");

			addLastPublishDateColumn("MDRRuleGroup");

			updateLastPublishDates("178", "MDRRuleGroup");

			addLastPublishDateColumn("MDRRuleGroupInstance");

			updateLastPublishDates("178", "MDRRuleGroupInstance");
		}
	}

	protected void upgradeSiteAdmin() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			addLastPublishDateColumn("Team");

			updateLastPublishDates("134", "Team");
		}
	}

	protected void upgradeWebSite() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			addLastPublishDateColumn("Website");
		}
	}

}