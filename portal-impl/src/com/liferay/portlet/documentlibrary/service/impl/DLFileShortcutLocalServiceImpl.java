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

import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.kernel.service.persistence.DLFolderPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portlet.documentlibrary.service.base.DLFileShortcutLocalServiceBaseImpl;
import com.liferay.trash.kernel.service.TrashEntryLocalService;
import com.liferay.trash.kernel.service.TrashVersionLocalService;

import java.util.Date;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLFileShortcutLocalServiceImpl
	extends DLFileShortcutLocalServiceBaseImpl {

	@Override
	public DLFileShortcut addFileShortcut(
			long userId, long groupId, long repositoryId, long folderId,
			long toFileEntryId, ServiceContext serviceContext)
		throws PortalException {

		// File shortcut

		User user = _userPersistence.findByPrimaryKey(userId);

		folderId = getFolderId(user.getCompanyId(), folderId);

		validate(user, toFileEntryId);

		long fileShortcutId = counterLocalService.increment();

		DLFileShortcut fileShortcut = dlFileShortcutPersistence.create(
			fileShortcutId);

		fileShortcut.setUuid(serviceContext.getUuid());
		fileShortcut.setGroupId(groupId);
		fileShortcut.setCompanyId(user.getCompanyId());
		fileShortcut.setUserId(user.getUserId());
		fileShortcut.setUserName(user.getFullName());
		fileShortcut.setRepositoryId(repositoryId);
		fileShortcut.setFolderId(folderId);
		fileShortcut.setToFileEntryId(toFileEntryId);
		fileShortcut.setTreePath(fileShortcut.buildTreePath());
		fileShortcut.setActive(true);
		fileShortcut.setStatus(WorkflowConstants.STATUS_APPROVED);
		fileShortcut.setStatusByUserId(userId);
		fileShortcut.setStatusByUserName(user.getFullName());
		fileShortcut.setStatusDate(new Date());

		fileShortcut = dlFileShortcutPersistence.update(fileShortcut);

		// Resources

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addFileShortcutResources(
				fileShortcut, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addFileShortcutResources(
				fileShortcut, serviceContext.getModelPermissions());
		}

		// Folder

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			_dlFolderLocalService.updateLastPostDate(
				folderId, fileShortcut.getModifiedDate());
		}

		// Asset

		copyAssetTags(
			_dlAppLocalService.getFileEntry(toFileEntryId), serviceContext);

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

		_resourceLocalService.addResources(
			fileShortcut.getCompanyId(), fileShortcut.getGroupId(),
			fileShortcut.getUserId(), DLFileShortcutConstants.getClassName(),
			fileShortcut.getFileShortcutId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	@Override
	public void addFileShortcutResources(
			DLFileShortcut fileShortcut, ModelPermissions modelPermissions)
		throws PortalException {

		_resourceLocalService.addModelResources(
			fileShortcut.getCompanyId(), fileShortcut.getGroupId(),
			fileShortcut.getUserId(), DLFileShortcutConstants.getClassName(),
			fileShortcut.getFileShortcutId(), modelPermissions);
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
			long fileShortcutId, ModelPermissions modelPermissions)
		throws PortalException {

		DLFileShortcut fileShortcut =
			dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);

		addFileShortcutResources(fileShortcut, modelPermissions);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteFileShortcut(DLFileShortcut fileShortcut)
		throws PortalException {

		// File shortcut

		dlFileShortcutPersistence.remove(fileShortcut);

		// Resources

		_resourceLocalService.deleteResource(
			fileShortcut.getCompanyId(), DLFileShortcutConstants.getClassName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			fileShortcut.getFileShortcutId());

		// Asset

		assetEntryLocalService.deleteEntry(
			DLFileShortcutConstants.getClassName(),
			fileShortcut.getFileShortcutId());

		// Trash

		if (fileShortcut.isInTrashExplicitly()) {
			_trashEntryLocalService.deleteEntry(
				DLFileShortcutConstants.getClassName(),
				fileShortcut.getFileShortcutId());
		}
		else {
			_trashVersionLocalService.deleteTrashVersion(
				DLFileShortcutConstants.getClassName(),
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
	public void deleteRepositoryFileShortcuts(long repositoryId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			dlFileShortcutLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq("repositoryId", repositoryId)));

		long groupId = repositoryId;

		Repository repository = _repositoryLocalService.fetchRepository(
			repositoryId);

		if (repository != null) {
			groupId = repository.getGroupId();
		}

		actionableDynamicQuery.setGroupId(groupId);

		actionableDynamicQuery.setPerformActionMethod(
			(DLFileShortcut fileShortcut) ->
				dlFileShortcutLocalService.deleteDLFileShortcut(fileShortcut));

		actionableDynamicQuery.performActions();
	}

	@Override
	public void disableFileShortcuts(long toFileEntryId) {
		updateFileShortcutsActive(toFileEntryId, false);
	}

	@Override
	public void enableFileShortcuts(long toFileEntryId) {
		updateFileShortcutsActive(toFileEntryId, true);
	}

	@Override
	public DLFileShortcut getFileShortcut(long fileShortcutId)
		throws PortalException {

		return dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);
	}

	@Override
	public List<DLFileShortcut> getFileShortcuts(long toFileEntryId) {
		return dlFileShortcutPersistence.findByToFileEntryId(toFileEntryId);
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
		_dlFolderLocalService.rebuildTree(companyId);
	}

	@Override
	public void setTreePaths(long folderId, String treePath)
		throws PortalException {

		if (treePath == null) {
			throw new IllegalArgumentException("Tree path is null");
		}

		ActionableDynamicQuery actionableDynamicQuery =
			getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property folderIdProperty = PropertyFactoryUtil.forName(
					"folderId");

				dynamicQuery.add(folderIdProperty.eq(folderId));

				Property treePathProperty = PropertyFactoryUtil.forName(
					"treePath");

				dynamicQuery.add(
					RestrictionsFactoryUtil.or(
						treePathProperty.isNull(),
						treePathProperty.ne(treePath)));
			});

		actionableDynamicQuery.setPerformActionMethod(
			(DLFileShortcut dlFileShortcut) -> {
				dlFileShortcut.setTreePath(treePath);

				updateDLFileShortcut(dlFileShortcut);
			});

		actionableDynamicQuery.performActions();
	}

	@Override
	public void updateAsset(
			long userId, DLFileShortcut fileShortcut, long[] assetCategoryIds,
			String[] assetTagNames)
		throws PortalException {

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(
			fileShortcut.getToFileEntryId());

		assetEntryLocalService.updateEntry(
			userId, fileShortcut.getGroupId(), fileShortcut.getCreateDate(),
			fileShortcut.getModifiedDate(),
			DLFileShortcutConstants.getClassName(),
			fileShortcut.getFileShortcutId(), fileShortcut.getUuid(), 0,
			assetCategoryIds, assetTagNames, true, false, null, null,
			fileShortcut.getCreateDate(), null, fileEntry.getMimeType(),
			fileEntry.getTitle(), fileEntry.getDescription(), null, null, null,
			0, 0, null);
	}

	@Override
	public DLFileShortcut updateFileShortcut(
			long userId, long fileShortcutId, long repositoryId, long folderId,
			long toFileEntryId, ServiceContext serviceContext)
		throws PortalException {

		// File shortcut

		User user = _userPersistence.findByPrimaryKey(userId);

		DLFileShortcut fileShortcut =
			dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);

		validate(user, toFileEntryId);

		fileShortcut.setFolderId(folderId);
		fileShortcut.setToFileEntryId(toFileEntryId);
		fileShortcut.setTreePath(fileShortcut.buildTreePath());

		fileShortcut = dlFileShortcutPersistence.update(fileShortcut);

		// Folder

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			_dlFolderLocalService.updateLastPostDate(
				folderId, fileShortcut.getModifiedDate());
		}

		// Asset

		copyAssetTags(
			_dlAppLocalService.getFileEntry(toFileEntryId), serviceContext);

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
	public void updateFileShortcutsActive(long toFileEntryId, boolean active) {
		List<DLFileShortcut> fileShortcuts =
			dlFileShortcutPersistence.findByToFileEntryId(toFileEntryId);

		for (DLFileShortcut fileShortcut : fileShortcuts) {
			fileShortcut.setActive(active);

			dlFileShortcutPersistence.update(fileShortcut);
		}
	}

	@Override
	public DLFileShortcut updateStatus(
			long userId, long fileShortcutId, int status,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _userPersistence.findByPrimaryKey(userId);

		DLFileShortcut fileShortcut =
			dlFileShortcutPersistence.findByPrimaryKey(fileShortcutId);

		fileShortcut.setStatus(status);
		fileShortcut.setStatusByUserId(user.getUserId());
		fileShortcut.setStatusByUserName(user.getFullName());
		fileShortcut.setStatusDate(serviceContext.getModifiedDate(null));

		return dlFileShortcutPersistence.update(fileShortcut);
	}

	protected void copyAssetTags(
			FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException {

		String[] assetTagNames = _assetTagLocalService.getTagNames(
			FileEntry.class.getName(), fileEntry.getFileEntryId());

		_assetTagLocalService.checkTags(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			assetTagNames);

		serviceContext.setAssetTagNames(assetTagNames);
	}

	protected long getFolderId(long companyId, long folderId) {
		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			// Ensure folder exists and belongs to the proper company

			DLFolder dlFolder = _dlFolderPersistence.fetchByPrimaryKey(
				folderId);

			if ((dlFolder == null) || (companyId != dlFolder.getCompanyId())) {
				folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
			}
		}

		return folderId;
	}

	protected void validate(User user, long toFileEntryId)
		throws PortalException {

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(toFileEntryId);

		if (user.getCompanyId() != fileEntry.getCompanyId()) {
			throw new NoSuchFileEntryException(
				"{fileEntryId=" + toFileEntryId + "}");
		}
	}

	@BeanReference(type = AssetTagLocalService.class)
	private AssetTagLocalService _assetTagLocalService;

	@BeanReference(type = DLAppLocalService.class)
	private DLAppLocalService _dlAppLocalService;

	@BeanReference(type = DLFolderLocalService.class)
	private DLFolderLocalService _dlFolderLocalService;

	@BeanReference(type = DLFolderPersistence.class)
	private DLFolderPersistence _dlFolderPersistence;

	@BeanReference(type = RepositoryLocalService.class)
	private RepositoryLocalService _repositoryLocalService;

	@BeanReference(type = ResourceLocalService.class)
	private ResourceLocalService _resourceLocalService;

	@BeanReference(type = TrashEntryLocalService.class)
	@SuppressWarnings("deprecation")
	private TrashEntryLocalService _trashEntryLocalService;

	@BeanReference(type = TrashVersionLocalService.class)
	@SuppressWarnings("deprecation")
	private TrashVersionLocalService _trashVersionLocalService;

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

}