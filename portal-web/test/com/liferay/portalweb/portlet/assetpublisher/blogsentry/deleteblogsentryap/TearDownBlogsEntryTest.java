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

package com.liferay.portalweb.portlet.assetpublisher.blogsentry.deleteblogsentryap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownBlogsEntryTest extends BaseTestCase {
	public void testTearDownBlogsEntry() throws Exception {
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
						if (selenium.isVisible("link=Blogs Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Blogs Test Page",
					RuntimeVariables.replace("Blogs Test Page"));
				selenium.waitForPageToLoad("30000");

				boolean blogsEntry1Present = selenium.isElementPresent(
						"//td[3]/span/a/span");

				if (!blogsEntry1Present) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//td[3]/span/a/span"));
				selenium.click(RuntimeVariables.replace("//td[3]/span/a/span"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean blogsEntry2Present = selenium.isElementPresent(
						"//td[3]/span/a/span");

				if (!blogsEntry2Present) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//td[3]/span/a/span"));
				selenium.click(RuntimeVariables.replace("//td[3]/span/a/span"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean blogsEntry3Present = selenium.isElementPresent(
						"//td[3]/span/a/span");

				if (!blogsEntry3Present) {
					label = 4;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//td[3]/span/a/span"));
				selenium.click(RuntimeVariables.replace("//td[3]/span/a/span"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean blogsEntry4Present = selenium.isElementPresent(
						"//td[3]/span/a/span");

				if (!blogsEntry4Present) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//td[3]/span/a/span"));
				selenium.click(RuntimeVariables.replace("//td[3]/span/a/span"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

				boolean blogsEntry5Present = selenium.isElementPresent(
						"//td[3]/span/a/span");

				if (!blogsEntry5Present) {
					label = 6;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText("//td[3]/span/a/span"));
				selenium.click(RuntimeVariables.replace("//td[3]/span/a/span"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));

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