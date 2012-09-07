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

package com.liferay.portalweb.plugins.samplespring.pet.addpet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownPetTest extends BaseTestCase {
	public void testTearDownPet() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Pets Test Page",
					RuntimeVariables.replace("Pets Test Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Pets"),
					selenium.getText("//div[@class='portlet-body']/h1"));

				boolean petPresent = selenium.isElementPresent(
						"//div[@class='portlet-body']/table/tbody/tr[contains(.,'Fish')]/td[contains(.,'Remove')]/a");

				if (!petPresent) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Remove"),
					selenium.getText(
						"//div[@class='portlet-body']/table/tbody/tr[contains(.,'Fish')]/td[contains(.,'Remove')]/a"));
				selenium.clickAt("//div[@class='portlet-body']/table/tbody/tr[contains(.,'Fish')]/td[contains(.,'Remove')]/a",
					RuntimeVariables.replace("Remove"));
				selenium.waitForText("//div[@class='portlet-body']/table",
					"Species Breed Name Bird Macaw Polly Remove Cat Calico Boots Remove Dog Poodle Fido Remove Snake Boa Bo Remove Add a Pet");
				assertEquals(RuntimeVariables.replace(
						"Species Breed Name Bird Macaw Polly Remove Cat Calico Boots Remove Dog Poodle Fido Remove Snake Boa Bo Remove Add a Pet"),
					selenium.getText("//div[@class='portlet-body']/table"));

			case 2:
			case 100:
				label = -1;
			}
		}
	}
}