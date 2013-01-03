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

import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.document.rc.CPDocumentsAndMediaDocumentActions;
import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.home.rc.CPDocumentsAndMediaHomeActions;
import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.macros.rc.CPDocumentsAndMediaDocumentMacros;
import com.liferay.portalweb.blocks.portal.home.page.macros.rc.GotoMacros;
import com.liferay.portalweb.blocks.portal.recyclebin.controlpanel.macros.rc.CPRecycleBinMacros;
import com.liferay.portalweb.blocks.portal.signin.page.macros.rc.SignInUserMacros;
import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.SeleniumUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class CheckoutDMDocumentTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		CPDocumentsAndMediaDocumentMacros cPDocumentsAndMediaDocumentMacros = new CPDocumentsAndMediaDocumentMacros(selenium);
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		signInUserMacros.signIn("test@liferay.com", "test");
		cPDocumentsAndMediaDocumentMacros.add("DM Document Title",
			"Document_1.doc");
	}

	public void test() throws Exception {
		CPDocumentsAndMediaDocumentActions cPDocumentsAndMediaDocumentActions = new CPDocumentsAndMediaDocumentActions(selenium);
		CPDocumentsAndMediaDocumentMacros cPDocumentsAndMediaDocumentMacros = new CPDocumentsAndMediaDocumentMacros(selenium);
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		cPDocumentsAndMediaDocumentMacros.checkout("DM Document Title");
		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_NAME",
			"DM Document Title (Draft)");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("DOCUMENT_LOCK_MESSAGE",
			"You now have a lock on this document. No one else can edit this document until you unlock it. This lock will automatically expire in 1 day.");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("TOOLBAR_CANCEL_CHECKOUT",
			"Cancel Checkout");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("TOOLBAR_CHECKIN",
			"Checkin");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("VERSION_INFO_WORKFLOW_STATUS",
			"Status: Draft");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("VERSION_HISTORY_VERSION_1",
			"PWC");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("VERSION_HISTORY_VERSION_2",
			"1.0");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("VERSION_HISTORY_STATUS_1",
			"Draft");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("VERSION_HISTORY_STATUS_2",
			"Approved");
	}

	@Override
	public void tearDown() throws Exception {
		CPDocumentsAndMediaDocumentMacros cPDocumentsAndMediaDocumentMacros = new CPDocumentsAndMediaDocumentMacros(selenium);
		CPRecycleBinMacros cPRecycleBinMacros = new CPRecycleBinMacros(selenium);
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		cPDocumentsAndMediaDocumentMacros.tearDown();
		cPRecycleBinMacros.empty();
		signInUserMacros.signOut();
	}
}