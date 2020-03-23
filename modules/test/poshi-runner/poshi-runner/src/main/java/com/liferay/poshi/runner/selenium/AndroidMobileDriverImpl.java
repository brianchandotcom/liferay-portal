/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.poshi.runner.selenium;

import com.liferay.poshi.runner.util.PropsValues;
import com.liferay.poshi.runner.util.StringUtil;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author Kenji Heigel
 * @author Kwang Lee
 */
public class AndroidMobileDriverImpl extends BaseMobileDriverImpl {

	public AndroidMobileDriverImpl(String browserURL, WebDriver webDriver) {
		super(browserURL, webDriver);
	}

	@Override
	public void type(String locator, String value) {
		WebElement webElement = getWebElement(locator);

		if (!webElement.isEnabled()) {
			return;
		}

		webElement.clear();

		Runtime runtime = Runtime.getRuntime();

		StringBuilder sb = new StringBuilder(43);

		sb.append(PropsValues.MOBILE_ANDROID_HOME);
		sb.append("/platform-tools/adb -s emulator-5554 shell input text ");

		value = StringUtil.replace(value, " ", "%s");

		sb.append(value);

		try {
			runtime.exec(sb.toString());
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	@Override
	protected void swipeWebElementIntoView(String locator) {
		int elementPositionCenterY = getElementPositionCenterY(locator);

		for (int i = 0; i < 25; i++) {
			int viewportPositionBottom = getViewportPositionBottom();
			int viewportPositionTop = getScrollOffsetY();

			StringBuilder sb = new StringBuilder(4);

			sb.append(PropsValues.MOBILE_ANDROID_HOME);
			sb.append("/platform-tools/");

			if (elementPositionCenterY >= viewportPositionBottom) {
				try {
					Runtime runtime = Runtime.getRuntime();

					sb.append("adb -s emulator-5554 shell ");
					sb.append("/data/local/swipe_up.sh");

					runtime.exec(sb.toString());
				}
				catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
			else if (elementPositionCenterY <= viewportPositionTop) {
				try {
					Runtime runtime = Runtime.getRuntime();

					sb.append("adb -s emulator-5554 shell ");
					sb.append("/data/local/swipe_down.sh");

					runtime.exec(sb.toString());
				}
				catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
			else {
				break;
			}

			try {
				LiferaySeleniumHelper.pause("1000");
			}
			catch (Exception exception) {
			}
		}
	}

	@Override
	protected void tap(String locator) {
		try {
			Runtime runtime = Runtime.getRuntime();

			StringBuilder sb = new StringBuilder(6);

			sb.append(PropsValues.MOBILE_ANDROID_HOME);
			sb.append("/platform-tools/adb -s emulator-5554 shell ");
			sb.append("/data/local/tap.sh ");

			int elementPositionCenterX = getElementPositionCenterX(locator);

			int screenPositionX = elementPositionCenterX * 3 / 2;

			sb.append(screenPositionX);

			sb.append(" ");

			int elementPositionCenterY = getElementPositionCenterY(locator);
			int navigationBarHeight = 116;
			int viewportPositionTop = getScrollOffsetY();

			int screenPositionY =
				(((elementPositionCenterY - viewportPositionTop) * 3) / 2) +
					navigationBarHeight;

			sb.append(screenPositionY);

			runtime.exec(sb.toString());
		}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

}