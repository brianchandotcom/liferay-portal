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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.poshi.runner.util.StringPool;
import com.liferay.poshi.runner.util.StringUtil;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import org.junit.Assert;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * @author Cheryl Tang
 */
public class SegmentForm {

	@Then("^I should see that there are no criteria fields$")
	public void assertDeletedCriteria() throws Exception {
		_faroSelenium.assertText(
			"//div[@class='criteria-message']",
			"Drag and drop criterion from the right to add rules.");
	}

	@Then("^I should see that criteria field is duplicated$")
	public void assertDuplicatedCriteria() throws Exception {
		List<WebElement> duplicateWebElement = _faroSelenium.findElements(
			"//div[@class='criterion']");

		Assert.assertEquals(
			duplicateWebElement.toString(), 2, duplicateWebElement.size());
	}

	@Then("^I should see editor label is (.*)$")
	public void assertEditorLabel(
			@Transform(FaroTransformer.class) String label)
		throws Exception {

		_faroSelenium.assertText(
			"//div[contains(@class,'label-root')]",
			StringUtil.toUpperCase(label));
	}

	/**
	 * Asserts that a missing criteria field is highlighted red by checking its
	 * CSS values for a specific color.
	 *
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see that a criteria field is missing$")
	public void assertMissingCriteria() throws Exception {
		_faroSelenium.assertText(
			"//div[@class='non-existent-property-message']",
			"Attribute no longer exists.");
	}

	/**
	 * Asserts that a user should be on specified page of creation modal
	 *
	 * @param pageNumber the page user should be on
	 */
	@Then("^I should be on page (.*) in the creation modal$")
	public void assertPageInCreationModal(
			@Transform(FaroTransformer.class) String pageNumber)
		throws Exception {

		_faroSelenium.assertText(
			"//li[@class='page-item active']/button", pageNumber);
	}

	/**
	 * Clicks the button to view the Dynamic Segment membership preview modal
	 *
	 * @throws Exception if an exception occurred
	 */
	@When("^I click the Dynamic Segment membership preview$")
	public void clickDynamicSegmentMembershipPreview() throws Exception {
		Thread.sleep(500);

		_faroSelenium.click(
			"//div[@class='total-members']/following-sibling::button");

		_faroSelenium.waitForPageLoadingComplete();
		_faroSelenium.waitForLoadingComplete();
	}

	/**
	 * Clicks the switch to include anonymous
	 *
	 * @throws Exception if an exception occurred
	 */
	@When("^I click the Include Anonymous switch$")
	public void clickIncludeAnonymousSwitch() throws Exception {
		_faroSelenium.click("//label[@for='includeAnonymousUsers']/span/span");

		_faroSelenium.waitForPageLoadingComplete();
		_faroSelenium.waitForLoadingComplete();
	}

