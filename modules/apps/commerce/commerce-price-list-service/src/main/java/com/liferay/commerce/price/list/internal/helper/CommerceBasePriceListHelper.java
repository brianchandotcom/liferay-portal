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

package com.liferay.commerce.price.list.internal.helper;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.pricing.configuration.CommercePricingConfiguration;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(enabled = false, service = CommerceBasePriceListHelper.class)
public class CommerceBasePriceListHelper {

	public void addCatalogBaseCommercePriceList(CommerceCatalog commerceCatalog)
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

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(commerceCatalog.getGroupId());
		serviceContext.setUserId(commerceCatalog.getUserId());
		serviceContext.setCompanyId(commerceCatalog.getCompanyId());

		_addCatalogBaseCommercePriceList(
			commerceCatalog, CommercePriceListConstants.TYPE_PRICE_LIST,
			LanguageUtil.format(
				LocaleUtil.fromLanguageId(
					commerceCatalog.getCatalogDefaultLanguageId()),
				"x-base-price-list", commerceCatalog.getName(), false),
			serviceContext);
		_addCatalogBaseCommercePriceList(
			commerceCatalog, CommercePriceListConstants.TYPE_PROMOTION,
			LanguageUtil.format(
				LocaleUtil.fromLanguageId(
					commerceCatalog.getCatalogDefaultLanguageId()),
				"x-base-promotion", commerceCatalog.getName(), false),
			serviceContext);
	}

	public void deleteCatalogBaseCommercePriceList(
			CommerceCatalog commerceCatalog)
		throws PortalException {

		_deleteCatalogBaseCommercePriceList(
			commerceCatalog, CommercePriceListConstants.TYPE_PRICE_LIST);

		_deleteCatalogBaseCommercePriceList(
			commerceCatalog, CommercePriceListConstants.TYPE_PROMOTION);
	}

	private void _addCatalogBaseCommercePriceList(
			CommerceCatalog commerceCatalog, String type, String name,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePriceList catalogBaseCommercePriceList =
			_commercePriceListLocalService.
				fetchCatalogBaseCommercePriceListByType(
					commerceCatalog.getGroupId(), type);

		if (catalogBaseCommercePriceList == null) {
			CommerceCurrency commerceCurrency =
				_commerceCurrencyLocalService.getCommerceCurrency(
					serviceContext.getCompanyId(),
					commerceCatalog.getCommerceCurrencyCode());

			_commercePriceListLocalService.addCatalogBaseCommercePriceList(
				commerceCatalog.getGroupId(), serviceContext.getUserId(),
				commerceCurrency.getCommerceCurrencyId(), type, name,
				serviceContext);
		}
	}

	private void _deleteCatalogBaseCommercePriceList(
			CommerceCatalog commerceCatalog, String type)
		throws PortalException {

		CommercePriceList catalogBaseCommercePriceList =
			_commercePriceListLocalService.
				fetchCatalogBaseCommercePriceListByType(
					commerceCatalog.getGroupId(), type);

		if (catalogBaseCommercePriceList != null) {
			_commercePriceListLocalService.forceDeleteCommercePriceList(
				catalogBaseCommercePriceList);
		}
	}

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

	@Reference
	private ConfigurationProvider _configurationProvider;

}