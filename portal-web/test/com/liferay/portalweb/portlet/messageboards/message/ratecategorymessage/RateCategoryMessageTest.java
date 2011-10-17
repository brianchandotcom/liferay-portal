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

package com.liferay.portalweb.portlet.messageboards.message.ratecategorymessage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RateCategoryMessageTest extends BaseTestCase {
	public void testRateCategoryMessage() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Message Boards Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//a/strong"));
		selenium.clickAt("//a/strong",
			RuntimeVariables.replace("MB Category Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"MB Category Thread Message Subject"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("MB Category Thread Message Subject"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText(
				"//div[@class='taglib-ratings thumbs']/div/div/div"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down']"));
		assertFalse(selenium.isElementPresent(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up aui-rating-element-on']"));
		assertFalse(selenium.isElementPresent(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down aui-rating-element-on']"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a[1]",
			RuntimeVariables.replace("Thumb Up"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("+1 (1 Vote)")
										.equals(selenium.getText(
								"//div[@class='taglib-ratings thumbs']/div/div/div"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText(
				"//div[@class='taglib-ratings thumbs']/div/div/div"));
		assertFalse(selenium.isElementPresent(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up aui-rating-element-on']"));
		assertFalse(selenium.isElementPresent(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down aui-rating-element-on']"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a[2]",
			RuntimeVariables.replace("Thumb Down"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("-1 (1 Vote)")
										.equals(selenium.getText(
								"//div[@class='taglib-ratings thumbs']/div/div/div"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("-1 (1 Vote)"),
			selenium.getText(
				"//div[@class='taglib-ratings thumbs']/div/div/div"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up']"));
		assertFalse(selenium.isElementPresent(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down']"));
		assertFalse(selenium.isElementPresent(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up aui-rating-element-on']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down aui-rating-element-on']"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a[2]",
			RuntimeVariables.replace("Thumb Down"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("0 (0 Votes)")
										.equals(selenium.getText(
								"//div[@class='taglib-ratings thumbs']/div/div/div"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText(
				"//div[@class='taglib-ratings thumbs']/div/div/div"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up']"));
		assertTrue(selenium.isVisible(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down']"));
		assertFalse(selenium.isElementPresent(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-up aui-rating-element-on']"));
		assertFalse(selenium.isElementPresent(
				"//a[@class='aui-rating-element aui-rating-element-off aui-rating-thumb-down aui-rating-element-on']"));
	}
}