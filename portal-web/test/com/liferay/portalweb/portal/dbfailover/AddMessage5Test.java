/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.dbfailover;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddMessage5Test extends BaseTestCase {
	public void testAddMessage5() throws Exception {
		selenium.open("/user/joebloggs/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.click(RuntimeVariables.replace(
				"link=M\u00e9ssag\u00e9 Boards T\u00e9st Pag\u00e9"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Test Category"),
			selenium.getText("//tr[3]/td/a"));
		selenium.click(RuntimeVariables.replace("//tr[3]/td/a"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.click(RuntimeVariables.replace(
				"//input[@value='Post New Thread']"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_19_subject']",
			RuntimeVariables.replace("Test Message 5 Subject"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Source", RuntimeVariables.replace("Source"));
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//td[@id='cke_contents__19_editor']/textarea")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.type("//td[@id='cke_contents__19_editor']/textarea",
			RuntimeVariables.replace("Test Message 5 Content"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Publish']",
			RuntimeVariables.replace("Publish"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Test Message 5 Content"),
			selenium.getText("//div[@class='thread-body']"));
		assertEquals(RuntimeVariables.replace("Test Message 5 Subject"),
			selenium.getText("//nav[@id='breadcrumbs']/ul/li[6]/span/a"));
		assertEquals(RuntimeVariables.replace("Test Category"),
			selenium.getText("//nav[@id='breadcrumbs']/ul/li[5]/span/a"));
		selenium.clickAt("//nav[@id='breadcrumbs']/ul/li[5]/span/a",
			RuntimeVariables.replace("Test Category"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("Test Message 5 Subject"),
			selenium.getText("//tr[3]/td/a"));
		System.out.println("Sample data 5 added successfully.\\");
	}
}