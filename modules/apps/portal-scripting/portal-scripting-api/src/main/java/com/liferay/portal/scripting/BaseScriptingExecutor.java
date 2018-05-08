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

package com.liferay.portal.scripting;

import com.liferay.portal.kernel.scripting.ScriptingContainer;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.Map;
import java.util.Set;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 */
public abstract class BaseScriptingExecutor implements ScriptingExecutor {

	@Override
	public void clearCache() {
	}

	@Override
	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, File scriptFile)
		throws ScriptingException {

		try {
			String script = FileUtil.read(scriptFile);

			return eval(allowedClasses, inputObjects, outputNames, script);
		}
		catch (IOException ioe) {
			throw new ScriptingException(ioe);
		}
	}

	/**
	 * @deprecated As of 1.0.0, replaced by {@link #eval(Set, Map, Set, File)}
	 */
	@Deprecated
	@Override
	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, File scriptFile,
			ClassLoader... classloaders)
		throws ScriptingException {

		return eval(allowedClasses, inputObjects, outputNames, scriptFile);
	}

	/**
	 * @deprecated As of 1.0.0, replaced by {@link #eval(Set, Map, Set, String)}
	 */
	@Deprecated
	@Override
	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String script, ClassLoader... classloaders)
		throws ScriptingException {

		return eval(allowedClasses, inputObjects, outputNames, script);
	}

	@Override
	public ScriptingContainer<?> getScriptingContainer() {
		return null;
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		return AggregateClassLoader.getAggregateClassLoader(
			classLoader, contextClassLoader);
	}

}