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
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.base.ObjectFieldSettingServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(
	property = {
		"json.web.service.context.name=object",
		"json.web.service.context.path=ObjectFieldSetting"
	},
	service = AopService.class
)
public class ObjectFieldSettingServiceImpl
	extends ObjectFieldSettingServiceBaseImpl {

	@Override
	public ObjectFieldSetting addObjectFieldSetting(
			long objectFieldId, String name, boolean required, String value)
		throws PortalException {

		ObjectField objectField = _objectFieldPersistence.fetchByPrimaryKey(
			objectFieldId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectField.getObjectDefinitionId(),
			ActionKeys.UPDATE);

		return objectFieldSettingLocalService.addObjectFieldSetting(
			getUserId(), objectFieldId, name, required, value);
	}

	@Override
	public ObjectFieldSetting deleteObjectFieldSetting(
			long objectFieldSettingId)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			_getObjectDefinitionId(objectFieldSettingId), ActionKeys.UPDATE);

		return objectFieldSettingLocalService.deleteObjectFieldSetting(
			objectFieldSettingId);
	}

	@Override
	public ObjectFieldSetting getObjectFieldSetting(long objectFieldSettingId)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			_getObjectDefinitionId(objectFieldSettingId), ActionKeys.VIEW);

		return objectFieldSettingLocalService.getObjectFieldSetting(
			objectFieldSettingId);
	}

	@Override
	public ObjectFieldSetting updateObjectFieldSetting(
			long objectFieldSettingId, String value)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			_getObjectDefinitionId(objectFieldSettingId), ActionKeys.UPDATE);

		return objectFieldSettingLocalService.updateObjectFieldSetting(
			objectFieldSettingId, value);
	}

	private long _getObjectDefinitionId(long objectFieldSettingId)
		throws PortalException {

		ObjectFieldSetting objectFieldSetting =
			objectFieldSettingPersistence.findByPrimaryKey(
				objectFieldSettingId);

		ObjectField objectField = _objectFieldPersistence.fetchByPrimaryKey(
			objectFieldSetting.getObjectFieldId());

		return objectField.getObjectDefinitionId();
	}

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectDefinition)"
	)
	private ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

}