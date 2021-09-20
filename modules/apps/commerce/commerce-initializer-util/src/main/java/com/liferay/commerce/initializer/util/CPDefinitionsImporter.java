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

package com.liferay.commerce.initializer.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.MappingJsonFactory;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.service.CommerceAccountGroupLocalService;
import com.liferay.commerce.account.service.CommerceAccountGroupRelLocalService;
import com.liferay.commerce.constants.CPDefinitionInventoryConstants;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.model.CPDAvailabilityEstimate;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CommerceAvailabilityEstimate;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.exception.NoSuchSkuContributorCPDefinitionOptionRelException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalService;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.commerce.product.service.CPOptionValueLocalService;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
import com.liferay.commerce.product.service.CPTaxCategoryLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelLocalService;
import com.liferay.commerce.service.CPDAvailabilityEstimateLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.service.CommerceAvailabilityEstimateLocalService;
import com.liferay.commerce.util.comparator.CommerceAvailabilityEstimatePriorityComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.File;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(enabled = false, service = CPDefinitionsImporter.class)
public class CPDefinitionsImporter {

	public void importCPDefinitions(
			File cpDefinitionsFile, String assetVocabularyName,
			long catalogGroupId, long commerceChannelId,
			long[] commerceInventoryWarehouseIds, ClassLoader classLoader,
			String imageDependenciesPath, long scopeGroupId, long userId)
		throws Exception {

		MappingJsonFactory mappingJsonFactory = new MappingJsonFactory();

		JsonParser jsonFactoryParser = mappingJsonFactory.createParser(
			cpDefinitionsFile);

		JsonToken jsonToken = jsonFactoryParser.nextToken();

		if (jsonToken != JsonToken.START_ARRAY) {
			throw new Exception("JSON Array Expected");
		}

		ServiceContext serviceContext = getServiceContext(scopeGroupId, userId);

		int importCount = 0;

		while (jsonFactoryParser.nextToken() != JsonToken.END_ARRAY) {
			TreeNode treeNode = jsonFactoryParser.readValueAsTree();

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				treeNode.toString());

			if (_log.isDebugEnabled()) {
				_log.debug(jsonObject);
			}

			_importCPDefinition(
				jsonObject, assetVocabularyName, catalogGroupId,
				commerceChannelId, commerceInventoryWarehouseIds, classLoader,
				imageDependenciesPath, serviceContext);

			importCount += 1;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Products import count: " + importCount);
		}

