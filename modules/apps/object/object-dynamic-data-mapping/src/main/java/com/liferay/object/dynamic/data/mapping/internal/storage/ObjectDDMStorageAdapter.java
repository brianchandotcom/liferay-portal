/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveResponse;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.math.BigDecimal;

import java.text.NumberFormat;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = "ddm.storage.adapter.type=object",
	service = DDMStorageAdapter.class
)
public class ObjectDDMStorageAdapter implements DDMStorageAdapter {

	@Override
	public DDMStorageAdapterDeleteResponse delete(
			DDMStorageAdapterDeleteRequest ddmStorageAdapterDeleteRequest)
		throws StorageException {

		throw new UnsupportedOperationException();
	}

	@Override
	public DDMStorageAdapterGetResponse get(
			DDMStorageAdapterGetRequest ddmStorageAdapterGetRequest)
		throws StorageException {

		throw new UnsupportedOperationException();
	}

	@Override
	public DDMStorageAdapterSaveResponse save(
			DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
		throws StorageException {

		try {
			DDMFormInstance ddmFormInstance =
				ddmStorageAdapterSaveRequest.getDDMFormInstance();

			DDMFormInstanceSettings ddmFormInstanceSettings =
				ddmFormInstance.getSettingsModel();

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					GetterUtil.getLong(
						ddmFormInstanceSettings.objectDefinitionId()));

			ObjectEntryManager objectEntryManager =
				_objectEntryManagerRegistry.getObjectEntryManager(
					objectDefinition.getStorageType());

			DDMFormValues ddmFormValues =
				ddmStorageAdapterSaveRequest.getDDMFormValues();

			DDMForm ddmForm = ddmFormValues.getDDMForm();

			ObjectEntry objectEntry = objectEntryManager.addObjectEntry(
				new DefaultDTOConverterContext(
					true,
					Collections.singletonMap(
						"delete", Collections.singletonMap("delete", "")),
					null, null, null, ddmForm.getDefaultLocale(), null,
					_userLocalService.getUser(
						ddmStorageAdapterSaveRequest.getUserId())),
				objectDefinition,
				new ObjectEntry() {
					{
						properties = _toProperties(
							ddmFormValues.getDDMFormFieldValuesMap(true),
							ObjectFieldUtil.toObjectFieldsMap(
								_objectFieldLocalService.getObjectFields(
									objectDefinition.getObjectDefinitionId())));
					}
				},
				_getScopeKey(
					ddmStorageAdapterSaveRequest.getGroupId(),
					objectDefinition));

			return DDMStorageAdapterSaveResponse.Builder.newBuilder(
				GetterUtil.getLong(objectEntry.getId())
			).build();
		}
		catch (Exception exception) {
			throw new StorageException(exception.getMessage(), exception);
		}
	}

	private String _getOptionReference(
		DDMFormField ddmFormField, String optionValue, String propertyName) {

		DDMFormFieldOptions ddmFormFieldOptions =
			(DDMFormFieldOptions)ddmFormField.getProperty(propertyName);

		return MapUtil.getString(
			ddmFormFieldOptions.getOptionsReferences(), optionValue);
	}

	private String _getScopeKey(
		long groupId, ObjectDefinition objectDefinition) {

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		if (!objectScopeProvider.isGroupAware()) {
			return null;
		}

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return null;
		}

		return group.getGroupKey();
	}

	private String _getValueString(
			DDMFormField ddmFormField, Locale locale, ObjectField objectField,
			String valueString)
		throws Exception {

		if (StringUtil.equals(
				ddmFormField.getType(),
				DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE) ||
			StringUtil.equals(
				ddmFormField.getType(), DDMFormFieldTypeConstants.GRID) ||
			StringUtil.equals(
				ddmFormField.getType(), DDMFormFieldTypeConstants.SELECT)) {

			StringBundler sb = _toStringBundler(ddmFormField, valueString);

			if (sb.index() > 0) {
				sb.setIndex(sb.index() - 1);
			}

			return sb.toString();
		}
		else if (StringUtil.equals(
					ddmFormField.getType(),
					DDMFormFieldTypeConstants.DOCUMENT_LIBRARY)) {

			JSONObject jsonObject = _jsonFactory.createJSONObject(valueString);

			return jsonObject.getString("fileEntryId");
		}
		else if (StringUtil.equals(
					ddmFormField.getType(), DDMFormFieldTypeConstants.RADIO)) {

			return _getOptionReference(ddmFormField, valueString, "options");
		}

		if (Objects.equals(
				objectField.getDBType(),
				ObjectFieldConstants.DB_TYPE_BIG_DECIMAL) ||
			Objects.equals(
				objectField.getDBType(), ObjectFieldConstants.DB_TYPE_DOUBLE)) {

			if (valueString.isEmpty()) {
				return String.valueOf(GetterUtil.DEFAULT_DOUBLE);
			}

			NumberFormat numberFormat = NumberFormat.getInstance(locale);

			Number number = numberFormat.parse(valueString);

			if (Objects.equals(
					objectField.getDBType(),
					ObjectFieldConstants.DB_TYPE_BIG_DECIMAL)) {

				return String.valueOf(GetterUtil.get(number, BigDecimal.ZERO));
			}

			return String.valueOf(GetterUtil.getDouble(number));
		}

		return valueString;
	}

	private Map<String, Object> _toProperties(
			Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap,
			Map<String, ObjectField> objectFieldsMap)
		throws Exception {

		Map<String, Object> properties = new HashMap<>();

		for (List<DDMFormFieldValue> ddmFormFieldValues :
				ddmFormFieldValuesMap.values()) {

			for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
				DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

				if (ddmFormField == null) {
					continue;
				}

				JSONArray jsonArray = _jsonFactory.createJSONArray(
					(String)ddmFormField.getProperty("objectFieldName"));

				ObjectField objectField = objectFieldsMap.get(
					jsonArray.getString(0));

				if (objectField == null) {
					continue;
				}

				Value value = ddmFormFieldValue.getValue();

				if (value == null) {
					continue;
				}

				String valueString = _getValueString(
					ddmFormField, value.getDefaultLocale(), objectField,
					value.getString(value.getDefaultLocale()));

				if (objectField.getListTypeDefinitionId() > 0) {
					properties.put(
						objectField.getName(),
						HashMapBuilder.put(
							"key", valueString
						).build());
				}
				else {
					properties.put(objectField.getName(), valueString);
				}
			}
		}

		return properties;
	}

	private StringBundler _toStringBundler(
			DDMFormField ddmFormField, String valueString)
		throws Exception {

		StringBundler sb = new StringBundler();

		if (!StringUtil.equals(
				ddmFormField.getType(), DDMFormFieldTypeConstants.GRID)) {

			for (Object optionValue :
					_jsonFactory.createJSONArray(valueString)) {

				sb.append(
					_getOptionReference(
						ddmFormField, optionValue.toString(), "options"));
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			return sb;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(valueString);

		for (String key : jsonObject.keySet()) {
			sb.append(_getOptionReference(ddmFormField, key, "rows"));
			sb.append(StringPool.COLON + StringPool.SPACE);
			sb.append(
				_getOptionReference(
					ddmFormField, jsonObject.getString(key), "columns"));
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		return sb;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private UserLocalService _userLocalService;

}