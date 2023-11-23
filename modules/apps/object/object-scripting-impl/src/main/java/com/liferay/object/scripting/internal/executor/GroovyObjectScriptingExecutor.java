/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.scripting.internal.executor;

import com.liferay.object.scripting.executor.ObjectScriptingExecutor;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.lang.ThreadContextClassLoaderUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.scripting.ScriptingExecutorRegistry;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(
	property = "scripting.language=groovy",
	service = ObjectScriptingExecutor.class
)
public class GroovyObjectScriptingExecutor implements ObjectScriptingExecutor {

	@Override
	public Map<String, Object> execute(
		Map<String, Object> inputObjects, Set<String> outputNames,
		String script) {

		ScriptingExecutor scriptingExecutor =
			_scriptingExecutorRegistry.getScriptingExecutor("groovy");

		if (scriptingExecutor == null) {
			return Collections.emptyMap();
		}

		Map<String, Object> results = new HashMap<>();

		try (SafeCloseable safeCloseable = ThreadContextClassLoaderUtil.swap(
				GroovyObjectScriptingExecutor.class.getClassLoader())) {

			results = scriptingExecutor.eval(
				null, inputObjects, outputNames, script);

			results.put("invalidScript", false);
		}
		catch (Exception exception) {
			_log.error(exception);

			results.put("invalidScript", true);
		}

		results.putIfAbsent(
			"validationCriteriaMet",
			!GetterUtil.getBoolean(results.get("invalidFields")));

		return results;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroovyObjectScriptingExecutor.class);

	@Reference
	private ScriptingExecutorRegistry _scriptingExecutorRegistry;

}