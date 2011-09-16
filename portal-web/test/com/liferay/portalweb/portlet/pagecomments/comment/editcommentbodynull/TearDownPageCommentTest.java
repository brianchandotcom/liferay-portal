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

package com.liferay.portalweb.portlet.pagecomments.comment.editcommentbodynull;

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

				for (int second = 0;; second++) {
					if (second >= 60) {
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

				selenium.saveScreenShotAndSource();
				selenium.clickAt("link=Page Comments Test Page",
					RuntimeVariables.replace("Page Comments Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();

				boolean pageComment1Present = selenium.isElementPresent(
						"//li[4]/span/a/span");

				if (!pageComment1Present) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//li[4]/span/a/span"));
				selenium.click("//li[4]/span/a/span");
				Thread.sleep(5000);
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 2:

				boolean pageComment2Present = selenium.isElementPresent(
						"//li[4]/span/a/span");

				if (!pageComment2Present) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//li[4]/span/a/span"));
				selenium.click("//li[4]/span/a/span");
				Thread.sleep(5000);
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 3:

				boolean pageComment3Present = selenium.isElementPresent(
						"//li[4]/span/a/span");

				if (!pageComment3Present) {
					label = 4;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//li[4]/span/a/span"));
				selenium.click("//li[4]/span/a/span");
				Thread.sleep(5000);
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 4:

				boolean pageComment4Present = selenium.isElementPresent(
						"//li[4]/span/a/span");

				if (!pageComment4Present) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//li[4]/span/a/span"));
				selenium.click("//li[4]/span/a/span");
				Thread.sleep(5000);
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 5:

				boolean pageComment5Present = selenium.isElementPresent(
						"//li[4]/span/a/span");

				if (!pageComment5Present) {
					label = 6;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//li[4]/span/a/span"));
				selenium.click("//li[4]/span/a/span");
				Thread.sleep(5000);
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 6:
			case 100:
				label = -1;
			}
		}
	}
}