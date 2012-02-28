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

import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;


/**
 * @author Brian Wing Shun Chan
 */
public class LiferayDefaultSelenium
	extends FirefoxDriver implements LiferaySelenium {

	public LiferayDefaultSelenium() {
		super();
		initializeKeyTable();
	}


	public void acceptConfirmation() {
		WebDriver.TargetLocator targetLocator = switchTo();
		Alert alert = targetLocator.alert();
		alert.accept();
	}

	public void addSelection(String param2, String param3) {
		Select select = new Select(getWebElement(param2));

		if (param3.startsWith("label=")) {
			select.selectByVisibleText(param3.substring(6));
		} else if (param3.startsWith("value=")) {
			select.selectByValue(param3.substring(6));			
		} else if (param3.startsWith("index=")) {
			select.selectByIndex(Integer.parseInt(param3.substring(6)));
		} else {
			select.selectByVisibleText(param3);	
		}	
	}

	public boolean assertConfirmation(String param2) {
		return getConfirmation().matches("^" + param2 + "$");	
	}

	public void captureEntirePageScreenshot(String param2, String param3) {
		// do nothing?
	}

	public void check(String param2) {
		click(param2);
	}

	public void clearAndType(String param2, String param3) {
		WebElement element = getWebElement(param2);

		if (element.isEnabled()) {
			element.clear();
			element.sendKeys(param3);	
		}
	}

	public void click(String param2) {
		WebElement element = getWebElement(param2);
		element.click();
	}

	public void clickAt(String param2, String param3) {
		Actions actions = new Actions(this);
		WebElement element = getWebElement(param2);

		if (param3.contains(",")) {
			String [] coords = param3.split(",");
			int x = Integer.parseInt(coords[0]);
			int y = Integer.parseInt(coords[1]);

			actions.moveToElement(element, x, y);
			actions.click();		
		} else {
			element.click();
		}
	}

	public void doubleClick(String param2) {
		Actions actions = new Actions(this);
		WebElement element = getWebElement(param2);
		actions.doubleClick(element);
		actions.build();
		actions.perform();
	}

	// edit this to account for coords
	public void doubleClickAt(String param2, String param3) {
		Actions actions = new Actions(this);		
		WebElement element = getWebElement(param2);

		if (param3.contains(",")) {
			String [] coords = param3.split(",");
			int x = Integer.parseInt(coords[0]);
			int y = Integer.parseInt(coords[1]);

			actions.moveToElement(element, x, y);
			actions.doubleClick(element);
			actions.build();
			actions.perform();			
		} else {
			actions.doubleClick(element);
			actions.build();
			actions.perform();
		}
	}	

	public void downloadFile(String value) {
		if (!_BROWSER_TYPE.equals("*chrome") &&
			!_BROWSER_TYPE.equals("*firefox") &&
			!_BROWSER_TYPE.equals("*iehta") &&
			!_BROWSER_TYPE.equals("*iexplore")) {

			return;
		}

		try {
			String[] commands = {
				RuntimeVariables.replace(
					_SELENIUM_EXECUTABLE_DIR +
						TestPropsValues.SELENIUM_DOWNLOAD_FILE),
				TestPropsValues.OUTPUT_DIR + value
			};

			Runtime runtime = Runtime.getRuntime();

			Thread.sleep(5000);

			runtime.exec(commands);

			Thread.sleep(30000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dragAndDropToObject(String param2, String param3) {
		Actions actions = new Actions(this);
		WebElement element1 = getWebElement(param2);
		WebElement element2 = getWebElement(param3);

		actions.dragAndDrop(element1, element2);
		actions.build();
		actions.perform();
	}

	public String getAttribute(String param2){
		String[] a = param2.split("@");
		String locator = "";
		int last = a.length - 1;

		for(int i = 0; i < last; i++){
			if(i != last - 1){
				locator = locator + a[i] + "@";	
			}
			else{
				locator = locator + a[i];
			}
		}

		WebElement element = getWebElement(locator);
		return element.getAttribute(a[last]);
	}

	public String getConfirmation() {
		WebDriver.TargetLocator targetLocator = switchTo();
		Alert alert = targetLocator.alert();
		return alert.getText();
	}

	public String getElementText(String param2) {
		WebElement element = getWebElement(param2);
		return element.getText();
	}	

	public String getFirstNumber(String param2) {
		String text = getWebElement(param2).getText();

		if (text == null){
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler();

		char[] chars = text.toCharArray();

		for (char c : chars) {
			boolean isDigit = false;

			if (Validator.isDigit(c)) {
				sb.append(c);
				isDigit = true;
			}

			if ((Validator.isNotNull(sb.toString())) && (isDigit == false)) {
				break;
			}
		}
		return sb.toString();
	}

	public String getFirstNumberIncrement(String param2) {
		String firstNumber = getFirstNumber(param2);
		int i = Integer.parseInt(firstNumber);
		return Integer.toString(i + 1);
	}	

	public String getLocation() {
		return getCurrentUrl();
	}

	public String getSelectedLabel(String param2) {
		WebElement element = getWebElement(param2);
		Select select = new Select(element);	
		return select.getFirstSelectedOption().getText();
	}

	public String [] getSelectedLabels(String param2) {
		WebElement element = getWebElement(param2);
		Select select = new Select(element);
		List<WebElement> webElements = select.getAllSelectedOptions();
		String [] labels = new String[webElements.size()];

		for (int i = 0; i < webElements.size(); i++) {
			labels[i] = webElements.get(i).getText();
		}

		return labels;
	}

	public String [] getSelectedOptions(String param2) {
		WebElement element = getWebElement(param2);
		Select select = new Select(element);
		List<WebElement> webElements = select.getOptions();
		String [] labels = new String[webElements.size()];

		for (int i = 0; i < webElements.size(); i++) {
			labels[i] = webElements.get(i).getText();
		}

		return labels;
	}

	public String getText(String param2) {
		WebElement element = getWebElement(param2);
		return element.getText();
	}

	public String getValue(String param2) {
		WebElement element = getWebElement(param2);
		return element.getAttribute("value");
	}

	public WebElement getWebElement(String param2) {					
		if (param2.startsWith("//")) {
			return findElement(By.xpath(param2));
		} else if (param2.startsWith("xpath=")) {
			return findElement(By.xpath(param2.substring(6)));
		} else if (param2.startsWith("link=")) {
			return findElement(By.linkText(param2.substring(5)));
		} else if (param2.startsWith("name=")) {
			return findElement(By.name(param2.substring(5)));
		} else if (param2.startsWith("class=")) {
			return findElement(By.className(param2.substring(6)));
		} else if (param2.startsWith("tag=")) {
			return findElement(By.tagName(param2.substring(4)));
		} else if (param2.startsWith("css=")) {
			return findElement(By.cssSelector(param2.substring(4)));			
		} else {
			return findElement(By.id(param2));
		}
	}

	public List<WebElement> getWebElements(String param2) {					
		if (param2.startsWith("//")) {
			return findElements(By.xpath(param2));
		} else if (param2.startsWith("xpath=")) {
			return findElements(By.xpath(param2.substring(6)));
		} else if (param2.startsWith("link=")) {
			return findElements(By.linkText(param2.substring(5)));
		} else if (param2.startsWith("name=")) {
			return findElements(By.name(param2.substring(5)));
		} else if (param2.startsWith("class=")) {
			return findElements(By.className(param2.substring(6)));
		} else if (param2.startsWith("tag=")) {
			return findElements(By.tagName(param2.substring(4)));
		} else if (param2.startsWith("css=")) {
			return findElements(By.cssSelector(param2.substring(4)));			
		} else {
			return findElements(By.id(param2));
		}
	}	

	public void initializeKeyTable() {
		keyTable[107] = Keys.ADD;
		keyTable[18] = Keys.ALT;
		keyTable[40] = Keys.ARROW_DOWN;
		keyTable[37] = Keys.ARROW_LEFT;
		keyTable[39] = Keys.ARROW_RIGHT;
		keyTable[38] = Keys.ARROW_UP;
		keyTable[8] = Keys.BACK_SPACE;
		//keyTable[] = Keys.CANCEL;
		//keyTable[] = Keys.CLEAR;
		//keyTable[] = Keys.COMMAND;
		keyTable[17] = Keys.CONTROL;
		keyTable[110] = Keys.DECIMAL;
		keyTable[46] = Keys.DELETE;
		keyTable[111] = Keys.DIVIDE;
		//keyTable[] = Keys.DOWN;
		//keyTable[] = Keys.END;
		keyTable[13] = Keys.ENTER;
		//keyTable[] = Keys.EQUALS;
		keyTable[27] = Keys.ESCAPE;
		keyTable[112] = Keys.F1;
		keyTable[121] = Keys.F10;
		keyTable[122] = Keys.F11;
		keyTable[123] = Keys.F12;
		keyTable[113] = Keys.F2;
		keyTable[114] = Keys.F3;
		keyTable[115] = Keys.F4;
		keyTable[116] = Keys.F5;
		keyTable[117] = Keys.F6;
		keyTable[118] = Keys.F7;
		keyTable[119] = Keys.F8;
		keyTable[120] = Keys.F9;
		//keyTable[] = Keys.HELP;
		keyTable[36] = Keys.HOME;
		keyTable[45] = Keys.INSERT;
		//keyTable[] = Keys.LEFT;
		//keyTable[] = Keys.LEFT_ALT;
		//keyTable[] = Keys.LEFT_CONTROL;
		//keyTable[] = Keys.LEFT_SHIFT;
		//keyTable[] = Keys.META;
		//keyTable[] = Keys.NULL;
		keyTable[96] = Keys.NUMPAD0;
		keyTable[97] = Keys.NUMPAD1;
		keyTable[98] = Keys.NUMPAD2;
		keyTable[99] = Keys.NUMPAD3;
		keyTable[100] = Keys.NUMPAD4;
		keyTable[101] = Keys.NUMPAD5;
		keyTable[102] = Keys.NUMPAD6;
		keyTable[103] = Keys.NUMPAD7;
		keyTable[104] = Keys.NUMPAD8;
		keyTable[105] = Keys.NUMPAD9;
		keyTable[34] = Keys.PAGE_DOWN;
		keyTable[33] = Keys.PAGE_UP;
		keyTable[19] = Keys.PAUSE;
		//keyTable[] = Keys.RETURN;
		//keyTable[] = Keys.RIGHT;
		//keyTable[] = Keys.SEMICOLON;
		//keyTable[] = Keys.SEPARATOR;
		keyTable[16] = Keys.SHIFT;
		keyTable[32] = Keys.SPACE;
		keyTable[109] = Keys.SUBTRACT;
		keyTable[9] = Keys.TAB;
		//keyTable[] = Keys.UP;
	}

	public boolean isChecked(String param2) {
		WebElement element = getWebElement(param2);
		return element.isSelected();
	}

	public boolean isElementPresent(String param2) {
		List<WebElement> webElements = getWebElements(param2);
		shortImplicitWait();
		return (getWebElements(param2).size() > 0);
	}	

	public boolean isPartialText(String param2, String param3) {
		WebElement element = getWebElement(param2);
		String text = element.getText();
		return text.contains(param3);					
	}		

	public boolean isText(String param2, String param3) {
		WebElement element = getWebElement(param2);
		String text = element.getText();
		return param3.equals(text);
	}

	public boolean isTextPresent(String param2) {
		WebElement body = findElement(By.tagName("body"));
		String bodyText = body.getText(); 
		return bodyText.contains(param2);
	}
	
	public boolean isValue(String param2, String param3) {
		WebElement element = getWebElement(param2);
		String value = element.getAttribute("value");
		return param3.equals(value);
	}

	public boolean isVisible(String param2) {
		WebElement element = getWebElement(param2);
		return element.isDisplayed();
	}

	public void keyPress(String param2, String param3) {
		Actions actions = new Actions(this);
		WebElement element = getWebElement(param2);

		if (param2.startsWith("\\")) {
			String sub = param3.substring(1, (param3.length()));
			int index = Integer.parseInt(sub);
			Keys key = keyTable[index];
			actions.keyDown(element, key);
		} else {
			element.sendKeys(param3);
		}
	}

	public void loadRequiredJavaScriptModules() {
		// do nothing
	}

	public void longImplicitWait() {
		manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void mouseDown(String param2) {
		Actions actions = new Actions(this);
		WebElement element = getWebElement(param2);
		WebDriverBackedSelenium selenium1 = new WebDriverBackedSelenium(
			this, getCurrentUrl());

		selenium1.mouseDown(param2);
	}

	public void mouseMove(String param2) {
		Actions actions = new Actions(this);
		WebElement element = getWebElement(param2);
		WebDriverBackedSelenium selenium1 = new WebDriverBackedSelenium(
			this, getCurrentUrl());

		selenium1.mouseMove(param2);
	}

	public void mouseMoveAt(String param2, String param3) {
		Actions actions = new Actions(this);
		WebElement element = getWebElement(param2);
		String [] coords = param3.split(",");
		int x = Integer.parseInt(coords[0]);
		int y = Integer.parseInt(coords[1]);

		WebDriverBackedSelenium selenium1 = new WebDriverBackedSelenium(
			this, getCurrentUrl());
		selenium1.mouseMoveAt(param2, param3);
	}

	public void mouseOver(String param2) {
		Actions a = new Actions(this);
		WebElement e = getWebElement(param2);

		WebDriverBackedSelenium selenium1 = new WebDriverBackedSelenium(
			this, getCurrentUrl());
		selenium1.mouseOver(param2);
	}	

	public void mouseUp(String param2) {
		Actions a = new Actions(this);
		WebElement e = getWebElement(param2);

		WebDriverBackedSelenium selenium1 = new WebDriverBackedSelenium(
			this, getCurrentUrl());
		selenium1.mouseUp(param2);
	}

	public void open(String param2) {
		String portalURL = TestPropsValues.PORTAL_URL;

		if (param2.startsWith("/")) {
			get(portalURL + param2);
		} else {
			get(param2);
		}
	}

	public void openWindow(String param2, String param3) {
		open(param2);
	}

	public void refresh() {
		navigate().refresh();
	}

	public void select(String param2, String param3) {
		WebElement element = getWebElement(param2);
		Select select = new Select(element);

		if (param3.startsWith("label=")) {
			select.selectByVisibleText(param3.substring(6));
		} else if (param3.startsWith("value=")) {
			select.selectByValue(param3.substring(6));			
		} else if (param3.startsWith("index=")) {
			select.selectByIndex(Integer.parseInt(param3.substring(6)));
		} else {
			select.selectByVisibleText(param3);	
		}
	}

	public void selectAndWait(String param2, String param3) {
		WebElement element = getWebElement(param2);
		Select select = new Select(element);

		if (param3.startsWith("label=")) {
			select.selectByVisibleText(param3.substring(6));
		} else if (param3.startsWith("value=")) {
			select.selectByValue(param3.substring(6));			
		} else if (param3.startsWith("index=")) {
			select.selectByIndex(Integer.parseInt(param3.substring(6)));
		} else {
			select.selectByVisibleText(param3);	
		}
	}

	public void selectFrame(String param2) {
		 if (param2.equals("relative=top")) {
  			switchTo().defaultContent();
  		 } else if (param2.equals("null")) {
  		 	switchTo().frame("null");
  		 } else {
   			WebElement frameElement = getWebElement(param2);
   			switchTo().frame(frameElement); 
  		 }
 	}

 	public void selectPopUp(String param2) {
 		// do nothing
 	}

	public void selectWindow(String param2) {
		if (param2.startsWith("title=")) {
			Set<String> availableWindows = getWindowHandles(); 
	        if (!availableWindows.isEmpty()) {
		        for (String windowId : availableWindows) {
		        	String windowTitle1 = param2.substring(6);
		        	String windowTitle2 = switchTo().window(windowId).getTitle();
		        	if (windowTitle2.equals(windowTitle1)) {
		        		switchTo().window(windowId);
		        	}
		        }
		    }
		} else if (param2.equals("null") || param2.equals("relative=top")) {
			switchTo().defaultContent();
		} else {
			switchTo().window(param2);
		}
	}

	public void setBrowserOption() {
		if (!_BROWSER_TYPE.equals("*chrome") &&
			!_BROWSER_TYPE.equals("*firefox")) {

			return;
		}

		try {
			String command = RuntimeVariables.replace(
				_SELENIUM_EXECUTABLE_DIR +
					TestPropsValues.SELENIUM_SET_BROWSER_OPTION);

			Runtime runtime = Runtime.getRuntime();

			runtime.exec(command);

			Thread.sleep(10000);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setTimeout(String param2) {
		manage().timeouts().implicitlyWait(
			Long.valueOf(param2), TimeUnit.MILLISECONDS);
	}

	public void shortImplicitWait() {
		manage().timeouts().implicitlyWait(
			1, TimeUnit.SECONDS);
	}

	public void type(String param2, String param3) {
		clearAndType(param2, param3);
	}

	public void typeKeys(String param2, String param3) {
		WebElement element = getWebElement(param2);
		
		if (element.isEnabled()) {
			element.sendKeys(param3);			
		}
	}


	public void uncheck(String param2) {
		WebElement element = getWebElement(param2);
		if (element.isSelected()){
			click(param2);
		} 
	}

	public void windowFocus() {
		// do nothing
	}

	public void windowMaximize () {
		WebDriverBackedSelenium selenium1 = new WebDriverBackedSelenium(
			this, getCurrentUrl());
		selenium1.windowMaximize();
	}

	public void waitForPageToLoad(String duration) {
		// do nothing
	}

	public void waitForPopUp(String param2, String param3) {
		// do nothing
	}

	public void waitForTable(String param2) {
		// do nothing
	}

	public void uploadFile(String location, String value) {
		if (!_BROWSER_TYPE.equals("*chrome") &&
			!_BROWSER_TYPE.equals("*firefox") &&
			!_BROWSER_TYPE.equals("*iehta") &&
			!_BROWSER_TYPE.equals("*iexplore")) {

			return;
		}

		try {
			String path = TestPropsValues.OUTPUT_DIR + value;
			if (value.startsWith("L:\\")) {
					path = value;
				}
			
			String[] commands = {
				RuntimeVariables.replace(
					_SELENIUM_EXECUTABLE_DIR +
						TestPropsValues.SELENIUM_UPLOAD_FILE),
				path
			};

			Runtime runtime = Runtime.getRuntime();

			Thread.sleep(5000);

			runtime.exec(commands);

			Thread.sleep(30000);

			selectWindow("relative=top");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static final String _BROWSER_TYPE = TestPropsValues.BROWSER_TYPE;

	private static final String _OUTPUT_SCREENSHOTS_DIR =
		TestPropsValues.OUTPUT_DIR + "screenshots/";

	private static final String _SELENIUM_EXECUTABLE_DIR =
		TestPropsValues.SELENIUM_EXECUTABLE_DIR;

	private String _timeout = "90000";
		
	private final Keys [] keyTable = new Keys[128];
}