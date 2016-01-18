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

package com.liferay.dynamic.data.mapping.upgrade;

import com.liferay.dynamic.data.mapping.upgrade.v1_0_0.UpgradeClassNames;
import com.liferay.dynamic.data.mapping.upgrade.v1_0_0.UpgradeCompanyId;
import com.liferay.dynamic.data.mapping.upgrade.v1_0_0.UpgradeDynamicDataMapping;
import com.liferay.dynamic.data.mapping.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.dynamic.data.mapping.upgrade.v1_0_0.UpgradeSchema;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.service.ResourceActionLocalService;
import com.liferay.portal.service.ResourcePermissionLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class DDMServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.dynamic.data.mapping.service", "0.0.1", "1.0.0",
			new UpgradeSchema(), new UpgradeClassNames(),
			new UpgradeCompanyId(),
			new UpgradeDynamicDataMapping(
				_assetEntryLocalService, _dlFileEntryLocalService,
				_dlFileVersionLocalService, _dlFolderLocalService,
				_resourceActionLocalService, _resourcePermissionLocalService),
			new UpgradeLastPublishDate());
	}

	@Reference(unbind = "-")
	public void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setDlFileVersionLocalService(
		DLFileVersionLocalService dlFileVersionLocalService) {

		_dlFileVersionLocalService = dlFileVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setDlFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		_dlFolderLocalService = dlFolderLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setResourceActionLocalService(
		ResourceActionLocalService resourceActionLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private DLFileEntryLocalService _dlFileEntryLocalService;
	private DLFileVersionLocalService _dlFileVersionLocalService;
	private DLFolderLocalService _dlFolderLocalService;
	private ResourceActionLocalService _resourceActionLocalService;
	private ResourcePermissionLocalService _resourcePermissionLocalService;

}