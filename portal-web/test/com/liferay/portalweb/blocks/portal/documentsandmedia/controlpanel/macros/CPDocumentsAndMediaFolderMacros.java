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

package com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.macros;

import com.liferay.portalweb.blocks.base.macros.BaseMacros;
import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.addfolder.CPDocumentsAndMediaAddFolderActions;
import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.home.CPDocumentsAndMediaHomeActions;
import com.liferay.portalweb.blocks.portal.home.page.macros.GotoMacros;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaFolderMacros extends BaseMacros {
	public CPDocumentsAndMediaFolderMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void add(String name) throws Exception {
		CPDocumentsAndMediaAddFolderActions cPDocumentsAndMediaAddFolderActions = new CPDocumentsAndMediaAddFolderActions(selenium);
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.click("TOOLBAR_ADD_BUTTON", "Add");
		cPDocumentsAndMediaHomeActions.click("ADD_FOLDER", "Folder");
		cPDocumentsAndMediaAddFolderActions.type("CONTENT_NAME", name);
		cPDocumentsAndMediaAddFolderActions.click("BUTTON_SAVE", "Save");
		cPDocumentsAndMediaHomeActions.assertTextEquals("HEADER_SUCCESS_MESSAGE",
			"Your request completed successfully.");
		cPDocumentsAndMediaHomeActions.assertTextEquals("FOLDER_NAME", name);
	}

	public void delete(String name) throws Exception {
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.assertTextEquals("FOLDER_NAME", title);
		cPDocumentsAndMediaHomeActions.click("FOLDER_ACTIONS", null);
		cPDocumentsAndMediaHomeActions.click("FOLDER_ACTIONS_MOVE_TO_THE_RECYCLE_BIN",
			"Move to the Recycle Bin");
		cPDocumentsAndMediaHomeActions.assertTextEquals("HEADER_SUCCESS_MESSAGE_UNDO",
			"The selected item was moved to the Recycle Bin. Undo");
		cPDocumentsAndMediaHomeActions.assertTextNotPresent("", title);
	}

	public void tearDown() throws Exception {
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");

		while (cPDocumentsAndMediaHomeActions.isElementPresent("FOLDER_NAME", "")) {
			cPDocumentsAndMediaHomeActions.check("TOOLBAR_ALL_ROWS", null);
			cPDocumentsAndMediaHomeActions.click("TOOLBAR_ACTIONS_BUTTON",
				"Actions");
			cPDocumentsAndMediaHomeActions.click("ALL_ACTIONS_MOVE_TO_THE_RECYCLE_BIN",
				"Move to the Recycle Bin");
		}
	}
}