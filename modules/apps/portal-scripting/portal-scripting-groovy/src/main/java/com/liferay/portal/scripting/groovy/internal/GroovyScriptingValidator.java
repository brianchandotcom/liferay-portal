/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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