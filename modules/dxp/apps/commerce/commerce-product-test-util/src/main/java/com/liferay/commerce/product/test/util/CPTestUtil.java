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

package com.liferay.commerce.product.test.util;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceConstants;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Time;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Andrea Di Giorgi
 */
public class CPTestUtil {

	public static CPInstance addCPInstance(long groupId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CPDefinition cpDefinition = addCPDefinition(
			SimpleCPTypeConstants.NAME, true, serviceContext);

		return CPInstanceLocalServiceUtil.getCPInstance(
			cpDefinition.getCPDefinitionId(), CPInstanceConstants.DEFAULT_SKU);
	}

	protected static CPDefinition addCPDefinition(
			String productTypeName, boolean hasDefaultInstance,
			ServiceContext serviceContext)
		throws Exception {

		User user = UserLocalServiceUtil.getUser(serviceContext.getUserId());

		long now = System.currentTimeMillis();

		Map<Locale, String> titleMap = RandomTestUtil.randomLocaleStringMap();
		Map<Locale, String> shortDescriptionMap =
			RandomTestUtil.randomLocaleStringMap();
		Map<Locale, String> descriptionMap =
			RandomTestUtil.randomLocaleStringMap();
		Map<Locale, String> metaTitleMap =
			RandomTestUtil.randomLocaleStringMap();
		Map<Locale, String> metaKeywordsMap =
			RandomTestUtil.randomLocaleStringMap();
		Map<Locale, String> metaDescriptionMap =
			RandomTestUtil.randomLocaleStringMap();
		Map<Locale, String> urlTitleMap =
			RandomTestUtil.randomLocaleStringMap();
		boolean ignoreSKUCombinations = RandomTestUtil.randomBoolean();
		boolean shippable = RandomTestUtil.randomBoolean();
		boolean freeShipping = RandomTestUtil.randomBoolean();
		boolean shipSeparately = RandomTestUtil.randomBoolean();
		double shippingExtraPrice = RandomTestUtil.randomDouble();
		double width = RandomTestUtil.randomDouble();
		double height = RandomTestUtil.randomDouble();
		double depth = RandomTestUtil.randomDouble();
		double weight = RandomTestUtil.randomDouble();
		String ddmStructureKey = null;
		boolean published = RandomTestUtil.randomBoolean();

		Date displayDate = new Date(now - Time.HOUR);
		Date expirationDate = new Date(now + Time.DAY);

		Calendar displayCal = CalendarFactoryUtil.getCalendar(
			user.getTimeZone());

		displayCal.setTime(displayDate);

		int displayDateMonth = displayCal.get(Calendar.MONTH);
		int displayDateDay = displayCal.get(Calendar.DATE);
		int displayDateYear = displayCal.get(Calendar.YEAR);
		int displayDateHour = displayCal.get(Calendar.HOUR);
		int displayDateMinute = displayCal.get(Calendar.MINUTE);

		if (displayCal.get(Calendar.AM_PM) == Calendar.PM) {
			displayDateHour += 12;
		}

		Calendar expirationCal = CalendarFactoryUtil.getCalendar(
			user.getTimeZone());

		expirationCal.setTime(expirationDate);

		int expirationDateMonth = expirationCal.get(Calendar.MONTH);
		int expirationDateDay = expirationCal.get(Calendar.DATE);
		int expirationDateYear = expirationCal.get(Calendar.YEAR);
		int expirationDateHour = expirationCal.get(Calendar.HOUR);
		int expirationDateMinute = expirationCal.get(Calendar.MINUTE);

		if (expirationCal.get(Calendar.AM_PM) == Calendar.PM) {
			expirationDateHour += 12;
		}

		boolean neverExpire = false;

		return CPDefinitionLocalServiceUtil.addCPDefinition(
			titleMap, shortDescriptionMap, descriptionMap, urlTitleMap,
			metaTitleMap, metaKeywordsMap, metaDescriptionMap, productTypeName,
			ignoreSKUCombinations, shippable, freeShipping, shipSeparately,
			shippingExtraPrice, width, height, depth, weight, ddmStructureKey,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, hasDefaultInstance,
			serviceContext);
	}

	protected static String getJSON(
		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels) {

		Map<Long, Set<Long>> cpDefinitionOptionValueRelMap = new HashMap<>();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			long key = cpDefinitionOptionValueRel.getCPDefinitionOptionRelId();

			Set<Long> values = cpDefinitionOptionValueRelMap.get(key);

			if (values == null) {
				values = new HashSet<>();

				cpDefinitionOptionValueRelMap.put(key, values);
			}

			values.add(
				cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId());
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Map.Entry<Long, Set<Long>> entry :
				cpDefinitionOptionValueRelMap.entrySet()) {

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("key", entry.getKey());

			JSONArray valueJSONArray = JSONFactoryUtil.createJSONArray();

			for (long value : entry.getValue()) {
				valueJSONArray.put(value);
			}

			jsonObject.put("value", valueJSONArray);

			jsonArray.put(jsonObject);
		}

		return jsonArray.toJSONString();
	}

}