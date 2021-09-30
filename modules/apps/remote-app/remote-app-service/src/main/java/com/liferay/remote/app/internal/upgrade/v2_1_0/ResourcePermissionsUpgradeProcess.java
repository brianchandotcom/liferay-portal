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

package com.liferay.remote.app.internal.upgrade.v2_1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.remote.app.model.RemoteAppEntry;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author Javier de Arcos
 */
public class ResourcePermissionsUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() {
		_insertResourcePermissions();
	}

	private void _insertResourcePermissions() {
		try (Statement s = connection.createStatement();
			ResultSet resultSet = s.executeQuery(
				"select mvccVersion, remoteAppEntryId, companyId, userId " +
					"from RemoteAppEntry");
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.autoBatch(
					connection.prepareStatement(
						StringBundler.concat(
							"insert into ResourcePermission (mvccVersion, ",
							"resourcePermissionId, companyId, name, scope, ",
							"primKey, primKeyId, roleId, ownerId, actionIds, ",
							"viewActionId) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ",
							"?, ?), (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")))) {

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
				preparedStatement.setString(4, RemoteAppEntry.class.getName());
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
				preparedStatement.setString(15, RemoteAppEntry.class.getName());
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
				_log.debug(exception, exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcePermissionsUpgradeProcess.class);

}