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

package com.liferay.object.dynamic.data.mapping.internal.storage;

import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
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
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.math.BigDecimal;

import java.text.NumberFormat;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true, property = "ddm.storage.adapter.type=object",
	service = DDMStorageAdapter.class
)
public class ObjectDDMStorageAdapter implements DDMStorageAdapter {

	@Override
	public DDMStorageAdapterDeleteResponse delete(
			DDMStorageAdapterDeleteRequest ddmStorageAdapterDeleteRequest)
		throws StorageException {

		try {
			ObjectEntry objectEntry = _objectEntryManager.getObjectEntry(
				_getDTOConverterContext(null, null, null),
				ddmStorageAdapterDeleteRequest.getPrimaryKey());

			_objectEntryManager.deleteObjectEntry(objectEntry.getId());

			return DDMStorageAdapterDeleteResponse.Builder.newBuilder(
			).build();
		}
		catch (Exception exception) {
			throw new StorageException(exception);
		}
	}

	@Override
	public DDMStorageAdapterGetResponse get(
			DDMStorageAdapterGetRequest ddmStorageAdapterGetRequest)
		throws StorageException {

		try {
			DDMForm ddmForm = ddmStorageAdapterGetRequest.getDDMForm();

			return DDMStorageAdapterGetResponse.Builder.newBuilder(
				_getDDMFormValues(
					ddmForm,
					_objectEntryManager.getObjectEntry(
						_getDTOConverterContext(
							ddmStorageAdapterGetRequest.getPrimaryKey(), null,
							ddmForm.getDefaultLocale()),
						ddmStorageAdapterGetRequest.getPrimaryKey()))
			).build();
		}
		catch (Exception exception) {
			throw new StorageException(exception);
		}
	}

	@Override
	public DDMStorageAdapterSaveResponse save(
			DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
		throws StorageException {

		try {
			User user = _userLocalService.getUser(
				ddmStorageAdapterSaveRequest.getUserId());

			DDMFormValues ddmFormValues =
				ddmStorageAdapterSaveRequest.getDDMFormValues();

			DDMForm ddmForm = ddmFormValues.getDDMForm();

			long objectDefinitionId = _getObjectDefinitionId(
				ddmStorageAdapterSaveRequest);

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectDefinitionId);

			ObjectScopeProvider objectScopeProvider =
				_objectScopeProviderRegistry.getObjectScopeProvider(
					objectDefinition.getScope());

			long groupId = 0;

			if (objectScopeProvider.isGroupAware()) {
				groupId = ddmStorageAdapterSaveRequest.getScopeGroupId();
			}

			ObjectEntry addObjectEntry = _objectEntryManager.addObjectEntry(
				_getDTOConverterContext(null, user, ddmForm.getDefaultLocale()),
				user.getUserId(), groupId, objectDefinitionId,
				new ObjectEntry() {
					{
						properties = _getObjectEntryProperties(
							ddmFormValues.getDDMFormFieldValues(),
							_objectFieldLocalService.getObjectFields(
								objectDefinitionId));
					}
				});

			return DDMStorageAdapterSaveResponse.Builder.newBuilder(
				addObjectEntry.getId()
			).build();
		}
		catch (Exception exception) {
			throw new StorageException(exception);
		}
	}

	private Value _getDDMFormFieldValue(
		DDMFormField ddmFormField, Map<String, DDMFormField> ddmFormFieldsMap,
		Locale locale, Map<String, Object> properties) {

		Value value = new LocalizedValue(locale);

		Object objectFieldValue = properties.get(
			_getObjectFieldName(ddmFormFieldsMap.get(ddmFormField.getName())));

		if (objectFieldValue instanceof Double) {
			NumberFormat numberFormat = NumberFormat.getInstance(locale);

			value.addString(locale, numberFormat.format(objectFieldValue));
		}
		else if (objectFieldValue instanceof byte[]) {
			value.addString(locale, new String((byte[])objectFieldValue));
		}
		else {
			value.addString(locale, String.valueOf(objectFieldValue));
		}

		return value;
	}

