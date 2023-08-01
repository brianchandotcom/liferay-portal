/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.poshi.runner.util;

import com.liferay.poshi.core.PoshiProperties;

import java.io.IOException;
import java.io.StringWriter;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;

import org.openqa.selenium.Proxy;

/**
 * @author Kenji Heigel
 */
public class ProxyUtil {

	public static String getHarRecording(String jsonPath) throws IOException {
		BrowserMobProxy browserMobProxy = getBrowserMobProxy();

		Har har = browserMobProxy.getHar();

		StringWriter stringWriter = new StringWriter();

		har.writeTo(stringWriter);

		String harString = stringWriter.toString();

		return JSONUtil.getWithJSONPath(harString, jsonPath);
	}

	public static Proxy getSeleniumProxy() {
		return _proxyUtil._getSeleniumProxy();
	}

	public static void startHarRecording(String harName) {
		BrowserMobProxy browserMobProxy = getBrowserMobProxy();

		browserMobProxy.newHar(harName);
	}

	public static void stopBrowserMobProxy() {
		_proxyUtil._stopBrowserMobProxy();
	}

	public static void stopHarRecording() {
		BrowserMobProxy browserMobProxy = getBrowserMobProxy();

		browserMobProxy.endHar();
	}

	protected static BrowserMobProxy getBrowserMobProxy() {
		return _proxyUtil._getBrowserMobProxy();
	}

	private BrowserMobProxy _getBrowserMobProxy() {
		if (_browserMobProxy == null) {
			_startBrowserMobProxy();
		}

		return _browserMobProxy;
	}

	private Proxy _getSeleniumProxy() {
		if (_browserMobProxy == null) {
			_startBrowserMobProxy();
		}

		Proxy seleniumProxy = ClientUtil.createSeleniumProxy(_browserMobProxy);

		try {
			InetAddress inetAddress = Inet4Address.getLocalHost();

			String hostIPAddress = inetAddress.getHostAddress();

			seleniumProxy.setHttpProxy(
				hostIPAddress + ":" + _browserMobProxy.getPort());
			seleniumProxy.setSslProxy(
				hostIPAddress + ":" + _browserMobProxy.getPort());
		}
		catch (UnknownHostException unknownHostException) {
			throw new RuntimeException(unknownHostException);
		}

		return seleniumProxy;
	}

	private void _startBrowserMobProxy() {
		_browserMobProxy = new BrowserMobProxyServer();

		PoshiProperties poshiProperties = PoshiProperties.getPoshiProperties();

		_browserMobProxy.start(poshiProperties.proxyServerPort);

		System.out.println("Started BrowserMob Proxy.");

		_browserMobProxy.enableHarCaptureTypes(
			CaptureType.REQUEST_CONTENT, CaptureType.RESPONSE_CONTENT);
	}

	private void _stopBrowserMobProxy() {
		if (_browserMobProxy != null) {
			_browserMobProxy.stop();

			System.out.println("Stopped BrowserMob Proxy.");
		}

		_browserMobProxy = null;
	}

	private static final ProxyUtil _proxyUtil = new ProxyUtil();

	private BrowserMobProxy _browserMobProxy;

}