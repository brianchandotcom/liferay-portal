/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.impl;

import com.liferay.dynamic.data.mapping.expression.CreateExpressionRequest;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.constants.ObjectValidationRuleSettingConstants;
import com.liferay.object.exception.ObjectValidationRuleEngineException;
import com.liferay.object.exception.ObjectValidationRuleNameException;
import com.liferay.object.exception.ObjectValidationRuleOutputTypeException;
import com.liferay.object.exception.ObjectValidationRuleScriptException;
import com.liferay.object.exception.ObjectValidationRuleSettingNameException;
import com.liferay.object.exception.ObjectValidationRuleSettingValueException;
import com.liferay.object.internal.action.util.ObjectEntryVariablesUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.model.ObjectValidationRuleSetting;
import com.liferay.object.scripting.exception.ObjectScriptingException;
import com.liferay.object.scripting.validator.ObjectScriptingValidator;
import com.liferay.object.service.ObjectValidationRuleSettingLocalService;
import com.liferay.object.service.base.ObjectValidationRuleLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.object.service.persistence.ObjectValidationRuleSettingPersistence;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.object.validation.rule.ObjectValidationRuleEngineRegistry;
import com.liferay.object.validation.rule.ObjectValidationRuleResult;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
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

import java.util.ArrayList;
import java.util.Collections;
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
			String outputType, String script,
			List<ObjectValidationRuleSetting> objectValidationRuleSettings)
		throws PortalException {

		_validate(
			engine, nameMap, outputType, script, objectValidationRuleSettings);

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
		objectValidationRule.setOutputType(outputType);
		objectValidationRule.setScript(script);

		objectValidationRule = objectValidationRulePersistence.update(
			objectValidationRule);

		objectValidationRule.setObjectValidationRuleSettings(
			_addObjectValidationRuleSettings(
				objectValidationRule, objectValidationRuleSettings));

		return objectValidationRule;
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

		objectValidationRule = objectValidationRulePersistence.remove(
			objectValidationRule);

		_objectValidationRuleSettingPersistence.removeByObjectValidationRuleId(
			objectValidationRule.getObjectValidationRuleId());

		return objectValidationRule;
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

		ObjectValidationRule objectValidationRule =
			objectValidationRulePersistence.findByPrimaryKey(
				objectValidationRuleId);

		objectValidationRule.setObjectValidationRuleSettings(
			_objectValidationRuleSettingPersistence.
				findByObjectValidationRuleId(objectValidationRuleId));

		return objectValidationRule;
	}

	@Override
	public List<ObjectValidationRule> getObjectValidationRules(
		long objectDefinitionId) {

		return _getObjectValidationRules(
			objectValidationRulePersistence.findByObjectDefinitionId(
				objectDefinitionId));
	}

	@Override
	public List<ObjectValidationRule> getObjectValidationRules(
		long objectDefinitionId, boolean active) {

		return _getObjectValidationRules(
			objectValidationRulePersistence.findByODI_A(
				objectDefinitionId, active));
	}

	@Override
	public int getObjectValidationRulesCount(
		long objectDefinitionId, boolean active) {

		return objectValidationRulePersistence.countByODI_A(
			objectDefinitionId, active);
	}

	@Override
	public void unassociateObjectField(ObjectField objectField) {
		for (ObjectValidationRule objectValidationRule :
				objectValidationRulePersistence.findByODI_O(
					objectField.getObjectDefinitionId(),
					ObjectValidationRuleConstants.
						OUTPUT_TYPE_PARTIAL_VALIDATION)) {

			ObjectValidationRuleSetting objectValidationRuleSetting =
				_objectValidationRuleSettingPersistence.fetchByOVRI_N_V(
					objectValidationRule.getObjectValidationRuleId(),
					ObjectValidationRuleSettingConstants.NAME_OBJECT_FIELD_ID,
					String.valueOf(objectField.getObjectFieldId()));

			if (objectValidationRuleSetting == null) {
				continue;
			}

			_objectValidationRuleSettingPersistence.remove(
				objectValidationRuleSetting);

			int count = _objectValidationRuleSettingPersistence.countByOVRI_N(
				objectValidationRule.getObjectValidationRuleId(),
				ObjectValidationRuleSettingConstants.NAME_OBJECT_FIELD_ID);

			if (count == 0) {
				objectValidationRule.setOutputType(
					ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION);

				objectValidationRulePersistence.update(objectValidationRule);
			}
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectValidationRule updateObjectValidationRule(
			long objectValidationRuleId, boolean active, String engine,
			Map<Locale, String> errorLabelMap, Map<Locale, String> nameMap,
			String outputType, String script,
			List<ObjectValidationRuleSetting> objectValidationRuleSettings)
		throws PortalException {

		_validate(
			engine, nameMap, outputType, script, objectValidationRuleSettings);

		ObjectValidationRule objectValidationRule =
			objectValidationRulePersistence.findByPrimaryKey(
				objectValidationRuleId);

		objectValidationRule.setActive(active);
		objectValidationRule.setEngine(engine);
		objectValidationRule.setErrorLabelMap(errorLabelMap);
		objectValidationRule.setNameMap(nameMap);
		objectValidationRule.setOutputType(outputType);
		objectValidationRule.setScript(script);

		objectValidationRule = objectValidationRulePersistence.update(
			objectValidationRule);

		_objectValidationRuleSettingPersistence.removeByObjectValidationRuleId(
			objectValidationRuleId);

		objectValidationRule.setObjectValidationRuleSettings(
			_addObjectValidationRuleSettings(
				objectValidationRule, objectValidationRuleSettings));

		return objectValidationRule;
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

		List<ObjectValidationRuleResult> objectValidationRuleResults =
			new ArrayList<>();

		for (ObjectValidationRule objectValidationRule :
				objectValidationRules) {

			Map<String, Object> results = new HashMap<>();

			ObjectValidationRuleEngine objectValidationRuleEngine =
				_objectValidationRuleEngineRegistry.
					getObjectValidationRuleEngine(
						objectValidationRule.getEngine());

			if (StringUtil.equals(
					objectValidationRuleEngine.getLabel(),
					ObjectValidationRuleConstants.ENGINE_TYPE_DDM)) {

				results = objectValidationRuleEngine.execute(
					variables, objectValidationRule.getScript());
			}
			else if (StringUtil.equals(
						objectValidationRuleEngine.getLabel(),
						ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY)) {

				results = objectValidationRuleEngine.execute(
					(Map<String, Object>)variables.get("baseModel"),
					objectValidationRule.getScript());
			}
			else {
				results = objectValidationRuleEngine.execute(
					(Map<String, Object>)variables.get("entryDTO"), null);
			}

			Locale locale = LocaleUtil.getMostRelevantLocale();

			User user = _userLocalService.fetchUser(userId);

			if (user != null) {
				locale = user.getLocale();
			}

			if (!FeatureFlagManagerUtil.isEnabled("LPS-187846")) {
				if (GetterUtil.getBoolean(results.get("invalidFields")) ||
					!GetterUtil.getBoolean(
						results.get("validationCriteriaMet"))) {

					throw new ObjectValidationRuleEngineException.InvalidFields(
						objectValidationRule.getErrorLabel(locale));
				}

				if (GetterUtil.getBoolean(results.get("invalidScript"))) {
					throw new ObjectValidationRuleEngineException.
						InvalidScript();
				}

				continue;
			}

			String errorMessage = null;

			if (GetterUtil.getBoolean(results.get("invalidFields"))) {
				errorMessage = objectValidationRule.getErrorLabel(locale);
			}
			else if (GetterUtil.getBoolean(results.get("invalidScript"))) {
				errorMessage = _language.get(
					locale, "there-was-an-error-validating-your-data");
			}

			if (Validator.isNull(errorMessage)) {
				continue;
			}

			if (objectValidationRule.compareOutputType(
					ObjectValidationRuleConstants.
						OUTPUT_TYPE_PARTIAL_VALIDATION)) {

				for (ObjectValidationRuleSetting objectValidationRuleSetting :
						_objectValidationRuleSettingPersistence.findByOVRI_N(
							objectValidationRule.getObjectValidationRuleId(),
							ObjectValidationRuleSettingConstants.
								NAME_OBJECT_FIELD_ID)) {

					ObjectField objectField =
						_objectFieldPersistence.fetchByPrimaryKey(
							GetterUtil.getLong(
								objectValidationRuleSetting.getValue()));

					if (objectField == null) {
						continue;
					}

					objectValidationRuleResults.add(
						new ObjectValidationRuleResult(
							errorMessage, objectField.getName()));
				}
			}
			else {
				objectValidationRuleResults.add(
					new ObjectValidationRuleResult(errorMessage));
			}
		}

		if (ListUtil.isNotEmpty(objectValidationRuleResults)) {
			throw new ObjectValidationRuleEngineException(
				objectValidationRuleResults);
		}
	}

	private List<ObjectValidationRuleSetting> _addObjectValidationRuleSettings(
		ObjectValidationRule objectValidationRule,
		List<ObjectValidationRuleSetting> objectValidationRuleSettings) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-187846")) {
			return Collections.emptyList();
		}

		return TransformUtil.transform(
			objectValidationRuleSettings,
			objectValidationRuleSetting ->
				_objectValidationRuleSettingLocalService.
					addObjectValidationRuleSetting(
						objectValidationRule.getUserId(),
						objectValidationRule.getObjectValidationRuleId(),
						objectValidationRuleSetting.getName(),
						objectValidationRuleSetting.getValue()));
	}

	private List<ObjectValidationRule> _getObjectValidationRules(
		List<ObjectValidationRule> objectValidationRules) {

		for (ObjectValidationRule objectValidationRule :
				objectValidationRules) {

			objectValidationRule.setObjectValidationRuleSettings(
				_objectValidationRuleSettingPersistence.
					findByObjectValidationRuleId(
						objectValidationRule.getObjectValidationRuleId()));
		}

		return objectValidationRules;
	}

	private void _validate(
			String engine, Map<Locale, String> nameMap, String outputType,
			String script,
			List<ObjectValidationRuleSetting> objectValidationRuleSettings)
		throws PortalException {

		if (Validator.isNull(engine)) {
			throw new ObjectValidationRuleEngineException.MustNotBeNull();
		}

		ObjectValidationRuleEngine objectValidationRuleEngine =
			_objectValidationRuleEngineRegistry.getObjectValidationRuleEngine(
				engine);

		if (objectValidationRuleEngine == null) {
			throw new ObjectValidationRuleEngineException.NoSuchEngine(engine);
		}

		Locale locale = LocaleUtil.getSiteDefault();

		if ((nameMap == null) || Validator.isNull(nameMap.get(locale))) {
			throw new ObjectValidationRuleNameException(
				"Name is null for locale " + locale.getDisplayName());
		}

		if (FeatureFlagManagerUtil.isEnabled("LPS-187846") &&
			!StringUtil.equals(
				outputType,
				ObjectValidationRuleConstants.OUTPUT_TYPE_FULL_VALIDATION) &&
			!StringUtil.equals(
				outputType,
				ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION)) {

			throw new ObjectValidationRuleOutputTypeException(
				"Invalid output type " + outputType);
		}

		if (Validator.isNull(script)) {
			throw new ObjectValidationRuleScriptException(
				"The script is required", "required");
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
					objectScriptingException.getMessage(),
					objectScriptingException.getMessageKey());
			}

			throw new ObjectValidationRuleScriptException(
				"The script syntax is invalid", "syntax-error");
		}

		if (!FeatureFlagManagerUtil.isEnabled("LPS-187846")) {
			return;
		}

		if (StringUtil.equals(
				outputType,
				ObjectValidationRuleConstants.OUTPUT_TYPE_PARTIAL_VALIDATION) &&
			ListUtil.isEmpty(objectValidationRuleSettings)) {

			throw new ObjectValidationRuleSettingNameException.
				MissingRequiredName(
					ObjectValidationRuleSettingConstants.NAME_OBJECT_FIELD_ID);
		}

		for (ObjectValidationRuleSetting objectValidationRuleSetting :
				objectValidationRuleSettings) {

			if (StringUtil.equals(
					outputType,
					ObjectValidationRuleConstants.
						OUTPUT_TYPE_FULL_VALIDATION) ||
				!objectValidationRuleSetting.compareName(
					ObjectValidationRuleSettingConstants.
						NAME_OBJECT_FIELD_ID)) {

				throw new ObjectValidationRuleSettingNameException.
					NotAllowedName(objectValidationRuleSetting.getName());
			}

			ObjectField objectField = _objectFieldPersistence.fetchByPrimaryKey(
				GetterUtil.getLong(objectValidationRuleSetting.getValue()));

			if ((objectField == null) || objectField.isSystem()) {
				throw new ObjectValidationRuleSettingValueException.
					InvalidValue(
						objectValidationRuleSetting.getName(),
						objectValidationRuleSetting.getValue());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectValidationRuleLocalServiceImpl.class);

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private Language _language;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

	@Reference
	private ObjectScriptingValidator _objectScriptingValidator;

	@Reference
	private ObjectValidationRuleEngineRegistry
		_objectValidationRuleEngineRegistry;

	@Reference
	private ObjectValidationRuleSettingLocalService
		_objectValidationRuleSettingLocalService;

	@Reference
	private ObjectValidationRuleSettingPersistence
		_objectValidationRuleSettingPersistence;

	@Reference
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

	@Reference
	private UserLocalService _userLocalService;

}