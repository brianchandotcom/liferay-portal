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

package com.liferay.data.engine.rest.internal.storage;

import com.liferay.data.engine.rest.dto.v2_0.DataRecord;
import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataRecordCollectionUtil;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataRecordValuesUtil;
import com.liferay.data.engine.storage.DataStorage;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true, property = "data.storage.type=json",
	service = DataStorage.class
)
public class JSONDataStorage implements DataStorage {

	@Override
	public long delete(long dataStorageId) throws Exception {
		DDMContent ddmContent = _ddmContentLocalService.fetchDDMContent(
			dataStorageId);

		if (ddmContent != null) {
			_ddmContentLocalService.deleteDDMContent(ddmContent);
		}

		return dataStorageId;
	}

	@Override
	public Map<String, Object> get(long dataDefinitionId, long dataStorageId)
		throws Exception {

		DDMContent ddmContent = _ddmContentLocalService.getContent(
			dataStorageId);

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		return _toDataRecordValues(
			deserialize(
				ddmContent.getData(), ddmStructure.getFullHierarchyDDMForm()));
	}

	@Override
	public long save(
			long dataRecordCollectionId, Map<String, Object> dataRecordValues,
			long siteId)
		throws Exception {

		DataRecordCollection dataRecordCollection =
			DataRecordCollectionUtil.toDataRecordCollection(
				_ddlRecordSetLocalService.getRecordSet(dataRecordCollectionId));

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataRecordCollection.getDataDefinitionId());

		DDMContent ddmContent = _ddmContentLocalService.addContent(
			PrincipalThreadLocal.getUserId(), siteId,
			DataRecord.class.getName(), null,
			serialize(
				DataRecordValuesUtil.toDDMFormValues(
					dataRecordValues, ddmStructure.getFullHierarchyDDMForm(),
					_portal.getSiteDefaultLocale(siteId))),
			new ServiceContext() {
				{
					setScopeGroupId(siteId);
					setUserId(PrincipalThreadLocal.getUserId());
				}
			});

		return ddmContent.getPrimaryKey();
	}

	protected DDMFormValues deserialize(String content, DDMForm ddmForm) {
		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				_jsonDDMFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	protected String serialize(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				_jsonDDMFormValuesSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
	}

	private Map<String, Object> _toDataRecordValues(DDMFormValues ddmFormValues)
		throws JSONException {

		Map<String, Object> dataRecordValues = new HashMap<>();

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			Value value = ddmFormFieldValue.getValue();

			if (value != null) {
				DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

				String fieldType = ddmFormField.getType();

				if (fieldType.equals(DDMFormFieldType.CHECKBOX_MULTIPLE) ||
					fieldType.equals(DDMFormFieldType.SELECT)) {

					dataRecordValues.put(
						ddmFormFieldValue.getName(),
						JSONUtil.toStringList(
							JSONFactoryUtil.createJSONArray(
								value.getString(value.getDefaultLocale()))));
				}
				else {
					dataRecordValues.put(
						ddmFormFieldValue.getName(),
						value.getString(value.getDefaultLocale()));
				}
			}
		}

		return dataRecordValues;
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMContentLocalService _ddmContentLocalService;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference(target = "(ddm.form.values.deserializer.type=json)")
	private DDMFormValuesDeserializer _jsonDDMFormValuesDeserializer;

	@Reference(target = "(ddm.form.values.serializer.type=json)")
	private DDMFormValuesSerializer _jsonDDMFormValuesSerializer;

	@Reference
	private Portal _portal;

}