	private List<DDMFormFieldValue> _getDDMFormFieldValues(
		List<DDMFormField> ddmFormFields,
		Map<String, DDMFormField> ddmFormFieldsMap, Locale locale,
		Map<String, Object> properties) {

		List<DDMFormFieldValue> ddmFormFieldValues = new ArrayList<>();

		ddmFormFields.forEach(
			ddmFormField -> {
				if (StringUtil.equals(
						ddmFormField.getType(),
						DDMFormFieldTypeConstants.FIELDSET)) {

					ddmFormFieldValues.addAll(
						_getDDMFormFieldValues(
							ddmFormField.getNestedDDMFormFields(),
							ddmFormFieldsMap, locale, properties));
				}

				DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

				ddmFormFieldValue.setName(ddmFormField.getName());

				ddmFormFieldValue.setValue(
					_getDDMFormFieldValue(
						ddmFormField, ddmFormFieldsMap, locale, properties));

				ddmFormFieldValues.add(ddmFormFieldValue);
			});

		return ddmFormFieldValues;
	}

	private DDMFormValues _getDDMFormValues(
		DDMForm ddmForm, ObjectEntry objectEntry) {

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(ddmForm.getDefaultLocale());
		ddmFormValues.setDDMFormFieldValues(
			_getDDMFormFieldValues(
				ddmForm.getDDMFormFields(), ddmForm.getDDMFormFieldsMap(true),
				ddmForm.getDefaultLocale(), objectEntry.getProperties()));
		ddmFormValues.setDefaultLocale(ddmForm.getDefaultLocale());

		return ddmFormValues;
	}

	private DefaultDTOConverterContext _getDTOConverterContext(
		Long objectEntryId, User user, Locale locale) {

		return new DefaultDTOConverterContext(
			true,
			Collections.singletonMap(
				"delete", Collections.singletonMap("delete", "")),
			null, null, objectEntryId, locale, null, user);
	}

	private long _getObjectDefinitionId(
			DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest)
		throws Exception {

		DDMFormInstance ddmFormInstance =
			ddmStorageAdapterSaveRequest.getDDMFormInstance();

		DDMFormInstanceSettings ddmFormInstanceSettings =
			ddmFormInstance.getSettingsModel();

		return GetterUtil.getLong(ddmFormInstanceSettings.objectDefinitionId());
	}

	private Map<String, Object> _getObjectEntryProperties(
			List<DDMFormFieldValue> ddmFormFieldValues,
			List<ObjectField> objectFields)
		throws ParseException {

		Map<String, Object> properties = new HashMap<>();

		Stream<ObjectField> stream = objectFields.stream();

		Map<String, String> objectFieldTypes = stream.collect(
			Collectors.toMap(ObjectField::getName, ObjectField::getType));

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			if (StringUtil.equals(
					ddmFormFieldValue.getType(),
					DDMFormFieldTypeConstants.FIELDSET)) {

				properties.putAll(
					_getObjectEntryProperties(
						ddmFormFieldValue.getNestedDDMFormFieldValues(),
						objectFields));
			}
			else {
				String objectFieldName = _getObjectFieldName(
					ddmFormFieldValue.getDDMFormField());

				Value value = ddmFormFieldValue.getValue();

				Map<Locale, String> values = value.getValues();

				properties.put(
					objectFieldName,
					_getValue(
						value.getDefaultLocale(),
						objectFieldTypes.get(objectFieldName),
						values.get(value.getDefaultLocale())));
			}
		}

		return properties;
	}

	private String _getObjectFieldName(DDMFormField ddmFormField) {
		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(
				(String)ddmFormField.getProperty("objectFieldName"));

			return jsonArray.getString(0);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return StringPool.BLANK;
		}
	}

	private Object _getValue(
			Locale locale, String objectFieldType, String value)
		throws ParseException {

		if (Objects.equals(objectFieldType, "BigDecimal")) {
			return GetterUtil.get(value, BigDecimal.ZERO);
		}
		else if (Objects.equals(objectFieldType, "Blob")) {
			return value.getBytes();
		}
		else if (Objects.equals(objectFieldType, "Boolean")) {
			return GetterUtil.getBoolean(value);
		}
		else if (Objects.equals(objectFieldType, "Double")) {
			NumberFormat numberFormat = NumberFormat.getInstance(locale);

			return GetterUtil.getDouble(numberFormat.parse(value));
		}
		else if (Objects.equals(objectFieldType, "Integer")) {
			return GetterUtil.getInteger(value);
		}
		else if (Objects.equals(objectFieldType, "Long")) {
			return GetterUtil.getLong(value);
		}
		else {
			return value;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectDDMStorageAdapter.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private UserLocalService _userLocalService;

}