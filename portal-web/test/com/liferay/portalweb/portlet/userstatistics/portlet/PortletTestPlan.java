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

package com.liferay.portalweb.portlet.userstatistics.portlet;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.userstatistics.portlet.addportletus.AddPortletUSTests;
import com.liferay.portalweb.portlet.userstatistics.portlet.addportletusduplicate.AddPortletUSDuplicateTests;
import com.liferay.portalweb.portlet.userstatistics.portlet.addportletussite.AddPortletUSSiteTests;
import com.liferay.portalweb.portlet.userstatistics.portlet.removeportletus.RemovePortletUSTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletUSTests.suite());
		testSuite.addTest(AddPortletUSDuplicateTests.suite());
		testSuite.addTest(AddPortletUSSiteTests.suite());
		testSuite.addTest(RemovePortletUSTests.suite());

		return testSuite;
	}

}