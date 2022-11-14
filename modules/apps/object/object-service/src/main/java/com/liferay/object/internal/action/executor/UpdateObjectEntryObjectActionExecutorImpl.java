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

package com.liferay.object.internal.action.executor;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpression;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.internal.action.util.ObjectEntryVariablesUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadataRegistry;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ObjectActionExecutor.class)
public class UpdateObjectEntryObjectActionExecutorImpl
	implements ObjectActionExecutor {

	@Override
	public void execute(
			long companyId, UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		ObjectDefinition sourceObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				payloadJSONObject.getLong("objectDefinitionId"));
		User user = _userLocalService.getUser(userId);

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerRegistry.getObjectEntryManager(
				sourceObjectDefinition.getStorageType());

		objectEntryManager.updateObjectEntry(
			new DefaultDTOConverterContext(
				false, Collections.emptyMap(), _dtoConverterRegistry, null,
				user.getLocale(), null, user),
			sourceObjectDefinition,
			GetterUtil.getLong(payloadJSONObject.getLong("classPK")),
			new ObjectEntry() {
				{
					properties = _getValues(
						sourceObjectDefinition, parametersUnicodeProperties,
						payloadJSONObject);
				}
			});
	}

	@Override
	public String getKey() {
		return ObjectActionExecutorConstants.KEY_UPDATE_OBJECT_ENTRY;
	}

	private Serializable _evaluateExpression(
			String expression, Map<String, Object> variables)
		throws Exception {

		DDMExpression<Serializable> ddmExpression =
			_ddmExpressionFactory.createExpression(
				CreateExpressionRequest.Builder.newBuilder(
					expression
				).build());

		ddmExpression.setVariables(variables);

		return ddmExpression.evaluate();
	}

	private Map<String, Object> _getValues(
			ObjectDefinition objectDefinition,
			UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject)
		throws Exception {

		Map<String, Object> values = new HashMap<>();

		Map<String, Object> variables =
			ObjectEntryVariablesUtil.getActionVariables(
				_dtoConverterRegistry, objectDefinition, payloadJSONObject,
				_systemObjectDefinitionMetadataRegistry);

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			parametersUnicodeProperties.get("predefinedValues"));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Object value = jsonObject.get("value");

			if (Validator.isNull(value)) {
				continue;
			}

			if (!jsonObject.getBoolean("inputAsValue")) {
				value = _evaluateExpression(value.toString(), variables);
			}

			values.put(jsonObject.getString("name"), value);
		}

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId(), false);

		objectFields.forEach(
			objectField -> {
				if (!values.containsKey(objectField.getName())) {
					values.put(
						objectField.getName(),
						variables.get(objectField.getName()));
				}
			});

		return values;
	}

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryManagerRegistry _objectEntryManagerRegistry;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private SystemObjectDefinitionMetadataRegistry
		_systemObjectDefinitionMetadataRegistry;

	@Reference
	private UserLocalService _userLocalService;

}