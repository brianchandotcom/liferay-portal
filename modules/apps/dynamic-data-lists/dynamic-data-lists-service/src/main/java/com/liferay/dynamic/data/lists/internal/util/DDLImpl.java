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

package com.liferay.dynamic.data.lists.internal.util;

import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.dynamic.data.lists.constants.DDLRecordConstants;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.lists.util.DDL;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
@Component(immediate = true, service = DDL.class)
public class DDLImpl implements DDL {

	@Override
	public JSONObject getRecordJSONObject(
			DDLRecord record, boolean latestRecordVersion, Locale locale)
		throws Exception {

		DDLRecordSet recordSet = record.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (String fieldName : ddmStructure.getFieldNames()) {
			jsonObject.put(fieldName, StringPool.BLANK);
		}

		jsonObject.put(
			"displayIndex", record.getDisplayIndex()
		).put(
			"recordId", record.getRecordId()
		);

		DDLRecordVersion recordVersion = record.getRecordVersion();

		if (latestRecordVersion) {
			recordVersion = record.getLatestRecordVersion();
		}

		DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
			recordVersion.getDDMStorageId());

		Fields fields = _ddmFormValuesToFieldsConverter.convert(
			ddmStructure, ddmFormValues);

		for (Field field : fields) {
			Object[] fieldValues = getFieldValues(field, locale);

			if (fieldValues.length == 0) {
				continue;
			}

			String fieldName = field.getName();
			String fieldType = field.getType();

			Stream<Object> fieldValuesStream = Arrays.stream(fieldValues);

			if (fieldType.equals(DDMFormFieldType.DOCUMENT_LIBRARY)) {
				Stream<String> fieldValuesStringStream = fieldValuesStream.map(
					fieldValue -> getDocumentLibraryFieldValue(fieldValue));

				JSONObject fieldJSONObject = JSONUtil.put(
					"title",
					fieldValuesStringStream.collect(
						Collectors.joining(StringPool.COMMA_AND_SPACE)));

				jsonObject.put(fieldName, fieldJSONObject.toString());
			}
			else if (fieldType.equals(DDMFormFieldType.LINK_TO_PAGE)) {
				Stream<String> fieldValuesStringStream = fieldValuesStream.map(
					fieldValue -> getLinkToPageFieldValue(fieldValue, locale));

				JSONObject fieldJSONObject = JSONUtil.put(
					"name",
					fieldValuesStringStream.collect(
						Collectors.joining(StringPool.COMMA_AND_SPACE)));

				jsonObject.put(fieldName, fieldJSONObject.toString());
			}
			else if (fieldType.equals(DDMFormFieldType.SELECT)) {
				JSONArray fieldJSONArray = JSONFactoryUtil.createJSONArray();

				fieldValuesStream.forEach(
					fieldValue -> {
						JSONArray valueJSONArray = getJSONArrayValue(
							fieldValue);

						for (Object object : valueJSONArray) {
							fieldJSONArray.put(object);
						}
					});

				jsonObject.put(fieldName, fieldJSONArray);
			}
			else {
				Stream<String> fieldValuesStringStream = fieldValuesStream.map(
					fieldValue -> {
						if (fieldValue instanceof Date) {
							Date fieldValueDate = (Date)fieldValue;

							return String.valueOf(fieldValueDate.getTime());
						}

						return String.valueOf(fieldValue);
					});

				jsonObject.put(
					fieldName,
					fieldValuesStringStream.collect(
						Collectors.joining(StringPool.COMMA_AND_SPACE)));
			}
		}

