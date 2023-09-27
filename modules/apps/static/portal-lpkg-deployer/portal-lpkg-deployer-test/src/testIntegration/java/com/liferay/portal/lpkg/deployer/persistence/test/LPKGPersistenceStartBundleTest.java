/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lpkg.deployer.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.module.util.BundleUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class LPKGPersistenceStartBundleTest {

	@Test
	public void testStartBundle() throws BundleException {
		Bundle bundle = FrameworkUtil.getBundle(
			LPKGPersistenceStartBundleTest.class);

		Bundle lpkgPersistenceTestBundle = BundleUtil.getBundle(
			bundle.getBundleContext(), "lpkg.persistence.test");

		Assert.assertNotNull(lpkgPersistenceTestBundle);

		lpkgPersistenceTestBundle.start();

		Assert.assertEquals(
			Bundle.ACTIVE, lpkgPersistenceTestBundle.getState());
	}

}