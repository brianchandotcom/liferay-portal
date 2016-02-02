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

package com.liferay.exportimport.upgrade;

import com.liferay.exportimport.upgrade.v1_0_0.UpgradePublisherRequest;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portlet.exportimport.service.ExportImportConfigurationLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(
	immediate = true,
	service = {ExportImportServiceUpgrade.class, UpgradeStepRegistrator.class}
)
public class ExportImportServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.exportimport.service", "0.0.1", "1.0.0",
			new UpgradePublisherRequest(
				_exportImportConfigurationLocalService, _groupLocalService,
				_schedulerEngineHelper, _userLocalService));
	}

	@Reference(unbind = "-")
	protected void setExportImportConfigurationLocalService(
		ExportImportConfigurationLocalService
			exportImportConfigurationLocalService) {

		_exportImportConfigurationLocalService =
			exportImportConfigurationLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setSchedulerEngineHelper(
		SchedulerEngineHelper schedulerEngineHelper) {

		_schedulerEngineHelper = schedulerEngineHelper;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private ExportImportConfigurationLocalService
		_exportImportConfigurationLocalService;
	private GroupLocalService _groupLocalService;
	private SchedulerEngineHelper _schedulerEngineHelper;
	private UserLocalService _userLocalService;

}