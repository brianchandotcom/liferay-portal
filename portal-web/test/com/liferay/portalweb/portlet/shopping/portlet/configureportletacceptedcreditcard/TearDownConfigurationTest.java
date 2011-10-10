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

package com.liferay.portalweb.portlet.shopping.portlet.configureportletacceptedcreditcard;

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
				selenium.open("/web/guest/home/");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Shopping Test Page")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Shopping Test Page",
					RuntimeVariables.replace("Shopping Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//strong/a",
					RuntimeVariables.replace("Options"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a",
					RuntimeVariables.replace("Configuration"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible("link=Payment Settings")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("link=Payment Settings",
					RuntimeVariables.replace("Payment Settings"));
				selenium.waitForPageToLoad("30000");

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//select[@id='_86_current_cc_types']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean amexAvailable = selenium.isPartialText("_86_available_cc_types",
						"American Express");

				if (!amexAvailable) {
					label = 2;

					continue;
				}

				selenium.addSelection("//select[@id='_86_available_cc_types']",
					RuntimeVariables.replace("label=American Express"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[2]/div/span/span/button[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//div[2]/div/span/span/button[2]",
					RuntimeVariables.replace("Left Arrow"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText(
									"//select[@id='_86_current_cc_types']",
									"American Express")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 2:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//select[@id='_86_current_cc_types']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean discoverAvailable = selenium.isPartialText("_86_available_cc_types",
						"Discover");

				if (!discoverAvailable) {
					label = 3;

					continue;
				}

				selenium.addSelection("//select[@id='_86_available_cc_types']",
					RuntimeVariables.replace("label=Discover"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[2]/div/span/span/button[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//div[2]/div/span/span/button[2]",
					RuntimeVariables.replace("Left Arrow"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText(
									"//select[@id='_86_current_cc_types']",
									"Discover")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 3:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//select[@id='_86_current_cc_types']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean masterCardAvailable = selenium.isPartialText("_86_available_cc_types",
						"MasterCard");

				if (!masterCardAvailable) {
					label = 4;

					continue;
				}

				selenium.addSelection("//select[@id='_86_available_cc_types']",
					RuntimeVariables.replace("label=MasterCard"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[2]/div/span/span/button[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//div[2]/div/span/span/button[2]",
					RuntimeVariables.replace("Left Arrow"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText(
									"//select[@id='_86_current_cc_types']",
									"MasterCard")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

			case 4:

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//select[@id='_86_current_cc_types']")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				boolean visaAvailable = selenium.isPartialText("_86_available_cc_types",
						"Visa");

				if (!visaAvailable) {
					label = 5;

					continue;
				}

				selenium.addSelection("//select[@id='_86_available_cc_types']",
					RuntimeVariables.replace("label=Visa"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isVisible(
									"//div[2]/div/span/span/button[2]")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

				selenium.clickAt("//div[2]/div/span/span/button[2]",
					RuntimeVariables.replace("Left Arrow"));

				for (int second = 0;; second++) {
					if (second >= 90) {
						fail("timeout");
					}

					try {
						if (selenium.isPartialText(
									"//select[@id='_86_current_cc_types']",
									"Visa")) {
							break;
						}
					}
					catch (Exception e) {
					}

					Thread.sleep(1000);
				}

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