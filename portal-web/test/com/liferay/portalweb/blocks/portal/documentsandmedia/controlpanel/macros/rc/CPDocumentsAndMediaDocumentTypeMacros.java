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
import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.documenttypes.rc.CPDocumentsAndMediaAddDocumentTypeActions;
import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.documenttypes.rc.CPDocumentsAndMediaDocumentTypeHomeActions;
import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.home.rc.CPDocumentsAndMediaHomeActions;
import com.liferay.portalweb.blocks.portal.home.page.macros.rc.GotoMacros;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaDocumentTypeMacros extends BaseMacros {
	public CPDocumentsAndMediaDocumentTypeMacros(
		LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void add(String name) throws Exception {
		CPDocumentsAndMediaAddDocumentTypeActions cPDocumentsAndMediaAddDocumentTypeActions =
			new CPDocumentsAndMediaAddDocumentTypeActions(selenium);
		CPDocumentsAndMediaDocumentTypeHomeActions cPDocumentsAndMediaDocumentTypeHomeActions =
			new CPDocumentsAndMediaDocumentTypeHomeActions(selenium);
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.click("TOOLBAR_MANAGE_BUTTON", "Manage");
		cPDocumentsAndMediaHomeActions.click("MANAGE_DOCUMENT_TYPES",
			"Document Types");
		cPDocumentsAndMediaDocumentTypeHomeActions.selectWindow("PAGE_NAME", "");
		cPDocumentsAndMediaDocumentTypeHomeActions.click("BUTTON_ADD", "Add");
		cPDocumentsAndMediaAddDocumentTypeActions.type("CONTENT_NAME", name);
		cPDocumentsAndMediaAddDocumentTypeActions.dragAndDrop("METADATA_FIELD_TEXT",
			"Text", "METADATA_CANVAS_BUILDER", "");
		cPDocumentsAndMediaAddDocumentTypeActions.click("BUTTON_SAVE", "Save");
		cPDocumentsAndMediaDocumentTypeHomeActions.assertTextEquals("HEADER_SUCCESS_MESSAGE",
			"Your request completed successfully.");
		cPDocumentsAndMediaDocumentTypeHomeActions.assertTextPresent("", name);
		cPDocumentsAndMediaDocumentTypeHomeActions.selectWindow("TOP", "");
	}

	public void delete(String name) throws Exception {
		CPDocumentsAndMediaDocumentTypeHomeActions cPDocumentsAndMediaDocumentTypeHomeActions =
			new CPDocumentsAndMediaDocumentTypeHomeActions(selenium);
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.click("TOOLBAR_MANAGE_BUTTON", "Manage");
		cPDocumentsAndMediaHomeActions.click("MANAGE_DOCUMENT_TYPES",
			"Document Types");
		cPDocumentsAndMediaDocumentTypeHomeActions.selectWindow("PAGE_NAME", "");
		cPDocumentsAndMediaDocumentTypeHomeActions.type("SEARCH_FIELD", name);
		cPDocumentsAndMediaDocumentTypeHomeActions.click("SEARCH_BUTTON",
			"Search");
		cPDocumentsAndMediaDocumentTypeHomeActions.assertTextEquals("TABLE_NAME_1",
			name);
		cPDocumentsAndMediaDocumentTypeHomeActions.click("TABLE_ACTIONS_1",
			"Actions");
		cPDocumentsAndMediaDocumentTypeHomeActions.click("ACTIONS_DELETE",
			"Delete");
		cPDocumentsAndMediaDocumentTypeHomeActions.confirm("ACTIONS_DELETE_CONFIRMATION",
			"Are you sure you want to delete this? It will be deleted immediately.");
		cPDocumentsAndMediaDocumentTypeHomeActions.assertTextNotPresent("", name);
		cPDocumentsAndMediaDocumentTypeHomeActions.selectWindow("TOP", "");
	}

	public void tearDown() throws Exception {
		CPDocumentsAndMediaDocumentTypeHomeActions cPDocumentsAndMediaDocumentTypeHomeActions =
			new CPDocumentsAndMediaDocumentTypeHomeActions(selenium);
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.click("TOOLBAR_MANAGE_BUTTON", "Manage");
		cPDocumentsAndMediaHomeActions.click("MANAGE_DOCUMENT_TYPES",
			"Document Types");
		cPDocumentsAndMediaDocumentTypeHomeActions.selectWindow("PAGE_NAME", "");
		cPDocumentsAndMediaDocumentTypeHomeActions.type("SEARCH_FIELD", "DM");
		cPDocumentsAndMediaDocumentTypeHomeActions.click("SEARCH_BUTTON",
			"Search");

		while (cPDocumentsAndMediaDocumentTypeHomeActions.isElementPresent(
					"TABLE_ACTIONS_1", "")) {
			cPDocumentsAndMediaDocumentTypeHomeActions.click("TABLE_ACTIONS_1",
				"Actions");
			cPDocumentsAndMediaDocumentTypeHomeActions.click("ACTIONS_DELETE",
				"Delete");
			cPDocumentsAndMediaDocumentTypeHomeActions.confirm("ACTIONS_DELETE_CONFIRMATION",
				"Are you sure you want to delete this? It will be deleted immediately.");
		}

		cPDocumentsAndMediaDocumentTypeHomeActions.selectWindow("TOP", "");
	}
}