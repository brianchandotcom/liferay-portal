/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.upgrade.registry;

import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.search.tuning.rankings.web.internal.upgrade.v2_0_0.RenameRankingUpgradeProcess;
import com.liferay.portal.search.tuning.rankings.web.internal.upgrade.v3_0_0.JSONStorageEntryRankingUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Almir Ferreira
 */
@Component(service = UpgradeStepRegistrator.class)
public class PortalSearchTuningRankingsWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register("0.0.1", "1.0.0", new DummyUpgradeStep());

		registry.register(
			"1.0.0", "2.0.0",
			new RenameRankingUpgradeProcess(_classNameLocalService));

		registry.register(
			"2.0.0", "3.0.0",
			new JSONStorageEntryRankingUpgradeProcess(_classNameLocalService));
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

}