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

package com.liferay.portalweb.portlet.activities.portlet.draganddropportletactivities;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DragAndDropPortletActivitiesTest extends BaseTestCase {
	public void testDragAndDropPortletActivities() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/group/joebloggs/home/");
		selenium.clickAt("link=Activities Test Page",
			RuntimeVariables.replace("Activities Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isVisible(
				"//div[@id='column-1']/div/div[contains(@class,'portlet-activities')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-2']/div/div[contains(@class,'portlet-activities')]"));
		assertEquals(RuntimeVariables.replace("Activities"),
			selenium.getText("//span[@class='portlet-title-text']"));
		selenium.clickAt("//span[@class='portlet-title-text']",
			RuntimeVariables.replace("Activities"));
		Thread.sleep(5000);
		selenium.dragAndDropToObject("//span[@class='portlet-title-text']",
			"//div[@id='column-2']");
		selenium.waitForVisible(
			"//div[@id='column-2']/div/div[contains(@class,'portlet-activities')]");
		assertTrue(selenium.isVisible(
				"//div[@id='column-2']/div/div[contains(@class,'portlet-activities')]"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@id='column-1']/div/div[contains(@class,'portlet-activities')]"));
	}
}