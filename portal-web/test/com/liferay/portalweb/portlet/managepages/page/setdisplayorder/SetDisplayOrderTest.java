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

package com.liferay.portalweb.portlet.managepages.page.setdisplayorder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SetDisplayOrderTest extends BaseTestCase {
	public void testSetDisplayOrder() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Manage Pages Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.mouseOver("link=Manage Pages Test Page");
				assertEquals(RuntimeVariables.replace("Child Test Page1"),
					selenium.getText("//li[2]/ul/li[1]"));
				assertEquals(RuntimeVariables.replace("Child Test Page2"),
					selenium.getText("//li[2]/ul/li[2]"));
				assertEquals(RuntimeVariables.replace("Child Test Page3"),
					selenium.getText("//li[2]/ul/li[3]"));
				selenium.clickAt("//div[@id='dockbar']",
					RuntimeVariables.replace("Dockbar"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//li[contains(@class,'manage-page')]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//li[contains(@class,'manage-page')]/a",
					RuntimeVariables.replace("Manage Pages"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Public Pages")
												.equals(selenium.getText(
										"//a[@class='layout-tree']"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Public Pages"),
					selenium.getText("//a[@class='layout-tree']"));

				boolean welcome1Present = selenium.isElementPresent(
						"//li/ul/li[1]/div/div[3]/a");

				if (welcome1Present) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@id='_88_layoutsTreeOutput']/ul/li/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 2:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Welcome")
												.equals(selenium.getText(
										"//li/ul/li[1]/div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Welcome"),
					selenium.getText("//li/ul/li[1]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Manage Pages Test Page"),
					selenium.getText("//li/ul/li[2]/div/div[3]/a"));
				selenium.clickAt("//li/ul/li[2]/div/div[3]/a",
					RuntimeVariables.replace("Manage Pages Test Page"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Manage Pages Test Page")
												.equals(selenium.getValue(
										"//div[1]/fieldset/div/span[1]/span/span/span/input"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals("Manage Pages Test Page",
					selenium.getValue(
						"//div[1]/fieldset/div/span[1]/span/span/span/input"));

				boolean childPagePresent = selenium.isElementPresent(
						"//li[2]/ul/li[1]/div/div[3]/a");

				if (childPagePresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("//li/ul/li[2]/div/div[1]",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 3:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Child Test Page1")
												.equals(selenium.getText(
										"//li[2]/ul/li[1]/div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Child Test Page1"),
					selenium.getText("//li[2]/ul/li[1]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Child Test Page2"),
					selenium.getText("//li[2]/ul/li[2]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Child Test Page3"),
					selenium.getText("//li[2]/ul/li[3]/div/div[3]/a"));
				selenium.mouseOver("//li[2]/ul/li[1]/div/div[3]/a");
				Thread.sleep(5000);
				selenium.mouseDown("//li[2]/ul/li[1]/div/div[3]/a");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[contains(@class, 'yui3-dd-drop-active-valid')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.mouseMoveAt("//li[2]/ul/li[3]/div/div[3]/a",
					RuntimeVariables.replace("0,15"));
				selenium.mouseMoveAt("//li[2]/ul/li[3]/div/div[3]/a",
					RuntimeVariables.replace("0,15"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[contains(@class,'aui-tree-drag-insert-below')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.mouseUp("//li[2]/ul/li[3]/div/div[3]/a");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (RuntimeVariables.replace("Child Test Page2")
												.equals(selenium.getText(
										"//li[2]/ul/li[1]/div/div[3]/a"))) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Child Test Page2"),
					selenium.getText("//li[2]/ul/li[1]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Child Test Page3"),
					selenium.getText("//li[2]/ul/li[2]/div/div[3]/a"));
				assertEquals(RuntimeVariables.replace("Child Test Page1"),
					selenium.getText("//li[2]/ul/li[3]/div/div[3]/a"));
				selenium.open("/web/guest/home/");
				loadRequiredJavaScriptModules();

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Manage Pages Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.mouseOver("link=Manage Pages Test Page");
				assertEquals(RuntimeVariables.replace("Child Test Page2"),
					selenium.getText("//li[2]/ul/li[1]"));
				assertEquals(RuntimeVariables.replace("Child Test Page3"),
					selenium.getText("//li[2]/ul/li[2]"));
				assertEquals(RuntimeVariables.replace("Child Test Page1"),
					selenium.getText("//li[2]/ul/li[3]"));

			case 100:
				label = -1;
			}
		}
	}
}