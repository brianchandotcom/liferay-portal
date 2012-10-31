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

package com.liferay.portalweb.portlet.messageboards.mblar.importmblar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewImportMBLARTest extends BaseTestCase {
	public void testViewImportMBLAR() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"),
			selenium.getText("//tr[3]/td[1]/a[1]/strong"));
		assertTrue(selenium.isPartialText("//tr[3]/td[1]/a[1]",
				"This is a t\u00e9st cat\u00e9gory!"));
		assertTrue(selenium.isPartialText("//tr[3]/td[1]/span", "Subcategories"));
		assertEquals(RuntimeVariables.replace("T\u00e9st Subcat\u00e9gory"),
			selenium.getText("//tr[3]/td[1]/a[2]"));
		assertEquals(RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"),
			selenium.getText("//tr[3]/td[1]/a/strong"));
		assertTrue(selenium.isPartialText("//tr[3]/td[1]/a",
				"\nThis is a t\u00e9st cat\u00e9gory!"));
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText("//tr[3]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText("//tr[3]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("4"),
			selenium.getText("//tr[3]/td[4]/a"));
		assertEquals(RuntimeVariables.replace(
				"T\u00e9st Cat\u00e9gory Edit\u00e9d"),
			selenium.getText("//tr[4]/td[1]/a/strong"));
		assertTrue(selenium.isPartialText("//tr[4]/td[1]/a",
				"This is a t\u00e9st cat\u00e9gory edited!"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[4]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//tr[4]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//tr[4]/td[4]/a"));
		selenium.clickAt("//tr[3]/td[1]/a[1]/strong",
			RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("T\u00e9st Subcat\u00e9gory"),
			selenium.getText("//td[1]/a[1]/strong"));
		assertEquals(RuntimeVariables.replace(
				"S\u00e9cond T\u00e9st Subcat\u00e9gory"),
			selenium.getText("//td[1]/a[2]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("2"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("4"),
			selenium.getText("//td[4]/a"));
		selenium.clickAt("//td[1]/a/strong",
			RuntimeVariables.replace("T\u00e9st Subcat\u00e9gory"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"S\u00e9cond T\u00e9st Subcat\u00e9gory"),
			selenium.getText("//td[1]/a/strong"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//td[2]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//td[4]/a"));
		assertEquals(RuntimeVariables.replace(
				"T\u00e9st M\u00e9ssag\u00e9 Edited"),
			selenium.getText(
				"//div[2]/div[2]/div/div[1]/div/table/tbody/tr[3]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//div[2]/div[2]/div/div[1]/div/table/tbody/tr[3]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//div[2]/div[2]/div/div[1]/div/table/tbody/tr[3]/td[4]/a"));
		selenium.clickAt("//div[2]/div[2]/div/div[1]/div/table/tbody/tr[3]/td[1]/a",
			RuntimeVariables.replace("T\u00e9st M\u00e9ssag\u00e9 Edited"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"This is a t\u00e9st m\u00e9ssag\u00e9 edited!"),
			selenium.getText("//div[@class='thread-body']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"),
			selenium.getText("//tr[3]/td[1]/a[1]/strong"));
		selenium.clickAt("//tr[3]/td[1]/a[1]/strong",
			RuntimeVariables.replace("T\u00e9st Cat\u00e9gory"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("T\u00e9st Subcat\u00e9gory"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("T\u00e9st Subcat\u00e9gory"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("RE: T\u00e9st M\u00e9ssag\u00e9"),
			selenium.getText("//tr[4]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[4]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("3"),
			selenium.getText("//tr[4]/td[4]/a"));
		selenium.clickAt("//tr[4]/td[1]/a",
			RuntimeVariables.replace("RE: T\u00e9st M\u00e9ssag\u00e9"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"This is a t\u00e9st r\u00e9ply m\u00e9ssag\u00e9!"),
			selenium.getText("xPath=(//div[@class='thread-body'])[1]"));
		assertEquals(RuntimeVariables.replace("This is a second reply message."),
			selenium.getText("xPath=(//div[@class='thread-body'])[2]"));
		assertEquals(RuntimeVariables.replace("This is a third reply message."),
			selenium.getText("xPath=(//div[@class='thread-body'])[3]"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"T\u00e9st Cat\u00e9gory Edit\u00e9d"),
			selenium.getText("//tr[4]/td[1]/a/strong"));
		selenium.clickAt("//tr[4]/td[1]/a/strong",
			RuntimeVariables.replace("T\u00e9st Cat\u00e9gory Edit\u00e9d"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Moved to Sujr"),
			selenium.getText("//td[1]/a"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//td[3]/a"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//td[4]/a"));
		selenium.clickAt("//td[1]/a", RuntimeVariables.replace("Moved to Sujr"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Trust and paths will be straightened."),
			selenium.getText("//div[@class='thread-body']"));
	}
}