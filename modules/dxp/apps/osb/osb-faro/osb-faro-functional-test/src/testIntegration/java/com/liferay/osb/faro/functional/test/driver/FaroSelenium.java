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

package com.liferay.osb.faro.functional.test.driver;


import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.liferay.poshi.core.selenium.LiferaySelenium;

/**
 * @author Cheryl Tang
 */
public interface FaroSelenium extends LiferaySelenium, WebDriver {

	public void assertWebElementHasAnyText(String xpath);

	@Override
	public void click(String locator);

	public void click(WebElement webElement);

	public void clickIcon(String className);

	public void dragAndDropChrome(String fromElement, String toElement)
		throws Exception;

	public WebElement findElement(String xpath);

	public WebElement findElement(String xpath, WebElement webElement);

	public List<WebElement> findElements(String xpath);

	public void forceWindowFocus();

	public String getIdFromURL() throws Exception;

	public String getPropertiesIdFromURL() throws Exception;

	public String getWorkspaceIdFromURL() throws Exception;

	public void refreshUntilElementNotPresent(
			int timeout, int pollInterval, String xpath)
		throws Exception;

	public void refreshUntilElementPresent(
			int timeout, int pollInterval, String xpath)
		throws Exception;

	public void refreshUntilTextAsserted(
			int timeout, int pollInterval, String xpath, String expectedValue)
		throws Exception;

	@Override
	public void sendKeys(String locator, String value) throws Exception;

	public void setMainWindowHandle();

	public void switchToMainWindow();

	public void switchToPopupWindow() throws Exception;

	public void waitForElementNotPresent(String locator) throws Exception;

	public void waitForElementPresent(String locator) throws Exception;

	public void waitForLoadingComplete() throws Exception;

	public void waitForPageLoadingComplete();

	public boolean webElementContainsText(WebElement webElement, String pattern)
		throws Exception;

}