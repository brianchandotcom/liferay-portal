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

package com.liferay.portalweb.portal.dbupgrade.sampledata6012.shopping.order;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CheckOutOrderTest extends BaseTestCase {
	public void testCheckOutOrder() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Communities",
					RuntimeVariables.replace("Communities"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_134_name']",
					RuntimeVariables.replace("Shopping Order Community"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//td[2]/a", RuntimeVariables.replace("Open"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Shopping Order Page",
					RuntimeVariables.replace("Shopping Order Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Cart", RuntimeVariables.replace("Cart"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Checkout']",
					RuntimeVariables.replace("Checkout"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_34_billingStreet']",
					RuntimeVariables.replace("1234 Sesame Street"));
				selenium.type("//input[@id='_34_billingCity']",
					RuntimeVariables.replace("Gotham City"));
				selenium.select("//select[@id='_34_billingStateSel']",
					RuntimeVariables.replace("label=California"));
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
					RuntimeVariables.replace("label=Visa"));
				selenium.type("//input[@id='_34_ccNumber']",
					RuntimeVariables.replace("4111111111111111"));
				selenium.select("//select[@id='_34_ccExpYear']",
					RuntimeVariables.replace("label=2017"));
				selenium.type("//textarea[@id='_34_comments']",
					RuntimeVariables.replace("Please take care of my order."));
				selenium.clickAt("//input[@value='Continue']",
					RuntimeVariables.replace("Continue"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent("1234 Sesame Street"));
				selenium.clickAt("//input[@value='Finished']",
					RuntimeVariables.replace("Finished"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Thank you for your purchase."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.open("/web/guest/home/");
				selenium.waitForElementPresent("link=Control Panel");
				selenium.clickAt("link=Control Panel",
					RuntimeVariables.replace("Control Panel"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Communities",
					RuntimeVariables.replace("Communities"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_134_name']",
					RuntimeVariables.replace("Shopping Order Community"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//td[2]/a", RuntimeVariables.replace("Open"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Shopping Order Page",
					RuntimeVariables.replace("Shopping Order Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Orders",
					RuntimeVariables.replace("Orders"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Checkout",
					RuntimeVariables.replace("Checkout"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isTextPresent(
						"Please take care of my order."));

			case 100:
				label = -1;
			}
		}
	}
}