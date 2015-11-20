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

package com.liferay.bookmarks.trash;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.bookmarks.service.permission.BookmarksEntryPermissionChecker;
import com.liferay.bookmarks.service.permission.BookmarksFolderPermissionChecker;
import com.liferay.bookmarks.util.BookmarksUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.trash.model.TrashEntry;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Represents the trash handler for bookmarks entries entity.
 *
 * @author Levente Hudák
 * @author Zsolt Berentey
 */
@Component(
	property = {"model.class.name=com.liferay.bookmarks.model.BookmarksEntry"},
	service = TrashHandler.class
)
public class BookmarksEntryTrashHandler extends BookmarksBaseTrashHandler {

	@Override
	public void deleteTrashEntry(long classPK) throws PortalException {
		_bookmarksEntryLocalService.deleteEntry(classPK);
	}

	@Override
	public String getClassName() {
		return BookmarksEntry.class.getName();
	}

	@Override
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException {

		BookmarksEntry entry = _bookmarksEntryLocalService.getEntry(classPK);

		long parentFolderId = entry.getFolderId();

		if (parentFolderId <= 0) {
			return null;
		}

		return getContainerModel(parentFolderId);
	}

	@Override
	public ContainerModel getParentContainerModel(TrashedModel trashedModel)
		throws PortalException {

		BookmarksEntry entry = (BookmarksEntry)trashedModel;

		return getContainerModel(entry.getFolderId());
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		BookmarksEntry entry = _bookmarksEntryLocalService.getEntry(classPK);

		return BookmarksUtil.getControlPanelLink(
			portletRequest, entry.getFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException {

		BookmarksEntry entry = _bookmarksEntryLocalService.getEntry(classPK);

		return BookmarksUtil.getAbsolutePath(
			portletRequest, entry.getFolderId());
	}

	@Override
	public TrashEntry getTrashEntry(long classPK) throws PortalException {
		BookmarksEntry entry = _bookmarksEntryLocalService.getEntry(classPK);

		return entry.getTrashEntry();
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return BookmarksFolderPermissionChecker.contains(
				permissionChecker, groupId, classPK, ActionKeys.ADD_ENTRY);
		}

		return super.hasTrashPermission(
			permissionChecker, groupId, classPK, trashActionId);
	}

	@Override
	public boolean isInTrash(long classPK) throws PortalException {
		BookmarksEntry entry = _bookmarksEntryLocalService.getEntry(classPK);

		return entry.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK) throws PortalException {
		BookmarksEntry entry = _bookmarksEntryLocalService.getEntry(classPK);

		return entry.isInTrashContainer();
	}

	@Override
	public boolean isRestorable(long classPK) throws PortalException {
		BookmarksEntry entry = _bookmarksEntryLocalService.getEntry(classPK);

		if ((entry.getFolderId() > 0) &&
			(_bookmarksFolderLocalService.fetchBookmarksFolder(
				entry.getFolderId()) == null)) {

			return false;
		}

		return !entry.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		_bookmarksEntryLocalService.moveEntry(classPK, containerModelId);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerId,
			ServiceContext serviceContext)
		throws PortalException {

		_bookmarksEntryLocalService.moveEntryFromTrash(
			userId, classPK, containerId);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		_bookmarksEntryLocalService.restoreEntryFromTrash(userId, classPK);
	}

	@Override
	protected long getGroupId(long classPK) throws PortalException {
		BookmarksEntry entry = _bookmarksEntryLocalService.getEntry(classPK);

		return entry.getGroupId();
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		BookmarksEntry entry = _bookmarksEntryLocalService.getEntry(classPK);

		return BookmarksEntryPermissionChecker.contains(
			permissionChecker, entry, actionId);
	}

	@Reference(unbind = "-")
	protected void setBookmarksEntryLocalService(
		BookmarksEntryLocalService bookmarksEntryLocalService) {

		_bookmarksEntryLocalService = bookmarksEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setBookmarksFolderLocalService(
		BookmarksFolderLocalService bookmarksFolderLocalService) {

		_bookmarksFolderLocalService = bookmarksFolderLocalService;
	}

	private volatile BookmarksEntryLocalService _bookmarksEntryLocalService;
	private volatile BookmarksFolderLocalService _bookmarksFolderLocalService;

}