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
import com.liferay.osb.faro.functional.test.pages.fragments.Toolbar;
import com.liferay.osb.faro.functional.test.util.DxpStringPool;
import com.liferay.osb.faro.functional.test.util.FaroSeleniumUtil;
import com.liferay.osb.faro.functional.test.util.FaroTestConstants;
import com.liferay.osb.faro.functional.test.util.FaroTestDataUtil;
import com.liferay.osb.faro.functional.test.util.FaroTransformer;
import com.liferay.petra.string.StringBundler;
import com.liferay.poshi.runner.util.PropsUtil;
import com.liferay.poshi.runner.util.StringUtil;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.FileInputStream;

import java.util.Properties;

import org.openqa.selenium.WebElement;

import org.sikuli.api.robot.Key;

/**
 * @author Cheryl Tang
 */
public class DxpSteps {

	/**
	 * Connects AC to a local DXP instance using OAuth2, then stores the
	 * generated Client and Secret in {@link FaroTestDataUtil} _placeholders for
	 * use by subsequent steps.
	 *
	 * @throws Exception if an exception occurred
	 */
	@Given("^I add an OAuth2 application on the local DXP instance$")
	public void addDxpOAuth2App() throws Exception {
		Properties properties = new Properties();

		try (FileInputStream fileInputStream = new FileInputStream(
				"src/testIntegration/resources/ngrok.properties")) {

			properties.load(fileInputStream);
		}

		FaroTestDataUtil.setPlaceholder(
			"NGROK_URL", "http://" + properties.getProperty("url"));
		FaroTestDataUtil.setPlaceholder(
			"IP_ADDRESS", properties.getProperty("ip"));

		_faroSelenium.get(FaroTestDataUtil.getPlaceholder("NGROK_URL"));

		_faroSelenium.waitForLoadingComplete();

		_handleDxpLogin();

		_faroSelenium.get(
			FaroTestDataUtil.getPlaceholder("NGROK_URL") +
				DxpStringPool.OAUTH_URL_PATH);

		_faroSelenium.waitForElementPresent(
			DxpStringPool.ADD_OAUTH_APP_BUTTON_XPATH);

		if (_faroSelenium.isElementNotPresent("//td//a[text()='AC']")) {
			_faroSelenium.click(DxpStringPool.ADD_OAUTH_APP_BUTTON_XPATH);

			_faroSelenium.waitForPageLoadingComplete();

			InputSteps.inputText(
				"AC", "Application Name", FaroTestConstants.INPUT_TYPE_INPUT);
			InputSteps.inputText(
				PropsUtil.get("analytics.cloud.url"), "Website URL",
				FaroTestConstants.INPUT_TYPE_INPUT);
			InputSteps.inputText(
				PropsUtil.get("analytics.cloud.url") + "/oauth/receive",
				"Callback URIs", FaroTestConstants.INPUT_TYPE_TEXT_AREA);
		}
		else {
			_faroSelenium.click("//td//a[text()='AC']");

			_faroSelenium.waitForPageLoadingComplete();

			InputSteps.inputText(
				PropsUtil.get("analytics.cloud.url"), "Website URL",
				FaroTestConstants.INPUT_TYPE_INPUT);
			InputSteps.inputText(
				PropsUtil.get("analytics.cloud.url") + "/oauth/receive",
				"Callback URIs", FaroTestConstants.INPUT_TYPE_TEXT_AREA);

			ClickSteps.clickButton("Save");
		}

		ClickSteps.clickButton("Save");

		_faroSelenium.waitForPageLoadingComplete();

		Thread.sleep(2000);

		// Get client id and secret

		_faroSelenium.waitForElementPresent(DxpStringPool.OAUTH_SECRET_XPATH);

		WebElement clientIdWebElement = _faroSelenium.findElement(
			DxpStringPool.OAUTH_ID_XPATH);

		String clientId = clientIdWebElement.getAttribute("value");

		WebElement clientSecretWebElement = _faroSelenium.findElement(
			DxpStringPool.OAUTH_SECRET_XPATH);

		String clientSecret = clientSecretWebElement.getAttribute("value");

		// Tick all auth scopes

		Thread.sleep(2000);

		try {
			_faroSelenium.click(DxpStringPool.OAUTH_SCOPES_TAB_XPATH);
		}
		catch (Exception exception) {
			_faroSelenium.click(DxpStringPool.MASTER_OAUTH_SCOPES_TAB_XPATH);
		}

		_faroSelenium.waitForElementPresent(
			DxpStringPool.ANALYTICS_SCOPE_XPATH);

		if (_faroSelenium.isElementNotPresent(
				DxpStringPool.ANALYTICS_SCOPE_EXPANDED_XPATH)) {

			_faroSelenium.click(DxpStringPool.ANALYTICS_SCOPE_XPATH);
		}

		_faroSelenium.waitForPageLoadingComplete();

		for (WebElement checkboxWebElement :
				_faroSelenium.findElements(
					DxpStringPool.SCOPE_CHECKBOX_XPATH)) {

			if (!checkboxWebElement.isSelected()) {
				checkboxWebElement.sendKeys(Key.SPACE);
			}
		}

		ClickSteps.clickButton("Save");

		// Add the client id and secret we obtained to placeholders map

		FaroTestDataUtil.setPlaceholder("OAUTH2_CLIENT_ID", clientId);
		FaroTestDataUtil.setPlaceholder("OAUTH2_SECRET", clientSecret);
	}

