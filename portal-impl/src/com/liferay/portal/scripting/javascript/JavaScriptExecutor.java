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

package com.liferay.portal.scripting.javascript;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.scripting.BaseScriptingExecutor;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.util.ClassLoaderUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Wrapper;

/**
 * @author Alberto Montero
 */
public class JavaScriptExecutor extends BaseScriptingExecutor {

	@Override
	public void clearCache() {
		_portalCache.removeAll();
	}

	@Override
	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String script, ClassLoader... classLoaders)
		throws ScriptingException {

		Script compiledScript = getCompiledScript(script, classLoaders);

		try {
			Context context = Context.enter();

			Scriptable scriptable = context.initStandardObjects();

			if (ArrayUtil.isNotEmpty(classLoaders)) {
				ClassLoader aggregateClassLoader =
					AggregateClassLoader.getAggregateClassLoader(
						ClassLoaderUtil.getPortalClassLoader(), classLoaders);

				context.setApplicationClassLoader(aggregateClassLoader);
			}

			for (Map.Entry<String, Object> entry : inputObjects.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				ScriptableObject.putProperty(
					scriptable, key, Context.javaToJS(value, scriptable));
			}

			if (allowedClasses != null) {
				context.setClassShutter(
					new JavaScriptClassVisibilityChecker(allowedClasses));
			}

			compiledScript.exec(context, scriptable);

			if (outputNames == null) {
				return null;
			}

			Map<String, Object> outputObjects = new HashMap<String, Object>();

			for (String outputName : outputNames) {
				Object property = ScriptableObject.getProperty(
					scriptable, outputName);

				if (property instanceof Wrapper) {
					Wrapper wrapper = (Wrapper)property;

					property = wrapper.unwrap();
				}

				outputObjects.put(outputName, property);
			}

			return outputObjects;
		}
		catch (Exception e) {
			throw new ScriptingException(e.getMessage() + "\n\n", e);
		}
		finally {
			Context.exit();
		}
	}

	@Override
	public String getLanguage() {
		return _LANGUAGE;
	}

	protected Script getCompiledScript(
		String script, ClassLoader... classLoaders) {

		String key = String.valueOf(script.hashCode());

		Script compiledScript = _portalCache.get(key);

		if (compiledScript != null) {
			return compiledScript;
		}

		try {
			Context context = Context.enter();

			if (ArrayUtil.isNotEmpty(classLoaders)) {
				ClassLoader aggregateClassLoader =
					AggregateClassLoader.getAggregateClassLoader(
						ClassLoaderUtil.getPortalClassLoader(), classLoaders);

				context.setApplicationClassLoader(aggregateClassLoader);
			}

			compiledScript = context.compileString(script, "script", 0, null);
		}
		finally {
			Context.exit();
		}

		_portalCache.put(key, compiledScript);

		return compiledScript;
	}

	private static final String _CACHE_NAME =
		JavaScriptExecutor.class.getName();

	private static final String _LANGUAGE = "javascript";

	private final PortalCache<String, Script> _portalCache =
		SingleVMPoolUtil.getCache(_CACHE_NAME);

}