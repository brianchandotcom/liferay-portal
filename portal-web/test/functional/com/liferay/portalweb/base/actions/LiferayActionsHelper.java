/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.base.actions;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portalweb.portal.util.TestPropsValues;

import com.thoughtworks.selenium.Selenium;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class LiferayActionsHelper {

	public static String firstLetter(String s) {
		return s.substring(0, 1);
	}

	public static String[] getParams(
		Map<String, String> pathMap, String param1, String param2) {

		String[] params = new String[2];

		Pattern pattern = Pattern.compile("__[0-9]*");

		Matcher matcher = pattern.matcher(param1);

		String iteration = null;

		if (matcher.find()) {
			iteration = matcher.group();

			param1 = StringUtil.replace(param1, iteration, "");

			iteration = StringUtil.replace(iteration, "__", "");
		}

		if (pathMap.containsKey(param1)) {
			if (!(iteration == null)) {
				params[0] =
					"xpath=(" + pathMap.get(param1) + ")[" + iteration + "]";
			}
			else {
				params[0] = pathMap.get(param1);
			}
		}
		else {
			params[0] = param1;
		}

		if (param2 == null) {
			params[1] = "";
		}
		else {
			params[1] = param2;
		}

		return params;
	}

	public static boolean isSelenium() {
		String seleniumImplementation = TestPropsValues.SELENIUM_IMPLEMENTATION;

		return seleniumImplementation.equals(Selenium.class.getName());
	}

}