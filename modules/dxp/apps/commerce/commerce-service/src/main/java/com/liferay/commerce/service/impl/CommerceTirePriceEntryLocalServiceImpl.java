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

import com.liferay.commerce.model.CommerceTirePriceEntry;
import com.liferay.commerce.service.base.CommerceTirePriceEntryLocalServiceBaseImpl;
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
public class CommerceTirePriceEntryLocalServiceImpl
	extends CommerceTirePriceEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTirePriceEntry addCommerceTirePriceEntry(
			long commercePriceEntryId, double price, int minQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce tire price entry

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceTirePriceEntryId = counterLocalService.increment();

		CommerceTirePriceEntry commerceTirePriceEntry =
			commerceTirePriceEntryPersistence.create(commerceTirePriceEntryId);

		commerceTirePriceEntry.setUuid(serviceContext.getUuid());
		commerceTirePriceEntry.setGroupId(groupId);
		commerceTirePriceEntry.setCompanyId(user.getCompanyId());
		commerceTirePriceEntry.setUserId(user.getUserId());
		commerceTirePriceEntry.setUserName(user.getFullName());
		commerceTirePriceEntry.setCommercePriceEntryId(commercePriceEntryId);
		commerceTirePriceEntry.setPrice(price);
		commerceTirePriceEntry.setMinQuantity(minQuantity);
		commerceTirePriceEntry.setExpandoBridgeAttributes(serviceContext);

		commerceTirePriceEntryPersistence.update(commerceTirePriceEntry);

		// Commerce price entry

		commercePriceEntryLocalService.setHasTirePrice(
			commercePriceEntryId, true);

		return commerceTirePriceEntry;
	}

	@Override
	public void deleteCommerceTirePriceEntries(long commercePriceEntryId)
		throws PortalException {

		List<CommerceTirePriceEntry> commerceTirePriceEntries =
			commerceTirePriceEntryLocalService.getCommerceTirePriceEntries(
				commercePriceEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceTirePriceEntry commerceTirePriceEntry :
				commerceTirePriceEntries) {

			commerceTirePriceEntryLocalService.deleteCommerceTirePriceEntry(
				commerceTirePriceEntry);
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceTirePriceEntry deleteCommerceTirePriceEntry(
			CommerceTirePriceEntry commerceTirePriceEntry)
		throws PortalException {

		// Commerce tire price entry

		commerceTirePriceEntryPersistence.remove(commerceTirePriceEntry);

		// Commerce price entry

		List<CommerceTirePriceEntry> commerceTirePriceEntries =
			commerceTirePriceEntryLocalService.getCommerceTirePriceEntries(
				commerceTirePriceEntry.getCommercePriceEntryId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		if (commerceTirePriceEntries.isEmpty()) {
			commercePriceEntryLocalService.setHasTirePrice(
				commerceTirePriceEntry.getCommercePriceEntryId(), false);
		}

		// Expando

		expandoRowLocalService.deleteRows(
			commerceTirePriceEntry.getCommerceTirePriceEntryId());

		return commerceTirePriceEntry;
	}

	@Override
	public CommerceTirePriceEntry deleteCommerceTirePriceEntry(
			long commerceTirePriceEntryId)
		throws PortalException {

		CommerceTirePriceEntry commerceTirePriceEntry =
			commerceTirePriceEntryPersistence.findByPrimaryKey(
				commerceTirePriceEntryId);

		return commerceTirePriceEntryLocalService.deleteCommerceTirePriceEntry(
			commerceTirePriceEntry);
	}

	@Override
	public List<CommerceTirePriceEntry> getCommerceTirePriceEntries(
		long commercePriceEntryId, int start, int end) {

		return commerceTirePriceEntryPersistence.findByCommercePriceEntryId(
			commercePriceEntryId, start, end);
	}

	@Override
	public List<CommerceTirePriceEntry> getCommerceTirePriceEntries(
		long commercePriceEntryId, int start, int end,
		OrderByComparator<CommerceTirePriceEntry> orderByComparator) {

		return commerceTirePriceEntryPersistence.findByCommercePriceEntryId(
			commercePriceEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTirePriceEntriesCount(long commercePriceEntryId) {
		return commerceTirePriceEntryPersistence.countByCommercePriceEntryId(
			commercePriceEntryId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CommerceTirePriceEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					CommerceTirePriceEntry.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public BaseModelSearchResult<CommerceTirePriceEntry>
			searchCommerceTirePriceEntries(
				long companyId, long groupId, long commercePriceEntryId,
				String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, commercePriceEntryId, keywords, start, end,
			sort);

		return searchCommerceTirePriceEntries(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceTirePriceEntry updateCommerceTirePriceEntry(
			long commerceTirePriceEntryId, double price, int minQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceTirePriceEntry commerceTirePriceEntry =
			commerceTirePriceEntryPersistence.findByPrimaryKey(
				commerceTirePriceEntryId);

		commerceTirePriceEntry.setPrice(price);
		commerceTirePriceEntry.setMinQuantity(minQuantity);
		commerceTirePriceEntry.setExpandoBridgeAttributes(serviceContext);

		return commerceTirePriceEntryPersistence.update(commerceTirePriceEntry);
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

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	protected BaseModelSearchResult<CommerceTirePriceEntry>
			searchCommerceTirePriceEntries(SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceTirePriceEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CommerceTirePriceEntry.class);

		List<CommerceTirePriceEntry> commerceTirePriceEntries =
			new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			Document[] documents = hits.getDocs();

			for (Document document : documents) {
				long classPK = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				CommerceTirePriceEntry commerceTirePriceEntry =
					getCommerceTirePriceEntry(classPK);

				commerceTirePriceEntries.add(commerceTirePriceEntry);
			}

			if (commerceTirePriceEntries != null) {
				return new BaseModelSearchResult<>(
					commerceTirePriceEntries, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	private static final String[] _SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID};

}