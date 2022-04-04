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

import com.liferay.object.exception.ObjectValidationRuleException;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.base.ObjectValidationRuleLocalServiceBaseImpl;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.object.validation.rule.ObjectValidationRuleEngineServicesTracker;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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
		_validateScript(script);

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
	public void deleteObjectValidationRuleByObjectDefinitionId(
			Long objectDefinitionId)
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
		_validateScript(script);

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
	public void validate(
			long userId, long objectDefinitionId,
			BaseModel<?> originalBaseModel, BaseModel<?> baseModel)
		throws PortalException {

		List<ObjectValidationRule> objectValidationRules =
			objectValidationRuleLocalService.getObjectValidationRules(
				objectDefinitionId, true);

		ObjectEntry objectEntry = (ObjectEntry)baseModel;

		Map<String, Serializable> values = null;

		if (objectEntry != null) {
			values = _objectEntryLocalService.getValues(objectEntry);
		}

		for (ObjectValidationRule objectValidationRule :
				objectValidationRules) {

			ObjectValidationRuleEngine objectValidationRuleEngine =
				_objectValidationRuleEngineServicesTracker.
					getObjectValidationRuleEngine(
						objectValidationRule.getEngine());

			HashMapBuilder.HashMapWrapper<String, Object> hashMapWrapper =
				HashMapBuilder.<String, Object>putAll(
					baseModel.getModelAttributes());

			if ((objectEntry != null) && (values != null)) {
				hashMapWrapper.putAll(values);
			}

			if (originalBaseModel != null) {
				Map<String, Object> modelAttributes =
					originalBaseModel.getModelAttributes();

				for (Map.Entry<String, Object> entry :
						modelAttributes.entrySet()) {

					hashMapWrapper.put(
						"original." + entry.getKey(), entry.getValue());
				}
			}

			if (userId > 0) {
				User user = _userLocalService.getUser(userId);

				hashMapWrapper.put(
					"user.emailAddress", user.getEmailAddress()
				).put(
					"user.firstName", user.getFirstName()
				).put(
					"user.lastName", user.getLastName()
				).put(
					"userId", userId
				);
			}

			if (!objectValidationRuleEngine.evaluate(
					hashMapWrapper.build(), objectValidationRule.getScript())) {

				throw new ObjectValidationRuleException(
					objectValidationRule.getErrorLabel(
						LocaleUtil.getMostRelevantLocale()));
			}
		}
	}

	private void _validateEngine(String engine) throws PortalException {
		if (Validator.isNull(engine)) {
			throw new ObjectValidationRuleException("Engine is null");
		}

		ObjectValidationRuleEngine objectValidationRuleEngine =
			_objectValidationRuleEngineServicesTracker.
				getObjectValidationRuleEngine(engine);

		if (objectValidationRuleEngine == null) {
			throw new ObjectValidationRuleException("Engine is invalid");
		}
	}

	private void _validateName(Map<Locale, String> nameMap)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		if ((nameMap == null) || Validator.isNull(nameMap.get(locale))) {
			throw new ObjectValidationRuleException(
				"Name is null for locale " + locale.getDisplayName());
		}
	}

	private void _validateScript(String script) throws PortalException {
		if (Validator.isNull(script)) {
			throw new ObjectValidationRuleException("Script is null");
		}
	}

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectValidationRuleEngineServicesTracker
		_objectValidationRuleEngineServicesTracker;

	@Reference
	private UserLocalService _userLocalService;

}