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

package com.liferay.object.internal.validation.rule;

import com.liferay.object.constants.ObjectValidationRuleConstants;
import com.liferay.object.validation.rule.ObjectValidationRuleEngine;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.scripting.ScriptingException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true, service = ObjectValidationRuleEngine.class)
public class GroovyObjectValidationRuleEngineImpl
	implements ObjectValidationRuleEngine {

	@Override
	public boolean evaluate(Map<String, Object> inputObjects, String script) {
		try {
			return _evaluate(inputObjects, script);
		}
		catch (Exception exception) {
			_log.error(exception);

			return false;
		}
	}

	@Override
	public String getName() {
		return ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY;
	}

	@Override
	public boolean isValidScript(String script) {
		try {
			_scripting.eval(
				null, Collections.emptyMap(), new HashSet<>(),
				ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY, script);
		}
		catch (ScriptingException scriptingException) {
			if (_log.isDebugEnabled()) {
				_log.debug(scriptingException);
			}

			return false;
		}

		return true;
	}

	private boolean _evaluate(Map<String, Object> inputObjects, String script)
		throws Exception {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		try {
			currentThread.setContextClassLoader(classLoader);

			_scripting.eval(
				null, inputObjects, new HashSet<>(),
				ObjectValidationRuleConstants.ENGINE_TYPE_GROOVY, script);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroovyObjectValidationRuleEngineImpl.class);

	@Reference
	private Scripting _scripting;

}