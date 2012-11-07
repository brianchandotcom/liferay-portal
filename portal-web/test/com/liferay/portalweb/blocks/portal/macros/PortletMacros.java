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
import com.liferay.portalweb.blocks.portal.actions.AddApplicationActions;
import com.liferay.portalweb.blocks.portal.actions.HomeActions;
import com.liferay.portalweb.blocks.portal.macros.NavigationMacros;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySeleniumHelper;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletMacros extends BaseMacros {
	public PortletMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void addPortlet(String portlet) throws Exception {
		AddApplicationActions addApplicationActions = new AddApplicationActions(selenium);
		HomeActions homeActions = new HomeActions(selenium);
		NavigationMacros navigationMacros = new NavigationMacros(selenium);

		navigationMacros.navigatePortletPage(portlet);
		homeActions.mouseOver("ADD_LINK", null);
		homeActions.click("ADD_LINK_APPLICATION", null);
		addApplicationActions.type("PORTLET_FIELD_SEARCH",
			LiferaySeleniumHelper.firstLetter(portlet));
		addApplicationActions.click("//div[@title='" + portlet + "']/p/a", null);
		homeActions.assertTextEquals("PORTLET_TEXT_TITLE", portlet);
	}
}