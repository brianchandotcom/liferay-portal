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

package com.liferay.portlet.blogs;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class BlogsEntryImageSelectorHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetEmptyImageSelectorWithDifferentFileEntryIds()
		throws Exception {

		BlogsEntryImageSelectorHelper blogsEntryImageSelectorHelper =
			new BlogsEntryImageSelectorHelper(
				0, 1, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK);

		ImageSelector imageSelector =
			blogsEntryImageSelectorHelper.getImageSelector();

		Assert.assertNull(imageSelector.getImageBytes());
		Assert.assertEquals(StringPool.BLANK, imageSelector.getImageTitle());
		Assert.assertEquals(StringPool.BLANK, imageSelector.getImageMimeType());
		Assert.assertEquals(
			StringPool.BLANK, imageSelector.getImageCropRegion());
		Assert.assertEquals(StringPool.BLANK, imageSelector.getImageURL());
	}

	@Test
	public void testGetImageSelectorWithDLImageFileEntry() throws Exception {
		InputStream inputStream = null;

		try {
			inputStream = getInputStream();

			byte[] bytes = FileUtil.getBytes(inputStream);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _IMAGE_TITLE,
				MimeTypesUtil.getContentType(_IMAGE_TITLE), "image",
				StringPool.BLANK, StringPool.BLANK, bytes, serviceContext);

			BlogsEntryImageSelectorHelper blogsEntryImageSelectorHelper =
				new BlogsEntryImageSelectorHelper(
					fileEntry.getFileEntryId(), fileEntry.getFileEntryId() + 1,
					_IMAGE_CROP_REGION, StringPool.BLANK, StringPool.BLANK);

			ImageSelector imageSelector =
				blogsEntryImageSelectorHelper.getImageSelector();

			Assert.assertArrayEquals(bytes, imageSelector.getImageBytes());
			Assert.assertEquals(_IMAGE_TITLE, imageSelector.getImageTitle());
			Assert.assertEquals(
				MimeTypesUtil.getContentType(_IMAGE_TITLE),
				imageSelector.getImageMimeType());
			Assert.assertEquals(
				_IMAGE_CROP_REGION, imageSelector.getImageCropRegion());
			Assert.assertEquals(StringPool.BLANK, imageSelector.getImageURL());
			Assert.assertFalse(
				blogsEntryImageSelectorHelper.isFileEntryTempFile());
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	@Test
	public void testGetImageSelectorWithImageURL() throws Exception {
		BlogsEntryImageSelectorHelper blogsEntryImageSelectorHelper =
			new BlogsEntryImageSelectorHelper(
				0, 0, StringPool.BLANK, _IMAGE_URL, StringPool.BLANK);

		ImageSelector imageSelector =
			blogsEntryImageSelectorHelper.getImageSelector();

		Assert.assertNull(imageSelector.getImageBytes());
		Assert.assertEquals(StringPool.BLANK, imageSelector.getImageTitle());
		Assert.assertEquals(StringPool.BLANK, imageSelector.getImageMimeType());
		Assert.assertEquals(
			StringPool.BLANK, imageSelector.getImageCropRegion());
		Assert.assertEquals(_IMAGE_URL, imageSelector.getImageURL());
		Assert.assertFalse(blogsEntryImageSelectorHelper.isFileEntryTempFile());
	}

	@Test
	public void testGetImageSelectorWithSameDLImageFileEntry()
		throws Exception {

		InputStream inputStream = null;

		try {
			inputStream = getInputStream();

			byte[] bytes = FileUtil.getBytes(inputStream);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
				TestPropsValues.getUserId(), _group.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, _IMAGE_TITLE,
				MimeTypesUtil.getContentType(_IMAGE_TITLE), "image",
				StringPool.BLANK, StringPool.BLANK, bytes, serviceContext);

			BlogsEntryImageSelectorHelper blogsEntryImageSelectorHelper =
				new BlogsEntryImageSelectorHelper(
					fileEntry.getFileEntryId(), fileEntry.getFileEntryId(),
					_IMAGE_CROP_REGION, StringPool.BLANK, StringPool.BLANK);

			Assert.assertNull(blogsEntryImageSelectorHelper.getImageSelector());
			Assert.assertFalse(
				blogsEntryImageSelectorHelper.isFileEntryTempFile());
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	@Test
	public void testGetImageSelectorWithSameImageURL() throws Exception {
		BlogsEntryImageSelectorHelper blogsEntryImageSelectorHelper =
			new BlogsEntryImageSelectorHelper(
				0, 0, StringPool.BLANK, _IMAGE_URL, _IMAGE_URL);

		Assert.assertNull(blogsEntryImageSelectorHelper.getImageSelector());
		Assert.assertFalse(blogsEntryImageSelectorHelper.isFileEntryTempFile());
	}

	@Test
	public void testGetImageSelectorWithTempImageFileEntry() throws Exception {
		InputStream inputStream = null;

		try {
			inputStream = getInputStream();

			byte[] bytes = FileUtil.getBytes(inputStream);

			FileEntry tempFileEntry = TempFileEntryUtil.addTempFileEntry(
				_group.getGroupId(), TestPropsValues.getUserId(),
				_TEMP_FOLDER_NAME, _IMAGE_TITLE, getInputStream(),
				ContentTypes.IMAGE_JPEG);

			BlogsEntryImageSelectorHelper blogsEntryImageSelectorHelper =
				new BlogsEntryImageSelectorHelper(
					tempFileEntry.getFileEntryId(),
					tempFileEntry.getFileEntryId() + 1, _IMAGE_CROP_REGION,
					StringPool.BLANK, StringPool.BLANK);

			ImageSelector imageSelector =
				blogsEntryImageSelectorHelper.getImageSelector();

			Assert.assertArrayEquals(bytes, imageSelector.getImageBytes());
			Assert.assertEquals(_IMAGE_TITLE, imageSelector.getImageTitle());
			Assert.assertEquals(
				MimeTypesUtil.getContentType(_IMAGE_TITLE),
				imageSelector.getImageMimeType());
			Assert.assertEquals(
				_IMAGE_CROP_REGION, imageSelector.getImageCropRegion());
			Assert.assertEquals(_IMAGE_URL, imageSelector.getImageURL());
			Assert.assertTrue(
				blogsEntryImageSelectorHelper.isFileEntryTempFile());
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	protected InputStream getInputStream() {
		Class<?> clazz = BlogsEntryAttachmentFileEntryHelperTest.class;

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(
			"com/liferay/portal/util/dependencies/test.jpg");
	}

	private static final String _IMAGE_CROP_REGION =
		"{\"height\": 10, \"width\": 10, \"x\": 0, \"y\": 0}";

	private static final String _IMAGE_TITLE = "image.jpg";

	private static final String _IMAGE_URL = "http://www.liferay.com";

	private static final String _TEMP_FOLDER_NAME =
		BlogsEntryImageSelectorHelperTest.class.getName();

	@DeleteAfterTestRun
	private Group _group;

}