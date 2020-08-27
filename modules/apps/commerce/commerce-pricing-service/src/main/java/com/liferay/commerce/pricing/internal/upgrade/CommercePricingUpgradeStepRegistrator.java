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

package com.liferay.commerce.pricing.internal.upgrade;

import com.liferay.commerce.pricing.internal.upgrade.v1_1_0.CommercePricingClassUpgradeProcess;
import com.liferay.commerce.pricing.internal.upgrade.v2_0_1.CommercePriceModifierUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class CommercePricingUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("COMMERCE PRICING UPGRADE STEP REGISTRATOR STARTED");
		}

		registry.register(
			_SCHEMA_VERSION_1_0_0, _SCHEMA_VERSION_1_1_0,
			new CommercePricingClassUpgradeProcess());

		registry.register(
			_SCHEMA_VERSION_1_1_0, _SCHEMA_VERSION_2_0_0,
			new com.liferay.commerce.pricing.internal.upgrade.v2_0_0.
				CommercePricingClassUpgradeProcess(
					_resourceActionLocalService, _resourceLocalService));

		registry.register(
			_SCHEMA_VERSION_2_0_0, _SCHEMA_VERSION_2_0_1,
			new CommercePriceModifierUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info("COMMERCE PRICING UPGRADE STEP REGISTRATOR FINISHED");
		}
	}

	private static final String _SCHEMA_VERSION_1_0_0 = "1.0.0";

	private static final String _SCHEMA_VERSION_1_1_0 = "1.1.0";

	private static final String _SCHEMA_VERSION_2_0_0 = "2.0.0";

	private static final String _SCHEMA_VERSION_2_0_1 = "2.0.1";

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePricingUpgradeStepRegistrator.class);

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

}