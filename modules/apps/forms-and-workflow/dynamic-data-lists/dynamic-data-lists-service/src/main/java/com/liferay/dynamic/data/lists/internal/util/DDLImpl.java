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
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordConstants;
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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
@Component(immediate = true)
public class DDLImpl implements DDL {

	/**
	 * @deprecated As of 1.1.0
	 */
	@Deprecated
	@Override
	@SuppressWarnings("deprecation")
	public JSONObject getRecordJSONObject(DDLRecord record) throws Exception {
		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		return getRecordJSONObject(record, false, locale);
	}

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

		jsonObject.put("displayIndex", record.getDisplayIndex());
		jsonObject.put("recordId", record.getRecordId());

		DDLRecordVersion recordVersion = record.getRecordVersion();

		if (latestRecordVersion) {
			recordVersion = record.getLatestRecordVersion();
		}

		DDMFormValues ddmFormValues = _storageEngine.getDDMFormValues(
			recordVersion.getDDMStorageId());

		Fields fields = _ddmFormValuesToFieldsConverter.convert(
			ddmStructure, ddmFormValues);

		for (Field field : fields) {
			String fieldName = field.getName();
			String fieldType = field.getType();
			Object[] fieldValues = getFieldValues(field, locale);

			if (fieldValues.length == 0) {
				continue;
			}

			if (fieldType.equals(DDMFormFieldType.DOCUMENT_LIBRARY)) {
				Stream<String> valuesStream = Arrays.stream(fieldValues).map(
					fieldValue -> getDocumentLibraryFieldValue(fieldValue));

				JSONObject fieldJSONObject = JSONFactoryUtil.createJSONObject();

				fieldJSONObject.put(
					"title",
					valuesStream.collect(
						Collectors.joining(StringPool.COMMA_AND_SPACE)));

				jsonObject.put(fieldName, fieldJSONObject.toString());
			}
			else if (fieldType.equals(DDMFormFieldType.LINK_TO_PAGE)) {
				Stream<String> valuesStream = Arrays.stream(fieldValues).map(
					fieldValue -> getLinkToPageFieldValue(fieldValue, locale));

				JSONObject fieldJSONObject = JSONFactoryUtil.createJSONObject();

				fieldJSONObject.put(
					"name",
					valuesStream.collect(
						Collectors.joining(StringPool.COMMA_AND_SPACE)));

				jsonObject.put(fieldName, fieldJSONObject.toString());
			}
			else if (fieldType.equals(DDMFormFieldType.RADIO) ||
					 fieldType.equals(DDMFormFieldType.SELECT)) {

				JSONArray fieldJSONArray = JSONFactoryUtil.createJSONArray();

				for (Object fieldValue : fieldValues) {
					JSONArray tempJSONArray = JSONFactoryUtil.createJSONArray(
						String.valueOf(fieldValue));

					fieldJSONArray.put(tempJSONArray.get(0));
				}

				jsonObject.put(fieldName, fieldJSONArray);
			}
			else {
				Stream<String> valuesStream = Arrays.stream(fieldValues).map(
					fieldValue -> {
						if (fieldValue instanceof Date) {
							return String.valueOf(((Date)fieldValue).getTime());
						}
						else {
							return String.valueOf(fieldValue);
						}
					});

				jsonObject.put(
					fieldName,
					valuesStream.collect(
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

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			String dataType = ddmFormField.getDataType();

			jsonObject.put("dataType", dataType);

			boolean readOnly = ddmFormField.isReadOnly();

			jsonObject.put("editable", !readOnly);

			LocalizedValue label = ddmFormField.getLabel();

			jsonObject.put("label", label.getString(locale));

			jsonObject.put("name", name);

			boolean required = ddmFormField.isRequired();

			jsonObject.put("required", required);

			jsonObject.put("sortable", true);

			String type = ddmFormField.getType();

			jsonObject.put("type", type);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	/**
	 * @deprecated As of 1.1.0
	 */
	@Deprecated
	@Override
	@SuppressWarnings("deprecation")
	public JSONArray getRecordsJSONArray(DDLRecordSet recordSet)
		throws Exception {

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		return getRecordsJSONArray(recordSet.getRecords(), false, locale);
	}

	/**
	 * @deprecated As of 1.1.0
	 */
	@Deprecated
	@Override
	@SuppressWarnings("deprecation")
	public JSONArray getRecordsJSONArray(List<DDLRecord> records)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDLRecord record : records) {
			JSONObject jsonObject = getRecordJSONObject(record);

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

	/**
	 * @deprecated As of 1.1.0, with no direct replacement
	 */
	@Deprecated
	@Override
	@SuppressWarnings("deprecation")
	public boolean isEditable(
			HttpServletRequest request, String portletId, long groupId)
		throws Exception {

		return true;
	}

	/**
	 * @deprecated As of 1.1.0, with no direct replacement
	 */
	@Deprecated
	@Override
	@SuppressWarnings("deprecation")
	public boolean isEditable(
			PortletPreferences preferences, String portletId, long groupId)
		throws Exception {

		return true;
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
			if (checkPermission) {
				record = _ddlRecordService.updateRecord(
					recordId, majorVersion,
					DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields,
					mergeFields, serviceContext);
			}
			else {
				record = _ddlRecordLocalService.updateRecord(
					serviceContext.getUserId(), recordId, majorVersion,
					DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields,
					mergeFields, serviceContext);
			}
		}
		else {
			if (checkPermission) {
				record = _ddlRecordService.addRecord(
					serviceContext.getScopeGroupId(), recordSetId,
					DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields,
					serviceContext);
			}
			else {
				record = _ddlRecordLocalService.addRecord(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), recordSetId,
					DDLRecordConstants.DISPLAY_INDEX_DEFAULT, fields,
					serviceContext);
			}
		}

		return record;
	}

	/**
	 * @deprecated As of 1.1.0
	 */
	@Deprecated
	@Override
	@SuppressWarnings("deprecation")
	public DDLRecord updateRecord(
			long recordId, long recordSetId, boolean mergeFields,
			ServiceContext serviceContext)
		throws Exception {

		return updateRecord(
			recordId, recordSetId, mergeFields, true, serviceContext);
	}

	protected String getDocumentLibraryFieldValue(Object fieldValue) {
		try {
			JSONObject fieldValueJSONObject = JSONFactoryUtil.createJSONObject(
				String.valueOf(fieldValue));

			String uuid = fieldValueJSONObject.getString("uuid");
			long groupId = fieldValueJSONObject.getLong("groupId");

			return getFileEntryTitle(uuid, groupId);
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	protected Object[] getFieldValues(Field field, Locale locale) {
		Object fieldValue = field.getValue(locale);

		if (Validator.isNull(fieldValue)) {
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
		catch (Exception e) {
			return LanguageUtil.format(
				LocaleUtil.getSiteDefault(), "is-temporarily-unavailable",
				"content");
		}
	}

	protected String getLayoutName(
		long groupId, boolean privateLayout, long layoutId, String languageId) {

		try {
			return _layoutService.getLayoutName(
				groupId, privateLayout, layoutId, languageId);
		}
		catch (Exception e) {
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
		catch (Exception e) {
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
	protected void setLayoutService(LayoutService layoutService) {
		_layoutService = layoutService;
	}

	@Reference(unbind = "-")
	protected void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	private DDLRecordLocalService _ddlRecordLocalService;
	private DDLRecordService _ddlRecordService;
	private DDLRecordSetLocalService _ddlRecordSetLocalService;
	private DDM _ddm;
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;
	private DLAppLocalService _dlAppLocalService;
	private LayoutService _layoutService;
	private StorageEngine _storageEngine;

}