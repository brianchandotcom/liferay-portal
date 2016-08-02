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

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portlet.blogs.BlogsEntryAttachmentFileEntryHelper;
import com.liferay.portlet.blogs.BlogsEntryAttachmentFileEntryReference;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class BlogsEntryAttachmentFileEntryHelperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addGroupAdminUser(_group);

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testAddBlogsEntryAttachmentFileEntries() throws Exception {
		FileEntry tempFileEntry = TempFileEntryUtil.addTempFileEntry(
			_group.getGroupId(), _user.getUserId(), _TEMP_FOLDER_NAME,
			"image.jpg", getInputStream(), ContentTypes.IMAGE_JPEG);

		List<BlogsEntryAttachmentFileEntryReference>
			blogsEntryAttachmentFileEntryReferences =
				getBlogsEntryAttachmentFileEntryReferences(tempFileEntry);

		Assert.assertEquals(1, blogsEntryAttachmentFileEntryReferences.size());

		BlogsEntryAttachmentFileEntryReference
			blogsEntryAttachmentFileEntryReference =
				blogsEntryAttachmentFileEntryReferences.get(0);

		Assert.assertEquals(
			tempFileEntry.getFileEntryId(),
			blogsEntryAttachmentFileEntryReference.
				getTempBlogsEntryAttachmentFileEntryId());

		FileEntry fileEntry =
			blogsEntryAttachmentFileEntryReference.
				getBlogsEntryAttachmentFileEntry();

		Assert.assertEquals(tempFileEntry.getTitle(), fileEntry.getTitle());
		Assert.assertEquals(
			tempFileEntry.getMimeType(), fileEntry.getMimeType());
		Assert.assertEquals(
			DigesterUtil.digestBase64(tempFileEntry.getContentStream()),
			DigesterUtil.digestBase64(fileEntry.getContentStream()));
	}

	@Test
	public void testGetTempBlogsEntryAttachmentFileEntries() throws Exception {
		FileEntry tempFileEntry = TempFileEntryUtil.addTempFileEntry(
			_group.getGroupId(), _user.getUserId(), _TEMP_FOLDER_NAME,
			"image.jpg", getInputStream(), ContentTypes.IMAGE_JPEG);

		String tempFileEntryImgTag =
			BlogsTestUtil.getTempBlogsEntryAttachmentFileEntryImgTag(
				tempFileEntry.getFileEntryId(),
				PortletFileRepositoryUtil.getPortletFileEntryURL(
					null, tempFileEntry, StringPool.BLANK));

		List<FileEntry> tempBlogsEntryAttachmentFileEntries =
			_blogsEntryAttachmentFileEntryHelper.
				getTempBlogsEntryAttachmentFileEntries(
					getContent(tempFileEntryImgTag));

		Assert.assertEquals(1, tempBlogsEntryAttachmentFileEntries.size());

		for (FileEntry tempBlogsEntryAttachmentFileEntry :
				tempBlogsEntryAttachmentFileEntries) {

			Assert.assertEquals(
				tempFileEntry.getFileEntryId(),
				tempBlogsEntryAttachmentFileEntry.getFileEntryId());
		}
	}

	@Test
	public void testGetTempBlogsEntryAttachmentFileEntriesWithModifiedImgTag()
		throws Exception {

		FileEntry tempFileEntry = TempFileEntryUtil.addTempFileEntry(
			_group.getGroupId(), _user.getUserId(), _TEMP_FOLDER_NAME,
			"image.jpg", getInputStream(), ContentTypes.IMAGE_JPEG);

		String tempFileEntryImgTag = getModifiedTempFileEntryImgTag(
			tempFileEntry);

		List<FileEntry> tempBlogsEntryAttachmentFileEntries =
			_blogsEntryAttachmentFileEntryHelper.
				getTempBlogsEntryAttachmentFileEntries(
					getContent(tempFileEntryImgTag));

		Assert.assertEquals(1, tempBlogsEntryAttachmentFileEntries.size());

		for (FileEntry tempBlogsEntryAttachmentFileEntry :
				tempBlogsEntryAttachmentFileEntries) {

			Assert.assertEquals(
				tempFileEntry.getFileEntryId(),
				tempBlogsEntryAttachmentFileEntry.getFileEntryId());
		}
	}

	protected List<BlogsEntryAttachmentFileEntryReference>
			getBlogsEntryAttachmentFileEntryReferences(
				FileEntry tempFileEntry)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId());

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			_user.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		List<FileEntry> tempFileEntries = new ArrayList<>();

		tempFileEntries.add(tempFileEntry);

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			_user.getUserId(), _group.getGroupId());

		return
			_blogsEntryAttachmentFileEntryHelper.
				addBlogsEntryAttachmentFileEntries(
					_group.getGroupId(), _user.getUserId(), entry.getEntryId(),
					folder.getFolderId(), tempFileEntries);
	}

	protected String getContent(String tempFileEntryImgTag) {
		StringBundler sb = new StringBundler(10);

		sb.append("<p>");
		sb.append(RandomTestUtil.randomStrings(50));
		sb.append("</p>");
		sb.append("<a href=\"www.liferay.com\"><span>");
		sb.append(RandomTestUtil.randomStrings(50));
		sb.append("<img src=\"www.liferay.com/logo.png\" /><span>");
		sb.append(RandomTestUtil.randomStrings(50));
		sb.append("</span>");
		sb.append(tempFileEntryImgTag);
		sb.append("<span></a>");

		return sb.toString();
	}

	protected InputStream getInputStream() {
		Class<?> clazz = BlogsEntryAttachmentFileEntryHelperTest.class;

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(
			"com/liferay/blogs/dependencies/test.jpg");
	}

	protected String getModifiedTempFileEntryImgTag(FileEntry tempFileEntry) {
		StringBundler sb = new StringBundler(7);

		sb.append("<img ");
		sb.append(EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);
		sb.append("=\"");
		sb.append(tempFileEntry.getFileEntryId());
		sb.append("\" class=\"test-class\" id=\"test-id\" src=\"");
		sb.append(
			PortletFileRepositoryUtil.getPortletFileEntryURL(
				null, tempFileEntry, StringPool.BLANK));
		sb.append("\" title=\"test-title\" />");

		return sb.toString();
	}

	private static final String _TEMP_FOLDER_NAME = BlogsEntry.class.getName();

	private final BlogsEntryAttachmentFileEntryHelper
		_blogsEntryAttachmentFileEntryHelper =
			new BlogsEntryAttachmentFileEntryHelper();

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user;

}