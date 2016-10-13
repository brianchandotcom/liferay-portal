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

package com.liferay.blogs.util;

import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Sergio González
 */
@PrepareForTest(PortletFileRepositoryUtil.class)
@RunWith(PowerMockRunner.class)
public class BlogsEntryAttachmentFileEntryHelperUpdateContentTest
	extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		stub(
			method(
				PortletFileRepositoryUtil.class, "getPortletFileEntryURL",
				ThemeDisplay.class, FileEntry.class, String.class)
		).toReturn(
			_FILE_ENTRY_IMAGE_URL
		);

		long tempFileEntryId = RandomTestUtil.randomLong();

		_blogsEntryAttachmentFileEntryReferences.add(
			new BlogsEntryAttachmentFileEntryReference(
				tempFileEntryId, _fileEntry));

		_tempFileEntryImgTag =
			BlogsTestUtil.getTempBlogsEntryAttachmentFileEntryImgTag(
				tempFileEntryId, _TEMP_FILE_ENTRY_IMAGE_URL);
	}

	@Test
	public void
			testUpdateContentWithEmptyBlogsEntryAttachmentFileEntryReferences()
		throws Exception {

		String content = BlogsEntryAttachmentFileEntryHelper.updateContent(
			populateContentWithSingleImgTag(_tempFileEntryImgTag),
			Collections.<BlogsEntryAttachmentFileEntryReference>emptyList());

		String expectedContent = populateContentWithSingleImgTag(
			_tempFileEntryImgTag);

		Assert.assertEquals(expectedContent, content);
	}

	@Test
	public void testUpdateContentWithMultipleImgTags() throws Exception {
		String content = BlogsEntryAttachmentFileEntryHelper.updateContent(
			populateContentWithMultipleImgTags(_tempFileEntryImgTag),
			_blogsEntryAttachmentFileEntryReferences);

		String expectedContent = populateContentWithMultipleImgTags(
			"<img src=\"" + _FILE_ENTRY_IMAGE_URL + "\" />");

		Assert.assertEquals(expectedContent, content);
	}

	@Test
	public void testUpdateContentWithoutImgTag() throws Exception {
		String content = BlogsEntryAttachmentFileEntryHelper.updateContent(
			populateContentWithSingleImgTag(StringPool.BLANK),
			_blogsEntryAttachmentFileEntryReferences);

		String expectedContent = populateContentWithSingleImgTag(
			StringPool.BLANK);

		Assert.assertEquals(expectedContent, content);
	}

	@Test
	public void testUpdateContentWithSingleImgTag() throws Exception {
		String content = BlogsEntryAttachmentFileEntryHelper.updateContent(
			populateContentWithSingleImgTag(_tempFileEntryImgTag),
			_blogsEntryAttachmentFileEntryReferences);

		String expectedContent = populateContentWithSingleImgTag(
			"<img src=\"" + _FILE_ENTRY_IMAGE_URL + "\" />");

		Assert.assertEquals(expectedContent, content);
	}

	protected String populateContentWithMultipleImgTags(String imgTag) {
		StringBundler sb = new StringBundler(4);

		sb.append("<p>Sample Text</p><a href=\"www.liferay.com\">");
		sb.append("<span><img src=\"www.liferay.com/pic1.jpg\" /></span>");
		sb.append(imgTag);
		sb.append("<img src=\"www.liferay.com/pic2.jpg\" /></a>");

		return sb.toString();
	}

	protected String populateContentWithSingleImgTag(String imgTag) {
		StringBundler sb = new StringBundler(3);

		sb.append("<p>Sample Text</p><a href=\"www.liferay.com\">");
		sb.append(imgTag);
		sb.append("<span></a>");

		return sb.toString();
	}

	private static final String _FILE_ENTRY_IMAGE_URL = "www.liferay.com/logo";

	private static final String _TEMP_FILE_ENTRY_IMAGE_URL =
		"www.liferay.com/temp_logo";

	private final List<BlogsEntryAttachmentFileEntryReference>
		_blogsEntryAttachmentFileEntryReferences = new ArrayList<>();

	@Mock
	private FileEntry _fileEntry;

	private String _tempFileEntryImgTag;

}