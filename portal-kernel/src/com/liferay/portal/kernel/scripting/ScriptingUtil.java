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

package com.liferay.portal.kernel.scripting;

import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.Map;
import java.util.Set;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class ScriptingUtil {

	public static void clearCache(String language) throws ScriptingException {
		_getScripting().clearCache(language);
	}

	public static ScriptingExecutor createScriptingExecutor(
		String language, boolean executeInSeparateThread) {

		return _getScripting().createScriptingExecutor(
			language, executeInSeparateThread);
	}

	public static Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String language, String script)
		throws ScriptingException {

		return _getScripting().eval(
			allowedClasses, inputObjects, outputNames, language, script);
	}

	public static Set<String> getSupportedLanguages() {
		return _getScripting().getSupportedLanguages();
	}

	private static Scripting _getScripting() {
		return _scripting;
	}

	private static volatile Scripting _scripting =
		ServiceProxyFactory.newServiceTrackedInstance(
			Scripting.class, ScriptingUtil.class, "_scripting", false);

}