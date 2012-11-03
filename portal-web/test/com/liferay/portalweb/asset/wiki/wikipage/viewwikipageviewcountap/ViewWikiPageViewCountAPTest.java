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

package com.liferay.portalweb.asset.wiki.wikipage.viewwikipageviewcountap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWikiPageViewCountAPTest extends BaseTestCase {
	public void testViewWikiPageViewCountAP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");

		String viewCount = selenium.getFirstNumberIncrement(
				"//span[@class='metadata-entry metadata-view-count']");
		RuntimeVariables.setValue("viewCount", viewCount);
		selenium.open("/web/guest/home/");
		selenium.waitForVisible("link=Asset Publisher Test Page");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//span[@class='metadata-entry metadata-view-count']",
				RuntimeVariables.getValue("viewCount")));

		String viewCount2 = selenium.getFirstNumberIncrement(
				"//span[@class='metadata-entry metadata-view-count']");
		RuntimeVariables.setValue("viewCount2", viewCount2);
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Test Page",
			RuntimeVariables.replace("Wiki Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText("//div[@class='stats']",
				RuntimeVariables.getValue("viewCount2")));

		String viewCount3 = selenium.getFirstNumberIncrement(
				"//div[@class='stats']");
		RuntimeVariables.setValue("viewCount3", viewCount3);
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//span[@class='metadata-entry metadata-view-count']",
				RuntimeVariables.getValue("viewCount3")));
	}
}