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

package com.liferay.osb.faro.functional.test;

import com.liferay.osb.faro.functional.test.driver.FaroSelenium;
import com.liferay.osb.faro.functional.test.util.FaroSeleniumUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.util.PropsUtil;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

/**
 * @author Cheryl Tang
 */
@CucumberOptions(
	features = "src/testIntegration/resources/features",
	glue = {
		"com.liferay.osb.faro.functional.test.pages",
		"com.liferay.osb.faro.functional.test.steps"
	},
	plugin = {
		"pretty", "json:build/reports/json/cucumber-report.json",
		"com.cucumber.listener.ExtentCucumberFormatter"
	},
	tags = {"~@blocked", "~@prototype", "~@skip"}
)
@RunWith(Cucumber.class)
public class FaroTest {

	@BeforeClass
	public static void setUpClass() {
		System.setProperty(
			"wdm.targetPath", "src/testIntegration/resources/webdriver");

		WebDriverManager webDriverManager = WebDriverManager.chromedriver();

		webDriverManager.browserVersion(
			System.getProperty("chrome.driver.version", "79.0.3945.36"));

		webDriverManager.setup();

		PropsUtil.set("analytics.cloud.url", "http://localhost:8080");

		PropsUtil.set("browser.type", "chrome");

		PropsUtil.set(
			"portal.url",
			System.getProperty("portal.url", "http://localhost:7080"));

		PropsUtil.set(
			"selenium.chrome.driver.executable",
			System.getProperty("webdriver.chrome.driver"));

		PropsUtil.set("selenium.executable.dir.name", StringPool.BLANK);
		PropsUtil.set("timeout.explicit.wait", "30");

		FaroSeleniumUtil.createReport();

		System.setProperty(
			"webdriver.chrome.logfile",
			"src/testIntegration/resources/webdriver/chromedriver/driver.log");
		System.setProperty("webdriver.chrome.verboseLogging", "true");
	}

	@AfterClass
	public static void tearDownClass() {
		FaroSelenium faroSelenium = FaroSeleniumUtil.getFaroSelenium();

		faroSelenium.close();
		faroSelenium.quit();
	}

}