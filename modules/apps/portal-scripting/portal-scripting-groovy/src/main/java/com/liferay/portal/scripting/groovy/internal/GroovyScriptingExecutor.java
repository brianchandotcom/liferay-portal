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

import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.scripting.BaseScriptingExecutor;

import groovy.lang.Binding;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = "scripting.language=" + GroovyScriptingExecutor.LANGUAGE,
	service = ScriptingExecutor.class
)
public class GroovyScriptingExecutor extends BaseScriptingExecutor {

	public static final String LANGUAGE = "groovy";

	@Override
	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String script)
		throws ScriptingException {

		if (allowedClasses != null) {
			throw new ScriptingException(
				"Constrained execution not supported for Groovy");
		}

		try {
			GroovyShell groovyShell = new GroovyShell(getClassLoader());

			Script compiledScript = groovyShell.parse(script);

			Binding binding = new Binding(inputObjects);

			compiledScript.setBinding(binding);

			compiledScript.run();

			if (outputNames == null) {
				return null;
			}

			Map<String, Object> outputObjects = new HashMap<>();

			for (String outputName : outputNames) {
				if (binding.hasVariable(outputName)) {
					outputObjects.put(
						outputName, binding.getVariable(outputName));
				}
			}

			return outputObjects;
		}
		catch (GroovyRuntimeException groovyRuntimeException) {
			throw new ScriptingException(
				groovyRuntimeException.getMessage(),
				groovyRuntimeException.getCause());
		}
	}

	@Override
	public String getLanguage() {
		return LANGUAGE;
	}

	@Override
	public ScriptingExecutor newInstance(boolean executeInSeparateThread) {
		return new GroovyScriptingExecutor();
	}

}