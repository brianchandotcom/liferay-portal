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

package com.liferay.object.runtime.scripting.internal.validator;

import com.liferay.object.runtime.scripting.exception.GroovyScriptingException;
import com.liferay.object.runtime.scripting.validator.GroovyScriptingValidator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;

import groovy.lang.GroovyShell;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(immediate = true, service = GroovyScriptingValidator.class)
public class GroovyScriptingValidatorImpl implements GroovyScriptingValidator {

	@Override
	public void validate(String script) throws GroovyScriptingException {
		if (StringUtil.count(script, _NEW_LINE) >
				_MAXIMUM_NUMBER_OF_LINES) {

			throw new GroovyScriptingException(
				"the-maximum-number-of-lines-available-is-2987");
		}

		try {
			GroovyShell groovyShell = new GroovyShell();

			groovyShell.parse(script);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw new GroovyScriptingException("syntax-error");
		}
	}

	private static final int _MAXIMUM_NUMBER_OF_LINES = 2987;

	private static final String _NEW_LINE = "\n";

	private static final Log _log = LogFactoryUtil.getLog(
		GroovyScriptingValidatorImpl.class);

}