/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scripting.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.kernel.scripting.ScriptingValidator;
import com.liferay.portal.kernel.scripting.UnsupportedLanguageException;

import java.io.IOException;
import java.io.LineNumberReader;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.StopWatch;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@Component(service = Scripting.class)
public class ScriptingImpl implements Scripting {

	@Override
	public void clearCache(String language) throws ScriptingException {
		ScriptingExecutor scriptingExecutor =
			_scriptingExecutorsServiceTrackerMap.getService(language);

		if (scriptingExecutor == null) {
			throw new UnsupportedLanguageException(language);
		}

		scriptingExecutor.clearCache();
	}

	@Override
	public ScriptingExecutor createScriptingExecutor(
		String language, boolean executeInSeparateThread) {

		ScriptingExecutor scriptingExecutor =
			_scriptingExecutorsServiceTrackerMap.getService(language);

		return scriptingExecutor.newInstance(executeInSeparateThread);
	}

	@Override
	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String language, String script)
		throws ScriptingException {

		ScriptingExecutor scriptingExecutor =
			_scriptingExecutorsServiceTrackerMap.getService(language);

		if (scriptingExecutor == null) {
			throw new UnsupportedLanguageException(language);
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		try {
			return scriptingExecutor.eval(
				allowedClasses, inputObjects, outputNames, script);
		}
		catch (Exception exception) {
			throw new ScriptingException(
				_getErrorMessage(script, exception), exception);
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
		return _scriptingExecutorsServiceTrackerMap.keySet();
	}

	@Override
	public void validate(String language, String script)
		throws ScriptingException {

		ScriptingValidator scriptingValidator =
			_scriptingValidatorsServiceTrackerMap.getService(language);

		scriptingValidator.validate(script);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_scriptingExecutorsServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ScriptingExecutor.class, null,
				ServiceReferenceMapperFactory.create(
					bundleContext,
					(scriptingExecutor, emitter) -> emitter.emit(
						scriptingExecutor.getLanguage())));
		_scriptingValidatorsServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, ScriptingValidator.class, null,
				ServiceReferenceMapperFactory.create(
					bundleContext,
					(scriptingValidator, emitter) -> emitter.emit(
						scriptingValidator.getLanguage())));
	}

	@Deactivate
	protected void deactivate() {
		_scriptingExecutorsServiceTrackerMap.close();
		_scriptingValidatorsServiceTrackerMap.close();
	}

	private String _getErrorMessage(String script, Exception exception) {
		StringBundler sb = new StringBundler();

		sb.append(exception.getMessage());
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

			sb.append(exception.getMessage());
			sb.append(StringPool.NEW_LINE);
			sb.append(script);
		}

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(ScriptingImpl.class);

	private ServiceTrackerMap<String, ScriptingExecutor>
		_scriptingExecutorsServiceTrackerMap;
	private ServiceTrackerMap<String, ScriptingValidator>
		_scriptingValidatorsServiceTrackerMap;

}