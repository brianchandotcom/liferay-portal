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
public class TypeFunctions extends BaseFunctions {
	public TypeFunctions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void sendKeys(String target, String value) throws Exception {
		selenium.waitForVisible(target);
		selenium.sendKeys(target, value);
	}

	public void sendKeysHomeAddApplication(String target, String value)
		throws Exception {
		TypeFunctions typeFunctions = new TypeFunctions(selenium);

		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-live-search/aui-live-search-min.js')]");
		typeFunctions.sendKeys(target, value);
	}

	public void type(String target, String value) throws Exception {
		selenium.waitForVisible(target);
		selenium.type(target, value);
	}

	public void typeCPBlogsCKEditor(String target, String value)
		throws Exception {
		selenium.waitForElementPresent(
			"//textarea[@id='_161_editor' and @style='display: none;']");
		selenium.waitForVisible("//span[.='Source']");
		selenium.assertText("//span[.='Source']", "Source");
		selenium.click("//span[.='Source']");
		selenium.waitForVisible("//a[@class='cke_button_source cke_on']");
		selenium.waitForVisible("//td[@id='cke_contents__161_editor']/textarea");
		selenium.type("//td[@id='cke_contents__161_editor']/textarea", value);
		selenium.waitForVisible("//span[.='Source']");
		selenium.assertText("//span[.='Source']", "Source");
		selenium.click("//span[.='Source']");
		selenium.waitForElementPresent(
			"//textarea[@id='_161_editor' and @style='display: none;']");
		selenium.waitForVisible("//td[@id='cke_contents__161_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__161_editor']/iframe");
		selenium.waitForText("//body", value);
		selenium.assertText("//body", value);
		selenium.selectFrame("relative=top");
	}

	public void typeCPWebContentCKEditor(String target, String value)
		throws Exception {
		selenium.waitForElementPresent(
			"//textarea[@id='_15__15_structure_el_TextAreaField_content' and @style='display: none;']");
		selenium.waitForVisible("//span[.='Source']");
		selenium.assertText("//span[.='Source']", "Source");
		selenium.clickAt("//span[.='Source']", value);
		selenium.waitForVisible("//a[@class='cke_button_source cke_on']");
		selenium.waitForVisible(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/textarea");
		selenium.type("//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/textarea",
			value);
		selenium.waitForVisible("//span[.='Source']");
		selenium.assertText("//span[.='Source']", "Source");
		selenium.clickAt("//span[.='Source']", value);
		selenium.waitForElementPresent(
			"//textarea[@id='_15__15_structure_el_TextAreaField_content' and @style='display: none;']");
		selenium.waitForVisible(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
		selenium.selectFrame(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
		selenium.waitForText("//body", value);
		selenium.assertText("//body", value);
		selenium.selectFrame("relative=top");
	}

	public void uploadCommonFile(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.uploadCommonFile(target, value);
	}
}