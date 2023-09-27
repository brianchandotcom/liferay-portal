/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Cristina González
 */
public class UpgradeDocumentLibraryPortletId
	extends BasePortletIdUpgradeProcess {

	protected void deleteDuplicateResourceActions() throws SQLException {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select actionId from ResourceAction where name = '" +
					_PORTLET_ID_DOCUMENT_LIBRARY + "'");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				try (PreparedStatement preparedStatement2 =
						connection.prepareStatement(
							"delete from ResourceAction where name = ? and " +
								"actionId = ?")) {

					preparedStatement2.setString(1, _PORTLET_ID_DL_DISPLAY);
					preparedStatement2.setString(
						2, resultSet.getString("actionId"));

					preparedStatement2.execute();
				}
			}
		}
	}

	protected void deleteDuplicateResourcePermissions() throws SQLException {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select companyId, scope, primKey from ResourcePermission ",
					"where name = '", _PORTLET_ID_DOCUMENT_LIBRARY, "'"));
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				try (PreparedStatement preparedStatement2 =
						connection.prepareStatement(
							StringBundler.concat(
								"delete from ResourcePermission where ",
								"companyId = ? and name = ? and scope = ? and ",
								"primKey = ?"))) {

					preparedStatement2.setLong(
						1, resultSet.getLong("companyId"));
					preparedStatement2.setString(2, _PORTLET_ID_DL_DISPLAY);
					preparedStatement2.setInt(3, resultSet.getInt("scope"));
					preparedStatement2.setString(
						4, resultSet.getString("primKey"));

					preparedStatement2.execute();
				}
			}
		}
	}

	@Override
	protected String[][] getRenamePortletIdsArray() {
		return new String[][] {
			{_PORTLET_ID_DL_DISPLAY, _PORTLET_ID_DOCUMENT_LIBRARY}
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
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		super.updatePortlet(oldRootPortletId, newRootPortletId);
	}

	private static final String _PORTLET_ID_DL_DISPLAY = "110";

	private static final String _PORTLET_ID_DOCUMENT_LIBRARY = "20";

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradeDocumentLibraryPortletId.class);

}