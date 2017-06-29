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

package com.liferay.bookmarks.service.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLinkConstants;
import com.liferay.bookmarks.configuration.BookmarksGroupServiceOverriddenConfiguration;
import com.liferay.bookmarks.constants.BookmarksConstants;
import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.exception.EntryURLException;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.base.BookmarksEntryLocalServiceBaseImpl;
import com.liferay.bookmarks.service.permission.BookmarksResourcePermissionChecker;
import com.liferay.bookmarks.social.BookmarksActivityKeys;
import com.liferay.bookmarks.util.comparator.EntryModifiedDateComparator;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GroupSubscriptionCheckSubscriptionSender;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.trash.exception.RestoreEntryException;
import com.liferay.trash.exception.TrashEntryException;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.model.TrashVersion;
import com.liferay.trash.service.TrashEntryLocalService;
import com.liferay.trash.service.TrashVersionLocalService;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Levente Hudák
 */
public class BookmarksEntryLocalServiceImpl
	extends BookmarksEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BookmarksEntry addEntry(
			long userId, long groupId, long folderId, String name, String url,
			String description, ServiceContext serviceContext)
		throws PortalException {

		// Entry

		User user = userLocalService.getUser(userId);

		if (Validator.isNull(name)) {
			name = url;
		}

		validate(url);

		long entryId = counterLocalService.increment();

		BookmarksEntry entry = bookmarksEntryPersistence.create(entryId);

		entry.setUuid(serviceContext.getUuid());
		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setFolderId(folderId);
		entry.setTreePath(entry.buildTreePath());
		entry.setName(name);
		entry.setUrl(url);
		entry.setDescription(description);
		entry.setExpandoBridgeAttributes(serviceContext);

		bookmarksEntryPersistence.update(entry);

		// Resources

		resourceLocalService.addModelResources(entry, serviceContext);

		// Asset

		updateAsset(
			userId, entry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", entry.getName());

		socialActivityLocalService.addActivity(
			userId, groupId, BookmarksEntry.class.getName(), entryId,
			BookmarksActivityKeys.ADD_ENTRY, extraDataJSONObject.toString(), 0);

		// Subscriptions

		notifySubscribers(userId, entry, serviceContext);

		return entry;
	}

	@Override
	public void deleteEntries(long groupId, long folderId)
		throws PortalException {

		deleteEntries(groupId, folderId, true);
	}

	@Override
	public void deleteEntries(
			long groupId, long folderId, boolean includeTrashedEntries)
		throws PortalException {

		List<BookmarksEntry> entries = bookmarksEntryPersistence.findByG_F(
			groupId, folderId);

		for (BookmarksEntry entry : entries) {
			if (includeTrashedEntries || !entry.isInTrashExplicitly()) {
				bookmarksEntryLocalService.deleteEntry(entry);
			}
		}
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public BookmarksEntry deleteEntry(BookmarksEntry entry)
		throws PortalException {

		// Entry

		bookmarksEntryPersistence.remove(entry);

		// Resources

		resourceLocalService.deleteResource(
			entry, ResourceConstants.SCOPE_INDIVIDUAL);

		// Asset

		assetEntryLocalService.deleteEntry(
			BookmarksEntry.class.getName(), entry.getEntryId());

		// Expando

		expandoRowLocalService.deleteRows(entry.getEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			BookmarksEntry.class.getName(), entry.getEntryId());

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			entry.getCompanyId(), BookmarksEntry.class.getName(),
			entry.getEntryId());

		// Trash

		if (entry.isInTrashExplicitly()) {
			trashEntryLocalService.deleteEntry(
				BookmarksEntry.class.getName(), entry.getEntryId());
		}
		else {
			trashVersionLocalService.deleteTrashVersion(
				BookmarksEntry.class.getName(), entry.getEntryId());
		}

		return entry;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public BookmarksEntry deleteEntry(long entryId) throws PortalException {
		BookmarksEntry entry = bookmarksEntryPersistence.findByPrimaryKey(
			entryId);

		return bookmarksEntryLocalService.deleteEntry(entry);
	}

	@Override
	public List<BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end) {

		return getEntries(
			groupId, folderId, WorkflowConstants.STATUS_APPROVED, start, end);
	}

	@Override
	public List<BookmarksEntry> getEntries(
		long groupId, long folderId, int status, int start, int end) {

		return getEntries(groupId, folderId, status, start, end, null);
	}

	@Override
	public List<BookmarksEntry> getEntries(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return bookmarksEntryPersistence.findByG_F_S(
			groupId, folderId, status, start, end, orderByComparator);
	}

	@Override
	public List<BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end,
		OrderByComparator<BookmarksEntry> orderByComparator) {

		return getEntries(
			groupId, folderId, WorkflowConstants.STATUS_APPROVED, start, end,
			orderByComparator);
	}

	@Override
	public int getEntriesCount(long groupId, long folderId) {
		return getEntriesCount(
			groupId, folderId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getEntriesCount(long groupId, long folderId, int status) {
		return bookmarksEntryPersistence.countByG_F_S(
			groupId, folderId, status);
	}

	@Override
	public BookmarksEntry getEntry(long entryId) throws PortalException {
		return bookmarksEntryPersistence.findByPrimaryKey(entryId);
	}

	@Override
	public int getFoldersEntriesCount(long groupId, List<Long> folderIds) {
		return bookmarksEntryPersistence.countByG_F_S(
			groupId,
			ArrayUtil.toArray(folderIds.toArray(new Long[folderIds.size()])),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public List<BookmarksEntry> getGroupEntries(
		long groupId, int start, int end) {

		return bookmarksEntryPersistence.findByG_S(
			groupId, WorkflowConstants.STATUS_APPROVED, start, end,
			new EntryModifiedDateComparator());
	}

	@Override
	public List<BookmarksEntry> getGroupEntries(
		long groupId, long userId, int start, int end) {

		OrderByComparator<BookmarksEntry> orderByComparator =
			new EntryModifiedDateComparator();

		if (userId <= 0) {
			return bookmarksEntryPersistence.findByG_S(
				groupId, WorkflowConstants.STATUS_APPROVED, start, end,
				orderByComparator);
		}
		else {
			return bookmarksEntryPersistence.findByG_U_S(
				groupId, userId, WorkflowConstants.STATUS_APPROVED, start, end,
				orderByComparator);
		}
	}

	@Override
	public int getGroupEntriesCount(long groupId) {
		return bookmarksEntryPersistence.countByG_S(
			groupId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getGroupEntriesCount(long groupId, long userId) {
		if (userId <= 0) {
			return getGroupEntriesCount(groupId);
		}
		else {
			return bookmarksEntryPersistence.countByG_U_S(
				groupId, userId, WorkflowConstants.STATUS_APPROVED);
		}
	}

	@Override
	public List<BookmarksEntry> getNoAssetEntries() {
		return bookmarksEntryFinder.findByNoAssets();
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BookmarksEntry moveEntry(long entryId, long parentFolderId)
		throws PortalException {

		BookmarksEntry entry = getBookmarksEntry(entryId);

		entry.setFolderId(parentFolderId);
		entry.setTreePath(entry.buildTreePath());

		bookmarksEntryPersistence.update(entry);

		return entry;
	}

	@Override
	public BookmarksEntry moveEntryFromTrash(
			long userId, long entryId, long parentFolderId)
		throws PortalException {

		BookmarksEntry entry = getBookmarksEntry(entryId);

		if (!entry.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		if (entry.isInTrashExplicitly()) {
			restoreEntryFromTrash(userId, entryId);
		}
		else {

			// Entry

			TrashVersion trashVersion = trashVersionLocalService.fetchVersion(
				BookmarksEntry.class.getName(), entryId);

			int status = WorkflowConstants.STATUS_APPROVED;

			if (trashVersion != null) {
				status = trashVersion.getStatus();
			}

			updateStatus(userId, entry, status);

			// Trash

			if (trashVersion != null) {
				trashVersionLocalService.deleteTrashVersion(trashVersion);
			}
		}

		return bookmarksEntryLocalService.moveEntry(entryId, parentFolderId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BookmarksEntry moveEntryToTrash(long userId, BookmarksEntry entry)
		throws PortalException {

		if (entry.isInTrash()) {
			throw new TrashEntryException();
		}

		int oldStatus = entry.getStatus();

		entry = updateStatus(userId, entry, WorkflowConstants.STATUS_IN_TRASH);

		trashEntryLocalService.addTrashEntry(
			userId, entry.getGroupId(), BookmarksEntry.class.getName(),
			entry.getEntryId(), entry.getUuid(), null, oldStatus, null, null);

		return entry;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BookmarksEntry moveEntryToTrash(long userId, long entryId)
		throws PortalException {

		BookmarksEntry entry = getEntry(entryId);

		return moveEntryToTrash(userId, entry);
	}

	@Override
	public BookmarksEntry openEntry(long userId, BookmarksEntry entry) {
		entry.setVisits(entry.getVisits() + 1);

		bookmarksEntryPersistence.update(entry);

		assetEntryLocalService.incrementViewCounter(
			userId, BookmarksEntry.class.getName(), entry.getEntryId(), 1);

		return entry;
	}

	@Override
	public BookmarksEntry openEntry(long userId, long entryId)
		throws PortalException {

		BookmarksEntry entry = bookmarksEntryPersistence.findByPrimaryKey(
			entryId);

		return openEntry(userId, entry);
	}

	@Override
	public void rebuildTree(long companyId) throws PortalException {
		bookmarksFolderLocalService.rebuildTree(companyId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BookmarksEntry restoreEntryFromTrash(long userId, long entryId)
		throws PortalException {

		BookmarksEntry entry = bookmarksEntryPersistence.findByPrimaryKey(
			entryId);

		if (!entry.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			BookmarksEntry.class.getName(), entryId);

		entry = updateStatus(userId, entry, trashEntry.getStatus());

		trashEntryLocalService.deleteEntry(
			BookmarksEntry.class.getName(), entry.getEntryId());

		return entry;
	}

	@Override
	public Hits search(
			long groupId, long userId, long creatorUserId, int status,
			int start, int end)
		throws PortalException {

		Indexer<BookmarksEntry> indexer = IndexerRegistryUtil.getIndexer(
			BookmarksEntry.class.getName());

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(Field.STATUS, status);

		if (creatorUserId > 0) {
			searchContext.setAttribute(
				Field.USER_ID, String.valueOf(creatorUserId));
		}

		searchContext.setAttribute("paginationType", "none");

		Group group = groupLocalService.getGroup(groupId);

		searchContext.setCompanyId(group.getCompanyId());

		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setSorts(new Sort(Field.MODIFIED_DATE, true));
		searchContext.setStart(start);
		searchContext.setUserId(userId);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return indexer.search(searchContext);
	}

	@Override
	public void setTreePaths(
			final long folderId, final String treePath, final boolean reindex)
		throws PortalException {

		if (treePath == null) {
			throw new IllegalArgumentException("Tree path is null");
		}

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property folderIdProperty = PropertyFactoryUtil.forName(
						"folderId");

					dynamicQuery.add(folderIdProperty.eq(folderId));

					Property treePathProperty = PropertyFactoryUtil.forName(
						"treePath");

					dynamicQuery.add(
						RestrictionsFactoryUtil.or(
							treePathProperty.isNull(),
							treePathProperty.ne(treePath)));
				}

			});

		final Indexer<BookmarksEntry> indexer = IndexerRegistryUtil.getIndexer(
			BookmarksEntry.class);

		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<BookmarksEntry>() {

				@Override
				public void performAction(BookmarksEntry entry)
					throws PortalException {

					entry.setTreePath(treePath);

					updateBookmarksEntry(entry);

					if (!reindex) {
						return;
					}

					Document document = indexer.getDocument(entry);

					indexableActionableDynamicQuery.addDocuments(document);
				}

			});

		indexableActionableDynamicQuery.performActions();
	}

	@Override
	public void subscribeEntry(long userId, long entryId)
		throws PortalException {

		BookmarksEntry entry = bookmarksEntryPersistence.findByPrimaryKey(
			entryId);

		subscriptionLocalService.addSubscription(
			userId, entry.getGroupId(), BookmarksEntry.class.getName(),
			entryId);
	}

	@Override
	public void unsubscribeEntry(long userId, long entryId)
		throws PortalException {

		subscriptionLocalService.deleteSubscription(
			userId, BookmarksEntry.class.getName(), entryId);
	}

	@Override
	public void updateAsset(
			long userId, BookmarksEntry entry, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds, Double priority)
		throws PortalException {

		AssetEntry assetEntry = assetEntryLocalService.updateEntry(
			userId, entry.getGroupId(), entry.getCreateDate(),
			entry.getModifiedDate(), BookmarksEntry.class.getName(),
			entry.getEntryId(), entry.getUuid(), 0, assetCategoryIds,
			assetTagNames, true, true, null, null, entry.getCreateDate(), null,
			ContentTypes.TEXT_PLAIN, entry.getName(), entry.getDescription(),
			null, entry.getUrl(), null, 0, 0, priority);

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BookmarksEntry updateEntry(
			long userId, long entryId, long groupId, long folderId, String name,
			String url, String description, ServiceContext serviceContext)
		throws PortalException {

		// Entry

		BookmarksEntry entry = bookmarksEntryPersistence.findByPrimaryKey(
			entryId);

		if (Validator.isNull(name)) {
			name = url;
		}

		validate(url);

		entry.setFolderId(folderId);
		entry.setTreePath(entry.buildTreePath());
		entry.setName(name);
		entry.setUrl(url);
		entry.setDescription(description);
		entry.setExpandoBridgeAttributes(serviceContext);

		bookmarksEntryPersistence.update(entry);

		// Asset

		updateAsset(
			userId, entry, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", entry.getName());

		socialActivityLocalService.addActivity(
			userId, entry.getGroupId(), BookmarksEntry.class.getName(), entryId,
			BookmarksActivityKeys.UPDATE_ENTRY, extraDataJSONObject.toString(),
			0);

		// Subscriptions

		notifySubscribers(userId, entry, serviceContext);

		return entry;
	}

	@Override
	public BookmarksEntry updateStatus(
			long userId, BookmarksEntry entry, int status)
		throws PortalException {

		// Entry

		User user = userLocalService.getUser(userId);

		entry.setStatus(status);
		entry.setStatusByUserId(userId);
		entry.setStatusByUserName(user.getScreenName());
		entry.setStatusDate(new Date());

		bookmarksEntryPersistence.update(entry);

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", entry.getName());

		if (status == WorkflowConstants.STATUS_APPROVED) {

			// Asset

			assetEntryLocalService.updateVisible(
				BookmarksEntry.class.getName(), entry.getEntryId(), true);

			// Social

			socialActivityLocalService.addActivity(
				userId, entry.getGroupId(), BookmarksEntry.class.getName(),
				entry.getEntryId(),
				SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
				extraDataJSONObject.toString(), 0);
		}
		else if (status == WorkflowConstants.STATUS_IN_TRASH) {

			// Asset

			assetEntryLocalService.updateVisible(
				BookmarksEntry.class.getName(), entry.getEntryId(), false);

			// Social

			socialActivityLocalService.addActivity(
				userId, entry.getGroupId(), BookmarksEntry.class.getName(),
				entry.getEntryId(), SocialActivityConstants.TYPE_MOVE_TO_TRASH,
				extraDataJSONObject.toString(), 0);
		}

		return entry;
	}

	protected long getFolder(BookmarksEntry entry, long folderId) {
		if ((entry.getFolderId() != folderId) &&
			(folderId != BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			BookmarksFolder newFolder =
				bookmarksFolderPersistence.fetchByPrimaryKey(folderId);

			if ((newFolder == null) ||
				(entry.getGroupId() != newFolder.getGroupId())) {

				folderId = entry.getFolderId();
			}
		}

		return folderId;
	}

	protected void notifySubscribers(
			long userId, BookmarksEntry entry, ServiceContext serviceContext)
		throws PortalException {

		String layoutFullURL = serviceContext.getLayoutFullURL();

		if (!entry.isApproved() || Validator.isNull(layoutFullURL)) {
			return;
		}

		BookmarksGroupServiceOverriddenConfiguration
			bookmarksGroupServiceOverriddenConfiguration =
				configurationProvider.getConfiguration(
					BookmarksGroupServiceOverriddenConfiguration.class,
					new GroupServiceSettingsLocator(
						entry.getGroupId(), BookmarksConstants.SERVICE_NAME));

		if ((serviceContext.isCommandAdd() &&
			 !bookmarksGroupServiceOverriddenConfiguration.
				 emailEntryAddedEnabled()) ||
			(serviceContext.isCommandUpdate() &&
			 !bookmarksGroupServiceOverriddenConfiguration.
				 emailEntryUpdatedEnabled())) {

			return;
		}

		String statusByUserName = StringPool.BLANK;

		try {
			User user = userLocalService.getUserById(
				serviceContext.getGuestOrUserId());

			statusByUserName = user.getFullName();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		String entryTitle = entry.getName();

		StringBundler sb = new StringBundler(7);

		sb.append(layoutFullURL);
		sb.append(Portal.FRIENDLY_URL_SEPARATOR);
		sb.append("bookmarks");
		sb.append(StringPool.SLASH);
		sb.append("folder");
		sb.append(StringPool.SLASH);
		sb.append(entry.getFolderId());

		String entryURL = sb.toString();

		String fromName =
			bookmarksGroupServiceOverriddenConfiguration.emailFromName();
		String fromAddress =
			bookmarksGroupServiceOverriddenConfiguration.emailFromAddress();

		LocalizedValuesMap subjectLocalizedValuesMap = null;
		LocalizedValuesMap bodyLocalizedValuesMap = null;

		if (serviceContext.isCommandUpdate()) {
			subjectLocalizedValuesMap =
				bookmarksGroupServiceOverriddenConfiguration.
					emailEntryUpdatedSubject();
			bodyLocalizedValuesMap =
				bookmarksGroupServiceOverriddenConfiguration.
					emailEntryUpdatedBody();
		}
		else {
			subjectLocalizedValuesMap =
				bookmarksGroupServiceOverriddenConfiguration.
					emailEntryAddedSubject();
			bodyLocalizedValuesMap =
				bookmarksGroupServiceOverriddenConfiguration.
					emailEntryAddedBody();
		}

		SubscriptionSender subscriptionSender =
			new GroupSubscriptionCheckSubscriptionSender(
				BookmarksResourcePermissionChecker.RESOURCE_NAME);

		subscriptionSender.setClassName(entry.getModelClassName());
		subscriptionSender.setClassPK(entry.getEntryId());
		subscriptionSender.setCompanyId(entry.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$BOOKMARKS_ENTRY_STATUS_BY_USER_NAME$]", statusByUserName,
			"[$BOOKMARKS_ENTRY_URL$]", entryURL);
		subscriptionSender.setContextCreatorUserPrefix("BOOKMARKS_ENTRY");
		subscriptionSender.setCreatorUserId(entry.getUserId());
		subscriptionSender.setCurrentUserId(userId);
		subscriptionSender.setEntryTitle(entryTitle);
		subscriptionSender.setEntryURL(entryURL);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);

		if (bodyLocalizedValuesMap != null) {
			subscriptionSender.setLocalizedBodyMap(
				LocalizationUtil.getMap(bodyLocalizedValuesMap));
		}

		if (subjectLocalizedValuesMap != null) {
			subscriptionSender.setLocalizedSubjectMap(
				LocalizationUtil.getMap(subjectLocalizedValuesMap));
		}

		subscriptionSender.setMailId("bookmarks_entry", entry.getEntryId());

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (serviceContext.isCommandUpdate()) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		subscriptionSender.setNotificationType(notificationType);

		subscriptionSender.setPortletId(BookmarksPortletKeys.BOOKMARKS);
		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setScopeGroupId(entry.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);

		BookmarksFolder folder = entry.getFolder();

		if (folder != null) {
			subscriptionSender.addPersistedSubscribers(
				BookmarksFolder.class.getName(), folder.getFolderId());

			for (Long ancestorFolderId : folder.getAncestorFolderIds()) {
				subscriptionSender.addPersistedSubscribers(
					BookmarksFolder.class.getName(), ancestorFolderId);
			}
		}

		subscriptionSender.addPersistedSubscribers(
			BookmarksFolder.class.getName(), entry.getGroupId());

		subscriptionSender.addPersistedSubscribers(
			BookmarksEntry.class.getName(), entry.getEntryId());

		subscriptionSender.flushNotificationsAsync();
	}

	protected void validate(String url) throws PortalException {
		if (!Validator.isUrl(url)) {
			throw new EntryURLException();
		}
	}

	@ServiceReference(type = ConfigurationProvider.class)
	protected ConfigurationProvider configurationProvider;

	@ServiceReference(type = SubscriptionLocalService.class)
	protected SubscriptionLocalService subscriptionLocalService;

	@ServiceReference(type = TrashEntryLocalService.class)
	protected TrashEntryLocalService trashEntryLocalService;

	@ServiceReference(type = TrashVersionLocalService.class)
	protected TrashVersionLocalService trashVersionLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		BookmarksEntryLocalServiceImpl.class);

}