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

import com.liferay.portalweb.blocks.controlpanel.blogs.actions.CPBlogsEntryAddActions;
import com.liferay.portalweb.blocks.controlpanel.blogs.actions.CPBlogsEntryViewActions;
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
public class AddBlogsEntryTitle151CharactersTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		PortletSignInUserMacros portletSignInUserMacros = new PortletSignInUserMacros(selenium);

		portletSignInUserMacros.signIn("test@liferay.com", "test");
	}

	public void test() throws Exception {
		CPBlogsEntryAddActions cPBlogsEntryAddActions = new CPBlogsEntryAddActions(selenium);
		CPBlogsEntryViewActions cPBlogsEntryViewActions = new CPBlogsEntryViewActions(selenium);
		CPBlogsPortletActions cPBlogsPortletActions = new CPBlogsPortletActions(selenium);
		NavigationMacros navigationMacros = new NavigationMacros(selenium);

		navigationMacros.navigateControlPanelPage("Blogs");
		cPBlogsPortletActions.click("PORTLET_LINK_ADD", null);
		cPBlogsEntryAddActions.type("CONTENT_FIELD_TITLE",
			"|||||||||1|||||||||2|||||||||3|||||||||4|||||||||5|||||||||6|||||||||7|||||||||8|||||||||9||||||||10||||||||11||||||||12||||||||13||||||||14||||||||15X");
		cPBlogsEntryAddActions.type("CONTENT_FIELD_CONTENT",
			"Blogs Entry Content");
		cPBlogsEntryAddActions.click("CONTENT_LINK_SAVE", null);
		cPBlogsPortletActions.assertTextEquals("PORTLET_TEXT_SUCCESS", null);
		cPBlogsPortletActions.click("BLOGS_ENTRY_LINK_TITLE_1",
			"|||||||||1|||||||||2|||||||||3|||||||||4|||||||||5|||||||||6|||||||||7|||||||||8|||||||||9||||||||10||||||||11||||||||12||||||||13||||||||14||||||||15");
		cPBlogsEntryViewActions.click("BLOGS_ENTRY_TEXT_TITLE",
			"|||||||||1|||||||||2|||||||||3|||||||||4|||||||||5|||||||||6|||||||||7|||||||||8|||||||||9||||||||10||||||||11||||||||12||||||||13||||||||14||||||||15");
		cPBlogsEntryViewActions.click("BLOGS_ENTRY_TEXT_CONTENT",
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