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

package com.liferay.portlet.bookmarks.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.bookmarks.asset.BookmarksFolderAssetRenderer;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.permission.BookmarksFolderPermission;
import com.liferay.portlet.bookmarks.util.BookmarksUtil;

import javax.portlet.PortletRequest;

/**
 * Represents the trash handler for bookmarks folder entity.
 *
 * @author Eudaldo Alonso
 */
public class BookmarksFolderTrashHandler extends BookmarksBaseTrashHandler {

	@Override
	public void deleteTrashEntry(long classPK)
		throws PortalException, SystemException {

		BookmarksFolderLocalServiceUtil.deleteFolder(classPK, false);
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
		throws PortalException, SystemException {

		BookmarksFolder folder = getBookmarksFolder(classPK);

		long parentFolderId = folder.getParentFolderId();

		if (parentFolderId <= 0) {
			return null;
		}

		return getContainerModel(parentFolderId);
	}

	@Override
	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		BookmarksFolder folder = getBookmarksFolder(classPK);

		return BookmarksUtil.getControlPanelLink(
			portletRequest, folder.getParentFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		BookmarksFolder folder = getBookmarksFolder(classPK);

		return BookmarksUtil.getAbsolutePath(
			portletRequest, folder.getParentFolderId());
	}

	@Override
	public ContainerModel getTrashContainer(long classPK)
		throws PortalException, SystemException {

		BookmarksFolder folder = getBookmarksFolder(classPK);

		return folder.getTrashContainer();
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		BookmarksFolder folder = getBookmarksFolder(classPK);

		return new BookmarksFolderAssetRenderer(folder);
	}

	@Override
	public boolean isContainerModel() {
		return true;
	}

	@Override
	public boolean isInTrash(long classPK)
		throws PortalException, SystemException {

		BookmarksFolder folder = getBookmarksFolder(classPK);

		return folder.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK)
		throws PortalException, SystemException {

		BookmarksFolder folder = getBookmarksFolder(classPK);

		return folder.isInTrashContainer();
	}

	@Override
	public boolean isRestorable(long classPK)
		throws PortalException, SystemException {

		BookmarksFolder folder = getBookmarksFolder(classPK);

		return !folder.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		BookmarksFolderLocalServiceUtil.moveFolder(classPK, containerModelId);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		BookmarksFolderLocalServiceUtil.moveFolderFromTrash(
			userId, classPK, containerId);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException, SystemException {

		BookmarksFolderLocalServiceUtil.restoreFolderFromTrash(userId, classPK);
	}

	@Override
	protected BookmarksFolder getBookmarksFolder(long classPK)
		throws PortalException, SystemException {

		return BookmarksFolderLocalServiceUtil.getFolder(classPK);
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		BookmarksFolder folder = getBookmarksFolder(classPK);

		return BookmarksFolderPermission.contains(
			permissionChecker, folder, actionId);
	}

}