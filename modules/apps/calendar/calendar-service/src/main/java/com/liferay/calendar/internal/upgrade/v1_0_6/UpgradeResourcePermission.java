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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PermissionedModel;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.security.permission.ResourceActionsImpl;
import com.liferay.portal.util.PropsValues;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author José María Muñoz
 * @author Alberto Chaparro
 */
public class UpgradeResourcePermission extends UpgradeProcess {

	public UpgradeResourcePermission(
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		upgradeGuestResourceBlockPermissions();
	}

	protected List<String> getCalendarResourceUnsupportedActionIds()
		throws PortalException {

		List<String> actionIds = new ArrayList<>();

		List<String> guestUnsupportedActions =
			getModelResourceGuestUnsupportedActions();

		for (String resourceActionId : _NEW_UNSUPPORTED_ACTION_IDS) {
			if (guestUnsupportedActions.contains(resourceActionId)) {
				ResourceAction resourceAction =
					_resourceActionLocalService.getResourceAction(
						_CALENDAR_RESOURCE_NAME, resourceActionId);

				actionIds.add(resourceAction.getActionId());
			}
		}

		return actionIds;
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
		List<String> unsupportedActionIds =
			getCalendarResourceUnsupportedActionIds();

		if (unsupportedActionIds.isEmpty()) {
			return;
		}

		StringBundler sb = new StringBundler(2);

		sb.append("select companyId, groupId, calendarResourceId, ");
		sb.append("resourceBlockId from CalendarResource");

		try (PreparedStatement ps = connection.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				long companyId = rs.getLong(1);
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
						if (_newResourceBlockId == -1) {
							return;
						}

						StringBundler updateSB = new StringBundler(3);

						updateSB.append("update CalendarResource set ");
						updateSB.append("resourceBlockId = ? where ");
						updateSB.append("calendarResourceId = ?");

						try (PreparedStatement ps = connection.prepareStatement(
								updateSB.toString())) {

							ps.setLong(1, _newResourceBlockId);
							ps.setLong(2, calendarResourceId);

							ps.execute();
						}
						catch (SQLException sqle) {
							throw new SystemException(sqle);
						}
					}

					@Override
					public void setResourceBlockId(long resourceBlockId) {
						_newResourceBlockId = resourceBlockId;
					}

					private long _newResourceBlockId = -1;

				};

				for (String unsupportedActionId : unsupportedActionIds) {
					_resourcePermissionLocalService.removeResourcePermission(
						companyId, _CALENDAR_RESOURCE_NAME,
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(permissionedModel),
						guestRole.getRoleId(), unsupportedActionId);
				}
			}
		}
	}

	private static final String _CALENDAR_RESOURCE_NAME =
		"com.liferay.calendar.model.CalendarResource";

	private static final String[] _NEW_UNSUPPORTED_ACTION_IDS = {
		ActionKeys.PERMISSIONS, ActionKeys.VIEW
	};

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}