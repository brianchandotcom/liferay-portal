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

package com.liferay.portlet.documentlibrary.trash;

import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.trash.DuplicateEntryException;
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
	public void checkDuplicateTrashEntry(
			TrashEntry trashEntry, long containerModelId, String newName)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getDLFileEntry(trashEntry.getClassPK());

		if (containerModelId == TrashEntryConstants.DEFAULT_CONTAINER_ID) {
			containerModelId = dlFileEntry.getFolderId();
		}

		String originalTitle = trashEntry.getTypeSettingsProperty("title");

		if (Validator.isNotNull(newName)) {
			originalTitle = newName;
		}

		DLFileEntry duplicateDLFileEntry =
			DLFileEntryLocalServiceUtil.fetchFileEntry(
				dlFileEntry.getGroupId(), containerModelId, originalTitle);

		if (duplicateDLFileEntry != null) {
			DuplicateEntryException dee = new DuplicateEntryException();

			dee.setDuplicateEntryId(duplicateDLFileEntry.getFileEntryId());
			dee.setOldName(duplicateDLFileEntry.getTitle());
			dee.setTrashEntryId(trashEntry.getEntryId());

			throw dee;
		}
	}

	@Override
	public void deleteTrashEntry(long classPK)
		throws PortalException, SystemException {

		DLAppLocalServiceUtil.deleteFileEntry(classPK);
	}

	@Override
	public String getClassName() {
		return DLFileEntry.class.getName();
	}

	@Override
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		long parentFolderId = dlFileEntry.getFolderId();

		if (parentFolderId <= 0) {
			return null;
		}

		return getContainerModel(parentFolderId);
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
	public ContainerModel getTrashContainer(long classPK)
		throws PortalException, SystemException {

		try {
			DLFileEntry dlFileEntry = getDLFileEntry(classPK);

			return dlFileEntry.getTrashContainer();
		}
		catch (InvalidRepositoryException ire) {
			return null;
		}
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException, SystemException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return DLFolderPermission.contains(
				permissionChecker, groupId, classPK, ActionKeys.ADD_DOCUMENT);
		}

		return super.hasTrashPermission(
			permissionChecker, groupId, classPK, trashActionId);
	}

	@Override
	public boolean isInTrash(long classPK)
		throws PortalException, SystemException {

		try {
			DLFileEntry dlFileEntry = getDLFileEntry(classPK);

			DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

			return dlFileVersion.isInTrash();
		}
		catch (InvalidRepositoryException ire) {
			return false;
		}
	}

	@Override
	public boolean isInTrashContainer(long classPK)
		throws PortalException, SystemException {

		try {
			DLFileEntry dlFileEntry = getDLFileEntry(classPK);

			return dlFileEntry.isInTrashContainer();
		}
		catch (InvalidRepositoryException ire) {
			return false;
		}
	}

	@Override
	public boolean isRestorable(long classPK)
		throws PortalException, SystemException {

		try {
			DLFileEntry dlFileEntry = getDLFileEntry(classPK);

			return !dlFileEntry.isInTrashContainer();
		}
		catch (InvalidRepositoryException ire) {
			return false;
		}
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLAppLocalServiceUtil.moveFileEntry(
			userId, classPK, containerModelId, serviceContext);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(classPK);

		DLAppHelperLocalServiceUtil.moveFileEntryFromTrash(
			userId, repository.getFileEntry(classPK), containerModelId,
			serviceContext);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException, SystemException {

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
	public void updateTitle(long classPK, String name)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		dlFileEntry.setTitle(name);

		DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		dlFileVersion.setTitle(name);

		DLFileVersionLocalServiceUtil.updateDLFileVersion(dlFileVersion);
	}

	protected DLFileEntry getDLFileEntry(long classPK)
		throws PortalException, SystemException {

		Repository repository = RepositoryServiceUtil.getRepositoryImpl(
			0, classPK, 0);

		if (!(repository instanceof LiferayRepository)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		FileEntry fileEntry = repository.getFileEntry(classPK);

		return (DLFileEntry)fileEntry.getModel();
	}

	@Override
	protected Repository getRepository(long classPK)
		throws PortalException, SystemException {

		Repository repository = RepositoryServiceUtil.getRepositoryImpl(
			0, classPK, 0);

		if (!(repository instanceof LiferayRepository)) {
			throw new InvalidRepositoryException(
				"Repository " + repository.getRepositoryId() +
					" does not support trash operations");
		}

		return repository;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getDLFileEntry(classPK);

		if (dlFileEntry.isInHiddenFolder() &&
			actionId.equals(ActionKeys.VIEW)) {

			return false;
		}

		return DLFileEntryPermission.contains(
			permissionChecker, classPK, actionId);
	}

}