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

package com.liferay.portalweb.portlet.wikidisplay.comment.ratewdfrontpagecomment;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RateWDFrontPageCommentTest extends BaseTestCase {
	public void testRateWDFrontPageComment() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Wiki Display Test Page",
			RuntimeVariables.replace("Wiki Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//div[contains(@id,'ratingThumbContent')]/div"));
		selenium.clickAt("//div[contains(@id,'ratingThumbContent')]/a[contains(@class,'thumb-down')]",
			RuntimeVariables.replace("Thumb Down"));
		selenium.waitForText("//div[contains(@id,'ratingThumbContent')]/div",
			"-1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("-1 (1 Vote)"),
			selenium.getText("//div[contains(@id,'ratingThumbContent')]/div"));
		selenium.clickAt("//div[contains(@id,'ratingThumbContent')]/a[contains(@class,'thumb-down')]",
			RuntimeVariables.replace("Thumb Down"));
		selenium.waitForText("//div[contains(@id,'ratingThumbContent')]/div",
			"0 (0 Votes)");
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//div[contains(@id,'ratingThumbContent')]/div"));
		selenium.clickAt("//div[contains(@id,'ratingThumbContent')]/a[contains(@class,'thumb-up')]",
			RuntimeVariables.replace("Thumb Up"));
		selenium.waitForText("//div[contains(@id,'ratingThumbContent')]/div",
			"+1 (1 Vote)");
		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("//div[contains(@id,'ratingThumbContent')]/div"));
	}
}