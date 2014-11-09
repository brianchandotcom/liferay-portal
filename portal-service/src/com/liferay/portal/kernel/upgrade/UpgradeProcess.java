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

package com.liferay.portal.kernel.upgrade;

import com.liferay.portal.kernel.dao.db.BaseDBProcess;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public abstract class UpgradeProcess extends BaseDBProcess {

	public int getThreshold() {

		// This upgrade process will only run if the build number is larger than
		// the returned threshold value. Return 0 to always run this upgrade
		// process.

		return 0;
	}

	public boolean hasTable(String tableName) throws Exception {
		if (doHasTable(StringUtil.toLowerCase(tableName)) ||
			doHasTable(StringUtil.toUpperCase(tableName)) ||
			doHasTable(tableName)) {

			return true;
		}

		return false;
	}

	public long increment() {
		DB db = DBFactoryUtil.getDB();

		return db.increment();
	}

	public long increment(String name) {
		DB db = DBFactoryUtil.getDB();

		return db.increment(name);
	}

	public boolean isSupportsAlterColumnName() {
		DB db = DBFactoryUtil.getDB();

		return db.isSupportsAlterColumnName();
	}

	public boolean isSupportsAlterColumnType() {
		DB db = DBFactoryUtil.getDB();

		return db.isSupportsAlterColumnType();
	}

	public boolean isSupportsStringCaseSensitiveQuery() {
		DB db = DBFactoryUtil.getDB();

		return db.isSupportsStringCaseSensitiveQuery();
	}

	public boolean isSupportsUpdateWithInnerJoin() {
		DB db = DBFactoryUtil.getDB();

		return db.isSupportsUpdateWithInnerJoin();
	}

	public boolean tableHasColumn(String tableName, String columnName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select * from " + tableName);

			rs = ps.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();

			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				String curColumnName = rsmd.getColumnName(i + 1);

				if (StringUtil.equalsIgnoreCase(curColumnName, columnName)) {
					return true;
				}
			}
		}
		catch (Exception e) {
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return false;
	}

	public boolean tableHasData(String tableName) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement("select count(*) from " + tableName);

			rs = ps.executeQuery();

			while (rs.next()) {
				int count = rs.getInt(1);

				if (count > 0) {
					return true;
				}
			}
		}
		catch (Exception e) {
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return false;
	}

	public void upgrade() throws UpgradeException {
		long start = System.currentTimeMillis();

		try {
			if (_log.isInfoEnabled()) {
				_log.info("Upgrading " + ClassUtil.getClassName(this));
			}

			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
		finally {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Completed upgrade process " +
						ClassUtil.getClassName(this) + " in " +
							(System.currentTimeMillis() - start) + "ms");
			}
		}
	}

	public void upgrade(Class<?> upgradeProcessClass) throws UpgradeException {
		UpgradeProcess upgradeProcess = null;

		try {
			upgradeProcess = (UpgradeProcess)upgradeProcessClass.newInstance();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}

		upgradeProcess.upgrade();
	}

	public void upgrade(UpgradeProcess upgradeProcess) throws UpgradeException {
		upgradeProcess.upgrade();
	}

	protected boolean doHasTable(String tableName) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			DatabaseMetaData metadata = con.getMetaData();

			rs = metadata.getTables(null, null, tableName, null);

			while (rs.next()) {
				return true;
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return false;
	}

	protected void doUpgrade() throws Exception {
	}

	protected void upgradeTable(String tableName, Object[][] tableColumns)
		throws Exception {

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			tableName, tableColumns);

		upgradeTable.updateTable();
	}

	protected void upgradeTable(
			String tableName, Object[][] tableColumns, String sqlCreate,
			String[] sqlAddIndexes)
		throws Exception {

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			tableName, tableColumns);

		upgradeTable.setCreateSQL(sqlCreate);
		upgradeTable.setIndexesSQL(sqlAddIndexes);

		upgradeTable.updateTable();
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeProcess.class);

}