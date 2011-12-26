/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.templateparser.Transformer;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DuplicateDirectoryException;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordConstants;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordLocalServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordServiceUtil;
import com.liferay.portlet.dynamicdatalists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.portlet.PortletRequestUtil;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcelllus Tavares
 * @author Eduardo Lundgren
 */
public class DDLImpl implements DDL {

	public void addAllReservedEls(
		Element rootElement, Map<String, String> tokens,
		DDLRecordSet recordSet) {

		JournalUtil.addReservedEl(
			rootElement, tokens, DDLConstants.RESERVED_RECORD_SET_ID,
			String.valueOf(recordSet.getRecordSetId()));

		JournalUtil.addReservedEl(
			rootElement, tokens, DDLConstants.RESERVED_RECORD_SET_NAME,
			recordSet.getName());

		JournalUtil.addReservedEl(
			rootElement, tokens, DDLConstants.RESERVED_RECORD_SET_DESCRIPTION,
			recordSet.getDescription());

		JournalUtil.addReservedEl(
			rootElement, tokens, DDLConstants.RESERVED_DDM_STRUCTURE_ID,
			String.valueOf(recordSet.getDDMStructureId()));
	}

	public Fields getFields(
			UploadPortletRequest uploadPortletRequest, long ddmStructureId)
		throws PortalException, SystemException {

		return getFields(uploadPortletRequest, ddmStructureId, 0);
	}

	public Fields getFields(
			UploadPortletRequest uploadPortletRequest, long ddmStructureId,
			long ddmTemplateId)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		try {
			DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
				ddmTemplateId);

