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

package com.liferay.portalweb.portal.controlpanel.blogs.entry.deleteblogsentrytitleescapecharactercp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddBlogsEntryTitleEscapeCharacterCPTest extends BaseTestCase {
	public void testAddBlogsEntryTitleEscapeCharacterCP()
		throws Exception {
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
		selenium.clickAt("link=Blogs", RuntimeVariables.replace("Blogs"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_161_title']",
			RuntimeVariables.replace("<!--Html Escape Character Test-->"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[@id='cke_48_label' and .='Source']"));
		selenium.clickAt("//span[@id='cke_48_label' and .='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//td[@id='cke_contents__161_editor']/textarea");
		selenium.type("//td[@id='cke_contents__161_editor']/textarea",
			RuntimeVariables.replace("Blogs Entry Content"));
		assertEquals(RuntimeVariables.replace("Source"),
			selenium.getText("//span[@id='cke_48_label' and .='Source']"));
		selenium.clickAt("//span[@id='cke_48_label' and .='Source']",
			RuntimeVariables.replace("Source"));
		selenium.waitForVisible("//td[@id='cke_contents__161_editor']/iframe");
		selenium.selectFrame("//td[@id='cke_contents__161_editor']/iframe");
		selenium.waitForText("//body", "Blogs Entry Content");
		selenium.selectFrame("relative=top");
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"<!--Html Escape Character Test-->"),
			selenium.getText("//td[2]/a"));
	}
}