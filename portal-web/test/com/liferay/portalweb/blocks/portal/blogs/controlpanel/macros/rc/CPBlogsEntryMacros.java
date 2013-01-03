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

package com.liferay.portalweb.blocks.portal.blogs.controlpanel.macros.rc;

import com.liferay.portalweb.blocks.base.macros.rc.BaseMacros;
import com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.addentry.rc.CPBlogsAddEntryActions;
import com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.entry.rc.CPBlogsEntryViewActions;
import com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.entryedit.rc.CPBlogsEntryEditActions;
import com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.home.rc.CPBlogsHomeActions;
import com.liferay.portalweb.blocks.portal.home.page.macros.rc.GotoMacros;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPBlogsEntryMacros extends BaseMacros {
	public CPBlogsEntryMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void add(String title, String content) throws Exception {
		CPBlogsAddEntryActions cPBlogsAddEntryActions = new CPBlogsAddEntryActions(selenium);
		CPBlogsEntryViewActions cPBlogsEntryViewActions = new CPBlogsEntryViewActions(selenium);
		CPBlogsHomeActions cPBlogsHomeActions = new CPBlogsHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Blogs");
		cPBlogsHomeActions.click("PORTLET_LINK_ADD", "Add");
		cPBlogsAddEntryActions.type("CONTENT_FIELD_TITLE", title);
		cPBlogsAddEntryActions.type("CONTENT_FIELD_CONTENT", content);
		cPBlogsAddEntryActions.click("CONTENT_LINK_SAVE", "Publish");
		cPBlogsAddEntryActions.assertTextEquals("PORTLET_TEXT_SUCCESS",
			"Your request completed successfully.");
		cPBlogsHomeActions.click("BLOGS_ENTRY_LINK_TITLE_1", title);
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_TITLE", title);
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_CONTENT",
			content);
	}

	public void saveAsDraft(String title, String content)
		throws Exception {
		CPBlogsAddEntryActions cPBlogsAddEntryActions = new CPBlogsAddEntryActions(selenium);
		CPBlogsEntryEditActions cPBlogsEntryEditActions = new CPBlogsEntryEditActions(selenium);
		CPBlogsEntryViewActions cPBlogsEntryViewActions = new CPBlogsEntryViewActions(selenium);
		CPBlogsHomeActions cPBlogsHomeActions = new CPBlogsHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Blogs");
		cPBlogsHomeActions.click("PORTLET_LINK_ADD", "Add");
		cPBlogsAddEntryActions.type("CONTENT_FIELD_TITLE", title);
		cPBlogsAddEntryActions.type("CONTENT_FIELD_CONTENT", content);
		cPBlogsAddEntryActions.click("CONTENT_LINK_DRAFT", "Save as Draft");
		cPBlogsAddEntryActions.assertTextEquals("PORTLET_TEXT_SUCCESS",
			"Your request completed successfully.");
		cPBlogsEntryEditActions.assertTextEquals("CONTENT_TEXT_STATUS", "Draft");
		gotoMacros.controlPanelPortlet("Blogs");
		cPBlogsHomeActions.assertTextEquals("BLOGS_ENTRY_LINK_TITLE", title);
		cPBlogsHomeActions.assertTextEquals("BLOGS_ENTRY_LINK_STATUS", "Draft");
		cPBlogsHomeActions.click("BLOGS_ENTRY_LINK_TITLE", title);
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_DRAFT",
			"Draft");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_TITLE", title);
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_CONTENT",
			content);
	}

	public void tearDown() throws Exception {
		CPBlogsHomeActions cPBlogsHomeActions = new CPBlogsHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Blogs");

		while (cPBlogsHomeActions.isElementPresent(
					"BLOGS_ENTRY_LINK_CHECKBOX_1", "")) {
			cPBlogsHomeActions.check("BLOGS_ENTRY_LINK_CHECKBOX_ALL", null);
			cPBlogsHomeActions.click("PORTLET_LINK_DELETE",
				"Move to the Recycle Bin");
		}
	}
}