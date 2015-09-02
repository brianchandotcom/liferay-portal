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

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * @author Brian Wing Shun Chan
 */
public class EdgeRemoteWebDriverImpl extends EdgeWebDriverImpl {

	public EdgeRemoteWebDriverImpl(String projectDirName, String browserURL) {
		super(
			projectDirName, browserURL,
			new RemoteWebDriver(_remoteURL, _desiredCapabilities));
	}

	private static final DesiredCapabilities _desiredCapabilities;
	private static final URL _remoteURL;

	static {
		_desiredCapabilities = DesiredCapabilities.edge();

		_desiredCapabilities.setCapability("platform", "WINDOWS");

		URL remoteURL = null;

		try {
			remoteURL = new URL(
				PropsValues.SELENIUM_REMOTE_DRIVER_HUB + ":4444/wd/hub");
		}
		catch (MalformedURLException murle) {
		}

		_remoteURL = remoteURL;
	}

}