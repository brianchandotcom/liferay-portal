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

import com.liferay.commerce.model.CPDefinitionInventoryConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionLinkLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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

		JSONArray catalogJSONArray = getCatalogJSONArray();

		// Product definitions

		for (int i = 0; i < catalogJSONArray.length(); i++) {
			JSONObject productJSONObject = catalogJSONArray.getJSONObject(i);

			String title = productJSONObject.getString("title");
			String description = productJSONObject.getString("description");
			String productTypeName = productJSONObject.getString(
				"productTypeName");

			// Asset Categories

			JSONArray productJSONArray = productJSONObject.getJSONArray(
				"categories");

			long[] assetCategoryIds =
				_assetCategoryDemoDataCreatorHelper.getProductAssetCategoryIds(
					productJSONArray);

			// Commerce product definition

			CPDefinition cpDefinition = createCPDefinition(
				userId, groupId, title, description, productTypeName,
				assetCategoryIds);

			// Commerce product definition inventory

			_cpDefinitionInventoryLocalService.addCPDefinitionInventory(
				cpDefinition.getCPDefinitionId(), null, null, false, false, 0,
				true, CPDefinitionInventoryConstants.DEFAULT_MIN_ORDER_QUANTITY,
				CPDefinitionInventoryConstants.DEFAULT_MAX_ORDER_QUANTITY, null,
				CPDefinitionInventoryConstants.DEFAULT_MULTIPLE_ORDER_QUANTITY,
				serviceContext);

			// Commerce product option categories

			JSONArray cpOptionCategories = productJSONObject.getJSONArray(
				"optionCategories");

			_cpOptionCategoryDemoDataCreatorHelper.addCPOptionCategories(
				userId, groupId, cpOptionCategories);

			// Commerce product options

			JSONArray cpOptionsJSONArray = productJSONObject.getJSONArray(
				"options");

			_cpOptionDemoDataCreatorHelper.addCPOptions(
				Locale.US, userId, groupId, cpDefinition.getCPDefinitionId(),
				cpOptionsJSONArray);

			// Commerce product specification options

			JSONArray cpSpecificationOptions = productJSONObject.getJSONArray(
				"specificationOptions");

			_cpSpecificationOptionDemoDataCreatorHelper.
				addCPSpecificationOptions(
					Locale.US, userId, groupId,
					cpDefinition.getCPDefinitionId(), cpSpecificationOptions);

			// Commerce product attachment file entries

			JSONArray cpAttachmentFileEntriesJSONArray =
				productJSONObject.getJSONArray("images");

			_cpAttachmentFileEntryDemoDataCreatorHelper.
				addCPDefinitionAttachmentFileEntries(
					userId, groupId, cpDefinition.getCPDefinitionId(),
					cpAttachmentFileEntriesJSONArray);

			if (buildSkus) {

				// Commerce product instances

				_cpInstanceLocalService.buildCPInstances(
					cpDefinition.getCPDefinitionId(), serviceContext);

				// Update commerce product instances price

				double price = productJSONObject.getDouble("price");

				List<CPInstance> cpInstances =
					_cpInstanceLocalService.getCPDefinitionInstances(
						cpDefinition.getCPDefinitionId());

				for (CPInstance cpInstance : cpInstances) {
					_cpInstanceLocalService.updatePricingInfo(
						cpInstance.getCPInstanceId(), cpInstance.getCost(),
						BigDecimal.valueOf(price), serviceContext);
				}
			}
		}

		// Related product definitions

		createCPDefinitionLinks(catalogJSONArray, serviceContext);
	}

	public CPDefinition getCPDefinitionByTitle(String title) {
		return _cpDefinitions.get(title);
	}

	public void init() {
		_cpDefinitions = new HashMap<>();
	}

	@Activate
	protected void activate() {
		init();
	}

	protected CPDefinition createCPDefinition(
			long userId, long groupId, String title, String description,
			String productTypeName, long[] assetCategoryIds)
		throws PortalException {

		CPDefinition cpDefinition = getCPDefinitionByTitle(title);

		if (cpDefinition != null) {
			return cpDefinition;
		}

		ServiceContext serviceContext = getServiceContext(userId, groupId);

		serviceContext.setAssetCategoryIds(assetCategoryIds);

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			serviceContext.getTimeZone());

		displayCalendar.add(Calendar.YEAR, -1);

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

		int expirationDateMonth = expirationCalendar.get(Calendar.MONTH);
		int expirationDateDay = expirationCalendar.get(Calendar.DAY_OF_MONTH);
		int expirationDateYear = expirationCalendar.get(Calendar.YEAR);
		int expirationDateHour = expirationCalendar.get(Calendar.HOUR);
		int expirationDateMinute = expirationCalendar.get(Calendar.MINUTE);
		int expirationDateAmPm = expirationCalendar.get(Calendar.AM_PM);

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		Map<Locale, String> titleMap = Collections.singletonMap(
			Locale.US, title);
		Map<Locale, String> descriptionMap = Collections.singletonMap(
			Locale.US, description);

		cpDefinition = _cpDefinitionLocalService.addCPDefinition(
			titleMap, null, descriptionMap, titleMap, null, null, null,
			productTypeName, false, true, false, false, 0, 10, 10, 10, 10, 0,
			false, false, null, true, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, true, false,
			serviceContext);

		_cpDefinitions.put(title, cpDefinition);

		return cpDefinition;
	}

	protected void createCPDefinitionLinks(
			JSONArray catalogJSONArray, ServiceContext serviceContext)
		throws Exception {

		for (int i = 0; i < catalogJSONArray.length(); i++) {
			JSONObject productJSONObject = catalogJSONArray.getJSONObject(i);

			String title = productJSONObject.getString("title");

			CPDefinition cpDefinition = getCPDefinitionByTitle(title);

			JSONArray cpDefinitionLinksJSONArray =
				productJSONObject.getJSONArray("relatedProducts");

			if (cpDefinitionLinksJSONArray == null) {
				continue;
			}

			List<Long> cpDefinitionIdsList = new ArrayList<>();

			for (int j = 0; j < cpDefinitionLinksJSONArray.length(); j++) {
				CPDefinition cpDefinitionEntry = getCPDefinitionByTitle(
					cpDefinitionLinksJSONArray.getString(j));

				cpDefinitionIdsList.add(cpDefinitionEntry.getCPDefinitionId());
			}

			long[] cpDefinitionEntryIds = ArrayUtil.toLongArray(
				cpDefinitionIdsList);

			_cpDefinitionLinkLocalService.updateCPDefinitionLinks(
				cpDefinition.getCPDefinitionId(), cpDefinitionEntryIds,
				"related", serviceContext);
		}
	}

	@Deactivate
	protected void deactivate() {
		_cpDefinitions = null;
	}

	protected JSONArray getCatalogJSONArray() throws Exception {
		Class<?> clazz = getClass();

		String catalogPath =
			"com/liferay/commerce/product/demo/data/creator/internal" +
				"/dependencies/catalog.json";

		String catalogJSON = StringUtil.read(
			clazz.getClassLoader(), catalogPath, false);

		JSONArray catalogJSONArray = _jsonFactory.createJSONArray(catalogJSON);

		return catalogJSONArray;
	}

	@Reference
	private AssetCategoryDemoDataCreatorHelper
		_assetCategoryDemoDataCreatorHelper;

	@Reference
	private AssetVocabularyDemoDataCreatorHelper
		_assetVocabularyDemoDataCreatorHelper;

	@Reference
	private CPAttachmentFileEntryDemoDataCreatorHelper
		_cpAttachmentFileEntryDemoDataCreatorHelper;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPDefinitionLinkLocalService _cpDefinitionLinkLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	private Map<String, CPDefinition> _cpDefinitions;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private CPOptionCategoryDemoDataCreatorHelper
		_cpOptionCategoryDemoDataCreatorHelper;

	@Reference
	private CPOptionDemoDataCreatorHelper _cpOptionDemoDataCreatorHelper;

	@Reference
	private CPSpecificationOptionDemoDataCreatorHelper
		_cpSpecificationOptionDemoDataCreatorHelper;

	@Reference
	private JSONFactory _jsonFactory;

}