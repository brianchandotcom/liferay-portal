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

package com.liferay.portalweb.portlet.wiki.wikipage.addchildpage1childpageduplicatechildpage2;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class AddChildPage1ChildPageDuplicateChildPage2Tests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddPageWikiTest.class);
		testSuite.addTestSuite(AddPortletWikiTest.class);
		testSuite.addTestSuite(AddFrontPageTest.class);
		testSuite.addTestSuite(AddFrontPageChildPage1Test.class);
		testSuite.addTestSuite(AddFrontPageChildPage2Test.class);
		testSuite.addTestSuite(AddFrontPageChildPage1ChildPageTest.class);
		testSuite.addTestSuite(
			AddChildPage1ChildPageDuplicateChildPage2Test.class);
		testSuite.addTestSuite(ViewFrontPageChildPage1ChildPageTest.class);
		testSuite.addTestSuite(TearDownWikiNodeTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}

}