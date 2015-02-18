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
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.model.Layout;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		updateSubscriptionClassNames(
			Folder.class.getName(), DLFolder.class.getName());
		updateSubscriptionClassNames(
			JournalArticle.class.getName(), JournalFolder.class.getName());

		updateSubscriptionGroupIds();
	}

	protected String getClassName(long classNameId) throws SQLException {
		String className = _classNamesMap.get(classNameId);

		if (className == null) {
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				con = DataAccess.getUpgradeOptimizedConnection();

				ps = con.prepareStatement(
					"select value from ClassName_ where classNameId = ?");

				ps.setLong(1, classNameId);

				rs = ps.executeQuery();

				if (rs.next()) {
					className = rs.getString("value");
				}
				else {
					className = "";
				}
			}
			finally {
				DataAccess.cleanUp(con, ps, rs);
			}

			_classNamesMap.put(classNameId, className);
		}

		return className;
	}

	protected long getGroupId(String className, long classPK) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			String sql = _classNameSQL.get(className);

			ps = con.prepareStatement(sql);

			ps.setLong(1, classPK);

			rs = ps.executeQuery();

			while (rs.next()) {
				return rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return 0;
	}

	protected boolean isExistingGroup(long groupId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select count(*) from Group_ where groupId = ?");

			ps.setLong(1, groupId);

			rs = ps.executeQuery();

			while (rs.next()) {
				int count = rs.getInt(1);

				if (count > 0) {
					return true;
				}
			}

			return false;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateSubscriptionClassNames(
			String oldClassName, String newClassName)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("update Subscription set classNameId = ");
		sb.append(PortalUtil.getClassNameId(newClassName));
		sb.append(" where classNameId = ");
		sb.append(PortalUtil.getClassNameId(oldClassName));

		runSQL(sb.toString());
	}

	protected void updateSubscriptionGroupId(
			long subscriptionId, String className, long classPK)
		throws Exception {

		long groupId = getGroupId(className, classPK);

		if ((groupId == 0) && isExistingGroup(classPK)) {
			groupId = classPK;
		}

		if (groupId != 0) {
			runSQL(
				"update Subscription set groupId = " + groupId + " where " +
					"subscriptionId = " + subscriptionId);
		}
	}

	protected void updateSubscriptionGroupIds() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select subscriptionId, classNameId, classPK from " +
					"Subscription");

			rs = ps.executeQuery();

			while (rs.next()) {
				long subscriptionId = rs.getLong("subscriptionId");
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");

				String className = getClassName(classNameId);

				if (Validator.isNotNull(className)) {
					updateSubscriptionGroupId(
						subscriptionId, className, classPK);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final Map<String, String> _classNameSQL = new HashMap();

	static {
		_classNameSQL.put(
			BlogsEntry.class.getName(),
			"select groupId from BlogsEntry where entryId = ?");
		_classNameSQL.put(
			"com.liferay.bookmarks.model.BookmarksEntry",
			"select groupId from BookmarksEntry where entryId = ?");
		_classNameSQL.put(
			"com.liferay.bookmarks.model.BookmarksFolder",
			"select groupId from BookmarksFolder where folderId = ?");
		_classNameSQL.put(
			DDMStructure.class.getName(),
			"select groupId from DDMStructure where structureId = ?");
		_classNameSQL.put(
			DLFileEntry.class.getName(),
			"select groupId from DLFileEntry where fileEntryId = ?");
		_classNameSQL.put(
			DLFileEntryType.class.getName(),
			"select groupId from DLFileEntryType where fileEntryTypeId = ?");
		_classNameSQL.put(
			DLFolder.class.getName(),
			"select groupId from DLFolder where folderId = ?");
		_classNameSQL.put(
			JournalFolder.class.getName(),
			"select groupId from JournalFolder where folderId = ?");
		_classNameSQL.put(
			Layout.class.getName(),
			"select groupId from Layout where plid = ?");
		_classNameSQL.put(
			MBCategory.class.getName(),
			"select groupId from MBCategory where categoryId = ?");
		_classNameSQL.put(
			MBThread.class.getName(),
			"select groupId from MBThread where threadId = ?");
		_classNameSQL.put(
			SCProductEntry.class.getName(),
			"select groupId from SCProductEntry where productEntryId = ?");
		_classNameSQL.put(
			ShoppingOrder.class.getName(),
			"select groupId from ShoppingOrder where orderId = ?");
		_classNameSQL.put(
			"com.liferay.portlet.wiki.model.WikiNode",
			"select groupId from WikiNode where nodeId = ?");
		_classNameSQL.put(
			"com.liferay.portlet.wiki.model.WikiPage",
			"select groupId from WikiPage where resourcePrimKey = ?");
		_classNameSQL.put(
			WorkflowInstance.class.getName(),
			"select groupId from WorkflowInstance where workflowInstanceId = " +
				"?");
	}

	private final Map<Long, String> _classNamesMap = new HashMap<>();

}