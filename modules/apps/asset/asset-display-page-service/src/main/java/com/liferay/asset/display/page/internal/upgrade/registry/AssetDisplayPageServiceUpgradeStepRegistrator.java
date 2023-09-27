/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.internal.upgrade.registry;

import com.liferay.asset.display.page.internal.upgrade.v2_0_0.util.AssetDisplayPageEntryTable;
import com.liferay.asset.display.page.internal.upgrade.v2_1_0.AssetDisplayLayoutUpgradeProcess;
import com.liferay.asset.display.page.internal.upgrade.v2_1_1.AssetDisplayPrivateLayoutUpgradeProcess;
import com.liferay.asset.display.page.internal.upgrade.v2_2_1.AssetDisplayLayoutFriendlyURLPrivateLayoutUpgradeProcess;
import com.liferay.asset.display.page.internal.upgrade.v3_0_0.UpgradeAssetDisplayPageEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.BaseSQLServerDatetimeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author José Ángel Jiménez
 */
@Component(service = UpgradeStepRegistrator.class)
public class AssetDisplayPageServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0",
			new BaseSQLServerDatetimeUpgradeProcess(
				new Class<?>[] {AssetDisplayPageEntryTable.class}));

		registry.register(
			"2.0.0", "2.1.0",
			new AssetDisplayLayoutUpgradeProcess(
				_assetEntryLocalService, _layoutLocalService,
				_layoutPageTemplateEntryLocalService,
				_layoutPageTemplateEntryService));

		registry.register(
			"2.1.0", "2.1.1",
			new AssetDisplayPrivateLayoutUpgradeProcess(
				_layoutLocalService, _resourceLocalService));

		registry.register(
			"2.1.1", "2.2.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getTableNames() {
					return new String[] {"AssetDisplayPageEntry"};
				}

			});

		registry.register(
			"2.2.0", "2.2.1",
			new AssetDisplayLayoutFriendlyURLPrivateLayoutUpgradeProcess());

		registry.register(
			"2.2.1", "2.2.2",
			new AssetDisplayLayoutFriendlyURLPrivateLayoutUpgradeProcess());

		registry.register(
			"2.2.2", "2.3.0",
			new CTModelUpgradeProcess("AssetDisplayPageEntry"));

		registry.register("2.3.0", "2.3.1", new DummyUpgradeStep());

		registry.register(
			"2.3.1", "2.3.2",
			new com.liferay.asset.display.page.internal.upgrade.v2_3_2.
				AssetDisplayPageEntryUpgradeProcess());

		registry.register("2.3.2", "3.0.0", new UpgradeAssetDisplayPageEntry());

		registry.register("3.0.0", "3.0.1", new DummyUpgradeProcess());
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