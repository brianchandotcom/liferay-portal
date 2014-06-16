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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.base.BookmarksEntryServiceBaseImpl;
import com.liferay.portlet.bookmarks.service.permission.BookmarksEntryPermission;
import com.liferay.portlet.bookmarks.service.permission.BookmarksFolderPermission;
import com.liferay.portlet.bookmarks.util.comparator.EntryModifiedDateComparator;

import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Levente Hudák
 */
public class BookmarksEntryServiceImpl extends BookmarksEntryServiceBaseImpl {

	@Override
	public BookmarksEntry addEntry(
			long groupId, long folderId, String name, String url,
			String description, ServiceContext serviceContext)
		throws PortalException {

		BookmarksFolderPermission.check(
			getPermissionChecker(), groupId, folderId, ActionKeys.ADD_ENTRY);

		return bookmarksEntryLocalService.addEntry(
			getUserId(), groupId, folderId, name, url, description,
			serviceContext);
	}

	@Override
	public void deleteEntry(long entryId) throws PortalException {
		BookmarksEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		bookmarksEntryLocalService.deleteEntry(entryId);
	}

	@Override
	public List<BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end) {

		return bookmarksEntryPersistence.filterFindByG_F_S(
			groupId, folderId, WorkflowConstants.STATUS_APPROVED, start, end);
	}

	@Override
	public List<BookmarksEntry> getEntries(
		long groupId, long folderId, int start, int end,
		OrderByComparator orderByComparator) {

		return bookmarksEntryPersistence.filterFindByG_F_S(
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
		if (status == WorkflowConstants.STATUS_ANY) {
			return bookmarksEntryPersistence.filterCountByG_F_NotS(
				groupId, folderId, WorkflowConstants.STATUS_IN_TRASH);
		}
		else {
			return bookmarksEntryPersistence.filterCountByG_F_S(
				groupId, folderId, status);
		}
	}

	@Override
	public BookmarksEntry getEntry(long entryId) throws PortalException {
		BookmarksEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.VIEW);

		return bookmarksEntryLocalService.getEntry(entryId);
	}

	@Override
	public int getFoldersEntriesCount(long groupId, List<Long> folderIds) {
		return bookmarksEntryPersistence.filterCountByG_F_S(
			groupId,
			ArrayUtil.toArray(folderIds.toArray(new Long[folderIds.size()])),
			WorkflowConstants.STATUS_APPROVED);
	}

	@Override
	public List<BookmarksEntry> getGroupEntries(
			long groupId, int start, int end)
		throws PortalException {

		return getGroupEntries(
			groupId, 0, WorkflowConstants.STATUS_APPROVED, start, end);
	}

	@Override
	public List<BookmarksEntry> getGroupEntries(
			long groupId, long userId, int start, int end)
		throws PortalException {

		return getGroupEntries(
			groupId, userId, BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			start, end);
	}

	@Override
	public List<BookmarksEntry> getGroupEntries(
			long groupId, long userId, long rootFolderId, int start, int end)
		throws PortalException {

		if (rootFolderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			if (userId <= 0) {
				return bookmarksEntryPersistence.filterFindByG_NotS(
					groupId, WorkflowConstants.STATUS_IN_TRASH, start, end,
					new EntryModifiedDateComparator());
			}
			else {
				return bookmarksEntryPersistence.filterFindByG_U_NotS(
					groupId, userId, WorkflowConstants.STATUS_IN_TRASH, start,
					end, new EntryModifiedDateComparator());
			}
		}

		List<Long> folderIds = bookmarksFolderService.getFolderIds(
			groupId, rootFolderId);

		if (folderIds.isEmpty()) {
			return Collections.emptyList();
		}
		else if (userId <= 0) {
			return bookmarksEntryPersistence.filterFindByG_F_S(
				groupId, ArrayUtil.toLongArray(folderIds),
				WorkflowConstants.STATUS_APPROVED, start, end,
				new EntryModifiedDateComparator());
		}
		else {
			return bookmarksEntryPersistence.filterFindByG_U_F_S(
				groupId, userId, ArrayUtil.toLongArray(folderIds),
				WorkflowConstants.STATUS_APPROVED, start, end,
				new EntryModifiedDateComparator());
		}
	}

	@Override
	public int getGroupEntriesCount(long groupId) throws PortalException {
		return getGroupEntriesCount(groupId, 0);
	}

	@Override
	public int getGroupEntriesCount(long groupId, long userId)
		throws PortalException {

		return getGroupEntriesCount(
			groupId, userId, BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Override
	public int getGroupEntriesCount(
			long groupId, long userId, long rootFolderId)
		throws PortalException {

		if (rootFolderId == BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			if (userId <= 0) {
				return bookmarksEntryPersistence.filterCountByG_NotS(
					groupId, WorkflowConstants.STATUS_IN_TRASH);
			}
			else {
				return bookmarksEntryPersistence.filterCountByG_U_NotS(
					groupId, userId, WorkflowConstants.STATUS_IN_TRASH);
			}
		}

		List<Long> folderIds = bookmarksFolderService.getFolderIds(
			groupId, rootFolderId);

		if (folderIds.isEmpty()) {
			return 0;
		}
		else if (userId <= 0) {
			return bookmarksEntryPersistence.filterCountByG_F_S(
				groupId, ArrayUtil.toLongArray(folderIds),
				WorkflowConstants.STATUS_APPROVED);
		}
		else {
			return bookmarksEntryPersistence.filterCountByG_U_F_S(
				groupId, userId, ArrayUtil.toLongArray(folderIds),
				WorkflowConstants.STATUS_APPROVED);
		}
	}

	@Override
	public BookmarksEntry moveEntry(long entryId, long parentFolderId)
		throws PortalException {

		BookmarksEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return bookmarksEntryLocalService.moveEntry(entryId, parentFolderId);
	}

	@Override
	public BookmarksEntry moveEntryFromTrash(long entryId, long parentFolderId)
		throws PortalException {

		BookmarksEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return bookmarksEntryLocalService.moveEntryFromTrash(
			getUserId(), entryId, parentFolderId);
	}

	@Override
	public BookmarksEntry moveEntryToTrash(long entryId)
		throws PortalException {

		BookmarksEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		return bookmarksEntryLocalService.moveEntryToTrash(
			getUserId(), entryId);
	}

	@Override
	public BookmarksEntry openEntry(BookmarksEntry entry)
		throws PortalException {

		BookmarksEntryPermission.check(
			getPermissionChecker(), entry, ActionKeys.VIEW);

		return bookmarksEntryLocalService.openEntry(getGuestOrUserId(), entry);
	}

	@Override
	public BookmarksEntry openEntry(long entryId) throws PortalException {
		BookmarksEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.VIEW);

		return bookmarksEntryLocalService.openEntry(
			getGuestOrUserId(), entryId);
	}

	@Override
	public void restoreEntryFromTrash(long entryId) throws PortalException {
		BookmarksEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		bookmarksEntryLocalService.restoreEntryFromTrash(getUserId(), entryId);
	}

	@Override
	public Hits search(
			long groupId, long creatorUserId, int status, int start, int end)
		throws PortalException {

		return bookmarksEntryLocalService.search(
			groupId, getUserId(), creatorUserId, status, start, end);
	}

	@Override
	public void subscribeEntry(long entryId) throws PortalException {
		BookmarksEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.SUBSCRIBE);

		bookmarksEntryLocalService.subscribeEntry(getUserId(), entryId);
	}

	@Override
	public void unsubscribeEntry(long entryId) throws PortalException {
		BookmarksEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.SUBSCRIBE);

		bookmarksEntryLocalService.unsubscribeEntry(getUserId(), entryId);
	}

	@Override
	public BookmarksEntry updateEntry(
			long entryId, long groupId, long folderId, String name, String url,
			String description, ServiceContext serviceContext)
		throws PortalException {

		BookmarksEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return bookmarksEntryLocalService.updateEntry(
			getUserId(), entryId, groupId, folderId, name, url, description,
			serviceContext);
	}

}