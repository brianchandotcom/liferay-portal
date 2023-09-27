/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.arquillian.extension.junit.bridge.server;

import com.liferay.arquillian.extension.junit.bridge.constants.Headers;
import com.liferay.arquillian.extension.junit.bridge.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * @author Shuyang Zhou
 */
public class TestBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		Bundle testBundle = bundleContext.getBundle();

		URL url = testBundle.getResource("/META-INF/MANIFEST.MF");

		Manifest manifest = new Manifest();

		try (InputStream inputStream = url.openStream()) {
			manifest.read(inputStream);
		}
		catch (IOException ioException) {
			throw new IllegalArgumentException(
				"Unable to read test manifest", ioException);
		}

		Attributes attributes = manifest.getMainAttributes();

		String reportServerHostName = attributes.getValue(
			Headers.TEST_BRIDGE_REPORT_SERVER_HOST_NAME);
		int reportServerPort = Integer.parseInt(
			attributes.getValue(Headers.TEST_BRIDGE_REPORT_SERVER_PORT));

		Map<String, List<String>> filteredMethodNamesMap = new HashMap<>();

		for (String filteredMethodNamesEntry :
				StringUtil.split(
					attributes.getValue(
						Headers.TEST_BRIDGE_FILTERED_METHOD_NAMES),
					';')) {

			int index = filteredMethodNamesEntry.indexOf(':');

			filteredMethodNamesMap.put(
				filteredMethodNamesEntry.substring(0, index),
				StringUtil.split(
					filteredMethodNamesEntry.substring(index + 1), ','));
		}

		long passCode = Long.parseLong(
			attributes.getValue(Headers.TEST_BRIDGE_PASS_CODE));

		Bundle systemBundle = bundleContext.getBundle(0);

		BundleContext systemBundleContext = systemBundle.getBundleContext();

		systemBundleContext.addBundleListener(
			new TestBundleListener(
				systemBundleContext, testBundle, filteredMethodNamesMap,
				reportServerHostName, reportServerPort, passCode));
	}

	@Override
	public void stop(BundleContext bundleContext) {
	}

}