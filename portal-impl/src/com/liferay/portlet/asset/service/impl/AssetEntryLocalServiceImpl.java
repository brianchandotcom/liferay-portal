/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.lar.ImportExportThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.NoSuchEntryException;
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetEntryDisplay;
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
import com.liferay.portlet.wiki.model.WikiPage;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Zsolt Berentey
 */
public class AssetEntryLocalServiceImpl extends AssetEntryLocalServiceBaseImpl {

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

		socialEquityLogLocalService.deactivateEquityLogs(entry.getEntryId());
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		AssetEntry entry = assetEntryPersistence.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	public void deleteEntry(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (entry != null) {
			deleteEntry(entry);
		}
	}

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

	public List<AssetEntry> getCompanyEntries(
			long companyId, int start, int end)
		throws SystemException {

		return assetEntryPersistence.findByCompanyId(companyId, start, end);
	}

	public int getCompanyEntriesCount(long companyId) throws SystemException {
		return assetEntryPersistence.countByCompanyId(companyId);
	}

	public AssetEntryDisplay[] getCompanyEntryDisplays(
			long companyId, int start, int end, String languageId)
		throws SystemException {

		return getEntryDisplays(
			getCompanyEntries(companyId, start, end), languageId);
	}

	public List<AssetEntry> getEntries(AssetEntryQuery entryQuery)
		throws SystemException {

		return assetEntryFinder.findEntries(entryQuery);
	}

	public int getEntriesCount(AssetEntryQuery entryQuery)
		throws SystemException {

		return assetEntryFinder.countEntries(entryQuery);
	}

