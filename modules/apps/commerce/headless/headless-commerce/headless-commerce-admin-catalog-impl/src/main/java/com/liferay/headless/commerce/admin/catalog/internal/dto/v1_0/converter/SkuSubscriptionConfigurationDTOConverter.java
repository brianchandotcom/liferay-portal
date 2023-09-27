/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					cpInstance.
						getDeliverySubscriptionTypeSettingsUnicodeProperties();
				enable = cpInstance.isSubscriptionEnabled();
				length = cpInstance.getSubscriptionLength();
				numberOfLength = cpInstance.getMaxSubscriptionCycles();
				overrideSubscriptionInfo =
					cpInstance.isOverrideSubscriptionInfo();
				subscriptionType = SubscriptionType.create(
					cpInstance.getSubscriptionType());
				subscriptionTypeSettings =
					cpInstance.getSubscriptionTypeSettingsUnicodeProperties();
			}
		};
	}

	@Reference
	private CPInstanceService _cpInstanceService;

}