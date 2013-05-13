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
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.permission.BookmarksEntryPermission;
import com.liferay.portlet.bookmarks.util.BookmarksUtil;

import javax.portlet.PortletRequest;

/**
 * Represents the trash handler for bookmarks entries entity.
 *
 * @author Levente Hudák
 * @author Zsolt Berentey
 */
public class BookmarksEntryTrashHandler extends BookmarksBaseTrashHandler {

	@Override
	public void deleteTrashEntry(long classPK)
		throws PortalException, SystemException {

		BookmarksEntryLocalServiceUtil.deleteEntry(classPK);
	}

	@Override
	public String getClassName() {
		return BookmarksEntry.class.getName();
	}

	@Override
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		long parentFolderId = entry.getFolderId();

		if (parentFolderId <= 0) {
			return null;
		}

		return getContainerModel(parentFolderId);
	}

	@Override
	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		return BookmarksUtil.getControlPanelLink(
			portletRequest, entry.getFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		return BookmarksUtil.getAbsolutePath(
			portletRequest, entry.getFolderId());
	}

	@Override
	public ContainerModel getTrashContainer(long classPK)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		return entry.getTrashContainer();
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException, SystemException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return true;
		}

		return super.hasTrashPermission(
			permissionChecker, groupId, classPK, trashActionId);
	}

	@Override
	public boolean isInTrash(long classPK)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		return entry.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		return entry.isInTrashContainer();
	}

	@Override
	public boolean isRestorable(long classPK)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		return !entry.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		BookmarksEntryLocalServiceUtil.moveEntry(classPK, containerModelId);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		BookmarksEntryLocalServiceUtil.moveEntryFromTrash(
			userId, classPK, containerId);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException, SystemException {

		BookmarksEntryLocalServiceUtil.restoreEntryFromTrash(userId, classPK);
	}

	@Override
	protected BookmarksFolder getBookmarksFolder(long classPK)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		return entry.getFolder();
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		return BookmarksEntryPermission.contains(
			permissionChecker, entry, actionId);
	}

}