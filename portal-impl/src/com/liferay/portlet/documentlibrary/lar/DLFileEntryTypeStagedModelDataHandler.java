/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class DLFileEntryTypeStagedModelDataHandler
	extends BaseStagedModelDataHandler<DLFileEntryType> {

	public static final String[] CLASS_NAMES =
		{DLFileEntryType.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException, SystemException {

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.
				fetchDLFileEntryTypeByUuidAndGroupId(uuid, groupId);

		if (dlFileEntryType != null) {
			DLFileEntryTypeLocalServiceUtil.deleteFileEntryType(
				dlFileEntryType);
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public Map<String, String> getReferenceAttributes(
		PortletDataContext portletDataContext, DLFileEntryType fileEntryType) {

		Map<String, String> referenceAttributes = new HashMap<String, String>();

		referenceAttributes.put(
			"file-entry-type-key", fileEntryType.getFileEntryTypeKey());

		long defaultUserId = 0;

		try {
			defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				fileEntryType.getCompanyId());
		}
		catch (Exception e) {
			return referenceAttributes;
		}

		boolean preloaded = false;

		if (defaultUserId == fileEntryType.getUserId()) {
			preloaded = true;
		}

		referenceAttributes.put("preloaded", String.valueOf(preloaded));

		return referenceAttributes;
	}

	@Override
	public void importCompanyStagedModel(
			PortletDataContext portletDataContext, Element element)
		throws PortletDataException {

		String uuid = element.attributeValue("uuid");
		String fileEntryTypeKey = element.attributeValue("file-entry-type-key");
		boolean preloaded = GetterUtil.getBoolean(
			element.attributeValue("preloaded"));

		DLFileEntryType existingFileEntryType = null;

		try {
			existingFileEntryType = getExistingFileEntryType(
				uuid, portletDataContext.getCompanyGroupId(), fileEntryTypeKey,
				preloaded);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}

		Map<Long, Long> fileEntryTypeIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntryType.class);

		long fileEntryTypeId = GetterUtil.getLong(
			element.attributeValue("class-pk"));

		fileEntryTypeIds.put(
			fileEntryTypeId, existingFileEntryType.getFileEntryTypeId());
	}

	@Override
	public boolean validateReference(
		PortletDataContext portletDataContext, Element referenceElement) {

		String uuid = referenceElement.attributeValue("uuid");
		String fileEntryTypeKey = referenceElement.attributeValue(
			"file-entry-type-key");
		boolean preloaded = GetterUtil.getBoolean(
			referenceElement.attributeValue("preloaded"));

		try {
			DLFileEntryType existingFileEntryType =
				getExistingFileEntryType(
					uuid, portletDataContext.getScopeGroupId(),
					fileEntryTypeKey, preloaded);

			if (existingFileEntryType == null) {
				existingFileEntryType = getExistingFileEntryType(
					uuid, portletDataContext.getCompanyGroupId(),
					fileEntryTypeKey, preloaded);
			}

			if (existingFileEntryType == null) {
				return false;
			}

			return true;
		}
		catch (Exception e) {
			return false;
		}
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
			Element referenceElement =
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, fileEntryType, ddmStructure,
					PortletDataContext.REFERENCE_TYPE_STRONG);

			referenceElement.addAttribute(
				"structure-id",
				StringUtil.valueOf(ddmStructure.getStructureId()));
		}

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
			fileEntryType.getCompanyId());

		if (defaultUserId == fileEntryType.getUserId()) {
			fileEntryTypeElement.addAttribute("preloaded", "true");
		}

		portletDataContext.addClassedModel(
			fileEntryTypeElement,
			ExportImportPathUtil.getModelPath(fileEntryType), fileEntryType);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			DLFileEntryType fileEntryType)
		throws Exception {

		long userId = portletDataContext.getUserId(fileEntryType.getUserUuid());

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, fileEntryType, DDMStructure.class);

		List<Element> ddmStructureReferenceElements =
			portletDataContext.getReferenceElements(
				fileEntryType, DDMStructure.class);

		long[] ddmStructureIdsArray =
			new long[ddmStructureReferenceElements.size()];

		Map<Long, Long> ddmStructureIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

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
				getExistingFileEntryType(
					fileEntryType.getUuid(),
					portletDataContext.getScopeGroupId(),
					fileEntryType.getFileEntryTypeKey(), preloaded);

			if (existingDLFileEntryType == null) {
				serviceContext.setUuid(fileEntryType.getUuid());

				importedDLFileEntryType =
					DLFileEntryTypeLocalServiceUtil.addFileEntryType(
						userId, portletDataContext.getScopeGroupId(),
						fileEntryType.getFileEntryTypeKey(),
						fileEntryType.getNameMap(),
						fileEntryType.getDescriptionMap(), ddmStructureIdsArray,
						serviceContext);
			}
			else {
				DLFileEntryTypeLocalServiceUtil.updateFileEntryType(
					userId, existingDLFileEntryType.getFileEntryTypeId(),
					fileEntryType.getNameMap(),
					fileEntryType.getDescriptionMap(), ddmStructureIdsArray,
					serviceContext);

				importedDLFileEntryType =
					DLFileEntryTypeLocalServiceUtil.fetchDLFileEntryType(
						existingDLFileEntryType.getFileEntryTypeId());
			}
		}
		else {
			importedDLFileEntryType =
				DLFileEntryTypeLocalServiceUtil.addFileEntryType(
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

			importedDDMStructure.setStructureKey(
				importedDLFileEntryDDMStructureKey);

			DDMStructureLocalServiceUtil.updateDDMStructure(
				importedDDMStructure);
		}
	}

	protected DLFileEntryType getExistingFileEntryType(
			String uuid, long groupId, String fileEntryTypeKey,
			boolean preloaded)
		throws Exception {

		DLFileEntryType existingDLFileEntryType = null;

		if (!preloaded) {
			existingDLFileEntryType =
				DLFileEntryTypeLocalServiceUtil.
					fetchDLFileEntryTypeByUuidAndGroupId(uuid, groupId);
		}
		else {
			existingDLFileEntryType =
				DLFileEntryTypeLocalServiceUtil.fetchFileEntryType(
					groupId, fileEntryTypeKey);
		}

		return existingDLFileEntryType;
	}

}