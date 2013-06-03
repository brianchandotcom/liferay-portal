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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordModel;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.io.File;
import java.io.Serializable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class VerifyDynamicDataMapping extends VerifyProcess {

	protected FileEntry addFileEntry(
			long companyId, long userId, long groupId, long folderId,
			String fileName, String filePath, int status)
		throws Exception {

		String contentType = MimeTypesUtil.getContentType(fileName);

		String title = fileName;

		int index = title.indexOf(CharPool.PERIOD);

		if (index > 0) {
			title = title.substring(0, index);
		}

		File file = DLStoreUtil.getFile(
			companyId, CompanyConstants.SYSTEM, filePath);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			userId, groupId, folderId, fileName, contentType, title,
			StringPool.BLANK, StringPool.BLANK, file, serviceContext);

		updateFileEntryStatus(fileEntry, status, serviceContext);

		return fileEntry;
	}

	protected boolean containsFileUploadFields(DDMStructure structure)
		throws Exception {

		Map<String, Map<String, String>> fieldsMap = structure.getFieldsMap();

		for (Map<String, String> field : fieldsMap.values()) {
			String dataType = field.get(FieldConstants.DATA_TYPE);

			if (dataType.equals(FieldConstants.FILE_UPLOAD)) {
				return true;
			}
		}

		return false;
	}

	@Override
	protected void doVerify() throws Exception {
		List<DDMStructure> structures =
			DDMStructureLocalServiceUtil.getStructures();

		for (DDMStructure structure : structures) {
			updateFileUploadReferences(structure);
		}
	}

	protected String getFileUploadPath(BaseModel<?> baseModel) 
		throws Exception {
		
		StringBundler sb = new StringBundler(7);

		long primaryKey = 0;

		String version = StringPool.BLANK;

		if (baseModel instanceof DDLRecordModel) {
			DDLRecord record = (DDLRecord)baseModel;

			primaryKey = record.getPrimaryKey();

			DDLRecordVersion recordVersion = record.getRecordVersion();

			version = recordVersion.getVersion();
		}
		else {
			DLFileEntryMetadata fileEntryMetadata =
				(DLFileEntryMetadata)baseModel;

			primaryKey = fileEntryMetadata.getPrimaryKey();

			DLFileVersion fileVersion = fileEntryMetadata.getFileVersion();

			version = fileVersion.getVersion();
		}

		sb.append("ddm");
		sb.append(StringPool.SLASH);
		sb.append(baseModel.getModelClassName());
		sb.append(StringPool.SLASH);
		sb.append(primaryKey);
		sb.append(StringPool.SLASH);
		sb.append(version);

		return sb.toString();
	}

	protected String getJSON(FileEntry fileEntry) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("uuid", fileEntry.getUuid());
		jsonObject.put("groupId", fileEntry.getGroupId());

		return jsonObject.toString();
	}

	protected void updateDDLFileUploadReferences(long ddlRecordSetId)
		throws Exception {

		List<DDLRecord> ddlRecords = DDLRecordLocalServiceUtil.getRecords(
			ddlRecordSetId);

		for (DDLRecord ddlRecord : ddlRecords) {
			updateFileUploadReferences(
				ddlRecord.getCompanyId(), ddlRecord.getDDMStorageId(),
				ddlRecord.getUserId(), ddlRecord.getGroupId(), ddlRecord,
				ddlRecord.getStatus());
		}
	}

	protected void updateDLFileUploadReferences(long dlFileEntryMetadataId)
		throws Exception {

		DLFileEntryMetadata dlFileEntryMetadata =
			DLFileEntryMetadataLocalServiceUtil.getFileEntryMetadata(
				dlFileEntryMetadataId);

		FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
			dlFileEntryMetadata.getFileEntryId());

		FileVersion fileVersion = fileEntry.getFileVersion();

		updateFileUploadReferences(
			fileEntry.getCompanyId(), dlFileEntryMetadata.getDDMStorageId(),
			fileEntry.getUserId(), fileEntry.getGroupId(), dlFileEntryMetadata,
			fileVersion.getStatus());
	}
	
	protected void updateFileUploadReferences(
			long companyId, long storageId, long userId, long groupId,
			BaseModel<?> baseModel, int status) 
		throws Exception {
		
		Map<String, String> fieldValues = new HashMap<String, String>();

		Fields fields = StorageEngineUtil.getFields(storageId);

		Iterator<Field> itr = fields.iterator();

		while (itr.hasNext()) {
			Field field = itr.next();

			String dataType = field.getDataType();

			if (!dataType.equals(FieldConstants.FILE_UPLOAD) || 
					(field.getValue() == null)) {

				continue;
			}
			
			String valueString = String.valueOf(field.getValue());
			
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			String filePath =
				getFileUploadPath(baseModel) + StringPool.SLASH + 
				field.getName();

			FileEntry fileEntry = addFileEntry(
				companyId, userId, groupId, 
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				jsonObject.getString("name"), filePath, status);

			fieldValues.put(field.getName(), getJSON(fileEntry));
		}

		updateFieldValues(storageId, fieldValues);
	}

	protected void updateFieldValues(
			long storageId, Map<String, String> fieldValues)
		throws Exception {

		Fields fields = new Fields();

		for (Map.Entry<String, String> entry : fieldValues.entrySet()) {
			Field field = new Field(
				storageId, entry.getKey(), entry.getValue());

			fields.put(field);
		}

		ServiceContext serviceContext = new ServiceContext();

		StorageEngineUtil.update(storageId, fields, true, serviceContext);
	}

	protected void updateFileEntryStatus(
			FileEntry fileEntry, int status, ServiceContext serviceContext)
		throws Exception {

		FileVersion fileVersion = fileEntry.getFileVersion();

		Map<String, Serializable> workflowContext =
			new HashMap<String, Serializable>();

		workflowContext.put("event", DLSyncConstants.EVENT_ADD);

		DLFileEntryLocalServiceUtil.updateStatus(
			fileVersion.getUserId(), fileVersion.getFileVersionId(), status,
			workflowContext, serviceContext);
	}

	protected void updateFileUploadReferences(DDMStructure structure)
		throws Exception {

		if (!containsFileUploadFields(structure)) {
			return;
		}

		List<DDMStructureLink> structureLinks =
			DDMStructureLinkLocalServiceUtil.getStructureLinks(
				structure.getStructureId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (DDMStructureLink structureLink : structureLinks) {
			updateFileUploadReferences(structureLink);
		}
		
		updateStructure(structure);
	}

	protected void updateStructure(DDMStructure structure) throws Exception {
		String xsd = updateXSD(structure.getXsd());
		
		structure.setXsd(xsd);
		
		DDMStructureLocalServiceUtil.updateDDMStructure(structure);
	}
	
	protected String updateXSD(String xsd) throws Exception {
		Document document = SAXReaderUtil.read(xsd);
		
		Element rootElement = document.getRootElement();

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateXSDDynamicElement(dynamicElementElement);
		}
		
		return DDMXMLUtil.formatXML(document);
	}
	
	protected void updateXSDDynamicElement(Element element) {
		String dataType = element.attributeValue("dataType");

		if (Validator.equals(dataType, "file-upload")) {
			element.addAttribute("dataType", "document-library");
			element.addAttribute("type", "ddm-documentlibrary");
		}

		List<Element> dynamicElementElements = element.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateXSDDynamicElement(dynamicElementElement);
		}
	}

	protected void updateFileUploadReferences(DDMStructureLink structureLink)
		throws Exception {

		long classNameId = structureLink.getClassNameId();

		if (classNameId == _ddlRecordSetClassNameId) {
			updateDDLFileUploadReferences(structureLink.getClassPK());
		}
		else if (classNameId == _dlFileEntryMetadataClassNameId) {
			updateDLFileUploadReferences(structureLink.getClassPK());
		}
	}

	private long _ddlRecordSetClassNameId = PortalUtil.getClassNameId(
		DDLRecordSet.class);
	private long _dlFileEntryMetadataClassNameId = PortalUtil.getClassNameId(
		DLFileEntryMetadata.class);

}