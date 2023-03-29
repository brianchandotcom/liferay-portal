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
import com.liferay.osb.faro.functional.test.steps.ClickSteps;
import com.liferay.osb.faro.functional.test.util.FaroSeleniumUtil;
import com.liferay.osb.faro.functional.test.util.FaroTestDataUtil;
import com.liferay.osb.faro.functional.test.util.FaroTransformer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;

import org.openqa.selenium.WebElement;

/**
 * @author Cheryl Tang
 */
public class Table {

	/**
	 * Gets the list of names returned by the search results.
	 *
	 * @return the list of names returned by the search
	 */
	public static List<String> getSearchResultNames() throws Exception {
		List<String> results = new ArrayList<>();

		_faroSelenium.waitForElementPresent(_ENTITY_LIST_ITEM);

		for (WebElement webElement :
				_faroSelenium.findElements(_ENTITY_LIST_ITEM)) {

			String webElementText = webElement.getText();

			if (!webElementText.equals(StringPool.BLANK)) {
				results.add(webElementText);
			}
		}

		return results;
	}

	public static int tableItemCount() {
		List<WebElement> webElements = _faroSelenium.findElements(
			_ENTITY_LIST_ITEM);

		return webElements.size();
	}

	/**
	 * Asserts that a list of items in a Table is sorted using a specified
	 * {@code sortMethod}.
	 *
	 * @param sortMethod the sorting direction used to assert the list of items,
	 *        either ascending or descending
	 */
	@Then(
		"^I should see the (?:.*) sorted in (ascending|descending) alphabetical order$"
	)
	public void assertAlphabeticalSortOrder(String sortMethod) {
		List<WebElement> webElementList = _faroSelenium.findElements(
			"//td[contains(@class,'name-cell-root')]//a|//div" +
				"[@class='table-title text-truncate']");

		List<String> tableNames = new ArrayList<>();

		webElementList.forEach(
			webElement -> tableNames.add(webElement.getText()));

		List<String> sortedNames = new ArrayList<>(tableNames);

		if (sortMethod.equals("ascending")) {
			Collections.sort(sortedNames);

			Assert.assertEquals(sortedNames, tableNames);
		}
		else if (sortMethod.equals("descending")) {
			sortedNames.sort(Collections.reverseOrder());

			Assert.assertEquals(sortedNames, tableNames);
		}
	}

	@Then("^I should see (.*) columns in the bar graph table$")
	public void assertBarGraphColumnAmount(
		@Transform(FaroTransformer.class) String number) {

		StringBundler sb = new StringBundler(8);

		sb.append("//*[name()='g' and contains(@class,'recharts-line')]");
		sb.append("/*[name()='path']");

		try {
			WebElement rechart = _faroSelenium.findElement(sb.toString());

			String barPoint = rechart.getAttribute("d");

			String[] countArray = barPoint.split(",", -1);

			int count = countArray.length - 1;

			String counter = String.valueOf(count);

			Assert.assertEquals(number, counter);
		}
		catch (Exception exception) {
			sb.append("|//*[name()='svg' and @class='recharts-surface']");
			sb.append("/*[name()='g' and contains(@class,'recharts-bar')]");
			sb.append("/*[name()='g']//*[name()='g' and contains(@class,'");
			sb.append("rectangle')][");
			sb.append(number);
			sb.append("]");

			_faroSelenium.findElement(sb.toString());
		}
	}

