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

package com.liferay.portalweb.blocks.controlpanel.blogs.macros;

import com.liferay.portalweb.blocks.base.macros.BaseMacros;
import com.liferay.portalweb.blocks.controlpanel.blogs.actions.CPBlogsEntryAddActions;
import com.liferay.portalweb.blocks.controlpanel.blogs.actions.CPBlogsEntryEditActions;
import com.liferay.portalweb.blocks.controlpanel.blogs.actions.CPBlogsEntryViewActions;
import com.liferay.portalweb.blocks.controlpanel.blogs.actions.CPBlogsPortletActions;
import com.liferay.portalweb.blocks.portal.macros.NavigationMacros;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPBlogsEntryMacros extends BaseMacros {
	public CPBlogsEntryMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void addBlogsEntry(String title, String content)
		throws Exception {
		CPBlogsEntryAddActions cPBlogsEntryAddActions = new CPBlogsEntryAddActions(selenium);
		CPBlogsEntryViewActions cPBlogsEntryViewActions = new CPBlogsEntryViewActions(selenium);
		CPBlogsPortletActions cPBlogsPortletActions = new CPBlogsPortletActions(selenium);
		NavigationMacros navigationMacros = new NavigationMacros(selenium);

		navigationMacros.navigateControlPanelPage("Blogs");
		cPBlogsPortletActions.click("PORTLET_LINK_ADD", null);
		cPBlogsEntryAddActions.type("CONTENT_FIELD_TITLE", title);
		cPBlogsEntryAddActions.type("CONTENT_FIELD_CONTENT", content);
		cPBlogsEntryAddActions.click("CONTENT_LINK_SAVE", null);
		cPBlogsEntryAddActions.assertTextEquals("PORTLET_TEXT_SUCCESS", null);
		cPBlogsPortletActions.click("BLOGS_ENTRY_LINK_TITLE_1", title);
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_TITLE", title);
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_CONTENT",
			content);
	}

	public void saveAsDraftBlogsEntry(String title, String content)
		throws Exception {
		CPBlogsEntryAddActions cPBlogsEntryAddActions = new CPBlogsEntryAddActions(selenium);
		CPBlogsEntryEditActions cPBlogsEntryEditActions = new CPBlogsEntryEditActions(selenium);
		CPBlogsEntryViewActions cPBlogsEntryViewActions = new CPBlogsEntryViewActions(selenium);
		CPBlogsPortletActions cPBlogsPortletActions = new CPBlogsPortletActions(selenium);
		NavigationMacros navigationMacros = new NavigationMacros(selenium);

		navigationMacros.navigateControlPanelPage("Blogs");
		cPBlogsPortletActions.click("PORTLET_LINK_ADD", null);
		cPBlogsEntryAddActions.type("CONTENT_FIELD_TITLE", title);
		cPBlogsEntryAddActions.type("CONTENT_FIELD_CONTENT", content);
		cPBlogsEntryAddActions.click("CONTENT_LINK_DRAFT", null);
		cPBlogsEntryAddActions.assertTextEquals("PORTLET_TEXT_SUCCESS", null);
		cPBlogsEntryEditActions.assertTextEquals("CONTENT_TEXT_STATUS", "Draft");
		navigationMacros.navigateControlPanelPage("Blogs");
		cPBlogsPortletActions.assertTextEquals("BLOGS_ENTRY_LINK_TITLE", title);
		cPBlogsPortletActions.assertTextEquals("BLOGS_ENTRY_LINK_STATUS",
			"Draft");
		cPBlogsPortletActions.click("BLOGS_ENTRY_LINK_TITLE", title);
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_DRAFT",
			"Draft");
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_TITLE", title);
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_ENTRY_TEXT_CONTENT",
			content);
	}

	public void tearDownBlogsEntry() throws Exception {
		CPBlogsPortletActions cPBlogsPortletActions = new CPBlogsPortletActions(selenium);
		NavigationMacros navigationMacros = new NavigationMacros(selenium);

		navigationMacros.navigateControlPanelPage("Blogs");

		while (cPBlogsPortletActions.isElementPresent(
					"BLOGS_ENTRY_LINK_CHECKBOX_1", "")) {
			cPBlogsPortletActions.check("BLOGS_ENTRY_LINK_CHECKBOX_ALL", null);
			cPBlogsPortletActions.click("PORTLET_LINK_DELETE", null);
		}
	}
}