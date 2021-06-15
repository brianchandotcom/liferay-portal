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

package com.liferay.object.dynamic.data.mapping.storage.internal;

import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterDeleteResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterGetResponse;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveRequest;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterSaveResponse;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldModel;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

			Map<String, Object> properties = objectEntry.getProperties();

			_ddmFieldLocalService.deleteDDMFormValues(
				(Long)properties.get("ddmStorageId"));

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

			ObjectEntry objectEntry = _objectEntryManager.getObjectEntry(
				_getDTOConverterContext(
					ddmStorageAdapterGetRequest.getPrimaryKey(), null,
					ddmForm.getDefaultLocale()),
				ddmStorageAdapterGetRequest.getPrimaryKey());

			Map<String, Object> properties = objectEntry.getProperties();

			DDMFormValues ddmFormValues =
				_ddmFieldLocalService.getDDMFormValues(
					ddmForm, (Long)properties.get("ddmStorageId"));

			for (DDMFormFieldValue ddmFormFieldValue :
					ddmFormValues.getDDMFormFieldValues()) {

				DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();
				Value value = new LocalizedValue(ddmForm.getDefaultLocale());

				value.addString(
					ddmForm.getDefaultLocale(),
					(String)properties.get(
						StringUtil.toLowerCase(ddmFormField.getName())));

				ddmFormFieldValue.setValue(value);
			}

			return DDMStorageAdapterGetResponse.Builder.newBuilder(
				ddmFormValues
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

			DDMStorageAdapterSaveResponse ddmStorageAdapterSaveResponse =
				_ddmStorageAdapter.save(ddmStorageAdapterSaveRequest);

			DDMFormInstance ddmFormInstance =
				ddmStorageAdapterSaveRequest.getDDMFormInstance();

			DDMFormInstanceSettings ddmFormInstanceSettings =
				ddmFormInstance.getSettingsModel();

			long objectDefinitionId = Long.parseLong(
				ddmFormInstanceSettings.objectDefinitionId());

			ObjectEntry addObjectEntry = _objectEntryManager.addObjectEntry(
				_getDTOConverterContext(null, user, ddmForm.getDefaultLocale()),
				user.getUserId(), objectDefinitionId,
				new ObjectEntry() {
					{
						properties = _getObjectEntryProperties(
							ddmStorageAdapterSaveRequest,
							ddmStorageAdapterSaveResponse,
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

	private DefaultDTOConverterContext _getDTOConverterContext(
		Long objectEntryId, User user, Locale locale) {

		return new DefaultDTOConverterContext(
			true,
			Collections.singletonMap(
				"delete", Collections.singletonMap("delete", "")),
			null, null, objectEntryId, locale, null, user);
	}

	private Map<String, Object> _getObjectEntryProperties(
		DDMStorageAdapterSaveRequest ddmStorageAdapterSaveRequest,
		DDMStorageAdapterSaveResponse ddmStorageAdapterSaveResponse,
		List<ObjectField> objectFields) {

		Map<String, Object> properties = HashMapBuilder.<String, Object>put(
			"ddmStorageId", ddmStorageAdapterSaveResponse.getPrimaryKey()
		).build();

		Stream<ObjectField> stream = objectFields.stream();

		List<String> objectFieldNames = stream.map(
			ObjectFieldModel::getName
		).collect(
			Collectors.toList()
		);

		DDMFormValues ddmFormValues =
			ddmStorageAdapterSaveRequest.getDDMFormValues();

		for (DDMFormFieldValue ddmFormValue :
				ddmFormValues.getDDMFormFieldValues()) {

			DDMFormField ddmFormField = ddmFormValue.getDDMFormField();

			if (!objectFieldNames.contains(ddmFormField.getFieldReference())) {
				continue;
			}

			Value value = ddmFormValue.getValue();

			Map<Locale, String> values = value.getValues();

			properties.put(
				StringUtil.toLowerCase(ddmFormField.getFieldReference()),
				values.get(value.getDefaultLocale()));
		}

		return properties;
	}

	@Reference
	private DDMFieldLocalService _ddmFieldLocalService;

	@Reference(target = "(ddm.storage.adapter.type=default)")
	private DDMStorageAdapter _ddmStorageAdapter;

	@Reference
	private ObjectEntryManager _objectEntryManager;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private UserLocalService _userLocalService;

}