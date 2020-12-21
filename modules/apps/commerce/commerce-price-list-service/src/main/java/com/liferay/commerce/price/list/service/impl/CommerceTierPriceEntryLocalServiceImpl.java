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
import com.liferay.commerce.price.list.exception.CommerceTierPriceEntryDisplayDateException;
import com.liferay.commerce.price.list.exception.CommerceTierPriceEntryExpirationDateException;
import com.liferay.commerce.price.list.exception.DuplicateCommerceTierPriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchPriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchTierPriceEntryException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.base.CommerceTierPriceEntryLocalServiceBaseImpl;
import com.liferay.commerce.price.list.service.persistence.CommercePriceEntryPersistence;
import com.liferay.commerce.price.list.util.comparator.CommerceTierPriceEntryMinQuantityComparator;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanReference;
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

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 * @author Zoltán Takács
 */
public class CommerceTierPriceEntryLocalServiceImpl
	extends CommerceTierPriceEntryLocalServiceBaseImpl {

	@Override
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
			long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
			boolean bulkPricing, int minQuantity, ServiceContext serviceContext)
		throws PortalException {

		return addCommerceTierPriceEntry(
			commercePriceEntryId, null, price, promoPrice, bulkPricing,
			minQuantity, serviceContext);
	}

	@Override
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
			long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
			int minQuantity, ServiceContext serviceContext)
		throws PortalException {

		return addCommerceTierPriceEntry(
			commercePriceEntryId, null, price, promoPrice, minQuantity,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
			long commercePriceEntryId, String externalReferenceCode,
			BigDecimal price, BigDecimal promoPrice, boolean bulkPricing,
			int minQuantity, ServiceContext serviceContext)
		throws PortalException {

		Calendar now = new GregorianCalendar();

		return addCommerceTierPriceEntry(
			commercePriceEntryId, externalReferenceCode, price, promoPrice,
			minQuantity, bulkPricing, true, null, null, null, null,
			now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH),
			now.get(Calendar.YEAR), now.get(Calendar.HOUR),
			now.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
			long commercePriceEntryId, String externalReferenceCode,
			BigDecimal price, BigDecimal promoPrice, int minQuantity,
			boolean bulkPricing, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		// Commerce tier price entry

		User user = userLocalService.getUser(serviceContext.getUserId());

		validate(0, commercePriceEntryId, minQuantity);

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		validateExternalReferenceCode(
			serviceContext.getCompanyId(), externalReferenceCode);

		Date expirationDate = null;
		Date now = new Date();

		Date displayDate = PortalUtil.getDate(
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, user.getTimeZone(),
			CommerceTierPriceEntryDisplayDateException.class);

		if (!neverExpire) {
			expirationDate = PortalUtil.getDate(
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, user.getTimeZone(),
				CommerceTierPriceEntryExpirationDateException.class);
		}

		long commerceTierPriceEntryId = counterLocalService.increment();

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryPersistence.create(commerceTierPriceEntryId);

		commerceTierPriceEntry.setCompanyId(user.getCompanyId());
		commerceTierPriceEntry.setUserId(user.getUserId());
		commerceTierPriceEntry.setUserName(user.getFullName());
		commerceTierPriceEntry.setCommercePriceEntryId(commercePriceEntryId);
		commerceTierPriceEntry.setPrice(price);
		commerceTierPriceEntry.setPromoPrice(promoPrice);
		commerceTierPriceEntry.setDiscountDiscovery(discountDiscovery);
		commerceTierPriceEntry.setDiscountLevel1(discountLevel1);
		commerceTierPriceEntry.setDiscountLevel2(discountLevel2);
		commerceTierPriceEntry.setDiscountLevel3(discountLevel3);
		commerceTierPriceEntry.setDiscountLevel4(discountLevel4);
		commerceTierPriceEntry.setMinQuantity(minQuantity);
		commerceTierPriceEntry.setExpandoBridgeAttributes(serviceContext);
		commerceTierPriceEntry.setExternalReferenceCode(externalReferenceCode);
		commerceTierPriceEntry.setDisplayDate(displayDate);

		if ((expirationDate == null) || expirationDate.after(now)) {
			commerceTierPriceEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commerceTierPriceEntry.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		commerceTierPriceEntry.setExpirationDate(expirationDate);
		commerceTierPriceEntry.setStatusByUserId(user.getUserId());
		commerceTierPriceEntry.setStatusDate(
			serviceContext.getModifiedDate(now));

		commerceTierPriceEntry = commerceTierPriceEntryPersistence.update(
			commerceTierPriceEntry);

		// Commerce price entry

		commercePriceEntryLocalService.setHasTierPrice(
			commercePriceEntryId, true, bulkPricing);

		return startWorkflowInstance(
			user.getUserId(), commerceTierPriceEntry, serviceContext);
	}

	@Override
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
			long commercePriceEntryId, String externalReferenceCode,
			BigDecimal price, BigDecimal promoPrice, int minQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		Calendar now = new GregorianCalendar();

		return addCommerceTierPriceEntry(
			commercePriceEntryId, externalReferenceCode, price, promoPrice,
			minQuantity, true, true, null, null, null, null,
			now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH),
			now.get(Calendar.YEAR), now.get(Calendar.HOUR),
			now.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, serviceContext);
	}

	@Override
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
			long commercePriceEntryId, String externalReferenceCode,
			BigDecimal price, int minQuantity, boolean bulkPricing,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		return addCommerceTierPriceEntry(
			commercePriceEntryId, externalReferenceCode, price, null,
			minQuantity, bulkPricing, discountDiscovery, discountLevel1,
			discountLevel2, discountLevel3, discountLevel4, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public void checkCommerceTierPriceEntries() throws PortalException {
		checkCommerceTierPriceEntriesByDisplayDate();
		checkCommerceTierPriceEntriesByExpirationDate();
	}

	@Override
	public void deleteCommerceTierPriceEntries(long commercePriceEntryId)
		throws PortalException {

		List<CommerceTierPriceEntry> commerceTierPriceEntries =
			commerceTierPriceEntryLocalService.getCommerceTierPriceEntries(
				commercePriceEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceTierPriceEntry commerceTierPriceEntry :
				commerceTierPriceEntries) {

			commerceTierPriceEntryLocalService.deleteCommerceTierPriceEntry(
				commerceTierPriceEntry);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceTierPriceEntry deleteCommerceTierPriceEntry(
			CommerceTierPriceEntry commerceTierPriceEntry)
		throws PortalException {

		// Commerce tier price entry

		commerceTierPriceEntryPersistence.remove(commerceTierPriceEntry);

		// Commerce price entries

		List<CommerceTierPriceEntry> commerceTierPriceEntries =
			commerceTierPriceEntryLocalService.getCommerceTierPriceEntries(
				commerceTierPriceEntry.getCommercePriceEntryId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (commerceTierPriceEntries.isEmpty()) {
			commercePriceEntryLocalService.setHasTierPrice(
				commerceTierPriceEntry.getCommercePriceEntryId(), false);
		}

		// Expando

		expandoRowLocalService.deleteRows(
			commerceTierPriceEntry.getCommerceTierPriceEntryId());

		return commerceTierPriceEntry;
	}

	@Override
	public CommerceTierPriceEntry deleteCommerceTierPriceEntry(
			long commerceTierPriceEntryId)
		throws PortalException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryPersistence.findByPrimaryKey(
				commerceTierPriceEntryId);

		return commerceTierPriceEntryLocalService.deleteCommerceTierPriceEntry(
			commerceTierPriceEntry);
	}

	@Override
	public CommerceTierPriceEntry fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		return commerceTierPriceEntryPersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	@Override
	public List<CommerceTierPriceEntry> fetchCommerceTierPriceEntries(
		long companyId, int start, int end) {

		return commerceTierPriceEntryPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public CommerceTierPriceEntry findClosestCommerceTierPriceEntry(
		long commercePriceEntryId, int quantity) {

		CommerceTierPriceEntry commerceTierPriceEntry = null;

		try {
			commerceTierPriceEntry =
				commerceTierPriceEntryPersistence.findByC_LtM_S_First(
					commercePriceEntryId, quantity,
					WorkflowConstants.STATUS_APPROVED,
					new CommerceTierPriceEntryMinQuantityComparator(false));
		}
		catch (NoSuchTierPriceEntryException noSuchTierPriceEntryException) {
		}

		return commerceTierPriceEntry;
	}

	@Override
	public List<CommerceTierPriceEntry> findCommerceTierPriceEntries(
		long commercePriceEntryId, int quantity) {

		return commerceTierPriceEntryPersistence.findByC_LtM_S(
			commercePriceEntryId, quantity, WorkflowConstants.STATUS_APPROVED,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new CommerceTierPriceEntryMinQuantityComparator(true));
	}

	@Override
	public List<CommerceTierPriceEntry> getCommerceTierPriceEntries(
		long commercePriceEntryId, int start, int end) {

		return commerceTierPriceEntryPersistence.findByCommercePriceEntryId(
			commercePriceEntryId, start, end);
	}

	@Override
	public List<CommerceTierPriceEntry> getCommerceTierPriceEntries(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTierPriceEntry> orderByComparator) {

		return commerceTierPriceEntryPersistence.findByCommercePriceEntryId(
			commercePriceEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTierPriceEntriesCount(long commercePriceEntryId) {
		return commerceTierPriceEntryPersistence.countByCommercePriceEntryId(
			commercePriceEntryId);
	}

	@Override
	public int getCommerceTierPriceEntriesCountByCompanyId(long companyId) {
		return commerceTierPriceEntryPersistence.countByCompanyId(companyId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CommerceTierPriceEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					CommerceTierPriceEntry.class);

			return indexer.search(searchContext);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Override
	public BaseModelSearchResult<CommerceTierPriceEntry>
			searchCommerceTierPriceEntries(
				long companyId, long commercePriceEntryId, String keywords,
				int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, commercePriceEntryId, keywords, start, end, sort);

		return searchCommerceTierPriceEntries(searchContext);
	}

	@Override
	public int searchCommerceTierPriceEntriesCount(
			long companyId, long commercePriceEntryId, String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, commercePriceEntryId, keywords, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		return searchCommerceTierPriceEntriesCount(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTierPriceEntry updateCommerceTierPriceEntry(
			long commerceTierPriceEntryId, BigDecimal price,
			BigDecimal promoPrice, int minQuantity, boolean bulkPricing,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryPersistence.findByPrimaryKey(
				commerceTierPriceEntryId);

		validate(
			commerceTierPriceEntryId,
			commerceTierPriceEntry.getCommercePriceEntryId(), minQuantity);

		Date expirationDate = null;
		Date now = new Date();

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

		commerceTierPriceEntry.setPrice(price);
		commerceTierPriceEntry.setPromoPrice(promoPrice);
		commerceTierPriceEntry.setMinQuantity(minQuantity);
		commerceTierPriceEntry.setExpandoBridgeAttributes(serviceContext);
		commerceTierPriceEntry.setDiscountDiscovery(discountDiscovery);
		commerceTierPriceEntry.setDiscountLevel1(discountLevel1);
		commerceTierPriceEntry.setDiscountLevel2(discountLevel2);
		commerceTierPriceEntry.setDiscountLevel3(discountLevel3);
		commerceTierPriceEntry.setDiscountLevel4(discountLevel4);

		commerceTierPriceEntry.setDisplayDate(displayDate);

		if ((expirationDate == null) || expirationDate.after(now)) {
			commerceTierPriceEntry.setStatus(WorkflowConstants.STATUS_DRAFT);
		}
		else {
			commerceTierPriceEntry.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		commerceTierPriceEntry.setExpirationDate(expirationDate);
		commerceTierPriceEntry.setStatusByUserId(user.getUserId());
		commerceTierPriceEntry.setStatusDate(
			serviceContext.getModifiedDate(now));

		// Commerce price entry

		commercePriceEntryLocalService.setHasTierPrice(
			commerceTierPriceEntry.getCommercePriceEntryId(), true,
			bulkPricing);

		commerceTierPriceEntry = commerceTierPriceEntryPersistence.update(
			commerceTierPriceEntry);

		commerceTierPriceEntry = startWorkflowInstance(
			user.getUserId(), commerceTierPriceEntry, serviceContext);

		return commerceTierPriceEntry;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTierPriceEntry updateCommerceTierPriceEntry(
			long commerceTierPriceEntryId, BigDecimal price,
			BigDecimal promoPrice, int minQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		Calendar now = new GregorianCalendar();

		return updateCommerceTierPriceEntry(
			commerceTierPriceEntryId, price, promoPrice, minQuantity, true,
			true, null, null, null, null, now.get(Calendar.MONTH),
			now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.YEAR),
			now.get(Calendar.HOUR), now.get(Calendar.MINUTE), 0, 0, 0, 0, 0,
			true, serviceContext);
	}

	@Override
	public CommerceTierPriceEntry updateCommerceTierPriceEntry(
			long commerceTierPriceEntryId, BigDecimal price, int minQuantity,
			boolean bulkPricing, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		return updateCommerceTierPriceEntry(
			commerceTierPriceEntryId, price, null, minQuantity, bulkPricing,
			discountDiscovery, discountLevel1, discountLevel2, discountLevel3,
			discountLevel4, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTierPriceEntry updateExternalReferenceCode(
			CommerceTierPriceEntry commerceTierPriceEntry,
			String externalReferenceCode)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		commerceTierPriceEntry.setExternalReferenceCode(externalReferenceCode);

		return commerceTierPriceEntryPersistence.update(commerceTierPriceEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTierPriceEntry updateStatus(
			long userId, long commerceTierPriceEntryId, int status,
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);
		Date now = new Date();

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryPersistence.findByPrimaryKey(
				commerceTierPriceEntryId);

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(commerceTierPriceEntry.getDisplayDate() != null) &&
			now.before(commerceTierPriceEntry.getDisplayDate())) {

			status = WorkflowConstants.STATUS_SCHEDULED;
		}

		Date modifiedDate = serviceContext.getModifiedDate(now);

		if (status == WorkflowConstants.STATUS_APPROVED) {
			Date expirationDate = commerceTierPriceEntry.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(now)) {
				commerceTierPriceEntry.setExpirationDate(null);
			}
		}

		if (status == WorkflowConstants.STATUS_EXPIRED) {
			commerceTierPriceEntry.setExpirationDate(now);
		}

		commerceTierPriceEntry.setStatus(status);
		commerceTierPriceEntry.setStatusByUserId(user.getUserId());
		commerceTierPriceEntry.setStatusByUserName(user.getFullName());
		commerceTierPriceEntry.setStatusDate(modifiedDate);

		return commerceTierPriceEntryPersistence.update(commerceTierPriceEntry);
	}

	@Override
	public CommerceTierPriceEntry upsertCommerceTierPriceEntry(
			long commerceTierPriceEntryId, long commercePriceEntryId,
			String externalReferenceCode, BigDecimal price,
			BigDecimal promoPrice, int minQuantity, boolean bulkPricing,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			String priceEntryExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		// Update

		if (commerceTierPriceEntryId > 0) {
			try {
				return updateCommerceTierPriceEntry(
					commerceTierPriceEntryId, price, promoPrice, minQuantity,
					bulkPricing, discountDiscovery, discountLevel1,
					discountLevel2, discountLevel3, discountLevel4,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);
			}
			catch (NoSuchTierPriceEntryException
						noSuchTierPriceEntryException) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find tier price entry with ID: " +
							commerceTierPriceEntryId);
				}
			}
		}

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		if (Validator.isNotNull(externalReferenceCode)) {
			CommerceTierPriceEntry commerceTierPriceEntry =
				commerceTierPriceEntryPersistence.fetchByC_ERC(
					serviceContext.getCompanyId(), externalReferenceCode);

			if (commerceTierPriceEntry != null) {
				return updateCommerceTierPriceEntry(
					commerceTierPriceEntry.getCommerceTierPriceEntryId(), price,
					promoPrice, minQuantity, bulkPricing, discountDiscovery,
					discountLevel1, discountLevel2, discountLevel3,
					discountLevel4, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					serviceContext);
			}
		}

		// Add

		if (commercePriceEntryId > 0) {
			validate(0L, commercePriceEntryId, minQuantity);

			CommercePriceEntry commercePriceEntry =
				_commercePriceEntryPersistence.findByPrimaryKey(
					commercePriceEntryId);

			return addCommerceTierPriceEntry(
				commercePriceEntry.getCommercePriceEntryId(),
				externalReferenceCode, price, promoPrice, minQuantity,
				bulkPricing, discountDiscovery, discountLevel1, discountLevel2,
				discountLevel3, discountLevel4, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, serviceContext);
		}

		if (Validator.isNotNull(priceEntryExternalReferenceCode)) {
			CommercePriceEntry commercePriceEntry =
				_commercePriceEntryPersistence.findByC_ERC(
					serviceContext.getCompanyId(),
					priceEntryExternalReferenceCode);

			validate(
				0L, commercePriceEntry.getCommercePriceEntryId(), minQuantity);

			return addCommerceTierPriceEntry(
				commercePriceEntry.getCommercePriceEntryId(),
				externalReferenceCode, price, promoPrice, minQuantity,
				bulkPricing, discountDiscovery, discountLevel1, discountLevel2,
				discountLevel3, discountLevel4, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, serviceContext);
		}

		StringBundler sb = new StringBundler(6);

		sb.append("{commercePriceEntryId=");
		sb.append(commercePriceEntryId);
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append("priceEntryExternalReferenceCode=");
		sb.append(priceEntryExternalReferenceCode);
		sb.append(CharPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPriceEntryException(sb.toString());
	}

	/**
	 * This method is used to insert a new CommerceTierPriceEntry or update an
	 * existing one
	 *
	 * @param  commerceTierPriceEntryId - <b>Only</b> used when updating an
	 *         entity; the matching one will be updated
	 * @param  commercePriceEntryId - <b>Only</b> used when adding a new entity
	 * @param  externalReferenceCode - The external identifier code from a 3rd
	 *         party system to be able to locate the same entity in the portal
	 *         <b>Only</b> used when updating an entity; the first entity with a
	 *         matching reference code one will be updated
	 * @param  price
	 * @param  promoPrice
	 * @param  minQuantity
	 * @param  priceEntryExternalReferenceCode - <b>Only</b> used when adding a
	 *         new entity, similar as <code>commercePriceEntryId</code> but the
	 *         external identifier code from a 3rd party system. If
	 *         commercePriceEntryId is used, it doesn't have any effect,
	 *         otherwise it tries to fetch the CommercePriceEntry against the
	 *         external code reference
	 * @param  serviceContext
	 * @return CommerceTierPriceEntry
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTierPriceEntry upsertCommerceTierPriceEntry(
			long commerceTierPriceEntryId, long commercePriceEntryId,
			String externalReferenceCode, BigDecimal price,
			BigDecimal promoPrice, int minQuantity,
			String priceEntryExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		Calendar now = new GregorianCalendar();

		return upsertCommerceTierPriceEntry(
			commerceTierPriceEntryId, commercePriceEntryId,
			externalReferenceCode, price, promoPrice, minQuantity, true, true,
			null, null, null, null, now.get(Calendar.MONTH),
			now.get(Calendar.DAY_OF_MONTH), now.get(Calendar.YEAR),
			now.get(Calendar.HOUR), now.get(Calendar.MINUTE), 0, 0, 0, 0, 0,
			true, priceEntryExternalReferenceCode, serviceContext);
	}

	@Override
	public CommerceTierPriceEntry upsertCommerceTierPriceEntry(
			long commerceTierPriceEntryId, long commercePriceEntryId,
			String externalReferenceCode, BigDecimal price, int minQuantity,
			boolean bulkPricing, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String priceEntryExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		return upsertCommerceTierPriceEntry(
			commerceTierPriceEntryId, commercePriceEntryId,
			externalReferenceCode, price, null, minQuantity, bulkPricing,
			discountDiscovery, discountLevel1, discountLevel2, discountLevel3,
			discountLevel4, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, priceEntryExternalReferenceCode,
			serviceContext);
	}

	protected SearchContext buildSearchContext(
		long companyId, long commercePriceEntryId, String keywords, int start,
		int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes =
			HashMapBuilder.<String, Serializable>put(
				Field.ENTRY_CLASS_PK, keywords
			).put(
				"commercePriceEntryId", commercePriceEntryId
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords", keywords
				).build()
			).build();

		searchContext.setAttributes(attributes);

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

	protected void checkCommerceTierPriceEntriesByDisplayDate()
		throws PortalException {

		List<CommerceTierPriceEntry> commerceTierPriceEntries =
			commerceTierPriceEntryPersistence.findByLtD_S(
				new Date(), WorkflowConstants.STATUS_SCHEDULED);

		for (CommerceTierPriceEntry commerceTierPriceEntry :
				commerceTierPriceEntries) {

			long userId = PortalUtil.getValidUserId(
				commerceTierPriceEntry.getCompanyId(),
				commerceTierPriceEntry.getUserId());

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCommand(Constants.UPDATE);

			CommercePriceEntry commercePriceEntry =
				commerceTierPriceEntry.getCommercePriceEntry();

			CommercePriceList commercePriceList =
				commercePriceEntry.getCommercePriceList();

			serviceContext.setScopeGroupId(commercePriceList.getGroupId());

			commerceTierPriceEntryLocalService.updateStatus(
				userId, commerceTierPriceEntry.getCommerceTierPriceEntryId(),
				WorkflowConstants.STATUS_APPROVED, serviceContext,
				new HashMap<String, Serializable>());
		}
	}

	protected void checkCommerceTierPriceEntriesByExpirationDate()
		throws PortalException {

		List<CommerceTierPriceEntry> commerceTierPriceEntries =
			commerceTierPriceEntryPersistence.findByLtE_S(
				new Date(), WorkflowConstants.STATUS_APPROVED);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Expiring " + commerceTierPriceEntries.size() +
					" commerce tier price entries");
		}

		if ((commerceTierPriceEntries != null) &&
			!commerceTierPriceEntries.isEmpty()) {

			for (CommerceTierPriceEntry commerceTierPriceEntry :
					commerceTierPriceEntries) {

				long userId = PortalUtil.getValidUserId(
					commerceTierPriceEntry.getCompanyId(),
					commerceTierPriceEntry.getUserId());

				ServiceContext serviceContext = new ServiceContext();

				serviceContext.setCommand(Constants.UPDATE);

				CommercePriceEntry commercePriceEntry =
					commerceTierPriceEntry.getCommercePriceEntry();

				CommercePriceList commercePriceList =
					commercePriceEntry.getCommercePriceList();

				serviceContext.setScopeGroupId(commercePriceList.getGroupId());

				commerceTierPriceEntryLocalService.updateStatus(
					userId,
					commerceTierPriceEntry.getCommerceTierPriceEntryId(),
					WorkflowConstants.STATUS_EXPIRED, serviceContext,
					new HashMap<String, Serializable>());
			}
		}
	}

	protected List<CommerceTierPriceEntry> getCommerceTierPriceEntries(
			Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommerceTierPriceEntry> commerceTierPriceEntries = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long commerceTierPriceEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommerceTierPriceEntry commerceTierPriceEntry =
				fetchCommerceTierPriceEntry(commerceTierPriceEntryId);

			if (commerceTierPriceEntry == null) {
				commerceTierPriceEntries = null;

				Indexer<CommerceTierPriceEntry> indexer =
					IndexerRegistryUtil.getIndexer(
						CommerceTierPriceEntry.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commerceTierPriceEntries != null) {
				commerceTierPriceEntries.add(commerceTierPriceEntry);
			}
		}

		return commerceTierPriceEntries;
	}

	protected BaseModelSearchResult<CommerceTierPriceEntry>
			searchCommerceTierPriceEntries(SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceTierPriceEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CommerceTierPriceEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CommerceTierPriceEntry> commerceTierPriceEntries =
				getCommerceTierPriceEntries(hits);

			if (commerceTierPriceEntries != null) {
				return new BaseModelSearchResult<>(
					commerceTierPriceEntries, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected int searchCommerceTierPriceEntriesCount(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceTierPriceEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CommerceTierPriceEntry.class);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	protected CommerceTierPriceEntry startWorkflowInstance(
			long userId, CommerceTierPriceEntry commerceTierPriceEntry,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, Serializable> workflowContext = new HashMap<>();

		return WorkflowHandlerRegistryUtil.startWorkflowInstance(
			commerceTierPriceEntry.getCompanyId(), 0L, userId,
			CommerceTierPriceEntry.class.getName(),
			commerceTierPriceEntry.getCommerceTierPriceEntryId(),
			commerceTierPriceEntry, serviceContext, workflowContext);
	}

	protected void validate(
			long commerceTierPriceEntryId, long commercePriceEntryId,
			int minQuantity)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryPersistence.findByPrimaryKey(
				commercePriceEntryId);

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryPersistence.fetchByC_M(
				commercePriceEntry.getCommercePriceEntryId(), minQuantity);

		if ((commerceTierPriceEntry != null) &&
			!(commerceTierPriceEntry.getCommerceTierPriceEntryId() ==
				commerceTierPriceEntryId)) {

			throw new DuplicateCommerceTierPriceEntryException();
		}
	}

	protected void validateExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		if (Validator.isNull(externalReferenceCode)) {
			return;
		}

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryPersistence.fetchByC_ERC(
				companyId, externalReferenceCode);

		if (commerceTierPriceEntry != null) {
			throw new DuplicateCommerceTierPriceEntryException(
				"There is another commerce tier price entry with external " +
					"reference code " + externalReferenceCode);
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID
	};

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTierPriceEntryLocalServiceImpl.class);

	@BeanReference(type = CommercePriceEntryPersistence.class)
	private CommercePriceEntryPersistence _commercePriceEntryPersistence;

}