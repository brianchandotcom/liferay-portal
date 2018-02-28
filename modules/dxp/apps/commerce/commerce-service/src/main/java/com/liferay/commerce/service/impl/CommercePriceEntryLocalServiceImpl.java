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

import com.liferay.commerce.model.CommercePriceEntry;
import com.liferay.commerce.service.base.CommercePriceEntryLocalServiceBaseImpl;
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
public class CommercePriceEntryLocalServiceImpl
	extends CommercePriceEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry addCommercePriceEntry(
			long cpInstanceId, long commercePriceListId, double price,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commercePriceEntryId = counterLocalService.increment();

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryPersistence.create(commercePriceEntryId);

		commercePriceEntry.setUuid(serviceContext.getUuid());
		commercePriceEntry.setGroupId(groupId);
		commercePriceEntry.setCompanyId(user.getCompanyId());
		commercePriceEntry.setUserId(user.getUserId());
		commercePriceEntry.setUserName(user.getFullName());
		commercePriceEntry.setCPInstanceId(cpInstanceId);
		commercePriceEntry.setCommercePriceListId(commercePriceListId);
		commercePriceEntry.setPrice(price);
		commercePriceEntry.setExpandoBridgeAttributes(serviceContext);

		return commercePriceEntryPersistence.update(commercePriceEntry);
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
	public void deleteCommercePriceEntriesByCPInstanceId(long cpInstanceId)
		throws PortalException {

		List<CommercePriceEntry> commercePriceEntries =
			commercePriceEntryPersistence.findByCPInstanceId(cpInstanceId);

		for (CommercePriceEntry commercePriceEntry : commercePriceEntries) {
			deleteCommercePriceEntry(commercePriceEntry);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommercePriceEntry deleteCommercePriceEntry(
			CommercePriceEntry commercePriceEntry)
		throws PortalException {

		// Commerce price entry

		commercePriceEntryPersistence.remove(commercePriceEntry);

		// Commerce tier price entries

		commerceTierPriceEntryLocalService.deleteCommerceTierPriceEntries(
			commercePriceEntry.getCommercePriceEntryId());

		// Expando

		expandoRowLocalService.deleteRows(
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
	public int getCommercePriceEntriesCount(long commercePriceListId) {
		return commercePriceEntryPersistence.countByCommercePriceListId(
			commercePriceListId);
	}

	@Override
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end) {

		return commercePriceEntryPersistence.findByCPInstanceId(
			cpInstanceId, start, end);
	}

	@Override
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
		long cpInstanceId, int start, int end,
		OrderByComparator<CommercePriceEntry> orderByComparator) {

		return commercePriceEntryPersistence.findByCPInstanceId(
			cpInstanceId, start, end, orderByComparator);
	}

	@Override
	public int getInstanceCommercePriceEntriesCount(long cpInstanceId) {
		return commercePriceEntryPersistence.countByCPInstanceId(cpInstanceId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CommercePriceEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					CommercePriceEntry.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public BaseModelSearchResult<CommercePriceEntry> searchCommercePriceEntries(
			long companyId, long groupId, long commercePriceListId,
			String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, commercePriceListId, keywords, start, end,
			sort);

		return searchCommercePriceEntries(searchContext);
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

		return commercePriceEntryPersistence.update(commercePriceEntry);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, double price,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryPersistence.findByPrimaryKey(
				commercePriceEntryId);

		commercePriceEntry.setPrice(price);
		commercePriceEntry.setExpandoBridgeAttributes(serviceContext);

		return commercePriceEntryPersistence.update(commercePriceEntry);
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, long commercePriceListId, String keywords,
		int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", keywords);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.ENTRY_CLASS_PK, keywords);
		attributes.put("commercePriceListId", commercePriceListId);
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

	private static final String[] _SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID};

}