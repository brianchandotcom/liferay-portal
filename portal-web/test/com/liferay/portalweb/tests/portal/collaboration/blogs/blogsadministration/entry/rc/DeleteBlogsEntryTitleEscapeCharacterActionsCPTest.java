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

package com.liferay.portalweb.tests.portal.collaboration.blogs.blogsadministration.entry.rc;

import com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.home.rc.CPBlogsHomeActions;
import com.liferay.portalweb.blocks.portal.blogs.controlpanel.macros.rc.CPBlogsEntryMacros;
import com.liferay.portalweb.blocks.portal.home.page.macros.rc.GotoMacros;
import com.liferay.portalweb.blocks.portal.recyclebin.controlpanel.macros.rc.CPRecycleBinMacros;
import com.liferay.portalweb.blocks.portal.signin.page.macros.rc.SignInUserMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteBlogsEntryTitleEscapeCharacterActionsCPTest
	extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		CPBlogsEntryMacros cPBlogsEntryMacros = new CPBlogsEntryMacros(selenium);
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		signInUserMacros.signIn("test@liferay.com", "test");
		cPBlogsEntryMacros.add("<!-- -->", "Blogs Entry Content");
	}

	public void test() throws Exception {
		CPBlogsHomeActions cPBlogsHomeActions = new CPBlogsHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Blogs");
		cPBlogsHomeActions.assertTextEquals("BLOGS_ENTRY_LINK_TITLE_1",
			"<!-- -->");
		cPBlogsHomeActions.assertTextEquals("BLOGS_ENTRY_LINK_AUTHOR_1",
			"Joe Bloggs");
		cPBlogsHomeActions.assertTextEquals("BLOGS_ENTRY_LINK_STATUS_1",
			"Approved");
		cPBlogsHomeActions.click("BLOGS_ENTRY_LINK_ACTIONS_1", "Actions");
		cPBlogsHomeActions.click("BLOGS_ENTRY_LINK_ACTIONS_DELETE",
			"Move to the Recycle Bin");
		cPBlogsHomeActions.assertTextEquals("PORTLET_TEXT_SUCCESS_UNDO",
			"The selected item was moved to the Recycle Bin. Undo");
		cPBlogsHomeActions.assertTextEquals("PORTLET_TEXT_INFO",
			"No entries were found.");
		cPBlogsHomeActions.assertTextNotPresent("", "<!-- -->");
		cPBlogsHomeActions.assertTextNotPresent("", "Blogs Entry Content");
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