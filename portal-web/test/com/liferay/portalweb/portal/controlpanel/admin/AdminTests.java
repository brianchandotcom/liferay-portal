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

package com.liferay.portalweb.portal.controlpanel.admin;

import com.liferay.portalweb.portal.BaseTestSuite;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AdminTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddServerCategoryTest.class);
		testSuite.addTestSuite(EditServerCategoryTest.class);
		testSuite.addTestSuite(BrowseServerTest.class);
		testSuite.addTestSuite(BrowseServerInstanceTest.class);
		testSuite.addTestSuite(AddServerInstanceTest.class);
		testSuite.addTestSuite(AddNullServerInstanceWebIDTest.class);
		testSuite.addTestSuite(AddDuplicateServerInstanceWebIDTest.class);
		testSuite.addTestSuite(AddNullServerInstanceVHTest.class);
		testSuite.addTestSuite(AddDuplicateServerInstanceVHTest.class);
		testSuite.addTestSuite(AddInvalidServerInstanceVHTest.class);
		testSuite.addTestSuite(AddNullServerInstanceMDTest.class);
		testSuite.addTestSuite(EditServerInstanceTest.class);
		testSuite.addTestSuite(BrowseServerPluginsInstallationTest.class);

		return testSuite;
	}
}