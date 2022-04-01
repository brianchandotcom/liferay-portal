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

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.service.base.ObjectValidationRuleServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"json.web.service.context.name=object",
		"json.web.service.context.path=ObjectValidationRule"
	},
	service = AopService.class
)
public class ObjectValidationRuleServiceImpl
	extends ObjectValidationRuleServiceBaseImpl {

	@Override
	public ObjectValidationRule addObjectValidationRule(
			long objectDefinitionId, boolean active, String engine,
			Map<Locale, String> errorLabelMap, Map<Locale, String> nameMap,
			String script)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.UPDATE);

		return objectValidationRuleLocalService.addObjectValidationRule(
			getUserId(), objectDefinitionId, active, engine, errorLabelMap,
			nameMap, script);
	}

	@Override
	public ObjectValidationRule deleteObjectValidationRule(
			long objectValidationRuleId)
		throws PortalException {

		ObjectValidationRule objectValidationRule =
			objectValidationRulePersistence.findByPrimaryKey(
				objectValidationRuleId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			objectValidationRule.getObjectDefinitionId(), ActionKeys.UPDATE);

		return objectValidationRuleLocalService.deleteObjectValidationRule(
			objectValidationRule);
	}

	@Override
	public ObjectValidationRule getObjectValidationRule(
			long objectValidationRuleId)
		throws PortalException {

		ObjectValidationRule objectValidationRule =
			objectValidationRulePersistence.findByPrimaryKey(
				objectValidationRuleId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			objectValidationRule.getObjectDefinitionId(), ActionKeys.VIEW);

		return objectValidationRuleLocalService.getObjectValidationRule(
			objectValidationRuleId);
	}

	@Override
	public ObjectValidationRule updateObjectValidationRule(
			long objectValidationRuleId, boolean active, String engine,
			Map<Locale, String> errorLabelMap, Map<Locale, String> nameMap,
			String script)
		throws PortalException {

		ObjectValidationRule objectValidationRule =
			objectValidationRulePersistence.findByPrimaryKey(
				objectValidationRuleId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			objectValidationRule.getObjectDefinitionId(), ActionKeys.UPDATE);

		return objectValidationRuleLocalService.updateObjectValidationRule(
			objectValidationRuleId, active, engine, errorLabelMap, nameMap,
			script);
	}

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectDefinition)"
	)
	private ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;

}