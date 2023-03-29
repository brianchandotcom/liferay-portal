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
import com.liferay.poshi.runner.util.Validator;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import org.junit.Assert;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * @author Cheryl Tang
 */
public class ContactPage {

	@Then("^I add another breakdown$")
	public void addAdditionalBreakdown() throws Exception {
		_faroSelenium.click("//div[contains(@class,'tabs-root')]/button");
	}

	/**
	 * Add a name to the breakdown name box
	 *
	 * @param name the name of the breakdown
	 */
	@Then("^I name the breakdown (.*)$")
	public void addBreakdownName(
		@Transform(FaroTransformer.class) String name) {

		WebElement breakdownWebElement = _faroSelenium.findElement(
			"//*[text()='Breakdown Name']/ancestor::label" +
				"/following-sibling::input[@type='text']");

		if (Validator.isBlank(breakdownWebElement.getText())) {
			breakdownWebElement.sendKeys(name);
			breakdownWebElement.sendKeys(Keys.TAB);
		}
	}

	/**
	 * Add a name to the breakdown name box
	 *
	 * @param name the name of the breakdown
	 */
	@Then("^I name and save the breakdown (.*)$")
	public void addBreakdownNameAndSave(
			@Transform(FaroTransformer.class) String name)
		throws Exception {

		WebElement breakdownWebElement = _faroSelenium.findElement(
			"//*[text()='Breakdown Name']/ancestor::label" +
				"/following-sibling::input[@type='text']");

		if (Validator.isBlank(breakdownWebElement.getText())) {
			breakdownWebElement.sendKeys(name);

			_faroSelenium.click(
				"//div[@class='form-navigation']/button[text()='Save']");
		}
	}

