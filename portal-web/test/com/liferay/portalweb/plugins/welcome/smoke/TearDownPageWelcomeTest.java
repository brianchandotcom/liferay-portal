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

package com.liferay.portalweb.plugins.welcome.smoke;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPageWelcomeTest extends BaseTestCase {
	public void testTearDownPageWelcome() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/guest/what-we-do/");
				loadRequiredJavaScriptModules();
				selenium.clickAt("//nav[@id='navigation']",
					RuntimeVariables.replace("Navigation"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementPresent(
									"//script[contains(@src,'/aui/sortable/sortable-min.js')]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean page1Present = selenium.isElementPresent(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])/a/span");

				if (!page1Present) {
					label = 2;

					continue;
				}

				selenium.mouseOver(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("X"),
					selenium.getText(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']"));
				selenium.click(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this page?".equals(
									selenium.getConfirmation())) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementNotPresent(
									"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean page2Present = selenium.isElementPresent(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])/a/span");

				if (!page2Present) {
					label = 3;

					continue;
				}

				selenium.mouseOver(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("X"),
					selenium.getText(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']"));
				selenium.click(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this page?".equals(
									selenium.getConfirmation())) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementNotPresent(
									"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean page3Present = selenium.isElementPresent(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])/a/span");

				if (!page3Present) {
					label = 4;

					continue;
				}

				selenium.mouseOver(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("X"),
					selenium.getText(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']"));
				selenium.click(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this page?".equals(
									selenium.getConfirmation())) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementNotPresent(
									"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean page4Present = selenium.isElementPresent(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])/a/span");

				if (!page4Present) {
					label = 5;

					continue;
				}

				selenium.mouseOver(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("X"),
					selenium.getText(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']"));
				selenium.click(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this page?".equals(
									selenium.getConfirmation())) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementNotPresent(
									"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean page5Present = selenium.isElementPresent(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])/a/span");

				if (!page5Present) {
					label = 6;

					continue;
				}

				selenium.mouseOver(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("X"),
					selenium.getText(
						"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']"));
				selenium.click(
					"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this page?".equals(
									selenium.getConfirmation())) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isElementNotPresent(
									"xPath=(//li[contains(@class,'lfr-nav-deletable')])/span[@class='delete-tab']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 100:
				label = -1;
			}
		}
	}
}