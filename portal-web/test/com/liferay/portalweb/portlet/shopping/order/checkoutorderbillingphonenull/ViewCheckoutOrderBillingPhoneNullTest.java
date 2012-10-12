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

package com.liferay.portalweb.portlet.shopping.order.checkoutorderbillingphonenull;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewCheckoutOrderBillingPhoneNullTest extends BaseTestCase {
	public void testViewCheckoutOrderBillingPhoneNull()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Shopping Test Page",
			RuntimeVariables.replace("Shopping Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Orders", RuntimeVariables.replace("Orders"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("No orders were found."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertFalse(selenium.isTextPresent("test@liferay.com"));
		assertFalse(selenium.isTextPresent("1234 Sesame Street"));
		assertFalse(selenium.isTextPresent("Gotham City"));
		assertFalse(selenium.isTextPresent("CA"));
		assertFalse(selenium.isTextPresent("90028"));
		assertFalse(selenium.isTextPresent("USA"));
		assertFalse(selenium.isTextPresent("626-589-1453"));
		assertFalse(selenium.isTextPresent("Visa"));
		assertFalse(selenium.isTextPresent("4111111111111111"));
		assertFalse(selenium.isTextPresent("2014"));
		assertFalse(selenium.isTextPresent("Shopping Category Item Comments"));
	}
}