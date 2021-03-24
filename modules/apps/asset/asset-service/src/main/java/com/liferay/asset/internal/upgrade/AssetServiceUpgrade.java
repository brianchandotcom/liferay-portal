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

package com.liferay.asset.internal.upgrade;

import com.liferay.asset.internal.upgrade.v1_0_0.AssetEntryUsageUpgradeProcess;
import com.liferay.asset.internal.upgrade.v2_0_0.UpgradeCompanyId;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.kernel.upgrade.ViewCountUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.view.count.service.ViewCountEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class AssetServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.1", "1.0.0", new AssetEntryUsageUpgradeProcess());

		registry.register(
			"1.0.0", "1.1.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {"AssetEntryUsage"};
				}

			});

		registry.register(
			"1.1.0", "2.0.0", new UpgradeCompanyId(),
			new ViewCountUpgradeProcess(
				"AssetEntry", AssetEntry.class, "entryId", "viewCount"));

		registry.register("2.0.0", "2.0.1", new DummyUpgradeStep());

		registry.register(
			"2.0.1", "2.1.0", new CTModelUpgradeProcess("AssetEntryUsage"));
	}

	/**
	 * See LPS-101084. The ViewCount table needs to exist.
	 */
	@Reference
	private ViewCountEntryLocalService _viewCountEntryLocalService;

}