/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upload.internal;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.DependenciesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 */
public class LiferayFileItemTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws IOException {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		ReflectionTestUtil.setFieldValue(
			MimeTypesUtil.class, "_mimeTypes",
			new MimeTypes() {

				@Override
				public String getContentType(File file) {
					throw new UnsupportedOperationException();
				}

				@Override
				public String getContentType(File file, String fileName) {
					throw new UnsupportedOperationException();
				}

				@Override
				public String getContentType(
					InputStream inputStream, String fileName) {

					try {
						Path path = Files.createTempFile(null, null);

						Files.copy(
							inputStream, path,
							StandardCopyOption.REPLACE_EXISTING);

						String contentType = Files.probeContentType(path);

						if (contentType == null) {
							contentType = ContentTypes.APPLICATION_OCTET_STREAM;
						}

						Files.delete(path);

						return contentType;
					}
					catch (IOException ioException) {
						throw new RuntimeException(ioException);
					}
				}

				@Override
				public String getContentType(String fileName) {
					throw new UnsupportedOperationException();
				}

				@Override
				public String getExtensionContentType(String extension) {
					throw new UnsupportedOperationException();
				}

				@Override
				public Set<String> getExtensions(String contentType) {
					throw new UnsupportedOperationException();
				}

			});

		_liferayFileItemFactory = new LiferayFileItemFactory(
			FileUtil.createTempFolder(), 0, "UTF-8");
	}

	@Test
	public void testCreateItem() {
		String fieldName = RandomTestUtil.randomString();
		String fileName = RandomTestUtil.randomString();

		LiferayFileItem liferayFileItem = _liferayFileItemFactory.createItem(
			fieldName, RandomTestUtil.randomString(), false, fileName);

		Assert.assertEquals(fieldName, liferayFileItem.getFieldName());
		Assert.assertEquals(fileName, liferayFileItem.getFullFileName());
		Assert.assertFalse(liferayFileItem.isFormField());
	}

	@Test
	public void testGetContentTypeFromInvalidFile() throws IOException {
		LiferayFileItem liferayFileItem = _liferayFileItemFactory.createItem(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			RandomTestUtil.randomString());

		Assert.assertNotNull(liferayFileItem);

		liferayFileItem.getOutputStream();

		Assert.assertEquals(
			ContentTypes.TEXT_PLAIN, liferayFileItem.getContentType());
	}

	@Test
	public void testGetContentTypeFromRealFile() throws Exception {
		File file = DependenciesTestUtil.getDependencyAsFile(
			getClass(), "LiferayFileItem.txt");

		String contentType = Files.probeContentType(file.toPath());

		LiferayFileItem liferayFileItem = _liferayFileItemFactory.createItem(
			RandomTestUtil.randomString(), contentType, false, file.getName());

		Assert.assertNotNull(liferayFileItem);

		liferayFileItem.getOutputStream();

		Assert.assertEquals(contentType, liferayFileItem.getContentType());
	}

	@Test
	public void testGetFileNameExtension() {
		LiferayFileItem liferayFileItem = _liferayFileItemFactory.createItem(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			RandomTestUtil.randomString() + ".txt");

		Assert.assertEquals("txt", liferayFileItem.getFileNameExtension());
	}

	@Test
	public void testGetFileNameExtensionWithNullValue() {
		LiferayFileItem liferayFileItem = _liferayFileItemFactory.createItem(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			"theFile");

		Assert.assertEquals("", liferayFileItem.getFileNameExtension());
	}

	@Test
	public void testSetStringRequiresCharacterEncoding() throws Exception {
		LiferayFileItem liferayFileItem = _liferayFileItemFactory.createItem(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			RandomTestUtil.randomString() + ".txt");

		liferayFileItem.getOutputStream();

		Assert.assertEquals("", liferayFileItem.getString());
	}

	@Test
	public void testWriteRequiresCallingGetOutputStream() throws Exception {
		LiferayFileItem liferayFileItem = _liferayFileItemFactory.createItem(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			RandomTestUtil.randomString() + ".txt");

		liferayFileItem.getOutputStream();

		liferayFileItem.write(FileUtil.createTempFile());

		Assert.assertEquals("", liferayFileItem.getString());
	}

	private static LiferayFileItemFactory _liferayFileItemFactory;

}