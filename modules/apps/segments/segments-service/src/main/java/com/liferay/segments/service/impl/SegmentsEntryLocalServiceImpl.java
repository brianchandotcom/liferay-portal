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

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
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
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.GroupThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.exception.RequiredSegmentsEntryException;
import com.liferay.segments.exception.SegmentsEntryKeyException;
import com.liferay.segments.exception.SegmentsEntryNameException;
import com.liferay.segments.internal.constants.SegmentsDestinationNames;
import com.liferay.segments.internal.criteria.contributor.SegmentsEntrySegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryRelLocalService;
import com.liferay.segments.service.SegmentsEntryRoleLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.base.SegmentsEntryLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "model.class.name=com.liferay.segments.model.SegmentsEntry",
	service = AopService.class
)
public class SegmentsEntryLocalServiceImpl
	extends SegmentsEntryLocalServiceBaseImpl {

	@Override
	public SegmentsEntry addSegmentsEntry(
			String segmentsEntryKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active, String criteria,
			String type, ServiceContext serviceContext)
		throws PortalException {

		return segmentsEntryLocalService.addSegmentsEntry(
			segmentsEntryKey, nameMap, descriptionMap, active, criteria, null,
			type, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SegmentsEntry addSegmentsEntry(
			String segmentsEntryKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active, String criteria,
			String source, String type, ServiceContext serviceContext)
		throws PortalException {

		// Segments entry

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		if (Validator.isNull(segmentsEntryKey)) {
			segmentsEntryKey = String.valueOf(counterLocalService.increment());
		}
		else {
			segmentsEntryKey = StringUtil.toUpperCase(segmentsEntryKey.trim());
		}

		validateKey(0, groupId, segmentsEntryKey);
		validateName(groupId, nameMap);

		long segmentsEntryId = counterLocalService.increment();

		SegmentsEntry segmentsEntry = segmentsEntryPersistence.create(
			segmentsEntryId);

		segmentsEntry.setUuid(serviceContext.getUuid());
		segmentsEntry.setGroupId(groupId);
		segmentsEntry.setCompanyId(user.getCompanyId());
		segmentsEntry.setUserId(user.getUserId());
		segmentsEntry.setUserName(user.getFullName());
		segmentsEntry.setCreateDate(serviceContext.getCreateDate(new Date()));
		segmentsEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		segmentsEntry.setSegmentsEntryKey(segmentsEntryKey);
		segmentsEntry.setNameMap(nameMap);
		segmentsEntry.setDescriptionMap(descriptionMap);
		segmentsEntry.setActive(active);
		segmentsEntry.setCriteria(criteria);
		segmentsEntry.setSource(getSource(criteria, source));
		segmentsEntry.setType(type);

		segmentsEntry = segmentsEntryPersistence.update(segmentsEntry);

		// Resources

		resourceLocalService.addModelResources(segmentsEntry, serviceContext);

		// Indexer

		reindexSegmentsEntryRels(segmentsEntry);

		return segmentsEntry;
	}

	@Override
	public void addSegmentsEntryClassPKs(
			long segmentsEntryId, long[] classPKs,
			ServiceContext serviceContext)
		throws PortalException {

		SegmentsEntry segmentsEntry = getSegmentsEntry(segmentsEntryId);

		_segmentsEntryRelLocalService.addSegmentsEntryRels(
			segmentsEntryId, _portal.getClassNameId(segmentsEntry.getType()),
			classPKs, serviceContext);

		segmentsEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));

		segmentsEntry = segmentsEntryPersistence.update(segmentsEntry);

		reindexSegmentsEntryRels(segmentsEntry);
	}

	@Override
	public void deleteSegmentsEntries(long groupId) throws PortalException {
		List<SegmentsEntry> segmentsEntries =
			segmentsEntryPersistence.findByGroupId(groupId);

		for (SegmentsEntry segmentsEntry : segmentsEntries) {
			segmentsEntryLocalService.deleteSegmentsEntry(segmentsEntry);
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

		if (!GroupThreadLocal.isDeleteInProcess()) {
			int count = segmentsExperiencePersistence.countBySegmentsEntryId(
				segmentsEntry.getSegmentsEntryId());

			if (count > 0) {
				throw new RequiredSegmentsEntryException.
					MustNotDeleteSegmentsEntryReferencedBySegmentsExperiences(
						segmentsEntry.getSegmentsEntryId());
			}
		}

		segmentsEntryPersistence.remove(segmentsEntry);

		// Resources

		resourceLocalService.deleteResource(
			segmentsEntry, ResourceConstants.SCOPE_INDIVIDUAL);

		// Segments experiences

		_segmentsExperienceLocalService.deleteSegmentsEntrySegmentsExperiences(
			segmentsEntry.getSegmentsEntryId());

		// Segments rels

		_segmentsEntryRelLocalService.deleteSegmentsEntryRels(
			segmentsEntry.getSegmentsEntryId());

		// Segments roles

		_segmentsEntryRoleLocalService.deleteSegmentsEntryRoles(
			segmentsEntry.getSegmentsEntryId());

		// Indexer

		reindexSegmentsEntryRels(segmentsEntry);

		return segmentsEntry;
	}

	@Override
	public void deleteSegmentsEntryClassPKs(
			long segmentsEntryId, long[] classPKs)
		throws PortalException {

		SegmentsEntry segmentsEntry = getSegmentsEntry(segmentsEntryId);

		_segmentsEntryRelLocalService.deleteSegmentsEntryRels(
			segmentsEntryId, _portal.getClassNameId(segmentsEntry.getType()),
			classPKs);

		segmentsEntry.setModifiedDate(new Date());

		segmentsEntry = segmentsEntryPersistence.update(segmentsEntry);

		reindexSegmentsEntryRels(segmentsEntry);
	}

	@Override
	public SegmentsEntry fetchSegmentsEntry(
		long groupId, String segmentsEntryKey,
		boolean includeAncestorSegmentsEntries) {

		SegmentsEntry segmentsEntry = segmentsEntryPersistence.fetchByG_S(
			groupId, segmentsEntryKey);

		if (segmentsEntry != null) {
			return segmentsEntry;
		}

		for (long ancestorSiteGroupId :
				_portal.getAncestorSiteGroupIds(groupId)) {

			segmentsEntry = segmentsEntryPersistence.fetchByG_S(
				ancestorSiteGroupId, segmentsEntryKey);

			if (segmentsEntry != null) {
				return segmentsEntry;
			}
		}

		return null;
	}

	@Override
	public List<SegmentsEntry> getSegmentsEntries(
		long groupId, boolean includeAncestorSegmentsEntries, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator) {

		if (!includeAncestorSegmentsEntries) {
			return segmentsEntryPersistence.findByGroupId(
				groupId, start, end, orderByComparator);
		}

		return segmentsEntryPersistence.findByGroupId(
			ArrayUtil.append(_portal.getAncestorSiteGroupIds(groupId), groupId),
			start, end, orderByComparator);
	}

	@Override
	public List<SegmentsEntry> getSegmentsEntries(
		long groupId, boolean active, String type, int start, int end,
		OrderByComparator<SegmentsEntry> orderByComparator) {

		return segmentsEntryPersistence.findByG_A_T(
			ArrayUtil.append(_portal.getAncestorSiteGroupIds(groupId), groupId),
			active, type, start, end, orderByComparator);
	}

	@Override
	public List<SegmentsEntry> getSegmentsEntries(
		long groupId, boolean active, String source, String type, int start,
		int end, OrderByComparator<SegmentsEntry> orderByComparator) {

		return segmentsEntryPersistence.findByG_A_S_T(
			ArrayUtil.append(_portal.getAncestorSiteGroupIds(groupId), groupId),
			active, source, type, start, end, orderByComparator);
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

		if (!includeAncestorSegmentsEntries) {
			return segmentsEntryPersistence.countByGroupId(groupId);
		}

		return segmentsEntryPersistence.countByGroupId(
			ArrayUtil.append(
				_portal.getAncestorSiteGroupIds(groupId), groupId));
	}

	@Override
	public BaseModelSearchResult<SegmentsEntry> searchSegmentsEntries(
			long companyId, long groupId, String keywords,
			boolean includeAncestorSegmentsEntries,
			LinkedHashMap<String, Object> params, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, keywords, includeAncestorSegmentsEntries,
			params, start, end, sort);

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
			long segmentsEntryId, String segmentsEntryKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			boolean active, String criteria, ServiceContext serviceContext)
		throws PortalException {

		// Segments entry

		SegmentsEntry segmentsEntry = segmentsEntryPersistence.findByPrimaryKey(
			segmentsEntryId);

		segmentsEntryKey = StringUtil.toUpperCase(segmentsEntryKey.trim());

		validateKey(
			segmentsEntryId, segmentsEntry.getGroupId(), segmentsEntryKey);

		validateName(segmentsEntry.getGroupId(), nameMap);

		segmentsEntry.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		segmentsEntry.setSegmentsEntryKey(segmentsEntryKey);
		segmentsEntry.setNameMap(nameMap);
		segmentsEntry.setDescriptionMap(descriptionMap);
		segmentsEntry.setActive(active);
		segmentsEntry.setCriteria(criteria);
		segmentsEntry.setSource(getSource(criteria, segmentsEntry.getSource()));

		segmentsEntry = segmentsEntryPersistence.update(segmentsEntry);

		// Indexer

		reindexSegmentsEntryRels(segmentsEntry);

		return segmentsEntry;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, String keywords,
		boolean includeAncestorSegmentsEntries,
		LinkedHashMap<String, Object> params, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		Map<String, Serializable> attributes =
			HashMapBuilder.<String, Serializable>put(
				Field.NAME, keywords
			).build();

		params.put("keywords", keywords);

		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		long[] groupIds = {groupId};

		if (includeAncestorSegmentsEntries) {
			groupIds = ArrayUtil.append(
				groupIds, _portal.getAncestorSiteGroupIds(groupId));
		}

		searchContext.setGroupIds(groupIds);

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

	protected String getSource(String criteria, String source) {
		if (Validator.isNotNull(criteria)) {
			Criteria criteriaObj = CriteriaSerializer.deserialize(criteria);

			String referredFilterString = criteriaObj.getFilterString(
				Criteria.Type.REFERRED);

			if (Validator.isNotNull(referredFilterString)) {
				return SegmentsEntryConstants.SOURCE_REFERRED;
			}
			else if (SegmentsEntryConstants.SOURCE_REFERRED.equals(source) &&
					 Validator.isNull(referredFilterString)) {

				return SegmentsEntryConstants.SOURCE_DEFAULT;
			}
		}

		if (Validator.isNull(source)) {
			return SegmentsEntryConstants.SOURCE_DEFAULT;
		}

		return source;
	}

	protected void reindexSegmentsEntryRels(SegmentsEntry segmentsEntry)
		throws PortalException {

		_reindexSegmentsEntryRels(segmentsEntry);

		_reindexReferredSegmentsEntryRels(segmentsEntry);
	}

	protected void validateKey(
			long segmentsEntryId, long groupId, String segmentsEntryKey)
		throws PortalException {

		SegmentsEntry segmentsEntry = fetchSegmentsEntry(
			groupId, segmentsEntryKey, true);

		if ((segmentsEntry != null) &&
			(segmentsEntry.getSegmentsEntryId() != segmentsEntryId)) {

			throw new SegmentsEntryKeyException();
		}
	}

	protected void validateName(long groupId, Map<Locale, String> nameMap)
		throws PortalException {

		Locale defaultLocale = _portal.getSiteDefaultLocale(groupId);

		if (nameMap.isEmpty() || Validator.isNull(nameMap.get(defaultLocale))) {
			throw new SegmentsEntryNameException(
				"Name is null for locale " + defaultLocale.getDisplayName());
		}
	}

	private void _reindexReferredSegmentsEntryRels(SegmentsEntry segmentsEntry)
		throws PortalException {

		List<SegmentsEntry> referredSegmentsEntries =
			segmentsEntryPersistence.findBySource(
				SegmentsEntryConstants.SOURCE_REFERRED);

		for (SegmentsEntry referredSegmentsEntry : referredSegmentsEntries) {
			Criteria criteria = referredSegmentsEntry.getCriteriaObj();

			Criteria.Criterion criterion = criteria.getCriterion(
				SegmentsEntrySegmentsCriteriaContributor.KEY);

			if (criterion != null) {
				String filterString = criterion.getFilterString();

				if (Validator.isNotNull(filterString) &&
					filterString.contains(
						String.valueOf(segmentsEntry.getSegmentsEntryId()))) {

					_reindexSegmentsEntryRels(referredSegmentsEntry);
				}
			}
		}
	}

	private void _reindexSegmentsEntryRels(SegmentsEntry segmentsEntry) {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				Message message = new Message();

				message.put("companyId", segmentsEntry.getCompanyId());
				message.put(
					"segmentsEntryId", segmentsEntry.getSegmentsEntryId());
				message.put("type", segmentsEntry.getType());

				_messageBus.sendMessage(
					SegmentsDestinationNames.SEGMENTS_ENTRY_REINDEX, message);

				return null;
			});
	}

	@Reference
	private BackgroundTaskManager _backgroundTaskManager;

	@Reference
	private MessageBus _messageBus;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

	@Reference
	private SegmentsEntryRoleLocalService _segmentsEntryRoleLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}