			ddmStructure.setXsd(ddmTemplate.getScript());
		}
		catch (NoSuchTemplateException nste) {
		}

		Set<String> fieldNames = ddmStructure.getFieldNames();

		Fields fields = new Fields();

		for (String fieldName : fieldNames) {
			Field field = new Field();

			field.setName(fieldName);

			String fieldDataType = ddmStructure.getFieldDataType(fieldName);
			String fieldType = ddmStructure.getFieldType(fieldName);
			String fieldValue = uploadPortletRequest.getParameter(fieldName);

			if (fieldDataType.equals(FieldConstants.FILE_UPLOAD)) {
				continue;
			}

			if (fieldType.equals("radio") || fieldType.equals("select")) {
				String[] fieldValues = ParamUtil.getParameterValues(
					uploadPortletRequest, fieldName);

				fieldValue = JSONFactoryUtil.serialize(fieldValues);
			}

			if (fieldValue == null) {
				continue;
			}

			Serializable fieldValueSerializable =
				FieldConstants.getSerializable(
					fieldDataType, GetterUtil.getString(fieldValue));

			field.setValue(fieldValueSerializable);

			fields.put(field);
		}

		return fields;
	}

	public void getRecordFileUpload(
			HttpServletRequest request, HttpServletResponse response,
			DDLRecord record, String fieldName)
		throws Exception {

		Serializable fieldValue = record.getFieldValue(fieldName);

		JSONObject fileJSONObject = JSONFactoryUtil.createJSONObject(
			String.valueOf(fieldValue));

		String fileName = fileJSONObject.getString("name");
		String filePath = fileJSONObject.getString("path");

		InputStream is = DLStoreUtil.getFileAsStream(
			record.getCompanyId(), CompanyConstants.SYSTEM, filePath);
		long contentLength = DLStoreUtil.getFileSize(
			record.getCompanyId(), CompanyConstants.SYSTEM, filePath);
		String contentType = MimeTypesUtil.getContentType(fileName);

		ServletResponseUtil.sendFile(
			request, response, fileName, is, contentLength, contentType);
	}

	public void getRecordFileUpload(
			HttpServletRequest request, HttpServletResponse response,
			long recordId, String fieldName)
		throws Exception {

		DDLRecord record = DDLRecordServiceUtil.getRecord(recordId);

		getRecordFileUpload(request, response, record, fieldName);
	}

	public String getRecordFileUploadPath(DDLRecord record) {
		return "ddl_records/" + record.getRecordId();
	}

	public JSONObject getRecordJSONObject(DDLRecord record) throws Exception {
		DDLRecordSet recordSet = record.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (String fieldName : ddmStructure.getFieldNames()) {
			jsonObject.put(fieldName, StringPool.BLANK);
		}

		jsonObject.put("displayIndex", record.getDisplayIndex());
		jsonObject.put("recordId", record.getRecordId());

		Fields fields = record.getFields();

		Iterator<Field> itr = fields.iterator();

		while (itr.hasNext()) {
			Field field = itr.next();

			String fieldName = field.getName();
			String fieldType = field.getType();
			Object fieldValue = field.getValue();

			if (fieldValue instanceof Date) {
				jsonObject.put(fieldName, ((Date)fieldValue).getTime());
			}
			else if ((fieldType.equals("radio") ||
					  fieldType.equals("select")) &&
					 Validator.isNotNull(fieldValue)) {

				fieldValue = JSONFactoryUtil.createJSONArray(
					String.valueOf(fieldValue));

				jsonObject.put(fieldName, (JSONArray)fieldValue);
			}
			else {
				jsonObject.put(fieldName, String.valueOf(fieldValue));
			}
		}

		return jsonObject;
	}

	public JSONArray getRecordSetJSONArray(DDLRecordSet recordSet)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Map<String, Map<String, String>> fieldsMap =
			ddmStructure.getFieldsMap();

		for (Map<String, String> fields : fieldsMap.values()) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			String dataType = fields.get(FieldConstants.DATA_TYPE);

			jsonObject.put("dataType", dataType);

			boolean editable = GetterUtil.getBoolean(
				fields.get(FieldConstants.EDITABLE), true);

			jsonObject.put("editable", editable);

			String label = fields.get(FieldConstants.LABEL);

			jsonObject.put("label", label);

			String name = fields.get(FieldConstants.NAME);

			jsonObject.put("name", name);

			boolean required = GetterUtil.getBoolean(
				fields.get(FieldConstants.REQUIRED));

			jsonObject.put("required", required);

			boolean sortable = GetterUtil.getBoolean(
				fields.get(FieldConstants.SORTABLE), true);

			jsonObject.put("sortable", sortable);

			String type = fields.get(FieldConstants.TYPE);

			jsonObject.put("type", type);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public JSONArray getRecordsJSONArray(DDLRecordSet recordSet)
		throws Exception {

		return getRecordsJSONArray(recordSet.getRecords());
	}

	public JSONArray getRecordsJSONArray(List<DDLRecord> records)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDLRecord record : records) {
			JSONObject jsonObject = getRecordJSONObject(record);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public String getTemplateContent(
			long ddmTemplateId, DDLRecordSet recordSet,
			ThemeDisplay themeDisplay, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		String viewMode = ParamUtil.getString(renderRequest, "viewMode");

		String languageId = LanguageUtil.getLanguageId(renderRequest);

		String xmlRequest = PortletRequestUtil.toXML(
			renderRequest, renderResponse);

		if (Validator.isNull(xmlRequest)) {
			xmlRequest = "<request />";
		}

		Map<String, String> tokens = JournalUtil.getTokens(
			recordSet.getGroupId(), themeDisplay, xmlRequest);

		String xml = StringPool.BLANK;

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		Document requestDocument = SAXReaderUtil.read(xmlRequest);

		rootElement.add(requestDocument.getRootElement().createCopy());

		addAllReservedEls(rootElement, tokens, recordSet);

		xml = DDMXMLUtil.formatXML(document);

		DDMTemplate template = DDMTemplateLocalServiceUtil.getTemplate(
			ddmTemplateId);

		return _transformer.transform(
			themeDisplay, tokens, viewMode, languageId, xml,
			template.getScript(), template.getLanguage());
	}

	public String storeRecordFieldFile(
			DDLRecord record, String fieldName, InputStream inputStream)
		throws Exception {

		DDLRecordVersion recordVersion = record.getLatestRecordVersion();

		String dirName =
			getRecordFileUploadPath(record) + StringPool.SLASH +
				recordVersion.getVersion();

		try {
			DLStoreUtil.addDirectory(
				record.getCompanyId(), CompanyConstants.SYSTEM, dirName);
		}
		catch (DuplicateDirectoryException dde) {
		}

		String fileName = dirName + StringPool.SLASH + fieldName;

		try {
			DLStoreUtil.addFile(
				record.getCompanyId(), CompanyConstants.SYSTEM, fileName,
				inputStream);
		}
		catch (DuplicateFileException dfe) {
		}

		return fileName;
	}

	public DDLRecord updateRecord(
			UploadPortletRequest uploadPortletRequest, long recordId,
			long recordSetId, boolean mergeFields)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		boolean majorVersion = ParamUtil.getBoolean(
			uploadPortletRequest, "majorVersion");

		DDLRecord record = DDLRecordLocalServiceUtil.fetchRecord(recordId);

		DDLRecordSet recordSet =
			DDLRecordSetLocalServiceUtil.getDDLRecordSet(recordSetId);

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Fields fields = getFields(
			uploadPortletRequest, ddmStructure.getStructureId());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			uploadPortletRequest);

		if (record != null) {
			record = DDLRecordServiceUtil.updateRecord(
				recordId, majorVersion,
				DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields, mergeFields,
				serviceContext);
		}
		else {
			record = DDLRecordServiceUtil.addRecord(
				themeDisplay.getScopeGroupId(), recordSetId,
				DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields,
				serviceContext);
		}

		uploadRecordFieldFiles(record, uploadPortletRequest, serviceContext);

		return record;
	}

	public void uploadRecordFieldFile(
			DDLRecord record, String fieldName,
			UploadPortletRequest uploadPortletRequest,
			ServiceContext serviceContext)
		throws Exception {

		Fields fields = new Fields();

		String fileName = uploadPortletRequest.getFileName(fieldName);

		Field field = record.getField(fieldName);

		String fieldValue = StringPool.BLANK;

		if (field != null) {
			fieldValue = String.valueOf(field.getValue());
		}

		InputStream inputStream = null;

		try {
			inputStream = uploadPortletRequest.getFileAsStream(fieldName, true);

			if (inputStream != null) {
				String filePath = storeRecordFieldFile(
					record, fieldName, inputStream);

				JSONObject recordFileJSONObject =
					JSONFactoryUtil.createJSONObject();

				recordFileJSONObject.put("name", fileName);
				recordFileJSONObject.put("path", filePath);
				recordFileJSONObject.put("recordId", record.getRecordId());

				fieldValue = recordFileJSONObject.toString();
			}

			DDLRecordSet recordSet = record.getRecordSet();

			DDMStructure ddmStructure = recordSet.getDDMStructure();

			field = new Field(
				ddmStructure.getStructureId(), fieldName, fieldValue);

			fields.put(field);

			DDLRecordVersion recordVersion = record.getLatestRecordVersion();

			StorageEngineUtil.update(
				recordVersion.getDDMStorageId(), fields, true, serviceContext);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	public void uploadRecordFieldFiles(
			DDLRecord record, UploadPortletRequest uploadPortletRequest,
			ServiceContext serviceContext)
		throws Exception {

		DDLRecordSet recordSet = record.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		for (String fieldName : ddmStructure.getFieldNames()) {
			String fieldDataType = ddmStructure.getFieldDataType(fieldName);

			if (fieldDataType.equals(FieldConstants.FILE_UPLOAD)) {
				uploadRecordFieldFile(
					record, fieldName, uploadPortletRequest, serviceContext);
			}
		}
	}

	private Transformer _transformer = new DDLTransformer();

}