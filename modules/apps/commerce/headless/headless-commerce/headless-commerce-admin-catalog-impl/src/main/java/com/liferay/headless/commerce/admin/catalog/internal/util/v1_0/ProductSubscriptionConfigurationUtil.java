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

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductSubscriptionConfiguration;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class ProductSubscriptionConfigurationUtil {

	public static CPDefinition updateCPDefinitionSubscriptionInfo(
			CPDefinitionService cpDefinitionService,
			ProductSubscriptionConfiguration productSubscriptionConfiguration,
			CPDefinition cpDefinition, ServiceContext serviceContext)
		throws PortalException {

		String subscriptionTypeValue = null;

		ProductSubscriptionConfiguration.SubscriptionType subscriptionType =
			productSubscriptionConfiguration.getSubscriptionType();

		if (subscriptionType != null) {
			subscriptionTypeValue = subscriptionType.getValue();
		}

		return cpDefinitionService.updateSubscriptionInfo(
			cpDefinition.getCPDefinitionId(),
			GetterUtil.get(
				productSubscriptionConfiguration.getEnable(),
				cpDefinition.isSubscriptionEnabled()),
			GetterUtil.get(
				productSubscriptionConfiguration.getLength(),
				cpDefinition.getSubscriptionLength()),
			subscriptionTypeValue, null,
			GetterUtil.get(
				productSubscriptionConfiguration.getNumberOfLength(),
				cpDefinition.getMaxSubscriptionCycles()),
			serviceContext);
	}

}