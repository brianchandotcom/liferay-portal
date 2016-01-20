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

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Cristina González
 */
public class UpgradeDLDisplayPortlet
	extends com.liferay.portal.upgrade.util.UpgradePortletId {

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {_DL_DISPLAY_PORTLET, _DOCUMENT_LIBRARY_PORTLET}
		};
	}

	@Override
	protected void updatePortlet(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		try {
			runSQL(
				"delete from Portlet where portletId = '" +
					_DL_DISPLAY_PORTLET + "'");

			_deleteDuplicatedResourceActions();

			_deleteDuplicatedResourcePermissions();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		super.updatePortlet(oldRootPortletId, newRootPortletId);
	}

	private void _deleteDuplicatedResourceActions() throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select actionId from ResourceAction where name = '" +
					_DOCUMENT_LIBRARY_PORTLET + "'");

			rs = ps.executeQuery();

			while (rs.next()) {
				PreparedStatement ps2 = null;

				try {
					ps2 = connection.prepareStatement(
						"delete from ResourceAction where name = ? " +
							"and actionId = ?");

					ps2.setString(1, _DL_DISPLAY_PORTLET);
					ps2.setString(2, rs.getString("actionId"));

					ps2.execute();
				}
				finally {
					DataAccess.cleanUp(ps2);
				}
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	private void _deleteDuplicatedResourcePermissions() throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(
				"select companyId, scope, primKey " +
					"from ResourcePermission where name = '" +
						_DOCUMENT_LIBRARY_PORTLET + "'");

			rs = ps.executeQuery();

			while (rs.next()) {
				PreparedStatement ps2 = null;

				try {
					ps2 = connection.prepareStatement(
						"delete from ResourcePermission where name = ? " +
							"and companyId = ? and scope = ? and primKey = ?");

					ps2.setString(1, _DL_DISPLAY_PORTLET);
					ps2.setString(2, rs.getString("companyId"));
					ps2.setString(3, rs.getString("scope"));
					ps2.setString(4, rs.getString("primKey"));

					ps2.execute();
				}
				finally {
					DataAccess.cleanUp(ps2);
				}
			}
		}
		finally {
			DataAccess.cleanUp(ps, rs);
		}
	}

	private static final String _DL_DISPLAY_PORTLET = "100";

	private static final String _DOCUMENT_LIBRARY_PORTLET = "20";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDLDisplayPortlet.class);

}