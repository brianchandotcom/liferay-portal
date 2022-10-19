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

import com.liferay.object.model.ObjectState;
import com.liferay.object.service.ObjectStateTransitionLocalService;
import com.liferay.object.service.base.ObjectStateLocalServiceBaseImpl;
import com.liferay.object.service.persistence.ObjectStateTransitionPersistence;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectState",
	service = AopService.class
)
public class ObjectStateLocalServiceImpl
	extends ObjectStateLocalServiceBaseImpl {

	@Override
	public ObjectState addObjectState(
			long userId, long listTypeEntryId, long objectStateFlowId)
		throws PortalException {

		ObjectState objectState = createObjectState(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectState.setCompanyId(user.getCompanyId());
		objectState.setUserId(user.getUserId());
		objectState.setUserName(user.getFullName());

		objectState.setListTypeEntryId(listTypeEntryId);
		objectState.setObjectStateFlowId(objectStateFlowId);

		return updateObjectState(objectState);
	}

	@Override
	public void deleteListTypeEntryObjectStates(long listTypeEntryId) {
		List<ObjectState> objectStates =
			objectStatePersistence.findByListTypeEntryId(listTypeEntryId);

		for (ObjectState objectState : objectStates) {
			objectStatePersistence.remove(objectState);

			_objectStateTransitionLocalService.
				deleteObjectStateObjectStateTransitions(
					objectState.getObjectStateId());
		}
	}

	@Override
	public void deleteObjectStateFlowObjectStates(long objectStateFlowId) {
		objectStatePersistence.removeByObjectStateFlowId(objectStateFlowId);
	}

	@Override
	public List<ObjectState> getNextObjectStates(long sourceObjectStateId) {
		return TransformUtil.transform(
			_objectStateTransitionPersistence.findBySourceObjectStateId(
				sourceObjectStateId),
			objectStateTransition -> objectStatePersistence.fetchByPrimaryKey(
				objectStateTransition.getTargetObjectStateId()));
	}

	@Override
	public ObjectState getObjectStateFlowObjectState(
			long listTypeEntryId, long objectStateFlowId)
		throws PortalException {

		return objectStatePersistence.findByLTEI_OSFI(
			listTypeEntryId, objectStateFlowId);
	}

	@Override
	public List<ObjectState> getObjectStateFlowObjectStates(
		long objectStateFlowId) {

		return objectStatePersistence.findByObjectStateFlowId(
			objectStateFlowId);
	}

	@Reference
	private ObjectStateTransitionLocalService
		_objectStateTransitionLocalService;

	@Reference
	private ObjectStateTransitionPersistence _objectStateTransitionPersistence;

	@Reference
	private UserLocalService _userLocalService;

}