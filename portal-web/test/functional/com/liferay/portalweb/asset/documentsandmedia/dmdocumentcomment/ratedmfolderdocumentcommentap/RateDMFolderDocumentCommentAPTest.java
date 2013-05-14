/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.asset.documentsandmedia.dmdocumentcomment.ratedmfolderdocumentcommentap;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RateDMFolderDocumentCommentAPTest extends BaseTestCase {
	public void testRateDMFolderDocumentCommentAP() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Asset Publisher Test Page",
			RuntimeVariables.replace("Asset Publisher Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForPartialText("//div[@class='rating-label-element']",
			"0 Votes");
		assertTrue(selenium.isPartialText(
				"//div[@class='rating-label-element']", "0 Votes"));
		selenium.clickAt("//div[2]/div/div/div/a",
			RuntimeVariables.replace("Rate this as good."));
		selenium.waitForText("//div[@class='rating-label-element']",
			"+1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("//div[@class='rating-label-element']"));
		assertTrue(selenium.isElementPresent(
				"//a[@class='rating-element rating-element-off rating-thumb-up rating-element-on']"));
		selenium.clickAt("//div/a[2]",
			RuntimeVariables.replace("Rate this as bad."));
		selenium.waitForText("//div[@class='rating-label-element']",
			"-1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("-1 (1 Vote)"),
			selenium.getText("//div[@class='rating-label-element']"));
		assertTrue(selenium.isElementPresent(
				"//a[@class='rating-element rating-element-off rating-thumb-down rating-element-on']"));
		selenium.clickAt("//div/a[2]",
			RuntimeVariables.replace("Rate this as bad."));
		assertTrue(selenium.isElementPresent(
				"//a[@class='rating-element rating-element-off rating-thumb-up']"));
		assertTrue(selenium.isElementPresent(
				"//a[@class='rating-element rating-element-off rating-thumb-down']"));
	}
}