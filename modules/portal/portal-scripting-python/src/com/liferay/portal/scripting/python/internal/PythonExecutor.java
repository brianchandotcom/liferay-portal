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

package com.liferay.portal.scripting.python.internal;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.scripting.ExecutionException;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.scripting.BaseScriptingExecutor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.python.core.CompileMode;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFile;
import org.python.core.PyObject;
import org.python.core.PySyntaxError;
import org.python.core.PySystemState;
import org.python.util.InteractiveInterpreter;

/**
 * @author Alberto Montero
 */
@Component(
	immediate = true,
	property = {"scripting.language=" + PythonExecutor.LANGUAGE},
	service = ScriptingExecutor.class
)
public class PythonExecutor extends BaseScriptingExecutor {

	public static final String LANGUAGE = "python";

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

		try {
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

			Map<String, Object> outputObjects = new HashMap<>();

			for (String outputName : outputNames) {
				PyObject pyObject = interactiveInterpreter.get(outputName);

				outputObjects.put(
					outputName, pyObject.__tojava__(Object.class));
			}

			return outputObjects;
		}
		catch (PySyntaxError pySyntaxError) {
			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			Py.displayException(
				pySyntaxError.type, pySyntaxError.value,
				pySyntaxError.traceback,
				new PyFile(unsyncByteArrayOutputStream));

			String message = unsyncByteArrayOutputStream.toString();

			throw new ScriptingException(message, pySyntaxError);
		}
	}

	@Override
	public String getLanguage() {
		return LANGUAGE;
	}

	@Override
	public ScriptingExecutor newInstance(boolean executeInSeparateThread) {
		PythonExecutor pythonExecutor = new PythonExecutor();

		pythonExecutor._portalCache = _portalCache;

		return pythonExecutor;
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

	@Reference(unbind = "-")
	protected void setSingleVMPool(SingleVMPool singleVMPool) {
		_portalCache = (PortalCache<String, PyCode>)singleVMPool.getPortalCache(
			_CACHE_NAME);
	}

	private static final String _CACHE_NAME = PythonExecutor.class.getName();

	private volatile boolean _initialized;
	private PortalCache<String, PyCode> _portalCache;

}