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

package com.liferay.segments.service.impl;

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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.exception.SegmentsEntryKeyException;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.base.SegmentsEntryLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Eduardo Garcia
 */
public class SegmentsEntryLocalServiceImpl
	extends SegmentsEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SegmentsEntry addEntry(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String key, String type, boolean active, String criteria,
			ServiceContext serviceContext)
		throws PortalException {

		// Entry

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(0, groupId, key);

		long entryId = counterLocalService.increment();

		SegmentsEntry entry = segmentsEntryPersistence.create(entryId);

		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(serviceContext.getCreateDate(new Date()));
		entry.setModifiedDate(serviceContext.getModifiedDate(new Date()));
		entry.setNameMap(nameMap);
		entry.setDescriptionMap(descriptionMap);
		entry.setKey(key);
		entry.setType(type);
		entry.setActive(active);
		entry.setCriteria(criteria);

		segmentsEntryPersistence.update(entry);

		// Resources

		resourceLocalService.addModelResources(entry, serviceContext);

		return entry;
	}

	@Override
	public void deleteEntries(long groupId) throws PortalException {
		List<SegmentsEntry> segmentsEntries =
			segmentsEntryPersistence.findByGroupId(groupId);

		for (SegmentsEntry entry : segmentsEntries) {
			segmentsEntryLocalService.deleteSegmentsEntry(
				entry.getSegmentsEntryId());
		}
	}

	@Override
	public SegmentsEntry deleteEntry(long entryId) throws PortalException {
		SegmentsEntry entry = segmentsEntryPersistence.findByPrimaryKey(
			entryId);

		return segmentsEntryLocalService.deleteSegmentsEntry(entry);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SegmentsEntry deleteEntry(SegmentsEntry entry)
		throws PortalException {

		// Entry

		segmentsEntryPersistence.remove(entry);

		// Resources

		resourceLocalService.deleteResource(
			entry, ResourceConstants.SCOPE_INDIVIDUAL);

		return entry;
	}

	@Override
	public SegmentsEntry fetchEntry(long groupId, String key) {
		return segmentsEntryPersistence.fetchByG_K(groupId, key);
	}

	@Override
	public List<SegmentsEntry> getEntries(
		long groupId, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return segmentsEntryPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getEntriesCount(long groupId) {
		return segmentsEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public SegmentsEntry getEntry(long groupId, String key)
		throws PortalException {

		return segmentsEntryPersistence.findByG_K(groupId, key);
	}

	@Override
	public BaseModelSearchResult<SegmentsEntry> searchEntries(
			long companyId, long groupId, String keywords, int start, int end,
			Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, keywords, start, end, sort);

		return segmentsEntryLocalService.searchEntries(searchContext);
	}

	@Override
	public BaseModelSearchResult<SegmentsEntry> searchEntries(
			SearchContext searchContext)
		throws PortalException {

		Indexer<SegmentsEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			SegmentsEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<SegmentsEntry> entries = getEntries(hits);

			if (entries != null) {
				return new BaseModelSearchResult<>(entries, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SegmentsEntry updateEntry(
			long entryId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String key, boolean active,
			String criteria, ServiceContext serviceContext)
		throws PortalException {

		SegmentsEntry entry = segmentsEntryPersistence.findByPrimaryKey(
			entryId);

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(entryId, entry.getGroupId(), key);

		entry.setNameMap(nameMap);
		entry.setDescriptionMap(descriptionMap);
		entry.setKey(key);
		entry.setActive(active);
		entry.setCriteria(criteria);

		return segmentsEntryPersistence.update(entry);
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

	protected List<SegmentsEntry> getEntries(Hits hits) throws PortalException {
		List<Document> documents = hits.toList();

		List<SegmentsEntry> entries = new ArrayList<>(documents.size());

		for (Document document : documents) {
			long entryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			SegmentsEntry entry = fetchSegmentsEntry(entryId);

			if (entry == null) {
				entries = null;

				Indexer<SegmentsEntry> indexer = IndexerRegistryUtil.getIndexer(
					SegmentsEntry.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (entries != null) {
				entries.add(entry);
			}
		}

		return entries;
	}

	protected void validate(long entryId, long groupId, String key)
		throws PortalException {

		SegmentsEntry entry = segmentsEntryPersistence.fetchByG_K(groupId, key);

		if ((entry != null) && (entry.getSegmentsEntryId() != entryId)) {
			throw new SegmentsEntryKeyException();
		}
	}

}