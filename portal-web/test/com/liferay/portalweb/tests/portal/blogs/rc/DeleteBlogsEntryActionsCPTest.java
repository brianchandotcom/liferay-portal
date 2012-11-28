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

package com.liferay.portalweb.tests.portal.blogs.rc;

import com.liferay.portalweb.blocks.controlpanel.blogs.actions.CPBlogsPortletActions;
import com.liferay.portalweb.blocks.controlpanel.blogs.macros.CPBlogsEntryMacros;
import com.liferay.portalweb.blocks.controlpanel.recyclebin.macros.CPRecycleBinMacros;
import com.liferay.portalweb.blocks.portal.macros.NavigationMacros;
import com.liferay.portalweb.blocks.portlet.signin.macros.PortletSignInUserMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteBlogsEntryActionsCPTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		CPBlogsEntryMacros cPBlogsEntryMacros = new CPBlogsEntryMacros(selenium);
		PortletSignInUserMacros portletSignInUserMacros = new PortletSignInUserMacros(selenium);

		portletSignInUserMacros.signIn("test@liferay.com", "test");
		cPBlogsEntryMacros.addBlogsEntry("Blogs Entry Title",
			"Blogs Entry Content");
	}

	public void test() throws Exception {
		CPBlogsPortletActions cPBlogsPortletActions = new CPBlogsPortletActions(selenium);
		NavigationMacros navigationMacros = new NavigationMacros(selenium);

		navigationMacros.navigateControlPanelPage("Blogs");
		cPBlogsPortletActions.assertTextEquals("BLOGS_ENTRY_LINK_TITLE",
			"Blogs Entry Title");
		cPBlogsPortletActions.assertTextEquals("BLOGS_ENTRY_LINK_AUTHOR",
			"Joe Bloggs");
		cPBlogsPortletActions.assertTextEquals("BLOGS_ENTRY_LINK_STATUS",
			"Approved");
		cPBlogsPortletActions.click("BLOGS_ENTRY_LINK_ACTIONS", "Actions");
		cPBlogsPortletActions.click("BLOGS_ENTRY_LINK_ACTIONS_DELETE",
			"Move to the Recycle Bin");
		cPBlogsPortletActions.assertTextEquals("PORTLET_TEXT_SUCCESS_UNDO",
			"The selected item was moved to the Recycle Bin. Undo");
		cPBlogsPortletActions.assertTextEquals("PORTLET_TEXT_INFO",
			"No entries were found.");
		cPBlogsPortletActions.assertTextNotPresent("", "Blogs Entry Title");
		cPBlogsPortletActions.assertTextNotPresent("", "Blogs Entry Content");
	}

	@Override
	public void tearDown() throws Exception {
		CPBlogsEntryMacros cPBlogsEntryMacros = new CPBlogsEntryMacros(selenium);
		CPRecycleBinMacros cPRecycleBinMacros = new CPRecycleBinMacros(selenium);
		PortletSignInUserMacros portletSignInUserMacros = new PortletSignInUserMacros(selenium);

		cPBlogsEntryMacros.tearDownBlogsEntry();
		cPRecycleBinMacros.emptyRecycleBin();
		portletSignInUserMacros.signOut();
	}
}