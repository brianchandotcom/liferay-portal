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
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

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
			Set<String> outputNames, File scriptFile,
			ClassLoader... classloaders)
		throws ScriptingException {

		try {
			String script = FileUtil.read(scriptFile);

			return eval(
				allowedClasses, inputObjects, outputNames, script,
				classloaders);
		}
		catch (IOException ioe) {
			throw new ScriptingException(ioe);
		}
	}

	@Override
	public ScriptingContainer<?> getScriptingContainer() {
		return null;
	}

	protected ClassLoader getAggregateClassLoader(ClassLoader... classLoaders) {
		return AggregateClassLoader.getAggregateClassLoader(
			getScriptingExecutorClassLoader(), classLoaders);
	}

	protected ClassLoader getScriptingExecutorClassLoader() {
		return _scriptingExecutorClassLoader;
	}

	protected void initScriptingExecutorClassLoader() {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		if (!classLoader.equals(PortalClassLoaderUtil.getClassLoader())) {
			_scriptingExecutorClassLoader =
				AggregateClassLoader.getAggregateClassLoader(
					PortalClassLoaderUtil.getClassLoader(), classLoader);
		}
		else {
			_scriptingExecutorClassLoader = classLoader;
		}
	}

	private ClassLoader _scriptingExecutorClassLoader;

}