	/**
	 * Asserts breakdown name matches first result
	 *
	 * @param  dataTable the ordered table items
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see the following names in \"(.*)\" breakdown:$")
	public void assertBreakdownNames(String name, DataTable dataTable)
		throws Exception {

		_faroSelenium.waitForLoadingComplete();

		StringBundler sb = new StringBundler(6);

		sb.append("//*[text()='");
		sb.append(name);
		sb.append("']//ancestor::div[@class='card-header']");
		sb.append("/following-sibling::div[contains(@class,'card-body");
		sb.append("')]//*[name()='g' and contains(@class,'yAxis')]//*");
		sb.append("[name()='text']/*[name()='tspan']");

		List<WebElement> rowWebElements = _faroSelenium.findElements(
			sb.toString());

		Stream<WebElement> stream = rowWebElements.stream();

		Assert.assertEquals(
			dataTable.asList(String.class),
			stream.map(
				WebElement::getText
			).collect(
				Collectors.toList()
			));
	}

	@Then("^I should see \"(.*)\" in the card list \"(.*)\"$")
	public void assertCardListName(
			@Transform(FaroTransformer.class) String name, String cardTitle)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("//h5[text()='");
		sb.append(cardTitle);
		sb.append("']/ancestor::div[contains(@class,'card-root')]//a[text()='");
		sb.append(name);
		sb.append("']");

		_faroSelenium.assertElementPresent(sb.toString());
	}

	@Then("^I should see chart popover (date |formatted YYYY MMM DD)(?:(.*))?$")
	public void assertChartPopoverDateFormat(
			@Transform(FaroTransformer.class) String assertionType,
			@Transform(FaroTransformer.class) String date)
		throws Exception {

		if (assertionType.equals("date")) {
			StringBundler sb = new StringBundler(4);

			sb.append("//div[@class='bb-tooltip-container']/table/thead/tr");
			sb.append("/td[2]/div[text()='");
			sb.append(date);
			sb.append("']");

			_faroSelenium.assertElementPresent(sb.toString());
		}
		else if (assertionType.contains("format")) {
			_faroSelenium.waitForElementPresent(
				"//div[@class='bb-tooltip-container']/table/thead/tr/td[2]" +
					"/div[text()]");

			String dateString = _faroSelenium.getText(
				"//div[@class='bb-tooltip-container']/table/thead/tr/td[2]" +
					"/div[text()]");

			String[] splitDate = dateString.split("\\s+");

			String year = splitDate[0];
			String month = splitDate[1];
			String day = splitDate[2];

			Assert.assertTrue(year.matches("\\d{4}"));
			Assert.assertTrue(
				month.matches(
					"(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)"));
			Assert.assertTrue(day.matches("\\d{1,2}"));
		}
	}

	/**
	 * Asserts the presence of a Data Source by name within an table.
	 *
	 * @param  name the name of the Data Source
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should (not )?see a (.*) Data Source named (.*)$")
	public void assertDataSource(
			String negation, @Transform(FaroTransformer.class) String type,
			@Transform(FaroTransformer.class) String name)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("//table//td[contains(text(),'");
		sb.append(type);
		sb.append("')]/preceding-sibling::td//*[text()='");
		sb.append(name);
		sb.append("']");

		if (negation == null) {
			_faroSelenium.assertElementPresent(sb.toString());
		}
		else {
			_faroSelenium.assertElementNotPresent(sb.toString());
		}
	}

	/**
	 * Checks for the presence of the 'disabled' warning icon in the table row
	 * of a specified table item.
	 *
	 * @param  name the disabled item to check for
	 * @param  negation negation to check if an item is enabled
	 * @throws Exception if an exception occurred
	 */
	@And("^I should see that (.*) is (not )?disabled$")
	public void assertDisabledIcon(
			@Transform(FaroTransformer.class) String name, String negation)
		throws Exception {

		String xpath =
			"//a[text()='" + name +
				"']//ancestor::tr//div[contains(@class,'sticker-warning')]";

		if (negation == null) {
			_faroSelenium.assertElementPresent(xpath);
		}
		else {
			_faroSelenium.assertElementNotPresent(xpath);
		}
	}

	/**
	 * Asserts the name and number of items contained by a single entity within
	 * an table.
	 *
	 * @param type the type of the entity, either 'Account' or 'Segment'
	 * @param name the name that should exclusively be in
	 * @param count the number of child items expected within an entity
	 */
	@Then("^I should see a (.*) named (.*) with (.*) items$")
	public void assertEntityChildrenCount(
			@Transform(FaroTransformer.class) String type,
			@Transform(FaroTransformer.class) String name,
			@Transform(FaroTransformer.class) String count)
		throws Exception {

		_faroSelenium.refreshUntilTextAsserted(
			60, 15, "//a[text()='" + name + "']//ancestor::tr/td[2]", count);
	}