		jsonFactoryParser.close();
	}

	public List<CPDefinition> importCPDefinitions(
			JSONArray jsonArray, String assetVocabularyName,
			long catalogGroupId, long commerceChannelId,
			long[] commerceInventoryWarehouseIds, ClassLoader classLoader,
			String imageDependenciesPath, long scopeGroupId, long userId)
		throws Exception {

		ServiceContext serviceContext = getServiceContext(scopeGroupId, userId);

		List<CPDefinition> cpDefinitions = new ArrayList<>(jsonArray.length());

		for (int i = 0; i < jsonArray.length(); i++) {
			CPDefinition cpDefinition = _importCPDefinition(
				jsonArray.getJSONObject(i), assetVocabularyName, catalogGroupId,
				commerceChannelId, commerceInventoryWarehouseIds, classLoader,
				imageDependenciesPath, serviceContext);

			cpDefinitions.add(cpDefinition);
		}

		return cpDefinitions;
	}

	protected ServiceContext getServiceContext(long scopeGroupId, long userId)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);

		return serviceContext;
	}

	private CPDefinition _addCPDefinition(
			long catalogGroupId, String name, String shortDescription,
			String description, String externalReferenceCode, boolean shippable,
			String sku, String taxCategory, long width, long height, long depth,
			long weight, boolean subscriptionEnabled, int subscriptionLength,
			String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, long[] assetCategoryIds,
			String[] assetTagNames, ServiceContext serviceContext)
		throws Exception {

		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);

		User user = _userLocalService.getUser(serviceContext.getUserId());

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			user.getTimeZone());

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
			user.getTimeZone());

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

		Locale locale = LocaleUtil.getSiteDefault();

		Map<Locale, String> nameMap = Collections.singletonMap(locale, name);
		Map<Locale, String> shortDescriptionMap = Collections.singletonMap(
			locale, shortDescription);
		Map<Locale, String> descriptionMap = Collections.singletonMap(
			locale, description);

		return _cpDefinitionLocalService.addCPDefinition(
			externalReferenceCode, catalogGroupId, user.getUserId(), nameMap,
			shortDescriptionMap, descriptionMap, nameMap, null, null, null,
			"simple", true, shippable, false, false, 0D, width, height, depth,
			weight, _getCPTaxCategoryId(taxCategory, serviceContext), false,
			false, null, true, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, true, sku,
			subscriptionEnabled, subscriptionLength, subscriptionType,
			subscriptionTypeSettingsUnicodeProperties, maxSubscriptionCycles,
			serviceContext);
	}

	private void _addWarehouseQuantities(
			JSONObject skuJSONObject, long[] commerceInventoryWarehouseIds,
			ServiceContext serviceContext, CPInstance cpInstance)
		throws Exception {

		for (int i = 0; i < commerceInventoryWarehouseIds.length; i++) {
			int quantity = skuJSONObject.getInt(
				"warehouse" + String.valueOf(i + 1));

			if (quantity > 0) {
				long commerceInventoryWarehouseId =
					commerceInventoryWarehouseIds[i];

				_commerceInventoryWarehouseItemLocalService.
					addOrUpdateCommerceInventoryWarehouseItem(
						serviceContext.getUserId(),
						commerceInventoryWarehouseId, cpInstance.getSku(),
						quantity);
			}
		}
	}

	private long _getCPTaxCategoryId(
			String taxCategory, ServiceContext serviceContext)
		throws Exception {

		if (Validator.isNull(taxCategory)) {
			return 0;
		}

		List<CPTaxCategory> cpTaxCategories =
			_cpTaxCategoryLocalService.getCPTaxCategories(
				serviceContext.getCompanyId());

		for (CPTaxCategory cpTaxCategory : cpTaxCategories) {
			if (taxCategory.equals(
					cpTaxCategory.getName(LocaleUtil.getSiteDefault()))) {

				return cpTaxCategory.getCPTaxCategoryId();
			}
		}

		Map<Locale, String> nameMap = Collections.singletonMap(
			LocaleUtil.getSiteDefault(), taxCategory);

		CPTaxCategory cpTaxCategory =
			_cpTaxCategoryLocalService.addCPTaxCategory(
				StringPool.BLANK, nameMap, Collections.emptyMap(),
				serviceContext);

		return cpTaxCategory.getCPTaxCategoryId();
	}

	private UnicodeProperties _getSubscriptionTypeSettingsUnicodeProperties(
		JSONObject subscriptionInfoJSONObject) {

		if (subscriptionInfoJSONObject == null) {
			return null;
		}

		String subscriptionTypeSettingsUnicodeProperties = GetterUtil.getString(
			subscriptionInfoJSONObject.get(
				"subscriptionTypeSettingsUnicodeProperties"));

		if (Validator.isNull(subscriptionTypeSettingsUnicodeProperties)) {
			return null;
		}

		return UnicodePropertiesBuilder.create(
			true
		).fastLoad(
			subscriptionTypeSettingsUnicodeProperties
		).build();
	}

	private CPDefinition _importCPDefinition(
			JSONObject jsonObject, String assetVocabularyName,
			long catalogGroupId, long commerceChannelId,
			long[] commerceInventoryWarehouseIds, ClassLoader classLoader,
			String imageDependenciesPath, ServiceContext serviceContext)
		throws Exception {

		Company company = _companyLocalService.getCompany(
			serviceContext.getCompanyId());

		// Categories

		List<AssetCategory> assetCategories = Collections.emptyList();

		JSONArray categoriesJSONArray = jsonObject.getJSONArray("categories");

		if (categoriesJSONArray != null) {
			assetCategories = _assetCategoriesImporter.importAssetCategories(
				categoriesJSONArray, assetVocabularyName, classLoader,
				imageDependenciesPath, company.getGroupId(),
				serviceContext.getUserId());
		}

		// Tags

		JSONArray tagsJSONArray = jsonObject.getJSONArray("tags");

		if (tagsJSONArray != null) {
			_assetTagsImporter.importAssetTags(
				tagsJSONArray, company.getGroupId(),
				serviceContext.getUserId());
		}
		else {
			tagsJSONArray = new JSONArrayImpl();
		}

		// Commerce product definition

		String externalReferenceCode = jsonObject.getString(
			"externalReferenceCode");

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, company.getCompanyId());

		if (cpDefinition != null) {
			_commerceChannelRelLocalService.addCommerceChannelRel(
				CPDefinition.class.getName(), cpDefinition.getCPDefinitionId(),
				commerceChannelId, serviceContext);

			Indexer<CPDefinition> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(CPDefinition.class);

			indexer.reindex(cpDefinition);

			return cpDefinition;
		}

		String name = jsonObject.getString("name");
		String shortDescription = jsonObject.getString("shortDescription");
		String description = jsonObject.getString("description");
		boolean shippable = jsonObject.getBoolean("shippable", true);
		String sku = jsonObject.getString("sku");
		String taxCategory = jsonObject.getString("taxCategory");

		long width = jsonObject.getLong("width");
		long height = jsonObject.getLong("height");
		long length = jsonObject.getLong("length");
		long weight = jsonObject.getLong("weight");

		boolean subscriptionEnabled = false;
		int subscriptionLength = 1;
		String subscriptionType = null;
		long maxSubscriptionCycles = 0;

		JSONObject subscriptionInfoJSONObject = jsonObject.getJSONObject(
			"subscriptionInfo");

		if (subscriptionInfoJSONObject != null) {
			subscriptionEnabled = GetterUtil.getBoolean(
				subscriptionInfoJSONObject.get("subscriptionEnabled"));
			subscriptionLength = GetterUtil.getInteger(
				subscriptionInfoJSONObject.get("subscriptionLength"), 1);
			subscriptionType = GetterUtil.getString(
				subscriptionInfoJSONObject.get("subscriptionType"));
			maxSubscriptionCycles = GetterUtil.getLong(
				subscriptionInfoJSONObject.get("maxSubscriptionCycles"));
		}

		long[] assetCategoryIds = ListUtil.toLongArray(
			assetCategories, AssetCategory.CATEGORY_ID_ACCESSOR);

		String[] assetTagNames = ArrayUtil.toStringArray(tagsJSONArray);

		int originalWorkflowAction = serviceContext.getWorkflowAction();

		serviceContext.setWorkflowAction(WorkflowConstants.STATUS_DRAFT);

		cpDefinition = _addCPDefinition(
			catalogGroupId, name, shortDescription, description,
			externalReferenceCode, shippable, sku, taxCategory, width, height,
			length, weight, subscriptionEnabled, subscriptionLength,
			subscriptionType,
			_getSubscriptionTypeSettingsUnicodeProperties(
				subscriptionInfoJSONObject),
			maxSubscriptionCycles, assetCategoryIds, assetTagNames,
			serviceContext);

		serviceContext.setWorkflowAction(originalWorkflowAction);

		// Commerce product definition specification option values

		JSONArray specificationOptionsJSONArray = jsonObject.getJSONArray(
			"specificationOptions");

		if (specificationOptionsJSONArray != null) {
			for (int i = 0; i < specificationOptionsJSONArray.length(); i++) {
				JSONObject specificationOptionJSONObject =
					specificationOptionsJSONArray.getJSONObject(i);

				_importCPDefinitionSpecificationOptionValue(
					company.getCompanyId(), cpDefinition.getCPDefinitionId(),
					specificationOptionJSONObject, i, serviceContext);
			}
		}

		// Commerce product definition option rels

		JSONArray optionsJSONArray = jsonObject.getJSONArray("options");

		if (optionsJSONArray != null) {
			for (int i = 0; i < optionsJSONArray.length(); i++) {
				JSONObject optionJSONObject = optionsJSONArray.getJSONObject(i);

				_importCPDefinitionOptionRel(
					catalogGroupId, company.getCompanyId(),
					cpDefinition.getCPDefinitionId(), optionJSONObject,
					serviceContext);
			}
		}

		// Commerce product instances

		JSONArray skusJSONArray = jsonObject.getJSONArray("skus");

		if (skusJSONArray != null) {
			Calendar calendar = Calendar.getInstance();

			for (int i = 0; i < skusJSONArray.length(); i++) {
				JSONObject skuJSONObject = skusJSONArray.getJSONObject(i);

				_importCPInstance(
					cpDefinition.getCPDefinitionId(), skuJSONObject,
					commerceInventoryWarehouseIds, calendar, serviceContext);
			}
		}
		else {
			try {
				_cpInstanceLocalService.buildCPInstances(
					cpDefinition.getCPDefinitionId(), serviceContext);
			}
			catch (NoSuchSkuContributorCPDefinitionOptionRelException
						noSuchSkuContributorCPDefinitionOptionRelException) {

				if (_log.isInfoEnabled()) {
					_log.info(
						"No options defined as sku contributor for " +
							"CPDefinition " + cpDefinition.getCPDefinitionId(),
						noSuchSkuContributorCPDefinitionOptionRelException);
				}
			}

			List<CPInstance> cpInstances = cpDefinition.getCPInstances();

			for (CPInstance cpInstance : cpInstances) {

				// Commerce product instance

				double priceDouble = jsonObject.getDouble("price", 0);

				BigDecimal price = BigDecimal.valueOf(priceDouble);

				BigDecimal cost = BigDecimal.valueOf(
					jsonObject.getDouble("cost", 0));

				BigDecimal promoPrice = BigDecimal.valueOf(
					jsonObject.getDouble("promoPrice", 0));

				cpInstance.setPrice(price);
				cpInstance.setPromoPrice(promoPrice);
				cpInstance.setCost(cost);

				String manufacturerPartNumber = jsonObject.getString(
					"manufacturerPartNumber");

				cpInstance.setManufacturerPartNumber(manufacturerPartNumber);

				String cpInstanceExternalReferenceCode =
					FriendlyURLNormalizerUtil.normalize(sku);

				cpInstance.setExternalReferenceCode(
					cpInstanceExternalReferenceCode);

				_cpInstanceLocalService.updateCPInstance(cpInstance);

				// Commerce warehouse items

				_addWarehouseQuantities(
					jsonObject, commerceInventoryWarehouseIds, serviceContext,
					cpInstance);
			}
		}

		// Commerce product definition inventory

		String cpDefinitionInventoryEngine = jsonObject.getString(
			"cpDefinitionInventoryEngine");
		String lowStockActivity = jsonObject.getString("lowStockActivity");
		boolean displayAvailability = jsonObject.getBoolean(
			"displayAvailability");
		boolean displayStockQuantity = jsonObject.getBoolean(
			"displayStockQuantity");
		int minStockQuantity = jsonObject.getInt("minStockQuantity");
		boolean backOrders = jsonObject.getBoolean("backOrders");
		int minOrderQuantity = jsonObject.getInt(
			"minOrderQuantity",
			CPDefinitionInventoryConstants.DEFAULT_MIN_ORDER_QUANTITY);
		int maxOrderQuantity = jsonObject.getInt(
			"maxOrderQuantity",
			CPDefinitionInventoryConstants.DEFAULT_MAX_ORDER_QUANTITY);
		String allowedOrderQuantities = jsonObject.getString(
			"allowedOrderQuantities");
		int multipleOrderQuantity = jsonObject.getInt(
			"multipleOrderQuantity",
			CPDefinitionInventoryConstants.DEFAULT_MULTIPLE_ORDER_QUANTITY);

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpDefinition.getCPDefinitionId());

		if (cpDefinitionInventory == null) {
			_cpDefinitionInventoryLocalService.addCPDefinitionInventory(
				serviceContext.getUserId(), cpDefinition.getCPDefinitionId(),
				cpDefinitionInventoryEngine, lowStockActivity,
				displayAvailability, displayStockQuantity, minStockQuantity,
				backOrders, minOrderQuantity, maxOrderQuantity,
				allowedOrderQuantities, multipleOrderQuantity);
		}
		else {
			_cpDefinitionInventoryLocalService.updateCPDefinitionInventory(
				cpDefinitionInventory.getCPDefinitionInventoryId(),
				cpDefinitionInventoryEngine, lowStockActivity,
				displayAvailability, displayStockQuantity, minStockQuantity,
				backOrders, minOrderQuantity, maxOrderQuantity,
				allowedOrderQuantities, multipleOrderQuantity);
		}

		// Commerce product definition availability estimate

		String availabilityEstimate = jsonObject.getString(
			"availabilityEstimate");

		if (Validator.isNotNull(availabilityEstimate)) {
			_updateCPDAvailabilityEstimate(
				cpDefinition.getCProductId(), availabilityEstimate,
				serviceContext);
		}

		// Commerce product images

		String image = jsonObject.getString("image");

		if (Validator.isNotNull(image)) {
			_cpAttachmentFileEntryCreator.addCPAttachmentFileEntry(
				cpDefinition, classLoader, imageDependenciesPath, image, 0,
				CPAttachmentFileEntryConstants.TYPE_IMAGE, catalogGroupId,
				serviceContext.getUserId());
		}

		JSONArray imagesJSONArray = jsonObject.getJSONArray("images");

		if (imagesJSONArray != null) {
			for (int i = 0; i < imagesJSONArray.length(); i++) {
				_cpAttachmentFileEntryCreator.addCPAttachmentFileEntry(
					cpDefinition, classLoader, imageDependenciesPath,
					imagesJSONArray.getString(i), i,
					CPAttachmentFileEntryConstants.TYPE_IMAGE, catalogGroupId,
					serviceContext.getUserId());
			}
		}

		// Commerce product attachment file entries

		String attachment = jsonObject.getString("attachment");

		if (Validator.isNotNull(attachment)) {
			_cpAttachmentFileEntryCreator.addCPAttachmentFileEntry(
				cpDefinition, classLoader, imageDependenciesPath, attachment, 0,
				CPAttachmentFileEntryConstants.TYPE_OTHER, catalogGroupId,
				serviceContext.getUserId());
		}

		JSONArray attachmentsJSONArray = jsonObject.getJSONArray("attachments");

		if (attachmentsJSONArray != null) {
			for (int i = 0; i < attachmentsJSONArray.length(); i++) {
				_cpAttachmentFileEntryCreator.addCPAttachmentFileEntry(
					cpDefinition, classLoader, imageDependenciesPath,
					attachmentsJSONArray.getString(i), i,
					CPAttachmentFileEntryConstants.TYPE_OTHER, catalogGroupId,
					serviceContext.getUserId());
			}
		}

		// Commerce product channel

		_cpDefinitionLocalService.updateCPDefinitionChannelFilter(
			cpDefinition.getCPDefinitionId(), true);

		_commerceChannelRelLocalService.addCommerceChannelRel(
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId(),
			commerceChannelId, serviceContext);

		// Filter account groups

		JSONArray filterAccountGroupsJSONArray = jsonObject.getJSONArray(
			"filterAccountGroups");

		if (filterAccountGroupsJSONArray != null) {
			_cpDefinitionLocalService.updateCPDefinitionAccountGroupFilter(
				cpDefinition.getCPDefinitionId(), true);

			for (int i = 0; i < filterAccountGroupsJSONArray.length(); i++) {
				String accountGroupExternalReferenceCode =
					FriendlyURLNormalizerUtil.normalize(
						filterAccountGroupsJSONArray.getString(i));

				CommerceAccountGroup commerceAccountGroup =
					_commerceAccountGroupLocalService.
						fetchByExternalReferenceCode(
							company.getCompanyId(),
							accountGroupExternalReferenceCode);

				if (commerceAccountGroup != null) {
					_commerceAccountGroupRelLocalService.
						addCommerceAccountGroupRel(
							CPDefinition.class.getName(),
							cpDefinition.getCPDefinitionId(),
							commerceAccountGroup.getCommerceAccountGroupId(),
							serviceContext);
				}
			}
		}

		return _cpDefinitionLocalService.updateStatus(
			cpDefinition.getUserId(), cpDefinition.getCPDefinitionId(),
			WorkflowConstants.STATUS_APPROVED, serviceContext,
			Collections.emptyMap());
	}

	private CPDefinitionOptionRel _importCPDefinitionOptionRel(
			long catalogGroupId, long companyId, long cpDefinitionId,
			JSONObject jsonObject, ServiceContext serviceContext)
		throws Exception {

		// Commerce product definition option rel

		CPOption cpOption = _cpOptionLocalService.getCPOption(
			companyId, jsonObject.getString("key"));

		boolean importOptionValue = true;

		JSONArray valuesJSONArray = jsonObject.getJSONArray("values");

		if ((valuesJSONArray != null) && (valuesJSONArray.length() > 0)) {
			importOptionValue = false;
		}

		CPDefinitionOptionRel cpDefinitionOptionRel = null;

		long scopeGroupId = serviceContext.getScopeGroupId();

		serviceContext.setScopeGroupId(catalogGroupId);

		try {
			cpDefinitionOptionRel =
				_cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(
					cpDefinitionId, cpOption.getCPOptionId(), importOptionValue,
					serviceContext);
		}
		finally {
			serviceContext.setScopeGroupId(scopeGroupId);
		}

		// Commerce product definition option value rels

		if (!importOptionValue) {
			for (int i = 0; i < valuesJSONArray.length(); i++) {
				String key;

				JSONObject valueJSONObject = valuesJSONArray.getJSONObject(i);

				if (valueJSONObject != null) {
					key = valueJSONObject.getString("key");
				}
				else {
					key = valuesJSONArray.getString(i);
				}

				_importCPDefinitionOptionValueRel(
					key, cpDefinitionOptionRel, serviceContext);
			}
		}

		return cpDefinitionOptionRel;
	}

	private CPDefinitionOptionValueRel _importCPDefinitionOptionValueRel(
			String key, CPDefinitionOptionRel cpDefinitionOptionRel,
			ServiceContext serviceContext)
		throws Exception {

		CPOptionValue cpOptionValue =
			_cpOptionValueLocalService.getCPOptionValue(
				cpDefinitionOptionRel.getCPOptionId(),
				FriendlyURLNormalizerUtil.normalize(key));

		return _cpDefinitionOptionValueRelLocalService.
			addCPDefinitionOptionValueRel(
				cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
				cpOptionValue, serviceContext);
	}

	private CPDefinitionSpecificationOptionValue
			_importCPDefinitionSpecificationOptionValue(
				long companyId, long cpDefinitionId, JSONObject jsonObject,
				double defaultPriority, ServiceContext serviceContext)
		throws Exception {

		CPSpecificationOption cpSpecificationOption =
			_cpSpecificationOptionLocalService.getCPSpecificationOption(
				companyId, jsonObject.getString("key"));

		long cpOptionCategoryId = 0;

		String categoryKey = jsonObject.getString("categoryKey");

		if (Validator.isNotNull(categoryKey)) {
			CPOptionCategory cpOptionCategory =
				_cpOptionCategoryLocalService.getCPOptionCategory(
					cpSpecificationOption.getCompanyId(), categoryKey);

			cpOptionCategoryId = cpOptionCategory.getCPOptionCategoryId();
		}
		else {
			cpOptionCategoryId = cpSpecificationOption.getCPOptionCategoryId();
		}

		Map<Locale, String> valueMap = Collections.singletonMap(
			LocaleUtil.getSiteDefault(), jsonObject.getString("value"));
		double priority = jsonObject.getDouble("priority", defaultPriority);

		return _cpDefinitionSpecificationOptionValueLocalService.
			addCPDefinitionSpecificationOptionValue(
				cpDefinitionId,
				cpSpecificationOption.getCPSpecificationOptionId(),
				cpOptionCategoryId, valueMap, priority, serviceContext);
	}

	private CPInstance _importCPInstance(
			long cpDefinitionId, JSONObject skuJSONObject,
			long[] commerceInventoryWarehouseIds, Calendar calendar,
			ServiceContext serviceContext)
		throws Exception {

		String sku = skuJSONObject.getString("sku");
		String manufacturerPartNumber = skuJSONObject.getString(
			"manufacturerPartNumber");
		double price = skuJSONObject.getDouble("price");
		double promoPrice = skuJSONObject.getDouble("promoPrice");

		JSONArray optionsJSONArray = skuJSONObject.getJSONArray(
			"contributorOptions");

		String optionsJSON = null;

		if (optionsJSONArray != null) {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

			for (int i = 0; i < optionsJSONArray.length(); i++) {
				JSONObject optionsJSONObject = optionsJSONArray.getJSONObject(
					i);

				String key = optionsJSONObject.getString("key");

				CPOption cpOption = _cpOptionLocalService.fetchCPOption(
					serviceContext.getCompanyId(), key);

				if (cpOption == null) {
					continue;
				}

				CPDefinitionOptionRel cpDefinitionOptionRel =
					_cpDefinitionOptionRelLocalService.
						fetchCPDefinitionOptionRel(
							cpDefinitionId, cpOption.getCPOptionId());

				if (cpDefinitionOptionRel == null) {
					continue;
				}

				JSONObject jsonObject = JSONUtil.put(
					"key", cpDefinitionOptionRel.getKey());

				List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
					_cpDefinitionOptionValueRelLocalService.
						getCPDefinitionOptionValueRels(
							cpDefinitionOptionRel.getCPDefinitionOptionRelId());

				for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
						cpDefinitionOptionValueRels) {

					String name = cpDefinitionOptionValueRel.getName(
						LocaleUtil.getSiteDefault());

					if (name.equals(optionsJSONObject.getString("value"))) {
						JSONArray valueJSONArray = JSONUtil.put(
							cpDefinitionOptionValueRel.getKey());

						jsonObject.put("value", valueJSONArray);
					}
				}

				jsonArray.put(jsonObject);
			}

			optionsJSON = jsonArray.toString();
		}

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		String externalReferenceCode = FriendlyURLNormalizerUtil.normalize(sku);

		boolean overrideSubscriptionInfo = false;
		boolean subscriptionEnabled = false;
		int subscriptionLength = 0;
		String subscriptionType = null;
		long maxSubscriptionCycles = 0;

		JSONObject subscriptionInfoJSONObject = skuJSONObject.getJSONObject(
			"subscriptionInfo");

		if (subscriptionInfoJSONObject != null) {
			overrideSubscriptionInfo = GetterUtil.getBoolean(
				subscriptionInfoJSONObject.get("overrideSubscriptionInfo"));
			subscriptionEnabled = GetterUtil.getBoolean(
				subscriptionInfoJSONObject.get("subscriptionEnabled"));
			subscriptionLength = GetterUtil.getInteger(
				subscriptionInfoJSONObject.get("subscriptionLength"), 1);
			subscriptionType = GetterUtil.getString(
				subscriptionInfoJSONObject.get("subscriptionType"));
			maxSubscriptionCycles = GetterUtil.getLong(
				subscriptionInfoJSONObject.get("maxSubscriptionCycles"));
		}

		CPInstance cpInstance = _cpInstanceLocalService.addCPInstance(
			externalReferenceCode, cpDefinitionId, cpDefinition.getGroupId(),
			sku, null, manufacturerPartNumber, true,
			_cpDefinitionOptionRelLocalService.
				getCPDefinitionOptionRelCPDefinitionOptionValueRelIds(
					cpDefinitionId, optionsJSON),
			cpDefinition.getWidth(), cpDefinition.getHeight(),
			cpDefinition.getDepth(), cpDefinition.getWeight(),
			BigDecimal.valueOf(price), BigDecimal.valueOf(promoPrice),
			BigDecimal.valueOf(0), true, calendar.get(Calendar.MONTH),
			calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.YEAR),
			calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
			0, 0, 0, 0, 0, true, overrideSubscriptionInfo, subscriptionEnabled,
			subscriptionLength, subscriptionType,
			_getSubscriptionTypeSettingsUnicodeProperties(
				subscriptionInfoJSONObject),
			maxSubscriptionCycles, serviceContext);

		_addWarehouseQuantities(
			skuJSONObject, commerceInventoryWarehouseIds, serviceContext,
			cpInstance);

		return cpInstance;
	}

	private CPDAvailabilityEstimate _updateCPDAvailabilityEstimate(
			long cProductId, String availabilityEstimate,
			ServiceContext serviceContext)
		throws Exception {

		List<CommerceAvailabilityEstimate> commerceAvailabilityEstimates =
			_commerceAvailabilityEstimateLocalService.
				getCommerceAvailabilityEstimates(
					serviceContext.getCompanyId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS,
					new CommerceAvailabilityEstimatePriorityComparator(true));

		for (CommerceAvailabilityEstimate commerceAvailabilityEstimate :
				commerceAvailabilityEstimates) {

			if (availabilityEstimate.equals(
					commerceAvailabilityEstimate.getTitle(
						LocaleUtil.getSiteDefault()))) {

				return _cpdAvailabilityEstimateLocalService.
					updateCPDAvailabilityEstimateByCProductId(
						0, cProductId,
						commerceAvailabilityEstimate.
							getCommerceAvailabilityEstimateId(),
						serviceContext);
			}
		}

		Map<Locale, String> titleMap = Collections.singletonMap(
			LocaleUtil.getSiteDefault(), availabilityEstimate);

		CommerceAvailabilityEstimate commerceAvailabilityEstimate =
			_commerceAvailabilityEstimateLocalService.
				addCommerceAvailabilityEstimate(titleMap, 0, serviceContext);

		return _cpdAvailabilityEstimateLocalService.
			updateCPDAvailabilityEstimateByCProductId(
				0, cProductId,
				commerceAvailabilityEstimate.
					getCommerceAvailabilityEstimateId(),
				serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionsImporter.class);

	@Reference
	private AssetCategoriesImporter _assetCategoriesImporter;

	@Reference
	private AssetTagsImporter _assetTagsImporter;

	@Reference
	private CommerceAccountGroupLocalService _commerceAccountGroupLocalService;

	@Reference
	private CommerceAccountGroupRelLocalService
		_commerceAccountGroupRelLocalService;

	@Reference
	private CommerceAvailabilityEstimateLocalService
		_commerceAvailabilityEstimateLocalService;

	@Reference
	private CommerceChannelRelLocalService _commerceChannelRelLocalService;

	@Reference
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CPAttachmentFileEntryCreator _cpAttachmentFileEntryCreator;

	@Reference
	private CPDAvailabilityEstimateLocalService
		_cpdAvailabilityEstimateLocalService;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPDefinitionOptionRelLocalService
		_cpDefinitionOptionRelLocalService;

	@Reference
	private CPDefinitionOptionValueRelLocalService
		_cpDefinitionOptionValueRelLocalService;

	@Reference
	private CPDefinitionSpecificationOptionValueLocalService
		_cpDefinitionSpecificationOptionValueLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@Reference
	private CPOptionLocalService _cpOptionLocalService;

	@Reference
	private CPOptionValueLocalService _cpOptionValueLocalService;

	@Reference
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	@Reference
	private CPTaxCategoryLocalService _cpTaxCategoryLocalService;

	@Reference
	private UserLocalService _userLocalService;

}