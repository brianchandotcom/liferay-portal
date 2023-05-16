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
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.exception.ObjectValidationRuleEngineException;
import com.liferay.object.exception.ObjectValidationRuleNameException;
import com.liferay.object.exception.ObjectValidationRuleScriptException;
import com.liferay.object.internal.action.util.ObjectEntryVariablesUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.scripting.exception.ObjectScriptingException;
import com.liferay.object.scripting.validator.ObjectScriptingValidator;
import com.liferay.object.service.base.ObjectValidationRuleLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.object.validation.rule.ObjectValidationRuleEngineRegistry;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectValidationRule",
	service = AopService.class
)
public class ObjectValidationRuleLocalServiceImpl
	extends ObjectValidationRuleLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectValidationRule addObjectValidationRule(
			long userId, long objectDefinitionId, boolean active, String engine,
			Map<Locale, String> errorLabelMap, Map<Locale, String> nameMap,
			String script)
		throws PortalException {

		_validateEngine(engine);
		_validateName(nameMap);
		_validateScript(engine, script);

		ObjectValidationRule objectValidationRule =
			objectValidationRulePersistence.create(
				counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectValidationRule.setCompanyId(user.getCompanyId());
		objectValidationRule.setUserId(user.getUserId());
		objectValidationRule.setUserName(user.getFullName());

		objectValidationRule.setObjectDefinitionId(objectDefinitionId);
		objectValidationRule.setActive(active);
		objectValidationRule.setEngine(engine);
		objectValidationRule.setErrorLabelMap(errorLabelMap);
		objectValidationRule.setNameMap(nameMap);
		objectValidationRule.setScript(script);

		return objectValidationRulePersistence.update(objectValidationRule);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public ObjectValidationRule deleteObjectValidationRule(
			long objectValidationRuleId)
		throws PortalException {

		ObjectValidationRule objectValidationRule =
			objectValidationRulePersistence.findByPrimaryKey(
				objectValidationRuleId);

		return deleteObjectValidationRule(objectValidationRule);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public ObjectValidationRule deleteObjectValidationRule(
		ObjectValidationRule objectValidationRule) {

		return objectValidationRulePersistence.remove(objectValidationRule);
	}

	@Override
	public void deleteObjectValidationRules(Long objectDefinitionId)
		throws PortalException {

		for (ObjectValidationRule objectValidationRule :
				objectValidationRulePersistence.findByObjectDefinitionId(
					objectDefinitionId)) {

			objectValidationRuleLocalService.deleteObjectValidationRule(
				objectValidationRule);
		}
	}

	@Override
	public ObjectValidationRule getObjectValidationRule(
			long objectValidationRuleId)
		throws PortalException {

		return objectValidationRulePersistence.findByPrimaryKey(
			objectValidationRuleId);
	}

	@Override
	public List<ObjectValidationRule> getObjectValidationRules(
		long objectDefinitionId) {

		return objectValidationRulePersistence.findByObjectDefinitionId(
			objectDefinitionId);
	}

	@Override
	public List<ObjectValidationRule> getObjectValidationRules(
		long objectDefinitionId, boolean active) {

		return objectValidationRulePersistence.findByODI_A(
			objectDefinitionId, active);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectValidationRule updateObjectValidationRule(
			long objectValidationRuleId, boolean active, String engine,
			Map<Locale, String> errorLabelMap, Map<Locale, String> nameMap,
			String script)
		throws PortalException {

		_validateEngine(engine);
		_validateName(nameMap);
		_validateScript(engine, script);

		ObjectValidationRule objectValidationRule =
			objectValidationRulePersistence.findByPrimaryKey(
				objectValidationRuleId);

		objectValidationRule.setActive(active);
		objectValidationRule.setEngine(engine);
		objectValidationRule.setErrorLabelMap(errorLabelMap);
		objectValidationRule.setNameMap(nameMap);
		objectValidationRule.setScript(script);

		return objectValidationRulePersistence.update(objectValidationRule);
	}

	@Override
	@Transactional(readOnly = true)
	public void validate(
			BaseModel<?> baseModel, long objectDefinitionId,
			JSONObject payloadJSONObject, long userId)
		throws PortalException {

		if (baseModel == null) {
			return;
		}

		List<ObjectValidationRule> objectValidationRules =
			objectValidationRuleLocalService.getObjectValidationRules(
				objectDefinitionId, true);

		if (ListUtil.isEmpty(objectValidationRules)) {
			return;
		}

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.fetchByPrimaryKey(objectDefinitionId);

		Map<String, Object> variables = ObjectEntryVariablesUtil.getVariables(
			_dtoConverterRegistry, objectDefinition, payloadJSONObject,
			_systemObjectDefinitionManagerRegistry);

		for (ObjectValidationRule objectValidationRule :
				objectValidationRules) {

			Map<String, Object> results = new HashMap<>();

			ObjectValidationRuleEngine objectValidationRuleEngine =
				_objectValidationRuleEngineRegistry.
					getObjectValidationRuleEngine(
						objectValidationRule.getEngine());

			if (StringUtil.equals(
					objectValidationRuleEngine.getName(),
					ObjectValidationRuleConstants.ENGINE_TYPE_DDM)) {

				results = objectValidationRuleEngine.execute(
					variables, objectValidationRule.getScript());
			}
			else {
				results = objectValidationRuleEngine.execute(
					(Map<String, Object>)variables.get("baseModel"),
					objectValidationRule.getScript());
			}

			if (GetterUtil.getBoolean(results.get("invalidFields"))) {
				User user = _userLocalService.fetchUser(userId);

				throw new ObjectValidationRuleEngineException.InvalidFields(
					objectValidationRule.getErrorLabel(user.getLocale()));
			}

			if (GetterUtil.getBoolean(results.get("invalidScript"))) {
				throw new ObjectValidationRuleEngineException.InvalidScript();
			}
		}
	}

	private void _validateEngine(String engine) throws PortalException {
		if (Validator.isNull(engine)) {
			throw new ObjectValidationRuleEngineException.MustNotBeNull();
		}

		ObjectValidationRuleEngine objectValidationRuleEngine =
			_objectValidationRuleEngineRegistry.getObjectValidationRuleEngine(
				engine);

		if (objectValidationRuleEngine == null) {
			throw new ObjectValidationRuleEngineException.NoSuchEngine(engine);
		}
	}

	private void _validateName(Map<Locale, String> nameMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		if ((nameMap == null) || Validator.isNull(nameMap.get(locale))) {
			throw new ObjectValidationRuleNameException(
				"Name is null for locale " + locale.getDisplayName());
		}
	}

	private void _validateScript(String engine, String script)
		throws PortalException {

		if (Validator.isNull(script)) {
			throw new ObjectValidationRuleScriptException("required");
		}

		try {
			if (Objects.equals(
					engine, ObjectValidationRuleConstants.ENGINE_TYPE_DDM)) {

				_ddmExpressionFactory.createExpression(
					CreateExpressionRequest.Builder.newBuilder(
						script
					).build());
			}
			else if (Objects.equals(
						engine,
						ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY)) {

				_objectScriptingValidator.validate("groovy", script);
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			if (portalException instanceof ObjectScriptingException) {
				ObjectScriptingException objectScriptingException =
					(ObjectScriptingException)portalException;

				throw new ObjectValidationRuleScriptException(
					objectScriptingException.getMessageKey());
			}

			throw new ObjectValidationRuleScriptException("syntax-error");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectValidationRuleLocalServiceImpl.class);

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectScriptingValidator _objectScriptingValidator;

	@Reference
	private ObjectValidationRuleEngineRegistry
		_objectValidationRuleEngineRegistry;

	@Reference
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

	@Reference
	private UserLocalService _userLocalService;

}