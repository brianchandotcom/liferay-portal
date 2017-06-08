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
public class MouseOverUnits extends BaseActionsUnits {
	public MouseOverUnits(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void mouseOver(String param1, String param2) throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForText(param1, param2);
		selenium.assertText(param1, param2);
		selenium.mouseOver(param1);
	}

	public void partialTextMouseOver(String param1, String param2)
		throws Exception {

		selenium.waitForVisible(param1);
		selenium.waitForPartialText(param1, param2);
		selenium.assertPartialText(param1, param2);
		selenium.mouseOver(param1);
	}

	public void textMouseOver(String param1, String param2) throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForText(param1, param2);
		selenium.assertText(param1, param2);
		selenium.mouseOver(param1);
	}

	public void valueMouseOver(String param1, String param2) throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForValue(param1, param2);
		selenium.assertValue(param1, param2);
		selenium.mouseOver(param1);
	}

}