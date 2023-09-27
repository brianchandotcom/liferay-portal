/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.bundle.blacklist.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class BundleBlacklistVerifyUninstalledTest {

	@Test
	public void testVerifyBundlesUninstalled() {
		Bundle bundle = FrameworkUtil.getBundle(
			BundleBlacklistVerifyUninstalledTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		for (Bundle installedBundles : bundleContext.getBundles()) {
			String symbolicName = installedBundles.getSymbolicName();

			Assert.assertNotEquals(
				bundle + " was not uninstalled", _JAR_BUNDLE_SYMBOLIC_NAME,
				symbolicName);

			Assert.assertNotEquals(
				bundle + " was not uninstalled", _WAR_BUNDLE_SYMBOLIC_NAME,
				symbolicName);
		}
	}

	private static final String _JAR_BUNDLE_SYMBOLIC_NAME =
		"com.liferay.portal.bundle.blacklist.test.bundle";

	private static final String _WAR_BUNDLE_SYMBOLIC_NAME =
		_JAR_BUNDLE_SYMBOLIC_NAME.concat(".war");

}