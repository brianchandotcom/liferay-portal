/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.portal.util;

import java.util.List;
import com.thoughtworks.selenium.Selenium;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * @author Brian Wing Shun Chan
 */
public interface LiferaySelenium extends WebDriver {
	public void acceptConfirmation();

	public void addSelection(String param2, String param3);

	public boolean assertConfirmation(String param2);

	public void check(String param2);

	public void click(String param2);

	public void clickAt(String param2, String param3);	

	public void clearAndType(String param2, String param3);

	public void doubleClick(String param2);

	public void downloadFile(String value);

	public void dragAndDropToObject(String param2, String param3);	

	public String getAttribute(String param2);

	public String getConfirmationText();

	public String getElementText(String param2);

	public String getFirstNumber(String locator);

	public String getFirstNumberIncrement(String locator);

	public String getLocation();

	public String getSelectedLabel(String param2);

	public String [] getSelectedLabels(String param2);

	public String [] getSelectedOptions(String param2);

	public String getText(String param2);

	public String getValue(String param2);

	public WebElement getWebElement(String param2);

	public List<WebElement> getWebElements(String param2);

	public boolean isChecked(String param2);

	public boolean isElementPresent(String param2);

	public boolean isPartialText(String param2, String param3);

	public boolean isText(String param2, String param3);

	public boolean isTextPresent(String param2);

	public boolean isValue(String param2, String param3);

	public boolean isVisible(String param2);

	public void keyPress(String param2, String param3);

	public void longImplicitWait();	

	public void mouseDown(String param2);

	public void mouseMove(String param2);

	public void mouseMoveAt(String param2, String param3);

	public void mouseOver(String param2);

	public void mouseUp(String param2);

	public void open(String param2);

	public void refresh();

	public void select(String param2, String param3);

	public void selectAndWait(String param2, String param3);

	public void selectFrame(String param2);

	public void selectWindow(String param2);

	public void setBrowserOption();

	public void setTimeOut(String param2);

	public void shortImplicitWait();

	public void type(String param2, String param3);

	public void uncheck(String param2);

	public void uploadFile(String locator, String value);

	public void windowMaximize();

}