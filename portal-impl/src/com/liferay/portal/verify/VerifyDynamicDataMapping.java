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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.CharPool;
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
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLink;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplateConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.io.File;
import java.io.Serializable;

import java.util.HashMap;
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

	protected ServiceContext createServiceContext() {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return serviceContext;
	}

	@Override
	protected void doVerify() throws Exception {
		setUpClassNameIds();

		List<DDMStructure> ddmStructures =
			DDMStructureLocalServiceUtil.getStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			verifyDDMStructure(ddmStructure);
			verifyDDMTemplates(ddmStructure);

			updateFileUploadReferences(ddmStructure);
		}
	}

	protected List<Node> getDynamicElementNodes(Document document) {
		XPath xPathSelector = SAXReaderUtil.createXPath("//dynamic-element");

		return xPathSelector.selectNodes(document);
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

	protected List<DDMTemplate> getFormDDMTemplates(DDMStructure ddmStructure)
		throws Exception {

		return DDMTemplateLocalServiceUtil.getTemplates(
			ddmStructure.getGroupId(), _ddmStructureClassNameId,
			ddmStructure.getStructureId(),
			DDMTemplateConstants.TEMPLATE_TYPE_FORM);
	}

	protected String getJSON(FileEntry fileEntry) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("groupId", fileEntry.getGroupId());
		jsonObject.put("uuid", fileEntry.getUuid());

		return jsonObject.toString();
	}

	protected long getUserId(AuditedModel auditedModel) throws PortalException {
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

	protected boolean hasFileUploadFields(DDMStructure ddmStructure)
		throws Exception {

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmStructure.getFullHierarchyDDMFormFieldsMap(true);

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			String dataType = ddmFormField.getDataType();

			if (Validator.equals(dataType, "file-upload")) {
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

	protected void updateDDMStructure(
			DDMStructure ddmStructure, DDMForm ddmForm)
		throws Exception {

		ddmStructure.updateDDMForm(ddmForm);

		DDMStructureLocalServiceUtil.updateDDMStructure(ddmStructure);
	}

	protected void updateDDMTemplate(DDMTemplate template, String script)
		throws Exception {

		if (script.equals(template.getScript())) {
			return;
		}

		template.setScript(script);

		DDMTemplateLocalServiceUtil.updateDDMTemplate(template);
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
			serviceContext, workflowContext);
	}

	protected void updateFileUploadReferences(DDMForm ddmForm)
		throws Exception {

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFields) {
			updateFileUploadReferences(ddmFormField);
		}
	}

	protected void updateFileUploadReferences(DDMFormField ddmFormField) {
		String dataType = ddmFormField.getDataType();

		if (Validator.equals(dataType, "file-upload")) {
			ddmFormField.setDataType("document-library");
			ddmFormField.setType("ddm-documentlibrary");
		}
	}

	protected void updateFileUploadReferences(DDMStructure ddmStructure)
		throws Exception {

		if (!hasFileUploadFields(ddmStructure)) {
			return;
		}

		List<DDMStructureLink> ddmStructureLinks =
			DDMStructureLinkLocalServiceUtil.getStructureLinks(
				ddmStructure.getStructureId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (DDMStructureLink ddmStructureLink : ddmStructureLinks) {
			updateFileUploadReferences(ddmStructureLink);
		}

		DDMForm ddmForm = ddmStructure.getDDMForm();

		updateFileUploadReferences(ddmForm);

		updateDDMStructure(ddmStructure, ddmForm);

		List<DDMTemplate> ddmTemplates = getFormDDMTemplates(ddmStructure);

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			String script = updateFileUploadReferences(ddmTemplate.getScript());

			updateDDMTemplate(ddmTemplate, script);
		}
	}

	protected void updateFileUploadReferences(DDMStructureLink ddmStructureLink)
		throws Exception {

		long classNameId = ddmStructureLink.getClassNameId();

		if (classNameId == _ddlRecordSetClassNameId) {
			updateDDLFileUploadReferences(ddmStructureLink.getClassPK());
		}
		else if (classNameId == _dlFileEntryMetadataClassNameId) {
			updateDLFileUploadReferences(ddmStructureLink.getClassPK());
		}
	}

	protected void updateFileUploadReferences(Element dynamicElementElement) {
		String dataType = dynamicElementElement.attributeValue("dataType");

		if (Validator.equals(dataType, "file-upload")) {
			dynamicElementElement.addAttribute("dataType", "document-library");
			dynamicElementElement.addAttribute("type", "ddm-documentlibrary");
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

	protected String updateFileUploadReferences(String xsd) throws Exception {
		Document document = SAXReaderUtil.read(xsd);

		List<Node> nodes = getDynamicElementNodes(document);

		for (Node node : nodes) {
			Element dynamicElementElement = (Element)node;

			updateFileUploadReferences(dynamicElementElement);
		}

		return DDMXMLUtil.formatXML(document.asXML());
	}

	protected DDMForm verifyDDMForm(DDMForm ddmForm) {
		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFields) {
			verifyDDMFormField(ddmFormField);
		}

		return ddmForm;
	}

	protected void verifyDDMFormField(DDMFormField ddmFormField) {
		String dataType = ddmFormField.getDataType();

		if (Validator.equals(dataType, "image")) {
			ddmFormField.setNamespace("ddm");
			ddmFormField.setType("ddm-image");
		}
	}

	protected void verifyDDMStructure(DDMStructure ddmStructure)
		throws Exception {

		DDMForm ddmForm = verifyDDMForm(ddmStructure.getDDMForm());

		updateDDMStructure(ddmStructure, ddmForm);
	}

	protected void verifyDDMTemplate(DDMTemplate ddmTemplate) throws Exception {
		if (ddmTemplate.getType() != DDMTemplateConstants.TEMPLATE_TYPE_FORM) {
			return;
		}

		String script = verifySchema(
			ddmTemplate.getScript(), ddmTemplate.getDefaultLanguageId());

		updateDDMTemplate(ddmTemplate, script);
	}

	protected void verifyDDMTemplates(DDMStructure ddmStructure)
		throws Exception {

		List<DDMTemplate> ddmTemplates = getFormDDMTemplates(ddmStructure);

		for (DDMTemplate ddmTemplate : ddmTemplates) {
			verifyDDMTemplate(ddmTemplate);
		}
	}

	protected void verifyDynamicElement(
		Element dynamicElementElement, String defaultLanguageId) {

		String dataType = dynamicElementElement.attributeValue("dataType");

		if (Validator.equals(dataType, "image")) {
			dynamicElementElement.addAttribute("fieldNamespace", "ddm");
			dynamicElementElement.addAttribute("type", "ddm-image");
		}

		Attribute attribute = dynamicElementElement.attribute(
			"autoGeneratedName");

		if (attribute != null) {
			dynamicElementElement.remove(attribute);
		}

		verifyMetadataElement(dynamicElementElement, defaultLanguageId);
	}

	protected void verifyMetadataElement(
		Element dynamicElementElement, String defaultLanguageId) {

		boolean hasDefaultMetadataElement = hasDefaultMetadataElement(
			dynamicElementElement, defaultLanguageId);

		if (hasDefaultMetadataElement) {
			return;
		}

		Element metadataElement = dynamicElementElement.addElement("meta-data");

		metadataElement.addAttribute("locale", defaultLanguageId);

		Element entryElement = metadataElement.addElement("entry");

		entryElement.addAttribute("name", "label");
		entryElement.addCDATA(StringPool.BLANK);
	}

	protected String verifySchema(String xsd, String defaultLanguageId)
		throws Exception {

		Document document = SAXReaderUtil.read(xsd);

		List<Node> nodes = getDynamicElementNodes(document);

		for (Node node : nodes) {
			Element dynamicElementElement = (Element)node;

			verifyDynamicElement(dynamicElementElement, defaultLanguageId);
		}

		return DDMXMLUtil.formatXML(document.asXML());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyDynamicDataMapping.class);

	private long _ddlRecordSetClassNameId;
	private long _ddmStructureClassNameId;
	private long _dlFileEntryMetadataClassNameId;

}