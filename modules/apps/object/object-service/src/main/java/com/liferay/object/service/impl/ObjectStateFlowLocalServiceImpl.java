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

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.exception.NoSuchObjectStateFlowException;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.object.service.ObjectStateTransitionLocalService;
import com.liferay.object.service.base.ObjectStateFlowLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectStateFlow",
	service = AopService.class
)
public class ObjectStateFlowLocalServiceImpl
	extends ObjectStateFlowLocalServiceBaseImpl {

	@Override
	public ObjectStateFlow addDefaultObjectStateFlow(ObjectField objectField)
		throws PortalException {

		if (!objectField.isState()) {
			return null;
		}

		ObjectStateFlow objectStateFlow = _addObjectStateFlow(
			objectField.getUserId(), objectField.getObjectFieldId());

		List<ListTypeEntry> listTypeEntries =
			_listTypeEntryLocalService.getListTypeEntries(
				objectField.getListTypeDefinitionId());

		List<ObjectState> objectStates = new ArrayList<>();

		for (ListTypeEntry listTypeEntry : listTypeEntries) {
			objectStates.add(
				_objectStateLocalService.addObjectState(
					objectField.getUserId(), listTypeEntry.getListTypeEntryId(),
					objectStateFlow.getObjectStateFlowId()));
		}

		for (ObjectState sourceObjectState : objectStates) {
			for (ObjectState targetObjectState : objectStates) {
				if (sourceObjectState.equals(targetObjectState)) {
					continue;
				}

				_objectStateTransitionLocalService.addObjectStateTransition(
					objectField.getUserId(),
					objectStateFlow.getObjectStateFlowId(),
					sourceObjectState.getObjectStateId(),
					targetObjectState.getObjectStateId());
			}
		}

		return objectStateFlow;
	}

	@Override
	public void deleteObjectFieldObjectStateFlow(long objectFieldId)
		throws NoSuchObjectStateFlowException {

		ObjectStateFlow objectStateFlow = getObjectFieldObjectStateFlow(
			objectFieldId);

		objectStateFlowPersistence.remove(
			objectStateFlow.getObjectStateFlowId());

		_objectStateLocalService.deleteObjectStateFlowObjectStates(
			objectStateFlow.getObjectStateFlowId());

		_objectStateTransitionLocalService.
			deleteObjectStateFlowObjectStateTransitions(
				objectStateFlow.getObjectStateFlowId());
	}

	@Override
	public ObjectStateFlow getObjectFieldObjectStateFlow(long objectFieldId) {
		return objectStateFlowPersistence.fetchByObjectFieldId(objectFieldId);
	}

	@Override
	public void updateObjectStateTransitions(ObjectStateFlow objectStateFlow)
		throws PortalException {

		if (objectStateFlow == null) {
			return;
		}

		for (ObjectState objectState : objectStateFlow.getObjectStates()) {
			_objectStateLocalService.updateObjectStateTransitions(
				objectState.getObjectStateId(),
				objectState.getObjectStateTransitions());
		}
	}

	private ObjectStateFlow _addObjectStateFlow(long userId, long objectFieldId)
		throws PortalException {

		ObjectStateFlow objectStateFlow = createObjectStateFlow(
			counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectStateFlow.setCompanyId(user.getCompanyId());
		objectStateFlow.setUserId(user.getUserId());
		objectStateFlow.setUserName(user.getFullName());

		objectStateFlow.setObjectFieldId(objectFieldId);

		return addObjectStateFlow(objectStateFlow);
	}

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectStateLocalService _objectStateLocalService;

	@Reference
	private ObjectStateTransitionLocalService
		_objectStateTransitionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}