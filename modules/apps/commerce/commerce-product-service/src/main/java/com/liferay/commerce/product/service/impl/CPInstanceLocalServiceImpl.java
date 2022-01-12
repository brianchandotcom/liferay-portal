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

import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.exception.CPInstanceDisplayDateException;
import com.liferay.commerce.product.exception.CPInstanceExpirationDateException;
import com.liferay.commerce.product.exception.CPInstanceReplacementCPInstanceUuidException;
import com.liferay.commerce.product.exception.CPInstanceSkuException;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.exception.NoSuchSkuContributorCPDefinitionOptionRelException;
import com.liferay.commerce.product.internal.util.SKUCombinationsIterator;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.base.CPInstanceLocalServiceBaseImpl;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
public class CPInstanceLocalServiceImpl extends CPInstanceLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPInstance addCPInstance(
			String externalReferenceCode, long cpDefinitionId, long groupId,
			String sku, String gtin, String manufacturerPartNumber,
			boolean purchasable,
			Map<Long, List<Long>>
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			double width, double height, double depth, double weight,
			BigDecimal price, BigDecimal promoPrice, BigDecimal cost,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			boolean overrideSubscriptionInfo, boolean subscriptionEnabled,
			int subscriptionLength, String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, boolean deliverySubscriptionEnabled,
			int deliverySubscriptionLength, String deliverySubscriptionType,
			UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties,
			long deliveryMaxSubscriptionCycles, String unspsc,
			boolean discontinued, String replacementCPInstanceUuid,
			long replacementCProductId, int discontinuedDateMonth,
			int discontinuedDateDay, int discontinuedDateYear,
			ServiceContext serviceContext)
		throws PortalException {

		_validateSku(cpDefinitionId, 0, sku);

		// Commerce product instance

		User user = userLocalService.getUser(serviceContext.getUserId());

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		Date expirationDate = null;
		Date date = new Date();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CPInstanceDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CPInstanceExpirationDateException.class);
		}

		long cpInstanceId = counterLocalService.increment();

		CPInstance cpInstance = cpInstancePersistence.create(cpInstanceId);

		if (cpDefinitionLocalService.isVersionable(cpDefinitionId)) {
			CPDefinition newCPDefinition =
				cpDefinitionLocalService.copyCPDefinition(cpDefinitionId);

			cpDefinitionId = newCPDefinition.getCPDefinitionId();
		}

		cpInstance.setExternalReferenceCode(externalReferenceCode);
		cpInstance.setGroupId(groupId);
		cpInstance.setCompanyId(user.getCompanyId());
		cpInstance.setUserId(user.getUserId());
		cpInstance.setUserName(user.getFullName());
		cpInstance.setCPDefinitionId(cpDefinitionId);
		cpInstance.setCPInstanceUuid(PortalUUIDUtil.generate());
		cpInstance.setSku(sku);
		cpInstance.setGtin(gtin);
		cpInstance.setManufacturerPartNumber(manufacturerPartNumber);
		cpInstance.setPurchasable(purchasable);
		cpInstance.setWidth(width);
		cpInstance.setHeight(height);
		cpInstance.setDepth(depth);
		cpInstance.setWeight(weight);
		cpInstance.setPrice(price);
		cpInstance.setPromoPrice(promoPrice);
		cpInstance.setCost(cost);
		cpInstance.setPublished(published);
		cpInstance.setDisplayDate(displayDate);
		cpInstance.setExpirationDate(expirationDate);
		cpInstance.setOverrideSubscriptionInfo(overrideSubscriptionInfo);
		cpInstance.setSubscriptionEnabled(subscriptionEnabled);
		cpInstance.setSubscriptionLength(subscriptionLength);
		cpInstance.setSubscriptionType(subscriptionType);
		cpInstance.setSubscriptionTypeSettingsProperties(
			subscriptionTypeSettingsUnicodeProperties);
		cpInstance.setMaxSubscriptionCycles(maxSubscriptionCycles);
		cpInstance.setDeliverySubscriptionEnabled(deliverySubscriptionEnabled);
		cpInstance.setDeliverySubscriptionLength(deliverySubscriptionLength);
		cpInstance.setDeliverySubscriptionType(deliverySubscriptionType);
		cpInstance.setDeliverySubscriptionTypeSettingsProperties(
			deliverySubscriptionTypeSettingsUnicodeProperties);
		cpInstance.setDeliveryMaxSubscriptionCycles(
			deliveryMaxSubscriptionCycles);

		cpInstance.setStatus(WorkflowConstants.STATUS_DRAFT);

		if ((displayDate != null) && date.before(displayDate)) {
			cpInstance.setStatus(WorkflowConstants.STATUS_SCHEDULED);
		}

		if (!neverExpire && expirationDate.before(date)) {
			cpInstance.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		cpInstance.setStatusByUserId(user.getUserId());
		cpInstance.setStatusDate(serviceContext.getModifiedDate(date));
		cpInstance.setExpandoBridgeAttributes(serviceContext);
		cpInstance.setUnspsc(unspsc);
		cpInstance.setDiscontinued(discontinued);
		cpInstance.setReplacementCPInstanceUuid(replacementCPInstanceUuid);
		cpInstance.setReplacementCProductId(replacementCProductId);

		Date discontinuedDate = PortalUtil.getDate(
			discontinuedDateMonth, discontinuedDateDay, discontinuedDateYear);

		cpInstance.setDiscontinuedDate(discontinuedDate);

		cpInstance = cpInstancePersistence.update(cpInstance);

		if ((cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds != null) &&
			!cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds.isEmpty()) {

			cpInstanceOptionValueRelLocalService.
				updateCPInstanceOptionValueRels(
					groupId, user.getCompanyId(), user.getUserId(),
					cpInstanceId,
					cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds);
		}

		reindexCPDefinition(cpDefinitionId);

		if (!_isWorkflowActionPublish(serviceContext)) {
			return cpInstance;
		}

		CPDefinition cpDefinition = cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		if (cpDefinition.isIgnoreSKUCombinations()) {
			_expireApprovedSiblingCPInstances(
				cpDefinition.getCPDefinitionId(), cpInstance.getCPInstanceId(),
				serviceContext);
		}
		else {
			if (!cpInstanceOptionValueRelLocalService.
					hasCPInstanceOptionValueRel(cpInstanceId)) {

				cpInstance = cpInstanceLocalService.updateStatus(
					user.getUserId(), cpInstance.getCPInstanceId(),
					WorkflowConstants.STATUS_INACTIVE);
			}

			_expireApprovedSiblingMatchingCPInstances(
				cpDefinitionId,
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
				serviceContext);

			_inactivateNoOptionSiblingCPInstances(
				cpDefinitionId, serviceContext);
		}

		// Workflow

		if (cpInstance.getStatus() == WorkflowConstants.STATUS_DRAFT) {
			cpInstance = startWorkflowInstance(
				user.getUserId(), cpInstance, serviceContext);
		}

		return cpInstance;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public CPInstance addCPInstance(
			String externalReferenceCode, long cpDefinitionId, long groupId,
			String sku, String gtin, String manufacturerPartNumber,
			boolean purchasable,
			Map<Long, List<Long>>
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			double width, double height, double depth, double weight,
			BigDecimal price, BigDecimal promoPrice, BigDecimal cost,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			boolean overrideSubscriptionInfo, boolean subscriptionEnabled,
			int subscriptionLength, String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, boolean deliverySubscriptionEnabled,
			int deliverySubscriptionLength, String deliverySubscriptionType,
			UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties,
			long deliveryMaxSubscriptionCycles, String unspsc,
			ServiceContext serviceContext)
		throws PortalException {

		return cpInstanceLocalService.addCPInstance(
			externalReferenceCode, cpDefinitionId, groupId, sku, gtin,
			manufacturerPartNumber, purchasable,
			cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds, width, height,
			depth, weight, price, promoPrice, cost, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			overrideSubscriptionInfo, subscriptionEnabled, subscriptionLength,
			subscriptionType, subscriptionTypeSettingsUnicodeProperties,
			maxSubscriptionCycles, deliverySubscriptionEnabled,
			deliverySubscriptionLength, deliverySubscriptionType,
			deliverySubscriptionTypeSettingsUnicodeProperties,
			deliveryMaxSubscriptionCycles, unspsc, false, null, 0, 0, 0, 0,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public CPInstance addCPInstance(
			String externalReferenceCode, long cpDefinitionId, long groupId,
			String sku, String gtin, String manufacturerPartNumber,
			boolean purchasable,
			Map<Long, List<Long>>
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			double width, double height, double depth, double weight,
			BigDecimal price, BigDecimal promoPrice, BigDecimal cost,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			boolean overrideSubscriptionInfo, boolean subscriptionEnabled,
			int subscriptionLength, String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, ServiceContext serviceContext)
		throws PortalException {

		return cpInstanceLocalService.addCPInstance(
			externalReferenceCode, cpDefinitionId, groupId, sku, gtin,
			manufacturerPartNumber, purchasable,
			cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds, width, height,
			depth, weight, price, promoPrice, cost, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			overrideSubscriptionInfo, subscriptionEnabled, subscriptionLength,
			subscriptionType, subscriptionTypeSettingsUnicodeProperties,
			maxSubscriptionCycles, null, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public CPInstance addCPInstance(
			String externalReferenceCode, long cpDefinitionId, long groupId,
			String sku, String gtin, String manufacturerPartNumber,
			boolean purchasable,
			Map<Long, List<Long>>
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			double width, double height, double depth, double weight,
			BigDecimal price, BigDecimal promoPrice, BigDecimal cost,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			boolean overrideSubscriptionInfo, boolean subscriptionEnabled,
			int subscriptionLength, String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, String unspsc,
			ServiceContext serviceContext)
		throws PortalException {

		return cpInstanceLocalService.addCPInstance(
			externalReferenceCode, cpDefinitionId, groupId, sku, gtin,
			manufacturerPartNumber, purchasable,
			cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds, width, height,
			depth, weight, price, promoPrice, cost, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			overrideSubscriptionInfo, subscriptionEnabled, subscriptionLength,
			subscriptionType, subscriptionTypeSettingsUnicodeProperties,
			maxSubscriptionCycles, false, 1, null, null, 0, unspsc,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public CPInstance addOrUpdateCPInstance(
			String externalReferenceCode, long cpDefinitionId, long groupId,
			String sku, String gtin, String manufacturerPartNumber,
			boolean purchasable, String json, double width, double height,
			double depth, double weight, BigDecimal price,
			BigDecimal promoPrice, BigDecimal cost, boolean published,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return cpInstanceLocalService.addOrUpdateCPInstance(
			externalReferenceCode, cpDefinitionId, groupId, sku, gtin,
			manufacturerPartNumber, purchasable, json, width, height, depth,
			weight, price, promoPrice, cost, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, null,
			serviceContext);
	}

	@Override
	public CPInstance addOrUpdateCPInstance(
			String externalReferenceCode, long cpDefinitionId, long groupId,
			String sku, String gtin, String manufacturerPartNumber,
			boolean purchasable, String json, double width, double height,
			double depth, double weight, BigDecimal price,
			BigDecimal promoPrice, BigDecimal cost, boolean published,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String unspsc, boolean discontinued,
			String replacementCPInstanceUuid, long replacementCProductId,
			int discontinuedDateMonth, int discontinuedDateDay,
			int discontinuedDateYear, ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}
		else {
			CPInstance cpInstance = cpInstancePersistence.fetchByC_ERC(
				serviceContext.getCompanyId(), externalReferenceCode);

			if (cpInstance != null) {
				return cpInstanceLocalService.updateCPInstance(
					cpInstance.getCPInstanceId(), sku, gtin,
					manufacturerPartNumber, purchasable, width, height, depth,
					weight, price, promoPrice, cost, published,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, unspsc, discontinued,
					replacementCPInstanceUuid, replacementCProductId,
					discontinuedDateMonth, discontinuedDateDay,
					discontinuedDateYear, serviceContext);
			}
		}

		return cpInstanceLocalService.addCPInstance(
			externalReferenceCode, cpDefinitionId, groupId, sku, gtin,
			manufacturerPartNumber, purchasable,
			cpDefinitionOptionRelLocalService.
				getCPDefinitionOptionRelCPDefinitionOptionValueRelIds(
					cpDefinitionId, json),
			width, height, depth, weight, price, promoPrice, cost, published,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, false, false, 1, StringPool.BLANK, null, 0, false, 1,
			StringPool.BLANK, null, 0, unspsc, discontinued,
			replacementCPInstanceUuid, replacementCProductId,
			discontinuedDateMonth, discontinuedDateDay, discontinuedDateYear,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public CPInstance addOrUpdateCPInstance(
			String externalReferenceCode, long cpDefinitionId, long groupId,
			String sku, String gtin, String manufacturerPartNumber,
			boolean purchasable, String json, double width, double height,
			double depth, double weight, BigDecimal price,
			BigDecimal promoPrice, BigDecimal cost, boolean published,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String unspsc, ServiceContext serviceContext)
		throws PortalException {

		return cpInstanceLocalService.addOrUpdateCPInstance(
			externalReferenceCode, cpDefinitionId, groupId, sku, gtin,
			manufacturerPartNumber, purchasable, json, width, height, depth,
			weight, price, promoPrice, cost, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, unspsc,
			false, null, 0, 0, 0, 0, serviceContext);
	}

	@Override
	public void buildCPInstances(
			long cpDefinitionId, ServiceContext serviceContext)
		throws PortalException {

		CPDefinition cpDefinition = cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);

		boolean neverExpire = false;

		if (cpDefinition.getExpirationDate() == null) {
			neverExpire = true;
		}

		SKUCombinationsIterator iterator = _getSKUCombinationsIterator(
			cpDefinitionId);

		while (iterator.hasNext()) {
			CPDefinitionOptionValueRel[] cpDefinitionOptionValueRels =
				iterator.next();

			String sku = _getSKU(
				cpDefinitionOptionValueRels, serviceContext.getLanguageId());

			CPInstance cpInstance = cpInstancePersistence.fetchByCPDI_SKU(
				cpDefinitionId, sku);

			if (cpInstance != null) {
				continue;
			}

			addCPInstance(
				cpDefinitionId, cpDefinition.getGroupId(), sku,
				StringPool.BLANK, StringPool.BLANK, true,
				_toCpDefinitionOptionRelIdCPDefinitionOptionValueRelIds(
					cpDefinitionOptionValueRels),
				cpDefinition.getWidth(), cpDefinition.getHeight(),
				cpDefinition.getDepth(), cpDefinition.getWeight(),
				BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, true,
				cpDefinition.getDisplayDate(), cpDefinition.getExpirationDate(),
				neverExpire, serviceContext);
		}
	}

	@Override
	public void checkCPInstances() throws PortalException {
		checkCPInstancesByDisplayDate(0);
		checkCPInstancesByExpirationDate();
	}

	@Override
	public void checkCPInstancesByDisplayDate(long cpDefinitionId)
		throws PortalException {

		List<CPInstance> cpInstances = null;

		if (cpDefinitionId > 0) {
			cpInstances = cpInstancePersistence.findByC_LtD_S(
				cpDefinitionId, new Date(), WorkflowConstants.STATUS_SCHEDULED);
		}
		else {
			cpInstances = cpInstancePersistence.findByLtD_S(
				new Date(), WorkflowConstants.STATUS_SCHEDULED);
		}

		for (CPInstance cpInstance : cpInstances) {
			long userId = PortalUtil.getValidUserId(
				cpInstance.getCompanyId(), cpInstance.getUserId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCommand(Constants.UPDATE);
			serviceContext.setScopeGroupId(cpInstance.getGroupId());
			serviceContext.setUserId(userId);
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

			CPDefinition cpDefinition =
				cpDefinitionLocalService.getCPDefinition(cpDefinitionId);

			if (cpDefinition.isIgnoreSKUCombinations()) {
				_expireApprovedSiblingCPInstances(
					cpDefinitionId, cpInstance.getCPInstanceId(),
					serviceContext);
			}
			else {
				_expireApprovedSiblingMatchingCPInstances(
					cpDefinitionId, cpInstance.getCPInstanceId(),
					serviceContext);
			}

			cpInstanceLocalService.updateStatus(
				userId, cpInstance.getCPInstanceId(),
				WorkflowConstants.STATUS_APPROVED);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPInstance deleteCPInstance(CPInstance cpInstance)
		throws PortalException {

		if (cpDefinitionLocalService.isVersionable(
				cpInstance.getCPDefinitionId())) {

			CPDefinition newCPDefinition =
				cpDefinitionLocalService.copyCPDefinition(
					cpInstance.getCPDefinitionId());

			cpInstance = cpInstancePersistence.findByC_C(
				newCPDefinition.getCPDefinitionId(),
				cpInstance.getCPInstanceUuid());
		}

		// Commerce product instance

		cpInstancePersistence.remove(cpInstance);

		cpInstanceOptionValueRelPersistence.removeByCPInstanceId(
			cpInstance.getCPInstanceId());

		cpDefinitionOptionValueRelLocalService.
			resetCPInstanceCPDefinitionOptionValueRels(
				cpInstance.getCPInstanceUuid());

		// Expando

		_expandoRowLocalService.deleteRows(cpInstance.getCPInstanceId());

		// Workflow

		_workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
			cpInstance.getCompanyId(), cpInstance.getGroupId(),
			CPInstance.class.getName(), cpInstance.getCPInstanceId());

		reindexCPDefinition(cpInstance.getCPDefinitionId());

		return cpInstance;
	}

	@Override
	public CPInstance deleteCPInstance(long cpInstanceId)
		throws PortalException {

		CPInstance cpInstance = cpInstancePersistence.findByPrimaryKey(
			cpInstanceId);

		return cpInstanceLocalService.deleteCPInstance(cpInstance);
	}

	@Override
	public void deleteCPInstances(long cpDefinitionId) throws PortalException {
		List<CPInstance> cpInstances =
			cpInstancePersistence.findByCPDefinitionId(cpDefinitionId);

		for (CPInstance cpInstance : cpInstances) {
			cpInstanceLocalService.deleteCPInstance(cpInstance);
		}
	}

	@Override
	public CPInstance fetchByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return cpInstancePersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	@Override
	public CPInstance fetchCProductInstance(
		long cProductId, String cpInstanceUuid) {

		CProduct cProduct = cProductLocalService.fetchCProduct(cProductId);

		if (cProduct == null) {
			return null;
		}

		return cpInstancePersistence.fetchByC_C(
			cProduct.getPublishedCPDefinitionId(), cpInstanceUuid);
	}

	@Override
	public List<CPInstance> getCPDefinitionApprovedCPInstances(
		long cpDefinitionId) {

		return cpInstancePersistence.findByC_ST(
			cpDefinitionId, WorkflowConstants.STATUS_APPROVED,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * @param      cpDefinitionId
	 * @return
	 *
	 * @deprecated As of Athanasius (7.3.x), use workflow aware method {@link
	 *             #getCPDefinitionInstances(long, int, int, int,
	 *             OrderByComparator)} or {@link
	 *             #getCPDefinitionApprovedCPInstances(long)}
	 */
	@Deprecated
	@Override
	public List<CPInstance> getCPDefinitionInstances(long cpDefinitionId) {
		return cpInstancePersistence.findByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public List<CPInstance> getCPDefinitionInstances(
		long cpDefinitionId, int status, int start, int end,
		OrderByComparator<CPInstance> orderByComparator) {

		if (status == WorkflowConstants.STATUS_ANY) {
			return cpInstancePersistence.findByCPDefinitionId(
				cpDefinitionId, start, end, orderByComparator);
		}

		return cpInstancePersistence.findByC_ST(
			cpDefinitionId, status, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionInstancesCount(long cpDefinitionId, int status) {
		if (status == WorkflowConstants.STATUS_ANY) {
			return cpInstancePersistence.countByCPDefinitionId(cpDefinitionId);
		}

		return cpInstancePersistence.countByC_ST(cpDefinitionId, status);
	}

	@Override
	public CPInstance getCPInstance(long cpDefinitionId, String sku)
		throws PortalException {

		return cpInstancePersistence.findByCPDI_SKU(cpDefinitionId, sku);
	}

	@Override
	public CPInstance getCPInstanceByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			throw new NoSuchCPInstanceException();
		}

		return cpInstancePersistence.findByC_ERC(
			companyId, externalReferenceCode);
	}

	@Override
	public List<CPInstance> getCPInstances(
			long groupId, int status, int start, int end,
			OrderByComparator<CPInstance> orderByComparator)
		throws PortalException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return cpInstancePersistence.findByGroupId(
				groupId, start, end, orderByComparator);
		}

		return cpInstancePersistence.findByG_ST(
			groupId, status, start, end, orderByComparator);
	}

	@Override
	public List<CPInstance> getCPInstances(long companyId, String sku) {
		return cpInstancePersistence.findByC_SKU(companyId, sku);
	}

	@Override
	public int getCPInstancesCount(long groupId, int status)
		throws PortalException {

		if (status == WorkflowConstants.STATUS_ANY) {
			return cpInstancePersistence.countByGroupId(groupId);
		}

		return cpInstancePersistence.countByG_ST(groupId, status);
	}

	@Override
	public CPInstance getCProductInstance(
			long cProductId, String cpInstanceUuid)
		throws PortalException {

		CProduct cProduct = cProductLocalService.getCProduct(cProductId);

		return cpInstancePersistence.findByC_C(
			cProduct.getPublishedCPDefinitionId(), cpInstanceUuid);
	}

	@Override
	public String[] getSKUs(long cpDefinitionId) {
		List<CPInstance> cpInstances = getCPDefinitionInstances(
			cpDefinitionId, WorkflowConstants.STATUS_APPROVED,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		String[] skusArray = new String[cpInstances.size()];

		List<String> skus = new ArrayList<>();

		for (CPInstance cpInstance : cpInstances) {
			skus.add(cpInstance.getSku());
		}

		return skus.toArray(skusArray);
	}

	@Override
	public void inactivateCPDefinitionOptionRelCPInstances(
			long userId, long cpDefinitionId, long cpDefinitionOptionRelId)
		throws PortalException {

		_inactivateCPDefinitionOptionRelCPInstances(
			userId, cpDefinitionOptionRelId,
			cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinitionId, WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null));
	}

	@Override
	public void inactivateCPDefinitionOptionValueRelCPInstances(
			long userId, long cpDefinitionId, long cpDefinitionOptionValueRelId)
		throws PortalException {

		_inactivateCPDefinitionOptionValueRelCPInstances(
			userId, cpDefinitionOptionValueRelId,
			cpInstanceLocalService.getCPDefinitionInstances(
				cpDefinitionId, WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null));
	}

	@Override
	public void inactivateIncompatibleCPInstances(
			long userId, long cpDefinitionId)
		throws PortalException {

		List<CPInstance> cpInstances = cpInstancePersistence.findByC_ST(
			cpDefinitionId, WorkflowConstants.STATUS_APPROVED);

		for (CPInstance curCPInstance : cpInstances) {
			if (cpInstanceOptionValueRelLocalService.
					matchesCPDefinitionOptionRels(
						cpDefinitionId, curCPInstance.getCPInstanceId())) {

				continue;
			}

			cpInstanceLocalService.updateStatus(
				userId, curCPInstance.getCPInstanceId(),
				WorkflowConstants.STATUS_INACTIVE);
		}
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CPInstance> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(CPInstance.class);

			return indexer.search(searchContext);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Override
	public BaseModelSearchResult<CPInstance> searchCPDefinitionInstances(
			long companyId, long cpDefinitionId, String keywords, int status,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, cpDefinitionId, keywords, status, start, end, sort);

		return searchCPInstances(searchContext);
	}

	@Override
	public BaseModelSearchResult<CPInstance> searchCPDefinitionInstances(
			long companyId, long cpDefinitionId, String keywords, int status,
			Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, cpDefinitionId, keywords, status, sort);

		return searchCPInstances(searchContext);
	}

	@Override
	public BaseModelSearchResult<CPInstance> searchCPInstances(
			long companyId, long[] groupIds, String keywords, int status,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupIds, keywords, status, start, end, sort);

		return searchCPInstances(searchContext);
	}

	@Override
	public BaseModelSearchResult<CPInstance> searchCPInstances(
			long companyId, String keywords, int status, int start, int end,
			Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, status, start, end, sort);

		return searchCPInstances(searchContext);
	}

	@Override
	public BaseModelSearchResult<CPInstance> searchCPInstances(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CPInstance> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPInstance.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CPInstance> cpInstances = getCPInstances(hits);

			if (cpInstances != null) {
				return new BaseModelSearchResult<>(
					cpInstances, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public CPInstance updateCPInstance(
			long cpInstanceId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return cpInstanceLocalService.updateCPInstance(
			cpInstanceId, sku, gtin, manufacturerPartNumber, purchasable,
			published, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, null, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public CPInstance updateCPInstance(
			long cpInstanceId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			boolean published, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, String unspsc,
			ServiceContext serviceContext)
		throws PortalException {

		CPInstance cpInstance = cpInstancePersistence.findByPrimaryKey(
			cpInstanceId);

		int discontinuedDateMonth = 0;
		int discontinuedDateDay = 0;
		int discontinuedDateYear = 0;

		Date discontinuedDate = cpInstance.getDiscontinuedDate();

		if (discontinuedDate != null) {
			Calendar discontinuedDateCalendar = CalendarFactoryUtil.getCalendar(
				discontinuedDate.getTime(), serviceContext.getTimeZone());

			discontinuedDateMonth = discontinuedDateCalendar.get(
				Calendar.MONTH);
			discontinuedDateDay = discontinuedDateCalendar.get(
				Calendar.DAY_OF_MONTH);
			discontinuedDateYear = discontinuedDateCalendar.get(Calendar.YEAR);
		}

		return cpInstanceLocalService.updateCPInstance(
			cpInstanceId, sku, gtin, manufacturerPartNumber, purchasable,
			cpInstance.getWidth(), cpInstance.getHeight(),
			cpInstance.getDepth(), cpInstance.getWeight(),
			cpInstance.getPrice(), cpInstance.getPromoPrice(),
			cpInstance.getCost(), published, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, unspsc,
			cpInstance.isDiscontinued(), cpInstance.getCPInstanceUuid(),
			cpInstance.getReplacementCProductId(), discontinuedDateMonth,
			discontinuedDateDay, discontinuedDateYear, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPInstance updateCPInstance(
			long cpInstanceId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable, double width,
			double height, double depth, double weight, BigDecimal price,
			BigDecimal promoPrice, BigDecimal cost, boolean published,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return cpInstanceLocalService.updateCPInstance(
			cpInstanceId, sku, gtin, manufacturerPartNumber, purchasable, width,
			height, depth, weight, price, promoPrice, cost, published,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, null, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPInstance updateCPInstance(
			long cpInstanceId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable, double width,
			double height, double depth, double weight, BigDecimal price,
			BigDecimal promoPrice, BigDecimal cost, boolean published,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String unspsc, boolean discontinued,
			String replacementCPInstanceUuid, long replacementCProductId,
			int discontinuedDateMonth, int discontinuedDateDay,
			int discontinuedDateYear, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product instance

		CPInstance cpInstance = cpInstancePersistence.findByPrimaryKey(
			cpInstanceId);

		_validateSku(cpInstance.getCPDefinitionId(), cpInstanceId, sku);

		_validateReplacementCPInstance(
			cpInstance, replacementCPInstanceUuid, replacementCProductId);

		User user = userLocalService.getUser(serviceContext.getUserId());

		if (cpDefinitionLocalService.isVersionable(
				cpInstance.getCPDefinitionId())) {

			CPDefinition newCPDefinition =
				cpDefinitionLocalService.copyCPDefinition(
					cpInstance.getCPDefinitionId());

			cpInstance = cpInstancePersistence.findByC_C(
				newCPDefinition.getCPDefinitionId(),
				cpInstance.getCPInstanceUuid());
		}

		Date expirationDate = null;
		Date date = new Date();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CPInstanceDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CPInstanceExpirationDateException.class);
		}

		cpInstance.setSku(sku);
		cpInstance.setGtin(gtin);
		cpInstance.setManufacturerPartNumber(manufacturerPartNumber);
		cpInstance.setPurchasable(purchasable);
		cpInstance.setWidth(width);
		cpInstance.setHeight(height);
		cpInstance.setDepth(depth);
		cpInstance.setWeight(weight);
		cpInstance.setPrice(price);
		cpInstance.setPromoPrice(promoPrice);
		cpInstance.setCost(cost);
		cpInstance.setPublished(published);
		cpInstance.setDisplayDate(displayDate);
		cpInstance.setExpirationDate(expirationDate);

		if (!neverExpire && expirationDate.before(date)) {
			cpInstance.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		cpInstance.setStatusByUserId(user.getUserId());
		cpInstance.setStatusDate(serviceContext.getModifiedDate(date));
		cpInstance.setExpandoBridgeAttributes(serviceContext);
		cpInstance.setUnspsc(unspsc);
		cpInstance.setDiscontinued(discontinued);
		cpInstance.setReplacementCPInstanceUuid(replacementCPInstanceUuid);
		cpInstance.setReplacementCProductId(replacementCProductId);

		Date discontinuedDate = PortalUtil.getDate(
			discontinuedDateMonth, discontinuedDateDay, discontinuedDateYear);

		cpInstance.setDiscontinuedDate(discontinuedDate);

		cpInstance = cpInstancePersistence.update(cpInstance);

		reindexCPDefinition(cpInstance.getCPDefinitionId());

		if (!_isWorkflowActionPublish(serviceContext)) {
			return cpInstance;
		}

		CPDefinition cpDefinition = cpDefinitionLocalService.getCPDefinition(
			cpInstance.getCPDefinitionId());

		if (cpDefinition.isIgnoreSKUCombinations()) {
			_expireApprovedSiblingCPInstances(
				cpDefinition.getCPDefinitionId(), cpInstanceId, serviceContext);
		}
		else {
			if (!cpInstanceOptionValueRelLocalService.
					hasCPInstanceOptionValueRel(cpInstanceId)) {

				cpInstance = cpInstanceLocalService.updateStatus(
					user.getUserId(), cpInstance.getCPInstanceId(),
					WorkflowConstants.STATUS_INACTIVE);
			}

			_inactivateNoOptionSiblingCPInstances(
				cpInstance.getCPDefinitionId(), serviceContext);
		}

		// Workflow

		if ((cpInstance.getStatus() == WorkflowConstants.STATUS_APPROVED) ||
			_isWorkflowActionPublish(serviceContext)) {

			cpInstance = startWorkflowInstance(
				user.getUserId(), cpInstance, serviceContext);
		}

		return cpInstance;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public CPInstance updateCPInstance(
			long cpInstanceId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable, double width,
			double height, double depth, double weight, BigDecimal price,
			BigDecimal promoPrice, BigDecimal cost, boolean published,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String unspsc, ServiceContext serviceContext)
		throws PortalException {

		CPInstance cpInstance = cpInstancePersistence.findByPrimaryKey(
			cpInstanceId);

		int discontinuedDateMonth = 0;
		int discontinuedDateDay = 0;
		int discontinuedDateYear = 0;

		Date discontinuedDate = cpInstance.getDiscontinuedDate();

		if (discontinuedDate != null) {
			Calendar discontinuedDateCalendar = CalendarFactoryUtil.getCalendar(
				discontinuedDate.getTime(), serviceContext.getTimeZone());

			discontinuedDateMonth = discontinuedDateCalendar.get(
				Calendar.MONTH);
			discontinuedDateDay = discontinuedDateCalendar.get(
				Calendar.DAY_OF_MONTH);
			discontinuedDateYear = discontinuedDateCalendar.get(Calendar.YEAR);
		}

		return cpInstanceLocalService.updateCPInstance(
			cpInstanceId, sku, gtin, manufacturerPartNumber, purchasable, width,
			height, depth, weight, price, promoPrice, cost, published,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, unspsc, cpInstance.isDiscontinued(),
			cpInstance.getCPInstanceUuid(),
			cpInstance.getReplacementCProductId(), discontinuedDateMonth,
			discontinuedDateDay, discontinuedDateYear, serviceContext);
	}

	@Override
	public CPInstance updatePricingInfo(
			long cpInstanceId, BigDecimal price, BigDecimal promoPrice,
			BigDecimal cost, ServiceContext serviceContext)
		throws PortalException {

		CPInstance cpInstance = cpInstancePersistence.findByPrimaryKey(
			cpInstanceId);

		if (cpDefinitionLocalService.isVersionable(
				cpInstance.getCPDefinitionId())) {

			CPDefinition newCPDefinition =
				cpDefinitionLocalService.copyCPDefinition(
					cpInstance.getCPDefinitionId());

			cpInstance = cpInstancePersistence.findByC_C(
				newCPDefinition.getCPDefinitionId(),
				cpInstance.getCPInstanceUuid());
		}

		cpInstance.setPrice(price);
		cpInstance.setPromoPrice(promoPrice);
		cpInstance.setCost(cost);

		return cpInstancePersistence.update(cpInstance);
	}

	@Override
	public CPInstance updateShippingInfo(
			long cpInstanceId, double width, double height, double depth,
			double weight, ServiceContext serviceContext)
		throws PortalException {

		CPInstance cpInstance = cpInstancePersistence.findByPrimaryKey(
			cpInstanceId);

		if (cpDefinitionLocalService.isVersionable(
				cpInstance.getCPDefinitionId())) {

			CPDefinition newCPDefinition =
				cpDefinitionLocalService.copyCPDefinition(
					cpInstance.getCPDefinitionId());

			cpInstance = cpInstancePersistence.findByC_C(
				newCPDefinition.getCPDefinitionId(),
				cpInstance.getCPInstanceUuid());
		}

		cpInstance.setWidth(width);
		cpInstance.setHeight(height);
		cpInstance.setDepth(depth);
		cpInstance.setWeight(weight);

		return cpInstancePersistence.update(cpInstance);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPInstance updateStatus(long userId, long cpInstanceId, int status)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		Date date = new Date();

		CPInstance cpInstance = cpInstancePersistence.findByPrimaryKey(
			cpInstanceId);

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		if (!cpDefinition.isIgnoreSKUCombinations() &&
			!cpInstanceOptionValueRelLocalService.hasCPInstanceOptionValueRel(
				cpInstance.getCPInstanceId())) {

			status = WorkflowConstants.STATUS_INACTIVE;
		}

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(cpInstance.getDisplayDate() != null) &&
			date.before(cpInstance.getDisplayDate())) {

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		if (status == WorkflowConstants.STATUS_APPROVED) {
			Date expirationDate = cpInstance.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(date)) {
				cpInstance.setExpirationDate(null);
			}
		}

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			cpInstance.setExpirationDate(date);
		}

		if ((cpInstance.getStatus() == WorkflowConstants.STATUS_APPROVED) &&
			(status != WorkflowConstants.STATUS_APPROVED)) {

			cpDefinitionOptionValueRelLocalService.
				resetCPInstanceCPDefinitionOptionValueRels(
					cpInstance.getCPInstanceUuid());
		}

		cpInstance.setStatus(status);
		cpInstance.setStatusByUserId(user.getUserId());
		cpInstance.setStatusByUserName(user.getFullName());
		cpInstance.setStatusDate(date);

		return cpInstancePersistence.update(cpInstance);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPInstance updateSubscriptionInfo(
			long cpInstanceId, boolean overrideSubscriptionInfo,
			boolean subscriptionEnabled, int subscriptionLength,
			String subscriptionType,
			UnicodeProperties subscriptionTypeSettingsUnicodeProperties,
			long maxSubscriptionCycles, boolean deliverySubscriptionEnabled,
			int deliverySubscriptionLength, String deliverySubscriptionType,
			UnicodeProperties deliverySubscriptionTypeSettingsUnicodeProperties,
			long deliveryMaxSubscriptionCycles)
		throws PortalException {

		CPInstance cpInstance = cpInstancePersistence.findByPrimaryKey(
			cpInstanceId);

		if (cpDefinitionLocalService.isVersionable(
				cpInstance.getCPDefinitionId())) {

			CPDefinition newCPDefinition =
				cpDefinitionLocalService.copyCPDefinition(
					cpInstance.getCPDefinitionId());

			cpInstance = cpInstancePersistence.findByC_C(
				newCPDefinition.getCPDefinitionId(),
				cpInstance.getCPInstanceUuid());
		}

		cpInstance.setOverrideSubscriptionInfo(overrideSubscriptionInfo);
		cpInstance.setSubscriptionEnabled(subscriptionEnabled);
		cpInstance.setSubscriptionLength(subscriptionLength);
		cpInstance.setSubscriptionType(subscriptionType);
		cpInstance.setSubscriptionTypeSettingsProperties(
			subscriptionTypeSettingsUnicodeProperties);
		cpInstance.setMaxSubscriptionCycles(maxSubscriptionCycles);
		cpInstance.setDeliverySubscriptionEnabled(deliverySubscriptionEnabled);
		cpInstance.setDeliverySubscriptionLength(deliverySubscriptionLength);
		cpInstance.setDeliverySubscriptionType(deliverySubscriptionType);
		cpInstance.setDeliverySubscriptionTypeSettingsProperties(
			deliverySubscriptionTypeSettingsUnicodeProperties);
		cpInstance.setDeliveryMaxSubscriptionCycles(
			deliveryMaxSubscriptionCycles);

		return cpInstancePersistence.update(cpInstance);
	}

	protected CPInstance addCPInstance(
			long cpDefinitionId, long groupId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			Map<Long, List<Long>>
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			double width, double height, double depth, double weight,
			BigDecimal price, BigDecimal promoPrice, BigDecimal cost,
			boolean published, Date displayDate, Date expirationDate,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return addCPInstance(
			cpDefinitionId, groupId, sku, gtin, manufacturerPartNumber,
			purchasable, cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			width, height, depth, weight, price, promoPrice, cost, published,
			displayDate, expirationDate, neverExpire, null, serviceContext);
	}

	protected CPInstance addCPInstance(
			long cpDefinitionId, long groupId, String sku, String gtin,
			String manufacturerPartNumber, boolean purchasable,
			Map<Long, List<Long>>
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			double width, double height, double depth, double weight,
			BigDecimal price, BigDecimal promoPrice, BigDecimal cost,
			boolean published, Date displayDate, Date expirationDate,
			boolean neverExpire, String unspsc, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		Calendar displayDateCalendar = CalendarFactoryUtil.getCalendar(
			displayDate.getTime(), user.getTimeZone());

		int displayDateMonth = displayDateCalendar.get(Calendar.MONTH);
		int displayDateDay = displayDateCalendar.get(Calendar.DAY_OF_MONTH);
		int displayDateYear = displayDateCalendar.get(Calendar.YEAR);
		int displayDateHour = displayDateCalendar.get(Calendar.HOUR);
		int displayDateMinute = displayDateCalendar.get(Calendar.MINUTE);

		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;

		if (!neverExpire) {
			Calendar expirationDateCalendar = CalendarFactoryUtil.getCalendar(
				expirationDate.getTime(), user.getTimeZone());

			expirationDateMonth = expirationDateCalendar.get(Calendar.MONTH);
			expirationDateDay = expirationDateCalendar.get(
				Calendar.DAY_OF_MONTH);
			expirationDateYear = expirationDateCalendar.get(Calendar.YEAR);
			expirationDateHour = expirationDateCalendar.get(Calendar.HOUR);
			expirationDateMinute = expirationDateCalendar.get(Calendar.MINUTE);
		}

		return cpInstanceLocalService.addCPInstance(
			StringPool.BLANK, cpDefinitionId, groupId, sku, gtin,
			manufacturerPartNumber, purchasable,
			cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds, width, height,
			depth, weight, price, promoPrice, cost, published, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire, false, false,
			1, StringPool.BLANK, null, 0, unspsc, serviceContext);
	}

	protected SearchContext buildSearchContext(
		long companyId, long cpDefinitionId, String keywords, int status,
		int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				CPField.CP_DEFINITION_ID, cpDefinitionId
			).put(
				CPField.CP_DEFINITION_STATUS, WorkflowConstants.STATUS_ANY
			).put(
				Field.CONTENT, keywords
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

	protected SearchContext buildSearchContext(
		long companyId, long cpDefinitionId, String keywords, int status,
		Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				CPField.CP_DEFINITION_ID, cpDefinitionId
			).put(
				CPField.CP_DEFINITION_STATUS, WorkflowConstants.STATUS_ANY
			).put(
				Field.CONTENT, keywords
			).put(
				Field.STATUS, status
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords", keywords
				).build()
			).build());

		searchContext.setCompanyId(companyId);

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	protected SearchContext buildSearchContext(
		long companyId, long[] groupIds, String keywords, int status, int start,
		int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.CONTENT, keywords
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
		searchContext.setGroupIds(groupIds);

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

	protected SearchContext buildSearchContext(
		long companyId, String keywords, int status, int start, int end,
		Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.CONTENT, keywords
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

	protected void checkCPInstancesByExpirationDate() throws PortalException {
		List<CPInstance> cpInstances = cpInstanceFinder.findByExpirationDate(
			new Date(),
			new QueryDefinition<>(WorkflowConstants.STATUS_APPROVED));

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Expiring " + cpInstances.size() +
					" commerce product instances");
		}

		if ((cpInstances != null) && !cpInstances.isEmpty()) {
			for (CPInstance cpInstance : cpInstances) {
				long userId = PortalUtil.getValidUserId(
					cpInstance.getCompanyId(), cpInstance.getUserId());

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setCommand(Constants.UPDATE);
				serviceContext.setScopeGroupId(cpInstance.getGroupId());

				cpInstanceLocalService.updateStatus(
					userId, cpInstance.getCPInstanceId(),
					WorkflowConstants.STATUS_EXPIRED);
			}
		}
	}

	protected List<CPInstance> getCPInstances(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CPInstance> cpInstances = new ArrayList<>(documents.size());

		for (Document document : documents) {
			long cpInstanceId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CPInstance cpInstance = fetchCPInstance(cpInstanceId);

			if (cpInstance == null) {
				cpInstances = null;

				Indexer<CPInstance> indexer = IndexerRegistryUtil.getIndexer(
					CPInstance.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (cpInstances != null) {
				cpInstances.add(cpInstance);
			}
		}

		return cpInstances;
	}

	protected void reindexCPDefinition(long cpDefinitionId)
		throws PortalException {

		Indexer<CPDefinition> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPDefinition.class);

		indexer.reindex(CPDefinition.class.getName(), cpDefinitionId);
	}

	protected CPInstance startWorkflowInstance(
			long userId, CPInstance cpInstance, ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			cpInstance.getCompanyId(), cpInstance.getGroupId(), userId,
			CPInstance.class.getName(), cpInstance.getCPInstanceId(),
			cpInstance, serviceContext, workflowContext);
	}

	private void _checkReplacementCPInstance(
			String cpInstanceUuid, long cProductId,
			String replacementCPInstanceUuid, long replacementCProductId)
		throws CPInstanceReplacementCPInstanceUuidException {

		CPInstance replacementCPInstance =
			cpInstanceLocalService.fetchCProductInstance(
				replacementCProductId, replacementCPInstanceUuid);

		if (replacementCPInstance == null) {
			return;
		}

		if ((cProductId == replacementCPInstance.getReplacementCProductId()) &&
			cpInstanceUuid.equals(
				replacementCPInstance.getReplacementCPInstanceUuid())) {

			throw new CPInstanceReplacementCPInstanceUuidException();
		}

		_checkReplacementCPInstance(
			cpInstanceUuid, cProductId,
			replacementCPInstance.getReplacementCPInstanceUuid(),
			replacementCPInstance.getReplacementCProductId());
	}

	private void _expireApprovedSiblingCPInstances(
			long cpDefinitionId, long siblingCPInstanceId,
			ServiceContext serviceContext)
		throws PortalException {

		List<CPInstance> cpInstances = cpInstancePersistence.findByC_ST(
			cpDefinitionId, WorkflowConstants.STATUS_APPROVED);

		for (CPInstance cpInstance : cpInstances) {
			if (cpInstance.getCPInstanceId() == siblingCPInstanceId) {
				continue;
			}

			cpInstanceLocalService.updateStatus(
				serviceContext.getUserId(), cpInstance.getCPInstanceId(),
				WorkflowConstants.STATUS_EXPIRED);
		}
	}

	private void _expireApprovedSiblingMatchingCPInstances(
			long cpDefinitionId, long cpInstanceId,
			ServiceContext serviceContext)
		throws PortalException {

		List<CPInstance> cpInstances = cpInstancePersistence.findByC_ST(
			cpDefinitionId, WorkflowConstants.STATUS_APPROVED);

		List<CPInstanceOptionValueRel> cpInstanceCPInstanceOptionValueRels =
			cpInstanceOptionValueRelLocalService.
				getCPInstanceCPInstanceOptionValueRels(cpInstanceId);

		for (CPInstance curCPInstance : cpInstances) {
			if (!cpInstanceOptionValueRelLocalService.
					matchesCPInstanceOptionValueRels(
						curCPInstance.getCPInstanceId(),
						cpInstanceCPInstanceOptionValueRels)) {

				continue;
			}

			cpInstanceLocalService.updateStatus(
				serviceContext.getUserId(), curCPInstance.getCPInstanceId(),
				WorkflowConstants.STATUS_EXPIRED);
		}
	}

	private void _expireApprovedSiblingMatchingCPInstances(
			long cpDefinitionId,
			Map<Long, List<Long>>
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds,
			ServiceContext serviceContext)
		throws PortalException {

		List<CPInstance> cpInstances = cpInstancePersistence.findByC_ST(
			cpDefinitionId, WorkflowConstants.STATUS_APPROVED);

		for (CPInstance curCPInstance : cpInstances) {
			if (!cpInstanceOptionValueRelLocalService.
					matchesCPInstanceOptionValueRels(
						curCPInstance.getCPInstanceId(),
						cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds)) {

				continue;
			}

			cpInstanceLocalService.updateStatus(
				serviceContext.getUserId(), curCPInstance.getCPInstanceId(),
				WorkflowConstants.STATUS_EXPIRED);
		}
	}

	private String _getSKU(
		CPDefinitionOptionValueRel[] cpDefinitionOptionValueRels,
		String languageId) {

		StringBundler skuSB = new StringBundler(
			cpDefinitionOptionValueRels.length + 1);

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			skuSB.append(
				StringUtil.toUpperCase(
					cpDefinitionOptionValueRel.getName(languageId)));
		}

		return skuSB.toString();
	}

	private SKUCombinationsIterator _getSKUCombinationsIterator(
			long cpDefinitionId)
		throws NoSuchSkuContributorCPDefinitionOptionRelException {

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
				cpDefinitionId, true);

		if (cpDefinitionOptionRels.isEmpty()) {
			throw new NoSuchSkuContributorCPDefinitionOptionRelException();
		}

		Map<CPDefinitionOptionRel, CPDefinitionOptionValueRel[]>
			combinationGeneratorMap = new HashMap<>();

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRel.getCPDefinitionOptionValueRels();

			CPDefinitionOptionValueRel[] cpDefinitionOptionValueRelArray =
				new CPDefinitionOptionValueRel
					[cpDefinitionOptionValueRels.size()];

			cpDefinitionOptionValueRelArray =
				cpDefinitionOptionValueRels.toArray(
					cpDefinitionOptionValueRelArray);

			combinationGeneratorMap.put(
				cpDefinitionOptionRel, cpDefinitionOptionValueRelArray);
		}

		return new SKUCombinationsIterator(combinationGeneratorMap);
	}

	private void _inactivateCPDefinitionOptionRelCPInstances(
			long userId, long cpDefinitionOptionRelId,
			List<CPInstance> cpInstances)
		throws PortalException {

		for (CPInstance cpInstance : cpInstances) {
			if (cpInstance.isInactive() ||
				!cpInstanceOptionValueRelLocalService.
					hasCPInstanceCPDefinitionOptionRel(
						cpDefinitionOptionRelId,
						cpInstance.getCPInstanceId())) {

				continue;
			}

			if (userId <= 0) {
				userId = cpInstance.getUserId();
			}

			cpInstanceLocalService.updateStatus(
				userId, cpInstance.getCPInstanceId(),
				WorkflowConstants.STATUS_INACTIVE);
		}
	}

	private void _inactivateCPDefinitionOptionValueRelCPInstances(
			long userId, long cpDefinitionOptionValueRelId,
			List<CPInstance> cpInstances)
		throws PortalException {

		for (CPInstance cpInstance : cpInstances) {
			if (cpInstance.isInactive() ||
				!cpInstanceOptionValueRelLocalService.
					hasCPInstanceCPDefinitionOptionValueRel(
						cpDefinitionOptionValueRelId,
						cpInstance.getCPInstanceId())) {

				continue;
			}

			if (userId <= 0) {
				userId = cpInstance.getUserId();
			}

			cpInstanceLocalService.updateStatus(
				userId, cpInstance.getCPInstanceId(),
				WorkflowConstants.STATUS_INACTIVE);
		}
	}

	private void _inactivateNoOptionSiblingCPInstances(
			long cpDefinitionId, ServiceContext serviceContext)
		throws PortalException {

		List<CPInstance> cpInstances = cpInstancePersistence.findByC_ST(
			cpDefinitionId, WorkflowConstants.STATUS_APPROVED);

		for (CPInstance curCPInstance : cpInstances) {
			if (cpInstanceOptionValueRelLocalService.
					hasCPInstanceOptionValueRel(
						curCPInstance.getCPInstanceId())) {

				continue;
			}

			cpInstanceLocalService.updateStatus(
				serviceContext.getUserId(), curCPInstance.getCPInstanceId(),
				WorkflowConstants.STATUS_INACTIVE);
		}
	}

	private boolean _isWorkflowActionPublish(ServiceContext serviceContext) {
		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_PUBLISH) {

			return true;
		}

		return false;
	}

	private Map<Long, List<Long>>
		_toCpDefinitionOptionRelIdCPDefinitionOptionValueRelIds(
			CPDefinitionOptionValueRel[] cpDefinitionOptionValueRels) {

		Map<Long, List<Long>>
			cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds =
				new HashMap<>();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			List<Long> cpDefinitionOptionValueRelIds =
				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds.get(
					cpDefinitionOptionValueRel.getCPDefinitionOptionRelId());

			if (cpDefinitionOptionValueRelIds == null) {
				cpDefinitionOptionValueRelIds = new ArrayList<>();

				cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds.put(
					cpDefinitionOptionValueRel.getCPDefinitionOptionRelId(),
					cpDefinitionOptionValueRelIds);
			}

			cpDefinitionOptionValueRelIds.add(
				cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId());
		}

		return cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds;
	}

	private void _validateReplacementCPInstance(
			CPInstance cpInstance, String replacementCPInstanceUuid,
			long replacementCProductId)
		throws PortalException {

		CPDefinition cpDefinition = cpInstance.getCPDefinition();

		if ((replacementCProductId == cpDefinition.getCProductId()) &&
			replacementCPInstanceUuid.equals(cpInstance.getCPInstanceUuid())) {

			throw new CPInstanceReplacementCPInstanceUuidException();
		}

		_checkReplacementCPInstance(
			cpInstance.getCPInstanceUuid(), cpDefinition.getCProductId(),
			replacementCPInstanceUuid, replacementCProductId);
	}

	private void _validateSku(
			long cpDefinitionId, long cpInstanceId, String sku)
		throws CPInstanceSkuException {

		if (Validator.isNull(sku)) {
			throw new CPInstanceSkuException(
				"SKU value required for product definition ID " +
					cpDefinitionId);
		}

		CPInstance cpInstance = cpInstancePersistence.fetchByCPDI_SKU(
			cpDefinitionId, sku);

		if ((cpInstance == null) ||
			(cpInstanceId == cpInstance.getCPInstanceId())) {

			return;
		}

		throw new CPInstanceSkuException(
			"Duplicate SKU value for product definition ID " + cpDefinitionId);
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CPInstanceLocalServiceImpl.class);

	@ServiceReference(type = ExpandoRowLocalService.class)
	private ExpandoRowLocalService _expandoRowLocalService;

	@ServiceReference(type = WorkflowInstanceLinkLocalService.class)
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}