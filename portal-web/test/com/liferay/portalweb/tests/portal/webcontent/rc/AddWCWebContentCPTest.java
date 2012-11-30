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

package com.liferay.portalweb.tests.portal.webcontent.rc;

import com.liferay.portalweb.blocks.portal.controlpanel.webcontent.actions.home.CPWebContentHomeActions;
import com.liferay.portalweb.blocks.portal.controlpanel.webcontent.actions.webcontent.CPWebContentEditWebContentActions;
import com.liferay.portalweb.blocks.portal.controlpanel.webcontent.macros.CPWebContentArticleMacros;
import com.liferay.portalweb.blocks.portal.portlet.signin.macros.PortletSignInUserMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class AddWCWebContentCPTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		PortletSignInUserMacros portletSignInUserMacros = new PortletSignInUserMacros(selenium);

		portletSignInUserMacros.signIn("test@liferay.com", "test");
	}

	public void test() throws Exception {
		CPWebContentArticleMacros cPWebContentArticleMacros = new CPWebContentArticleMacros(selenium);
		CPWebContentEditWebContentActions cPWebContentEditWebContentActions = new CPWebContentEditWebContentActions(selenium);
		CPWebContentHomeActions cPWebContentHomeActions = new CPWebContentHomeActions(selenium);

		cPWebContentArticleMacros.addCPWebContent("WC WebContent Title",
			"WC WebContent Content");
		cPWebContentHomeActions.click("ENTRIES_ARTICLE_1", "WC WebContent Title");
		cPWebContentEditWebContentActions.assertTextEquals("ARTICLE_TITLE",
			"WC WebContent Title");
		cPWebContentEditWebContentActions.assertTextEquals("ARTICLE_CONTENT",
			"WC WebContent Content");
	}

	@Override
	public void tearDown() throws Exception {
		CPWebContentArticleMacros cPWebContentArticleMacros = new CPWebContentArticleMacros(selenium);
		PortletSignInUserMacros portletSignInUserMacros = new PortletSignInUserMacros(selenium);

		cPWebContentArticleMacros.tearDownCPWebContent();
		portletSignInUserMacros.signOut();
	}
}