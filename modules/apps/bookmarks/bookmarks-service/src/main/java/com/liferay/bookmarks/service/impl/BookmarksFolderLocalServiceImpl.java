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
import com.liferay.bookmarks.exception.FolderNameException;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.bookmarks.service.base.BookmarksFolderLocalServiceBaseImpl;
import com.liferay.bookmarks.util.comparator.FolderIdComparator;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.tree.TreeModelTasksAdapter;
import com.liferay.portal.kernel.tree.TreePathUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.social.kernel.model.SocialActivityConstants;
import com.liferay.subscription.service.SubscriptionLocalService;
import com.liferay.trash.exception.RestoreEntryException;
import com.liferay.trash.exception.TrashEntryException;
import com.liferay.trash.model.TrashEntry;
import com.liferay.trash.model.TrashVersion;
import com.liferay.trash.service.TrashEntryLocalService;
import com.liferay.trash.service.TrashVersionLocalService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
@Component(
	property = "model.class.name=com.liferay.bookmarks.model.BookmarksFolder",
	service = AopService.class
)
public class BookmarksFolderLocalServiceImpl
	extends BookmarksFolderLocalServiceBaseImpl {

	@Override
	public BookmarksFolder addFolder(
			long userId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		// Folder

		User user = userLocalService.getUser(userId);

		long groupId = serviceContext.getScopeGroupId();

		parentFolderId = getParentFolderId(groupId, parentFolderId);

		validate(name);

		long folderId = counterLocalService.increment();

		BookmarksFolder folder = bookmarksFolderPersistence.create(folderId);

		folder.setUuid(serviceContext.getUuid());
		folder.setGroupId(groupId);
		folder.setCompanyId(user.getCompanyId());
		folder.setUserId(user.getUserId());
		folder.setUserName(user.getFullName());
		folder.setParentFolderId(parentFolderId);
		folder.setTreePath(folder.buildTreePath());
		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		bookmarksFolderPersistence.update(folder);

		// Resources

		resourceLocalService.addModelResources(folder, serviceContext);

		// Asset

		updateAsset(
			userId, folder, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		return folder;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public BookmarksFolder deleteFolder(BookmarksFolder folder)
		throws PortalException {

		return bookmarksFolderLocalService.deleteFolder(folder, true);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public BookmarksFolder deleteFolder(
			BookmarksFolder folder, boolean includeTrashedEntries)
		throws PortalException {

		// Folders

		List<BookmarksFolder> folders = bookmarksFolderPersistence.findByG_P(
			folder.getGroupId(), folder.getFolderId());

		for (BookmarksFolder curFolder : folders) {
			if (includeTrashedEntries || !curFolder.isInTrashExplicitly()) {
				bookmarksFolderLocalService.deleteFolder(curFolder);
			}
		}

		// Folder

		bookmarksFolderPersistence.remove(folder);

		// Resources

		resourceLocalService.deleteResource(
			folder, ResourceConstants.SCOPE_INDIVIDUAL);

		// Entries

		_blogsEntryLocalService.deleteEntries(
			folder.getGroupId(), folder.getFolderId(), includeTrashedEntries);

		// Asset

		assetEntryLocalService.deleteEntry(
			BookmarksFolder.class.getName(), folder.getFolderId());

		// Expando

		expandoRowLocalService.deleteRows(folder.getFolderId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
			BookmarksFolder.class.getName(), folder.getFolderId());

		// Subscriptions

		_subscriptionLocalService.deleteSubscriptions(
			folder.getCompanyId(), BookmarksFolder.class.getName(),
			folder.getFolderId());

		// Trash

		if (folder.isInTrashExplicitly()) {
			_trashEntryLocalService.deleteEntry(
				BookmarksFolder.class.getName(), folder.getFolderId());
		}
		else {
			_trashVersionLocalService.deleteTrashVersion(
				BookmarksFolder.class.getName(), folder.getFolderId());
		}

		return folder;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public BookmarksFolder deleteFolder(long folderId) throws PortalException {
		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		return bookmarksFolderLocalService.deleteFolder(folder);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public BookmarksFolder deleteFolder(
			long folderId, boolean includeTrashedEntries)
		throws PortalException {

		BookmarksFolder folder = bookmarksFolderLocalService.getFolder(
			folderId);

		return bookmarksFolderLocalService.deleteFolder(
			folder, includeTrashedEntries);
	}

	@Override
	public void deleteFolders(long groupId) throws PortalException {
		List<BookmarksFolder> folders =
			bookmarksFolderPersistence.findByGroupId(groupId);

		for (BookmarksFolder folder : folders) {
			bookmarksFolderLocalService.deleteFolder(folder);
		}
	}

	@Override
	public List<BookmarksFolder> getCompanyFolders(
		long companyId, int start, int end) {

		return bookmarksFolderPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public int getCompanyFoldersCount(long companyId) {
		return bookmarksFolderPersistence.countByCompanyId(companyId);
	}

	@Override
	public BookmarksFolder getFolder(long folderId) throws PortalException {
		return bookmarksFolderPersistence.findByPrimaryKey(folderId);
	}

	@Override
	public List<BookmarksFolder> getFolders(long groupId) {
		return bookmarksFolderPersistence.findByGroupId(groupId);
	}

	@Override
	public List<BookmarksFolder> getFolders(long groupId, long parentFolderId) {
		return bookmarksFolderPersistence.findByG_P(groupId, parentFolderId);
	}

	@Override
	public List<BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int start, int end) {

		return getFolders(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED, start,
			end);
	}

	@Override
	public List<BookmarksFolder> getFolders(
		long groupId, long parentFolderId, int status, int start, int end) {

		return bookmarksFolderPersistence.findByG_P_S(
			groupId, parentFolderId, status, start, end);
	}

	@Override
	public List<Object> getFoldersAndEntries(long groupId, long folderId) {
		return getFoldersAndEntries(
			groupId, folderId, WorkflowConstants.STATUS_ANY);
	}

	@Override
	public List<Object> getFoldersAndEntries(
		long groupId, long folderId, int status) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(status);

		return bookmarksFolderFinder.findF_E_ByG_F(
			groupId, folderId, queryDefinition);
	}

	@Override
	public List<Object> getFoldersAndEntries(
		long groupId, long folderId, int status, int start, int end) {

		return getFoldersAndEntries(
			groupId, folderId, status, start, end, null);
	}

	@Override
	public List<Object> getFoldersAndEntries(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator obc) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			status, start, end, obc);

		return bookmarksFolderFinder.findF_E_ByG_F(
			groupId, folderId, queryDefinition);
	}

	@Override
	public int getFoldersAndEntriesCount(
		long groupId, long folderId, int status) {

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(status);

		return bookmarksFolderFinder.countF_E_ByG_F(
			groupId, folderId, queryDefinition);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId) {
		return getFoldersCount(
			groupId, parentFolderId, WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public int getFoldersCount(long groupId, long parentFolderId, int status) {
		return bookmarksFolderPersistence.countByG_P_S(
			groupId, parentFolderId, status);
	}

	@Override
	public List<BookmarksFolder> getNoAssetFolders() {
		return bookmarksFolderFinder.findByNoAssets();
	}

	@Override
	public void getSubfolderIds(
		List<Long> folderIds, long groupId, long folderId) {

		List<BookmarksFolder> folders = bookmarksFolderPersistence.findByG_P(
			groupId, folderId);

		for (BookmarksFolder folder : folders) {
			folderIds.add(folder.getFolderId());

			getSubfolderIds(
				folderIds, folder.getGroupId(), folder.getFolderId());
		}
	}

	@Override
	public void mergeFolders(long folderId, long parentFolderId)
		throws PortalException {

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		parentFolderId = getParentFolderId(folder, parentFolderId);

		if (folderId != parentFolderId) {
			mergeFolders(folder, parentFolderId);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BookmarksFolder moveFolder(long folderId, long parentFolderId)
		throws PortalException {

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		if (folder.getParentFolderId() == parentFolderId) {
			return folder;
		}

		folder.setParentFolderId(parentFolderId);
		folder.setTreePath(folder.buildTreePath());

		bookmarksFolderPersistence.update(folder);

		rebuildTree(
			folder.getCompanyId(), folderId, folder.getTreePath(), true);

		return folder;
	}

	@Override
	public BookmarksFolder moveFolderFromTrash(
			long userId, long folderId, long parentFolderId)
		throws PortalException {

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		if (!folder.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		if (folder.isInTrashExplicitly()) {
			restoreFolderFromTrash(userId, folderId);
		}
		else {

			// Folder

			TrashVersion trashVersion = _trashVersionLocalService.fetchVersion(
				BookmarksFolder.class.getName(), folderId);

			int status = WorkflowConstants.STATUS_APPROVED;

			if (trashVersion != null) {
				status = trashVersion.getStatus();
			}

			updateStatus(userId, folder, status);

			// Trash

			if (trashVersion != null) {
				_trashVersionLocalService.deleteTrashVersion(trashVersion);
			}

			// Folders and entries

			List<Object> foldersAndEntries =
				bookmarksFolderLocalService.getFoldersAndEntries(
					folder.getGroupId(), folder.getFolderId(),
					WorkflowConstants.STATUS_IN_TRASH);

			restoreDependentsFromTrash(foldersAndEntries);
		}

		return bookmarksFolderLocalService.moveFolder(folderId, parentFolderId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BookmarksFolder moveFolderToTrash(long userId, long folderId)
		throws PortalException {

		// Folder

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		if (folder.isInTrash()) {
			throw new TrashEntryException();
		}

		int oldStatus = folder.getStatus();

		folder = updateStatus(
			userId, folder, WorkflowConstants.STATUS_IN_TRASH);

		// Trash

		TrashEntry trashEntry = _trashEntryLocalService.addTrashEntry(
			userId, folder.getGroupId(), BookmarksFolder.class.getName(),
			folder.getFolderId(), folder.getUuid(), null, oldStatus, null,
			null);

		// Folders and entries

		List<Object> foldersAndEntries =
			bookmarksFolderLocalService.getFoldersAndEntries(
				folder.getGroupId(), folder.getFolderId());

		moveDependentsToTrash(foldersAndEntries, trashEntry.getEntryId());

		// Social

		JSONObject extraDataJSONObject = JSONUtil.put(
			"title", folder.getName());

		socialActivityLocalService.addActivity(
			userId, folder.getGroupId(), BookmarksFolder.class.getName(),
			folder.getFolderId(), SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			extraDataJSONObject.toString(), 0);

		return folder;
	}

	@Override
	public void rebuildTree(long companyId) throws PortalException {
		rebuildTree(
			companyId, BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringPool.SLASH, false);
	}

	@Override
	public void rebuildTree(
			long companyId, long parentFolderId, String parentTreePath,
			final boolean reindex)
		throws PortalException {

		TreePathUtil.rebuildTree(
			companyId, parentFolderId, parentTreePath,
			new TreeModelTasksAdapter<BookmarksFolder>() {

				@Override
				public List<BookmarksFolder> findTreeModels(
					long previousId, long companyId, long parentPrimaryKey,
					int size) {

					return bookmarksFolderPersistence.findByF_C_P_NotS(
						previousId, companyId, parentPrimaryKey,
						WorkflowConstants.STATUS_IN_TRASH, QueryUtil.ALL_POS,
						size, new FolderIdComparator(true));
				}

				@Override
				public void rebuildDependentModelsTreePaths(
						long parentPrimaryKey, String treePath)
					throws PortalException {

					_blogsEntryLocalService.setTreePaths(
						parentPrimaryKey, treePath, false);
				}

			});
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BookmarksFolder restoreFolderFromTrash(long userId, long folderId)
		throws PortalException {

		// Folder

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		if (!folder.isInTrash()) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_STATUS);
		}

		TrashEntry trashEntry = _trashEntryLocalService.getEntry(
			BookmarksFolder.class.getName(), folderId);

		updateStatus(userId, folder, trashEntry.getStatus());

		// Folders and entries

		List<Object> foldersAndEntries =
			bookmarksFolderLocalService.getFoldersAndEntries(
				folder.getGroupId(), folder.getFolderId(),
				WorkflowConstants.STATUS_IN_TRASH);

		restoreDependentsFromTrash(foldersAndEntries);

		// Trash

		_trashEntryLocalService.deleteEntry(trashEntry.getEntryId());

		// Social

		JSONObject extraDataJSONObject = JSONUtil.put(
			"title", folder.getName());

		socialActivityLocalService.addActivity(
			userId, folder.getGroupId(), BookmarksFolder.class.getName(),
			folder.getFolderId(),
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH,
			extraDataJSONObject.toString(), 0);

		return folder;
	}

	@Override
	public void subscribeFolder(long userId, long groupId, long folderId)
		throws PortalException {

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			folderId = groupId;
		}

		_subscriptionLocalService.addSubscription(
			userId, groupId, BookmarksFolder.class.getName(), folderId);
	}

	@Override
	public void unsubscribeFolder(long userId, long groupId, long folderId)
		throws PortalException {

		if (folderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			folderId = groupId;
		}

		_subscriptionLocalService.deleteSubscription(
			userId, BookmarksFolder.class.getName(), folderId);
	}

	@Override
	public void updateAsset(
			long userId, BookmarksFolder folder, long[] assetCategoryIds,
			String[] assetTagNames, long[] assetLinkEntryIds, Double priority)
		throws PortalException {

		AssetEntry assetEntry = assetEntryLocalService.updateEntry(
			userId, folder.getGroupId(), folder.getCreateDate(),
			folder.getModifiedDate(), BookmarksFolder.class.getName(),
			folder.getFolderId(), folder.getUuid(), 0, assetCategoryIds,
			assetTagNames, true, true, null, null, folder.getCreateDate(), null,
			ContentTypes.TEXT_PLAIN, folder.getName(), folder.getDescription(),
			null, null, null, 0, 0, priority);

		assetLinkLocalService.updateLinks(
			userId, assetEntry.getEntryId(), assetLinkEntryIds,
			AssetLinkConstants.TYPE_RELATED);
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #updateFolder(long, long, long, String, String,
	 *             ServiceContext)} and {@link #mergeFolders(long, long)}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BookmarksFolder updateFolder(
			long userId, long folderId, long parentFolderId, String name,
			String description, boolean mergeWithParentFolder,
			ServiceContext serviceContext)
		throws PortalException {

		// Merge folders

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		parentFolderId = getParentFolderId(folder, parentFolderId);

		if (mergeWithParentFolder && (folderId != parentFolderId)) {
			mergeFolders(folder, parentFolderId);

			return folder;
		}

		// Folder

		return updateFolder(
			userId, folderId, parentFolderId, name, description,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BookmarksFolder updateFolder(
			long userId, long folderId, long parentFolderId, String name,
			String description, ServiceContext serviceContext)
		throws PortalException {

		BookmarksFolder folder = bookmarksFolderPersistence.findByPrimaryKey(
			folderId);

		parentFolderId = getParentFolderId(folder, parentFolderId);

		validate(name);

		long oldParentFolderId = folder.getParentFolderId();

		if (oldParentFolderId != parentFolderId) {
			folder.setParentFolderId(parentFolderId);
			folder.setTreePath(folder.buildTreePath());
		}

		folder.setName(name);
		folder.setDescription(description);
		folder.setExpandoBridgeAttributes(serviceContext);

		bookmarksFolderPersistence.update(folder);

		// Asset

		updateAsset(
			userId, folder, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames(),
			serviceContext.getAssetLinkEntryIds(),
			serviceContext.getAssetPriority());

		if (oldParentFolderId != parentFolderId) {
			rebuildTree(
				folder.getCompanyId(), folderId, folder.getTreePath(), true);
		}

		return folder;
	}

	@Override
	public BookmarksFolder updateStatus(
			long userId, BookmarksFolder folder, int status)
		throws PortalException {

		// Folder

		User user = userLocalService.getUser(userId);

		folder.setStatus(status);
		folder.setStatusByUserId(userId);
		folder.setStatusByUserName(user.getFullName());
		folder.setStatusDate(new Date());

		folder = bookmarksFolderPersistence.update(folder);

		// Asset

		if (status == WorkflowConstants.STATUS_APPROVED) {
			assetEntryLocalService.updateVisible(
				BookmarksFolder.class.getName(), folder.getFolderId(), true);
		}
		else if (status == WorkflowConstants.STATUS_IN_TRASH) {
			assetEntryLocalService.updateVisible(
				BookmarksFolder.class.getName(), folder.getFolderId(), false);
		}

		// Indexer

		Indexer<BookmarksFolder> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(BookmarksFolder.class);

		indexer.reindex(folder);

		return folder;
	}

	protected long getParentFolderId(
		BookmarksFolder folder, long parentFolderId) {

		if (parentFolderId ==
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			return parentFolderId;
		}

		if (folder.getFolderId() == parentFolderId) {
			return folder.getParentFolderId();
		}

		BookmarksFolder parentFolder =
			bookmarksFolderPersistence.fetchByPrimaryKey(parentFolderId);

		if ((parentFolder == null) ||
			(folder.getGroupId() != parentFolder.getGroupId())) {

			return folder.getParentFolderId();
		}

		List<Long> subfolderIds = new ArrayList<>();

		getSubfolderIds(
			subfolderIds, folder.getGroupId(), folder.getFolderId());

		if (subfolderIds.contains(parentFolderId)) {
			return folder.getParentFolderId();
		}

		return parentFolderId;
	}

	protected long getParentFolderId(long groupId, long parentFolderId) {
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
		throws PortalException {

		List<BookmarksFolder> folders = bookmarksFolderPersistence.findByG_P(
			fromFolder.getGroupId(), fromFolder.getFolderId());

		for (BookmarksFolder folder : folders) {
			mergeFolders(folder, toFolderId);
		}

		List<BookmarksEntry> entries = bookmarksEntryPersistence.findByG_F(
			fromFolder.getGroupId(), fromFolder.getFolderId());

		for (BookmarksEntry entry : entries) {
			entry.setFolderId(toFolderId);
			entry.setTreePath(entry.buildTreePath());

			bookmarksEntryPersistence.update(entry);

			Indexer<BookmarksEntry> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(BookmarksEntry.class);

			indexer.reindex(entry);
		}

		bookmarksFolderLocalService.deleteFolder(fromFolder);
	}

	protected void moveDependentsToTrash(
			List<Object> foldersAndEntries, long trashEntryId)
		throws PortalException {

		for (Object object : foldersAndEntries) {
			if (object instanceof BookmarksEntry) {

				// Entry

				BookmarksEntry entry = (BookmarksEntry)object;

				if (entry.isInTrash()) {
					continue;
				}

				int oldStatus = entry.getStatus();

				entry.setStatus(WorkflowConstants.STATUS_IN_TRASH);

				bookmarksEntryPersistence.update(entry);

				// Trash

				int status = oldStatus;

				if (oldStatus == WorkflowConstants.STATUS_PENDING) {
					status = WorkflowConstants.STATUS_DRAFT;
				}

				if (oldStatus != WorkflowConstants.STATUS_APPROVED) {
					_trashVersionLocalService.addTrashVersion(
						trashEntryId, BookmarksEntry.class.getName(),
						entry.getEntryId(), status, null);
				}

				// Asset

				assetEntryLocalService.updateVisible(
					BookmarksEntry.class.getName(), entry.getEntryId(), false);

				// Indexer

				Indexer<BookmarksEntry> indexer =
					IndexerRegistryUtil.nullSafeGetIndexer(
						BookmarksEntry.class);

				indexer.reindex(entry);
			}
			else if (object instanceof BookmarksFolder) {

				// Folder

				BookmarksFolder folder = (BookmarksFolder)object;

				if (folder.isInTrash()) {
					continue;
				}

				int oldStatus = folder.getStatus();

				folder.setStatus(WorkflowConstants.STATUS_IN_TRASH);

				bookmarksFolderPersistence.update(folder);

				// Trash

				if (oldStatus != WorkflowConstants.STATUS_APPROVED) {
					_trashVersionLocalService.addTrashVersion(
						trashEntryId, BookmarksFolder.class.getName(),
						folder.getFolderId(), oldStatus, null);
				}

				// Folders and entries

				List<Object> curFoldersAndEntries = getFoldersAndEntries(
					folder.getGroupId(), folder.getFolderId());

				moveDependentsToTrash(curFoldersAndEntries, trashEntryId);

				// Asset

				assetEntryLocalService.updateVisible(
					BookmarksFolder.class.getName(), folder.getFolderId(),
					false);

				// Indexer

				Indexer<BookmarksFolder> indexer =
					IndexerRegistryUtil.nullSafeGetIndexer(
						BookmarksFolder.class);

				indexer.reindex(folder);
			}
		}
	}

	protected void restoreDependentsFromTrash(List<Object> foldersAndEntries)
		throws PortalException {

		for (Object object : foldersAndEntries) {
			if (object instanceof BookmarksEntry) {

				// Entry

				BookmarksEntry entry = (BookmarksEntry)object;

				if (!entry.isInTrashImplicitly()) {
					continue;
				}

				TrashVersion trashVersion =
					_trashVersionLocalService.fetchVersion(
						BookmarksEntry.class.getName(), entry.getEntryId());

				int oldStatus = WorkflowConstants.STATUS_APPROVED;

				if (trashVersion != null) {
					oldStatus = trashVersion.getStatus();
				}

				entry.setStatus(oldStatus);

				bookmarksEntryPersistence.update(entry);

				// Trash

				if (trashVersion != null) {
					_trashVersionLocalService.deleteTrashVersion(trashVersion);
				}

				// Asset

				if (oldStatus == WorkflowConstants.STATUS_APPROVED) {
					assetEntryLocalService.updateVisible(
						BookmarksEntry.class.getName(), entry.getEntryId(),
						true);
				}

				// Indexer

				Indexer<BookmarksEntry> indexer =
					IndexerRegistryUtil.nullSafeGetIndexer(
						BookmarksEntry.class);

				indexer.reindex(entry);
			}
			else if (object instanceof BookmarksFolder) {

				// Folder

				BookmarksFolder folder = (BookmarksFolder)object;

				if (!folder.isInTrashImplicitly()) {
					continue;
				}

				TrashVersion trashVersion =
					_trashVersionLocalService.fetchVersion(
						BookmarksFolder.class.getName(), folder.getFolderId());

				int oldStatus = WorkflowConstants.STATUS_APPROVED;

				if (trashVersion != null) {
					oldStatus = trashVersion.getStatus();
				}

				folder.setStatus(oldStatus);

				bookmarksFolderPersistence.update(folder);

				// Folders and entries

				List<Object> curFoldersAndEntries = getFoldersAndEntries(
					folder.getGroupId(), folder.getFolderId(),
					WorkflowConstants.STATUS_IN_TRASH);

				restoreDependentsFromTrash(curFoldersAndEntries);

				// Trash

				if (trashVersion != null) {
					_trashVersionLocalService.deleteTrashVersion(trashVersion);
				}

				// Asset

				assetEntryLocalService.updateVisible(
					BookmarksFolder.class.getName(), folder.getFolderId(),
					true);

				// Indexer

				Indexer<BookmarksFolder> indexer =
					IndexerRegistryUtil.nullSafeGetIndexer(
						BookmarksFolder.class);

				indexer.reindex(folder);
			}
		}
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name) || name.contains("\\\\") ||
			name.contains("//")) {

			throw new FolderNameException();
		}
	}

	@Reference
	private BookmarksEntryLocalService _blogsEntryLocalService;

	@Reference
	private SubscriptionLocalService _subscriptionLocalService;

	@Reference
	private TrashEntryLocalService _trashEntryLocalService;

	@Reference
	private TrashVersionLocalService _trashVersionLocalService;

}