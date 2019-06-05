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

package com.liferay.portal.lpkg.deployer.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.Version;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class LPKGPersistenceVerifyUpgradeTest {

	@Test
	public void testVerifyBundleUpgradePersistence() {
		Bundle bundle = FrameworkUtil.getBundle(
			LPKGPersistenceVerifyTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Bundle jarBundle = null;

		Bundle lpkgBundle = null;

		for (Bundle testBundle : bundleContext.getBundles()) {
			if ((jarBundle != null) && (lpkgBundle != null)) {
				break;
			}

			String symbolicName = testBundle.getSymbolicName();

			if (symbolicName.equals("lpkg.persistence.test")) {
				jarBundle = testBundle;
			}

			if (symbolicName.equals("Liferay Persistence Test")) {
				lpkgBundle = testBundle;
			}
		}

		Assert.assertNotNull(jarBundle);

		Assert.assertEquals(Bundle.ACTIVE, jarBundle.getState());

		Version version = new Version(2, 0, 0);

		Assert.assertEquals(version, jarBundle.getVersion());

		Assert.assertNotNull(lpkgBundle);

		Assert.assertEquals(Bundle.ACTIVE, lpkgBundle.getState());

		Assert.assertEquals(version, lpkgBundle.getVersion());
	}

}