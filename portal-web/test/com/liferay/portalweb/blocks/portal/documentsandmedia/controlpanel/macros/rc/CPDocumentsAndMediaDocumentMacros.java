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

package com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.macros.rc;

import com.liferay.portalweb.blocks.base.macros.rc.BaseMacros;
import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.addfileentry.rc.CPDocumentsAndMediaAddFileEntryActions;
import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.documentedit.rc.CPDocumentsAndMediaDocumentEditActions;
import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.home.rc.CPDocumentsAndMediaHomeActions;
import com.liferay.portalweb.blocks.portal.home.page.macros.rc.GotoMacros;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaDocumentMacros extends BaseMacros {
	public CPDocumentsAndMediaDocumentMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void add(String title, String file) throws Exception {
		CPDocumentsAndMediaAddFileEntryActions cPDocumentsAndMediaAddFileEntryActions =
			new CPDocumentsAndMediaAddFileEntryActions(selenium);
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.click("TOOLBAR_ADD_BUTTON", "Add");
		cPDocumentsAndMediaHomeActions.click("ADD_BASIC_DOCUMENT",
			"Basic Document");
		cPDocumentsAndMediaAddFileEntryActions.type("CONTENT_FILE", file);
		cPDocumentsAndMediaAddFileEntryActions.type("CONTENT_TITLE", title);
		cPDocumentsAndMediaAddFileEntryActions.click("BUTTON_PUBLISH", "Publish");
		cPDocumentsAndMediaHomeActions.assertTextEquals("HEADER_SUCCESS_MESSAGE",
			"Your request completed successfully.");
		cPDocumentsAndMediaHomeActions.assertTextEquals("DOCUMENT_NAME", title);
	}

	public void checkin(String oldTitle, String newTitle)
		throws Exception {
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.assertTextEquals("DOCUMENT_NAME",
			"" + oldTitle + " (Draft)");
		cPDocumentsAndMediaHomeActions.assertElementPresent("DOCUMENT_IMAGE_LOCK",
			null);
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_ACTIONS", null);
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_ACTIONS_CHECKIN",
			"Checkin");
		cPDocumentsAndMediaHomeActions.assertTextEquals("DOCUMENT_NAME",
			newTitle);
		cPDocumentsAndMediaHomeActions.assertElementNotPresent("DOCUMENT_IMAGE_LOCK",
			null);
	}

	public void checkout(String title) throws Exception {
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.assertTextEquals("DOCUMENT_NAME", title);
		cPDocumentsAndMediaHomeActions.assertElementNotPresent("DOCUMENT_IMAGE_LOCK",
			null);
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_ACTIONS", null);
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_ACTIONS_CHECKOUT",
			"Checkout");
		cPDocumentsAndMediaHomeActions.assertTextEquals("DOCUMENT_NAME",
			"" + title + " (Draft)");
		cPDocumentsAndMediaHomeActions.assertElementPresent("DOCUMENT_IMAGE_LOCK",
			null);
	}

	public void delete(String title) throws Exception {
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.assertTextEquals("DOCUMENT_NAME", title);
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_ACTIONS", null);
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_ACTIONS_MOVE_TO_THE_RECYCLE_BIN",
			"Move to the Recycle Bin");
		cPDocumentsAndMediaHomeActions.assertTextEquals("HEADER_SUCCESS_MESSAGE_UNDO",
			"The selected item was moved to the Recycle Bin. Undo");
		cPDocumentsAndMediaHomeActions.assertTextNotPresent("", title);
	}

	public void edit(String oldTitle, String newTitle)
		throws Exception {
		CPDocumentsAndMediaDocumentEditActions cPDocumentsAndMediaDocumentEditActions =
			new CPDocumentsAndMediaDocumentEditActions(selenium);
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.assertTextEquals("DOCUMENT_NAME",
			oldTitle);
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_ACTIONS", null);
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_ACTIONS_EDIT", "Edit");
		cPDocumentsAndMediaDocumentEditActions.type("CONTENT_TITLE", newTitle);
		cPDocumentsAndMediaDocumentEditActions.click("BUTTON_PUBLISH", "Publish");
		cPDocumentsAndMediaHomeActions.assertTextEquals("HEADER_SUCCESS_MESSAGE",
			"Your request completed successfully.");
		cPDocumentsAndMediaHomeActions.assertTextEquals("DOCUMENT_NAME",
			newTitle);
	}

	public void editCheckout(String oldTitle, String newTitle)
		throws Exception {
		CPDocumentsAndMediaDocumentEditActions cPDocumentsAndMediaDocumentEditActions =
			new CPDocumentsAndMediaDocumentEditActions(selenium);
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.assertTextEquals("DOCUMENT_NAME",
			"" + oldTitle + " (Draft)");
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_ACTIONS", null);
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_ACTIONS_EDIT", "Edit");
		cPDocumentsAndMediaDocumentEditActions.type("CONTENT_TITLE", newTitle);
		cPDocumentsAndMediaDocumentEditActions.click("BUTTON_SAVE", "Save");
		cPDocumentsAndMediaHomeActions.assertTextEquals("HEADER_SUCCESS_MESSAGE",
			"Your request completed successfully.");
		cPDocumentsAndMediaHomeActions.assertTextEquals("DOCUMENT_NAME",
			"" + oldTitle + " (Draft)");
	}

	public void tearDown() throws Exception {
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");

		while (cPDocumentsAndMediaHomeActions.isElementPresent(
					"DOCUMENT_NAME", "")) {
			cPDocumentsAndMediaHomeActions.check("TOOLBAR_ALL_ROWS", null);
			cPDocumentsAndMediaHomeActions.click("TOOLBAR_ACTIONS_BUTTON",
				"Actions");
			cPDocumentsAndMediaHomeActions.click("ALL_ACTIONS_MOVE_TO_THE_RECYCLE_BIN",
				"Move to the Recycle Bin");
		}
	}
}