	@Then("^I should see a property named (.*) in DXP$")
	public void assertDXPListItem(
			@Transform(FaroTransformer.class) String propName)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append("//table[contains(@class,'table')]//a[contains(text(),'");
		sb.append(propName);
		sb.append("')]");

		_faroSelenium.waitForElementPresent(sb.toString());
	}

	@Then("^I should see an oauth alert message (.*)$")
	public void assertOAuthAlert(
			@Transform(FaroTransformer.class) String message)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append("//text()[contains(.,'");
		sb.append(message);
		sb.append("')]");

		_faroSelenium.waitForElementPresent(sb.toString());
	}

	@Then("^I should not be able to create property with site (.*)$")
	public void assertSiteBlockedFromProperty(
			@Transform(FaroTransformer.class) String siteName)
		throws Exception {

		_faroSelenium.click(
			"//*[text()='New Property']/parent::*|//a[@title='New Property']");

		_faroSelenium.waitForPageLoadingComplete();
		_faroSelenium.waitForElementPresent(
			"//td[contains(text(),'" + siteName +
				"')]//preceding-sibling::td/input[@disabled]");

		_faroSelenium.assertElementPresent(
			"//td[contains(text(),'" + siteName +
				"')]//preceding-sibling::td/input[@disabled]");
	}

	/**
	 * Uses the auth token set in {@link
	 * com.liferay.osb.faro.functional.test.pages.DataSourcePage#copyDXPAuthenticationToken}
	 * to connect AC to DXP
	 *
	 * @throws Exception if an exception occurred
	 */
	@And("^I connect Analytics Cloud to DXP using the Authentication Token$")
	public void connectDxpWithAuthToken() throws Exception {
		_faroSelenium.get(
			PropsUtil.get("portal.url") +
				DxpStringPool.AC_INSTANCE_SETTINGS_URL_PATH);

		_handleDxpLogin();

		_faroSelenium.waitForPageLoadingComplete();
		_faroSelenium.waitForLoadingComplete();

		if (_faroSelenium.isTextPresent("Go to Workspace")) {
			_faroSelenium.javaScriptClick(
				"//span[text()='Disconnect']/parent::button");

			GeneralSteps.handleAlert("accept");

			_faroSelenium.get(
				PropsUtil.get("portal.url") +
					DxpStringPool.AC_INSTANCE_SETTINGS_URL_PATH);
		}

		_faroSelenium.type(
			DxpStringPool.AUTH_TOKEN_FIELD_XPATH,
			FaroTestDataUtil.getPlaceholder("DXP_AUTH_TOKEN"));

		_faroSelenium.click(DxpStringPool.CONNECT_AUTH_TOKEN_BUTTON_XPATH);

		_faroSelenium.waitForElementPresent(
			"//div[contains(text(),'Your request completed successfully.')]");
	}

	/**
	 * Creates a page on DXP and generates click activity
	 *
	 * @param  pageName name of the page to create
	 * @throws Exception if an exception occurred
	 */
	@And("^I create a dummy page called (.*) on the (.*)?(?: )?DXP Site$")
	public void createDummyPage(
			@Transform(FaroTransformer.class) String pageName,
			@Transform(FaroTransformer.class) String site)
		throws Exception {

		if (site == null) {
			site = "Liferay DXP";
		}

		_faroSelenium.get(PropsUtil.get("portal.url"));

		_handleDxpLogin();
		_handleDxpPasswordReminder();

		_faroSelenium.get(
			PropsUtil.get("portal.url") +
				DxpStringPool.getSitePageUrl(site, pageName));

		StringBundler sb = new StringBundler(5);

		sb.append("//div[@id='navbar_com_liferay_site_navigation_menu_web_po");
		sb.append("rtlet_SiteNavigationMenuPortlet']");

		_faroSelenium.waitForElementPresent(sb.toString());

		sb.append("//li//span[contains(text(),' ");
		sb.append(pageName);
		sb.append(" ')]");

		if (_faroSelenium.isElementNotPresent(sb.toString())) {
			_faroSelenium.get(
				PropsUtil.get("portal.url") +
					DxpStringPool.getPageCreationUrlPath(site));

			_faroSelenium.waitForElementPresent("//div[text()='Blog']");

			_faroSelenium.click("//div[text()='Blog']");

			_faroSelenium.waitForLoadingComplete();

			NavigationSteps.switchToFocusedModal();

			_faroSelenium.type(
				DxpStringPool.PAGE_CREATION_NAME_FIELD, pageName);

			ClickSteps.clickButton("Add");

			NavigationSteps.switchToMainFrame();

			ClickSteps.clickButton("Save");
		}
	}

	@When("^I add activity to user$")
	public void createDxpActivity() throws Exception {
		_faroSelenium.get(PropsUtil.get("portal.url"));

		_handleDxpLogin();
		_handleDxpPasswordReminder();

		_faroSelenium.click("//h2[@role='heading' and text()='Liferay DXP']");

		_handleDxpLogout();
	}

	@When(
		"^(?i)I create a new (Combined|Multiple) Property with the following Sites on DXP:$"
	)
	public void createDxpProperty(
			@Transform(FaroTransformer.class) String propType,
			DataTable dataTable)
		throws Exception {

		_faroSelenium.click(
			"//*[text()='New Property']/parent::*|//a[@title='New Property']");

		_faroSelenium.waitForPageLoadingComplete();

		_faroSelenium.click(
			"//input[@type='radio' and @value='" +
				StringUtil.lowerCase(propType) + "']");

		for (String name : dataTable.asList(String.class)) {
			ClickSteps.clickTableRowCheckbox(name);
		}

		ClickSteps.clickButton("Done");

		_faroSelenium.waitForPageLoadingComplete();
	}

	@Given("^I create a new Site named (.*) in DXP$")
	public void createNewSite(@Transform(FaroTransformer.class) String siteName)
		throws Exception {

		_faroSelenium.get(
			PropsUtil.get("portal.url") + DxpStringPool.ADD_SITES_URL_PATH);

		_faroSelenium.click("//div[text()='Blank Site']");

		_faroSelenium.type("//input[@type='text']", siteName);

		ClickSteps.clickButton("Save");
	}

	@And(
		"^I generate page views on the following pages as (.*) on the (.*) DXP Site$"
	)
	public void generatePageViewsOnSitePages(
			@Transform(FaroTransformer.class) String user,
			@Transform(FaroTransformer.class) String site, DataTable dataTable)
		throws Exception {

		_faroSelenium.get(
			PropsUtil.get("portal.url") +
				DxpStringPool.USERS_ORGANIZATIONS_CONTROL_PANEL_URL_PATH);

		Toolbar.submitDxpSearchBar(user);

		String userKebabXpath =
			"//tr[descendant::*[contains(text(),'" + user +
				"')]]/descendant::div/*[self::a or self::button]";

		_faroSelenium.refreshUntilElementPresent(6, 2, userKebabXpath);

		_faroSelenium.click(userKebabXpath);

		WebElement webElement = _faroSelenium.findElement(
			"//ul/li//*[text()='Impersonate User']/parent::a");

		_faroSelenium.get(webElement.getAttribute("href"));

		_switchDxpSiteAsUser(site);

		for (String page : dataTable.asList(String.class)) {
			_faroSelenium.get(
				PropsUtil.get("portal.url") +
					DxpStringPool.getSitePageUrl(site, page));

			_faroSelenium.mouseOver("//footer");

			_faroSelenium.refresh();

			_faroSelenium.click("//*[@class='portlet-title-text']");

			_faroSelenium.mouseOver("//*[@id='banner']");

			_faroSelenium.refresh();
		}

		Thread.sleep(10000);
	}

	@Given("^I go to the (.*) DXP Page$")
	public void goToDXPPage(@Transform(FaroTransformer.class) String pageName) {
		StringBuilder url = new StringBuilder(PropsUtil.get("portal.url"));

		if (StringUtil.equalsIgnoreCase(pageName, "Synced Contacts")) {
			url.append(DxpStringPool.SYNCED_CONTACTS_PATH);
		}
		else if (StringUtil.equalsIgnoreCase(pageName, "Synced Sites")) {
			url.append(DxpStringPool.SYNCED_SITES_PATH);
		}
		else if (StringUtil.equalsIgnoreCase(pageName, "Analytics Cloud")) {
			url.append(DxpStringPool.AC_INSTANCE_SETTINGS_URL_PATH);
		}

		_faroSelenium.get(url.toString());
	}

	@When("^I select DXP site (.*)$")
	public void selectDXPProperty(
			@Transform(FaroTransformer.class) String propType)
		throws Exception {

		_faroSelenium.waitForPageLoadingComplete();

		_faroSelenium.click(
			"//td[contains(text(),'" + propType +
				"')]/preceding-sibling::td/input");

		ClickSteps.clickButton("Done");
	}

	@When("^I click existing property (.*) in synced sites$")
	public void selectExistingProperty(
			@Transform(FaroTransformer.class) String propType)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("//table//a[text()='");
		sb.append(propType);
		sb.append("\']|//table[contains(@class,'table')]//tr");
		sb.append("[@data-qa-id=\"row\"]//span[contains(text(),\'");
		sb.append(propType);
		sb.append("\')]");

		_faroSelenium.click(sb.toString());
	}

	/**
	 * Initializes the DXP instance for use by subsequent steps
	 *
	 * @throws Exception if an exception occurred
	 */
	@Given("^I set up the local DXP instance$")
	public void setUpLocalDxp() throws Exception {
		if (System.getProperty("liferay.ci") != null) {
			System.out.println("Running on Portal CI detected.");
		}

		_faroSelenium.get(PropsUtil.get("portal.url"));

		_handleDxpConfiguration();
		_handleDxpLogin();
		_handleDxpChangePassword();
		_handleDxpTos();
		_handleDxpPasswordReminder();
	}

	private void _handleDxpChangePassword() throws Exception {
		if (_faroSelenium.isElementPresent(
				"//h2[contains(text(),'Change Password')]")) {

			_faroSelenium.sendKeys("//input[@id='password1']", "test");
			_faroSelenium.sendKeys("//input[@id='password2']", "test");

			ClickSteps.clickButton("Save");

			_handleDxpTos();
		}
	}

	private void _handleDxpConfiguration() throws Exception {
		if (_faroSelenium.isElementPresent(
				"//h2[@title='Basic Configuration']")) {

			InputSteps.inputText(
				"test@liferay.com", "Email",
				FaroTestConstants.INPUT_TYPE_INPUT);
			ClickSteps.clickButton("Finish Configuration");

			_faroSelenium.waitForPageLoadingComplete();
			_faroSelenium.waitForLoadingComplete();

			_handleDxpTos();
			_handleDxpChangePassword();
		}
	}

	private void _handleDxpLogin() throws Exception {
		if (_faroSelenium.isElementPresent("//a[contains(@class,'sign-in')]")) {
			_faroSelenium.click("//a[contains(@class,'sign-in')]");

			InputSteps.inputText(
				"test@liferay.com", "Email Address",
				FaroTestConstants.INPUT_TYPE_INPUT);
			InputSteps.inputText(
				"test", "Password", FaroTestConstants.INPUT_TYPE_INPUT);

			_faroSelenium.click("//button[contains(@id,'login')]");

			_faroSelenium.waitForPageLoadingComplete();
			_faroSelenium.waitForLoadingComplete();
		}
	}

	private void _handleDxpLogout() throws Exception {
		if (_faroSelenium.isElementNotPresent(
				"//a[contains(@class,'sign-in')]")) {

			_faroSelenium.click(
				"//h2[@role='heading' and text()='Liferay DXP']");

			_faroSelenium.waitForPageLoadingComplete();
			_faroSelenium.waitForLoadingComplete();

			_faroSelenium.click(
				"//div[@class='personal-menu-dropdown']//button");

			_faroSelenium.click("//a[text()='Sign Out']");
		}
		else {
			_handleDxpLogin();

			_faroSelenium.click(
				"//h2[@role='heading' and text()='Liferay DXP']");

			_faroSelenium.waitForPageLoadingComplete();
			_faroSelenium.waitForLoadingComplete();

			_faroSelenium.click(
				"//div[@class='personal-menu-dropdown']//button");

			_faroSelenium.click("//a[text()='Sign Out']");
		}
	}

	private void _handleDxpPasswordReminder() throws Exception {
		if (_faroSelenium.isElementPresentAfterWait(
				"//h2[contains(text(), 'Password Reminder')]")) {

			InputSteps.inputText(
				"test", "Answer", FaroTestConstants.INPUT_TYPE_INPUT);

			ClickSteps.clickButton("Save");
		}
	}

	private void _handleDxpTos() throws Exception {
		if (_faroSelenium.isElementPresentAfterWait(
				"//h2[contains(text(),'Terms of Use')]")) {

			_faroSelenium.click("//button[@type='submit']");

			_faroSelenium.waitForPageLoadingComplete();
			_faroSelenium.waitForLoadingComplete();
		}
	}

	private void _switchDxpSiteAsUser(String site) throws Exception {
		_faroSelenium.click("//span[@class='user-avatar-link']");

		_faroSelenium.click("//a[text()='My Sites']");

		_faroSelenium.waitForElementPresent("//iframe");

		NavigationSteps.switchToFocusedModal();

		_faroSelenium.click("//a[text()='My Sites']");

		Toolbar.submitDxpSearchBar(site);

		_faroSelenium.click(
			"//div[@class='card-body']//*[contains(text(),'" + site + "')]");

		NavigationSteps.switchToMainFrame();
	}

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}