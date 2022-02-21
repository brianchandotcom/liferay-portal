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

package com.liferay.commerce.price.list.service.impl;

import com.liferay.commerce.price.list.exception.CommercePriceEntryDisplayDateException;
import com.liferay.commerce.price.list.exception.CommercePriceEntryExpirationDateException;
import com.liferay.commerce.price.list.exception.DuplicateCommercePriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchPriceEntryException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceEntryTable;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.base.CommercePriceEntryLocalServiceBaseImpl;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Zoltán Takács
 */
public class CommercePriceEntryLocalServiceImpl
	extends CommercePriceEntryLocalServiceBaseImpl {

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public CommercePriceEntry addCommercePriceEntry(
			long cpInstanceId, long commercePriceListId, BigDecimal price,
			BigDecimal promoPrice, ServiceContext serviceContext)
		throws PortalException {

		return addCommercePriceEntry(
			null, cpInstanceId, commercePriceListId, price, promoPrice,
			serviceContext);
	}

	@Override
	public CommercePriceEntry addCommercePriceEntry(
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			BigDecimal price, BigDecimal promoPrice,
			ServiceContext serviceContext)
		throws PortalException {

		return addCommercePriceEntry(
			cProductId, cpInstanceUuid, commercePriceListId, null, price,
			promoPrice, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addCommercePriceEntry(String, long, String, long,
	 *             BigDecimal, BigDecimal, boolean, BigDecimal, BigDecimal,
	 *             BigDecimal, BigDecimal, int, int, int, int, int, int, int,
	 *             int, int, int, boolean, ServiceContext)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry addCommercePriceEntry(
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			String externalReferenceCode, BigDecimal price,
			BigDecimal promoPrice, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return addCommercePriceEntry(
			externalReferenceCode, cProductId, cpInstanceUuid,
			commercePriceListId, price, promoPrice, discountDiscovery,
			discountLevel1, discountLevel2, discountLevel3, discountLevel4,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addCommercePriceEntry(String, long, String, long,
	 *             BigDecimal, BigDecimal, ServiceContext)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry addCommercePriceEntry(
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			String externalReferenceCode, BigDecimal price,
			BigDecimal promoPrice, ServiceContext serviceContext)
		throws PortalException {

		return addCommercePriceEntry(
			externalReferenceCode, cProductId, cpInstanceUuid,
			commercePriceListId, price, promoPrice, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addCommercePriceEntry(String, long, String, long,
	 *             BigDecimal, boolean, BigDecimal, BigDecimal, BigDecimal,
	 *             BigDecimal, int, int, int, int, int, int, int, int, int,
	 *             int, boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public CommercePriceEntry addCommercePriceEntry(
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			String externalReferenceCode, BigDecimal price,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return addCommercePriceEntry(
			externalReferenceCode, cProductId, cpInstanceUuid,
			commercePriceListId, price, discountDiscovery, discountLevel1,
			discountLevel2, discountLevel3, discountLevel4, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long cpInstanceId,
			long commercePriceListId, BigDecimal price, BigDecimal promoPrice,
			ServiceContext serviceContext)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpInstance.getCPDefinitionId());

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		return commercePriceEntryLocalService.addCommercePriceEntry(
			cpDefinition.getCProductId(), cpInstance.getCPInstanceUuid(),
			commercePriceListId, externalReferenceCode, price, promoPrice,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long cProductId,
			String cpInstanceUuid, long commercePriceListId, BigDecimal price,
			BigDecimal promoPrice, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		validateExternalReferenceCode(
			externalReferenceCode, serviceContext.getCompanyId());

		Date expirationDate = null;
		Date date = new Date();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CommercePriceEntryDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommercePriceEntryExpirationDateException.class);
		}

		long commercePriceEntryId = counterLocalService.increment();

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryPersistence.create(commercePriceEntryId);

		commercePriceEntry.setExternalReferenceCode(externalReferenceCode);
		commercePriceEntry.setCompanyId(user.getCompanyId());
		commercePriceEntry.setUserId(user.getUserId());
		commercePriceEntry.setUserName(user.getFullName());
		commercePriceEntry.setCommercePriceListId(commercePriceListId);
		commercePriceEntry.setPrice(price);
		commercePriceEntry.setPromoPrice(promoPrice);
		commercePriceEntry.setDiscountDiscovery(discountDiscovery);
		commercePriceEntry.setDiscountLevel1(discountLevel1);
		commercePriceEntry.setDiscountLevel2(discountLevel2);
		commercePriceEntry.setDiscountLevel3(discountLevel3);
		commercePriceEntry.setDiscountLevel4(discountLevel4);
		commercePriceEntry.setExpandoBridgeAttributes(serviceContext);
		commercePriceEntry.setCPInstanceUuid(cpInstanceUuid);
		commercePriceEntry.setCProductId(cProductId);
		commercePriceEntry.setDisplayDate(displayDate);

		if ((expirationDate == null) || expirationDate.after(date)) {
			commercePriceEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commercePriceEntry.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		commercePriceEntry.setExpirationDate(expirationDate);
		commercePriceEntry.setStatusByUserId(user.getUserId());
		commercePriceEntry.setStatusDate(serviceContext.getModifiedDate(date));

		commercePriceEntry = commercePriceEntryPersistence.update(
			commercePriceEntry);

		commercePriceEntry = startWorkflowInstance(
			user.getUserId(), commercePriceEntry, serviceContext);

		return commercePriceEntry;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long cProductId,
			String cpInstanceUuid, long commercePriceListId, BigDecimal price,
			BigDecimal promoPrice, ServiceContext serviceContext)
		throws PortalException {

		Calendar now = new GregorianCalendar();

		return addCommercePriceEntry(
			cProductId, cpInstanceUuid, commercePriceListId,
			externalReferenceCode, price, promoPrice, true, null, null, null,
			null, now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH),
			now.get(Calendar.YEAR), now.get(Calendar.HOUR),
			now.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, serviceContext);
	}

	@Override
	public CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long cProductId,
			String cpInstanceUuid, long commercePriceListId, BigDecimal price,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return addCommercePriceEntry(
			externalReferenceCode, cProductId, cpInstanceUuid,
			commercePriceListId, price, null, discountDiscovery, discountLevel1,
			discountLevel2, discountLevel3, discountLevel4, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommercePriceEntry addOrUpdateCommercePriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			BigDecimal price, BigDecimal promoPrice, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String skuExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		// Update

		if (commercePriceEntryId > 0) {
			try {
				return updateCommercePriceEntry(
					commercePriceEntryId, price, promoPrice, discountDiscovery,
					discountLevel1, discountLevel2, discountLevel3,
					discountLevel4, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);
			}
			catch (NoSuchPriceEntryException noSuchPriceEntryException) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find price entry with ID: " +
							commercePriceEntryId,
						noSuchPriceEntryException);
				}
			}
		}

		CommercePriceEntry commercePriceEntry = null;

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		if (!Validator.isBlank(externalReferenceCode)) {
			commercePriceEntry = commercePriceEntryPersistence.fetchByC_ERC(
				serviceContext.getCompanyId(), externalReferenceCode);
		}

		if (commercePriceEntry != null) {
			return updateCommercePriceEntry(
				commercePriceEntry.getCommercePriceEntryId(), price, promoPrice,
				discountDiscovery, discountLevel1, discountLevel2,
				discountLevel3, discountLevel4, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, serviceContext);
		}

		// Add

		if ((cProductId > 0) && (cpInstanceUuid != null)) {
			return addCommercePriceEntry(
				externalReferenceCode, cProductId, cpInstanceUuid,
				commercePriceListId, price, promoPrice, discountDiscovery,
				discountLevel1, discountLevel2, discountLevel3, discountLevel4,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);
		}

		if (Validator.isNotNull(skuExternalReferenceCode)) {
			CPInstance cpInstance =
				_cpInstanceLocalService.getCPInstanceByExternalReferenceCode(
					skuExternalReferenceCode, serviceContext.getCompanyId());

			CPDefinition cpDefinition =
				_cpDefinitionLocalService.getCPDefinition(
					cpInstance.getCPDefinitionId());

			return addCommercePriceEntry(
				externalReferenceCode, cpDefinition.getCProductId(),
				cpInstance.getCPInstanceUuid(), commercePriceListId, price,
				promoPrice, discountDiscovery, discountLevel1, discountLevel2,
				discountLevel3, discountLevel4, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, serviceContext);
		}

		throw new NoSuchCPInstanceException(
			StringBundler.concat(
				"{cProductId=", cProductId, ", cpInstanceUuid=", cpInstanceUuid,
				", skuExternalReferenceCode=", skuExternalReferenceCode,
				CharPool.CLOSE_CURLY_BRACE));
	}

	/**
	 * This method is used to insert a new CommercePriceEntry or update an
	 * existing one
	 *
	 * @param  externalReferenceCode - The external identifier code from a 3rd
	 *         party system to be able to locate the same entity in the portal
	 *         <b>Only</b> used when updating an entity; the first entity with a
	 *         matching reference code one will be updated
	 * @param  commercePriceEntryId - <b>Only</b> used when updating an entity
	 *         the matching one will be updated
	 * @param  cProductId - <b>Only</b> used when adding a new entity
	 * @param  commercePriceListId - <b>Only</b> used when adding a new entity
	 *         to a price list
	 * @param  price
	 * @param  promoPrice
	 * @param  skuExternalReferenceCode - <b>Only</b> used when adding a new
	 *         entity, similar as <code>cpInstanceId</code> but the external
	 *         identifier code from a 3rd party system. If cpInstanceId is used,
	 *         it doesn't have any effect, otherwise it tries to fetch the
	 *         CPInstance against the external code reference
	 * @param  serviceContext
	 * @return CommercePriceEntry
	 * @throws PortalException
	 * @review
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry addOrUpdateCommercePriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			BigDecimal price, BigDecimal promoPrice,
			String skuExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		Calendar now = new GregorianCalendar();

		return addOrUpdateCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId, cProductId,
			cpInstanceUuid, commercePriceListId, price, promoPrice, true, null,
			null, null, null, now.get(Calendar.MONTH),
			now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.YEAR),
			now.get(Calendar.HOUR), now.get(Calendar.MINUTE), 0, 0, 0, 0, 0,
			true, skuExternalReferenceCode, serviceContext);
	}

	@Override
	public CommercePriceEntry addOrUpdateCommercePriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			BigDecimal price, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String skuExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		return addOrUpdateCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId, cProductId,
			cpInstanceUuid, commercePriceListId, price, null, discountDiscovery,
			discountLevel1, discountLevel2, discountLevel3, discountLevel4,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, skuExternalReferenceCode, serviceContext);
	}

	@Override
	public void checkCommercePriceEntries() throws PortalException {
		checkCommercePriceEntriesByDisplayDate();
		checkCommercePriceEntriesByExpirationDate();
	}

	@Override
	public void deleteCommercePriceEntries(long commercePriceListId)
		throws PortalException {

		List<CommercePriceEntry> commercePriceEntries =
			commercePriceEntryLocalService.getCommercePriceEntries(
				commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommercePriceEntry commercePriceEntry : commercePriceEntries) {
			commercePriceEntryLocalService.deleteCommercePriceEntry(
				commercePriceEntry);
		}
	}

	@Override
	public void deleteCommercePriceEntries(String cpInstanceUuid)
		throws PortalException {

		List<CommercePriceEntry> commercePriceEntries =
			commercePriceEntryPersistence.findByCPInstanceUuid(cpInstanceUuid);

		for (CommercePriceEntry commercePriceEntry : commercePriceEntries) {
			commercePriceEntryLocalService.deleteCommercePriceEntry(
				commercePriceEntry);
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public void deleteCommercePriceEntriesByCPInstanceId(long cpInstanceId)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		if (cpInstance != null) {
			commercePriceEntryLocalService.deleteCommercePriceEntries(
				cpInstance.getCPInstanceUuid());
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommercePriceEntry deleteCommercePriceEntry(
			CommercePriceEntry commercePriceEntry)
		throws PortalException {

		// Commerce tier price entries

		commerceTierPriceEntryLocalService.deleteCommerceTierPriceEntries(
			commercePriceEntry.getCommercePriceEntryId());

		// Commerce price entry

		commercePriceEntryPersistence.remove(commercePriceEntry);

		// Expando

		_expandoRowLocalService.deleteRows(
			commercePriceEntry.getCommercePriceEntryId());

		return commercePriceEntry;
	}

	@Override
	public CommercePriceEntry deleteCommercePriceEntry(
			long commercePriceEntryId)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryPersistence.findByPrimaryKey(
				commercePriceEntryId);

		return commercePriceEntryLocalService.deleteCommercePriceEntry(
			commercePriceEntry);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #fetchByExternalReferenceCode(String, long)}
	 */
	@Deprecated
	@Override
	public CommercePriceEntry fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		return fetchByExternalReferenceCode(externalReferenceCode, companyId);
	}

	@Override
	public CommercePriceEntry fetchByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return commercePriceEntryPersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public CommercePriceEntry fetchCommercePriceEntry(
		long cpInstanceId, long commercePriceListId) {

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance == null) {
			return null;
		}

		return commercePriceEntryLocalService.fetchCommercePriceEntry(
			commercePriceListId, cpInstance.getCPInstanceUuid());
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public CommercePriceEntry fetchCommercePriceEntry(
		long cpInstanceId, long commercePriceListId, boolean useAncestor) {

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance == null) {
			return null;
		}

		return commercePriceEntryLocalService.fetchCommercePriceEntry(
			commercePriceListId, cpInstance.getCPInstanceUuid(), useAncestor);
	}

	@Override
	public CommercePriceEntry fetchCommercePriceEntry(
		long commercePriceListId, String cpInstanceUuid) {

		List<CommercePriceEntry> commercePriceEntries = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceEntryTable.INSTANCE),
				commercePriceListId, cpInstanceUuid,
				WorkflowConstants.STATUS_ANY
			).orderBy(
				CommercePriceEntryTable.INSTANCE.displayDate.descending(),
				CommercePriceEntryTable.INSTANCE.createDate.descending()
			).limit(
				0, 1
			));

		if (commercePriceEntries.isEmpty()) {
			return null;
		}

		return commercePriceEntries.get(0);
	}

	@Override
	public CommercePriceEntry fetchCommercePriceEntry(
		long commercePriceListId, String cpInstanceUuid, boolean useAncestor) {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceListId, cpInstanceUuid,
				WorkflowConstants.STATUS_APPROVED);

		if (!useAncestor || (commercePriceEntry != null)) {
			return commercePriceEntry;
		}

		CommercePriceList commercePriceList =
			commercePriceListLocalService.fetchCommercePriceList(
				commercePriceListId);

		if ((commercePriceList == null) ||
			(commercePriceList.getParentCommercePriceListId() == 0)) {

			return null;
		}

		return commercePriceEntryLocalService.fetchCommercePriceEntry(
			commercePriceList.getParentCommercePriceListId(), cpInstanceUuid,
			useAncestor);
	}

	@Override
	public CommercePriceEntry fetchCommercePriceEntry(
		long commercePriceListId, String cpInstanceUuid, int status) {

		List<CommercePriceEntry> commercePriceEntries = dslQuery(
			_getGroupByStep(
				DSLQueryFactoryUtil.selectDistinct(
					CommercePriceEntryTable.INSTANCE),
				commercePriceListId, cpInstanceUuid, status
			).orderBy(
				CommercePriceEntryTable.INSTANCE.displayDate.descending(),
				CommercePriceEntryTable.INSTANCE.createDate.descending()
			).limit(
				0, 1
			));

		if (commercePriceEntries.isEmpty()) {
			return null;
		}

		return commercePriceEntries.get(0);
	}

	@Override
	public List<CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end) {

		return commercePriceEntryPersistence.findByCommercePriceListId(
			commercePriceListId, start, end);
	}

	@Override
	public List<CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return commercePriceEntryPersistence.findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator);
	}

	@Override
	public List<CommercePriceEntry> getCommercePriceEntriesByCompanyId(
		long companyId, int start, int end) {

		return commercePriceEntryPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public int getCommercePriceEntriesCount(long commercePriceListId) {
		return commercePriceEntryPersistence.countByCommercePriceListId(
			commercePriceListId);
	}

	@Override
	public int getCommercePriceEntriesCountByCompanyId(long companyId) {
		return commercePriceEntryPersistence.countByCompanyId(companyId);
	}

	@Override
	public CommercePriceEntry getInstanceBaseCommercePriceEntry(
		String cpInstanceUuid, String priceListType) {

		return commercePriceListFinder.findBasePriceEntry(
			cpInstanceUuid, priceListType);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end) {

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance == null) {
			return Collections.emptyList();
		}

		return commercePriceEntryLocalService.getInstanceCommercePriceEntries(
			cpInstance.getCPInstanceUuid(), start, end);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance == null) {
			return Collections.emptyList();
		}

		return commercePriceEntryLocalService.getInstanceCommercePriceEntries(
			cpInstance.getCPInstanceUuid(), start, end, orderByComparator);
	}

	@Override
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		String cpInstanceUuid, int start, int end) {

		return commercePriceListFinder.findByCPInstanceUuid(
			cpInstanceUuid, start, end);
	}

	@Override
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		String cpInstanceUuid, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return commercePriceEntryPersistence.findByCPInstanceUuid(
			cpInstanceUuid, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public int getInstanceCommercePriceEntriesCount(long cpInstanceId) {
		CPInstance cpInstance = _cpInstanceLocalService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance == null) {
			return 0;
		}

		return commercePriceEntryLocalService.
			getInstanceCommercePriceEntriesCount(
				cpInstance.getCPInstanceUuid());
	}

	@Override
	public int getInstanceCommercePriceEntriesCount(String cpInstanceUuid) {
		return commercePriceListFinder.countByCPInstanceUuid(cpInstanceUuid);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CommercePriceEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					CommercePriceEntry.class);

			return indexer.search(searchContext);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Override
	public BaseModelSearchResult<CommercePriceEntry> searchCommercePriceEntries(
			long companyId, long commercePriceListId, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, commercePriceListId, keywords, start, end, sort);

		return searchCommercePriceEntries(searchContext);
	}

	@Override
	public int searchCommercePriceEntriesCount(
			long companyId, long commercePriceListId, String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, commercePriceListId, keywords, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		return searchCommercePriceEntriesCount(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry setHasTierPrice(
			long commercePriceEntryId, boolean hasTierPrice)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryPersistence.findByPrimaryKey(
				commercePriceEntryId);

		commercePriceEntry.setHasTierPrice(hasTierPrice);
		commercePriceEntry.setBulkPricing(true);

		return commercePriceEntryPersistence.update(commercePriceEntry);
	}

	@Override
	public CommercePriceEntry setHasTierPrice(
			long commercePriceEntryId, boolean hasTierPrice,
			boolean bulkPricing)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryPersistence.findByPrimaryKey(
				commercePriceEntryId);

		commercePriceEntry.setHasTierPrice(hasTierPrice);
		commercePriceEntry.setBulkPricing(bulkPricing);

		return commercePriceEntryPersistence.update(commercePriceEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, boolean bulkPricing,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryPersistence.findByPrimaryKey(
				commercePriceEntryId);

		Date expirationDate = null;
		Date date = new Date();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CommercePriceEntryDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommercePriceEntryExpirationDateException.class);
		}

		commercePriceEntry.setExpandoBridgeAttributes(serviceContext);
		commercePriceEntry.setPrice(price);
		commercePriceEntry.setPromoPrice(promoPrice);
		commercePriceEntry.setDiscountDiscovery(discountDiscovery);
		commercePriceEntry.setDiscountLevel1(discountLevel1);
		commercePriceEntry.setDiscountLevel2(discountLevel2);
		commercePriceEntry.setDiscountLevel3(discountLevel3);
		commercePriceEntry.setDiscountLevel4(discountLevel4);
		commercePriceEntry.setBulkPricing(bulkPricing);

		commercePriceEntry.setDisplayDate(displayDate);

		if ((expirationDate == null) || expirationDate.after(date)) {
			commercePriceEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commercePriceEntry.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		commercePriceEntry.setExpirationDate(expirationDate);
		commercePriceEntry.setStatusByUserId(user.getUserId());
		commercePriceEntry.setStatusDate(serviceContext.getModifiedDate(date));

		commercePriceEntry = commercePriceEntryPersistence.update(
			commercePriceEntry);

		commercePriceEntry = startWorkflowInstance(
			user.getUserId(), commercePriceEntry, serviceContext);

		return commercePriceEntry;
	}

	@Override
	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return updateCommercePriceEntry(
			commercePriceEntryId, price, promoPrice, discountDiscovery,
			discountLevel1, discountLevel2, discountLevel3, discountLevel4,
			true, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
			ServiceContext serviceContext)
		throws PortalException {

		Calendar now = new GregorianCalendar();

		return updateCommercePriceEntry(
			commercePriceEntryId, price, promoPrice, true, null, null, null,
			null, now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH),
			now.get(Calendar.YEAR), now.get(Calendar.HOUR),
			now.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, serviceContext);
	}

	@Override
	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, BigDecimal price,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return updateCommercePriceEntry(
			commercePriceEntryId, price, null, discountDiscovery,
			discountLevel1, discountLevel2, discountLevel3, discountLevel4,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #updateExternalReferenceCode(String, CommercePriceEntry)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry updateExternalReferenceCode(
			CommercePriceEntry commercePriceEntry, String externalReferenceCode)
		throws PortalException {

		return updateExternalReferenceCode(
			externalReferenceCode, commercePriceEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry updateExternalReferenceCode(
			String externalReferenceCode, CommercePriceEntry commercePriceEntry)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		commercePriceEntry.setExternalReferenceCode(externalReferenceCode);

		return commercePriceEntryPersistence.update(commercePriceEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry updateStatus(
			long userId, long commercePriceEntryId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		Date date = new Date();

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryPersistence.findByPrimaryKey(
				commercePriceEntryId);

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(commercePriceEntry.getDisplayDate() != null) &&
			date.before(commercePriceEntry.getDisplayDate())) {

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		Date modifiedDate = serviceContext.getModifiedDate(date);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			Date expirationDate = commercePriceEntry.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(date)) {
				commercePriceEntry.setExpirationDate(null);
			}
		}

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			commercePriceEntry.setExpirationDate(date);
		}

		commercePriceEntry.setStatus(status);
		commercePriceEntry.setStatusByUserId(user.getUserId());
		commercePriceEntry.setStatusByUserName(user.getFullName());
		commercePriceEntry.setStatusDate(modifiedDate);

		return commercePriceEntryPersistence.update(commercePriceEntry);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cpInstanceId,
			long commercePriceListId, String externalReferenceCode,
			BigDecimal price, BigDecimal promoPrice,
			String skuExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpInstance.getCPDefinitionId());

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		return commercePriceEntryLocalService.upsertCommercePriceEntry(
			commercePriceEntryId, cpDefinition.getCProductId(),
			cpInstance.getCPInstanceUuid(), commercePriceListId,
			externalReferenceCode, price, promoPrice, skuExternalReferenceCode,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addOrUpdateCommercePriceEntry(String, long, long, String, long,
	 *             BigDecimal, BigDecimal, boolean, BigDecimal, BigDecimal,
	 *             BigDecimal, BigDecimal, int, int, int, int, int, int, int,
	 *             int, int, int, boolean, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cProductId, String cpInstanceUuid,
			long commercePriceListId, String externalReferenceCode,
			BigDecimal price, BigDecimal promoPrice, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String skuExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		return addOrUpdateCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId, cProductId,
			cpInstanceUuid, commercePriceListId, price, promoPrice,
			discountDiscovery, discountLevel1, discountLevel2, discountLevel3,
			discountLevel4, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, skuExternalReferenceCode,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addOrUpdateCommercePriceEntry(String, long, long, String, long,
	 *             BigDecimal, BigDecimal, String, ServiceContext)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cProductId, String cpInstanceUuid,
			long commercePriceListId, String externalReferenceCode,
			BigDecimal price, BigDecimal promoPrice,
			String skuExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		return addOrUpdateCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId, cProductId,
			cpInstanceUuid, commercePriceListId, price, promoPrice,
			skuExternalReferenceCode, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addOrUpdateCommercePriceEntry(String, long, long, String, long,
	 *             BigDecimal, BigDecimal, BigDecimal, BigDecimal, int, int,
	 *             int, int, int, int, int, int, int, int, boolean, String,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cProductId, String cpInstanceUuid,
			long commercePriceListId, String externalReferenceCode,
			BigDecimal price, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String skuExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		return upsertCommercePriceEntry(
			commercePriceEntryId, cProductId, cpInstanceUuid,
			commercePriceListId, externalReferenceCode, price,
			discountDiscovery, discountLevel1, discountLevel2, discountLevel3,
			discountLevel4, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, skuExternalReferenceCode,
			serviceContext);
	}

	protected SearchContext buildSearchContext(
		long companyId, long commercePriceListId, String keywords, int start,
		int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.ENTRY_CLASS_PK, keywords
			).put(
				"commercePriceListId", commercePriceListId
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

	protected void checkCommercePriceEntriesByDisplayDate()
		throws PortalException {

		List<CommercePriceEntry> commercePriceEntries =
			commercePriceEntryPersistence.findByLtD_S(
				new Date(), WorkflowConstants.STATUS_SCHEDULED);

		for (CommercePriceEntry commercePriceEntry : commercePriceEntries) {
			long userId = PortalUtil.getValidUserId(
				commercePriceEntry.getCompanyId(),
				commercePriceEntry.getUserId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCommand(Constants.UPDATE);

			CommercePriceList commercePriceList =
				commercePriceEntry.getCommercePriceList();

			serviceContext.setScopeGroupId(commercePriceList.getGroupId());

			commercePriceEntryLocalService.updateStatus(
				userId, commercePriceEntry.getCommercePriceEntryId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext,
				new HashMap<String, Serializable>());
		}
	}

	protected void checkCommercePriceEntriesByExpirationDate()
		throws PortalException {

		List<CommercePriceEntry> commercePriceEntries =
			commercePriceEntryPersistence.findByLtE_S(
				new Date(), WorkflowConstants.STATUS_APPROVED);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Expiring " + commercePriceEntries.size() +
					" commerce price entries");
		}

		if ((commercePriceEntries != null) && !commercePriceEntries.isEmpty()) {
			for (CommercePriceEntry commercePriceEntry : commercePriceEntries) {
				long userId = PortalUtil.getValidUserId(
					commercePriceEntry.getCompanyId(),
					commercePriceEntry.getUserId());

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setCommand(Constants.UPDATE);

				CommercePriceList commercePriceList =
					commercePriceEntry.getCommercePriceList();

				serviceContext.setScopeGroupId(commercePriceList.getGroupId());

				commercePriceEntryLocalService.updateStatus(
					userId, commercePriceEntry.getCommercePriceEntryId(),
					WorkflowConstants.STATUS_EXPIRED, serviceContext,
					new HashMap<String, Serializable>());
			}
		}
	}

	protected List<CommercePriceEntry> getCommercePriceEntries(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommercePriceEntry> commercePriceEntries = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long commercePriceEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommercePriceEntry commercePriceEntry = fetchCommercePriceEntry(
				commercePriceEntryId);

			if (commercePriceEntry == null) {
				commercePriceEntries = null;

				Indexer<CommercePriceEntry> indexer =
					IndexerRegistryUtil.getIndexer(CommercePriceEntry.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commercePriceEntries != null) {
				commercePriceEntries.add(commercePriceEntry);
			}
		}

		return commercePriceEntries;
	}

	protected BaseModelSearchResult<CommercePriceEntry>
			searchCommercePriceEntries(SearchContext searchContext)
		throws PortalException {

		Indexer<CommercePriceEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommercePriceEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CommercePriceEntry> commercePriceEntries =
				getCommercePriceEntries(hits);

			if (commercePriceEntries != null) {
				return new BaseModelSearchResult<>(
					commercePriceEntries, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected int searchCommercePriceEntriesCount(SearchContext searchContext)
		throws PortalException {

		Indexer<CommercePriceEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CommercePriceEntry.class);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	protected CommercePriceEntry startWorkflowInstance(
			long userId, CommercePriceEntry commercePriceEntry,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			commercePriceEntry.getCompanyId(), 0L, userId,
			CommercePriceEntry.class.getName(),
			commercePriceEntry.getCommercePriceEntryId(), commercePriceEntry,
			serviceContext, workflowContext);
	}

	protected void validateExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryPersistence.fetchByC_ERC(
				companyId, externalReferenceCode);

		if (commercePriceEntry != null) {
			throw new DuplicateCommercePriceEntryException(
				"There is another commerce price entry with external " +
					"reference code " + externalReferenceCode);
		}
	}

	private GroupByStep _getGroupByStep(
		FromStep fromStep, long commercePriceListId, String cpInstanceUuid,
		int status) {

		return fromStep.from(
			CommercePriceEntryTable.INSTANCE
		).where(
			CommercePriceEntryTable.INSTANCE.commercePriceListId.eq(
				commercePriceListId
			).and(
				CommercePriceEntryTable.INSTANCE.CPInstanceUuid.eq(
					cpInstanceUuid)
			).and(
				() -> {
					if (status == WorkflowConstants.STATUS_ANY) {
						return CommercePriceEntryTable.INSTANCE.status.neq(
							WorkflowConstants.STATUS_IN_TRASH);
					}

					return CommercePriceEntryTable.INSTANCE.status.eq(status);
				}
			)
		);
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceEntryLocalServiceImpl.class);

	@ServiceReference(type = CPDefinitionLocalService.class)
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@ServiceReference(type = CPInstanceLocalService.class)
	private CPInstanceLocalService _cpInstanceLocalService;

	@ServiceReference(type = ExpandoRowLocalService.class)
	private ExpandoRowLocalService _expandoRowLocalService;

}