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

package com.liferay.portalweb.blocks.portal.controlpanel.blogs.macros;

import com.liferay.portalweb.blocks.base.macros.BaseMacros;
import com.liferay.portalweb.blocks.portal.controlpanel.blogs.actions.entry.CPBlogsEntryCommentAddActions;
import com.liferay.portalweb.blocks.portal.controlpanel.blogs.actions.entry.CPBlogsEntryViewActions;
import com.liferay.portalweb.blocks.portal.controlpanel.blogs.actions.home.CPBlogsPortletActions;
import com.liferay.portalweb.blocks.portal.home.macros.NavigationMacros;
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
		CPBlogsEntryCommentAddActions cPBlogsEntryCommentAddActions = new CPBlogsEntryCommentAddActions(selenium);
		CPBlogsEntryViewActions cPBlogsEntryViewActions = new CPBlogsEntryViewActions(selenium);
		CPBlogsPortletActions cPBlogsPortletActions = new CPBlogsPortletActions(selenium);
		NavigationMacros navigationMacros = new NavigationMacros(selenium);

		navigationMacros.navigateControlPanelPage("Blogs");
		cPBlogsPortletActions.click("BLOGS_ENTRY_LINK_TITLE", blogsEntry);
		cPBlogsEntryViewActions.assertTextEquals("PORTLET_TEXT_COMMENT_MESSAGE",
			"No comments yet.");
		cPBlogsEntryViewActions.click("BLOGS_ENTRY_LINK_ADD_COMMENT_1", null);
		cPBlogsEntryCommentAddActions.type("BLOGS_COMMENT_FIELD_BODY",
			blogsEntryComment);
		cPBlogsEntryCommentAddActions.click("BLOGS_COMMENT_LINK_SAVE", null);
		cPBlogsEntryCommentAddActions.assertTextEquals("BLOGS_COMMENT_TEXT_SUCCESS",
			null);
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_COMMENT_TEXT_BODY",
			blogsEntryComment);
		cPBlogsEntryViewActions.assertTextNotPresent("", "No comments yet.");
	}

	public void addBlogsEntryComment2(String blogsEntry,
		String blogsEntryComment) throws Exception {
		CPBlogsEntryCommentAddActions cPBlogsEntryCommentAddActions = new CPBlogsEntryCommentAddActions(selenium);
		CPBlogsEntryViewActions cPBlogsEntryViewActions = new CPBlogsEntryViewActions(selenium);
		CPBlogsPortletActions cPBlogsPortletActions = new CPBlogsPortletActions(selenium);
		NavigationMacros navigationMacros = new NavigationMacros(selenium);

		navigationMacros.navigateControlPanelPage("Blogs");
		cPBlogsPortletActions.click("BLOGS_ENTRY_LINK_TITLE", blogsEntry);
		cPBlogsEntryViewActions.click("BLOGS_ENTRY_LINK_ADD_COMMENT_2", null);
		cPBlogsEntryCommentAddActions.type("BLOGS_COMMENT_FIELD_BODY",
			blogsEntryComment);
		cPBlogsEntryCommentAddActions.click("BLOGS_COMMENT_LINK_SAVE", null);
		cPBlogsEntryCommentAddActions.assertTextEquals("BLOGS_COMMENT_TEXT_SUCCESS",
			null);
		cPBlogsEntryViewActions.assertTextEquals("BLOGS_COMMENT_TEXT_BODY",
			blogsEntryComment);
		cPBlogsEntryViewActions.assertTextNotPresent("", "No comments yet.");
	}
}