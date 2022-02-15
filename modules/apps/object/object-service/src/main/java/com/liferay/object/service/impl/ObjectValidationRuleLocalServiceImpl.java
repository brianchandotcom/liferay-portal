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

import com.liferay.object.exception.ObjectValidationException;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.service.base.ObjectValidationRuleLocalServiceBaseImpl;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.object.validation.rule.ObjectValidationRuleEngineServicesTracker;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.HashMap;
import java.util.List;
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

	@Override
	public List<ObjectValidationRule> getObjectValidationRules(
		long objectDefinitionId, boolean active, int start, int end) {

		return objectValidationRulePersistence.findByO_A(
			objectDefinitionId, active, start, end);
	}

	public void validate(
			long userId, long objectDefinitionId, BaseModel originalBaseModel,
			BaseModel baseModel)
		throws PortalException {

		List<ObjectValidationRule> objectValidationRules =
			objectValidationRuleLocalService.getObjectValidationRules(
				objectDefinitionId, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ObjectValidationRule objectValidationRule :
				objectValidationRules) {

			ObjectValidationRuleEngine objectValidationRuleEngine =
				_objectValidationRuleEngineServicesTracker.
					getObjectValidationRuleEngine(
						objectValidationRule.getEngine());

			HashMap<String, Object> inputObjects = new HashMap<>();

			inputObjects.putAll(baseModel.getModelAttributes());

			if (originalBaseModel != null) {
				Map<String, Object> modelAttributes =
					originalBaseModel.getModelAttributes();

				for (Map.Entry<String, Object> entry :
						modelAttributes.entrySet()) {

					inputObjects.put(
						"original." + entry.getKey(), entry.getValue());
				}
			}

			if (userId > 0) {
				User user = _userLocalService.getUser(userId);

				inputObjects.put("user.emailAddress", user.getEmailAddress());
				inputObjects.put("user.firstName", user.getFirstName());
				inputObjects.put("user.lastName", user.getLastName());

				inputObjects.put("userId", userId);
			}

			if (!objectValidationRuleEngine.evaluate(
					objectValidationRule.getScript(), inputObjects)) {

				throw new ObjectValidationException(
					objectValidationRule.getErrorLabel());
			}
		}
	}

	@Reference
	private ObjectValidationRuleEngineServicesTracker
		_objectValidationRuleEngineServicesTracker;

	@Reference
	private UserLocalService _userLocalService;

}