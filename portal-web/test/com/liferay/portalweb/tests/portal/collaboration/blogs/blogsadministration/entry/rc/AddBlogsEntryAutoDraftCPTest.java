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

import com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.addentry.CPBlogsAddEntryActions;
import com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.entry.CPBlogsEntryViewActions;
import com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.home.CPBlogsHomeActions;
import com.liferay.portalweb.blocks.portal.blogs.controlpanel.macros.CPBlogsEntryMacros;
import com.liferay.portalweb.blocks.portal.home.page.macros.GotoMacros;
import com.liferay.portalweb.blocks.portal.recyclebin.controlpanel.macros.CPRecycleBinMacros;
import com.liferay.portalweb.blocks.portal.signin.page.macros.SignInUserMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class AddBlogsEntryAutoDraftCPTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		signInUserMacros.signIn("test@liferay.com", "test");
	}

	public void test() throws Exception {
		CPBlogsAddEntryActions cPBlogsAddEntryActions = new CPBlogsAddEntryActions(selenium);
		CPBlogsEntryViewActions cPBlogsEntryViewActions = new CPBlogsEntryViewActions(selenium);
		CPBlogsHomeActions cPBlogsHomeActions = new CPBlogsHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Blogs");
		cPBlogsHomeActions.click("PORTLET_LINK_ADD", "Add");
		cPBlogsAddEntryActions.type("CONTENT_FIELD_TITLE", "Blogs Entry Title");
		cPBlogsAddEntryActions.type("CONTENT_FIELD_CONTENT",
			"Blogs Entry Content");
		cPBlogsAddEntryActions.assertTextEquals("CONTENT_TEXT_SAVE_STATUS",
			"Draft saved");
		gotoMacros.controlPanelPortlet("Blogs");
		cPBlogsHomeActions.assertTextEquals("BLOGS_ENTRY_LINK_TITLE",
			"Blogs Entry Title");
		cPBlogsHomeActions.assertTextEquals("BLOGS_ENTRY_LINK_STATUS", "Draft");
		cPBlogsHomeActions.click("BLOGS_ENTRY_LINK_TITLE", "Blogs Entry Title");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_DRAFT",
			"Draft");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_TITLE",
			"Blogs Entry Title");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_CONTENT",
			"Blogs Entry Content");
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