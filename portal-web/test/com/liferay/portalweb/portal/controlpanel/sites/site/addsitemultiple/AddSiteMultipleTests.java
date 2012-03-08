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

package com.liferay.portalweb.portal.controlpanel.sites.site.addsitemultiple;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.AddSite1Test;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.AddSite2Test;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.AddSite3Test;
import com.liferay.portalweb.portal.controlpanel.sites.site.addsite.TearDownSiteTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSiteMultipleTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddSite1Test.class);
		testSuite.addTestSuite(AddSite2Test.class);
		testSuite.addTestSuite(AddSite3Test.class);
		testSuite.addTestSuite(TearDownSiteTest.class);

		return testSuite;
	}
}