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

package com.liferay.portal.kernel.upgrade.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.dao.ReleaseDAO;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;

import java.io.IOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Roberto Díaz
 * @author Arthur Chan
 */
public abstract class UpgradeModules extends UpgradeProcess {

	public abstract String[] getBundleSymbolicNames();

	public abstract String[][] getConvertedLegacyModules();

	public abstract String[][] getLegacyServiceModules();

	protected void addRelease(String... bundleSymbolicNames)
		throws SQLException {

		ReleaseDAO releaseDAO = new ReleaseDAO();

		for (String bundleSymbolicName : bundleSymbolicNames) {
			releaseDAO.addRelease(connection, bundleSymbolicName);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		updateExtractedModules();

		updateConvertedLegacyModules();

		updateLegacyServiceModules();
	}

	protected boolean hasServiceComponent(String buildNamespace)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select serviceComponentId from ServiceComponent where " +
					"buildNamespace = ?")) {

			preparedStatement.setString(1, buildNamespace);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
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

				try (PreparedStatement preparedStatement =
						connection.prepareStatement(
							"select servletContextName, buildNumber from " +
								"Release_ where servletContextName = ?")) {

					preparedStatement.setString(1, oldServletContextName);

					try (ResultSet resultSet =
							preparedStatement.executeQuery()) {

						if (!resultSet.next()) {
							String buildNamespace = convertedLegacyModule[2];

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

	protected void updateLegacyServiceModules() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			for (String[] legacyServiceModule : getLegacyServiceModules()) {
				if (hasTable(legacyServiceModule[1])) {
					addRelease(legacyServiceModule[0]);
				}
			}
		}
	}

	protected void updateServletContextName(
			String oldServletContextName, String newServletContextName)
		throws IOException, SQLException {

		runSQL(
			StringBundler.concat(
				"update Release_ set servletContextName = '",
				newServletContextName, "' where servletContextName = '",
				oldServletContextName, "'"));
	}

}