/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scheduler.internal.messaging.config;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scripting.ScriptingUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tina Tian
 */
public class ScriptingMessageListener extends BaseMessageListener {

	@Override
	public void doReceive(Message message) throws Exception {
		Map<String, Object> inputObjects = new HashMap<>();

		String language = (String)message.get(SchedulerEngine.LANGUAGE);
		String script = (String)message.get(SchedulerEngine.SCRIPT);

		ScriptingUtil.eval(null, inputObjects, null, language, script);
	}

}