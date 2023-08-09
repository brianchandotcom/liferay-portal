/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.scripting.internal.validator;

import com.liferay.object.scripting.exception.ObjectScriptingException;
import com.liferay.object.scripting.validator.ObjectScriptingValidator;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = ObjectScriptingValidator.class)
public class ObjectScriptingValidatorImpl implements ObjectScriptingValidator {

	@Override
	public void validate(String language, String script)
		throws ObjectScriptingException {

		if (StringUtil.count(script, _NEW_LINE) > _MAXIMUM_NUMBER_OF_LINES) {
			throw new ObjectScriptingException(
				"The script exceeds the maximum number of lines",
				"the-maximum-number-of-lines-available-is-2987");
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Class<?> clazz = getClass();

		currentThread.setContextClassLoader(clazz.getClassLoader());

		try {
			_scripting.validate(language, script);
		}
		catch (ScriptingException scriptingException) {
			if (_log.isDebugEnabled()) {
				_log.debug(scriptingException);
			}

			throw new ObjectScriptingException(
				"The script syntax is invalid", "syntax-error");
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private static final int _MAXIMUM_NUMBER_OF_LINES = 2987;

	private static final String _NEW_LINE = "\n";

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectScriptingValidatorImpl.class);

	@Reference
	private Scripting _scripting;

}