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

package com.liferay.change.tracking.web.internal.upgrade;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.web.internal.upgrade.v1_0_2.PublicationsUserRoleUpgradeProcess;
import com.liferay.change.tracking.web.internal.upgrade.v1_0_3.PublicationsConfigurationPortletUpgradeProcess;
import com.liferay.change.tracking.web.internal.upgrade.v1_0_4.PublicationsRolePermissionsUpgradeProcess;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class PublicationsWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.0", "1.0.4", new DummyUpgradeStep());

		registry.register(
			"0.0.1", "1.0.1",
			new BasePortletIdUpgradeProcess() {

				@Override
				protected String[][] getRenamePortletIdsArray() {
					return new String[][] {
						{
							"com_liferay_change_tracking_web_portlet_" +
								"ChangeListsPortlet",
							CTPortletKeys.PUBLICATIONS
						},
						{
							"com_liferay_change_tracking_web_portlet_" +
								"ChangeListsConfigurationPortlet",
							"com_liferay_change_tracking_web_portlet_" +
								"PublicationsConfigurationPortlet"
						}
					};
				}

			});

		registry.register(
			"1.0.1", "1.0.2",
			new PublicationsUserRoleUpgradeProcess(
				_resourceActions, _resourcePermissionLocalService,
				_roleLocalService, _userLocalService));

		registry.register(
			"1.0.2", "1.0.3",
			new PublicationsConfigurationPortletUpgradeProcess(
				_resourceActionLocalService, _resourcePermissionLocalService));

		registry.register(
			"1.0.3", "1.0.4",
			new PublicationsRolePermissionsUpgradeProcess(
				_resourcePermissionLocalService, _roleLocalService));
	}

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceActions _resourceActions;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}