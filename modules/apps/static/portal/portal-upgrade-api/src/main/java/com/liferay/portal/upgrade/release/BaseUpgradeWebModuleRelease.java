/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.release;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.dao.ReleaseDAO;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseUpgradeWebModuleRelease extends UpgradeProcess {

	@Override
	public void upgrade() throws UpgradeException {
		try (Connection connection = DataAccess.getConnection()) {
			if (hasAnyPortletId(connection, getPortletIds())) {
				super.upgrade();
			}
		}
		catch (SQLException sqlException) {
			throw new UpgradeException(sqlException);
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		ReleaseDAO releaseDAO = new ReleaseDAO();

		releaseDAO.addRelease(connection, getBundleSymbolicName());
	}

	protected abstract String getBundleSymbolicName();

	protected abstract String[] getPortletIds();

	protected boolean hasAnyPortletId(
			Connection connection, String... portletIds)
		throws SQLException {

		if (portletIds.length == 0) {
			return false;
		}

		StringBundler sb = new StringBundler(portletIds.length + 1);

		sb.append("(?");

		for (int i = 1; i < portletIds.length; i++) {
			sb.append(", ?");
		}

		sb.append(CharPool.CLOSE_PARENTHESIS);

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select portletId from Portlet where portletId in " +
					sb.toString())) {

			for (int i = 0; i < portletIds.length; i++) {
				preparedStatement.setString(i + 1, portletIds[i]);
			}

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return true;
				}

				return false;
			}
		}
	}

}