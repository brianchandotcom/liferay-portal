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

import com.liferay.poshi.runner.selenium.SeleniumUtil;

import junit.framework.TestCase;

import org.dom4j.Element;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Hashimoto
 * @author Karen Dang
 */
public class PoshiRunner extends TestCase {

	@Override
	public void setUp() throws Exception {
		SeleniumUtil.startSelenium();
	}

	@Override
	public void tearDown() throws Exception {
		SeleniumUtil.stopSelenium();
	}

	public void testPoshiRunner() throws Exception {
		String classCommandName = System.getProperty("test.case.name");

		Element element = PoshiRunnerContext.getTestcaseCommandElement(
			classCommandName);

		PoshiRunnerExecutor.parse(element);
	}

}