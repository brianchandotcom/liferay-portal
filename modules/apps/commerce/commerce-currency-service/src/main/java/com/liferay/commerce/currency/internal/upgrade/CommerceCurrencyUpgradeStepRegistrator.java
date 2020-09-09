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

package com.liferay.commerce.currency.internal.upgrade;

import com.liferay.commerce.currency.internal.upgrade.v1_1_0.CommerceCurrencyUpgradeProcess;
import com.liferay.commerce.currency.internal.upgrade.v1_2_0.CommerceCurrencySymbolUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceCurrencyUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("COMMERCE CURRENCY UPGRADE STEP REGISTRATOR STARTED");
		}

		registry.register(
			"1.0.0", "1.1.0", new CommerceCurrencyUpgradeProcess());

		registry.register(
			"1.1.0", "1.2.0", new CommerceCurrencySymbolUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info("COMMERCE CURRENCY UPGRADE STEP REGISTRATOR FINISHED");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceCurrencyUpgradeStepRegistrator.class);

}