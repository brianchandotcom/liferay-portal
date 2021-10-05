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

package com.liferay.object.internal.model.listener;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.constants.ObjectActionTriggerConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ModelListener.class)
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public void onAfterCreate(User user) throws ModelListenerException {
		_executeObjectActions(
			ObjectActionTriggerConstants.KEY_ON_AFTER_CREATE, null, user);
	}

	@Override
	public void onAfterRemove(User user) throws ModelListenerException {
		try {
			_executeObjectActions(
				ObjectActionTriggerConstants.KEY_ON_AFTER_REMOVE, null, user);

			ObjectDefinition objectDefinition =
				_objectDefinitionLocalService.fetchObjectDefinitionByClassName(
					user.getCompanyId(), User.class.getName());

			if (objectDefinition == null) {
				return;
			}

			_objectEntryLocalService.deleteRelatedObjectEntries(
				0, objectDefinition.getObjectDefinitionId(),
				user.getPrimaryKey());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Override
	public void onAfterUpdate(User originalUser, User user)
		throws ModelListenerException {

		_executeObjectActions(
			ObjectActionTriggerConstants.KEY_ON_AFTER_UPDATE, originalUser,
			user);
	}

	private void _executeObjectActions(
			String objectActionTriggerKey, User originalUser, User user)
		throws ModelListenerException {

		try {
			long userId = PrincipalThreadLocal.getUserId();

			if (userId == 0) {
				userId = user.getUserId();
			}

			_objectActionEngine.executeObjectActions(
				userId, User.class.getName(), objectActionTriggerKey,
				HashMapBuilder.<String, Serializable>put(
					"payload",
					_getPayload(objectActionTriggerKey, originalUser, user)
				).build());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	private Serializable _getPayload(
			String objectActionTriggerKey, User originalUser, User user)
		throws JSONException {

		JSONObject payloadJSONObject = JSONUtil.put(
			"objectActionTriggerKey", objectActionTriggerKey);

		JSONObject userJSONObject = _jsonFactory.createJSONObject(
			user.toString());

		payloadJSONObject.put("user", userJSONObject);

		if (originalUser != null) {
			JSONObject originalUserJSONObject = _jsonFactory.createJSONObject(
				originalUser.toString());

			payloadJSONObject.put("originalUser", originalUserJSONObject);
		}

		return payloadJSONObject.toString();
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectActionEngine _objectActionEngine;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}