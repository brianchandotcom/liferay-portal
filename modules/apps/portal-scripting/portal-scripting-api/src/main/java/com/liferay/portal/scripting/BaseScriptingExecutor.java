/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scripting;

import com.liferay.portal.kernel.scripting.ScriptingContainer;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.scripting.ScriptingExecutor;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.util.Map;
import java.util.Set;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 */
public abstract class BaseScriptingExecutor implements ScriptingExecutor {

	@Override
	public void clearCache() {
	}

	@Override
	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, File scriptFile)
		throws ScriptingException {

		try {
			String script = FileUtil.read(scriptFile);

			return eval(allowedClasses, inputObjects, outputNames, script);
		}
		catch (IOException ioException) {
			throw new ScriptingException(ioException);
		}
	}

	@Override
	public ScriptingContainer<?> getScriptingContainer() {
		return null;
	}

	protected ClassLoader getClassLoader() {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		Thread currentThread = Thread.currentThread();

		return AggregateClassLoader.getAggregateClassLoader(
			classLoader, currentThread.getContextClassLoader());
	}

}