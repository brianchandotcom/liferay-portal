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
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPDefinitionDemoDataCreatorHelper.class)
public class CPDefinitionDemoDataCreatorHelper
	extends BaseCPDemoDataCreatorHelper {

	public void addCPDefinitions(long userId, long groupId, boolean buildSkus)
		throws Exception {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		AssetVocabulary commerceAssetVocabulary =
			_assetVocabularyDemoDataCreatorHelper.createAssetVocabulary(
				userId, groupId, "Commerce");

		AssetVocabulary manufacturersAssetVocabulary =
			_assetVocabularyDemoDataCreatorHelper.createAssetVocabulary(
				userId, groupId, "Manufacturers");

		long commerceVocabularyId = commerceAssetVocabulary.getVocabularyId();
		long manufacturersVocabularyId =
			manufacturersAssetVocabulary.getVocabularyId();

		JSONArray catalogJSONArray = getCatalogJSONArray();

		for (int i = 0; i < catalogJSONArray.length(); i++) {
			JSONObject productJSONObject = catalogJSONArray.getJSONObject(i);

			String baseSKU = productJSONObject.getString("baseSKU");
			String name = productJSONObject.getString("name");
			String title = productJSONObject.getString("title");
			String description = productJSONObject.getString("description");
			String productTypeName = productJSONObject.getString(
				"productTypeName");

			Map<Locale, String> titleMap = Collections.singletonMap(
				Locale.US, title);
			Map<Locale, String> descriptionMap = Collections.singletonMap(
				Locale.US, description);

			JSONArray categoriesJSONArray = productJSONObject.getJSONArray(
				"categories");
			JSONArray manufacturersJSONArray = productJSONObject.getJSONArray(
				"manufacturers");

			long[] commerceAssetCategoryIds =
				_assetCategoryDemoDataCreatorHelper.getAssetCategoryIds(
					userId, groupId, commerceVocabularyId, categoriesJSONArray);

			long[] manufacturersAssetCategoryIds =
				_assetCategoryDemoDataCreatorHelper.getAssetCategoryIds(
					userId, groupId, manufacturersVocabularyId,
					manufacturersJSONArray);

			long[] assetCategoryIds = ArrayUtil.append(
				commerceAssetCategoryIds, manufacturersAssetCategoryIds);

			CPDefinition cpDefinition = createCPDefinition(
				userId, groupId, baseSKU, name, titleMap, descriptionMap,
				productTypeName, assetCategoryIds);

			JSONArray cpOptionsJSONArray = productJSONObject.getJSONArray(
				"options");

			_cpOptionDemoDataCreatorHelper.addCPOptions(
				Locale.US, userId, groupId, cpDefinition.getCPDefinitionId(),
				cpOptionsJSONArray);

			if (buildSkus) {
				_cpInstanceLocalService.buildCPInstances(
					cpDefinition.getCPDefinitionId(), serviceContext);
			}
		}
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

	protected CPDefinition createCPDefinition(
			long userId, long groupId, String baseSKU, String name,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String productTypeName, long[] assetCategoryIds)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		serviceContext.setAssetCategoryIds(assetCategoryIds);

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		int displayDateMonth = displayCalendar.get(Calendar.MONTH);
		int displayDateDay = displayCalendar.get(Calendar.DAY_OF_MONTH) - 1;
		int displayDateYear = displayCalendar.get(Calendar.YEAR);
		int displayDateHour = displayCalendar.get(Calendar.HOUR);
		int displayDateMinute = displayCalendar.get(Calendar.MINUTE);
		int displayDateAmPm = displayCalendar.get(Calendar.AM_PM);

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		int expirationDateMonth = expirationCalendar.get(Calendar.MONTH) + 1;
		int expirationDateDay = expirationCalendar.get(Calendar.DAY_OF_MONTH);
		int expirationDateYear = expirationCalendar.get(Calendar.YEAR);
		int expirationDateHour = expirationCalendar.get(Calendar.HOUR);
		int expirationDateMinute = expirationCalendar.get(Calendar.MINUTE);
		int expirationDateAmPm = expirationCalendar.get(Calendar.AM_PM);

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

	protected JSONArray getCatalogJSONArray() throws Exception {
		Class<?> clazz = getClass();

		String catalogPath =
			"com/liferay/commerce/product/demo/data/creator/internal" +
				"/dependencies/catalog.json";

		String catalogJSON = StringUtil.read(
			clazz.getClassLoader(), catalogPath, false);

		JSONArray catalogJSONArray = JSONFactoryUtil.createJSONArray(
			catalogJSON);

		return catalogJSONArray;
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
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private CPOptionDemoDataCreatorHelper _cpOptionDemoDataCreatorHelper;

}