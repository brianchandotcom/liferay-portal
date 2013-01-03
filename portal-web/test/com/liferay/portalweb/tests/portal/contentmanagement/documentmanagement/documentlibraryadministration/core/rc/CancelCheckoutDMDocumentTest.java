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
public class CancelCheckoutDMDocumentTest extends BaseTestCase {
	@Override
	public void setUp() throws Exception {
		selenium = SeleniumUtil.getSelenium();

		CPDocumentsAndMediaDocumentMacros cPDocumentsAndMediaDocumentMacros = new CPDocumentsAndMediaDocumentMacros(selenium);
		SignInUserMacros signInUserMacros = new SignInUserMacros(selenium);

		signInUserMacros.signIn("test@liferay.com", "test");
		cPDocumentsAndMediaDocumentMacros.add("DM Document Title",
			"Document_1.doc");
		cPDocumentsAndMediaDocumentMacros.checkout("DM Document Title");
		cPDocumentsAndMediaDocumentMacros.editCheckout("DM Document Title",
			"DM Document Title Edit");
	}

	public void test() throws Exception {
		CPDocumentsAndMediaDocumentActions cPDocumentsAndMediaDocumentActions = new CPDocumentsAndMediaDocumentActions(selenium);
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.assertElementPresent("DOCUMENT_IMAGE_LOCK",
			null);
		cPDocumentsAndMediaHomeActions.assertTextEquals("DOCUMENT_NAME",
			"DM Document Title (Draft)");
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_ACTIONS", null);
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_ACTIONS_CANCEL_CHECKOUT",
			"Cancel Checkout");
		cPDocumentsAndMediaHomeActions.assertTextEquals("HEADER_SUCCESS_MESSAGE",
			"Your request completed successfully.");
		cPDocumentsAndMediaHomeActions.assertElementNotPresent("DOCUMENT_IMAGE_LOCK",
			null);
		cPDocumentsAndMediaHomeActions.assertTextEquals("DOCUMENT_NAME",
			"DM Document Title");
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_NAME",
			"DM Document Title");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("VERSION_INFO_WORKFLOW_STATUS",
			"Status: Approved");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("VERSION_HISTORY_VERSION_1",
			"1.0");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("VERSION_HISTORY_STATUS_1",
			"Approved");
		cPDocumentsAndMediaDocumentActions.assertElementNotPresent("VERSION_HISTORY_VERSION_2",
			null);
		cPDocumentsAndMediaDocumentActions.assertElementNotPresent("VERSION_HISTORY_STATUS_2",
			null);
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