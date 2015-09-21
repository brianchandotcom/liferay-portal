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

package com.liferay.portlet.blogs.attachments;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.User;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.blogs.constants.BlogsConstants;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public abstract class BaseBlogsImageTestCase {

	@Test
	public void testAddCoverImage() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry coverImageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				blogsEntry.getCoverImageFileEntryId());

		Assert.assertEquals("image1.jpg", coverImageFileEntry.getTitle());
	}

	@Test
	public void testAddOriginalCoverImage() throws Exception {
		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			user.getUserId(), group.getGroupId());

		int initialFolderFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folder.getFolderId());

		addBlogsEntry("image1.jpg");

		int portletFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(
			initialFolderFileEntriesCount + 1, portletFileEntriesCount);

		PortletFileRepositoryUtil.getPortletFileEntry(
			group.getGroupId(), folder.getFolderId(), "image1.jpg");
	}

	@Test
	public void testAddOriginalCoverImageWhenUpdatingBlogsEntry()
		throws Exception {

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			user.getUserId(), group.getGroupId());

		int initialFolderFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folder.getFolderId());

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		updateBlogsEntry(blogsEntry.getEntryId(), "image2.jpg");

		int portletFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(
			initialFolderFileEntriesCount + 2, portletFileEntriesCount);

		PortletFileRepositoryUtil.getPortletFileEntry(
			group.getGroupId(), folder.getFolderId(), "image2.jpg");
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testCoverImageDeletedWhenDeletingBlogsEntry() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry coverImageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				blogsEntry.getCoverImageFileEntryId());

		BlogsEntryLocalServiceUtil.deleteEntry(blogsEntry);

		PortletFileRepositoryUtil.getPortletFileEntry(
			coverImageFileEntry.getFileEntryId());
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testCoverImageDeletedWhenEmptyCoverImageSelector()
		throws Exception {

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry coverImageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				blogsEntry.getCoverImageFileEntryId());

		ImageSelector coverImageSelector = new ImageSelector(
			0, StringPool.BLANK, StringPool.BLANK);

		blogsEntry = updateBlogsEntry(
			blogsEntry.getEntryId(), coverImageSelector);

		Assert.assertEquals(0, blogsEntry.getCoverImageFileEntryId());

		PortletFileRepositoryUtil.getPortletFileEntry(
			coverImageFileEntry.getFileEntryId());
	}

	@Test
	public void testCoverImageNotChangedWhenNullCoverImageSelector()
		throws Exception {

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry coverImageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				blogsEntry.getCoverImageFileEntryId());

		ImageSelector coverImageSelector = null;

		blogsEntry = updateBlogsEntry(
			blogsEntry.getEntryId(), coverImageSelector);

		Assert.assertEquals(
			coverImageFileEntry.getFileEntryId(),
			blogsEntry.getCoverImageFileEntryId());

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			user.getUserId(), group.getGroupId());

		PortletFileRepositoryUtil.getPortletFileEntry(
			group.getGroupId(), folder.getFolderId(), "image1.jpg");

		PortletFileRepositoryUtil.getPortletFileEntry(
			coverImageFileEntry.getFileEntryId());
	}

	@Test
	public void testCoverImageStoredInBlogsRepository() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry coverImageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				blogsEntry.getCoverImageFileEntryId());

		Repository repository = RepositoryLocalServiceUtil.getRepository(
			coverImageFileEntry.getRepositoryId());

		Assert.assertEquals(BlogsConstants.SERVICE_NAME, repository.getName());
	}

	@Test
	public void testCoverImageStoredInCoverImageFolder() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry coverImageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				blogsEntry.getCoverImageFileEntryId());

		Folder coverImageFolder = coverImageFileEntry.getFolder();

		Assert.assertNotEquals(
			BlogsConstants.SERVICE_NAME, coverImageFolder.getName());
	}

	@Test
	public void testOriginalCoverImageNotDeletedWhenEmptyCoverImageSelector()
		throws Exception {

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		ImageSelector coverImageSelector = new ImageSelector(
			0, StringPool.BLANK, StringPool.BLANK);

		updateBlogsEntry(blogsEntry.getEntryId(), coverImageSelector);

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			user.getUserId(), group.getGroupId());

		PortletFileRepositoryUtil.getPortletFileEntry(
			group.getGroupId(), folder.getFolderId(), "image1.jpg");
	}

	@Test
	public void testOriginalCoverImageNotDeletedWhenNullCoverImageSelector()
		throws Exception {

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			user.getUserId(), group.getGroupId());

		int initialFolderFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folder.getFolderId());

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		ImageSelector coverImageSelector = null;

		updateBlogsEntry(blogsEntry.getEntryId(), coverImageSelector);

		int portletFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(
			initialFolderFileEntriesCount + 1, portletFileEntriesCount);

		PortletFileRepositoryUtil.getPortletFileEntry(
			group.getGroupId(), folder.getFolderId(), "image1.jpg");
	}

	@Test
	public void testOriginalCoverImageStoredInBlogsRepository()
		throws Exception {

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry coverImageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				blogsEntry.getCoverImageFileEntryId());

		Repository repository = RepositoryLocalServiceUtil.getRepository(
			coverImageFileEntry.getRepositoryId());

		Assert.assertEquals(BlogsConstants.SERVICE_NAME, repository.getName());
	}

	@Test(expected = NoSuchFileEntryException.class)
	public void testPreviousCoverImageDeletedWhenChangingCoverImage()
		throws Exception {

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		FileEntry coverImageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				blogsEntry.getCoverImageFileEntryId());

		updateBlogsEntry(blogsEntry.getEntryId(), "image2.jpg");

		PortletFileRepositoryUtil.getPortletFileEntry(
			coverImageFileEntry.getFileEntryId());
	}

	@Test
	public void testPreviousOriginalCoverImageNotDeletedWhenChangingCoverImage()
		throws Exception {

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		updateBlogsEntry(blogsEntry.getEntryId(), "image2.jpg");

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			user.getUserId(), group.getGroupId());

		PortletFileRepositoryUtil.getPortletFileEntry(
			group.getGroupId(), folder.getFolderId(), "image1.jpg");
	}

	@Test
	public void testUpdateCoverImage() throws Exception {
		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		blogsEntry = updateBlogsEntry(blogsEntry.getEntryId(), "image2.jpg");

		FileEntry coverImageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				blogsEntry.getCoverImageFileEntryId());

		Assert.assertEquals("image2.jpg", coverImageFileEntry.getTitle());
	}

	protected abstract BlogsEntry addBlogsEntry(String coverImageTitle)
		throws Exception;

	protected abstract FileEntry getTempFileEntry(
		long userId, String title, ServiceContext serviceContext)
		throws PortalException;

	protected abstract BlogsEntry updateBlogsEntry(
		long blogsEntryId, ImageSelector coverImageSelector)
		throws Exception;

	protected abstract BlogsEntry updateBlogsEntry(
		long blogsEntryId, String coverImageTitle)
		throws Exception;

	@DeleteAfterTestRun
	protected Group group;

	protected User user;
}