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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.User;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.blogs.constants.BlogsConstants;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;

import java.io.InputStream;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class CoverImageTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = TestPropsValues.getUser();
	}

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
			_user.getUserId(), _group.getGroupId());

		int initialFolderFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				_group.getGroupId(), folder.getFolderId());

		addBlogsEntry("image1.jpg");

		int portletFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				_group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(
			initialFolderFileEntriesCount + 1, portletFileEntriesCount);

		PortletFileRepositoryUtil.getPortletFileEntry(
			_group.getGroupId(), folder.getFolderId(), "image1.jpg");
	}

	@Test
	public void testAddOriginalCoverImageWhenUpdatingBlogsEntry()
		throws Exception {

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			_user.getUserId(), _group.getGroupId());

		int initialFolderFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				_group.getGroupId(), folder.getFolderId());

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		updateBlogsEntry(blogsEntry.getEntryId(), "image2.jpg");

		int portletFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				_group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(
			initialFolderFileEntriesCount + 2, portletFileEntriesCount);

		PortletFileRepositoryUtil.getPortletFileEntry(
			_group.getGroupId(), folder.getFolderId(), "image2.jpg");
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
			_user.getUserId(), _group.getGroupId());

		PortletFileRepositoryUtil.getPortletFileEntry(
			_group.getGroupId(), folder.getFolderId(), "image1.jpg");

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
			_user.getUserId(), _group.getGroupId());

		PortletFileRepositoryUtil.getPortletFileEntry(
			_group.getGroupId(), folder.getFolderId(), "image1.jpg");
	}

	@Test
	public void testOriginalCoverImageNotDeletedWhenNullCoverImageSelector()
		throws Exception {

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			_user.getUserId(), _group.getGroupId());

		int initialFolderFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				_group.getGroupId(), folder.getFolderId());

		BlogsEntry blogsEntry = addBlogsEntry("image1.jpg");

		ImageSelector coverImageSelector = null;

		updateBlogsEntry(blogsEntry.getEntryId(), coverImageSelector);

		int portletFileEntriesCount =
			PortletFileRepositoryUtil.getPortletFileEntriesCount(
				_group.getGroupId(), folder.getFolderId());

		Assert.assertEquals(
			initialFolderFileEntriesCount + 1, portletFileEntriesCount);

		PortletFileRepositoryUtil.getPortletFileEntry(
			_group.getGroupId(), folder.getFolderId(), "image1.jpg");
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
			_user.getUserId(), _group.getGroupId());

		PortletFileRepositoryUtil.getPortletFileEntry(
			_group.getGroupId(), folder.getFolderId(), "image1.jpg");
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

	protected BlogsEntry addBlogsEntry(String coverImageTitle)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		FileEntry fileEntry = getTempFileEntry(
			_user.getUserId(), coverImageTitle, serviceContext);

		ImageSelector coverImageSelector = new ImageSelector(
			fileEntry.getFileEntryId(), StringPool.BLANK, _IMAGE_CROP_REGION);
		ImageSelector smallImageSelector = null;

		return BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), true, true,
			new String[0], StringPool.BLANK, coverImageSelector,
			smallImageSelector, serviceContext);
	}

	protected FileEntry getTempFileEntry(
			long userId, String title, ServiceContext serviceContext)
		throws PortalException {

		ClassLoader classLoader = getClass().getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/portal/util/dependencies/test.jpg");

		return TempFileEntryUtil.addTempFileEntry(
			serviceContext.getScopeGroupId(), userId,
			BlogsEntry.class.getName(), title, inputStream,
			MimeTypesUtil.getContentType(title));
	}

	protected BlogsEntry updateBlogsEntry(
			long blogsEntryId, ImageSelector coverImageSelector)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		ImageSelector smallImageSelector = null;

		return BlogsEntryLocalServiceUtil.updateEntry(
			_user.getUserId(), blogsEntryId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), true, true,
			new String[0], StringPool.BLANK, coverImageSelector,
			smallImageSelector, serviceContext);
	}

	protected BlogsEntry updateBlogsEntry(
			long blogsEntryId, String coverImageTitle)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		FileEntry fileEntry = getTempFileEntry(
			_user.getUserId(), coverImageTitle, serviceContext);

		ImageSelector coverImageSelector = new ImageSelector(
			fileEntry.getFileEntryId(), StringPool.BLANK, _IMAGE_CROP_REGION);

		return updateBlogsEntry(blogsEntryId, coverImageSelector);
	}

	private static final String _IMAGE_CROP_REGION =
		"{\"height\": 10, \"width\": 10, \"x\": 0, \"y\": 0}";

	@DeleteAfterTestRun
	private Group _group;

	private User _user;

}