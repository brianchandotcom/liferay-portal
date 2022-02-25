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

import com.liferay.document.library.kernel.exception.DuplicateFileEntryTypeException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryTypeException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.exception.NoSuchMetadataSetException;
import com.liferay.document.library.kernel.exception.RequiredFileEntryTypeException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryPersistence;
import com.liferay.document.library.kernel.service.persistence.DLFolderPersistence;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureLink;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureLinkManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManager;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManager;
import com.liferay.dynamic.data.mapping.kernel.StructureDefinitionException;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryTypeLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
	public void addDDMStructureLinks(
		long fileEntryTypeId, Set<Long> ddmStructureIds) {

		long classNameId = _classNameLocalService.getClassNameId(
			DLFileEntryType.class);

		for (long ddmStructureId : ddmStructureIds) {
			DDMStructureLinkManagerUtil.addStructureLink(
				classNameId, fileEntryTypeId, ddmStructureId);
		}
	}

	@Override
	public DLFileEntryType addFileEntryType(
			long userId, long groupId, long dataDefinitionId,
			String fileEntryTypeKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int scope,
			ServiceContext serviceContext)
		throws PortalException {

		User user = _userPersistence.findByPrimaryKey(userId);

		if (Validator.isNull(fileEntryTypeKey)) {
			fileEntryTypeKey = String.valueOf(counterLocalService.increment());
		}
		else {
			fileEntryTypeKey = StringUtil.toUpperCase(fileEntryTypeKey.trim());
		}

		String fileEntryTypeUuid = GetterUtil.getString(
			serviceContext.getAttribute("fileEntryTypeUuid"));

		if (Validator.isNull(fileEntryTypeUuid)) {
			fileEntryTypeUuid = serviceContext.getUuid();

			if (Validator.isNull(fileEntryTypeUuid)) {
				fileEntryTypeUuid = PortalUUIDUtil.generate();
			}
		}

		_validateFileEntryTypeKey(groupId, fileEntryTypeKey);

		_validateDDMStructures(fileEntryTypeKey, new long[] {dataDefinitionId});

		DLFileEntryType dlFileEntryType = dlFileEntryTypePersistence.create(
			counterLocalService.increment());

		dlFileEntryType.setUuid(fileEntryTypeUuid);
		dlFileEntryType.setGroupId(groupId);
		dlFileEntryType.setCompanyId(user.getCompanyId());
		dlFileEntryType.setUserId(user.getUserId());
		dlFileEntryType.setUserName(user.getFullName());
		dlFileEntryType.setDataDefinitionId(dataDefinitionId);
		dlFileEntryType.setFileEntryTypeKey(fileEntryTypeKey);
		dlFileEntryType.setNameMap(nameMap);
		dlFileEntryType.setDescriptionMap(descriptionMap);
		dlFileEntryType.setScope(scope);

		dlFileEntryType = dlFileEntryTypePersistence.update(dlFileEntryType);

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addFileEntryTypeResources(
				dlFileEntryType, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addFileEntryTypeResources(
				dlFileEntryType, serviceContext.getModelPermissions());
		}

		return dlFileEntryType;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addFileEntryType(long, long, long, String, Map, Map, long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public DLFileEntryType addFileEntryType(
			long userId, long groupId, long dataDefinitionId,
			String fileEntryTypeKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		return addFileEntryType(
			userId, groupId, dataDefinitionId, fileEntryTypeKey, nameMap,
			descriptionMap,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_SCOPE_DEFAULT,
			serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addFileEntryType(long, long, String, Map, Map, long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public DLFileEntryType addFileEntryType(
			long userId, long groupId, String fileEntryTypeKey,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException {

		User user = _userPersistence.findByPrimaryKey(userId);

		if (Validator.isNull(fileEntryTypeKey)) {
			fileEntryTypeKey = String.valueOf(counterLocalService.increment());
		}
		else {
			fileEntryTypeKey = StringUtil.toUpperCase(fileEntryTypeKey.trim());
		}

		String fileEntryTypeUuid = GetterUtil.getString(
			serviceContext.getAttribute("fileEntryTypeUuid"));

		if (Validator.isNull(fileEntryTypeUuid)) {
			fileEntryTypeUuid = serviceContext.getUuid();

			if (Validator.isNull(fileEntryTypeUuid)) {
				fileEntryTypeUuid = PortalUUIDUtil.generate();
			}
		}

		long fileEntryTypeId = counterLocalService.increment();

		ddmStructureIds = _updateDDMStructure(
			userId, fileEntryTypeUuid, fileEntryTypeId, groupId, nameMap,
			descriptionMap, ddmStructureIds, serviceContext);

		_validateFileEntryTypeKey(groupId, fileEntryTypeKey);

		_validateDDMStructures(fileEntryTypeKey, ddmStructureIds);

		DLFileEntryType dlFileEntryType = dlFileEntryTypePersistence.create(
			fileEntryTypeId);

		dlFileEntryType.setUuid(fileEntryTypeUuid);
		dlFileEntryType.setGroupId(groupId);
		dlFileEntryType.setCompanyId(user.getCompanyId());
		dlFileEntryType.setUserId(user.getUserId());
		dlFileEntryType.setUserName(user.getFullName());
		dlFileEntryType.setDataDefinitionId(ddmStructureIds[0]);
		dlFileEntryType.setFileEntryTypeKey(fileEntryTypeKey);
		dlFileEntryType.setNameMap(nameMap);
		dlFileEntryType.setDescriptionMap(descriptionMap);

		dlFileEntryType = dlFileEntryTypePersistence.update(dlFileEntryType);

		addDDMStructureLinks(
			fileEntryTypeId, SetUtil.fromArray(ddmStructureIds));

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addFileEntryTypeResources(
				dlFileEntryType, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addFileEntryTypeResources(
				dlFileEntryType, serviceContext.getModelPermissions());
		}

		return dlFileEntryType;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addFileEntryType(long, long, String, Map, Map, long,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public DLFileEntryType addFileEntryType(
			long userId, long groupId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException {

		return addFileEntryType(
			userId, groupId, null,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), name
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), description
			).build(),
			ddmStructureIds, serviceContext);
	}

	@Override
	public void cascadeFileEntryTypes(long userId, DLFolder dlFolder)
		throws PortalException {

		long[] groupIds = PortalUtil.getCurrentAndAncestorSiteGroupIds(
			dlFolder.getGroupId());

		List<DLFileEntryType> dlFileEntryTypes = getFolderFileEntryTypes(
			groupIds, dlFolder.getFolderId(), true);

		long defaultFileEntryTypeId = getDefaultFileEntryTypeId(
			dlFolder.getFolderId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(dlFolder.getCompanyId());
		serviceContext.setScopeGroupId(dlFolder.getGroupId());
		serviceContext.setUserId(userId);

		cascadeFileEntryTypes(
			userId, dlFolder.getGroupId(), dlFolder.getFolderId(),
			defaultFileEntryTypeId, getFileEntryTypeIds(dlFileEntryTypes),
			serviceContext);
	}

	@Override
	public DLFileEntryType createBasicDocumentDLFileEntryType() {
		DLFileEntryType dlFileEntryType = dlFileEntryTypePersistence.create(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		dlFileEntryType.setCompanyId(CompanyConstants.SYSTEM);
		dlFileEntryType.setFileEntryTypeKey(
			StringUtil.toUpperCase(
				DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT));
		dlFileEntryType.setName(
			DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT,
			LocaleUtil.getDefault());

		return dlFileEntryTypePersistence.update(dlFileEntryType);
	}

	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public void deleteFileEntryType(DLFileEntryType dlFileEntryType)
		throws PortalException {

		int count = _dlFileEntryPersistence.countByFileEntryTypeId(
			dlFileEntryType.getFileEntryTypeId());

		if (count > 0) {
			throw new RequiredFileEntryTypeException(
				"There are file entries of file entry type " +
					dlFileEntryType.getFileEntryTypeId());
		}

		DDMStructureLinkManagerUtil.deleteStructureLinks(
			_classNameLocalService.getClassNameId(DLFileEntryType.class),
			dlFileEntryType.getFileEntryTypeId());

		DDMStructure ddmStructure = DDMStructureManagerUtil.fetchStructure(
			dlFileEntryType.getDataDefinitionId());

		if (ddmStructure == null) {
			ddmStructure = DDMStructureManagerUtil.fetchStructure(
				dlFileEntryType.getGroupId(),
				_classNameLocalService.getClassNameId(
					DLFileEntryMetadata.class),
				DLUtil.getDDMStructureKey(dlFileEntryType));
		}

		if (ddmStructure == null) {
			ddmStructure = DDMStructureManagerUtil.fetchStructure(
				dlFileEntryType.getGroupId(),
				_classNameLocalService.getClassNameId(
					DLFileEntryMetadata.class),
				DLUtil.getDeprecatedDDMStructureKey(dlFileEntryType));
		}

		if (ddmStructure != null) {
			DDMStructureManagerUtil.deleteStructure(
				ddmStructure.getStructureId());
		}

		_resourceLocalService.deleteResource(
			dlFileEntryType.getCompanyId(), DLFileEntryType.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			dlFileEntryType.getFileEntryTypeId());

		dlFileEntryTypePersistence.remove(dlFileEntryType);
	}

	@Override
	public void deleteFileEntryType(long fileEntryTypeId)
		throws PortalException {

		DLFileEntryType dlFileEntryType =
			dlFileEntryTypePersistence.findByPrimaryKey(fileEntryTypeId);

		dlFileEntryTypeLocalService.deleteFileEntryType(dlFileEntryType);
	}

	@Override
	public void deleteFileEntryTypes(long groupId) throws PortalException {
		List<DLFileEntryType> dlFileEntryTypes =
			dlFileEntryTypePersistence.findByGroupId(groupId);

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			dlFileEntryTypeLocalService.deleteFileEntryType(dlFileEntryType);
		}
	}

	@Override
	public DLFileEntryType fetchDataDefinitionFileEntryType(
		long groupId, long dataDefinitionId) {

		return dlFileEntryTypePersistence.fetchByG_DDI(
			groupId, dataDefinitionId);
	}

	@Override
	public DLFileEntryType fetchFileEntryType(long fileEntryTypeId) {
		return dlFileEntryTypePersistence.fetchByPrimaryKey(fileEntryTypeId);
	}

	@Override
	public DLFileEntryType fetchFileEntryType(
		long groupId, String fileEntryTypeKey) {

		fileEntryTypeKey = StringUtil.toUpperCase(
			StringUtil.trim(fileEntryTypeKey));

		return dlFileEntryTypePersistence.fetchByG_F(groupId, fileEntryTypeKey);
	}

	@Override
	public DLFileEntryType getBasicDocumentDLFileEntryType()
		throws NoSuchFileEntryTypeException {

		DLFileEntryType dlFileEntryType =
			dlFileEntryTypePersistence.fetchByPrimaryKey(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		if (dlFileEntryType != null) {
			return dlFileEntryType;
		}

		return dlFileEntryTypeLocalService.createBasicDocumentDLFileEntryType();
	}

	@Override
	public long getDefaultFileEntryTypeId(long folderId)
		throws PortalException {

		folderId = _getFileEntryTypesPrimaryFolderId(folderId);

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = _dlFolderPersistence.findByPrimaryKey(folderId);

			return dlFolder.getDefaultFileEntryTypeId();
		}

		return DLFileEntryTypeConstants.COMPANY_ID_BASIC_DOCUMENT;
	}

	@Override
	public DLFileEntryType getFileEntryType(long fileEntryTypeId)
		throws PortalException {

		return dlFileEntryTypePersistence.findByPrimaryKey(fileEntryTypeId);
	}

	@Override
	public DLFileEntryType getFileEntryType(
			long groupId, String fileEntryTypeKey)
		throws PortalException {

		fileEntryTypeKey = StringUtil.toUpperCase(
			StringUtil.trim(fileEntryTypeKey));

		return dlFileEntryTypePersistence.findByG_F(groupId, fileEntryTypeKey);
	}

	@Override
	public List<DLFileEntryType> getFileEntryTypes(long[] groupIds) {
		return dlFileEntryTypePersistence.findByGroupId(groupIds);
	}

	@Override
	public List<DLFileEntryType> getFolderFileEntryTypes(
			long[] groupIds, long folderId, boolean inherited)
		throws PortalException {

		if (!inherited) {
			return _dlFolderPersistence.getDLFileEntryTypes(folderId);
		}

		folderId = _getFileEntryTypesPrimaryFolderId(folderId);

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return _dlFolderPersistence.getDLFileEntryTypes(folderId);
		}

		List<DLFileEntryType> dlFileEntryTypes = new ArrayList<>(
			getFileEntryTypes(groupIds));

		DLFileEntryType dlFileEntryType =
			dlFileEntryTypeLocalService.getBasicDocumentDLFileEntryType();

		dlFileEntryTypes.add(0, dlFileEntryType);

		return dlFileEntryTypes;
	}

	@Override
	public List<DLFileEntryType> search(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType, int start, int end,
		OrderByComparator<DLFileEntryType> orderByComparator) {

		return dlFileEntryTypeFinder.findByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType, start,
			end, orderByComparator);
	}

	@Override
	public int searchCount(
		long companyId, long[] groupIds, String keywords,
		boolean includeBasicFileEntryType) {

		return dlFileEntryTypeFinder.countByKeywords(
			companyId, groupIds, keywords, includeBasicFileEntryType);
	}

	@Override
	public void unsetFolderFileEntryTypes(long folderId) {
		List<DLFileEntryType> dlFileEntryTypes =
			_dlFolderPersistence.getDLFileEntryTypes(folderId);

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			_dlFolderPersistence.removeDLFileEntryType(
				folderId, dlFileEntryType);
		}
	}

	@Override
	public void updateDDMStructureLinks(
			long fileEntryTypeId, Set<Long> ddmStructureIds)
		throws PortalException {

		Set<Long> existingDDMStructureLinkStructureIds =
			getExistingDDMStructureLinkStructureIds(fileEntryTypeId);

		deleteDDMStructureLinks(
			fileEntryTypeId,
			getStaleDDMStructureLinkStructureIds(
				ddmStructureIds, existingDDMStructureLinkStructureIds));

		addDDMStructureLinks(
			fileEntryTypeId,
			getMissingDDMStructureLinkStructureIds(
				ddmStructureIds, existingDDMStructureLinkStructureIds));
	}

	@Override
	public DLFileEntry updateFileEntryFileEntryType(
			DLFileEntry dlFileEntry, ServiceContext serviceContext)
		throws PortalException {

		long groupId = serviceContext.getScopeGroupId();
		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		DLFolder dlFolder = _dlFolderPersistence.fetchByPrimaryKey(
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

		DLFileVersion dlFileVersion =
			_dlFileVersionLocalService.getLatestFileVersion(
				dlFileEntry.getFileEntryId(), true);

		if (dlFileVersion.isPending()) {
			_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
				dlFileVersion.getCompanyId(), dlFileEntry.getGroupId(),
				DLFileEntry.class.getName(), dlFileVersion.getFileVersionId());
		}

		return _dlFileEntryLocalService.updateFileEntry(
			serviceContext.getUserId(), dlFileEntry.getFileEntryId(), null,
			null, null, null, null, null,
			DLVersionNumberIncrease.fromMajorVersion(false),
			getDefaultFileEntryTypeId(folderId), null, null, null, 0, null,
			null, serviceContext);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #updateFileEntryType(long, Map, Map)}
	 */
	@Deprecated
	@Override
	public void updateFileEntryType(
			long userId, long fileEntryTypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long[] ddmStructureIds,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileEntryType dlFileEntryType =
			dlFileEntryTypePersistence.findByPrimaryKey(fileEntryTypeId);

		ddmStructureIds = _updateDDMStructure(
			userId, dlFileEntryType.getUuid(), fileEntryTypeId,
			dlFileEntryType.getGroupId(), nameMap, descriptionMap,
			ddmStructureIds, serviceContext);

		_validateDDMStructures(
			dlFileEntryType.getFileEntryTypeKey(), ddmStructureIds);

		dlFileEntryType.setNameMap(nameMap);
		dlFileEntryType.setDescriptionMap(descriptionMap);

		dlFileEntryTypePersistence.update(dlFileEntryType);

		updateDDMStructureLinks(
			fileEntryTypeId, SetUtil.fromArray(ddmStructureIds));
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #updateFileEntryType(long, Map, Map)}
	 */
	@Deprecated
	@Override
	public void updateFileEntryType(
			long userId, long fileEntryTypeId, String name, String description,
			long[] ddmStructureIds, ServiceContext serviceContext)
		throws PortalException {

		updateFileEntryType(
			userId, fileEntryTypeId,
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), name
			).build(),
			HashMapBuilder.put(
				LocaleUtil.getSiteDefault(), description
			).build(),
			ddmStructureIds, serviceContext);
	}

	@Override
	public DLFileEntryType updateFileEntryType(
			long fileEntryTypeId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap)
		throws PortalException {

		DLFileEntryType dlFileEntryType =
			dlFileEntryTypePersistence.findByPrimaryKey(fileEntryTypeId);

		dlFileEntryType.setNameMap(nameMap);
		dlFileEntryType.setDescriptionMap(descriptionMap);

		return dlFileEntryTypePersistence.update(dlFileEntryType);
	}

	@Override
	public void updateFolderFileEntryTypes(
		DLFolder dlFolder, List<Long> fileEntryTypeIds,
		long defaultFileEntryTypeId, ServiceContext serviceContext) {

		List<Long> originalFileEntryTypeIds = getFileEntryTypeIds(
			_dlFolderPersistence.getDLFileEntryTypes(dlFolder.getFolderId()));

		if (fileEntryTypeIds.equals(originalFileEntryTypeIds)) {
			return;
		}

		for (Long fileEntryTypeId : fileEntryTypeIds) {
			if (!originalFileEntryTypeIds.contains(fileEntryTypeId)) {
				if (fileEntryTypeId ==
						DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_ALL) {

					continue;
				}

				DLFileEntryType dlFileEntryType =
					DLFileEntryTypeLocalServiceUtil.fetchDLFileEntryType(
						fileEntryTypeId);

				if (dlFileEntryType == null) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							"Document library file entry type " +
								fileEntryTypeId + " does not exist");
					}

					continue;
				}

				_dlFolderPersistence.addDLFileEntryType(
					dlFolder.getFolderId(), fileEntryTypeId);
			}
		}

		for (Long originalFileEntryTypeId : originalFileEntryTypeIds) {
			if (!fileEntryTypeIds.contains(originalFileEntryTypeId)) {
				_dlFolderPersistence.removeDLFileEntryType(
					dlFolder.getFolderId(), originalFileEntryTypeId);

				_workflowDefinitionLinkLocalService.
					deleteWorkflowDefinitionLink(
						dlFolder.getCompanyId(), dlFolder.getGroupId(),
						DLFolder.class.getName(), dlFolder.getFolderId(),
						originalFileEntryTypeId);
			}
		}
	}

	protected void addFileEntryTypeResources(
			DLFileEntryType dlFileEntryType, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		_resourceLocalService.addResources(
			dlFileEntryType.getCompanyId(), dlFileEntryType.getGroupId(),
			dlFileEntryType.getUserId(), DLFileEntryType.class.getName(),
			dlFileEntryType.getFileEntryTypeId(), false, addGroupPermissions,
			addGuestPermissions);
	}

	protected void addFileEntryTypeResources(
			DLFileEntryType dlFileEntryType, ModelPermissions modelPermissions)
		throws PortalException {

		_resourceLocalService.addModelResources(
			dlFileEntryType.getCompanyId(), dlFileEntryType.getGroupId(),
			dlFileEntryType.getUserId(), DLFileEntryType.class.getName(),
			dlFileEntryType.getFileEntryTypeId(), modelPermissions);
	}

	protected void cascadeFileEntryTypes(
			long userId, long groupId, long folderId,
			long defaultFileEntryTypeId, List<Long> fileEntryTypeIds,
			ServiceContext serviceContext)
		throws PortalException {

		List<DLFileEntry> dlFileEntries = _dlFileEntryPersistence.findByG_F(
			groupId, folderId);

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			if (fileEntryTypeIds.contains(dlFileEntry.getFileEntryTypeId())) {
				continue;
			}

			DLFileVersion dlFileVersion =
				_dlFileVersionLocalService.getLatestFileVersion(
					dlFileEntry.getFileEntryId(), true);

			if (dlFileVersion.isPending()) {
				_workflowInstanceLinkLocalService.deleteWorkflowInstanceLink(
					dlFileVersion.getCompanyId(), groupId,
					DLFileEntry.class.getName(),
					dlFileVersion.getFileVersionId());
			}

			_dlFileEntryLocalService.updateFileEntryType(
				userId, dlFileEntry.getFileEntryId(), defaultFileEntryTypeId,
				serviceContext);

			_dlAppHelperLocalService.updateAsset(
				userId, new LiferayFileEntry(dlFileEntry),
				new LiferayFileVersion(dlFileVersion),
				serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(),
				serviceContext.getAssetLinkEntryIds());
		}

		List<DLFolder> subfolders = _dlFolderPersistence.findByG_M_P_H(
			groupId, false, folderId, false);

		for (DLFolder subfolder : subfolders) {
			if (subfolder.getRestrictionType() ==
					DLFolderConstants.RESTRICTION_TYPE_INHERIT) {

				continue;
			}

			cascadeFileEntryTypes(
				userId, groupId, subfolder.getFolderId(),
				defaultFileEntryTypeId, fileEntryTypeIds, serviceContext);
		}
	}

	protected void deleteDDMStructureLinks(
			long fileEntryTypeId, Set<Long> ddmStructureIds)
		throws PortalException {

		long classNameId = _classNameLocalService.getClassNameId(
			DLFileEntryType.class);

		for (long ddmStructureId : ddmStructureIds) {
			DDMStructureLinkManagerUtil.deleteStructureLink(
				classNameId, fileEntryTypeId, ddmStructureId);
		}
	}

	protected void fixDDMStructureKey(
			String fileEntryTypeUuid, long fileEntryTypeId, long groupId)
		throws PortalException {

		DDMStructure ddmStructure = DDMStructureManagerUtil.fetchStructure(
			groupId,
			_classNameLocalService.getClassNameId(DLFileEntryMetadata.class),
			DLUtil.getDeprecatedDDMStructureKey(fileEntryTypeId));

		if (ddmStructure != null) {
			DDMStructureManagerUtil.updateStructureKey(
				ddmStructure.getStructureId(),
				DLUtil.getDDMStructureKey(fileEntryTypeUuid));
		}
	}

	protected Set<Long> getExistingDDMStructureLinkStructureIds(
		long fileEntryTypeId) {

		long classNameId = _classNameLocalService.getClassNameId(
			DLFileEntryType.class);

		Set<Long> existingDDMStructureLinkStructureIds = new HashSet<>();

		List<DDMStructureLink> structureLinks =
			DDMStructureLinkManagerUtil.getStructureLinks(
				classNameId, fileEntryTypeId);

		for (DDMStructureLink structureLink : structureLinks) {
			existingDDMStructureLinkStructureIds.add(
				structureLink.getStructureId());
		}

		return existingDDMStructureLinkStructureIds;
	}

	protected List<Long> getFileEntryTypeIds(
		List<DLFileEntryType> dlFileEntryTypes) {

		List<Long> fileEntryTypeIds = new SortedArrayList<>();

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			fileEntryTypeIds.add(dlFileEntryType.getFileEntryTypeId());
		}

		return fileEntryTypeIds;
	}

	protected Set<Long> getMissingDDMStructureLinkStructureIds(
		Set<Long> ddmStructureIds, Set<Long> existingDDMStructureIds) {

		Set<Long> missingDDMStructureLinkStructureIds = new HashSet<>(
			ddmStructureIds);

		missingDDMStructureLinkStructureIds.removeAll(existingDDMStructureIds);

		return missingDDMStructureLinkStructureIds;
	}

	protected Set<Long> getStaleDDMStructureLinkStructureIds(
		Set<Long> ddmStructureIds, Set<Long> existingDDMStructureIds) {

		Set<Long> staleDDMStructureLinkStructureIds = new HashSet<>(
			existingDDMStructureIds);

		staleDDMStructureLinkStructureIds.removeAll(ddmStructureIds);

		return staleDDMStructureLinkStructureIds;
	}

	private void _deleteDDMStructure(long fileEntryTypeId, long ddmStructureId)
		throws PortalException {

		deleteDDMStructureLinks(
			fileEntryTypeId, Collections.singleton(ddmStructureId));

		DDMStructureManagerUtil.deleteStructure(ddmStructureId);
	}

	private DDMForm _getDDMForm(
		DDMStructure ddmStructure, ServiceContext serviceContext) {

		DDMForm ddmForm = (DDMForm)serviceContext.getAttribute("ddmForm");

		if (ddmForm != null) {
			return ddmForm;
		}

		if (ddmStructure != null) {
			return ddmStructure.getDDMForm();
		}

		return null;
	}

	private long _getFileEntryTypesPrimaryFolderId(long folderId)
		throws NoSuchFolderException {

		while (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = _dlFolderPersistence.findByPrimaryKey(folderId);

			if (dlFolder.getRestrictionType() ==
					DLFolderConstants.
						RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW) {

				break;
			}

			folderId = dlFolder.getParentFolderId();
		}

		return folderId;
	}

	private boolean _isEmptyDDMForm(DDMForm ddmForm) {
		if (ddmForm == null) {
			return true;
		}

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		if (ddmFormFields.isEmpty()) {
			return true;
		}

		return false;
	}

	private long[] _updateDDMStructure(
			long userId, String fileEntryTypeUuid, long fileEntryTypeId,
			long groupId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, long[] ddmStructureIds,
			ServiceContext serviceContext)
		throws PortalException {

		DDMStructure ddmStructure = null;

		try {
			fixDDMStructureKey(fileEntryTypeUuid, fileEntryTypeId, groupId);

			String ddmStructureKey = DLUtil.getDDMStructureKey(
				fileEntryTypeUuid);

			ddmStructure = DDMStructureManagerUtil.fetchStructure(
				groupId,
				_classNameLocalService.getClassNameId(
					DLFileEntryMetadata.class),
				ddmStructureKey);

			DDMForm ddmForm = _getDDMForm(ddmStructure, serviceContext);

			if (_isEmptyDDMForm(ddmForm)) {
				if (ddmStructure != null) {
					_deleteDDMStructure(
						fileEntryTypeId, ddmStructure.getStructureId());

					return ArrayUtil.remove(
						ddmStructureIds, ddmStructure.getStructureId());
				}

				return ddmStructureIds;
			}

			if (ddmStructure == null) {
				ddmStructure = DDMStructureManagerUtil.addStructure(
					userId, groupId, null,
					_classNameLocalService.getClassNameId(
						DLFileEntryMetadata.class),
					ddmStructureKey, nameMap, descriptionMap, ddmForm,
					StorageEngineManager.STORAGE_TYPE_DEFAULT,
					DDMStructureManager.STRUCTURE_TYPE_AUTO, serviceContext);
			}
			else {
				ddmStructure = DDMStructureManagerUtil.updateStructure(
					userId, ddmStructure.getStructureId(),
					ddmStructure.getParentStructureId(), nameMap,
					descriptionMap, ddmForm, serviceContext);
			}

			return ArrayUtil.append(
				ddmStructureIds, ddmStructure.getStructureId());
		}
		catch (StructureDefinitionException structureDefinitionException) {
			if (_log.isWarnEnabled()) {
				_log.warn(structureDefinitionException);
			}

			if (ddmStructure != null) {
				long ddmStructureId = ddmStructure.getStructureId();

				_deleteDDMStructure(fileEntryTypeId, ddmStructureId);

				return ArrayUtil.remove(ddmStructureIds, ddmStructureId);
			}

			return ddmStructureIds;
		}
	}

	private void _validateDDMStructures(
			String fileEntryTypeKey, long[] ddmStructureIds)
		throws NoSuchMetadataSetException {

		if (ddmStructureIds.length == 0) {
			throw new NoSuchMetadataSetException(
				"DDM structure IDs is empty for file entry type " +
					fileEntryTypeKey);
		}

		for (long ddmStructureId : ddmStructureIds) {
			DDMStructure ddmStructure = DDMStructureManagerUtil.fetchStructure(
				ddmStructureId);

			if (ddmStructure == null) {
				throw new NoSuchMetadataSetException(
					"{ddmStructureId=" + ddmStructureId + "}");
			}
		}
	}

	private void _validateFileEntryTypeKey(
			long groupId, String fileEntryTypeKey)
		throws DuplicateFileEntryTypeException {

		DLFileEntryType dlFileEntryType = dlFileEntryTypePersistence.fetchByG_F(
			groupId, fileEntryTypeKey);

		if (dlFileEntryType != null) {
			throw new DuplicateFileEntryTypeException(
				"A file entry type already exists for key " + fileEntryTypeKey);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryTypeLocalServiceImpl.class);

	@BeanReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

	@BeanReference(type = DLAppHelperLocalService.class)
	private DLAppHelperLocalService _dlAppHelperLocalService;

	@BeanReference(type = DLFileEntryLocalService.class)
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@BeanReference(type = DLFileEntryPersistence.class)
	private DLFileEntryPersistence _dlFileEntryPersistence;

	@BeanReference(type = DLFileVersionLocalService.class)
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@BeanReference(type = DLFolderPersistence.class)
	private DLFolderPersistence _dlFolderPersistence;

	@BeanReference(type = ResourceLocalService.class)
	private ResourceLocalService _resourceLocalService;

	@BeanReference(type = UserPersistence.class)
	private UserPersistence _userPersistence;

	@BeanReference(type = WorkflowDefinitionLinkLocalService.class)
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@BeanReference(type = WorkflowInstanceLinkLocalService.class)
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

}