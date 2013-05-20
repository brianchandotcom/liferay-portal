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

package com.liferay.portal.tools.seleniumbuilder;

import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class TestSuiteConverter extends BaseConverter {

	public TestSuiteConverter(SeleniumBuilderContext seleniumBuilderContext) {
		super(seleniumBuilderContext);
	}

	public void convert(String testSuiteName) throws Exception {
		Map<String, Object> context = getContext();

		StackWrapper macroNameStack = new StackWrapper();

		context.put("macroNameStack", macroNameStack);
		context.put("testSuiteName", testSuiteName);

		String javaContent = processTemplate("test_suite.ftl", context);

		seleniumBuilderFileUtil.writeFile(
			seleniumBuilderContext.getTestSuiteJavaFileName(testSuiteName),
			javaContent, true);

		String htmlContent = processTemplate("test_suite_html.ftl", context);

		seleniumBuilderFileUtil.writeFile(
			seleniumBuilderContext.getTestSuiteHTMLFileName(testSuiteName),
			htmlContent, false);
	}

}