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

package com.liferay.commerce.pricing.web.internal.display.context;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.CustomAttributesUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTierCommercePriceEntryDisplayContext
	extends BaseCommercePriceListDisplayContext {

	public CommerceTierCommercePriceEntryDisplayContext(
		CommerceCatalogService commerceCatalogService,
		CommercePriceEntryService commercePriceEntryService,
		ModelResourcePermission<CommercePriceList>
			commercePriceListModelResourcePermission,
		CommercePriceListService commercePriceListService,
		CommerceTierPriceEntryService commerceTierPriceEntryService,
		HttpServletRequest httpServletRequest) {

		super(
			commerceCatalogService, commercePriceListModelResourcePermission,
			commercePriceListService, httpServletRequest);

		_commercePriceEntryService = commercePriceEntryService;
		_commerceTierPriceEntryService = commerceTierPriceEntryService;
	}

	public CommercePriceEntry getCommercePriceEntry() throws PortalException {
		long commercePriceEntryId = ParamUtil.getLong(
			commercePricingRequestHelper.getRequest(), "commercePriceEntryId");

		if (commercePriceEntryId > 0) {
			return _commercePriceEntryService.getCommercePriceEntry(
				commercePriceEntryId);
		}

		return null;
	}

	public long getCommercePriceEntryId() throws PortalException {
		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry == null) {
			return 0;
		}

		return commercePriceEntry.getCommercePriceEntryId();
	}

	public CommerceCurrency getCommercePriceListCurrency()
		throws PortalException {

		CommercePriceList commercePriceList = getCommercePriceList();

		return commercePriceList.getCommerceCurrency();
	}

	public CommerceTierPriceEntry getCommerceTierPriceEntry()
		throws PortalException {

		if (_commerceTierPriceEntry != null) {
			return _commerceTierPriceEntry;
		}

		long commerceTierPriceEntryId = ParamUtil.getLong(
			commercePricingRequestHelper.getRequest(),
			"commerceTierPriceEntryId");

		if (commerceTierPriceEntryId > 0) {
			_commerceTierPriceEntry =
				_commerceTierPriceEntryService.getCommerceTierPriceEntry(
					commerceTierPriceEntryId);
		}

		return _commerceTierPriceEntry;
	}

	public long getCommerceTierPriceEntryId() throws PortalException {
		CommerceTierPriceEntry commerceTierPriceEntry =
			getCommerceTierPriceEntry();

		if (commerceTierPriceEntry == null) {
			return 0;
		}

		return commerceTierPriceEntry.getCommerceTierPriceEntryId();
	}

	public String getCommerceTierPriceEntryPrice(
			CommerceTierPriceEntry commerceTierPriceEntry)
		throws PortalException {

		if (commerceTierPriceEntry == null) {
			CommerceCurrency commerceCurrency = getCommercePriceListCurrency();

			CommerceMoney zeroCommerceMoney = commerceCurrency.getZero();

			return zeroCommerceMoney.format(
				commercePricingRequestHelper.getLocale());
		}

		CommercePriceList commercePriceList = getCommercePriceList();

		CommerceMoney priceCommerceMoney =
			commerceTierPriceEntry.getPriceCommerceMoney(
				commercePriceList.getCommerceCurrencyId());

		return priceCommerceMoney.format(
			commercePricingRequestHelper.getLocale());
	}

	public String getContextTitle() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(5);

		CommercePriceList commercePriceList = getCommercePriceList();

		if (commercePriceList != null) {
			sb.append(commercePriceList.getName());
		}

		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry != null) {
			CPInstance cpInstance = commercePriceEntry.getCPInstance();

			if (cpInstance != null) {
				CPDefinition cpDefinition = cpInstance.getCPDefinition();

				if (cpDefinition != null) {
					sb.append(" - ");
					sb.append(
						cpDefinition.getName(themeDisplay.getLanguageId()));
					sb.append(" - ");
					sb.append(cpInstance.getSku());
				}
			}
		}

		CommerceTierPriceEntry commerceTierPriceEntry =
			getCommerceTierPriceEntry();

		String contextTitle = sb.toString();

		if (commerceTierPriceEntry == null) {
			contextTitle = LanguageUtil.format(
				themeDisplay.getRequest(), "add-tier-price-entry-to-x",
				contextTitle);
		}

		return contextTitle;
	}

	public boolean hasCustomAttributes() throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return CustomAttributesUtil.hasCustomAttributes(
			themeDisplay.getCompanyId(), CommerceTierPriceEntry.class.getName(),
			getCommerceTierPriceEntryId(), null);
	}

	private final CommercePriceEntryService _commercePriceEntryService;
	private CommerceTierPriceEntry _commerceTierPriceEntry;
	private final CommerceTierPriceEntryService _commerceTierPriceEntryService;

}