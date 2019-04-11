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

package com.liferay.arquillian.extension.junit.bridge.server;

import com.liferay.arquillian.extension.junit.bridge.constants.Headers;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

import java.util.List;
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
		catch (IOException ioe) {
			throw new IllegalArgumentException(
				"Unable to read test manifest", ioe);
		}

		Attributes attributes = manifest.getMainAttributes();

		String reportServerHostName = attributes.getValue(
			Headers.TEST_BRIDGE_REPORT_SERVER_HOST_NAME);
		int reportServerPort = Integer.parseInt(
			attributes.getValue(Headers.TEST_BRIDGE_REPORT_SERVER_PORT));

		List<String> filterMethodNames = StringUtil.split(
			attributes.getValue(Headers.TEST_BRIDGE_FILTERED_METHOD_NAMES),
			CharPool.COMMA);

		long passCode = Long.parseLong(
			attributes.getValue(Headers.TEST_BRIDGE_PASS_CODE));

		Bundle systemBundle = bundleContext.getBundle(0);

		BundleContext systemBundleContext = systemBundle.getBundleContext();

		systemBundleContext.addBundleListener(
			new TestBundleListener(
				systemBundleContext, testBundle, filterMethodNames,
				reportServerHostName, reportServerPort, passCode));
	}

	@Override
	public void stop(BundleContext bundleContext) {
	}

}