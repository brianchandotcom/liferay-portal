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

package com.liferay.osb.faro.functional.test.pages.fragments;

import com.liferay.osb.faro.functional.test.driver.FaroSelenium;
import com.liferay.osb.faro.functional.test.util.FaroSeleniumUtil;
import com.liferay.osb.faro.functional.test.util.FaroTransformer;
import com.liferay.portal.kernel.util.StringBundler;

import cucumber.api.Transform;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.junit.Assert;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

/**
 * @author Cheryl Tang
 */
public class Toolbar {

	@When("^I search for (.*) in DXP$")
	public static void submitDxpSearchBar(
			@Transform(FaroTransformer.class) String input)
		throws Exception {

		_faroSelenium.waitForElementPresent(_DXP_SEARCH_BAR);

		WebElement webElement = _faroSelenium.findElement(_DXP_SEARCH_BAR);

		webElement.clear();

		webElement.sendKeys(input);

		webElement.sendKeys(Keys.RETURN);

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();
	}

	/**
	 * Clears and enters text into the search bar, then submits the form.
	 *
	 * @param  input the text to enter into the search bar
	 * @throws Exception if an exception occurred
	 */
	@When("^I search for \"(.*)\"$")
	public static void submitSearchBar(
			@Transform(FaroTransformer.class) String input)
		throws Exception {

		WebElement webElement = _faroSelenium.findElement(_SEARCH_BAR);

		webElement.clear();

		webElement.sendKeys(input);

		webElement.sendKeys(Keys.RETURN);

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();
	}

	/**
	 * Asserts the "No {@code item} found" message when a search returns no
	 * results.
	 *
	 * @param  item the type of item for which there are no results
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see a message that there are no (.*) found$")
	public void assertNoResultsFoundMessage(
			@Transform(FaroTransformer.class) String item)
		throws Exception {

		_faroSelenium.waitForElementPresent("//h4[@class='no-results-title']");

		_faroSelenium.assertText(
			"//h4[@class='no-results-title']",
			"There are no " + item + " found.");
	}

	@Then(
		"^I should see (.*) results returned for (.*) in the search results header$"
	)
	public void assertSearchResultsCount(
		@Transform(FaroTransformer.class) String resultsCount,
		@Transform(FaroTransformer.class) String query) {

		WebElement searchResultsHeaderWebElement = _faroSelenium.findElement(
			"//div[@class='tbar-item tbar-item-expand']/div" +
				"[@class='tbar-section text-truncate']");

		String searchResultsHeaderText =
			searchResultsHeaderWebElement.getText();

		Assert.assertTrue(
			searchResultsHeaderText.contains(
				StringBundler.concat(
					resultsCount, " Results for \"", query, "\"")));
	}

	/**
	 * Clicks the select all checkbox in the toolbar.
	 *
	 * @throws Exception if an exception occurred
	 */
	@When("^I click the select all checkbox in the toolbar$")
	public void clickToolbarCheckbox() throws Exception {
		try {
			_faroSelenium.click(_CHECKBOX);
		}
		catch (WebDriverException webDriverException) {
			WebElement checkboxWebElement = _faroSelenium.findElement(
				_CHECKBOX);

			checkboxWebElement.sendKeys(Keys.SPACE);
		}
	}

	@When("^I click the delete icon from the toolbar$")
	public void clickToolbarDelete() throws Exception {
		try {
			_faroSelenium.click(_DELETE);
		}
		catch (WebDriverException webDriverException) {
			WebElement deleteIcon = _faroSelenium.findElement(_DELETE);

			deleteIcon.sendKeys(Keys.SPACE);
		}
	}

	/**
	 * Clicks the sort order button in the toolbar.
	 *
	 * @throws Exception if an exception occurred
	 */
	@When("^I click the sort order button in the toolbar$")
	public void clickToolbarSortOrderButton() throws Exception {
		_faroSelenium.click(_ORDER_BUTTON);

		_faroSelenium.waitForLoadingComplete();

		_faroSelenium.waitForPageLoadingComplete();
	}

	/**
	 * Selects search bar under card title
	 *
	 * @param  input text to enter in the search bar
	 * @param  searchBar card title above search bar
	 * @throws Exception if an exception occurred
	 */
	@When("^I search for \"(.*)\" in the (.*) card$")
	public void selectSearchBar(
			@Transform(FaroTransformer.class) String input, String searchBar)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("//h5[text()='");
		sb.append(searchBar);
		sb.append("']/ancestor::div[contains(@class,'");
		sb.append("card-root')]//input[@type='text' and @placeholder=");
		sb.append("'Search' and not(@disabled)]");

		WebElement webElement = _faroSelenium.findElement(sb.toString());

		webElement.sendKeys(input);

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();
	}

	private static final String _CHECKBOX =
		"//nav[contains(@class,'navbar-root')]//input[@type='checkbox']";

	private static final String _DELETE =
		"//nav[contains(@class,'navbar-root')]/div/ul/button";

	private static final String _DXP_SEARCH_BAR =
		"//input[@type='text' and @placeholder='Search for']";

	private static final String _ORDER_BUTTON =
		"//nav//a[contains(@href,'?orderBy=')]";

	private static final String _SEARCH_BAR =
		"//input[@type='text' and @placeholder = 'Search' and not(@disabled)]";

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}