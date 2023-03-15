/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.functional.test.steps;

import com.liferay.osb.faro.functional.test.driver.FaroSelenium;
import com.liferay.osb.faro.functional.test.pages.DashboardPage;
import com.liferay.osb.faro.functional.test.pages.fragments.Table;
import com.liferay.osb.faro.functional.test.util.FaroSeleniumUtil;
import com.liferay.osb.faro.functional.test.util.FaroTestDataUtil;
import com.liferay.poshi.runner.selenium.SeleniumUtil;
import com.liferay.poshi.runner.util.GetterUtil;
import com.liferay.poshi.runner.util.PropsUtil;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Cheryl Tang
 */
public class FaroHooks {

	@Before
	public static void setUp() {
		SeleniumUtil.getSelenium();
	}

	@After
	public static void tearDown(Scenario scenario) throws Exception {
		FaroSelenium faroSelenium = FaroSeleniumUtil.getFaroSelenium();

		faroSelenium.switchToMainWindow();

		if (scenario.isFailed()) {
			faroSelenium.saveScreenshot();
		}

		WebDriver.Options manage = faroSelenium.manage();

		manage.deleteAllCookies();

		FaroTestDataUtil.cleanUp();

		PropsUtil.set(
			"analytics.cloud.url",
			System.getProperty("analytics.cloud.url", "http://localhost:8080"));
	}

	@After("@Keywords")
	public static void tearDownKeyword() throws Exception {
		_tableDeleteAll("Keywords");
	}

	@After("@Property, @Data_Source")
	public static void tearDownProperty() throws Exception {
		_tableDeleteProperties();
	}

	@After("@Token")
	public static void tearDownToken() throws Exception {
		FaroTestDataUtil.deleteTokenDataSource();
	}

	private static void _tableDeleteAll(String page) throws Exception {
		FaroSelenium faroSelenium = FaroSeleniumUtil.getFaroSelenium();

		NavigationSteps.goToURL(page, "page");

		faroSelenium.waitForLoadingComplete();
		faroSelenium.waitForPageLoadingComplete();

		if (faroSelenium.isElementNotPresent("//h2[text()='Settings']")) {
			DashboardPage.login("test@faro.io", "test", null);

			NavigationSteps.goToURL(page, "page");

			faroSelenium.waitForElementPresent("//h2[text()='Settings']");
		}

		if (faroSelenium.isElementNotPresent(
				"//div[contains(@class,'no-results-root')]")) {

			WebElement checkboxWebElement = faroSelenium.findElement(
				"//div[@class='toolbar-root']//input[@type='checkbox']");

			checkboxWebElement.sendKeys(Keys.SPACE);

			faroSelenium.click("//button[contains(@class,'nav-btn')]");

			faroSelenium.click("//button[text()='Continue']");

			faroSelenium.waitForElementPresent(
				"//div[contains(@class,'no-results-root')]");
		}
	}

	private static void _tableDeleteProperties() throws Exception {
		FaroSelenium faroSelenium = FaroSeleniumUtil.getFaroSelenium();

		NavigationSteps.goToURL("Properties", "page");

		faroSelenium.waitForLoadingComplete();
		faroSelenium.waitForPageLoadingComplete();

		if (faroSelenium.isElementNotPresent("//h2[text()='Settings']")) {
			DashboardPage.login("test@faro.io", "test", null);

			NavigationSteps.goToURL("Properties", "page");

			faroSelenium.waitForElementPresent("//h2[text()='Settings']");
		}

		if (faroSelenium.isElementNotPresent(
				"//div[contains(@class,'no-results-root')]")) {

			WebElement checkboxWebElement = faroSelenium.findElement(
				"//div[@class='toolbar-root']//input[@type='checkbox']");

			checkboxWebElement.sendKeys(Keys.SPACE);

			WebElement demoDataCheckbox = faroSelenium.findElement(
				"//span[text()='LIFERAY-DATASOURCE-FARO-EXAMPLE']" +
					"/ancestor::tr//input[@type='checkbox']");

			demoDataCheckbox.sendKeys(Keys.SPACE);

			String itemCount = String.valueOf(Table.tableItemCount());

			List<String> list = Table.getSearchResultNames();

			String singleItem = list.get(0);

			if (singleItem.equals("LIFERAY-DATASOURCE-FARO-EXAMPLE") &&
				!itemCount.equals("1")) {

				singleItem = list.get(1);
			}

			if (!itemCount.equals("1")) {
				int minusDemo = GetterUtil.getInteger(itemCount) - 1;

				itemCount = String.valueOf(minusDemo);
			}

			if (itemCount.equals("1") &&
				singleItem.equals("LIFERAY-DATASOURCE-FARO-EXAMPLE")) {
			}
			else if (itemCount.equals("1")) {
				faroSelenium.click("//button[text()='Delete']");

				faroSelenium.waitForLoadingComplete();
				faroSelenium.waitForPageLoadingComplete();

				faroSelenium.type(
					"//input[@name='delete' and @type='text']",
					"Delete " + singleItem);

				faroSelenium.click("//button[text()='Delete']");
			}
			else {
				faroSelenium.click("//button[text()='Delete']");

				faroSelenium.waitForLoadingComplete();
				faroSelenium.waitForPageLoadingComplete();

				faroSelenium.type(
					"//input[@name='delete' and @type='text']",
					"Delete " + itemCount + " Properties");

				faroSelenium.click("//button[text()='Delete']");
			}

			faroSelenium.waitForElementPresent(
				"//span[text()='LIFERAY-DATASOURCE-FARO-EXAMPLE']");

			faroSelenium.refresh();

			faroSelenium.waitForLoadingComplete();
			faroSelenium.waitForPageLoadingComplete();

			String finalCount = String.valueOf(Table.tableItemCount());

			if (!finalCount.equals("1")) {
				throw new Exception("Properties not deleted");
			}
		}
	}

}