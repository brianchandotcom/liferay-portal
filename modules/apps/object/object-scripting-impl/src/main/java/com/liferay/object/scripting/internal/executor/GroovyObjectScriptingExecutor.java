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

package com.liferay.object.scripting.internal.executor;

import com.liferay.object.scripting.executor.ObjectScriptingExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "scripting.language=groovy",
	service = ObjectScriptingExecutor.class
)
public class GroovyObjectScriptingExecutor implements ObjectScriptingExecutor {

	@Override
	public Map<String, Object> execute(
		Map<String, Object> inputObjects, Set<String> outputNames,
		String script) {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Class<?> clazz = getClass();

		Map<String, Object> results = new HashMap<>();

		currentThread.setContextClassLoader(clazz.getClassLoader());

		try {
			results = _scriptingExecutor.eval(
				null, inputObjects, outputNames, script);

			results.put("invalidScript", false);
		}
		catch (Exception exception) {
			_log.error(exception);

			results.put("invalidScript", true);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		return results;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroovyObjectScriptingExecutor.class);

	@Reference(target = "(scripting.language=groovy)")
	private ScriptingExecutor _scriptingExecutor;

}