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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.NumberIncrement;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.base.AssetEntryLocalServiceBaseImpl;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.asset.util.AssetEntryValidator;
import com.liferay.portlet.asset.util.AssetSearcher;
import com.liferay.portlet.social.model.SocialActivityConstants;

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

		socialActivityLocalService.deleteActivities(entry);
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
		String title, String description, boolean advancedSearch,
		boolean andOperator, int start, int end, String orderByCol1,
		String orderByCol2, String orderByType1, String orderByType2) {

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
			groupIds, classNameIds, keywords, userName, title, description,
			advancedSearch, andOperator, start, end, orderByCol1, orderByCol2,
			orderByType1, orderByType2);

		return getEntries(assetEntryQuery);
	}

	@Override
	public int getEntriesCount(AssetEntryQuery entryQuery) {
		return assetEntryFinder.countEntries(entryQuery);
	}

	@Override
	public int getEntriesCount(
		long[] groupIds, long[] classNameIds, String keywords, String userName,
		String title, String description, boolean advancedSearch,
		boolean andOperator) {

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(
			groupIds, classNameIds, keywords, userName, title, description,
			advancedSearch, andOperator, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null, null, null, null);

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
	public AssetEntry incrementViewCounter(
			long userId, String className, long classPK)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		assetEntryLocalService.incrementViewCounter(
			user.getUserId(), className, classPK, 1);

		AssetEntry assetEntry = getEntry(className, classPK);

		if (!user.isDefaultUser()) {
			socialActivityLocalService.addActivity(
				user.getUserId(), assetEntry.getGroupId(), className, classPK,
				SocialActivityConstants.TYPE_VIEW, StringPool.BLANK, 0);
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
		long classTypeId, String keywords, int status, int start, int end) {

		return search(
			companyId, groupIds, userId, className, classTypeId, keywords,
			keywords, keywords, null, null, status, false, start, end);
	}

	@Override
	public Hits search(
		long companyId, long[] groupIds, long userId, String className,
		long classTypeId, String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, int status,
		boolean andSearch, int start, int end) {

		try {
			Indexer indexer = AssetSearcher.getInstance();

			AssetSearcher assetSearcher = (AssetSearcher)indexer;

			AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

			assetEntryQuery.setClassNameIds(
				getClassNameIds(companyId, className));

			SearchContext searchContext = new SearchContext();

			searchContext.setAndSearch(andSearch);
			searchContext.setAssetCategoryIds(
				StringUtil.split(assetCategoryIds, 0L));
			searchContext.setAssetTagNames(StringUtil.split(assetTagNames));
			searchContext.setAttribute(Field.DESCRIPTION, description);
			searchContext.setAttribute(Field.TITLE, title);
			searchContext.setAttribute(Field.USER_NAME, userName);
			searchContext.setAttribute("paginationType", "regular");
			searchContext.setAttribute("status", status);

			if (classTypeId > 0) {
				searchContext.setClassTypeIds(new long[] {classTypeId});
			}

			searchContext.setCompanyId(companyId);
			searchContext.setEnd(end);
			searchContext.setGroupIds(groupIds);
			searchContext.setStart(start);
			searchContext.setUserId(userId);

			QueryConfig queryConfig = searchContext.getQueryConfig();

			queryConfig.setHighlightEnabled(false);
			queryConfig.setScoreEnabled(false);

			assetSearcher.setAssetEntryQuery(assetEntryQuery);

			return assetSearcher.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #search(long, long[], long,
	 *             String, String, int, int, int)}
	 */
	@Deprecated
	@Override
	public Hits search(
		long companyId, long[] groupIds, long userId, String className,
		String keywords, int start, int end) {

		return search(
			companyId, groupIds, userId, className, keywords,
			WorkflowConstants.STATUS_ANY, start, end);
	}

	@Override
	public Hits search(
		long companyId, long[] groupIds, long userId, String className,
		String keywords, int status, int start, int end) {

		return search(
			companyId, groupIds, userId, className, 0, keywords, status, start,
			end);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #search(long, long[], long,
	 *             String, String, String, String, String, String, int, boolean,
	 *             int, int)}
	 */
	@Deprecated
	@Override
	public Hits search(
		long companyId, long[] groupIds, long userId, String className,
		String userName, String title, String description,
		String assetCategoryIds, String assetTagNames, boolean andSearch,
		int start, int end) {

		return search(
			companyId, groupIds, userId, className, userName, title,
			description, assetCategoryIds, assetTagNames,
			WorkflowConstants.STATUS_ANY, andSearch, start, end);
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

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #search(long, long[], long,
	 *             String, String, int, int, int)}
	 */
	@Deprecated
	@Override
	public Hits search(
		long companyId, long[] groupIds, String className, String keywords,
		int start, int end) {

		return search(
			companyId, groupIds, 0, className, keywords,
			WorkflowConstants.STATUS_ANY, start, end);
	}

	@Override
	public AssetEntry updateEntry(
			long userId, long groupId, Date createDate, Date modifiedDate,
			String className, long classPK, String classUuid, long classTypeId,
			long[] categoryIds, String[] tagNames, boolean visible,
			Date startDate, Date endDate, Date expirationDate, String mimeType,
			String title, String description, String summary, String url,
			String layoutUuid, int height, int width, Integer priority)
		throws PortalException {

		// Entry

		long classNameId = classNameLocalService.getClassNameId(className);

		validate(groupId, className, classTypeId, categoryIds, tagNames);

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

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameId);

		if (assetRendererFactory != null) {
			entry.setListable(assetRendererFactory.isListable(classPK));
		}

		entry.setVisible(visible);
		entry.setStartDate(startDate);
		entry.setEndDate(endDate);
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
			entry.setPriority(priority.intValue());
		}

		// Categories

		if (categoryIds != null) {
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

				assetEntryPersistence.updateImpl(entry);

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
	 *             boolean, Date, Date, Date, String, String, String, String,
	 *             String, String, int, int, Integer)}
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

		return updateEntry(
			userId, groupId, createDate, modifiedDate, className, classPK,
			classUuid, classTypeId, categoryIds, tagNames, visible, startDate,
			endDate, expirationDate, mimeType, title, description, summary, url,
			layoutUuid, height, width, priority);
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
				entry.isVisible(), entry.getStartDate(), entry.getEndDate(),
				entry.getExpirationDate(), entry.getMimeType(),
				entry.getTitle(), entry.getDescription(), entry.getSummary(),
				entry.getUrl(), entry.getLayoutUuid(), entry.getHeight(),
				entry.getWidth(), GetterUtil.getInteger(entry.getPriority()));
		}

		return updateEntry(
			userId, groupId, null, null, className, classPK, null, 0,
			categoryIds, tagNames, true, null, null, null, null, null, null,
			null, null, null, 0, 0, null);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #updateEntry(long, long,
	 *             String, long, String, long, long[], String[], boolean, Date,
	 *             Date, Date, String, String, String, String, String, String,
	 *             int, int, Integer, boolean)}
	 */
	@Deprecated
	@Override
	public AssetEntry updateEntry(
			long userId, long groupId, String className, long classPK,
			String classUuid, long classTypeId, long[] categoryIds,
			String[] tagNames, boolean visible, Date startDate, Date endDate,
			Date publishDate, Date expirationDate, String mimeType,
			String title, String description, String summary, String url,
			String layoutUuid, int height, int width, Integer priority,
			boolean sync)
		throws PortalException {

		return updateEntry(
			userId, groupId, className, classPK, classUuid, classTypeId,
			categoryIds, tagNames, visible, startDate, endDate, expirationDate,
			mimeType, title, description, summary, url, layoutUuid, height,
			width, priority, sync);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #updateEntry(long, long,
	 *             Date, Date, String, long, String, long, long[], String[],
	 *             boolean, Date, Date, Date, String, String, String, String,
	 *             String, String, int, int, Integer, boolean)}
	 */
	@Deprecated
	@Override
	public AssetEntry updateEntry(
			long userId, long groupId, String className, long classPK,
			String classUuid, long classTypeId, long[] categoryIds,
			String[] tagNames, boolean visible, Date startDate, Date endDate,
			Date expirationDate, String mimeType, String title,
			String description, String summary, String url, String layoutUuid,
			int height, int width, Integer priority, boolean sync)
		throws PortalException {

		return updateEntry(
			userId, groupId, null, null, className, classPK, classUuid,
			classTypeId, categoryIds, tagNames, visible, startDate, endDate,
			expirationDate, mimeType, title, description, summary, url,
			layoutUuid, height, width, priority);
	}

	@Override
	public AssetEntry updateEntry(
			String className, long classPK, Date publishDate, boolean visible)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.findByC_C(
			classNameId, classPK);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameId);

		entry.setListable(assetRendererFactory.isListable(classPK));

		entry.setPublishDate(publishDate);

		return updateVisible(entry, visible);
	}

	@Override
	public AssetEntry updateEntry(
			String className, long classPK, Date publishDate,
			Date expirationDate, boolean visible)
		throws PortalException {

		long classNameId = classNameLocalService.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.findByC_C(
			classNameId, classPK);

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(classNameId);

		entry.setListable(assetRendererFactory.isListable(classPK));

		entry.setExpirationDate(expirationDate);
		entry.setPublishDate(publishDate);

		return updateVisible(entry, visible);
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
			long groupId, String className, long classTypePK,
			long[] categoryIds, String[] tagNames)
		throws PortalException {

		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		AssetEntryValidator validator = (AssetEntryValidator)InstancePool.get(
			PropsValues.ASSET_ENTRY_VALIDATOR);

		validator.validate(
			groupId, className, classTypePK, categoryIds, tagNames);
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

	protected AssetEntryQuery getAssetEntryQuery(
		long[] groupIds, long[] classNameIds, String keywords, String userName,
		String title, String description, boolean advancedSearch,
		boolean andOperator, int start, int end, String orderByCol1,
		String orderByCol2, String orderByType1, String orderByType2) {

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

		List<AssetRendererFactory> rendererFactories =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
				companyId);

		long[] classNameIds = new long[rendererFactories.size()];

		for (int i = 0; i < rendererFactories.size(); i++) {
			AssetRendererFactory rendererFactory = rendererFactories.get(i);

			classNameIds[i] = classNameLocalService.getClassNameId(
				rendererFactory.getClassName());
		}

		return classNameIds;
	}

	protected void reindex(AssetEntry entry) throws PortalException {
		String className = PortalUtil.getClassName(entry.getClassNameId());

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(className);

		indexer.reindex(className, entry.getClassPK());
	}

	protected AssetEntry updateVisible(AssetEntry entry, boolean visible)
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

}