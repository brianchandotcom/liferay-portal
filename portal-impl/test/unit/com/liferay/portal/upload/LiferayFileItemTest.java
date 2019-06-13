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

package com.liferay.portal.upload;

import com.liferay.portal.kernel.test.util.DependenciesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.util.FileImpl;

import java.io.File;

import java.nio.file.Files;

import org.apache.commons.fileupload.FileItem;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Manuel de la Peña
 */
public class LiferayFileItemTest {

	@ClassRule
	public static final TemporaryFolder temporaryFolder = new TemporaryFolder();

	@BeforeClass
	public static void setUpClass() {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		_liferayFileItemFactory = new LiferayFileItemFactory(
			temporaryFolder.getRoot());
	}

	@Test
	public void testCreateItem() {
		String fieldName = RandomTestUtil.randomString();
		String fileName = RandomTestUtil.randomString();

		LiferayFileItem liferayFileItem = _liferayFileItemFactory.createItem(
			fieldName, RandomTestUtil.randomString(), false, fileName);

		Assert.assertEquals(fieldName, liferayFileItem.getFieldName());
		Assert.assertEquals(fileName, liferayFileItem.getFullFileName());
		Assert.assertEquals(false, liferayFileItem.isFormField());
	}

	@Test(expected = NullPointerException.class)
	public void testGetContentTypeFromInvalidFile() {
		FileItem fileItem = _liferayFileItemFactory.createItem(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			RandomTestUtil.randomString());

		Assert.assertNotNull(fileItem);

		fileItem.getContentType();
	}

	@Test(expected = NullPointerException.class)
	public void testGetContentTypeFromRealFile() throws Exception {
		File file = DependenciesTestUtil.getDependencyAsFile(
			getClass(), "LiferayFileItem.txt");

		FileItem fileItem = _liferayFileItemFactory.createItem(
			RandomTestUtil.randomString(),
			Files.probeContentType(file.toPath()), false, file.getName());

		Assert.assertNotNull(fileItem);

		fileItem.getContentType();
	}

	@Test
	public void testGetEncodedStringAfterCreateItem() {
		LiferayFileItem liferayFileItem = _liferayFileItemFactory.createItem(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			RandomTestUtil.randomString());

		Assert.assertNotNull(liferayFileItem);
		Assert.assertNull(liferayFileItem.getEncodedString());
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

		liferayFileItem.setString("UTF-8");

		Assert.assertEquals("", liferayFileItem.getEncodedString());
	}

	@Test(expected = NullPointerException.class)
	public void testSetStringWithoutOutputStream() {
		LiferayFileItem liferayFileItem = _liferayFileItemFactory.createItem(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			RandomTestUtil.randomString() + ".txt");

		Assert.assertNotNull(liferayFileItem);

		liferayFileItem.setString(RandomTestUtil.randomString());
	}

	@Test
	public void testWriteRequiresCallingGetOutputStream() throws Exception {
		LiferayFileItem liferayFileItem = _liferayFileItemFactory.createItem(
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), false,
			RandomTestUtil.randomString() + ".txt");

		liferayFileItem.getOutputStream();

		liferayFileItem.write(temporaryFolder.newFile());

		liferayFileItem.setString("UTF-8");

		Assert.assertEquals("", liferayFileItem.getEncodedString());
	}

	private static LiferayFileItemFactory _liferayFileItemFactory;

}