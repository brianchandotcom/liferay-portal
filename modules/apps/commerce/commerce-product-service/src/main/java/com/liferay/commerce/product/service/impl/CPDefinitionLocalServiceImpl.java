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

package com.liferay.commerce.product.service.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetLinkLocalService;
import com.liferay.commerce.product.configuration.CProductVersionConfiguration;
import com.liferay.commerce.product.constants.CPAttachmentFileEntryConstants;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.exception.CPDefinitionDisplayDateException;
import com.liferay.commerce.product.exception.CPDefinitionExpirationDateException;
import com.liferay.commerce.product.exception.CPDefinitionIgnoreSKUCombinationsException;
import com.liferay.commerce.product.exception.CPDefinitionMetaDescriptionException;
import com.liferay.commerce.product.exception.CPDefinitionMetaKeywordsException;
import com.liferay.commerce.product.exception.CPDefinitionMetaTitleException;
import com.liferay.commerce.product.exception.CPDefinitionProductTypeNameException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.model.CPDefinitionLocalization;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.impl.CPDefinitionImpl;
import com.liferay.commerce.product.model.impl.CPDefinitionModelImpl;
import com.liferay.commerce.product.service.base.CPDefinitionLocalServiceBaseImpl;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.CPTypeServicesTracker;
import com.liferay.commerce.product.util.CPVersionContributor;
import com.liferay.commerce.product.util.CPVersionContributorRegistryUtil;
import com.liferay.dynamic.data.mapping.exception.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.model.FriendlyURLEntryLocalization;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.MultiValueFacet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionLocalServiceImpl
	extends CPDefinitionLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinition addCPDefinition(
			String externalReferenceCode, long groupId, long userId,
			Map<Locale, String> nameMap,
			Map<Locale, String> shortDescriptionMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
			Map<Locale, String> metaTitleMap,
			Map<Locale, String> metaDescriptionMap,
			Map<Locale, String> metaKeywordsMap, String productTypeName,
			boolean ignoreSKUCombinations, boolean shippable,
			boolean freeShipping, boolean shipSeparately,
			double shippingExtraPrice, double width, double height,
			double depth, double weight, long cpTaxCategoryId,
			boolean taxExempt, boolean telcoOrElectronics,
			String ddmStructureKey, boolean published, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String defaultSku, boolean subscriptionEnabled,
			int subscriptionLength, String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, boolean deliverySubscriptionEnabled,
			int deliverySubscriptionLength, String deliverySubscriptionType,
			UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties,
			long deliveryMaxSubscriptionCycles, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition

		User user = userLocalService.getUser(userId);

		Date expirationDate = null;
		Date date = new Date();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CPDefinitionDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CPDefinitionExpirationDateException.class);
		}

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		validate(
			groupId, ddmStructureKey, metaTitleMap, metaDescriptionMap,
			metaKeywordsMap, displayDate, expirationDate, productTypeName);

		long cpDefinitionId = counterLocalService.increment();

		CPDefinition cpDefinition = cpDefinitionPersistence.create(
			cpDefinitionId);

		CProduct cProduct = cProductLocalService.addCProduct(
			externalReferenceCode, groupId, userId, new ServiceContext());

		cpDefinition.setGroupId(groupId);
		cpDefinition.setCompanyId(user.getCompanyId());
		cpDefinition.setUserId(user.getUserId());
		cpDefinition.setUserName(user.getFullName());
		cpDefinition.setCProductId(cProduct.getCProductId());
		cpDefinition.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition.setProductTypeName(productTypeName);
		cpDefinition.setIgnoreSKUCombinations(ignoreSKUCombinations);
		cpDefinition.setShippable(shippable);
		cpDefinition.setFreeShipping(freeShipping);
		cpDefinition.setShipSeparately(shipSeparately);
		cpDefinition.setShippingExtraPrice(shippingExtraPrice);
		cpDefinition.setWidth(width);
		cpDefinition.setHeight(height);
		cpDefinition.setDepth(depth);
		cpDefinition.setWeight(weight);
		cpDefinition.setTaxExempt(taxExempt);
		cpDefinition.setTelcoOrElectronics(telcoOrElectronics);
		cpDefinition.setDDMStructureKey(ddmStructureKey);
		cpDefinition.setPublished(published);
		cpDefinition.setDisplayDate(displayDate);
		cpDefinition.setExpirationDate(expirationDate);
		cpDefinition.setSubscriptionEnabled(subscriptionEnabled);
		cpDefinition.setSubscriptionLength(subscriptionLength);
		cpDefinition.setSubscriptionType(subscriptionType);
		cpDefinition.setSubscriptionTypeSettingsProperties(
			subscriptionTypeSettingsUnicodeProperties);
		cpDefinition.setMaxSubscriptionCycles(maxSubscriptionCycles);
		cpDefinition.setDeliverySubscriptionEnabled(
			deliverySubscriptionEnabled);
		cpDefinition.setDeliverySubscriptionLength(deliverySubscriptionLength);
		cpDefinition.setDeliverySubscriptionType(deliverySubscriptionType);
		cpDefinition.setDeliverySubscriptionTypeSettingsProperties(
			deliverySubscriptionTypeSettingsUnicodeProperties);
		cpDefinition.setDeliveryMaxSubscriptionCycles(
			deliveryMaxSubscriptionCycles);
		cpDefinition.setAccountGroupFilterEnabled(false);
		cpDefinition.setChannelFilterEnabled(false);
		cpDefinition.setVersion(1);

		if ((expirationDate == null) || expirationDate.after(date)) {
			cpDefinition.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			cpDefinition.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		cpDefinition.setStatusByUserId(user.getUserId());
		cpDefinition.setStatusDate(serviceContext.getModifiedDate(date));
		cpDefinition.setExpandoBridgeAttributes(serviceContext);

		cpDefinition = cpDefinitionPersistence.update(cpDefinition);

		// Commerce product definition localization

		_addCPDefinitionLocalizedFields(
			user.getCompanyId(), cpDefinitionId, nameMap, shortDescriptionMap,
			descriptionMap, metaTitleMap, metaDescriptionMap, metaKeywordsMap);

		// Commerce product instance

		if (Validator.isNotNull(defaultSku)) {
			ServiceContext cpInstanceServiceContext = new ServiceContext();

			cpInstanceServiceContext.setScopeGroupId(groupId);
			cpInstanceServiceContext.setUserId(userId);

			cpInstanceLocalService.addCPInstance(
				externalReferenceCode, cpDefinitionId, groupId, defaultSku,
				null, null, published, null, cpDefinition.getWidth(),
				cpDefinition.getHeight(), cpDefinition.getDepth(),
				cpDefinition.getWeight(), BigDecimal.ZERO, BigDecimal.ZERO,
				BigDecimal.ZERO, published, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				cpInstanceServiceContext);
		}

		// Friendly URL

		Group companyGroup = _groupLocalService.getCompanyGroup(
			cpDefinition.getCompanyId());

		Map<String, String> newURLTitleMap = new HashMap<>();

		if (MapUtil.isEmpty(urlTitleMap)) {
			newURLTitleMap = _getUniqueUrlTitles(cpDefinition, nameMap);
		}
		else {
			newURLTitleMap = _getUniqueUrlTitles(cpDefinition, urlTitleMap);
		}

		_friendlyURLEntryLocalService.addFriendlyURLEntry(
			companyGroup.getGroupId(),
			classNameLocalService.getClassNameId(CProduct.class),
			cProduct.getCProductId(), newURLTitleMap, serviceContext);

		// Asset

		updateAsset(
			user.getUserId(), cpDefinition,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		// Workflow

		return startWorkflowInstance(
			user.getUserId(), cpDefinition, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinition addCPDefinition(
			String externalReferenceCode, long groupId, long userId,
			Map<Locale, String> nameMap,
			Map<Locale, String> shortDescriptionMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
			Map<Locale, String> metaTitleMap,
			Map<Locale, String> metaDescriptionMap,
			Map<Locale, String> metaKeywordsMap, String productTypeName,
			boolean ignoreSKUCombinations, boolean shippable,
			boolean freeShipping, boolean shipSeparately,
			double shippingExtraPrice, double width, double height,
			double depth, double weight, long cpTaxCategoryId,
			boolean taxExempt, boolean telcoOrElectronics,
			String ddmStructureKey, boolean published, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String defaultSku, boolean subscriptionEnabled,
			int subscriptionLength, String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, ServiceContext serviceContext)
		throws PortalException {

		return cpDefinitionLocalService.addCPDefinition(
			externalReferenceCode, groupId, userId, nameMap,
			shortDescriptionMap, descriptionMap, urlTitleMap, metaTitleMap,
			metaDescriptionMap, metaKeywordsMap, productTypeName,
			ignoreSKUCombinations, shippable, freeShipping, shipSeparately,
			shippingExtraPrice, width, height, depth, weight, cpTaxCategoryId,
			taxExempt, telcoOrElectronics, ddmStructureKey, published,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, defaultSku, subscriptionEnabled, subscriptionLength,
			subscriptionType, subscriptionTypeSettingsUnicodeProperties,
			maxSubscriptionCycles, false, 1, null, null, 0, serviceContext);
	}

	@Override
	public CPDefinition addOrUpdateCPDefinition(
			String externalReferenceCode, long groupId, long userId,
			Map<Locale, String> nameMap,
			Map<Locale, String> shortDescriptionMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
			Map<Locale, String> metaTitleMap,
			Map<Locale, String> metaDescriptionMap,
			Map<Locale, String> metaKeywordsMap, String productTypeName,
			boolean ignoreSKUCombinations, boolean shippable,
			boolean freeShipping, boolean shipSeparately,
			double shippingExtraPrice, double width, double height,
			double depth, double weight, long cpTaxCategoryId,
			boolean taxExempt, boolean telcoOrElectronics,
			String ddmStructureKey, boolean published, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String defaultSku, boolean subscriptionEnabled,
			int subscriptionLength, String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, boolean deliverySubscriptionEnabled,
			int deliverySubscriptionLength, String deliverySubscriptionType,
			UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties,
			long deliveryMaxSubscriptionCycles, ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}
		else {
			CProduct cProduct =
				cProductLocalService.fetchCProductByReferenceCode(
					serviceContext.getCompanyId(), externalReferenceCode);

			if (cProduct != null) {
				CPDefinition cpDefinition =
					cpDefinitionLocalService.updateCPDefinition(
						cProduct.getPublishedCPDefinitionId(), nameMap,
						shortDescriptionMap, descriptionMap, urlTitleMap,
						metaTitleMap, metaDescriptionMap, metaKeywordsMap,
						ignoreSKUCombinations, shippable, freeShipping,
						shipSeparately, shippingExtraPrice, width, height,
						depth, weight, cpTaxCategoryId, taxExempt,
						telcoOrElectronics, ddmStructureKey, published,
						displayDateMonth, displayDateDay, displayDateYear,
						displayDateHour, displayDateMinute, expirationDateMonth,
						expirationDateDay, expirationDateYear,
						expirationDateHour, expirationDateMinute, neverExpire,
						serviceContext);

				return cpDefinitionLocalService.updateSubscriptionInfo(
					cpDefinition.getCPDefinitionId(), subscriptionEnabled,
					subscriptionLength, subscriptionType,
					subscriptionTypeSettingsUnicodeProperties,
					maxSubscriptionCycles, deliverySubscriptionEnabled,
					deliverySubscriptionLength, deliverySubscriptionType,
					deliverySubscriptionTypeSettingsUnicodeProperties,
					deliveryMaxSubscriptionCycles);
			}
		}

		return cpDefinitionLocalService.addCPDefinition(
			externalReferenceCode, groupId, userId, nameMap,
			shortDescriptionMap, descriptionMap, urlTitleMap, metaTitleMap,
			metaDescriptionMap, metaKeywordsMap, productTypeName,
			ignoreSKUCombinations, shippable, freeShipping, shipSeparately,
			shippingExtraPrice, width, height, depth, weight, cpTaxCategoryId,
			taxExempt, telcoOrElectronics, ddmStructureKey, published,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, defaultSku, subscriptionEnabled, subscriptionLength,
			subscriptionType, subscriptionTypeSettingsUnicodeProperties,
			maxSubscriptionCycles, deliverySubscriptionEnabled,
			deliverySubscriptionLength, deliverySubscriptionType,
			deliverySubscriptionTypeSettingsUnicodeProperties,
			deliveryMaxSubscriptionCycles, serviceContext);
	}

	@Override
	public CPDefinition addOrUpdateCPDefinition(
			String externalReferenceCode, long groupId, long userId,
			Map<Locale, String> nameMap,
			Map<Locale, String> shortDescriptionMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
			Map<Locale, String> metaTitleMap,
			Map<Locale, String> metaDescriptionMap,
			Map<Locale, String> metaKeywordsMap, String productTypeName,
			boolean ignoreSKUCombinations, boolean shippable,
			boolean freeShipping, boolean shipSeparately,
			double shippingExtraPrice, double width, double height,
			double depth, double weight, long cpTaxCategoryId,
			boolean taxExempt, boolean telcoOrElectronics,
			String ddmStructureKey, boolean published, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String defaultSku, boolean subscriptionEnabled,
			int subscriptionLength, String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, ServiceContext serviceContext)
		throws PortalException {

		return cpDefinitionLocalService.addOrUpdateCPDefinition(
			externalReferenceCode, groupId, userId, nameMap,
			shortDescriptionMap, descriptionMap, urlTitleMap, metaTitleMap,
			metaDescriptionMap, metaKeywordsMap, productTypeName,
			ignoreSKUCombinations, shippable, freeShipping, shipSeparately,
			shippingExtraPrice, width, height, depth, weight, cpTaxCategoryId,
			taxExempt, telcoOrElectronics, ddmStructureKey, published,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, defaultSku, subscriptionEnabled, subscriptionLength,
			subscriptionType, subscriptionTypeSettingsUnicodeProperties,
			maxSubscriptionCycles, false, 1, null, null, 0, serviceContext);
	}

	@Override
	public void checkCPDefinitions() throws PortalException {
		checkCPDefinitionsByDisplayDate();
		checkCPDefinitionsByExpirationDate();
	}

	@Override
	public CPDefinition copyCPDefinition(long cpDefinitionId)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		return cpDefinitionLocalService.copyCPDefinition(
			cpDefinitionId, cpDefinition.getGroupId());
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinition copyCPDefinition(long cpDefinitionId, long groupId)
		throws PortalException {

		// CPDefinition

		CPDefinition originalCPDefinition =
			cpDefinitionLocalService.getCPDefinition(cpDefinitionId);

		CPDefinition newCPDefinition =
			(CPDefinition)originalCPDefinition.clone();

		long newCPDefinitionId = counterLocalService.increment();

		newCPDefinition.setCPDefinitionId(newCPDefinitionId);

		if (originalCPDefinition.isPublished() &&
			cpDefinitionLocalService.isVersionable(originalCPDefinition)) {

			originalCPDefinition.setPublished(false);

			originalCPDefinition = cpDefinitionPersistence.update(
				originalCPDefinition);

			newCPDefinition.setVersion(
				cProductLocalService.increment(
					originalCPDefinition.getCProductId()));
		}
		else {
			CProduct originalCProduct = originalCPDefinition.getCProduct();

			CProduct newCProduct = (CProduct)originalCProduct.clone();

			newCProduct.setCProductId(counterLocalService.increment());
			newCProduct.setUuid(PortalUUIDUtil.generate());
			newCProduct.setPublishedCPDefinitionId(newCPDefinitionId);

			newCPDefinition.setCProductId(newCProduct.getCProductId());

			cProductPersistence.update(newCProduct);
		}

		newCPDefinition.setGroupId(groupId);
		newCPDefinition.setUuid(PortalUUIDUtil.generate());

		newCPDefinition = cpDefinitionPersistence.update(newCPDefinition);

		// AssetEntry

		long cpDefinitionClassNameId = classNameLocalService.getClassNameId(
			CPDefinition.class);

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			cpDefinitionClassNameId, cpDefinitionId);

		if (assetEntry != null) {
			AssetEntry newAssetEntry = (AssetEntry)assetEntry.clone();

			newAssetEntry.setEntryId(counterLocalService.increment());

			newAssetEntry.setClassPK(newCPDefinitionId);

			_assetEntryLocalService.addAssetEntry(newAssetEntry);
		}

		// CPDefinitionLocalization

		List<CPDefinitionLocalization> cpDefinitionLocalizations =
			cpDefinitionLocalizationPersistence.findByCPDefinitionId(
				cpDefinitionId);

		for (CPDefinitionLocalization cpDefinitionLocalization :
				cpDefinitionLocalizations) {

			CPDefinitionLocalization newCPDefinitionLocalization =
				(CPDefinitionLocalization)cpDefinitionLocalization.clone();

			newCPDefinitionLocalization.setCpDefinitionLocalizationId(
				counterLocalService.increment());
			newCPDefinitionLocalization.setCPDefinitionId(newCPDefinitionId);

			if (originalCPDefinition.getCProductId() !=
					newCPDefinition.getCProductId()) {

				newCPDefinitionLocalization.setName(
					LanguageUtil.format(
						LocaleUtil.fromLanguageId(
							newCPDefinitionLocalization.getLanguageId()),
						"copy-of-x", newCPDefinitionLocalization.getName()));
			}

			cpDefinitionLocalizationPersistence.update(
				newCPDefinitionLocalization);
		}

		// CPAttachmentFileEntry

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			cpAttachmentFileEntryPersistence.findByC_C(
				cpDefinitionClassNameId, cpDefinitionId);

		for (CPAttachmentFileEntry cpAttachmentFileEntry :
				cpAttachmentFileEntries) {

			CPAttachmentFileEntry newCPAttachmentFileEntry =
				(CPAttachmentFileEntry)cpAttachmentFileEntry.clone();

			newCPAttachmentFileEntry.setUuid(PortalUUIDUtil.generate());
			newCPAttachmentFileEntry.setCPAttachmentFileEntryId(
				counterLocalService.increment());

			newCPAttachmentFileEntry.setClassPK(newCPDefinitionId);

			cpAttachmentFileEntryPersistence.update(newCPAttachmentFileEntry);
		}

		// CPDefinitionLink

		List<CPDefinitionLink> cpDefinitionLinks =
			cpDefinitionLinkPersistence.findByCPDefinitionId(cpDefinitionId);

		for (CPDefinitionLink cpDefinitionLink : cpDefinitionLinks) {
			CPDefinitionLink newCPDefinitionLink =
				(CPDefinitionLink)cpDefinitionLink.clone();

			newCPDefinitionLink.setUuid(PortalUUIDUtil.generate());
			newCPDefinitionLink.setCPDefinitionLinkId(
				counterLocalService.increment());

			newCPDefinitionLink.setCPDefinitionId(newCPDefinitionId);

			cpDefinitionLinkPersistence.update(newCPDefinitionLink);
		}

		// CPDefinitionOptionRel

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			cpDefinitionOptionRelPersistence.findByCPDefinitionId(
				cpDefinitionId);

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			CPDefinitionOptionRel newCPDefinitionOptionRel =
				(CPDefinitionOptionRel)cpDefinitionOptionRel.clone();

			newCPDefinitionOptionRel.setUuid(PortalUUIDUtil.generate());

			long newCPDefinitionOptionRelId = counterLocalService.increment();

			newCPDefinitionOptionRel.setCPDefinitionOptionRelId(
				newCPDefinitionOptionRelId);

			newCPDefinitionOptionRel.setCPDefinitionId(newCPDefinitionId);

			newCPDefinitionOptionRel = cpDefinitionOptionRelPersistence.update(
				newCPDefinitionOptionRel);

			// CPDefinitionOptionValueRel

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionValueRelPersistence.
					findByCPDefinitionOptionRelId(
						cpDefinitionOptionRel.getCPDefinitionOptionRelId());

			for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
					cpDefinitionOptionValueRels) {

				CPDefinitionOptionValueRel newCPDefinitionOptionValueRel =
					(CPDefinitionOptionValueRel)
						cpDefinitionOptionValueRel.clone();

				newCPDefinitionOptionValueRel.setUuid(
					PortalUUIDUtil.generate());
				newCPDefinitionOptionValueRel.setCPDefinitionOptionValueRelId(
					counterLocalService.increment());

				newCPDefinitionOptionValueRel.setCPDefinitionOptionRelId(
					newCPDefinitionOptionRelId);

				cpDefinitionOptionValueRelPersistence.update(
					newCPDefinitionOptionValueRel);
			}

			reindexCPDefinitionOptionValueRels(newCPDefinitionOptionRel);
		}

		reindexCPDefinitionOptionRels(newCPDefinition);

		// CPDefinitionSpecificationOptionValue

		List<CPDefinitionSpecificationOptionValue>
			cpDefinitionSpecificationOptionValues =
				cpDefinitionSpecificationOptionValuePersistence.
					findByCPDefinitionId(cpDefinitionId);

		for (CPDefinitionSpecificationOptionValue
				cpDefinitionSpecificationOptionValue :
					cpDefinitionSpecificationOptionValues) {

			CPDefinitionSpecificationOptionValue
				newCPDefinitionSpecificationOptionValue =
					(CPDefinitionSpecificationOptionValue)
						cpDefinitionSpecificationOptionValue.clone();

			newCPDefinitionSpecificationOptionValue.setUuid(
				PortalUUIDUtil.generate());
			newCPDefinitionSpecificationOptionValue.
				setCPDefinitionSpecificationOptionValueId(
					counterLocalService.increment());

			newCPDefinitionSpecificationOptionValue.setCPDefinitionId(
				newCPDefinitionId);

			cpDefinitionSpecificationOptionValuePersistence.update(
				newCPDefinitionSpecificationOptionValue);
		}

		// CPDisplayLayout

		List<CPDisplayLayout> cpDisplayLayouts =
			cpDisplayLayoutPersistence.findByC_C(
				cpDefinitionClassNameId, cpDefinitionId);

		for (CPDisplayLayout cpDisplayLayout : cpDisplayLayouts) {
			CPDisplayLayout newCPDisplayLayout =
				(CPDisplayLayout)cpDisplayLayout.clone();

			newCPDisplayLayout.setUuid(PortalUUIDUtil.generate());
			newCPDisplayLayout.setCPDisplayLayoutId(
				counterLocalService.increment());

			newCPDisplayLayout.setClassPK(newCPDefinitionId);

			cpDisplayLayoutPersistence.update(newCPDisplayLayout);
		}

		// CPInstance

		List<CPInstance> cpInstances =
			cpInstancePersistence.findByCPDefinitionId(cpDefinitionId);

		for (CPInstance cpInstance : cpInstances) {
			CPInstance newCPInstance = (CPInstance)cpInstance.clone();

			newCPInstance.setUuid(PortalUUIDUtil.generate());
			newCPInstance.setCPInstanceId(counterLocalService.increment());

			newCPInstance.setCPDefinitionId(newCPDefinitionId);

			cpInstancePersistence.update(newCPInstance);
		}

		// CPVersionContributors

		List<CPVersionContributor> cpVersionContributors =
			CPVersionContributorRegistryUtil.getCPVersionContributors();

		for (CPVersionContributor cpVersionContributor :
				cpVersionContributors) {

			cpVersionContributor.onUpdate(cpDefinitionId, newCPDefinitionId);
		}

		// CProduct

		if (cpDefinitionLocalService.isVersionable(originalCPDefinition)) {
			cProductLocalService.updatePublishedCPDefinitionId(
				newCPDefinition.getCProductId(),
				newCPDefinition.getCPDefinitionId());

			long cProductId = newCPDefinition.getCProductId();

			TransactionCommitCallbackUtil.registerCallback(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						cpDefinitionLocalService.maintainVersionThreshold(
							cProductId);

						return null;
					}

				});
		}

		return newCPDefinition;
	}

	@Override
	public void deleteAssetCategoryCPDefinition(
			long cpDefinitionId, long categoryId, ServiceContext serviceContext)
		throws PortalException {

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			CPDefinition.class.getName(), cpDefinitionId);

		long[] categoryIds = ArrayUtil.remove(
			assetEntry.getCategoryIds(), categoryId);

		serviceContext.setAssetCategoryIds(categoryIds);

		serviceContext.setAssetTagNames(assetEntry.getTagNames());

		cpDefinitionLocalService.updateCPDefinitionCategorization(
			cpDefinitionId, serviceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinition deleteCPDefinition(CPDefinition cpDefinition)
		throws PortalException {

		// Commerce product

		int cpDefinitionsCount = cpDefinitionPersistence.countByC_S(
			cpDefinition.getCProductId(), WorkflowConstants.STATUS_ANY);

		if (!_isVersioningEnabled() ||
			(_isVersioningEnabled() && (cpDefinitionsCount == 1))) {

			cProductLocalService.deleteCProduct(cpDefinition.getCProductId());
		}

		// Commerce product definition specification option values

		cpDefinitionSpecificationOptionValueLocalService.
			deleteCPDefinitionSpecificationOptionValues(
				cpDefinition.getCPDefinitionId());

		// Commerce product instances

		cpInstanceLocalService.deleteCPInstances(
			cpDefinition.getCPDefinitionId());

		// Commerce product definition option rels

		cpDefinitionOptionRelLocalService.deleteCPDefinitionOptionRels(
			cpDefinition.getCPDefinitionId());

		// Commerce product definition attachment file entries

		cpAttachmentFileEntryLocalService.deleteCPAttachmentFileEntries(
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		// Commerce product definition links

		cpDefinitionLinkLocalService.deleteCPDefinitionLinksByCPDefinitionId(
			cpDefinition.getCPDefinitionId());

		// Commerce product type

		CPType cpType = _cpTypeServicesTracker.getCPType(
			cpDefinition.getProductTypeName());

		if (cpType != null) {
			cpType.deleteCPDefinition(cpDefinition.getCPDefinitionId());
		}

		// Commerce product friendly URL entries

		Group companyGroup = _groupLocalService.getCompanyGroup(
			cpDefinition.getCompanyId());

		_friendlyURLEntryLocalService.deleteFriendlyURLEntry(
			companyGroup.getGroupId(), CProduct.class,
			cpDefinition.getCProductId());

		// Commerce product display layouts

		cpDisplayLayoutLocalService.deleteCPDisplayLayouts(
			CPDefinition.class, cpDefinition.getCPDefinitionId());

		// Commerce product version contributors

		List<CPVersionContributor> cpVersionContributors =
			CPVersionContributorRegistryUtil.getCPVersionContributors();

		for (CPVersionContributor cpVersionContributor :
				cpVersionContributors) {

			cpVersionContributor.onDelete(cpDefinition.getCPDefinitionId());
		}

		// Commerce product definition

		cpDefinitionPersistence.remove(cpDefinition);

		// Asset

		_assetEntryLocalService.deleteEntry(
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		// Expando

		_expandoRowLocalService.deleteRows(cpDefinition.getCPDefinitionId());

		// Workflow

		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			cpDefinition.getCompanyId(), cpDefinition.getGroupId(),
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		return cpDefinition;
	}

	@Override
	public CPDefinition deleteCPDefinition(long cpDefinitionId)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionPersistence.findByPrimaryKey(
			cpDefinitionId);

		return cpDefinitionLocalService.deleteCPDefinition(cpDefinition);
	}

	@Override
	public void deleteCPDefinitions(long companyId) throws PortalException {
		List<CPDefinition> cpDefinitions =
			cpDefinitionPersistence.findByCompanyId(companyId);

		for (CPDefinition cpDefinition : cpDefinitions) {
			cpDefinitionLocalService.deleteCPDefinition(cpDefinition);
		}
	}

	@Override
	public void deleteCPDefinitions(long cProductId, int status) {
		cpDefinitionPersistence.removeByC_S(cProductId, status);
	}

	@Override
	public CPDefinition fetchCPDefinitionByCProductExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		if (Validator.isNull(externalReferenceCode)) {
			return null;
		}

		CProduct cProduct = cProductLocalService.fetchCProductByReferenceCode(
			companyId, externalReferenceCode);

		if (cProduct == null) {
			return null;
		}

		CPDefinition cpDefinition = cpDefinitionPersistence.fetchByPrimaryKey(
			cProduct.getPublishedCPDefinitionId());

		if (cpDefinition != null) {
			return cpDefinition;
		}

		return cpDefinitionPersistence.fetchByC_V(
			cProduct.getCProductId(), cProduct.getLatestVersion());
	}

	@Override
	public CPDefinition fetchCPDefinitionByCProductId(long cProductId) {
		CProduct cProduct = cProductLocalService.fetchCProduct(cProductId);

		if (cProduct == null) {
			return null;
		}

		CPDefinition cpDefinition = cpDefinitionPersistence.fetchByPrimaryKey(
			cProduct.getPublishedCPDefinitionId());

		if (cpDefinition != null) {
			return cpDefinition;
		}

		return cpDefinitionPersistence.fetchByC_V(
			cProduct.getCProductId(), cProduct.getLatestVersion());
	}

	@Override
	public Map<Locale, String> getCPDefinitionDescriptionMap(
		long cpDefinitionId) {

		Map<Locale, String> cpDefinitionLocalizationDescriptionMap =
			new HashMap<>();

		List<CPDefinitionLocalization> cpDefinitionLocalizationList =
			cpDefinitionLocalizationPersistence.findByCPDefinitionId(
				cpDefinitionId);

		for (CPDefinitionLocalization cpDefinitionLocalization :
				cpDefinitionLocalizationList) {

			cpDefinitionLocalizationDescriptionMap.put(
				LocaleUtil.fromLanguageId(
					cpDefinitionLocalization.getLanguageId()),
				cpDefinitionLocalization.getDescription());
		}

		return cpDefinitionLocalizationDescriptionMap;
	}

	@Override
	public List<String> getCPDefinitionLocalizationLanguageIds(
		long cpDefinitionId) {

		List<CPDefinitionLocalization> cpDefinitionLocalizationList =
			cpDefinitionLocalizationPersistence.findByCPDefinitionId(
				cpDefinitionId);

		List<String> availableLanguageIds = new ArrayList<>();

		for (CPDefinitionLocalization cpDefinitionLocalization :
				cpDefinitionLocalizationList) {

			availableLanguageIds.add(cpDefinitionLocalization.getLanguageId());
		}

		return availableLanguageIds;
	}

	@Override
	public Map<Locale, String> getCPDefinitionMetaDescriptionMap(
		long cpDefinitionId) {

		Map<Locale, String> cpDefinitionLocalizationMetaDescriptionMap =
			new HashMap<>();

		List<CPDefinitionLocalization> cpDefinitionLocalizationList =
			cpDefinitionLocalizationPersistence.findByCPDefinitionId(
				cpDefinitionId);

		for (CPDefinitionLocalization cpDefinitionLocalization :
				cpDefinitionLocalizationList) {

			cpDefinitionLocalizationMetaDescriptionMap.put(
				LocaleUtil.fromLanguageId(
					cpDefinitionLocalization.getLanguageId()),
				cpDefinitionLocalization.getMetaDescription());
		}

		return cpDefinitionLocalizationMetaDescriptionMap;
	}

	@Override
	public Map<Locale, String> getCPDefinitionMetaKeywordsMap(
		long cpDefinitionId) {

		Map<Locale, String> cpDefinitionLocalizationMetaKeywordsMap =
			new HashMap<>();

		List<CPDefinitionLocalization> cpDefinitionLocalizationList =
			cpDefinitionLocalizationPersistence.findByCPDefinitionId(
				cpDefinitionId);

		for (CPDefinitionLocalization cpDefinitionLocalization :
				cpDefinitionLocalizationList) {

			cpDefinitionLocalizationMetaKeywordsMap.put(
				LocaleUtil.fromLanguageId(
					cpDefinitionLocalization.getLanguageId()),
				cpDefinitionLocalization.getMetaKeywords());
		}

		return cpDefinitionLocalizationMetaKeywordsMap;
	}

	@Override
	public Map<Locale, String> getCPDefinitionMetaTitleMap(
		long cpDefinitionId) {

		Map<Locale, String> cpDefinitionLocalizationMetaTitleMap =
			new HashMap<>();

		List<CPDefinitionLocalization> cpDefinitionLocalizationList =
			cpDefinitionLocalizationPersistence.findByCPDefinitionId(
				cpDefinitionId);

		for (CPDefinitionLocalization cpDefinitionLocalization :
				cpDefinitionLocalizationList) {

			cpDefinitionLocalizationMetaTitleMap.put(
				LocaleUtil.fromLanguageId(
					cpDefinitionLocalization.getLanguageId()),
				cpDefinitionLocalization.getMetaTitle());
		}

		return cpDefinitionLocalizationMetaTitleMap;
	}

	@Override
	public Map<Locale, String> getCPDefinitionNameMap(long cpDefinitionId) {
		Map<Locale, String> cpDefinitionLocalizationNameMap = new HashMap<>();

		List<CPDefinitionLocalization> cpDefinitionLocalizationList =
			cpDefinitionLocalizationPersistence.findByCPDefinitionId(
				cpDefinitionId);

		for (CPDefinitionLocalization cpDefinitionLocalization :
				cpDefinitionLocalizationList) {

			cpDefinitionLocalizationNameMap.put(
				LocaleUtil.fromLanguageId(
					cpDefinitionLocalization.getLanguageId()),
				cpDefinitionLocalization.getName());
		}

		return cpDefinitionLocalizationNameMap;
	}

	@Override
	public List<CPDefinition> getCPDefinitions(
		long groupId, boolean subscriptionEnabled) {

		return cpDefinitionPersistence.findByG_SE(groupId, subscriptionEnabled);
	}

	@Override
	public List<CPDefinition> getCPDefinitions(
		long groupId, int status, int start, int end) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return cpDefinitionPersistence.findByGroupId(groupId, start, end);
		}

		return cpDefinitionPersistence.findByG_S(groupId, status, start, end);
	}

	@Override
	public List<CPDefinition> getCPDefinitions(
		long groupId, int status, int start, int end,
		OrderByComparator<CPDefinition> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return cpDefinitionPersistence.findByGroupId(
				groupId, start, end, orderByComparator);
		}

		return cpDefinitionPersistence.findByG_S(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public List<CPDefinition> getCPDefinitions(
		long groupId, String productTypeName, String languageId, int status,
		int start, int end, OrderByComparator<CPDefinition> orderByComparator) {

		QueryDefinition<CPDefinition> queryDefinition = new QueryDefinition<>(
			status, start, end, orderByComparator);

		return cpDefinitionFinder.findByG_P_S(
			groupId, productTypeName, languageId, queryDefinition);
	}

	@Override
	public int getCPDefinitionsCount(
		long groupId, boolean subscriptionEnabled) {

		return cpDefinitionPersistence.countByG_SE(
			groupId, subscriptionEnabled);
	}

	@Override
	public int getCPDefinitionsCount(long groupId, int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return cpDefinitionPersistence.countByGroupId(groupId);
		}

		return cpDefinitionPersistence.countByG_S(groupId, status);
	}

	@Override
	public int getCPDefinitionsCount(
		long groupId, String productTypeName, String languageId, int status) {

		QueryDefinition<CPDefinition> queryDefinition = new QueryDefinition<>(
			status);

		return cpDefinitionFinder.countByG_P_S(
			groupId, productTypeName, languageId, queryDefinition);
	}

	@Override
	public Map<Locale, String> getCPDefinitionShortDescriptionMap(
		long cpDefinitionId) {

		Map<Locale, String> cpDefinitionLocalizationShortDescriptionMap =
			new HashMap<>();

		List<CPDefinitionLocalization> cpDefinitionLocalizationList =
			cpDefinitionLocalizationPersistence.findByCPDefinitionId(
				cpDefinitionId);

		for (CPDefinitionLocalization cpDefinitionLocalization :
				cpDefinitionLocalizationList) {

			cpDefinitionLocalizationShortDescriptionMap.put(
				LocaleUtil.fromLanguageId(
					cpDefinitionLocalization.getLanguageId()),
				cpDefinitionLocalization.getShortDescription());
		}

		return cpDefinitionLocalizationShortDescriptionMap;
	}

	@Override
	public CPAttachmentFileEntry getDefaultImageCPAttachmentFileEntry(
			long cpDefinitionId)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(
			CPDefinition.class);

		List<CPAttachmentFileEntry> cpAttachmentFileEntries =
			cpAttachmentFileEntryLocalService.getCPAttachmentFileEntries(
				classNameId, cpDefinitionId,
				CPAttachmentFileEntryConstants.TYPE_IMAGE,
				WorkflowConstants.STATUS_APPROVED, 0, 1);

		if (cpAttachmentFileEntries.isEmpty()) {
			return null;
		}

		return cpAttachmentFileEntries.get(0);
	}

	@Override
	public List<Facet> getFacets(
		String filterFields, String filterValues, SearchContext searchContext) {

		List<Facet> facets = new ArrayList<>();

		if (Validator.isNotNull(filterFields) &&
			Validator.isNotNull(filterValues)) {

			Map<String, List<String>> facetMap = new HashMap<>();

			String[] filterFieldsArray = StringUtil.split(filterFields);
			String[] filterValuesArray = StringUtil.split(filterValues);

			for (int i = 0; i < filterFieldsArray.length; i++) {
				String key = filterFieldsArray[i];
				String value = filterValuesArray[i];

				if (key.startsWith("OPTION_")) {
					key = StringUtil.removeSubstring(key, "OPTION_");

					key = _getIndexFieldName(
						key, searchContext.getLanguageId());
				}

				List<String> facetValues = null;

				if (facetMap.containsKey(key)) {
					facetValues = facetMap.get(key);
				}

				if (facetValues == null) {
					facetValues = new ArrayList<>();
				}

				facetValues.add(value);

				facetMap.put(key, facetValues);
			}

			for (Map.Entry<String, List<String>> entry : facetMap.entrySet()) {
				String fieldName = entry.getKey();

				String[] values = ArrayUtil.toStringArray(entry.getValue());

				MultiValueFacet multiValueFacet = new MultiValueFacet(
					searchContext);

				multiValueFacet.setFieldName(fieldName);
				multiValueFacet.setValues(values);

				searchContext.setAttribute(fieldName, StringUtil.merge(values));

				if (fieldName.equals("assetCategoryIds")) {
					Stream<String> stream = Arrays.stream(values);

					LongStream longStream = stream.mapToLong(
						GetterUtil::getLong);

					searchContext.setAssetCategoryIds(longStream.toArray());
				}

				facets.add(multiValueFacet);
			}
		}

		return facets;
	}

	@Override
	public String getLayoutUuid(long groupId, long cpDefinitionId) {
		CPDisplayLayout cpDisplayLayout =
			cpDisplayLayoutLocalService.fetchCPDisplayLayout(
				groupId, CPDefinition.class, cpDefinitionId);

		if (cpDisplayLayout == null) {
			return null;
		}

		return cpDisplayLayout.getLayoutUuid();
	}

	@Override
	public Map<Locale, String> getUrlTitleMap(long cpDefinitionId) {
		CPDefinition cpDefinition = cpDefinitionPersistence.fetchByPrimaryKey(
			cpDefinitionId);

		if (cpDefinition == null) {
			return Collections.emptyMap();
		}

		Map<Locale, String> urlTitleMap = new HashMap<>();

		try {
			FriendlyURLEntry friendlyURLEntry =
				_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
					classNameLocalService.getClassNameId(CProduct.class),
					cpDefinition.getCProductId());

			List<FriendlyURLEntryLocalization> friendlyURLEntryLocalizations =
				_friendlyURLEntryLocalService.getFriendlyURLEntryLocalizations(
					friendlyURLEntry.getFriendlyURLEntryId());

			for (FriendlyURLEntryLocalization friendlyURLEntryLocalization :
					friendlyURLEntryLocalizations) {

				urlTitleMap.put(
					LocaleUtil.fromLanguageId(
						friendlyURLEntryLocalization.getLanguageId()),
					friendlyURLEntryLocalization.getUrlTitle());
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return Collections.emptyMap();
		}

		return urlTitleMap;
	}

	@Override
	public String getUrlTitleMapAsXML(long cpDefinitionId)
		throws PortalException {

		try {
			CPDefinition cpDefinition =
				cpDefinitionPersistence.findByPrimaryKey(cpDefinitionId);

			FriendlyURLEntry friendlyURLEntry =
				_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
					classNameLocalService.getClassNameId(CProduct.class),
					cpDefinition.getCProductId());

			return friendlyURLEntry.getUrlTitleMapAsXML();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return StringPool.BLANK;
		}
	}

	@Override
	public boolean hasChildCPDefinitions(long cpDefinitionId) {
		int count =
			cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsCount(
				cpDefinitionId);

		if ((count <= 0) ||
			!cpDefinitionOptionRelLocalService.
				hasLinkedCPInstanceCPDefinitionOptionRels(cpDefinitionId)) {

			return false;
		}

		return true;
	}

	@Override
	public boolean isPublishedCPDefinition(CPDefinition cpDefinition) {
		CProduct cProduct = cProductLocalService.fetchCProduct(
			cpDefinition.getCProductId());

		if ((cProduct != null) &&
			(cProduct.getPublishedCPDefinitionId() ==
				cpDefinition.getCPDefinitionId())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isPublishedCPDefinition(long cpDefinitionId) {
		CPDefinition cpDefinition = cpDefinitionLocalService.fetchCPDefinition(
			cpDefinitionId);

		if (cpDefinition == null) {
			return false;
		}

		return isPublishedCPDefinition(cpDefinition);
	}

	@Override
	public boolean isVersionable(CPDefinition cpDefinition) {
		if (!_isVersioningEnabled()) {
			return false;
		}

		return isPublishedCPDefinition(cpDefinition);
	}

	@Override
	public boolean isVersionable(long cpDefinitionId) {
		if (!_isVersioningEnabled()) {
			return false;
		}

		return isPublishedCPDefinition(cpDefinitionId);
	}

	@Override
	public boolean isVersionable(
		long cpDefinitionId, HttpServletRequest httpServletRequest) {

		if (httpServletRequest == null) {
			return isVersionable(cpDefinitionId);
		}

		boolean versionable = GetterUtil.getBoolean(
			httpServletRequest.getAttribute("versionable#" + cpDefinitionId),
			true);

		if (versionable) {
			return isVersionable(cpDefinitionId);
		}

		return false;
	}

	@Override
	public void maintainVersionThreshold(long cProductId)
		throws PortalException {

		int threshold = 0;

		try {
			CProductVersionConfiguration cProductVersionConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					CProductVersionConfiguration.class,
					new SystemSettingsLocator(
						CProductVersionConfiguration.class.getName()));

			threshold = cProductVersionConfiguration.versionThreshold();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return;
		}

		OrderByComparator<CPDefinition> orderByComparator =
			OrderByComparatorFactoryUtil.create(
				CPDefinitionModelImpl.TABLE_NAME, Field.VERSION, false);

		List<CPDefinition> deletableCPDefinitions =
			cpDefinitionPersistence.findByC_S(
				cProductId, WorkflowConstants.STATUS_APPROVED, threshold,
				threshold + Short.MAX_VALUE, orderByComparator);

		for (CPDefinition cpDefinition : deletableCPDefinitions) {
			cpDefinitionLocalService.deleteCPDefinition(cpDefinition);
		}
	}

	@Override
	public BaseModelSearchResult<CPDefinition> searchCPDefinitions(
			long companyId, long[] groupIds, String keywords, int status,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupIds, keywords, status, start, end, sort);

		return searchCPDefinitions(searchContext);
	}

	@Override
	public BaseModelSearchResult<CPDefinition> searchCPDefinitions(
			long companyId, long[] groupIds, String keywords,
			String filterFields, String filterValues, int start, int end,
			Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupIds, keywords, WorkflowConstants.STATUS_ANY, start,
			end, sort);

		List<Facet> facets = getFacets(
			filterFields, filterValues, searchContext);

		searchContext.setFacets(facets);

		return searchCPDefinitions(searchContext);
	}

	@Override
	public BaseModelSearchResult<CPDefinition>
			searchCPDefinitionsByChannelGroupId(
				long companyId, long[] groupIds, long commerceChannelGroupId,
				String keywords, int status, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupIds, keywords, status, start, end, sort);

		searchContext.setAttribute(
			CPField.COMMERCE_CHANNEL_GROUP_ID, commerceChannelGroupId);
		searchContext.setAttribute("secure", Boolean.TRUE);

		return searchCPDefinitions(searchContext);
	}

	@Override
	public void updateAsset(
			long userId, CPDefinition cpDefinition, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds, Double priority)
		throws PortalException {

		Group companyGroup = _groupLocalService.getCompanyGroup(
			cpDefinition.getCompanyId());

		AssetEntry assetEntry = _assetEntryLocalService.updateEntry(
			userId, companyGroup.getGroupId(), cpDefinition.getCreateDate(),
			cpDefinition.getModifiedDate(), CPDefinition.class.getName(),
			cpDefinition.getCPDefinitionId(), cpDefinition.getUuid(), 0,
			assetCategoryIds, assetTagNames, true, true, null, null,
			cpDefinition.getCreateDate(), null, ContentTypes.TEXT_PLAIN,
			cpDefinition.getNameMapAsXML(),
			cpDefinition.getDescriptionMapAsXML(), null, null, null, 0, 0,
			priority);

		_assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinition updateCPDefinition(
			long cpDefinitionId, Map<Locale, String> nameMap,
			Map<Locale, String> shortDescriptionMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
			Map<Locale, String> metaTitleMap,
			Map<Locale, String> metaDescriptionMap,
			Map<Locale, String> metaKeywordsMap, boolean ignoreSKUCombinations,
			boolean shippable, boolean freeShipping, boolean shipSeparately,
			double shippingExtraPrice, double width, double height,
			double depth, double weight, long cpTaxCategoryId,
			boolean taxExempt, boolean telcoOrElectronics,
			String ddmStructureKey, boolean published, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition

		User user = userLocalService.getUser(serviceContext.getUserId());

		CPDefinition cpDefinition = cpDefinitionPersistence.findByPrimaryKey(
			cpDefinitionId);

		long groupId = cpDefinition.getGroupId();

		Date expirationDate = null;
		Date date = new Date();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CPDefinitionDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CPDefinitionExpirationDateException.class);
		}

		validate(
			groupId, ddmStructureKey, metaTitleMap, metaDescriptionMap,
			metaKeywordsMap, displayDate, expirationDate,
			cpDefinition.getProductTypeName());

		if (cpDefinitionLocalService.isVersionable(cpDefinition)) {
			cpDefinition = cpDefinitionLocalService.copyCPDefinition(
				cpDefinitionId);

			if (serviceContext.getWorkflowAction() !=
					WorkflowConstants.ACTION_PUBLISH) {

				cProductLocalService.updatePublishedCPDefinitionId(
					cpDefinition.getCProductId(), cpDefinitionId);
			}
		}

		cpDefinition.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition.setIgnoreSKUCombinations(ignoreSKUCombinations);
		cpDefinition.setShippable(shippable);
		cpDefinition.setFreeShipping(freeShipping);
		cpDefinition.setShipSeparately(shipSeparately);
		cpDefinition.setShippingExtraPrice(shippingExtraPrice);
		cpDefinition.setWidth(width);
		cpDefinition.setHeight(height);
		cpDefinition.setDepth(depth);
		cpDefinition.setWeight(weight);
		cpDefinition.setTaxExempt(taxExempt);
		cpDefinition.setTelcoOrElectronics(telcoOrElectronics);
		cpDefinition.setDDMStructureKey(ddmStructureKey);
		cpDefinition.setPublished(published);
		cpDefinition.setDisplayDate(displayDate);
		cpDefinition.setExpirationDate(expirationDate);

		if ((expirationDate == null) || expirationDate.after(date)) {
			cpDefinition.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			cpDefinition.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		cpDefinition.setStatusByUserId(user.getUserId());
		cpDefinition.setStatusDate(serviceContext.getModifiedDate(date));
		cpDefinition.setExpandoBridgeAttributes(serviceContext);

		cpDefinition = cpDefinitionPersistence.update(cpDefinition);

		Map<String, String> newUrlTitleMap = new HashMap<>();

		if (MapUtil.isEmpty(urlTitleMap)) {
			newUrlTitleMap = _getUniqueUrlTitles(cpDefinition, nameMap);
		}
		else {
			newUrlTitleMap = _getUniqueUrlTitles(cpDefinition, urlTitleMap);
		}

		// Commerce product definition localization

		_updateCPDefinitionLocalizedFields(
			cpDefinition.getCompanyId(), cpDefinition.getCPDefinitionId(),
			nameMap, shortDescriptionMap, descriptionMap, metaTitleMap,
			metaDescriptionMap, metaKeywordsMap);

		// Commerce product friendly URL entries

		long classNameId = classNameLocalService.getClassNameId(CProduct.class);

		long classPK = cpDefinition.getCProductId();

		try {
			FriendlyURLEntry friendlyURLEntry =
				_friendlyURLEntryLocalService.getMainFriendlyURLEntry(
					classNameId, classPK);

			_friendlyURLEntryLocalService.updateFriendlyURLEntry(
				friendlyURLEntry.getFriendlyURLEntryId(),
				friendlyURLEntry.getClassNameId(),
				friendlyURLEntry.getClassPK(),
				friendlyURLEntry.getDefaultLanguageId(), newUrlTitleMap);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			Group companyGroup = _groupLocalService.getCompanyGroup(
				cpDefinition.getCompanyId());

			_friendlyURLEntryLocalService.addFriendlyURLEntry(
				companyGroup.getGroupId(), classNameId, classPK, newUrlTitleMap,
				serviceContext);
		}

		// Asset

		updateAsset(
			user.getUserId(), cpDefinition,
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		// Workflow

		return startWorkflowInstance(
			user.getUserId(), cpDefinition, serviceContext);
	}

	@Override
	public CPDefinition updateCPDefinition(
			long cpDefinitionId, Map<Locale, String> nameMap,
			Map<Locale, String> shortDescriptionMap,
			Map<Locale, String> descriptionMap, Map<Locale, String> urlTitleMap,
			Map<Locale, String> metaTitleMap,
			Map<Locale, String> metaDescriptionMap,
			Map<Locale, String> metaKeywordsMap, boolean ignoreSKUCombinations,
			String ddmStructureKey, boolean published, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionPersistence.findByPrimaryKey(
			cpDefinitionId);

		return cpDefinitionLocalService.updateCPDefinition(
			cpDefinitionId, nameMap, shortDescriptionMap, descriptionMap,
			urlTitleMap, metaTitleMap, metaDescriptionMap, metaKeywordsMap,
			ignoreSKUCombinations, cpDefinition.isShippable(),
			cpDefinition.isFreeShipping(), cpDefinition.isShipSeparately(),
			cpDefinition.getShippingExtraPrice(), cpDefinition.getWidth(),
			cpDefinition.getHeight(), cpDefinition.getDepth(),
			cpDefinition.getWeight(), cpDefinition.getCPTaxCategoryId(),
			cpDefinition.isTaxExempt(), cpDefinition.isTelcoOrElectronics(),
			ddmStructureKey, published, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinition updateCPDefinitionAccountGroupFilter(
			long cpDefinitionId, boolean enable)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		cpDefinition.setAccountGroupFilterEnabled(enable);

		return cpDefinitionPersistence.update(cpDefinition);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinition updateCPDefinitionCategorization(
			long cpDefinitionId, ServiceContext serviceContext)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionPersistence.findByPrimaryKey(
			cpDefinitionId);

		if (cpDefinitionLocalService.isVersionable(cpDefinition)) {
			cpDefinition = cpDefinitionLocalService.copyCPDefinition(
				cpDefinitionId);

			cpDefinitionId = cpDefinition.getCPDefinitionId();
		}

		updateStatus(
			serviceContext.getUserId(), cpDefinitionId,
			cpDefinition.getStatus(), serviceContext,
			new HashMap<String, Serializable>());

		// Asset

		Group companyGroup = _groupLocalService.getCompanyGroup(
			serviceContext.getCompanyId());

		_assetEntryLocalService.updateEntry(
			serviceContext.getUserId(), companyGroup.getGroupId(),
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId(),
			serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		return cpDefinitionPersistence.update(cpDefinition);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinition updateCPDefinitionChannelFilter(
			long cpDefinitionId, boolean enable)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		cpDefinition.setChannelFilterEnabled(enable);

		return cpDefinitionPersistence.update(cpDefinition);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinition updateCPDefinitionIgnoreSKUCombinations(
			long cpDefinitionId, boolean ignoreSKUCombinations,
			ServiceContext serviceContext)
		throws PortalException {

		checkCPInstances(
			serviceContext.getUserId(), cpDefinitionId, ignoreSKUCombinations);

		CPDefinition cpDefinition = cpDefinitionPersistence.findByPrimaryKey(
			cpDefinitionId);

		cpDefinition.setIgnoreSKUCombinations(ignoreSKUCombinations);

		return cpDefinitionPersistence.update(cpDefinition);
	}

	@Override
	public void updateCPDefinitionsByCPTaxCategoryId(long cpTaxCategoryId)
		throws PortalException {

		List<CPDefinition> cpDefinitions =
			cpDefinitionPersistence.findByCPTaxCategoryId(cpTaxCategoryId);

		for (CPDefinition cpDefinition : cpDefinitions) {
			updateTaxCategoryInfo(
				cpDefinition.getCPDefinitionId(), 0, cpDefinition.isTaxExempt(),
				cpDefinition.isTelcoOrElectronics());
		}
	}

	@Override
	public CPDefinition updateExternalReferenceCode(
			String externalReferenceCode, long cpDefinitionId)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		cProductLocalService.updateCProductExternalReferenceCode(
			externalReferenceCode, cpDefinition.getCProductId());

		return cpDefinitionLocalService.getCPDefinition(cpDefinitionId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinition updateShippingInfo(
			long cpDefinitionId, boolean shippable, boolean freeShipping,
			boolean shipSeparately, double shippingExtraPrice, double width,
			double height, double depth, double weight,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionPersistence.findByPrimaryKey(
			cpDefinitionId);

		if (cpDefinitionLocalService.isVersionable(cpDefinition)) {
			cpDefinition = cpDefinitionLocalService.copyCPDefinition(
				cpDefinitionId);
		}

		cpDefinition.setShippable(shippable);
		cpDefinition.setFreeShipping(freeShipping);
		cpDefinition.setShipSeparately(shipSeparately);
		cpDefinition.setShippingExtraPrice(shippingExtraPrice);
		cpDefinition.setWidth(width);
		cpDefinition.setHeight(height);
		cpDefinition.setDepth(depth);
		cpDefinition.setWeight(weight);

		return cpDefinitionPersistence.update(cpDefinition);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinition updateStatus(
			long userId, long cpDefinitionId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		// Commerce product definition

		User user = userLocalService.getUser(userId);
		Date date = new Date();

		CPDefinition cpDefinition = cpDefinitionPersistence.findByPrimaryKey(
			cpDefinitionId);

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(cpDefinition.getDisplayDate() != null) &&
			date.before(cpDefinition.getDisplayDate())) {

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		Date modifiedDate = serviceContext.getModifiedDate(date);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			Date expirationDate = cpDefinition.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(date)) {
				cpDefinition.setExpirationDate(null);
			}
		}

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			cpDefinition.setExpirationDate(date);
		}

		cpDefinition.setStatus(status);
		cpDefinition.setStatusByUserId(user.getUserId());
		cpDefinition.setStatusByUserName(user.getFullName());
		cpDefinition.setStatusDate(modifiedDate);

		cpDefinition = cpDefinitionPersistence.update(cpDefinition);

		if (status == WorkflowConstants.STATUS_APPROVED) {

			// Asset

			_assetEntryLocalService.updateEntry(
				CPDefinition.class.getName(), cpDefinition.getCPDefinitionId(),
				cpDefinition.getDisplayDate(), cpDefinition.getExpirationDate(),
				true, true);

			//CProduct

			cProductLocalService.updatePublishedCPDefinitionId(
				cpDefinition.getCProductId(), cpDefinition.getCPDefinitionId());
		}
		else {

			// Asset

			_assetEntryLocalService.updateVisible(
				CPDefinition.class.getName(), cpDefinitionId, false);
		}

		// Commerce product instances

		reindexCPInstances(cpDefinition);

		return cpDefinition;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinition updateSubscriptionInfo(
			long cpDefinitionId, boolean subscriptionEnabled,
			int subscriptionLength, String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, boolean deliverySubscriptionEnabled,
			int deliverySubscriptionLength, String deliverySubscriptionType,
			UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties,
			long deliveryMaxSubscriptionCycles)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionPersistence.findByPrimaryKey(
			cpDefinitionId);

		if (cpDefinitionLocalService.isVersionable(cpDefinition)) {
			cpDefinition = cpDefinitionLocalService.copyCPDefinition(
				cpDefinitionId);
		}

		cpDefinition.setSubscriptionEnabled(subscriptionEnabled);
		cpDefinition.setSubscriptionLength(subscriptionLength);
		cpDefinition.setSubscriptionType(subscriptionType);
		cpDefinition.setSubscriptionTypeSettingsProperties(
			subscriptionTypeSettingsUnicodeProperties);
		cpDefinition.setMaxSubscriptionCycles(maxSubscriptionCycles);
		cpDefinition.setDeliverySubscriptionEnabled(
			deliverySubscriptionEnabled);
		cpDefinition.setDeliverySubscriptionLength(deliverySubscriptionLength);
		cpDefinition.setDeliverySubscriptionType(deliverySubscriptionType);
		cpDefinition.setDeliverySubscriptionTypeSettingsProperties(
			deliverySubscriptionTypeSettingsUnicodeProperties);
		cpDefinition.setDeliveryMaxSubscriptionCycles(
			deliveryMaxSubscriptionCycles);

		return cpDefinitionPersistence.update(cpDefinition);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CPDefinition updateSubscriptionInfo(
			long cpDefinitionId, boolean subscriptionEnabled,
			int subscriptionLength, String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, ServiceContext serviceContext)
		throws PortalException {

		return cpDefinitionLocalService.updateSubscriptionInfo(
			cpDefinitionId, subscriptionEnabled, subscriptionLength,
			subscriptionType, subscriptionTypeSettingsUnicodeProperties,
			maxSubscriptionCycles, false, 1, null, null, 0);
	}

	@Override
	public CPDefinition updateTaxCategoryInfo(
			long cpDefinitionId, long cpTaxCategoryId, boolean taxExempt,
			boolean telcoOrElectronics)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionPersistence.findByPrimaryKey(
			cpDefinitionId);

		if (cpDefinitionLocalService.isVersionable(cpDefinition)) {
			cpDefinition = cpDefinitionLocalService.copyCPDefinition(
				cpDefinitionId);
		}

		cpDefinition.setCPTaxCategoryId(cpTaxCategoryId);
		cpDefinition.setTaxExempt(taxExempt);
		cpDefinition.setTelcoOrElectronics(telcoOrElectronics);

		return cpDefinitionPersistence.update(cpDefinition);
	}

	protected SearchContext buildSearchContext(
		long companyId, long[] groupIds, String keywords, int status, int start,
		int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.CONTENT, keywords
			).put(
				Field.DESCRIPTION, keywords
			).put(
				Field.ENTRY_CLASS_PK, keywords
			).put(
				Field.NAME, keywords
			).put(
				Field.STATUS, status
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords", keywords
				).build()
			).build());

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		if (groupIds.length > 0) {
			searchContext.setGroupIds(groupIds);
		}

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected void checkCPDefinitionsByDisplayDate() throws PortalException {
		List<CPDefinition> cpDefinitions = cpDefinitionPersistence.findByLtD_S(
			new Date(), WorkflowConstants.STATUS_SCHEDULED);

		for (CPDefinition cpDefinition : cpDefinitions) {
			long userId = PortalUtil.getValidUserId(
				cpDefinition.getCompanyId(), cpDefinition.getUserId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCommand(Constants.UPDATE);
			serviceContext.setScopeGroupId(cpDefinition.getGroupId());

			cpDefinitionLocalService.updateStatus(
				userId, cpDefinition.getCPDefinitionId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext,
				new HashMap<String, Serializable>());

			if (cpDefinition.isApproved()) {
				long classNameId = classNameLocalService.getClassNameId(
					cpDefinition.getModelClassName());

				cpAttachmentFileEntryLocalService.
					checkCPAttachmentFileEntriesByDisplayDate(
						classNameId, cpDefinition.getCPDefinitionId());

				cpInstanceLocalService.checkCPInstancesByDisplayDate(
					cpDefinition.getCPDefinitionId());
			}
		}
	}

	protected void checkCPDefinitionsByExpirationDate() throws PortalException {
		List<CPDefinition> cpDefinitions =
			cpDefinitionFinder.findByExpirationDate(
				new Date(),
				new QueryDefinition<>(WorkflowConstants.STATUS_APPROVED));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Expiring " + cpDefinitions.size() +
					" commerce product definitions");
		}

		if ((cpDefinitions != null) && !cpDefinitions.isEmpty()) {
			for (CPDefinition cpDefinition : cpDefinitions) {
				long userId = PortalUtil.getValidUserId(
					cpDefinition.getCompanyId(), cpDefinition.getUserId());

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setCommand(Constants.UPDATE);
				serviceContext.setScopeGroupId(cpDefinition.getGroupId());

				cpDefinitionLocalService.updateStatus(
					userId, cpDefinition.getCPDefinitionId(),
					WorkflowConstants.STATUS_EXPIRED, serviceContext,
					new HashMap<String, Serializable>());
			}
		}
	}

	protected void checkCPInstances(
			long userId, long cpDefinitionId, boolean ignoreSKUCombinations)
		throws PortalException {

		if (ignoreSKUCombinations) {
			int cpInstancesCount =
				cpInstanceLocalService.getCPDefinitionInstancesCount(
					cpDefinitionId, WorkflowConstants.STATUS_APPROVED);

			if (cpInstancesCount <= 1) {
				return;
			}

			throw new CPDefinitionIgnoreSKUCombinationsException();
		}

		int cpDefinitionOptionRelsCount =
			cpDefinitionOptionRelLocalService.getCPDefinitionOptionRelsCount(
				cpDefinitionId, true);

		if (cpDefinitionOptionRelsCount == 0) {
			return;
		}

		List<CPInstance> cpInstances =
			cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinitionId, WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (CPInstance cpInstance : cpInstances) {
			if (!cpInstanceOptionValueRelLocalService.
					hasCPInstanceOptionValueRel(cpInstance.getCPInstanceId())) {

				cpInstanceLocalService.updateStatus(
					userId, cpInstance.getCPInstanceId(),
					WorkflowConstants.STATUS_INACTIVE);
			}
		}
	}

	protected List<CPDefinition> getCPDefinitions(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CPDefinition> cpDefinitions = new ArrayList<>(documents.size());

		for (Document document : documents) {
			long cpDefinitionId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CPDefinition cpDefinition = fetchCPDefinition(cpDefinitionId);

			if (cpDefinition == null) {
				cpDefinitions = null;

				Indexer<CPDefinition> indexer = IndexerRegistryUtil.getIndexer(
					CPDefinition.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (cpDefinitions != null) {
				cpDefinitions.add(cpDefinition);
			}
		}

		return cpDefinitions;
	}

	protected void reindexCPDefinitionOptionRels(CPDefinition cpDefinition)
		throws PortalException {

		Indexer<CPDefinitionOptionRel> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CPDefinitionOptionRel.class);

		indexer.reindex(cpDefinition.getCPDefinitionOptionRels());
	}

	protected void reindexCPDefinitionOptionValueRels(
			CPDefinitionOptionRel cpDefinitionOptionRel)
		throws PortalException {

		Indexer<CPDefinitionOptionValueRel> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CPDefinitionOptionValueRel.class);

		indexer.reindex(cpDefinitionOptionRel.getCPDefinitionOptionValueRels());
	}

	protected void reindexCPInstances(CPDefinition cpDefinition)
		throws PortalException {

		Indexer<CPInstance> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPInstance.class);

		indexer.reindex(cpDefinition.getCPInstances());
	}

	protected BaseModelSearchResult<CPDefinition> searchCPDefinitions(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CPDefinition> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPDefinition.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CPDefinition> cpDefinitions = getCPDefinitions(hits);

			if (cpDefinitions != null) {
				return new BaseModelSearchResult<>(
					cpDefinitions, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected CPDefinition startWorkflowInstance(
			long userId, CPDefinition cpDefinition,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			cpDefinition.getCompanyId(), cpDefinition.getGroupId(), userId,
			CPDefinition.class.getName(), cpDefinition.getCPDefinitionId(),
			cpDefinition, serviceContext, workflowContext);
	}

	protected void validate(
			long groupId, String ddmStructureKey,
			Map<Locale, String> metaTitleMap,
			Map<Locale, String> metaDescriptionMap,
			Map<Locale, String> metaKeywordsMap, Date displayDate,
			Date expirationDate, String productTypeName)
		throws PortalException {

		if (Validator.isNotNull(ddmStructureKey)) {
			long classNameId = classNameLocalService.getClassNameId(
				CPDefinition.class.getName());

			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchStructure(
					groupId, classNameId, ddmStructureKey, true);

			if (ddmStructure == null) {
				throw new NoSuchStructureException();
			}
		}

		if (metaTitleMap != null) {
			for (Map.Entry<Locale, String> entry : metaTitleMap.entrySet()) {
				CPDefinitionMetaTitleException cpDefinitionMetaTitleException =
					CPDefinitionImpl.validateMetaTitle(entry.getValue());

				if (cpDefinitionMetaTitleException != null) {
					throw cpDefinitionMetaTitleException;
				}
			}
		}

		if (metaDescriptionMap != null) {
			for (Map.Entry<Locale, String> entry :
					metaDescriptionMap.entrySet()) {

				CPDefinitionMetaDescriptionException
					cpDefinitionMetaDescriptionException =
						CPDefinitionImpl.validateMetaDescription(
							entry.getValue());

				if (cpDefinitionMetaDescriptionException != null) {
					throw cpDefinitionMetaDescriptionException;
				}
			}
		}

		if (metaKeywordsMap != null) {
			for (Map.Entry<Locale, String> entry : metaKeywordsMap.entrySet()) {
				CPDefinitionMetaKeywordsException
					cpDefinitionMetaKeywordsException =
						CPDefinitionImpl.validateMetaKeyword(entry.getValue());

				if (cpDefinitionMetaKeywordsException != null) {
					throw cpDefinitionMetaKeywordsException;
				}
			}
		}

		if ((expirationDate != null) &&
			(expirationDate.before(new Date()) ||
			 ((displayDate != null) && expirationDate.before(displayDate)))) {

			throw new CPDefinitionExpirationDateException(
				"Expiration date " + expirationDate + " is in the past");
		}

		CPType cpType = _cpTypeServicesTracker.getCPType(productTypeName);

		if (cpType == null) {
			throw new CPDefinitionProductTypeNameException();
		}
	}

	private List<CPDefinitionLocalization> _addCPDefinitionLocalizedFields(
			long companyId, long cpDefinitionId, Map<Locale, String> nameMap,
			Map<Locale, String> shortDescriptionMap,
			Map<Locale, String> descriptionMap,
			Map<Locale, String> metaTitleMap,
			Map<Locale, String> metaDescriptionMap,
			Map<Locale, String> metaKeywordsMap)
		throws PortalException {

		Set<Locale> localeSet = new HashSet<>();

		localeSet.addAll(nameMap.keySet());

		if (shortDescriptionMap != null) {
			localeSet.addAll(shortDescriptionMap.keySet());
		}

		if (descriptionMap != null) {
			localeSet.addAll(descriptionMap.keySet());
		}

		if (metaTitleMap != null) {
			localeSet.addAll(metaTitleMap.keySet());
		}

		if (metaDescriptionMap != null) {
			localeSet.addAll(metaDescriptionMap.keySet());
		}

		if (metaKeywordsMap != null) {
			localeSet.addAll(metaKeywordsMap.keySet());
		}

		List<CPDefinitionLocalization> cpDefinitionLocalizations =
			new ArrayList<>();

		for (Locale locale : localeSet) {
			String name = nameMap.get(locale);
			String shortDescription = null;
			String description = null;
			String metaTitle = null;
			String metaDescription = null;
			String metaKeywords = null;

			if (shortDescriptionMap != null) {
				shortDescription = shortDescriptionMap.get(locale);
			}

			if (descriptionMap != null) {
				description = descriptionMap.get(locale);
			}

			if (metaTitleMap != null) {
				metaTitle = metaTitleMap.get(locale);
			}

			if (metaDescriptionMap != null) {
				metaDescription = metaDescriptionMap.get(locale);
			}

			if (metaKeywordsMap != null) {
				metaKeywords = metaKeywordsMap.get(locale);
			}

			if (Validator.isNull(name) && Validator.isNull(shortDescription) &&
				Validator.isNull(description) && Validator.isNull(metaTitle) &&
				Validator.isNull(metaDescription) &&
				Validator.isNull(metaKeywords)) {

				continue;
			}

			CPDefinitionLocalization cpDefinitionLocalization =
				_addCPDefinitionLocalizedFields(
					companyId, cpDefinitionId, name, shortDescription,
					description, metaTitle, metaDescription, metaKeywords,
					LocaleUtil.toLanguageId(locale));

			cpDefinitionLocalizations.add(cpDefinitionLocalization);
		}

		return cpDefinitionLocalizations;
	}

	private CPDefinitionLocalization _addCPDefinitionLocalizedFields(
			long companyId, long cpDefinitionId, String name,
			String shortDescription, String description, String metaTitle,
			String metaDescription, String metaKeywords, String languageId)
		throws PortalException {

		CPDefinitionLocalization cpDefinitionLocalization =
			cpDefinitionLocalizationPersistence.
				fetchByCPDefinitionId_LanguageId(cpDefinitionId, languageId);

		if (cpDefinitionLocalization == null) {
			long cpDefinitionLocalizationId = counterLocalService.increment();

			cpDefinitionLocalization =
				cpDefinitionLocalizationPersistence.create(
					cpDefinitionLocalizationId);

			cpDefinitionLocalization.setCompanyId(companyId);
			cpDefinitionLocalization.setCPDefinitionId(cpDefinitionId);
			cpDefinitionLocalization.setName(name);
			cpDefinitionLocalization.setShortDescription(shortDescription);
			cpDefinitionLocalization.setDescription(description);
			cpDefinitionLocalization.setMetaTitle(metaTitle);
			cpDefinitionLocalization.setMetaDescription(metaDescription);
			cpDefinitionLocalization.setMetaKeywords(metaKeywords);
			cpDefinitionLocalization.setLanguageId(languageId);
		}
		else {
			cpDefinitionLocalization.setName(name);
			cpDefinitionLocalization.setShortDescription(shortDescription);
			cpDefinitionLocalization.setDescription(description);
			cpDefinitionLocalization.setMetaTitle(metaTitle);
			cpDefinitionLocalization.setMetaDescription(metaDescription);
			cpDefinitionLocalization.setMetaKeywords(metaKeywords);
		}

		return cpDefinitionLocalizationPersistence.update(
			cpDefinitionLocalization);
	}

	private String _getIndexFieldName(String optionKey, String languageId) {
		return StringBundler.concat(
			languageId, "_ATTRIBUTE_", optionKey, "_VALUES_NAMES");
	}

	private Map<String, String> _getUniqueUrlTitles(
			CPDefinition cpDefinition, Map<Locale, String> urlTitleMap)
		throws PortalException {

		Map<String, String> newURLTitleMap = new HashMap<>();

		Group companyGroup = _groupLocalService.getCompanyGroup(
			cpDefinition.getCompanyId());

		long classNameId = classNameLocalService.getClassNameId(CProduct.class);

		for (Map.Entry<Locale, String> titleEntry : urlTitleMap.entrySet()) {
			String urlTitle = urlTitleMap.get(titleEntry.getKey());

			if (Validator.isNotNull(urlTitle) ||
				((urlTitle != null) && urlTitle.equals(StringPool.BLANK))) {

				urlTitle = _friendlyURLEntryLocalService.getUniqueUrlTitle(
					companyGroup.getGroupId(), classNameId,
					cpDefinition.getCProductId(), titleEntry.getValue());

				newURLTitleMap.put(
					LocaleUtil.toLanguageId(titleEntry.getKey()), urlTitle);
			}
		}

		return newURLTitleMap;
	}

	private boolean _isVersioningEnabled() {
		try {
			CProductVersionConfiguration cProductVersionConfiguration =
				ConfigurationProviderUtil.getConfiguration(
					CProductVersionConfiguration.class,
					new SystemSettingsLocator(
						CProductVersionConfiguration.class.getName()));

			if (cProductVersionConfiguration.enabled()) {
				return true;
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		return false;
	}

	private List<CPDefinitionLocalization> _updateCPDefinitionLocalizedFields(
			long companyId, long cpDefinitionId, Map<Locale, String> nameMap,
			Map<Locale, String> shortDescriptionMap,
			Map<Locale, String> descriptionMap,
			Map<Locale, String> metaTitleMap,
			Map<Locale, String> metaDescriptionMap,
			Map<Locale, String> metaKeywordsMap)
		throws PortalException {

		List<CPDefinitionLocalization> oldCPDefinitionLocalizations =
			new ArrayList<>(
				cpDefinitionLocalizationPersistence.findByCPDefinitionId(
					cpDefinitionId));

		List<CPDefinitionLocalization> newCPDefinitionLocalizations =
			_addCPDefinitionLocalizedFields(
				companyId, cpDefinitionId, nameMap, shortDescriptionMap,
				descriptionMap, metaTitleMap, metaDescriptionMap,
				metaKeywordsMap);

		oldCPDefinitionLocalizations.removeAll(newCPDefinitionLocalizations);

		for (CPDefinitionLocalization oldCPDefinitionLocalization :
				oldCPDefinitionLocalizations) {

			cpDefinitionLocalizationPersistence.remove(
				oldCPDefinitionLocalization);
		}

		return newCPDefinitionLocalizations;
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CPDefinitionLocalServiceImpl.class);

	@ServiceReference(type = AssetEntryLocalService.class)
	private AssetEntryLocalService _assetEntryLocalService;

	@ServiceReference(type = AssetLinkLocalService.class)
	private AssetLinkLocalService _assetLinkLocalService;

	@ServiceReference(type = CPTypeServicesTracker.class)
	private CPTypeServicesTracker _cpTypeServicesTracker;

	@ServiceReference(type = DDMStructureLocalService.class)
	private DDMStructureLocalService _ddmStructureLocalService;

	@ServiceReference(type = ExpandoRowLocalService.class)
	private ExpandoRowLocalService _expandoRowLocalService;

	@ServiceReference(type = FriendlyURLEntryLocalService.class)
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@ServiceReference(type = GroupLocalService.class)
	private GroupLocalService _groupLocalService;

	@ServiceReference(type = WorkflowInstanceLinkLocalService.class)
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}