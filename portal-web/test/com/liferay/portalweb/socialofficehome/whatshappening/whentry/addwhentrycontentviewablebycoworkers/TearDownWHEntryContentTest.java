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

package com.liferay.portalweb.socialofficehome.whatshappening.whentry.addwhentrycontentviewablebycoworkers;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownWHEntryContentTest extends BaseTestCase {
	public void testTearDownWHEntryContent() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.open("/web/joebloggs/home/");

				for (int second = 0;; second++) {
					if (second >= 60) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("//ul[2]/li[2]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.saveScreenShotAndSource();
				selenium.clickAt("//ul[2]/li[2]/a",
					RuntimeVariables.replace("Microblogs"));
				selenium.waitForPageToLoad("30000");
				selenium.saveScreenShotAndSource();
				assertEquals(RuntimeVariables.replace("Microblogs"),
					selenium.getText("//div[2]/div/div/section/header/h1"));
				assertTrue(selenium.isPartialText("//div[2]/h1/span",
						"What's happening?"));

				boolean whatsHappeningEntry1Present = selenium.isElementPresent(
						"//span[.='Delete' and count(*)=0]");

				if (!whatsHappeningEntry1Present) {
					label = 2;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//span[.='Delete' and count(*)=0]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 2:

				boolean whatsHappeningEntry2Present = selenium.isElementPresent(
						"//span[.='Delete' and count(*)=0]");

				if (!whatsHappeningEntry2Present) {
					label = 3;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//span[.='Delete' and count(*)=0]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 3:

				boolean whatsHappeningEntry3Present = selenium.isElementPresent(
						"//span[.='Delete' and count(*)=0]");

				if (!whatsHappeningEntry3Present) {
					label = 4;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//span[.='Delete' and count(*)=0]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 4:

				boolean whatsHappeningEntry4Present = selenium.isElementPresent(
						"//span[.='Delete' and count(*)=0]");

				if (!whatsHappeningEntry4Present) {
					label = 5;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//span[.='Delete' and count(*)=0]"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S]$"));
				selenium.saveScreenShotAndSource();

			case 5:

				boolean whatsHappeningEntry5Present = selenium.isElementPresent(
						"//span[.='Delete' and count(*)=0]");

				if (!whatsHappeningEntry5Present) {
					label = 6;

					continue;
				}

				selenium.click(RuntimeVariables.replace(
						"//span[.='Delete' and count(*)=0]"));
				selenium.waitForPageToLoad("30000");
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