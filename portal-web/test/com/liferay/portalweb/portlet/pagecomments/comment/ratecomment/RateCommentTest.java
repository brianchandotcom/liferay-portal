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

package com.liferay.portalweb.portlet.pagecomments.comment.ratecomment;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RateCommentTest extends BaseTestCase {
	public void testRateComment() throws Exception {
		selenium.open("/web/guest/home/");

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Page Comments Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		selenium.clickAt("link=Page Comments Test Page",
			RuntimeVariables.replace("Page Comments Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.saveScreenShotAndSource();

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("0 (0 Votes)")
										.equals(selenium.getText(
								"//div[@class='aui-rating-label-element']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertEquals(RuntimeVariables.replace("Rate this as good."),
			selenium.getText("//div[@class='taglib-ratings thumbs']/div/div/a"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a",
			RuntimeVariables.replace("Rate this as good."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("+1 (1 Vote)")
										.equals(selenium.getText(
								"//div[@class='aui-rating-label-element']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertEquals(RuntimeVariables.replace("Rate this as good."),
			selenium.getText("//div[@class='taglib-ratings thumbs']/div/div/a"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a",
			RuntimeVariables.replace("Rate this as good."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("0 (0 Votes)")
										.equals(selenium.getText(
								"//div[@class='aui-rating-label-element']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertEquals(RuntimeVariables.replace("Rate this as bad."),
			selenium.getText(
				"//div[@class='taglib-ratings thumbs']/div/div/a[2]"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a[2]",
			RuntimeVariables.replace("Rate this as bad."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("-1 (1 Vote)")
										.equals(selenium.getText(
								"//div[@class='aui-rating-label-element']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("-1 (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertEquals(RuntimeVariables.replace("Rate this as bad."),
			selenium.getText(
				"//div[@class='taglib-ratings thumbs']/div/div/a[2]"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a[2]",
			RuntimeVariables.replace("Rate this as bad."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("0 (0 Votes)")
										.equals(selenium.getText(
								"//div[@class='aui-rating-label-element']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertEquals(RuntimeVariables.replace("Rate this as good."),
			selenium.getText("//div[@class='taglib-ratings thumbs']/div/div/a"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a",
			RuntimeVariables.replace("Rate this as good."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("+1 (1 Vote)")
										.equals(selenium.getText(
								"//div[@class='aui-rating-label-element']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertEquals(RuntimeVariables.replace("Rate this as bad."),
			selenium.getText(
				"//div[@class='taglib-ratings thumbs']/div/div/a[2]"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a[2]",
			RuntimeVariables.replace("Rate this as bad."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("-1 (1 Vote)")
										.equals(selenium.getText(
								"//div[@class='aui-rating-label-element']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("-1 (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertEquals(RuntimeVariables.replace("Rate this as bad."),
			selenium.getText(
				"//div[@class='taglib-ratings thumbs']/div/div/a[2]"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a[2]",
			RuntimeVariables.replace("Rate this as bad."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("0 (0 Votes)")
										.equals(selenium.getText(
								"//div[@class='aui-rating-label-element']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("0 (0 Votes)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertEquals(RuntimeVariables.replace("Rate this as bad."),
			selenium.getText(
				"//div[@class='taglib-ratings thumbs']/div/div/a[2]"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a[2]",
			RuntimeVariables.replace("Rate this as bad."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("-1 (1 Vote)")
										.equals(selenium.getText(
								"//div[@class='aui-rating-label-element']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("-1 (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertEquals(RuntimeVariables.replace("Rate this as good."),
			selenium.getText("//div[@class='taglib-ratings thumbs']/div/div/a"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a",
			RuntimeVariables.replace("Rate this as good."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("+1 (1 Vote)")
										.equals(selenium.getText(
								"//div[@class='aui-rating-label-element']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
		assertEquals(RuntimeVariables.replace("+1 (1 Vote)"),
			selenium.getText("//div[@class='aui-rating-label-element']"));
		assertEquals(RuntimeVariables.replace("Rate this as good."),
			selenium.getText("//div[@class='taglib-ratings thumbs']/div/div/a"));
		selenium.clickAt("//div[@class='taglib-ratings thumbs']/div/div/a",
			RuntimeVariables.replace("Rate this as good."));

		for (int second = 0;; second++) {
			if (second >= 60) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("0 (0 Votes)")
										.equals(selenium.getText(
								"//div[@class='aui-rating-label-element']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.saveScreenShotAndSource();
	}
}