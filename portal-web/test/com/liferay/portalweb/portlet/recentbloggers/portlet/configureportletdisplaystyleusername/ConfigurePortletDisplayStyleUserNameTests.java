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

package com.liferay.portalweb.portlet.recentbloggers.portlet.configureportletdisplaystyleusername;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.recentbloggers.portlet.addportletrb.AddPageRBTest;
import com.liferay.portalweb.portlet.recentbloggers.portlet.addportletrb.AddPortletRBTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ConfigurePortletDisplayStyleUserNameTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageRBTest.class);
		testSuite.addTestSuite(AddPortletRBTest.class);
		testSuite.addTestSuite(ConfigurePortletDisplayStyleUserNameTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}