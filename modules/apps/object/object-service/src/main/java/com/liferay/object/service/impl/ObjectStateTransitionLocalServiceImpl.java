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

import com.liferay.object.exception.NoSuchObjectStateTransitionException;
import com.liferay.object.model.ObjectStateTransition;
import com.liferay.object.service.base.ObjectStateTransitionLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectStateTransition",
	service = AopService.class
)
public class ObjectStateTransitionLocalServiceImpl
	extends ObjectStateTransitionLocalServiceBaseImpl {

	@Override
	public ObjectStateTransition addObjectStateTransition(
			long userId, long objectStateFlowId, long sourceObjectStateId,
			long targetObjectStateId)
		throws PortalException {

		ObjectStateTransition objectStateTransition =
			createObjectStateTransition(counterLocalService.increment());

		User user = _userLocalService.getUser(userId);

		objectStateTransition.setCompanyId(user.getCompanyId());
		objectStateTransition.setUserId(user.getUserId());
		objectStateTransition.setUserName(user.getFullName());

		objectStateTransition.setObjectStateFlowId(objectStateFlowId);
		objectStateTransition.setSourceObjectStateId(sourceObjectStateId);
		objectStateTransition.setTargetObjectStateId(targetObjectStateId);

		return addObjectStateTransition(objectStateTransition);
	}

	@Override
	public void addObjectStateTransitions(
			List<ObjectStateTransition> objectStateTransitions)
		throws PortalException {

		if (ListUtil.isEmpty(objectStateTransitions)) {
			return;
		}

		User user = _userLocalService.fetchUser(
			PrincipalThreadLocal.getUserId());

		for (ObjectStateTransition objectStateTransition :
				objectStateTransitions) {

			addObjectStateTransition(
				user.getUserId(), objectStateTransition.getObjectStateFlowId(),
				objectStateTransition.getSourceObjectStateId(),
				objectStateTransition.getTargetObjectStateId());
		}
	}

	@Override
	public void deleteObjectStateFlowObjectStateTransitions(
		long objectStateFlowId) {

		objectStateTransitionPersistence.removeByObjectStateFlowId(
			objectStateFlowId);
	}

	@Override
	public void deleteObjectStateObjectStateTransitions(long objectStateId) {
		objectStateTransitionPersistence.removeBySourceObjectStateId(
			objectStateId);

		objectStateTransitionPersistence.removeByTargetObjectStateId(
			objectStateId);
	}

	@Override
	public void deleteObjectStateTransitions(
			List<ObjectStateTransition> objectStateTransitions)
		throws NoSuchObjectStateTransitionException {

		if (ListUtil.isEmpty(objectStateTransitions)) {
			return;
		}

		for (ObjectStateTransition objectStateTransition :
				objectStateTransitions) {

			objectStateTransitionPersistence.remove(
				objectStateTransition.getObjectStateTransitionId());
		}
	}

	@Override
	public List<ObjectStateTransition> getObjectStateFlowObjectStateTransitions(
		long objectStateFlowId) {

		return objectStateTransitionPersistence.findByObjectStateFlowId(
			objectStateFlowId);
	}

	@Override
	public List<ObjectStateTransition> getObjectStateObjectStateTransitions(
		long sourceObjectStateId) {

		return objectStateTransitionPersistence.findBySourceObjectStateId(
			sourceObjectStateId);
	}

	@Reference
	private UserLocalService _userLocalService;

}