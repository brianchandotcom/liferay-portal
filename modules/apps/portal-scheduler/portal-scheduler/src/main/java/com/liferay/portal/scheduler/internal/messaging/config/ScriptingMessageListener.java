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