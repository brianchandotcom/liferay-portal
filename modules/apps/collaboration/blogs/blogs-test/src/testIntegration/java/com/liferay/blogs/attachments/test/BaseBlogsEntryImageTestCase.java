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

package com.liferay.blogs.attachments.test;

import com.liferay.blogs.constants.BlogsConstants;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public abstract class BaseBlogsEntryImageTestCase {

	@Test
	public void testAddImage() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry((ImageSelector)null);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), user.getUserId());

		FileEntry fileEntry = getTempFileEntry(
			user.getUserId(), "image1.jpg", serviceContext);

		ImageSelector imageSelector = new ImageSelector(
			FileUtil.getBytes(fileEntry.getContentStream()),
			fileEntry.getTitle(), fileEntry.getMimeType(), IMAGE_CROP_REGION);

		addImage(blogsEntry.getEntryId(), imageSelector);

		blogsEntry = BlogsEntryLocalServiceUtil.getEntry(
			blogsEntry.getEntryId());

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntryId(blogsEntry));

		Assert.assertEquals("image1.jpg", imageFileEntry.getTitle());
	}

	@Test
	public void testAddImageWhenAddingEntry() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntryId(blogsEntry));

		Assert.assertEquals("image1.jpg", imageFileEntry.getTitle());
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testImageDeletedWhenDeletingBlogsEntry() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntryId(blogsEntry));

		BlogsEntryLocalServiceUtil.deleteEntry(blogsEntry);

		PortletFileRepositoryUtil.getPortletFileEntry(
			imageFileEntry.getFileEntryId());
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testImageDeletedWhenUpdatingBlogsEntryWithEmptyImageSelector()
		throws Exception {

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntryId(blogsEntry));

		ImageSelector imageSelector = new ImageSelector(
			null, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK);

		blogsEntry = updateBlogsEntry(blogsEntry.getEntryId(), imageSelector);

		Assert.assertEquals(0, getImageFileEntryId(blogsEntry));

		PortletFileRepositoryUtil.getPortletFileEntry(
			imageFileEntry.getFileEntryId());
	}

	@Test
	public void testImageNotUpdatedWhenUpdatingBlogsEntryWithNullImageSelector()
		throws Exception {

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntryId(blogsEntry));

		ImageSelector imageSelector = null;

		blogsEntry = updateBlogsEntry(blogsEntry.getEntryId(), imageSelector);

		Assert.assertEquals(
			imageFileEntry.getFileEntryId(), getImageFileEntryId(blogsEntry));

		PortletFileRepositoryUtil.getPortletFileEntry(
			imageFileEntry.getFileEntryId());
	}

	@Test
	public void testImageStoredInBlogsRepository() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntryId(blogsEntry));

		Repository repository = RepositoryLocalServiceUtil.getRepository(
			imageFileEntry.getRepositoryId());

		Assert.assertEquals(BlogsConstants.SERVICE_NAME, repository.getName());
	}

	@Test
	public void testImageStoredInInvisibleImageFolder() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntryId(blogsEntry));

		Folder imageFolder = imageFileEntry.getFolder();

		Assert.assertNotEquals(
			BlogsConstants.SERVICE_NAME, imageFolder.getName());
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testPreviousImageDeletedWhenUpdatingImage() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntryId(blogsEntry));

		updateBlogsEntry(blogsEntry.getEntryId(), "image2.jpg");

		PortletFileRepositoryUtil.getPortletFileEntry(
			imageFileEntry.getFileEntryId());
	}

	@Test
	public void testUpdateImageWhenUpdatingEntry() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		blogsEntry = updateBlogsEntry(blogsEntry.getEntryId(), "image2.jpg");

		FileEntry imageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				getImageFileEntryId(blogsEntry));

		Assert.assertEquals("image2.jpg", imageFileEntry.getTitle());
	}

	protected abstract BlogsEntry addBlogsEntry(ImageSelector imageSelector)
		throws Exception;

	protected abstract BlogsEntry addBlogsEntry(String imageTitle)
		throws Exception;

	protected abstract void addImage(long entryId, ImageSelector imageSelector)
		throws Exception;

	protected FileEntry getFileEntry(
			long userId, String title, ServiceContext serviceContext)
		throws PortalException {

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/blogs/dependencies/test.jpg");

		return PortletFileRepositoryUtil.addPortletFileEntry(
			serviceContext.getScopeGroupId(), userId,
			BlogsEntry.class.getName(), 0, StringUtil.randomString(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, inputStream, title,
			MimeTypesUtil.getContentType(title), false);
	}

	protected abstract long getImageFileEntryId(BlogsEntry blogsEntry);

	protected FileEntry getTempFileEntry(
			long userId, String title, ServiceContext serviceContext)
		throws PortalException {

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/blogs/dependencies/test.jpg");

		return TempFileEntryUtil.addTempFileEntry(
			serviceContext.getScopeGroupId(), userId,
			BlogsEntry.class.getName(), title, inputStream,
			MimeTypesUtil.getContentType(title));
	}

	protected abstract BlogsEntry updateBlogsEntry(
			long blogsEntryId, ImageSelector imageSelector)
		throws Exception;

	protected abstract BlogsEntry updateBlogsEntry(
			long blogsEntryId, String imageTitle)
		throws Exception;

	protected static final String IMAGE_CROP_REGION =
		"{\"height\": 10, \"width\": 10, \"x\": 0, \"y\": 0}";

	@DeleteAfterTestRun
	protected Group group;

	protected User user;

}