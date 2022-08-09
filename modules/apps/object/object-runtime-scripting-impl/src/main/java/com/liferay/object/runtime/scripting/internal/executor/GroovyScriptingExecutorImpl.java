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

package com.liferay.object.runtime.scripting.internal.executor;

import com.liferay.object.runtime.scripting.executor.GroovyScriptingExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.Scripting;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(immediate = true, service = GroovyScriptingExecutor.class)
public class GroovyScriptingExecutorImpl implements GroovyScriptingExecutor {

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
			results = _scripting.eval(
				null, inputObjects, outputNames, "groovy", script);

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
		GroovyScriptingExecutorImpl.class);

	@Reference
	private Scripting _scripting;

}