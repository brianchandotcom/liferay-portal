/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.scripting;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.Scripting;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.kernel.scripting.UnsupportedLanguageException;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;
import java.io.LineNumberReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.lang.time.StopWatch;

import org.python.core.Py;
import org.python.core.PyFile;
import org.python.core.PySyntaxError;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class ScriptingImpl implements Scripting {

	@Override
	public void addScriptingExecutor(
		String language, ScriptingExecutor scriptingExecutor) {

		_scriptingExecutors.put(language, scriptingExecutor);
	}

	@Override
	public void clearCache(String language) throws ScriptingException {
		ScriptingExecutor scriptingExecutor = _scriptingExecutors.get(language);

		if (scriptingExecutor == null) {
			throw new UnsupportedLanguageException(language);
		}

		scriptingExecutor.clearCache();
	}

	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String language, String script,
			ClassLoader... classLoaders)
		throws ScriptingException {

		ScriptingExecutor scriptingExecutor = _scriptingExecutors.get(language);

		if (scriptingExecutor == null) {
			throw new UnsupportedLanguageException(language);
		}

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		try {
			return scriptingExecutor.eval(
				allowedClasses, inputObjects, outputNames, script,
				classLoaders);
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

	public void exec(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			String language, String script, ClassLoader... classLoaders)
		throws ScriptingException {

		eval(
			allowedClasses, inputObjects, null, language, script, classLoaders);
	}

	@Override
	public Map<String, Object> getPortletObjects(
		PortletConfig portletConfig, PortletContext portletContext,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		Map<String, Object> objects = new HashMap<String, Object>();

		objects.put("portletConfig", portletConfig);
		objects.put("portletContext", portletContext);
		objects.put("preferences", portletRequest.getPreferences());

		if (portletRequest instanceof ActionRequest) {
			objects.put("actionRequest", portletRequest);
		}
		else if (portletRequest instanceof RenderRequest) {
			objects.put("renderRequest", portletRequest);
		}
		else if (portletRequest instanceof ResourceRequest) {
			objects.put("resourceRequest", portletRequest);
		}
		else {
			objects.put("portletRequest", portletRequest);
		}

		if (portletResponse instanceof ActionResponse) {
			objects.put("actionResponse", portletResponse);
		}
		else if (portletResponse instanceof RenderResponse) {
			objects.put("renderResponse", portletResponse);
		}
		else if (portletResponse instanceof ResourceResponse) {
			objects.put("resourceResponse", portletResponse);
		}
		else {
			objects.put("portletResponse", portletResponse);
		}

		objects.put(
			"userInfo", portletRequest.getAttribute(PortletRequest.USER_INFO));

		return objects;
	}

	@Override
	public Set<String> getSupportedLanguages() {
		return _scriptingExecutors.keySet();
	}

	@Override
	public void setScriptingExecutors(
		Map<String, ScriptingExecutor> scriptingExecutors) {

		for (Map.Entry<String, ScriptingExecutor> entry :
				scriptingExecutors.entrySet()) {

			_scriptingExecutors.put(entry.getKey(), entry.getValue());
		}
	}

	protected String getErrorMessage(Exception e) {
		String message = e.getMessage();

		if (e instanceof PySyntaxError) {
			PySyntaxError pySyntaxError = (PySyntaxError)e;

			UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
				new UnsyncByteArrayOutputStream();

			Py.displayException(
				pySyntaxError.type, pySyntaxError.value,
				pySyntaxError.traceback,
				new PyFile(unsyncByteArrayOutputStream));

			message = unsyncByteArrayOutputStream.toString();
		}

		return message;
	}

	protected String getErrorMessage(String script, Exception e) {
		StringBundler sb = new StringBundler();

		sb.append(getErrorMessage(e));
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

			sb.append(getErrorMessage(e));
			sb.append(StringPool.NEW_LINE);
			sb.append(script);
		}

		return sb.toString();
	}

	private static Log _log = LogFactoryUtil.getLog(ScriptingImpl.class);

	private Map<String, ScriptingExecutor> _scriptingExecutors =
		new ConcurrentHashMap<String, ScriptingExecutor>();

}