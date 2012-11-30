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

package com.liferay.portalweb.blocks.portal.home.macros;

import com.liferay.portalweb.blocks.base.macros.BaseMacros;
import com.liferay.portalweb.blocks.portal.home.actions.HomeActions;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class GotoMacros extends BaseMacros {
	public GotoMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void controlPanelPortlet(String portlet) throws Exception {
		HomeActions homeActions = new HomeActions(selenium);

		homeActions.open("URL_GUEST", "http://localhost:8080/web/guest/home/");
		homeActions.mouseOver("GOTO_LINK", "Go to");
		homeActions.click("GOTO_LINK_CONTROL_PANEL", "Control Panel");
		homeActions.click("link=" + portlet + "", portlet);
	}

	public void pagePortlet(String portlet) throws Exception {
		HomeActions homeActions = new HomeActions(selenium);

		homeActions.open("URL_GUEST", "http://localhost:8080/web/guest/home/");
		homeActions.click("link=" + portlet + " Page", "" + portlet + " Page");
	}

	public void siteContentPortlet(String portlet) throws Exception {
		HomeActions homeActions = new HomeActions(selenium);

		homeActions.open("URL_GUEST", "http://localhost:8080/web/guest/home/");
		homeActions.click("link=" + portlet + " Page", "" + portlet + " Page");
	}
}