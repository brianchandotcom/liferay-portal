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

package com.liferay.portalweb.portlet.wikidisplay.wikipage.editwikifrontpageminorchange;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditWikiFrontPageMinorChangeTest extends BaseTestCase {
	public void testEditWikiFrontPageMinorChange() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Wiki Display Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Wiki Display Test Page",
					RuntimeVariables.replace("Wiki Display Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Wiki Front Page Content"),
					selenium.getText("//div[@class='wiki-body']/p"));
				assertEquals(RuntimeVariables.replace("Edit"),
					selenium.getText("//span[1]/a/span"));
				selenium.clickAt("//span[1]/a/span",
					RuntimeVariables.replace("Edit"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent("//iframe")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.selectFrame("//iframe");
				selenium.type("//body",
					RuntimeVariables.replace("Wiki Front Page Content Edit"));
				selenium.selectFrame("relative=top");

				boolean minorEditChecked = selenium.isChecked(
						"//span[2]/span/span/input[2]");

				if (minorEditChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//span[2]/span/span/input[2]",
					RuntimeVariables.replace(""));

			case 2:
				selenium.clickAt("//input[@value='Publish']",
					RuntimeVariables.replace("Publish"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace(
						"Wiki Front Page Content Edit"),
					selenium.getText("//div[@class='wiki-body']/p"));
				assertNotEquals(RuntimeVariables.replace(
						"Wiki Front Page Content"),
					selenium.getText("//div[@class='wiki-body']/p"));
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Wiki Display Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Wiki Display Test Page",
					RuntimeVariables.replace("Wiki Display Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Details"),
					selenium.getText("//div[3]/span[2]/a/span"));
				selenium.clickAt("//div[3]/span[2]/a/span",
					RuntimeVariables.replace("Details"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=History",
					RuntimeVariables.replace("History"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("1.2 (Minor Edit)"),
					selenium.getText("//td[4]/a"));
				assertEquals(RuntimeVariables.replace("1.1"),
					selenium.getText("//tr[4]/td[4]/a"));
				assertEquals(RuntimeVariables.replace("1.0 (Minor Edit)"),
					selenium.getText("//tr[5]/td[4]/a"));

			case 100:
				label = -1;
			}
		}
	}
}