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

package com.liferay.portal.upgrade.util;

import com.liferay.portal.kernel.model.ReleaseConstants;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Miguel Pastor
 */
public abstract class UpgradeModules extends UpgradeProcess {

	public abstract String[] getBundleSymbolicNames();

	public abstract String[][] getConvertedLegacyModules();

	protected void addRelease(String... bundleSymbolicNames)
		throws SQLException {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		StringBundler sb = new StringBundler(4);

		sb.append("insert into Release_ (mvccVersion, releaseId, ");
		sb.append("createDate, modifiedDate, servletContextName, ");
		sb.append("schemaVersion, buildNumber, buildDate, verified, state_, ");
		sb.append("testString) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			for (String bundleSymbolicName : bundleSymbolicNames) {
				ps.setLong(1, 0);
				ps.setLong(2, increment());
				ps.setTimestamp(3, timestamp);
				ps.setTimestamp(4, timestamp);
				ps.setString(5, bundleSymbolicName);
				ps.setString(6, "0.0.1");
				ps.setInt(7, 001);
				ps.setTimestamp(8, timestamp);
				ps.setBoolean(9, false);
				ps.setInt(10, 0);
				ps.setString(11, ReleaseConstants.TEST_STRING);

				ps.addBatch();
			}

			ps.executeBatch();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateExtractedModules();

		updateConvertedLegacyModules();
	}

	protected boolean hasServiceComponent(String buildNamespace)
		throws SQLException {

		try (PreparedStatement ps = connection.prepareStatement(
				"select serviceComponentId from ServiceComponent " +
					"where buildNamespace = ?")) {

			ps.setString(1, buildNamespace);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return true;
				}
			}
		}

		return false;
	}

	protected void updateConvertedLegacyModules()
		throws IOException, SQLException {

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			for (String[] convertedLegacyModule : getConvertedLegacyModules()) {
				String oldServletContextName = convertedLegacyModule[0];
				String newServletContextName = convertedLegacyModule[1];
				String buildNamespace = convertedLegacyModule[2];

				try (PreparedStatement ps = connection.prepareStatement(
						"select servletContextName, buildNumber from Release_" +
							" where servletContextName = ?")) {

					ps.setString(1, oldServletContextName);

					try (ResultSet rs = ps.executeQuery()) {
						if (!rs.next()) {
							if (hasServiceComponent(buildNamespace)) {
								addRelease(newServletContextName);
							}
						}
						else {
							updateServletContextName(
								oldServletContextName, newServletContextName);
						}
					}
				}
			}
		}
	}

	protected void updateExtractedModules() throws SQLException {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			addRelease(getBundleSymbolicNames());
		}
	}

	protected void updateServletContextName(
			String oldServletContextName, String newServletContextName)
		throws IOException, SQLException {

		runSQL(
			"update Release_ set servletContextName = '" +
				newServletContextName + "' where servletContextName = '" +
					oldServletContextName + "'");
	}

}