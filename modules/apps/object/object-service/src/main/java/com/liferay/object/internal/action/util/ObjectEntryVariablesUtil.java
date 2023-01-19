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

package com.liferay.object.internal.action.util;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.object.internal.dynamic.data.mapping.expression.ObjectDDMExpressionParameterAccessor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.object.system.JaxRsApplicationDescriptor;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadataRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.extension.EntityExtensionThreadLocal;
import com.liferay.portal.vulcan.util.ObjectMapperUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Carolina Barbosa
 */
public class ObjectEntryVariablesUtil {

	public static Map<String, Object> getActionVariables(
		DTOConverterRegistry dtoConverterRegistry,
		ObjectDefinition objectDefinition, JSONObject payloadJSONObject,
		SystemObjectDefinitionMetadataRegistry
			systemObjectDefinitionMetadataRegistry) {

		// TODO Remove all references to version 1 after March 2023

		if (PropsValues.OBJECT_ENTRY_SCRIPT_VARIABLES_VERSION == 2) {
			return HashMapBuilder.<String, Object>put(
				"objectEntry",
				_getVariables(
					dtoConverterRegistry, objectDefinition, false,
					payloadJSONObject, systemObjectDefinitionMetadataRegistry)
			).put(
				"originalObjectEntry",
				() -> {
					if (payloadJSONObject.has("originalObjectEntry")) {
						return _getVariables(
							dtoConverterRegistry, objectDefinition, true,
							payloadJSONObject,
							systemObjectDefinitionMetadataRegistry);
					}

					return Collections.emptyMap();
				}
			).build();
		}

		if (objectDefinition.isSystem()) {
			String contentType = _getContentType(
				dtoConverterRegistry, objectDefinition,
				systemObjectDefinitionMetadataRegistry);

			Object object = payloadJSONObject.get("modelDTO" + contentType);

			if (object == null) {
				return payloadJSONObject.toMap();
			}

			return HashMapBuilder.<String, Object>putAll(
				(Map<String, Object>)object
			).putAll(
				(Map<String, Object>)payloadJSONObject.get("extendedProperties")
			).put(
				"companyId", payloadJSONObject.getLong("companyId")
			).put(
				"creator", payloadJSONObject.get("userName")
			).put(
				"currentUserId", payloadJSONObject.getLong("userId")
			).put(
				"id", payloadJSONObject.getLong("classPK")
			).put(
				"objectDefinitionId",
				payloadJSONObject.getLong("objectDefinitionId")
			).put(
				"status", payloadJSONObject.get("status")
			).build();
		}

		Map<String, Object> variables = new HashMap<>(
			(Map)payloadJSONObject.get("objectEntry"));

		Object values = variables.get("values");

		if (values != null) {
			variables.putAll((Map<String, Object>)values);

			variables.remove("values");
		}

		variables.put("creator", variables.get("userName"));
		variables.put("currentUserId", payloadJSONObject.getLong("userId"));
		variables.put("id", payloadJSONObject.getLong("classPK"));

		return variables;
	}

	public static Map<String, Object> getValidationRuleVariables(
			BaseModel<?> baseModel, DTOConverterRegistry dtoConverterRegistry,
			ObjectDefinition objectDefinition,
			ObjectEntryLocalService objectEntryLocalService,
			JSONObject payloadJSONObject,
			SystemObjectDefinitionMetadataRegistry
				systemObjectDefinitionMetadataRegistry)
		throws PortalException {

		if (PropsValues.OBJECT_ENTRY_SCRIPT_VARIABLES_VERSION == 2) {
			return HashMapBuilder.<String, Object>put(
				"objectEntry",
				_getVariables(
					dtoConverterRegistry, objectDefinition, false,
					payloadJSONObject, systemObjectDefinitionMetadataRegistry)
			).put(
				"originalObjectEntry",
				() -> {
					if (payloadJSONObject.has("originalObjectEntry")) {
						return _getVariables(
							dtoConverterRegistry, objectDefinition, true,
							payloadJSONObject,
							systemObjectDefinitionMetadataRegistry);
					}

					return Collections.emptyMap();
				}
			).build();
		}

		Map<String, Object> variables = HashMapBuilder.<String, Object>putAll(
			baseModel.getModelAttributes()
		).build();

		if (baseModel instanceof ObjectEntry) {
			variables.putAll(
				objectEntryLocalService.getValues((ObjectEntry)baseModel));
		}
		else {
			Map<String, Serializable> extendedProperties =
				EntityExtensionThreadLocal.getExtendedProperties();

			if (extendedProperties != null) {
				variables.putAll(extendedProperties);
			}
		}

		variables.putAll(
			objectEntryLocalService.
				getExtensionDynamicObjectDefinitionTableValues(
					objectDefinition,
					GetterUtil.getLong(baseModel.getPrimaryKeyObj())));

		return variables;
	}

