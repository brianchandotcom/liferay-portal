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

package com.liferay.portalweb.portlet.shopping.order.invoiceorder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class InvoiceOrderTest extends BaseTestCase {
	public void testInvoiceOrder() throws Exception {
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
		selenium.clickAt("link=Orders", RuntimeVariables.replace("Orders"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[3]/td[5]"));
		assertEquals(RuntimeVariables.replace("Checkout"),
			selenium.getText("//td[4]/a"));
		selenium.clickAt("//td[4]/a", RuntimeVariables.replace("Checkout"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("Shopping Category Item Comments"));
		selenium.clickAt("//input[@value='Invoice']",
			RuntimeVariables.replace("Invoice"));
		selenium.waitForPopUp("", RuntimeVariables.replace("30000"));
		selenium.selectWindow("name=undefined");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Invoice")
										.equals(selenium.getText(
								"//form[@id='_34_fm']/span/strong"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Invoice"),
			selenium.getText("//form[@id='_34_fm']/span/strong"));
		selenium.close();
		selenium.selectWindow("null");
	}
}