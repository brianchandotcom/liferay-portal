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

package com.liferay.portlet.bookmarks.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.service.BookmarksEntryServiceUtil;
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

	public static final String CLASS_NAME = BookmarksEntry.class.getName();

	public void deleteTrashEntries(long[] classPKs, boolean checkPermission)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			if (checkPermission) {
				BookmarksEntryServiceUtil.deleteEntry(classPK);
			}
			else {
				BookmarksEntryLocalServiceUtil.deleteEntry(classPK);
			}
		}
	}

	public String getClassName() {
		return CLASS_NAME;
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
	public boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		return BookmarksEntryPermission.contains(
			permissionChecker, entry, actionId);
	}

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
			long classPK, long containerModelId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		BookmarksEntryServiceUtil.moveEntry(classPK, containerModelId);
	}

	@Override
	public void moveTrashEntry(
			long classPK, long containerId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		BookmarksEntryServiceUtil.moveEntryFromTrash(classPK, containerId);
	}

	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			BookmarksEntryServiceUtil.restoreEntryFromTrash(classPK);
		}
	}

	@Override
	protected BookmarksFolder getBookmarksFolder(long classPK)
		throws PortalException, SystemException {

		BookmarksEntry entry = BookmarksEntryLocalServiceUtil.getEntry(classPK);

		return entry.getFolder();
	}

}