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