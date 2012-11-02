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

package com.liferay.portalweb.asset.documentsandmedia.dmdocument.ratedmfolderdocumentap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RateDMFolderDocumentAPTest extends BaseTestCase {
	public void testRateDMFolderDocumentAP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);

		String voteCount = selenium.getFirstNumberIncrement(
				"xPath=(//div[@class='aui-rating-label-element'])[2]");
		RuntimeVariables.setValue("voteCount", voteCount);
		selenium.waitForVisible("//a[5]");
		selenium.clickAt("//a[5]", RuntimeVariables.replace("5 Stars"));
		selenium.waitForPartialText("xPath=(//div[@class='aui-rating-label-element'])[2]",
			RuntimeVariables.getValue("voteCount"));
		assertTrue(selenium.isPartialText(
				"xPath=(//div[@class='aui-rating-label-element'])[2]",
				RuntimeVariables.getValue("voteCount")));
	}
}