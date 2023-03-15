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

package com.liferay.osb.faro.functional.test.pages;

import com.liferay.osb.faro.functional.test.driver.FaroSelenium;
import com.liferay.osb.faro.functional.test.steps.ClickSteps;
import com.liferay.osb.faro.functional.test.steps.InputSteps;
import com.liferay.osb.faro.functional.test.util.FaroPagePool;
import com.liferay.osb.faro.functional.test.util.FaroSeleniumUtil;
import com.liferay.osb.faro.functional.test.util.FaroTestConstants;
import com.liferay.osb.faro.functional.test.util.FaroTransformer;

import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Cheryl Tang
 */
public class DashboardPage {

	/**
	 * Logs in as a specified user then asserts that redirection to the landing
	 * page was successful.
	 *
	 * @param  userName the userName
	 * @param  password the password
	 * @throws Exception if an exception occurred
	 */
	@When("^I login as \"(.*?):(.*?)\"( not selecting workspace)?$")
	public static void login(
			String userName, String password, String workSpaceFlag)
		throws Exception {

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();

		if (_faroSelenium.isTextNotPresent("Your Workspaces")) {
			_faroSelenium.refreshUntilElementPresent(
				180, 10,
				"//fieldset" +
					"[@class='input-container' and not(@disabled='disabled')]");

			_faroSelenium.forceWindowFocus();

			InputSteps.inputText(
				userName, "Email Address", FaroTestConstants.INPUT_TYPE_INPUT);
			InputSteps.inputText(
				password, "Password", FaroTestConstants.INPUT_TYPE_INPUT);

			ClickSteps.clickButton("Sign In");

			_faroSelenium.waitForElementPresent("faroApp");
		}

		if (workSpaceFlag == null) {
			String xpath = "//span[text()='FARO-DEV-liferay']";

			if (_faroSelenium.isElementPresentAfterWait(xpath)) {
				_faroSelenium.click(xpath);
			}

			WebDriverWait waitDriverWait = new WebDriverWait(_faroSelenium, 30);

			waitDriverWait.until(
				webDriver -> {
					String currentUrl = _faroSelenium.getCurrentUrl();

					return currentUrl.contains("/workspace/");
				});

			FaroPagePool.setProjectId(_faroSelenium.getWorkspaceIdFromURL());

			_faroSelenium.setMainWindowHandle();

			_faroSelenium.waitForPageLoadingComplete();
			_faroSelenium.waitForLoadingComplete();

			String newUrl = _faroSelenium.getCurrentUrl();

			if (!newUrl.contains("/settings/")) {
				FaroPagePool.setPropertyId(
					_faroSelenium.getPropertiesIdFromURL());
			}
		}
	}

	@And("^I click the name of the signed in user$")
	public void clickWorkspaceUser() throws Exception {
		_faroSelenium.click("//button[contains(@class, 'user-menu')]");
	}

	/**
	 * Clicks an option from workspace userName option
	 *
	 * @param targetName the target element's name or another identifier
	 */
	@When("^I click the (.*) User Menu option$")
	public void clickWorkspaceUserOption(
			@Transform(FaroTransformer.class) String targetName)
		throws Exception {

		WebElement webElement = _faroSelenium.findElement(
			"//div[contains(@class, 'dropdown-menu')]//*[text()='" +
				targetName + "']");

		webElement.click();

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();
	}

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}