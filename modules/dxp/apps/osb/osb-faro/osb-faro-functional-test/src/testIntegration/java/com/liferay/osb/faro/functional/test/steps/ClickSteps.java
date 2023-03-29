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
import com.liferay.osb.faro.functional.test.util.FaroSeleniumUtil;
import com.liferay.osb.faro.functional.test.util.FaroTransformer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.poshi.runner.selenium.WebDriverUtil;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.When;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * @author Cheryl Tang
 */
public class ClickSteps {

	/**
	 * Clicks a node in the breadcrumb navigation.
	 *
	 * @param targetName the target element's name or another identifier
	 */
	@When("^I click (.*) in the breadcrumb$")
	public static void clickBreadcrumbItem(
		@Transform(FaroTransformer.class) String targetName) {

		StringBundler sb = new StringBundler(4);

		sb.append("//div[@class='breadcrumb-content']");
		sb.append("/descendant::a[normalize-space(text())=");
		sb.append(targetName);
		sb.append("']");

		WebElement webElement = _faroSelenium.findElement(sb.toString());

		webElement.click();
	}

	public static void clickButton(String targetName) throws Exception {
		clickButton(targetName, null);
	}

	/**
	 * Clicks a button.
	 *
	 * @param targetName the target element's name or another identifier
	 */
	@When("^I click the (.*?) (radio )?button$")
	public static void clickButton(
			@Transform(FaroTransformer.class) String targetName, String radio)
		throws Exception {

		if (radio != null) {
			WebElement webElement = _faroSelenium.findElement(
				"//input[@type='radio']/following-sibling::span[contains(.,'" +
					targetName + "')]");

			webElement.click();

			return;
		}

		StringBundler sb = new StringBundler(11);

		sb.append("//button[normalize-space(text())='");
		sb.append(targetName);
		sb.append("' and not(@disabled)]|//h4[text()='");
		sb.append(targetName);
		sb.append("']/parent::div/parent::div/parent::button|//button");
		sb.append("/descendant-or-self::*[normalize-space(text())='");
		sb.append(targetName);
		sb.append("' and not(@disabled)]|//a/descendant-or-self::");
		sb.append("*[normalize-space(text())='");
		sb.append(targetName);
		sb.append("' and not(@disabled)]");

		String xpath = sb.toString();

		_faroSelenium.waitForLoadingComplete();

		_faroSelenium.waitForElementPresent(xpath);

		try {
			if (!targetName.contains("Authorize")) {
				_faroSelenium.waitForPageLoadingComplete();

				Thread.sleep(1000);

				_faroSelenium.click(xpath);

				_faroSelenium.waitForLoadingComplete();
			}
			else {
				_faroSelenium.click(xpath);
			}
		}
		catch (ElementClickInterceptedException
					elementClickInterceptedException) {

			WebElement webElement = _faroSelenium.findElement(xpath);

			JavascriptExecutor jsExecutor =
				(JavascriptExecutor)WebDriverUtil.getWebDriver();

			jsExecutor.executeScript("arguments[0].click()", webElement);
		}
	}

	/**
	 * Clicks a checkbox.
	 *
	 * @param targetName the target element's name or another identifier
	 */
	@When("^I click the (.*) checkbox$")
	public static void clickCheckbox(
		@Transform(FaroTransformer.class) String targetName) {

		WebElement webElement = _faroSelenium.findElement(
			"//input[@type='checkbox' and contains(.,'" + targetName + "')]");

		webElement.click();
	}

	/**
	 * Clicks a dropdown button.
	 *
	 * @param targetName the target element's name or another identifier
	 */
	@When("^I click the (.*) dropdown$")
	public static void clickDropdown(
		@Transform(FaroTransformer.class) String targetName) {

		WebElement webElement = _faroSelenium.findElement(
			StringBundler.concat(
				"//button[contains(@class, 'dropdown-toggle')]",
				"/span[normalize-space(text())='", targetName, "']"));

		webElement.click();
	}

	/**
	 * Clicks a dropdown button found in a certain card
	 *
	 * @param targetName the target element's name or another identifier
	 * @param cardName the card where the element is found
	 */
	@When("^I click the (.*) dropdown in the(?: )?(.*)? card$")
	public static void clickDropdownCard(
		@Transform(FaroTransformer.class) String targetName,
		@Transform(FaroTransformer.class) String cardName) {

		WebElement webElement;

		if (!cardName.equals("")) {
			webElement = _faroSelenium.findElement(
				StringBundler.concat(
					"//h5[text()='", cardName,
					"']/parent::div//button[text()='", targetName, "']"));
		}
		else {
			webElement = _faroSelenium.findElement(
				"//div[contains(@class,'dropdown')]/button[text()='" +
					targetName + "']");
		}

		webElement.click();
	}

	/**
	 * Clicks an option in a dropdown that contains the partial text.
	 *
	 * @param targetName the target element's name or another identifier
	 */
	@When("^I click the (.*) dropdown option$")
	public static void clickDropdownOption(
			@Transform(FaroTransformer.class) String targetName)
		throws Exception {

		if (!targetName.contains("Last")) {
			WebElement webElement = _faroSelenium.findElement(
				"//div[contains(@class, 'dropdown-menu show')]//*[text()='" +
					targetName + "']");

			webElement.click();
		}
		else {
			WebElement webElement = _faroSelenium.findElement(
				"//div[contains(@class,'show')]/ul/li/button[text()='" +
					targetName + "']/ancestor::li");

			webElement.click();
		}

		if (!targetName.equals("Dynamic Segment")) {
			_faroSelenium.waitForLoadingComplete();
		}

		_faroSelenium.waitForPageLoadingComplete();
	}

