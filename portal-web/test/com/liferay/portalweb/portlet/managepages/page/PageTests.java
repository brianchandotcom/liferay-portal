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

package com.liferay.portalweb.portlet.managepages.page;

import com.liferay.portalweb.portal.BaseTests;
import com.liferay.portalweb.portlet.managepages.page.addchildpage.AddChildPageTests;
import com.liferay.portalweb.portlet.managepages.page.addchildpagemultiple.AddChildPageMultipleTests;
import com.liferay.portalweb.portlet.managepages.page.addpage.AddPageTests;
import com.liferay.portalweb.portlet.managepages.page.addpagefriendlyurlreservedwords.AddPageFriendlyURLReservedWordsTests;
import com.liferay.portalweb.portlet.managepages.page.addpagemultiple.AddPageMultipleTests;
import com.liferay.portalweb.portlet.managepages.page.copypagechildpage.CopyPageChildPageTests;
import com.liferay.portalweb.portlet.managepages.page.copypagepage.CopyPagePageTests;
import com.liferay.portalweb.portlet.managepages.page.savepagetypeembedded.SavePageTypeEmbeddedTests;
import com.liferay.portalweb.portlet.managepages.page.savepagetypelinktopage.SavePageTypeLinkToPageTests;
import com.liferay.portalweb.portlet.managepages.page.savepagetypepanel.SavePageTypePanelTests;
import com.liferay.portalweb.portlet.managepages.page.savepagetypeportlet.SavePageTypePortletTests;
import com.liferay.portalweb.portlet.managepages.page.savepagetypeurl.SavePageTypeURLTests;
import com.liferay.portalweb.portlet.managepages.page.setdisplayorder.SetDisplayOrderTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class PageTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddChildPageTests.suite());
		testSuite.addTest(AddChildPageMultipleTests.suite());
		testSuite.addTest(AddPageTests.suite());
		testSuite.addTest(AddPageFriendlyURLReservedWordsTests.suite());
		testSuite.addTest(AddPageMultipleTests.suite());
		testSuite.addTest(CopyPageChildPageTests.suite());
		testSuite.addTest(CopyPagePageTests.suite());
		testSuite.addTest(SavePageTypeEmbeddedTests.suite());
		testSuite.addTest(SavePageTypeLinkToPageTests.suite());
		testSuite.addTest(SavePageTypePanelTests.suite());
		testSuite.addTest(SavePageTypePortletTests.suite());
		testSuite.addTest(SavePageTypeURLTests.suite());
		//testSuite.addTest(SavePageTypeWebContentTests.suite());
		testSuite.addTest(SetDisplayOrderTests.suite());

		return testSuite;
	}

}