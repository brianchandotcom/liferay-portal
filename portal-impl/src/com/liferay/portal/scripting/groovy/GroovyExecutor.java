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

package com.liferay.portal.scripting.groovy;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.scripting.BaseScriptingExecutor;
import com.liferay.portal.kernel.scripting.ExecutionException;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.util.ClassLoaderUtil;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 */
public class GroovyExecutor extends BaseScriptingExecutor {

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
				"Constrained execution not supported for Groovy");
		}

		Script compiledScript = getCompiledScript(script, classLoaders);

		Binding binding = new Binding(inputObjects);

		compiledScript.setBinding(binding);

		compiledScript.run();

		if (outputNames == null) {
			return null;
		}

		Map<String, Object> outputObjects = new HashMap<String, Object>();

		for (String outputName : outputNames) {
			outputObjects.put(outputName, binding.getVariable(outputName));
		}

		return outputObjects;
	}

	@Override
	public String getLanguage() {
		return _LANGUAGE;
	}

	protected Script getCompiledScript(
		String script, ClassLoader[] classLoaders) {

		GroovyShell groovyShell = getGroovyShell(classLoaders);

		String key = String.valueOf(script.hashCode());

		Script compiledScript = _portalCache.get(key);

		if (compiledScript == null) {
			compiledScript = groovyShell.parse(script);

			_portalCache.put(key, compiledScript);
		}

		return compiledScript;
	}

	protected GroovyShell getGroovyShell(ClassLoader[] classLoaders) {
		if (ArrayUtil.isEmpty(classLoaders)) {
			if (_groovyShell == null) {
				synchronized (this) {
					if (_groovyShell == null) {
						_groovyShell = new GroovyShell();
					}
				}
			}

			return _groovyShell;
		}

		ClassLoader aggregateClassLoader =
			AggregateClassLoader.getAggregateClassLoader(
				ClassLoaderUtil.getPortalClassLoader(), classLoaders);

		GroovyShell groovyShell = _groovyShells.get(aggregateClassLoader);

		if (groovyShell == null) {
			groovyShell = new GroovyShell(aggregateClassLoader);

			GroovyShell oldGroovyShell = _groovyShells.putIfAbsent(
				aggregateClassLoader, groovyShell);

			if (oldGroovyShell != null) {
				groovyShell = oldGroovyShell;
			}
		}

		return groovyShell;
	}

	private static final String _CACHE_NAME = GroovyExecutor.class.getName();

	private static final String _LANGUAGE = "groovy";

	private volatile GroovyShell _groovyShell = new GroovyShell();
	private final ConcurrentMap<ClassLoader, GroovyShell> _groovyShells =
		new ConcurrentReferenceKeyHashMap<ClassLoader, GroovyShell>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);
	private final PortalCache<String, Script> _portalCache =
		SingleVMPoolUtil.getCache(_CACHE_NAME);

}