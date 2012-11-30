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

import com.liferay.portalweb.blocks.portal.controlpanel.blogs.actions.addentry.CPBlogsEntryAddActions;
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
public class AddBlogsEntryContentNullCPTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		PortletSignInUserMacros portletSignInUserMacros = new PortletSignInUserMacros(selenium);

		portletSignInUserMacros.signIn("test@liferay.com", "test");
	}

	public void test() throws Exception {
		CPBlogsEntryAddActions cPBlogsEntryAddActions = new CPBlogsEntryAddActions(selenium);
		CPBlogsPortletActions cPBlogsPortletActions = new CPBlogsPortletActions(selenium);
		NavigationMacros navigationMacros = new NavigationMacros(selenium);

		navigationMacros.navigateControlPanelPage("Blogs");
		cPBlogsPortletActions.click("PORTLET_LINK_ADD", "Add");
		cPBlogsEntryAddActions.type("CONTENT_FIELD_TITLE", "Blogs Entry Title");
		cPBlogsEntryAddActions.type("CONTENT_FIELD_CONTENT", "");
		cPBlogsEntryAddActions.click("CONTENT_LINK_SAVE", "Publish");
		cPBlogsEntryAddActions.assertTextEquals("CONTENT_TEXT_ERROR_MESSAGE_1",
			"Your request failed to complete.");
		cPBlogsEntryAddActions.assertTextEquals("CONTENT_TEXT_ERROR_MESSAGE_2",
			"Please enter valid content.");
		navigationMacros.navigateControlPanelPage("Blogs");
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