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

package com.liferay.asset.display.page.internal.upgrade;

import com.liferay.asset.display.page.internal.upgrade.v2_0_0.util.AssetDisplayPageEntryTable;
import com.liferay.asset.display.page.internal.upgrade.v2_1_0.UpgradeAssetDisplayLayout;
import com.liferay.asset.display.page.internal.upgrade.v2_1_1.UpgradeAssetDisplayPrivateLayout;
import com.liferay.asset.display.page.internal.upgrade.v2_2_1.UpgradeAssetDisplayLayoutFriendlyURLPrivateLayout;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.BaseUpgradeSQLServerDatetime;
import com.liferay.portal.kernel.upgrade.UpgradeMVCCVersion;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Ángel Jiménez
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class AssetDisplayPageServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0",
			new BaseUpgradeSQLServerDatetime(
				new Class<?>[] {AssetDisplayPageEntryTable.class}));

		registry.register(
			"2.0.0", "2.1.0",
			new UpgradeAssetDisplayLayout(
				_assetEntryLocalService, _layoutLocalService,
				_layoutPageTemplateEntryLocalService,
				_layoutPageTemplateEntryService));

		registry.register(
			"2.1.0", "2.1.1",
			new UpgradeAssetDisplayPrivateLayout(
				_layoutLocalService, _resourceLocalService));

		registry.register(
			"2.1.1", "2.2.0",
			new UpgradeMVCCVersion() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {"AssetDisplayPageEntry"};
				}

			});

		registry.register(
			"2.2.0", "2.2.1",
			new UpgradeAssetDisplayLayoutFriendlyURLPrivateLayout());

		registry.register(
			"2.2.1", "2.2.2",
			new UpgradeAssetDisplayLayoutFriendlyURLPrivateLayout());
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryService _layoutPageTemplateEntryService;

	@Reference
	private ResourceLocalService _resourceLocalService;

}