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

package com.liferay.portal.upgrade.legacy;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Adolfo Pérez
 */
public class UpgradePluginRelease {

	public UpgradePluginRelease(CounterLocalService counterLocalService) {
		_counterLocalService = counterLocalService;
	}

	public void upgrade(String bundleSymbolicName, String... portletIds)
		throws UpgradeException {

		try (Connection con = DataAccess.getUpgradeOptimizedConnection()) {
			_connection = con;

			_doUpgrade(bundleSymbolicName, portletIds);
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
		finally {
			_connection = null;
		}
	}

	private void _addRelease(String bundleSymbolicName) throws SQLException {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		StringBundler sb = new StringBundler(4);

		sb.append("insert into Release_ (mvccVersion, releaseId, ");
		sb.append("createDate, modifiedDate, servletContextName, ");
		sb.append("schemaVersion, buildNumber, buildDate, verified, state_, ");
		sb.append("testString) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps = _connection.prepareStatement(
				sb.toString())) {

			ps.setLong(1, 0);
			ps.setLong(2, _counterLocalService.increment());
			ps.setTimestamp(3, timestamp);
			ps.setTimestamp(4, timestamp);
			ps.setString(5, bundleSymbolicName);
			ps.setString(6, "0.0.1");
			ps.setInt(7, 001);
			ps.setTimestamp(8, timestamp);
			ps.setBoolean(9, false);
			ps.setInt(10, 0);
			ps.setString(11, ReleaseConstants.TEST_STRING);

			ps.execute();
		}
	}

	private void _doUpgrade(String bundleSymbolicName, String... portletIds)
		throws SQLException {

		if (_hasAnyPortlet(portletIds)) {
			_addRelease(bundleSymbolicName);
		}
	}

	private boolean _hasAnyPortlet(String... portletIds) throws SQLException {
		if (portletIds.length == 0) {
			return false;
		}

		StringBundler sb = new StringBundler(portletIds.length + 1);

		sb.append("(?");

		for (int i = 1; i < portletIds.length; i++) {
			sb.append(", ?");
		}

		sb.append(CharPool.CLOSE_PARENTHESIS);

		try (PreparedStatement ps = _connection.prepareStatement(
				"select portletId from Portlet where portletId in " +
					sb.toString())) {

			for (int i = 0; i < portletIds.length; i++) {
				ps.setString(i + 1, portletIds[i]);
			}

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return true;
				}

				return false;
			}
		}
	}

	private Connection _connection;
	private final CounterLocalService _counterLocalService;

}