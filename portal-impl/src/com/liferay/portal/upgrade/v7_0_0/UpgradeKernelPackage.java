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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBInspector;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.WildcardMode;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
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
				"ClassName_", "value", getClassNames(), WildcardMode.SURROUND,
				true);
			upgradeTable(
				"Counter", "name", getClassNames(), WildcardMode.SURROUND);
			upgradeTable(
				"Lock_", "className", getClassNames(), WildcardMode.SURROUND);
			upgradeTable(
				"ResourceAction", "name", getClassNames(),
				WildcardMode.SURROUND, true);
			upgradeTable(
				"ResourcePermission", "name", getClassNames(),
				WildcardMode.SURROUND);
			upgradeLongTextTable(
				"UserNotificationEvent", "payload", "userNotificationEventId",
				getClassNames(), WildcardMode.SURROUND);

			upgradeTable(
				"ListType", "type_", getClassNames(), WildcardMode.TRAILING);
			upgradeTable(
				"ResourceAction", "name", getResourceNames(),
				WildcardMode.LEADING, true);
			upgradeTable(
				"ResourcePermission", "name", getResourceNames(),
				WildcardMode.LEADING);
			upgradeLongTextTable(
				"UserNotificationEvent", "payload", "userNotificationEventId",
				getResourceNames(), WildcardMode.LEADING);

			DBInspector dbInspector = new DBInspector(connection);

			if (dbInspector.hasTable("ResourceBlock")) {
				upgradeTable(
					"ResourceBlock", "name", getClassNames(),
					WildcardMode.SURROUND);

				upgradeTable(
					"ResourceBlock", "name", getResourceNames(),
					WildcardMode.LEADING);
			}
		}
		catch (Exception exception) {
			throw new UpgradeException(exception);
		}
	}

	protected String[][] getClassNames() {
		return _CLASS_NAMES;
	}

	protected String[][] getResourceNames() {
		return _RESOURCE_NAMES;
	}

	protected void upgradeLongTextTable(
			String columnName, String primaryKeyColumnName, String selectSQL,
			String updateSQL, String[] name)
		throws SQLException {

		try (PreparedStatement ps1 = connection.prepareStatement(selectSQL);
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 = AutoBatchPreparedStatementUtil.autoBatch(
				connection.prepareStatement(updateSQL))) {

			while (rs.next()) {
				ps2.setString(
					1,
					StringUtil.replace(
						rs.getString(columnName), name[0], name[1]));

				ps2.setLong(2, rs.getLong(primaryKeyColumnName));

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected void upgradeLongTextTable(
			String tableName, String columnName, String primaryKeyColumnName,
			String[][] names, WildcardMode wildcardMode)
		throws Exception {

		DB db = DBManagerUtil.getDB();

		if (db.getDBType() != DBType.SYBASE) {
			upgradeTable(tableName, columnName, names, wildcardMode);

			return;
		}

		try (LoggingTimer loggingTimer = new LoggingTimer(
				getClass(), tableName)) {

			StringBundler updateSB = new StringBundler(7);

			updateSB.append("update ");
			updateSB.append(tableName);
			updateSB.append(" set ");
			updateSB.append(columnName);
			updateSB.append(" = ? where ");
			updateSB.append(primaryKeyColumnName);
			updateSB.append(" = ?");

			String updateSQL = updateSB.toString();

			StringBundler selectPrefixSB = new StringBundler(10);

			selectPrefixSB.append("select ");
			selectPrefixSB.append(columnName);
			selectPrefixSB.append(", ");
			selectPrefixSB.append(primaryKeyColumnName);
			selectPrefixSB.append(" from ");
			selectPrefixSB.append(tableName);
			selectPrefixSB.append(" where ");
			selectPrefixSB.append(columnName);
			selectPrefixSB.append(" like '");
			selectPrefixSB.append(wildcardMode.getLeadingWildcard());

			String selectPrefix = selectPrefixSB.toString();

			String selectPostfix =
				wildcardMode.getTrailingWildcard() + StringPool.APOSTROPHE;

			for (String[] name : names) {
				upgradeLongTextTable(
					columnName, primaryKeyColumnName,
					StringBundler.concat(selectPrefix, name[0], selectPostfix),
					updateSQL, name);
			}
		}
	}

	protected void upgradeTable(
			String tableName, String columnName, String[][] names,
			WildcardMode wildcardMode)
		throws Exception {

		upgradeTable(tableName, columnName, names, wildcardMode, false);
	}

	protected void upgradeTable(
			String tableName, String columnName, String[][] names,
			WildcardMode wildcardMode, boolean preventDuplicates)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(
				getClass(), tableName)) {

			if (preventDuplicates) {
				_executeDelete(tableName, columnName, names, wildcardMode);
			}

			_executeUpdate(tableName, columnName, names, wildcardMode);
		}
	}

	private void _executeDelete(
			String tableName, String columnName, String[][] names,
			WildcardMode wildcardMode)
		throws Exception {

		for (String[] name : names) {
			StringBundler sb = new StringBundler(4);

			sb.append("delete from ");
			sb.append(tableName);
			sb.append(_getWhereClause(columnName, name[1], wildcardMode));
			sb.append(
				_getNotLikeClause(
					columnName, (String)ArrayUtil.getValue(name, 2),
					wildcardMode));

			runSQL(sb.toString());
		}
	}

	private void _executeUpdate(
			String tableName, String columnName, String[][] names,
			WildcardMode wildcardMode)
		throws Exception {

		StringBundler sb1 = new StringBundler(7);

		sb1.append("update ");
		sb1.append(tableName);
		sb1.append(" set ");
		sb1.append(columnName);
		sb1.append(" = replace(");
		sb1.append(_transformColumnName(columnName));
		sb1.append(", '");

		String tableSQL = sb1.toString();

		StringBundler sb2 = new StringBundler(6);

		for (String[] name : names) {
			sb2.append(tableSQL);
			sb2.append(name[0]);
			sb2.append("', '");
			sb2.append(name[1]);
			sb2.append("') ");

			String whereClause = _getWhereClause(
				columnName, name[0], wildcardMode);

			sb2.append(whereClause);

			runSQL(sb2.toString());

			sb2.setIndex(0);
		}
	}

	private String _getNotLikeClause(
		String columnName, String value, WildcardMode wildcardMode) {

		if (value == null) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(" and ");
		sb.append(columnName);
		sb.append(" not like ");
		sb.append(StringPool.APOSTROPHE);
		sb.append(wildcardMode.getLeadingWildcard());
		sb.append(value);
		sb.append(wildcardMode.getTrailingWildcard());
		sb.append(StringPool.APOSTROPHE);

		return sb.toString();
	}

	private String _getWhereClause(
		String columnName, String columnValue, WildcardMode wildcardMode) {

		StringBundler sb = new StringBundler(8);

		sb.append(" where ");
		sb.append(columnName);
		sb.append(" like ");
		sb.append(StringPool.APOSTROPHE);
		sb.append(wildcardMode.getLeadingWildcard());
		sb.append(columnValue);
		sb.append(wildcardMode.getTrailingWildcard());
		sb.append(StringPool.APOSTROPHE);

		return sb.toString();
	}

	private String _transformColumnName(String columnName) {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() == DBType.SQLSERVER) {
			return "CAST_TEXT(" + columnName + ")";
		}

		return columnName;
	}

	private static final String[][] _CLASS_NAMES = {
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
		{"com.liferay.portal.model.Lock", "com.liferay.portal.lock.model.Lock"},
		{"com.liferay.portal.model.", "com.liferay.portal.kernel.model."},
		{
			"com.liferay.portlet.announcements.model.",
			"com.liferay.announcements.kernel.model."
		},
		{"com.liferay.portlet.asset.model.", "com.liferay.asset.kernel.model."},
		{"com.liferay.portlet.blogs.model.", "com.liferay.blogs.kernel.model."},
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
		{"com.liferay.portlet.journal.model.", "com.liferay.journal.model."},
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
		{"com.liferay.portlet.trash.model.", "com.liferay.trash.kernel.model."},
		{
			"com.liferay.socialnetworking.model.",
			"com.liferay.social.networking.model."
		}
	};

	private static final String[][] _RESOURCE_NAMES = {
		{"com.liferay.portlet.asset", "com.liferay.asset"},
		{"com.liferay.portlet.blogs", "com.liferay.blogs"},
		{"com.liferay.portlet.documentlibrary", "com.liferay.document.library"},
		{"com.liferay.portlet.journal", "com.liferay.journal"},
		{"com.liferay.portlet.messageboards", "com.liferay.message.boards"}
	};

}