	@Then("^I should (not )?see (?:.*) named (.*) in the table$")
	public void assertEntityListItem(
			String negation, @Transform(FaroTransformer.class) String name)
		throws Exception {

		StringBundler sb = new StringBundler(10);

		sb.append("//table[contains(@class,'table')]//div/*[text()=\"");
		sb.append(name);
		sb.append("\"]|//li[contains(@class,'timeline')]//span[text()=\"");
		sb.append(name);
		sb.append("\"]|//table[contains(@class,'table')]//*[text()=\"");
		sb.append(name);
		sb.append("\"]|//table[contains(@class,'table')]//tr");
		sb.append("[@data-qa-id='row']//span[contains(text(),\'");
		sb.append(name);
		sb.append("\')]");

		try {
			if (negation == null) {
				_faroSelenium.waitForElementPresent(sb.toString());
				_faroSelenium.assertElementPresent(sb.toString());
			}
			else {
				_faroSelenium.assertElementNotPresent(sb.toString());
			}
		}
		catch (Exception exception) {
			if (negation != null) {
				throw exception;
			}

			_faroSelenium.refreshUntilElementPresent(60, 10, sb.toString());
		}
	}

	/**
	 * Asserts that the table either exclusively or does not contain items
	 * matching a name.
	 *
	 * @param  name the name of the entity
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should only see (?:.*) named (.*) in the table$")
	public void assertEntityListNames(
			@Transform(FaroTransformer.class) String name)
		throws Exception {

		_faroSelenium.waitForElementPresent(_ENTITY_LIST_ITEM);

		for (String resultName : getSearchResultNames()) {
			String lowerResultName = StringUtil.toLowerCase(resultName);
			String lowerName = StringUtil.toLowerCase(name);

			Assert.assertTrue(lowerResultName.contains(lowerName));
		}
	}

	/**
	 * Asserts the number of rows elements in the table.
	 *
	 * @param expectedRowCount the number of rows in the table to expect
	 */
	@Then("^I should see (.*) rows in the table$")
	public void assertEntityRowCount(
		@Transform(FaroTransformer.class) String expectedRowCount) {

		List<WebElement> webElements = _faroSelenium.findElements(
			_ENTITY_LIST_ITEM);

		Assert.assertEquals(
			expectedRowCount, String.valueOf(webElements.size()));
	}

	@Then("^I should see an element exists in the table$")
	public void assertExistsTableItem() throws Exception {
		StringBundler sb = new StringBundler(2);

		sb.append("//table[contains(@class,'table')]//div");
		sb.append("|//li[contains(@class,'timeline')]//div");

		_faroSelenium.assertElementPresent(sb.toString());
	}

	/**
	 * Assert item appears on expanded session
	 *
	 * @param  name the name of the item
	 * @throws Exception if an exception occurred
	 */
	@Then(
		"^I should see an expanded item named \"(?:Visited|Submitted) (.*)\"$"
	)
	public void assertExpandedListItem(
			@Transform(FaroTransformer.class) String name)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append("//a[@class='title']/strong[text()='");
		sb.append(name);
		sb.append("']");

