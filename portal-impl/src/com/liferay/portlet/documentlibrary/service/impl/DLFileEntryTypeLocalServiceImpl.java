/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryTypeLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * @author Alexander Chow
 * @author Sergio González
 */
public class DLFileEntryTypeLocalServiceImpl
	extends DLFileEntryTypeLocalServiceBaseImpl {

	public DLFileEntryType addFileEntryType(
			long userId, long groupId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		verify(ddmStructureIds);

		long fileEntryTypeId = counterLocalService.increment();

		DLFileEntryType dlFileEntryType = dlFileEntryTypePersistence.create(
			fileEntryTypeId);

		dlFileEntryType.setGroupId(groupId);
		dlFileEntryType.setCompanyId(user.getCompanyId());
		dlFileEntryType.setUserId(user.getUserId());
		dlFileEntryType.setUserName(user.getFullName());
		dlFileEntryType.setCreateDate(serviceContext.getCreateDate(now));
		dlFileEntryType.setModifiedDate(serviceContext.getModifiedDate(now));
		dlFileEntryType.setName(name);
		dlFileEntryType.setDescription(description);

		dlFileEntryTypePersistence.update(dlFileEntryType, false);

		dlFileEntryTypePersistence.addDDMStructures(
			fileEntryTypeId, ddmStructureIds);

		if (serviceContext.getAddGroupPermissions() ||
			 serviceContext.getAddGuestPermissions()) {

			addFileEntryTypeResources(
				dlFileEntryType, serviceContext.getAddGroupPermissions(),
				serviceContext.getAddGuestPermissions());
		}
		else {
			addFileEntryTypeResources(
				dlFileEntryType, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		return dlFileEntryType;
	}

	public void cascadeFileEntryTypes(long userId, DLFolder dlFolder)
		throws PortalException, SystemException {

		long groupId = dlFolder.getGroupId();
		long folderId = dlFolder.getFolderId();

		List<DLFileEntryType> fileEntryTypes =
			getFileEntryTypesByFolder(groupId, folderId, true);

		List<Long> fileEntryTypeIds = getFileEntryTypeIds(fileEntryTypes);

		long defaultFileEntryTypeId = getDefaultFileEntryType(
			groupId, folderId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setUserId(userId);
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setCompanyId(dlFolder.getCompanyId());
		serviceContext.setAttribute("fileEntryTypeId", defaultFileEntryTypeId);

		cascadeFileEntryTypes(
			groupId, folderId, fileEntryTypeIds, serviceContext);
	}

	public void deleteFileEntryType(long fileEntryTypeId)
		throws PortalException, SystemException {

		dlFileEntryTypePersistence.remove(fileEntryTypeId);
	}

	public void deleteFileEntryTypes(long folderId)
		throws PortalException, SystemException {

		List<DLFileEntryType> fileEntryTypes =
			dlFolderPersistence.getDLFileEntryTypes(folderId);

		for (DLFileEntryType fileEntryType : fileEntryTypes) {
			dlFolderPersistence.removeDLFileEntryType(folderId, fileEntryType);
		}
	}

	public long getDefaultFileEntryType(long groupId, long folderId)
		throws PortalException, SystemException {

		folderId = getFileEntryTypesPrimaryFolderId(folderId);

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

			return dlFolder.getDefaultFileEntryTypeId();
		}
		else {
			return 0;
		}
	}

	public DLFileEntryType getFileEntryType(long fileEntryTypeId)
		throws PortalException, SystemException {

		return dlFileEntryTypePersistence.findByPrimaryKey(fileEntryTypeId);
	}

	public List<DLFileEntryType> getFileEntryTypes(long groupId)
		throws SystemException {

		return dlFileEntryTypePersistence.findByGroupId(groupId);
	}

	public List<DLFileEntryType> getFileEntryTypes(
			long groupId, int start, int end)
		throws SystemException {

		return dlFileEntryTypePersistence.findByGroupId(groupId, start, end);
	}

	public List<DLFileEntryType> getFileEntryTypes(
			long groupId, String name, String description)
		throws SystemException {

		return dlFileEntryTypePersistence.findByG_N_D(
			groupId, name, description);
	}

	public List<DLFileEntryType> getFileEntryTypesByFolder(
			long groupId, long folderId, boolean inherited)
		throws PortalException, SystemException {

		if (!inherited) {
			return dlFolderPersistence.getDLFileEntryTypes(folderId);
		}

		List<DLFileEntryType> fileEntryTypes = null;

		folderId = getFileEntryTypesPrimaryFolderId(folderId);

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			fileEntryTypes = dlFolderPersistence.getDLFileEntryTypes(folderId);
		}

		if (fileEntryTypes == null || fileEntryTypes.isEmpty()) {
			fileEntryTypes = getFileEntryTypes(groupId);

			fileEntryTypes.add(new DLFileEntryTypeImpl());
		}

		return fileEntryTypes;
	}

	public List<DLFileEntryType> search(
			long companyId, long groupId, String keywords, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return dlFileEntryTypeFinder.findByKeywords(
			companyId, groupId, keywords, start, end, orderByComparator);
	}

	public int searchCount(long companyId, long groupId, String keywords)
		throws SystemException {

		return dlFileEntryTypeFinder.countByKeywords(
			companyId, groupId, keywords);
	}

	public void updateFileEntryType(
			long fileEntryTypeId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryType dlFileEntryType =
			dlFileEntryTypePersistence.findByPrimaryKey(fileEntryTypeId);

		dlFileEntryType.setModifiedDate(serviceContext.getModifiedDate(null));
		dlFileEntryType.setName(name);
		dlFileEntryType.setDescription(description);

		dlFileEntryTypePersistence.update(dlFileEntryType, false);

		dlFileEntryTypePersistence.setDDMStructures(
			fileEntryTypeId, ddmStructureIds);
	}

	public void updateFileEntryTypesByFolder(
			DLFolder dlFolder, List<Long> fileEntryTypeIds,
			long defaultFileEntryTypeId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		// DLFolder

		List<Long> originalFileEntryTypeIds = getFileEntryTypeIds(
			dlFolderPersistence.getDLFileEntryTypes(dlFolder.getFolderId()));

		if (fileEntryTypeIds.equals(originalFileEntryTypeIds)) {
			return;
		}

		for (Long fileEntryTypeId : fileEntryTypeIds) {
			if (!originalFileEntryTypeIds.contains(fileEntryTypeId)) {
				dlFolderPersistence.addDLFileEntryType(
					dlFolder.getFolderId(), fileEntryTypeId);
			}
		}

		for (Long originalFileEntryTypeId : originalFileEntryTypeIds) {
			if (!fileEntryTypeIds.contains(originalFileEntryTypeId)) {
				dlFolderPersistence.removeDLFileEntryType(
					dlFolder.getFolderId(), originalFileEntryTypeId);
			}
		}
	}

	protected void addFileEntryTypeResources(
			DLFileEntryType fileEntryType, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			fileEntryType.getCompanyId(), fileEntryType.getGroupId(),
			fileEntryType.getUserId(), DLFileEntryType.class.getName(),
			fileEntryType.getFileEntryTypeId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	protected void addFileEntryTypeResources(
			DLFileEntryType fileEntryType, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			fileEntryType.getCompanyId(), fileEntryType.getGroupId(),
			fileEntryType.getUserId(), DLFileEntryType.class.getName(),
			fileEntryType.getFileEntryTypeId(), groupPermissions,
			guestPermissions);
	}

	protected void cascadeFileEntryTypes(
			long groupId, long folderId, List<Long> fileEntryTypeIds,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<DLFileEntry> dlFileEntries = dlFileEntryPersistence.findByG_F(
			groupId, folderId);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			Long fileEntryTypeId = dlFileEntry.getFileEntryTypeId();

			if (fileEntryTypeIds.contains(fileEntryTypeId)) {
				continue;
			}

			dlFileEntryService.updateFileEntry(
				dlFileEntry.getFileEntryId(), null, null, null, null, null,
				false, null, 0, serviceContext);
		}

		List<DLFolder> subFolders = dlFolderPersistence.findByG_P_M(
			groupId, folderId, false);

		for (DLFolder subFolder : subFolders) {
			long subFolderId = subFolder.getFolderId();

			if (subFolder.isOverrideFileEntryTypes()) {
				continue;
			}

			cascadeFileEntryTypes(
				groupId, subFolderId, fileEntryTypeIds, serviceContext);
		}
	}

	protected List<Long> getFileEntryTypeIds(
		List<DLFileEntryType> fileEntryTypes) {

		List<Long> fileEntryTypeIds = new SortedArrayList<Long>();

		for (DLFileEntryType fileEntryType : fileEntryTypes) {
			fileEntryTypeIds.add(fileEntryType.getFileEntryTypeId());
		}

		return fileEntryTypeIds;
	}

	protected long getFileEntryTypesPrimaryFolderId(long folderId)
		throws SystemException, NoSuchFolderException {

		while (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

			if (dlFolder.isOverrideFileEntryTypes()) {
				break;
			}

			folderId = dlFolder.getParentFolderId();
		}

		return folderId;
	}

	protected void verify(long[] ddmStructureIds)
		throws PortalException, SystemException {

		for (long ddmStructureId : ddmStructureIds) {
			ddmStructurePersistence.findByPrimaryKey(ddmStructureId);
		}
	}

}