	public AssetEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return assetEntryPersistence.findByPrimaryKey(entryId);
	}

	public AssetEntry getEntry(long groupId, String classUuid)
		throws PortalException, SystemException {

		return assetEntryPersistence.findByG_CU(groupId, classUuid);
	}

	public AssetEntry getEntry(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return assetEntryPersistence.findByC_C(classNameId, classPK);
	}

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

	public List<AssetEntry> getTopViewedEntries(
			String className, boolean asc, int start, int end)
		throws SystemException {

		return getTopViewedEntries(new String[] {className}, asc, start, end);
	}

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

	@BufferedIncrement(incrementClass = NumberIncrement.class)
	public void incrementViewCounter(
			long userId, String className, long classPK, int increment)
		throws PortalException, SystemException {

		if (!PropsValues.ASSET_ENTRY_INCREMENT_VIEW_COUNTER_ENABLED) {
			return;
		}

		if (classPK <= 0) {
			return;
		}

		long classNameId = PortalUtil.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (entry != null) {
			entry.setViewCount(entry.getViewCount() + increment);

			assetEntryPersistence.update(entry, false);

			// Social

			if ((userId > 0) && (entry.getUserId() != userId)) {
				socialEquityLogLocalService.addEquityLogs(
					userId, entry.getEntryId(), ActionKeys.VIEW,
					StringPool.BLANK);
			}
		}
	}

	public void reindex(List<AssetEntry> entries) throws PortalException {
		for (AssetEntry entry : entries) {
			String className = PortalUtil.getClassName(entry.getClassNameId());

			Indexer indexer = IndexerRegistryUtil.getIndexer(className);

			indexer.reindex(className, entry.getClassPK());
		}
	}

	public Hits search(
			long companyId, long[] groupIds, String className, String keywords,
			int start, int end)
		throws SystemException {

		return search(
			companyId, groupIds, 0, className, keywords, start, end);
	}

	public Hits search(
			long companyId, long[] groupIds, long userId, String className,
			String keywords, int start, int end)
		throws SystemException {

		try {
			SearchContext searchContext = new SearchContext();

			Facet assetEntriesFacet = new AssetEntriesFacet(searchContext);

			assetEntriesFacet.setStatic(true);

			searchContext.addFacet(assetEntriesFacet);

			Facet scopeFacet = new ScopeFacet(searchContext);

			scopeFacet.setStatic(true);

			searchContext.addFacet(scopeFacet);

			searchContext.setCompanyId(companyId);
			searchContext.setEnd(end);
			searchContext.setEntryClassNames(getClassNames(className));
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

	public Hits search(
			long companyId, long[] groupIds, long userId, String className,
			String userName, String title, String description,
			String assetCategoryIds, String assetTagNames, boolean andSearch,
			int start, int end)
		throws SystemException {

		try {
			Map<String, Serializable> attributes =
				new HashMap<String, Serializable>();

			attributes.put(Field.DESCRIPTION, description);
			attributes.put(Field.TITLE, title);
			attributes.put(Field.USER_NAME, userName);

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
			searchContext.setAttributes(attributes);
			searchContext.setCompanyId(companyId);
			searchContext.setEnd(end);
			searchContext.setEntryClassNames(getClassNames(className));
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

	public AssetEntryDisplay[] searchEntryDisplays(
			long companyId, long[] groupIds, String className, String keywords,
			String languageId, int start, int end)
		throws SystemException {

		List<AssetEntry> entries = new ArrayList<AssetEntry>();

		Hits hits = search(
			companyId, groupIds, className, keywords, start, end);

		List<Document> hitsList = hits.toList();

		for (Document document : hitsList) {
			try {
				AssetEntry entry = getEntry(document);

				if (entry != null) {
					entries.add(entry);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}
		}

		return getEntryDisplays(entries, languageId);
	}

	public int searchEntryDisplaysCount(
			long companyId, long[] groupIds, String className, String keywords,
			String languageId)
		throws SystemException {

		Hits hits = search(
			companyId, groupIds, className, keywords, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		return hits.getLength();
	}

	public AssetEntry updateEntry(
			long userId, long groupId, String className, long classPK,
			long[] categoryIds, String[] tagNames)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		if (entry != null) {
			return updateEntry(
				userId, groupId, className, classPK, entry.getClassUuid(),
				categoryIds, tagNames, entry.getVisible(), entry.getStartDate(),
				entry.getEndDate(), entry.getPublishDate(),
				entry.getExpirationDate(), entry.getMimeType(),
				entry.getTitle(), entry.getDescription(), entry.getSummary(),
				entry.getUrl(), entry.getLayoutUuid(), entry.getHeight(),
				entry.getWidth(), GetterUtil.getInteger(entry.getPriority()),
				false);
		}

		return updateEntry(
			userId, groupId, className, classPK, null, categoryIds, tagNames,
			true, null, null, null, null, null, null, null, null, null, null, 0,
			0, null, false);
	}

	public AssetEntry updateEntry(
			long userId, long groupId, String className, long classPK,
			String classUuid, long[] categoryIds, String[] tagNames,
			boolean visible, Date startDate, Date endDate, Date publishDate,
			Date expirationDate, String mimeType, String title,
			String description, String summary, String url, String layoutUuid,
			int height, int width, Integer priority, boolean sync)
		throws PortalException, SystemException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);

		title = StringUtil.shorten(title, 300, StringPool.BLANK);
		Date now = new Date();

		validate(groupId, className, categoryIds, tagNames);

		AssetEntry entry = assetEntryPersistence.fetchByC_C(
			classNameId, classPK);

		boolean oldVisible = false;

		if (entry != null) {
			oldVisible = entry.isVisible();
		}

		if (entry == null) {
			long entryId = counterLocalService.increment();

			entry = assetEntryPersistence.create(entryId);

			entry.setCompanyId(user.getCompanyId());
			entry.setUserId(user.getUserId());
			entry.setUserName(user.getFullName());
			entry.setCreateDate(now);
			entry.setClassNameId(classNameId);
			entry.setClassPK(classPK);
			entry.setClassUuid(classUuid);
			entry.setVisible(visible);
			entry.setPublishDate(publishDate);
			entry.setExpirationDate(expirationDate);

			if (priority == null) {
				entry.setPriority(0);
			}

			entry.setViewCount(0);
		}

		entry.setGroupId(groupId);
		entry.setModifiedDate(now);
		entry.setVisible(visible);
		entry.setStartDate(startDate);
		entry.setEndDate(endDate);
		entry.setPublishDate(publishDate);
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
			long parentGroupId = PortalUtil.getParentGroupId(groupId);

			List<AssetTag> tags = new ArrayList<AssetTag>(tagNames.length);

			for (String tagName : tagNames) {
				AssetTag tag = null;

				try {
					tag = assetTagLocalService.getTag(parentGroupId, tagName);
				}
				catch (NoSuchTagException nste) {
					ServiceContext serviceContext = new ServiceContext();

					serviceContext.setAddGroupPermissions(true);
					serviceContext.setAddGuestPermissions(true);
					serviceContext.setScopeGroupId(parentGroupId);

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

				assetEntryPersistence.updateImpl(entry, false);

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

		assetEntryPersistence.update(entry, false);

		// Synchronize

		if (!sync) {
			return entry;
		}

		if (className.equals(BlogsEntry.class.getName())) {
			BlogsEntry blogsEntry = blogsEntryPersistence.findByPrimaryKey(
				classPK);

			blogsEntry.setTitle(title);

			blogsEntryPersistence.update(blogsEntry, false);
		}
		else if (className.equals(BookmarksEntry.class.getName())) {
			BookmarksEntry bookmarksEntry =
				bookmarksEntryPersistence.findByPrimaryKey(classPK);

			bookmarksEntry.setName(title);
			bookmarksEntry.setDescription(description);
			bookmarksEntry.setUrl(url);

			bookmarksEntryPersistence.update(bookmarksEntry, false);
		}
		else if (className.equals(DLFileEntry.class.getName())) {
			DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
				classPK);

			dlFileEntry.setTitle(title);
			dlFileEntry.setDescription(description);

			dlFileEntryPersistence.update(dlFileEntry, false);
		}
		else if (className.equals(JournalArticle.class.getName())) {
			JournalArticle journalArticle =
				journalArticlePersistence.findByPrimaryKey(classPK);

			journalArticle.setTitle(title);
			journalArticle.setDescription(description);

			journalArticlePersistence.update(journalArticle, false);
		}
		else if (className.equals(MBMessage.class.getName())) {
			MBMessage mbMessage = mbMessagePersistence.findByPrimaryKey(
				classPK);

			mbMessage.setSubject(title);

			mbMessagePersistence.update(mbMessage, false);
		}
		else if (className.equals(WikiPage.class.getName())) {
			WikiPage wikiPage = wikiPagePersistence.findByPrimaryKey(classPK);

			wikiPage.setTitle(title);

			wikiPagePersistence.update(wikiPage, false);
		}

		return entry;
	}

	public AssetEntry updateVisible(
			String className, long classPK, boolean visible)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		AssetEntry entry = assetEntryPersistence.findByC_C(
			classNameId, classPK);

		List<AssetTag> tags = assetEntryPersistence.getAssetTags(
			entry.getEntryId());

		if (visible && !entry.isVisible()) {
			for (AssetTag tag : tags) {
				assetTagLocalService.incrementAssetCount(
					tag.getTagId(), classNameId);
			}
		}
		else if (!visible && entry.isVisible()) {
			for (AssetTag tag : tags) {
				assetTagLocalService.decrementAssetCount(
					tag.getTagId(), classNameId);
			}
		}

		entry.setVisible(visible);

		assetEntryPersistence.update(entry, false);

		return entry;
	}

	public void validate(
			long groupId, String className, long[] categoryIds,
			String[] tagNames)
		throws PortalException, SystemException {

		if (ImportExportThreadLocal.isImportInProcess()) {
			return;
		}

		AssetEntryValidator validator = (AssetEntryValidator)InstancePool.get(
			PropsValues.ASSET_ENTRY_VALIDATOR);

		validator.validate(groupId, className, categoryIds, tagNames);
	}

	protected String[] getClassNames(String className) {
		if (Validator.isNotNull(className)) {
			return new String[] {className};
		}
		else {
			List<AssetRendererFactory> rendererFactories =
				AssetRendererFactoryRegistryUtil.getAssetRendererFactories();

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

	protected AssetEntryDisplay[] getEntryDisplays(
			List<AssetEntry> entries, String languageId)
		throws SystemException {

		AssetEntryDisplay[] entryDisplays =
			new AssetEntryDisplay[entries.size()];

		for (int i = 0; i < entries.size(); i++) {
			AssetEntry entry = entries.get(i);

			String className = PortalUtil.getClassName(entry.getClassNameId());
			String portletId = PortalUtil.getClassNamePortletId(className);
			String portletTitle = PortalUtil.getPortletTitle(
				portletId, languageId);

			List<AssetCategory> categories =
				assetEntryPersistence.getAssetCategories(entry.getEntryId());

			String categoryIdsString = ListUtil.toString(
				categories, AssetCategory.CATEGORY_ID_ACCESSOR,
				StringPool.COMMA);
			long[] categoryIds = StringUtil.split(
				categoryIdsString, StringPool.COMMA, 0L);

			List<AssetTag> tags = assetEntryPersistence.getAssetTags(
				entry.getEntryId());

			String tagNames = ListUtil.toString(
				tags, AssetTag.NAME_ACCESSOR, ", ");

			AssetEntryDisplay entryDisplay = new AssetEntryDisplay();

			entryDisplay.setEntryId(entry.getEntryId());
			entryDisplay.setCompanyId(entry.getCompanyId());
			entryDisplay.setUserId(entry.getUserId());
			entryDisplay.setUserName(entry.getUserName());
			entryDisplay.setCreateDate(entry.getCreateDate());
			entryDisplay.setModifiedDate(entry.getModifiedDate());
			entryDisplay.setClassNameId(entry.getClassNameId());
			entryDisplay.setClassName(className);
			entryDisplay.setClassPK(entry.getClassPK());
			entryDisplay.setPortletId(portletId);
			entryDisplay.setPortletTitle(portletTitle);
			entryDisplay.setStartDate(entry.getStartDate());
			entryDisplay.setEndDate(entry.getEndDate());
			entryDisplay.setPublishDate(entry.getPublishDate());
			entryDisplay.setExpirationDate(entry.getExpirationDate());
			entryDisplay.setMimeType(entry.getMimeType());
			entryDisplay.setTitle(entry.getTitle());
			entryDisplay.setDescription(entry.getDescription());
			entryDisplay.setSummary(entry.getSummary());
			entryDisplay.setUrl(entry.getUrl());
			entryDisplay.setHeight(entry.getHeight());
			entryDisplay.setWidth(entry.getWidth());
			entryDisplay.setPriority(entry.getPriority());
			entryDisplay.setViewCount(entry.getViewCount());
			entryDisplay.setCategoryIds(categoryIds);
			entryDisplay.setTagNames(tagNames);

			entryDisplays[i] = entryDisplay;
		}

		return entryDisplays;
	}

	private static Log _log = LogFactoryUtil.getLog(
		AssetEntryLocalServiceImpl.class);

}