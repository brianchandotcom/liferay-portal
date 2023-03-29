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
import com.liferay.osb.faro.functional.test.util.FaroSeleniumUtil;
import com.liferay.osb.faro.functional.test.util.FaroTestConstants;
import com.liferay.osb.faro.functional.test.util.FaroTestDataUtil;
import com.liferay.osb.faro.functional.test.util.FaroTransformer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Assume;

import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

/**
 * @author Cheryl Tang
 */
public class DataSourcePage {

	/**
	 * Asserts message detailing what data will be affected by Data Source
	 * Deletion on the Data Source Deletion Page
	 *
	 * @param  affectedData the number and type of data that will be affected,
	 * @throws Exception if an exception occurred
	 */
	@Then(
		"^I should see that (.*) will be affected on the Data Source deletion page$"
	)
	public void assertAffectedData(
			@Transform(FaroTransformer.class) String affectedData)
		throws Exception {

		_faroSelenium.waitForLoadingComplete();

		_faroSelenium.refreshUntilElementPresent(
			300, 15, "//h5[text()='" + affectedData + "']");
	}

	@Then("^I should see a preview that (.*) users will be synced$")
	public void assertContactCountPreview(
			@Transform(FaroTransformer.class) String userCount)
		throws Exception {

		_faroSelenium.waitForText(
			"//div[@class='total-unique']",
			"TOTAL UNIQUE CONTACTS TO SYNC: " + userCount);
	}

	@Then("^I should see that Contacts are being configured$")
	public void assertContactsConfiguring() throws Exception {
		_faroSelenium.waitForPageLoadingComplete();
		_faroSelenium.waitForLoadingComplete();

		_faroSelenium.pause("500");

		_faroSelenium.assertPartialText(
			"//div[@class='status']", "Configuring");
	}

	/**
	 * Asserts the match oval icons between the CSV and SCV data model fields.
	 */
	@Then("^I should see the correct icons for matched data model fields$")
	public void assertDataModelMatchIcons() {
		List<WebElement> infoCircleWebElements = _faroSelenium.findElements(
			"//div[contains(@class,'form-group-item-shrink')]" +
				"/span[@data-tooltip]");
		List<WebElement> matchIconWebElements = _faroSelenium.findElements(
			"//div[contains(@class,'form-group-item-shrink')]" +
				"/*[contains(@class,'ovals')]");

		Assert.assertEquals(
			matchIconWebElements.toString(), infoCircleWebElements.size(),
			matchIconWebElements.size());

		for (int i = 0; i < infoCircleWebElements.size(); i++) {
			WebElement infoCircleWebElement = infoCircleWebElements.get(i);

			String infoCircleTitleAttribute = infoCircleWebElement.getAttribute(
				"title");

			WebElement matchIconWebElement = matchIconWebElements.get(i);

			String matchIconClassAttribute = matchIconWebElement.getAttribute(
				"class");

			if (infoCircleTitleAttribute.contains("Best match selected")) {
				Assert.assertTrue(
					matchIconClassAttribute.contains("success-ovals"));
			}
			else {
				Assert.assertTrue(
					matchIconClassAttribute.contains("error-ovals"));
			}
		}
	}

	/**
	 * Asserts the number of rows of matched Data Model fields.
	 *
	 * @param rowCount the number of rows to expect
	 */
	@Then("^I should see (.*) rows of data model matches$")
	public void assertDataModelRowCount(
		@Transform(FaroTransformer.class) String rowCount) {

		List<WebElement> webElements = _faroSelenium.findElements(
			"//div[contains(@class,'data-transformation-item-root')]");

		Assert.assertEquals(
			webElements.toString(), GetterUtil.getInteger(rowCount),
			webElements.size());
	}

