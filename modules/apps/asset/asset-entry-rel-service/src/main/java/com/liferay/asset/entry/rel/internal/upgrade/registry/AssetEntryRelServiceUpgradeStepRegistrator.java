/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.entry.rel.internal.upgrade.registry;

import com.liferay.asset.entry.rel.internal.upgrade.v1_0_0.AssetEntryAssetCategoryRelUpgradeProcess;
import com.liferay.asset.entry.rel.internal.upgrade.v2_0_0.UpgradeCompanyId;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = UpgradeStepRegistrator.class)
public class AssetEntryRelServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.1", "1.0.0", new AssetEntryAssetCategoryRelUpgradeProcess());

		registry.register(
			"1.0.0", "1.1.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getTableNames() {
					return new String[] {"AssetEntryAssetCategoryRel"};
				}

			});

		registry.register("1.1.0", "2.0.0", new UpgradeCompanyId());

		registry.register(
			"2.0.0", "2.1.0",
			new CTModelUpgradeProcess("AssetEntryAssetCategoryRel"));

		registry.register(
			"2.1.0", "3.0.0",
			UpgradeProcessFactory.dropTables("AssetEntries_AssetCategories"));
	}

}