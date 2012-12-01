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

import com.liferay.portalweb.blocks.portal.controlpanel.blogs.actions.addentry.CPBlogsAddEntryActions;
import com.liferay.portalweb.blocks.portal.controlpanel.blogs.actions.entry.CPBlogsEntryViewActions;
import com.liferay.portalweb.blocks.portal.controlpanel.blogs.actions.home.CPBlogsHomeActions;
import com.liferay.portalweb.blocks.portal.controlpanel.blogs.macros.CPBlogsEntryMacros;
import com.liferay.portalweb.blocks.portal.controlpanel.recyclebin.macros.CPRecycleBinMacros;
import com.liferay.portalweb.blocks.portal.home.macros.GotoMacros;
import com.liferay.portalweb.blocks.portal.portlet.signin.macros.PortletSignInUserMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class AddBlogsEntryTrackbackCPTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		CPBlogsEntryMacros cPBlogsEntryMacros = new CPBlogsEntryMacros(selenium);
		PortletSignInUserMacros portletSignInUserMacros = new PortletSignInUserMacros(selenium);

		portletSignInUserMacros.signIn("test@liferay.com", "test");
		cPBlogsEntryMacros.add("Blogs Entry1 Title", "Blogs Entry1 Content");
	}

	public void test() throws Exception {
		CPBlogsAddEntryActions cPBlogsAddEntryActions = new CPBlogsAddEntryActions(selenium);
		CPBlogsEntryViewActions cPBlogsEntryViewActions = new CPBlogsEntryViewActions(selenium);
		CPBlogsHomeActions cPBlogsHomeActions = new CPBlogsHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Blogs");
		cPBlogsHomeActions.click("BLOGS_ENTRY_LINK_TITLE_1",
			"Blogs Entry1 Title");
		cPBlogsEntryViewActions.copy("BLOGS_ENTRY_FIELD_TRACKBACK_URL", null);
		gotoMacros.controlPanelPortlet("Blogs");
		cPBlogsHomeActions.click("PORTLET_LINK_ADD", null);
		cPBlogsAddEntryActions.type("CONTENT_FIELD_TITLE", "Blogs Entry2 Title");
		cPBlogsAddEntryActions.type("CONTENT_FIELD_CONTENT",
			"Blogs Entry2 Content");
		cPBlogsAddEntryActions.paste("CONTENT_FIELD_TRACKBACKS", null);
		cPBlogsAddEntryActions.click("CONTENT_LINK_SAVE", "Publish");
		cPBlogsHomeActions.assertTextEquals("PORTLET_TEXT_SUCCESS",
			"Your request completed successfully.");
		cPBlogsHomeActions.click("BLOGS_ENTRY_LINK_TITLE_1",
			"Blogs Entry2 Title");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_TITLE",
			"Blogs Entry2 Title");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_CONTENT",
			"Blogs Entry2 Content");
		gotoMacros.controlPanelPortlet("Blogs");
		cPBlogsHomeActions.click("BLOGS_ENTRY_LINK_TITLE_2",
			"Blogs Entry1 Title");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_TITLE",
			"Blogs Entry1 Title");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_CONTENT",
			"Blogs Entry1 Content");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_COMMENT_TEXT_BODY_1",
			"[...] Blogs Entry2 Content [...] Read More");
		cPBlogsEntryViewActions.click("BLOGS_COMMENT_LINK_READ_MORE",
			"Read More");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_TITLE",
			"Blogs Entry2 Title");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_CONTENT",
			"Blogs Entry2 Content");
	}

	@Override
	public void tearDown() throws Exception {
		CPBlogsEntryMacros cPBlogsEntryMacros = new CPBlogsEntryMacros(selenium);
		CPRecycleBinMacros cPRecycleBinMacros = new CPRecycleBinMacros(selenium);
		PortletSignInUserMacros portletSignInUserMacros = new PortletSignInUserMacros(selenium);

		cPBlogsEntryMacros.tearDown();
		cPRecycleBinMacros.empty();
		portletSignInUserMacros.signOut();
	}
}