	/**
	 * Asserts the contents of the data preview when connecting a CSV data
	 * source.
	 *
	 * @param  dataTable the expected data table, with one item per table cell
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see the CSV data preview match the following table:$")
	public void assertDataPreviewCSV(DataTable dataTable) throws Exception {
		List<List<String>> dataTableRows = dataTable.raw();

		List<String> dataTableRowStrings = new ArrayList<>();

		for (List<String> dataTableRow : dataTableRows) {
			Stream<String> dataTableRowStream = dataTableRow.stream();

			String dataTableRowString = dataTableRowStream.collect(
				Collectors.joining(StringPool.SPACE));

			dataTableRowStrings.add(dataTableRowString);
		}

		Stream<String> dataTableStringListStream = dataTableRowStrings.stream();

		String dataTableString = dataTableStringListStream.collect(
			Collectors.joining(StringPool.NEW_LINE));

		WebElement webElement = _faroSelenium.findElement(
			"//div[@class='modal-content']/table");

		_faroSelenium.waitForElementPresent(
			"//div[@class='modal-content']/table/tr/td");

		if (!dataTableString.equals(webElement.getText())) {
			throw new Exception(
				"Contents of the given Data Table does not match CSV Data " +
					"Preview");
		}
	}

	/**
	 * Asserts that a Data Source's data has finished syncing.
	 *
	 * @param  dataType the synced data to assert
	 * @throws Exception if an exception occurred
	 */
	@Then(
		"^I should see that the (?:DXP|Salesforce) " +
			"(Accounts|Individuals|Contacts|Analytics) data is synced$"
	)
	public void assertDataSourceSync(String dataType) throws Exception {
		String syncTextXPath =
			"//b[contains(text(),'" + dataType +
				"')]/following::div[@class='status'][1]";

		try {
			_faroSelenium.waitForPartialText(syncTextXPath, "Sync");
		}
		catch (Exception exception) {
			Thread.sleep(10000);

			_faroSelenium.refresh();
		}

		if (_faroSelenium.isElementNotPresent(syncTextXPath)) {
			_faroSelenium.refreshUntilElementPresent(120, 20, syncTextXPath);
		}

		if (_faroSelenium.isNotPartialText(syncTextXPath, "Last Sync:")) {
			syncTextXPath = StringBundler.concat(
				"//b[contains(text(),'", dataType,
				"')]/following::div[@class='status' and contains(text(),",
				"'Last Sync:')]");

			_faroSelenium.refreshUntilElementPresent(120, 20, syncTextXPath);
		}
	}

	/**
	 * Asserts that a CSV data source was successfully uploaded or edited by
	 * asserting the data source name on its details page.
	 *
	 * @param  dataSourceName the name of the CSV data source to assert
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see that (.*) was successfully (.*|authenticated)?$")
	public void assertDataSourceUpload(
			@Transform(FaroTransformer.class) String dataSourceName,
			String action)
		throws Exception {

		try {
			_faroSelenium.waitForElementNotPresent(
				"//button[text()='Authorize & Save' and @type='submit']");
		}
		catch (Exception exception) {
			_faroSelenium.click(
				"//button[text()='Authorize & Save' and @type='submit']");

			handleLiferayOAuthPopup(null, "accept");

			Thread.sleep(3000);

			_faroSelenium.waitForElementNotPresent(
				"//button[text()='Authorize & Save' and @type='submit']");
		}

		_faroSelenium.waitForPageLoadingComplete();

		if (action.equals("authenticated")) {
			_faroSelenium.waitForElementPresent(
				"//div[text()='Current Status:']/following::span[text()='Au" +
					"thenticated' or text()='Active' or text()='Connected']");
		}
		else {
			_faroSelenium.refreshUntilTextAsserted(
				120, 10, "//h3//button/parent::div", dataSourceName);
		}

		FaroTestDataUtil.addDataSourceId(
			dataSourceName, _faroSelenium.getIdFromURL());
	}

	/**
	 * Asserts the current status of a Liferay DXP Data Source.
	 *
	 * @param  expectedStatus the expected current status for the Data Source
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see that the (.*) data source status is currently (.*)$")
	public void assertDXPDataSourceStatus(
			@Transform(FaroTransformer.class) String name,
			@Transform(FaroTransformer.class) String expectedStatus)
		throws Exception {

		StringBundler sb = new StringBundler(7);

		sb.append("//div[text()='Current Status:']/following::div[text()='");
		sb.append(expectedStatus);
		sb.append("']|//*[text()='");
		sb.append(name);
		sb.append(
			"']//ancestor::tr//span[contains(@class,'label') and text()='");
		sb.append(expectedStatus);
		sb.append("']");

		_faroSelenium.assertElementPresent(sb.toString());
	}

	/**
	 * Asserts the matched and unmatched fields after adding a data source.
	 *
	 * @param  fieldsMappedCount the expected number of matched fields
	 * @param  fieldsNotMappedCount the expected number of unmatched fields
	 * @throws Exception if an exception occurred
	 */
	@And("^I should see (.*) fields mapped and (.*) fields not mapped")
	public void assertFieldsMatched(
			@Transform(FaroTransformer.class) String fieldsMappedCount,
			@Transform(FaroTransformer.class) String fieldsNotMappedCount)
		throws Exception {

		_faroSelenium.waitForLoadingComplete();

		_faroSelenium.waitForText(
			"//*[@class='summary']",
			StringBundler.concat(
				fieldsMappedCount, " fields mapped. ", fieldsNotMappedCount,
				" fields not mapped."));
	}