	@Then("^I should see the Account Overview Cards with their details$")
	public void assertAccountOverviewCards() throws Exception {
		_faroSelenium.waitForElementPresent(
			"//div[contains(@class,'account-activities')]//*[contains(" +
				"text(),'Total Activity Count')]");
		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'account-activities')]//*[contains(" +
				"text(),'Engagement Score')]");

		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'info-card')]" +
				"/div[text()='Account Firmographics']");
		_faroSelenium.assertWebElementHasAnyText(
			"//dt[text()='Name']/following-sibling::dd");

		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'info-card')]" +
				"/div[text()='Contact Information']");

		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'known-individuals-card')]");

		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'interests-card')]");

		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'associated-segments')]");
	}

	@Then("^I should see the breakdown is deleted$")
	public void assertBreakdownDeleted() throws Exception {
		_faroSelenium.assertElementNotPresent(
			"//li[contains(@class,'tab-item')]");

		_faroSelenium.assertText(
			"//div[contains(@class,'distribution-card')]//*" +
				"[@class='description']/h4",
			"Add a Breakdown by Attribute");
	}

	/**
	 * Asserts the rows and their content in the Contact's Details tab.
	 *
	 * @param negation to negatively assert
	 * @param dataTable the table of details to assert
	 */
	@Then("^I should (not )?see the following rows in the Contact Details tab$")
	public void assertContactDetailsRows(String negation, DataTable dataTable)
		throws Exception {

		for (List<String> dataTableRow : dataTable.raw()) {
			if (negation == null) {
				List<WebElement> webElementList = _faroSelenium.findElements(
					"//tbody//div[text()='" + dataTableRow.get(0) +
						"']/ancestor::tr//*[text()]");

				for (int i = 0; i < dataTableRow.size(); i++) {
					WebElement webElement = webElementList.get(i);

					Assert.assertEquals(
						FaroTestDataUtil.parsePlaceholders(
							StringUtil.unquote(dataTableRow.get(i))),
						webElement.getText());
				}
			}
			else {
				for (String dataTableRowItem : dataTableRow) {
					_faroSelenium.assertElementNotPresent(
						"//tbody//div[text()='" + dataTableRowItem +
							"']/ancestor::tr//*[text()]");
				}
			}
		}
	}

	/**
	 * Asserts the Individuals count on the Individuals dashboard quick
	 * overview.
	 *
	 * @param name the name of the card
	 * @param count the number of individuals to assert
	 */
	@Then("^I should see the (.*) count is (.*)$")
	public void assertIndividualDashboardIndividualCount(
			@Transform(FaroTransformer.class) String name,
			@Transform(FaroTransformer.class) String count)
		throws Exception {

		String xpath =
			"and contains(.,'" + name + "')]//div[contains(@class,'total')]";

		if (name.equals("Enriched Profiles")) {
			_faroSelenium.assertText(
				"//div[contains(@class,'enriched-profiles') " + xpath,
				count + " Profiles");
		}
		else {
			_faroSelenium.assertText(
				"//div[@class='trend-item-root' " + xpath, count);
		}
	}

	/**
	 * Asserts the Individuals Overview tab's cards and checks that there is
	 * some text for the Individual's details card.
	 *
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see the Individual Overview Cards with their details$")
	public void assertIndividualOverviewCards() throws Exception {
		_faroSelenium.waitForElementPresent(
			"//div[contains(@class,'individual-profile-card')]//*[contains(" +
				"text(),'Individual Activities')]");
		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'individual-profile-card')]//*[contains(" +
				"text(),'Engagement Score')]");

		_faroSelenium.assertWebElementHasAnyText("//div[@class='job-title']");
		_faroSelenium.assertWebElementHasAnyText("//div[@class='email']");

		_faroSelenium.assertText(
			"//div[contains(@class,'interests-card')]//*[@class='card-header']",
			"Current Interests");
		_faroSelenium.assertText(
			"//div[contains(@class,'associated-segments-card')]//*" +
				"[@class='card-header']",
			"Associated Segments");
	}

	/**
	 * Verify the cards on the Individuals Overview Dashboard page
	 *
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see the Individual Overview Dashboard Cards$")
	public void assertIndividualOverviewDashboardCards() throws Exception {
		_faroSelenium.assertElementPresent(
			"//h5[text()='Total Individuals']//ancestor::div[@class='trend-i" +
				"tem-root']//div[@class='total']");

		_faroSelenium.assertElementPresent(
			"//h5[text()='Known']//ancestor::div[@class='trend-item-root'" +
				"]//div[@class='total']");

		_faroSelenium.assertElementPresent(
			"//h5[text()='Anonymous']//ancestor::div[@class='trend-item-root" +
				"']//div[@class='total']");

		_faroSelenium.assertElementPresent(
			"//h5[text()='Enriched Profiles']//parent::div//following-siblin" +
				"g::div/div[contains(text(),' Profiles')]");

		_faroSelenium.assertElementPresent(
			"//h5[text()='Active Individuals']/parent::div/following-siblin" +
				"g::div/div[contains(@class,'recharts')]");

		_faroSelenium.assertElementPresent(
			"//h5[text()='Top Interests as of Today']/parent::div/following-" +
				"sibling::div/table");

		_faroSelenium.assertElementPresent(
			"//h4[text()='Add a Breakdown by Attribute']/parent::div/followin" +
				"g-sibling::form");
	}

	@Then("^I should see the name already exists")
	public void assertNameExists() throws Exception {
		_faroSelenium.assertElementPresent(
			"//div[text()='Tab name already exists.']");
	}

	@Then("^I should see the Sites Overview Cards with their details$")
	public void assertSitesOverviewCards() throws Exception {
		_faroSelenium.waitForElementPresent(
			"//h5[text()='LIFERAY-DATASOURCE-FARO-EXAMPLE Activities']/pa" +
				"rent::div/parent::div//li[contains(@class,'active')]/button" +
					"/span[text()='Visitors']");

		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'table-tabs')]//h5[text()='Top Pages']");

		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'acquisitions')]//h5[text()=" +
				"'Acquisitions']");

		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'visitors-by-time')]//h5[text()=" +
				"'Visitors by Day and Time']");

		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'search-terms')]//h5[text()=" +
				"'Search Terms']");

		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'interests')]//h5[text()='Interests']");

		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'locations')]//h5[text()=" +
				"'Sessions by Location']");

		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'devices')]//h5[text()=" +
				"'Session Technology']");

		_faroSelenium.assertElementPresent(
			"//div[contains(@class,'cohort-analysis')]//h5[text()=" +
				"'Cohort Analysis']");
	}

	/**
	 * Asserts the Sites Page Overview tab's card and checks that there is some
	 * text for the Site's Page details card
	 *
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see the Sites Page Overview Cards with their details$")
	public void assertSitesPageOverviewCards() throws Exception {
		_faroSelenium.waitForElementPresent(
			"//div[contains(@class,'card-header')]//*[contains(" +
				"text(),'Visitors Behavior')]");
		_faroSelenium.waitForElementPresent(
			"//div[@class='card-body']//*[text()='Avg. Engagement']");

		_faroSelenium.assertText(
			"//div[contains(@class,'analytics-audience')]//*" +
				"[@class='card-title']",
			"Audience");
		_faroSelenium.assertText(
			"//div[contains(@class,'analytics-location')]//*" +
				"[@class='card-title']",
			"Views by Location");
		_faroSelenium.assertText(
			"//div[contains(@class,'analytics-devices')]//*" +
				"[@class='card-title']",
			"Views by Technology");
		_faroSelenium.assertText(
			"//div[contains(@class,'analytics-assets')]//*" +
				"[@class='card-title']",
			"Assets");
	}

	@Then("^I delete the breakdown$")
	public void deleteBreakdown() throws Exception {
		_faroSelenium.click("//div[@class='tab-title active']/button");

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();
	}

	@When("^I select \"(.*)\" in the card list \"(.*)\"$")
	public void selectItemCardTitle(
			@Transform(FaroTransformer.class) String name, String cardTitle)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("//h5[text()='");
		sb.append(cardTitle);
		sb.append("']/ancestor::div[contains(@class,'card-root')]//a[text()='");
		sb.append(name);
		sb.append("']");

		_faroSelenium.click(sb.toString());
	}

	/**
	 * Selects the attribute in breakdown
	 *
	 * @param name the name of the attribute
	 */
	@Then("^I select the attribute (.*)$")
	public void selectPropertyType(
			@Transform(FaroTransformer.class) String name)
		throws Exception {

		WebElement propertyWebElement = _faroSelenium.findElement(
			"//div[@class='input-group-item']" +
				"/input[@placeholder='Select Field']");

		if (Validator.isBlank(propertyWebElement.getText())) {
			propertyWebElement.sendKeys(name);

			_faroSelenium.click(
				"//ul[contains(@class,'dropdown-menu show')]//span[text()='" +
					name + "']");
		}
	}

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}