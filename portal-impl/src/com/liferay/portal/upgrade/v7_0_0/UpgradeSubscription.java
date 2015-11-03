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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.model.Layout;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eduardo Garcia
 * @author Roberto Díaz
 * @author Iván Zaera
 */
public class UpgradeSubscription extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (Connection con = DataAccess.getUpgradeOptimizedConnection()) {
			updateSubscriptionClassNames(
				con, Folder.class.getName(), DLFolder.class.getName());
			updateSubscriptionClassNames(
				con, "com.liferay.portlet.journal.model.JournalArticle",
				"com.liferay.portlet.journal.model.JournalFolder");

			updateSubscriptionGroupIds(con);
		}
	}

	protected long getGroupId(Connection con, long classNameId, long classPK)
		throws Exception {

		String className = PortalUtil.getClassName(classNameId);

		String[] groupIdSQLParts = StringUtil.split(
			_getGroupIdSQLPartsMap.get(className));

		if (ArrayUtil.isEmpty(groupIdSQLParts)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to determine the group ID for the class name " +
						className);
			}

			return 0;
		}

		StringBundler sb = new StringBundler(8);

		sb.append("select ");
		sb.append(groupIdSQLParts[1]);
		sb.append(" from ");
		sb.append(groupIdSQLParts[0]);
		sb.append(" where ");
		sb.append(groupIdSQLParts[2]);
		sb.append(" = ");
		sb.append(classPK);

		try (PreparedStatement ps = con.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return rs.getLong("groupId");
			}

			return 0;
		}
	}

	protected boolean hasGroup(Connection con, long groupId) throws Exception {
		String sql = "select count(*) from Group_ where groupId = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setLong(1, groupId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt(1);

					if (count > 0) {
						return true;
					}
				}
			}

			return false;
		}
	}

	protected void updateSubscriptionClassNames(
			Connection con, String oldClassName, String newClassName)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("update Subscription set classNameId = ");
		sb.append(PortalUtil.getClassNameId(newClassName));
		sb.append(" where classNameId = ");
		sb.append(PortalUtil.getClassNameId(oldClassName));

		runSQL(con, sb.toString());
	}

	protected void updateSubscriptionGroupId(
			Connection con, long subscriptionId, long classNameId, long classPK)
		throws Exception {

		long groupId = getGroupId(con, classNameId, classPK);

		if ((groupId == 0) && hasGroup(con, classPK)) {
			groupId = classPK;
		}

		if (groupId != 0) {
			runSQL(
				con,
				"update Subscription set groupId = " + groupId + " where " +
					"subscriptionId = " + subscriptionId);
		}
	}

	protected void updateSubscriptionGroupIds(Connection con) throws Exception {
		String sql =
			"select subscriptionId, classNameId, classPK from Subscription";

		try (PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long subscriptionId = rs.getLong("subscriptionId");
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");

				updateSubscriptionGroupId(
					con, subscriptionId, classNameId, classPK);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeSubscription.class);

	private static final Map<String, String> _getGroupIdSQLPartsMap =
		new HashMap<>();

	static {
		_getGroupIdSQLPartsMap.put(
			BlogsEntry.class.getName(), "BlogsEntry,groupId,entryId");
		_getGroupIdSQLPartsMap.put(
			DLFileEntry.class.getName(), "DLFileEntry,groupId,fileEntryId");
		_getGroupIdSQLPartsMap.put(
			DLFileEntryType.class.getName(),
			"DLFileEntryType,groupId,fileEntryTypeId");
		_getGroupIdSQLPartsMap.put(
			DLFolder.class.getName(), "DLFolder,groupId,folderId");
		_getGroupIdSQLPartsMap.put(
			Layout.class.getName(), "Layout,groupId,plid");
		_getGroupIdSQLPartsMap.put(
			MBCategory.class.getName(), "MBCategory,groupId,categoryId");
		_getGroupIdSQLPartsMap.put(
			MBThread.class.getName(), "MBThread,groupId,threadId");
		_getGroupIdSQLPartsMap.put(
			SCProductEntry.class.getName(),
			"SCProductEntry,groupId,productEntryId");
		_getGroupIdSQLPartsMap.put(
			WorkflowInstance.class.getName(),
			"WorkflowInstance,groupId,workflowInstanceId");
		_getGroupIdSQLPartsMap.put(
			"com.liferay.bookmarks.model.BookmarksEntry",
			"BookmarksEntry,groupId,entryId");
		_getGroupIdSQLPartsMap.put(
			"com.liferay.bookmarks.model.BookmarksFolder",
			"BookmarksFolder,groupId,folderId");
		_getGroupIdSQLPartsMap.put(
			"com.liferay.portlet.dynamicdatamapping.DDMStructure",
			"DDMStructure,groupId,structureId");
		_getGroupIdSQLPartsMap.put(
			"com.liferay.portlet.journal.model.JournalFolder",
			"JournalFolder,groupId,folderId");
		_getGroupIdSQLPartsMap.put(
			"com.liferay.portlet.wiki.model.WikiNode",
			"WikiNode,groupId,nodeId");
		_getGroupIdSQLPartsMap.put(
			"com.liferay.portlet.wiki.model.WikiPage",
			"WikiPage,groupId,resourcePrimKey");
	}

}