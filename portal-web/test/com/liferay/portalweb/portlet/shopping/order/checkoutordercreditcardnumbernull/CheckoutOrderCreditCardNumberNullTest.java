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

package com.liferay.portalweb.portlet.shopping.order.checkoutordercreditcardnumbernull;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CheckoutOrderCreditCardNumberNullTest extends BaseTestCase {
	public void testCheckoutOrderCreditCardNumberNull()
		throws Exception {
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
				selenium.clickAt("link=Cart", RuntimeVariables.replace("Cart"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Shopping Category Item Name\nShopping Category Item Description\n\nAvailability: In Stock\n\n\nPrice for 1 Items and Above:$9.99"),
					selenium.getText("//td[2]/a"));
				selenium.clickAt("//input[@value='Checkout']",
					RuntimeVariables.replace("Checkout"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_34_billingStreet']",
					RuntimeVariables.replace("1234 Sesame Street"));
				selenium.type("//input[@id='_34_billingCity']",
					RuntimeVariables.replace("Gotham City"));
				selenium.select("//select[@id='_34_billingStateSel']",
					RuntimeVariables.replace("California"));
				selenium.type("//input[@id='_34_billingZip']",
					RuntimeVariables.replace("90028"));
				selenium.type("//input[@id='_34_billingCountry']",
					RuntimeVariables.replace("USA"));
				selenium.type("//input[@id='_34_billingPhone']",
					RuntimeVariables.replace("626-589-1453"));

				boolean sameAsBillingChecked = selenium.isChecked(
						"_34_shipToBillingCheckbox");

				if (sameAsBillingChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_34_shipToBillingCheckbox']",
					RuntimeVariables.replace(""));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_34_shipToBillingCheckbox']"));
				selenium.select("//select[@id='_34_ccType']",
					RuntimeVariables.replace("Visa"));
				selenium.type("//input[@id='_34_ccNumber']",
					RuntimeVariables.replace(""));
				selenium.select("//select[@id='_34_ccExpYear']",
					RuntimeVariables.replace("2014"));
				selenium.type("//textarea[@id='_34_comments']",
					RuntimeVariables.replace("Shopping Category Item Comments"));
				selenium.clickAt("//input[@value='Continue']",
					RuntimeVariables.replace("Continue"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a valid credit card number."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));

			case 100:
				label = -1;
			}
		}
	}
}