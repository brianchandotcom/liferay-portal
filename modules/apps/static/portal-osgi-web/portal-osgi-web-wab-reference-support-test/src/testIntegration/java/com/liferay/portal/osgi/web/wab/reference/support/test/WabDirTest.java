/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.wab.reference.support.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.File;
import java.io.FileOutputStream;

import java.net.URI;
import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
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
		Assert.assertNotNull(_urlStreamHandlerService);
	}

	@Ignore
	@Test
	public void testWebBundlerDirPortletInstall() throws Exception {
		File portletZipFile = _getFile("exploded-test-portlet.zip");

		Assert.assertTrue(portletZipFile.exists());

		File portletTempDir = tempFolder.newFolder("exploded-test-portlet");

		FileUtil.unzip(portletZipFile, portletTempDir);

		URI portletTempDirURI = portletTempDir.toURI();

		Bundle portletBundle = _bundleContext.installBundle(
			"webbundledir:" + portletTempDirURI.toASCIIString());

		Assert.assertNotNull(portletBundle);

		portletBundle.start();

		Assert.assertEquals(Bundle.ACTIVE, portletBundle.getState());

		portletBundle.stop();

		Assert.assertEquals(Bundle.RESOLVED, portletBundle.getState());

		portletBundle.uninstall();

		Assert.assertEquals(Bundle.UNINSTALLED, portletBundle.getState());
	}

	@Ignore
	@Test
	public void testWebBundlerDirThemeInstall() throws Exception {
		File themeZipFile = _getFile("exploded-test-theme.zip");

		Assert.assertTrue(themeZipFile.exists());

		File themeTempDir = tempFolder.newFolder("exploded-test-theme");

		FileUtil.unzip(themeZipFile, themeTempDir);

		URI themeTempDirURI = themeTempDir.toURI();

		Bundle themeBundle = _bundleContext.installBundle(
			"webbundledir:" + themeTempDirURI.toASCIIString());

		Assert.assertNotNull(themeBundle);

		themeBundle.start();

		Assert.assertEquals(Bundle.ACTIVE, themeBundle.getState());

		themeBundle.stop();

		Assert.assertEquals(Bundle.RESOLVED, themeBundle.getState());

		themeBundle.uninstall();

		Assert.assertEquals(Bundle.UNINSTALLED, themeBundle.getState());
	}

	private File _getFile(String fileName) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		URL url = classLoader.getResource(fileName);

		File file = tempFolder.newFile(fileName);

		StreamUtil.transfer(url.openStream(), new FileOutputStream(file));

		return file;
	}

	private void _setUp() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(WabDirTest.class);

		_bundleContext = bundle.getBundleContext();

		Filter filter = FrameworkUtil.createFilter(
			"(" + URLConstants.URL_HANDLER_PROTOCOL + "=webbundledir)");

		ServiceTracker<URLStreamHandlerService, URLStreamHandlerService>
			serviceTracker = new ServiceTracker<>(_bundleContext, filter, null);

		serviceTracker.open();

		_urlStreamHandlerService = serviceTracker.getService();
	}

	private BundleContext _bundleContext;
	private URLStreamHandlerService _urlStreamHandlerService;

}