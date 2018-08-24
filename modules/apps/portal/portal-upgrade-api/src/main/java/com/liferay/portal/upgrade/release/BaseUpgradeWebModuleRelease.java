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
		try (Connection con = DataAccess.getConnection()) {
			if (hasAnyPortletId(con, getPortletIds())) {
				super.upgrade();
			}
		}
		catch (SQLException sqle) {
			throw new UpgradeException(sqle);
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

		try (PreparedStatement ps = connection.prepareStatement(
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

}