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

package com.liferay.object.internal.action.trigger.messaging;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
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
		long companyId = GetterUtil.getLong(message.get("companyId"));

		_objectActionEngine.executeObjectActions(
			StringBundler.concat(_className, StringPool.POUND, companyId),
			companyId, _objectActionTriggerKey,
			(JSONObject)message.getPayload(),
			GetterUtil.getLong(message.get("principalName")));
	}

	private final String _className;
	private final ObjectActionEngine _objectActionEngine;
	private final String _objectActionTriggerKey;

}