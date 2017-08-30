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