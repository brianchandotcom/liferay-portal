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
import com.liferay.object.service.base.ObjectActionLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectDefinitionPersistence;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectAction",
	service = AopService.class
)
public class ObjectActionLocalServiceImpl
	extends ObjectActionLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectAction addObjectAction(
			long userId, long objectDefinitionId, boolean active, String name,
			String objectActionExecutorKey, String objectActionTriggerKey,
			UnicodeProperties parametersUnicodeProperties)
		throws PortalException {

		ObjectDefinition objectDefinition =
			_objectDefinitionPersistence.findByPrimaryKey(objectDefinitionId);

		ObjectAction objectAction = objectActionPersistence.create(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectAction.setCompanyId(user.getCompanyId());
		objectAction.setUserId(user.getUserId());
		objectAction.setUserName(user.getFullName());

		objectAction.setObjectDefinitionId(
			objectDefinition.getObjectDefinitionId());
		objectAction.setActive(active);
		objectAction.setName(name);
		objectAction.setObjectActionExecutorKey(objectActionExecutorKey);
		objectAction.setObjectActionTriggerKey(objectActionTriggerKey);
		objectAction.setParameters(parametersUnicodeProperties.toString());

		return objectActionPersistence.update(objectAction);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public ObjectAction deleteObjectAction(long objectActionId)
		throws PortalException {

		ObjectAction objectAction = objectActionPersistence.findByPrimaryKey(
			objectActionId);

		return deleteObjectAction(objectAction);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public ObjectAction deleteObjectAction(ObjectAction objectAction) {
		return objectActionPersistence.remove(objectAction);
	}

	@Override
	public List<ObjectAction> getObjectActions(
		long objectDefinitionId, String objectActionTriggerKey) {

		return objectActionPersistence.findByO_A_OAEK(
			objectDefinitionId, true, objectActionTriggerKey);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public ObjectAction updateObjectAction(
			long objectActionId, boolean active, String name,
			UnicodeProperties parametersUnicodeProperties)
		throws PortalException {

		ObjectAction objectAction = objectActionPersistence.findByPrimaryKey(
			objectActionId);

		objectAction.setActive(active);
		objectAction.setName(name);
		objectAction.setParameters(parametersUnicodeProperties.toString());

		return objectActionPersistence.update(objectAction);
	}

	@Reference
	private ObjectDefinitionPersistence _objectDefinitionPersistence;

	@Reference
	private UserLocalService _userLocalService;

}