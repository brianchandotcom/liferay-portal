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

import com.liferay.portalweb.selenium.AssertEmailContentTestCase;
import com.liferay.portalweb.selenium.AssertEmailSubjectTestCase;
import com.liferay.portalweb.selenium.ConnectEmailTestCase;
import com.liferay.portalweb.selenium.DeleteEmailsTestCase;
import com.liferay.portalweb.selenium.ReplyEmailTestCase;
import com.liferay.portalweb.selenium.SendEmailTestCase;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Kwang Lee
 */
public class SeleniumEmailTestSuite extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AssertEmailContentTestCase.class);
		testSuite.addTestSuite(AssertEmailSubjectTestCase.class);
		testSuite.addTestSuite(ConnectEmailTestCase.class);
		testSuite.addTestSuite(DeleteEmailsTestCase.class);
		testSuite.addTestSuite(ReplyEmailTestCase.class);
		testSuite.addTestSuite(SendEmailTestCase.class);

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}