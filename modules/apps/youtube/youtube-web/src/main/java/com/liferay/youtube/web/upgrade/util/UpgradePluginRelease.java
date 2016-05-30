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

package com.liferay.youtube.web.upgrade.util;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Adolfo Pérez
 */
public class UpgradePluginRelease extends UpgradeProcess {

	public UpgradePluginRelease(CounterLocalService counterLocalService) {
		_counterLocalService = counterLocalService;
	}

	protected void doUpgrade() throws Exception {
		if (_hasPortlet(_PORTLET_ID)) {
			_addRelease(_BUNDLE_SYMBOLIC_NAME);
		}
	}

	private void _addRelease(String bundleSymbolicName) throws SQLException {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		StringBundler sb = new StringBundler(4);

		sb.append("insert into Release_ (mvccVersion, releaseId, ");
		sb.append("createDate, modifiedDate, servletContextName, ");
		sb.append("schemaVersion, buildNumber, buildDate, verified, state_, ");
		sb.append("testString) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps = connection.prepareStatement(
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

	private boolean _hasPortlet(String portletId) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(
				"select portletId from Portlet where portletId = ?")) {

			ps.setString(1, portletId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return true;
				}

				return false;
			}
		}
	}

	private static final String _BUNDLE_SYMBOLIC_NAME =
		"com.liferay.youtube.web";

	private static final String _PORTLET_ID = "1_WAR_youtubeportlet";

	private final CounterLocalService _counterLocalService;

}