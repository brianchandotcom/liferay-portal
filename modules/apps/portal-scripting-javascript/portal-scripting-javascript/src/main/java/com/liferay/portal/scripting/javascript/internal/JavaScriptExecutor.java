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

package com.liferay.portal.scripting.javascript.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.scripting.BaseScriptingExecutor;
import com.liferay.portal.scripting.javascript.configuration.JavaScriptExecutorConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.Wrapper;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alberto Montero
 */
@Component(
	immediate = true,
	property = {"scripting.language=" + JavaScriptExecutor.LANGUAGE},
	service = ScriptingExecutor.class
)
public class JavaScriptExecutor extends BaseScriptingExecutor {

	public static final String LANGUAGE = "javascript";

	@Override
	public void clearCache() {
		_portalCache.removeAll();
	}

	@Override
	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String script)
		throws ScriptingException {

		Script compiledScript = getCompiledScript(script);

		try {
			Context context = Context.enter();

			Scriptable scriptable = context.initStandardObjects();

			Class<?> clazz = getClass();

			context.setApplicationClassLoader(clazz.getClassLoader());

			for (Map.Entry<String, Object> entry : inputObjects.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();

				ScriptableObject.putProperty(
					scriptable, key, Context.javaToJS(value, scriptable));
			}

			if (allowedClasses != null) {
				context.setClassShutter(
					new JavaScriptClassVisibilityChecker(
						allowedClasses, _forbiddenClassNames));
			}

			compiledScript.exec(context, scriptable);

			if (outputNames == null) {
				return null;
			}

			Map<String, Object> outputObjects = new HashMap<>();

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
		return LANGUAGE;
	}

	@Override
	public ScriptingExecutor newInstance(boolean executeInSeparateThread) {
		return new JavaScriptExecutor();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		JavaScriptExecutorConfiguration javaScriptExecutorConfiguration =
			ConfigurableUtil.createConfigurable(
				JavaScriptExecutorConfiguration.class, properties);

		_forbiddenClassNames = new HashSet<>(
			Arrays.asList(
				javaScriptExecutorConfiguration.forbiddenClassNames()));
	}

	protected Script getCompiledScript(String script)
		throws ScriptingException {

		String key = String.valueOf(script.hashCode());

		Script compiledScript = _portalCache.get(key);

		if (compiledScript != null) {
			return compiledScript;
		}

		try {
			Context context = Context.enter();

			Class<?> clazz = getClass();

			context.setApplicationClassLoader(clazz.getClassLoader());

			compiledScript = context.compileString(script, "script", 0, null);
		}
		catch (Exception e) {
			throw new ScriptingException(e.getMessage() + "\n\n", e);
		}
		finally {
			Context.exit();
		}

		_portalCache.put(key, compiledScript);

		return compiledScript;
	}

	@Reference(unbind = "-")
	protected void setSingleVMPool(SingleVMPool singleVMPool) {
		_portalCache = (PortalCache<String, Script>)singleVMPool.getPortalCache(
			_CACHE_NAME);
	}

	private static final String _CACHE_NAME =
		JavaScriptExecutor.class.getName();

	private volatile Set<String> _forbiddenClassNames;
	private PortalCache<String, Script> _portalCache;

}