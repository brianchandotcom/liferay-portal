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

package com.liferay.headless.commerce.admin.pricing.internal.util.v2_0;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.TierPrice;
import com.liferay.headless.commerce.core.util.DateConfig;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Riccardo Alberti
 */
public class TierPriceUtil {

	public static CommerceTierPriceEntry addOrUpdateCommerceTierPriceEntry(
			CommerceTierPriceEntryService commerceTierPriceEntryService,
			TierPrice tierPrice, CommercePriceEntry commercePriceEntry,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext =
			serviceContextHelper.getServiceContext();

		DateConfig displayDateConfig = _getDisplayDateConfig(
			tierPrice.getDisplayDate(), serviceContext.getTimeZone());

		DateConfig expirationDateConfig = _getExpirationDateConfig(
			tierPrice.getExpirationDate(), serviceContext.getTimeZone());

		return commerceTierPriceEntryService.addOrUpdateCommerceTierPriceEntry(
			tierPrice.getExternalReferenceCode(),
			GetterUtil.getLong(tierPrice.getId()),
			commercePriceEntry.getCommercePriceEntryId(),
			BigDecimal.valueOf(tierPrice.getPrice()),
			tierPrice.getMinimumQuantity(), commercePriceEntry.isBulkPricing(),
			GetterUtil.getBoolean(tierPrice.getDiscountDiscovery(), true),
			tierPrice.getDiscountLevel1(), tierPrice.getDiscountLevel2(),
			tierPrice.getDiscountLevel3(), tierPrice.getDiscountLevel4(),
			displayDateConfig.getMonth(), displayDateConfig.getDay(),
			displayDateConfig.getYear(), displayDateConfig.getHour(),
			displayDateConfig.getMinute(), expirationDateConfig.getMonth(),
			expirationDateConfig.getDay(), expirationDateConfig.getYear(),
			expirationDateConfig.getHour(), expirationDateConfig.getMinute(),
			GetterUtil.getBoolean(tierPrice.getNeverExpire(), true),
			tierPrice.getPriceEntryExternalReferenceCode(), serviceContext);
	}

	private static DateConfig _getDisplayDateConfig(
		Date date, TimeZone timeZone) {

		if (date == null) {
			return new DateConfig(CalendarFactoryUtil.getCalendar(timeZone));
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			date.getTime(), timeZone);

		return new DateConfig(calendar);
	}

	private static DateConfig _getExpirationDateConfig(
		Date date, TimeZone timeZone) {

		if (date == null) {
			Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
				timeZone);

			expirationCalendar.add(Calendar.MONTH, 1);

			return new DateConfig(expirationCalendar);
		}

		Calendar calendar = CalendarFactoryUtil.getCalendar(
			date.getTime(), timeZone);

		return new DateConfig(calendar);
	}

}