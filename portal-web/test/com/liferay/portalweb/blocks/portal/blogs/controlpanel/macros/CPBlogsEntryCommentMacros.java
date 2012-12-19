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

package com.liferay.portalweb.blocks.portal.blogs.controlpanel.macros;

import com.liferay.portalweb.blocks.base.macros.BaseMacros;
import com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.entry.CPBlogsEntryViewActions;
import com.liferay.portalweb.blocks.portal.blogs.controlpanel.actions.home.CPBlogsHomeActions;
import com.liferay.portalweb.blocks.portal.home.page.macros.GotoMacros;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPBlogsEntryCommentMacros extends BaseMacros {
	public CPBlogsEntryCommentMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void addBlogsEntryComment1(String blogsEntry,
		String blogsEntryComment) throws Exception {
		CPBlogsEntryViewActions cPBlogsEntryViewActions = new CPBlogsEntryViewActions(selenium);
		CPBlogsHomeActions cPBlogsHomeActions = new CPBlogsHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Blogs");
		cPBlogsHomeActions.click("BLOGS_ENTRY_LINK_TITLE", blogsEntry);
		cPBlogsEntryViewActions.assertTextEquals("PORTLET_TEXT_COMMENT_MESSAGE",
			"No comments yet.");
		cPBlogsEntryViewActions.click("BLOGS_ENTRY_LINK_ADD_COMMENT_1", null);
		cPBlogsEntryViewActions.type("BLOGS_COMMENT_ADD_FIELD_BODY",
			blogsEntryComment);
		cPBlogsEntryViewActions.click("BLOGS_COMMENT_ADD_LINK_SAVE", null);
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_COMMENT_ADD_TEXT_SUCCESS",
			null);
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_COMMENT_TEXT_BODY",
			blogsEntryComment);
		cPBlogsEntryViewActions.assertTextNotPresent("", "No comments yet.");
	}

	public void addBlogsEntryComment2(String blogsEntry,
		String blogsEntryComment) throws Exception {
		CPBlogsEntryViewActions cPBlogsEntryViewActions = new CPBlogsEntryViewActions(selenium);
		CPBlogsHomeActions cPBlogsHomeActions = new CPBlogsHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Blogs");
		cPBlogsHomeActions.click("BLOGS_ENTRY_LINK_TITLE", blogsEntry);
		cPBlogsEntryViewActions.click("BLOGS_ENTRY_LINK_ADD_COMMENT_2", null);
		cPBlogsEntryViewActions.type("BLOGS_COMMENT_ADD_FIELD_BODY",
			blogsEntryComment);
		cPBlogsEntryViewActions.click("BLOGS_COMMENT_ADD_LINK_SAVE", null);
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_COMMENT_ADD_TEXT_SUCCESS",
			null);
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_COMMENT_TEXT_BODY",
			blogsEntryComment);
		cPBlogsEntryViewActions.assertTextNotPresent("", "No comments yet.");
	}
}