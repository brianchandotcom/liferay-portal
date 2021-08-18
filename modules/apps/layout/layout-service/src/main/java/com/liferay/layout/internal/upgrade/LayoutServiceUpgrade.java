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

package com.liferay.layout.internal.upgrade;

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.service.AssetEntryUsageLocalService;
import com.liferay.layout.internal.upgrade.v1_0_0.LayoutClassedModelUsageUpgradeProcess;
import com.liferay.layout.internal.upgrade.v1_0_0.LayoutPermissionsUpgradeProcess;
import com.liferay.layout.internal.upgrade.v1_0_0.LayoutUpgradeProcess;
import com.liferay.layout.internal.upgrade.v1_1_0.UpgradeCompanyId;
import com.liferay.layout.internal.upgrade.v1_2_1.LayoutAssetUpgradeProcess;
import com.liferay.layout.internal.upgrade.v1_2_2.LayoutSEOUpgradeProcess;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class LayoutServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.1", "1.0.0",
			new LayoutClassedModelUsageUpgradeProcess(
				_assetEntryLocalService, _assetEntryUsageLocalService),
			new LayoutPermissionsUpgradeProcess());

		registry.register("1.0.0", "1.0.1", new LayoutUpgradeProcess());

		registry.register("1.0.1", "1.1.0", new UpgradeCompanyId());

		registry.register(
			"1.1.0", "1.2.0",
			new CTModelUpgradeProcess("LayoutClassedModelUsage"));

		registry.register(
			"1.2.0", "1.2.1",
			new LayoutAssetUpgradeProcess(
				_assetCategoryLocalService, _assetEntryLocalService,
				_assetTagLocalService, _groupLocalService,
				_layoutLocalService));

		registry.register(
			"1.2.1", "1.2.2", new LayoutSEOUpgradeProcess(_layoutLocalService));
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetEntryUsageLocalService _assetEntryUsageLocalService;

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

}