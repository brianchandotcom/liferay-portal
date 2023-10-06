/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.model.CPDAvailabilityEstimate;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.service.CPDAvailabilityEstimateLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductConfiguration;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.model.CPDefinitionInventory",
	service = DTOConverter.class
)
public class ProductConfigurationDTOConverter
	implements DTOConverter<CPDefinitionInventory, ProductConfiguration> {

	@Override
	public String getContentType() {
		return ProductConfiguration.class.getSimpleName();
	}

	@Override
	public ProductConfiguration toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					(Long)dtoConverterContext.getId());

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			(Long)dtoConverterContext.getId());

		CPDAvailabilityEstimate cpdAvailabilityEstimate =
			_cpdAvailabilityEstimateLocalService.
				fetchCPDAvailabilityEstimateByCProductId(
					cpDefinition.getCProductId());

		if ((cpDefinitionInventory == null) &&
			(cpdAvailabilityEstimate == null)) {

			return new ProductConfiguration();
		}

		return new ProductConfiguration() {
			{
				setAllowBackOrder(
					() -> {
						if (cpDefinitionInventory == null) {
							return null;
						}

						return cpDefinitionInventory.isBackOrders();
					});
				setAllowedOrderQuantities(
					() -> {
						if (cpDefinitionInventory == null) {
							return null;
						}

						return cpDefinitionInventory.
							getAllowedOrderQuantitiesArray();
					});
				setAvailabilityEstimateId(
					() -> {
						if (cpdAvailabilityEstimate == null) {
							return null;
						}

						return cpdAvailabilityEstimate.
							getCommerceAvailabilityEstimateId();
					});
				setAvailabilityEstimateName(
					() -> {
						if (cpdAvailabilityEstimate == null) {
							return null;
						}

						CommerceAvailabilityEstimate
							commerceAvailabilityEstimate =
								cpdAvailabilityEstimate.
									getCommerceAvailabilityEstimate();

						if (commerceAvailabilityEstimate == null) {
							return null;
						}

						return commerceAvailabilityEstimate.getTitle(
							dtoConverterContext.getLocale());
					});
				setInventoryEngine(
					() -> {
						if (cpDefinitionInventory == null) {
							return null;
						}

						return cpDefinitionInventory.
							getCPDefinitionInventoryEngine();
					});
				setMaxOrderQuantity(
					() -> {
						if (cpDefinitionInventory == null) {
							return null;
						}

						return BigDecimalUtil.stripTrailingZeros(
							cpDefinitionInventory.getMaxOrderQuantity());
					});
				setMinOrderQuantity(
					() -> {
						if (cpDefinitionInventory == null) {
							return null;
						}

						return BigDecimalUtil.stripTrailingZeros(
							cpDefinitionInventory.getMinOrderQuantity());
					});
				setMultipleOrderQuantity(
					() -> {
						if (cpDefinitionInventory == null) {
							return null;
						}

						return BigDecimalUtil.stripTrailingZeros(
							cpDefinitionInventory.getMultipleOrderQuantity());
					});
			}
		};
	}

	@Reference
	private CPDAvailabilityEstimateLocalService
		_cpdAvailabilityEstimateLocalService;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

}