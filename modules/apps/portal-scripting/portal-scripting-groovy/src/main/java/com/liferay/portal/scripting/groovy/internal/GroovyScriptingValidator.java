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
import com.liferay.portal.kernel.scripting.ScriptingValidator;
import com.liferay.portal.kernel.util.AggregateClassLoader;

import groovy.lang.GroovyShell;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "scripting.language=" + GroovyScriptingValidator.LANGUAGE,
	service = ScriptingValidator.class
)
public class GroovyScriptingValidator implements ScriptingValidator {

	public static final String LANGUAGE = "groovy";

	@Override
	public String getLanguage() {
		return LANGUAGE;
	}

	@Override
	public void validate(String script) throws ScriptingException {
		try {
			GroovyShell groovyShell = new GroovyShell(getClassLoader());

			groovyShell.parse(script);
		}
		catch (Exception exception) {
			throw new ScriptingException(exception);
		}
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Thread currentThread = Thread.currentThread();

		return AggregateClassLoader.getAggregateClassLoader(
			classLoader, currentThread.getContextClassLoader());
	}

}