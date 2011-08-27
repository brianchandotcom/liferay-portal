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

package com.liferay.portalweb.portlet.shopping.coupon.addcouponcodenumber;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddCouponCodeNumberTest extends BaseTestCase {
	public void testAddCouponCodeNumber() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
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

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Coupons", RuntimeVariables.replace("Coupons"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Add Coupon']",
			RuntimeVariables.replace("Add Coupon"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_34_code']",
			RuntimeVariables.replace("123456789"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_34_name']",
			RuntimeVariables.replace("Shopping Coupon Name"));
		selenium.saveScreenShotAndSource();
		selenium.type("//textarea[@id='_34_description']",
			RuntimeVariables.replace("Shopping Coupon Description"));
		selenium.saveScreenShotAndSource();
		selenium.type("//input[@id='_34_discount']",
			RuntimeVariables.replace("0.50"));
		selenium.saveScreenShotAndSource();
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace(
				"Your request failed to complete."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[1]"));
		assertEquals(RuntimeVariables.replace("Please enter a valid code."),
			selenium.getText("xPath=(//div[@class='portlet-msg-error'])[2]"));
	}
}