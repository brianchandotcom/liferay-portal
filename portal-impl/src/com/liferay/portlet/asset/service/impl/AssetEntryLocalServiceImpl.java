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

package com.liferay.portlet.asset.service.impl;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.exception.NoSuchEntryException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.kernel.validator.AssetEntryValidator;
import com.liferay.asset.kernel.validator.AssetEntryValidatorExclusionRule;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.NumberIncrement;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.social.SocialActivityManagerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.asset.service.base.AssetEntryLocalServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;
import com.liferay.portlet.asset.util.AssetSearcher;
import com.liferay.portlet.asset.validator.AssetEntryValidatorRegistry;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.social.kernel.model.SocialActivityConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Provides the local service for accessing, deleting, updating, and validating
 * asset entries.
 *
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Zsolt Berentey
 */
public class AssetEntryLocalServiceImpl extends AssetEntryLocalServiceBaseImpl {

	@Override
	public void deleteEntry(AssetEntry entry) throws PortalException {

		// Entry

		List<AssetTag> tags = assetEntryPersistence.getAssetTags(
			entry.getEntryId());

		assetEntryPersistence.remove(entry);

		// Links

		assetLinkLocalService.deleteLinks(entry.getEntryId());

		// Tags

		for (AssetTag tag : tags) {
			if (entry.isVisible()) {
				assetTagLocalService.decrementAssetCount(
					tag.getTagId(), entry.getClassNameId());
			}
		}

		// Social

		SocialActivityManagerUtil.deleteActivities(entry);
	}

	@Override
	public void deleteEntry(long entryId) throws PortalException {
		AssetEntry entry = assetEntryPersistence.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	@Override
	public void deleteEntry(String className, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (entry != null) {
			deleteEntry(entry);
		}
	}

	@Override
	public void deleteGroupEntries(long groupId) throws PortalException {
		List<AssetEntry> assetEntries = getGroupEntries(groupId);

		for (AssetEntry assetEntry : assetEntries) {
			deleteEntry(assetEntry);
		}
	}

	@Override
	public void destroy() {
		super.destroy();

		_serviceTrackerMap.close();
	}

	@Override
	public AssetEntry fetchEntry(long entryId) {
		return assetEntryPersistence.fetchByPrimaryKey(entryId);
	}

	@Override
	public AssetEntry fetchEntry(long groupId, String classUuid) {
		return assetEntryPersistence.fetchByG_CU(groupId, classUuid);
	}

	@Override
	public AssetEntry fetchEntry(String className, long classPK) {
		long classNameId = classNameLocalService.getClassNameId(className);

		return assetEntryPersistence.fetchByC_C(classNameId, classPK);
	}

	@Override
	public List<AssetEntry> getAncestorEntries(long entryId)
		throws PortalException {

		List<AssetEntry> entries = new ArrayList<>();

		AssetEntry parentEntry = getParentEntry(entryId);

		while (parentEntry != null) {
			entries.add(parentEntry);

			parentEntry = getParentEntry(parentEntry.getEntryId());
		}

		return entries;
	}

	@Override
	public List<AssetEntry> getChildEntries(long entryId)
		throws PortalException {

		List<AssetEntry> entries = new ArrayList<>();

		List<AssetLink> links = assetLinkLocalService.getDirectLinks(
			entryId, AssetLinkConstants.TYPE_CHILD);

		for (AssetLink link : links) {
			AssetEntry curAsset = getEntry(link.getEntryId2());

			entries.add(curAsset);
		}

		return entries;
	}

	@Override
	public List<AssetEntry> getCompanyEntries(
		long companyId, int start, int end) {

		return assetEntryPersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public int getCompanyEntriesCount(long companyId) {
		return assetEntryPersistence.countByCompanyId(companyId);
	}

	@Override
	public List<AssetEntry> getEntries(AssetEntryQuery entryQuery) {
		return assetEntryFinder.findEntries(entryQuery);
	}

	@Override
	public List<AssetEntry> getEntries(
		long[] groupIds, long[] classNameIds, String keywords, String userName,
		String title, String description, Boolean listable,
		boolean advancedSearch, boolean andOperator, int start, int end,
		String orderByCol1, String orderByCol2, String orderByType1,
		String orderByType2) {

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
			groupIds, classNameIds, keywords, userName, title, description,
			listable, advancedSearch, andOperator, start, end, orderByCol1,
			orderByCol2, orderByType1, orderByType2);

		return getEntries(assetEntryQuery);
	}

	@Override
	public int getEntriesCount(AssetEntryQuery entryQuery) {
		return assetEntryFinder.countEntries(entryQuery);
	}

	@Override
	public int getEntriesCount(
		long[] groupIds, long[] classNameIds, String keywords, String userName,
		String title, String description, Boolean listable,
		boolean advancedSearch, boolean andOperator) {

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
			groupIds, classNameIds, keywords, userName, title, description,
			listable, advancedSearch, andOperator, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null, null, null, null);

		return getEntriesCount(assetEntryQuery);
	}

