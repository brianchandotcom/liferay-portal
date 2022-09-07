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

package com.liferay.portal.lpkg.deployer.container.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.util.PropsValues;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class LPKGContainerVerifyTest {

	@Before
	public void setUp() {
		Bundle bundle = FrameworkUtil.getBundle(LPKGContainerVerifyTest.class);

		_bundleContext = bundle.getBundleContext();
	}

	@Test
	public void testLPKGController() throws Exception {
		Path path = Paths.get(
			PropsValues.MODULE_FRAMEWORK_MARKETPLACE_DIR,
			"Liferay Container Test.lpkg");

		Assert.assertTrue(Files.notExists(path));

		boolean found = false;

		for (Bundle bundle : _bundleContext.getBundles()) {
			if (_LPKG_INNER_NAME.equals(bundle.getSymbolicName())) {
				found = true;

				break;
			}
		}

		Assert.assertTrue("Inner LPKG bundle not installed", found);
	}

	private static final String _LPKG_INNER_NAME =
		"Liferay Inner Container Test";

	private BundleContext _bundleContext;

}