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
public class PageMacros extends BaseMacros {
	public PageMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void add(String portlet) throws Exception {
		HomeActions homeActions = new HomeActions(selenium);

		homeActions.open("URL_GUEST", null);
		homeActions.mouseOver("ADD_LINK", null);
		homeActions.click("ADD_LINK_PAGE", null);
		homeActions.type("NEW_PAGE_FIELD", "" + portlet + " Page");
		homeActions.click("NEW_PAGE_LINK_SAVE", null);
		homeActions.type("link=" + portlet + " Page", "" + portlet + " Page");
	}
}