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

package com.liferay.portlet.journal.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.journal.asset.JournalFolderAssetRenderer;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalFolderPermission;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.trash.DuplicateEntryException;
import com.liferay.portlet.trash.model.TrashEntry;

import javax.portlet.PortletRequest;

/**
 * Implements trash handling for the journal folder entity.
 *
 * @author Eudaldo Alonso
 */
public class JournalFolderTrashHandler extends JournalBaseTrashHandler {

	@Override
	public void checkDuplicateTrashEntry(
			TrashEntry trashEntry, long containerModelId, String newName)
		throws PortalException, SystemException {

		JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(
			trashEntry.getClassPK());

		String originalTitle = trashEntry.getTypeSettingsProperty("title");

		if (Validator.isNotNull(newName)) {
			originalTitle = newName;
		}

		JournalFolder duplicateFolder =
			JournalFolderLocalServiceUtil.fetchFolder(
				folder.getGroupId(), folder.getParentFolderId(), originalTitle);

		if (duplicateFolder != null) {
			DuplicateEntryException dee = new DuplicateEntryException();

			dee.setDuplicateEntryId(duplicateFolder.getFolderId());
			dee.setOldName(duplicateFolder.getName());
			dee.setTrashEntryId(trashEntry.getEntryId());

			throw dee;
		}
	}

	@Override
	public void deleteTrashEntry(long classPK)
		throws PortalException, SystemException {

		JournalFolderLocalServiceUtil.deleteFolder(classPK, false);
	}

	@Override
	public String getClassName() {
		return JournalFolder.class.getName();
	}

	@Override
	public String getDeleteMessage() {
		return "found-in-deleted-folder-x";
	}

	@Override
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException, SystemException {

		JournalFolder folder = getJournalFolder(classPK);

		long parentFolderId = folder.getParentFolderId();

		if (parentFolderId <= 0) {
			return null;
		}

		return getContainerModel(parentFolderId);
	}

	@Override
	public String getRestoreLink(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(classPK);

		return JournalUtil.getJournalControlPanelLink(
			portletRequest, folder.getParentFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(classPK);

		return JournalUtil.getAbsolutePath(
			portletRequest, folder.getParentFolderId());
	}

	@Override
	public ContainerModel getTrashContainer(long classPK)
		throws PortalException, SystemException {

		JournalFolder folder = getJournalFolder(classPK);

		return folder.getTrashContainer();
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(classPK);

		return new JournalFolderAssetRenderer(folder);
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException, SystemException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return JournalFolderPermission.contains(
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
	public boolean isInTrash(long classPK)
		throws PortalException, SystemException {

		JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(classPK);

		return folder.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK)
		throws PortalException, SystemException {

		JournalFolder folder = getJournalFolder(classPK);

		return folder.isInTrashContainer();
	}

	@Override
	public boolean isRestorable(long classPK)
		throws PortalException, SystemException {

		JournalFolder folder = getJournalFolder(classPK);

		return !folder.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalFolderLocalServiceUtil.moveFolder(
			classPK, containerModelId, serviceContext);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalFolderLocalServiceUtil.moveFolderFromTrash(
			userId, classPK, containerModelId, serviceContext);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException, SystemException {

		JournalFolderLocalServiceUtil.restoreFolderFromTrash(userId, classPK);
	}

	@Override
	public void updateTitle(long classPK, String name)
		throws PortalException, SystemException {

		JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(classPK);

		folder.setName(name);

		JournalFolderLocalServiceUtil.updateJournalFolder(folder);
	}

	@Override
	protected JournalFolder getJournalFolder(long classPK)
		throws PortalException, SystemException {

		JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(classPK);

		return folder;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		JournalFolder folder = JournalFolderLocalServiceUtil.getFolder(classPK);

		return JournalFolderPermission.contains(
			permissionChecker, folder, actionId);
	}

}