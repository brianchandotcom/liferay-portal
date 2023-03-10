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

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuSubscriptionConfiguration;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Crescenzo Rega
 */
@Component(
	property = "dto.class.name=SkuSubscriptionConfiguration",
	service = {
		DTOConverter.class, SkuSubscriptionConfigurationDTOConverter.class
	}
)
public class SkuSubscriptionConfigurationDTOConverter
	implements DTOConverter<CPInstance, SkuSubscriptionConfiguration> {

	@Override
	public String getContentType() {
		return SkuSubscriptionConfiguration.class.getSimpleName();
	}

	@Override
	public SkuSubscriptionConfiguration toDTO(
			DTOConverterContext dtoConverterContext)
		throws Exception {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(
			(Long)dtoConverterContext.getId());

		return new SkuSubscriptionConfiguration() {
			{
				deliverySubscriptionEnable =
					cpInstance.isDeliverySubscriptionEnabled();
				deliverySubscriptionLength =
					cpInstance.getDeliverySubscriptionLength();
				deliverySubscriptionNumberOfLength =
					cpInstance.getDeliveryMaxSubscriptionCycles();
				deliverySubscriptionType = DeliverySubscriptionType.create(
					cpInstance.getDeliverySubscriptionType());
				deliverySubscriptionTypeSettings =
					cpInstance.getDeliverySubscriptionTypeSettingsProperties();
				enable = cpInstance.isSubscriptionEnabled();
				length = cpInstance.getSubscriptionLength();
				numberOfLength = cpInstance.getMaxSubscriptionCycles();
				overrideSubscriptionInfo =
					cpInstance.isOverrideSubscriptionInfo();
				subscriptionType = SubscriptionType.create(
					cpInstance.getSubscriptionType());
				subscriptionTypeSettings =
					cpInstance.getSubscriptionTypeSettingsProperties();
			}
		};
	}

	@Reference
	private CPInstanceService _cpInstanceService;

}