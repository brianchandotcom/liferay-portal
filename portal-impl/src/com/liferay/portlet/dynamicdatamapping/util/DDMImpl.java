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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.DuplicateDirectoryException;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadataModel;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordModel;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordVersion;
import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.dynamicdatamapping.util.comparator.StructureIdComparator;
import com.liferay.portlet.dynamicdatamapping.util.comparator.StructureModifiedDateComparator;
import com.liferay.portlet.dynamicdatamapping.util.comparator.TemplateIdComparator;
import com.liferay.portlet.dynamicdatamapping.util.comparator.TemplateModifiedDateComparator;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 * @author Eduardo Garcia
 */
public class DDMImpl implements DDM {

	public static final String TYPE_CHECKBOX = "checkbox";

	public static final String TYPE_DDM_DOCUMENTLIBRARY = "ddm-documentlibrary";

	public static final String TYPE_DDM_FILEUPLOAD = "ddm-fileupload";

	public static final String TYPE_RADIO = "radio";

	public static final String TYPE_SELECT = "select";

	public Fields getFields(
			long ddmStructureId, long ddmTemplateId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return getFields(
			ddmStructureId, ddmTemplateId, StringPool.BLANK, serviceContext);
	}

	public Fields getFields(
			long ddmStructureId, long ddmTemplateId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		try {
			DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
				ddmTemplateId);

			// Clone ddmStructure to make sure changes are never persisted

			ddmStructure = (DDMStructure)ddmStructure.clone();

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
			Serializable fieldValue = serviceContext.getAttribute(
				fieldNamespace + fieldName);

			if (fieldDataType.equals(FieldConstants.DATE)) {
				int fieldValueMonth = GetterUtil.getInteger(
					serviceContext.getAttribute(
						fieldNamespace + fieldName + "Month"));
				int fieldValueYear = GetterUtil.getInteger(
					serviceContext.getAttribute(
						fieldNamespace + fieldName + "Year"));
				int fieldValueDay = GetterUtil.getInteger(
					serviceContext.getAttribute(
						fieldNamespace + fieldName + "Day"));

				Date fieldValueDate = PortalUtil.getDate(
					fieldValueMonth, fieldValueDay, fieldValueYear);

				if (fieldValueDate != null) {
					fieldValue = String.valueOf(fieldValueDate.getTime());
				}
			}

			if ((fieldValue == null) ||
				fieldDataType.equals(FieldConstants.FILE_UPLOAD)) {

				continue;
			}

			if (fieldType.equals(DDMImpl.TYPE_RADIO) ||
				fieldType.equals(DDMImpl.TYPE_SELECT)) {

				if (fieldValue instanceof String) {
					fieldValue = new String[] {String.valueOf(fieldValue)};
				}

				fieldValue = JSONFactoryUtil.serialize(fieldValue);
			}

			Serializable fieldValueSerializable =
				FieldConstants.getSerializable(
					fieldDataType, GetterUtil.getString(fieldValue));

			field.setValue(fieldValueSerializable);

			fields.put(field);
		}

