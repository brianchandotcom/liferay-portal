/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.web.internal.upgrade.v1_0_2;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Roberto Díaz
 */
public class PermissionUpgradeProcess extends UpgradeProcess {

	public PermissionUpgradeProcess() {
		this(false);
	}

	public PermissionUpgradeProcess(
		boolean ignoreMissingAddEntryResourceAction) {

		_ignoreMissingAddEntryResourceAction =
			ignoreMissingAddEntryResourceAction;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_addAnnouncementsAdminResourceActions();

		_upgradeAlertsResourcePermission();
		_upgradeAnnouncementsResourcePermission();
	}

	private void _addAnnouncementsAdminResourceActions() {
		_addResourceAction(
			ActionKeys.ACCESS_IN_CONTROL_PANEL,
			_BITWISE_VALUE_ACCESS_IN_CONTROL_PANEL);
		_addResourceAction(ActionKeys.VIEW, _BITWISE_VALUE_VIEW);
	}

	private void _addAnnouncementsAdminViewResourcePermission(
			long companyId, int scope, String primKey, long primKeyId,
			long roleId)
		throws Exception {

		String key = _getKey(companyId, scope, primKey, roleId);

		if (_resourcePermissions.contains(key)) {
			return;
		}

		_resourcePermissions.add(key);

		long resourcePermissionId = increment(
			ResourcePermission.class.getName());

		long actionBitwiseValue =
			_BITWISE_VALUE_VIEW | _BITWISE_VALUE_ACCESS_IN_CONTROL_PANEL;

		String name =
			"com_liferay_announcements_web_portlet_AnnouncementsAdminPortlet";
		long ownerId = 0;

		String sql = StringBundler.concat(
			"insert into ResourcePermission (mvccVersion, ",
			"resourcePermissionId, companyId, name, scope, primKey, ",
			"primKeyId, roleId, ownerId, actionIds, viewActionId) values (?, ",
			"?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				sql)) {

			preparedStatement.setLong(1, 0);
			preparedStatement.setLong(2, resourcePermissionId);
			preparedStatement.setLong(3, companyId);
			preparedStatement.setString(4, name);
			preparedStatement.setInt(5, scope);
			preparedStatement.setString(6, primKey);
			preparedStatement.setLong(7, primKeyId);
			preparedStatement.setLong(8, roleId);
			preparedStatement.setLong(9, ownerId);
			preparedStatement.setLong(10, actionBitwiseValue);
			preparedStatement.setBoolean(11, true);

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add resource permission", exception);
			}
		}
	}

	private void _addResourceAction(String actionId, long bitwiseValue) {
		long resourceActionId = increment(ResourceAction.class.getName());

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into ResourceAction (mvccVersion, ",
					"resourceActionId, name, actionId, bitwiseValue) values ",
					"(?, ?, ?, ?, ?)"))) {

			preparedStatement.setLong(1, 0);
			preparedStatement.setLong(2, resourceActionId);
			preparedStatement.setString(
				3,
				"com_liferay_announcements_web_portlet_" +
					"AnnouncementsAdminPortlet");
			preparedStatement.setString(4, actionId);
			preparedStatement.setLong(5, bitwiseValue);

			preparedStatement.executeUpdate();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add resource action", exception);
			}
		}
	}

	private void _deleteResourceAction(long resourceActionId)
		throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from ResourceAction where resourceActionId = ?")) {

			preparedStatement.setLong(1, resourceActionId);

			preparedStatement.executeUpdate();
		}
	}

	private String _getKey(
		long companyId, int scope, String primKey, long roleId) {

		return StringBundler.concat(
			companyId, StringPool.PERIOD, scope, StringPool.PERIOD, primKey,
			StringPool.PERIOD, roleId);
	}

	private void _updateResourcePermission(
			long resourcePermissionId, long bitwiseValue)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update ResourcePermission set actionIds = ? where " +
					"resourcePermissionId = ?")) {

			preparedStatement.setLong(1, bitwiseValue);
			preparedStatement.setLong(2, resourcePermissionId);

			preparedStatement.executeUpdate();
		}
	}

	private void _upgradeAlertsResourcePermission() throws Exception {
		_upgradeResourcePermission(
			"com_liferay_announcements_web_portlet_AlertsPortlet");
	}

	private void _upgradeAnnouncementsResourcePermission() throws Exception {
		_upgradeResourcePermission(
			"com_liferay_announcements_web_portlet_AnnouncementsPortlet");
	}

	private void _upgradeResourcePermission(String name) throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				StringBundler.concat(
					"select resourceActionId, bitwiseValue from ",
					"ResourceAction where actionId = 'ADD_ENTRY' and name = '",
					name, "'"));
			ResultSet resultSet1 = preparedStatement1.executeQuery()) {

			if (!resultSet1.next()) {
				if (!_ignoreMissingAddEntryResourceAction) {
					_log.error(
						StringBundler.concat(
							"Unable to upgrade ADD_ENTRY action, ",
							"ResourceAction for ", name,
							" is not initialized"));
				}

				return;
			}

			long bitwiseValue = resultSet1.getLong("bitwiseValue");

			try (PreparedStatement preparedStatement2 =
					connection.prepareStatement(
						StringBundler.concat(
							"select resourcePermissionId, companyId, scope, ",
							"primKey, primKeyId, roleId, actionIds from ",
							"ResourcePermission where name = '", name, "'"));
				ResultSet resultSet = preparedStatement2.executeQuery()) {

				while (resultSet.next()) {
					long actionIds = resultSet.getLong("actionIds");

					if ((bitwiseValue & actionIds) == 0) {
						continue;
					}

					long resourcePermissionId = resultSet.getLong(
						"resourcePermissionId");
					long companyId = resultSet.getLong("companyId");
					int scope = resultSet.getInt("scope");
					String primKey = resultSet.getString("primKey");
					long primKeyId = resultSet.getLong("primKeyId");

					_updateResourcePermission(
						resourcePermissionId, actionIds - bitwiseValue);

					if (scope == ResourceConstants.SCOPE_INDIVIDUAL) {
						if (primKey.contains("_LAYOUT_")) {
							primKey = String.valueOf(companyId);
							primKeyId = companyId;
							scope = ResourceConstants.SCOPE_COMPANY;
						}
						else {
							continue;
						}
					}

					long roleId = resultSet.getLong("roleId");

					_addAnnouncementsAdminViewResourcePermission(
						companyId, scope, primKey, primKeyId, roleId);
				}
			}

			long resourceActionId = resultSet1.getLong("resourceActionId");

			_deleteResourceAction(resourceActionId);
		}
	}

	/**
	 * @see com.liferay.portal.service.impl.ResourceActionLocalServiceImpl#checkResourceActions
	 */
	private static final long _BITWISE_VALUE_ACCESS_IN_CONTROL_PANEL = 1 << 1;

	private static final long _BITWISE_VALUE_VIEW = 1;

	private static final Log _log = LogFactoryUtil.getLog(
		PermissionUpgradeProcess.class);

	private final boolean _ignoreMissingAddEntryResourceAction;
	private final Set<String> _resourcePermissions = new HashSet<>();

}