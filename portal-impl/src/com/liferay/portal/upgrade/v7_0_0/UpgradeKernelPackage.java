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

import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.upgrade.AutoBatchPreparedStatementUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Preston Crary
 */
public class UpgradeKernelPackage extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws SQLException {
		upgradeTable("Counter", "name", _CLASS_NAMES, WildcardMode.SURROUND);
		upgradeTable(
			"ClassName_", "value", _CLASS_NAMES, WildcardMode.SURROUND);
		upgradeTable(
			"ResourceBlock", "name", _CLASS_NAMES, WildcardMode.SURROUND);
		upgradeTable(
			"ResourcePermission", "name", _CLASS_NAMES, WildcardMode.SURROUND);

		upgradeTable(
			"ResourceBlock", "name", _RESOURCE_NAMES, WildcardMode.LEADING);
		upgradeTable(
			"ResourcePermission", "name", _RESOURCE_NAMES,
			WildcardMode.LEADING);
	}

	protected void upgradeTable(
			String columnName, String selectSQL, String updateSQL,
			String[] name)
		throws SQLException {

		try (PreparedStatement ps1 = connection.prepareStatement(selectSQL);
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(updateSQL))) {

			while (rs.next()) {
				String oldValue = rs.getString(columnName);

				String newValue = StringUtil.replace(
					oldValue, name[0], name[1]);

				ps2.setString(1, newValue);
				ps2.setString(2, oldValue);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void upgradeTable(
			String tableName, String columnName, String[][] names,
			WildcardMode wildcardMode)
		throws SQLException {

		StringBundler updateSB = new StringBundler(7);

		updateSB.append("update ");
		updateSB.append(tableName);
		updateSB.append(" set ");
		updateSB.append(columnName);
		updateSB.append(" = ? where ");
		updateSB.append(columnName);
		updateSB.append(" = ?");

		String updateSQL = updateSB.toString();

		StringBundler selectSB = new StringBundler(11);

		for (String[] name : names) {
			selectSB.append("select ");
			selectSB.append(columnName);
			selectSB.append(" from ");
			selectSB.append(tableName);
			selectSB.append(" where ");
			selectSB.append(columnName);
			selectSB.append(" like '");

			if (wildcardMode.equals(WildcardMode.LEADING) ||
				wildcardMode.equals(WildcardMode.SURROUND)) {

				selectSB.append(StringPool.PERCENT);
			}

			selectSB.append(name[0]);

			if (wildcardMode.equals(WildcardMode.SURROUND) ||
				wildcardMode.equals(WildcardMode.TRAILING)) {

				selectSB.append(StringPool.PERCENT);
			}

			selectSB.append(StringPool.APOSTROPHE);

			upgradeTable(columnName, selectSB.toString(), updateSQL, name);

			selectSB.setIndex(0);
		}
	}

	private static final String[][] _CLASS_NAMES = new String[][] {
		{
			"com.liferay.portal.kernel.mail.Account",
			"com.liferay.mail.kernel.model.Account"
		},
		{
			"com.liferay.portal.model.BackgroundTask",
			"com.liferay.portal.kernel.backgroundtask.model.BackgroundTask"
		},
		{
			"com.liferay.portal.lock.model.Lock",
			"com.liferay.portal.kernel.lock.model.Lock"
		},
		{
			"com.liferay.portal.model.Account",
			"com.liferay.portal.kernel.model.Account"
		},
		{
			"com.liferay.portal.model.Address",
			"com.liferay.portal.kernel.model.Address"
		},
		{
			"com.liferay.portal.model.BrowserTracker",
			"com.liferay.portal.kernel.model.BrowserTracker"
		},
		{
			"com.liferay.portal.model.ClassName",
			"com.liferay.portal.kernel.model.ClassName"
		},
		{
			"com.liferay.portal.model.ClusterGroup",
			"com.liferay.portal.kernel.model.ClusterGroup"
		},
		{
			"com.liferay.portal.model.Company",
			"com.liferay.portal.kernel.model.Company"
		},
		{
			"com.liferay.portal.model.Contact",
			"com.liferay.portal.kernel.model.Contact"
		},
		{
			"com.liferay.portal.model.Country",
			"com.liferay.portal.kernel.model.Country"
		},
		{
			"com.liferay.portal.model.Dummy",
			"com.liferay.portal.kernel.model.Dummy"
		},
		{
			"com.liferay.portal.model.EmailAddress",
			"com.liferay.portal.kernel.model.EmailAddress"
		},
		{
			"com.liferay.portal.model.Group",
			"com.liferay.portal.kernel.model.Group"
		},
		{
			"com.liferay.portal.model.Image",
			"com.liferay.portal.kernel.model.Image"
		},
		{
			"com.liferay.portal.model.Layout",
			"com.liferay.portal.kernel.model.Layout"
		},
		{
			"com.liferay.portal.model.LayoutBranch",
			"com.liferay.portal.kernel.model.LayoutBranch"
		},
		{
			"com.liferay.portal.model.LayoutFriendlyURL",
			"com.liferay.portal.kernel.model.LayoutFriendlyURL"
		},
		{
			"com.liferay.portal.model.LayoutPrototype",
			"com.liferay.portal.kernel.model.LayoutPrototype"
		},
		{
			"com.liferay.portal.model.LayoutRevision",
			"com.liferay.portal.kernel.model.LayoutRevision"
		},
		{
			"com.liferay.portal.model.LayoutSet",
			"com.liferay.portal.kernel.model.LayoutSet"
		},
		{
			"com.liferay.portal.model.LayoutSetBranch",
			"com.liferay.portal.kernel.model.LayoutSetBranch"
		},
		{
			"com.liferay.portal.model.LayoutSetPrototype",
			"com.liferay.portal.kernel.model.LayoutSetPrototype"
		},
		{
			"com.liferay.portal.model.ListType",
			"com.liferay.portal.kernel.model.ListType"
		},
		{
			"com.liferay.portal.model.MembershipRequest",
			"com.liferay.portal.kernel.model.MembershipRequest"
		},
		{
			"com.liferay.portal.model.Organization",
			"com.liferay.portal.kernel.model.Organization"
		},
		{
			"com.liferay.portal.model.OrgGroupRole",
			"com.liferay.portal.kernel.model.OrgGroupRole"
		},
		{
			"com.liferay.portal.model.OrgLabor",
			"com.liferay.portal.kernel.model.OrgLabor"
		},
		{
			"com.liferay.portal.model.PasswordPolicy",
			"com.liferay.portal.kernel.model.PasswordPolicy"
		},
		{
			"com.liferay.portal.model.PasswordPolicyRel",
			"com.liferay.portal.kernel.model.PasswordPolicyRel"
		},
		{
			"com.liferay.portal.model.PasswordTracker",
			"com.liferay.portal.kernel.model.PasswordTracker"
		},
		{
			"com.liferay.portal.model.Phone",
			"com.liferay.portal.kernel.model.Phone"
		},
		{
			"com.liferay.portal.model.PluginSetting",
			"com.liferay.portal.kernel.model.PluginSetting"
		},
		{
			"com.liferay.portal.model.PortalPreferences",
			"com.liferay.portal.kernel.model.PortalPreferences"
		},
		{
			"com.liferay.portal.model.Portlet",
			"com.liferay.portal.kernel.model.Portlet"
		},
		{
			"com.liferay.portal.model.PortletItem",
			"com.liferay.portal.kernel.model.PortletItem"
		},
		{
			"com.liferay.portal.model.PortletPreferences",
			"com.liferay.portal.kernel.model.PortletPreferences"
		},
		{
			"com.liferay.portal.model.Region",
			"com.liferay.portal.kernel.model.Region"
		},
		{
			"com.liferay.portal.model.Release",
			"com.liferay.portal.kernel.model.Release"
		},
		{
			"com.liferay.portal.model.Repository",
			"com.liferay.portal.kernel.model.Repository"
		},
		{
			"com.liferay.portal.model.RepositoryEntry",
			"com.liferay.portal.kernel.model.RepositoryEntry"
		},
		{
			"com.liferay.portal.model.ResourceAction",
			"com.liferay.portal.kernel.model.ResourceAction"
		},
		{
			"com.liferay.portal.model.ResourceBlock",
			"com.liferay.portal.kernel.model.ResourceBlock"
		},
		{
			"com.liferay.portal.model.ResourceBlockPermission",
			"com.liferay.portal.kernel.model.ResourceBlockPermission"
		},
		{
			"com.liferay.portal.model.ResourcePermission",
			"com.liferay.portal.kernel.model.ResourcePermission"
		},
		{
			"com.liferay.portal.model.ResourceTypePermission",
			"com.liferay.portal.kernel.model.ResourceTypePermission"
		},
		{
			"com.liferay.portal.model.Role",
			"com.liferay.portal.kernel.model.Role"
		},
		{
			"com.liferay.portal.model.ServiceComponent",
			"com.liferay.portal.kernel.model.ServiceComponent"
		},
		{
			"com.liferay.portal.model.Subscription",
			"com.liferay.portal.kernel.model.Subscription"
		},
		{
			"com.liferay.portal.model.SystemEvent",
			"com.liferay.portal.kernel.model.SystemEvent"
		},
		{
			"com.liferay.portal.model.Team",
			"com.liferay.portal.kernel.model.Team"
		},
		{
			"com.liferay.portal.model.Ticket",
			"com.liferay.portal.kernel.model.Ticket"
		},
		{
			"com.liferay.portal.model.User",
			"com.liferay.portal.kernel.model.User"
		},
		{
			"com.liferay.portal.model.UserGroup",
			"com.liferay.portal.kernel.model.UserGroup"
		},
		{
			"com.liferay.portal.model.UserGroupGroupRole",
			"com.liferay.portal.kernel.model.UserGroupGroupRole"
		},
		{
			"com.liferay.portal.model.UserGroupRole",
			"com.liferay.portal.kernel.model.UserGroupRole"
		},
		{
			"com.liferay.portal.model.UserIdMapper",
			"com.liferay.portal.kernel.model.UserIdMapper"
		},
		{
			"com.liferay.portal.model.UserNotificationDelivery",
			"com.liferay.portal.kernel.model.UserNotificationDelivery"
		},
		{
			"com.liferay.portal.model.UserNotificationEvent",
			"com.liferay.portal.kernel.model.UserNotificationEvent"
		},
		{
			"com.liferay.portal.model.UserTracker",
			"com.liferay.portal.kernel.model.UserTracker"
		},
		{
			"com.liferay.portal.model.UserTrackerPath",
			"com.liferay.portal.kernel.model.UserTrackerPath"
		},
		{
			"com.liferay.portal.model.VirtualHost",
			"com.liferay.portal.kernel.model.VirtualHost"
		},
		{
			"com.liferay.portal.model.WebDAVProps",
			"com.liferay.portal.kernel.model.WebDAVProps"
		},
		{
			"com.liferay.portal.model.Website",
			"com.liferay.portal.kernel.model.Website"
		},
		{
			"com.liferay.portal.model.WorkflowDefinitionLink",
			"com.liferay.portal.kernel.model.WorkflowDefinitionLink"
		},
		{
			"com.liferay.portal.model.WorkflowInstanceLink",
			"com.liferay.portal.kernel.model.WorkflowInstanceLink"
		},
		{
			"com.liferay.portlet.announcements.model.AnnouncementsDelivery",
			"com.liferay.announcements.kernel.model.AnnouncementsDelivery"
		},
		{
			"com.liferay.portlet.announcements.model.AnnouncementsEntry",
			"com.liferay.announcements.kernel.model.AnnouncementsEntry"
		},
		{
			"com.liferay.portlet.announcements.model.AnnouncementsFlag",
			"com.liferay.announcements.kernel.model.AnnouncementsFlag"
		},
		{
			"com.liferay.portlet.asset.model.AssetCategory",
			"com.liferay.asset.kernel.model.AssetCategory"
		},
		{
			"com.liferay.portlet.asset.model.AssetCategoryProperty",
			"com.liferay.asset.kernel.model.AssetCategoryProperty"
		},
		{
			"com.liferay.portlet.asset.model.AssetEntry",
			"com.liferay.asset.kernel.model.AssetEntry"
		},
		{
			"com.liferay.portlet.asset.model.AssetLink",
			"com.liferay.asset.kernel.model.AssetLink"
		},
		{
			"com.liferay.portlet.asset.model.AssetTag",
			"com.liferay.asset.kernel.model.AssetTag"
		},
		{
			"com.liferay.portlet.asset.model.AssetTagStats",
			"com.liferay.asset.kernel.model.AssetTagStats"
		},
		{
			"com.liferay.portlet.asset.model.AssetVocabulary",
			"com.liferay.asset.kernel.model.AssetVocabulary"
		},
		{
			"com.liferay.portlet.blogs.model.BlogsEntry",
			"com.liferay.blogs.kernel.model.BlogsEntry"
		},
		{
			"com.liferay.portlet.blogs.model.BlogsStatsUser",
			"com.liferay.blogs.kernel.model.BlogsStatsUser"
		},
		{
			"com.liferay.portlet.bookmarks.model.BookmarksEntry",
			"com.liferay.bookmarks.model.BookmarksEntry"
		},
		{
			"com.liferay.portlet.bookmarks.model.BookmarksFolder",
			"com.liferay.bookmarks.model.BookmarksFolder"
		},
		{
			"com.liferay.portlet.counter.model.Counter",
			"com.liferay.counter.kernel.model.Counter"
		},
		{
			"com.liferay.portlet.documentlibrary.model.DLContent",
			"com.liferay.document.library.kernel.model.DLContent"
		},
		{
			"com.liferay.portlet.documentlibrary.model.DLFileEntry",
			"com.liferay.document.library.kernel.model.DLFileEntry"
		},
		{
			"com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata",
			"com.liferay.document.library.kernel.model.DLFileEntryMetadata"
		},
		{
			"com.liferay.portlet.documentlibrary.model.DLFileEntryType",
			"com.liferay.document.library.kernel.model.DLFileEntryType"
		},
		{
			"com.liferay.portlet.documentlibrary.model.DLFileRank",
			"com.liferay.document.library.kernel.model.DLFileRank"
		},
		{
			"com.liferay.portlet.documentlibrary.model.DLFileShortcut",
			"com.liferay.document.library.kernel.model.DLFileShortcut"
		},
		{
			"com.liferay.portlet.documentlibrary.model.DLFileVersion",
			"com.liferay.document.library.kernel.model.DLFileVersion"
		},
		{
			"com.liferay.portlet.documentlibrary.model.DLFolder",
			"com.liferay.document.library.kernel.model.DLFolder"
		},
		{
			"com.liferay.portlet.documentlibrary.model.DLSyncEvent",
			"com.liferay.document.library.kernel.model.DLSyncEvent"
		},
		{
			"com.liferay.portlet.dynamicdatalists.model.DDLRecord",
			"com.liferay.dynamic.data.lists.model.DDLRecord"
		},
		{
			"com.liferay.portlet.dynamicdatalists.model.DDLRecordSet",
			"com.liferay.dynamic.data.lists.model.DDLRecordSet"
		},
		{
			"com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion",
			"com.liferay.dynamic.data.lists.model.DDLRecordVersion"
		},
		{
			"com.liferay.portlet.dynamicdatamapping.model.DDMStructure",
			"com.liferay.dynamic.data.mapping.kernel.model.DDMStructure"
		},
		{
			"com.liferay.portlet.dynamicdatamapping.model.DDMTemplate",
			"com.liferay.dynamic.data.mapping.kernel.model.DDMTemplate"
		},
		{
			"com.liferay.portlet.dynamicdatamapping.model.DDMContent",
			"com.liferay.dynamic.data.mapping.model.DDMContent"
		},
		{
			"com.liferay.portlet.dynamicdatamapping.model.DDMStorageLink",
			"com.liferay.dynamic.data.mapping.model.DDMStorageLink"
		},
		{
			"com.liferay.portlet.dynamicdatamapping.model.DDMStructure",
			"com.liferay.dynamic.data.mapping.model.DDMStructure"
		},
		{
			"com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink",
			"com.liferay.dynamic.data.mapping.model.DDMStructureLink"
		},
		{
			"com.liferay.portlet.dynamicdatamapping.model.DDMTemplate",
			"com.liferay.dynamic.data.mapping.model.DDMTemplate"
		},
		{
			"com.liferay.portlet.expando.model.ExpandoColumn",
			"com.liferay.expando.kernel.model.ExpandoColumn"
		},
		{
			"com.liferay.portlet.expando.model.ExpandoRow",
			"com.liferay.expando.kernel.model.ExpandoRow"
		},
		{
			"com.liferay.portlet.expando.model.ExpandoTable",
			"com.liferay.expando.kernel.model.ExpandoTable"
		},
		{
			"com.liferay.portlet.expando.model.ExpandoValue",
			"com.liferay.expando.kernel.model.ExpandoValue"
		},
		{
			"com.liferay.portlet.journal.model.JournalArticle",
			"com.liferay.journal.model.JournalArticle"
		},
		{
			"com.liferay.portlet.journal.model.JournalArticleImage",
			"com.liferay.journal.model.JournalArticleImage"
		},
		{
			"com.liferay.portlet.journal.model.JournalArticleResource",
			"com.liferay.journal.model.JournalArticleResource"
		},
		{
			"com.liferay.portlet.journal.model.JournalContentSearch",
			"com.liferay.journal.model.JournalContentSearch"
		},
		{
			"com.liferay.portlet.journal.model.JournalFeed",
			"com.liferay.journal.model.JournalFeed"
		},
		{
			"com.liferay.portlet.journal.model.JournalFolder",
			"com.liferay.journal.model.JournalFolder"
		},
		{
			"com.liferay.portlet.messageboards.model.MBBan",
			"com.liferay.message.boards.kernel.model.MBBan"
		},
		{
			"com.liferay.portlet.messageboards.model.MBCategory",
			"com.liferay.message.boards.kernel.model.MBCategory"
		},
		{
			"com.liferay.portlet.messageboards.model.MBDiscussion",
			"com.liferay.message.boards.kernel.model.MBDiscussion"
		},
		{
			"com.liferay.portlet.messageboards.model.MBMailingList",
			"com.liferay.message.boards.kernel.model.MBMailingList"
		},
		{
			"com.liferay.portlet.messageboards.model.MBMessage",
			"com.liferay.message.boards.kernel.model.MBMessage"
		},
		{
			"com.liferay.portlet.messageboards.model.MBStatsUser",
			"com.liferay.message.boards.kernel.model.MBStatsUser"
		},
		{
			"com.liferay.portlet.messageboards.model.MBThread",
			"com.liferay.message.boards.kernel.model.MBThread"
		},
		{
			"com.liferay.portlet.messageboards.model.MBThreadFlag",
			"com.liferay.message.boards.kernel.model.MBThreadFlag"
		},
		{
			"com.liferay.portlet.mobiledevicerules.model.MDRAction",
			"com.liferay.mobile.device.rules.model.MDRAction"
		},
		{
			"com.liferay.portlet.mobiledevicerules.model.MDRRule",
			"com.liferay.mobile.device.rules.model.MDRRule"
		},
		{
			"com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup",
			"com.liferay.mobile.device.rules.model.MDRRuleGroup"
		},
		{
			"com.liferay.portlet.mobiledevicerules.model.MDRRuleGroupInstance",
			"com.liferay.mobile.device.rules.model.MDRRuleGroupInstance"
		},
		{
			"com.liferay.portlet.polls.model.PollsChoice",
			"com.liferay.polls.model.PollsChoice"
		},
		{
			"com.liferay.portlet.polls.model.PollsQuestion",
			"com.liferay.polls.model.PollsQuestion"
		},
		{
			"com.liferay.portlet.polls.model.PollsVote",
			"com.liferay.polls.model.PollsVote"
		},
		{
			"com.liferay.portlet.ratings.model.RatingsEntry",
			"com.liferay.ratings.kernel.model.RatingsEntry"
		},
		{
			"com.liferay.portlet.ratings.model.RatingsStats",
			"com.liferay.ratings.kernel.model.RatingsStats"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingCart",
			"com.liferay.shopping.model.ShoppingCart"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingCategory",
			"com.liferay.shopping.model.ShoppingCategory"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingCoupon",
			"com.liferay.shopping.model.ShoppingCoupon"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingItem",
			"com.liferay.shopping.model.ShoppingItem"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingItemField",
			"com.liferay.shopping.model.ShoppingItemField"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingItemPrice",
			"com.liferay.shopping.model.ShoppingItemPrice"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingOrder",
			"com.liferay.shopping.model.ShoppingOrder"
		},
		{
			"com.liferay.portlet.shopping.model.ShoppingOrderItem",
			"com.liferay.shopping.model.ShoppingOrderItem"
		},
		{
			"com.liferay.portlet.social.model.SocialActivity",
			"com.liferay.social.kernel.model.SocialActivity"
		},
		{
			"com.liferay.portlet.social.model.SocialActivityCounter",
			"com.liferay.social.kernel.model.SocialActivityCounter"
		},
		{
			"com.liferay.portlet.social.model.SocialActivityLimit",
			"com.liferay.social.kernel.model.SocialActivityLimit"
		},
		{
			"com.liferay.portlet.social.model.SocialActivitySet",
			"com.liferay.social.kernel.model.SocialActivitySet"
		},
		{
			"com.liferay.portlet.social.model.SocialActivitySetting",
			"com.liferay.social.kernel.model.SocialActivitySetting"
		},
		{
			"com.liferay.portlet.social.model.SocialRelation",
			"com.liferay.social.kernel.model.SocialRelation"
		},
		{
			"com.liferay.portlet.social.model.SocialRequest",
			"com.liferay.social.kernel.model.SocialRequest"
		},
		{
			"com.liferay.portlet.trash.model.TrashEntry",
			"com.liferay.trash.kernel.model.TrashEntry"
		},
		{
			"com.liferay.portlet.trash.model.TrashVersion",
			"com.liferay.trash.kernel.model.TrashVersion"
		},
		{
			"com.liferay.portlet.wiki.model.WikiNode",
			"com.liferay.wiki.model.WikiNode"
		},
		{
			"com.liferay.portlet.wiki.model.WikiPage",
			"com.liferay.wiki.model.WikiPage"
		},
		{
			"com.liferay.portlet.wiki.model.WikiPageResource",
			"com.liferay.wiki.model.WikiPageResource"
		},
		{
			"com.liferay.socialnetworking.model.MeetupsEntry",
			"com.liferay.social.networking.model.MeetupsEntry"
		},
		{
			"com.liferay.socialnetworking.model.MeetupsRegistration",
			"com.liferay.social.networking.model.MeetupsRegistration"
		},
		{
			"com.liferay.socialnetworking.model.WallEntry",
			"com.liferay.social.networking.model.WallEntry"
		}
	};

	private static final String[][] _RESOURCE_NAMES = new String[][] {
		{
			"com.liferay.portlet.asset", "com.liferay.asset"
		},
		{
			"com.liferay.portlet.blogs", "com.liferay.blogs"
		},
		{
			"com.liferay.portlet.bookmarks", "com.liferay.bookmarks"
		},
		{
			"com.liferay.portlet.calendar", "com.liferay.calendar"
		},
		{
			"com.liferay.portlet.documentlibrary",
			"com.liferay.document.library"
		},
		{
			"com.liferay.portlet.dynamicdatalists",
			"com.liferay.dynamic.data.lists"
		},
		{
			"com.liferay.portlet.dynamicdatamapping",
			"com.liferay.dynamic.data.mapping"
		},
		{
			"com.liferay.portlet.journal", "com.liferay.journal"
		},
		{
			"com.liferay.portlet.messageboards", "com.liferay.message.boards"
		},
		{
			"com.liferay.portlet.polls", "com.liferay.polls"
		},
		{
			"com.liferay.portlet.shopping", "com.liferay.shopping"
		},
		{
			"com.liferay.portlet.wiki", "com.liferay.wiki"
		}
	};

}