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

package com.liferay.portalweb.blocks.portal.webcontent.controlpanel.macros.rc;

import com.liferay.portalweb.blocks.base.macros.rc.BaseMacros;
import com.liferay.portalweb.blocks.portal.home.page.macros.rc.GotoMacros;
import com.liferay.portalweb.blocks.portal.webcontent.controlpanel.actions.article.rc.CPWebContentAddArticleActions;
import com.liferay.portalweb.blocks.portal.webcontent.controlpanel.actions.home.rc.CPWebContentHomeActions;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPWebContentArticleMacros extends BaseMacros {
	public CPWebContentArticleMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void add(String title, String content) throws Exception {
		CPWebContentAddArticleActions cPWebContentAddArticleActions = new CPWebContentAddArticleActions(selenium);
		CPWebContentHomeActions cPWebContentHomeActions = new CPWebContentHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Web Content");
		cPWebContentHomeActions.click("BUTTONS_ADD", "Add");
		cPWebContentHomeActions.click("ADD_BASIC_WEB_CONTENT",
			"Basic Web Content");
		cPWebContentAddArticleActions.type("ARTICLE_TITLE", title);
		cPWebContentAddArticleActions.type("ARTICLE_CONTENT", content);
		cPWebContentAddArticleActions.click("SIDEBAR_PUBLISH", "Publish");
		cPWebContentHomeActions.assertTextEquals("HEADER_PORTLET_SUCCESS",
			"Your request completed successfully.");
	}

	public void tearDown() throws Exception {
		CPWebContentHomeActions cPWebContentHomeActions = new CPWebContentHomeActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Web Content");

		while (cPWebContentHomeActions.isElementPresent("ENTRIES_ARTICLE_1", "")) {
			cPWebContentHomeActions.check("ENTRIES_ARTICLE_1_CHECKBOX", null);
			cPWebContentHomeActions.click("BUTTONS_ACTIONS", "Actions");
			cPWebContentHomeActions.click("ACTIONS_DELETE", "Delete");
		}
	}
}