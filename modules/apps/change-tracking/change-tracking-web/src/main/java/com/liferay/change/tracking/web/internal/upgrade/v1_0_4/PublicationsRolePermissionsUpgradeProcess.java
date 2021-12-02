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

package com.liferay.change.tracking.web.internal.upgrade.v1_0_4;

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.web.internal.constants.PublicationRoleConstants;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleTable;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.List;

/**
 * @author Cheryl Tang
 */
public class PublicationsRolePermissionsUpgradeProcess extends UpgradeProcess {

	public PublicationsRolePermissionsUpgradeProcess(
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<Role> inviterRoles = _roleLocalService.dslQuery(
			DSLQueryFactoryUtil.select(
				RoleTable.INSTANCE
			).from(
				RoleTable.INSTANCE
			).where(
				RoleTable.INSTANCE.name.eq(_NAME_INVITER)
			));

		for (Role inviterRole : inviterRoles) {
			_resourcePermissionLocalService.addResourcePermission(
				inviterRole.getCompanyId(), CTCollection.class.getName(),
				ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
				inviterRole.getRoleId(), CTActionKeys.PUBLISH);
		}

		List<Role> publisherRoles = _roleLocalService.dslQuery(
			DSLQueryFactoryUtil.select(
				RoleTable.INSTANCE
			).from(
				RoleTable.INSTANCE
			).where(
				RoleTable.INSTANCE.name.eq(
					PublicationRoleConstants.NAME_PUBLISHER)
			));

		for (Role publisherRole : publisherRoles) {
			_resourcePermissionLocalService.removeResourcePermission(
				publisherRole.getCompanyId(), CTCollection.class.getName(),
				ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
				publisherRole.getRoleId(), ActionKeys.PERMISSIONS);
		}
	}

	private static final String _NAME_INVITER =
		"com_liferay_change_tracking_web_portlet_PublicationsPortlet.inviter";

	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}