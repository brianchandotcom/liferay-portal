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
import com.liferay.object.model.ObjectLayout;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.service.base.ObjectLayoutServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
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
 * @author Brian Wing Shun Chan
 */
@Component(
	property = {
		"json.web.service.context.name=object",
		"json.web.service.context.path=ObjectLayout"
	},
	service = AopService.class
)
public class ObjectLayoutServiceImpl extends ObjectLayoutServiceBaseImpl {

	@Override
	public ObjectLayout addObjectLayout(
			long objectDefinitionId, boolean defaultObjectLayout,
			Map<Locale, String> nameMap, List<ObjectLayoutTab> objectLayoutTabs)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectDefinition.getObjectDefinitionId(),
			ActionKeys.UPDATE);

		return objectLayoutLocalService.addObjectLayout(
			getUserId(), objectDefinitionId, defaultObjectLayout, nameMap,
			objectLayoutTabs);
	}

	@Override
	public ObjectLayout deleteObjectLayout(long objectLayoutId)
		throws PortalException {

		ObjectLayout objectLayout = objectLayoutPersistence.findByPrimaryKey(
			objectLayoutId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectLayout.getObjectDefinitionId(),
			ActionKeys.DELETE);

		return objectLayoutLocalService.deleteObjectLayout(objectLayoutId);
	}

	@Override
	public ObjectLayout getObjectLayout(long objectLayoutId)
		throws PortalException {

		ObjectLayout objectLayout = objectLayoutPersistence.findByPrimaryKey(
			objectLayoutId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectLayout.getObjectDefinitionId(),
			ActionKeys.VIEW);

		return objectLayoutLocalService.getObjectLayout(objectLayoutId);
	}

	@Override
	public ObjectLayout updateObjectLayout(
			long objectLayoutId, boolean defaultObjectLayout,
			Map<Locale, String> nameMap, List<ObjectLayoutTab> objectLayoutTabs)
		throws PortalException {

		ObjectLayout objectLayout = objectLayoutPersistence.findByPrimaryKey(
			objectLayoutId);

		_objectDefinitionModelResourcePermission.check(
			getPermissionChecker(), objectLayout.getObjectDefinitionId(),
			ActionKeys.UPDATE);

		return objectLayoutLocalService.updateObjectLayout(
			objectLayoutId, defaultObjectLayout, nameMap, objectLayoutTabs);
	}

	@Reference(
		target = "(model.class.name=com.liferay.object.model.ObjectDefinition)"
	)
	private ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

}