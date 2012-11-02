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
public class PasteUnits extends BaseActionsUnits {
	public PasteUnits(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void paste(String param1, String param2) throws Exception {
		selenium.waitForVisible(param1);
		selenium.paste(param1);
	}

	public void pasteCKEditor(String param1, String param2) throws Exception {
		selenium.waitForElementPresent(
			"//textarea[@id='_161_editor' and @style='display: none;']");
		selenium.waitForVisible("//span[.='Source']");
		selenium.assertText("//span[.='Source']", "Source");
		selenium.click("//span[.='Source']");
		selenium.waitForVisible("//a[@class='cke_button_source cke_on']");
		selenium.waitForVisible(
			"//td[@id='cke_contents__161_editor']/textarea");
		selenium.paste("//td[@id='cke_contents__161_editor']/textarea");
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