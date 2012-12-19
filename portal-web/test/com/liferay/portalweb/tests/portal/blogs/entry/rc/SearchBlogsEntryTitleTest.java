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

package com.liferay.portalweb.tests.portal.blogs.entry.rc;

import com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.home.CPBlogsHomeActions;
import com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.home.CPBlogsSearchActions;
import com.liferay.portalweb.blocks.portal.blogs.controlpanel.macros.CPBlogsEntryMacros;
import com.liferay.portalweb.blocks.portal.home.page.macros.GotoMacros;
import com.liferay.portalweb.blocks.portal.recyclebin.controlpanel.macros.CPRecycleBinMacros;
import com.liferay.portalweb.blocks.portal.signin.page.macros.SignInUserMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchBlogsEntryTitleTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		CPBlogsEntryMacros cPBlogsEntryMacros = new CPBlogsEntryMacros(selenium);
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		signInUserMacros.signIn("test@liferay.com", "test");
		cPBlogsEntryMacros.add("Blogs Entry Title", "Blogs Entry Content");
	}

	public void test() throws Exception {
		CPBlogsHomeActions cPBlogsHomeActions = new CPBlogsHomeActions(selenium);
		CPBlogsSearchActions cPBlogsSearchActions = new CPBlogsSearchActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Blogs");
		cPBlogsHomeActions.type("PORTLET_FIELD_SEARCH", "Title");
		cPBlogsHomeActions.click("PORTLET_LINK_SEARCH", "Search");
		cPBlogsSearchActions.assertTextEquals("TABLE_NUMBER", "1.");
		cPBlogsSearchActions.assertTextEquals("TABLE_ENTRY", "Blogs Entry Title");
		cPBlogsSearchActions.assertTextEquals("SEARCH_RESULTS",
			"Showing 1 result.");
		cPBlogsSearchActions.type("SEARCH_FIELD", "Title1");
		cPBlogsSearchActions.click("SEARCH_BUTTON", "Search");
		cPBlogsSearchActions.assertElementNotPresent("TABLE_NUMBER", "1.");
		cPBlogsSearchActions.assertElementNotPresent("TABLE_ENTRY",
			"Blogs Entry Title");
		cPBlogsSearchActions.assertTextNotPresent("", "Blogs Entry Title");
		cPBlogsSearchActions.assertTextEquals("PORTLET_INFO",
			"No entries were found that matched the keywords: Title1.");
	}

	@Override
	public void tearDown() throws Exception {
		CPBlogsEntryMacros cPBlogsEntryMacros = new CPBlogsEntryMacros(selenium);
		CPRecycleBinMacros cPRecycleBinMacros = new CPRecycleBinMacros(selenium);
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		cPBlogsEntryMacros.tearDown();
		cPRecycleBinMacros.empty();
		signInUserMacros.signOut();
	}
}