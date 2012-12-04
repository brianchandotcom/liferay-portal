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

package com.liferay.portalweb.blocks.portal.macros;

import com.liferay.portalweb.blocks.base.macros.BaseMacros;
import com.liferay.portalweb.blocks.portal.actions.HomeActions;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class NavigationMacros extends BaseMacros {
	public NavigationMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void navigateControlPanelPage(String page) throws Exception {
		HomeActions homeActions = new HomeActions(selenium);

		homeActions.open("URL_GUEST", null);
		homeActions.mouseOver("GOTO_LINK", null);
		homeActions.click("GOTO_LINK_CONTROL_PANEL", null);
		homeActions.click("link=" + page + "", page);
	}

	public void navigatePortletPage(String page) throws Exception {
		HomeActions homeActions = new HomeActions(selenium);

		homeActions.open("URL_GUEST", null);
		homeActions.click("link=" + page + " Test Page",
			"" + page + " Test Page");
	}
}