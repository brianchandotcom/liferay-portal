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
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryTypeLocalServiceBaseImpl;

import java.util.ArrayList;
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

		List<DLFileEntryType> dlFileEntryTypes = getFolderFileEntryTypes(
			new long[] {dlFolder.getGroupId()}, dlFolder.getFolderId(), true);

		List<Long> fileEntryTypeIds = getFileEntryTypeIds(dlFileEntryTypes);

		long defaultFileEntryTypeId = getDefaultFileEntryType(
			dlFolder.getFolderId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(dlFolder.getCompanyId());
		serviceContext.setScopeGroupId(dlFolder.getGroupId());
		serviceContext.setUserId(userId);

		cascadeFileEntryTypes(
			dlFolder.getGroupId(), dlFolder.getFolderId(),
			defaultFileEntryTypeId, fileEntryTypeIds, serviceContext);
	}

	public void deleteFileEntryType(long fileEntryTypeId)
		throws PortalException, SystemException {

		dlFileEntryTypePersistence.remove(fileEntryTypeId);
	}

	public void deleteFileEntryTypes(long folderId) throws SystemException {
		List<DLFileEntryType> dlFileEntryTypes =
			dlFolderPersistence.getDLFileEntryTypes(folderId);

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			dlFolderPersistence.removeDLFileEntryType(
				folderId, dlFileEntryType);
		}
	}

	public long getDefaultFileEntryType(long folderId)
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

	public List<DLFileEntryType> getFileEntryTypes(long[] groupIds)
		throws SystemException {

		return dlFileEntryTypePersistence.findByGroupId(groupIds);
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

	public List<DLFileEntryType> getFolderFileEntryTypes(
			long[] groupIds, long folderId, boolean inherited)
		throws PortalException, SystemException {

		if (!inherited) {
			return dlFolderPersistence.getDLFileEntryTypes(folderId);
		}

		List<DLFileEntryType> dlFileEntryTypes = null;

		folderId = getFileEntryTypesPrimaryFolderId(folderId);

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			dlFileEntryTypes = dlFolderPersistence.getDLFileEntryTypes(
				folderId);
		}

		if ((dlFileEntryTypes == null) || dlFileEntryTypes.isEmpty()) {
			dlFileEntryTypes = new ArrayList<DLFileEntryType>(
				getFileEntryTypes(groupIds));

			dlFileEntryTypes.add(new DLFileEntryTypeImpl());
		}

		return dlFileEntryTypes;
	}

	public List<DLFileEntryType> search(
			long companyId, long[] groupIds, String keywords, int start,
			int end, OrderByComparator orderByComparator)
		throws SystemException {

		return dlFileEntryTypeFinder.findByKeywords(
			companyId, groupIds, keywords, start, end, orderByComparator);
	}

	public int searchCount(long companyId, long[] groupIds, String keywords)
		throws SystemException {

		return dlFileEntryTypeFinder.countByKeywords(
			companyId, groupIds, keywords);
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

	public void updateFolderFileEntryTypes(
			DLFolder dlFolder, List<Long> fileEntryTypeIds,
			long defaultFileEntryTypeId, ServiceContext serviceContext)
		throws SystemException {

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
			DLFileEntryType dlFileEntryType, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			dlFileEntryType.getCompanyId(), dlFileEntryType.getGroupId(),
			dlFileEntryType.getUserId(), DLFileEntryType.class.getName(),
			dlFileEntryType.getFileEntryTypeId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	protected void addFileEntryTypeResources(
			DLFileEntryType dlFileEntryType, String[] groupPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			dlFileEntryType.getCompanyId(), dlFileEntryType.getGroupId(),
			dlFileEntryType.getUserId(), DLFileEntryType.class.getName(),
			dlFileEntryType.getFileEntryTypeId(), groupPermissions,
			guestPermissions);
	}

	protected void cascadeFileEntryTypes(
			long groupId, long folderId, long defaultFileEntryTypeId,
			List<Long> fileEntryTypeIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<DLFileEntry> dlFileEntries = dlFileEntryPersistence.findByG_F(
			groupId, folderId);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			Long fileEntryTypeId = dlFileEntry.getFileEntryTypeId();

			if (fileEntryTypeIds.contains(fileEntryTypeId)) {
				continue;
			}

			DLFileVersion dlFileVersion =
				dlFileVersionLocalService.getLatestFileVersion(
					dlFileEntry.getFileEntryId(), true);

			if (dlFileVersion.isPending()) {
				workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
					dlFileVersion.getCompanyId(), groupId,
					DLFileEntry.class.getName(),
					dlFileVersion.getFileVersionId());
			}

			dlFileEntryService.updateFileEntry(
				dlFileEntry.getFileEntryId(), null, null, null, null, null,
				false, defaultFileEntryTypeId, null, null, null, 0,
				serviceContext);
		}

		List<DLFolder> subFolders = dlFolderPersistence.findByG_P_M(
			groupId, folderId, false);

		for (DLFolder subFolder : subFolders) {
			long subFolderId = subFolder.getFolderId();

			if (subFolder.isOverrideFileEntryTypes()) {
				continue;
			}

			cascadeFileEntryTypes(
				groupId, subFolderId, defaultFileEntryTypeId, fileEntryTypeIds,
				serviceContext);
		}
	}

	protected List<Long> getFileEntryTypeIds(
		List<DLFileEntryType> dlFileEntryTypes) {

		List<Long> fileEntryTypeIds = new SortedArrayList<Long>();

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			fileEntryTypeIds.add(dlFileEntryType.getFileEntryTypeId());
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