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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileEntryTypeException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.NoSuchMetadataSetException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryTypeLocalServiceBaseImpl;
import com.liferay.portlet.dynamicdatamapping.StructureXsdException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

		long fileEntryTypeId = counterLocalService.increment();

		long dynamicStructureId = updateDynamicStructure(
			userId, fileEntryTypeId, groupId, name, description,
			serviceContext);

		if (dynamicStructureId > 0) {
			ddmStructureIds = ArrayUtil.append(
				ddmStructureIds, dynamicStructureId);
		}

		Date now = new Date();

		validate(fileEntryTypeId, groupId, name, ddmStructureIds);

		DLFileEntryType dlFileEntryType = dlFileEntryTypePersistence.create(
			fileEntryTypeId);

		dlFileEntryType.setUuid(serviceContext.getUuid());
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

		long defaultFileEntryTypeId = getDefaultFileEntryTypeId(
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

		DLFileEntryType dlFileEntryType =
			dlFileEntryTypePersistence.findByPrimaryKey(fileEntryTypeId);

		DDMStructure ddmStructure = ddmStructureLocalService.fetchStructure(
			dlFileEntryType.getGroupId(), "auto_" + fileEntryTypeId);

		if (ddmStructure != null) {
			ddmStructureLocalService.deleteStructure(
				ddmStructure.getStructureId());
		}

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

	public long getDefaultFileEntryTypeId(long folderId)
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

	public DLFileEntryType getFileEntryType(long groupId, String name)
		throws PortalException, SystemException {

		return dlFileEntryTypePersistence.findByG_N(groupId, name);
	}

	public List<DLFileEntryType> getFileEntryTypes(
			long groupId, int start, int end)
		throws SystemException {

		return dlFileEntryTypePersistence.findByGroupId(groupId, start, end);
	}

	public List<DLFileEntryType> getFileEntryTypes(long[] groupIds)
		throws SystemException {

		return dlFileEntryTypePersistence.findByGroupId(groupIds);
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
			long userId, long fileEntryTypeId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryType dlFileEntryType =
			dlFileEntryTypePersistence.findByPrimaryKey(fileEntryTypeId);

		long dynamicStructureId = updateDynamicStructure(
			userId, fileEntryTypeId, dlFileEntryType.getGroupId(), name,
			description, serviceContext);

		if (dynamicStructureId > 0) {
			ddmStructureIds = ArrayUtil.append(
				ddmStructureIds, dynamicStructureId);
		}

		validate(
			fileEntryTypeId, dlFileEntryType.getGroupId(), name,
			ddmStructureIds);

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

	protected long updateDynamicStructure(
			long userId, long fileEntryTypeId, long groupId, String name,
			String description, ServiceContext serviceContext)
		throws SystemException, PortalException {

		String ddmStructureKey = "auto_" + fileEntryTypeId;

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		Locale locale = ServiceContextUtil.getLocale(serviceContext);

		nameMap.put(locale, name);

		Locale defaultLocale = LocaleUtil.getDefault();

		nameMap.put(defaultLocale, name);

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(locale, description);
		descriptionMap.put(defaultLocale, description);

		String xsd = ParamUtil.getString(serviceContext, "xsd");

		DDMStructure ddmStructure = ddmStructureLocalService.fetchStructure(
			groupId, ddmStructureKey);

		try {
			if (ddmStructure == null) {
				ddmStructure = ddmStructureLocalService.addStructure(
					userId, groupId,
					PortalUtil.getClassNameId(DLFileEntryMetadata.class),
					ddmStructureKey, nameMap, descriptionMap, xsd, "xml",
					serviceContext);
			}
			else {
				ddmStructure = ddmStructureLocalService.updateStructure(
					ddmStructure.getStructureId(), nameMap, descriptionMap, xsd,
					serviceContext);
			}

			return ddmStructure.getStructureId();
		}
		catch (StructureXsdException sxe) {
			if (ddmStructure != null) {
				ddmStructureLocalService.deleteStructure(
					ddmStructure.getStructureId());
			}
		}

		return 0;
	}

	protected void validate(
			long fileEntryTypeId, long groupId, String fileEntryTypeName,
			long[] ddmStructureIds)
		throws PortalException, SystemException {

		DLFileEntryType dlFileEntryType = dlFileEntryTypePersistence.fetchByG_N(
			groupId, fileEntryTypeName);

		if ((dlFileEntryType != null) &&
			(dlFileEntryType.getFileEntryTypeId() != fileEntryTypeId)) {

			throw new DuplicateFileEntryTypeException(fileEntryTypeName);
		}

		if (ddmStructureIds.length == 0) {
			throw new NoSuchMetadataSetException();
		}

		for (long ddmStructureId : ddmStructureIds) {
			DDMStructure ddmStructure =
				ddmStructurePersistence.fetchByPrimaryKey(ddmStructureId);

			if (ddmStructure == null) {
				throw new NoSuchMetadataSetException();
			}
		}
	}

}