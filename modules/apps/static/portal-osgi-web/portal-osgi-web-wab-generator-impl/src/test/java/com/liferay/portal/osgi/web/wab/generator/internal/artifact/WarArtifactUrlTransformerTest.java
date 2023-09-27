/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.wab.generator.internal.artifact;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;
import java.io.FileOutputStream;

import java.nio.file.Files;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Gregory Amerson
 */
public class WarArtifactUrlTransformerTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Test
	public void testClientExtensionURLWithConfigFile() throws Exception {
		File dir = temporaryFolder.newFolder();

		File configFile = new File(dir, "test.client-extension-config.json");

		Files.write(configFile.toPath(), "".getBytes());

		File file = temporaryFolder.newFile("clientextension.zip");

		_zipDirToFile(dir, file);

		WarArtifactUrlTransformer warArtifactUrlTransformer =
			new WarArtifactUrlTransformer();

		Assert.assertTrue(warArtifactUrlTransformer.canTransformURL(file));
	}

	@Test
	public void testClientExtensionURLWithoutConfigFile() throws Exception {
		File clientExtensionZipFile = new File(
			temporaryFolder.newFolder(), "clientextension.zip");

		clientExtensionZipFile.createNewFile();

		WarArtifactUrlTransformer warArtifactUrlTransformer =
			new WarArtifactUrlTransformer();

		Assert.assertFalse(
			warArtifactUrlTransformer.canTransformURL(clientExtensionZipFile));
	}

	private void _zipDirToFile(File dir, File zipFile) throws Exception {
		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				new FileOutputStream(zipFile))) {

			for (File file : dir.listFiles()) {
				ZipEntry zipEntry = new ZipEntry(file.getName());

				zipOutputStream.putNextEntry(zipEntry);

				byte[] bytes = Files.readAllBytes(file.toPath());

				zipOutputStream.write(bytes, 0, bytes.length);

				zipOutputStream.closeEntry();
			}
		}
	}

}