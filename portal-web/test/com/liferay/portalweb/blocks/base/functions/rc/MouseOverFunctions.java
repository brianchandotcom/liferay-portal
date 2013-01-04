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

package com.liferay.portalweb.blocks.base.functions.rc;

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class MouseOverFunctions extends BaseFunctions {
	public MouseOverFunctions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void mouseOver(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.mouseOver(target);
	}

	public void partialTextMouseOver(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForPartialText(target, value);
		selenium.assertPartialText(target, value);
		selenium.mouseOver(target);
	}

	public void textMouseOver(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForText(target, value);
		selenium.assertText(target, value);
		selenium.mouseOver(target);
	}

	public void textMouseOverHomeClickDockbar(String target, String value)
		throws Exception {
		MouseOverFunctions mouseOverFunctions = new MouseOverFunctions(selenium);

		selenium.pause("1000");
		selenium.clickAt("//div[@id='dockbar']", "");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-button-item/aui-button-item-min.js')]");
		mouseOverFunctions.textMouseOver(target, value);
	}

	public void valueMouseOver(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForValue(target, value);
		selenium.assertValue(target, value);
		selenium.mouseOver(target);
	}
}