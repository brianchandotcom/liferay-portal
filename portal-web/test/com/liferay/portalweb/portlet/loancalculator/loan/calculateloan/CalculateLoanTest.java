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

package com.liferay.portalweb.portlet.loancalculator.loan.calculateloan;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CalculateLoanTest extends BaseTestCase {
	public void testCalculateLoan() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Loan Calculator Test Page",
			RuntimeVariables.replace("Loan Calculator Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_61_loanAmount']",
			RuntimeVariables.replace("1,000"));
		selenium.type("//input[@name='_61_interest']",
			RuntimeVariables.replace("4.75"));
		selenium.type("//input[@name='_61_years']",
			RuntimeVariables.replace("20"));
		selenium.clickAt("//input[@value='Calculate']",
			RuntimeVariables.replace("Calculate"));
		selenium.waitForText("//tr[6]/td[2]/strong", "1,551");
		assertEquals(RuntimeVariables.replace("1,551"),
			selenium.getText("//tr[6]/td[2]/strong"));
	}
}