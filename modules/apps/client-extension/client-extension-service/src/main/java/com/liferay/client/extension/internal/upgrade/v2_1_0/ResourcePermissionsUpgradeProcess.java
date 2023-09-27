/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.internal.upgrade.v2_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Javier de Arcos
 */
public class ResourcePermissionsUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (hasTable("RemoteAppEntry") && !_hasResourcePermissions()) {
			_insertResourcePermissions();
		}
	}

	private boolean _hasResourcePermissions() throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from ResourcePermission where name = ?")) {

			preparedStatement.setString(
				1, "com.liferay.remote.app.model.RemoteAppEntry");

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int count = resultSet.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	private void _insertResourcePermissions() {
		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				"select mvccVersion, remoteAppEntryId, companyId, userId " +
					"from RemoteAppEntry");
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection,
					StringBundler.concat(
						"insert into ResourcePermission (mvccVersion, ",
						"resourcePermissionId, companyId, name, scope, ",
						"primKey, primKeyId, roleId, ownerId, actionIds, ",
						"viewActionId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ",
						"?), (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))) {

			while (resultSet.next()) {
				long mvccVersion = resultSet.getLong("mvccVersion");
				long remoteAppEntryId = resultSet.getLong("remoteAppEntryId");
				long companyId = resultSet.getLong("companyId");
				long userId = resultSet.getLong("userId");

				Role ownerRole = RoleLocalServiceUtil.getRole(
					companyId, "Owner");
				Role guestRole = RoleLocalServiceUtil.getRole(
					companyId, "Guest");

				preparedStatement.setLong(1, mvccVersion);
				preparedStatement.setLong(2, increment());
				preparedStatement.setLong(3, companyId);
				preparedStatement.setString(
					4, "com.liferay.remote.app.model.RemoteAppEntry");
				preparedStatement.setInt(5, ResourceConstants.SCOPE_INDIVIDUAL);
				preparedStatement.setString(
					6, String.valueOf(remoteAppEntryId));
				preparedStatement.setLong(7, remoteAppEntryId);
				preparedStatement.setLong(8, ownerRole.getRoleId());
				preparedStatement.setLong(9, userId);
				preparedStatement.setLong(10, 15);
				preparedStatement.setBoolean(11, true);
				preparedStatement.setLong(12, mvccVersion);
				preparedStatement.setLong(13, increment());
				preparedStatement.setLong(14, companyId);
				preparedStatement.setString(
					15, "com.liferay.remote.app.model.RemoteAppEntry");
				preparedStatement.setInt(
					16, ResourceConstants.SCOPE_INDIVIDUAL);
				preparedStatement.setString(
					17, String.valueOf(remoteAppEntryId));
				preparedStatement.setLong(18, remoteAppEntryId);
				preparedStatement.setLong(19, guestRole.getRoleId());
				preparedStatement.setLong(20, 0);
				preparedStatement.setLong(21, 1);
				preparedStatement.setBoolean(22, true);

				preparedStatement.addBatch();
			}

			preparedStatement.executeBatch();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcePermissionsUpgradeProcess.class);

}