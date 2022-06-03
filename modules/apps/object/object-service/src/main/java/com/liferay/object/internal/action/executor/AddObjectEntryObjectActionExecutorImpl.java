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
import com.liferay.object.internal.action.util.ObjectActionVariablesUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
@Component(service = ObjectActionExecutor.class)
public class AddObjectEntryObjectActionExecutorImpl
	implements ObjectActionExecutor {

	@Override
	public void execute(
			long companyId, UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		if (!GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-152180"))) {
			throw new UnsupportedOperationException();
		}

		ObjectDefinition targetObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				GetterUtil.getLong(
					parametersUnicodeProperties.get("objectDefinitionId")));

		if ((targetObjectDefinition == null) ||
			targetObjectDefinition.isSystem()) {

			return;
		}

		long defaultUserId = _userLocalService.getDefaultUserId(companyId);
		ObjectDefinition sourceObjectDefinition =
			_objectDefinitionLocalService.fetchObjectDefinition(
				payloadJSONObject.getLong("objectDefinitionId"));

		ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
			defaultUserId,
			_getGroupId(
				companyId, payloadJSONObject, sourceObjectDefinition,
				targetObjectDefinition),
			targetObjectDefinition.getObjectDefinitionId(),
			_getValues(
				sourceObjectDefinition, parametersUnicodeProperties,
				payloadJSONObject),
			_getServiceContext(companyId, defaultUserId));

		if (!GetterUtil.getBoolean(
				parametersUnicodeProperties.get("relatedObjectEntries"))) {

			return;
		}

		for (ObjectRelationship objectRelationship :
				_objectRelationshipLocalService.getObjectRelationships(
					sourceObjectDefinition.getObjectDefinitionId())) {

			if (!Objects.equals(
					objectRelationship.getObjectDefinitionId2(),
					targetObjectDefinition.getObjectDefinitionId())) {

				continue;
			}

			_objectRelationshipLocalService.
				addObjectRelationshipMappingTableValues(
					userId, objectRelationship.getObjectRelationshipId(),
					payloadJSONObject.getLong("classPK"),
					objectEntry.getObjectEntryId(),
					_getServiceContext(companyId, userId));
		}
	}

	@Override
	public String getKey() {
		return ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY;
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

	private long _getGroupId(
			long companyId, JSONObject payloadJSONObject,
			ObjectDefinition sourceObjectDefinition,
			ObjectDefinition targetObjectDefinition)
		throws Exception {

		ObjectScopeProvider targetObjectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				targetObjectDefinition.getScope());

		if (!targetObjectScopeProvider.isGroupAware()) {
			return 0L;
		}

		ObjectScopeProvider sourceObjectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				sourceObjectDefinition.getScope());

		if (!sourceObjectScopeProvider.isGroupAware()) {
			Group companyGroup = _groupLocalService.fetchCompanyGroup(
				companyId);

			return companyGroup.getGroupId();
		}

		if (sourceObjectDefinition.isSystem()) {
			return MapUtil.getLong(
				(Map<String, Object>)payloadJSONObject.get(
					"model" + sourceObjectDefinition.getName()),
				"groupId");
		}

		return MapUtil.getLong(
			(Map<String, Object>)payloadJSONObject.get("objectEntry"),
			"groupId");
	}

	private ServiceContext _getServiceContext(long companyId, long userId) {
		return new ServiceContext() {
			{
				setCompanyId(companyId);
				setUserId(userId);
			}
		};
	}

	private Map<String, Serializable> _getValues(
			ObjectDefinition objectDefinition,
			UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject)
		throws Exception {

		Map<String, Serializable> values = new HashMap<>();

		Map<String, Object> variables = ObjectActionVariablesUtil.toVariables(
			_dtoConverterRegistry, objectDefinition, payloadJSONObject);

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			parametersUnicodeProperties.get("predefinedValues"));

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Serializable value = (Serializable)jsonObject.get("value");

			if (!jsonObject.getBoolean("inputAsValue")) {
				value = _evaluateExpression(value.toString(), variables);
			}

			values.put(jsonObject.getString("name"), value);
		}

		return values;
	}

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectRelationshipLocalService _objectRelationshipLocalService;

	@Reference
	private ObjectScopeProviderRegistry _objectScopeProviderRegistry;

	@Reference
	private UserLocalService _userLocalService;

}