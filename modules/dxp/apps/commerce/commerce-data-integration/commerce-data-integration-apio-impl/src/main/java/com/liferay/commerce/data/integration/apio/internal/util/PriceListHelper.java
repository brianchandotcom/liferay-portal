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

package com.liferay.commerce.data.integration.apio.internal.util;

import com.liferay.apio.architect.functional.Try;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;

import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.NotFoundException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltán Takács
 */
@Component(immediate = true, service = PriceListHelper.class)
public class PriceListHelper {

	public static String getCurrencyCode(CommercePriceList commercePriceList) {
		return Try.fromFallible(
			commercePriceList::getCommerceCurrency
		).map(
			CommerceCurrency::getCode
		).orElse(
			null
		);
	}

	public CommercePriceList addCommercePriceList(
			long groupId, String currency, String name, Double priority,
			Boolean neverExpire, Date displayDate, Date expirationDate)
		throws PortalException {

		long commerceCurrencyId = _getCommerceCurrencyId(groupId, currency);

		if (neverExpire == null) {
			neverExpire = Boolean.TRUE;
		}

		if (priority == null) {
			priority = 0D;
		}

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			groupId);

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		if (displayDate != null) {
			displayCalendar = _convertDateToCalendar(displayDate);
		}

		int displayDateMonth = displayCalendar.get(Calendar.MONTH);
		int displayDateDay = displayCalendar.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = displayCalendar.get(Calendar.YEAR);
		int displayDateHour = displayCalendar.get(Calendar.HOUR);
		int displayDateMinute = displayCalendar.get(Calendar.MINUTE);
		int displayDateAmPm = displayCalendar.get(Calendar.AM_PM);

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		if (expirationDate != null) {
			expirationCalendar = _convertDateToCalendar(expirationDate);
		}

		int expirationDateMonth = expirationCalendar.get(Calendar.MONTH);
		int expirationDateDay = expirationCalendar.get(Calendar.DAY_OF_MONTH);
		int expirationDateYear = expirationCalendar.get(Calendar.YEAR);
		int expirationDateHour = expirationCalendar.get(Calendar.HOUR);
		int expirationDateMinute = expirationCalendar.get(Calendar.MINUTE);
		int expirationDateAmPm = expirationCalendar.get(Calendar.AM_PM);

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		return _commercePriceListService.addCommercePriceList(
			commerceCurrencyId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public CommercePriceList getCommercePriceList(Long commercePriceListId) {
		CommercePriceList commercePriceList =
			_commercePriceListService.fetchCommercePriceList(
				commercePriceListId);

		if (commercePriceList == null) {
			throw new NotFoundException(
				"Unable to find price list with ID " + commercePriceListId);
		}

		return commercePriceList;
	}

	public CommercePriceList updateCommercePriceList(
			long commercePriceListId, String currency, String name,
			Double priority, Boolean neverExpire, Date displayDate,
			Date expirationDate)
		throws PortalException {

		CommercePriceList commercePriceList = getCommercePriceList(
			commercePriceListId);

		long groupId = commercePriceList.getGroupId();

		long commerceCurrencyId = _getCommerceCurrencyId(groupId, currency);

		if (neverExpire == null) {
			neverExpire = Boolean.TRUE;
		}

		if (priority == null) {
			priority = 0D;
		}

		ServiceContext serviceContext = _serviceContextHelper.getServiceContext(
			groupId);

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		if (displayDate != null) {
			displayCalendar = _convertDateToCalendar(displayDate);
		}

		int displayDateMonth = displayCalendar.get(Calendar.MONTH);
		int displayDateDay = displayCalendar.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = displayCalendar.get(Calendar.YEAR);
		int displayDateHour = displayCalendar.get(Calendar.HOUR);
		int displayDateMinute = displayCalendar.get(Calendar.MINUTE);
		int displayDateAmPm = displayCalendar.get(Calendar.AM_PM);

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		expirationCalendar.add(Calendar.MONTH, 1);

		if (expirationDate != null) {
			expirationCalendar = _convertDateToCalendar(expirationDate);
		}

		int expirationDateMonth = expirationCalendar.get(Calendar.MONTH);
		int expirationDateDay = expirationCalendar.get(Calendar.DAY_OF_MONTH);
		int expirationDateYear = expirationCalendar.get(Calendar.YEAR);
		int expirationDateHour = expirationCalendar.get(Calendar.HOUR);
		int expirationDateMinute = expirationCalendar.get(Calendar.MINUTE);
		int expirationDateAmPm = expirationCalendar.get(Calendar.AM_PM);

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		return _commercePriceListService.updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	private Calendar _convertDateToCalendar(Date date) {
		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.setTime(date);

		return calendar;
	}

	private long _getCommerceCurrencyId(Long groupId, String currencyCode)
		throws PortalException {

		CommerceCurrency commerceCurrency =
			_commerceCurrencyService.getCommerceCurrency(groupId, currencyCode);

		return commerceCurrency.getCommerceCurrencyId();
	}

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}