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

import com.liferay.object.exception.NoSuchObjectStateException;
import com.liferay.object.model.ObjectState;
import com.liferay.object.model.ObjectStateTransition;
import com.liferay.object.model.ObjectStateTransitionModel;
import com.liferay.object.service.ObjectStateTransitionLocalService;
import com.liferay.object.service.base.ObjectStateLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
			_objectStateTransitionLocalService.
				deleteObjectStateObjectStateTransitions(
					objectState.getObjectStateId());
		}

		objectStatePersistence.removeByListTypeEntryId(listTypeEntryId);
	}

	@Override
	public void deleteObjectStateFlowObjectStates(long objectStateFlowId) {
		objectStatePersistence.removeByObjectStateFlowId(objectStateFlowId);
	}

	@Override
	public List<ObjectState> getNextObjectStates(long sourceObjectStateId) {
		return Stream.of(
			_objectStateTransitionLocalService.
				getObjectStateObjectStateTransitions(sourceObjectStateId)
		).flatMap(
			List::stream
		).map(
			objectStateTransition -> objectStatePersistence.fetchByPrimaryKey(
				objectStateTransition.getTargetObjectStateId())
		).collect(
			Collectors.toList()
		);
	}

	@Override
	public List<ObjectState> getObjectStateFlowObjectStates(
		long objectStateFlowId) {

		return objectStatePersistence.findByObjectStateFlowId(
			objectStateFlowId);
	}

	@Override
	public ObjectState getObjectStatesByListTypeEntryIdAndObjectStateFlowId(
			long listTypeEntryId, long objectStateFlowId)
		throws NoSuchObjectStateException {

		return objectStatePersistence.findByLTEI_OSFI(
			listTypeEntryId, objectStateFlowId);
	}

	@Override
	public void updateObjectStateTransitions(
			long objectStateId,
			List<ObjectStateTransition> objectStateTransitions)
		throws PortalException {

		List<ObjectStateTransition> persistedObjectStateTransitions =
			_objectStateTransitionLocalService.
				getObjectStateObjectStateTransitions(objectStateId);

		if (persistedObjectStateTransitions.isEmpty()) {
			_objectStateTransitionLocalService.addObjectStateTransitions(
				objectStateTransitions);

			return;
		}

		if (objectStateTransitions.isEmpty()) {
			_objectStateTransitionLocalService.deleteObjectStateTransitions(
				persistedObjectStateTransitions);

			return;
		}

		List<Long> targetObjectStateIds = ListUtil.toList(
			objectStateTransitions,
			ObjectStateTransitionModel::getTargetObjectStateId);

		_objectStateTransitionLocalService.deleteObjectStateTransitions(
			ListUtil.filter(
				persistedObjectStateTransitions,
				objectStateTransition -> !targetObjectStateIds.contains(
					objectStateTransition.getTargetObjectStateId())));

		List<Long> persistedTargetObjectStateIds = ListUtil.toList(
			persistedObjectStateTransitions,
			ObjectStateTransitionModel::getTargetObjectStateId);

		_objectStateTransitionLocalService.addObjectStateTransitions(
			ListUtil.filter(
				objectStateTransitions,
				objectStateTransition ->
					!persistedTargetObjectStateIds.contains(
						objectStateTransition.getTargetObjectStateId())));
	}

	@Reference
	private ObjectStateTransitionLocalService
		_objectStateTransitionLocalService;

	@Reference
	private UserLocalService _userLocalService;

}