	/**
	 * Asserts the presence of an info icon in a specified row.
	 *
	 * @param  negation to negatively assert
	 * @param  row the row the icon is in from top to bottom, starting at 1
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should (not )?see the info icon in row (.*)$")
	public void assertInfoIcon(
			String negation, @Transform(FaroTransformer.class) String row)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("//div[contains(@class,'data-transformation-item-root')][");
		sb.append(row);
		sb.append("]");

		if (negation == null) {
			sb.append("//*[contains(@class,'info-circle') and ");
			sb.append("not(@style='visibility: hidden;')]");

			_faroSelenium.assertElementPresent(sb.toString());
		}
		else {
			sb.append("/div[contains(@class,'add-on') and not(descendant::*)]");
		}
	}

	/**
	 * Clicks an item to view details on how data will be affected by data
	 * source deletion
	 *
	 * @param  affectedData the kind of a data to see details for
	 * @throws Exception if an exception occurred
	 */
	@When("^I click the affected (.*) on the Data Source deletion page$")
	public void clickAffectedDataItem(
			@Transform(FaroTransformer.class) String affectedData)
		throws Exception {

		_faroSelenium.click(
			"//h5[contains(text(),'" + affectedData + "')]/parent::button");

		_faroSelenium.waitForLoadingComplete();
	}

	@When("^I click the button for (Contacts|Analytics) Configuration$")
	public void clickDataSourceConfigurationButton(
			@Transform(FaroTransformer.class) String configurationChoice)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append("//b[text()='Configure ");
		sb.append(configurationChoice);
		sb.append("']/ancestor::div[@class='base-configuration-item-root']//a");

		_faroSelenium.click(sb.toString());

