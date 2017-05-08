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

package com.liferay.twitter.internal.upgrade;

import com.liferay.portal.kernel.model.dao.ReleaseDAO;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.upgrade.release.BaseUpgradeServiceModuleRelease;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Adolfo Pérez
 */
public class TwitterUpgradeServiceModuleRelease
	extends BaseUpgradeServiceModuleRelease {

	@Override
	protected void doUpgrade() throws Exception {
		if (Validator.isNull(getOldBundleSymbolicName())) {
			_createRelease();

			return;
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"select buildNumber from Release_ where servletContextName = " +
					"?")) {

			ps.setString(1, getOldBundleSymbolicName());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String buildNumber = rs.getString("buildNumber");

					_updateRelease(_toSchemaVersion(buildNumber));
				}
				else if (_hasServiceComponent()) {
					_createRelease();
				}
			}
		}
	}

	@Override
	protected String getNamespace() {
		return "Twitter";
	}

	@Override
	protected String getNewBundleSymbolicName() {
		return "com.liferay.twitter.service";
	}

	@Override
	protected String getOldBundleSymbolicName() {
		return "twitter-portlet";
	}

	private void _createRelease() throws SQLException {
		ReleaseDAO releaseDAO = new ReleaseDAO();

		releaseDAO.addRelease(connection, getNewBundleSymbolicName());
	}

	private boolean _hasServiceComponent() throws SQLException {
		if (Validator.isNull(getNamespace())) {
			return false;
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"select serviceComponentId from ServiceComponent where " +
					"buildNamespace = ?")) {

			ps.setString(1, getNamespace());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return true;
				}
			}
		}

		return false;
	}

	private String _toSchemaVersion(String buildNumber) {
		StringBuilder sb = new StringBuilder(2 * buildNumber.length());

		for (int i = 0; i < buildNumber.length(); i++) {
			sb.append(buildNumber.charAt(i));
			sb.append(CharPool.PERIOD);
		}

		if (buildNumber.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	private void _updateRelease(String schemaVersion) throws SQLException {
		try (PreparedStatement ps = connection.prepareStatement(
				"update Release_ set servletContextName = ?, schemaVersion = " +
					"? where servletContextName = ?")) {

			ps.setString(1, getNewBundleSymbolicName());
			ps.setString(2, schemaVersion);
			ps.setString(3, getOldBundleSymbolicName());

			ps.execute();
		}
	}

}