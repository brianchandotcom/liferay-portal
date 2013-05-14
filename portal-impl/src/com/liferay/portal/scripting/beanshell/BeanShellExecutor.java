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

package com.liferay.portal.scripting.beanshell;

import bsh.Interpreter;

import com.liferay.portal.kernel.scripting.BaseScriptingExecutor;
import com.liferay.portal.kernel.scripting.ExecutionException;
import com.liferay.portal.kernel.scripting.ScriptingException;
import com.liferay.portal.kernel.util.AggregateClassLoader;
import com.liferay.portal.util.ClassLoaderUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Shuyang Zhou
 */
public class BeanShellExecutor extends BaseScriptingExecutor {

	public static final String LANGUAGE = "beanshell";

	public Map<String, Object> eval(
			Set<String> allowedClasses, Map<String, Object> inputObjects,
			Set<String> outputNames, String script, ClassLoader... classLoaders)
		throws ScriptingException {

		if (allowedClasses != null) {
			throw new ExecutionException(
				"Constrained execution not supported for BeanShell");
		}

		try {
			Interpreter interpreter = new Interpreter();

			for (Map.Entry<String, Object> entry : inputObjects.entrySet()) {
				interpreter.set(entry.getKey(), entry.getValue());
			}

			if ((classLoaders != null) && (classLoaders.length > 0)) {
				ClassLoader aggregateClassLoader =
					AggregateClassLoader.getAggregateClassLoader(
						ClassLoaderUtil.getPortalClassLoader(), classLoaders);

				interpreter.setClassLoader(aggregateClassLoader);
			}

			interpreter.eval(script);

			if (outputNames == null) {
				return null;
			}

			Map<String, Object> outputObjects = new HashMap<String, Object>();

			for (String outputName : outputNames) {
				outputObjects.put(outputName, interpreter.get(outputName));
			}

			return outputObjects;
		}
		catch (Exception e) {
			throw new ScriptingException(e.getMessage(), e);
		}
	}

	@Override
	public String getLanguage() {
		return LANGUAGE;
	}

}