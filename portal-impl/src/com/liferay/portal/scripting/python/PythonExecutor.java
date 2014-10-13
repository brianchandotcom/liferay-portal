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

package com.liferay.portal.scripting.python;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.scripting.BaseScriptingExecutor;
import com.liferay.portal.kernel.scripting.ExecutionException;
import com.liferay.portal.kernel.scripting.ScriptingException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.python.core.CompileMode;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PySystemState;
import org.python.util.InteractiveInterpreter;

/**
 * @author Alberto Montero
 */
public class PythonExecutor extends BaseScriptingExecutor {

	@Override
	public void clearCache() {
		_portalCache.removeAll();
	}

	@Override
	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String script, ClassLoader... classLoaders)
		throws ScriptingException {

		if (allowedClasses != null) {
			throw new ExecutionException(
				"Constrained execution not supported for Python");
		}

		PyCode compiledScript = getCompiledScript(script);

		InteractiveInterpreter interactiveInterpreter =
			new InteractiveInterpreter();

		for (Map.Entry<String, Object> entry : inputObjects.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			interactiveInterpreter.set(key, value);
		}

		interactiveInterpreter.exec(compiledScript);

		if (outputNames == null) {
			return null;
		}

		Map<String, Object> outputObjects = new HashMap<String, Object>();

		for (String outputName : outputNames) {
			outputObjects.put(
				outputName, interactiveInterpreter.get(outputName));
		}

		return outputObjects;
	}

	@Override
	public String getLanguage() {
		return _LANGUAGE;
	}

	protected PyCode getCompiledScript(String script) {
		if (!_initialized) {
			synchronized (this) {
				if (!_initialized) {
					PySystemState.initialize();

					_initialized = true;
				}
			}
		}

		String key = String.valueOf(script.hashCode());

		PyCode compiledScript = _portalCache.get(key);

		if (compiledScript == null) {
			compiledScript = Py.compile_flags(
				script, "<string>", CompileMode.exec, Py.getCompilerFlags());

			_portalCache.put(key, compiledScript);
		}

		return compiledScript;
	}

	private static final String _CACHE_NAME = PythonExecutor.class.getName();

	private static final String _LANGUAGE = "python";

	private volatile boolean _initialized;
	private final PortalCache<String, PyCode> _portalCache =
		SingleVMPoolUtil.getCache(_CACHE_NAME);

}