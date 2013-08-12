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

package com.liferay.portalweb.portal;

import com.liferay.portalweb.selenium.AssertPartialTextTestCase;
import com.liferay.portalweb.selenium.AssertTextTestCase;
import com.liferay.portalweb.selenium.ClickAtTestCase;
import com.liferay.portalweb.selenium.ClickTestCase;
import com.liferay.portalweb.selenium.MouseOverTestCase;
import com.liferay.portalweb.selenium.SelectTestCase;
import com.liferay.portalweb.selenium.WaitForVisibleTestCase;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SeleniumTestSuite extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AssertPartialTextTestCase.class);
		testSuite.addTestSuite(AssertTextTestCase.class);
		testSuite.addTestSuite(ClickAtTestCase.class);
		testSuite.addTestSuite(ClickTestCase.class);
		testSuite.addTestSuite(MouseOverTestCase.class);
		testSuite.addTestSuite(SelectTestCase.class);
		testSuite.addTestSuite(WaitForVisibleTestCase.class);

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}