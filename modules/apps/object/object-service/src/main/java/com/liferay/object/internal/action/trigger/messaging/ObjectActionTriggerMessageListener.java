/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.action.trigger.messaging;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectActionTriggerMessageListener extends BaseMessageListener {

	public ObjectActionTriggerMessageListener(
		String className, ObjectActionEngine objectActionEngine,
		String objectActionTriggerKey) {

		_className = className;
		_objectActionEngine = objectActionEngine;
		_objectActionTriggerKey = objectActionTriggerKey;
	}

	@Override
	protected void doReceive(Message message) {
		_objectActionEngine.executeObjectActions(
			_className, GetterUtil.getLong(message.get("companyId")),
			_objectActionTriggerKey, () -> (JSONObject)message.getPayload(),
			_getUserId(message));
	}

	private long _getUserId(Message message) {
		long userId = GetterUtil.getLong(message.get("principalName"));

		if (userId != 0L) {
			return userId;
		}

		Object object = message.get("permissionChecker");

		if (!(object instanceof PermissionChecker)) {
			return 0L;
		}

		PermissionChecker permissionChecker = (PermissionChecker)object;

		return permissionChecker.getUserId();
	}

	private final String _className;
	private final ObjectActionEngine _objectActionEngine;
	private final String _objectActionTriggerKey;

}