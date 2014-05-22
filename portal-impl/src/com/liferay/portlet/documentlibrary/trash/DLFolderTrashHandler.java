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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.asset.DLFolderAssetRenderer;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.trash.RestoreEntryException;
import com.liferay.portlet.trash.TrashEntryConstants;
import com.liferay.portlet.trash.model.TrashEntry;

import javax.portlet.PortletRequest;
import java.util.List;

/**
 * Implements trash handling for the folder entity.
 *
 * @author Alexander Chow
 * @author Zsolt Berentey
 */
public class DLFolderTrashHandler extends DLBaseTrashHandler {

	@Override
	public void checkRestorableEntry(
			long classPK, long containerModelId, String newName)
		throws PortalException, SystemException {

		DLFolder dlFolder = getDLFolder(classPK);

		checkRestorableEntry(
			classPK, 0, containerModelId, dlFolder.getName(), newName);
	}

	@Override
	public void checkRestorableEntry(
			TrashEntry trashEntry, long containerModelId, String newName)
		throws PortalException, SystemException {

		checkRestorableEntry(
			trashEntry.getClassPK(), trashEntry.getEntryId(), containerModelId,
			trashEntry.getTypeSettingsProperty("title"), newName);
	}

	@Override
	public void deleteTrashEntry(long classPK)
		throws PortalException, SystemException {

		DLFolder dlFolder = DLFolderLocalServiceUtil.getDLFolder(classPK);

		List<DLFileEntry> fileEntries =
			DLFileEntryLocalServiceUtil.getGroupFileEntries(
				dlFolder.getGroupId(), 0, classPK, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (DLFileEntry fileEntry : fileEntries) {
			DLAppHelperLocalServiceUtil.deleteFileEntry(
				new LiferayFileEntry(fileEntry));
		}

		DLAppHelperLocalServiceUtil.deleteFolder(new LiferayFolder(dlFolder));

		DLFolderLocalServiceUtil.deleteFolder(classPK, false);
	}

	@Override
	public String getClassName() {
		return DLFolder.class.getName();
	}

	@Override
	public String getDeleteMessage() {
		return "found-in-deleted-folder-x";
	}

	@Override
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException, SystemException {

		DLFolder dlFolder = getDLFolder(classPK);

		long parentFolderId = dlFolder.getParentFolderId();

		if (parentFolderId <= 0) {
			return null;
		}

		return getContainerModel(parentFolderId);
	}

	@Override
	public String getRestoreContainedModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		DLFolder dlFolder = getDLFolder(classPK);

		return DLUtil.getDLFolderControlPanelLink(
			portletRequest, dlFolder.getFolderId());
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		DLFolder dlFolder = getDLFolder(classPK);

		return DLUtil.getDLFolderControlPanelLink(
			portletRequest, dlFolder.getParentFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException, SystemException {

		DLFolder dlFolder = getDLFolder(classPK);

		return DLUtil.getAbsolutePath(
			portletRequest, dlFolder.getParentFolderId());
	}

	@Override
	public String getSystemEventClassName() {
		return DLFolderConstants.getClassName();
	}

	@Override
	public TrashEntry getTrashEntry(long classPK)
		throws PortalException, SystemException {

		DLFolder dlFolder = getDLFolder(classPK);

		return dlFolder.getTrashEntry();
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK)
		throws PortalException, SystemException {

		Folder folder = DLAppLocalServiceUtil.getFolder(classPK);

		return new DLFolderAssetRenderer(folder);
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException, SystemException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return DLFolderPermission.contains(
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

		try {
			DLFolder dlFolder = getDLFolder(classPK);

			return dlFolder.isInTrash();
		}
		catch (InvalidRepositoryException ire) {
			return false;
		}
	}

	@Override
	public boolean isInTrashContainer(long classPK)
		throws PortalException, SystemException {

		try {
			DLFolder dlFolder = getDLFolder(classPK);

			return dlFolder.isInTrashContainer();
		}
		catch (InvalidRepositoryException ire) {
			return false;
		}
	}

	@Override
	public boolean isRestorable(long classPK)
		throws PortalException, SystemException {

		DLFolder dlFolder = fetchDLFolder(classPK);

		if ((dlFolder == null) ||
			((dlFolder.getParentFolderId() > 0) &&
			 (DLFolderLocalServiceUtil.fetchFolder(
				dlFolder.getParentFolderId()) == null))) {

			return false;
		}

		return !dlFolder.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFolderLocalServiceUtil.moveFolder(
			userId, classPK, containerModelId, serviceContext);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Repository repository = getRepository(classPK);

		DLAppHelperLocalServiceUtil.moveFolderFromTrash(
			userId, repository.getFolder(classPK), containerModelId,
			serviceContext);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException, SystemException {

		Repository repository = getRepository(classPK);

		DLAppHelperLocalServiceUtil.restoreFolderFromTrash(
			userId, repository.getFolder(classPK));
	}

	@Override
	public void updateTitle(long classPK, String name)
		throws PortalException, SystemException {

		DLFolder dlFolder = getDLFolder(classPK);

		dlFolder.setName(name);

		DLFolderLocalServiceUtil.updateDLFolder(dlFolder);
	}

	protected void checkRestorableEntry(
			long classPK, long trashEntryId, long containerModelId,
			String originalTitle, String newName)
		throws PortalException, SystemException {

		DLFolder dlFolder = getDLFolder(classPK);

		if (containerModelId == TrashEntryConstants.DEFAULT_CONTAINER_ID) {
			containerModelId = dlFolder.getParentFolderId();
		}

		if (Validator.isNotNull(newName)) {
			originalTitle = newName;
		}

		DLFolder duplicateDLFolder = DLFolderLocalServiceUtil.fetchFolder(
			dlFolder.getGroupId(), containerModelId, originalTitle);

		if (duplicateDLFolder != null) {
			RestoreEntryException ree = new RestoreEntryException(
				RestoreEntryException.DUPLICATE);

			ree.setDuplicateEntryId(duplicateDLFolder.getFolderId());
			ree.setOldName(duplicateDLFolder.getName());
			ree.setTrashEntryId(trashEntryId);

			throw ree;
		}
	}

	@Override
	protected Repository getRepository(long classPK)
		throws PortalException, SystemException {

		Repository repository = RepositoryServiceUtil.getRepositoryImpl(
			classPK, 0, 0);

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

		DLFolder dlFolder = getDLFolder(classPK);

		if (dlFolder.isInHiddenFolder() && actionId.equals(ActionKeys.VIEW)) {
			return false;
		}

		return DLFolderPermission.contains(
			permissionChecker, dlFolder, actionId);
	}

}