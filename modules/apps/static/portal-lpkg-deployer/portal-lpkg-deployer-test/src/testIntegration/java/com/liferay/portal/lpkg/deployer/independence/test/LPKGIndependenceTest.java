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

package com.liferay.portal.lpkg.deployer.independence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class LPKGIndependenceTest {

	@Test
	public void testLPKGIndependence() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(LPKGIndependenceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceReference serviceReference = bundleContext.getServiceReference(
			"com.liferay.portal.lpkg.deployer.internal.LPKGIndexValidator");

		Object service = bundleContext.getService(serviceReference);

		Path tempDir = Paths.get(
			PropsValues.MODULE_FRAMEWORK_MARKETPLACE_DIR, "temp");

		File tempFile = tempDir.toFile();

		for (File lpkgDir : tempFile.listFiles()) {
			List<File> files = Arrays.asList(lpkgDir.listFiles());

			Assert.assertTrue(
				"Unable to validate " + files,
				(Boolean)ReflectionTestUtil.invoke(
					service, "validate", new Class<?>[] {List.class}, files));
		}
	}

}