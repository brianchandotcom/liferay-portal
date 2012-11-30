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

package com.liferay.portalweb.blocks.portal.controlpanel.webcontent.macros;

import com.liferay.portalweb.blocks.base.macros.BaseMacros;
import com.liferay.portalweb.blocks.portal.controlpanel.webcontent.actions.home.CPWebContentHomeActions;
import com.liferay.portalweb.blocks.portal.controlpanel.webcontent.actions.webcontent.CPWebContentAddWebContentActions;
import com.liferay.portalweb.blocks.portal.home.macros.NavigationMacros;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPWebContentArticleMacros extends BaseMacros {
	public CPWebContentArticleMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void addCPWebContent(String title, String content)
		throws Exception {
		CPWebContentAddWebContentActions cPWebContentAddWebContentActions = new CPWebContentAddWebContentActions(selenium);
		CPWebContentHomeActions cPWebContentHomeActions = new CPWebContentHomeActions(selenium);
		NavigationMacros navigationMacros = new NavigationMacros(selenium);

		navigationMacros.navigateControlPanelPage("Web Content");
		cPWebContentHomeActions.click("BUTTONS_ADD", "Add");
		cPWebContentHomeActions.click("ADD_BASIC_WEB_CONTENT",
			"Basic Web Content");
		cPWebContentAddWebContentActions.type("ARTICLE_TITLE", title);
		cPWebContentAddWebContentActions.type("ARTICLE_CONTENT", content);
		cPWebContentAddWebContentActions.click("SIDEBAR_PUBLISH", "Publish");
		cPWebContentHomeActions.assertTextEquals("HEADER_PORTLET_SUCCESS",
			"Your request completed successfully.");
	}

	public void tearDownCPWebContent() throws Exception {
		CPWebContentHomeActions cPWebContentHomeActions = new CPWebContentHomeActions(selenium);
		NavigationMacros navigationMacros = new NavigationMacros(selenium);

		navigationMacros.navigateControlPanelPage("Web Content");

		while (cPWebContentHomeActions.isElementPresent("ENTRIES_ARTICLE_1", "")) {
			cPWebContentHomeActions.check("ENTRIES_ARTICLE_1_CHECKBOX", null);
			cPWebContentHomeActions.click("BUTTONS_ACTIONS", "Actions");
			cPWebContentHomeActions.click("ACTIONS_DELETE", "Delete");
		}
	}
}