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

package com.liferay.poshi.runner;

import com.liferay.poshi.runner.selenium.LiferaySelenium;
import com.liferay.poshi.runner.selenium.SeleniumUtil;

import java.lang.reflect.Method;

import org.dom4j.Element;

/**
 * @author Karen Dang
 * @author Michael Hashimoto
 */
public class PoshiRunnerExecutor {

	public static Object invoker(
			Method method, Object obj, String[] parameters, String methodName)
		throws Exception {

		int parameterCount = 0;

		if (parameters != null) {
			parameterCount = parameters.length;
		}

		if (parameterCount == 0) {
			Object invokedObject = method.invoke(obj);

			return invokedObject;
		}
		else if (parameterCount == 1) {
			Object invokedObject = method.invoke(obj, parameters[0]);

			return invokedObject;
		}
		else if (parameterCount == 2) {
			if (methodName.equals("clickAt") && (parameters[1] == null)) {
				parameters[1] = "";
			}

			Object invokedObject = method.invoke(
				obj, parameters[0], parameters[1]);

			return invokedObject;
		}
		else if (parameterCount == 3) {
			Object invokedObject = method.invoke(
				obj, parameters[0], parameters[1], parameters[2]);

			return invokedObject;
		}

		return null;
	}

	public static void runSeleniumElement(Element element) throws Exception {
		String argument1 = element.attributeValue("argument1");
		String selenium = element.attributeValue("selenium");

		int x = PoshiRunnerContext.getSeleniumParameterCount(selenium);

		Object obj = _liferaySelenium;

		Class clazz = obj.getClass();

		if (x == 0) {
			Method method = clazz.getMethod(selenium, null);

			invoker(method, obj, new String[] {}, selenium);
		}
		else if (x == 1) {
			Method method = clazz.getMethod(
				selenium, new Class[] {String.class});

			invoker(method, obj, new String[] {argument1}, selenium);
		}
		else if (x == 2) {
			String argument2 = element.attributeValue("argument2");

			Method method = clazz.getMethod(
				selenium, new Class[] {String.class, String.class});

			invoker(method, obj, new String[] {argument1, argument2}, selenium);
		}
		else if (x == 3) {
			String argument2 = element.attributeValue("argument2");
			String argument3 = element.attributeValue("argument3");

			Method method = clazz.getMethod(
				selenium,
				new Class[] {String.class, String.class, String.class});

			invoker(
				method, obj, new String[] {argument1, argument2, argument3},
				selenium);
		}
	}

	private static final LiferaySelenium _liferaySelenium =
		SeleniumUtil.getSelenium();

}