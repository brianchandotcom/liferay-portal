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
import com.liferay.portal.kernel.dao.db.DBProcessContext;
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
public abstract class UpgradeProcess
	extends BaseDBProcess implements UpgradeStep {

	public int getThreshold() {

		// This upgrade process will only run if the build number is larger than
		// the returned threshold value. Return 0 to always run this upgrade
		// process.

		return 0;
	}

	public void upgrade() throws UpgradeException {
		long start = System.currentTimeMillis();

		if (_log.isInfoEnabled()) {
			_log.info("Upgrading " + ClassUtil.getClassName(this));
		}

		try (Connection con = DataAccess.getUpgradeOptimizedConnection()) {
			connection = con;

			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
		finally {
			connection = null;

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

	@Override
	public void upgrade(DBProcessContext dbProcessContext)
		throws UpgradeException {

		upgrade();
	}

	public void upgrade(UpgradeProcess upgradeProcess) throws UpgradeException {
		upgradeProcess.upgrade();
	}

	protected boolean doHasTable(String tableName) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			DatabaseMetaData metadata = connection.getMetaData();

			rs = metadata.getTables(null, null, tableName, null);

			while (rs.next()) {
				return true;
			}
		}
		finally {
			DataAccess.cleanUp(null, ps, rs);
		}

		return false;
	}

	protected abstract void doUpgrade() throws Exception;

	protected boolean hasTable(String tableName) throws Exception {
		if (doHasTable(StringUtil.toLowerCase(tableName)) ||
			doHasTable(StringUtil.toUpperCase(tableName)) ||
			doHasTable(tableName)) {

			return true;
		}

		return false;
	}

	protected long increment() {
		DB db = DBFactoryUtil.getDB();

		return db.increment();
	}

	protected long increment(String name) {
		DB db = DBFactoryUtil.getDB();

		return db.increment(name);
	}

	protected boolean isSupportsAlterColumnName() {
		DB db = DBFactoryUtil.getDB();

		return db.isSupportsAlterColumnName();
	}

	protected boolean isSupportsAlterColumnType() {
		DB db = DBFactoryUtil.getDB();

		return db.isSupportsAlterColumnType();
	}

	protected boolean isSupportsStringCaseSensitiveQuery() {
		DB db = DBFactoryUtil.getDB();

		return db.isSupportsStringCaseSensitiveQuery();
	}

	protected boolean isSupportsUpdateWithInnerJoin() {
		DB db = DBFactoryUtil.getDB();

		return db.isSupportsUpdateWithInnerJoin();
	}

	protected boolean tableHasColumn(String tableName, String columnName)
		throws Exception {

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement("select * from " + tableName);

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
			DataAccess.cleanUp(null, ps, rs);
		}

		return false;
	}

	protected boolean tableHasData(String tableName) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select count(*) from " + tableName);

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
			DataAccess.cleanUp(null, ps, rs);
		}

		return false;
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

	private static final Log _log = LogFactoryUtil.getLog(UpgradeProcess.class);

}