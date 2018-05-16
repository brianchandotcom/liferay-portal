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

package com.liferay.commerce.user.segment.service.impl;

import com.liferay.commerce.user.segment.exception.CommerceUserSegmentEntryKeyException;
import com.liferay.commerce.user.segment.exception.CommerceUserSegmentEntrySystemException;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntryConstants;
import com.liferay.commerce.user.segment.service.base.CommerceUserSegmentEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
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
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceUserSegmentEntryLocalServiceImpl
	extends CommerceUserSegmentEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceUserSegmentEntry addCommerceUserSegmentEntry(
			Map<Locale, String> nameMap, String key, boolean active,
			boolean system, double priority, ServiceContext serviceContext)
		throws PortalException {

		// Commerce user segment entry

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(0, groupId, key);

		long commerceUserSegmentEntryId = counterLocalService.increment();

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			commerceUserSegmentEntryPersistence.create(
				commerceUserSegmentEntryId);

		commerceUserSegmentEntry.setGroupId(groupId);
		commerceUserSegmentEntry.setCompanyId(user.getCompanyId());
		commerceUserSegmentEntry.setUserId(user.getUserId());
		commerceUserSegmentEntry.setUserName(user.getFullName());
		commerceUserSegmentEntry.setNameMap(nameMap);
		commerceUserSegmentEntry.setKey(key);
		commerceUserSegmentEntry.setActive(active);
		commerceUserSegmentEntry.setSystem(system);
		commerceUserSegmentEntry.setPriority(priority);
		commerceUserSegmentEntry.setExpandoBridgeAttributes(serviceContext);

		commerceUserSegmentEntryPersistence.update(commerceUserSegmentEntry);

		// Resources

		resourceLocalService.addModelResources(
			commerceUserSegmentEntry, serviceContext);

		commerceUserSegmentEntryLocalService.cleanUserSegmentsChache(
			commerceUserSegmentEntry.getGroupId());

		return commerceUserSegmentEntry;
	}

	@Override
	public void cleanUserSegmentsChache(long groupId) {
		MultiVMPoolUtil.removePortalCache("USER_SEGMENTS_" + groupId);
	}

	@Override
	public void deleteCommerceUserSegmentEntries(long groupId)
		throws PortalException {

		List<CommerceUserSegmentEntry> commerceUserSegmentEntries =
			commerceUserSegmentEntryPersistence.findByGroupId(groupId);

		for (CommerceUserSegmentEntry commerceUserSegmentEntry :
				commerceUserSegmentEntries) {

			commerceUserSegmentEntryLocalService.deleteCommerceUserSegmentEntry(
				commerceUserSegmentEntry.getCommerceUserSegmentEntryId());
		}

		commerceUserSegmentEntryLocalService.cleanUserSegmentsChache(groupId);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceUserSegmentEntry deleteCommerceUserSegmentEntry(
			CommerceUserSegmentEntry commerceUserSegmentEntry)
		throws PortalException {

		if (commerceUserSegmentEntry.isSystem()) {
			throw new CommerceUserSegmentEntrySystemException();
		}

		// Commerce user segment criteria

		commerceUserSegmentCriterionLocalService.
			deleteCommerceUserSegmentCriteria(
				commerceUserSegmentEntry.getCommerceUserSegmentEntryId());

		// Commerce user segment entry

		commerceUserSegmentEntryPersistence.remove(commerceUserSegmentEntry);

		// Resources

		resourceLocalService.deleteResource(
			commerceUserSegmentEntry, ResourceConstants.SCOPE_INDIVIDUAL);

		// Expando

		expandoRowLocalService.deleteRows(
			commerceUserSegmentEntry.getCommerceUserSegmentEntryId());

		//Cache

		commerceUserSegmentEntryLocalService.cleanUserSegmentsChache(
			commerceUserSegmentEntry.getGroupId());

		return commerceUserSegmentEntry;
	}

	@Override
	public CommerceUserSegmentEntry deleteCommerceUserSegmentEntry(
			long commerceUserSegmentEntryId)
		throws PortalException {

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			commerceUserSegmentEntryPersistence.findByPrimaryKey(
				commerceUserSegmentEntryId);

		return commerceUserSegmentEntryLocalService.
			deleteCommerceUserSegmentEntry(commerceUserSegmentEntry);
	}

	@Override
	public CommerceUserSegmentEntry fetchCommerceUserSegmentEntry(
		long groupId, String key) {

		return commerceUserSegmentEntryPersistence.fetchByG_K(groupId, key);
	}

	@Override
	public List<CommerceUserSegmentEntry> getCommerceUserSegmentEntries(
		long groupId, int start, int end,
		OrderByComparator<CommerceUserSegmentEntry> orderByComparator) {

		return commerceUserSegmentEntryPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceUserSegmentEntriesCount(long groupId) {
		return commerceUserSegmentEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public long[] getCommerceUserSegmentEntryIds(
			long groupId, long organizationId, long userId)
		throws PortalException {

		String cacheKey = userId + "_" + organizationId;

		PortalCache<String, Serializable> portalCache =
			MultiVMPoolUtil.getPortalCache("USER_SEGMENTS_" + groupId);

		boolean userSegmentsCalculated = GetterUtil.getBoolean(
			portalCache.get(cacheKey + "_calculated"));

		long[] commerceUserSegmentEntryIds = (long[])portalCache.get(cacheKey);

		if (userSegmentsCalculated) {
			return commerceUserSegmentEntryIds;
		}

		if (userId == 0) {
			CommerceUserSegmentEntry commerceUserSegmentEntry =
				commerceUserSegmentEntryPersistence.fetchByG_K(
					groupId, CommerceUserSegmentEntryConstants.KEY_GUEST);

			if (commerceUserSegmentEntry == null) {
				return commerceUserSegmentEntryIds;
			}

			commerceUserSegmentEntryIds = new long[] {
				commerceUserSegmentEntry.getCommerceUserSegmentEntryId()
			};

			portalCache.put(cacheKey + "_calculated", true);
			portalCache.put(cacheKey, commerceUserSegmentEntryIds);

			return commerceUserSegmentEntryIds;
		}

		User user = userLocalService.getUser(userId);

		SearchContext searchContext = buildSearchContext(
			user.getCompanyId(), groupId, organizationId, userId);

		Indexer<CommerceUserSegmentEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CommerceUserSegmentEntry.class);

		Hits hits = indexer.search(searchContext, Field.ENTRY_CLASS_PK);

		List<Document> documents = hits.toList();

		Stream<Document> stream = documents.stream();

		commerceUserSegmentEntryIds = stream.mapToLong(
			field -> GetterUtil.getLong(field.get(Field.ENTRY_CLASS_PK))).
				toArray();

		portalCache.put(cacheKey + "_calculated", true);
		portalCache.put(cacheKey, commerceUserSegmentEntryIds);

		return commerceUserSegmentEntryIds;
	}

	@Override
	public void importSystemCommerceUserSegmentEntries(
			ServiceContext serviceContext)
		throws PortalException {

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			commerceUserSegmentEntryPersistence.fetchByG_K(
				serviceContext.getScopeGroupId(),
				CommerceUserSegmentEntryConstants.KEY_GUEST);

		if (commerceUserSegmentEntry != null) {
			return;
		}

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(
			serviceContext.getLocale(),
			CommerceUserSegmentEntryConstants.KEY_GUEST);

		commerceUserSegmentEntryLocalService.addCommerceUserSegmentEntry(
			nameMap, CommerceUserSegmentEntryConstants.KEY_GUEST, true, true, 0,
			serviceContext);
	}

	@Override
	public BaseModelSearchResult<CommerceUserSegmentEntry>
			searchCommerceUserSegmentEntries(
				long companyId, long groupId, String keywords, int start,
				int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, keywords, start, end, sort);

		return commerceUserSegmentEntryLocalService.
			searchCommerceUserSegmentEntries(searchContext);
	}

	@Override
	public BaseModelSearchResult<CommerceUserSegmentEntry>
			searchCommerceUserSegmentEntries(SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceUserSegmentEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CommerceUserSegmentEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CommerceUserSegmentEntry> commerceUserSegmentEntries =
				getCommerceUserSegmentEntries(hits);

			if (commerceUserSegmentEntries != null) {
				return new BaseModelSearchResult<>(
					commerceUserSegmentEntries, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceUserSegmentEntry updateCommerceUserSegmentEntry(
			long commerceUserSegmentEntryId, Map<Locale, String> nameMap,
			String key, boolean active, double priority,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			commerceUserSegmentEntryPersistence.findByPrimaryKey(
				commerceUserSegmentEntryId);

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(
			commerceUserSegmentEntryId, commerceUserSegmentEntry.getGroupId(),
			key);

		commerceUserSegmentEntry.setNameMap(nameMap);

		if (!commerceUserSegmentEntry.isSystem()) {
			commerceUserSegmentEntry.setKey(key);
			commerceUserSegmentEntry.setActive(active);
		}

		commerceUserSegmentEntry.setPriority(priority);
		commerceUserSegmentEntry.setExpandoBridgeAttributes(serviceContext);

		commerceUserSegmentEntryLocalService.cleanUserSegmentsChache(
			commerceUserSegmentEntry.getGroupId());

		return commerceUserSegmentEntryPersistence.update(
			commerceUserSegmentEntry);
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, long organizationId, long userId) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put("active", true);
		attributes.put("criterionType", true);
		attributes.put("organizationId", organizationId);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setStart(0);
		searchContext.setEnd(10);
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setUserId(userId);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Sort sort = SortFactoryUtil.create(
			Field.PRIORITY + "_Number_sortable", true);

		searchContext.setSorts(sort);

		return searchContext;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, String keywords, int start, int end,
		Sort sort) {

		SearchContext searchContext = new SearchContext();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", keywords);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.NAME, keywords);
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

	protected List<CommerceUserSegmentEntry> getCommerceUserSegmentEntries(
			Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommerceUserSegmentEntry> commerceUserSegmentEntries =
			new ArrayList<>(documents.size());

		for (Document document : documents) {
			long commerceUserSegmentEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommerceUserSegmentEntry commerceUserSegmentEntry =
				fetchCommerceUserSegmentEntry(commerceUserSegmentEntryId);

			if (commerceUserSegmentEntry == null) {
				commerceUserSegmentEntries = null;

				Indexer<CommerceUserSegmentEntry> indexer =
					IndexerRegistryUtil.getIndexer(
						CommerceUserSegmentEntry.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commerceUserSegmentEntries != null) {
				commerceUserSegmentEntries.add(commerceUserSegmentEntry);
			}
		}

		return commerceUserSegmentEntries;
	}

	protected void validate(
			long commerceUserSegmentEntryId, long groupId, String key)
		throws PortalException {

		CommerceUserSegmentEntry commerceUserSegmentEntry =
			commerceUserSegmentEntryPersistence.fetchByG_K(groupId, key);

		if ((commerceUserSegmentEntry != null) &&
			(commerceUserSegmentEntry.getCommerceUserSegmentEntryId() !=
				commerceUserSegmentEntryId)) {

			throw new CommerceUserSegmentEntryKeyException();
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID};

}