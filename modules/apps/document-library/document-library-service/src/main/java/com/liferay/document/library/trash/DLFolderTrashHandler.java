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

package com.liferay.document.library.trash;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.capabilities.UnsupportedCapabilityException;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.trash.kernel.exception.RestoreEntryException;
import com.liferay.trash.kernel.model.TrashEntry;
import com.liferay.trash.kernel.model.TrashEntryConstants;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implements trash handling for the folder entity.
 *
 * @author Alexander Chow
 * @author Zsolt Berentey
 */
@Component(
	property = {
		"model.class.name=com.liferay.portlet.documentlibrary.model.DLFolder"
	},
	service = TrashHandler.class
)
public class DLFolderTrashHandler extends DLBaseTrashHandler {

	@Override
	public void checkRestorableEntry(
			long classPK, long containerModelId, String newName)
		throws PortalException {

		DLFolder dlFolder = getDLFolder(classPK);

		checkRestorableEntry(
			classPK, 0, containerModelId, dlFolder.getName(), newName);
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
		DocumentRepository documentRepository = getDocumentRepository(classPK);

		TrashCapability trashCapability = documentRepository.getCapability(
			TrashCapability.class);

		Folder folder = documentRepository.getFolder(classPK);

		trashCapability.deleteFolder(folder);
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
		throws PortalException {

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
		throws PortalException {

		DLFolder dlFolder = getDLFolder(classPK);

		return DLUtil.getDLFolderControlPanelLink(
			portletRequest, dlFolder.getFolderId());
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		DLFolder dlFolder = getDLFolder(classPK);

		return DLUtil.getDLFolderControlPanelLink(
			portletRequest, dlFolder.getParentFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException {

		DLFolder dlFolder = getDLFolder(classPK);

		return DLUtil.getAbsolutePath(
			portletRequest, dlFolder.getParentFolderId());
	}

	@Override
	public String getSubcontainerModelName() {
		return "folder";
	}

	@Override
	public String getSystemEventClassName() {
		return DLFolderConstants.getClassName();
	}

	@Override
	public TrashEntry getTrashEntry(long classPK) throws PortalException {
		DLFolder dlFolder = getDLFolder(classPK);

		return dlFolder.getTrashEntry();
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK) throws PortalException {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				DLFolder.class.getName());

		return (TrashRenderer)assetRendererFactory.getAssetRenderer(classPK);
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException {

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
	public boolean isInTrash(long classPK) throws PortalException {
		try {
			DLFolder dlFolder = getDLFolder(classPK);

			return dlFolder.isInTrash();
		}
		catch (UnsupportedCapabilityException uce) {
			if (_log.isDebugEnabled()) {
				_log.debug(uce, uce);
			}

			return false;
		}
	}

	@Override
	public boolean isInTrashContainer(long classPK) throws PortalException {
		try {
			DLFolder dlFolder = getDLFolder(classPK);

			return dlFolder.isInTrashContainer();
		}
		catch (UnsupportedCapabilityException uce) {
			if (_log.isDebugEnabled()) {
				_log.debug(uce, uce);
			}

			return false;
		}
	}

	@Override
	public boolean isRestorable(long classPK) throws PortalException {
		DLFolder dlFolder = fetchDLFolder(classPK);

		if ((dlFolder == null) ||
			((dlFolder.getParentFolderId() > 0) &&
			 (_dlFolderLocalService.fetchFolder(
				 dlFolder.getParentFolderId()) == null))) {

			return false;
		}

		return !dlFolder.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		_dlAppLocalService.moveFolder(
			userId, classPK, containerModelId, serviceContext);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		DocumentRepository documentRepository = getDocumentRepository(classPK);

		TrashCapability trashCapability = documentRepository.getCapability(
			TrashCapability.class);

		Folder folder = documentRepository.getFolder(classPK);

		Folder destinationFolder = null;

		if (containerModelId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			destinationFolder = documentRepository.getFolder(containerModelId);
		}

		trashCapability.moveFolderFromTrash(
			userId, folder, destinationFolder, serviceContext);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		DocumentRepository documentRepository = getDocumentRepository(classPK);

		TrashCapability trashCapability = documentRepository.getCapability(
			TrashCapability.class);

		Folder folder = documentRepository.getFolder(classPK);

		trashCapability.restoreFolderFromTrash(userId, folder);
	}

	@Override
	public void updateTitle(long classPK, String name) throws PortalException {
		DLFolder dlFolder = getDLFolder(classPK);

		dlFolder.setName(name);

		_dlFolderLocalService.updateDLFolder(dlFolder);
	}

	protected void checkRestorableEntry(
			long classPK, long trashEntryId, long containerModelId,
			String originalTitle, String newName)
		throws PortalException {

		DLFolder dlFolder = getDLFolder(classPK);

		if (containerModelId == TrashEntryConstants.DEFAULT_CONTAINER_ID) {
			containerModelId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		if (Validator.isNotNull(newName)) {
			originalTitle = newName;
		}

		DLFolder duplicateDLFolder = _dlFolderLocalService.fetchFolder(
			dlFolder.getGroupId(), containerModelId, originalTitle);

		if (duplicateDLFolder != null) {
			RestoreEntryException ree = new RestoreEntryException(
				RestoreEntryException.DUPLICATE);

			ree.setDuplicateEntryId(duplicateDLFolder.getFolderId());
			ree.setOldName(duplicateDLFolder.getName());
			ree.setTrashEntryId(trashEntryId);

			throw ree;
		}

		DLFileEntry duplicateDLFileEntry =
			_dlFileEntryLocalService.fetchFileEntry(
				dlFolder.getGroupId(), containerModelId, originalTitle);

		if (duplicateDLFileEntry != null) {
			RestoreEntryException ree = new RestoreEntryException(
				RestoreEntryException.DUPLICATE);

			ree.setDuplicateEntryId(duplicateDLFileEntry.getFileEntryId());
			ree.setOldName(duplicateDLFileEntry.getTitle());
			ree.setOverridable(false);
			ree.setTrashEntryId(trashEntryId);

			throw ree;
		}
	}

	@Override
	protected DocumentRepository getDocumentRepository(long classPK)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getFolderLocalRepository(classPK);

		if (!localRepository.isCapabilityProvided(TrashCapability.class)) {
			throw new UnsupportedCapabilityException(
				TrashCapability.class,
				"Repository " + localRepository.getRepositoryId());
		}

		return localRepository;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		DLFolder dlFolder = getDLFolder(classPK);

		if (dlFolder.isInHiddenFolder() && actionId.equals(ActionKeys.VIEW)) {
			return false;
		}

		return DLFolderPermission.contains(
			permissionChecker, dlFolder, actionId);
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		_dlFolderLocalService = dlFolderLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFolderTrashHandler.class);

	private DLAppLocalService _dlAppLocalService;
	private DLFileEntryLocalService _dlFileEntryLocalService;
	private DLFolderLocalService _dlFolderLocalService;

}