/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.bookmarks.FolderNameException;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.base.BookmarksFolderLocalServiceBaseImpl;
import com.liferay.portlet.trash.model.TrashEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class BookmarksFolderLocalServiceImpl
	extends BookmarksFolderLocalServiceBaseImpl {

	public BookmarksFolder addFolder(
			long userId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Folder

		User user = userPersistence.findByPrimaryKey(userId);
		long groupId = serviceContext.getScopeGroupId();
		parentFolderId = getParentFolderId(groupId, parentFolderId);
		Date now = new Date();

		validate(name);

		long folderId = counterLocalService.increment();

		BookmarksFolder folder = bookmarksFolderPersistence.create(folderId);

		folder.setUuid(serviceContext.getUuid());
		folder.setGroupId(groupId);
		folder.setCompanyId(user.getCompanyId());
		folder.setUserId(user.getUserId());
		folder.setUserName(user.getFullName());
		folder.setCreateDate(serviceContext.getCreateDate(now));
		folder.setModifiedDate(serviceContext.getModifiedDate(now));
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		bookmarksFolderPersistence.update(folder);

		// Resources

		resourceLocalService.addModelResources(folder, serviceContext);

		return folder;
	}

	@Indexable(type = IndexableType.DELETE)
	public BookmarksFolder deleteFolder(BookmarksFolder folder)
		throws PortalException, SystemException {

		return deleteFolder(folder, true);
	}

	@Indexable(type = IndexableType.DELETE)
	public BookmarksFolder deleteFolder(
			BookmarksFolder folder, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		// Folders

		List<BookmarksFolder> folders = bookmarksFolderPersistence.findByG_P_S(
			folder.getGroupId(), folder.getFolderId(),
			WorkflowConstants.STATUS_ANY);

		for (BookmarksFolder curFolder : folders) {
			if (includeTrashedEntries || !curFolder.isInTrash()) {
				deleteFolder(curFolder);
			}
		}

		// Folder

		bookmarksFolderPersistence.remove(folder);

		// Resources

		resourceLocalService.deleteResource(
			folder, ResourceConstants.SCOPE_INDIVIDUAL);

		// Entries

		bookmarksEntryLocalService.deleteEntries(
			folder.getGroupId(), folder.getFolderId(), includeTrashedEntries);

		// Expando

		expandoValueLocalService.deleteValues(
			BookmarksFolder.class.getName(), folder.getFolderId());

		// Subscriptions

		subscriptionLocalService.deleteSubscriptions(
			folder.getCompanyId(), BookmarksFolder.class.getName(),
			folder.getFolderId());

		// Trash

		trashEntryLocalService.deleteEntry(
			BookmarksFolder.class.getName(), folder.getFolderId());

		return folder;
	}

	@Indexable(type = IndexableType.DELETE)
	public BookmarksFolder deleteFolder(long folderId)
		throws PortalException, SystemException {

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		return deleteFolder(folder);
	}

	@Indexable(type = IndexableType.DELETE)
	public BookmarksFolder deleteFolder(
			long folderId, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		BookmarksFolder folder = bookmarksFolderLocalService.getFolder(
			folderId);

		return deleteFolder(folder, includeTrashedEntries);
	}

	public void deleteFolders(long groupId)
		throws PortalException, SystemException {

		List<BookmarksFolder> folders = bookmarksFolderPersistence.findByG_P(
			groupId, BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (BookmarksFolder folder : folders) {
			bookmarksFolderLocalService.deleteFolder(folder);
		}
	}

	public List<BookmarksFolder> getCompanyFolders(
			long companyId, int start, int end)
		throws SystemException {

		return bookmarksFolderPersistence.findByCompanyId(
			companyId, start, end);
	}

	public int getCompanyFoldersCount(long companyId) throws SystemException {
		return bookmarksFolderPersistence.countByCompanyId(companyId);
	}

	public BookmarksFolder getFolder(long folderId)
		throws PortalException, SystemException {

		return bookmarksFolderPersistence.findByPrimaryKey(folderId);
	}

	public List<BookmarksFolder> getFolders(long groupId)
		throws SystemException {

		return bookmarksFolderPersistence.findByGroupId(groupId);
	}

	public List<BookmarksFolder> getFolders(long groupId, long parentFolderId)
		throws SystemException {

		return bookmarksFolderPersistence.findByG_P(groupId, parentFolderId);
	}

	public List<BookmarksFolder> getFolders(
			long groupId, long parentFolderId, int start, int end)
		throws SystemException {

		return getFolders(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED, start,
			end);
	}

	public List<BookmarksFolder> getFolders(
			long groupId, long parentFolderId, int status, int start, int end)
		throws SystemException {

		return bookmarksFolderPersistence.findByG_P_S(
			groupId, parentFolderId, status, start, end);
	}

	public List<Object> getFoldersAndEntries(long groupId, long folderId)
		throws SystemException {

		return getFoldersAndEntries(
			groupId, folderId, WorkflowConstants.STATUS_ANY);
	}

	public List<Object> getFoldersAndEntries(
			long groupId, long folderId, int status)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return bookmarksFolderFinder.findF_E_ByG_F(
			groupId, folderId, queryDefinition);
	}

	public List<Object> getFoldersAndEntries(
			long groupId, long folderId, int status, int start, int end)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(
			status, start, end, null);

		return bookmarksFolderFinder.findF_E_ByG_F(
			groupId, folderId, queryDefinition);
	}

	public int getFoldersAndEntriesCount(
			long groupId, long folderId, int status)
		throws SystemException {

		QueryDefinition queryDefinition = new QueryDefinition(status);

		return bookmarksFolderFinder.countF_E_ByG_F(
				groupId, folderId, queryDefinition);
	}

	public int getFoldersCount(long groupId, long parentFolderId)
		throws SystemException {

		return getFoldersCount(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED);
	}

	public int getFoldersCount(long groupId, long parentFolderId, int status)
		throws SystemException {

		return bookmarksFolderPersistence.countByG_P_S(
				groupId, parentFolderId, status);
	}

	public void getSubfolderIds(
			List<Long> folderIds, long groupId, long folderId)
		throws SystemException {

		List<BookmarksFolder> folders = bookmarksFolderPersistence.findByG_P(
			groupId, folderId);

		for (BookmarksFolder folder : folders) {
			folderIds.add(folder.getFolderId());

			getSubfolderIds(
				folderIds, folder.getGroupId(), folder.getFolderId());
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	public BookmarksFolder moveFolder(long folderId, long parentFolderId)
		throws PortalException, SystemException {

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		folder.setParentFolderId(parentFolderId);

		bookmarksFolderPersistence.update(folder);

		return folder;
	}

	public BookmarksFolder moveFolderFromTrash(
			long userId, long folderId, long parentFolderId)
		throws PortalException, SystemException {

		restoreFolderFromTrash(userId, folderId);

		return moveFolder(folderId, parentFolderId);
	}

	@Indexable(type = IndexableType.REINDEX)
	public void moveFolderToTrash(long userId, long folderId)
		throws PortalException, SystemException {

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		updateStatus(userId, folder, WorkflowConstants.STATUS_IN_TRASH);
	}

	@Indexable(type = IndexableType.REINDEX)
	public void restoreFolderFromTrash(long userId, long folderId)
		throws PortalException, SystemException {

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		TrashEntry trashEntry = trashEntryLocalService.getEntry(
			BookmarksFolder.class.getName(), folderId);

		updateStatus(userId, folder, trashEntry.getStatus());
	}

	public void subscribeFolder(long userId, long groupId, long folderId)
		throws PortalException, SystemException {

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			folderId = groupId;
		}

		subscriptionLocalService.addSubscription(
			userId, groupId, BookmarksFolder.class.getName(), folderId);
	}

	public void unsubscribeFolder(long userId, long groupId, long folderId)
		throws PortalException, SystemException {

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			folderId = groupId;
		}

		subscriptionLocalService.deleteSubscription(
			userId, BookmarksFolder.class.getName(), folderId);
	}

	@Indexable(type = IndexableType.REINDEX)
	public BookmarksFolder updateFolder(
			long folderId, long parentFolderId, String name, String description,
			boolean mergeWithParentFolder, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// Merge folders

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		parentFolderId = getParentFolderId(folder, parentFolderId);

		if (mergeWithParentFolder && (folderId != parentFolderId)) {
			mergeFolders(folder, parentFolderId);

			return folder;
		}

		// Folder

		validate(name);

		folder.setModifiedDate(serviceContext.getModifiedDate(null));
		folder.setParentFolderId(parentFolderId);
		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		bookmarksFolderPersistence.update(folder);

		return folder;
	}

	public BookmarksFolder updateStatus(
			long userId, BookmarksFolder folder, int status)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		int oldStatus = folder.getStatus();

		folder.setStatus(status);
		folder.setStatusByUserId(userId);
		folder.setStatusByUserName(user.getFullName());
		folder.setStatusDate(new Date());

		bookmarksFolderPersistence.update(folder);

		// Folders and entries

		List<Object> foldersAndEntries =
			bookmarksFolderLocalService.getFoldersAndEntries(
				folder.getGroupId(), folder.getFolderId());

		updateDependentStatus(foldersAndEntries, status);

		// Trash

		if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
			trashEntryLocalService.deleteEntry(
				BookmarksFolder.class.getName(), folder.getFolderId());
		}
		else if (status == WorkflowConstants.STATUS_IN_TRASH) {
			trashEntryLocalService.addTrashEntry(
				userId, folder.getGroupId(), BookmarksFolder.class.getName(),
				folder.getFolderId(), oldStatus, null, null);
		}

		// Index

		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			BookmarksFolder.class);

		indexer.reindex(folder);

		return folder;
	}

	protected long getParentFolderId(
			BookmarksFolder folder, long parentFolderId)
		throws SystemException {

		if (parentFolderId ==
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return parentFolderId;
		}

		if (folder.getFolderId() == parentFolderId) {
			return folder.getParentFolderId();
		}
		else {
			BookmarksFolder parentFolder =
				bookmarksFolderPersistence.fetchByPrimaryKey(parentFolderId);

			if ((parentFolder == null) ||
				(folder.getGroupId() != parentFolder.getGroupId())) {

				return folder.getParentFolderId();
			}

			List<Long> subfolderIds = new ArrayList<Long>();

			getSubfolderIds(
				subfolderIds, folder.getGroupId(), folder.getFolderId());

			if (subfolderIds.contains(parentFolderId)) {
				return folder.getParentFolderId();
			}

			return parentFolderId;
		}
	}

	protected long getParentFolderId(long groupId, long parentFolderId)
		throws SystemException {

		if (parentFolderId !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			BookmarksFolder parentFolder =
				bookmarksFolderPersistence.fetchByPrimaryKey(parentFolderId);

			if ((parentFolder == null) ||
				(groupId != parentFolder.getGroupId())) {

				parentFolderId =
					BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return parentFolderId;
	}

	protected void mergeFolders(BookmarksFolder fromFolder, long toFolderId)
		throws PortalException, SystemException {

		List<BookmarksFolder> folders = bookmarksFolderPersistence.findByG_P(
			fromFolder.getGroupId(), fromFolder.getFolderId());

		for (BookmarksFolder folder : folders) {
			mergeFolders(folder, toFolderId);
		}

		List<BookmarksEntry> entries = bookmarksEntryPersistence.findByG_F(
			fromFolder.getGroupId(), fromFolder.getFolderId());

		for (BookmarksEntry entry : entries) {
			entry.setFolderId(toFolderId);

			bookmarksEntryPersistence.update(entry);

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				BookmarksEntry.class);

			indexer.reindex(entry);
		}

		bookmarksFolderLocalService.deleteFolder(fromFolder);
	}

	protected void updateDependentStatus(
			List<Object> foldersAndEntries, int status)
		throws PortalException, SystemException {

		for (Object object : foldersAndEntries) {
			if (object instanceof BookmarksEntry) {
				BookmarksEntry entry = (BookmarksEntry)object;

				if (status == WorkflowConstants.STATUS_IN_TRASH) {

					// Asset

					if (entry.getStatus() ==
							WorkflowConstants.STATUS_APPROVED) {

						assetEntryLocalService.updateVisible(
							BookmarksEntry.class.getName(), entry.getEntryId(),
							false);
					}

					// Social

					socialActivityCounterLocalService.disableActivityCounters(
						BookmarksEntry.class.getName(), entry.getEntryId());

					if (entry.getStatus() == WorkflowConstants.STATUS_PENDING) {
						entry.setStatus(WorkflowConstants.STATUS_DRAFT);

						bookmarksEntryPersistence.update(entry);
					}
				}
				else {

					// Asset

					if (entry.getStatus() ==
							WorkflowConstants.STATUS_APPROVED) {

						assetEntryLocalService.updateVisible(
							BookmarksEntry.class.getName(), entry.getEntryId(),
							true);
					}

					// Social

					socialActivityCounterLocalService.enableActivityCounters(
						BookmarksEntry.class.getName(), entry.getEntryId());
				}

				// Indexer

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					BookmarksEntry.class);

				indexer.reindex(entry);
			}
			else if (object instanceof BookmarksFolder) {
				BookmarksFolder folder = (BookmarksFolder)object;

				if (folder.isInTrash()) {
					continue;
				}

				List<Object> curFoldersAndEntries =
					bookmarksFolderLocalService.getFoldersAndEntries(
						folder.getGroupId(), folder.getFolderId());

				updateDependentStatus(curFoldersAndEntries, status);
			}
		}
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name) || name.contains("\\\\") ||
			name.contains("//")) {

			throw new FolderNameException();
		}
	}

}