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

package com.liferay.portalweb.plugins.samplespring.portlet.addportletdu;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletDUTest extends BaseTestCase {
	public void testViewPortletDU() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Description Upload Test Page",
			RuntimeVariables.replace("Description Upload Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Upload a Pet Description Here"),
			selenium.getText("//div[@class='portlet-body']/h1"));
		assertEquals(RuntimeVariables.replace(
				"Please upload a small text file (under 2K) and the contents will be added as a description for the pet you choose."),
			selenium.getText("//div[@class='portlet-body']/p"));
		assertTrue(selenium.isVisible("//select[@name='selectedPet']"));
		assertTrue(selenium.isVisible("//input[@name='file']"));
		assertEquals(RuntimeVariables.replace("Upload"),
			selenium.getText("//button[@type='submit']"));
	}
}