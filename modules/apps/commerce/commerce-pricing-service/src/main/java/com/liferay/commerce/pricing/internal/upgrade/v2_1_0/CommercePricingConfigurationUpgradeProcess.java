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

package com.liferay.commerce.pricing.internal.upgrade.v2_1_0;

import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.Dictionary;
import java.util.Hashtable;

/**
 * @author Riccardo Alberti
 */
public class CommercePricingConfigurationUpgradeProcess extends UpgradeProcess {

	public CommercePricingConfigurationUpgradeProcess(
		ConfigurationProvider configurationProvider) {

		_configurationProvider = configurationProvider;
	}

	@Override
	protected void doUpgrade() throws Exception {
		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			"commercePricingCalculationKey",
			CommercePricingConstants.VERSION_2_0);

		_configurationProvider.saveSystemConfiguration(
			CommercePricingConfiguration.class, properties);
	}

	private final ConfigurationProvider _configurationProvider;

}