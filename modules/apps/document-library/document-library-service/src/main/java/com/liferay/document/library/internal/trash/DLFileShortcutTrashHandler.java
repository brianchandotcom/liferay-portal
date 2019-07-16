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

package com.liferay.document.library.internal.trash;

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ContainerModel;
import com.liferay.portal.kernel.model.TrashedModel;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.capabilities.UnsupportedCapabilityException;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionHelper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.trash.TrashRendererFactory;
import com.liferay.trash.constants.TrashActionKeys;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implements trash handling for the file shortcut entity.
 *
 * @author Zsolt Berentey
 */
@Component(
	property = "model.class.name=com.liferay.document.library.kernel.model.DLFileShortcut",
	service = TrashHandler.class
)
public class DLFileShortcutTrashHandler extends DLBaseTrashHandler {

	@Override
	public void deleteTrashEntry(long classPK) throws PortalException {
		_dlAppLocalService.deleteFileShortcut(classPK);
	}

	@Override
	public String getClassName() {
		return DLFileShortcutConstants.getClassName();
	}

	@Override
	public ContainerModel getParentContainerModel(long classPK)
		throws PortalException {

		DLFileShortcut dlFileShortcut = getDLFileShortcut(classPK);

		long parentFolderId = dlFileShortcut.getFolderId();

		if (parentFolderId <= 0) {
			return null;
		}

		return getContainerModel(parentFolderId);
	}

	@Override
	public ContainerModel getParentContainerModel(TrashedModel trashedModel)
		throws PortalException {

		DLFileShortcut dlFileShortcut = (DLFileShortcut)trashedModel;

		return getContainerModel(dlFileShortcut.getFolderId());
	}

	@Override
	public String getRestoreContainedModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		DLFileShortcut dlFileShortcut = getDLFileShortcut(classPK);

		return _dlURLHelper.getFileEntryControlPanelLink(
			portletRequest, dlFileShortcut.getToFileEntryId());
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		DLFileShortcut dlFileShortcut = getDLFileShortcut(classPK);

		return _dlURLHelper.getFolderControlPanelLink(
			portletRequest, dlFileShortcut.getFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException {

		DLFileShortcut dlFileShortcut = getDLFileShortcut(classPK);

		return DLUtil.getAbsolutePath(
			portletRequest, dlFileShortcut.getFolderId());
	}

	@Override
	public TrashedModel getTrashedModel(long classPK) {
		try {
			return getDLFileShortcut(classPK);
		}
		catch (PortalException | UnsupportedCapabilityException e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return null;
		}
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK) throws PortalException {
		return _trashRendererFactory.getTrashRenderer(classPK);
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException {

		if (trashActionId.equals(TrashActionKeys.MOVE)) {
			return ModelResourcePermissionHelper.contains(
				_folderModelResourcePermission, permissionChecker, groupId,
				classPK, ActionKeys.ADD_SHORTCUT);
		}

		return super.hasTrashPermission(
			permissionChecker, groupId, classPK, trashActionId);
	}

	@Override
	public boolean isMovable(long classPK) throws PortalException {
		DLFileShortcut dlFileShortcut = getDLFileShortcut(classPK);

		try {
			DLFolder parentFolder = dlFileShortcut.getDLFolder();

			return parentFolder.isInTrash();
		}
		catch (NoSuchFolderException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsfe, nsfe);
			}

			return true;
		}
	}

	@Override
	public boolean isRestorable(long classPK) throws PortalException {
		DLFileShortcut dlFileShortcut = getDLFileShortcut(classPK);

		try {
			dlFileShortcut.getFolder();
		}
		catch (NoSuchFolderException nsfe) {
			if (_log.isDebugEnabled()) {
				_log.debug(nsfe, nsfe);
			}

			return false;
		}

		if (!hasTrashPermission(
				PermissionThreadLocal.getPermissionChecker(),
				dlFileShortcut.getGroupId(), classPK,
				TrashActionKeys.RESTORE)) {

			return false;
		}

		return !dlFileShortcut.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileShortcut dlFileShortcut = getDLFileShortcut(classPK);

		_dlAppLocalService.updateFileShortcut(
			userId, classPK, containerModelId,
			dlFileShortcut.getToFileEntryId(), serviceContext);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		DocumentRepository documentRepository = getDocumentRepository(classPK);

		TrashCapability trashCapability = documentRepository.getCapability(
			TrashCapability.class);

		Folder newFolder = null;

		if (containerModelId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			newFolder = documentRepository.getFolder(containerModelId);
		}

		trashCapability.moveFileShortcutFromTrash(
			userId, documentRepository.getFileShortcut(classPK), newFolder,
			serviceContext);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		DocumentRepository documentRepository = getDocumentRepository(classPK);

		TrashCapability trashCapability = documentRepository.getCapability(
			TrashCapability.class);

		trashCapability.restoreFileShortcutFromTrash(
			userId, documentRepository.getFileShortcut(classPK));
	}

	protected DLFileShortcut getDLFileShortcut(long classPK)
		throws PortalException {

		Repository repository =
			RepositoryProviderUtil.getFileShortcutRepository(classPK);

		if (!repository.isCapabilityProvided(TrashCapability.class)) {
			throw new UnsupportedCapabilityException(
				TrashCapability.class,
				"Repository " + repository.getRepositoryId());
		}

		FileShortcut fileShortcut = repository.getFileShortcut(classPK);

		return (DLFileShortcut)fileShortcut.getModel();
	}

	@Override
	protected DocumentRepository getDocumentRepository(long classPK)
		throws PortalException {

		LocalRepository localRepository =
			RepositoryProviderUtil.getFileShortcutLocalRepository(classPK);

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

		DLFileShortcut dlFileShortcut = getDLFileShortcut(classPK);

		if (dlFileShortcut.isInHiddenFolder() &&
			actionId.equals(ActionKeys.VIEW)) {

			return false;
		}

		return _fileShortcutModelResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFileShortcut)",
		unbind = "-"
	)
	protected void setTrashRendererFactory(
		TrashRendererFactory trashRendererFactory) {

		_trashRendererFactory = trashRendererFactory;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileShortcutTrashHandler.class);

	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLURLHelper _dlURLHelper;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileShortcut)"
	)
	private ModelResourcePermission<FileShortcut>
		_fileShortcutModelResourcePermission;

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.Folder)"
	)
	private ModelResourcePermission<Folder> _folderModelResourcePermission;

	private TrashRendererFactory _trashRendererFactory;

}