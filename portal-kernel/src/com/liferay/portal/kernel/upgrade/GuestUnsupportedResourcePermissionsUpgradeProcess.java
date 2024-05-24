/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Michael Bowerman
 */
public class GuestUnsupportedResourcePermissionsUpgradeProcess
	extends UpgradeProcess {

	public GuestUnsupportedResourcePermissionsUpgradeProcess(
		String resourceName, String... guestUnsupportedResourceActionIds) {

		_resourceName = resourceName;
		_guestUnsupportedResourceActionIds = guestUnsupportedResourceActionIds;
	}

	@Override
	protected void doUpgrade() throws Exception {
		long bitmask = _getBitmask();

		CompanyLocalServiceUtil.forEachCompanyId(
			companyId -> _removeGuestUnsupportedResourcePermissions(
				bitmask, companyId));
	}

	private long _getBitmask() throws Exception {
		long bitmask = 0xFFFFFFFFFFFFFFFFL;

		StringBundler sb = new StringBundler(
			2 + _guestUnsupportedResourceActionIds.length);

		sb.append("select bitwiseValue from ResourceAction where name = ? ");
		sb.append("and (");

		for (int i = 1; i < _guestUnsupportedResourceActionIds.length; i++) {
			sb.append("actionId = ? or ");
		}

		sb.append("actionId = ?)");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sb.toString())) {

			preparedStatement.setString(1, _resourceName);

			for (int i = 0; i < _guestUnsupportedResourceActionIds.length;
				 i++) {

				preparedStatement.setString(
					i + 2, _guestUnsupportedResourceActionIds[i]);
			}

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					bitmask &= ~resultSet.getLong("bitwiseValue");
				}
			}
		}

		return bitmask;
	}

	private long _getGuestRoleId(long companyId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select ctCollectionId, roleId from Role_ where companyId = " +
					"? and name = ?")) {

			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(2, RoleConstants.GUEST);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (!resultSet.next()) {
					throw new IllegalStateException(
						"Unable to find Guest role for company " + companyId);
				}

				return resultSet.getLong("roleId");
			}
		}
	}

	private void _removeGuestUnsupportedResourcePermissions(
			long bitmask, long companyId)
		throws Exception {

		long guestRoleId = _getGuestRoleId(companyId);

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select resourcePermissionId, ctCollectionId, actionIds from " +
					"ResourcePermission where companyId = ? and name = ? and " +
						"roleId = ?")) {

			preparedStatement1.setLong(1, companyId);
			preparedStatement1.setString(2, _resourceName);
			preparedStatement1.setLong(3, guestRoleId);

			try (ResultSet resultSet = preparedStatement1.executeQuery();
				PreparedStatement preparedStatement2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"update ResourcePermission set actionIds = ? where " +
							"resourcePermissionId = ? and ctCollectionId = " +
								"?")) {

				while (resultSet.next()) {
					preparedStatement2.setLong(
						1, resultSet.getLong("actionIds") & bitmask);
					preparedStatement2.setLong(
						2, resultSet.getLong("resourcePermissionId"));
					preparedStatement2.setLong(
						3, resultSet.getLong("ctCollectionId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}

		if (!ArrayUtil.contains(
				_guestUnsupportedResourceActionIds, ActionKeys.VIEW)) {

			return;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update ResourcePermission set viewActionId = 0 where " +
					"companyId = ? and name = ? and roleId = ?")) {

			preparedStatement.setLong(1, companyId);
			preparedStatement.setString(2, _resourceName);
			preparedStatement.setLong(3, guestRoleId);

			preparedStatement.executeUpdate();
		}
	}

	private final String[] _guestUnsupportedResourceActionIds;
	private final String _resourceName;

}