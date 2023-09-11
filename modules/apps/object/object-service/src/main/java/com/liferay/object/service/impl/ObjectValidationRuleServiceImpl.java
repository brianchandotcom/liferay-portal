/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.service.impl;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectValidationRule;
import com.liferay.object.model.ObjectValidationRuleSetting;
import com.liferay.object.service.base.ObjectValidationRuleServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import java.util.List;
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
			String outputType, String script, boolean system,
			List<ObjectValidationRuleSetting> objectValidationRuleSettings)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.UPDATE);

		return objectValidationRuleLocalService.addObjectValidationRule(
			getUserId(), objectDefinitionId, active, engine, errorLabelMap,
			nameMap, outputType, script, system, objectValidationRuleSettings);
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
			String outputType, String script,
			List<ObjectValidationRuleSetting> objectValidationRuleSettings)
		throws PortalException {

		ObjectValidationRule objectValidationRule =
			objectValidationRulePersistence.findByPrimaryKey(
				objectValidationRuleId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			objectValidationRule.getObjectDefinitionId(), ActionKeys.UPDATE);

		return objectValidationRuleLocalService.updateObjectValidationRule(
			objectValidationRuleId, active, engine, errorLabelMap, nameMap,
			outputType, script, objectValidationRuleSettings);
	}

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectDefinition)"
	)
	private ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;

}