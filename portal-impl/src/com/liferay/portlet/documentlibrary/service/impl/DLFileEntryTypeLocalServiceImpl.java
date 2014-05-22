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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileEntryTypeException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.NoSuchMetadataSetException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryTypeLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.RequiredStructureException;
import com.liferay.portlet.dynamicdatamapping.StructureXsdException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Provides the local service for accessing, adding, cascading, deleting, and
 * updating file and folder file entry types.
 *
 * @author Alexander Chow
 * @author Sergio González
 */
public class DLFileEntryTypeLocalServiceImpl
	extends DLFileEntryTypeLocalServiceBaseImpl {

	@Override
	public DLFileEntryType addFileEntryType(
			long userId, long groupId, String fileEntryTypeKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (Validator.isNull(fileEntryTypeKey)) {
			fileEntryTypeKey = String.valueOf(counterLocalService.increment());
		}
		else {
			fileEntryTypeKey = StringUtil.toUpperCase(fileEntryTypeKey.trim());
		}

		String fileEntryTypeUuid = serviceContext.getUuid();

		if (Validator.isNull(fileEntryTypeUuid)) {
			fileEntryTypeUuid = PortalUUIDUtil.generate();
		}

		long fileEntryTypeId = counterLocalService.increment();

		long ddmStructureId = updateDDMStructure(
			userId, fileEntryTypeUuid, fileEntryTypeId, groupId, nameMap,
			descriptionMap, serviceContext);

		if (ddmStructureId > 0) {
			ddmStructureIds = ArrayUtil.append(ddmStructureIds, ddmStructureId);
		}

		Date now = new Date();

		validate(fileEntryTypeId, groupId, fileEntryTypeKey, ddmStructureIds);

		DLFileEntryType dlFileEntryType = dlFileEntryTypePersistence.create(
			fileEntryTypeId);

		dlFileEntryType.setUuid(fileEntryTypeUuid);
		dlFileEntryType.setGroupId(groupId);
		dlFileEntryType.setCompanyId(user.getCompanyId());
		dlFileEntryType.setUserId(user.getUserId());
		dlFileEntryType.setUserName(user.getFullName());
		dlFileEntryType.setCreateDate(serviceContext.getCreateDate(now));
		dlFileEntryType.setModifiedDate(serviceContext.getModifiedDate(now));
		dlFileEntryType.setFileEntryTypeKey(fileEntryTypeKey);
		dlFileEntryType.setNameMap(nameMap);
		dlFileEntryType.setDescriptionMap(descriptionMap);

		dlFileEntryTypePersistence.update(dlFileEntryType);

		dlFileEntryTypePersistence.addDDMStructures(
			fileEntryTypeId, ddmStructureIds);

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addFileEntryTypeResources(
				dlFileEntryType, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addFileEntryTypeResources(
				dlFileEntryType, serviceContext.getGroupPermissions(),
				serviceContext.getGuestPermissions());
		}

		return dlFileEntryType;
	}

	@Override
	public DLFileEntryType addFileEntryType(
			long userId, long groupId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getSiteDefault(), name);

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(LocaleUtil.getSiteDefault(), description);

		return addFileEntryType(
			userId, groupId, null, nameMap, descriptionMap, ddmStructureIds,
			serviceContext);
	}

	@Override
	public void cascadeFileEntryTypes(long userId, DLFolder dlFolder)
		throws PortalException, SystemException {

		long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(
			dlFolder.getGroupId());

		List<DLFileEntryType> dlFileEntryTypes = getFolderFileEntryTypes(
			groupIds, dlFolder.getFolderId(), true);

		List<Long> fileEntryTypeIds = getFileEntryTypeIds(dlFileEntryTypes);

		long defaultFileEntryTypeId = getDefaultFileEntryTypeId(
			dlFolder.getFolderId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(dlFolder.getCompanyId());
		serviceContext.setScopeGroupId(dlFolder.getGroupId());
		serviceContext.setUserId(userId);

		cascadeFileEntryTypes(
			userId, dlFolder.getGroupId(), dlFolder.getFolderId(),
			defaultFileEntryTypeId, fileEntryTypeIds, serviceContext);
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE)
	public void deleteFileEntryType(DLFileEntryType dlFileEntryType)
		throws PortalException, SystemException {

		if (dlFileEntryPersistence.countByFileEntryTypeId(
				dlFileEntryType.getFileEntryTypeId()) > 0) {

			throw new RequiredStructureException(
				RequiredStructureException.REFERENCED_STRUCTURE);
		}

		DDMStructure ddmStructure = ddmStructureLocalService.fetchStructure(
			dlFileEntryType.getGroupId(),
			classNameLocalService.getClassNameId(DLFileEntryMetadata.class),
			DLUtil.getDDMStructureKey(dlFileEntryType));

		if (ddmStructure == null) {
			ddmStructure = ddmStructureLocalService.fetchStructure(
				dlFileEntryType.getGroupId(),
				classNameLocalService.getClassNameId(DLFileEntryMetadata.class),
				DLUtil.getDeprecatedDDMStructureKey(dlFileEntryType));
		}

		if (ddmStructure != null) {
			ddmStructureLocalService.deleteStructure(
				ddmStructure.getStructureId());
		}

		dlFileEntryTypePersistence.remove(dlFileEntryType);
	}

	@Override
	public void deleteFileEntryType(long fileEntryTypeId)
		throws PortalException, SystemException {

		DLFileEntryType dlFileEntryType =
			dlFileEntryTypePersistence.findByPrimaryKey(fileEntryTypeId);

		dlFileEntryTypeLocalService.deleteFileEntryType(dlFileEntryType);
	}

	@Override
	public void deleteFileEntryTypes(long groupId)
		throws PortalException, SystemException {

		List<DLFileEntryType> dlFileEntryTypes =
			dlFileEntryTypePersistence.findByGroupId(groupId);

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			dlFileEntryTypeLocalService.deleteFileEntryType(dlFileEntryType);
		}
	}

	@Override
	public DLFileEntryType fetchFileEntryType(long fileEntryTypeId)
		throws SystemException {

		return dlFileEntryTypePersistence.fetchByPrimaryKey(fileEntryTypeId);
	}

	@Override
	public DLFileEntryType fetchFileEntryType(
			long groupId, String fileEntryTypeKey)
		throws SystemException {

		fileEntryTypeKey = StringUtil.toUpperCase(fileEntryTypeKey.trim());

		return dlFileEntryTypePersistence.fetchByG_F(groupId, fileEntryTypeKey);
	}

	@Override
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

	@Override
	public DLFileEntryType getFileEntryType(long fileEntryTypeId)
		throws PortalException, SystemException {

		return dlFileEntryTypePersistence.findByPrimaryKey(fileEntryTypeId);
	}

	@Override
	public DLFileEntryType getFileEntryType(
			long groupId, String fileEntryTypeKey)
		throws PortalException, SystemException {

		fileEntryTypeKey = StringUtil.toUpperCase(fileEntryTypeKey.trim());

		return dlFileEntryTypePersistence.findByG_F(groupId, fileEntryTypeKey);
	}

	@Override
	public List<DLFileEntryType> getFileEntryTypes(long[] groupIds)
		throws SystemException {

		return dlFileEntryTypePersistence.findByGroupId(groupIds);
	}

	@Override
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
		else {
			dlFileEntryTypes = new ArrayList<DLFileEntryType>(
				getFileEntryTypes(groupIds));

			DLFileEntryType dlFileEntryType =
				dlFileEntryTypePersistence.findByPrimaryKey(
					DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

			dlFileEntryTypes.add(0, dlFileEntryType);
		}

		return dlFileEntryTypes;
	}

	@Override
	public List<DLFileEntryType> search(
			long companyId, long[] groupIds, String keywords,
			boolean includeBasicFileEntryType, int start, int end,
			OrderByComparator orderByComparator)
		throws SystemException {

		return dlFileEntryTypeFinder.findByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType, start,
			end, orderByComparator);
	}

	@Override
	public int searchCount(
			long companyId, long[] groupIds, String keywords,
			boolean includeBasicFileEntryType)
		throws SystemException {

		return dlFileEntryTypeFinder.countByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType);
	}

	@Override
	public void unsetFolderFileEntryTypes(long folderId)
		throws SystemException {

		List<DLFileEntryType> dlFileEntryTypes =
			dlFolderPersistence.getDLFileEntryTypes(folderId);

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			dlFolderPersistence.removeDLFileEntryType(
				folderId, dlFileEntryType);
		}
	}

	@Override
	public DLFileEntry updateFileEntryFileEntryType(
			DLFileEntry dlFileEntry, ServiceContext serviceContext)
		throws PortalException, SystemException {

		long groupId = serviceContext.getScopeGroupId();
		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		DLFolder dlFolder = dlFolderPersistence.fetchByPrimaryKey(
			dlFileEntry.getFolderId());

		if (dlFolder != null) {
			groupId = dlFolder.getGroupId();
			folderId = dlFolder.getFolderId();
		}

		List<DLFileEntryType> dlFileEntryTypes = getFolderFileEntryTypes(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId), folderId,
			true);

		List<Long> fileEntryTypeIds = getFileEntryTypeIds(dlFileEntryTypes);

		if (fileEntryTypeIds.contains(dlFileEntry.getFileEntryTypeId())) {
			return dlFileEntry;
		}

		long defaultFileEntryTypeId = getDefaultFileEntryTypeId(folderId);

		DLFileVersion dlFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(
				dlFileEntry.getFileEntryId(), true);

		if (dlFileVersion.isPending()) {
			workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				dlFileVersion.getCompanyId(), dlFileEntry.getGroupId(),
				DLFileEntry.class.getName(), dlFileVersion.getFileVersionId());
		}

		return dlFileEntryLocalService.updateFileEntry(
			serviceContext.getUserId(), dlFileEntry.getFileEntryId(), null,
			null, null, null, null, false, defaultFileEntryTypeId, null, null,
			null, 0, serviceContext);
	}

	@Override
	public void updateFileEntryType(
			long userId, long fileEntryTypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long[] ddmStructureIds,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntryType dlFileEntryType =
			dlFileEntryTypePersistence.findByPrimaryKey(fileEntryTypeId);

		long ddmStructureId = updateDDMStructure(
			userId, dlFileEntryType.getUuid(), fileEntryTypeId,
			dlFileEntryType.getGroupId(), nameMap, descriptionMap,
			serviceContext);

		if (ddmStructureId > 0) {
			ddmStructureIds = ArrayUtil.append(ddmStructureIds, ddmStructureId);
		}

		dlFileEntryType.setModifiedDate(serviceContext.getModifiedDate(null));
		dlFileEntryType.setNameMap(nameMap);
		dlFileEntryType.setDescriptionMap(descriptionMap);

		dlFileEntryTypePersistence.update(dlFileEntryType);

		dlFileEntryTypePersistence.setDDMStructures(
			fileEntryTypeId, ddmStructureIds);
	}

	@Override
	public void updateFileEntryType(
			long userId, long fileEntryTypeId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Map<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getSiteDefault(), name);

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(LocaleUtil.getSiteDefault(), description);

		updateFileEntryType(
			userId, fileEntryTypeId, nameMap, descriptionMap, ddmStructureIds,
			serviceContext);
	}

	@Override
	public void updateFolderFileEntryTypes(
			DLFolder dlFolder, List<Long> fileEntryTypeIds,
			long defaultFileEntryTypeId, ServiceContext serviceContext)
		throws PortalException, SystemException {

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

				workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(
					dlFolder.getCompanyId(), dlFolder.getGroupId(),
					DLFolder.class.getName(), dlFolder.getFolderId(),
					originalFileEntryTypeId);
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
			long userId, long groupId, long folderId,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			ServiceContext serviceContext)
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

			dlFileEntryLocalService.updateFileEntry(
				userId, dlFileEntry.getFileEntryId(), null, null, null, null,
				null, false, defaultFileEntryTypeId, null, null, null, 0,
				serviceContext);

			dlAppHelperLocalService.updateAsset(
				userId, new LiferayFileEntry(dlFileEntry),
				new LiferayFileVersion(dlFileVersion),
				serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(),
				serviceContext.getAssetLinkEntryIds());
		}

		List<DLFolder> subFolders = dlFolderPersistence.findByG_M_P_H(
			groupId, false, folderId, false);

		for (DLFolder subFolder : subFolders) {
			long subFolderId = subFolder.getFolderId();

			if (subFolder.isOverrideFileEntryTypes()) {
				continue;
			}

			cascadeFileEntryTypes(
				userId, groupId, subFolderId, defaultFileEntryTypeId,
				fileEntryTypeIds, serviceContext);
		}
	}

	protected void fixDDMStructureKey(
			String fileEntryTypeUuid, long fileEntryTypeId, long groupId)
		throws SystemException {

		DDMStructure ddmStructure = ddmStructureLocalService.fetchStructure(
			groupId,
			classNameLocalService.getClassNameId(DLFileEntryMetadata.class),
			DLUtil.getDeprecatedDDMStructureKey(fileEntryTypeId));

		if (ddmStructure != null) {
			ddmStructure.setStructureKey(
				DLUtil.getDDMStructureKey(fileEntryTypeUuid));

			ddmStructureLocalService.updateDDMStructure(ddmStructure);
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
		throws NoSuchFolderException, SystemException {

		while (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(folderId);

			if (dlFolder.isOverrideFileEntryTypes()) {
				break;
			}

			folderId = dlFolder.getParentFolderId();
		}

		return folderId;
	}

	protected long updateDDMStructure(
			long userId, String fileEntryTypeUuid, long fileEntryTypeId,
			long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException, SystemException {

		fixDDMStructureKey(fileEntryTypeUuid, fileEntryTypeId, groupId);

		String ddmStructureKey = DLUtil.getDDMStructureKey(fileEntryTypeUuid);

		String xsd = ParamUtil.getString(serviceContext, "xsd");

		DDMStructure ddmStructure = ddmStructureLocalService.fetchStructure(
			groupId,
			classNameLocalService.getClassNameId(DLFileEntryMetadata.class),
			ddmStructureKey);

		if ((ddmStructure != null) && Validator.isNull(xsd)) {
			xsd = ddmStructure.getXsd();
		}

		try {
			if (ddmStructure == null) {
				ddmStructure = ddmStructureLocalService.addStructure(
					userId, groupId,
					DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
					classNameLocalService.getClassNameId(
						DLFileEntryMetadata.class),
					ddmStructureKey, nameMap, descriptionMap, xsd, "xml",
					DDMStructureConstants.TYPE_AUTO, serviceContext);
			}
			else {
				ddmStructure = ddmStructureLocalService.updateStructure(
					ddmStructure.getStructureId(),
					ddmStructure.getParentStructureId(), nameMap,
					descriptionMap, xsd, serviceContext);
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
			long fileEntryTypeId, long groupId, String fileEntryTypeKey,
			long[] ddmStructureIds)
		throws PortalException, SystemException {

		DLFileEntryType dlFileEntryType = dlFileEntryTypePersistence.fetchByG_F(
			groupId, fileEntryTypeKey);

		if ((dlFileEntryType != null) &&
			(dlFileEntryType.getFileEntryTypeId() != fileEntryTypeId)) {

			throw new DuplicateFileEntryTypeException(fileEntryTypeKey);
		}

		if (ddmStructureIds.length == 0) {
			throw new NoSuchMetadataSetException();
		}

		for (long ddmStructureId : ddmStructureIds) {
			DDMStructure ddmStructure =
				ddmStructurePersistence.fetchByPrimaryKey(ddmStructureId);

			if (ddmStructure == null) {
				throw new NoSuchMetadataSetException(
					"{ddmStructureId=" + ddmStructureId);
			}
		}
	}

}