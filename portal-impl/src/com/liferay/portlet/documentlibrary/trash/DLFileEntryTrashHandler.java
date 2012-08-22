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

package com.liferay.portlet.documentlibrary.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.trash.DuplicateEntryException;
import com.liferay.portlet.trash.TrashEntryConstants;
import com.liferay.portlet.trash.model.TrashEntry;

import javax.portlet.PortletRequest;

/**
 * Represents the trash handler for the file entry entity.
 *
 * @author Alexander Chow
 * @author Manuel de la Peña
 * @author Zsolt Berentey
 */
public class DLFileEntryTrashHandler extends DLBaseTrashHandler {

	public static final String CLASS_NAME = DLFileEntry.class.getName();

	@Override
	public void checkDuplicateTrashEntry(
			TrashEntry trashEntry, long containerId, String newName)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getDLFileEntry(trashEntry.getClassPK());

		String restoredTitle = dlFileEntry.getTitle();

		if (Validator.isNotNull(newName)) {
			restoredTitle = newName;
		}

		String originalTitle = restoredTitle;

		if (restoredTitle.indexOf(StringPool.FORWARD_SLASH) > 0) {
			originalTitle = restoredTitle.substring(
				0, restoredTitle.indexOf(StringPool.FORWARD_SLASH));
		}

		if (containerId == TrashEntryConstants.DEFAULT_CONTAINER_ID) {
			containerId = dlFileEntry.getFolderId();
		}

		DLFileEntry duplicatedFileEntry =
			DLFileEntryLocalServiceUtil.fetchFileEntry(
				dlFileEntry.getGroupId(), containerId, originalTitle);

		if (duplicatedFileEntry != null) {
			DuplicateEntryException dee = new DuplicateEntryException();

			dee.setDuplicateEntryId(duplicatedFileEntry.getFileEntryId());
			dee.setOldName(duplicatedFileEntry.getTitle());
			dee.setTrashEntryId(trashEntry.getEntryId());

			throw dee;
		}
	}

	public void checkPermission(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException, SystemException {

		DLPermission.check(permissionChecker, groupId, actionId);
	}

	/**
	 * Deletes all file entries with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the file entries to be deleted
	 * @throws PortalException if any one of the file entries could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void deleteTrashEntries(long[] classPKs, boolean checkPermission)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			if (checkPermission) {
				DLAppServiceUtil.deleteFileEntry(classPK);
			}
			else {
				DLAppLocalServiceUtil.deleteFileEntry(classPK);
			}
		}
	}

	/**
	 * Returns the file entry entity's class name
	 *
	 * @return the file entry entity's class name
	 */
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		return DLUtil.getDLControlPanelLink(
			portletRequest, dlFileEntry.getFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		DLFolder dlFolder = dlFileEntry.getFolder();

		return DLUtil.getAbsolutePath(portletRequest, dlFolder.getFolderId());
	}

	@Override
	public boolean isRestorable(long classPK)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		return !dlFileEntry.isInTrashFolder();
	}

	@Override
	public void moveTrashEntry(
			long classPK, long containerId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLAppServiceUtil.moveFileEntryFromTrash(
			classPK, containerId, serviceContext);
	}

	/**
	 * Restores all file entries with the matching primary keys.
	 *
	 * @param  classPKs the primary keys of the file entries to be deleted
	 * @throws PortalException if any one of the file entries could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void restoreTrashEntries(long[] classPKs)
		throws PortalException, SystemException {

		for (long classPK : classPKs) {
			DLAppServiceUtil.restoreFileEntryFromTrash(classPK);
		}
	}

	@Override
	public void updateTitle(long classPK, String name)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		dlFileEntry.setTitle(name);

		DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry, false);

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		dlFileVersion.setTitle(name);

		DLFileVersionLocalServiceUtil.updateDLFileVersion(dlFileVersion, false);
	}

}