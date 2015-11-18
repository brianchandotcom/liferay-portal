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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
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
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.io.File;
import java.io.Serializable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

		try {
			File file = DLStoreUtil.getFile(
				companyId, CompanyConstants.SYSTEM, filePath);

			ServiceContext serviceContext = createServiceContext();

			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
				userId, groupId, folderId, fileName, contentType, title,
				StringPool.BLANK, StringPool.BLANK, file, serviceContext);

			updateFileEntryStatus(fileEntry, status, serviceContext);

			return fileEntry;
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to add file entry " + fileName, e);
			}

			return null;
		}
	}

	protected Folder addFolder(
			long userId, long groupId, long primaryKey, String fieldName)
		throws Exception {

		Folder ddmFolder = addFolder(
			userId, groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "DDM",
			StringPool.BLANK);

		Folder primaryKeyFolder = addFolder(
			userId, groupId, ddmFolder.getFolderId(),
			String.valueOf(primaryKey), StringPool.BLANK);

		return addFolder(
			userId, groupId, primaryKeyFolder.getFolderId(), fieldName,
			StringPool.BLANK);
	}

	protected Folder addFolder(
			long userId, long groupId, long parentFolderId, String name,
			String description)
		throws Exception {

		try {
			return DLAppLocalServiceUtil.getFolder(
				groupId, parentFolderId, name);
		}
		catch (NoSuchFolderException nsfe) {
			return DLAppLocalServiceUtil.addFolder(
				userId, groupId, parentFolderId, name, description,
				createServiceContext());
		}
	}

	protected boolean checkDuplicateNames(DDMStructure structure)
		throws Exception {

		String xml =
			"<root>" + getFullStructureXML(structure, StringPool.BLANK) +
				"</root>";

		Document document = SAXReaderUtil.read(xml);

		Set<String> duplicateElementNames =
			getDuplicateElementNames(
				document.getRootElement(), new HashSet<String>(),
				new HashSet<String>());

		if (duplicateElementNames.isEmpty()) {
			return false;
		}

		if (!_log.isWarnEnabled()) {
			return true;
		}

		StringBundler sb = new StringBundler(
			duplicateElementNames.size() * 2 + 7);

		sb.append("Structure with class name ID ");
		sb.append(structure.getClassNameId());
		sb.append(" and structure key = ");
		sb.append(structure.getStructureKey());
		sb.append(" contains more than one element that is identified by the ");
		sb.append("same name either within itself or within any of its ");
		sb.append("parent structures. The duplicate element names are: ");

		for (String duplicateElementName : duplicateElementNames) {
			sb.append(duplicateElementName);
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		_log.warn(sb.toString());

		return true;
	}

	protected boolean createDefaultMetadataElement(
		Element dynamicElementElement, String defaultLanguageId) {

		boolean hasDefaultMetadataElement = hasDefaultMetadataElement(
			dynamicElementElement, defaultLanguageId);

		if (hasDefaultMetadataElement) {
			return false;
		}

		Element metadataElement = dynamicElementElement.addElement("meta-data");

		metadataElement.addAttribute("locale", defaultLanguageId);

		Element entryElement = metadataElement.addElement("entry");

		entryElement.addAttribute("name", "label");
		entryElement.addCDATA(StringPool.BLANK);

		return true;
	}

	protected ServiceContext createServiceContext() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return serviceContext;
	}

	@Override
	protected void doVerify() throws Exception {
		setUpClassNameIds();

		List<DDMStructure> structures =
			DDMStructureLocalServiceUtil.getStructures();

		boolean duplicateExists = false;

		for (DDMStructure structure : structures) {
			if (checkDuplicateNames(structure)) {
				duplicateExists = true;
			}
		}

		if (duplicateExists) {
			throw new VerifyException(
				"Duplicate element name found in structures");
		}

		for (DDMStructure structure : structures) {
			verifyStructure(structure);
		}
	}

	protected Set<String> getDuplicateElementNames(
		Element element, Set<String> elementNames,
		Set<String> duplicateElementNames) {

		String elementName = element.attributeValue("name");

		if (!elementNames.add(elementName)) {
			duplicateElementNames.add(elementName);
		}

		List<Element> dynamicElements = element.elements("dynamic-element");

		for (Element dynamicElement : dynamicElements) {
			duplicateElementNames = getDuplicateElementNames(
				dynamicElement, elementNames, duplicateElementNames);
		}

		return duplicateElementNames;
	}

	protected String getFileUploadPath(BaseModel<?> baseModel)
		throws Exception {

		StringBundler sb = new StringBundler(7);

		long primaryKey = 0;

		String version = StringPool.BLANK;

		if (baseModel instanceof DDLRecordModel) {
			DDLRecord ddlRecord = (DDLRecord)baseModel;

			primaryKey = ddlRecord.getPrimaryKey();

			DDLRecordVersion ddlRecordVersion = ddlRecord.getRecordVersion();

			version = ddlRecordVersion.getVersion();
		}
		else {
			DLFileEntryMetadata dlFileEntryMetadata =
				(DLFileEntryMetadata)baseModel;

			primaryKey = dlFileEntryMetadata.getPrimaryKey();

			DLFileVersion dlFileVersion = dlFileEntryMetadata.getFileVersion();

			version = dlFileVersion.getVersion();
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

	protected String getFullStructureXML(DDMStructure structure, String xml)
		throws Exception {

		if (structure.getParentStructureId() != 0) {
			DDMStructure parentStructure =
				DDMStructureLocalServiceUtil.getStructure(
					structure.getParentStructureId());

			xml = getFullStructureXML(parentStructure, xml);
		}

		Document document = SAXReaderUtil.read(structure.getXsd());

		Element rootElement = document.getRootElement();

		List<Element> dynamicElements = rootElement.elements("dynamic-element");

		for (Element dynamicElement : dynamicElements) {
			xml += dynamicElement.asXML();
		}

		return xml;
	}

	protected String getJSON(FileEntry fileEntry) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("groupId", fileEntry.getGroupId());
		jsonObject.put("uuid", fileEntry.getUuid());

		return jsonObject.toString();
	}

	protected long getUserId(AuditedModel auditedModel) throws Exception {
		User user = UserLocalServiceUtil.fetchUser(auditedModel.getUserId());

		if (user != null) {
			return user.getUserId();
		}

		User defaultUser = UserLocalServiceUtil.getDefaultUser(
			auditedModel.getCompanyId());

		if (_log.isWarnEnabled()) {
			_log.warn(
				"Using default user " + defaultUser.getUserId() +
					" for audited model " + auditedModel.getModelClassName() +
						" with primary key " + auditedModel.getPrimaryKeyObj());
		}

		return defaultUser.getUserId();
	}

	protected boolean hasDefaultMetadataElement(
		Element dynamicElementElement, String defaultLanguageId) {

		List<Element> metadataElements = dynamicElementElement.elements(
			"meta-data");

		for (Element metadataElement : metadataElements) {
			String languageId = metadataElement.attributeValue("locale");

			if (languageId.equals(defaultLanguageId)) {
				return true;
			}
		}

		return false;
	}

	protected boolean hasFileUploadFields(DDMStructure structure)
		throws Exception {

		Map<String, Map<String, String>> fieldsMap = structure.getFieldsMap();

		for (Map<String, String> field : fieldsMap.values()) {
			String dataType = field.get(FieldConstants.DATA_TYPE);

			if (dataType.equals("file-upload")) {
				return true;
			}
		}

		return false;
	}

	protected void setUpClassNameIds() {
		_ddlRecordSetClassNameId = PortalUtil.getClassNameId(
			DDLRecordSet.class);
		_ddmStructureClassNameId = PortalUtil.getClassNameId(
			DDMStructure.class);
		_dlFileEntryMetadataClassNameId = PortalUtil.getClassNameId(
			DLFileEntryMetadata.class);
	}

	protected void updateDDLFileUploadReferences(long ddlRecordSetId)
		throws Exception {

		List<DDLRecord> ddlRecords = DDLRecordLocalServiceUtil.getRecords(
			ddlRecordSetId);

		for (DDLRecord ddlRecord : ddlRecords) {
			updateFileUploadReferences(
				ddlRecord.getCompanyId(), ddlRecord.getDDMStorageId(),
				getUserId(ddlRecord), ddlRecord.getGroupId(), ddlRecord,
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
			getUserId(fileEntry), fileEntry.getGroupId(), dlFileEntryMetadata,
			fileVersion.getStatus());
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

		if (!hasFileUploadFields(structure)) {
			return;
		}

		List<DDMStructureLink> structureLinks =
			DDMStructureLinkLocalServiceUtil.getStructureLinks(
				structure.getStructureId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (DDMStructureLink structureLink : structureLinks) {
			updateFileUploadReferences(structureLink);
		}

		List<DDMTemplate> templates = DDMTemplateLocalServiceUtil.getTemplates(
			structure.getGroupId(), _ddmStructureClassNameId,
			structure.getStructureId(),
			DDMTemplateConstants.TEMPLATE_TYPE_FORM);

		for (DDMTemplate template : templates) {
			updateTemplate(template, updateXSD(template.getScript()));
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

	protected void updateFileUploadReferences(
			long companyId, long storageId, long userId, long groupId,
			BaseModel<?> baseModel, int status)
		throws Exception {

		Map<String, String> fieldValues = new HashMap<String, String>();

		Fields fields = StorageEngineUtil.getFields(storageId);

		for (Field field : fields) {
			String dataType = field.getDataType();

			if (!dataType.equals("file-upload") ||
				Validator.isNull(field.getValue())) {

				continue;
			}

			long primaryKey = GetterUtil.getLong(baseModel.getPrimaryKeyObj());

			Folder folder = addFolder(
				userId, groupId, primaryKey, field.getName());

			String valueString = String.valueOf(field.getValue());

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			String filePath =
				getFileUploadPath(baseModel) + StringPool.SLASH +
					field.getName();

			FileEntry fileEntry = addFileEntry(
				companyId, userId, groupId, folder.getFolderId(),
				jsonObject.getString("name"), filePath, status);

			if (fileEntry != null) {
				fieldValues.put(field.getName(), getJSON(fileEntry));
			}
		}

		updateFieldValues(storageId, fieldValues);
	}

	protected void updateStructure(DDMStructure structure, String xsd)
		throws Exception {

		xsd = DDMXMLUtil.formatXML(xsd);

		structure.setXsd(xsd);

		DDMStructureLocalServiceUtil.updateDDMStructure(structure);
	}

	protected void updateTemplate(DDMTemplate template, String script)
		throws Exception {

		script = DDMXMLUtil.formatXML(script);

		template.setScript(script);

		DDMTemplateLocalServiceUtil.updateDDMTemplate(template);
	}

	protected String updateXSD(String xsd) throws Exception {
		Document document = SAXReaderUtil.read(xsd);

		Element rootElement = document.getRootElement();

		List<Element> dynamicElementElements = rootElement.elements(
			"dynamic-element");

		for (Element dynamicElementElement : dynamicElementElements) {
			updateXSDDynamicElement(dynamicElementElement);
		}

		return document.asXML();
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

		Attribute attribute = element.attribute("autoGeneratedName");

		if (attribute != null) {
			element.remove(attribute);
		}
	}

	protected void verifyStructure(DDMStructure structure) throws Exception {
		updateFileUploadReferences(structure);

		updateStructure(structure, updateXSD(structure.getXsd()));

		boolean modified = false;

		String defaultLanguageId = structure.getDefaultLanguageId();

		XPath xPathSelector = SAXReaderUtil.createXPath("//dynamic-element");

		Document document = structure.getDocument();

		List<Node> nodes = xPathSelector.selectNodes(document);

		for (Node node : nodes) {
			Element dynamicElementElement = (Element)node;

			if (createDefaultMetadataElement(
					dynamicElementElement, defaultLanguageId)) {

				modified = true;
			}
		}

		if (modified) {
			updateStructure(structure, document.asXML());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		VerifyDynamicDataMapping.class);

	private long _ddlRecordSetClassNameId;
	private long _ddmStructureClassNameId;
	private long _dlFileEntryMetadataClassNameId;

}