/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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