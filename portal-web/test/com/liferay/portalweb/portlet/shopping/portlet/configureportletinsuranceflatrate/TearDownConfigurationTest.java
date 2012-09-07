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

package com.liferay.portalweb.portlet.shopping.portlet.configureportletinsuranceflatrate;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownConfigurationTest extends BaseTestCase {
	public void testTearDownConfiguration() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForVisible("link=Shopping Test Page");
				selenium.clickAt("link=Shopping Test Page",
					RuntimeVariables.replace("Shopping Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//strong/a"));
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a",
					RuntimeVariables.replace("Configuration"));
				selenium.waitForVisible("link=Payment Settings");
				selenium.clickAt("link=Payment Settings",
					RuntimeVariables.replace("Payment Settings"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//select[@id='_86_current_cc_types']");

				boolean amexAvailable = selenium.isPartialText("_86_available_cc_types",
						"American Express");

				if (!amexAvailable) {
					label = 2;

					continue;
				}

				selenium.addSelection("//select[@id='_86_available_cc_types']",
					RuntimeVariables.replace("label=American Express"));
				selenium.waitForVisible("//div[2]/div/span/span/button[2]");
				selenium.clickAt("//div[2]/div/span/span/button[2]",
					RuntimeVariables.replace("Left Arrow"));
				selenium.waitForPartialText("//select[@id='_86_current_cc_types']",
					"American Express");

			case 2:
				selenium.waitForVisible("//select[@id='_86_current_cc_types']");

				boolean discoverAvailable = selenium.isPartialText("_86_available_cc_types",
						"Discover");

				if (!discoverAvailable) {
					label = 3;

					continue;
				}

				selenium.addSelection("//select[@id='_86_available_cc_types']",
					RuntimeVariables.replace("label=Discover"));
				selenium.waitForVisible("//div[2]/div/span/span/button[2]");
				selenium.clickAt("//div[2]/div/span/span/button[2]",
					RuntimeVariables.replace("Left Arrow"));
				selenium.waitForPartialText("//select[@id='_86_current_cc_types']",
					"Discover");

			case 3:
				selenium.waitForVisible("//select[@id='_86_current_cc_types']");

				boolean masterCardAvailable = selenium.isPartialText("_86_available_cc_types",
						"MasterCard");

				if (!masterCardAvailable) {
					label = 4;

					continue;
				}

				selenium.addSelection("//select[@id='_86_available_cc_types']",
					RuntimeVariables.replace("label=MasterCard"));
				selenium.waitForVisible("//div[2]/div/span/span/button[2]");
				selenium.clickAt("//div[2]/div/span/span/button[2]",
					RuntimeVariables.replace("Left Arrow"));
				selenium.waitForPartialText("//select[@id='_86_current_cc_types']",
					"MasterCard");

			case 4:
				selenium.waitForVisible("//select[@id='_86_current_cc_types']");

				boolean visaAvailable = selenium.isPartialText("_86_available_cc_types",
						"Visa");

				if (!visaAvailable) {
					label = 5;

					continue;
				}

				selenium.addSelection("//select[@id='_86_available_cc_types']",
					RuntimeVariables.replace("label=Visa"));
				selenium.waitForVisible("//div[2]/div/span/span/button[2]");
				selenium.clickAt("//div[2]/div/span/span/button[2]",
					RuntimeVariables.replace("Left Arrow"));
				selenium.waitForPartialText("//select[@id='_86_current_cc_types']",
					"Visa");

			case 5:
				selenium.select("//select[@id='_86_currencyId']",
					RuntimeVariables.replace("USD"));
				selenium.select("//select[@id='_86_taxState']",
					RuntimeVariables.replace("California"));
				selenium.type("//input[@id='_86_taxRate']",
					RuntimeVariables.replace("0"));
				selenium.type("//input[@id='_86_minOrder']",
					RuntimeVariables.replace("0"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Shipping Calculation",
					RuntimeVariables.replace("Shipping Calculation"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_86_shippingFormula']",
					RuntimeVariables.replace("Flat Amount"));
				selenium.type("//input[@id='_86_shipping0']",
					RuntimeVariables.replace("0"));
				selenium.type("//input[@id='_86_shipping1']",
					RuntimeVariables.replace("0"));
				selenium.type("//input[@id='_86_shipping2']",
					RuntimeVariables.replace("0"));
				selenium.type("//input[@id='_86_shipping3']",
					RuntimeVariables.replace("0"));
				selenium.type("//input[@id='_86_shipping4']",
					RuntimeVariables.replace("0"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Insurance Calculation",
					RuntimeVariables.replace("Insurance Calculation"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_86_insuranceFormula']",
					RuntimeVariables.replace("Flat Amount"));
				selenium.type("//input[@id='_86_insurance0']",
					RuntimeVariables.replace("0"));
				selenium.type("//input[@id='_86_insurance1']",
					RuntimeVariables.replace("0"));
				selenium.type("//input[@id='_86_insurance2']",
					RuntimeVariables.replace("0"));
				selenium.type("//input[@id='_86_insurance3']",
					RuntimeVariables.replace("0"));
				selenium.type("//input[@id='_86_insurance4']",
					RuntimeVariables.replace("0"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");

			case 100:
				label = -1;
			}
		}
	}
}