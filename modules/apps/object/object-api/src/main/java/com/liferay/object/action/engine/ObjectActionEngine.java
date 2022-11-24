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

package com.liferay.object.action.engine;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public interface ObjectActionEngine {

	public void executeObjectAction(
			String objectActionName, String objectActionTriggerKey,
			long objectDefinitionId, JSONObject payloadJSONObject, long userId)
		throws Exception;

	public void executeObjectActions(
		String className, long companyId, String objectActionTriggerKey,
		JSONObject payloadJSONObject, long userId);

}