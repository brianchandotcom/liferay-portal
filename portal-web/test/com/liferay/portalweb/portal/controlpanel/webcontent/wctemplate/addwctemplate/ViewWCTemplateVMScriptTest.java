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

package com.liferay.portalweb.portal.controlpanel.webcontent.wctemplate.addwctemplate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCTemplateVMScriptTest extends BaseTestCase {
	public void testViewWCTemplateVMScript() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Templates", RuntimeVariables.replace("Templates"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC Template Name"),
			selenium.getText("//td[3]/a"));
		selenium.clickAt("//td[3]/a",
			RuntimeVariables.replace("WC Template Name"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Launch Editor']",
			RuntimeVariables.replace("Launch Editor"));
		selenium.waitForVisible("//iframe[@id='_15_xslContentIFrame']");
		selenium.selectFrame("//iframe[@id='_15_xslContentIFrame']");
		selenium.waitForValue("//textarea[@id='_15_plainEditorField']",
			"<h3>$text.getData()</h3>");
		assertEquals("<h3>$text.getData()</h3>",
			selenium.getValue("//textarea[@id='_15_plainEditorField']"));
		selenium.clickAt("//input[@value='Cancel']",
			RuntimeVariables.replace("Cancel"));
		selenium.selectFrame("relative=top");
		selenium.waitForNotVisible("//iframe[@id='_15_xslContentIFrame']");
		assertFalse(selenium.isVisible("//iframe[@id='_15_xslContentIFrame']"));
		selenium.clickAt("//input[@value='Launch Editor']",
			RuntimeVariables.replace("Launch Editor"));
		selenium.waitForVisible("//iframe[@id='_15_xslContentIFrame']");
		selenium.selectFrame("//iframe[@id='_15_xslContentIFrame']");
		selenium.waitForValue("//textarea[@id='_15_plainEditorField']",
			"<h3>$text.getData()</h3>");
		assertEquals("<h3>$text.getData()</h3>",
			selenium.getValue("//textarea[@id='_15_plainEditorField']"));
		selenium.selectFrame("relative=top");
		selenium.clickAt("//button[@id='closethick']",
			RuntimeVariables.replace("X"));
		selenium.waitForNotVisible("//iframe[@id='_15_xslContentIFrame']");
		assertFalse(selenium.isVisible("//iframe[@id='_15_xslContentIFrame']"));
	}
}