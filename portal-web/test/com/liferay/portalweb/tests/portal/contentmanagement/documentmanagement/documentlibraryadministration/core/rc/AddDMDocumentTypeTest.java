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

package com.liferay.portalweb.tests.portal.contentmanagement.documentmanagement.documentlibraryadministration.core.rc;

import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.home.rc.CPDocumentsAndMediaHomeActions;
import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.macros.rc.CPDocumentsAndMediaDocumentTypeMacros;
import com.liferay.portalweb.blocks.portal.home.page.macros.rc.GotoMacros;
import com.liferay.portalweb.blocks.portal.recyclebin.controlpanel.macros.rc.CPRecycleBinMacros;
import com.liferay.portalweb.blocks.portal.signin.page.macros.rc.SignInUserMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMDocumentTypeTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		signInUserMacros.signIn("test@liferay.com", "test");
	}

	public void test() throws Exception {
		CPDocumentsAndMediaDocumentTypeMacros cPDocumentsAndMediaDocumentTypeMacros =
			new CPDocumentsAndMediaDocumentTypeMacros(selenium);
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		cPDocumentsAndMediaDocumentTypeMacros.add("DM DocumentType Name");
		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.click("TOOLBAR_ADD_BUTTON", "Add");
		cPDocumentsAndMediaHomeActions.assertTextEquals("//a[contains(.,'DM DocumentType Name') and @role='menuitem']",
			"DM DocumentType Name");
	}

	@Override
	public void tearDown() throws Exception {
		CPDocumentsAndMediaDocumentTypeMacros cPDocumentsAndMediaDocumentTypeMacros =
			new CPDocumentsAndMediaDocumentTypeMacros(selenium);
		CPRecycleBinMacros cPRecycleBinMacros = new CPRecycleBinMacros(selenium);
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		cPDocumentsAndMediaDocumentTypeMacros.tearDown();
		cPRecycleBinMacros.empty();
		signInUserMacros.signOut();
	}
}