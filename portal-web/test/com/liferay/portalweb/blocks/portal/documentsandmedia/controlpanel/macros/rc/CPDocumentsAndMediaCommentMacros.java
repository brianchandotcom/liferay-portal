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
import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.document.rc.CPDocumentsAndMediaDocumentActions;
import com.liferay.portalweb.blocks.portal.documentsandmedia.controlpanel.actions.home.rc.CPDocumentsAndMediaHomeActions;
import com.liferay.portalweb.blocks.portal.home.page.macros.rc.GotoMacros;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPDocumentsAndMediaCommentMacros extends BaseMacros {
	public CPDocumentsAndMediaCommentMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void add(String document, String documentComment)
		throws Exception {
		CPDocumentsAndMediaDocumentActions cPDocumentsAndMediaDocumentActions = new CPDocumentsAndMediaDocumentActions(selenium);
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_NAME", document);
		cPDocumentsAndMediaDocumentActions.assertTextEquals("COMMENT_MESSAGE_INFO",
			"No comments yet.");
		cPDocumentsAndMediaDocumentActions.click("COMMENT_LINK_ADD",
			"Be the first.");
		cPDocumentsAndMediaDocumentActions.type("COMMENT_TEXTAREA",
			documentComment);
		cPDocumentsAndMediaDocumentActions.click("COMMENT_BUTTON_REPLY", "Reply");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("COMMENT_MESSAGE_SUCCESS",
			"Your request processed successfully.");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("COMMENT_VIEW_MESSAGE_1",
			documentComment);
		cPDocumentsAndMediaDocumentActions.assertTextNotPresent("",
			"No comments yet.");
	}

	public void add2(String document, String documentComment)
		throws Exception {
		CPDocumentsAndMediaDocumentActions cPDocumentsAndMediaDocumentActions = new CPDocumentsAndMediaDocumentActions(selenium);
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_NAME", document);
		cPDocumentsAndMediaDocumentActions.click("COMMENT_LINK_ADD",
			"Add Comment");
		cPDocumentsAndMediaDocumentActions.type("COMMENT_TEXTAREA",
			documentComment);
		cPDocumentsAndMediaDocumentActions.click("COMMENT_BUTTON_REPLY", "Reply");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("COMMENT_MESSAGE_SUCCESS",
			"Your request processed successfully.");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("COMMENT_VIEW_MESSAGE_2",
			documentComment);
	}

	public void add3(String document, String documentComment)
		throws Exception {
		CPDocumentsAndMediaDocumentActions cPDocumentsAndMediaDocumentActions = new CPDocumentsAndMediaDocumentActions(selenium);
		CPDocumentsAndMediaHomeActions cPDocumentsAndMediaHomeActions = new CPDocumentsAndMediaHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Documents and Media");
		cPDocumentsAndMediaHomeActions.click("DOCUMENT_NAME", document);
		cPDocumentsAndMediaDocumentActions.click("COMMENT_LINK_ADD",
			"Add Comment");
		cPDocumentsAndMediaDocumentActions.type("COMMENT_TEXTAREA",
			documentComment);
		cPDocumentsAndMediaDocumentActions.click("COMMENT_BUTTON_REPLY", "Reply");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("COMMENT_MESSAGE_SUCCESS",
			"Your request processed successfully.");
		cPDocumentsAndMediaDocumentActions.assertTextEquals("COMMENT_VIEW_MESSAGE_2",
			documentComment);
	}
}