		_faroSelenium.waitForPageLoadingComplete();
		_faroSelenium.waitForLoadingComplete();
	}

	@When("^I click the Sync All toggle switch$")
	public void clickSyncAllToggle() throws Exception {
		_faroSelenium.click("//input[@id='syncAll']/parent::*");

		_faroSelenium.waitForPageLoadingComplete();
	}

	/**
	 * Stores the auth token in {@link FaroTestDataUtil#_placeholders} for later
	 * use by {@link
	 * com.liferay.osb.faro.functional.test.steps.DxpSteps#connectDxpWithAuthToken}
	 */
	@And("^I copy the DXP Authentication Token$")
	public void copyDXPAuthenticationToken() {
		WebElement tokenWebElement = _faroSelenium.findElement(
			"//div[contains(@class,'connect-dx')]//input");

		String tokenString = tokenWebElement.getAttribute("value");

		FaroTestDataUtil.setPlaceholder("DXP_AUTH_TOKEN", tokenString);
	}

	@When("^I( attempt to)? create a new Property named (.*)$")
	public void createProperty(
			String saveFlag, @Transform(FaroTransformer.class) String propName)
		throws Exception {

		ClickSteps.clickButton("New Property");

		InputSteps.inputText(propName, "Property Name", "input");

		if (saveFlag == null) {
			ClickSteps.clickButton("Save");
		}

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();
	}

	/**
	 * Deletes a Data Model row.
	 *
	 * @param row the row to delete
	 */
	@When("^I delete the data model match in row \"([^\"]*)\"$")
	public void deleteDataModelRow(
		@Transform(FaroTransformer.class) String row) {

		_faroSelenium.waitForPageLoadingComplete();

		List<WebElement> webElements = _faroSelenium.findElements(
			"//button[@title='Remove Field']");

		_faroSelenium.click(webElements.get(GetterUtil.getInteger(row) - 1));
	}

	/**
	 * Deletes a data source by its given name.
	 *
	 * @param  name the name of the Data Source to delete
	 * @throws Exception if an exception occurred
	 */
	@When("^I delete the (.*) Data Source$")
	public void deleteDataSource(@Transform(FaroTransformer.class) String name)
		throws Exception {

		_faroSelenium.type("//input[@id='delete']", "Remove " + name);

		ClickSteps.clickButton("Delete Data Source");
		ClickSteps.clickButton("Delete Data Source");

		_faroSelenium.refreshUntilElementNotPresent(
			30, 10,
			"//div[contains(@class,'label') and contains(.," +
				"'Deletion in Progress')]");
	}

	@When("^I delete the (.*) Property$")
	public void deleteProperty(@Transform(FaroTransformer.class) String name)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append("//table[contains(@class,'table')]//div/a/span[text()='");
		sb.append(name);
		sb.append("']");

		_faroSelenium.click(sb.toString());

		ClickSteps.clickButton("Delete");

		_faroSelenium.type(
			"//input[@name='delete' and @type='text']", "Delete " + name);

		ClickSteps.clickButton("Delete");
	}

	@And("^I edit the Data Source name to (.*)$")
	public void editDataSourceName(
			@Transform(FaroTransformer.class) String name)
		throws Exception {

		_faroSelenium.waitForPageLoadingComplete();
		_faroSelenium.waitForLoadingComplete();

		if (_faroSelenium.isTextPresent("CSV Field")) {
			_faroSelenium.click("//h3//button");

			_faroSelenium.sendKeys("//h3//input", name);
		}
		else {
			_faroSelenium.click("//button[text()='Edit']");

			InputSteps.inputText(
				name, "Name", FaroTestConstants.INPUT_TYPE_INPUT);
		}

		_faroSelenium.click("//button[@type='submit']");
	}

	@When("^I edit the Property (.*) name to (.*)$")
	public void editPropertyName(
			@Transform(FaroTransformer.class) String name,
			@Transform(FaroTransformer.class) String newName)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append("//table[contains(@class,'table')]//div/a/span[text()='");
		sb.append(name);
		sb.append("']");

		_faroSelenium.click(sb.toString());

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();

		_faroSelenium.waitForElementPresent(
			"//div[contains(@class,'title-display')]/button");

		WebElement webElement = _faroSelenium.findElement(
			"//div[contains(@class,'title-display')]/button");

		_faroSelenium.click(webElement);

		_faroSelenium.waitForElementPresent(
			"//div[contains(@class,'editing')]");

		_faroSelenium.typeScreen(newName);

		_faroSelenium.click("//h2");
	}

	/**
	 * Handles a popup with an OAuth authorization request.
	 *
	 * @param  choice whether to accept or deny the authorization request
	 * @throws Exception if an exception occurred
	 */
	@And("^I (try to )?(accept|deny) the Liferay DXP OAuth2 Popup$")
	public void handleLiferayOAuthPopup(String tryTo, String choice)
		throws Exception {

		Thread.sleep(5000);

		_faroSelenium.switchToPopupWindow();

		_faroSelenium.waitForPageLoadingComplete();

		if (_faroSelenium.isElementPresent("//button//*[text()='Sign In']")) {
			InputSteps.inputText(
				"test@liferay.com", "Email Address",
				FaroTestConstants.INPUT_TYPE_INPUT);
			InputSteps.inputText(
				"test", "Password", FaroTestConstants.INPUT_TYPE_INPUT);

			_faroSelenium.click("//button[@type='submit']/*[text()='Sign In']");

			_faroSelenium.waitForLoadingComplete();
			_faroSelenium.waitForPageLoadingComplete();
		}

		try {
			if (choice.equals("accept")) {
				_faroSelenium.refreshUntilElementPresent(
					60, 15, "//button/span[text()='Authorize']");

				try {
					ClickSteps.clickButton("Authorize");
				}
				catch (NoSuchWindowException noSuchWindowException) {
					_faroSelenium.switchToMainWindow();
				}
			}
			else if (choice.equals("deny")) {
				ClickSteps.clickButton("Cancel");
			}

			_faroSelenium.switchToMainWindow();
			_faroSelenium.waitForLoadingComplete();
		}
		catch (Exception exception) {
			if (tryTo == null) {
				throw exception;
			}
		}
	}

	/**
	 * Handles the Salesforce OAuth popup.
	 *
	 * @throws Exception if an exception occurred
	 */
	@And("^I complete the Salesforce OAuth authorization$")
	public void handleSalesforceOAuthPopup() throws Exception {
		String consumerKey = System.getenv("SALESFORCE_CONSUMER_KEY");
		String consumerSecret = System.getenv("SALESFORCE_CONSUMER_SECRET");
		String userName = System.getenv("SALESFORCE_USERNAME");
		String password = System.getenv("SALESFORCE_PASSWORD");

		Assume.assumeNotNull(consumerKey, consumerSecret, userName, password);

		InputSteps.inputText("https://test.salesforce.com", "URL", "input");
		InputSteps.inputText(consumerKey, "Consumer Key/Client ID", "input");
		InputSteps.inputText(
			consumerSecret, "Consumer Secret/Client Secret", "input");

		ClickSteps.clickButton("Authorize & Save");

		_faroSelenium.switchToPopupWindow();

		try {
			if (_faroSelenium.isElementPresent("//input[@id='Login']")) {
				InputSteps.inputText(
					userName, "Username", FaroTestConstants.INPUT_TYPE_INPUT);

				_faroSelenium.type("//input[@id='password']", password);

				_faroSelenium.click("//input[@id='Login']");

				if (_faroSelenium.isElementPresent(
						"//input[@id='oaapprove']")) {

					_faroSelenium.click("//input[@id='oaapprove']");
				}
			}
		}
		catch (NoSuchWindowException | TimeoutException exception) {
			_faroSelenium.switchToMainWindow();
		}

		_faroSelenium.switchToMainWindow();
	}

	@And("^I name the Data Source (.*)$")
	public void nameDataSource(@Transform(FaroTransformer.class) String name)
		throws Exception {

		_faroSelenium.waitForPageLoadingComplete();
		_faroSelenium.waitForLoadingComplete();

		_faroSelenium.sendKeys(
			"//label[contains(.,'Name Data Source')]/following-sibling::input",
			name);
	}

	/**
	 * Selects an option from the dropdown for either the CSV or SCV Data Model
	 * field in a specified row.
	 *
	 * @param  fieldName the name of the field to select for mapping
	 * @param  dataModelType the type of field to manually map
	 * @param  row the target field's row from top to bottom, starting at 1
	 * @throws Exception if an exception occurred
	 */
	@When("^I select (.*) for the (CSV|SCV) Data Model in row (.*)$")
	public void select(
			@Transform(FaroTransformer.class) String fieldName,
			String dataModelType, @Transform(FaroTransformer.class) String row)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append("//div[contains(@class,'data-transformation-item-root')][");
		sb.append(row);
		sb.append("]//div[@class='form-group-item']");

		if (dataModelType.equals("CSV")) {
			sb.append("[1]");
		}
		else if (dataModelType.equals("SCV")) {
			sb.append("[2]");
		}

		sb.append("//button[contains(@class,'dropdown-toggle')]");

		_faroSelenium.click(sb.toString());

		ClickSteps.selectDropdownOverlayOption(fieldName, fieldName);
	}

	@And(
		"^I sync the Data Source Contacts by the following (Organizations|User Groups):$"
	)
	public void syncDataSourceBy(
			@Transform(FaroTransformer.class) String syncOption,
			DataTable dataTable)
		throws Exception {

		_faroSelenium.javaScriptClick(
			StringBundler.concat(
				"//h4[@class='list-group-title' and text()='Sync By ",
				syncOption, "']|//p[contains(text(),'Sync by ", syncOption,
				"')]"));

		_faroSelenium.waitForLoadingComplete();
		_faroSelenium.waitForPageLoadingComplete();

		for (String name : dataTable.asList(String.class)) {
			ClickSteps.clickTableRowCheckbox(name);
		}

		ClickSteps.clickButton(
			_faroSelenium.isTextPresent("Instance Settings") ? "Save and Next" :
				"Add");

		ClickSteps.clickButton("Save");
	}

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}