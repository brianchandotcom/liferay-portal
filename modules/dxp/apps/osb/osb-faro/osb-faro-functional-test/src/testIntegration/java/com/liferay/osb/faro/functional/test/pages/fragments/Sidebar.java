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
import com.liferay.osb.faro.functional.test.util.FaroPagePool;
import com.liferay.osb.faro.functional.test.util.FaroSeleniumUtil;
import com.liferay.osb.faro.functional.test.util.FaroTestDataUtil;
import com.liferay.osb.faro.functional.test.util.FaroTransformer;
import com.liferay.portal.kernel.util.StringBundler;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import org.openqa.selenium.WebElement;

/**
 * @author Cheryl Tang
 */
public class Sidebar {

	/**
	 * Clicks an item in the sidebar.
	 *
	 * @param  targetName the target element's name or another identifier
	 * @throws Exception if an exception occurred
	 */
	@When("^(?i)I click (.*) in the sidebar$")
	public static void clickSidebarItem(
			@Transform(FaroTransformer.class) String targetName)
		throws Exception {

		WebElement webElement = _faroSelenium.findElement(
			"//span[text()='" + targetName + "'][1]");

		_faroSelenium.click(webElement);

		_faroSelenium.waitForPageLoadingComplete();
		_faroSelenium.waitForLoadingComplete();
	}

	/**
	 * Asserts that the workspace is scoped by a specified Property.
	 *
	 * @param  propertyName the name of the Property
	 * @throws Exception if an exception occurred
	 */
	@Then("^(?i)I should see that (.*) is the current active Property$")
	public void assertCurrentActiveSite(
			@Transform(FaroTransformer.class) String propertyName)
		throws Exception {

		_faroSelenium.assertText(
			"//div[@class='channels-menu-label']", propertyName);
	}

	/**
	 * Asserts the list of Properties in the Property Menu.
	 *
	 * @param negation to negatively assert
	 * @param dataTable the list of Properties that should be in the Property
	 *        Menu
	 */
	@And(
		"^(?i)I should (not )?see the following Properties in the Property Dropdown Menu:$"
	)
	public void assertPropertyMenuList(String negation, DataTable dataTable)
		throws Exception {

		List<List<String>> dataTableRows = dataTable.raw();

		for (List<String> row : dataTableRows) {
			String propertyName = FaroTestDataUtil.parsePlaceholders(
				row.get(0));

			if (negation == null) {
				_faroSelenium.assertElementPresent(
					_generatePropertyMenuItemXpath(propertyName));
			}
			else {
				_faroSelenium.assertElementNotPresent(
					_generatePropertyMenuItemXpath(propertyName));
			}
		}
	}

	/**
	 * Clicks the Property Dropdown Menu in the sidebar.
	 *
	 * @throws Exception if an exception occurred
	 */
	@When("^(?i)I click the Property Dropdown Menu$")
	public void clickPropertyMenuButton() throws Exception {
		_faroSelenium.click("//button[contains(@class, 'channels-menu')]");
	}

	/**
	 * Clicks a Property in the Property Dropdown Menu
	 *
	 * @param  propertyName the name of the Property
	 * @throws Exception if an exception occurred
	 */
	@When("^(?i)I click (.*) in the Property Dropdown Menu$")
	public void clickPropertyMenuItem(
			@Transform(FaroTransformer.class) String propertyName)
		throws Exception {

		_faroSelenium.click(_generatePropertyMenuItemXpath(propertyName));

		FaroPagePool.setPropertyId(_faroSelenium.getPropertiesIdFromURL());
	}

	/**
	 * @param  propertyName the name of the Property
	 * @return the Xpath for a Property in the Property Dropdown Menu
	 */
	private String _generatePropertyMenuItemXpath(String propertyName) {
		StringBundler sb = new StringBundler(4);

		sb.append("//div[contains(@class, 'channels-menu-dropdown-body')");
		sb.append("]//span[text()='");
		sb.append(propertyName);
		sb.append("']");

		return sb.toString();
	}

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}