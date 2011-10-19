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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescope;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class Guest_ViewConfigurePortlet2MaximumItemsToDisplay1Test
	extends BaseTestCase {
	public void testGuest_ViewConfigurePortlet2MaximumItemsToDisplay1()
		throws Exception {
		selenium.open("/web/blogs-page-scope-community/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Blogs Test Page2")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Blogs Test Page2",
			RuntimeVariables.replace("Blogs Test Page2"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs (Blogs Test Page2)")
										.equals(selenium.getText(
								"//span[@class='portlet-title-text']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs (Blogs Test Page2)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("Showing 1 - 1 of 2 results."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Items per Page 1"),
			selenium.getText("//div[@class='delta-selector']"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//span[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//span[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//a[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//a[@class='last']"));
		selenium.select("//select[@id='_33_page']",
			RuntimeVariables.replace("label=2"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry2 Title")
										.equals(selenium.getText(
								"//div[@class='entry-title']/h2/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("Showing 2 - 2 of 2 results."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Items per Page 1"),
			selenium.getText("//div[@class='delta-selector']"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//a[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//a[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//span[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//span[@class='last']"));
		selenium.select("//select[@id='_33_page']",
			RuntimeVariables.replace("label=1"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry3 Title")
										.equals(selenium.getText(
								"//div[@class='entry-title']/h2/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("Showing 1 - 1 of 2 results."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Items per Page 1"),
			selenium.getText("//div[@class='delta-selector']"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//span[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//span[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//a[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//a[@class='last']"));
		selenium.clickAt("//a[@class='next']", RuntimeVariables.replace("Next"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry2 Title")
										.equals(selenium.getText(
								"//div[@class='entry-title']/h2/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("Showing 2 - 2 of 2 results."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Items per Page 1"),
			selenium.getText("//div[@class='delta-selector']"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//a[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//a[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//span[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//span[@class='last']"));
		selenium.clickAt("//a[@class='previous']",
			RuntimeVariables.replace("Previous"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry3 Title")
										.equals(selenium.getText(
								"//div[@class='entry-title']/h2/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("Showing 1 - 1 of 2 results."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Items per Page 1"),
			selenium.getText("//div[@class='delta-selector']"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//span[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//span[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//a[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//a[@class='last']"));
		selenium.clickAt("//a[@class='last']", RuntimeVariables.replace("Last"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry2 Title")
										.equals(selenium.getText(
								"//div[@class='entry-title']/h2/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("Showing 2 - 2 of 2 results."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Items per Page 1"),
			selenium.getText("//div[@class='delta-selector']"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//a[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//a[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//span[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//span[@class='last']"));
		selenium.clickAt("//a[@class='first']",
			RuntimeVariables.replace("First"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry3 Title")
										.equals(selenium.getText(
								"//div[@class='entry-title']/h2/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("Showing 1 - 1 of 2 results."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Items per Page 1"),
			selenium.getText("//div[@class='delta-selector']"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//span[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//span[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//a[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//a[@class='last']"));
		selenium.open("/web/blogs-page-scope-community/");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Blogs Test Page3")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Blogs Test Page3",
			RuntimeVariables.replace("Blogs Test Page3"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs (Blogs Test Page2)")
										.equals(selenium.getText(
								"//span[@class='portlet-title-text']"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs (Blogs Test Page2)"),
			selenium.getText("//span[@class='portlet-title-text']"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry3 Title")
										.equals(selenium.getText(
								"//div[@class='entry-title']/h2/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("Showing 1 - 1 of 2 results."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Items per Page 1"),
			selenium.getText("//div[@class='delta-selector']"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//span[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//span[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//a[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//a[@class='last']"));
		selenium.select("//select[@id='_33_page']",
			RuntimeVariables.replace("label=2"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry2 Title")
										.equals(selenium.getText(
								"//div[@class='entry-title']/h2/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("Showing 2 - 2 of 2 results."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Items per Page 1"),
			selenium.getText("//div[@class='delta-selector']"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//a[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//a[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//span[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//span[@class='last']"));
		selenium.select("//select[@id='_33_page']",
			RuntimeVariables.replace("label=1"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry3 Title")
										.equals(selenium.getText(
								"//div[@class='entry-title']/h2/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("Showing 1 - 1 of 2 results."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Items per Page 1"),
			selenium.getText("//div[@class='delta-selector']"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//span[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//span[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//a[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//a[@class='last']"));
		selenium.clickAt("//a[@class='next']", RuntimeVariables.replace("Next"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry2 Title")
										.equals(selenium.getText(
								"//div[@class='entry-title']/h2/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("Showing 2 - 2 of 2 results."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Items per Page 1"),
			selenium.getText("//div[@class='delta-selector']"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//a[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//a[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//span[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//span[@class='last']"));
		selenium.clickAt("//a[@class='previous']",
			RuntimeVariables.replace("Previous"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry3 Title")
										.equals(selenium.getText(
								"//div[@class='entry-title']/h2/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("Showing 1 - 1 of 2 results."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Items per Page 1"),
			selenium.getText("//div[@class='delta-selector']"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//span[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//span[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//a[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//a[@class='last']"));
		selenium.clickAt("//a[@class='last']", RuntimeVariables.replace("Last"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry2 Title")
										.equals(selenium.getText(
								"//div[@class='entry-title']/h2/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry2 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry2 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("Showing 2 - 2 of 2 results."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Items per Page 1"),
			selenium.getText("//div[@class='delta-selector']"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//a[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//a[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//span[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//span[@class='last']"));
		selenium.clickAt("//a[@class='first']",
			RuntimeVariables.replace("First"));
		selenium.waitForPageToLoad("30000");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Blogs Entry3 Title")
										.equals(selenium.getText(
								"//div[@class='entry-title']/h2/a"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Blogs Entry3 Title"),
			selenium.getText("//div[@class='entry-title']/h2/a"));
		assertEquals(RuntimeVariables.replace("Blogs Entry3 Content"),
			selenium.getText("//div[@class='entry-body']/p"));
		assertEquals(RuntimeVariables.replace("Showing 1 - 1 of 2 results."),
			selenium.getText("//div[@class='search-results']"));
		assertEquals(RuntimeVariables.replace("Items per Page 1"),
			selenium.getText("//div[@class='delta-selector']"));
		assertEquals(RuntimeVariables.replace("First"),
			selenium.getText("//span[@class='first']"));
		assertEquals(RuntimeVariables.replace("Previous"),
			selenium.getText("//span[@class='previous']"));
		assertEquals(RuntimeVariables.replace("Next"),
			selenium.getText("//a[@class='next']"));
		assertEquals(RuntimeVariables.replace("Last"),
			selenium.getText("//a[@class='last']"));
	}
}