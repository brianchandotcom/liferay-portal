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
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsConstants;
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
 * @author Eduardo García
 */
public class SegmentsEntryLocalServiceImpl
	extends SegmentsEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SegmentsEntry addSegmentsEntry(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			boolean active, String criteria, String key, String source,
			String type, ServiceContext serviceContext)
		throws PortalException {

		// Segments entry

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(0, groupId, key);

		long segmentsEntryId = counterLocalService.increment();

		SegmentsEntry segmentsEntry = segmentsEntryPersistence.create(
			segmentsEntryId);

		segmentsEntry.setGroupId(groupId);
		segmentsEntry.setCompanyId(user.getCompanyId());
		segmentsEntry.setUserId(user.getUserId());
		segmentsEntry.setUserName(user.getFullName());
		segmentsEntry.setCreateDate(serviceContext.getCreateDate(new Date()));
		segmentsEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		segmentsEntry.setNameMap(nameMap);
		segmentsEntry.setDescriptionMap(descriptionMap);
		segmentsEntry.setActive(active);
		segmentsEntry.setCriteria(criteria);
		segmentsEntry.setKey(key);

		if (Validator.isNull(source)) {
			segmentsEntry.setSource(SegmentsConstants.SOURCE_DEFAULT);
		}
		else {
			segmentsEntry.setSource(source);
		}

		segmentsEntry.setType(type);

		segmentsEntryPersistence.update(segmentsEntry);

		// Resources

		resourceLocalService.addModelResources(segmentsEntry, serviceContext);

		return segmentsEntry;
	}

	@Override
	public void deleteSegmentsEntries(long groupId) throws PortalException {
		List<SegmentsEntry> segmentsEntries =
			segmentsEntryPersistence.findByGroupId(groupId);

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			segmentsEntryLocalService.deleteSegmentsEntry(
				segmentsEntry.getSegmentsEntryId());
		}
	}

	@Override
	public void deleteSegmentsEntries(String source) throws PortalException {
		List<SegmentsEntry> segmentsEntries =
			segmentsEntryPersistence.findBySource(source);

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			segmentsEntryLocalService.deleteSegmentsEntry(segmentsEntry);
		}
	}

	@Override
	public SegmentsEntry deleteSegmentsEntry(long segmentsEntryId)
		throws PortalException {

		SegmentsEntry segmentsEntry = segmentsEntryPersistence.findByPrimaryKey(
			segmentsEntryId);

		return segmentsEntryLocalService.deleteSegmentsEntry(segmentsEntry);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SegmentsEntry deleteSegmentsEntry(SegmentsEntry segmentsEntry)
		throws PortalException {

		// Segments entry

		segmentsEntryPersistence.remove(segmentsEntry);

		// Resources

		resourceLocalService.deleteResource(
			segmentsEntry, ResourceConstants.SCOPE_INDIVIDUAL);

		// Segment rels

		segmentsEntryRelLocalService.deleteSegmentsEntryRels(
			segmentsEntry.getSegmentsEntryId());

		return segmentsEntry;
	}

	@Override
	public SegmentsEntry fetchSegmentsEntry(
		long groupId, String key, boolean includeAncestorSegmentsEntries) {

		SegmentsEntry segmentsEntry = segmentsEntryPersistence.fetchByG_K(
			groupId, key);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		for (long ancestorSiteGroupId :
				PortalUtil.getAncestorSiteGroupIds(groupId)) {

			segmentsEntry = segmentsEntryPersistence.fetchByG_K(
				ancestorSiteGroupId, key);

			if (segmentsEntry != null) {
				return segmentsEntry;
			}
		}

		return null;
	}

	@Override
	public List<SegmentsEntry> getSegmentsEntries(
		boolean active, String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return segmentsEntryPersistence.findByA_T(
			active, type, start, end, orderByComparator);
	}

	@Override
	public List<SegmentsEntry> getSegmentsEntries(
		long groupId, boolean includeAncestorSegmentsEntries, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator) {

		List<SegmentsEntry> segmentsEntries = new ArrayList<>(
			segmentsEntryPersistence.findByGroupId(
				groupId, start, end, orderByComparator));

		if (!includeAncestorSegmentsEntries) {
			return segmentsEntries;
		}

		segmentsEntries.addAll(
			segmentsEntryPersistence.findByGroupId(
				PortalUtil.getAncestorSiteGroupIds(groupId), start, end,
				orderByComparator));

		return segmentsEntries;
	}

	@Override
	public List<SegmentsEntry> getSegmentsEntriesBySource(
		String source, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return segmentsEntryPersistence.findBySource(
			source, start, end, orderByComparator);
	}

	@Override
	public int getSegmentsEntriesCount(
		long groupId, boolean includeAncestorSegmentsEntries) {

		int count = segmentsEntryPersistence.countByGroupId(groupId);

		if (!includeAncestorSegmentsEntries) {
			return count;
		}

		return count +
			segmentsEntryPersistence.countByGroupId(
				PortalUtil.getAncestorSiteGroupIds(groupId));
	}

	@Override
	public BaseModelSearchResult<SegmentsEntry> searchSegmentsEntries(
			long companyId, long groupId, String keywords, int start, int end,
			Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, keywords, start, end, sort);

		return segmentsEntryLocalService.searchSegmentsEntries(searchContext);
	}

	@Override
	public BaseModelSearchResult<SegmentsEntry> searchSegmentsEntries(
			SearchContext searchContext)
		throws PortalException {

		Indexer<SegmentsEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			SegmentsEntry.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<SegmentsEntry> segmentsEntries = getSegmentsEntries(hits);

			if (segmentsEntries != null) {
				return new BaseModelSearchResult<>(
					segmentsEntries, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SegmentsEntry updateSegmentsEntry(
			long segmentsEntryId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active, String criteria,
			String key, ServiceContext serviceContext)
		throws PortalException {

		SegmentsEntry segmentsEntry = segmentsEntryPersistence.findByPrimaryKey(
			segmentsEntryId);

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(segmentsEntryId, segmentsEntry.getGroupId(), key);

		segmentsEntry.setNameMap(nameMap);
		segmentsEntry.setDescriptionMap(descriptionMap);
		segmentsEntry.setActive(active);
		segmentsEntry.setCriteria(criteria);
		segmentsEntry.setKey(key);

		return segmentsEntryPersistence.update(segmentsEntry);
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, String keywords, int start, int end,
		Sort sort) {

		SearchContext searchContext = new SearchContext();

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.NAME, keywords);

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", keywords);

		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		return searchContext;
	}

	protected List<SegmentsEntry> getSegmentsEntries(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<SegmentsEntry> segmentsEntries = new ArrayList<>(documents.size());

		for (Document document : documents) {
			long segmentsEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			SegmentsEntry segmentsEntry = fetchSegmentsEntry(segmentsEntryId);

			if (segmentsEntry == null) {
				segmentsEntries = null;

				Indexer<SegmentsEntry> indexer = IndexerRegistryUtil.getIndexer(
					SegmentsEntry.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (segmentsEntries != null) {
				segmentsEntries.add(segmentsEntry);
			}
		}

		return segmentsEntries;
	}

	protected void validate(long segmentsEntryId, long groupId, String key)
		throws PortalException {

		SegmentsEntry segmentsEntry = fetchSegmentsEntry(groupId, key, true);

		if ((segmentsEntry != null) &&
			(segmentsEntry.getSegmentsEntryId() != segmentsEntryId)) {

			throw new SegmentsEntryKeyException();
		}
	}

}