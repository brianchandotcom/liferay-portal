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

import com.liferay.osb.faro.functional.test.steps.ClickSteps;
import com.liferay.osb.faro.functional.test.steps.InputSteps;
import com.liferay.osb.faro.functional.test.util.FaroTestConstants;
import com.liferay.osb.faro.functional.test.util.FaroTransformer;

import cucumber.api.Transform;
import cucumber.api.java.en.When;

/**
 * @author Cheryl Tang
 */
public class CreateTouchpointPage {

	/**
	 * Creates a touchpoint.
	 *
	 * @param name the name of the touchpoint
	 * @param url the url of the touchpoint
	 */
	@When("I create a Touchpoint named (.*) for the url: (.*)")
	public static void createTouchpoint(
			@Transform(FaroTransformer.class) String name,
			@Transform(FaroTransformer.class) String url)
		throws Exception {

		InputSteps.inputText(name, "name", FaroTestConstants.INPUT_TYPE_INPUT);
		InputSteps.inputText(url, "url", FaroTestConstants.INPUT_TYPE_INPUT);

		ClickSteps.clickButton("Next Step");
		ClickSteps.clickButton("Create Touchpoint");
	}

}