	/**
	 * Selects or deselects items from a multiselect box from a table of
	 * selection values.
	 *
	 * @param action the action on the multiselect, either 'select' or
	 *        'deselect'
	 * @param targetName the target element's name or another identifier
	 * @param dataTable the single column table of values to perform action on
	 */
	@When("^I ((?:de)?select) the following in the (.*) multiselect$")
	public static void clickMultiselect(
		String action, @Transform(FaroTransformer.class) String targetName,
		DataTable dataTable) {

		Select select = new Select(
			_faroSelenium.findElement(
				"//select[contains(.,'" + targetName + "')]"));

		for (String selection : dataTable.asList(String.class)) {
			if (action.equals("select")) {
				select.selectByVisibleText(selection);
			}
			else {
				select.deselectByVisibleText(selection);
			}
		}
	}

	/**
	 * Clicks an option from a single-selection dropdown selection.
	 *
	 * @param option the option to select
	 * @param targetName the target element's name or another identifier
	 */
	@When("^I click the (.*) dropdown option from the (.*) dropdown selection$")
	public static void clickSelectOption(
			@Transform(FaroTransformer.class) String option,
			@Transform(FaroTransformer.class) String targetName)
		throws Exception {

		Select select = new Select(
			_faroSelenium.findElement(
				"//option[text()='" + targetName + "']/parent::select"));

		select.selectByVisibleText(option);

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();
	}

	/**
	 * Clicks a tab by name.
	 *
	 * @param targetName the target element's name or another identifier
	 * @param cardFlag determines element is a card tab
	 */
	@When("^I click the \"(.*)\"( card)? tab$")
	public static void clickTab(
			@Transform(FaroTransformer.class) String targetName,
			String cardFlag)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		if (cardFlag != null) {
			sb.append("//ul[contains(@class,'card-tabs-root')]/li[@class='");
			sb.append("card-tab']/a/span[text()='");
			sb.append(targetName);
			sb.append("']");
		}
		else {
			sb.append("//ul[@role='tablist']//*[text()='");
			sb.append(targetName);
			sb.append("']/parent::a[@role='tab']");
		}

		String xpath = sb.toString();

		_faroSelenium.waitForElementPresent(xpath);

		WebElement webElement = _faroSelenium.findElement(xpath);

		webElement.click();

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();
	}

	/**
	 * Clicks a checkbox in a specific table row.
	 *
	 * @param targetName the target element's name or another identifier
	 */
	@When("^I click the checkbox on the table row containing (.*)$")
	public static void clickTableRowCheckbox(
			@Transform(FaroTransformer.class) String targetName)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append(
			"//div[contains(@class, 'entity-list-root')]//a[contains(text(),'");
		sb.append(targetName);
		sb.append("')]/ancestor::li//input|//table//*[contains(text(),'");
		sb.append(targetName);
		sb.append("')]/ancestor::tr//input");

		String xpath = sb.toString();

		_faroSelenium.waitForElementPresent(xpath);

		WebElement webElement = _faroSelenium.findElement(xpath);

		webElement.sendKeys(StringPool.SPACE);
	}

	/**
	 * Optionally searches and selects an option from a currently open overlay
	 * dropdown menu.
	 *
	 * @param searchQuery the search query to filter dropdown menu items
	 * @param targetName the target element's name or another identifier
	 */
	@When(
		"^I (?:search for (.*) and )?click the (.*) dropdown option from the " +
			"dropdown overlay$"
	)
	public static void selectDropdownOverlayOption(
			@Transform(FaroTransformer.class) String searchQuery,
			@Transform(FaroTransformer.class) String targetName)
		throws Exception {

		if (searchQuery != null) {
			_faroSelenium.sendKeys(
				"//div[@class='dropdown-menu show']//input[@placeholder=" +
					"'Search']",
				searchQuery);
		}

		_faroSelenium.waitForPageLoadingComplete();

		_faroSelenium.waitForElementPresent(
			"//button[text()='" + targetName + "']");

		WebElement dropdownWebElement = _faroSelenium.findElement(
			"//div[@class='dropdown-menu show']");

		WebElement dropdownButtonWebElement = _faroSelenium.findElement(
			"//button[text()='" + targetName + "']", dropdownWebElement);

		dropdownButtonWebElement.click();

		_faroSelenium.waitForPageLoadingComplete();
	}

	@When("^I click the (.*) option from the field input overlay$")
	public static void selectFieldInputOverlayOption(
		@Transform(FaroTransformer.class) String targetName) {

		_faroSelenium.waitForPageLoadingComplete();

		WebElement dropdownWebElement = _faroSelenium.findElement(
			"//ul[contains(@class, 'dropdown-menu show')]");

		WebElement dropdownButtonWebElement = _faroSelenium.findElement(
			"//div[text()='" + targetName + "']", dropdownWebElement);

		dropdownButtonWebElement.click();

		_faroSelenium.waitForPageLoadingComplete();
	}

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}