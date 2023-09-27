/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lpkg.deployer.persistence.test;

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
public class LPKGPersistenceVerifyTest {

	@Test
	public void testVerifyBundlePersistence() {
		Bundle bundle = FrameworkUtil.getBundle(
			LPKGPersistenceVerifyTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		for (Bundle testBundle : bundleContext.getBundles()) {
			String symbolicName = testBundle.getSymbolicName();

			if (!symbolicName.equals("lpkg.persistence.test")) {
				continue;
			}

			Assert.assertEquals(Bundle.RESOLVED, testBundle.getState());

			break;
		}
	}

}