	@Override
	public AssetEntry getEntry(long entryId) throws PortalException {
		return assetEntryPersistence.findByPrimaryKey(entryId);
	}

	@Override
	public AssetEntry getEntry(long groupId, String classUuid)
		throws PortalException {

		return assetEntryPersistence.findByG_CU(groupId, classUuid);
	}

	@Override
	public AssetEntry getEntry(String className, long classPK)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		return assetEntryPersistence.findByC_C(classNameId, classPK);
	}

	@Override
	public List<AssetEntry> getGroupEntries(long groupId) {
		return assetEntryPersistence.findByGroupId(groupId);
	}

	@Override
	public AssetEntry getNextEntry(long entryId) throws PortalException {
		try {
			getParentEntry(entryId);
		}
		catch (NoSuchEntryException nsee) {
			List<AssetEntry> childEntries = getChildEntries(entryId);

			if (childEntries.isEmpty()) {
				throw nsee;
			}

			return childEntries.get(0);
		}

		List<AssetLink> links = assetLinkLocalService.getDirectLinks(
			entryId, AssetLinkConstants.TYPE_CHILD);

		for (int i = 0; i < links.size(); i++) {
			AssetLink link = links.get(i);

			if (link.getEntryId2() == entryId) {
				if ((i + 1) >= links.size()) {
					throw new NoSuchEntryException("{entryId=" + entryId + "}");
				}
				else {
					AssetLink nextLink = links.get(i + 1);

					return getEntry(nextLink.getEntryId2());
				}
			}
		}

		throw new NoSuchEntryException("{entryId=" + entryId + "}");
	}

	@Override
	public AssetEntry getParentEntry(long entryId) throws PortalException {
		List<AssetLink> links = assetLinkLocalService.getReverseLinks(
			entryId, AssetLinkConstants.TYPE_CHILD);

		if (links.isEmpty()) {
			throw new NoSuchEntryException("{entryId=" + entryId + "}");
		}

		AssetLink link = links.get(0);

		return getEntry(link.getEntryId1());
	}

	@Override
	public AssetEntry getPreviousEntry(long entryId) throws PortalException {
		getParentEntry(entryId);

		List<AssetLink> links = assetLinkLocalService.getDirectLinks(
			entryId, AssetLinkConstants.TYPE_CHILD);

		for (int i = 0; i < links.size(); i++) {
			AssetLink link = links.get(i);

			if (link.getEntryId2() == entryId) {
				if (i == 0) {
					throw new NoSuchEntryException("{entryId=" + entryId + "}");
				}
				else {
					AssetLink nextAssetLink = links.get(i - 1);

					return getEntry(nextAssetLink.getEntryId2());
				}
			}
		}

		throw new NoSuchEntryException("{entryId=" + entryId + "}");
	}

	@Override
	public List<AssetEntry> getTopViewedEntries(
		String className, boolean asc, int start, int end) {

		return getTopViewedEntries(new String[] {className}, asc, start, end);
	}

	@Override
	public List<AssetEntry> getTopViewedEntries(
		String[] className, boolean asc, int start, int end) {

		long[] classNameIds = new long[className.length];

		for (int i = 0; i < className.length; i++) {
			classNameIds[i] = classNameLocalService.getClassNameId(
				className[i]);
		}

		AssetEntryQuery entryQuery = new AssetEntryQuery();

		entryQuery.setClassNameIds(classNameIds);
		entryQuery.setEnd(end);
		entryQuery.setExcludeZeroViewCount(true);
		entryQuery.setOrderByCol1("viewCount");
		entryQuery.setOrderByType1(asc ? "ASC" : "DESC");
		entryQuery.setStart(start);

		return assetEntryFinder.findEntries(entryQuery);
	}

	@Override
	public void incrementViewCounter(long userId, AssetEntry assetEntry)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		assetEntryLocalService.incrementViewCounter(
			user.getUserId(), assetEntry.getClassName(),
			assetEntry.getClassPK(), 1);

		if (!user.isDefaultUser()) {
			SocialActivityManagerUtil.addActivity(
				user.getUserId(), assetEntry, SocialActivityConstants.TYPE_VIEW,
				StringPool.BLANK, 0);
		}
	}

	@Override
	public AssetEntry incrementViewCounter(
			long userId, String className, long classPK)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		assetEntryLocalService.incrementViewCounter(
			user.getUserId(), className, classPK, 1);

		AssetEntry assetEntry = getEntry(className, classPK);

		if (!user.isDefaultUser()) {
			SocialActivityManagerUtil.addActivity(
				user.getUserId(), assetEntry, SocialActivityConstants.TYPE_VIEW,
				StringPool.BLANK, 0);
		}

		return assetEntry;
	}

	@BufferedIncrement(
		configuration = "AssetEntry", incrementClass = NumberIncrement.class
	)
	@Override
	public void incrementViewCounter(
		long userId, String className, long classPK, int increment) {

		if (ExportImportThreadLocal.isImportInProcess() || (classPK <= 0)) {
			return;
		}

		long classNameId = classNameLocalService.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (entry == null) {
			return;
		}

		entry.setModifiedDate(entry.getModifiedDate());
		entry.setViewCount(entry.getViewCount() + increment);

		assetEntryPersistence.update(entry);
	}

	@Override
	public void reindex(List<AssetEntry> entries) throws PortalException {
		for (AssetEntry entry : entries) {
			reindex(entry);
		}
	}

	@Override
	public Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String keywords, boolean showNonindexable, int status,
		int start, int end) {

		return search(
			companyId, groupIds, userId, className, classTypeId, keywords,
			keywords, keywords, null, null, showNonindexable,
			new int[] {status}, false, start, end);
	}

	@Override
	public Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String keywords, boolean showNonindexable,
		int[] statuses, int start, int end) {

		try {
			SearchContext searchContext = buildSearchContext(
				companyId, groupIds, userId, classTypeId, keywords, null, null,
				showNonindexable, statuses, false, start, end);

			return doSearch(companyId, className, searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String keywords, boolean showNonindexable,
		int[] statuses, int start, int end, Sort sort) {

		try {
			SearchContext searchContext = buildSearchContext(
				companyId, groupIds, userId, classTypeId, keywords, null, null,
				showNonindexable, statuses, false, start, end, sort);

			return doSearch(companyId, className, searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String keywords, int status, int start, int end) {

		return search(
			companyId, groupIds, userId, className, classTypeId, keywords,
			false, status, start, end);
	}

	@Override
	public Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, boolean showNonindexable,
		int status, boolean andSearch, int start, int end) {

		return search(
			companyId, groupIds, userId, className, classTypeId, userName,
			title, description, assetCategoryIds, assetTagNames,
			showNonindexable, new int[] {status}, andSearch, start, end);
	}

	@Override
	public Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, boolean showNonindexable,
		int[] statuses, boolean andSearch, int start, int end) {

		try {
			SearchContext searchContext = buildSearchContext(
				companyId, groupIds, userId, classTypeId, userName, title,
				description, assetCategoryIds, assetTagNames, showNonindexable,
				statuses, andSearch, start, end);

			return doSearch(companyId, className, searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, int status,
		boolean andSearch, int start, int end) {

		return search(
			companyId, groupIds, userId, className, classTypeId, userName,
			title, description, assetCategoryIds, assetTagNames, false,
			new int[] {status}, andSearch, start, end);
	}

	@Override
	public Hits search(
		long companyId, long[] groupIds, long userId, String className,
		String keywords, int status, int start, int end) {

		return search(
			companyId, groupIds, userId, className, 0, keywords, status, start,
			end);
	}

	@Override
	public Hits search(
		long companyId, long[] groupIds, long userId, String className,
		String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, int status,
		boolean andSearch, int start, int end) {

		return search(
			companyId, groupIds, userId, className, 0, userName, title,
			description, assetCategoryIds, assetTagNames, status, andSearch,
			start, end);
	}

	@Override
	public long searchCount(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String keywords, boolean showNonindexable,
		int[] statuses) {

		return searchCount(
			companyId, groupIds, userId, className, classTypeId, keywords,
			keywords, keywords, null, null, showNonindexable, statuses, false);
	}

	@Override
	public long searchCount(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, boolean showInvisible,
		boolean showNonindexable, int[] statuses, boolean andSearch) {

		try {
			Indexer<?> indexer = AssetSearcher.getInstance();

			AssetSearcher assetSearcher = (AssetSearcher)indexer;

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

			assetEntryQuery.setAttribute("showInvisible", showInvisible);

			String[] assetTagNamesArray = StringUtil.split(assetTagNames);

			if (andSearch) {
				assetEntryQuery.setAnyCategoryIds(
					StringUtil.split(assetCategoryIds, 0L));

				for (String assetTagName : assetTagNamesArray) {
					long[] allAssetTagIds = getTagIds(groupIds, assetTagName);

					assetEntryQuery.addAllTagIdsArray(allAssetTagIds);
				}
			}
			else {
				assetEntryQuery.setAllCategoryIds(
					StringUtil.split(assetCategoryIds, 0L));

				if (ArrayUtil.isNotEmpty(assetTagNamesArray)) {
					assetEntryQuery.setAnyTagIds(
						getTagIds(groupIds, assetTagNames));
				}
			}

			assetEntryQuery.setClassNameIds(
				getClassNameIds(companyId, className));

			SearchContext searchContext = buildSearchContext(
				companyId, groupIds, userId, classTypeId, userName, title,
				description, assetCategoryIds, assetTagNames, showNonindexable,
				statuses, andSearch, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			QueryConfig queryConfig = searchContext.getQueryConfig();

			queryConfig.setHighlightEnabled(false);
			queryConfig.setScoreEnabled(false);

			assetSearcher.setAssetEntryQuery(assetEntryQuery);

			return assetSearcher.searchCount(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public long searchCount(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, boolean showNonindexable,
		int[] statuses, boolean andSearch) {

		return searchCount(
			companyId, groupIds, userId, className, classTypeId, userName,
			title, description, assetCategoryIds, assetTagNames,
			showNonindexable, false, statuses, andSearch);
	}

	@Override
	public AssetEntry updateEntry(
			long userId, long groupId, Date createDate, Date modifiedDate,
			String className, long classPK, String classUuid, long classTypeId,
			long[] categoryIds, String[] tagNames, boolean listable,
			boolean visible, Date startDate, Date endDate, Date publishDate,
			Date expirationDate, String mimeType, String title,
			String description, String summary, String url, String layoutUuid,
			int height, int width, Double priority)
		throws PortalException {

		// Entry

		long classNameId = classNameLocalService.getClassNameId(className);

		validate(
			groupId, className, classPK, classTypeId, categoryIds, tagNames);

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		boolean oldVisible = false;

		if (entry != null) {
			oldVisible = entry.isVisible();
		}

		if (modifiedDate == null) {
			modifiedDate = new Date();
		}

		if (entry == null) {
			long entryId = counterLocalService.increment();

			entry = assetEntryPersistence.create(entryId);

			Group group = groupLocalService.getGroup(groupId);

			entry.setCompanyId(group.getCompanyId());

			entry.setUserId(userId);

			User user = userPersistence.fetchByPrimaryKey(userId);

			if (user != null) {
				entry.setUserName(user.getFullName());
			}
			else {
				entry.setUserName(StringPool.BLANK);
			}

			if (createDate == null) {
				createDate = new Date();
			}

			entry.setCreateDate(createDate);

			entry.setClassNameId(classNameId);
			entry.setClassPK(classPK);
			entry.setClassUuid(classUuid);

			if (priority == null) {
				entry.setPriority(0);
			}

			entry.setViewCount(0);
		}

		entry.setGroupId(groupId);
		entry.setModifiedDate(modifiedDate);
		entry.setClassTypeId(classTypeId);
		entry.setListable(listable);
		entry.setVisible(visible);
		entry.setStartDate(startDate);
		entry.setEndDate(endDate);

		if (publishDate != null) {
			entry.setPublishDate(publishDate);
		}

		entry.setExpirationDate(expirationDate);
		entry.setMimeType(mimeType);
		entry.setTitle(title);
		entry.setDescription(description);
		entry.setSummary(summary);
		entry.setUrl(url);
		entry.setLayoutUuid(layoutUuid);
		entry.setHeight(height);
		entry.setWidth(width);

		if (priority != null) {
			entry.setPriority(priority.doubleValue());
		}

		// Categories

		if (categoryIds != null) {
			categoryIds = checkCategories(className, classPK, categoryIds);

			assetEntryPersistence.setAssetCategories(
				entry.getEntryId(), categoryIds);
		}

		// Tags

		if (tagNames != null) {
			long siteGroupId = PortalUtil.getSiteGroupId(groupId);

			Group siteGroup = groupLocalService.getGroup(siteGroupId);

			List<AssetTag> tags = assetTagLocalService.checkTags(
				userId, siteGroup, tagNames);

			List<AssetTag> oldTags = assetEntryPersistence.getAssetTags(
				entry.getEntryId());

			assetEntryPersistence.setAssetTags(entry.getEntryId(), tags);

			if (entry.isVisible()) {
				boolean isNew = entry.isNew();

				if (isNew) {
					for (AssetTag tag : tags) {
						assetTagLocalService.incrementAssetCount(
							tag.getTagId(), classNameId);
					}
				}
				else {
					for (AssetTag oldTag : oldTags) {
						if (!tags.contains(oldTag)) {
							assetTagLocalService.decrementAssetCount(
								oldTag.getTagId(), classNameId);
						}
					}

					for (AssetTag tag : tags) {
						if (!oldTags.contains(tag)) {
							assetTagLocalService.incrementAssetCount(
								tag.getTagId(), classNameId);
						}
					}
				}
			}
			else if (oldVisible) {
				for (AssetTag oldTag : oldTags) {
					assetTagLocalService.decrementAssetCount(
						oldTag.getTagId(), classNameId);
				}
			}
		}

		// Update entry after tags so that entry listeners have access to the
		// saved categories and tags

		assetEntryPersistence.update(entry);

		// Indexer

		reindex(entry);

		return entry;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, long,
	 *             Date, Date, String, long, String, long, long[], String[],
	 *             boolean, boolean, Date, Date, Date, Date, String, String,
	 *             String, String, String, String, int, int, Double)}
	 */
	@Deprecated
	@Override
	public AssetEntry updateEntry(
			long userId, long groupId, Date createDate, Date modifiedDate,
			String className, long classPK, String classUuid, long classTypeId,
			long[] categoryIds, String[] tagNames, boolean listable,
			boolean visible, Date startDate, Date endDate, Date expirationDate,
			String mimeType, String title, String description, String summary,
			String url, String layoutUuid, int height, int width,
			Double priority)
		throws PortalException {

		return updateEntry(
			userId, groupId, createDate, modifiedDate, className, classPK,
			classUuid, classTypeId, categoryIds, tagNames, true, visible,
			startDate, endDate, null, expirationDate, mimeType, title,
			description, summary, url, layoutUuid, height, width, priority);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateEntry(long, long,
	 *             Date, Date, String, long, String, long, long[], String[],
	 *             boolean, boolean, Date, Date, Date, Date, String, String,
	 *             String, String, String, String, int, int, Double)}
	 */
	@Deprecated
	@Override
	public AssetEntry updateEntry(
			long userId, long groupId, Date createDate, Date modifiedDate,
			String className, long classPK, String classUuid, long classTypeId,
			long[] categoryIds, String[] tagNames, boolean visible,
			Date startDate, Date endDate, Date expirationDate, String mimeType,
			String title, String description, String summary, String url,
			String layoutUuid, int height, int width, Integer priority,
			boolean sync)
		throws PortalException {

		Double priorityDouble = null;

		if (priority != null) {
			priorityDouble = priority.doubleValue();
		}

		return updateEntry(
			userId, groupId, createDate, modifiedDate, className, classPK,
			classUuid, classTypeId, categoryIds, tagNames, true, visible,
			startDate, endDate, null, expirationDate, mimeType, title,
			description, summary, url, layoutUuid, height, width,
			priorityDouble);
	}

	@Override
	public AssetEntry updateEntry(
			long userId, long groupId, String className, long classPK,
			long[] categoryIds, String[] tagNames)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (entry != null) {
			return updateEntry(
				userId, groupId, entry.getCreateDate(), entry.getModifiedDate(),
				className, classPK, entry.getClassUuid(),
				entry.getClassTypeId(), categoryIds, tagNames,
				entry.isListable(), entry.isVisible(), entry.getStartDate(),
				entry.getEndDate(), entry.getPublishDate(),
				entry.getExpirationDate(), entry.getMimeType(),
				entry.getTitle(), entry.getDescription(), entry.getSummary(),
				entry.getUrl(), entry.getLayoutUuid(), entry.getHeight(),
				entry.getWidth(), entry.getPriority());
		}

		return updateEntry(
			userId, groupId, null, null, className, classPK, null, 0,
			categoryIds, tagNames, true, true, null, null, null, null, null,
			null, null, null, null, null, 0, 0, (Double)null);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateEntry(String, long,
	 *             Date, Date, boolean, boolean)}
	 */
	@Deprecated
	@Override
	public AssetEntry updateEntry(
			String className, long classPK, Date publishDate, boolean visible)
		throws PortalException {

		return updateEntry(
			className, classPK, publishDate, null, true, visible);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #updateEntry(String, long,
	 *             Date, Date, boolean, boolean)}
	 */
	@Deprecated
	@Override
	public AssetEntry updateEntry(
			String className, long classPK, Date publishDate,
			Date expirationDate, boolean visible)
		throws PortalException {

		return updateEntry(
			className, classPK, publishDate, expirationDate, true, visible);
	}

	@Override
	public AssetEntry updateEntry(
			String className, long classPK, Date publishDate,
			Date expirationDate, boolean listable, boolean visible)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.findByC_C(
			classNameId, classPK);

		entry.setExpirationDate(expirationDate);
		entry.setListable(listable);
		entry.setPublishDate(publishDate);

		return updateVisible(entry, visible);
	}

	@Override
	public AssetEntry updateVisible(AssetEntry entry, boolean visible)
		throws PortalException {

		if (visible == entry.isVisible()) {
			return assetEntryPersistence.update(entry);
		}

		entry.setVisible(visible);

		assetEntryPersistence.update(entry);

		List<AssetTag> tags = assetEntryPersistence.getAssetTags(
			entry.getEntryId());

		if (visible) {
			for (AssetTag tag : tags) {
				assetTagLocalService.incrementAssetCount(
					tag.getTagId(), entry.getClassNameId());
			}

			socialActivityCounterLocalService.enableActivityCounters(
				entry.getClassNameId(), entry.getClassPK());
		}
		else {
			for (AssetTag tag : tags) {
				assetTagLocalService.decrementAssetCount(
					tag.getTagId(), entry.getClassNameId());
			}

			socialActivityCounterLocalService.disableActivityCounters(
				entry.getClassNameId(), entry.getClassPK());
		}

		return entry;
	}

	@Override
	public AssetEntry updateVisible(
			String className, long classPK, boolean visible)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.findByC_C(
			classNameId, classPK);

		return updateVisible(entry, visible);
	}

	@Override
	public void validate(
			long groupId, String className, long classPK, long classTypePK,
			long[] categoryIds, String[] tagNames)
		throws PortalException {

		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		List<AssetEntryValidatorExclusionRule> exclusionRules =
			_serviceTrackerMap.getService(className);

		if (exclusionRules != null) {
			for (AssetEntryValidatorExclusionRule exclusionRule :
					exclusionRules) {

				if (exclusionRule.isValidationExcluded(
						groupId, className, classPK, classTypePK, categoryIds,
						tagNames)) {

					return;
				}
			}
		}

		for (AssetEntryValidator assetEntryValidator :
				assetEntryValidatorRegistry.getAssetEntryValidators(
					className)) {

			assetEntryValidator.validate(
				groupId, className, classPK, classTypePK, categoryIds,
				tagNames);
		}
	}

	@Override
	public void validate(
			long groupId, String className, long classTypePK,
			long[] categoryIds, String[] tagNames)
		throws PortalException {

		validate(groupId, className, 0L, classTypePK, categoryIds, tagNames);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #validate(long, String, long,
	 *             long[], String[])}
	 */
	@Deprecated
	@Override
	public void validate(
			long groupId, String className, long[] categoryIds,
			String[] tagNames)
		throws PortalException {

		validate(
			groupId, className, AssetCategoryConstants.ALL_CLASS_TYPE_PK,
			categoryIds, tagNames);
	}

	protected SearchContext buildSearchContext(
		long companyId, long[] groupIds, long userId, long classTypeId,
		String assetCategoryIds, String assetTagNames, boolean showNonindexable,
		int[] statuses, boolean andSearch, int start, int end) {

		return buildSearchContext(
			companyId, groupIds, userId, classTypeId, assetCategoryIds,
			assetTagNames, showNonindexable, statuses, andSearch, start, end,
			null);
	}

	protected SearchContext buildSearchContext(
		long companyId, long[] groupIds, long userId, long classTypeId,
		String assetCategoryIds, String assetTagNames, boolean showNonindexable,
		int[] statuses, boolean andSearch, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAndSearch(andSearch);
		searchContext.setAssetCategoryIds(
			StringUtil.split(assetCategoryIds, 0L));
		searchContext.setAssetTagNames(StringUtil.split(assetTagNames));
		searchContext.setAttribute("paginationType", "regular");
		searchContext.setAttribute("status", statuses);

		if (classTypeId > 0) {
			searchContext.setClassTypeIds(new long[] {classTypeId});
		}

		if (showNonindexable) {
			searchContext.setAttribute("showNonindexable", Boolean.TRUE);
		}

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(groupIds);
		searchContext.setStart(start);
		searchContext.setSorts(sort);
		searchContext.setUserId(userId);

		return searchContext;
	}

	protected SearchContext buildSearchContext(
		long companyId, long[] groupIds, long userId, long classTypeId,
		String keywords, String assetCategoryIds, String assetTagNames,
		boolean showNonindexable, int[] statuses, boolean andSearch, int start,
		int end) {

		SearchContext searchContext = buildSearchContext(
			companyId, groupIds, userId, classTypeId, assetCategoryIds,
			assetTagNames, showNonindexable, statuses, andSearch, start, end);

		searchContext.setKeywords(keywords);

		return searchContext;
	}

	protected SearchContext buildSearchContext(
		long companyId, long[] groupIds, long userId, long classTypeId,
		String keywords, String assetCategoryIds, String assetTagNames,
		boolean showNonindexable, int[] statuses, boolean andSearch, int start,
		int end, Sort sort) {

		SearchContext searchContext = buildSearchContext(
			companyId, groupIds, userId, classTypeId, assetCategoryIds,
			assetTagNames, showNonindexable, statuses, andSearch, start, end,
			sort);

		searchContext.setKeywords(keywords);

		return searchContext;
	}

	protected SearchContext buildSearchContext(
		long companyId, long[] groupIds, long userId, long classTypeId,
		String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, boolean showNonindexable,
		int[] statuses, boolean andSearch, int start, int end) {

		SearchContext searchContext = buildSearchContext(
			companyId, groupIds, userId, classTypeId, assetCategoryIds,
			assetTagNames, showNonindexable, statuses, andSearch, start, end);

		searchContext.setAttribute(Field.DESCRIPTION, description);
		searchContext.setAttribute(Field.TITLE, title);
		searchContext.setAttribute(Field.USER_NAME, userName);

		return searchContext;
	}

	protected long[] checkCategories(
			String className, long classPK, long[] categoryIds)
		throws PortalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			return categoryIds;
		}

		List<AssetCategory> oldCategories =
			assetCategoryLocalService.getCategories(className, classPK);

		for (AssetCategory category : oldCategories) {
			if (!ArrayUtil.contains(categoryIds, category.getCategoryId()) &&
				!AssetCategoryPermission.contains(
					permissionChecker, category, ActionKeys.VIEW)) {

				categoryIds = ArrayUtil.append(
					categoryIds, category.getCategoryId());
			}
		}

		return categoryIds;
	}

	protected Hits doSearch(
			long companyId, String className, SearchContext searchContext)
		throws Exception {

		Indexer<?> indexer = AssetSearcher.getInstance();

		AssetSearcher assetSearcher = (AssetSearcher)indexer;

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setClassNameIds(getClassNameIds(companyId, className));

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		assetSearcher.setAssetEntryQuery(assetEntryQuery);

		return assetSearcher.search(searchContext);
	}

	protected AssetEntryQuery getAssetEntryQuery(
		long[] groupIds, long[] classNameIds, String keywords, String userName,
		String title, String description, Boolean listable,
		boolean advancedSearch, boolean andOperator, int start, int end,
		String orderByCol1, String orderByCol2, String orderByType1,
		String orderByType2) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		if (advancedSearch) {
			assetEntryQuery.setAndOperator(andOperator);
			assetEntryQuery.setDescription(description);
			assetEntryQuery.setTitle(title);
			assetEntryQuery.setUserName(userName);
		}
		else {
			assetEntryQuery.setKeywords(keywords);
		}

		assetEntryQuery.setClassNameIds(classNameIds);
		assetEntryQuery.setEnd(end);
		assetEntryQuery.setGroupIds(groupIds);
		assetEntryQuery.setListable(listable);
		assetEntryQuery.setOrderByCol1(orderByCol1);
		assetEntryQuery.setOrderByCol2(orderByCol2);
		assetEntryQuery.setOrderByType1(orderByType1);
		assetEntryQuery.setOrderByType2(orderByType2);
		assetEntryQuery.setStart(start);

		return assetEntryQuery;
	}

	protected long[] getClassNameIds(long companyId, String className) {
		if (Validator.isNotNull(className)) {
			return new long[] {classNameLocalService.getClassNameId(className)};
		}

		List<AssetRendererFactory<?>> rendererFactories =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				companyId);

		long[] classNameIds = new long[rendererFactories.size()];

		for (int i = 0; i < rendererFactories.size(); i++) {
			AssetRendererFactory<?> rendererFactory = rendererFactories.get(i);

			classNameIds[i] = classNameLocalService.getClassNameId(
				rendererFactory.getClassName());
		}

		return classNameIds;
	}

	protected long[] getTagIds(long[] groupIds, String tagName) {
		if (groupIds != null) {
			return assetTagLocalService.getTagIds(groupIds, tagName);
		}

		List<Long> tagIds = new ArrayList<>();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			AssetTag.class);

		Property property = PropertyFactoryUtil.forName("name");

		dynamicQuery.add(property.eq(tagName));

		List<AssetTag> assetTags = assetTagPersistence.findWithDynamicQuery(
			dynamicQuery);

		for (AssetTag assetTag : assetTags) {
			tagIds.add(assetTag.getTagId());
		}

		return ArrayUtil.toLongArray(tagIds);
	}

	protected void reindex(AssetEntry entry) throws PortalException {
		String className = PortalUtil.getClassName(entry.getClassNameId());

		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(className);

		indexer.reindex(className, entry.getClassPK());
	}

	@BeanReference(type = AssetEntryValidatorRegistry.class)
	protected AssetEntryValidatorRegistry assetEntryValidatorRegistry;

	private final ServiceTrackerMap
		<String, List<AssetEntryValidatorExclusionRule>>
			_serviceTrackerMap = ServiceTrackerCollections.openMultiValueMap(
				AssetEntryValidatorExclusionRule.class, "model.class.name");

}