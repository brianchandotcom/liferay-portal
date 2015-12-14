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

import com.liferay.portal.dao.jdbc.spring.DataSourceFactoryBean;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.v7_0_0.util.ClassNameTable;
import com.liferay.portal.upgrade.v7_0_0.util.ClusterGroupTable;
import com.liferay.portal.upgrade.v7_0_0.util.CounterTable;
import com.liferay.portal.upgrade.v7_0_0.util.CountryTable;
import com.liferay.portal.upgrade.v7_0_0.util.PortalPreferencesTable;
import com.liferay.portal.upgrade.v7_0_0.util.RegionTable;
import com.liferay.portal.upgrade.v7_0_0.util.ReleaseTable;
import com.liferay.portal.upgrade.v7_0_0.util.ResourceActionTable;
import com.liferay.portal.upgrade.v7_0_0.util.ServiceComponentTable;
import com.liferay.portal.upgrade.v7_0_0.util.VirtualHostTable;
import com.liferay.portal.util.PropsUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author Manuel de la Peña
 */
public class UpgradeSharding extends UpgradeProcess {

	protected void copyControlTable(
			Connection sourceConnection, Connection targetConnection,
			String tableName, Object[][] columns, String createSQL)
		throws Exception {

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			tableName, columns);

		upgradeTable.setCreateSQL(createSQL);

		upgradeTable.copyTable(sourceConnection, targetConnection);
	}

	protected void copyControlTables(List<String> shardNames) throws Exception {
		String defaultShardName = PropsUtil.get("shard.default.name");

		if (Validator.isNull(defaultShardName)) {
			StringBuilder sb = new StringBuilder(shardNames.size());

			for (int i = 0; i < shardNames.size(); i++) {
				String shardName = shardNames.get(i);

				sb.append(shardName);

				if (i < (shardNames.size() - 1)) {
					sb.append(", ");
				}
			}

			throw new RuntimeException(
				"The property \"shard.default.name\" is not set in " +
					"portal.properties. Please use your default shard name " +
					"from this list: " + sb.toString());
		}

		for (String shardName : shardNames) {
			if (!shardName.equals(defaultShardName)) {
				copyControlTables(shardName);
			}
		}
	}

	protected void copyControlTables(String shardName) throws Exception {
		Connection sourceConnection =
			DataAccess.getUpgradeOptimizedConnection();

		DataSourceFactoryBean dataSourceFactoryBean =
			new DataSourceFactoryBean();

		dataSourceFactoryBean.setPropertyPrefix("jdbc." + shardName + ".");

		DataSource dataSource = dataSourceFactoryBean.createInstance();

		Connection targetConnection = dataSource.getConnection();

		try {
			copyControlTable(
				sourceConnection, targetConnection, ClassNameTable.TABLE_NAME,
				ClassNameTable.TABLE_COLUMNS, ClassNameTable.TABLE_SQL_CREATE);
			copyControlTable(
				sourceConnection, targetConnection,
				ClusterGroupTable.TABLE_NAME, ClusterGroupTable.TABLE_COLUMNS,
				ClusterGroupTable.TABLE_SQL_CREATE);
			copyControlTable(
				sourceConnection, targetConnection, CounterTable.TABLE_NAME,
				CounterTable.TABLE_COLUMNS, CounterTable.TABLE_SQL_CREATE);
			copyControlTable(
				sourceConnection, targetConnection, CountryTable.TABLE_NAME,
				CountryTable.TABLE_COLUMNS, CountryTable.TABLE_SQL_CREATE);
			copyControlTable(
				sourceConnection, targetConnection,
				PortalPreferencesTable.TABLE_NAME,
				PortalPreferencesTable.TABLE_COLUMNS,
				PortalPreferencesTable.TABLE_SQL_CREATE);
			copyControlTable(
				sourceConnection, targetConnection, RegionTable.TABLE_NAME,
				RegionTable.TABLE_COLUMNS, RegionTable.TABLE_SQL_CREATE);
			copyControlTable(
				sourceConnection, targetConnection, ReleaseTable.TABLE_NAME,
				ReleaseTable.TABLE_COLUMNS, ReleaseTable.TABLE_SQL_CREATE);
			copyControlTable(
				sourceConnection, targetConnection,
				ResourceActionTable.TABLE_NAME,
				ResourceActionTable.TABLE_COLUMNS,
				ResourceActionTable.TABLE_SQL_CREATE);
			copyControlTable(
				sourceConnection, targetConnection,
				ServiceComponentTable.TABLE_NAME,
				ServiceComponentTable.TABLE_COLUMNS,
				ServiceComponentTable.TABLE_SQL_CREATE);
			copyControlTable(
				sourceConnection, targetConnection, VirtualHostTable.TABLE_NAME,
				VirtualHostTable.TABLE_COLUMNS,
				VirtualHostTable.TABLE_SQL_CREATE);
		}
		catch (Exception e) {
			_log.error("Unable to copy control tables", e);
		}
		finally {
			DataAccess.cleanUp(sourceConnection);

			DataAccess.cleanUp(targetConnection);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<String> shardNames = getShardNames();

		if (shardNames.size() <= 1) {
			return;
		}

		copyControlTables(shardNames);
	}

	protected List<String> getShardNames() throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;

		List<String> shardNames = new ArrayList<>();

		try {
			ps = connection.prepareStatement("select name from Shard");

			rs = ps.executeQuery();

			while (rs.next()) {
				shardNames.add(rs.getString("name"));
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}

		return shardNames;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeSharding.class);

}