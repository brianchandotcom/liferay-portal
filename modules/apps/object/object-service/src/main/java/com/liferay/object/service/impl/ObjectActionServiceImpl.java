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

import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.base.ObjectActionServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.UnicodeProperties;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"json.web.service.context.name=object",
		"json.web.service.context.path=ObjectAction"
	},
	service = AopService.class
)
public class ObjectActionServiceImpl extends ObjectActionServiceBaseImpl {

	@Override
	public ObjectAction addObjectAction(
			long objectDefinitionId, boolean active, String conditionExpression,
			String description, String name, String objectActionExecutorKey,
			String objectActionTriggerKey,
			UnicodeProperties parametersUnicodeProperties)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinitionId, ActionKeys.UPDATE);

		return objectActionLocalService.addObjectAction(
			getUserId(), objectDefinitionId, active, conditionExpression,
			description, name, objectActionExecutorKey, objectActionTriggerKey,
			parametersUnicodeProperties);
	}

	@Override
	public ObjectAction deleteObjectAction(long objectActionId)
		throws PortalException {

		ObjectAction objectAction = objectActionPersistence.findByPrimaryKey(
			objectActionId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectAction.getObjectDefinitionId(),
			ActionKeys.UPDATE);

		return objectActionLocalService.deleteObjectAction(objectAction);
	}

	@Override
	public ObjectAction getObjectAction(long objectActionId)
		throws PortalException {

		ObjectAction objectAction = objectActionPersistence.findByPrimaryKey(
			objectActionId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectAction.getObjectDefinitionId(),
			ActionKeys.VIEW);

		return objectActionLocalService.getObjectAction(objectActionId);
	}

	@Override
	public ObjectAction updateObjectAction(
			long objectActionId, boolean active, String conditionExpression,
			String description, String name, String objectActionExecutorKey,
			String objectActionTriggerKey,
			UnicodeProperties parametersUnicodeProperties)
		throws PortalException {

		ObjectAction objectAction = objectActionPersistence.findByPrimaryKey(
			objectActionId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectAction.getObjectDefinitionId(),
			ActionKeys.UPDATE);

		return objectActionLocalService.updateObjectAction(
			objectActionId, active, conditionExpression, description, name,
			objectActionExecutorKey, objectActionTriggerKey,
			parametersUnicodeProperties);
	}

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectDefinition)"
	)
	private ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;

}