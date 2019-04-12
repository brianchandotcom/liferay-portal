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

package com.liferay.portal.scripting.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.kernel.scripting.UnsupportedLanguageException;

import java.io.IOException;
import java.io.LineNumberReader;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = Scripting.class)
public class ScriptingImpl implements Scripting {

	@Override
	public void clearCache(String language) throws ScriptingException {
		ScriptingExecutor scriptingExecutor = _scriptingExecutors.get(language);

		if (scriptingExecutor == null) {
			throw new UnsupportedLanguageException(language);
		}

		scriptingExecutor.clearCache();
	}

	@Override
	public ScriptingExecutor createScriptingExecutor(
		String language, boolean executeInSeparateThread) {

		ScriptingExecutor scriptingExecutor = _scriptingExecutors.get(language);

		return scriptingExecutor.newInstance(executeInSeparateThread);
	}

	@Override
	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String language, String script)
		throws ScriptingException {

		ScriptingExecutor scriptingExecutor = _scriptingExecutors.get(language);

		if (scriptingExecutor == null) {
			throw new UnsupportedLanguageException(language);
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			return scriptingExecutor.eval(
				allowedClasses, inputObjects, outputNames, script);
		}
		catch (Exception e) {
			throw new ScriptingException(getErrorMessage(script, e), e);
		}
		finally {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Evaluated script in " + stopWatch.getTime() + " ms");
			}
		}
	}

	@Override
	public void exec(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			String language, String script)
		throws ScriptingException {

		eval(allowedClasses, inputObjects, null, language, script);
	}

	@Override
	public Set<String> getSupportedLanguages() {
		return _scriptingExecutors.keySet();
	}

	protected String getErrorMessage(String script, Exception e) {
		StringBundler sb = new StringBundler();

		sb.append(e.getMessage());
		sb.append(StringPool.NEW_LINE);

		try {
			LineNumberReader lineNumberReader = new LineNumberReader(
				new UnsyncStringReader(script));

			while (true) {
				String line = lineNumberReader.readLine();

				if (line == null) {
					break;
				}

				sb.append("Line ");
				sb.append(lineNumberReader.getLineNumber());
				sb.append(": ");
				sb.append(line);
				sb.append(StringPool.NEW_LINE);
			}
		}
		catch (IOException ioe) {
			sb.setIndex(0);

			sb.append(e.getMessage());
			sb.append(StringPool.NEW_LINE);
			sb.append(script);
		}

		return sb.toString();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setScriptingExecutors(ScriptingExecutor scriptingExecutor) {
		_scriptingExecutors.put(
			scriptingExecutor.getLanguage(), scriptingExecutor);
	}

	protected void unsetScriptingExecutors(
		ScriptingExecutor scriptingExecutor) {

		_scriptingExecutors.remove(scriptingExecutor.getLanguage());
	}

	private static final Log _log = LogFactoryUtil.getLog(ScriptingImpl.class);

	private final Map<String, ScriptingExecutor> _scriptingExecutors =
		new ConcurrentHashMap<>();

}