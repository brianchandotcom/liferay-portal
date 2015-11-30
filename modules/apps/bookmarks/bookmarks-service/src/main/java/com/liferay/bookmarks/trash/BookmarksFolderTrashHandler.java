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

import com.liferay.bookmarks.asset.BookmarksFolderAssetRenderer;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalService;
import com.liferay.bookmarks.service.permission.BookmarksFolderPermissionChecker;
import com.liferay.bookmarks.util.BookmarksUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.trash.model.TrashEntry;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Represents the trash handler for bookmarks folder entity.
 *
 * @author Eudaldo Alonso
 */
@Component(
	property = {"model.class.name=com.liferay.bookmarks.model.BookmarksFolder"},
	service = TrashHandler.class
)
public class BookmarksFolderTrashHandler extends BookmarksBaseTrashHandler {

	@Override
	public void deleteTrashEntry(long classPK) throws PortalException {
		_bookmarksFolderLocalService.deleteFolder(classPK, false);
	}

	@Override
	public String getClassName() {
		return BookmarksFolder.class.getName();
	}

	@Override
	public String getDeleteMessage() {
		return "found-in-deleted-folder-x";
	}

	@Override
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException {

		BookmarksFolder folder = getBookmarksFolder(classPK);

		long parentFolderId = folder.getParentFolderId();

		if (parentFolderId <= 0) {
			return null;
		}

		return getContainerModel(parentFolderId);
	}

	@Override
	public String getRestoreContainedModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		BookmarksFolder folder = getBookmarksFolder(classPK);

		return BookmarksUtil.getControlPanelLink(
			portletRequest, folder.getFolderId());
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		BookmarksFolder folder = getBookmarksFolder(classPK);

		return BookmarksUtil.getControlPanelLink(
			portletRequest, folder.getParentFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException {

		BookmarksFolder folder = getBookmarksFolder(classPK);

		return BookmarksUtil.getAbsolutePath(
			portletRequest, folder.getParentFolderId());
	}

	@Override
	public TrashEntry getTrashEntry(long classPK) throws PortalException {
		BookmarksFolder folder = getBookmarksFolder(classPK);

		return folder.getTrashEntry();
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK) throws PortalException {
		BookmarksFolder folder = getBookmarksFolder(classPK);

		return new BookmarksFolderAssetRenderer(folder);
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return BookmarksFolderPermissionChecker.contains(
				permissionChecker, groupId, classPK, ActionKeys.ADD_FOLDER);
		}

		return super.hasTrashPermission(
			permissionChecker, groupId, classPK, trashActionId);
	}

	@Override
	public boolean isContainerModel() {
		return true;
	}

	@Override
	public boolean isInTrash(long classPK) throws PortalException {
		BookmarksFolder folder = getBookmarksFolder(classPK);

		return folder.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK) throws PortalException {
		BookmarksFolder folder = getBookmarksFolder(classPK);

		return folder.isInTrashContainer();
	}

	@Override
	public boolean isRestorable(long classPK) throws PortalException {
		BookmarksFolder folder = getBookmarksFolder(classPK);

		if ((folder.getParentFolderId() > 0) &&
			(_bookmarksFolderLocalService.fetchBookmarksFolder(
				folder.getParentFolderId()) == null)) {

			return false;
		}

		return !folder.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		_bookmarksFolderLocalService.moveFolder(classPK, containerModelId);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerId,
			ServiceContext serviceContext)
		throws PortalException {

		_bookmarksFolderLocalService.moveFolderFromTrash(
			userId, classPK, containerId);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		_bookmarksFolderLocalService.restoreFolderFromTrash(userId, classPK);
	}

	protected BookmarksFolder getBookmarksFolder(long classPK)
		throws PortalException {

		return _bookmarksFolderLocalService.getFolder(classPK);
	}

	@Override
	protected long getGroupId(long classPK) throws PortalException {
		BookmarksFolder folder = getBookmarksFolder(classPK);

		return folder.getGroupId();
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		BookmarksFolder folder = getBookmarksFolder(classPK);

		return BookmarksFolderPermissionChecker.contains(
			permissionChecker, folder, actionId);
	}

	@Reference(unbind = "-")
	protected void setBookmarksFolderLocalService(
		BookmarksFolderLocalService bookmarksFolderLocalService) {

		_bookmarksFolderLocalService = bookmarksFolderLocalService;
	}

	private volatile BookmarksFolderLocalService _bookmarksFolderLocalService;

}