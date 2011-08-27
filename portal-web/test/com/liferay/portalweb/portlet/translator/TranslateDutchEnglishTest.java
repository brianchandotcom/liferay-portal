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

package com.liferay.portalweb.portlet.translator;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TranslateDutchEnglishTest extends BaseTestCase {
	public void testTranslateDutchEnglish() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Translator Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Translator Test Page",
			RuntimeVariables.replace("Translator Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@id='_26_text']",
			RuntimeVariables.replace(
				"Mijn naam is Vertaler Liferay, vloeiend in meer dan 6 miljoen vormen van mededeling."));
		selenium.saveScreenShotAndSource();
		selenium.select("//select[@id='_26_id']",
			RuntimeVariables.replace("Dutch to English"));
		selenium.clickAt("//input[@value='Translate']",
			RuntimeVariables.replace("Translate"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertTrue(selenium.isTextPresent(
				"My name is translator Liferay, fluently in more than 6 millions forms of communication."));
	}
}