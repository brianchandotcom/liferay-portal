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
import com.liferay.petra.string.StringBundler;

import cucumber.api.Transform;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.openqa.selenium.support.ui.Select;

/**
 * @author Cheryl Tang
 */
public class TimeFilter {

	@When("^I set start date (.*) and end date (.*)$")
	public static void inputCustomDate(
			@Transform(FaroTransformer.class) String startDate,
			@Transform(FaroTransformer.class) String endDate)
		throws Exception {

		String trimStartDate = startDate.trim();
		String trimEndDate = endDate.trim();

		String[] splitStartDate = trimStartDate.split("\\s+");
		String[] splitEndDate = trimEndDate.split("\\s+");

		String startMonth = splitStartDate[0];
		String startDay = splitStartDate[1];
		String startYear = splitStartDate[2];

		String endMonth = splitEndDate[0];
		String endDay = splitEndDate[1];
		String endYear = splitEndDate[2];

		Select monthSelector = new Select(
			_faroSelenium.findElement(
				"//select[@class='date-picker-select-root form-control'][1]"));

		monthSelector.selectByVisibleText(startMonth);

		Select yearSelector = new Select(
			_faroSelenium.findElement(
				"//select[@class='date-picker-select-root form-control'][2]"));

		yearSelector.selectByVisibleText(startYear);

		_faroSelenium.click(
			StringBundler.concat(
				"//table[@class='calendar-root']//butto",
				"n[@class='button-root btn btn-unstyled day-root",
				"' and text()='", startDay, "']"));

		monthSelector = new Select(
			_faroSelenium.findElement(
				"//select[@class='date-picker-select-root form-control'][1]"));

		monthSelector.selectByVisibleText(endMonth);

		yearSelector = new Select(
			_faroSelenium.findElement(
				"//select[@class='date-picker-select-root form-control'][2]"));

		yearSelector.selectByVisibleText(endYear);

		_faroSelenium.click(
			StringBundler.concat(
				"//table[@class='calendar-root']//butto",
				"n[@class='button-root btn btn-unstyled day-root",
				"' and text()='", endDay, "']"));
	}

	/**
	 * Asserts that the Custom Range date picker dropdown shows an error message
	 * when a date range exceeding 365 days is selected.
	 *
	 * @throws Exception
	 */
	@Then("^I should see an error saying the range exceeds the maximum range$")
	public void assertMaximumCustomRange() throws Exception {
		_faroSelenium.assertText(
			"//div[@class='range-warning']",
			"This exceeds the maximum range of 365 days.");
	}

	/**
	 * Asserts tht the time filter buttons are disabled.
	 *
	 * @throws Exception
	 */
	@Then("^I should see (?:that )?the time filter is disabled$")
	public void assertTimeFilterIsDisabled() throws Exception {
		_faroSelenium.assertElementPresent("//button[text()='D'][@disabled]");
		_faroSelenium.assertElementPresent("//button[text()='W'][@disabled]");
		_faroSelenium.assertElementPresent("//button[text()='M'][@disabled]");
		_faroSelenium.assertElementPresent(
			"//button[contains(text(),'Last ')][@disabled]");
	}

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}