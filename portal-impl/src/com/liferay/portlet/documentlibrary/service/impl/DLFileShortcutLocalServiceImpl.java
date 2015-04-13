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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLFileShortcutLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileShortcutLocalServiceImpl
	extends DLFileShortcutLocalServiceBaseImpl {

	@Override
	public DLFileShortcut addFileShortcut(
			long userId, long groupId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		// File shortcut

		User user = userPersistence.findByPrimaryKey(userId);
		folderId = getFolderId(user.getCompanyId(), folderId);
		Date now = new Date();

		validate(user, toFileEntryId);

		long fileShortcutId = counterLocalService.increment();

		DLFileShortcut fileShortcut = dlFileShortcutPersistence.create(
			fileShortcutId);

		fileShortcut.setUuid(serviceContext.getUuid());
		fileShortcut.setGroupId(groupId);
		fileShortcut.setCompanyId(user.getCompanyId());
		fileShortcut.setUserId(user.getUserId());
		fileShortcut.setUserName(user.getFullName());
		fileShortcut.setCreateDate(serviceContext.getCreateDate(now));
		fileShortcut.setModifiedDate(serviceContext.getModifiedDate(now));
		fileShortcut.setFolderId(folderId);
		fileShortcut.setToFileEntryId(toFileEntryId);
		fileShortcut.setTreePath(fileShortcut.buildTreePath());
		fileShortcut.setActive(true);
		fileShortcut.setStatus(WorkflowConstants.STATUS_APPROVED);
		fileShortcut.setStatusByUserId(userId);
		fileShortcut.setStatusByUserName(user.getFullName());
		fileShortcut.setStatusDate(now);

		dlFileShortcutPersistence.update(fileShortcut);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addFileShortcutResources(
				fileShortcut, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addFileShortcutResources(
				fileShortcut, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		// Folder

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			dlFolderLocalService.updateLastPostDate(
				folderId, fileShortcut.getModifiedDate());
		}

		// Asset

		FileEntry fileEntry = dlAppLocalService.getFileEntry(toFileEntryId);

		copyAssetTags(fileEntry, serviceContext);

		updateAsset(
			userId, fileShortcut, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		return fileShortcut;
	}

	@Override
	public void addFileShortcutResources(
			DLFileShortcut fileShortcut, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			fileShortcut.getCompanyId(), fileShortcut.getGroupId(),
			fileShortcut.getUserId(), DLFileShortcut.class.getName(),
			fileShortcut.getFileShortcutId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addFileShortcutResources(
			DLFileShortcut fileShortcut, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			fileShortcut.getCompanyId(), fileShortcut.getGroupId(),
			fileShortcut.getUserId(), DLFileShortcut.class.getName(),
			fileShortcut.getFileShortcutId(), groupPermissions,
			guestPermissions);
	}

	@Override
	public void addFileShortcutResources(
			long fileShortcutId, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		DLFileShortcut fileShortcut =
			dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);

		addFileShortcutResources(
			fileShortcut, addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addFileShortcutResources(
			long fileShortcutId, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException {

		DLFileShortcut fileShortcut =
			dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);

		addFileShortcutResources(
			fileShortcut, groupPermissions, guestPermissions);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteFileShortcut(DLFileShortcut fileShortcut)
		throws PortalException {

		// File shortcut

		dlFileShortcutPersistence.remove(fileShortcut);

		// Resources

		resourceLocalService.deleteResource(
			fileShortcut.getCompanyId(), DLFileShortcut.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			fileShortcut.getFileShortcutId());

		// Asset

		assetEntryLocalService.deleteEntry(
			DLFileShortcut.class.getName(), fileShortcut.getFileShortcutId());

		// Trash

		if (fileShortcut.isInTrashExplicitly()) {
			trashEntryLocalService.deleteEntry(
				DLFileShortcut.class.getName(),
				fileShortcut.getFileShortcutId());
		}
		else {
			trashVersionLocalService.deleteTrashVersion(
				DLFileShortcut.class.getName(),
				fileShortcut.getFileShortcutId());
		}
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId) throws PortalException {
		DLFileShortcut fileShortcut =
			dlFileShortcutLocalService.getDLFileShortcut(fileShortcutId);

		dlFileShortcutLocalService.deleteFileShortcut(fileShortcut);
	}

	@Override
	public void deleteFileShortcuts(long toFileEntryId) throws PortalException {
		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByToFileEntryId(toFileEntryId);

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			dlFileShortcutLocalService.deleteFileShortcut(fileShortcut);
		}
	}

	@Override
	public void deleteFileShortcuts(long groupId, long folderId)
		throws PortalException {

		deleteFileShortcuts(groupId, folderId, true);
	}

	@Override
	public void deleteFileShortcuts(
			long groupId, long folderId, boolean includeTrashedEntries)
		throws PortalException {

		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByG_F(groupId, folderId);

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			if (includeTrashedEntries || !fileShortcut.isInTrashExplicitly()) {
				dlFileShortcutLocalService.deleteFileShortcut(fileShortcut);
			}
		}
	}

	@Override
	public void disableFileShortcuts(long toFileEntryId) {
		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByToFileEntryId(toFileEntryId);

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			fileShortcut.setActive(false);

			dlFileShortcutPersistence.update(fileShortcut);
		}
	}

	@Override
	public void enableFileShortcuts(long toFileEntryId) {
		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByToFileEntryId(toFileEntryId);

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			fileShortcut.setActive(true);

			dlFileShortcutPersistence.update(fileShortcut);
		}
	}

	@Override
	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException {

		return dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);
	}

	@Override
	public List<DLFileShortcut> getFileShortcuts(
		long groupId, long folderId, boolean active, int status, int start,
		int end) {

		return dlFileShortcutPersistence.findByG_F_A_S(
			groupId, folderId, active, status, start, end);
	}

	@Override
	public int getFileShortcutsCount(
		long groupId, long folderId, boolean active, int status) {

		return dlFileShortcutPersistence.countByG_F_A_S(
			groupId, folderId, active, status);
	}

	@Override
	public void rebuildTree(long companyId) throws PortalException {
		dlFolderLocalService.rebuildTree(companyId);
	}

	@Override
	public void setTreePaths(final long folderId, final String treePath)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property folderIdProperty = PropertyFactoryUtil.forName(
						"folderId");

					dynamicQuery.add(folderIdProperty.eq(folderId));

					Property treePathProperty = PropertyFactoryUtil.forName(
						"treePath");

					dynamicQuery.add(treePathProperty.ne(treePath));
				}

			});

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object) {
					DLFileShortcut dlFileShortcut = (DLFileShortcut)object;

					dlFileShortcut.setTreePath(treePath);

					updateDLFileShortcut(dlFileShortcut);
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Override
	public void updateAsset(
			long userId, DLFileShortcut fileShortcut, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException {

		FileEntry fileEntry = dlAppLocalService.getFileEntry(
			fileShortcut.getToFileEntryId());

		assetEntryLocalService.updateEntry(
			userId, fileShortcut.getGroupId(), fileShortcut.getCreateDate(),
			fileShortcut.getModifiedDate(), DLFileShortcut.class.getName(),
			fileShortcut.getFileShortcutId(), fileShortcut.getUuid(), 0,
			assetCategoryIds, assetTagNames, false, null, null, null,
			fileEntry.getMimeType(), fileEntry.getTitle(),
			fileEntry.getDescription(), null, null, null, 0, 0, null);
	}

	@Override
	public DLFileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long folderId, long toFileEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		// File shortcut

		User user = userPersistence.findByPrimaryKey(userId);

		DLFileShortcut fileShortcut =
			dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);

		validate(user, toFileEntryId);

		fileShortcut.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		fileShortcut.setFolderId(folderId);
		fileShortcut.setToFileEntryId(toFileEntryId);
		fileShortcut.setTreePath(fileShortcut.buildTreePath());

		dlFileShortcutPersistence.update(fileShortcut);

		// Folder

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			dlFolderLocalService.updateLastPostDate(
				folderId, fileShortcut.getModifiedDate());
		}

		// Asset

		FileEntry fileEntry = dlAppLocalService.getFileEntry(toFileEntryId);

		copyAssetTags(fileEntry, serviceContext);

		updateAsset(
			userId, fileShortcut, serviceContext.getAssetCategoryIds(),
			serviceContext.getAssetTagNames());

		return fileShortcut;
	}

	@Override
	public void updateFileShortcuts(
		long oldToFileEntryId, long newToFileEntryId) {

		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByToFileEntryId(oldToFileEntryId);

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			fileShortcut.setToFileEntryId(newToFileEntryId);

			dlFileShortcutPersistence.update(fileShortcut);
		}
	}

	@Override
	public void updateStatus(
			long userId, long fileShortcutId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		DLFileShortcut fileShortcut =
			dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);

		fileShortcut.setStatus(status);
		fileShortcut.setStatusByUserId(user.getUserId());
		fileShortcut.setStatusByUserName(user.getFullName());
		fileShortcut.setStatusDate(serviceContext.getModifiedDate(new Date()));

		dlFileShortcutPersistence.update(fileShortcut);
	}

	protected void copyAssetTags(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		String[] assetTagNames = assetTagLocalService.getTagNames(
			FileEntry.class.getName(), fileEntry.getFileEntryId());

		assetTagLocalService.checkTags(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			assetTagNames);

		serviceContext.setAssetTagNames(assetTagNames);
	}

	protected long getFolderId(long companyId, long folderId) {
		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			// Ensure folder exists and belongs to the proper company

			DLFolder dlFolder = dlFolderPersistence.fetchByPrimaryKey(folderId);

			if ((dlFolder == null) || (companyId != dlFolder.getCompanyId())) {
				folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return folderId;
	}

	protected void validate(User user, long toFileEntryId)
		throws PortalException {

		FileEntry fileEntry = dlAppLocalService.getFileEntry(toFileEntryId);

		if (user.getCompanyId() != fileEntry.getCompanyId()) {
			throw new NoSuchFileEntryException(
				"{fileEntryId=" + toFileEntryId + "}");
		}
	}

}