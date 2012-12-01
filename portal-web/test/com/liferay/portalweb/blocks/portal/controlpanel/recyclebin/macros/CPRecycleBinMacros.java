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

package com.liferay.portalweb.blocks.portal.controlpanel.recyclebin.macros;

import com.liferay.portalweb.blocks.base.macros.BaseMacros;
import com.liferay.portalweb.blocks.portal.controlpanel.recyclebin.actions.home.CPRecycleBinPortletActions;
import com.liferay.portalweb.blocks.portal.home.macros.GotoMacros;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class CPRecycleBinMacros extends BaseMacros {
	public CPRecycleBinMacros(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void empty() throws Exception {
		CPRecycleBinPortletActions cPRecycleBinPortletActions = new CPRecycleBinPortletActions(selenium);
		GotoMacros gotoMacros = new GotoMacros(selenium);

		gotoMacros.controlPanelPortlet("Recycle Bin");

		while (cPRecycleBinPortletActions.isElementPresent(
					"EMPTY_RECYCLE_BIN_LINK", "Empty the Recycle Bin")) {
			cPRecycleBinPortletActions.click("EMPTY_RECYCLE_BIN_LINK",
				"Empty the Recycle Bin");
			cPRecycleBinPortletActions.confirm("EMPTY_RECYCLE_BIN_CONFIRM",
				"Are you sure you want to empty the Recycle Bin?");
			cPRecycleBinPortletActions.assertTextEquals("PORTLET_SUCCESS",
				"Your request completed successfully.");
		}

		cPRecycleBinPortletActions.assertTextEquals("TABLE_EMPTY_MESSAGE",
			"The Recycle Bin is empty.");
	}
}