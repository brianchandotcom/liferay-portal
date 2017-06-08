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

package com.liferay.portalweb.blocks.base.units;

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class OpenUnits extends BaseActionsUnits {
	public OpenUnits(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void open(String param1, String param2) throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open(param1);
	}

	public void openWindow(String param1, String param2) throws Exception {
		selenium.pause("15000");
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.openWindow(param1, "windowName");
		selenium.waitForPopUp("windowName", "windowName");
		selenium.selectWindow("windowName");
	}

}