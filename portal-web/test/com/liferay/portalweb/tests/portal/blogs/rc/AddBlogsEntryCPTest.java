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

import com.liferay.portalweb.blocks.portal.controlpanel.blogs.macros.CPBlogsEntryMacros;
import com.liferay.portalweb.blocks.portal.controlpanel.recyclebin.macros.CPRecycleBinMacros;
import com.liferay.portalweb.blocks.portal.portlet.signin.macros.PortletSignInUserMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class AddBlogsEntryCPTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		PortletSignInUserMacros portletSignInUserMacros = new PortletSignInUserMacros(selenium);

		portletSignInUserMacros.signIn("test@liferay.com", "test");
	}

	public void test() throws Exception {
		CPBlogsEntryMacros cPBlogsEntryMacros = new CPBlogsEntryMacros(selenium);

		cPBlogsEntryMacros.addBlogsEntry("Blogs Entry Title",
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