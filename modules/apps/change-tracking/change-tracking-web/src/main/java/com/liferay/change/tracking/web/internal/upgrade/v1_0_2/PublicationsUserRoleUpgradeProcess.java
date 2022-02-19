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

package com.liferay.change.tracking.web.internal.upgrade.v1_0_2;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Samuel Trong Tran
 */
public class PublicationsUserRoleUpgradeProcess extends UpgradeProcess {

	public PublicationsUserRoleUpgradeProcess(
		ResourceActions resourceActions,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService, UserLocalService userLocalService) {

		_resourceActions = resourceActions;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_resourceActions.populatePortletResources(
			PublicationsUserRoleUpgradeProcess.class.getClassLoader(),
			"resource-actions/default.xml");

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select CTPreferences.companyId from CTPreferences where " +
					"CTPreferences.userId = 0");
			ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				long companyId = resultSet.getLong(1);

				Role role = _roleLocalService.fetchRole(
					companyId, RoleConstants.PUBLICATIONS_USER);

				if (role == null) {
					long defaultUserId = _userLocalService.getDefaultUserId(
						companyId);

					role = _roleLocalService.addRole(
						defaultUserId, null, 0, RoleConstants.PUBLICATIONS_USER,
						null,
						HashMapBuilder.put(
							LocaleUtil.fromLanguageId(
								UpgradeProcessUtil.getDefaultLanguageId(
									companyId)),
							PropsUtil.get(
								StringBundler.concat(
									"system.role.",
									StringUtil.replace(
										RoleConstants.PUBLICATIONS_USER,
										CharPool.SPACE, CharPool.PERIOD),
									".description"))
						).build(),
						RoleConstants.TYPE_REGULAR, null, null);
				}

				_resourcePermissionLocalService.addResourcePermission(
					companyId, PortletKeys.PORTAL,
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					role.getRoleId(), ActionKeys.VIEW_CONTROL_PANEL);
				_resourcePermissionLocalService.addResourcePermission(
					companyId, CTPortletKeys.PUBLICATIONS,
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					role.getRoleId(), ActionKeys.ACCESS_IN_CONTROL_PANEL);
				_resourcePermissionLocalService.addResourcePermission(
					companyId, CTPortletKeys.PUBLICATIONS,
					ResourceConstants.SCOPE_COMPANY, String.valueOf(companyId),
					role.getRoleId(), ActionKeys.VIEW);
			}
		}
	}

	private final ResourceActions _resourceActions;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;
	private final UserLocalService _userLocalService;

}