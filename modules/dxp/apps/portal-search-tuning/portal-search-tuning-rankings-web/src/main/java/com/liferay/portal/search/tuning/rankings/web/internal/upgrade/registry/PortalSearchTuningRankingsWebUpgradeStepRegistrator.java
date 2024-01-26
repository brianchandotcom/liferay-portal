/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.upgrade.registry;

import com.liferay.portal.kernel.service.ClassNameLocalService;
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
		registry.register(
			"0.0.0", "0.0.1",
			new com.liferay.portal.search.tuning.rankings.web.internal.upgrade.
				v0_0_1.RankingUpgradeProcess(_classNameLocalService));

		registry.register(
			"0.0.1", "1.0.0",
			new com.liferay.portal.search.tuning.rankings.web.internal.upgrade.
				v1_0_0.RankingUpgradeProcess(_classNameLocalService));
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

}