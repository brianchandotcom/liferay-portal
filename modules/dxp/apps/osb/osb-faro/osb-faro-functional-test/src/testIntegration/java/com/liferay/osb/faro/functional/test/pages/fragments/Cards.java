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
import com.liferay.portal.kernel.util.StringBundler;

import cucumber.api.java.en.Then;

/**
 * @author Cheryl Tang
 */
public class Cards {

	/**
	 * Asserts the presence of a card by title.
	 *
	 * @param  cardName the name of the card
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should see a (.*) card$")
	public void assertCard(String cardName) throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append("//div[contains(@class,'card ')]");
		sb.append("/descendant::div[@title and text()='");
		sb.append(cardName);
		sb.append("']");

		_faroSelenium.assertElementPresent(sb.toString());
	}

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}