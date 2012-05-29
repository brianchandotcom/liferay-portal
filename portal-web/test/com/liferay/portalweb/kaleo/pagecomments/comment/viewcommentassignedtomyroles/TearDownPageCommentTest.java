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

package com.liferay.portalweb.kaleo.pagecomments.comment.viewcommentassignedtomyroles;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPageCommentTest extends BaseTestCase {
	public void testTearDownPageComment() throws Exception {
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
						if (selenium.isVisible("link=Page Comments Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Page Comments Test Page",
					RuntimeVariables.replace("Page Comments Test Page"));
				selenium.waitForPageToLoad("30000");
				loadRequiredJavaScriptModules();

				boolean pageComment1Present = selenium.isElementPresent(
						"//li[@class='lfr-discussion-delete']/span/a/span");

				if (!pageComment1Present) {
					label = 6;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//li[@class='lfr-discussion-delete']/span/a/span"));
				selenium.clickAt("//li[@class='lfr-discussion-delete']/span/a/span",
					RuntimeVariables.replace("Delete"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this? It will be deleted immediately.".equals(
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
						if (selenium.isVisible(
									"//div[@class='lfr-message-response portlet-msg-success']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText(
						"//div[@class='lfr-message-response portlet-msg-success']"));
				Thread.sleep(5000);

				boolean pageComment2Present = selenium.isElementPresent(
						"//li[@class='lfr-discussion-delete']/span/a/span");

				if (!pageComment2Present) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//li[@class='lfr-discussion-delete']/span/a/span"));
				selenium.clickAt("//li[@class='lfr-discussion-delete']/span/a/span",
					RuntimeVariables.replace("Delete"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this? It will be deleted immediately.".equals(
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
						if (selenium.isVisible(
									"//div[@class='lfr-message-response portlet-msg-success']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText(
						"//div[@class='lfr-message-response portlet-msg-success']"));
				Thread.sleep(5000);

				boolean pageComment3Present = selenium.isElementPresent(
						"//li[@class='lfr-discussion-delete']/span/a/span");

				if (!pageComment3Present) {
					label = 4;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//li[@class='lfr-discussion-delete']/span/a/span"));
				selenium.clickAt("//li[@class='lfr-discussion-delete']/span/a/span",
					RuntimeVariables.replace("Delete"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this? It will be deleted immediately.".equals(
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
						if (selenium.isVisible(
									"//div[@class='lfr-message-response portlet-msg-success']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText(
						"//div[@class='lfr-message-response portlet-msg-success']"));
				Thread.sleep(5000);

				boolean pageComment4Present = selenium.isElementPresent(
						"//li[@class='lfr-discussion-delete']/span/a/span");

				if (!pageComment4Present) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//li[@class='lfr-discussion-delete']/span/a/span"));
				selenium.click(
					"//li[@class='lfr-discussion-delete']/span/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this? It will be deleted immediately.".equals(
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
						if (selenium.isVisible(
									"//div[@class='lfr-message-response portlet-msg-success']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText(
						"//div[@class='lfr-message-response portlet-msg-success']"));
				Thread.sleep(5000);

				boolean pageComment5Present = selenium.isElementPresent(
						"//li[@class='lfr-discussion-delete']/span/a/span");

				if (!pageComment5Present) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//li[@class='lfr-discussion-delete']/span/a/span"));
				selenium.click(
					"//li[@class='lfr-discussion-delete']/span/a/span");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if ("Are you sure you want to delete this? It will be deleted immediately.".equals(
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
						if (selenium.isVisible(
									"//div[@class='lfr-message-response portlet-msg-success']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace(
						"Your request processed successfully."),
					selenium.getText(
						"//div[@class='lfr-message-response portlet-msg-success']"));

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