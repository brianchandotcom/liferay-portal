/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.scripting;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.UnsupportedLanguageException;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.server.admin.web.internal.scripting.util.ServerScriptingUtil;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.io.IOException;
import java.io.LineNumberReader;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Component;

/**
 * @author Carolina Barbosa
 */
@Component(service = ServerScripting.class)
public class ServerScripting {

	public void execute(
			Map<String, Object> inputObjects, String language, String script)
		throws ScriptingException {

		Set<String> supportedLanguages =
			ServerScriptingUtil.getSupportedLanguages();

		if (!supportedLanguages.contains(language)) {
			throw new UnsupportedLanguageException(language);
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			if (language.equals("groovy")) {
				_executeGroovyScript(inputObjects, script);
			}
		}
		catch (Exception exception) {
			throw new ScriptingException(
				_getErrorMessage(exception.getMessage(), script), exception);
		}
		finally {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Evaluated script in " + stopWatch.getTime() + " ms");
			}
		}
	}

	private void _executeGroovyScript(
			Map<String, Object> inputObjects, String script)
		throws Exception {

		Class<?> clazz = getClass();

		Thread currentThread = Thread.currentThread();

		GroovyShell groovyShell = new GroovyShell(
			AggregateClassLoader.getAggregateClassLoader(
				clazz.getClassLoader(), currentThread.getContextClassLoader()));

		Script compiledScript = groovyShell.parse(script);

		compiledScript.setBinding(new Binding(inputObjects));

		compiledScript.run();
	}

	private String _getErrorMessage(String exceptionMessage, String script) {
		String errorMessage = exceptionMessage.concat(StringPool.NEW_LINE);

		try {
			LineNumberReader lineNumberReader = new LineNumberReader(
				new UnsyncStringReader(script));

			while (true) {
				String line = lineNumberReader.readLine();

				if (line == null) {
					break;
				}

				errorMessage = StringBundler.concat(
					errorMessage, "Line ", lineNumberReader.getLineNumber(),
					": ", line, StringPool.NEW_LINE);
			}
		}
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}

			return StringBundler.concat(
				exceptionMessage, StringPool.NEW_LINE, script);
		}

		return errorMessage;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServerScripting.class);

}