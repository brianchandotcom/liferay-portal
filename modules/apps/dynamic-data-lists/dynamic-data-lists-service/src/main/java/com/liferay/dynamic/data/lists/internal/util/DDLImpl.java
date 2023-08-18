/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.dynamic.data.mapping.storage.DDMStorageEngineManager;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.dynamic.data.mapping.util.FieldsToDDMFormValuesConverter;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 * @author Eduardo Lundgren
 */
@Component(service = DDL.class)
public class DDLImpl implements DDL {

	@Override
	public JSONObject getRecordJSONObject(
			DDLRecord record, boolean latestRecordVersion, Locale locale)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		DDLRecordSet recordSet = record.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

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

		for (Field field :
				_ddmFormValuesToFieldsConverter.convert(
					ddmStructure,
					_ddmStorageEngineManager.getDDMFormValues(
						recordVersion.getDDMStorageId()))) {

			Object[] fieldValues = _getFieldValues(field, locale);

			if (fieldValues.length == 0) {
				continue;
			}

			String fieldName = field.getName();
			String fieldType = field.getType();

			if (fieldType.equals(DDMFormFieldType.DOCUMENT_LIBRARY)) {
				JSONObject fieldJSONObject = JSONUtil.put(
					"title",
					StringUtil.merge(
						TransformUtil.transformToList(
							fieldValues, this::_getDocumentLibraryFieldValue),
						StringPool.COMMA_AND_SPACE));

				jsonObject.put(fieldName, fieldJSONObject.toString());
			}
			else if (fieldType.equals(DDMFormFieldType.LINK_TO_PAGE)) {
				JSONObject fieldJSONObject = JSONUtil.put(
					"name",
					StringUtil.merge(
						TransformUtil.transformToList(
							fieldValues,
							fieldValue -> _getLinkToPageFieldValue(
								fieldValue, locale)),
						StringPool.COMMA_AND_SPACE));

				jsonObject.put(fieldName, fieldJSONObject.toString());
			}
			else if (fieldType.equals(DDMFormFieldType.SELECT)) {
				JSONArray fieldJSONArray = _jsonFactory.createJSONArray();

				for (Object fieldValue : fieldValues) {
					JSONArray valueJSONArray = _getJSONArrayValue(fieldValue);

					for (Object object : valueJSONArray) {
						fieldJSONArray.put(object);
					}
				}

				jsonObject.put(fieldName, fieldJSONArray);
			}
			else {
				jsonObject.put(
					fieldName,
					StringUtil.merge(
						TransformUtil.transformToList(
							fieldValues,
							fieldValue -> {
								if (fieldValue instanceof Date) {
									Date fieldValueDate = (Date)fieldValue;

									return String.valueOf(
										fieldValueDate.getTime());
								}

								return String.valueOf(fieldValue);
							}),
						StringPool.COMMA_AND_SPACE));
			}
		}

		return jsonObject;
	}

	@Override
	public JSONArray getRecordSetJSONArray(
			DDLRecordSet recordSet, Locale locale)
		throws Exception {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		for (DDMFormField ddmFormField : ddmStructure.getDDMFormFields(false)) {
			jsonArray.put(
				JSONUtil.put(
					"dataType", ddmFormField.getDataType()
				).put(
					"editable", !ddmFormField.isReadOnly()
				).put(
					"label",
					() -> {
						LocalizedValue label = ddmFormField.getLabel();

						return label.getString(locale);
					}
				).put(
					"name", ddmFormField.getName()
				).put(
					"required", ddmFormField.isRequired()
				).put(
					"sortable", true
				).put(
					"type", ddmFormField.getType()
				));
		}

		return jsonArray;
	}

	@Override
	public JSONArray getRecordsJSONArray(
			List<DDLRecord> records, boolean latestRecordVersion, Locale locale)
		throws Exception {

		return JSONUtil.toJSONArray(
			records,
			record -> getRecordJSONObject(record, latestRecordVersion, locale));
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
					_ddmStorageEngineManager.getDDMFormValues(
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

	private String _getDocumentLibraryFieldValue(Object fieldValue) {
		try {
			JSONObject fieldValueJSONObject = _jsonFactory.createJSONObject(
				String.valueOf(fieldValue));

			String uuid = fieldValueJSONObject.getString("uuid");
			long groupId = fieldValueJSONObject.getLong("groupId");

			return _getFileEntryTitle(uuid, groupId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	private Object[] _getFieldValues(Field field, Locale locale) {
		Object fieldValue = field.getValue(locale);

		if (fieldValue == null) {
			return new Object[0];
		}

		if (_isArray(fieldValue)) {
			return (Object[])fieldValue;
		}

		return new Object[] {fieldValue};
	}

	private String _getFileEntryTitle(String uuid, long groupId) {
		try {
			FileEntry fileEntry =
				_dlAppLocalService.getFileEntryByUuidAndGroupId(uuid, groupId);

			return fileEntry.getTitle();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return _language.format(
				LocaleUtil.getSiteDefault(), "is-temporarily-unavailable",
				"content");
		}
	}

	private JSONArray _getJSONArrayValue(Object fieldValue) {
		try {
			return _jsonFactory.createJSONArray(String.valueOf(fieldValue));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return _jsonFactory.createJSONArray();
		}
	}

	private String _getLayoutName(
		long groupId, boolean privateLayout, long layoutId, String languageId) {

		try {
			return _layoutService.getLayoutName(
				groupId, privateLayout, layoutId, languageId);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return _language.format(
				LocaleUtil.getSiteDefault(), "is-temporarily-unavailable",
				"content");
		}
	}

	private String _getLinkToPageFieldValue(Object fieldValue, Locale locale) {
		try {
			JSONObject fieldValueJSONObject = _jsonFactory.createJSONObject(
				String.valueOf(fieldValue));

			long groupId = fieldValueJSONObject.getLong("groupId");
			boolean privateLayout = fieldValueJSONObject.getBoolean(
				"privateLayout");
			long layoutId = fieldValueJSONObject.getLong("layoutId");

			return _getLayoutName(
				groupId, privateLayout, layoutId,
				_language.getLanguageId(locale));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	private boolean _isArray(Object parameter) {
		Class<?> clazz = parameter.getClass();

		return clazz.isArray();
	}

	private static final Log _log = LogFactoryUtil.getLog(DDLImpl.class);

	@Reference
	private DDLRecordLocalService _ddlRecordLocalService;

	@Reference
	private DDLRecordService _ddlRecordService;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDM _ddm;

	@Reference
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;

	@Reference
	private DDMStorageEngineManager _ddmStorageEngineManager;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private FieldsToDDMFormValuesConverter _fieldsToDDMFormValuesConverter;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutService _layoutService;

}