		return jsonObject;
	}

	@Override
	public JSONArray getRecordSetJSONArray(
			DDLRecordSet recordSet, Locale locale)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		List<DDMFormField> ddmFormFields = ddmStructure.getDDMFormFields(false);

		for (DDMFormField ddmFormField : ddmFormFields) {
			String name = ddmFormField.getName();

			JSONObject jsonObject = JSONUtil.put(
				"dataType", ddmFormField.getDataType());

			boolean readOnly = ddmFormField.isReadOnly();

			jsonObject.put("editable", !readOnly);

			LocalizedValue label = ddmFormField.getLabel();

			jsonObject.put(
				"label", label.getString(locale)
			).put(
				"name", name
			).put(
				"required", ddmFormField.isRequired()
			).put(
				"sortable", true
			).put(
				"type", ddmFormField.getType()
			);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Override
	public JSONArray getRecordsJSONArray(
			List<DDLRecord> records, boolean latestRecordVersion, Locale locale)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDLRecord record : records) {
			JSONObject jsonObject = getRecordJSONObject(
				record, latestRecordVersion, locale);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Override
	public DDLRecord updateRecord(
			long recordId, long recordSetId, boolean mergeFields,
			boolean checkPermission, ServiceContext serviceContext)
		throws Exception {

		DDLRecord record = _ddlRecordLocalService.fetchRecord(recordId);

		boolean majorVersion = ParamUtil.getBoolean(
			serviceContext, "majorVersion");

		DDLRecordSet recordSet = _ddlRecordSetLocalService.getDDLRecordSet(
			recordSetId);

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Fields fields = _ddm.getFields(
			ddmStructure.getStructureId(), serviceContext);

		if (record != null) {
			if (mergeFields) {
				DDLRecordVersion recordVersion =
					record.getLatestRecordVersion();

				DDMFormValues existingDDMFormValues =
					_storageEngine.getDDMFormValues(
						recordVersion.getDDMStorageId());

				Fields existingFields = _ddmFormValuesToFieldsConverter.convert(
					recordSet.getDDMStructure(), existingDDMFormValues);

				fields = _ddm.mergeFields(fields, existingFields);
			}

			if (checkPermission) {
				record = _ddlRecordService.updateRecord(
					recordId, majorVersion,
					DDLRecordConstants.DISPLAY_INDEX_DEFAULT,
					_fieldsToDDMFormValuesConverter.convert(
						recordSet.getDDMStructure(), fields),
					serviceContext);
			}
			else {
				record = _ddlRecordLocalService.updateRecord(
					serviceContext.getUserId(), recordId, majorVersion,
					DDLRecordConstants.DISPLAY_INDEX_DEFAULT,
					_fieldsToDDMFormValuesConverter.convert(
						recordSet.getDDMStructure(), fields),
					serviceContext);
			}
		}
		else {
			if (checkPermission) {
				record = _ddlRecordService.addRecord(
					serviceContext.getScopeGroupId(), recordSetId,
					DDLRecordConstants.DISPLAY_INDEX_DEFAULT,
					_fieldsToDDMFormValuesConverter.convert(
						recordSet.getDDMStructure(), fields),
					serviceContext);
			}
			else {
				record = _ddlRecordLocalService.addRecord(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), recordSetId,
					DDLRecordConstants.DISPLAY_INDEX_DEFAULT,
					_fieldsToDDMFormValuesConverter.convert(
						recordSet.getDDMStructure(), fields),
					serviceContext);
			}
		}

		return record;
	}

	protected String getDocumentLibraryFieldValue(Object fieldValue) {
		try {
			JSONObject fieldValueJSONObject = JSONFactoryUtil.createJSONObject(
				String.valueOf(fieldValue));

			String uuid = fieldValueJSONObject.getString("uuid");
			long groupId = fieldValueJSONObject.getLong("groupId");

			return getFileEntryTitle(uuid, groupId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return StringPool.BLANK;
		}
	}

	protected Object[] getFieldValues(Field field, Locale locale) {
		Object fieldValue = field.getValue(locale);

		if (fieldValue == null) {
			return new Object[0];
		}

		if (isArray(fieldValue)) {
			return (Object[])fieldValue;
		}

		return new Object[] {fieldValue};
	}

	protected String getFileEntryTitle(String uuid, long groupId) {
		try {
			FileEntry fileEntry =
				_dlAppLocalService.getFileEntryByUuidAndGroupId(uuid, groupId);

			return fileEntry.getTitle();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return LanguageUtil.format(
				LocaleUtil.getSiteDefault(), "is-temporarily-unavailable",
				"content");
		}
	}

	protected JSONArray getJSONArrayValue(Object fieldValue) {
		try {
			return JSONFactoryUtil.createJSONArray(String.valueOf(fieldValue));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return JSONFactoryUtil.createJSONArray();
		}
	}

	protected String getLayoutName(
		long groupId, boolean privateLayout, long layoutId, String languageId) {

		try {
			return _layoutService.getLayoutName(
				groupId, privateLayout, layoutId, languageId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return LanguageUtil.format(
				LocaleUtil.getSiteDefault(), "is-temporarily-unavailable",
				"content");
		}
	}

	protected String getLinkToPageFieldValue(Object fieldValue, Locale locale) {
		try {
			JSONObject fieldValueJSONObject = JSONFactoryUtil.createJSONObject(
				String.valueOf(fieldValue));

			long groupId = fieldValueJSONObject.getLong("groupId");
			boolean privateLayout = fieldValueJSONObject.getBoolean(
				"privateLayout");
			long layoutId = fieldValueJSONObject.getLong("layoutId");

			return getLayoutName(
				groupId, privateLayout, layoutId,
				LanguageUtil.getLanguageId(locale));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return StringPool.BLANK;
		}
	}

	protected boolean isArray(Object parameter) {
		Class<?> clazz = parameter.getClass();

		return clazz.isArray();
	}

	@Reference(unbind = "-")
	protected void setDDLRecordLocalService(
		DDLRecordLocalService ddlRecordLocalService) {

		_ddlRecordLocalService = ddlRecordLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordService(DDLRecordService ddlRecordService) {
		_ddlRecordService = ddlRecordService;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetLocalService(
		DDLRecordSetLocalService ddlRecordSetLocalService) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDM(DDM ddm) {
		_ddm = ddm;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesToFieldsConverter(
		DDMFormValuesToFieldsConverter ddmFormValuesToFieldsConverter) {

		_ddmFormValuesToFieldsConverter = ddmFormValuesToFieldsConverter;
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setFieldsToDDMFormValuesConverter(
		FieldsToDDMFormValuesConverter fieldsToDDMFormValuesConverter) {

		_fieldsToDDMFormValuesConverter = fieldsToDDMFormValuesConverter;
	}

	@Reference(unbind = "-")
	protected void setLayoutService(LayoutService layoutService) {
		_layoutService = layoutService;
	}

	@Reference(unbind = "-")
	protected void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	private static final Log _log = LogFactoryUtil.getLog(DDLImpl.class);

	private DDLRecordLocalService _ddlRecordLocalService;
	private DDLRecordService _ddlRecordService;
	private DDLRecordSetLocalService _ddlRecordSetLocalService;
	private DDM _ddm;
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;
	private DLAppLocalService _dlAppLocalService;
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;
	private LayoutService _layoutService;
	private StorageEngine _storageEngine;

}