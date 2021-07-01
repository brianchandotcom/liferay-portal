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

package com.liferay.commerce.pricing.web.internal.frontend.taglib.clay.data.set.provider;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.pricing.web.internal.frontend.constants.CommercePricingDataSetConstants;
import com.liferay.commerce.pricing.web.internal.model.InstancePriceEntry;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommercePricingDataSetConstants.COMMERCE_DATA_SET_KEY_INSTANCE_PRICE_ENTRIES,
	service = ClayDataSetDataProvider.class
)
public class CPInstancePriceEntryDataSetDataProvider
	implements ClayDataSetDataProvider<InstancePriceEntry> {

	@Override
	public List<InstancePriceEntry> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<InstancePriceEntry> instancePriceEntries = new ArrayList<>();

		long cpInstanceId = ParamUtil.getLong(
			httpServletRequest, "cpInstanceId");

		List<CommercePriceEntry> commercePriceEntries =
			_commercePriceEntryService.getInstanceCommercePriceEntries(
				cpInstanceId, pagination.getStartPosition(),
				pagination.getEndPosition());

		for (CommercePriceEntry commercePriceEntry : commercePriceEntries) {
			CommercePriceList commercePriceList =
				commercePriceEntry.getCommercePriceList();

			CommerceMoney priceCommerceMoney =
				commercePriceEntry.getPriceCommerceMoney(
					commercePriceList.getCommerceCurrencyId());

			Date createDate = commercePriceEntry.getCreateDate();

			String createDateDescription = LanguageUtil.getTimeDescription(
				httpServletRequest,
				System.currentTimeMillis() - createDate.getTime(), true);

			instancePriceEntries.add(
				new InstancePriceEntry(
					commercePriceEntry.getCommercePriceEntryId(),
					LanguageUtil.format(
						httpServletRequest, "x-ago", createDateDescription,
						false),
					commercePriceList.getName(),
					HtmlUtil.escape(
						priceCommerceMoney.format(
							_portal.getLocale(httpServletRequest)))));
		}

		return instancePriceEntries;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long cpInstanceId = ParamUtil.getLong(
			httpServletRequest, "cpInstanceId");

		return _commercePriceEntryService.getInstanceCommercePriceEntriesCount(
			cpInstanceId);
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private Portal _portal;

}