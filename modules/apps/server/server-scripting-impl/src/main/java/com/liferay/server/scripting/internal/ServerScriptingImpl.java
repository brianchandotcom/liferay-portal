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

package com.liferay.server.scripting.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.UnsupportedLanguageException;
import com.liferay.server.scripting.ServerScripting;
import com.liferay.server.scripting.executor.ServerScriptingExecutor;

import java.io.IOException;
import java.io.LineNumberReader;

import java.util.Map;
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
@Component(immediate = true, service = ServerScripting.class)
public class ServerScriptingImpl implements ServerScripting {

	@Override
	public void execute(
			Map<String, Object> inputObjects, String language, String script)
		throws ScriptingException {

		ServerScriptingExecutor serverScriptingExecutor =
			_serverScriptingExecutors.get(language);

		if (serverScriptingExecutor == null) {
			throw new UnsupportedLanguageException(language);
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			serverScriptingExecutor.execute(inputObjects, script);
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

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setServerScriptingExecutor(
		ServerScriptingExecutor serverScriptingExecutor) {

		_serverScriptingExecutors.put(
			serverScriptingExecutor.getLanguage(), serverScriptingExecutor);
	}

	protected void unsetServerScriptingExecutor(
		ServerScriptingExecutor serverScriptingExecutor) {

		_serverScriptingExecutors.remove(serverScriptingExecutor.getLanguage());
	}

	private String _getErrorMessage(String errorMessage, String script) {
		StringBundler sb = new StringBundler();

		sb.append(errorMessage);
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
		catch (IOException ioException) {
			if (_log.isDebugEnabled()) {
				_log.debug(ioException);
			}

			sb.setIndex(0);

			sb.append(errorMessage);
			sb.append(StringPool.NEW_LINE);
			sb.append(script);
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServerScriptingImpl.class);

	private final Map<String, ServerScriptingExecutor>
		_serverScriptingExecutors = new ConcurrentHashMap<>();

}