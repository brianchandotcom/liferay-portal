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

package com.liferay.portalweb.portlet.wiki.portlet;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portlet.wiki.portlet.addportletduplicate.AddPortletDuplicateTests;
import com.liferay.portalweb.portlet.wiki.portlet.addportletwiki.AddPortletWikiTests;
import com.liferay.portalweb.portlet.wiki.portlet.addportletwikisite.AddPortletWikiSiteTests;
import com.liferay.portalweb.portlet.wiki.portlet.removeportlet.RemovePortletTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddPortletDuplicateTests.suite());
		testSuite.addTest(AddPortletWikiTests.suite());
		testSuite.addTest(AddPortletWikiSiteTests.suite());
		testSuite.addTest(RemovePortletTests.suite());

		return testSuite;
	}

}