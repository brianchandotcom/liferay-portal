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

package com.liferay.portalweb.portlet.shopping.order.checkoutorder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewCheckoutOrderTest extends BaseTestCase {
	public void testViewCheckoutOrder() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Orders", RuntimeVariables.replace("Orders"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Checkout", RuntimeVariables.replace("Checkout"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Order"),
			selenium.getText("//div[@class='taglib-header ']/h1"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//div[@class='taglib-header ']/span/a"));
		assertEquals(RuntimeVariables.replace("Order #:"),
			selenium.getText("//form[@id='_34_fm']/table[1]/tbody/tr[1]/td[1]"));
		assertTrue(selenium.isVisible(
				"//form[@id='_34_fm']/table[1]/tbody/tr[1]/td[2]"));
		assertEquals(RuntimeVariables.replace("Order Date:"),
			selenium.getText("//form[@id='_34_fm']/table[1]/tbody/tr[2]/td[1]"));
		assertTrue(selenium.isVisible(
				"//form[@id='_34_fm']/table[1]/tbody/tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("Last Modified:"),
			selenium.getText("//form[@id='_34_fm']/table[1]/tbody/tr[3]/td[1]"));
		assertTrue(selenium.isVisible(
				"//form[@id='_34_fm']/table[1]/tbody/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Billing Address"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/strong"));
		assertEquals(RuntimeVariables.replace("First Name:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[1]"));
		assertEquals(RuntimeVariables.replace("Joe"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[1]/td[2]"));
		assertEquals(RuntimeVariables.replace("Last Name:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[2]/td[1]"));
		assertEquals(RuntimeVariables.replace("Bloggs"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("Email Address:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("test@liferay.com"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Company:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[4]/td[1]"));
		assertEquals(RuntimeVariables.replace("Street:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[5]/td[1]"));
		assertEquals(RuntimeVariables.replace("1234 Sesame Street"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[5]/td[2]"));
		assertEquals(RuntimeVariables.replace("City"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[6]/td[1]"));
		assertEquals(RuntimeVariables.replace("Gotham City:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[6]/td[2]"));
		assertEquals(RuntimeVariables.replace("State:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[7]/td[1]"));
		assertEquals(RuntimeVariables.replace("CA"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[7]/td[2]"));
		assertEquals(RuntimeVariables.replace("Postal Code:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[8]/td[1]"));
		assertEquals(RuntimeVariables.replace("90028"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[8]/td[2]"));
		assertEquals(RuntimeVariables.replace("Country:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[9]/td[1]"));
		assertEquals(RuntimeVariables.replace("USA"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[9]/td[2]"));
		assertEquals(RuntimeVariables.replace("Phone:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[10]/td[1]"));
		assertEquals(RuntimeVariables.replace("626-589-1453"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[1]/table/tbody/tr[10]/td[2]"));
		assertEquals(RuntimeVariables.replace("Shipping Address"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/strong"));
		assertEquals(RuntimeVariables.replace("First Name:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[1]"));
		assertEquals(RuntimeVariables.replace("Joe"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[1]/td[2]"));
		assertEquals(RuntimeVariables.replace("Last Name:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[1]"));
		assertEquals(RuntimeVariables.replace("Bloggs"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("Email Address:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("test@liferay.com"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Company:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[4]/td[1]"));
		assertEquals(RuntimeVariables.replace("Street:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[5]/td[1]"));
		assertEquals(RuntimeVariables.replace("1234 Sesame Street"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[5]/td[2]"));
		assertEquals(RuntimeVariables.replace("City"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[6]/td[1]"));
		assertEquals(RuntimeVariables.replace("Gotham City:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[6]/td[2]"));
		assertEquals(RuntimeVariables.replace("State:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[7]/td[1]"));
		assertEquals(RuntimeVariables.replace("CA"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[7]/td[2]"));
		assertEquals(RuntimeVariables.replace("Postal Code:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[8]/td[1]"));
		assertEquals(RuntimeVariables.replace("90028"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[8]/td[2]"));
		assertEquals(RuntimeVariables.replace("Country:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[9]/td[1]"));
		assertEquals(RuntimeVariables.replace("USA"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[9]/td[2]"));
		assertEquals(RuntimeVariables.replace("Phone:"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[10]/td[1]"));
		assertEquals(RuntimeVariables.replace("626-589-1453"),
			selenium.getText(
				"//form[@id='_34_fm']/table[2]/tbody/tr/td[2]/table/tbody/tr[10]/td[2]"));
		assertEquals(RuntimeVariables.replace("Credit Card"),
			selenium.getText("//form[@id='_34_fm']/strong[1]"));
		assertEquals(RuntimeVariables.replace("Full Name:"),
			selenium.getText("//form[@id='_34_fm']/table[3]/tbody/tr[1]/td[1]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//form[@id='_34_fm']/table[3]/tbody/tr[1]/td[2]"));
		assertEquals(RuntimeVariables.replace("Type:"),
			selenium.getText("//form[@id='_34_fm']/table[3]/tbody/tr[2]/td[1]"));
		assertEquals(RuntimeVariables.replace("Visa"),
			selenium.getText("//form[@id='_34_fm']/table[3]/tbody/tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("Number:"),
			selenium.getText("//form[@id='_34_fm']/table[3]/tbody/tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("************1111"),
			selenium.getText("//form[@id='_34_fm']/table[3]/tbody/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Expiration Date:"),
			selenium.getText("//form[@id='_34_fm']/table[3]/tbody/tr[4]/td[1]"));
		assertEquals(RuntimeVariables.replace("January, 2014"),
			selenium.getText("//form[@id='_34_fm']/table[3]/tbody/tr[4]/td[2]"));
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
		assertEquals(RuntimeVariables.replace("Quantity"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[3]"));
		assertEquals(RuntimeVariables.replace("Price"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[4]"));
		assertEquals(RuntimeVariables.replace("Total"),
			selenium.getText(
				"//tr[@class='portlet-section-header results-header']/th[5]"));
		assertEquals(RuntimeVariables.replace("1111"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[1]"));
		assertEquals(RuntimeVariables.replace("Shopping Category Item Name"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[2]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]"));
		assertEquals(RuntimeVariables.replace("$9.99"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[4]"));
		assertEquals(RuntimeVariables.replace("$9.99"),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[5]"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Subtotal:"),
			selenium.getText("//form[@id='_34_fm']/table[4]/tbody/tr[1]/td[1]"));
		assertEquals(RuntimeVariables.replace("$9.99"),
			selenium.getText("//form[@id='_34_fm']/table[4]/tbody/tr[1]/td[2]"));
		assertEquals(RuntimeVariables.replace("Tax:"),
			selenium.getText("//form[@id='_34_fm']/table[4]/tbody/tr[2]/td[1]"));
		assertEquals(RuntimeVariables.replace("$0.00"),
			selenium.getText("//form[@id='_34_fm']/table[4]/tbody/tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("Shipping"),
			selenium.getText("//form[@id='_34_fm']/table[4]/tbody/tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("$0.00"),
			selenium.getText("//form[@id='_34_fm']/table[4]/tbody/tr[3]/td[2]"));
		assertEquals(RuntimeVariables.replace("Total:"),
			selenium.getText("//form[@id='_34_fm']/table[4]/tbody/tr[4]/td[1]"));
		assertEquals(RuntimeVariables.replace("$9.99"),
			selenium.getText("//form[@id='_34_fm']/table[4]/tbody/tr[4]/td[2]"));
		assertEquals("Invoice",
			selenium.getValue("//form[@id='_34_fm']/div[3]/span[1]/span/input"));
		assertEquals("Resend Confirmation Email",
			selenium.getValue("//form[@id='_34_fm']/div[3]/span[2]/span/input"));
		assertEquals("Send Shipping Email",
			selenium.getValue("//form[@id='_34_fm']/div[3]/span[3]/span/input"));
		assertEquals("Delete",
			selenium.getValue("//form[@id='_34_fm']/div[3]/span[4]/span/input"));
		assertEquals("Cancel",
			selenium.getValue("//form[@id='_34_fm']/div[3]/span[5]/span/input"));
		assertEquals(RuntimeVariables.replace("Comments"),
			selenium.getText(
				"//div[@id='shoppingEditOrderCommentsPanel']/div[1]/div/span"));
		assertEquals(RuntimeVariables.replace(
				"No comments yet. Be the first. Subscribe to Comments"),
			selenium.getText("//form[@id='_34_fm2']/fieldset/div"));
	}
}