	/**
	 * Creates a criteria group with a conditions described in the datatable.
	 *
	 * @param dataTable the table of criteria in the format:
	 *        '| Field | Condition | Value |'
	 */
	@When(
		"^I create an? (nested )?\\s?(AND|OR)?\\s?criteria with the following (Date|Session|Web Behavior)?\\s?conditions?:$"
	)
	public void createCriteria(
			String nestedFlag, String conjunction, String dateFlag,
			DataTable dataTable)
		throws Exception {

		List<List<String>> dataTableRows = dataTable.raw();

		for (List<String> row : dataTableRows) {
			String property = row.get(0);

			WebElement webElement = _faroSelenium.findElement(
				"//div[contains(@class,'criteria')]//input[@type='text'" +
					"and @placeholder='Search' and not(@disabled)]");

			webElement.clear();

			webElement.sendKeys(property);

			webElement.sendKeys(Keys.RETURN);

			_faroSelenium.waitForPageLoadingComplete();

			if (nestedFlag != null) {
				_faroSelenium.dragAndDropChrome(
					"div.sidebar-collapse > ul > li > ul > " +
						"li.criteria-sidebar-item-root",
					"div.criterion div.criterion-row-root");
			}
			else {
				_faroSelenium.dragAndDropChrome(
					"div.sidebar-collapse > ul > li > ul > " +
						"li.criteria-sidebar-item-root",
					"div.criteria-builder-root div.drop-zone-target");
			}

			StringBundler sb = new StringBundler(6);

			sb.append("//select[@class='form-control criterion-input']");
			sb.append("| //div[text()='");
			sb.append(property);
			sb.append("']//following::select[1] | //b[text()='");
			sb.append(property);
			sb.append("']//preceding::select[1]");

			Select conditionDropdown = new Select(
				_faroSelenium.findElement(sb.toString()));

			conditionDropdown.selectByVisibleText(row.get(1));

			if (row.size() > 2) {
				String criteriaValue = row.get(2);

				if ((dateFlag != null) && dateFlag.equals("Date")) {
					String trimmedCriteriaValue = criteriaValue.trim();

					String[] splitDate = trimmedCriteriaValue.split("\\s+");

					String month = splitDate[0];
					String day = splitDate[1];
					String year = splitDate[2];

					_faroSelenium.click(
						"//div[text()='" + property +
							"']//following::input[1]");

					Select monthSelector = new Select(
						_faroSelenium.findElement(
							"//select[@class='" +
								"date-picker-select-root form-control'][1]"));

					monthSelector.selectByVisibleText(month);

					Select yearSelector = new Select(
						_faroSelenium.findElement(
							"//select[@class='" +
								"date-picker-select-root form-control'][2]"));

					yearSelector.selectByVisibleText(year);

					_faroSelenium.click(
						StringBundler.concat(
							"//table[@class='calendar-root']//butto",
							"n[@class='button-root btn btn-unstyled day-root",
							"' and text()='", day, "']"));
				}
				else if ((dateFlag != null) &&
						 dateFlag.equals("Web Behavior")) {

					_faroSelenium.click(
						"//b[text()='" + property + "']//following::button[1]");

					WebElement searchValueFieldWebElement =
						_faroSelenium.findElement(
							"//input[@type='text' and @placeholder" +
								"= 'Search' and not(@disabled)]");

					searchValueFieldWebElement.sendKeys(criteriaValue);

					searchValueFieldWebElement.sendKeys(Keys.RETURN);

					StringBundler searchValueSB = new StringBundler(3);

					searchValueSB.append("//table//*[contains(text(),'");
					searchValueSB.append(criteriaValue);
					searchValueSB.append("')]/ancestor::tr//input");

					String xpath = searchValueSB.toString();

					_faroSelenium.waitForElementPresent(xpath);

					WebElement xpathWebElement = _faroSelenium.findElement(
						xpath);

					xpathWebElement.sendKeys(StringPool.SPACE);

					_faroSelenium.click("//button[text()='Add']");

					StringBundler atLeastMostSB = new StringBundler(3);

					atLeastMostSB.append("//b[text()='");
					atLeastMostSB.append(property);
					atLeastMostSB.append("']//following::select[1]");

					String xpathAtLeastMost = atLeastMostSB.toString();

					Select atLeastMostDropdown = new Select(
						_faroSelenium.findElement(xpathAtLeastMost));

					String atLeastMostValue = row.get(3);

					atLeastMostDropdown.selectByVisibleText(atLeastMostValue);

					WebElement numberTimesWebElement =
						_faroSelenium.findElement(
							"//b[text()='" + property +
								"']//following::input[2]");

					numberTimesWebElement.sendKeys(
						Keys.chord(Keys.CONTROL, "a"));

					numberTimesWebElement.sendKeys(Keys.BACK_SPACE);

					numberTimesWebElement.sendKeys(row.get(4));

					String criteriaOption = row.get(5);

					StringBundler criteriaTimeSB = new StringBundler(3);

					criteriaTimeSB.append("//b[text()='");
					criteriaTimeSB.append(property);
					criteriaTimeSB.append("']//following::select[2]");

					Select criteriaTimeDropdown = new Select(
						_faroSelenium.findElement(criteriaTimeSB.toString()));

					criteriaTimeDropdown.selectByVisibleText(criteriaOption);

					if (!criteriaOption.equals("ever")) {
						String itemOption = row.get(6);

						if (criteriaOption.equals("since")) {
							StringBundler timeSinceSB = new StringBundler(3);

							timeSinceSB.append("//b[text()='");
							timeSinceSB.append(property);
							timeSinceSB.append("']//following::select[3]");

							Select timeSinceDropdown = new Select(
								_faroSelenium.findElement(
									timeSinceSB.toString()));

							timeSinceDropdown.selectByVisibleText(itemOption);
						}
						else {
							String trimDateValue = itemOption.trim();

							String[] splitDateValue = trimDateValue.split(
								"\\s+");

							String month1 = splitDateValue[0];
							String day1 = splitDateValue[1];
							String year1 = splitDateValue[2];

							_faroSelenium.click(
								"//div[.='" + property +
									"']//following::input[3]");

							Select monthSelector1 = new Select(
								_faroSelenium.findElement(
									"//select[@class='" +
										"date-picker-select-root form-control" +
											"'][1]"));

							monthSelector1.selectByVisibleText(month1);

							Select yearSelector1 = new Select(
								_faroSelenium.findElement(
									"//select[@class='" +
										"date-picker-select-root form-control" +
											"'][2]"));

							yearSelector1.selectByVisibleText(year1);

							_faroSelenium.click(
								StringBundler.concat(
									"//table[@class='calendar-root']//butto",
									"n[@class='button-root btn btn-unstyled ",
									"day-root' and text()='", day1, "']"));

							if (criteriaOption.equals("between")) {
								String itemOption1 = row.get(7);

								String trimDateValue1 = itemOption1.trim();

								String[] splitDateValue1 = trimDateValue1.split(
									"\\s+");

								String month2 = splitDateValue1[0];
								String day2 = splitDateValue1[1];
								String year2 = splitDateValue1[2];

								Select monthSelector2 = new Select(
									_faroSelenium.findElement(
										"//select[@class='" +
											"date-picker-select-root " +
												"form-control'][1]"));

								monthSelector2.selectByVisibleText(month2);

								Select yearSelector2 = new Select(
									_faroSelenium.findElement(
										"//select[@class='" +
											"date-picker-select-root " +
												"form-control'][2]"));

								yearSelector2.selectByVisibleText(year2);

								_faroSelenium.click(
									StringBundler.concat(
										"//table[@class='calendar-root'",
										"]//button[@class='button-root btn ",
										"btn-unstyled day-root' and text()='",
										day2, "']"));
							}
						}
					}
				}
				else if (!criteriaValue.isEmpty()) {
					WebElement criteriaValueFieldWebElement =
						_faroSelenium.findElement(
							"//div[text()='" + property +
								"']//following::input[1]");

					String criteriaValueField =
						criteriaValueFieldWebElement.getText();

					if (criteriaValueField.equals("")) {
						criteriaValueFieldWebElement.sendKeys(criteriaValue);

						if (_faroSelenium.isElementNotPresent(
								"//input[@type='number']")) {

							_faroSelenium.click(
								StringBundler.concat(
									"//ul[contains(@class,'dropdown-menu ",
									"show')]//button[text()='", criteriaValue,
									"']"));
						}
					}

					if ((dateFlag != null) && dateFlag.equals("Session")) {
						String daysBehavior = row.get(3);

						Select daysCondition = new Select(
							_faroSelenium.findElement(
								"//select[@class=" +
									"'form-control operator-input']"));

						daysCondition.selectByVisibleText(daysBehavior);
					}
				}
			}
		}

		String conjunctionXpath =
			"//div[@class='conjunction-container']/button[1]";

		if ((conjunction != null) &&
			!StringUtil.equalsIgnoreCase(
				conjunction, _faroSelenium.getText(conjunctionXpath))) {

			_faroSelenium.click(conjunctionXpath);

			_faroSelenium.waitForTextCaseInsensitive(
				conjunctionXpath, conjunction);
		}
	}

