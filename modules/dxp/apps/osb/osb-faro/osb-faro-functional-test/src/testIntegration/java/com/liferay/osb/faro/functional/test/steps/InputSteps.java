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
import com.liferay.osb.faro.functional.test.util.FaroSeleniumUtil;
import com.liferay.osb.faro.functional.test.util.FaroTransformer;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import cucumber.api.Transform;
import cucumber.api.java.en.When;

/**
 * @author Cheryl Tang
 */
public class InputSteps {

	/**
	 * Clears then enters text into the search bar.
	 *
	 * @param input string to enter into the search bar
	 */
	@When("I enter (.*) in the search bar")
	public static void inputSearchBar(
			@Transform(FaroTransformer.class) String input)
		throws Exception {

		_faroSelenium.sendKeys(
			"//form//input[@type='text' and @placeholder = 'Search']", input);
	}

	/**
	 * Enters text into an input field or text area.
	 *
	 * @param input the input string
	 * @param targetName the target element's name or another identifier
	 * @param fieldType type of input field, either 'text area' or 'input'
	 */
	@When("I type (.*) into the (.*) (text area|input)(?: field)?")
	public static void inputText(
			@Transform(FaroTransformer.class) String input,
			@Transform(FaroTransformer.class) String targetName,
			String fieldType)
		throws Exception {

		StringBundler sb = new StringBundler(26);

		sb.append("//div[contains(.,'");
		sb.append(targetName);
		sb.append("')]/");

		fieldType = StringUtil.strip(fieldType, CharPool.SPACE);

		sb.append(fieldType);

		sb.append("|//");
		sb.append(fieldType);
		sb.append("[@placeholder='");
		sb.append(targetName);
		sb.append("']|//label/following::");
		sb.append(fieldType);
		sb.append("[text()='");
		sb.append(targetName);
		sb.append("']|//label[text()='");
		sb.append(targetName);
		sb.append("']/following-sibling::div//");
		sb.append(fieldType);
		sb.append("|//");
		sb.append(fieldType);
		sb.append("[@name='");
		sb.append(targetName);
		sb.append("']|//label[contains(text(),'");
		sb.append(targetName);
		sb.append("')]/parent::div/input|//span[text()='");
		sb.append(targetName);
		sb.append("']/parent::label/following-sibling::div/div/");
		sb.append(fieldType);

		_faroSelenium.waitForElementPresent(sb.toString());

		_faroSelenium.sendKeys(sb.toString(), input);
	}

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}