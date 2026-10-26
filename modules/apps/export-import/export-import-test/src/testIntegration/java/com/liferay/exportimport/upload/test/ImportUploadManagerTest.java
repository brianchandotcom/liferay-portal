/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.upload.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.upload.ImportUploadEntry;
import com.liferay.exportimport.upload.ImportUploadException;
import com.liferay.exportimport.upload.ImportUploadManager;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;
import java.io.File;

import java.util.Arrays;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jaime León
 */
@RunWith(Arquillian.class)
public class ImportUploadManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddImportUpload() throws Exception {
		String fileName = RandomTestUtil.randomString() + ".lar";

		ImportUploadEntry importUploadEntry =
			_importUploadManager.addImportUpload(
				RandomTestUtil.randomString(), fileName, _group.getGroupId(), 0,
				null, 10, TestPropsValues.getUserId());

		importUploadEntry = _importUploadManager.fetchImportUpload(
			_group.getGroupId(), importUploadEntry.getImportUploadId(),
			TestPropsValues.getUserId());

		Assert.assertEquals(fileName, importUploadEntry.getFileName());
		Assert.assertEquals(10, importUploadEntry.getSize());
		Assert.assertEquals(1, importUploadEntry.getTotalChunksCount());
		Assert.assertEquals(0, importUploadEntry.getUploadedChunksCount());
	}

	@Test
	public void testAddImportUploadWithEmptyFile() throws Exception {
		Assert.assertThrows(
			ImportUploadException.class,
			() -> _importUploadManager.addImportUpload(
				RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".lar", _group.getGroupId(), 0,
				null, 0, TestPropsValues.getUserId()));
	}

	@Test
	public void testDeleteImportUpload() throws Exception {
		ImportUploadEntry importUploadEntry = _addImportUpload(
			_getRandomBytes(10));

		_importUploadManager.deleteImportUpload(
			_group.getGroupId(), importUploadEntry.getImportUploadId(),
			TestPropsValues.getUserId());

		Assert.assertNull(
			_importUploadManager.fetchImportUpload(
				_group.getGroupId(), importUploadEntry.getImportUploadId(),
				TestPropsValues.getUserId()));
	}

	@Test
	public void testFetchImportUploadWithInvalidId() throws Exception {
		Assert.assertThrows(
			ImportUploadException.class,
			() -> _importUploadManager.fetchImportUpload(
				_group.getGroupId(), "../" + RandomTestUtil.randomString(),
				TestPropsValues.getUserId()));
	}

	@Test
	public void testPutChunk() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.internal.configuration." +
						"DLSizeLimitConfiguration",
					HashMapDictionaryBuilder.<String, Object>put(
						"fileMaxSize", 1024L
					).build())) {

			byte[] bytes = _getRandomBytes(1027);

			ImportUploadEntry importUploadEntry =
				_importUploadManager.addImportUpload(
					RandomTestUtil.randomString(),
					RandomTestUtil.randomString() + ".lar", _group.getGroupId(),
					0, null, bytes.length, TestPropsValues.getUserId());

			int chunkSize = (int)importUploadEntry.getChunkSize();

			importUploadEntry = _importUploadManager.putChunk(
				1, _group.getGroupId(), importUploadEntry,
				new ByteArrayInputStream(
					Arrays.copyOfRange(bytes, chunkSize, bytes.length)),
				TestPropsValues.getUserId());

			Assert.assertEquals(0, importUploadEntry.getUploadedChunksCount());

			importUploadEntry = _importUploadManager.putChunk(
				0, _group.getGroupId(), importUploadEntry,
				new ByteArrayInputStream(
					Arrays.copyOfRange(bytes, 0, chunkSize)),
				TestPropsValues.getUserId());

			Assert.assertEquals(2, importUploadEntry.getUploadedChunksCount());

			importUploadEntry = _importUploadManager.putChunk(
				0, _group.getGroupId(), importUploadEntry,
				new ByteArrayInputStream(
					Arrays.copyOfRange(bytes, 0, chunkSize)),
				TestPropsValues.getUserId());

			Assert.assertEquals(2, importUploadEntry.getUploadedChunksCount());

			File file = FileUtil.createTempFile();

			_importUploadManager.writeTo(
				bytesWritten -> {
				},
				file, _group.getGroupId(),
				importUploadEntry.getImportUploadId(),
				TestPropsValues.getUserId());

			Assert.assertArrayEquals(bytes, FileUtil.getBytes(file));

			FileUtil.delete(file);
		}
	}

	@Test
	public void testPutChunkWithWrongSize() throws Exception {
		ImportUploadEntry importUploadEntry =
			_importUploadManager.addImportUpload(
				RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".lar", _group.getGroupId(), 0,
				null, 10, TestPropsValues.getUserId());

		Assert.assertThrows(
			ImportUploadException.class,
			() -> _importUploadManager.putChunk(
				0, _group.getGroupId(), importUploadEntry,
				new ByteArrayInputStream(_getRandomBytes(11)),
				TestPropsValues.getUserId()));

		ImportUploadEntry fetchedImportUploadEntry =
			_importUploadManager.fetchImportUpload(
				_group.getGroupId(), importUploadEntry.getImportUploadId(),
				TestPropsValues.getUserId());

		Assert.assertEquals(
			0, fetchedImportUploadEntry.getUploadedChunksCount());
	}

	private ImportUploadEntry _addImportUpload(byte[] bytes) throws Exception {
		ImportUploadEntry importUploadEntry =
			_importUploadManager.addImportUpload(
				RandomTestUtil.randomString(),
				RandomTestUtil.randomString() + ".lar", _group.getGroupId(), 0,
				null, bytes.length, TestPropsValues.getUserId());

		return _importUploadManager.putChunk(
			0, _group.getGroupId(), importUploadEntry,
			new ByteArrayInputStream(bytes), TestPropsValues.getUserId());
	}

	private byte[] _getRandomBytes(int length) {
		byte[] bytes = new byte[length];

		Random random = new Random();

		random.nextBytes(bytes);

		return bytes;
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ImportUploadManager _importUploadManager;

}