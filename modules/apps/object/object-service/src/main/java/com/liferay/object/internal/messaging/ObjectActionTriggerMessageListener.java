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

package com.liferay.object.internal.messaging;

import com.liferay.object.action.ObjectActionEngine;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Marco Leo
 */
public class ObjectActionTriggerMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) {
		long companyId = GetterUtil.getLong(message.get("companyId"));

		if (companyId < 0) {
			return;
		}

		long userId = GetterUtil.getLong(message.get("userId"));

		Object payload = message.getPayload();

		if (payload instanceof JSONObject) {
			_objectActionEngine.executeObjectActions(
				userId, _className, _triggerName, payload.toString());
		}
	}

	private String _className;
	private ObjectActionEngine _objectActionEngine;
	private String _triggerName;

}