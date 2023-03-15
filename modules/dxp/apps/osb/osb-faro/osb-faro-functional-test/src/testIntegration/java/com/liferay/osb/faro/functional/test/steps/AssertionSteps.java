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
import com.liferay.osb.faro.functional.test.util.FaroPagePool;
import com.liferay.osb.faro.functional.test.util.FaroSeleniumUtil;
import com.liferay.osb.faro.functional.test.util.FaroTestDataUtil;
import com.liferay.osb.faro.functional.test.util.FaroTransformer;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import cucumber.api.Transform;
import cucumber.api.java.en.Then;

import java.util.List;

import org.junit.Assert;

import org.openqa.selenium.WebElement;

/**
 * @author Cheryl Tang
 */
public class AssertionSteps {

	/**
	 * Asserts that the driver is on a specified page by checking the navigation
	 * element text.
	 *
	 * @param type the page to expect
	 */
	@Then("^I should see the (.*) (?:page)$")
	public static void checkURL(@Transform(FaroTransformer.class) String type)
		throws Exception {

		if (StringUtil.equalsIgnoreCase(type, "sites")) {
			try {
				_faroSelenium.assertPartialText(
					"//h1/span[@class='text-truncate']", "Properties");
			}
			catch (Exception exception) {
				_faroSelenium.assertPartialText(
					"//h1/span[@class='text-truncate']", "Site");
			}
		}
		else if (StringUtil.equalsIgnoreCase(type, "sign-in")) {
			_faroSelenium.assertPartialText(
				"//div[@class='login-container']/div[contains(@class,'" +
					"sign-in')]",
				"Sign In");
		}
		else {
			_faroSelenium.waitForElementPresent(
				"//h1[@class='title text-truncate']/span");

			_faroSelenium.assertText(
				"//h1[@class='title text-truncate']/span",
				_faroPagePool.getPageTitle(type));
		}
	}

	@Then("^I should see an active member percentage \"(.*)\"$")
	public void assertActiveMemberPercentage(
			@Transform(FaroTransformer.class) String percentNumber)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append("//h4[contains(@class,'table-title') and text()='");
		sb.append(percentNumber);
		sb.append("']");

