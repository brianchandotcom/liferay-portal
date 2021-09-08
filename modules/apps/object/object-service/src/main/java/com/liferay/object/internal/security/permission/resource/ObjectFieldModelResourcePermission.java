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

package com.liferay.object.internal.security.permission.resource;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.object.model.ObjectField",
	service = ModelResourcePermission.class
)
public class ObjectFieldModelResourcePermission
	implements ModelResourcePermission<ObjectField> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long objectFieldId,
			String actionId)
		throws PortalException {

		check(
			permissionChecker,
			_objectFieldLocalService.getObjectField(objectFieldId), actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, ObjectField objectField,
			String actionId)
		throws PortalException {

		_objectDefinitionModelResourcePermission.check(
			permissionChecker,
			_objectDefinitionLocalService.getObjectDefinition(
				objectField.getObjectDefinitionId()),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long objectFieldId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_objectFieldLocalService.getObjectField(objectFieldId), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, ObjectField objectField,
			String actionId)
		throws PortalException {

		return _objectDefinitionModelResourcePermission.contains(
			permissionChecker,
			_objectDefinitionLocalService.getObjectDefinition(
				objectField.getObjectDefinitionId()),
			actionId);
	}

	@Override
	public String getModelName() {
		return ObjectField.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectDefinition)"
	)
	private ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}