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

package com.liferay.change.tracking.web.internal.upgrade.registry;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.web.internal.configuration.helper.CTSettingsConfigurationHelper;
import com.liferay.change.tracking.web.internal.upgrade.v1_0_3.PublicationsConfigurationPortletUpgradeProcess;
import com.liferay.change.tracking.web.internal.upgrade.v1_0_4.PublicationsRolePermissionsUpgradeProcess;
import com.liferay.change.tracking.web.internal.upgrade.v1_0_5.PublicationsAdminRoleNameUpgradeProcess;
import com.liferay.change.tracking.web.internal.upgrade.v1_0_7.PublicationsEnabledUpgradeProcess;
import com.liferay.change.tracking.web.internal.upgrade.v1_0_8.CleanUpPDFPreviewsUpgradeProcess;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(service = UpgradeStepRegistrator.class)
public class PublicationsWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

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

		registry.register("1.0.1", "1.0.2", new DummyUpgradeStep());

		registry.register(
			"1.0.2", "1.0.3",
			new com.liferay.change.tracking.web.internal.upgrade.v1_0_3.
				PublicationsUserRoleUpgradeProcess(
					_companyLocalService, _resourceActions,
					_resourcePermissionLocalService, _roleLocalService,
					_userLocalService),
			new PublicationsConfigurationPortletUpgradeProcess(
				_resourceActionLocalService, _resourcePermissionLocalService));

		registry.register(
			"1.0.3", "1.0.4",
			new PublicationsRolePermissionsUpgradeProcess(
				_resourcePermissionLocalService, _roleLocalService));

		registry.register(
			"1.0.4", "1.0.5", new PublicationsAdminRoleNameUpgradeProcess());

		registry.register(
			"1.0.5", "1.0.6",
			new com.liferay.change.tracking.web.internal.upgrade.v1_0_6.
				PublicationsUserRoleUpgradeProcess(
					_companyLocalService, _resourcePermissionLocalService,
					_roleLocalService));

		registry.register(
			"1.0.6", "1.0.7",
			new PublicationsEnabledUpgradeProcess(
				_ctPreferencesLocalService, _ctSettingsConfigurationHelper));

		registry.register(
			"1.0.7", "1.0.8",
			new CleanUpPDFPreviewsUpgradeProcess(
				_ctCollectionLocalService, _portal));
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private CTSettingsConfigurationHelper _ctSettingsConfigurationHelper;

	@Reference
	private Portal _portal;

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