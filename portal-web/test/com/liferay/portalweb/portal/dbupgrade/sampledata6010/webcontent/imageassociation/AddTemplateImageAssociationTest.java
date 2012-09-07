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

package com.liferay.portalweb.portal.dbupgrade.sampledata6010.webcontent.imageassociation;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddTemplateImageAssociationTest extends BaseTestCase {
	public void testAddTemplateImageAssociation() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Communities", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_134_name",
			RuntimeVariables.replace("Web Content Image Association Community"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//td[2]/a", RuntimeVariables.replace("Open"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Web Content", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Templates", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Template']",
			RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.type("_15_newTemplateId",
			RuntimeVariables.replace("test_image"));
		selenium.type("_15_name",
			RuntimeVariables.replace("Image Template Test"));
		selenium.type("_15_description",
			RuntimeVariables.replace("This is an image template test."));
		selenium.click("//input[@value='Select']");
		selenium.waitForPopUp("structure", RuntimeVariables.replace("30000"));
		selenium.selectWindow("name=structure");
		selenium.waitForElementPresent("link=TEST_IMAGE");
		selenium.click("link=TEST_IMAGE");
		selenium.selectWindow("null");
		selenium.waitForText("_15_structureName", "Image Structure Test");
		assertEquals(RuntimeVariables.replace("Image Structure Test"),
			selenium.getText("_15_structureName"));
		selenium.clickAt("_15_editorButton", RuntimeVariables.replace(""));
		Thread.sleep(5000);
		selenium.waitForVisible("_15_xslContent");
		selenium.type("_15_xslContent",
			RuntimeVariables.replace(
				"<h1 id=\"web-content-title\">$text-test.getData()</h1>\n\n<h2 id=\"image-title\">Image Test:</h2>\n<a id=\"image\"><img src=\"$image-test.data\"></img></a>\n\n<h2 id=\"image-gallery-title\">Image Gallery Image Test:</h2>\n<a id=\"image-gallery\"><img src=\"$image-gallery-test.data\"></img></a>"));
		Thread.sleep(5000);
		selenium.click("//input[@value='Update']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("TEST_IMAGE"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[2]"));
		assertEquals(RuntimeVariables.replace(
				"Image Template Test\n This is an image template test."),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]"));
	}
}