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

package com.liferay.document.library.internal.exportimport.data.handler;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class DLFileEntryTypeStagedModelDataHandler
	extends BaseStagedModelDataHandler<DLFileEntryType> {

	public static final String[] CLASS_NAMES = {
		DLFileEntryType.class.getName()
	};

	@Override
	public void deleteStagedModel(DLFileEntryType fileEntryType)
		throws PortalException {

		_dlFileEntryTypeLocalService.deleteFileEntryType(fileEntryType);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DLFileEntryType dlFileEntryType = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (dlFileEntryType != null) {
			deleteStagedModel(dlFileEntryType);
		}
	}

	@Override
	public DLFileEntryType fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _dlFileEntryTypeLocalService.
			fetchDLFileEntryTypeByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<DLFileEntryType> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _dlFileEntryTypeLocalService.
			getDLFileEntryTypesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<DLFileEntryType>());
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public Map<String, String> getReferenceAttributes(
		PortletDataContext portletDataContext, DLFileEntryType fileEntryType) {

		long defaultUserId = UserConstants.USER_ID_DEFAULT;

		try {
			defaultUserId = _userLocalService.getDefaultUserId(
				fileEntryType.getCompanyId());
		}
		catch (Exception e) {
		}

		boolean preloaded = false;

		if ((fileEntryType.getFileEntryTypeId() ==
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) ||
			(defaultUserId == fileEntryType.getUserId())) {

			preloaded = true;
		}

		Map<String, String> referenceAttributes = HashMapBuilder.put(
			"file-entry-type-key", fileEntryType.getFileEntryTypeKey()
		).put(
			"preloaded", String.valueOf(preloaded)
		).build();

		return referenceAttributes;
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		validateMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		groupId = MapUtil.getLong(groupIds, groupId);

		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		if (!preloaded) {
			return super.validateMissingReference(uuid, groupId);
		}

		String fileEntryTypeKey = referenceElement.attributeValue(
			"file-entry-type-key");

		DLFileEntryType existingFileEntryType =
			fetchExistingFileEntryTypeWithParentGroups(
				uuid, groupId, fileEntryTypeKey, preloaded);

		if (existingFileEntryType == null) {
			return false;
		}

		return true;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			DLFileEntryType fileEntryType)
		throws Exception {

		Element fileEntryTypeElement = portletDataContext.getExportDataElement(
			fileEntryType);

		List<DDMStructure> ddmStructures = fileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			com.liferay.dynamic.data.mapping.model.DDMStructure structure =
				_ddmStructureLocalService.getStructure(
					ddmStructure.getStructureId());

			Element referenceElement =
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, fileEntryType, structure,
					PortletDataContext.REFERENCE_TYPE_STRONG);

			referenceElement.addAttribute(
				"structure-id", String.valueOf(ddmStructure.getStructureId()));
		}

		long defaultUserId = _userLocalService.getDefaultUserId(
			fileEntryType.getCompanyId());

		if (defaultUserId == fileEntryType.getUserId()) {
			fileEntryTypeElement.addAttribute("preloaded", "true");
		}

		portletDataContext.addClassedModel(
			fileEntryTypeElement,
			ExportImportPathUtil.getModelPath(fileEntryType), fileEntryType);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, Element referenceElement)
		throws PortletDataException {

		importMissingGroupReference(portletDataContext, referenceElement);

		String uuid = referenceElement.attributeValue("uuid");

		Map<Long, Long> groupIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Group.class);

		long groupId = GetterUtil.getLong(
			referenceElement.attributeValue("group-id"));

		groupId = MapUtil.getLong(groupIds, groupId);

		String fileEntryTypeKey = referenceElement.attributeValue(
			"file-entry-type-key");
		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		DLFileEntryType existingFileEntryType;

		if (!preloaded) {
			existingFileEntryType = fetchMissingReference(uuid, groupId);
		}
		else {
			existingFileEntryType = fetchExistingFileEntryTypeWithParentGroups(
				uuid, groupId, fileEntryTypeKey, preloaded);
		}

		if (existingFileEntryType == null) {
			return;
		}

		Map<Long, Long> fileEntryTypeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntryType.class);

		long fileEntryTypeId = GetterUtil.getLong(
			referenceElement.attributeValue("class-pk"));

		fileEntryTypeIds.put(
			fileEntryTypeId, existingFileEntryType.getFileEntryTypeId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			DLFileEntryType fileEntryType)
		throws Exception {

		long userId = portletDataContext.getUserId(fileEntryType.getUserUuid());

		List<Element> ddmStructureReferenceElements =
			portletDataContext.getReferenceElements(
				fileEntryType,
				com.liferay.dynamic.data.mapping.model.DDMStructure.class);

		long[] ddmStructureIdsArray =
			new long[ddmStructureReferenceElements.size()];

		Map<Long, Long> ddmStructureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				com.liferay.dynamic.data.mapping.model.DDMStructure.class);

		for (int i = 0; i < ddmStructureReferenceElements.size(); i++) {
			Element ddmStructureReferenceElement =
				ddmStructureReferenceElements.get(i);

			long ddmStructureId = GetterUtil.getLong(
				ddmStructureReferenceElement.attributeValue("class-pk"));

			ddmStructureIdsArray[i] = MapUtil.getLong(
				ddmStructureIds, ddmStructureId);
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileEntryType);

		DLFileEntryType importedDLFileEntryType = null;

		Element element = portletDataContext.getImportDataStagedModelElement(
			fileEntryType);

		boolean preloaded = GetterUtil.getBoolean(
			element.attributeValue("preloaded"));

		if (portletDataContext.isDataStrategyMirror()) {
			DLFileEntryType existingDLFileEntryType =
				fetchExistingFileEntryType(
					fileEntryType.getUuid(),
					portletDataContext.getScopeGroupId(),
					fileEntryType.getFileEntryTypeKey(), preloaded);

			if (existingDLFileEntryType == null) {
				serviceContext.setUuid(fileEntryType.getUuid());

				importedDLFileEntryType =
					_dlFileEntryTypeLocalService.addFileEntryType(
						userId, portletDataContext.getScopeGroupId(),
						fileEntryType.getFileEntryTypeKey(),
						fileEntryType.getNameMap(),
						fileEntryType.getDescriptionMap(), ddmStructureIdsArray,
						serviceContext);
			}
			else {
				_dlFileEntryTypeLocalService.updateFileEntryType(
					userId, existingDLFileEntryType.getFileEntryTypeId(),
					fileEntryType.getNameMap(),
					fileEntryType.getDescriptionMap(), ddmStructureIdsArray,
					serviceContext);

				importedDLFileEntryType =
					_dlFileEntryTypeLocalService.fetchDLFileEntryType(
						existingDLFileEntryType.getFileEntryTypeId());
			}
		}
		else {
			importedDLFileEntryType =
				_dlFileEntryTypeLocalService.addFileEntryType(
					userId, portletDataContext.getScopeGroupId(),
					fileEntryType.getFileEntryTypeKey(),
					fileEntryType.getNameMap(),
					fileEntryType.getDescriptionMap(), ddmStructureIdsArray,
					serviceContext);
		}

		portletDataContext.importClassedModel(
			fileEntryType, importedDLFileEntryType);

		if (preloaded) {
			return;
		}

		String importedDLFileEntryDDMStructureKey = DLUtil.getDDMStructureKey(
			importedDLFileEntryType);

		List<DDMStructure> importedDDMStructures =
			importedDLFileEntryType.getDDMStructures();

		for (DDMStructure importedDDMStructure : importedDDMStructures) {
			String ddmStructureKey = importedDDMStructure.getStructureKey();

			if (!DLUtil.isAutoGeneratedDLFileEntryTypeDDMStructureKey(
					ddmStructureKey)) {

				continue;
			}

			if (ddmStructureKey.equals(importedDLFileEntryDDMStructureKey)) {
				continue;
			}

			com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
				_ddmStructureLocalService.getDDMStructure(
					importedDDMStructure.getStructureId());

			ddmStructure.setStructureKey(importedDLFileEntryDDMStructureKey);

			_ddmStructureLocalService.updateDDMStructure(ddmStructure);
		}
	}

	protected DLFileEntryType fetchExistingFileEntryType(
		String uuid, long groupId, String fileEntryTypeKey, boolean preloaded) {

		DLFileEntryType existingDLFileEntryType = null;

		if (!preloaded) {
			existingDLFileEntryType = fetchStagedModelByUuidAndGroupId(
				uuid, groupId);
		}
		else {
			existingDLFileEntryType =
				_dlFileEntryTypeLocalService.fetchFileEntryType(
					groupId, fileEntryTypeKey);
		}

		return existingDLFileEntryType;
	}

	protected DLFileEntryType fetchExistingFileEntryTypeWithParentGroups(
		String uuid, long groupId, String fileEntryTypeKey, boolean preloaded) {

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return fetchExistingFileEntryType(
				uuid, groupId, fileEntryTypeKey, preloaded);
		}

		long companyId = group.getCompanyId();

		while (group != null) {
			DLFileEntryType existingDLFileEntryType =
				fetchExistingFileEntryType(
					uuid, group.getGroupId(), fileEntryTypeKey, preloaded);

			if (existingDLFileEntryType != null) {
				return existingDLFileEntryType;
			}

			group = group.getParentGroup();
		}

		Group companyGroup = _groupLocalService.fetchCompanyGroup(companyId);

		if (companyGroup == null) {
			return null;
		}

		return fetchExistingFileEntryType(
			uuid, companyGroup.getGroupId(), fileEntryTypeKey, preloaded);
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	@Reference(
		target = "(&(verify.process.name=com.liferay.document.library.service))",
		unbind = "-"
	)
	protected void setVerifyProcessCompletionMarker(Object object) {
	}

	private DDMStructureLocalService _ddmStructureLocalService;
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	private UserLocalService _userLocalService;

}