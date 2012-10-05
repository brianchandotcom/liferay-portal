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

package com.liferay.portalweb.asset.messageboards.mbthread.viewportletpaginationregularmbcategorythread6ap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class FirstButtonAPTest extends BaseTestCase {
	public void testFirstButtonAP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[contains(@id,'PageIterator')]",
			RuntimeVariables.replace("3"));
		selenium.waitForPageToLoad("30000");
		assertEquals("3",
			selenium.getSelectedLabel("//select[contains(@id,'PageIterator')]"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//a[@class='first']"));
		selenium.clickAt("//a[@class='first']",
			RuntimeVariables.replace("First"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent("//a[@class='first']"));
		assertTrue(selenium.isElementNotPresent("//a[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//a[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//a[@class='last']"));
		assertEquals("1",
			selenium.getSelectedLabel("//select[contains(@id,'PageIterator')]"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select[contains(@id,'PageIterator')]",
			RuntimeVariables.replace("2"));
		selenium.waitForPageToLoad("30000");
		assertEquals("2",
			selenium.getSelectedLabel("//select[contains(@id,'PageIterator')]"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//a[@class='first']"));
		selenium.clickAt("//a[@class='first']",
			RuntimeVariables.replace("First"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent("//a[@class='first']"));
		assertTrue(selenium.isElementNotPresent("//a[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//a[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//a[@class='last']"));
		assertEquals("1",
			selenium.getSelectedLabel("//select[contains(@id,'PageIterator')]"));
	}
}