/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.model.CPDAvailabilityEstimate;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.product.model.CPConfigurationEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPConfigurationEntryService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.service.CPDAvailabilityEstimateService;
import com.liferay.commerce.service.CPDefinitionInventoryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductConfiguration;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
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

		ProductConfiguration productConfiguration = new ProductConfiguration();

		if (FeatureFlagManagerUtil.isEnabled("LPD-10889")) {
			CPConfigurationEntry cpConfigurationEntry;

			if (dtoConverterContext.getId() != null) {
				CPDefinition cpDefinition =
					_cpDefinitionService.getCPDefinition(
						(Long)dtoConverterContext.getId());

				cpConfigurationEntry =
					cpDefinition.fetchMasterCPConfigurationEntry();
			}
			else {
				ProductConfigurationDTOConverterContext
					productConfigurationDTOConverterContext =
						(ProductConfigurationDTOConverterContext)
							dtoConverterContext;

				cpConfigurationEntry =
					_cpConfigurationEntryService.getCPConfigurationEntry(
						productConfigurationDTOConverterContext.
							getCPConfigurationEntryId());
			}

			if (cpConfigurationEntry == null) {
				return productConfiguration;
			}

			productConfiguration.setActions(dtoConverterContext::getActions);
			productConfiguration.setAllowBackOrder(
				cpConfigurationEntry::isBackOrders);
			productConfiguration.setAllowedOrderQuantities(
				cpConfigurationEntry::getAllowedOrderQuantitiesArray);
			productConfiguration.setEntityExternalReferenceCode(
				() -> _getEntityExternalReferenceCode(cpConfigurationEntry));
			productConfiguration.setEntityId(cpConfigurationEntry::getClassPK);
			productConfiguration.setExternalReferenceCode(
				cpConfigurationEntry::getExternalReferenceCode);
			productConfiguration.setId(
				cpConfigurationEntry::getCPConfigurationEntryId);
			productConfiguration.setInventoryEngine(
				cpConfigurationEntry::getCPDefinitionInventoryEngine);
			productConfiguration.setLowStockAction(
				cpConfigurationEntry::getLowStockActivity);
			productConfiguration.setMaxOrderQuantity(
				() -> BigDecimalUtil.stripTrailingZeros(
					cpConfigurationEntry.getMaxOrderQuantity()));
			productConfiguration.setMinOrderQuantity(
				() -> BigDecimalUtil.stripTrailingZeros(
					cpConfigurationEntry.getMinOrderQuantity()));
			productConfiguration.setMinStockQuantity(
				() -> BigDecimalUtil.stripTrailingZeros(
					cpConfigurationEntry.getMinStockQuantity()));
			productConfiguration.setMultipleOrderQuantity(
				() -> BigDecimalUtil.stripTrailingZeros(
					cpConfigurationEntry.getMultipleOrderQuantity()));
		}
		else {
			CPDAvailabilityEstimate cpdAvailabilityEstimate =
				_cpdAvailabilityEstimateService.
					fetchCPDAvailabilityEstimateByCPDefinitionId(
						(Long)dtoConverterContext.getId());
			CPDefinitionInventory cpDefinitionInventory =
				_cpDefinitionInventoryService.
					fetchCPDefinitionInventoryByCPDefinitionId(
						(Long)dtoConverterContext.getId());

			if ((cpdAvailabilityEstimate == null) &&
				(cpDefinitionInventory == null)) {

				return productConfiguration;
			}

			if (cpdAvailabilityEstimate != null) {
				productConfiguration.setAvailabilityEstimateId(
					cpdAvailabilityEstimate::getCommerceAvailabilityEstimateId);
				productConfiguration.setAvailabilityEstimateName(
					() -> {
						CommerceAvailabilityEstimate
							commerceAvailabilityEstimate =
								cpdAvailabilityEstimate.
									getCommerceAvailabilityEstimate();

						if (commerceAvailabilityEstimate == null) {
							return null;
						}

						return LanguageUtils.getLanguageIdMap(
							commerceAvailabilityEstimate.getTitleMap());
					});
			}

			if (cpDefinitionInventory != null) {
				productConfiguration.setAllowBackOrder(
					cpDefinitionInventory::isBackOrders);
				productConfiguration.setAllowedOrderQuantities(
					cpDefinitionInventory::getAllowedOrderQuantitiesArray);
				productConfiguration.setInventoryEngine(
					cpDefinitionInventory::getCPDefinitionInventoryEngine);
				productConfiguration.setLowStockAction(
					cpDefinitionInventory::getLowStockActivity);
				productConfiguration.setMaxOrderQuantity(
					() -> BigDecimalUtil.stripTrailingZeros(
						cpDefinitionInventory.getMaxOrderQuantity()));
				productConfiguration.setMinOrderQuantity(
					() -> BigDecimalUtil.stripTrailingZeros(
						cpDefinitionInventory.getMinOrderQuantity()));
				productConfiguration.setMinStockQuantity(
					() -> BigDecimalUtil.stripTrailingZeros(
						cpDefinitionInventory.getMinStockQuantity()));
				productConfiguration.setMultipleOrderQuantity(
					() -> BigDecimalUtil.stripTrailingZeros(
						cpDefinitionInventory.getMultipleOrderQuantity()));
			}
		}

		return productConfiguration;
	}

	private String _getEntityExternalReferenceCode(
			CPConfigurationEntry cpConfigurationEntry)
		throws Exception {

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpConfigurationEntry.getClassPK());

		CProduct cProduct = cpDefinition.getCProduct();

		return cProduct.getExternalReferenceCode();
	}

	@Reference
	private CPConfigurationEntryService _cpConfigurationEntryService;

	@Reference
	private CPDAvailabilityEstimateService _cpdAvailabilityEstimateService;

	@Reference
	private CPDefinitionInventoryService _cpDefinitionInventoryService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

}