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

package com.liferay.portalweb.portlet.wikidisplay.wikinode.selectwikinodemultiple;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.util.TearDownPageTest;
import com.liferay.portalweb.portlet.wiki.portlet.addportletwiki.AddPageWikiTest;
import com.liferay.portalweb.portlet.wiki.portlet.addportletwiki.AddPortletWikiTest;
import com.liferay.portalweb.portlet.wiki.wikinode.addwikinode.TearDownWikiNodeTest;
import com.liferay.portalweb.portlet.wikidisplay.portlet.addportletwd.AddPageWDTest;
import com.liferay.portalweb.portlet.wikidisplay.portlet.addportletwd.AddPortletWDTest;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class SelectWikiNodeMultipleTests extends BaseTestSuite {
	public static Test suite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTestSuite(AddPageWikiTest.class);
		testSuite.addTestSuite(AddPortletWikiTest.class);
		testSuite.addTestSuite(AddPageWDTest.class);
		testSuite.addTestSuite(AddPortletWDTest.class);
		testSuite.addTestSuite(AddWikiNode1Test.class);
		testSuite.addTestSuite(AddWikiNode2Test.class);
		testSuite.addTestSuite(AddWikiNode3Test.class);
		testSuite.addTestSuite(AddWikiFrontPage1Test.class);
		testSuite.addTestSuite(AddWikiFrontPage2Test.class);
		testSuite.addTestSuite(AddWikiFrontPage3Test.class);
		testSuite.addTestSuite(SelectWikiNode1Test.class);
		testSuite.addTestSuite(SelectWikiNode2Test.class);
		testSuite.addTestSuite(SelectWikiNode3Test.class);
		testSuite.addTestSuite(TearDownWikiNodeTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}
}