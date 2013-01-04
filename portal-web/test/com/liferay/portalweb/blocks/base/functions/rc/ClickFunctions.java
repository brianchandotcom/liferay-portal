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
public class ClickFunctions extends BaseFunctions {
	public ClickFunctions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void click(String target, String value) throws Exception {
		selenium.waitForVisible(target);
		selenium.click(target);
	}

	public void clickAndWait(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.clickAndWait(target);
	}

	public void clickAt(String target, String value) throws Exception {
		selenium.waitForVisible(target);
		selenium.clickAt(target, value);
	}

	public void clickAtAndWait(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.clickAtAndWait(target, value);
	}

	public void partialTextClick(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForPartialText(target, value);
		selenium.assertPartialText(target, value);
		selenium.click(target);
	}

	public void partialTextClickAndWait(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForPartialText(target, value);
		selenium.assertPartialText(target, value);
		selenium.clickAndWait(target);
	}

	public void partialTextClickAt(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForPartialText(target, value);
		selenium.assertPartialText(target, value);
		selenium.clickAt(target, value);
	}

	public void partialTextClickAtAndWait(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForPartialText(target, value);
		selenium.assertPartialText(target, value);
		selenium.clickAtAndWait(target, value);
	}

	public void textClick(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForText(target, value);
		selenium.assertText(target, value);
		selenium.click(target);
	}

	public void textClickAndWait(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForText(target, value);
		selenium.assertText(target, value);
		selenium.clickAndWait(target);
	}

	public void textClickAt(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForText(target, value);
		selenium.assertText(target, value);
		selenium.clickAt(target, value);
	}

	public void textClickAtAndWait(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForText(target, value);
		selenium.assertText(target, value);
		selenium.clickAtAndWait(target, value);
	}

	public void textClickAtHomeClickDockbar(String target, String value)
		throws Exception {
		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		selenium.pause("1000");
		selenium.clickAt("//div[@id='dockbar']", "");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-button-item/aui-button-item-min.js')]");
		clickFunctions.textClickAt(target, value);
	}

	public void textClickAtPause(String target, String value)
		throws Exception {
		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		selenium.pause("1000");
		clickFunctions.textClickAt(target, value);
	}

	public void valueClick(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForValue(target, value);
		selenium.assertValue(target, value);
		selenium.click(target);
	}

	public void valueClickAndWait(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForValue(target, value);
		selenium.assertValue(target, value);
		selenium.clickAndWait(target);
	}

	public void valueClickAt(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForValue(target, value);
		selenium.assertValue(target, value);
		selenium.clickAt(target, value);
	}

	public void valueClickAtAndWait(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForValue(target, value);
		selenium.assertValue(target, value);
		selenium.clickAtAndWait(target, value);
	}

	public void valueClickAtAndWaitCPBlogsCKEditor(String target, String value)
		throws Exception {
		ClickFunctions clickFunctions = new ClickFunctions(selenium);

		selenium.waitForElementPresent(
			"//textarea[@id='_161_editor' and @style='display: none;']");
		clickFunctions.valueClickAtAndWait(target, value);
	}
}