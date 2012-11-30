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

import com.liferay.portalweb.blocks.portal.controlpanel.blogs.actions.entry.CPBlogsEntryViewActions;
import com.liferay.portalweb.blocks.portal.controlpanel.blogs.actions.entryedit.CPBlogsEntryEditActions;
import com.liferay.portalweb.blocks.portal.controlpanel.blogs.actions.home.CPBlogsPortletActions;
import com.liferay.portalweb.blocks.portal.controlpanel.blogs.macros.CPBlogsEntryMacros;
import com.liferay.portalweb.blocks.portal.controlpanel.recyclebin.macros.CPRecycleBinMacros;
import com.liferay.portalweb.blocks.portal.home.macros.NavigationMacros;
import com.liferay.portalweb.blocks.portal.portlet.signin.macros.PortletSignInUserMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class EditBlogsEntryTitleActionsCPTest extends BaseTestCase {
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
		CPBlogsEntryEditActions cPBlogsEntryEditActions = new CPBlogsEntryEditActions(selenium);
		CPBlogsEntryViewActions cPBlogsEntryViewActions = new CPBlogsEntryViewActions(selenium);
		CPBlogsPortletActions cPBlogsPortletActions = new CPBlogsPortletActions(selenium);
		NavigationMacros navigationMacros = new NavigationMacros(selenium);

		navigationMacros.navigateControlPanelPage("Blogs");
		cPBlogsPortletActions.click("BLOGS_ENTRY_LINK_TITLE",
			"Blogs Entry Title");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_TITLE",
			"Blogs Entry Title");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_CONTENT",
			"Blogs Entry Content");
		navigationMacros.navigateControlPanelPage("Blogs");
		cPBlogsPortletActions.assertTextEquals("BLOGS_ENTRY_LINK_TITLE",
			"Blogs Entry Title");
		cPBlogsPortletActions.click("BLOGS_ENTRY_LINK_ACTIONS", "Actions");
		cPBlogsPortletActions.click("BLOGS_ENTRY_LINK_ACTIONS_EDIT", "Edit");
		cPBlogsEntryEditActions.type("CONTENT_FIELD_TITLE",
			"Blogs Entry Title Edit");
		cPBlogsEntryEditActions.click("CONTENT_LINK_SAVE", "Publish");
		cPBlogsEntryEditActions.assertTextEquals("PORTLET_TEXT_SUCCESS",
			"Your request completed successfully.");
		cPBlogsPortletActions.assertTextEquals("BLOGS_ENTRY_LINK_TITLE",
			"Blogs Entry Title Edit");
		cPBlogsPortletActions.click("BLOGS_ENTRY_LINK_TITLE",
			"Blogs Entry Title Edit");
		cPBlogsEntryViewActions.assertTextNotEquals("BLOGS_ENTRY_TEXT_TITLE",
			"Blogs Entry Title");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_TITLE",
			"Blogs Entry Title Edit");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_CONTENT",
			"Blogs Entry Content");
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