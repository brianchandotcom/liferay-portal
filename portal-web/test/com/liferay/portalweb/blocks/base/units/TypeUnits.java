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
public class TypeUnits extends BaseActionsUnits {

	public TypeUnits(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void sendKeys(String param1, String param2) throws Exception {
		selenium.waitForVisible(param1);
		selenium.sendKeys(param1, param2);
	}

	public void type(String param1, String param2) throws Exception {
		selenium.waitForVisible(param1);
		selenium.type(param1, param2);
	}

	public void typeCKEditor(String param1, String param2) throws Exception {
		selenium.waitForElementPresent(
			"//textarea[@id='_161_editor' and @style='display: none;']");
		selenium.waitForVisible("//span[.='Source']");
		selenium.assertText("//span[.='Source']", "Source");
		selenium.click("//span[.='Source']");
		selenium.waitForVisible("//a[@class='cke_button_source cke_on']");
		selenium.waitForVisible(
			"//td[@id='cke_contents__161_editor']/textarea");
		selenium.type("//td[@id='cke_contents__161_editor']/textarea", param2);
		selenium.waitForVisible("//span[.='Source']");
		selenium.assertText("//span[.='Source']", "Source");
		selenium.click("//span[.='Source']");
		selenium.waitForElementPresent(
			"//textarea[@id='_161_editor' and @style='display: none;']");
		selenium.waitForVisible("//td[@id='cke_contents__161_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__161_editor']/iframe");
		selenium.waitForText("//body", param2);
		selenium.assertText("//body", param2);
		selenium.selectFrame("relative=top");
	}

}