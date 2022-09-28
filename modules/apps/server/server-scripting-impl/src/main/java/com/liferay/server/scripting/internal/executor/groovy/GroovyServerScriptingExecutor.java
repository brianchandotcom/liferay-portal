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

package com.liferay.server.scripting.internal.executor.groovy;

import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.server.scripting.executor.ServerScriptingExecutor;

import groovy.lang.Binding;
import groovy.lang.GroovyRuntimeException;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = ServerScriptingExecutor.class)
public class GroovyServerScriptingExecutor implements ServerScriptingExecutor {

	@Override
	public void execute(Map<String, Object> inputObjects, String script)
		throws ScriptingException {

		try {
			GroovyShell groovyShell = new GroovyShell(_getClassLoader());

			Script compiledScript = groovyShell.parse(script);

			compiledScript.setBinding(new Binding(inputObjects));

			compiledScript.run();
		}
		catch (GroovyRuntimeException groovyRuntimeException) {
			throw new ScriptingException(
				groovyRuntimeException.getMessage(),
				groovyRuntimeException.getCause());
		}
	}

	@Override
	public String getLanguage() {
		return "groovy";
	}

	private ClassLoader _getClassLoader() {
		Class<?> clazz = getClass();

		Thread currentThread = Thread.currentThread();

		return AggregateClassLoader.getAggregateClassLoader(
			clazz.getClassLoader(), currentThread.getContextClassLoader());
	}

}