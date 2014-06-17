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

package com.liferay.portlet.bookmarks.service.impl;

import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TreePathUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.SubscriptionSender;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.bookmarks.BookmarksSettings;
import com.liferay.portlet.bookmarks.EntryURLException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.model.impl.BookmarksEntryModelImpl;
import com.liferay.portlet.bookmarks.model.impl.BookmarksFolderModelImpl;
import com.liferay.portlet.bookmarks.service.base.BookmarksEntryLocalServiceBaseImpl;
import com.liferay.portlet.bookmarks.social.BookmarksActivityKeys;
import com.liferay.portlet.bookmarks.util.comparator.EntryModifiedDateComparator;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashVersion;

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

		User user = userPersistence.findByPrimaryKey(userId);

		if (Validator.isNull(name)) {
			name = url;
		}

		Date now = new Date();

		validate(url);

		long entryId = counterLocalService.increment();

		BookmarksEntry entry = bookmarksEntryPersistence.create(entryId);

		entry.setUuid(serviceContext.getUuid());
		entry.setGroupId(groupId);
		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(serviceContext.getCreateDate(now));
		entry.setModifiedDate(serviceContext.getModifiedDate(now));
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
			serviceContext.getAssetLinkEntryIds());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", entry.getName());

		socialActivityLocalService.addActivity(
			userId, groupId, BookmarksEntry.class.getName(), entryId,
			BookmarksActivityKeys.ADD_ENTRY, extraDataJSONObject.toString(), 0);

		// Subscriptions

		notifySubscribers(entry, serviceContext);

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
		OrderByComparator orderByComparator) {

		return bookmarksEntryPersistence.findByG_F_S(
			groupId, folderId, status, start, end, orderByComparator);
	}

	@Override
	public List<BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end,
		OrderByComparator orderByComparator) {

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

		OrderByComparator orderByComparator = new EntryModifiedDateComparator();

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

		if (entry.isInTrashExplicitly()) {
			restoreEntryFromTrash(userId, entryId);
		}
		else {

			// Entry

			TrashEntry trashEntry = entry.getTrashEntry();

			TrashVersion trashVersion =
				trashVersionLocalService.fetchVersion(
					trashEntry.getEntryId(), BookmarksEntry.class.getName(),
					entryId);

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
	public void rebuildTree(long companyId) {
		bookmarksFolderLocalService.rebuildTree(companyId);

		Session session = bookmarksEntryPersistence.openSession();

		try {
			TreePathUtil.rebuildTree(
				session, companyId, BookmarksEntryModelImpl.TABLE_NAME,
				BookmarksFolderModelImpl.TABLE_NAME, "folderId", true);
		}
		finally {
			bookmarksEntryPersistence.closeSession(session);

			bookmarksEntryPersistence.clearCache();
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BookmarksEntry restoreEntryFromTrash(long userId, long entryId)
		throws PortalException {

		BookmarksEntry entry = bookmarksEntryPersistence.findByPrimaryKey(
			entryId);

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

		Indexer indexer = IndexerRegistryUtil.getIndexer(
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
			String[] assetTagNames, long[] assetLinkEntryIds)
		throws PortalException {

		AssetEntry assetEntry = assetEntryLocalService.updateEntry(
			userId, entry.getGroupId(), entry.getCreateDate(),
			entry.getModifiedDate(), BookmarksEntry.class.getName(),
			entry.getEntryId(), entry.getUuid(), 0, assetCategoryIds,
			assetTagNames, true, null, null, null, ContentTypes.TEXT_PLAIN,
			entry.getName(), entry.getDescription(), null, entry.getUrl(), null,
			0, 0, null, false);

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

		entry.setModifiedDate(serviceContext.getModifiedDate(null));
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
			serviceContext.getAssetLinkEntryIds());

		// Social

		JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

		extraDataJSONObject.put("title", entry.getName());

		socialActivityLocalService.addActivity(
			userId, entry.getGroupId(), BookmarksEntry.class.getName(), entryId,
			BookmarksActivityKeys.UPDATE_ENTRY, extraDataJSONObject.toString(),
			0);

		// Subscriptions

		notifySubscribers(entry, serviceContext);

		return entry;
	}

	@Override
	public BookmarksEntry updateStatus(
			long userId, BookmarksEntry entry, int status)
		throws PortalException {

		// Entry

		User user = userPersistence.findByPrimaryKey(userId);

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
			BookmarksEntry entry, ServiceContext serviceContext)
		throws PortalException {

		String layoutFullURL = serviceContext.getLayoutFullURL();

		if (!entry.isApproved() || Validator.isNull(layoutFullURL)) {
			return;
		}

		BookmarksSettings bookmarksSettings = BookmarksSettings.getInstance(
			entry.getGroupId());

		if ((serviceContext.isCommandAdd() &&
			 !bookmarksSettings.isEmailEntryAddedEnabled()) ||
			(serviceContext.isCommandUpdate() &&
			 !bookmarksSettings.isEmailEntryUpdatedEnabled())) {

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
		String entryURL =
			layoutFullURL + Portal.FRIENDLY_URL_SEPARATOR + "bookmarks" +
				StringPool.SLASH + entry.getEntryId();

		String fromName = bookmarksSettings.getEmailFromName();
		String fromAddress = bookmarksSettings.getEmailFromAddress();

		LocalizedValuesMap subjectLocalizedValuesMap = null;
		LocalizedValuesMap bodyLocalizedValuesMap = null;

		if (serviceContext.isCommandUpdate()) {
			subjectLocalizedValuesMap =
				bookmarksSettings.getEmailEntryUpdatedSubject();
			bodyLocalizedValuesMap =
				bookmarksSettings.getEmailEntryUpdatedBody();
		}
		else {
			subjectLocalizedValuesMap =
				bookmarksSettings.getEmailEntryAddedSubject();
			bodyLocalizedValuesMap = bookmarksSettings.getEmailEntryAddedBody();
		}

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setClassName(entry.getModelClassName());
		subscriptionSender.setClassPK(entry.getEntryId());
		subscriptionSender.setCompanyId(entry.getCompanyId());
		subscriptionSender.setContextAttributes(
			"[$BOOKMARKS_ENTRY_STATUS_BY_USER_NAME$]", statusByUserName,
			"[$BOOKMARKS_ENTRY_URL$]", entryURL);
		subscriptionSender.setContextUserPrefix("BOOKMARKS_ENTRY");
		subscriptionSender.setEntryTitle(entryTitle);
		subscriptionSender.setEntryURL(entryURL);
		subscriptionSender.setFrom(fromAddress, fromName);
		subscriptionSender.setHtmlFormat(true);
		subscriptionSender.setLocalizedBodyMap(bodyLocalizedValuesMap);
		subscriptionSender.setLocalizedSubjectMap(subjectLocalizedValuesMap);
		subscriptionSender.setMailId("bookmarks_entry", entry.getEntryId());

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (serviceContext.isCommandUpdate()) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		subscriptionSender.setNotificationType(notificationType);

		subscriptionSender.setPortletId(PortletKeys.BOOKMARKS);
		subscriptionSender.setReplyToAddress(fromAddress);
		subscriptionSender.setScopeGroupId(entry.getGroupId());
		subscriptionSender.setServiceContext(serviceContext);
		subscriptionSender.setUserId(entry.getUserId());

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

	private static Log _log = LogFactoryUtil.getLog(
		BookmarksEntryLocalServiceImpl.class);

}