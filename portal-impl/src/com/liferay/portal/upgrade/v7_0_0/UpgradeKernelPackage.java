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

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Preston Crary
 */
public class UpgradeKernelPackage extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws UpgradeException {
		try {
			upgradeTable(
				"Counter", "name", getClassNames(), WildcardMode.SURROUND);
			upgradeTable(
				"ClassName_", "value", getClassNames(), WildcardMode.SURROUND);
			upgradeTable(
				"Lock_", "className", getClassNames(), WildcardMode.SURROUND);
			upgradeTable(
				"ResourceAction", "name", getClassNames(),
				WildcardMode.SURROUND);
			upgradeTable(
				"ResourceBlock", "name", getClassNames(),
				WildcardMode.SURROUND);
			upgradeTable(
				"ResourcePermission", "name", getClassNames(),
				WildcardMode.SURROUND);
			upgradeLongTextTable(
				"UserNotificationEvent", "payload", getClassNames(),
				WildcardMode.SURROUND);

			upgradeTable(
				"ListType", "type_", getClassNames(), WildcardMode.TRAILING);
			upgradeTable(
				"ResourceAction", "name", getResourceNames(),
				WildcardMode.LEADING);
			upgradeTable(
				"ResourceBlock", "name", getResourceNames(),
				WildcardMode.LEADING);
			upgradeTable(
				"ResourcePermission", "name", getResourceNames(),
				WildcardMode.LEADING);
			upgradeLongTextTable(
				"UserNotificationEvent", "payload", getResourceNames(),
				WildcardMode.LEADING);
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected String[][] getClassNames() {
		return _CLASS_NAMES;
	}

	protected String[][] getResourceNames() {
		return _RESOURCE_NAMES;
	}

	protected void upgradeLongTextTable(
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

	protected void upgradeLongTextTable(
			String tableName, String columnName, String[][] names,
			WildcardMode wildcardMode)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		if (db.getDBType() != DBType.SYBASE) {
			upgradeTable(tableName, columnName, names, wildcardMode);

			return;
		}

		try (LoggingTimer loggingTimer = new LoggingTimer(tableName)) {
			StringBundler updateSB = new StringBundler(7);

			updateSB.append("update ");
			updateSB.append(tableName);
			updateSB.append(" set ");
			updateSB.append(columnName);
			updateSB.append(" = ? where ");
			updateSB.append(columnName);
			updateSB.append(" = ?");

			String updateSQL = updateSB.toString();

			StringBundler selectPrefixSB = new StringBundler(7);

			selectPrefixSB.append("select ");
			selectPrefixSB.append(columnName);
			selectPrefixSB.append(" from ");
			selectPrefixSB.append(tableName);
			selectPrefixSB.append(" where ");
			selectPrefixSB.append(columnName);

			if (wildcardMode.equals(WildcardMode.LEADING) ||
				wildcardMode.equals(WildcardMode.SURROUND)) {

				selectPrefixSB.append(" like '%");
			}
			else {
				selectPrefixSB.append(" like '");
			}

			String selectPrefix = selectPrefixSB.toString();

			String selectPostfix = StringPool.APOSTROPHE;

			if (wildcardMode.equals(WildcardMode.SURROUND) ||
				wildcardMode.equals(WildcardMode.TRAILING)) {

				selectPostfix = "%'";
			}

			for (String[] name : names) {
				String selectSQL = selectPrefix.concat(name[0]).concat(
					selectPostfix);

				upgradeLongTextTable(columnName, selectSQL, updateSQL, name);
			}
		}
	}

	protected void upgradeTable(
			String tableName, String columnName, String[][] names,
			WildcardMode wildcardMode)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(tableName)) {
			StringBundler sb1 = new StringBundler(7);

			sb1.append("update ");
			sb1.append(tableName);
			sb1.append(" set ");
			sb1.append(columnName);
			sb1.append(" = replace(");
			sb1.append(columnName);
			sb1.append(", '");

			String tableSQL = sb1.toString();

			StringBundler sb2 = new StringBundler(9);

			for (String[] name : names) {
				sb2.append(tableSQL);
				sb2.append(name[0]);
				sb2.append("', '");
				sb2.append(name[1]);
				sb2.append("') where ");
				sb2.append(columnName);

				if (wildcardMode.equals(WildcardMode.LEADING) ||
					wildcardMode.equals(WildcardMode.SURROUND)) {

					sb2.append(" like '%");
				}
				else {
					sb2.append(" like '");
				}

				sb2.append(name[0]);

				if (wildcardMode.equals(WildcardMode.SURROUND) ||
					wildcardMode.equals(WildcardMode.TRAILING)) {

					sb2.append("%'");
				}
				else {
					sb2.append("'");
				}

				runSQL(sb2.toString());

				sb2.setIndex(0);
			}
		}
	}

	private static final String[][] _CLASS_NAMES = new String[][] {
		{
			"com.liferay.counter.model.Counter",
			"com.liferay.counter.kernel.model.Counter"
		},
		{
			"com.liferay.portal.kernel.mail.Account",
			"com.liferay.mail.kernel.model.Account"
		},
		{
			"com.liferay.portal.model.BackgroundTask",
			"com.liferay.portal.background.task.model.BackgroundTask"
		},
		{
			"com.liferay.portal.model.Lock",
			"com.liferay.portal.lock.model.Lock"
		},
		{"com.liferay.portal.model.", "com.liferay.portal.kernel.model."},
		{
			"com.liferay.portlet.announcements.model.",
			"com.liferay.announcements.kernel.model."
		},
		{
			"com.liferay.portlet.asset.model.",
			"com.liferay.asset.kernel.model."
		},
		{
			"com.liferay.portlet.blogs.model.",
			"com.liferay.blogs.kernel.model."
		},
		{
			"com.liferay.portlet.documentlibrary.model.",
			"com.liferay.document.library.kernel.model."
		},
		{
			"com.liferay.portlet.documentlibrary.util.",
			"com.liferay.document.library.kernel.util."
		},
		{
			"com.liferay.portlet.expando.model.",
			"com.liferay.expando.kernel.model."
		},
		{
			"com.liferay.portlet.messageboards.model.",
			"com.liferay.message.boards.kernel.model."
		},
		{
			"com.liferay.portlet.mobiledevicerules.model.",
			"com.liferay.mobile.device.rules.model."
		},
		{
			"com.liferay.portlet.ratings.model.",
			"com.liferay.ratings.kernel.model."
		},
		{
			"com.liferay.portlet.social.model.",
			"com.liferay.social.kernel.model."
		},
		{
			"com.liferay.portlet.trash.model.",
			"com.liferay.trash.kernel.model."
		},
		{
			"com.liferay.socialnetworking.model.",
			"com.liferay.social.networking.model."
		}
	};

	private static final String[][] _RESOURCE_NAMES = new String[][] {
		{"com.liferay.portlet.asset", "com.liferay.asset"},
		{"com.liferay.portlet.blogs", "com.liferay.blogs"},
		{
			"com.liferay.portlet.documentlibrary",
			"com.liferay.document.library"
		},
		{"com.liferay.portlet.messageboards", "com.liferay.message.boards"}
	};

}