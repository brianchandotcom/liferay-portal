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

package com.liferay.calendar.internal.upgrade.v1_0_6;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PermissionedModel;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceBlockConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceBlockLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.security.permission.ResourceActionsImpl;
import com.liferay.portal.util.PropsValues;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

/**
 * @author José María Muñoz
 * @author Alberto Chaparro
 */
public class UpgradeResourcePermission extends UpgradeProcess {

	public UpgradeResourcePermission(
		ResourceActionLocalService resourceActionLocalService,
		ResourceBlockLocalService resourceBlockLocalService,
		RoleLocalService roleLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
		_resourceBlockLocalService = resourceBlockLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeGuestResourceBlockPermissions();
	}

	protected long getCalendarResourceUnsupportedActionsBitwiseValue()
		throws PortalException {

		int unsupportedBitwiseValue = 0;

		List<String> guestUnsupportedActions =
			getModelResourceGuestUnsupportedActions();

		for (String resourceActionId : _NEW_UNSUPPORTED_ACTION_IDS) {
			if (guestUnsupportedActions.contains(resourceActionId)) {
				ResourceAction resourceAction =
					_resourceActionLocalService.getResourceAction(
						_CALENDAR_RESOURCE_NAME, resourceActionId);

				unsupportedBitwiseValue |= resourceAction.getBitwiseValue();
			}
		}

		return unsupportedBitwiseValue;
	}

	protected List<String> getModelResourceGuestUnsupportedActions()
		throws UpgradeException {

		try {
			ResourceActionsImpl resourceActionsImpl = new ResourceActionsImpl();

			Class<?> clazz = getClass();

			ClassLoader classLoader = clazz.getClassLoader();

			for (String config : PropsValues.RESOURCE_ACTIONS_CONFIGS) {
				resourceActionsImpl.read(null, classLoader, config);
			}

			return resourceActionsImpl.getModelResourceGuestUnsupportedActions(
				_CALENDAR_RESOURCE_NAME);
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void upgradeGuestResourceBlockPermissions() throws Exception {
		long unsupportedBitwiseValue =
			getCalendarResourceUnsupportedActionsBitwiseValue();

		if (unsupportedBitwiseValue == 0) {
			return;
		}

		StringBundler sb = new StringBundler(2);

		sb.append("select companyId, groupId, calendarResourceId, ");
		sb.append("resourceBlockId from CalendarResource");

		try (PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong(1);
				long groupId = rs.getLong(2);
				final long calendarResourceId = rs.getLong(3);
				final long resourceBlockId = rs.getLong(4);

				Role guestRole = _roleLocalService.getRole(
					companyId, RoleConstants.GUEST);

				PermissionedModel permissionedModel = new PermissionedModel() {

					@Override
					public long getResourceBlockId() {
						return resourceBlockId;
					}

					@Override
					public void persist() {
						if (newResourceBlockId == -1) {
							return;
						}

						StringBundler sbUpdate = new StringBundler(3);

						sbUpdate.append("update CalendarResource set ");
						sbUpdate.append("resourceBlockId = ? where ");
						sbUpdate.append("calendarResourceId = ?");

						try (PreparedStatement ps = connection.prepareStatement(
								sbUpdate.toString())) {

							ps.setLong(1, newResourceBlockId);
							ps.setLong(2, calendarResourceId);

							ps.execute();
						}
						catch (SQLException sqle) {
							throw new SystemException(sqle);
						}
					}

					@Override
					public void setResourceBlockId(long resourceBlockId) {
						newResourceBlockId = resourceBlockId;
					}

					protected long newResourceBlockId = -1;

				};

				_resourceBlockLocalService.updateIndividualScopePermissions(
					companyId, groupId, _CALENDAR_RESOURCE_NAME,
					permissionedModel, guestRole.getRoleId(),
					unsupportedBitwiseValue,
					ResourceBlockConstants.OPERATOR_REMOVE);
			}
		}
	}

	private static final String _CALENDAR_RESOURCE_NAME =
		"com.liferay.calendar.model.CalendarResource";

	private static final String[] _NEW_UNSUPPORTED_ACTION_IDS =
		{ActionKeys.PERMISSIONS, ActionKeys.VIEW};

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourceBlockLocalService _resourceBlockLocalService;
	private final RoleLocalService _roleLocalService;

}