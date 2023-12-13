/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.installer;

import com.liferay.batch.engine.unit.BatchEngineUnit;
import com.liferay.batch.engine.unit.BatchEngineUnitProcessor;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;
import java.io.FileOutputStream;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
import java.util.Collection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Carlos Correa
 */
public class BatchEngineFileInstallerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testTransformURL() throws Exception {
		BatchEngineFileInstaller batchEngineFileInstaller =
			new BatchEngineFileInstaller();

		BatchEngineUnitProcessor spyBatchEngineUnitProcessor = Mockito.spy(
			BatchEngineUnitProcessor.class);

		ReflectionTestUtil.setFieldValue(
			batchEngineFileInstaller, "_batchEngineUnitProcessor",
			spyBatchEngineUnitProcessor);

		ArgumentCaptor<Collection<BatchEngineUnit>> argumentCaptor =
			ArgumentCaptor.forClass(Collection.class);

		Path testPath = Files.createTempDirectory(null);

		File zipFile = _createZipFile(
			testPath, "test.zip", "batch-engine-data.json",
			"folder1/-batch-engine-data.json",
			"folder1/file1-batch-engine-data.json",
			"folder1/file2-batch-engine-data.json",
			"folder1/file10-batch-engine-data.json",
			"folder1/file11-batch-engine-data.json",
			"folder2/file1-batch-engine-data.json",
			"folder2/file2-batch-engine-data.json",
			"folder3/file-batch-engine-data.json",
			"folder4/file-batch-engine-data.json",
			"folder10/file2-batch-engine-data.json",
			"folder11/file1-batch-engine-data.json");

		batchEngineFileInstaller.transformURL(zipFile);

		Mockito.verify(
			spyBatchEngineUnitProcessor
		).processBatchEngineUnits(
			argumentCaptor.capture()
		);

		Assert.assertEquals(
			Arrays.asList(
				"batch-engine-data.json", "folder1/-batch-engine-data.json",
				"folder1/file1-batch-engine-data.json",
				"folder1/file2-batch-engine-data.json",
				"folder1/file10-batch-engine-data.json",
				"folder1/file11-batch-engine-data.json",
				"folder2/file1-batch-engine-data.json",
				"folder2/file2-batch-engine-data.json",
				"folder3/file-batch-engine-data.json",
				"folder4/file-batch-engine-data.json",
				"folder10/file2-batch-engine-data.json",
				"folder11/file1-batch-engine-data.json"),
			TransformUtil.transform(
				argumentCaptor.getValue(), BatchEngineUnit::getDataFileName));
	}

	private File _createZipFile(
			Path destinationPath, String fileName, String... entries)
		throws Exception {

		File zipFile = new File(destinationPath.toFile(), fileName);

		try (ZipOutputStream zipOutputStream = new ZipOutputStream(
				new FileOutputStream(zipFile))) {

			for (String entry : entries) {
				ZipEntry zipEntry = new ZipEntry(entry);

				zipOutputStream.putNextEntry(zipEntry);

				zipOutputStream.write("{}".getBytes(), 0, "{}".length());

				zipOutputStream.closeEntry();
			}
		}

		return zipFile;
	}

}