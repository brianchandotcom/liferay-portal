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

package com.liferay.change.tracking.web.internal.upgrade.v1_0_3;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.ResourcePermissionTable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.List;

/**
 * @author Preston Crary
 */
public class PublicationsConfigurationPortletUpgradeProcess
	extends UpgradeProcess {

	public PublicationsConfigurationPortletUpgradeProcess(
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		List<ResourceAction> resourceActions =
			_resourceActionLocalService.getResourceActions(
				_PUBLICATIONS_CONFIGURATION_PORTLET_ID);

		for (ResourceAction resourceAction : resourceActions) {
			_upgradeResourcePermission(resourceAction);

			_resourceActionLocalService.deleteResourceAction(resourceAction);
		}
	}

	private void _upgradeResourcePermission(ResourceAction resourceAction)
		throws Exception {

		if (!ActionKeys.CONFIGURATION.equals(resourceAction.getName())) {
			return;
		}

		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.dslQuery(
				DSLQueryFactoryUtil.select(
					ResourcePermissionTable.INSTANCE
				).from(
					ResourcePermissionTable.INSTANCE
				).where(
					ResourcePermissionTable.INSTANCE.companyId.eq(
						CompanyThreadLocal.getCompanyId()
					).and(
						ResourcePermissionTable.INSTANCE.name.eq(
							_PUBLICATIONS_CONFIGURATION_PORTLET_ID)
					).and(
						ResourcePermissionTable.INSTANCE.scope.neq(
							ResourceConstants.SCOPE_INDIVIDUAL)
					).and(
						DSLFunctionFactoryUtil.bitAnd(
							ResourcePermissionTable.INSTANCE.actionIds,
							resourceAction.getBitwiseValue()
						).eq(
							resourceAction.getBitwiseValue()
						)
					)
				));

		for (ResourcePermission resourcePermission : resourcePermissions) {
			String primKey = resourcePermission.getPrimKey();

			if (primKey.equals(_PUBLICATIONS_CONFIGURATION_PORTLET_ID)) {
				primKey = CTPortletKeys.PUBLICATIONS;
			}

			_resourcePermissionLocalService.addResourcePermission(
				resourcePermission.getCompanyId(), CTPortletKeys.PUBLICATIONS,
				resourcePermission.getScope(), primKey,
				resourcePermission.getRoleId(), ActionKeys.CONFIGURATION);
		}
	}

	private static final String _PUBLICATIONS_CONFIGURATION_PORTLET_ID =
		"com_liferay_change_tracking_web_portlet_" +
			"PublicationsConfigurationPortlet";

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}