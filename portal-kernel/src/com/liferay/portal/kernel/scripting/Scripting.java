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

import java.util.Map;
import java.util.Set;

/**
 * @author Alberto Montero
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public interface Scripting {

	public void clearCache(String language) throws ScriptingException;

	public ScriptingExecutor createScriptingExecutor(
		String language, boolean executeInSeparateThread);

	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String language, String script)
		throws ScriptingException;

	public void exec(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			String language, String script)
		throws ScriptingException;

	public Set<String> getSupportedLanguages();

}