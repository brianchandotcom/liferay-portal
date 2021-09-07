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
import com.liferay.object.service.ObjectLayoutTabLocalService;
import com.liferay.object.service.base.ObjectLayoutLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

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
	property = "model.class.name=com.liferay.object.model.ObjectLayout",
	service = AopService.class
)
public class ObjectLayoutLocalServiceImpl
	extends ObjectLayoutLocalServiceBaseImpl {

	@Override
	public ObjectLayout addObjectLayout(
			long userId, long objectDefinitionId, boolean defaultObjectLayout,
			Map<Locale, String> nameMap)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		ObjectLayout objectLayout = objectLayoutPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectLayout.setCompanyId(user.getCompanyId());
		objectLayout.setUserId(user.getUserId());
		objectLayout.setUserName(user.getFullName());

		objectLayout.setObjectDefinitionId(
			objectDefinition.getObjectDefinitionId());
		objectLayout.setDefaultObjectLayout(defaultObjectLayout);
		objectLayout.setNameMap(nameMap);

		return objectLayoutPersistence.update(objectLayout);
	}

	@Override
	public List<ObjectLayout> getObjectLayouts(long objectDefinitionId) {
		return objectLayoutPersistence.findByObjectDefinitionId(
			objectDefinitionId);
	}

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private ObjectLayoutTabLocalService _objectLayoutTabLocalService;

	@Reference
	private UserLocalService _userLocalService;

}