		_faroSelenium.assertElementPresent(sb.toString());
	}

	/**
	 * Asserts the presence of an alert popup.
	 *
	 * @param  status the status of the alert message, either 'error' or
	 *         'success'
	 * @throws Exception if an exception occurred
	 */
	@Then(
		"^I should see an? (error|success|info|warning) alert(?: saying)?(.*)?$"
	)
	public void assertAlert(
			String status,
			@Transform(FaroTransformer.class) String alertMessage)
		throws Exception {

		_faroSelenium.waitForPageLoadingComplete();

		if (status.equals("error")) {
			String xpath = "//div[contains(@class,'alert-danger')]";

			if (_faroSelenium.isElementNotPresent(xpath)) {
				_faroSelenium.refreshUntilElementPresent(30, 10, xpath);
			}
		}
		else {
			String xpath = "//div[contains(@class,'alert-" + status + "')]";

			if (_faroSelenium.isElementNotPresent(xpath)) {
				_faroSelenium.refreshUntilElementPresent(30, 10, xpath);
			}
		}

		if (alertMessage != null) {
			_faroSelenium.assertPartialText(
				"//div[@role='alert']", alertMessage);
		}
	}

	@Then("^I should (not )?be able to click the (.*) button$")
	public void assertButtonClickable(
			String negation,
			@Transform(FaroTransformer.class) String buttonName)
		throws Exception {

		_faroSelenium.waitForPageLoadingComplete();

		StringBundler sb = new StringBundler(3);

		sb.append("//button[text()='");
		sb.append(buttonName);

		if (negation != null) {
			sb.append("' and @disabled]");
		}
		else {
			sb.append("']");
		}

		_faroSelenium.assertElementPresent(sb.toString());
	}

	@Then("^I should see granularity starts (.*) and ends (.*) in (.*) card$")
	public void assertChartGranularity(
			@Transform(FaroTransformer.class) String start,
			@Transform(FaroTransformer.class) String end,
			@Transform(FaroTransformer.class) String card)
		throws Exception {

		_faroSelenium.waitForElementPresent(
			StringBundler.concat(
				"//h5[text()='", card,
				"']/parent::div/parent::div//*[name()='g' and @class='bb-axis ",
				"bb-axis-x']/*[name()='g'][1]//*[name()='tspan' and ",
				"text()='", start, "']"));

		_faroSelenium.waitForElementPresent(
			StringBundler.concat(
				"//h5[text()='", card,
				"']/parent::div/parent::div//*[name()='g' and @class='bb-axis ",
				"bb-axis-x']/*[name()='g'][last()]//*[name()='tspan",
				"' and text()='", end, "']"));
	}

	@Then("^I should see time filter (.*) in(?: )?(.*)? card$")
	public void assertDateTimeFilter(
			@Transform(FaroTransformer.class) String date,
			@Transform(FaroTransformer.class) String card)
		throws Exception {

		if (!card.equals("")) {
			_faroSelenium.waitForElementPresent(
				StringBundler.concat(
					"//h5[text()='", card, "']/parent::div//button[text()='",
					date, "']"));
		}
		else {
			_faroSelenium.waitForElementPresent(
				"//div[contains(@class,'dropdown')]/button[text()='" + date +
					"']");
		}
	}

	/**
	 * Asserts the presence of options in a dropdown menu.
	 *
	 * @param  optionsList the list of options to expect
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see the following options in the dropdown menu$")
	public void assertDropdownOptions(List<String> optionsList)
		throws Exception {

		for (String option : optionsList) {
			_faroSelenium.assertElementPresent(
				"//div[contains(@class,'dropdown-menu show')]/*[text()='" +
					option + "']");
		}
	}

	@Then("^I should see standardized granularity of \"([^\"]*)\"$")
	public void assertGraphGranularity(
		@Transform(FaroTransformer.class) String pattern) {

		WebElement chartXAxis = _faroSelenium.findElement(
			"//*[name()='g' and @class='bb-axis bb-axis-x']/*[name()='g']" +
				"[1]//*[name()='tspan']|//*[name()='g' and contains(@class,'" +
					"axis-ticks')]/*[name()='g'][1]//*[name()='tspan']");

		String chartXAxisValue = chartXAxis.getText();

		Assert.assertTrue(
			FaroTestDataUtil.assertDateFormat(chartXAxisValue, pattern));
	}

	@Then("^I should see (.*) graph selected$")
	public void assertGraphSelected(
			@Transform(FaroTransformer.class) String target)
		throws Exception {

		_faroSelenium.assertElementPresent(
			"//li[@class='card-tab active']//span[contains(text(),'" + target +
				"')]");

		_faroSelenium.assertElementPresent(
			"//*[name()='svg' and @class='recharts-surface']" +
				"/*[name()='g' and contains(@class,'recharts-bar')]");
	}

	/**
	 * Asserts the input field value.
	 *
	 * @param  input the input string
	 * @param  targetName the target element's name or another identifier
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see (.*) in the (.*) input field$")
	public void assertInputFieldValue(
			@Transform(FaroTransformer.class) String input,
			@Transform(FaroTransformer.class) String targetName)
		throws Exception {

		_faroSelenium.waitForLoadingComplete();

		StringBundler sb = new StringBundler(9);

		sb.append("//div[contains(.,'");
		sb.append(targetName);
		sb.append("')]/input[@type='text' and @value='");
		sb.append(input);
		sb.append("']|//input[@placeholder='");
		sb.append(targetName);
		sb.append("' and @value='");
		sb.append(input);
		sb.append("']");

		String xpath = sb.toString();

		_faroSelenium.waitForElementPresent(xpath);

		_faroSelenium.assertElementPresent(xpath);
	}

	@Then("^I should see (.*) displayed top of the page$")
	public void assertNameTopOfPage(
			@Transform(FaroTransformer.class) String targetName)
		throws Exception {

		StringBundler sb = new StringBundler(3);

		sb.append("//h1/span[text()='");
		sb.append(targetName);
		sb.append("']");

		_faroSelenium.assertElementPresent(sb.toString());
	}

	/**
	 * Asserts that the expected text is present anywhere on the page.
	 *
	 * @param  expectedText the text to expect on the page
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see text saying (.*) on the page$")
	public void assertPageContainsText(
			@Transform(FaroTransformer.class) String expectedText)
		throws Exception {

		_faroSelenium.waitForTextPresent(expectedText);
	}

	@Then("^I should see pagination delta is set to \"(.*)\"$")
	public void assertPaginationDelta(
			@Transform(FaroTransformer.class) String delta)
		throws Exception {

		_faroSelenium.waitForLoadingComplete();

		_faroSelenium.assertText(
			"//button[contains(@class,'dropdown-toggle')]/span",
			delta + " Items");
	}

	/**
	 * Asserts percent change in engagement score
	 *
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see a percent change$")
	public void assertPercentChange() throws Exception {
		_faroSelenium.assertElementPresent(
			"//span[@class='net-change increase']");

		_faroSelenium.assertNotText(
			"//span[@class='net-change increase']", "+0(0%)");
	}

	@Then("^I should see (.*) in a table(?: row containing)?(.*)?$")
	public void assertTableItem(
			@Transform(FaroTransformer.class) String targetName,
			@Transform(FaroTransformer.class) String containingName)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("//table//*[text()='");
		sb.append(targetName);
		sb.append("']");

		if (containingName != null) {
			sb.append("/ancestor::tr//*[text()='");
			sb.append(targetName);
			sb.append("']");
		}

		_faroSelenium.assertElementPresent(sb.toString());
	}

	@Then("^I should see (.*) total associated segments$")
	public void assertTotalSegments(
			@Transform(FaroTransformer.class) String totalNumber)
		throws Exception {

		_faroSelenium.assertText(
			"//div[@class='secondary-info']", totalNumber + " Segments");
	}

	@Then("^I should see (.*) total visitors$")
	public void assertTotalVisitors(
			@Transform(FaroTransformer.class) String totalVisitors)
		throws Exception {

		_faroSelenium.assertText(
			"//table[@class='cohort-chart-root']//th[contains(@class,'" +
				"visitors table')]",
			totalVisitors);
	}

	private static final FaroPagePool _faroPagePool = new FaroPagePool();
	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}