/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductSubscriptionConfiguration;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=ProductSubscriptionConfiguration",
	service = DTOConverter.class
)
public class ProductSubscriptionConfigurationDTOConverter
	implements DTOConverter<CPDefinition, ProductSubscriptionConfiguration> {

	@Override
	public String getContentType() {
		return ProductSubscriptionConfiguration.class.getSimpleName();
	}

	@Override
	public ProductSubscriptionConfiguration toDTO(
			DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			(Long)dtoConverterContext.getId());

		return new ProductSubscriptionConfiguration() {
			{
				deliverySubscriptionEnable =
					cpDefinition.isDeliverySubscriptionEnabled();
				deliverySubscriptionLength =
					cpDefinition.getDeliverySubscriptionLength();
				deliverySubscriptionNumberOfLength =
					cpDefinition.getDeliveryMaxSubscriptionCycles();
				deliverySubscriptionType =
					ProductSubscriptionConfiguration.DeliverySubscriptionType.
						create(cpDefinition.getDeliverySubscriptionType());
				deliverySubscriptionTypeSettings =
					cpDefinition.
						getDeliverySubscriptionTypeSettingsUnicodeProperties();
				enable = cpDefinition.isSubscriptionEnabled();
				length = cpDefinition.getSubscriptionLength();
				numberOfLength = cpDefinition.getMaxSubscriptionCycles();
				subscriptionType =
					ProductSubscriptionConfiguration.SubscriptionType.create(
						cpDefinition.getSubscriptionType());
				subscriptionTypeSettings =
					cpDefinition.getSubscriptionTypeSettingsUnicodeProperties();
			}
		};
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

}