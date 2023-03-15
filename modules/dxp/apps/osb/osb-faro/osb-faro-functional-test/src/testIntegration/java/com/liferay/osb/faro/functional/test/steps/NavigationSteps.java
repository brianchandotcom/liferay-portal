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
import com.liferay.osb.faro.functional.test.util.FaroPagePool;
import com.liferay.osb.faro.functional.test.util.FaroRestUtil;
import com.liferay.osb.faro.functional.test.util.FaroSeleniumUtil;
import com.liferay.osb.faro.functional.test.util.FaroTransformer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.poshi.runner.selenium.WebDriverUtil;
import com.liferay.poshi.runner.util.PropsUtil;

import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Cheryl Tang
 */
public class NavigationSteps {

	/**
	 * Moves back a single "item" in the browser's history.
	 */
	@When("^I go back one page$")
	public static void browserBack() {
		WebDriver webDriver = WebDriverUtil.getWebDriver();

		WebDriver.Navigation navigation = webDriver.navigate();

		navigation.back();
	}

	/**
	 * Moves a single "item" forward in the browser's history. Doesn't do
	 * anything if we are on the latest page viewed.
	 */
	@When("^I go forward one page$")
	public static void browserForward() {
		WebDriver webDriver = WebDriverUtil.getWebDriver();

		WebDriver.Navigation navigation = webDriver.navigate();

		navigation.forward();
	}

	/**
	 * Navigates to a given page or URL.
	 *
	 * @param destination the destination URL path or page name. Usable page
	 *        names listed in {@link FaroPagePool}
	 * @param locationType URL or page name
	 */
	@Given("^I go to the (.*) (page|URL)$")
	public static void goToURL(
			@Transform(FaroTransformer.class) String destination,
			String locationType)
		throws Exception {

		if (locationType.equals("page")) {
			StringBundler sb = new StringBundler(6);

			sb.append(PropsUtil.get("analytics.cloud.url"));

			if (StringUtil.equalsIgnoreCase(destination, "Home")) {
				_faroSelenium.open(sb.toString());

				return;
			}

			sb.append("/workspace/");
			sb.append(FaroPagePool.getProjectId());

			if (!destination.equals("Keywords") &&
				!destination.equals("Properties") &&
				!destination.equals("Data Source") &&
				!destination.equals("Workspace") &&
				!destination.equals("User Management")) {

				sb.append("/");
				sb.append(FaroPagePool.getPropertyId());
			}

			sb.append(_faroPagePool.getPageUrlPath(destination));

			_faroSelenium.open(sb.toString());
		}
		else {
			_faroSelenium.get(destination);
		}

		_faroSelenium.waitForPageLoadingComplete();

		_faroSelenium.waitForLoadingComplete();
	}

	/**
	 * Refreshes the current page.
	 */
	@When("^I refresh the page$")
	public static void refreshPage() {
		FaroRestUtil.clearCache();

		WebDriver webDriver = WebDriverUtil.getWebDriver();

		WebDriver.Navigation navigation = webDriver.navigate();

		navigation.refresh();
	}

	/**
	 * Switches to the focused modal.
	 */
	@When("^I switch to the focused modal$")
	public static void switchToFocusedModal() throws Exception {
		_faroSelenium.waitForElementPresent("//iframe");

		WebDriver webDriver = WebDriverUtil.getWebDriver();

		WebElement modalWebElement = _faroSelenium.findElement("//iframe");

		WebDriver.TargetLocator targetLocator = webDriver.switchTo();

		targetLocator.defaultContent();

		targetLocator.frame(modalWebElement);
	}

	/**
	 * Switches to the main content frame.
	 */
	@When("^I switch to the main frame$")
	public static void switchToMainFrame() {
		WebDriver webDriver = WebDriverUtil.getWebDriver();

		WebDriver.TargetLocator targetLocator = webDriver.switchTo();

		targetLocator.defaultContent();
	}

	@And("^I switch to the main window$")
	public void switchToMainWindow() {
		_faroSelenium.switchToMainWindow();
	}

	@And("^I switch to the popup window$")
	public void switchToPopupWindow() throws Exception {
		_faroSelenium.switchToPopupWindow();
	}

	private static final FaroPagePool _faroPagePool = new FaroPagePool();
	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}