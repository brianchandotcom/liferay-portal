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

package com.liferay.portal.scripting.groovy.internal;

import com.liferay.portal.kernel.concurrent.ConcurrentReferenceKeyHashMap;
import com.liferay.portal.kernel.memory.FinalizeManager;
import com.liferay.portal.kernel.scripting.ExecutionException;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.scripting.BaseScriptingExecutor;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {"scripting.language=" + GroovyExecutor.LANGUAGE},
	service = ScriptingExecutor.class
)
public class GroovyExecutor extends BaseScriptingExecutor {

	public static final String LANGUAGE = "groovy";

	@Override
	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String script, ClassLoader... classLoaders)
		throws ScriptingException {

		if (allowedClasses != null) {
			throw new ExecutionException(
				"Constrained execution not supported for Groovy");
		}

		GroovyShell groovyShell = getGroovyShell(classLoaders);

		Script compiledScript = groovyShell.parse(script);

		Binding binding = new Binding(inputObjects);

		compiledScript.setBinding(binding);

		compiledScript.run();

		if (outputNames == null) {
			return null;
		}

		Map<String, Object> outputObjects = new HashMap<>();

		for (String outputName : outputNames) {
			outputObjects.put(outputName, binding.getVariable(outputName));
		}

		return outputObjects;
	}

	@Override
	public String getLanguage() {
		return LANGUAGE;
	}

	@Override
	public ScriptingExecutor newInstance(boolean executeInSeparateThread) {
		return new GroovyExecutor();
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		initScriptingExecutorClassLoader();

		_groovyShell = new GroovyShell(getScriptingExecutorClassLoader());
	}

	protected GroovyShell getGroovyShell(ClassLoader[] classLoaders) {
		if (ArrayUtil.isEmpty(classLoaders)) {
			if (_groovyShell == null) {
				synchronized (this) {
					if (_groovyShell == null) {
						_groovyShell = new GroovyShell(
							getScriptingExecutorClassLoader());
					}
				}
			}

			return _groovyShell;
		}

		ClassLoader aggregateClassLoader = getAggregateClassLoader(
			classLoaders);

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

	private volatile GroovyShell _groovyShell;
	private final ConcurrentMap<ClassLoader, GroovyShell> _groovyShells =
		new ConcurrentReferenceKeyHashMap<>(
			FinalizeManager.WEAK_REFERENCE_FACTORY);

}