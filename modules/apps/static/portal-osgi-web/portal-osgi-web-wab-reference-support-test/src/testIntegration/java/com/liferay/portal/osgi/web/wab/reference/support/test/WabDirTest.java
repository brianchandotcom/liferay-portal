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

package com.liferay.portal.osgi.web.wab.reference.support.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;
import java.io.FileOutputStream;

import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Gregory Amerson
 */
@RunWith(Arquillian.class)
public class WabDirTest {

	@ClassRule
	@Rule
	public static final TestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@ClassRule
	@Rule
	public static final TemporaryFolder tempFolder = new TemporaryFolder();

	@Before
	public void setUp() throws Exception {
		_setUp();
	}

	@Test
	public void testHandlerServiceIsAvailable() throws Exception {
		Assert.assertNotNull(_handlerService);
	}

	@Test
	public void testWebBundlerDirInstall() throws Exception {
		File themeZip = _getFile("exploded-test-theme.zip");

		Assert.assertTrue(themeZip.exists());

		File themeTempFolder = tempFolder.newFolder("exploded-test-theme");

		FileUtil.unzip(themeZip, themeTempFolder);

		String themeTempFolderURI = themeTempFolder.toURI().toASCIIString();

		Bundle themeBundle = _bundleContext.installBundle(
			"webbundledir:" + themeTempFolderURI);

		Assert.assertNotNull(themeBundle);

		themeBundle.start();

		int state = themeBundle.getState();

		Assert.assertEquals(Bundle.ACTIVE, state);
	}

	private File _getFile(String fileName) throws Exception {
		ClassLoader classLoader = getClass().getClassLoader();

		URL url = classLoader.getResource(fileName);

		File file = tempFolder.newFile(fileName);

		StreamUtil.transfer(url.openStream(), new FileOutputStream(file));

		return file;
	}

	private void _setUp() throws Exception {
		_bundleContext = FrameworkUtil.getBundle(
			WabDirTest.class).getBundleContext();

		Filter filter = FrameworkUtil.createFilter(
			"(" + URLConstants.URL_HANDLER_PROTOCOL + "=webbundledir)");

		ServiceTracker<URLStreamHandlerService, URLStreamHandlerService>
			serviceTracker = new ServiceTracker<>(_bundleContext, filter, null);

		serviceTracker.open();

		_handlerService = serviceTracker.getService();
	}

	private BundleContext _bundleContext;
	private URLStreamHandlerService _handlerService;

}