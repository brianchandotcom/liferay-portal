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

package com.liferay.portalweb.portal.dbupgrade.sampledata523.address.myaccount;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddAddress1MyAccountTest extends BaseTestCase {
	public void testAddAddress1MyAccount() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=My Account", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("addressesLink", RuntimeVariables.replace(""));
		selenium.waitForVisible("_2_addressStreet10");
		selenium.type("_2_addressStreet10",
			RuntimeVariables.replace("1220 Brea Canyon Rd"));
		selenium.select("_2_addressTypeId0",
			RuntimeVariables.replace("label=Business"));
		selenium.type("_2_addressStreet20", RuntimeVariables.replace("Ste 12"));
		selenium.type("_2_addressZip0", RuntimeVariables.replace("91789"));
		selenium.type("_2_addressStreet30", RuntimeVariables.replace("Walnut"));
		selenium.type("_2_addressCity0", RuntimeVariables.replace("Los Angeles"));
		selenium.select("_2_addressCountryId0",
			RuntimeVariables.replace("label=United States"));
		selenium.click("_2_addressPrimary0");
		selenium.click("_2_addressMailing0Checkbox");
		selenium.waitForText("_2_addressRegionId0",
			"AlabamaAlaskaArizonaArkansasCaliforniaColoradoConnecticutDelawareDistrict of ColumbiaFloridaGeorgiaHawaiiIdahoIllinoisIndianaIowaKansasKentuckyLouisianaMaineMarylandMassachusettsMichiganMinnesotaMississippiMissouriMontanaNebraskaNevadaNew HampshireNew JerseyNew MexicoNew YorkNorth CarolinaNorth DakotaOhioOklahomaOregonPennsylvaniaPuerto RicoRhode IslandSouth CarolinaSouth DakotaTennesseeTexasUtahVermontVirginiaWashingtonWest VirginiaWisconsinWyoming");
		selenium.select("_2_addressRegionId0",
			RuntimeVariables.replace("label=California"));
		Thread.sleep(5000);
		selenium.click(RuntimeVariables.replace("//input[@value='Save']"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForSelectedLabel("_2_addressCountryId0", "United States");
		selenium.waitForSelectedLabel("_2_addressRegionId0", "California");
		assertEquals("1220 Brea Canyon Rd",
			selenium.getValue("_2_addressStreet10"));
		assertEquals(RuntimeVariables.replace(
				"Your request processed successfully."),
			selenium.getText("//div[2]/div/div/div"));
		assertEquals("Business", selenium.getSelectedLabel("_2_addressTypeId0"));
		assertEquals("Ste 12", selenium.getValue("_2_addressStreet20"));
		assertEquals("91789", selenium.getValue("_2_addressZip0"));
		assertEquals("Walnut", selenium.getValue("_2_addressStreet30"));
		assertEquals("Los Angeles", selenium.getValue("_2_addressCity0"));
		assertEquals("United States",
			selenium.getSelectedLabel("_2_addressCountryId0"));
		assertTrue(selenium.isChecked("_2_addressPrimary0"));
		assertTrue(selenium.isChecked("_2_addressMailing0Checkbox"));
		assertEquals("California",
			selenium.getSelectedLabel("_2_addressRegionId0"));
	}
}