		_faroSelenium.waitForElementPresent(sb.toString());
	}

	/**
	 * Asserts items in a bar graph table are in a given order.
	 *
	 * @param dataTable the ordered table of items to assert against
	 */
	@Then("^I should see the following ordered rows in the bar graph table:?$")
	public void assertOrderedBarGraphTable(DataTable dataTable) {
		List<WebElement> webElementList = _faroSelenium.findElements(
			"//tbody//a");

		List<String> dataTableList = dataTable.asList(String.class);

		for (int i = 0; i < webElementList.size(); i++) {
			WebElement webElement = webElementList.get(i);

			Assert.assertEquals(webElement.getText(), dataTableList.get(i));
		}
	}

	/**
	 * Asserts that a table column's items are in a specified order.
	 *
	 * @param  colNum the number of the column to assert, from left to right
	 * @param  dataTable the ordered table of items to assert against
	 * @throws Exception if an exception occured
	 */
	@Then("^I should see the following ordered rows in column (.*):$")
	public void assertOrderedTableColumn(String colNum, DataTable dataTable)
		throws Exception {

		_faroSelenium.waitForLoadingComplete();

		List<String> dataTableNames = dataTable.asList(String.class);

		StringBundler sb = new StringBundler(8);

		sb.append("//tr//td[");
		sb.append(colNum);
		sb.append("]/div[");
		sb.append(colNum);
		sb.append("]");

		if (!colNum.contains("1")) {
			sb.append("|//tr//td[");
			sb.append(colNum);
			sb.append("]");
		}

		List<WebElement> rowWebElements = _faroSelenium.findElements(
			sb.toString());

		Stream<WebElement> stream = rowWebElements.stream();

		List<String> rowNames = stream.map(
			WebElement::getText
		).collect(
			Collectors.toList()
		);

		try {
			Assert.assertEquals(dataTableNames, rowNames);
		}
		catch (AssertionError ae) {
			if (!colNum.equals("1")) {
				throw ae;
			}

			List<String> firstColumnNames = getSearchResultNames();

			Assert.assertEquals(dataTableNames, firstColumnNames);
		}
	}

	/**
	 * Asserts that a user is on a specified page in a paginated table.
	 *
	 * @param  pageNumber the page the user should be on
	 * @throws Exception if an exception occurs
	 */
	@Given("^I should be on page \"(\\d+)\" in the table$")
	public void assertPageInTable(String pageNumber) throws Exception {
		String url = _faroSelenium.getCurrentUrl();

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();

		Assert.assertTrue(url.contains("page=" + pageNumber));

		_faroSelenium.assertText(
			"//li[@class='page-item active']/a", pageNumber);
	}

	/**
	 * Asserts the value of a <td> element in a table that is specified by a
	 * table header name and a row name.
	 *
	 * @param headerName the column name
	 * @param rowName the row name (usually 'Name' header)
	 * @param value the expected value for that <td> element
	 */
	@And("^I should see that the (.*) for (.*) is (.*)$")
	public void assertRowsColumnValue(
			@Transform(FaroTransformer.class) String headerName,
			@Transform(FaroTransformer.class) String rowName,
			@Transform(FaroTransformer.class) String value)
		throws Exception {

		List<WebElement> tableHeaders = _faroSelenium.findElements(
			"//thead//th");

		Stream<WebElement> stream = tableHeaders.stream();

		List<String> rowNames = stream.map(
			WebElement::getText
		).collect(
			Collectors.toList()
		);

		int rowTdOffset = 1;

		int index = rowNames.indexOf(headerName) + rowTdOffset;

		StringBundler sb = new StringBundler(5);

		sb.append("//tr//*[text()='");
		sb.append(rowName);
		sb.append("']//ancestor::tr/td[");
		sb.append(index);
		sb.append("]");

		_faroSelenium.refreshUntilTextAsserted(60, 5, sb.toString(), value);
	}

	/**
	 * Asserts the number of individuals in the results container table.
	 *
	 * @param expectedSearchResultsCount the number of individuals to expect
	 */
	@Then("^I should see (.*) in the individuals count$")
	public void assertSearchResultsCount(String expectedSearchResultsCount) {
		Assert.assertEquals(
			expectedSearchResultsCount, _getSearchResultsCount());
	}

	/**
	 * Asserts a header on mouse over of a table
	 *
	 * @param headerName the header name
	 * @param cardHeader the table name
	 * @param rowNumber the bar graph row number
	 */
	@Then(
		"^I should see mouse over header \"(.*)\" for \"(.*)\" table in row \"(.*)\"$"
	)
	public void assertTableMouseOver(
			@Transform(FaroTransformer.class) String headerName,
			@Transform(FaroTransformer.class) String cardHeader,
			@Transform(FaroTransformer.class) String rowNumber)
		throws Exception {

		StringBundler sb1 = new StringBundler(6);

		sb1.append("//h5[text()='");
		sb1.append(cardHeader);
		sb1.append("']/parent::div/parent::div//*[name()='g' and @class='");
		sb1.append("bb-chart']//*[name()='g']//*[name()='rect'][");
		sb1.append(rowNumber);
		sb1.append("]");

		_faroSelenium.mouseOver(sb1.toString());

		StringBundler sb2 = new StringBundler(3);

		sb2.append(
			"//div[@class='bb-tooltip-container']/table/thead//div[text()='");
		sb2.append(headerName);
		sb2.append("']");

		_faroSelenium.assertElementPresent(sb2.toString());
	}

	/**
	 * Asserts the presence of certain individuals in the results container
	 * table.
	 *
	 * @param expectedResults the comma delimited string of expected individuals
	 */
	@Then("^I should (not )?see the following users: (.*)$")
	public void checkSearchResults(
			String negation, List<String> expectedResults)
		throws Exception {

		List<String> actualResults = getSearchResultNames();

		for (String expectedResult : expectedResults) {
			expectedResult = FaroTestDataUtil.parsePlaceholders(expectedResult);

			if (negation == null) {
				Assert.assertTrue(actualResults.contains(expectedResult));
			}
			else {
				Assert.assertFalse(actualResults.contains(expectedResult));
			}
		}
	}

	/**
	 * Clicks a bar graph in the table
	 *
	 * @param  rowNumber selects the bar graph in that row
	 * @throws Exception if an exception occurred
	 */
	@And("^I click row number \"(.*)\" in the bar graph table$")
	public void clickBarGraphTableItem(
			@Transform(FaroTransformer.class) String rowNumber)
		throws Exception {

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();

		StringBundler sb = new StringBundler(6);

		sb.append("//*[name()='g' and contains(@class,'recharts-line')]|//*");
		sb.append("[name()='g' and contains(@class,'rectangles')]/*[name()");
		sb.append("='g']/*[name()='g' and contains(@class,'rectangle')][");
		sb.append(rowNumber);
		sb.append("]|//*[name()='g' and contains(@class,'");
		sb.append("recharts-area')]/*[name()='g' and @class='recharts-layer']");

		_faroSelenium.mouseOver(sb.toString());

		try {
			_faroSelenium.click(sb.toString());
		}
		catch (Exception exception) {
			_faroSelenium.waitForElementPresent(
				"//*[name()='g' and contains(@class,'recharts-yAxis " +
					"yAxis')]/ancestor::svg/*[name()='path' and contains(@" +
						"class,'recharts-tooltip-cursor')]");
			_faroSelenium.click(
				"//*[name()='g' and contains(@class,'" +
					"recharts-yAxis yAxis')]/ancestor::*[name()='svg']/*[name" +
						"()='g' and contains(@class,'active-dot')]");
		}

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();
	}

	/**
	 * Clicks an inline button for an item in a table.
	 *
	 * @param  button the inline button to click
	 * @param  name the item to click an inline button for
	 * @throws Exception if an exception occurred
	 */
	@And("^I click the inlined (.*) button for the (.*) row$")
	public void clickInlineButton(
			@Transform(FaroTransformer.class) String button,
			@Transform(FaroTransformer.class) String name)
		throws Exception {

		String xpath =
			"//table[contains(@class,'table')]//div/a[text()='" + name + "']";

		_faroSelenium.mouseMove(xpath);

		if (button.equals("Delete")) {
			_faroSelenium.click(xpath + "//following::button[@alt='Delete']");
		}
		else if (button.equals("Edit")) {
			_faroSelenium.click(
				xpath + "//following::a[contains(@href,'edit')]");
		}
		else if (button.equals("Kebab")) {
			_faroSelenium.click(
				xpath + "//following::button[contains(" +
					"@class,'dropdown-toggle')][1]");
		}
	}

	/**
	 * Clicks on an item in the table.
	 *
	 * @param  name the name of the item to click
	 * @throws Exception if an exception occurred
	 */
	@And("^I click (.*) in the table$")
	public void clickTableItem(@Transform(FaroTransformer.class) String name)
		throws Exception {

		StringBundler sb = new StringBundler(7);

		sb.append("//table[contains(@class,'table')]//div/a[text()='");
		sb.append(name);
		sb.append("']|//li[contains(@class,'timeline')]//span[text()='");
		sb.append(name);
		sb.append("']|//a[@class='title']/strong[text()='");
		sb.append(name);
		sb.append("']");

		_faroSelenium.click(sb.toString());

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();
	}

	/**
	 * Goes to a specified page in a paginated table.
	 *
	 * @param pageNumber the desired page to go to
	 */
	@When("^I go to page \"(\\d+)\" in the table$")
	public void goToPageInTable(String pageNumber) {
		String url = _faroSelenium.getCurrentUrl();

		if (url.contains("delta")) {
			url = url.substring(0, url.length() - 1) + pageNumber;
		}
		else if (url.contains("?")) {
			url = url + "&page=" + pageNumber;
		}
		else {
			url = url + "?page=" + pageNumber;
		}

		_faroSelenium.get(url);
	}

	@When("^I mouse over row (.*) in (.*) card$")
	public void mouseOverTable(
			@Transform(FaroTransformer.class) String rowNumber,
			@Transform(FaroTransformer.class) String card)
		throws Exception {

		_faroSelenium.waitForPageLoadingComplete();
		_faroSelenium.waitForLoadingComplete();

		StringBundler sb = new StringBundler(10);

		sb.append("//h5[text()='");
		sb.append(card);
		sb.append("']/parent::div/following-sibling::div//*");
		sb.append("[name()='g' and @class='recharts-layer']");
		sb.append("/*[name()='g' and contains(@class,'rectangle')][");
		sb.append(rowNumber);
		sb.append("]|//*[name()='g' and contains(@class,'recharts-la");
		sb.append("yer')]/*[name()='path' and contains(@class,'curve')]");
		sb.append("|//*[name()='g' and contains(@class,'recharts-car");
		sb.append("tesian')]/*[name()='g' and contains(@class,'grid')]");

		_faroSelenium.waitForElementPresent(sb.toString());

		_faroSelenium.mouseOver(sb.toString());

		try {
			_faroSelenium.assertElementPresent(
				"//div[@class='bb-tooltip-container']/table");
		}
		catch (Exception exception) {
			_faroSelenium.mouseOver(sb.toString());

			_faroSelenium.waitForElementPresent(
				"//div[@class='bb-tooltip-container']/table");
		}
	}

	/**
	 * Sets the pagination delta to a desired value.
	 *
	 * @param  delta the desired pagination delta
	 * @throws Exception if an exception occurred
	 */
	@When("^I set the pagination delta to (.*)$")
	public void setPaginationDelta(
			@Transform(FaroTransformer.class) String delta)
		throws Exception {

		_faroSelenium.click(
			"//div[contains(@class,'pagination-items-per-page')]/button");

		ClickSteps.clickDropdownOption(delta);

		_faroSelenium.waitForLoadingComplete();

		_faroSelenium.waitForText(
			"//div[contains(@class,'pagination-items-per-page')]/button" +
				"/span[@class='text-truncate']",
			delta + " Items");

		_faroSelenium.waitForLoadingComplete();
	}

	/**
	 * Sorts a table by a column header in a desired order.
	 *
	 * @param  columnHeader the table column header to sort by
	 * @param  sortOrder the order to sort in, either ascending or descending
	 * @throws Exception if an exception occurred
	 */
	@And(
		"^I sort the table by the (.*) column header in (ascending|descending) order$"
	)
	public void sortByColumnHeader(
			@Transform(FaroTransformer.class) String columnHeader,
			String sortOrder)
		throws Exception {

		String columnHeaderXPath = "//th//span[text()='" + columnHeader + "']";

		StringBundler sb = new StringBundler(3);

		sb.append(columnHeaderXPath);
		sb.append("//following-sibling::span/*[contains(@class,'arrow-");

		if (sortOrder.equals("ascending")) {
			sb.append("up')]");
		}
		else if (sortOrder.equals("descending")) {
			sb.append("down')]");
		}

		String arrowXPath = sb.toString();

		while (!_faroSelenium.isElementPresent(arrowXPath)) {
			_faroSelenium.click(columnHeaderXPath);

			_faroSelenium.waitForLoadingComplete();
			_faroSelenium.waitForPageLoadingComplete();
		}
	}

	@Then("^I should see top result (.*) in the (.*) card$")
	public void topTableResult(
			@Transform(FaroTransformer.class) String result,
			@Transform(FaroTransformer.class) String cardTitle)
		throws Exception {

		_faroSelenium.assertText(
			StringBundler.concat(
				"//h5[text()='", cardTitle, "']/ancestor::div[contains",
				"(@class,'interest')]//tbody[1]//span[text()='", result, "']"),
			result);
	}

	/**
	 * Gets the number of search results.
	 *
	 * @return the number of search results
	 */
	private String _getSearchResultsCount() {
		WebElement webElement = _faroSelenium.findElement(
			"//div[@class='autofit-col'][2]");

		String text = webElement.getText();

		return text.split(StringPool.SPACE)[0];
	}

	private static final String _ENTITY_LIST_ITEM =
		"//table//div/a|//table//h4";

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}