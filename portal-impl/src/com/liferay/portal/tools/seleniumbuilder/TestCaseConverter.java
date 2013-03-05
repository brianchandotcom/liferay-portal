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

package com.liferay.portal.tools.seleniumbuilder;

/**
 * @author Michael Hashimoto
 */
public class TestCaseConverter extends BaseConverter {

	public TestCaseConverter(SeleniumBuilderContext seleniumBuilderContext) {
		_seleniumBuilderContext = seleniumBuilderContext;
	}

	public void convert(String testCaseName) throws Exception {
		String testCaseSimpleClassName = _getTestCaseSimpleClassName(
			testCaseName);
	}

	private String _getTestCaseSimpleClassName(String testCaseName) {
		return _seleniumBuilderContext.getTestCaseSimpleClassName(testCaseName);
	}

	private SeleniumBuilderContext _seleniumBuilderContext;

}