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

package com.liferay.commerce.pricing.web.internal.util;

import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;

/**
 * @author Riccardo Alberti
 */
public class CommercePricingUtil {

	public static String getPricingEngineVersion(
			ConfigurationProvider configurationProvider)
		throws ConfigurationException {

		CommercePricingConfiguration commercePricingConfiguration =
			configurationProvider.getConfiguration(
				CommercePricingConfiguration.class,
				new SystemSettingsLocator(
					CommercePricingConstants.SERVICE_NAME));

		return commercePricingConfiguration.commercePricingCalculationKey();
	}

}