	public static Map<String, Object> getValues(
			DDMExpressionFactory ddmExpressionFactory,
			UnicodeProperties parametersUnicodeProperties,
			Map<String, Object> variables)
		throws Exception {

		Map<String, Object> values = new HashMap<>();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			parametersUnicodeProperties.get("predefinedValues"));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Object value = jsonObject.get("value");

			if (Validator.isNull(value)) {
				continue;
			}

			if (!jsonObject.getBoolean("inputAsValue")) {
				DDMExpression<Serializable> ddmExpression =
					ddmExpressionFactory.createExpression(
						CreateExpressionRequest.Builder.newBuilder(
							value.toString()
						).withDDMExpressionParameterAccessor(
							new ObjectDDMExpressionParameterAccessor(
								(Map<String, Object>)variables.get(
									"originalObjectEntry"))
						).build());

				ddmExpression.setVariables(
					(Map<String, Object>)variables.get("objectEntry"));

				value = ddmExpression.evaluate();
			}

			values.put(jsonObject.getString("name"), value);
		}

		return values;
	}

	private static String _getContentType(
		DTOConverterRegistry dtoConverterRegistry,
		ObjectDefinition objectDefinition,
		SystemObjectDefinitionMetadataRegistry
			systemObjectDefinitionMetadataRegistry) {

		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
			systemObjectDefinitionMetadataRegistry.
				getSystemObjectDefinitionMetadata(objectDefinition.getName());

		JaxRsApplicationDescriptor jaxRsApplicationDescriptor =
			systemObjectDefinitionMetadata.getJaxRsApplicationDescriptor();

		DTOConverter<?, ?> dtoConverter = dtoConverterRegistry.getDTOConverter(
			jaxRsApplicationDescriptor.getApplicationName(),
			objectDefinition.getClassName(),
			jaxRsApplicationDescriptor.getVersion());

		if (dtoConverter == null) {
			Class<?> modelClass =
				systemObjectDefinitionMetadata.getModelClass();

			return modelClass.getSimpleName();
		}

		return dtoConverter.getContentType();
	}

	private static Map<String, Object> _getVariables(
		DTOConverterRegistry dtoConverterRegistry,
		ObjectDefinition objectDefinition, boolean oldValues,
		JSONObject payloadJSONObject,
		SystemObjectDefinitionMetadataRegistry
			systemObjectDefinitionMetadataRegistry) {

		Map<String, Object> allowedVariables =
			HashMapBuilder.<String, Object>put(
				"creator", payloadJSONObject.get("userId")
			).put(
				"currentUserId", payloadJSONObject.get("userId")
			).build();

		Map<String, Object> variables = new HashMap<>();

		if (objectDefinition.isSystem()) {
			Object object = payloadJSONObject.get(
				"model" + objectDefinition.getName());

			if (object == null) {
				object = payloadJSONObject.get(
					StringUtil.lowerCaseFirstLetter(
						objectDefinition.getName()));
			}

			if (object == null) {
				return payloadJSONObject.toMap();
			}

			if (object instanceof JSONObject) {
				Map<String, Object> map = ObjectMapperUtil.readValue(
					Map.class, object);

				Map<String, Object> jsonObjectMap =
					(Map<String, Object>)map.get("_jsonObject");

				variables.putAll((Map<String, Object>)jsonObjectMap.get("map"));
			}
			else if (object instanceof Map) {
				variables.putAll((Map<String, Object>)object);
			}

			String contentType = _getContentType(
				dtoConverterRegistry, objectDefinition,
				systemObjectDefinitionMetadataRegistry);

			Map<String, Object> map =
				(Map<String, Object>)payloadJSONObject.get(
					"modelDTO" + contentType);

			if (map != null) {
				variables.putAll(map);
			}
		}
		else {
			if (oldValues) {
				variables.putAll(
					(Map<String, Object>)payloadJSONObject.get(
						"originalObjectEntry"));
			}
			else {
				variables.putAll(
					(Map<String, Object>)payloadJSONObject.get("objectEntry"));
			}

			variables.putAll((Map<String, Object>)variables.get("values"));

			variables.remove("values");

			Object objectEntryId = variables.get("objectEntryId");

			if (objectEntryId != null) {
				allowedVariables.put("id", objectEntryId);
			}
		}

		variables.remove("creator");

		List<ObjectField> objectFields =
			ObjectFieldLocalServiceUtil.getObjectFields(
				objectDefinition.getObjectDefinitionId());

		for (ObjectField objectField : objectFields) {
			if (!allowedVariables.containsKey(objectField.getName())) {
				allowedVariables.put(
					objectField.getName(),
					variables.get(objectField.getName()));
			}
		}

		return allowedVariables;
	}

}