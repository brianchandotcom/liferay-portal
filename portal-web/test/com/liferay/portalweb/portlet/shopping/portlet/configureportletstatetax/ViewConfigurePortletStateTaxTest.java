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

package com.liferay.portalweb.portlet.shopping.portlet.configureportletstatetax;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewConfigurePortletStateTaxTest extends BaseTestCase {
	public void testViewConfigurePortletStateTax() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Shopping Test Page",
					RuntimeVariables.replace("Shopping Test Page"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Cart", RuntimeVariables.replace("Cart"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isPartialText(
						"//tr[@class='portlet-section-body results-row last']/td[2]/a",
						"Shopping Category Item Name"));
				assertTrue(selenium.isPartialText(
						"//tr[@class='portlet-section-body results-row last']/td[2]/a",
						"Shopping Category Item Description"));
				assertTrue(selenium.isPartialText(
						"//tr[@class='portlet-section-body results-row last']/td[2]/a",
						"Availability:"));
				assertEquals(RuntimeVariables.replace("In Stock"),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isPartialText(
						"//tr[@class='portlet-section-body results-row last']/td[2]/a",
						"Price for 1 to 1 Items:"));
				assertTrue(selenium.isPartialText(
						"//tr[@class='portlet-section-body results-row last']/td[2]/a",
						"9.99"));
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
					RuntimeVariables.replace("4111111111111111"));
				selenium.select("//select[@id='_34_ccExpYear']",
					RuntimeVariables.replace("2014"));
				selenium.type("//textarea[@id='_34_comments']",
					RuntimeVariables.replace("Shopping Category Item Comments"));
				selenium.clickAt("//input[@value='Continue']",
					RuntimeVariables.replace("Continue"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Billing Address"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/strong"));
				assertEquals(RuntimeVariables.replace("First Name:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[1]"));
				assertEquals(RuntimeVariables.replace("Joe"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[1]/td[2]"));
				assertEquals(RuntimeVariables.replace("Last Name:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]"));
				assertEquals(RuntimeVariables.replace("Bloggs"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]"));
				assertEquals(RuntimeVariables.replace("Email Address:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]"));
				assertEquals(RuntimeVariables.replace("test@liferay.com"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]"));
				assertEquals(RuntimeVariables.replace("Company:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[4]/td[1]"));
				assertEquals(RuntimeVariables.replace("Street:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[5]/td[1]"));
				assertEquals(RuntimeVariables.replace("1234 Sesame Street"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[5]/td[2]"));
				assertEquals(RuntimeVariables.replace("City"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[6]/td[1]"));
				assertEquals(RuntimeVariables.replace("Gotham City:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[6]/td[2]"));
				assertEquals(RuntimeVariables.replace("State:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[7]/td[1]"));
				assertEquals(RuntimeVariables.replace("CA"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[7]/td[2]"));
				assertEquals(RuntimeVariables.replace("Postal Code:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[8]/td[1]"));
				assertEquals(RuntimeVariables.replace("90028"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[8]/td[2]"));
				assertEquals(RuntimeVariables.replace("Country:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[9]/td[1]"));
				assertEquals(RuntimeVariables.replace("USA"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[9]/td[2]"));
				assertEquals(RuntimeVariables.replace("Phone:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[10]/td[1]"));
				assertEquals(RuntimeVariables.replace("626-589-1453"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[1]/table/tbody/tr[10]/td[2]"));
				assertEquals(RuntimeVariables.replace("Shipping Address"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/strong"));
				assertEquals(RuntimeVariables.replace("First Name:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[1]/td[1]"));
				assertEquals(RuntimeVariables.replace("Joe"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[1]/td[2]"));
				assertEquals(RuntimeVariables.replace("Last Name:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[2]/td[1]"));
				assertEquals(RuntimeVariables.replace("Bloggs"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]"));
				assertEquals(RuntimeVariables.replace("Email Address:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[1]"));
				assertEquals(RuntimeVariables.replace("test@liferay.com"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]"));
				assertEquals(RuntimeVariables.replace("Company:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[4]/td[1]"));
				assertEquals(RuntimeVariables.replace("Street:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[5]/td[1]"));
				assertEquals(RuntimeVariables.replace("1234 Sesame Street"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]"));
				assertEquals(RuntimeVariables.replace("City"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[6]/td[1]"));
				assertEquals(RuntimeVariables.replace("Gotham City:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]"));
				assertEquals(RuntimeVariables.replace("State:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[7]/td[1]"));
				assertEquals(RuntimeVariables.replace("CA"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]"));
				assertEquals(RuntimeVariables.replace("Postal Code:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[8]/td[1]"));
				assertEquals(RuntimeVariables.replace("90028"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]"));
				assertEquals(RuntimeVariables.replace("Country:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[9]/td[1]"));
				assertEquals(RuntimeVariables.replace("USA"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[9]/td[2]"));
				assertEquals(RuntimeVariables.replace("Phone:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[10]/td[1]"));
				assertEquals(RuntimeVariables.replace("626-589-1453"),
					selenium.getText(
						"//form[@id='_34_fm']/table[1]/tbody/tr/td[2]/table/tbody/tr[10]/td[2]"));
				assertEquals(RuntimeVariables.replace("Credit Card"),
					selenium.getText("//form[@id='_34_fm']/strong[1]"));
				assertEquals(RuntimeVariables.replace("Full Name:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[2]/tbody/tr[1]/td[1]"));
				assertEquals(RuntimeVariables.replace("Joe Bloggs"),
					selenium.getText(
						"//form[@id='_34_fm']/table[2]/tbody/tr[1]/td[2]"));
				assertEquals(RuntimeVariables.replace("Type:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[2]/tbody/tr[2]/td[1]"));
				assertEquals(RuntimeVariables.replace("Visa"),
					selenium.getText(
						"//form[@id='_34_fm']/table[2]/tbody/tr[2]/td[2]"));
				assertEquals(RuntimeVariables.replace("Number:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[2]/tbody/tr[3]/td[1]"));
				assertEquals(RuntimeVariables.replace("************1111"),
					selenium.getText(
						"//form[@id='_34_fm']/table[2]/tbody/tr[3]/td[2]"));
				assertEquals(RuntimeVariables.replace("Expiration Date:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[2]/tbody/tr[4]/td[1]"));
				assertEquals(RuntimeVariables.replace("January, 2014"),
					selenium.getText(
						"//form[@id='_34_fm']/table[2]/tbody/tr[4]/td[2]"));
				assertEquals(RuntimeVariables.replace("Comments"),
					selenium.getText("//form[@id='_34_fm']/strong[2]"));
				assertTrue(selenium.isPartialText("//form[@id='_34_fm']",
						"Shopping Category Item Comments"));
				selenium.waitForVisible(
					"//tr[@class='portlet-section-header results-header']/th[1]");
				assertEquals(RuntimeVariables.replace("SKU"),
					selenium.getText(
						"//tr[@class='portlet-section-header results-header']/th[1]"));
				assertEquals(RuntimeVariables.replace("Description"),
					selenium.getText(
						"//tr[@class='portlet-section-header results-header']/th[2]"));
				assertEquals(RuntimeVariables.replace("Availability"),
					selenium.getText(
						"//tr[@class='portlet-section-header results-header']/th[3]"));
				assertEquals(RuntimeVariables.replace("Quantity"),
					selenium.getText(
						"//tr[@class='portlet-section-header results-header']/th[4]"));
				assertEquals(RuntimeVariables.replace("Price"),
					selenium.getText(
						"//tr[@class='portlet-section-header results-header']/th[5]"));
				assertEquals(RuntimeVariables.replace("Total"),
					selenium.getText(
						"//tr[@class='portlet-section-header results-header']/th[6]"));
				assertEquals(RuntimeVariables.replace("1111"),
					selenium.getText(
						"//tr[@class='portlet-section-body results-row last']/td[1]"));
				assertEquals(RuntimeVariables.replace(
						"Shopping Category Item Name"),
					selenium.getText(
						"//tr[@class='portlet-section-body results-row last']/td[2]"));
				assertEquals(RuntimeVariables.replace("In Stock"),
					selenium.getText(
						"//tr[@class='portlet-section-body results-row last']/td[3]"));
				assertEquals(RuntimeVariables.replace("1"),
					selenium.getText(
						"//tr[@class='portlet-section-body results-row last']/td[4]"));
				assertEquals(RuntimeVariables.replace("$9.99"),
					selenium.getText(
						"//tr[@class='portlet-section-body results-row last']/td[5]"));
				assertEquals(RuntimeVariables.replace("$9.99"),
					selenium.getText(
						"//tr[@class='portlet-section-body results-row last']/td[6]"));
				assertEquals(RuntimeVariables.replace("Showing 1 result."),
					selenium.getText("//div[@class='search-results']"));
				assertEquals(RuntimeVariables.replace("Subtotal:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[3]/tbody/tr[1]/td[1]"));
				assertEquals(RuntimeVariables.replace("$9.99"),
					selenium.getText(
						"//form[@id='_34_fm']/table[3]/tbody/tr[1]/td[2]"));
				assertEquals(RuntimeVariables.replace("Tax:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[3]/tbody/tr[2]/td[1]"));
				assertEquals(RuntimeVariables.replace("$0.72"),
					selenium.getText(
						"//form[@id='_34_fm']/table[3]/tbody/tr[2]/td[2]"));
				assertEquals(RuntimeVariables.replace("Shipping :"),
					selenium.getText(
						"//form[@id='_34_fm']/table[3]/tbody/tr[3]/td[1]"));
				assertEquals(RuntimeVariables.replace("$0.00"),
					selenium.getText(
						"//form[@id='_34_fm']/table[3]/tbody/tr[3]/td[2]"));
				assertEquals(RuntimeVariables.replace("Total:"),
					selenium.getText(
						"//form[@id='_34_fm']/table[3]/tbody/tr[4]/td[1]"));
				assertEquals(RuntimeVariables.replace("$10.71"),
					selenium.getText(
						"//form[@id='_34_fm']/table[3]/tbody/tr[4]/td[2]"));

			case 100:
				label = -1;
			}
		}
	}
}