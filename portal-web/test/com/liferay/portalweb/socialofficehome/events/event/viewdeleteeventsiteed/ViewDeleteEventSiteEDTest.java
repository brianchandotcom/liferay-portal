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

package com.liferay.portalweb.socialofficehome.events.event.viewdeleteeventsiteed;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewDeleteEventSiteEDTest extends BaseTestCase {
	public void testViewDeleteEventSiteED() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		selenium.waitForText("xPath=(//span[@class='portlet-title-text'])[4]",
			"Events");
		assertEquals(RuntimeVariables.replace("Events"),
			selenium.getText("xPath=(//span[@class='portlet-title-text'])[4]"));
		assertEquals(RuntimeVariables.replace("There are no more events today."),
			selenium.getText("//div[2]/div/div[2]/div/section/div/div/div"));
		assertTrue(selenium.isElementNotPresent("//h2[contains(.,'Events')]"));
		assertTrue(selenium.isElementNotPresent("//span[@class='event-name']/a"));
		assertFalse(selenium.isTextPresent("Calendar Event Title"));
	}
}