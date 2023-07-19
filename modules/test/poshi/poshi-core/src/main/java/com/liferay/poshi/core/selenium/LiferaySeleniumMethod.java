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

package com.liferay.poshi.core.selenium;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Calum Ragan
 */
public class LiferaySeleniumMethod {

	public LiferaySeleniumMethod(Method seleniumMethod) {
		_method = seleniumMethod;
	}

	public Method getMethod() {
		return _method;
	}

	public String getMethodName() {
		return _method.getName();
	}

	public int getParameterCount() {
		return _method.getParameterCount();
	}

	public List<String> getParameterNames() {
		if (_liferaySeleniumMethodNames.containsKey(getMethodName())) {
			return Arrays.asList(
				_liferaySeleniumMethodNames.get(getMethodName()));
		}

		int parameterCount = getParameterCount();

		if (parameterCount == 0) {
			return Collections.emptyList();
		}

		return _defaultParameterNames.subList(0, parameterCount);
	}

	public Class<?>[] getParameterTypes() {
		return _method.getParameterTypes();
	}

	private static final List<String> _defaultParameterNames = Arrays.asList(
		"locator1", "value1", "locator2");
	private static final List<String> _javaScriptMethodNames = Arrays.asList(
		"assertJavaScript", "executeJavaScript", "getJavaScriptResult",
		"waitForJavaScript", "waitForJavaScriptNoError", "verifyJavaScript");
	private static final Map<String, String[]> _liferaySeleniumMethodNames =
		new HashMap<String, String[]>() {
			{
				put(
					"assertCSSValue",
					new String[] {"locator1", "locator2", "value1"});
				put("executeCDPCommand", new String[] {"value1", "value2"});
				put(
					"ocularAssertElementImage",
					new String[] {"locator1", "value1", "value2"});
			}
		};
	private static final List<String> _singleValueMethodNames = Arrays.asList(
		"assertAlertText", "assertConfirmation", "assertConsoleTextNotPresent",
		"assertConsoleTextPresent", "assertHTMLSourceTextNotPresent",
		"assertHTMLSourceTextPresent", "assertLocation", "assertNotLocation",
		"assertPartialConfirmation", "assertPartialLocation",
		"assertTextNotPresent", "assertTextPresent", "isConsoleTextNotPresent",
		"isConsoleTextPresent", "scrollBy", "typeAlert", "waitForConfirmation",
		"waitForConsoleTextNotPresent", "waitForConsoleTextPresent",
		"waitForTextNotPresent", "waitForTextPresent");

	static {
		for (String methodName : _javaScriptMethodNames) {
			_liferaySeleniumMethodNames.put(
				methodName, new String[] {"value1", "value2", "value3"});
		}

		for (String methodName : _singleValueMethodNames) {
			_liferaySeleniumMethodNames.put(
				methodName, new String[] {"value1"});
		}
	}

	private final Method _method;

}