/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.NumberIncrement;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.FacetedSearcher;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.AssetEntriesFacet;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.ScopeFacet;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.base.AssetEntryLocalServiceBaseImpl;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.asset.util.AssetEntryValidator;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.wiki.model.WikiPage;

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
	public void deleteEntry(AssetEntry entry)
		throws PortalException, SystemException {

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
	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		AssetEntry entry = assetEntryPersistence.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	@Override
	public void deleteEntry(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (entry != null) {
			deleteEntry(entry);
		}
	}

	@Override
	public AssetEntry fetchEntry(long entryId) throws SystemException {
		return assetEntryPersistence.fetchByPrimaryKey(entryId);
	}

	@Override
	public AssetEntry fetchEntry(long groupId, String classUuid)
		throws SystemException {

		return assetEntryPersistence.fetchByG_CU(groupId, classUuid);
	}

	@Override
	public AssetEntry fetchEntry(String className, long classPK)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return assetEntryPersistence.fetchByC_C(classNameId, classPK);
	}

	@Override
	public List<AssetEntry> getAncestorEntries(long entryId)
		throws PortalException, SystemException {

		List<AssetEntry> entries = new ArrayList<AssetEntry>();

		AssetEntry parentEntry = getParentEntry(entryId);

		while (parentEntry != null) {
			entries.add(parentEntry);

			parentEntry = getParentEntry(parentEntry.getEntryId());
		}

		return entries;
	}

	@Override
	public List<AssetEntry> getChildEntries(long entryId)
		throws PortalException, SystemException {

		List<AssetEntry> entries = new ArrayList<AssetEntry>();

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
			long companyId, int start, int end)
		throws SystemException {

		return assetEntryPersistence.findByCompanyId(companyId, start, end);
	}

	@Override
	public int getCompanyEntriesCount(long companyId) throws SystemException {
		return assetEntryPersistence.countByCompanyId(companyId);
	}

	@Override
	public List<AssetEntry> getEntries(AssetEntryQuery entryQuery)
		throws SystemException {

		return assetEntryFinder.findEntries(entryQuery);
	}

	@Override
	public int getEntriesCount(AssetEntryQuery entryQuery)
		throws SystemException {

		return assetEntryFinder.countEntries(entryQuery);
	}

	@Override
	public AssetEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return assetEntryPersistence.findByPrimaryKey(entryId);
	}

	@Override
	public AssetEntry getEntry(long groupId, String classUuid)
		throws PortalException, SystemException {

		return assetEntryPersistence.findByG_CU(groupId, classUuid);
	}

	@Override
	public AssetEntry getEntry(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return assetEntryPersistence.findByC_C(classNameId, classPK);
	}

	@Override
	public AssetEntry getNextEntry(long entryId)
		throws PortalException, SystemException {

		try {
			getParentEntry(entryId);
		}
		catch (NoSuchEntryException nsee) {
			List<AssetEntry> childEntries = getChildEntries(entryId);

			if (childEntries.isEmpty()) {
				throw new NoSuchEntryException();
			}

			return childEntries.get(0);
		}

		List<AssetLink> links = assetLinkLocalService.getDirectLinks(
			entryId, AssetLinkConstants.TYPE_CHILD);

		for (int i = 0; i < links.size(); i++) {
			AssetLink link = links.get(i);

			if (link.getEntryId2() == entryId) {
				if ((i + 1) >= links.size()) {
					throw new NoSuchEntryException();
				}
				else {
					AssetLink nextLink = links.get(i + 1);

					return getEntry(nextLink.getEntryId2());
				}
			}
		}

		throw new NoSuchEntryException();
	}

	@Override
	public AssetEntry getParentEntry(long entryId)
		throws PortalException, SystemException {

		List<AssetLink> links = assetLinkLocalService.getReverseLinks(
			entryId, AssetLinkConstants.TYPE_CHILD);

		if (links.isEmpty()) {
			throw new NoSuchEntryException();
		}

		AssetLink link = links.get(0);

		return getEntry(link.getEntryId1());
	}

	@Override
	public AssetEntry getPreviousEntry(long entryId)
		throws PortalException, SystemException {

		getParentEntry(entryId);

		List<AssetLink> links = assetLinkLocalService.getDirectLinks(
			entryId, AssetLinkConstants.TYPE_CHILD);

		for (int i = 0; i < links.size(); i++) {
			AssetLink link = links.get(i);

			if (link.getEntryId2() == entryId) {
				if (i == 0) {
					throw new NoSuchEntryException();
				}
				else {
					AssetLink nextAssetLink = links.get(i - 1);

					return getEntry(nextAssetLink.getEntryId2());
				}
			}
		}

		throw new NoSuchEntryException();
	}

	@Override
	public List<AssetEntry> getTopViewedEntries(
			String className, boolean asc, int start, int end)
		throws SystemException {

		return getTopViewedEntries(new String[] {className}, asc, start, end);
	}

	@Override
	public List<AssetEntry> getTopViewedEntries(
			String[] className, boolean asc, int start, int end)
		throws SystemException {

		long[] classNameIds = new long[className.length];

		for (int i = 0; i < className.length; i++) {
			classNameIds[i] = PortalUtil.getClassNameId(className[i]);
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
		throws PortalException, SystemException {

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
		configuration = "AssetEntry", incrementClass = NumberIncrement.class)
	@Override
	public AssetEntry incrementViewCounter(
			long userId, String className, long classPK, int increment)
		throws SystemException {

		if (classPK <= 0) {
			return null;
		}

		long classNameId = PortalUtil.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (entry == null) {
			return null;
		}

		entry.setViewCount(entry.getViewCount() + increment);

		assetEntryPersistence.update(entry);

		return entry;
	}

	@Override
	public void reindex(List<AssetEntry> entries) throws PortalException {
		for (AssetEntry entry : entries) {
			String className = PortalUtil.getClassName(entry.getClassNameId());

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(className);

			indexer.reindex(className, entry.getClassPK());
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #search(long, long[], long,
	 *             String, String, int, int, int)}
	 */
	@Override
	public Hits search(
			long companyId, long[] groupIds, long userId, String className,
			String keywords, int start, int end)
		throws SystemException {

		return search(
			companyId, groupIds, userId, className, keywords,
			WorkflowConstants.STATUS_ANY, start, end);
	}

	@Override
	public Hits search(
			long companyId, long[] groupIds, long userId, String className,
			String keywords, int status, int start, int end)
		throws SystemException {

		try {
			SearchContext searchContext = new SearchContext();

			Facet assetEntriesFacet = new AssetEntriesFacet(searchContext);

			assetEntriesFacet.setStatic(true);

			searchContext.addFacet(assetEntriesFacet);

			Facet scopeFacet = new ScopeFacet(searchContext);

			scopeFacet.setStatic(true);

			searchContext.addFacet(scopeFacet);

			searchContext.setAttribute("paginationType", "regular");
			searchContext.setAttribute("status", status);
			searchContext.setCompanyId(companyId);
			searchContext.setEnd(end);
			searchContext.setEntryClassNames(
				getClassNames(companyId, className));
			searchContext.setGroupIds(groupIds);
			searchContext.setKeywords(keywords);

			QueryConfig queryConfig = new QueryConfig();

			queryConfig.setHighlightEnabled(false);
			queryConfig.setScoreEnabled(false);

			searchContext.setQueryConfig(queryConfig);

			searchContext.setStart(start);
			searchContext.setUserId(userId);

			Indexer indexer = FacetedSearcher.getInstance();

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #search(long, long[], long,
	 *             String, String, String, String, String, String, int, boolean,
	 *             int, int)}
	 */
	@Override
	public Hits search(
			long companyId, long[] groupIds, long userId, String className,
			String userName, String title, String description,
			String assetCategoryIds, String assetTagNames, boolean andSearch,
			int start, int end)
		throws SystemException {

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
			boolean andSearch, int start, int end)
		throws SystemException {

		try {
			SearchContext searchContext = new SearchContext();

			Facet assetEntriesFacet = new AssetEntriesFacet(searchContext);

			assetEntriesFacet.setStatic(true);

			searchContext.addFacet(assetEntriesFacet);

			Facet scopeFacet = new ScopeFacet(searchContext);

			scopeFacet.setStatic(true);

			searchContext.addFacet(scopeFacet);

			searchContext.setAndSearch(andSearch);
			searchContext.setAssetCategoryIds(
				StringUtil.split(assetCategoryIds, 0L));
			searchContext.setAssetTagNames(StringUtil.split(assetTagNames));
			searchContext.setAttribute(Field.DESCRIPTION, description);
			searchContext.setAttribute(Field.TITLE, title);
			searchContext.setAttribute(Field.USER_NAME, userName);
			searchContext.setAttribute("paginationType", "regular");
			searchContext.setAttribute("status", status);
			searchContext.setCompanyId(companyId);
			searchContext.setEnd(end);
			searchContext.setEntryClassNames(
				getClassNames(companyId, className));
			searchContext.setGroupIds(groupIds);

			QueryConfig queryConfig = new QueryConfig();

			queryConfig.setHighlightEnabled(false);
			queryConfig.setScoreEnabled(false);

			searchContext.setQueryConfig(queryConfig);

			searchContext.setStart(start);
			searchContext.setUserId(userId);

			Indexer indexer = FacetedSearcher.getInstance();

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #search(long, long[], long,
	 *             String, String, int, int, int)}
	 */
	@Override
	public Hits search(
			long companyId, long[] groupIds, String className, String keywords,
			int start, int end)
		throws SystemException {

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
			String layoutUuid, int height, int width, Integer priority,
			boolean sync)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);

		validate(groupId, className, categoryIds, tagNames);

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

			entry.setCompanyId(user.getCompanyId());
			entry.setUserId(user.getUserId());
			entry.setUserName(user.getFullName());

			if (createDate == null) {
				createDate = new Date();
			}

			entry.setCreateDate(createDate);

			entry.setModifiedDate(modifiedDate);
			entry.setClassNameId(classNameId);
			entry.setClassPK(classPK);
			entry.setClassUuid(classUuid);
			entry.setVisible(visible);
			entry.setExpirationDate(expirationDate);

			if (priority == null) {
				entry.setPriority(0);
			}

			entry.setViewCount(0);
		}

		entry.setGroupId(groupId);
		entry.setModifiedDate(modifiedDate);
		entry.setClassTypeId(classTypeId);
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

			List<AssetTag> tags = new ArrayList<AssetTag>(tagNames.length);

			for (String tagName : tagNames) {
				AssetTag tag = null;

				try {
					tag = assetTagLocalService.getTag(siteGroupId, tagName);
				}
				catch (NoSuchTagException nste) {
					ServiceContext serviceContext = new ServiceContext();

					serviceContext.setAddGroupPermissions(true);
					serviceContext.setAddGuestPermissions(true);
					serviceContext.setScopeGroupId(siteGroupId);

					tag = assetTagLocalService.addTag(
						user.getUserId(), tagName,
						PropsValues.ASSET_TAG_PROPERTIES_DEFAULT,
						serviceContext);
				}

				if (tag != null) {
					tags.add(tag);
				}
			}

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

		// Synchronize

		if (!sync) {
			return entry;
		}

		if (className.equals(BlogsEntry.class.getName())) {
			BlogsEntry blogsEntry = blogsEntryPersistence.findByPrimaryKey(
				classPK);

			blogsEntry.setTitle(title);

			blogsEntryPersistence.update(blogsEntry);
		}
		else if (className.equals(BookmarksEntry.class.getName())) {
			BookmarksEntry bookmarksEntry =
				bookmarksEntryPersistence.findByPrimaryKey(classPK);

			bookmarksEntry.setName(title);
			bookmarksEntry.setDescription(description);
			bookmarksEntry.setUrl(url);

			bookmarksEntryPersistence.update(bookmarksEntry);
		}
		else if (className.equals(DLFileEntry.class.getName())) {
			DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
				classPK);

			dlFileEntry.setTitle(title);
			dlFileEntry.setDescription(description);

			dlFileEntryPersistence.update(dlFileEntry);
		}
		else if (className.equals(JournalArticle.class.getName())) {
			JournalArticle journalArticle =
				journalArticlePersistence.findByPrimaryKey(classPK);

			journalArticle.setTitle(title);
			journalArticle.setDescription(description);

			journalArticlePersistence.update(journalArticle);
		}
		else if (className.equals(MBMessage.class.getName())) {
			MBMessage mbMessage = mbMessagePersistence.findByPrimaryKey(
				classPK);

			mbMessage.setSubject(title);

			mbMessagePersistence.update(mbMessage);
		}
		else if (className.equals(WikiPage.class.getName())) {
			WikiPage wikiPage = wikiPagePersistence.findByPrimaryKey(classPK);

			wikiPage.setTitle(title);

			wikiPagePersistence.update(wikiPage);
		}

		return entry;
	}

	@Override
	public AssetEntry updateEntry(
			long userId, long groupId, String className, long classPK,
			long[] categoryIds, String[] tagNames)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

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
				entry.getWidth(), GetterUtil.getInteger(entry.getPriority()),
				false);
		}

		return updateEntry(
			userId, groupId, null, null, className, classPK, null, 0,
			categoryIds, tagNames, true, null, null, null, null, null, null,
			null, null, null, 0, 0, null, false);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #updateEntry(long, long,
	 *             String, long, String, long, long[], String[], boolean, Date,
	 *             Date, Date, String, String, String, String, String, String,
	 *             int, int, Integer, boolean)}
	 */
	@Override
	public AssetEntry updateEntry(
			long userId, long groupId, String className, long classPK,
			String classUuid, long classTypeId, long[] categoryIds,
			String[] tagNames, boolean visible, Date startDate, Date endDate,
			Date publishDate, Date expirationDate, String mimeType,
			String title, String description, String summary, String url,
			String layoutUuid, int height, int width, Integer priority,
			boolean sync)
		throws PortalException, SystemException {

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
	@Override
	public AssetEntry updateEntry(
			long userId, long groupId, String className, long classPK,
			String classUuid, long classTypeId, long[] categoryIds,
			String[] tagNames, boolean visible, Date startDate, Date endDate,
			Date expirationDate, String mimeType, String title,
			String description, String summary, String url, String layoutUuid,
			int height, int width, Integer priority, boolean sync)
		throws PortalException, SystemException {

		return updateEntry(
			userId, groupId, null, null, className, classPK, classUuid,
			classTypeId, categoryIds, tagNames, visible, startDate, endDate,
			expirationDate, mimeType, title, description, summary, url,
			layoutUuid, height, width, priority, sync);
	}

	@Override
	public AssetEntry updateEntry(
			String className, long classPK, Date publishDate, boolean visible)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.findByC_C(
			classNameId, classPK);

		entry.setPublishDate(publishDate);

		updateVisible(entry, visible);

		assetEntryPersistence.update(entry);

		return entry;
	}

	@Override
	public AssetEntry updateEntry(
			String className, long classPK, Date publishDate,
			Date expirationDate, boolean visible)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.findByC_C(
			classNameId, classPK);

		entry.setExpirationDate(expirationDate);
		entry.setPublishDate(publishDate);

		updateVisible(entry, visible);

		assetEntryPersistence.update(entry);

		return entry;
	}

	@Override
	public AssetEntry updateVisible(
			String className, long classPK, boolean visible)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.findByC_C(
			classNameId, classPK);

		updateVisible(entry, visible);

		assetEntryPersistence.update(entry);

		return entry;
	}

	@Override
	public void validate(
			long groupId, String className, long[] categoryIds,
			String[] tagNames)
		throws PortalException, SystemException {

		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		AssetEntryValidator validator = (AssetEntryValidator)InstancePool.get(
			PropsValues.ASSET_ENTRY_VALIDATOR);

		validator.validate(groupId, className, categoryIds, tagNames);
	}

	protected String[] getClassNames(long companyId, String className) {
		if (Validator.isNotNull(className)) {
			return new String[] {className};
		}
		else {
			List<AssetRendererFactory> rendererFactories =
				AssetRendererFactoryRegistryUtil.getAssetRendererFactories(
					companyId);

			String[] classNames = new String[rendererFactories.size()];

			for (int i = 0; i < rendererFactories.size(); i++) {
				AssetRendererFactory rendererFactory = rendererFactories.get(i);

				classNames[i] = rendererFactory.getClassName();
			}

			return classNames;
		}
	}

	protected AssetEntry getEntry(Document document)
		throws PortalException, SystemException {

		String portletId = GetterUtil.getString(document.get(Field.PORTLET_ID));

		if (portletId.equals(PortletKeys.BLOGS)) {
			long entryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				BlogsEntry.class.getName());
			long classPK = entryId;

			return assetEntryPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.BOOKMARKS)) {
			long entryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				BookmarksEntry.class.getName());
			long classPK = entryId;

			return assetEntryPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.DOCUMENT_LIBRARY)) {
			long fileEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				DLFileEntry.class.getName());
			long classPK = fileEntryId;

			return assetEntryPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.JOURNAL)) {
			long groupId = GetterUtil.getLong(document.get(Field.GROUP_ID));
			String articleId = document.get("articleId");
			//double version = GetterUtil.getDouble(document.get("version"));

			long articleResourcePrimKey =
				journalArticleResourceLocalService.getArticleResourcePrimKey(
					groupId, articleId);

			long classNameId = PortalUtil.getClassNameId(
				JournalArticle.class.getName());
			long classPK = articleResourcePrimKey;

			return assetEntryPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.MESSAGE_BOARDS)) {
			long messageId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			long classNameId = PortalUtil.getClassNameId(
				MBMessage.class.getName());
			long classPK = messageId;

			return assetEntryPersistence.findByC_C(classNameId, classPK);
		}
		else if (portletId.equals(PortletKeys.WIKI)) {
			long nodeId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));
			String title = document.get(Field.TITLE);

			long pageResourcePrimKey =
				wikiPageResourceLocalService.getPageResourcePrimKey(
					nodeId, title);

			long classNameId = PortalUtil.getClassNameId(
				WikiPage.class.getName());
			long classPK = pageResourcePrimKey;

			return assetEntryPersistence.findByC_C(classNameId, classPK);
		}

		return null;
	}

	protected void updateVisible(AssetEntry entry, boolean visible)
		throws PortalException, SystemException {

		if (visible == entry.isVisible()) {
			return;
		}

		List<AssetTag> tags = assetEntryPersistence.getAssetTags(
			entry.getEntryId());

		if (visible) {
			for (AssetTag tag : tags) {
				assetTagLocalService.incrementAssetCount(
					tag.getTagId(), entry.getClassNameId());
			}
		}
		else {
			for (AssetTag tag : tags) {
				assetTagLocalService.decrementAssetCount(
					tag.getTagId(), entry.getClassNameId());
			}
		}

		entry.setVisible(visible);
	}

}