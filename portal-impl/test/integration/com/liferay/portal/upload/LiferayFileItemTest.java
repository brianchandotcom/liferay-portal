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

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.DependenciesTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;

import java.io.File;

import java.nio.file.Files;

import org.apache.commons.fileupload.FileItem;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Manuel de la Peña
 */
public class LiferayFileItemTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() {
		_liferayFileItemFactory = new LiferayFileItemFactory(
			temporaryFolder.getRoot());
	}

	@Test
	public void testCreateFromFactory() {
		String contentType = RandomTestUtil.randomString();
		String fieldName = RandomTestUtil.randomString();
		String fileName = RandomTestUtil.randomString();

		FileItem item = _liferayFileItemFactory.createItem(
			fieldName, contentType, false, fileName);

		LiferayFileItem liferayFileItem = (LiferayFileItem)item;

		Assert.assertEquals(fieldName, liferayFileItem.getFieldName());
		Assert.assertEquals(fileName, liferayFileItem.getFullFileName());
		Assert.assertEquals(false, liferayFileItem.isFormField());
	}

	@Test(expected = NullPointerException.class)
	public void
		testGetContentTypeFromInvalidFileShouldThrowNullPointerException() {

		String contentType = RandomTestUtil.randomString();
		String fieldName = RandomTestUtil.randomString();
		String fileName = RandomTestUtil.randomString();

		FileItem item = _liferayFileItemFactory.createItem(
			fieldName, contentType, false, fileName);

		Assert.assertNotNull(item);

		item.getContentType();
	}

	@Test(expected = NullPointerException.class)
	public void
			testGetContentTypeFromRealFileShouldThrowNullPointerException()
		throws Exception {

		File file = DependenciesTestUtil.getDependencyAsFile(
			this.getClass(), "LiferayFileItem.txt");

		String contentType = Files.probeContentType(file.toPath());
		String fieldName = RandomTestUtil.randomString();
		String fileName = file.getName();

		FileItem item = _liferayFileItemFactory.createItem(
			fieldName, contentType, false, fileName);

		Assert.assertNotNull(item);

		item.getContentType();
	}

	@Test
	public void testGetEncodingStringAfterCreateItemShouldBeNull() {
		String contentType = RandomTestUtil.randomString();
		String fieldName = RandomTestUtil.randomString();
		String fileName = RandomTestUtil.randomString();

		FileItem item = _liferayFileItemFactory.createItem(
			fieldName, contentType, false, fileName);

		LiferayFileItem liferayFileItem = (LiferayFileItem)item;

		Assert.assertNotNull(liferayFileItem);
		Assert.assertNull(liferayFileItem.getEncodedString());
	}

	@Test
	public void testGetFileNameExtensionShouldReturnFileExtension() {
		String contentType = RandomTestUtil.randomString();
		String fieldName = RandomTestUtil.randomString();
		String fileName = RandomTestUtil.randomString() + ".txt";

		FileItem item = _liferayFileItemFactory.createItem(
			fieldName, contentType, false, fileName);

		LiferayFileItem liferayFileItem = (LiferayFileItem)item;

		Assert.assertEquals("txt", liferayFileItem.getFileNameExtension());
	}

	@Test
	public void testGetFileNameExtensionWithNullValueShouldReturnBlank() {
		String contentType = RandomTestUtil.randomString();
		String fieldName = RandomTestUtil.randomString();
		String fileName = "theFile";

		FileItem item = _liferayFileItemFactory.createItem(
			fieldName, contentType, false, fileName);

		LiferayFileItem liferayFileItem = (LiferayFileItem)item;

		Assert.assertEquals("", liferayFileItem.getFileNameExtension());
	}

	@Test
	public void testSetStringNeedsAValidCharacterEncoding() throws Exception {
		String contentType = RandomTestUtil.randomString();
		String fieldName = RandomTestUtil.randomString();
		String fileName = RandomTestUtil.randomString() + ".txt";

		FileItem item = _liferayFileItemFactory.createItem(
			fieldName, contentType, false, fileName);

		LiferayFileItem liferayFileItem = (LiferayFileItem)item;

		liferayFileItem.getOutputStream();

		liferayFileItem.setString("UTF-8");

		Assert.assertEquals("", liferayFileItem.getEncodedString());
	}

	@Test(expected = NullPointerException.class)
	public void testSetStringWithoutOutputStreamThrowsNullPointerException()
		throws Exception {

		String contentType = RandomTestUtil.randomString();
		String fieldName = RandomTestUtil.randomString();
		String fileName = RandomTestUtil.randomString() + ".txt";

		FileItem item = _liferayFileItemFactory.createItem(
			fieldName, contentType, false, fileName);

		LiferayFileItem liferayFileItem = (LiferayFileItem)item;

		Assert.assertNotNull(liferayFileItem);

		liferayFileItem.setString(RandomTestUtil.randomString());
	}

	@Test
	public void testWriteNeedsCallingGetOutputStream() throws Exception {
		String contentType = RandomTestUtil.randomString();
		String fieldName = RandomTestUtil.randomString();
		String fileName = RandomTestUtil.randomString() + ".txt";

		FileItem item = _liferayFileItemFactory.createItem(
			fieldName, contentType, false, fileName);

		LiferayFileItem liferayFileItem = (LiferayFileItem)item;

		liferayFileItem.getOutputStream();

		liferayFileItem.write(temporaryFolder.newFile());

		liferayFileItem.setString("UTF-8");

		Assert.assertEquals("", liferayFileItem.getEncodedString());
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private LiferayFileItemFactory _liferayFileItemFactory;

}