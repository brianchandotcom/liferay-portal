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

import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sergio González
 */
public class BlogsEntryFileEntryHelper {

	public List<BlogsEntryFileEntryReference> addBlogsEntryFileEntries(
			long groupId, long userId, long entryId,
			List<FileEntry> tempBlogsEntryFileEntries)
		throws PortalException {

		List<BlogsEntryFileEntryReference> blogsEntryFileEntryReferences =
			new ArrayList<>();

		for (FileEntry tempBlogsEntryFileEntry : tempBlogsEntryFileEntries) {
			FileEntry blogsEntryFileEntry = addBlogsEntryFileEntry(
				groupId, userId, entryId, tempBlogsEntryFileEntry.getTitle(),
				tempBlogsEntryFileEntry.getMimeType(),
				tempBlogsEntryFileEntry.getContentStream());

			blogsEntryFileEntryReferences.add(
				new BlogsEntryFileEntryReference(
					tempBlogsEntryFileEntry.getFileEntryId(),
					blogsEntryFileEntry));
		}

		return blogsEntryFileEntryReferences;
	}

	public List<FileEntry> getTempBlogsEntryFileEntries(String content)
		throws PortalException {

		List<FileEntry> tempBlogsEntryFileEntries = new ArrayList<>();

		Pattern pattern = Pattern.compile(
			EditorConstants.ATTRIBUTE_DATA_IMAGE_ID + "=.(\\d+)");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			long fileEntryId = GetterUtil.getLong(matcher.group(1));

			FileEntry tempBlogsEntryFileEntry =
				PortletFileRepositoryUtil.getPortletFileEntry(fileEntryId);

			tempBlogsEntryFileEntries.add(tempBlogsEntryFileEntry);
		}

		return tempBlogsEntryFileEntries;
	}

	public String updateContent(
		String content,
		List<BlogsEntryFileEntryReference> blogsEntryFileEntryReferences) {

		for (BlogsEntryFileEntryReference blogsEntryFileEntryReference :
				blogsEntryFileEntryReferences) {

			StringBundler sb = new StringBundler(5);

			sb.append("<img.*");
			sb.append(EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);
			sb.append("=\\s?\"");
			sb.append(blogsEntryFileEntryReference.getTempFileEntryId());
			sb.append("\".*src=\\s?\"(.*)\".*/>");

			content = content.replaceAll(
				sb.toString(),
				getFileEntryLink(
					blogsEntryFileEntryReference.getFileEntry()));
		}

		return content;
	}

	protected FileEntry addBlogsEntryFileEntry(
			long groupId, long userId, long entryId, String fileName,
			String mimeType, InputStream is)
		throws PortalException {

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			userId, groupId);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, BlogsEntry.class.getName(), entryId,
			PortletKeys.BLOGS, folder.getFolderId(), is, fileName, mimeType,
			true);
	}

	protected String getFileEntryLink(FileEntry blogsEntryFileEntry) {
		String fileEntryURL = PortletFileRepositoryUtil.getPortletFileEntryURL(
			null, blogsEntryFileEntry, StringPool.BLANK);

		return "<img src=\"" + fileEntryURL + "\" />";
	}

}