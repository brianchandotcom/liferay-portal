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
 * @author Roberto Díaz
 */
public class BlogsEntryAttachmentFileEntryHelper {

	public List<BlogsEntryAttachmentFileEntryReference>
		addBlogsEntryAttachmentFileEntries(
			long groupId, long userId, long blogsEntryId,
			List<FileEntry> tempFileEntries)
		throws PortalException {

		List<BlogsEntryAttachmentFileEntryReference>
			blogsEntryAttachmentFileEntryReferences = new ArrayList<>();

		for (FileEntry tempFileEntry : tempFileEntries) {
			FileEntry blogsAttachmentEntryFileEntry =
				addBlogsEntryAttachmentFileEntry(
					groupId, userId, blogsEntryId, tempFileEntry.getTitle(),
					tempFileEntry.getMimeType(),
					tempFileEntry.getContentStream());

			blogsEntryAttachmentFileEntryReferences.add(
				new BlogsEntryAttachmentFileEntryReference(
					tempFileEntry.getFileEntryId(),
					blogsAttachmentEntryFileEntry));
		}

		return blogsEntryAttachmentFileEntryReferences;
	}

	public List<FileEntry> getTempBlogsEntryAttachmentFileEntries(
			String content)
		throws PortalException {

		List<FileEntry> tempBlogsEntryAttachmentFileEntries = new ArrayList<>();

		Pattern pattern = Pattern.compile(
			EditorConstants.ATTRIBUTE_DATA_IMAGE_ID + "=.(\\d+)");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			long fileEntryId = GetterUtil.getLong(matcher.group(1));

			FileEntry tempFileEntry =
				PortletFileRepositoryUtil.getPortletFileEntry(fileEntryId);

			tempBlogsEntryAttachmentFileEntries.add(tempFileEntry);
		}

		return tempBlogsEntryAttachmentFileEntries;
	}

	public String updateContent(
		String content, List<BlogsEntryAttachmentFileEntryReference>
			blogsEntryAttachmentFileEntryReferences) {

		for (BlogsEntryAttachmentFileEntryReference
				blogsEntryAttachmentFileEntryReference :
					blogsEntryAttachmentFileEntryReferences) {

			StringBundler sb = new StringBundler(5);

			sb.append("<img.*");
			sb.append(EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);
			sb.append("=\\s?\"");
			sb.append(
				blogsEntryAttachmentFileEntryReference.
					getTempBlogsEntryAttachmentFileEntryId());
			sb.append("\".*src=\\s?\"(.*)\".*/>");

			content = content.replaceAll(
				sb.toString(),
				getBlogsEntryAttachmentFileEntryImgTag(
					blogsEntryAttachmentFileEntryReference.
						getBlogsEntryAttachmentFileEntry()));
		}

		return content;
	}

	protected FileEntry addBlogsEntryAttachmentFileEntry(
			long groupId, long userId, long blogsEntryId, String fileName,
			String mimeType, InputStream is)
		throws PortalException {

		Folder folder = BlogsEntryLocalServiceUtil.addAttachmentsFolder(
			userId, groupId);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, BlogsEntry.class.getName(), blogsEntryId,
			PortletKeys.BLOGS, folder.getFolderId(), is, fileName, mimeType,
			true);
	}

	protected String getBlogsEntryAttachmentFileEntryImgTag(
		FileEntry blogsEntryAttachmentEntryFileEntry) {

		String fileEntryURL = PortletFileRepositoryUtil.getPortletFileEntryURL(
			null, blogsEntryAttachmentEntryFileEntry, StringPool.BLANK);

		return "<img src=\"" + fileEntryURL + "\" />";
	}

}