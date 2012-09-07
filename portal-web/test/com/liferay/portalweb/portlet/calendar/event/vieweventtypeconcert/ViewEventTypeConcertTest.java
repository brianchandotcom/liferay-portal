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

package com.liferay.portalweb.portlet.calendar.event.vieweventtypeconcert;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewEventTypeConcertTest extends BaseTestCase {
	public void testViewEventTypeConcert() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Calendar Test Page");
		selenium.clickAt("link=Calendar Test Page", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Day", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("label=Concert"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Caedmon's Call Concert."),
			selenium.getText("//div[@class='event-title']"));
		selenium.clickAt("link=Week", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("label=Concert"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Caedmon's Call Concert."),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[1]/a"));
		selenium.clickAt("link=Month", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("//select", RuntimeVariables.replace("label=Concert"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Caedmon's Call Concert."),
			selenium.getText(
				"//tr[@class='portlet-section-alternate results-row alt']/td[1]/a"));
		selenium.clickAt("link=Events", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Caedmon's Call Concert."),
			selenium.getText(
				"//tr[@class='portlet-section-body results-row last']/td[3]/a"));
	}
}