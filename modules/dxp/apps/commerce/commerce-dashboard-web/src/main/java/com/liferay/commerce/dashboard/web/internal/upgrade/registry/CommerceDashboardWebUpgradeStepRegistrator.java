/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.dashboard.web.internal.upgrade.registry;

import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.dashboard.web.internal.upgrade.v1_0_1.CommerceDashboardForecastsChartPortletPreferencesUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michele Vigilante
 */
@Component(service = UpgradeStepRegistrator.class)
public class CommerceDashboardWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce Dashboard web upgrade step registrator started");
		}

		registry.registerInitialization();

		registry.register(
			"1.0.0", "1.0.1",
			new CommerceDashboardForecastsChartPortletPreferencesUpgradeProcess(
				_assetCategoryLocalService));

		if (_log.isInfoEnabled()) {
			_log.info(
				"Commerce Dashboard web upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDashboardWebUpgradeStepRegistrator.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

}