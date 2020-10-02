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

package com.liferay.commerce.pricing.web.internal.frontend;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.commerce.pricing.web.internal.frontend.constants.CommercePricingDataSetConstants;
import com.liferay.commerce.pricing.web.internal.model.TierPriceEntry;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.text.DateFormat;
import java.text.Format;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_TIER_PRICE_ENTRIES,
	service = ClayDataSetDataProvider.class
)
public class CommerceTierPriceEntryDataSetDataProvider
	implements ClayDataSetDataProvider<TierPriceEntry> {

	@Override
	public List<TierPriceEntry> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<TierPriceEntry> tierPriceEntries = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Format dateTimeFormat = FastDateFormatFactoryUtil.getDateTime(
			DateFormat.MEDIUM, DateFormat.MEDIUM, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());

		long commercePriceEntryId = ParamUtil.getLong(
			httpServletRequest, "commercePriceEntryId");

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryService.getCommercePriceEntry(
				commercePriceEntryId);

		CommercePriceList commercePriceList =
			commercePriceEntry.getCommercePriceList();

		BaseModelSearchResult<CommerceTierPriceEntry>
			commerceTierPriceEntryBaseModelSearchResult =
				_commerceTierPriceEntryService.searchCommerceTierPriceEntries(
					_portal.getCompanyId(httpServletRequest),
					commercePriceEntryId, filter.getKeywords(),
					pagination.getStartPosition(), pagination.getEndPosition(),
					sort);

		for (CommerceTierPriceEntry commerceTierPriceEntry :
				commerceTierPriceEntryBaseModelSearchResult.getBaseModels()) {

			CommerceMoney priceCommerceMoney =
				commerceTierPriceEntry.getPriceMoney(
					commercePriceList.getCommerceCurrencyId());

			tierPriceEntries.add(
				new TierPriceEntry(
					_getDiscountLevels(commerceTierPriceEntry),
					_getEndDate(commerceTierPriceEntry, dateTimeFormat),
					_getOverride(commerceTierPriceEntry, httpServletRequest),
					priceCommerceMoney.format(themeDisplay.getLocale()),
					commerceTierPriceEntry.getMinQuantity(),
					dateTimeFormat.format(
						commerceTierPriceEntry.getDisplayDate()),
					commerceTierPriceEntry.getCommerceTierPriceEntryId()));
		}

		return tierPriceEntries;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commercePriceEntryId = ParamUtil.getLong(
			httpServletRequest, "commercePriceEntryId");

		return _commerceTierPriceEntryService.
			searchCommerceTierPriceEntriesCount(
				_portal.getCompanyId(httpServletRequest), commercePriceEntryId,
				filter.getKeywords());
	}

	private String _getDiscountLevels(
		CommerceTierPriceEntry commerceTierPriceEntry) {

		if (commerceTierPriceEntry.isDiscountDiscovery()) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(7);

		sb.append(commerceTierPriceEntry.getDiscountLevel1());
		sb.append(" - ");
		sb.append(commerceTierPriceEntry.getDiscountLevel2());
		sb.append(" - ");
		sb.append(commerceTierPriceEntry.getDiscountLevel3());
		sb.append(" - ");
		sb.append(commerceTierPriceEntry.getDiscountLevel4());

		return sb.toString();
	}

	private String _getEndDate(
		CommerceTierPriceEntry commerceTierPriceEntry, Format dateTimeFormat) {

		if (commerceTierPriceEntry.getExpirationDate() == null) {
			return StringPool.BLANK;
		}

		return dateTimeFormat.format(
			commerceTierPriceEntry.getExpirationDate());
	}

	private String _getOverride(
		CommerceTierPriceEntry commerceTierPriceEntry,
		HttpServletRequest httpServletRequest) {

		if (commerceTierPriceEntry.isDiscountDiscovery()) {
			return LanguageUtil.get(httpServletRequest, "no");
		}

		return LanguageUtil.get(httpServletRequest, "yes");
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

	@Reference
	private Portal _portal;

}