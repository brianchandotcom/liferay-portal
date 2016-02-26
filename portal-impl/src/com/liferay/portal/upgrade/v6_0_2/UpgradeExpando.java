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

package com.liferay.portal.upgrade.v6_0_2;

import com.liferay.expando.kernel.model.ExpandoTableConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Jorge Ferrer
 */
public class UpgradeExpando extends UpgradeProcess {

	protected void addRow(
			long rowId, long companyId, long tableId, long classPK)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"insert into ExpandoRow (rowId_, companyId, tableId, " +
					"classPK) values (?, ?, ?, ?)")) {

			ps.setLong(1, rowId);
			ps.setLong(2, companyId);
			ps.setLong(3, tableId);
			ps.setLong(4, classPK);

			ps.executeUpdate();
		}
	}

	protected void addValue(
			long valueId, long companyId, long tableId, long columnId,
			long rowId, long classNameId, long classPK, String data)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"insert into ExpandoValue (valueId, companyId, tableId, " +
					"columnId, rowId_, classNameId, classPK, data_) values " +
						"(?, ?, ?, ?, ?, ?, ?, ?)")) {

			ps.setLong(1, valueId);
			ps.setLong(2, companyId);
			ps.setLong(3, tableId);
			ps.setLong(4, columnId);
			ps.setLong(5, rowId);
			ps.setLong(6, classNameId);
			ps.setLong(7, classPK);
			ps.setString(8, data);

			ps.executeUpdate();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateTables(
			"com.liferay.portlet.journal.model.JournalArticle",
			"JournalArticle", "id_");

		updateTables("com.liferay.wiki.model.WikiPage", "WikiPage", "pageId");
	}

	protected boolean hasRow(long companyId, long tableId, long classPK)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select count(*) from ExpandoRow where companyId = ? and " +
					"tableId = ? and classPK = ?")) {

			ps.setLong(1, companyId);
			ps.setLong(2, tableId);
			ps.setLong(3, classPK);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int count = rs.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	protected boolean hasValue(
			long companyId, long tableId, long columnId, long rowId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select count(*) from ExpandoValue where companyId = ? and " +
					"tableId = ? and columnId = ? and rowId_ = ?")) {

			ps.setLong(1, companyId);
			ps.setLong(2, tableId);
			ps.setLong(3, columnId);
			ps.setLong(4, rowId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					int count = rs.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	protected void updateRow(
			long companyId, long classPK, String tableName, long tableId,
			String columnName, long rowId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select " + columnName + " from " + tableName + " where " +
					"resourcePrimKey = ?")) {

			ps.setLong(1, classPK);

			try (ResultSet rs = ps.executeQuery()) {
				boolean delete = false;

				while (rs.next()) {
					long newClassPK = rs.getLong(columnName);

					delete = true;

					if (!hasRow(companyId, tableId, newClassPK)) {
						long newRowId = increment();

						addRow(newRowId, companyId, tableId, newClassPK);

						updateValues(
							classPK, newClassPK, tableId, rowId, newRowId);
					}
				}

				if (delete) {
					runSQL("delete from ExpandoRow where rowId_ = " + rowId);
					runSQL("delete from ExpandoValue where rowId_ = " + rowId);
				}
			}
		}
	}

	protected void updateRows(String tableName, long tableId, String columnName)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from ExpandoRow where tableId = ?")) {

			ps.setLong(1, tableId);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long rowId = rs.getLong("rowId_");
					long companyId = rs.getLong("companyId");
					long classPK = rs.getLong("classPK");

					updateRow(
						companyId, classPK, tableName, tableId, columnName,
						rowId);
				}
			}
		}
	}

	protected void updateTables(
			String className, String tableName, String columnName)
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer(className)) {
			if (_log.isDebugEnabled()) {
				_log.debug("Upgrading " + tableName);
			}

			long classNameId = PortalUtil.getClassNameId(className);

			try (PreparedStatement ps = connection.prepareStatement(
					"select * from ExpandoTable where classNameId = ? and " +
						"name = ?")) {

				ps.setLong(1, classNameId);
				ps.setString(2, ExpandoTableConstants.DEFAULT_TABLE_NAME);

				try (ResultSet rs = ps.executeQuery()) {
					while (rs.next()) {
						long tableId = rs.getLong("tableId");

						updateRows(tableName, tableId, columnName);
					}
				}
			}
		}
	}

	protected void updateValues(
			long classPK, long newClassPK, long tableId, long rowId,
			long newRowId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from ExpandoValue where tableId = ? and rowId_ = ? " +
					"and classPK = ?")) {

			ps.setLong(1, tableId);
			ps.setLong(2, rowId);
			ps.setLong(3, classPK);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long companyId = rs.getLong("companyId");
					long columnId = rs.getLong("columnId");
					long classNameId = rs.getLong("classNameId");
					String data = rs.getString("data_");

					if (!hasValue(companyId, tableId, columnId, newRowId)) {
						long newValueId = increment();

						addValue(
							newValueId, companyId, tableId, columnId, newRowId,
							classNameId, newClassPK, data);
					}
				}
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeExpando.class);

}