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

import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateFlow;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.object.service.ObjectStateLocalService;
import com.liferay.object.service.ObjectStateTransitionLocalService;
import com.liferay.object.service.base.ObjectStateFlowLocalServiceBaseImpl;
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

		long objectStateFlowId = counterLocalService.increment();

		ObjectStateFlow objectStateFlow = objectStateFlowPersistence.create(
			objectStateFlowId);

		User user = _userLocalService.getUser(objectField.getUserId());

		objectStateFlow.setCompanyId(user.getCompanyId());
		objectStateFlow.setUserId(user.getUserId());
		objectStateFlow.setUserName(user.getFullName());

		objectStateFlow.setObjectFieldId(objectField.getObjectFieldId());

		objectStateFlow = objectStateFlowPersistence.update(objectStateFlow);

		List<ObjectState> objectStates = TransformUtil.transform(
			_listTypeEntryLocalService.getListTypeEntries(
				objectField.getListTypeDefinitionId()),
			listTypeEntry -> _objectStateLocalService.addObjectState(
				objectField.getUserId(), listTypeEntry.getListTypeEntryId(),
				objectStateFlowId));

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

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectField.getObjectFieldId(),
				ObjectFieldSettingConstants.NAME_STATE_FLOW);

		if (objectFieldSetting == null) {
			_objectFieldSettingLocalService.addObjectFieldSetting(
				objectField.getUserId(), objectField.getObjectFieldId(),
				ObjectFieldSettingConstants.NAME_STATE_FLOW,
				String.valueOf(objectStateFlowId));
		}
		else {
			_objectFieldSettingLocalService.updateObjectFieldSetting(
				objectFieldSetting.getObjectFieldSettingId(),
				String.valueOf(objectStateFlowId));
		}

		return objectStateFlow;
	}

	@Override
	public void deleteObjectFieldObjectStateFlow(long objectFieldId)
		throws PortalException {

		ObjectStateFlow objectStateFlow =
			objectStateFlowPersistence.fetchByObjectFieldId(objectFieldId);

		objectStateFlowPersistence.remove(
			objectStateFlow.getObjectStateFlowId());

		_objectStateLocalService.deleteObjectStateFlowObjectStates(
			objectStateFlow.getObjectStateFlowId());

		_objectStateTransitionLocalService.
			deleteObjectStateFlowObjectStateTransitions(
				objectStateFlow.getObjectStateFlowId());

		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectFieldId, ObjectFieldSettingConstants.NAME_STATE_FLOW);

		if (objectFieldSetting != null) {
			_objectFieldSettingLocalService.deleteObjectFieldSetting(
				objectFieldSetting.getObjectFieldSettingId());
		}
	}

	@Override
	public ObjectStateFlow fetchObjectFieldObjectStateFlow(long objectFieldId) {
		return objectStateFlowPersistence.fetchByObjectFieldId(objectFieldId);
	}

	@Override
	public ObjectStateFlow updateDefaultObjectStateFlow(
			ObjectField newObjectField, ObjectField oldObjectField)
		throws PortalException {

		if (!oldObjectField.isState() && !newObjectField.isState()) {
			return null;
		}

		if (oldObjectField.isState() && !newObjectField.isState()) {
			deleteObjectFieldObjectStateFlow(oldObjectField.getObjectFieldId());

			return null;
		}

		if (!oldObjectField.isState() && newObjectField.isState()) {
			return addDefaultObjectStateFlow(newObjectField);
		}

		if (oldObjectField.getListTypeDefinitionId() !=
				newObjectField.getListTypeDefinitionId()) {

			deleteObjectFieldObjectStateFlow(oldObjectField.getObjectFieldId());

			return addDefaultObjectStateFlow(newObjectField);
		}

		return null;
	}

	@Reference
	private ListTypeEntryLocalService _listTypeEntryLocalService;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private ObjectStateLocalService _objectStateLocalService;

	@Reference
	private ObjectStateTransitionLocalService
		_objectStateTransitionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}