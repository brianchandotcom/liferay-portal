/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.internal.upgrade.v2_1_0;

import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
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