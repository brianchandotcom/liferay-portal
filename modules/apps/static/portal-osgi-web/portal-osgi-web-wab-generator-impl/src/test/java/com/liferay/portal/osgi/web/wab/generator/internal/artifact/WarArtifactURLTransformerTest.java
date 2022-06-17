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

package com.liferay.portal.osgi.web.wab.generator.internal.artifact;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;
import java.io.FileOutputStream;

import java.nio.file.Files;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Gregory Amerson
 */
public class WarArtifactURLTransformerTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	public void testClientExtensionURLNoConfigFile() throws Exception {
		File dir = temporaryFolder.newFolder();

		File clientExtensionZipFile = new File(dir, "clientextension.zip");

		clientExtensionZipFile.createNewFile();

		WarArtifactUrlTransformer warArtifactUrlTransformer =
			new WarArtifactUrlTransformer(new AtomicBoolean(true));

		boolean canTransformURL = warArtifactUrlTransformer.canTransformURL(
			clientExtensionZipFile);

		Assert.assertFalse(
			"Expected to not be able to transform file " +
				clientExtensionZipFile,
			canTransformURL);
	}

	@Test
	public void testClientExtensionURLWithConfigFile() throws Exception {
		File dir = temporaryFolder.newFolder();

		File configFile = new File(dir, "test.client-extension-config.json");

		Files.write(configFile.toPath(), "".getBytes());

		File file = temporaryFolder.newFile("clientextension.zip");

		_zipDirToFile(dir, file);

		WarArtifactUrlTransformer warArtifactUrlTransformer =
			new WarArtifactUrlTransformer(new AtomicBoolean(true));

		boolean canTransformURL = warArtifactUrlTransformer.canTransformURL(
			file);

		Assert.assertTrue(
			"Expected to be able to transform file " + file, canTransformURL);
	}

	private void _zipDirToFile(File dir, File zipFile) throws Exception {
		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				new FileOutputStream(zipFile))) {

			File[] files = dir.listFiles();

			for (File file : files) {
				ZipEntry zipEntry = new ZipEntry(file.getName());

				zipOutputStream.putNextEntry(zipEntry);

				byte[] data = Files.readAllBytes(file.toPath());

				zipOutputStream.write(data, 0, data.length);

				zipOutputStream.closeEntry();
			}
		}
	}

}