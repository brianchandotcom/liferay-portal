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

import com.liferay.commerce.price.list.exception.DuplicateCommerceTierPriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchPriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchTierPriceEntryException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.base.CommerceTierPriceEntryLocalServiceBaseImpl;
import com.liferay.commerce.price.list.service.persistence.CommercePriceEntryPersistence;
import com.liferay.commerce.price.list.util.comparator.CommerceTierPriceEntryMinQuantityComparator;
import com.liferay.petra.string.CharPool;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
			BigDecimal price, BigDecimal promoPrice, int minQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce tier price entry

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		validate(0, commercePriceEntryId, minQuantity);

		long commerceTierPriceEntryId = counterLocalService.increment();

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryPersistence.create(commerceTierPriceEntryId);

		commerceTierPriceEntry.setUuid(serviceContext.getUuid());
		commerceTierPriceEntry.setGroupId(groupId);
		commerceTierPriceEntry.setCompanyId(user.getCompanyId());
		commerceTierPriceEntry.setUserId(user.getUserId());
		commerceTierPriceEntry.setUserName(user.getFullName());
		commerceTierPriceEntry.setCommercePriceEntryId(commercePriceEntryId);
		commerceTierPriceEntry.setPrice(price);
		commerceTierPriceEntry.setPromoPrice(promoPrice);
		commerceTierPriceEntry.setMinQuantity(minQuantity);
		commerceTierPriceEntry.setExpandoBridgeAttributes(serviceContext);
		commerceTierPriceEntry.setExternalReferenceCode(externalReferenceCode);

		commerceTierPriceEntryPersistence.update(commerceTierPriceEntry);

		// Commerce price entry

		commercePriceEntryLocalService.setHasTierPrice(
			commercePriceEntryId, true);

		return commerceTierPriceEntry;
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

		// Commerce price entry

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
		String externalReferenceCode) {

		return commerceTierPriceEntryPersistence.fetchByExternalReferenceCode(
			externalReferenceCode);
	}

	@Override
	public List<CommerceTierPriceEntry> fetchCommerceTierPriceEntries(
		long groupId, int start, int end) {

		return commerceTierPriceEntryPersistence.findByGroupId(
			groupId, start, end);
	}

	@Override
	public CommerceTierPriceEntry findClosestCommerceTierPriceEntry(
		long commercePriceEntryId, int quantity) {

		CommerceTierPriceEntry commerceTierPriceEntry = null;

		try {
			commerceTierPriceEntry =
				commerceTierPriceEntryPersistence.findByC_LtM_First(
					commercePriceEntryId, quantity,
					new CommerceTierPriceEntryMinQuantityComparator(false));
		}
		catch (NoSuchTierPriceEntryException nstpee) {
		}

		return commerceTierPriceEntry;
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
	public int getCommerceTierPriceEntriesCountByGroupId(long groupId) {
		return commerceTierPriceEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CommerceTierPriceEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					CommerceTierPriceEntry.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public BaseModelSearchResult<CommerceTierPriceEntry>
			searchCommerceTierPriceEntries(
				long companyId, long groupId, long commercePriceEntryId,
				String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, commercePriceEntryId, keywords, start, end,
			sort);

		return searchCommerceTierPriceEntries(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTierPriceEntry updateCommerceTierPriceEntry(
			long commerceTierPriceEntryId, BigDecimal price,
			BigDecimal promoPrice, int minQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryPersistence.findByPrimaryKey(
				commerceTierPriceEntryId);

		validate(
			commerceTierPriceEntryId,
			commerceTierPriceEntry.getCommercePriceEntryId(), minQuantity);

		commerceTierPriceEntry.setPrice(price);
		commerceTierPriceEntry.setPromoPrice(promoPrice);
		commerceTierPriceEntry.setMinQuantity(minQuantity);
		commerceTierPriceEntry.setExpandoBridgeAttributes(serviceContext);

		return commerceTierPriceEntryPersistence.update(commerceTierPriceEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTierPriceEntry updateExternalReferenceCode(
			CommerceTierPriceEntry commerceTierPriceEntry,
			String externalReferenceCode)
		throws PortalException {

		commerceTierPriceEntry.setExternalReferenceCode(externalReferenceCode);

		return commerceTierPriceEntryPersistence.update(commerceTierPriceEntry);
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

		// Update

		if (commerceTierPriceEntryId > 0) {
			try {
				return updateCommerceTierPriceEntry(
					commerceTierPriceEntryId, price, promoPrice, minQuantity,
					serviceContext);
			}
			catch (NoSuchTierPriceEntryException nstpee) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find tier price entry with ID: " +
							commerceTierPriceEntryId);
				}
			}
		}

		if (Validator.isNotNull(externalReferenceCode)) {
			CommerceTierPriceEntry commerceTierPriceEntry =
				commerceTierPriceEntryPersistence.fetchByExternalReferenceCode(
					externalReferenceCode);

			if (commerceTierPriceEntry != null) {
				return updateCommerceTierPriceEntry(
					commerceTierPriceEntry.getCommerceTierPriceEntryId(), price,
					promoPrice, minQuantity, serviceContext);
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
				serviceContext);
		}

		if (Validator.isNotNull(priceEntryExternalReferenceCode)) {
			CommercePriceEntry commercePriceEntry =
				_commercePriceEntryPersistence.findByExternalReferenceCode(
					priceEntryExternalReferenceCode);

			validate(
				0L, commercePriceEntry.getCommercePriceEntryId(), minQuantity);

			return addCommerceTierPriceEntry(
				commercePriceEntry.getCommercePriceEntryId(),
				externalReferenceCode, price, promoPrice, minQuantity,
				serviceContext);
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

	protected SearchContext buildSearchContext(
		long companyId, long groupId, long commercePriceEntryId,
		String keywords, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", keywords);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.ENTRY_CLASS_PK, keywords);
		attributes.put("commercePriceEntryId", commercePriceEntryId);
		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setStart(start);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

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

	private static final String[] _SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID};

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTierPriceEntryLocalServiceImpl.class);

	@BeanReference(type = CommercePriceEntryPersistence.class)
	private CommercePriceEntryPersistence _commercePriceEntryPersistence;

}