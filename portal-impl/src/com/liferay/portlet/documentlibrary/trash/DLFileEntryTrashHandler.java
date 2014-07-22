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

package com.liferay.portlet.documentlibrary.trash;

import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.model.TrashedModel;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.trash.RestoreEntryException;
import com.liferay.portlet.trash.TrashEntryConstants;
import com.liferay.portlet.trash.model.TrashEntry;

import javax.portlet.PortletRequest;

/**
 * Implements trash handling for the file entry entity.
 *
 * @author Alexander Chow
 * @author Manuel de la Peña
 * @author Zsolt Berentey
 */
public class DLFileEntryTrashHandler extends DLBaseTrashHandler {

	@Override
	public void checkRestorableEntry(
			long classPK, long containerModelId, String newName)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		checkRestorableEntry(
			classPK, 0, containerModelId, dlFileEntry.getTitle(), newName);
	}

	@Override
	public void checkRestorableEntry(
			TrashEntry trashEntry, long containerModelId, String newName)
		throws PortalException {

		checkRestorableEntry(
			trashEntry.getClassPK(), trashEntry.getEntryId(), containerModelId,
			trashEntry.getTypeSettingsProperty("title"), newName);
	}

	@Override
	public void deleteTrashEntry(long classPK) throws PortalException {
		Repository repository = getRepository(classPK);

		TrashCapability trashCapability = repository.getCapability(
			TrashCapability.class);

		trashCapability.deleteFileEntry(repository.getFileEntry(classPK));
	}

	@Override
	public String getClassName() {
		return DLFileEntry.class.getName();
	}

	@Override
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		long parentFolderId = dlFileEntry.getFolderId();

		if (parentFolderId <= 0) {
			return null;
		}

		return getContainerModel(parentFolderId);
	}

	@Override
	public ContainerModel getParentContainerModel(TrashedModel trashedModel)
		throws PortalException {

		DLFileEntry dlFileEntry = (DLFileEntry)trashedModel;

		return getContainerModel(dlFileEntry.getFolderId());
	}

	@Override
	public String getRestoreContainedModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		return DLUtil.getDLFileEntryControlPanelLink(
			portletRequest, dlFileEntry.getFileEntryId());
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		return DLUtil.getDLFolderControlPanelLink(
			portletRequest, dlFileEntry.getFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		DLFolder dlFolder = dlFileEntry.getFolder();

		return DLUtil.getAbsolutePath(portletRequest, dlFolder.getFolderId());
	}

	@Override
	public String getSystemEventClassName() {
		return DLFileEntryConstants.getClassName();
	}

	@Override
	public TrashEntry getTrashEntry(long classPK) throws PortalException {
		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		return dlFileEntry.getTrashEntry();
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return DLFolderPermission.contains(
				permissionChecker, groupId, classPK, ActionKeys.ADD_DOCUMENT);
		}

		return super.hasTrashPermission(
			permissionChecker, groupId, classPK, trashActionId);
	}

	@Override
	public boolean isInTrash(long classPK) throws PortalException {
		try {
			DLFileEntry dlFileEntry = getDLFileEntry(classPK);

			return dlFileEntry.isInTrash();
		}
		catch (InvalidRepositoryException ire) {
			return false;
		}
	}

	@Override
	public boolean isInTrashContainer(long classPK) throws PortalException {
		try {
			DLFileEntry dlFileEntry = getDLFileEntry(classPK);

			return dlFileEntry.isInTrashContainer();
		}
		catch (InvalidRepositoryException ire) {
			return false;
		}
	}

	@Override
	public boolean isRestorable(long classPK) throws PortalException {
		DLFileEntry dlFileEntry = fetchDLFileEntry(classPK);

		if ((dlFileEntry == null) ||
			((dlFileEntry.getFolderId() > 0) &&
			 (DLFolderLocalServiceUtil.fetchFolder(
				dlFileEntry.getFolderId()) == null))) {

			return false;
		}

		return !dlFileEntry.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		DLAppLocalServiceUtil.moveFileEntry(
			userId, classPK, containerModelId, serviceContext);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		DLAppLocalServiceUtil.moveFileEntryFromTrash(
			userId, classPK, containerModelId, serviceContext);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		if ((dlFileEntry.getClassNameId() > 0) &&
			(dlFileEntry.getClassPK() > 0)) {

			TrashHandler trashHandler =
				TrashHandlerRegistryUtil.getTrashHandler(
					dlFileEntry.getClassName());

			trashHandler.restoreRelatedTrashEntry(getClassName(), classPK);

			return;
		}

		DLAppLocalServiceUtil.restoreFileEntryFromTrash(userId, classPK);
	}

	@Override
	public void updateTitle(long classPK, String name) throws PortalException {
		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		dlFileEntry.setTitle(name);

		DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		dlFileVersion.setTitle(name);

		DLFileVersionLocalServiceUtil.updateDLFileVersion(dlFileVersion);
	}

	protected void checkRestorableEntry(
			long classPK, long entryId, long containerModelId,
			String originalTitle, String newName)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		if (containerModelId == TrashEntryConstants.DEFAULT_CONTAINER_ID) {
			containerModelId = dlFileEntry.getFolderId();
		}

		if (Validator.isNotNull(newName)) {
			originalTitle = newName;
		}

		DLFileEntry duplicateDLFileEntry =
			DLFileEntryLocalServiceUtil.fetchFileEntry(
				dlFileEntry.getGroupId(), containerModelId, originalTitle);

		if (duplicateDLFileEntry != null) {
			RestoreEntryException ree = new RestoreEntryException(
				RestoreEntryException.DUPLICATE);

			ree.setDuplicateEntryId(duplicateDLFileEntry.getFileEntryId());
			ree.setOldName(duplicateDLFileEntry.getTitle());
			ree.setTrashEntryId(entryId);

			throw ree;
		}
	}

	protected DLFileEntry fetchDLFileEntry(long classPK)
		throws PortalException {

		Repository repository = RepositoryServiceUtil.getRepositoryImpl(
			0, classPK, 0);

		if (!repository.isCapabilityProvided(TrashCapability.class)) {
			return null;
		}

		FileEntry fileEntry = repository.getFileEntry(classPK);

		return (DLFileEntry)fileEntry.getModel();
	}

	protected DLFileEntry getDLFileEntry(long classPK) throws PortalException {
		Repository repository = RepositoryServiceUtil.getRepositoryImpl(
			0, classPK, 0);

		if (!repository.isCapabilityProvided(TrashCapability.class)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		FileEntry fileEntry = repository.getFileEntry(classPK);

		return (DLFileEntry)fileEntry.getModel();
	}

	@Override
	protected Repository getRepository(long classPK) throws PortalException {
		Repository repository = RepositoryServiceUtil.getRepositoryImpl(
			0, classPK, 0);

		if (!repository.isCapabilityProvided(TrashCapability.class)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		return repository;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		if (dlFileEntry.isInHiddenFolder() &&
			actionId.equals(ActionKeys.VIEW)) {

			return false;
		}

		return DLFileEntryPermission.contains(
			permissionChecker, classPK, actionId);
	}

}