	/**
	 * Clones or deletes a criteria group by name.
	 *
	 * @param selection clones or deletes criteria
	 * @param rowNumber the row number of criteria to clone or delete
	 */
	@When("^I (delete|duplicate) the criteria group in row (.*)$")
	public void deleteCriteriaGroup(
		@Transform(FaroTransformer.class) String selection,
		@Transform(FaroTransformer.class) String rowNumber) {

		WebElement webElement;

		if (selection.equals("delete")) {
			webElement = _faroSelenium.findElement(
				"(//div[@class='actions']/button[@aria-label='Delete'])[" +
					rowNumber + "]");
		}
		else {
			webElement = _faroSelenium.findElement(
				"(//div[@class='actions']/button[@aria-label='Duplicate'])[" +
					rowNumber + "]");
		}

		webElement.click();
	}

	/**
	 * Goes to a specified page in creation modal
	 *
	 * @param pageNumber the desired page to go to
	 */
	@When("^I go to page (.*) in the creation modal$")
	public void goToPageInCreationModal(
			@Transform(FaroTransformer.class) String pageNumber)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append("//li[@class='page-item']/button[text()='");
		sb.append(pageNumber);
		sb.append("']");

		_faroSelenium.click(sb.toString());

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();
	}

	/**
	 * Names the current segment, then saves it.
	 *
	 * @param name the name for the segment being created
	 */
	@When("^I name the (?:Dynamic|Static) segment (.*) and save it$")
	public void nameAndSaveSegment(
			@Transform(FaroTransformer.class) String name)
		throws Exception {

		nameSegment(name);

		_faroSelenium.click("//button[@type='submit']");

		_faroSelenium.waitForElementPresent("//a[text()='Edit Segment']");

		FaroTestDataUtil.addSegmentId(name, _faroSelenium.getIdFromURL());
	}

	/**
	 * Names the current segment.
	 *
	 * @param name the name for the segment being created
	 */
	@When("^I name the segment (.*)$")
	public void nameSegment(@Transform(FaroTransformer.class) String name)
		throws Exception {

		_faroSelenium.waitForElementPresent("//h1/following-sibling::div");

		if (_faroSelenium.isPartialText(
				"//h1/following-sibling::div", "DYNAMIC")) {

			_faroSelenium.waitForElementPresent(
				"//div[contains(@class,'title-display')]//button");

			WebElement nameButtonWebElement = _faroSelenium.findElement(
				"//div[contains(@class,'editor')]");

			_faroSelenium.click(nameButtonWebElement);

			Thread.sleep(200);
		}

		_faroSelenium.sendKeys("//input[@name='name']", name);

		_faroSelenium.click("//h1");
	}

	@When("^I select (.*) from the criterion type dropdown$")
	public void selectCriterionType(
			@Transform(FaroTransformer.class) String criterionType)
		throws Exception {

		_faroSelenium.click(
			"//div[@class='criteria-sidebar-root']//div[@class=" +
				"'dropdown dropdown-root']/button");
		ClickSteps.selectDropdownOverlayOption(null, criterionType);
	}

	@When("^I select the ellipsis in row (.*)$")
	public void selectEllipsis(
		@Transform(FaroTransformer.class) String rowNumber) {

		WebElement webElement = _faroSelenium.findElement(
			"(//div[@class='actions']/div[contains(@class,'dropdown')])[" +
				rowNumber + "]");

		webElement.click();
	}

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}