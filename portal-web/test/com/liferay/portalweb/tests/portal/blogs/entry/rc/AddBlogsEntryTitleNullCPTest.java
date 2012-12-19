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

import com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.addentry.CPBlogsAddEntryActions;
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
public class AddBlogsEntryTitleNullCPTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		signInUserMacros.signIn("test@liferay.com", "test");
	}

	public void test() throws Exception {
		CPBlogsAddEntryActions cPBlogsAddEntryActions = new CPBlogsAddEntryActions(selenium);
		CPBlogsHomeActions cPBlogsHomeActions = new CPBlogsHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Blogs");
		cPBlogsHomeActions.click("PORTLET_LINK_ADD", "Add");
		cPBlogsAddEntryActions.type("CONTENT_FIELD_TITLE", "");
		cPBlogsAddEntryActions.type("CONTENT_FIELD_CONTENT",
			"Blogs Entry Content");
		cPBlogsAddEntryActions.click("CONTENT_LINK_SAVE", "Publish");
		cPBlogsAddEntryActions.assertTextEquals("CONTENT_TEXT_ERROR_MESSAGE_1",
			"Your request failed to complete.");
		cPBlogsAddEntryActions.assertTextEquals("CONTENT_TEXT_ERROR_MESSAGE_2",
			"Please enter a valid title.");
		gotoMacros.controlPanelPortlet("Blogs");
		cPBlogsHomeActions.assertTextNotPresent("", "Blogs Entry Title");
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