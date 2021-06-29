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

package com.liferay.commerce.initializer.util;

import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;

import java.math.BigDecimal;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = CommercePriceEntriesImporter.class)
public class CommercePriceEntriesImporter {

	public void importBaseCommercePriceListEntries(
			CommerceCatalog commerceCatalog, List<CPDefinition> cpDefinitions,
			String priceListType)
		throws PortalException {

		CommercePricingConfiguration commercePricingConfiguration =
			_configurationProvider.getConfiguration(
				CommercePricingConfiguration.class,
				new SystemSettingsLocator(
					CommercePricingConstants.SERVICE_NAME));

		if (!Objects.equals(
				commercePricingConfiguration.commercePricingCalculationKey(),
				CommercePricingConstants.VERSION_2_0)) {

			return;
		}

		CommercePriceList catalogBaseCommercePriceList =
			_commercePriceListLocalService.
				fetchCatalogBaseCommercePriceListByType(
					commerceCatalog.getGroupId(), priceListType);

		if (catalogBaseCommercePriceList == null) {
			if (_log.isWarnEnabled()) {
				if (priceListType.equals(
						CommercePriceListConstants.TYPE_PRICE_LIST)) {

					_log.warn("Catalog base price list is not present");
				}
				else if (priceListType.equals(
							CommercePriceListConstants.TYPE_PROMOTION)) {

					_log.warn("Catalog base promotion is not present");
				}
			}

			return;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(commerceCatalog.getCompanyId());
		serviceContext.setScopeGroupId(commerceCatalog.getGroupId());
		serviceContext.setUserId(commerceCatalog.getUserId());

		for (CPDefinition cpDefinition : cpDefinitions) {
			_importBaseCommercePriceListEntries(
				cpDefinition, catalogBaseCommercePriceList, serviceContext);
		}
	}

	public void importCommercePriceEntries(
			JSONArray jsonArray, long scopeGroupId, long userId)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);

		for (int i = 0; i < jsonArray.length(); i++) {
			_importCommercePriceEntry(
				jsonArray.getJSONObject(i), serviceContext);
		}
	}

	private void _importBaseCommercePriceListEntries(
			CPDefinition cpDefinition, CommercePriceList commercePriceList,
			ServiceContext serviceContext)
		throws PortalException {

		List<CPInstance> cpInstances =
			_cpInstanceLocalService.getCPDefinitionApprovedCPInstances(
				cpDefinition.getCPDefinitionId());

		for (CPInstance cpInstance : cpInstances) {
			BigDecimal price = cpInstance.getPrice();

			if (CommercePriceListConstants.TYPE_PROMOTION.equals(
					commercePriceList.getType())) {

				price = cpInstance.getPromoPrice();
			}

			_commercePriceEntryLocalService.addCommercePriceEntry(
				cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
				commercePriceList.getCommercePriceListId(), price,
				BigDecimal.ZERO, serviceContext);
		}
	}

	private void _importCommercePriceEntry(
			JSONObject jsonObject, ServiceContext serviceContext)
		throws PortalException {

		String name = jsonObject.getString("priceList");

		String priceListExternalReferenceCode =
			FriendlyURLNormalizerUtil.normalize(name);

		CommercePriceList commercePriceList =
			_commercePriceListLocalService.fetchByExternalReferenceCode(
				priceListExternalReferenceCode, serviceContext.getCompanyId());

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"No price list found with name " + name);
		}

		String sku = jsonObject.getString("sku");

		String externalReferenceCode = FriendlyURLNormalizerUtil.normalize(sku);

		CPInstance cpInstance =
			_cpInstanceLocalService.fetchByExternalReferenceCode(
				externalReferenceCode, serviceContext.getCompanyId());

		if (cpInstance == null) {
			throw new NoSuchCPInstanceException(
				"No cpInstance found with sku " + sku);
		}

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceList.getCommercePriceListId(),
				cpInstance.getCPInstanceUuid());

		if (commercePriceEntry != null) {
			return;
		}

		CPDefinition cpDefinition = _cpDefinitionLocalService.fetchCPDefinition(
			cpInstance.getCPDefinitionId());

		double price = jsonObject.getDouble("price", 0);
		double promoPrice = jsonObject.getDouble("promoPrice", 0);

		_commercePriceEntryLocalService.addCommercePriceEntry(
			cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(),
			BigDecimal.valueOf(price), BigDecimal.valueOf(promoPrice),
			serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceEntriesImporter.class);

	@Reference
	private CommercePriceEntryLocalService _commercePriceEntryLocalService;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private UserLocalService _userLocalService;

}