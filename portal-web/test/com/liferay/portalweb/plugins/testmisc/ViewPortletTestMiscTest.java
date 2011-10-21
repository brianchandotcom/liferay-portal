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

package com.liferay.portalweb.plugins.testmisc;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPortletTestMiscTest extends BaseTestCase {
	public void testViewPortletTestMisc() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Test Misc Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Test Misc Page",
			RuntimeVariables.replace("Test Misc Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Portlet Request"),
			selenium.getText("//div/div/h3[1]"));
		assertEquals(RuntimeVariables.replace("Attribute Sharing"),
			selenium.getText("//p[1]/a[1]"));
		assertEquals(RuntimeVariables.replace("Remote User"),
			selenium.getText("//p[1]/a[2]"));
		assertEquals(RuntimeVariables.replace(
				"Portlet Response (ActionResponse, Normal State)"),
			selenium.getText("//h3[2]"));
		assertEquals(RuntimeVariables.replace("Download File"),
			selenium.getText("//p[2]/a"));
		assertEquals(RuntimeVariables.replace(
				"Portlet Response (ActionResponse, Exclusive State)"),
			selenium.getText("//h3[3]"));
		assertEquals(RuntimeVariables.replace("Download File"),
			selenium.getText("//p[3]/a"));
		assertEquals(RuntimeVariables.replace(
				"Portlet Response (ResourceResponse)"),
			selenium.getText("//h3[4]"));
		assertEquals(RuntimeVariables.replace("Buffer Size"),
			selenium.getText("//p[4]/a[1]"));
		assertEquals(RuntimeVariables.replace("Download File"),
			selenium.getText("//p[4]/a[2]"));
		assertEquals(RuntimeVariables.replace("Portlet Session"),
			selenium.getText("//h3[5]"));
		assertEquals(RuntimeVariables.replace("Attribute Sharing"),
			selenium.getText("//p[5]/a"));
		assertTrue(selenium.isPartialText("//p[6]",
				"TestSchedulerMessageListener.isReceived=PASSED"));
		assertTrue(selenium.isPartialText("//p[6]",
				"TestSchedulerUtil.isScheduledBeforeSpringInitialized=PASSED"));
		assertTrue(selenium.isPartialText("//p[6]",
				"TestSchedulerUtil.isReceivedBeforeSpringInitialzed=PASSED"));
		assertEquals(RuntimeVariables.replace("Servlet Request"),
			selenium.getText("//h3[7]"));
		assertEquals(RuntimeVariables.replace("Remote User"),
			selenium.getText("//p[7]/a"));
		assertEquals(RuntimeVariables.replace("Upload"),
			selenium.getText("//h3[8]"));
		assertEquals(RuntimeVariables.replace("Form 1"),
			selenium.getText("//p[8]/a[1]"));
		assertEquals(RuntimeVariables.replace("Form 2"),
			selenium.getText("//p[8]/a[2]"));
		assertEquals(RuntimeVariables.replace("Form 3"),
			selenium.getText("//a[3]"));
	}
}