/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portlet.addapplication;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.addapplication.collaboration.CollaborationTests;
import com.liferay.portalweb.portlet.addapplication.community.CommunityTests;
import com.liferay.portalweb.portlet.addapplication.contentmanagement.ContentManagementTests;
import com.liferay.portalweb.portlet.addapplication.finance.FinanceTests;
import com.liferay.portalweb.portlet.addapplication.news.NewsTests;
import com.liferay.portalweb.portlet.addapplication.sample.SampleTests;
import com.liferay.portalweb.portlet.addapplication.shopping.ShoppingTests;
import com.liferay.portalweb.portlet.addapplication.social.SocialTests;
import com.liferay.portalweb.portlet.addapplication.tools.ToolsTests;
import com.liferay.portalweb.portlet.addapplication.wiki.WikiTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddApplicationTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(CollaborationTests.suite());
		testSuite.addTest(CommunityTests.suite());
		testSuite.addTest(ContentManagementTests.suite());
		testSuite.addTest(FinanceTests.suite());
		testSuite.addTest(NewsTests.suite());
		testSuite.addTest(SampleTests.suite());
		testSuite.addTest(ShoppingTests.suite());
		testSuite.addTest(SocialTests.suite());
		testSuite.addTest(ToolsTests.suite());
		testSuite.addTest(WikiTests.suite());

		return testSuite;
	}

}