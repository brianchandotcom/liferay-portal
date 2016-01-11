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

package com.liferay.journal.trash;

import com.liferay.journal.exception.InvalidDDMStructureException;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.service.permission.JournalFolderPermission;
import com.liferay.journal.util.impl.JournalUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.trash.TrashActionKeys;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ContainerModel;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.trash.RestoreEntryException;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.model.TrashEntryConstants;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Implements trash handling for the journal folder entity.
 *
 * @author Eudaldo Alonso
 */
@Component(
	property = {"model.class.name=com.liferay.journal.model.JournalFolder"},
	service = TrashHandler.class
)
public class JournalFolderTrashHandler extends JournalBaseTrashHandler {

	@Override
	public void checkRestorableEntry(
			long classPK, long containerModelId, String newName)
		throws PortalException {

		JournalFolder folder = _journalFolderLocalService.getFolder(classPK);

		checkRestorableEntry(
			classPK, 0, containerModelId, folder.getName(), newName);
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
		_journalFolderLocalService.deleteFolder(classPK, false);
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
		throws PortalException {

		JournalFolder folder = getJournalFolder(classPK);

		long parentFolderId = folder.getParentFolderId();

		if (parentFolderId <= 0) {
			return null;
		}

		return getContainerModel(parentFolderId);
	}

	@Override
	public String getRestoreContainedModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		JournalFolder folder = _journalFolderLocalService.getFolder(classPK);

		return JournalUtil.getJournalControlPanelLink(
			portletRequest, folder.getFolderId());
	}

	@Override
	public String getRestoreContainerModelLink(
			PortletRequest portletRequest, long classPK)
		throws PortalException {

		JournalFolder folder = _journalFolderLocalService.getFolder(classPK);

		return JournalUtil.getJournalControlPanelLink(
			portletRequest, folder.getParentFolderId());
	}

	@Override
	public String getRestoreMessage(PortletRequest portletRequest, long classPK)
		throws PortalException {

		JournalFolder folder = _journalFolderLocalService.getFolder(classPK);

		return JournalUtil.getAbsolutePath(
			portletRequest, folder.getParentFolderId());
	}

	@Override
	public TrashEntry getTrashEntry(long classPK) throws PortalException {
		JournalFolder folder = _journalFolderLocalService.getFolder(classPK);

		return folder.getTrashEntry();
	}

	@Override
	public TrashRenderer getTrashRenderer(long classPK) throws PortalException {
		AssetRendererFactory<JournalFolder> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClass(
				JournalFolder.class);

		return (TrashRenderer)assetRendererFactory.getAssetRenderer(classPK);
	}

	@Override
	public boolean hasTrashPermission(
			PermissionChecker permissionChecker, long groupId, long classPK,
			String trashActionId)
		throws PortalException {

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
	public boolean isInTrash(long classPK) throws PortalException {
		JournalFolder folder = _journalFolderLocalService.getFolder(classPK);

		return folder.isInTrash();
	}

	@Override
	public boolean isInTrashContainer(long classPK) throws PortalException {
		JournalFolder folder = getJournalFolder(classPK);

		return folder.isInTrashContainer();
	}

	@Override
	public boolean isRestorable(long classPK) throws PortalException {
		JournalFolder folder = getJournalFolder(classPK);

		if ((folder.getParentFolderId() > 0) &&
			(_journalFolderLocalService.fetchFolder(
				folder.getParentFolderId()) == null)) {

			return false;
		}

		return !folder.isInTrashContainer();
	}

	@Override
	public void moveEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		_journalFolderLocalService.moveFolder(
			classPK, containerModelId, serviceContext);
	}

	@Override
	public void moveTrashEntry(
			long userId, long classPK, long containerModelId,
			ServiceContext serviceContext)
		throws PortalException {

		_journalFolderLocalService.moveFolderFromTrash(
			userId, classPK, containerModelId, serviceContext);
	}

	@Override
	public void restoreTrashEntry(long userId, long classPK)
		throws PortalException {

		_journalFolderLocalService.restoreFolderFromTrash(userId, classPK);
	}

	@Override
	public void updateTitle(long classPK, String name) throws PortalException {
		JournalFolder folder = _journalFolderLocalService.getFolder(classPK);

		folder.setName(name);

		_journalFolderLocalService.updateJournalFolder(folder);
	}

	protected void checkDuplicateEntry(
			long classPK, long trashEntryId, long containerModelId,
			String originalTitle, String newName)
		throws PortalException {

		JournalFolder folder = _journalFolderLocalService.getFolder(classPK);

		if (containerModelId == TrashEntryConstants.DEFAULT_CONTAINER_ID) {
			containerModelId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;
		}

		if (Validator.isNotNull(newName)) {
			originalTitle = newName;
		}

		JournalFolder duplicateFolder = _journalFolderLocalService.fetchFolder(
			folder.getGroupId(), containerModelId, originalTitle);

		if (duplicateFolder != null) {
			RestoreEntryException ree = new RestoreEntryException(
				RestoreEntryException.DUPLICATE);

			ree.setDuplicateEntryId(duplicateFolder.getFolderId());
			ree.setOldName(duplicateFolder.getName());
			ree.setTrashEntryId(trashEntryId);

			throw ree;
		}
	}

	protected void checkRestorableEntry(
			long classPK, long trashEntryId, long containerModelId,
			String originalTitle, String newName)
		throws PortalException {

		checkValidContainer(classPK, containerModelId);

		checkDuplicateEntry(
			classPK, trashEntryId, containerModelId, originalTitle, newName);
	}

	protected void checkValidContainer(long classPK, long containerModelId)
		throws PortalException {

		try {
			_journalFolderLocalService.validateFolderDDMStructures(
				classPK, containerModelId);
		}
		catch (InvalidDDMStructureException iddmse) {
			throw new RestoreEntryException(
				RestoreEntryException.INVALID_CONTAINER, iddmse);
		}
	}

	@Override
	protected long getGroupId(long classPK) throws PortalException {
		JournalFolder folder = _journalFolderLocalService.getFolder(classPK);

		return folder.getGroupId();
	}

	protected JournalFolder getJournalFolder(long classPK)
		throws PortalException {

		JournalFolder folder = _journalFolderLocalService.getFolder(classPK);

		return folder;
	}

	@Override
	protected boolean hasPermission(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		JournalFolder folder = _journalFolderLocalService.getFolder(classPK);

		return JournalFolderPermission.contains(
			permissionChecker, folder, actionId);
	}

	@Reference(unbind = "-")
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = journalFolderLocalService;
	}

	private JournalFolderLocalService _journalFolderLocalService;

}