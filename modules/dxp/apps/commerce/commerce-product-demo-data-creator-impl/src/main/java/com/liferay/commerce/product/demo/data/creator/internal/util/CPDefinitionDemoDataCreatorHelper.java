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

package com.liferay.commerce.product.demo.data.creator.internal.util;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPDefinitionDemoDataCreatorHelper.class)
public class CPDefinitionDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public void addCPDefinitions(long userId, long groupId, boolean buildSkus)
		throws IOException, PortalException {

		AssetVocabulary commerceAssetVocabulary =
			_assetVocabularyDemoDataCreatorHelper.createAssetVocabulary(
				userId, groupId, "Commerce");

		AssetVocabulary manufacturersAssetVocabulary =
			_assetVocabularyDemoDataCreatorHelper.createAssetVocabulary(
				userId, groupId, "Manufacturers");

		long commerceVocabularyId = commerceAssetVocabulary.getVocabularyId();
		long manufacturersVocabularyId =
			manufacturersAssetVocabulary.getVocabularyId();

		JSONArray catalog = getCatalog();

		for (int i = 0; i < catalog.length(); i++) {
			JSONObject product = catalog.getJSONObject(i);

			String baseSKU = product.getString("baseSKU");
			String name = product.getString("name");
			String title = product.getString("title");
			String description = product.getString("description");
			String productTypeName = product.getString("productTypeName");

			Map<Locale, String> titleMap = new HashMap<>();
			Map<Locale, String> descriptionMap = new HashMap<>();

			titleMap.put(Locale.US, title);
			descriptionMap.put(Locale.US, description);

			JSONArray categories = product.getJSONArray("categories");
			JSONArray manufacturers = product.getJSONArray("manufacturers");

			long[] commerceAssetCategoryIds =
				_assetCategoryDemoDataCreatorHelper.getAssetCategoryIds(
					userId, groupId, commerceVocabularyId, categories);

			long[] manufacturersAssetCategoryIds =
				_assetCategoryDemoDataCreatorHelper.getAssetCategoryIds(
					userId, groupId, manufacturersVocabularyId, manufacturers);

			long[] assetCategoryIds =
				_assetCategoryDemoDataCreatorHelper.getAllAssetCategoryIds(
					commerceAssetCategoryIds, manufacturersAssetCategoryIds);

			CPDefinition cpDefinition = createCPDefinition(
				userId, groupId, baseSKU, name, titleMap, descriptionMap,
				productTypeName, assetCategoryIds);

			long cpDefinitionId = cpDefinition.getCPDefinitionId();

			JSONArray options = product.getJSONArray("options");

			_cpOptionDemoDataCreatorHelper.addCPOptions(
				Locale.US, userId, groupId, cpDefinitionId, options);

			if (buildSkus) {
				_cpInstanceDemoDataCreatorHelper.createCPInstance(
					userId, groupId, cpDefinitionId);
			}
		}
	}

	public CPDefinition createCPDefinition(
			long userId, long groupId, String baseSKU, String name,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String productTypeName, long[] assetCategoryIds)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		serviceContext.setAssetCategoryIds(assetCategoryIds);

		Date displayDate = _getRandomDate();
		Date expirationDate = _getRandomDate();

		Calendar calendar = new GregorianCalendar();

		calendar.setTime(displayDate);

		int displayDateMonth = calendar.get(Calendar.MONTH);
		int displayDateDay = calendar.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = calendar.get(Calendar.YEAR);
		int displayDateHour = calendar.get(Calendar.HOUR);
		int displayDateMinute = calendar.get(Calendar.MINUTE);
		int displayDateAmPm = calendar.get(Calendar.AM_PM);

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		calendar.setTime(expirationDate);

		int expirationDateMonth = calendar.get(Calendar.MONTH);
		int expirationDateDay = calendar.get(Calendar.DAY_OF_MONTH);
		int expirationDateYear = calendar.get(Calendar.YEAR);
		int expirationDateHour = calendar.get(Calendar.HOUR);
		int expirationDateMinute = calendar.get(Calendar.MINUTE);
		int expirationDateAmPm = calendar.get(Calendar.AM_PM);

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		CPDefinition cpDefinition = _cpDefinitionLocalService.addCPDefinition(
			baseSKU, name, titleMap, descriptionMap, productTypeName, null,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute, true,
			serviceContext);

		_cpDefinitionIds.add(cpDefinition.getCPDefinitionId());

		return cpDefinition;
	}

	public void deleteCPDefinitions() throws PortalException {
		for (long cpDefinitionId : _cpDefinitionIds) {
			try {
				_cpDefinitionLocalService.deleteCPDefinition(cpDefinitionId);
			}
			catch (NoSuchCPDefinitionException nscpde) {
				if (_log.isWarnEnabled()) {
					_log.warn(nscpde);
				}
			}

			_cpDefinitionIds.remove(cpDefinitionId);
		}
	}

	public JSONArray getCatalog() throws IOException, JSONException {
		Class<?> clazz = getClass();

		String catalogPath =
			"com/liferay/commerce/product/demo/data/creator/internal" +
				"/dependencies/products.json";

		String catalogFile = StringUtil.read(
			clazz.getClassLoader(), catalogPath, false);

		JSONArray catalog = JSONFactoryUtil.createJSONArray(catalogFile);

		return catalog;
	}

	private Date _getRandomDate() {
		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.set(2000, Calendar.JANUARY, 1);

		long start = calendar.getTimeInMillis();

		Date now = new Date();

		long end = now.getTime();

		ThreadLocalRandom current = ThreadLocalRandom.current();

		return new Date(current.nextLong(start, end));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionDemoDataCreatorHelper.class);

	@Reference
	private AssetCategoryDemoDataCreatorHelper
		_assetCategoryDemoDataCreatorHelper;

	@Reference
	private AssetVocabularyDemoDataCreatorHelper
		_assetVocabularyDemoDataCreatorHelper;

	private final List<Long> _cpDefinitionIds = new CopyOnWriteArrayList<>();

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceDemoDataCreatorHelper _cpInstanceDemoDataCreatorHelper;

	@Reference
	private CPOptionDemoDataCreatorHelper _cpOptionDemoDataCreatorHelper;

}