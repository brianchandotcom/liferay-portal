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

package com.liferay.portalweb.plugins.samplespring.pet.addpetdateformatmonthdayslash;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddPetDateFormatMonthDaySlashTest extends BaseTestCase {
	public void testAddPetDateFormatMonthDaySlash() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Pets Test Page",
			RuntimeVariables.replace("Pets Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Pets"),
			selenium.getText("//div[@class='portlet-body']/h1"));
		assertEquals(RuntimeVariables.replace(
				"Species Breed Name Bird Macaw Polly Remove Cat Calico Boots Remove Dog Poodle Fido Remove Snake Boa Bo Remove Add a Pet"),
			selenium.getText("//div[@class='portlet-body']/table"));
		assertEquals(RuntimeVariables.replace("Add a Pet"),
			selenium.getText("//tr[6]/td/a"));
		selenium.clickAt("//tr[6]/td/a", RuntimeVariables.replace("Add a Pet"));
		selenium.waitForText("//div[@class='portlet-body']/h1", "Add New Pet");
		assertEquals(RuntimeVariables.replace("Add New Pet"),
			selenium.getText("//div[@class='portlet-body']/h1"));
		selenium.type("//input[@id='species']", RuntimeVariables.replace("Fish"));
		selenium.clickAt("//input[@value='Next']",
			RuntimeVariables.replace("Next"));
		selenium.waitForVisible("//input[@id='breed']");
		selenium.type("//input[@id='breed']",
			RuntimeVariables.replace("Goldfish"));
		selenium.clickAt("//input[@value='Next']",
			RuntimeVariables.replace("Next"));
		selenium.waitForVisible("//input[@id='name']");
		selenium.type("//input[@id='name']", RuntimeVariables.replace("Bubbles"));
		selenium.clickAt("//input[@value='Next']",
			RuntimeVariables.replace("Next"));
		selenium.waitForVisible("//input[@id='birthdate']");
		assertEquals(RuntimeVariables.replace("Birthdate (MM/dd/yyyy)"),
			selenium.getText("//form[@id='pet']/table/tbody/tr/th"));
		selenium.type("//input[@id='birthdate']",
			RuntimeVariables.replace("08/14/2011"));
		selenium.clickAt("//input[@value='Finish']",
			RuntimeVariables.replace("Finish"));
		selenium.waitForText("//div[@class='portlet-body']/h1", "Pets");
		assertEquals(RuntimeVariables.replace("Pets"),
			selenium.getText("//div[@class='portlet-body']/h1"));
		assertEquals(RuntimeVariables.replace(
				"Species Breed Name Bird Macaw Polly Remove Cat Calico Boots Remove Dog Poodle Fido Remove Fish Goldfish Bubbles Remove Snake Boa Bo Remove Add a Pet"),
			selenium.getText("//div[@class='portlet-body']/table"));
	}
}