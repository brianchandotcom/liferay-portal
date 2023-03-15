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
import com.liferay.osb.faro.functional.test.util.FaroTransformer;
import com.liferay.portal.kernel.util.StringBundler;

import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * @author Joshua Itagaki
 */
public class DefinitionPage {

	@When("^I click keyword \"(.*)\" checkbox$")
	public void clickKeywordCheckbox(String name) throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append("//h4[text()='");
		sb.append(name);
		sb.append("']/../preceding-sibling::td/div/label/input");

		String xpath = sb.toString();

		_faroSelenium.waitForElementPresent(xpath);

		WebElement webElement = _faroSelenium.findElement(xpath);

		webElement.sendKeys(Keys.SPACE);
	}

	@Then("^I delete the keyword in row \"(.*)\"$")
	public void deleteKeyword(String row) throws Exception {
		Thread.sleep(3000);

		StringBundler sb = new StringBundler(3);

		sb.append("(//button[contains(@class,'btn-outline')])[");
		sb.append(row);
		sb.append("]");

		String xpath = sb.toString();

		WebElement webElement = _faroSelenium.findElement(xpath);

		webElement.click();
	}

	@When("^I name multiple keywords:$")
	public void multipleKeywords(DataTable dataTable) {
		List<List<String>> dataTableRows = dataTable.raw();

		for (List<String> row : dataTableRows) {
			String property = row.get(0);

			WebElement webElement = _faroSelenium.findElement(
				"//input[@type='text' and @placeholder='Enter Keyword']");

			webElement.clear();

			webElement.sendKeys(property);

			webElement.sendKeys(Keys.RETURN);
		}
	}

	/**
	 * Names the keyword
	 *
	 * @param name the name for the keyowrd being created
	 */
	@When("^I name the keyword (.*)$")
	public void nameKeyword(@Transform(FaroTransformer.class) String name)
		throws Exception {

		_faroSelenium.waitForPageLoadingComplete();
		_faroSelenium.waitForLoadingComplete();

		_faroSelenium.sendKeys(
			"//input[@type='text' and @placeholder='Enter Keyword']", name);
	}

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}