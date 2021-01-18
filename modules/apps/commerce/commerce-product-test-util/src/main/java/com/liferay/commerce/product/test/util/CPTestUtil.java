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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyLocalServiceUtil;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalServiceUtil;
import com.liferay.commerce.price.list.service.CommercePriceListLocalServiceUtil;
import com.liferay.commerce.product.configuration.CPOptionConfiguration;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.constants.CPInstanceConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalServiceUtil;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalServiceUtil;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.commerce.product.service.CPOptionLocalServiceUtil;
import com.liferay.commerce.product.service.CPOptionValueLocalServiceUtil;
import com.liferay.commerce.product.service.CommerceCatalogLocalServiceUtil;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.commerce.service.CPDefinitionInventoryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Andrea Di Giorgi
 * @author Luca Pellizzon
 * @author Igor Beslic
 */
public class CPTestUtil {

	public static AssetCategory addCategoryToCPDefinitions(
			long groupId, long... cpDefinitionIds)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				serviceContext.getUserId(), groupId,
				RandomTestUtil.randomString(), serviceContext);

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.addCategory(
			serviceContext.getUserId(), groupId, RandomTestUtil.randomString(),
			assetVocabulary.getVocabularyId(), serviceContext);

		serviceContext.setAssetCategoryIds(
			new long[] {assetCategory.getCategoryId()});

		for (long cpDefinitionId : cpDefinitionIds) {
			CPDefinitionLocalServiceUtil.updateCPDefinitionCategorization(
				cpDefinitionId, serviceContext);
		}

		return assetCategory;
	}

	public static CPDefinition addCPDefinition(long groupId)
		throws PortalException {

		return _addCPDefinition(
			groupId, SimpleCPTypeConstants.NAME, true, true,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static CPDefinition addCPDefinition(
			long groupId, boolean ignoreSKUCombinations,
			boolean hasDefaultInstance, int workflowAction)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setWorkflowAction(workflowAction);

		return _addCPDefinition(
			SimpleCPTypeConstants.NAME, ignoreSKUCombinations,
			hasDefaultInstance, serviceContext);
	}

	public static CPDefinition addCPDefinition(
			long groupId, String productTypeName, boolean ignoreSKUCombinations,
			boolean hasDefaultInstance)
		throws PortalException {

		return _addCPDefinition(
			productTypeName, ignoreSKUCombinations, hasDefaultInstance,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static CPInstance addCPDefinitionCPInstance(
			long cpDefinitionId,
			Map<Long, List<Long>>
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds)
		throws PortalException {

		CPDefinition cpDefinition =
			CPDefinitionLocalServiceUtil.getCPDefinition(cpDefinitionId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(cpDefinition.getGroupId());

		User user = UserLocalServiceUtil.getUser(serviceContext.getUserId());

		long now = System.currentTimeMillis();

		Date displayDate = new Date(now - Time.HOUR);
		Date expirationDate = new Date(now + Time.DAY);

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			user.getTimeZone());

		displayCalendar.setTime(displayDate);

		int displayDateMonth = displayCalendar.get(Calendar.MONTH);
		int displayDateDay = displayCalendar.get(Calendar.DATE);
		int displayDateYear = displayCalendar.get(Calendar.YEAR);
		int displayDateHour = displayCalendar.get(Calendar.HOUR);
		int displayDateMinute = displayCalendar.get(Calendar.MINUTE);

		if (displayCalendar.get(Calendar.AM_PM) == Calendar.PM) {
			displayDateHour += 12;
		}

		Calendar expirationCalendar = CalendarFactoryUtil.getCalendar(
			user.getTimeZone());

		expirationCalendar.setTime(expirationDate);

		int expirationDateMonth = expirationCalendar.get(Calendar.MONTH);
		int expirationDateDay = expirationCalendar.get(Calendar.DATE);
		int expirationDateYear = expirationCalendar.get(Calendar.YEAR);
		int expirationDateHour = expirationCalendar.get(Calendar.HOUR);
		int expirationDateMinute = expirationCalendar.get(Calendar.MINUTE);

		if (expirationCalendar.get(Calendar.AM_PM) == Calendar.PM) {
			expirationDateHour += 12;
		}

		return CPInstanceLocalServiceUtil.addCPInstance(
			cpDefinition.getCPDefinitionId(), cpDefinition.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), true,
			cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds, 19.77, 19.77,
			9.7, 14.55, BigDecimal.TEN, BigDecimal.TEN, BigDecimal.TEN, true,
			null, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, true, false, false, 1, StringPool.BLANK, null,
			0, serviceContext);
	}

	public static CPInstance addCPDefinitionCPInstanceWithPrice(
			long cpDefinitionId,
			Map<Long, List<Long>>
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			BigDecimal price)
		throws PortalException {

		CPInstance cpInstance = addCPDefinitionCPInstance(
			cpDefinitionId,
			cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds);

		cpInstance.setPrice(price);
		cpInstance.setPromoPrice(BigDecimal.ZERO);
		cpInstance.setCost(BigDecimal.ZERO);

		return CPInstanceLocalServiceUtil.updateCPInstance(cpInstance);
	}

	public static CPDefinition addCPDefinitionFromCatalog(
			long groupId, String productTypeName, boolean ignoreSKUCombinations,
			boolean hasDefaultInstance)
		throws PortalException {

		return _addCPDefinition(
			groupId, productTypeName, ignoreSKUCombinations, hasDefaultInstance,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static CPDefinitionOptionRel addCPDefinitionOptionRel(
			long groupId, long cpDefinitionId, boolean skuContributor,
			int cpDefinitionOptionValueRelsCount)
		throws PortalException {

		CPOption cpOption = addCPOption(groupId, skuContributor);

		for (int i = 0; i < cpDefinitionOptionValueRelsCount; i++) {
			addCPOptionValue(cpOption);
		}

		return addCPDefinitionOptionRel(
			groupId, cpDefinitionId, cpOption.getCPOptionId());
	}

	public static CPDefinitionOptionRel addCPDefinitionOptionRel(
			long groupId, long cpDefinitionId, long cpOptionId)
		throws PortalException {

		return CPDefinitionOptionRelLocalServiceUtil.addCPDefinitionOptionRel(
			cpDefinitionId, cpOptionId, true,
			ServiceContextTestUtil.getServiceContext(groupId));
	}

	public static CPDefinitionOptionValueRel
			addCPDefinitionOptionValueRelWithPrice(
				long groupId, long cpDefinitionId, long cpInstanceId,
				long cpOptionId, String priceType, BigDecimal price,
				int quantity, boolean required, boolean skuContributor,
				ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			CPDefinitionOptionRelLocalServiceUtil.fetchCPDefinitionOptionRel(
				cpDefinitionId, cpOptionId);

		if (cpDefinitionOptionRel == null) {
			cpDefinitionOptionRel =
				CPDefinitionOptionRelLocalServiceUtil.addCPDefinitionOptionRel(
					cpDefinitionId, cpOptionId,
					RandomTestUtil.randomLocaleStringMap(),
					RandomTestUtil.randomLocaleStringMap(),
					getDefaultDDMFormFieldType(true),
					RandomTestUtil.randomDouble(), false, required,
					skuContributor, false, priceType, serviceContext);
		}

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			CPDefinitionOptionValueRelLocalServiceUtil.
				addCPDefinitionOptionValueRel(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
					RandomTestUtil.randomLocaleStringMap(),
					RandomTestUtil.randomDouble(),
					RandomTestUtil.randomString(), serviceContext);

		if ((cpInstanceId == 0) && (priceType != null)) {
			CPInstance cpInstance = addCPInstanceFromCatalog(
				groupId, BigDecimal.valueOf(RandomTestUtil.randomInt()));

			cpInstanceId = cpInstance.getCPInstanceId();
		}

		return CPDefinitionOptionValueRelLocalServiceUtil.
			updateCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId(),
				cpDefinitionOptionValueRel.getNameMap(),
				cpDefinitionOptionValueRel.getPriority(),
				cpDefinitionOptionValueRel.getKey(), cpInstanceId, quantity,
				price, serviceContext);
	}

	public static CPDefinition addCPDefinitionWithChildCPDefinitions(
			long groupId)
		throws Exception {

		return addCPDefinitionWithChildCPDefinitions(groupId, 1);
	}

	public static CPDefinition addCPDefinitionWithChildCPDefinitions(
			long groupId, int priceableOptionsCount)
		throws Exception {

		return addCPDefinitionWithChildCPDefinitions(
			groupId, priceableOptionsCount, null);
	}

	public static CPDefinition addCPDefinitionWithChildCPDefinitions(
			long groupId, int priceableOptionsCount, String priceType)
		throws Exception {

		CPDefinition bundleCPDefinition = addCPDefinitionFromCatalog(
			groupId, SimpleCPTypeConstants.NAME, true, true);

		for (int i = 0; i < priceableOptionsCount; i++) {
			List<CPInstance> cpInstances = _getSimpleCPDefinitionCPInstances(
				groupId, RandomTestUtil.randomInt(2, 5));

			if (Validator.isNull(priceType)) {
				_toPriceableCPDefinitionOptionValueRels(
					groupId, bundleCPDefinition, _getRandomPriceType(),
					cpInstances);

				continue;
			}

			_toPriceableCPDefinitionOptionValueRels(
				groupId, bundleCPDefinition, priceType, cpInstances);
		}

		return bundleCPDefinition;
	}

	public static CPDefinition addCPDefinitionWithChildCPDefinitions(
			long groupId, String priceType)
		throws Exception {

		return addCPDefinitionWithChildCPDefinitions(groupId, 1, priceType);
	}

	public static CPInstance addCPInstance() throws PortalException {
		CPDefinition cpDefinition = _addCPDefinition(
			SimpleCPTypeConstants.NAME, true, true,
			ServiceContextTestUtil.getServiceContext());

		CPInstance cpInstance = CPInstanceLocalServiceUtil.getCPInstance(
			cpDefinition.getCPDefinitionId(), CPInstanceConstants.DEFAULT_SKU);

		_addCommercePriceEntry(cpInstance);

		return cpInstance;
	}

	public static CPInstance addCPInstance(long groupId)
		throws PortalException {

		CPDefinition cpDefinition = _addCPDefinition(
			SimpleCPTypeConstants.NAME, true, true,
			ServiceContextTestUtil.getServiceContext(groupId));

		CPInstance cpInstance = CPInstanceLocalServiceUtil.getCPInstance(
			cpDefinition.getCPDefinitionId(), CPInstanceConstants.DEFAULT_SKU);

		_addCommercePriceEntry(cpInstance);

		return cpInstance;
	}

	public static CPInstance addCPInstanceFromCatalog(long groupId)
		throws PortalException {

		CPDefinition cpDefinition = _addCPDefinition(
			groupId, SimpleCPTypeConstants.NAME, true, true,
			ServiceContextTestUtil.getServiceContext(groupId));

		return CPInstanceLocalServiceUtil.getCPInstance(
			cpDefinition.getCPDefinitionId(), CPInstanceConstants.DEFAULT_SKU);
	}

	public static CPInstance addCPInstanceFromCatalog(
			long groupId, BigDecimal price)
		throws PortalException {

		CPInstance cpInstance = addCPInstanceFromCatalog(groupId);

		cpInstance.setPrice(price);

		cpInstance = CPInstanceLocalServiceUtil.updateCPInstance(cpInstance);

		_addCommercePriceEntry(cpInstance);

		return cpInstance;
	}

	public static CPInstance addCPInstanceFromCatalog(
			long groupId, BigDecimal price, String sku)
		throws PortalException {

		CPInstance cpInstance = addCPInstanceFromCatalog(groupId);

		cpInstance.setSku(sku);
		cpInstance.setPrice(price);

		cpInstance = CPInstanceLocalServiceUtil.updateCPInstance(cpInstance);

		_addCommercePriceEntry(cpInstance);

		return cpInstance;
	}

	public static CPInstance addCPInstanceFromCatalog(
			long groupId, long[] assetCategoryIds)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setAssetCategoryIds(assetCategoryIds);

		CPDefinition cpDefinition = _addCPDefinition(
			groupId, SimpleCPTypeConstants.NAME, true, true, serviceContext);

		return CPInstanceLocalServiceUtil.getCPInstance(
			cpDefinition.getCPDefinitionId(), CPInstanceConstants.DEFAULT_SKU);
	}

	public static CPInstance addCPInstanceWithRandomSku(long groupId)
		throws PortalException {

		String sku = RandomTestUtil.randomString();

		CPDefinition cpDefinition = _addCPDefinitionWithSku(
			SimpleCPTypeConstants.NAME, true,
			ServiceContextTestUtil.getServiceContext(groupId), sku);

		CPInstance cpInstance = CPInstanceLocalServiceUtil.getCPInstance(
			cpDefinition.getCPDefinitionId(), sku);

		_addCommercePriceEntry(cpInstance);

		return cpInstance;
	}

	public static CPInstance addCPInstanceWithRandomSku(
			long groupId, BigDecimal price)
		throws PortalException {

		String sku = RandomTestUtil.randomString();

		CPDefinition cpDefinition = _addCPDefinitionWithSku(
			SimpleCPTypeConstants.NAME, true,
			ServiceContextTestUtil.getServiceContext(groupId), sku);

		CPInstance cpInstance = CPInstanceLocalServiceUtil.getCPInstance(
			cpDefinition.getCPDefinitionId(), sku);

		cpInstance.setPrice(price);

		_addCommercePriceEntry(cpInstance);

		return cpInstance;
	}

	public static CPInstance addCPInstanceWithRandomSkuFromCatalog(long groupId)
		throws PortalException {

		String sku = RandomTestUtil.randomString();

		CPDefinition cpDefinition = _addCPDefinitionWithSku(
			groupId, SimpleCPTypeConstants.NAME, true,
			ServiceContextTestUtil.getServiceContext(groupId), sku);

		return CPInstanceLocalServiceUtil.getCPInstance(
			cpDefinition.getCPDefinitionId(), sku);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), use addCPInstanceWithRandomSku
	 */
	@Deprecated
	public static CPInstance addCPInstanceWithSku(long groupId)
		throws PortalException {

		CPDefinition cpDefinition = _addCPDefinition(
			SimpleCPTypeConstants.NAME, true, true,
			ServiceContextTestUtil.getServiceContext(groupId));

		CPInstance cpInstance = addCPDefinitionCPInstance(
			cpDefinition.getCPDefinitionId(), Collections.emptyMap());

		cpInstance.setStatus(WorkflowConstants.STATUS_APPROVED);

		_addCommercePriceEntry(cpInstance);

		return CPInstanceLocalServiceUtil.updateCPInstance(cpInstance);
	}

	public static CPOption addCPOption(long groupId, boolean skuContributor)
		throws PortalException {

		return addCPOption(
			groupId, getDefaultDDMFormFieldType(skuContributor),
			skuContributor);
	}

	public static List<CPDefinitionOptionRel> addCPOption(
			long groupId, long cpDefinitionId, int cpOptionsCount,
			int cpOptionValuesCount)
		throws Exception {

		List<CPDefinitionOptionRel> cpDefinitionOptionRels = new ArrayList<>();

		for (int i = 0; i < cpOptionsCount; i++) {
			CPOption cpOption = addCPOption(groupId, true);

			for (int j = 0; j < cpOptionValuesCount; j++) {
				addCPOptionValue(cpOption);
			}

			cpDefinitionOptionRels.add(
				addCPDefinitionOptionRel(
					groupId, cpDefinitionId, cpOption.getCPOptionId()));
		}

		return cpDefinitionOptionRels;
	}

	public static CPOption addCPOption(
			long groupId, String ddmFormFieldType, boolean skuContributor)
		throws PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return CPOptionLocalServiceUtil.addCPOption(
			serviceContext.getUserId(), RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), ddmFormFieldType,
			RandomTestUtil.randomBoolean(), RandomTestUtil.randomBoolean(),
			skuContributor, RandomTestUtil.randomString(), null,
			serviceContext);
	}

	public static CPOptionValue addCPOptionValue(CPOption cpOption)
		throws PortalException {

		return CPOptionValueLocalServiceUtil.addCPOptionValue(
			cpOption.getCPOptionId(), RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomDouble(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
	}

	public static void buildCPInstances(CPDefinition cpDefinition)
		throws PortalException {

		CPInstanceLocalServiceUtil.buildCPInstances(
			cpDefinition.getCPDefinitionId(),
			ServiceContextTestUtil.getServiceContext(
				cpDefinition.getGroupId()));
	}

	public static String[] getCPOptionFieldTypes()
		throws ConfigurationException {

		CPOptionConfiguration cpOptionConfiguration =
			_getCPOptionConfiguration();

		return cpOptionConfiguration.ddmFormFieldTypesAllowed();
	}

	public static String getDefaultDDMFormFieldType(boolean skuContributor)
		throws ConfigurationException {

		CPOptionConfiguration cpOptionConfiguration =
			_getCPOptionConfiguration();

		String[] ddmFormFieldTypesAllowed =
			cpOptionConfiguration.ddmFormFieldTypesAllowed();

		if (skuContributor) {
			ddmFormFieldTypesAllowed =
				CPConstants.PRODUCT_OPTION_SKU_CONTRIBUTOR_FIELD_TYPES;
		}

		return ddmFormFieldTypesAllowed[0];
	}

	public static CPDefinitionOptionValueRel
		getRandomCPDefinitionOptionValueRel(long cpDefinitionId) {

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			CPDefinitionOptionRelLocalServiceUtil.getCPDefinitionOptionRels(
				cpDefinitionId);

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRels.get(
				RandomTestUtil.randomInt(0, cpDefinitionOptionRels.size() - 1));

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			cpDefinitionOptionRel.getCPDefinitionOptionValueRels();

		return cpDefinitionOptionValueRels.get(
			RandomTestUtil.randomInt(
				0, cpDefinitionOptionValueRels.size() - 1));
	}

	public static List<CPDefinitionOptionValueRel>
		getRandomCPDefinitionOptionValueRels(long cpDefinitionId) {

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			new ArrayList<>();

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			CPDefinitionOptionRelLocalServiceUtil.getCPDefinitionOptionRels(
				cpDefinitionId);

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			List<CPDefinitionOptionValueRel> sourceCPDefinitionOptionValueRels =
				cpDefinitionOptionRel.getCPDefinitionOptionValueRels();

			cpDefinitionOptionValueRels.add(
				sourceCPDefinitionOptionValueRels.get(
					RandomTestUtil.randomInt(
						0, sourceCPDefinitionOptionValueRels.size() - 1)));
		}

		return cpDefinitionOptionValueRels;
	}

	public static SearchContext getSearchContext(
		String keywords, int status, Group group) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes =
			HashMapBuilder.<String, Serializable>put(
				Field.STATUS, status
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords",
					() -> {
						if (Validator.isNotNull(keywords)) {
							return keywords;
						}

						return StringPool.STAR;
					}
				).build()
			).build();

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(group.getCompanyId());
		searchContext.setGroupIds(new long[] {group.getGroupId()});

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}
		else {
			searchContext.setKeywords(StringPool.STAR);
		}

		return searchContext;
	}

	public static BigDecimal stripTrailingZeros(BigDecimal bigDecimal) {
		if (bigDecimal == null) {
			return bigDecimal;
		}

		return bigDecimal.stripTrailingZeros();
	}

	private static void _addCatalogBaseCommercePriceList(
			long groupId, String currencyCode, String type,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePriceList commerceCatalogBasePriceList =
			CommercePriceListLocalServiceUtil.
				fetchCatalogBaseCommercePriceListByType(groupId, type);

		if (commerceCatalogBasePriceList == null) {
			CommerceCurrency commerceCurrency =
				CommerceCurrencyLocalServiceUtil.getCommerceCurrency(
					serviceContext.getCompanyId(), currencyCode);

			CommercePriceListLocalServiceUtil.addCatalogBaseCommercePriceList(
				groupId, serviceContext.getUserId(),
				commerceCurrency.getCommerceCurrencyId(), type,
				RandomTestUtil.randomString(), serviceContext);
		}
	}

	private static void _addCommercePriceEntry(CPInstance cpInstance)
		throws PortalException {

		CommercePriceList commercePriceList =
			CommercePriceListLocalServiceUtil.fetchCatalogBaseCommercePriceList(
				cpInstance.getGroupId());

		if (commercePriceList == null) {
			return;
		}

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		CommercePriceEntryLocalServiceUtil.addCommercePriceEntry(
			StringPool.BLANK, cpDefinition.getCProductId(),
			cpInstance.getCPInstanceUuid(),
			commercePriceList.getCommercePriceListId(), cpInstance.getPrice(),
			null,
			ServiceContextTestUtil.getServiceContext(cpInstance.getGroupId()));
	}

	private static CPDefinition _addCPDefinition(
			long groupId, String productTypeName, boolean ignoreSKUCombinations,
			boolean hasDefaultInstance, ServiceContext serviceContext)
		throws PortalException {

		String defaultSku = null;

		if (hasDefaultInstance) {
			defaultSku = CPInstanceConstants.DEFAULT_SKU;
		}

		return _addCPDefinitionWithSku(
			groupId, productTypeName, ignoreSKUCombinations, serviceContext,
			defaultSku);
	}

	private static CPDefinition _addCPDefinition(
			String productTypeName, boolean ignoreSKUCombinations,
			boolean hasDefaultInstance, ServiceContext serviceContext)
		throws PortalException {

		User user = UserLocalServiceUtil.getUser(serviceContext.getUserId());

		List<CommerceCatalog> commerceCatalogs =
			CommerceCatalogLocalServiceUtil.getCommerceCatalogs(
				user.getCompanyId(), true);

		CommerceCatalog commerceCatalog = commerceCatalogs.get(0);

		_addCatalogBaseCommercePriceList(
			commerceCatalog.getGroupId(),
			commerceCatalog.getCommerceCurrencyCode(),
			CommercePriceListConstants.TYPE_PRICE_LIST, serviceContext);

		_addCatalogBaseCommercePriceList(
			commerceCatalog.getGroupId(),
			commerceCatalog.getCommerceCurrencyCode(),
			CommercePriceListConstants.TYPE_PROMOTION, serviceContext);

		return _addCPDefinition(
			commerceCatalog.getGroupId(), productTypeName,
			ignoreSKUCombinations, hasDefaultInstance,
			ServiceContextTestUtil.getServiceContext(
				commerceCatalog.getGroupId()));
	}

	private static CPDefinition _addCPDefinitionWithSku(
			long groupId, String productTypeName, boolean ignoreSKUCombinations,
			ServiceContext serviceContext, String sku)
		throws PortalException {

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
		boolean shippable = RandomTestUtil.randomBoolean();
		boolean freeShipping = RandomTestUtil.randomBoolean();
		boolean shipSeparately = RandomTestUtil.randomBoolean();
		double shippingExtraPrice = RandomTestUtil.randomDouble();
		double width = RandomTestUtil.randomDouble();
		double height = RandomTestUtil.randomDouble();
		double depth = RandomTestUtil.randomDouble();
		double weight = RandomTestUtil.randomDouble();
		long cpTaxCategoryId = 0;
		boolean taxExempt = RandomTestUtil.randomBoolean();
		boolean telcoOrElectronics = RandomTestUtil.randomBoolean();
		String ddmStructureKey = null;
		boolean published = true;

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

		CPDefinition cpDefinition =
			CPDefinitionLocalServiceUtil.addCPDefinition(
				groupId, user.getUserId(), titleMap, shortDescriptionMap,
				descriptionMap, urlTitleMap, metaTitleMap, metaKeywordsMap,
				metaDescriptionMap, productTypeName, ignoreSKUCombinations,
				shippable, freeShipping, shipSeparately, shippingExtraPrice,
				width, height, depth, weight, cpTaxCategoryId, taxExempt,
				telcoOrElectronics, ddmStructureKey, published,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, false, sku, false, 0, null, null, 0L,
				null, serviceContext);

		CPDefinitionInventory cpDefinitionInventory =
			CPDefinitionInventoryLocalServiceUtil.
				fetchCPDefinitionInventoryByCPDefinitionId(
					cpDefinition.getCPDefinitionId());

		if (cpDefinitionInventory != null) {
			cpDefinitionInventory.setBackOrders(false);

			CPDefinitionInventoryLocalServiceUtil.updateCPDefinitionInventory(
				cpDefinitionInventory);
		}

		return cpDefinition;
	}

	private static CPDefinition _addCPDefinitionWithSku(
			String productTypeName, boolean ignoreSKUCombinations,
			ServiceContext serviceContext, String sku)
		throws PortalException {

		User user = UserLocalServiceUtil.getUser(serviceContext.getUserId());

		List<CommerceCatalog> commerceCatalogs =
			CommerceCatalogLocalServiceUtil.getCommerceCatalogs(
				user.getCompanyId(), true);

		CommerceCatalog commerceCatalog = commerceCatalogs.get(0);

		_addCatalogBaseCommercePriceList(
			commerceCatalog.getGroupId(),
			commerceCatalog.getCommerceCurrencyCode(),
			CommercePriceListConstants.TYPE_PRICE_LIST, serviceContext);

		_addCatalogBaseCommercePriceList(
			commerceCatalog.getGroupId(),
			commerceCatalog.getCommerceCurrencyCode(),
			CommercePriceListConstants.TYPE_PROMOTION, serviceContext);

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
		boolean shippable = true;
		boolean freeShipping = false;
		boolean shipSeparately = RandomTestUtil.randomBoolean();
		double shippingExtraPrice = RandomTestUtil.randomDouble();
		double width = RandomTestUtil.randomDouble();
		double height = RandomTestUtil.randomDouble();
		double depth = RandomTestUtil.randomDouble();
		double weight = RandomTestUtil.randomDouble();
		long cpTaxCategoryId = 0;
		boolean taxExempt = RandomTestUtil.randomBoolean();
		boolean telcoOrElectronics = RandomTestUtil.randomBoolean();
		String ddmStructureKey = null;
		boolean published = true;

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

		return CPDefinitionLocalServiceUtil.addCPDefinition(
			commerceCatalog.getGroupId(), user.getUserId(), titleMap,
			shortDescriptionMap, descriptionMap, urlTitleMap, metaTitleMap,
			metaKeywordsMap, metaDescriptionMap, productTypeName,
			ignoreSKUCombinations, shippable, freeShipping, shipSeparately,
			shippingExtraPrice, width, height, depth, weight, cpTaxCategoryId,
			taxExempt, telcoOrElectronics, ddmStructureKey, published,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute, false,
			sku, false, 0, null, null, 0L, null, serviceContext);
	}

	private static CPOptionConfiguration _getCPOptionConfiguration()
		throws ConfigurationException {

		return ConfigurationProviderUtil.getConfiguration(
			CPOptionConfiguration.class,
			new SystemSettingsLocator(CPConstants.CP_OPTION_SERVICE_NAME));
	}

	private static CPInstance _getRandomApprovedCPInstance(
		long cpDefinitionId) {

		List<CPInstance> cpDefinitionApprovedCPInstances =
			CPInstanceLocalServiceUtil.getCPDefinitionApprovedCPInstances(
				cpDefinitionId);

		return cpDefinitionApprovedCPInstances.get(0);
	}

	private static String _getRandomPriceType() {
		if (RandomTestUtil.randomBoolean()) {
			return CPConstants.PRODUCT_OPTION_PRICE_TYPE_DYNAMIC;
		}

		return CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC;
	}

	private static List<CPInstance> _getSimpleCPDefinitionCPInstances(
			long groupId, int size)
		throws Exception {

		List<CPInstance> cpInstances = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			CPDefinition optionACPDefinition = addCPDefinitionFromCatalog(
				groupId, SimpleCPTypeConstants.NAME, true, true);

			CPInstance cpInstance = _getRandomApprovedCPInstance(
				optionACPDefinition.getCPDefinitionId());

			cpInstance.setPurchasable(true);
			cpInstance.setPrice(new BigDecimal(RandomTestUtil.randomDouble()));

			cpInstances.add(
				CPInstanceLocalServiceUtil.updateCPInstance(cpInstance));
		}

		return cpInstances;
	}

	private static List<CPDefinitionOptionValueRel>
			_toPriceableCPDefinitionOptionValueRels(
				long groupId, CPDefinition parentCPDefinition, String priceType,
				List<CPInstance> childCPInstances)
		throws Exception {

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			new ArrayList<>();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		CPOption priceableCPOption = addCPOption(
			groupId, getDefaultDDMFormFieldType(true), true);

		CPDefinitionOptionRel cpDefinitionOptionRel =
			CPDefinitionOptionRelLocalServiceUtil.addCPDefinitionOptionRel(
				parentCPDefinition.getCPDefinitionId(),
				priceableCPOption.getCPOptionId(),
				RandomTestUtil.randomLocaleStringMap(),
				RandomTestUtil.randomLocaleStringMap(),
				getDefaultDDMFormFieldType(true), RandomTestUtil.nextDouble(),
				false, false, false, false, priceType, serviceContext);

		for (CPInstance cpInstance : childCPInstances) {
			CPDefinitionOptionValueRel cpInstanceOptionValueRel =
				CPDefinitionOptionValueRelLocalServiceUtil.
					addCPDefinitionOptionValueRel(
						cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
						RandomTestUtil.randomLocaleStringMap(),
						RandomTestUtil.nextDouble(),
						RandomTestUtil.randomString(), serviceContext);

			BigDecimal price = null;

			if (Objects.equals(
					priceType, CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC)) {

				price = new BigDecimal(RandomTestUtil.randomDouble());
			}

			cpDefinitionOptionValueRels.add(
				CPDefinitionOptionValueRelLocalServiceUtil.
					updateCPDefinitionOptionValueRel(
						cpInstanceOptionValueRel.
							getCPDefinitionOptionValueRelId(),
						cpInstanceOptionValueRel.getNameMap(),
						cpInstanceOptionValueRel.getPriority(),
						cpInstanceOptionValueRel.getKey(),
						cpInstance.getCPInstanceId(), 2, price,
						serviceContext));
		}

		return cpDefinitionOptionValueRels;
	}

}