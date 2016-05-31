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
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.util.DBRelease;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	private void _doUpgrade(String bundleSymbolicName, String... portletIds)
		throws SQLException {

		if (_hasAnyPortlet(portletIds)) {
			DBRelease dbRelease = new DBRelease(
				_connection, _counterLocalService);

			dbRelease.addRelease(bundleSymbolicName);
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