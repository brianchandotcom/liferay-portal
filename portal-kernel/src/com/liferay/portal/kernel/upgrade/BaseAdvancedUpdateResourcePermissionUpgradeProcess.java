/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upgrade;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Jürgen Kappler
 */
public abstract class BaseAdvancedUpdateResourcePermissionUpgradeProcess
	extends UpgradeProcess {

	public abstract String getResourcePermissionName();

	@Override
	protected void doUpgrade() throws Exception {
		String resourcePermissionName = getResourcePermissionName();

		List<ResourceAction> resourceActions =
			resourceActionLocalService.getResourceActions(
				resourcePermissionName);

		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select ctCollectionId, resourcePermissionId, actionIds from " +
					"ResourcePermission where name = ?")) {

			preparedStatement1.setString(1, resourcePermissionName);

			try (PreparedStatement preparedStatement2 =
					AutoBatchPreparedStatementUtil.concurrentAutoBatch(
						connection,
						"update ResourcePermission set actionIds = ? where " +
							"ctCollectionId = ? and resourcePermissionId = ?");
				ResultSet resultSet = preparedStatement1.executeQuery()) {

				while (resultSet.next()) {
					List<String> actionIds = _getResourceActionIds(
						resultSet.getLong("actionIds"), resourceActions);

					if (ListUtil.isEmpty(actionIds) ||
						actionIds.contains(ActionKeys.ADVANCED_UPDATE) ||
						!actionIds.contains(ActionKeys.UPDATE)) {

						continue;
					}

					actionIds.add(ActionKeys.ADVANCED_UPDATE);

					preparedStatement2.setLong(
						1,
						_getActionIdsLong(actionIds, resourcePermissionName));
					preparedStatement2.setLong(
						2, resultSet.getLong("ctCollectionId"));
					preparedStatement2.setLong(
						3, resultSet.getLong("resourcePermissionId"));

					preparedStatement2.addBatch();
				}

				preparedStatement2.executeBatch();
			}
		}
	}

	protected ResourceActionLocalService resourceActionLocalService;

	private long _getActionIdsLong(
			List<String> actionIds, String resourcePermissionName)
		throws PortalException {

		long actionIdsLong = 0;

		for (String actionId : actionIds) {
			if (actionId == null) {
				break;
			}

			ResourceAction resourceAction =
				resourceActionLocalService.getResourceAction(
					resourcePermissionName, actionId);

			actionIdsLong |= resourceAction.getBitwiseValue();
		}

		return actionIdsLong;
	}

	private List<String> _getResourceActionIds(
		long actionIds, List<ResourceAction> resourceActions) {

		return TransformUtil.transform(
			resourceActions,
			resourceAction -> {
				long bitwiseValue = resourceAction.getBitwiseValue();

				if ((actionIds & bitwiseValue) != bitwiseValue) {
					return null;
				}

				return resourceAction.getActionId();
			});
	}

}