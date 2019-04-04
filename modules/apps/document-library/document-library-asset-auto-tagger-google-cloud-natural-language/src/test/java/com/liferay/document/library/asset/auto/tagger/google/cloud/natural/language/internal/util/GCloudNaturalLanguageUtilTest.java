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

package com.liferay.document.library.asset.auto.tagger.google.cloud.natural.language.internal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FileImpl;

import java.io.ByteArrayInputStream;

import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Alicia García
 */
public class GCloudNaturalLanguageUtilTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testGetDocumentPayload() throws Exception {
		Mockito.when(
			_fileEntry.getFileVersion()
		).thenReturn(
			_fileVersion
		);

		String randomString = RandomTestUtil.randomString();

		Mockito.when(
			_fileVersion.getContentStream(false)
		).thenReturn(
			new ByteArrayInputStream(randomString.getBytes())
		);

		Assert.assertEquals(
			GCloudNaturalLanguageUtil.getDocumentPayload(
				randomString, StringPool.BLANK),
			GCloudNaturalLanguageUtil.getDocumentPayload(
				GCloudNaturalLanguageUtil.truncateToSize(
					new String(
						FileUtil.getBytes(
							_fileVersion.getContentStream(false))),
					5000),
				StringPool.BLANK));
	}

	@Test
	public void testGetTypeWithMSWord() {
		Assert.assertEquals(
			"PLAIN_TEXT",
			GCloudNaturalLanguageUtil.getType(ContentTypes.APPLICATION_MSWORD));
	}

	@Test
	public void testGetTypeWithTextPlain() {
		Assert.assertEquals(
			"PLAIN_TEXT",
			GCloudNaturalLanguageUtil.getType(ContentTypes.TEXT_PLAIN));
	}

	@Test
	public void testGetTypeWitHTML() {
		Assert.assertEquals(
			"HTML", GCloudNaturalLanguageUtil.getType(ContentTypes.TEXT_HTML));
	}

	@Test
	public void testTruncateToSizeEmptyString() {
		Assert.assertEquals(
			StringPool.BLANK,
			GCloudNaturalLanguageUtil.truncateToSize(
				StringPool.BLANK, RandomTestUtil.randomInt()));
	}

	@Test(expected = NullPointerException.class)
	public void testTruncateToSizeNullString() {
		GCloudNaturalLanguageUtil.truncateToSize(
			null, RandomTestUtil.randomInt());
	}

	@Test
	public void testTruncateToSizeSingleWordEqualToMax() {
		int size = _randomSize();

		String text = RandomTestUtil.randomString(size);

		Assert.assertEquals(
			text, GCloudNaturalLanguageUtil.truncateToSize(text, size));
	}

	@Test
	public void testTruncateToSizeSingleWordGreaterThanMax() {
		int size = _randomSize();

		Assert.assertEquals(
			StringPool.BLANK,
			GCloudNaturalLanguageUtil.truncateToSize(
				RandomTestUtil.randomString(size + 1), size));
	}

	@Test
	public void testTruncateToSizeSingleWordSmallerThanMax() {
		int size = _randomSize();

		String text = RandomTestUtil.randomString(size - 1);

		Assert.assertEquals(
			text, GCloudNaturalLanguageUtil.truncateToSize(text, size));
	}

	@Test
	public void testTruncateToSizeTextGreaterThanMax() {
		int size = _randomSize();

		Assert.assertEquals(
			StringPool.BLANK,
			GCloudNaturalLanguageUtil.truncateToSize(
				RandomTestUtil.randomString(size + 1) + StringPool.SPACE +
					RandomTestUtil.randomString(size + 1),
				size));
	}

	@Test
	public void testTruncateToSizeTextGreaterThanMaxWithTwoWordsSmallerThanSize() {
		int size = _randomSize();

		String text =
			RandomTestUtil.randomString(size - 1) + StringPool.SPACE +
				RandomTestUtil.randomString(size - 1);

		Assert.assertEquals(
			text,
			GCloudNaturalLanguageUtil.truncateToSize(
				text + StringPool.SPACE + RandomTestUtil.randomString(size - 1),
				text.length() + 1));
	}

	@Test
	public void testTruncateToSizeTextGreaterThanMaxWithWordSmallerThanSize() {
		int size = _randomSize();

		String text = RandomTestUtil.randomString(size - 1);

		Assert.assertEquals(
			text,
			GCloudNaturalLanguageUtil.truncateToSize(
				text + StringPool.SPACE + text, size));
	}

	@Test
	public void testTruncateToSizeTextSmallerThanMax() {
		int size = _randomSize();

		String text =
			RandomTestUtil.randomString((size / 2) - 1) + StringPool.SPACE +
				RandomTestUtil.randomString((size / 2) - 1);

		Assert.assertEquals(
			text, GCloudNaturalLanguageUtil.truncateToSize(text, size));
	}

	@Test
	public void testTruncateToSizeUnicodeTextEqualToMax() {
		String text = "中國哲學書電子化計劃 中國哲學書電子化計劃 中國哲學書電子化計劃";

		byte[] bytes = text.getBytes(StandardCharsets.UTF_8);

		Assert.assertEquals(
			text, GCloudNaturalLanguageUtil.truncateToSize(text, bytes.length));
	}

	@Test
	public void testTruncateToSizeUnicodeTextGreaterThanMax() {
		String text = "中國哲學書電子化計劃 中國哲學書電子化計劃 中國哲學書電子化計劃";

		byte[] bytes = text.getBytes(StandardCharsets.UTF_8);

		Assert.assertEquals(
			text.substring(0, text.lastIndexOf(CharPool.SPACE)),
			GCloudNaturalLanguageUtil.truncateToSize(text, bytes.length - 1));
	}

	private int _randomSize() {
		return RandomTestUtil.randomInt(1, 100);
	}

	@Mock
	private FileEntry _fileEntry;

	@Mock
	private FileVersion _fileVersion;

}