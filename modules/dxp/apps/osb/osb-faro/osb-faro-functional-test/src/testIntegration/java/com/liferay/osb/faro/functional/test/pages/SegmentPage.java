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
import com.liferay.osb.faro.functional.test.util.FaroSeleniumUtil;
import com.liferay.osb.faro.functional.test.util.FaroTestDataUtil;
import com.liferay.osb.faro.functional.test.util.FaroTransformer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

/**
 * @author Cheryl Tang
 */
public class SegmentPage {

	/**
	 * Asserts anonymous label is visible in segment criteria
	 *
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see Include Anonymous label$")
	public void assertIncludeAnonymousLabel() throws Exception {
		String anonymousText = "Include Anonymous";

		_faroSelenium.assertText(
			"//div[@class='card-header']/h5[text()='Segment Criteria']" +
				"/following-sibling::div",
			StringUtil.toUpperCase(anonymousText));
	}

	@Then("^I should see (.*) total members$")
	public void assertMembersTotal(
			@Transform(FaroTransformer.class) String total)
		throws Exception {

		_faroSelenium.assertText(
			"//span[text()='Membership']/parent::a//span[@class='" +
				"primary-content']",
			total);
	}

	/**
	 * Asserts segment composition individuals count
	 *
	 * @param  number amount of individuals
	 * @param  type active or known individuals
	 * @throws Exception if an exception occurred
	 */
	@Then(
		"^I should see segment composition with \"(.*)\" (Active|Known) individuals$"
	)
	public void assertSegmentComposition(
			@Transform(FaroTransformer.class) String number,
			@Transform(FaroTransformer.class) String type)
		throws Exception {

		_faroSelenium.waitForElementPresent(
			"//h5[text()='Segment Composition']");

		StringBundler sb = new StringBundler(5);

		sb.append("//span[text()='");
		sb.append(type);
		sb.append("']/parent::div/following-sibling::div[text()='");
		sb.append(number);
		sb.append("']");

		_faroSelenium.assertElementPresent(sb.toString());
	}

	@Then("^I should see the Segment Criteria with the following details:$")
	public void assertSegmentCriteriaAndDetails(DataTable dataTable)
		throws Exception {

		List<List<String>> dataTableRows = dataTable.raw();

		String membershipXpath =
			"//span[@class='title' and text()='Membership']" +
				"/following-sibling::div//b";

		_faroSelenium.waitForElementPresent(membershipXpath);

		for (List<String> row : dataTableRows) {
			StringBundler sb = new StringBundler(9);

			sb.append("//div[text()='");
			sb.append(row.get(0));
			sb.append("']//self::b[text()='");
			sb.append(row.get(1));
			sb.append("']//following-sibling::span[text()='");
			sb.append(row.get(2));
			sb.append("']//following-sibling::b[contains(text(),'");
			sb.append(row.get(3));
			sb.append("')]");

			_faroSelenium.assertWebElementHasAnyText(sb.toString());
		}
	}

	/**
	 * Asserts the Segment Profile's cards and checks that there is some text
	 * for the Membership count and Engagement Score.
	 *
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see the Segment Profile Cards with their details$")
	public void assertSegmentProfileCardsAndDetails() throws Exception {
		String membershipXpath =
			"//span[@class='title' and text()='Membership']" +
				"/following-sibling::div//b";

		_faroSelenium.waitForElementPresent(membershipXpath);
		_faroSelenium.assertWebElementHasAnyText(membershipXpath);

		String segmentEngagementXpath =
			"//span[@class='title' and text()='Segment Engagement Score']";

		_faroSelenium.assertElementPresent(segmentEngagementXpath);
		_faroSelenium.assertWebElementHasAnyText(segmentEngagementXpath);

		_faroSelenium.assertText(
			"//div[contains(@class,'interests-card')]//*[@class='card-header']",
			"Top Interests");
		_faroSelenium.assertText(
			"//div[contains(@class,'distribution-card')]//*" +
				"[@class='description']/h4",
			"Add a Breakdown by Attribute");
	}

	/**
	 * Deletes segments listed in a cucumber data table via API.
	 *
	 * @param  dataTable the cucumber data table with segments listed by name
	 * @throws Exception if an exception occurred
	 */
	@When("^I delete the following segments:$")
	public void deleteSegments(DataTable dataTable) throws Exception {
		List<List<String>> dataTableRows = dataTable.raw();

		for (List<String> dataTableRow : dataTableRows) {
			String segmentName = dataTableRow.get(0);

			FaroTestDataUtil.deleteSegment(
				FaroTestDataUtil.getSegmentId(segmentName));
		}
	}

	@When(
		"^I select breakdown \"(Individuals|Accounts)\" by \"(.*)\"( with number of bins )?(.*)?$"
	)
	public void selectDistributionBreakdown(
			@Transform(FaroTransformer.class) String breakdownList,
			String distribution, String numberProp, String binNumber)
		throws Exception {

		Select breakdownDropdownSelect = new Select(
			_faroSelenium.findElement(
				"//div[contains(@class,'form-group-item')]/select" +
					"[contains(@class,'form-control')]"));

		breakdownDropdownSelect.selectByVisibleText(breakdownList);

		WebElement distributionWebElement = _faroSelenium.findElement(
			"//div[contains(@class,'input-group')]/div/input");

		distributionWebElement.sendKeys(distribution);

		_faroSelenium.click("//div[contains(@class,'input-group')]/div/input");

		_faroSelenium.click(
			StringBundler.concat(
				"//button[contains(@class,'dropdown-item')]/div/div[@class='",
				"field-details']/span[text()='", distribution, "']"));

		if (numberProp != null) {
			WebElement binWebElement = _faroSelenium.findElement(
				"//input[@name='numberOfBins']");

			binWebElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));

			binWebElement.sendKeys(binNumber);
		}

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();
	}

	@When(
		"^I create breakdown context \"(Individuals|Accounts)\" by \"(.*)\"( with bin number )?(.*)? named (.*)$"
	)
	public void selectDistributionCardBreakdown(
			@Transform(FaroTransformer.class) String breakdownList,
			String distribution, String binFlag, String binNumber, String name)
		throws Exception {

		Select contextDropdownSelect = new Select(
			_faroSelenium.findElement(
				"//select[contains(@class,'form-control')]"));

		contextDropdownSelect.selectByVisibleText(breakdownList);

		WebElement propertyWebElement = _faroSelenium.findElement(
			"//input[contains(@class,'input-group-inset')]");

		propertyWebElement.sendKeys(distribution);

		_faroSelenium.click(
			"//ul[contains(@class,'dropdown-menu show')]//span[text()='" +
				distribution + "']");

		if (binFlag != null) {
			WebElement binWebElement = _faroSelenium.findElement(
				"//input[@name='numberOfBins']");

			binWebElement.sendKeys(Keys.chord(Keys.CONTROL, "a"));

			binWebElement.sendKeys(binNumber);
		}

		WebElement nameWebElement = _faroSelenium.findElement(
			"//*[text()='Breakdown Name']/ancestor::label" +
				"/following-sibling::input[@type='text']");

		nameWebElement.sendKeys(name);

		_faroSelenium.click(
			"//div[@class='form-navigation']/button[text()='Save']");
	}

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}