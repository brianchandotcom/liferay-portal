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

package com.liferay.object.internal.action.executor;

import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ObjectActionExecutor.class)
public class GroovyObjectActionExecutorImpl implements ObjectActionExecutor {

	@Override
	public void execute(
			long companyId, UnicodeProperties parametersUnicodeProperties,
			JSONObject payloadJSONObject, long userId)
		throws Exception {

		Map<String, Object> inputObjects = new HashMap<>();

		for (String key : payloadJSONObject.keySet()) {
			inputObjects.put(key, payloadJSONObject.get(key));
		}

		_execute(inputObjects, parametersUnicodeProperties.get("script"));
	}

	@Override
	public String getKey() {
		return ObjectActionExecutorConstants.KEY_GROOVY;
	}

	private void _execute(Map<String, Object> inputObjects, String script)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		try {
			currentThread.setContextClassLoader(classLoader);

			_scripting.eval(
				null, inputObjects, new HashSet<>(), "groovy", script);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Reference
	private Scripting _scripting;

}