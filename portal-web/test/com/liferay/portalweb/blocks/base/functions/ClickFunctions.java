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

package com.liferay.portalweb.blocks.base.functions;

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class ClickFunctions extends BaseFunctions {
	public ClickFunctions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void click(String param1, String param2) throws Exception {
		selenium.waitForVisible(param1);
		selenium.click(param1);
	}

	public void clickAndWait(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.clickAndWait(param1);
	}

	public void clickAt(String param1, String param2) throws Exception {
		selenium.waitForVisible(param1);
		selenium.clickAt(param1, param2);
	}

	public void clickAtAndWait(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.clickAtAndWait(param1, param2);
	}

	public void partialTextClick(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForPartialText(param1, param2);
		selenium.assertPartialText(param1, param2);
		selenium.click(param1);
	}

	public void partialTextClickAndWait(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForPartialText(param1, param2);
		selenium.assertPartialText(param1, param2);
		selenium.clickAndWait(param1);
	}

	public void partialTextClickAt(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForPartialText(param1, param2);
		selenium.assertPartialText(param1, param2);
		selenium.clickAt(param1, param2);
	}

	public void partialTextClickAtAndWait(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForPartialText(param1, param2);
		selenium.assertPartialText(param1, param2);
		selenium.clickAtAndWait(param1, param2);
	}

	public void textClick(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForText(param1, param2);
		selenium.assertText(param1, param2);
		selenium.click(param1);
	}

	public void textClickAndWait(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForText(param1, param2);
		selenium.assertText(param1, param2);
		selenium.clickAndWait(param1);
	}

	public void textClickAt(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForText(param1, param2);
		selenium.assertText(param1, param2);
		selenium.clickAt(param1, param2);
	}

	public void textClickAtAndWait(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForText(param1, param2);
		selenium.assertText(param1, param2);
		selenium.clickAtAndWait(param1, param2);
	}

	public void textClickAtHomeClickDockbar(String param1, String param2)
		throws Exception {
		selenium.clickAt("//div[@id='dockbar']", "");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-button-item/aui-button-item-min.js')]");

		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		clickFunctions.textClickAt(param1, param2);
	}

	public void textClickAtPause(String param1, String param2)
		throws Exception {
		selenium.pause("1000");

		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		clickFunctions.textClickAt(param1, param2);
	}

	public void valueClick(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForValue(param1, param2);
		selenium.assertValue(param1, param2);
		selenium.click(param1);
	}

	public void valueClickAndWait(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForValue(param1, param2);
		selenium.assertValue(param1, param2);
		selenium.clickAndWait(param1);
	}

	public void valueClickAt(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForValue(param1, param2);
		selenium.assertValue(param1, param2);
		selenium.clickAt(param1, param2);
	}

	public void valueClickAtAndWait(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForValue(param1, param2);
		selenium.assertValue(param1, param2);
		selenium.clickAtAndWait(param1, param2);
	}

	public void valueClickAtAndWaitCPBlogsCKEditor(String param1, String param2)
		throws Exception {
		selenium.waitForElementPresent(
			"//textarea[@id='_161_editor' and @style='display: none;']");

		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		clickFunctions.valueClickAtAndWait(param1, param2);
	}
}