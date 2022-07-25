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

package com.liferay.object.service.impl;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.object.action.executor.ObjectActionExecutorRegistry;
import com.liferay.object.constants.ObjectActionConstants;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.exception.ObjectActionConditionExpressionException;
import com.liferay.object.exception.ObjectActionExecutorKeyException;
import com.liferay.object.exception.ObjectActionNameException;
import com.liferay.object.exception.ObjectActionParametersException;
import com.liferay.object.exception.ObjectActionTriggerKeyException;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.base.ObjectActionLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;

import groovy.lang.GroovyShell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectAction",
	service = AopService.class
)
public class ObjectActionLocalServiceImpl
	extends ObjectActionLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectAction addObjectAction(
			long userId, long objectDefinitionId, boolean active,
			String conditionExpression, String description, String name,
			String objectActionExecutorKey, String objectActionTriggerKey,
			UnicodeProperties parametersUnicodeProperties)
		throws PortalException {

		_validate(
			conditionExpression, name, objectActionExecutorKey,
			objectActionTriggerKey, parametersUnicodeProperties);

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		ObjectAction objectAction = objectActionPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectAction.setCompanyId(user.getCompanyId());
		objectAction.setUserId(user.getUserId());
		objectAction.setUserName(user.getFullName());

		objectAction.setObjectDefinitionId(
			objectDefinition.getObjectDefinitionId());
		objectAction.setActive(active);
		objectAction.setConditionExpression(conditionExpression);
		objectAction.setDescription(description);
		objectAction.setName(name);
		objectAction.setObjectActionExecutorKey(objectActionExecutorKey);
		objectAction.setObjectActionTriggerKey(objectActionTriggerKey);
		objectAction.setParameters(parametersUnicodeProperties.toString());
		objectAction.setStatus(ObjectActionConstants.STATUS_NEVER_RAN);

		return objectActionPersistence.update(objectAction);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public ObjectAction deleteObjectAction(long objectActionId)
		throws PortalException {

		ObjectAction objectAction = objectActionPersistence.findByPrimaryKey(
			objectActionId);

		return deleteObjectAction(objectAction);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public ObjectAction deleteObjectAction(ObjectAction objectAction) {
		return objectActionPersistence.remove(objectAction);
	}

	@Override
	public void deleteObjectActions(long objectDefinitionId)
		throws PortalException {

		for (ObjectAction objectAction :
				objectActionPersistence.findByObjectDefinitionId(
					objectDefinitionId)) {

			objectActionLocalService.deleteObjectAction(objectAction);
		}
	}

	@Override
	public List<ObjectAction> getObjectActions(long objectDefinitionId) {
		return objectActionPersistence.findByObjectDefinitionId(
			objectDefinitionId);
	}

	@Override
	public List<ObjectAction> getObjectActions(
		long objectDefinitionId, String objectActionTriggerKey) {

		return objectActionPersistence.findByO_A_OATK(
			objectDefinitionId, true, objectActionTriggerKey);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectAction updateObjectAction(
			long objectActionId, boolean active, String conditionExpression,
			String description, String name, String objectActionExecutorKey,
			String objectActionTriggerKey,
			UnicodeProperties parametersUnicodeProperties)
		throws PortalException {

		_validate(
			conditionExpression, name, objectActionExecutorKey,
			objectActionTriggerKey, parametersUnicodeProperties);

		ObjectAction objectAction = objectActionPersistence.findByPrimaryKey(
			objectActionId);

		objectAction.setActive(active);
		objectAction.setConditionExpression(conditionExpression);
		objectAction.setDescription(description);
		objectAction.setName(name);
		objectAction.setObjectActionExecutorKey(objectActionExecutorKey);
		objectAction.setObjectActionTriggerKey(objectActionTriggerKey);
		objectAction.setParameters(parametersUnicodeProperties.toString());
		objectAction.setStatus(ObjectActionConstants.STATUS_NEVER_RAN);

		return objectActionPersistence.update(objectAction);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectAction updateStatus(long objectActionId, int status)
		throws PortalException {

		ObjectAction objectAction = objectActionPersistence.findByPrimaryKey(
			objectActionId);

		objectAction.setStatus(status);

		return objectActionPersistence.update(objectAction);
	}

	private void _validate(
			String conditionExpression, String name,
			String objectActionExecutorKey, String objectActionTriggerKey,
			UnicodeProperties parametersUnicodeProperties)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new ObjectActionNameException();
		}

		if (!_objectActionExecutorRegistry.hasObjectActionExecutor(
				objectActionExecutorKey)) {

			throw new ObjectActionExecutorKeyException(objectActionExecutorKey);
		}

		if (!Objects.equals(
				objectActionTriggerKey,
				ObjectActionTriggerConstants.KEY_ON_AFTER_ADD) &&
			!Objects.equals(
				objectActionTriggerKey,
				ObjectActionTriggerConstants.KEY_ON_AFTER_DELETE) &&
			!Objects.equals(
				objectActionTriggerKey,
				ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE)) {

			if (!_messageBus.hasDestination(objectActionTriggerKey)) {
				throw new ObjectActionTriggerKeyException();
			}

			if (Validator.isNotNull(conditionExpression)) {
				throw new ObjectActionConditionExpressionException();
			}
		}

		Map<String, Object> errorMessageKeys = new HashMap<>();

		if (Validator.isNotNull(conditionExpression)) {
			try {
				_ddmExpressionFactory.createExpression(
					CreateExpressionRequest.Builder.newBuilder(
						conditionExpression
					).build());
			}
			catch (Exception exception) {
				errorMessageKeys.put("conditionExpression", "syntax-error");
			}
		}

		if (Objects.equals(
				objectActionExecutorKey,
				ObjectActionExecutorConstants.KEY_ADD_OBJECT_ENTRY)) {

			long objectDefinitionId = GetterUtil.getLong(
				parametersUnicodeProperties.get("objectDefinitionId"));

			ObjectDefinition objectDefinition =
				_objectDefinitionPersistence.fetchByPrimaryKey(
					objectDefinitionId);

			if ((objectDefinition == null) || !objectDefinition.isActive() ||
				!objectDefinition.isApproved() || objectDefinition.isSystem()) {

				errorMessageKeys.put("objectDefinitionId", "invalid");
			}
			else {
				_validatePredefinedValues(
					errorMessageKeys, objectDefinitionId,
					_jsonFactory.createJSONArray(
						parametersUnicodeProperties.get("predefinedValues")));
			}
		}
		else if (Objects.equals(
					objectActionExecutorKey,
					ObjectActionExecutorConstants.KEY_GROOVY)) {

			String script = parametersUnicodeProperties.get("script");

			if (Validator.isNotNull(script)) {
				try {
					if (StringUtil.count(script, StringPool.NEW_LINE) <= 2987) {
						GroovyShell groovyShell = new GroovyShell();

						groovyShell.parse(script);
					}
					else {
						errorMessageKeys.put(
							"script",
							"the-maximum-number-of-lines-available-is-2987");
					}
				}
				catch (Exception exception) {
					errorMessageKeys.put("script", "syntax-error");
				}
			}
		}
		else if (Objects.equals(
					objectActionExecutorKey,
					ObjectActionExecutorConstants.KEY_WEBHOOK)) {

			if (Validator.isNull(parametersUnicodeProperties.get("url"))) {
				errorMessageKeys.put("url", "required");
			}
		}

		if (MapUtil.isNotEmpty(errorMessageKeys)) {
			throw new ObjectActionParametersException(errorMessageKeys);
		}
	}

	private void _validatePredefinedValues(
		Map<String, Object> errorMessageKeys, long objectDefinitionId,
		JSONArray predefinedValuesJSONArray) {

		Map<String, String> predefinedValuesErrorMessageKeys = new HashMap<>();

		Map<String, String> predefinedValuesMap = new HashMap<>();

		for (int i = 0; i < predefinedValuesJSONArray.length(); i++) {
			JSONObject predefinedValueJSONObject =
				predefinedValuesJSONArray.getJSONObject(i);

			String name = predefinedValueJSONObject.getString("name");

			ObjectField objectField = _objectFieldLocalService.fetchObjectField(
				objectDefinitionId, name);

			if (objectField == null) {
				predefinedValuesErrorMessageKeys.put(name, "invalid");

				continue;
			}

			String value = predefinedValueJSONObject.getString("value");

			predefinedValuesMap.put(name, value);

			if (Validator.isNull(value) ||
				predefinedValueJSONObject.getBoolean("inputAsValue")) {

				continue;
			}

			try {
				_ddmExpressionFactory.createExpression(
					CreateExpressionRequest.Builder.newBuilder(
						value
					).build());
			}
			catch (Exception exception) {
				predefinedValuesErrorMessageKeys.put(name, "syntax-error");
			}
		}

		for (ObjectField objectField :
				_objectFieldLocalService.getObjectFields(objectDefinitionId)) {

			if (!objectField.isRequired() ||
				Validator.isNotNull(
					predefinedValuesMap.get(objectField.getName()))) {

				continue;
			}

			predefinedValuesErrorMessageKeys.put(
				objectField.getName(), "required");
		}

		if (MapUtil.isNotEmpty(predefinedValuesErrorMessageKeys)) {
			errorMessageKeys.put(
				"predefinedValues", predefinedValuesErrorMessageKeys);
		}
	}

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MessageBus _messageBus;

	@Reference
	private ObjectActionExecutorRegistry _objectActionExecutorRegistry;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private UserLocalService _userLocalService;

}