		return fields;
	}

	public Fields getFields(long ddmStructureId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return getFields(ddmStructureId, 0, serviceContext);
	}

	public Fields getFields(
			long ddmStructureId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return getFields(ddmStructureId, 0, fieldNamespace, serviceContext);
	}

	public String getFileUploadPath(BaseModel<?> baseModel) {
		StringBundler sb = new StringBundler(7);

		try {
			long primaryKey = 0;

			String version = StringPool.BLANK;

			if (baseModel instanceof DDLRecordModel) {
				DDLRecord record = (DDLRecord)baseModel;

				primaryKey = record.getPrimaryKey();

				DDLRecordVersion recordVersion =
					record.getLatestRecordVersion();

				version = recordVersion.getVersion();
			}
			else if (baseModel instanceof DLFileEntryMetadataModel) {
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
		}
		catch (Exception e) {
		}

		return sb.toString();
	}

	public OrderByComparator getStructureOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("id")) {
			orderByComparator = new StructureIdComparator(orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new StructureModifiedDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public OrderByComparator getTemplateOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("id")) {
			orderByComparator = new TemplateIdComparator(orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new TemplateModifiedDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public void sendFieldFile(
			HttpServletRequest request, HttpServletResponse response,
			Field field)
		throws Exception {

		if (field == null) {
			return;
		}

		DDMStructure structure = field.getDDMStructure();

		Serializable fieldValue = field.getValue();

		JSONObject fileJSONObject = JSONFactoryUtil.createJSONObject(
			String.valueOf(fieldValue));

		String fileName = fileJSONObject.getString("name");
		String filePath = fileJSONObject.getString("path");

		InputStream is = DLStoreUtil.getFileAsStream(
			structure.getCompanyId(), CompanyConstants.SYSTEM, filePath);
		long contentLength = DLStoreUtil.getFileSize(
			structure.getCompanyId(), CompanyConstants.SYSTEM, filePath);
		String contentType = MimeTypesUtil.getContentType(fileName);

		ServletResponseUtil.sendFile(
			request, response, fileName, is, contentLength, contentType);
	}

	public String uploadFieldFile(
			long structureId, long storageId, BaseModel<?> baseModel,
			String fieldName, ServiceContext serviceContext)
		throws Exception {

		return uploadFieldFile(
			structureId, storageId, baseModel, fieldName, StringPool.BLANK,
			serviceContext);
	}

	public String uploadFieldFile(
			long structureId, long storageId, BaseModel<?> baseModel,
			String fieldName, String fieldNamespace,
			ServiceContext serviceContext)
		throws Exception {

		HttpServletRequest request = serviceContext.getRequest();

		if (!(request instanceof UploadRequest)) {
			return StringPool.BLANK;
		}

		UploadRequest uploadRequest = (UploadRequest)request;

		String fileName = uploadRequest.getFileName(fieldNamespace + fieldName);

		String fieldValue = StringPool.BLANK;

		InputStream inputStream = null;

		Fields fields = StorageEngineUtil.getFields(storageId);

		try {
			inputStream = uploadRequest.getFileAsStream(
				fieldNamespace + fieldName, true);

			if (inputStream != null) {
				String filePath = storeFieldFile(
					baseModel, fieldName, inputStream, serviceContext);

				JSONObject recordFileJSONObject =
					JSONFactoryUtil.createJSONObject();

				recordFileJSONObject.put("name", fileName);
				recordFileJSONObject.put("path", filePath);
				recordFileJSONObject.put(
					"className", baseModel.getModelClassName());
				recordFileJSONObject.put(
					"classPK", String.valueOf(baseModel.getPrimaryKeyObj()));

				fieldValue = recordFileJSONObject.toString();
			}
			else if (fields.contains(fieldName)) {
				return StringPool.BLANK;
			}

			Field field = new Field(structureId, fieldName, fieldValue);

			fields.put(field);

			StorageEngineUtil.update(storageId, fields, true, serviceContext);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}

		return fieldValue;
	}

	protected String storeFieldFile(
			BaseModel<?> baseModel, String fieldName, InputStream inputStream,
			ServiceContext serviceContext)
		throws Exception {

		String dirName = getFileUploadPath(baseModel);

		try {
			DLStoreUtil.addDirectory(
				serviceContext.getCompanyId(), CompanyConstants.SYSTEM,
				dirName);
		}
		catch (DuplicateDirectoryException dde) {
		}

		String fileName = dirName + StringPool.SLASH + fieldName;

		try {
			DLStoreUtil.addFile(
				serviceContext.getCompanyId(), CompanyConstants.SYSTEM,
				fileName, inputStream);
		}
		catch (DuplicateFileException dfe) {
		}

		return fileName;
	}

}