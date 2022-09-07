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

package com.liferay.portal.lpkg.deployer.container;

import com.liferay.portal.lpkg.deployer.test.util.LPKGTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.OutputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matthew Tambara
 */
public class LPKGContainerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testLPKGContainerDeploy() throws Exception {
		String liferayHome = System.getProperty("liferay.home");

		Assert.assertNotNull(
			"Missing system property \"liferay.home\"", liferayHome);

		Path path = Paths.get(
			liferayHome, "osgi/marketplace/Liferay Inner Container Test.lpkg");

		Files.createFile(path);

		LPKGTestUtil.createLPKG(path, _SYMBOLIC_NAME, false);

		Path containerPath = Paths.get(
			liferayHome, "osgi/marketplace/Liferay Container Test.lpkg");

		Files.createFile(containerPath);

		try (OutputStream outputStream = Files.newOutputStream(containerPath);
			ZipOutputStream zipOutputStream = new ZipOutputStream(
				outputStream)) {

			zipOutputStream.putNextEntry(
				new ZipEntry("Liferay Inner Container Test.lpkg"));

			zipOutputStream.write(Files.readAllBytes(path));

			zipOutputStream.closeEntry();
		}

		Files.delete(path);
	}

	private static final String _SYMBOLIC_NAME = "lpkg.inner.container.test";

}