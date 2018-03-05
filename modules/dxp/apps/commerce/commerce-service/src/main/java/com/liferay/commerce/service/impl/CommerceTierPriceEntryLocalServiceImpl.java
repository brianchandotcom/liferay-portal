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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.exception.NoSuchTierPriceEntryException;
import com.liferay.commerce.model.CommerceTierPriceEntry;
import com.liferay.commerce.service.base.CommerceTierPriceEntryLocalServiceBaseImpl;
import com.liferay.commerce.util.comparator.CommerceTierPriceEntryMinQuantityComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
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
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTierPriceEntryLocalServiceImpl
	extends CommerceTierPriceEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
			long commercePriceEntryId, double price, int minQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce tier price entry

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

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
		commerceTierPriceEntry.setMinQuantity(minQuantity);
		commerceTierPriceEntry.setExpandoBridgeAttributes(serviceContext);

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
	public CommerceTierPriceEntry findClosestCommerceTierPriceEntry(
		long commercePriceEntryId, int quantity) {

		CommerceTierPriceEntry commerceTierPriceEntry = null;

		try {
			commerceTierPriceEntry =
				commerceTierPriceEntryPersistence.findByC_M2_First(
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
			long commerceTierPriceEntryId, double price, int minQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryPersistence.findByPrimaryKey(
				commerceTierPriceEntryId);

		commerceTierPriceEntry.setPrice(price);
		commerceTierPriceEntry.setMinQuantity(minQuantity);
		commerceTierPriceEntry.setExpandoBridgeAttributes(serviceContext);

		return commerceTierPriceEntryPersistence.update(commerceTierPriceEntry);
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

	private static final String[] _SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID};

}