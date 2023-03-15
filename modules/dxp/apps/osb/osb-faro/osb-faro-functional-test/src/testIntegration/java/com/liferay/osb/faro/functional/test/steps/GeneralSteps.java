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
import com.liferay.poshi.runner.selenium.WebDriverUtil;
import com.liferay.poshi.runner.util.StringUtil;

import cucumber.api.Transform;
import cucumber.api.java.en.And;
import cucumber.api.java.en.When;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import java.nio.charset.StandardCharsets;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Cheryl Tang
 */
public class GeneralSteps {

	/**
	 * Accepts or dismisses a javascript alert popup.
	 *
	 * @param action the action on the alert, either 'accept' or 'dismiss'
	 */
	@When("I (accept|dismiss) the alert")
	public static void handleAlert(String action) {
		WebDriver webDriver = WebDriverUtil.getWebDriver();

		WebDriver.TargetLocator targetLocator = webDriver.switchTo();

		Alert alert = targetLocator.alert();

		if (StringUtil.equalsIgnoreCase(action, "accept")) {
			alert.accept();
		}
		else {
			alert.dismiss();
		}
	}

	/**
	 * Browses for a file by name to upload. Looks for the file in the temp
	 * testData directory.
	 *
	 * @param fileName name of the file, including file extension
	 */
	@When("^I browse for a file named (.*)$")
	public void browseAndUpload(
			@Transform(FaroTransformer.class) String fileName)
		throws Exception {

		File file = new File(FaroSeleniumUtil.getDependenciesDir(), fileName);

		_faroSelenium.uploadFile(
			"//input[contains(@id,'_faro_portlet_fileUploader')]",
			file.getAbsolutePath());

		_faroSelenium.waitForElementPresent(
			"//div[@class='status-wrapper']" +
				"/descendant::*[contains(@class,'lexicon-icon-check-circle')]");
	}

	@And("^I close the alert$")
	public void closeAlert() throws Exception {
		String closeAlert = "//button[@class='close']";

		_faroSelenium.waitForElementPresent(closeAlert);

		WebElement webElement = _faroSelenium.findElement(closeAlert);

		webElement.click();
	}

	@And("^I close the focused window$")
	public void closeFocusedWindow() {
		_faroSelenium.close();
	}

	/**
	 * Creates a file with a given name from a Cucumber Doc String.
	 *
	 * @param  fileName name to save the file as, including file extension
	 * @throws IOException
	 */
	@When("^I create a file named (.*) with the following content:$")
	public void createFile(
			@Transform(FaroTransformer.class) String fileName,
			@Transform(FaroTransformer.class) String dataTable)
		throws IOException {

		File file = new File(FaroSeleniumUtil.getDependenciesDir(), fileName);

		try (Writer writer = new OutputStreamWriter(
				new FileOutputStream(file), StandardCharsets.UTF_8);
			PrintWriter printWriter = new PrintWriter(writer)) {

			printWriter.write(dataTable);
		}
	}

	private static final FaroSelenium _faroSelenium =
		FaroSeleniumUtil.getFaroSelenium();

}