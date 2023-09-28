/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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