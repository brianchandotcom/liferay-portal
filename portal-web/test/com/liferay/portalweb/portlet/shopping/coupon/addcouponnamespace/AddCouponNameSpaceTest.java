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

package com.liferay.portalweb.portlet.shopping.coupon.addcouponnamespace;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddCouponNameSpaceTest extends BaseTestCase {
	public void testAddCouponNameSpace() throws Exception {
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
				selenium.clickAt("link=Coupons",
					RuntimeVariables.replace("Coupons"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//input[@value='Add Coupon']",
					RuntimeVariables.replace("Add Coupon"));
				selenium.waitForPageToLoad("30000");

				boolean autogenerateCodeChecked = selenium.isChecked(
						"//input[@id='_34_autoCodeCheckbox']");

				if (autogenerateCodeChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_34_autoCodeCheckbox']",
					RuntimeVariables.replace("Autogenerate Code"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_34_autoCodeCheckbox']"));
				selenium.type("//input[@id='_34_name']",
					RuntimeVariables.replace(" "));
				selenium.type("//textarea[@id='_34_description']",
					RuntimeVariables.replace("Shopping Coupon Description"));
				selenium.type("//input[@id='_34_discount']",
					RuntimeVariables.replace("0.50"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request failed to complete."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"Please enter a valid name."),
					selenium.getText(
						"xPath=(//div[@class='portlet-msg-error'])[2]"));

			case 100:
				label = -1;
			}
		}
	}
}