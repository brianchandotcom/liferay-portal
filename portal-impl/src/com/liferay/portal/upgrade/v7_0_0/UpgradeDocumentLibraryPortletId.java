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
public class UpgradeDocumentLibraryPortletId
	extends com.liferay.portal.upgrade.util.UpgradePortletId {

	protected void deleteDuplicateResourceActions() throws SQLException {
		PreparedStatement ps1 = null;
		ResultSet rs = null;

		try {
			ps1 = connection.prepareStatement(
				"select actionId from ResourceAction where name = '" +
					_PORTLET_ID_DOCUMENT_LIBRARY + "'");

			rs = ps1.executeQuery();

			while (rs.next()) {
				PreparedStatement ps2 = null;

				try {
					ps2 = connection.prepareStatement(
						"delete from ResourceAction where name = ? and " +
							"actionId = ?");

					ps2.setString(1, _PORTLET_ID_DL_DISPLAY);
					ps2.setString(2, rs.getString("actionId"));

					ps2.execute();
				}
				finally {
					DataAccess.cleanUp(ps2);
				}
			}
		}
		finally {
			DataAccess.cleanUp(ps1, rs);
		}
	}

	protected void deleteDuplicateResourcePermissions() throws SQLException {
		PreparedStatement ps1 = null;
		ResultSet rs = null;

		try {
			ps1 = connection.prepareStatement(
				"select companyId, scope, primKey from ResourcePermission " +
					"where name = '" + _PORTLET_ID_DOCUMENT_LIBRARY + "'");

			rs = ps1.executeQuery();

			while (rs.next()) {
				PreparedStatement ps2 = null;

				try {
					ps2 = connection.prepareStatement(
						"delete from ResourcePermission where companyId = ? " +
							"and name = ? and scope = ? and primKey = ?");

					ps2.setLong(1, rs.getLong("companyId"));
					ps2.setString(2, _PORTLET_ID_DL_DISPLAY);
					ps2.setInt(3, rs.getInt("scope"));
					ps2.setString(4, rs.getString("primKey"));

					ps2.execute();
				}
				finally {
					DataAccess.cleanUp(ps2);
				}
			}
		}
		finally {
			DataAccess.cleanUp(ps1, rs);
		}
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			new String[] {_PORTLET_ID_DL_DISPLAY, _PORTLET_ID_DOCUMENT_LIBRARY}
		};
	}

	@Override
	protected void updatePortlet(
			String oldRootPortletId, String newRootPortletId)
		throws Exception {

		try {
			runSQL(
				"delete from Portlet where portletId = '" +
					_PORTLET_ID_DL_DISPLAY + "'");

			deleteDuplicateResourceActions();
			deleteDuplicateResourcePermissions();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		super.updatePortlet(oldRootPortletId, newRootPortletId);
	}

	private static final String _PORTLET_ID_DL_DISPLAY = "100";

	private static final String _PORTLET_ID_DOCUMENT_LIBRARY = "20";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibraryPortletId.class);

}