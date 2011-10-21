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

package com.liferay.portalweb.portlet.unitconverter.unit.convertunit;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ConvertUnitTest extends BaseTestCase {
	public void testConvertUnit() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Unit Converter Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Unit Converter Test Page",
			RuntimeVariables.replace("Unit Converter Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_27_fromValue']",
			RuntimeVariables.replace("1.0"));
		selenium.select("//select[@name='_27_fromId']",
			RuntimeVariables.replace("Inch"));
		selenium.select("//select[@name='_27_toId']",
			RuntimeVariables.replace("Centimeter"));
		selenium.select("//select[@name='_27_type']",
			RuntimeVariables.replace("Length"));
		selenium.clickAt("//input[@value='Convert']",
			RuntimeVariables.replace("Convert"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("2.5399999187200026")
										.equals(selenium.getValue(
								"//input[@name='_27_to_value']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals("2.5399999187200026",
			selenium.getValue("//input[@name='_27_to_value']"));
	}
}