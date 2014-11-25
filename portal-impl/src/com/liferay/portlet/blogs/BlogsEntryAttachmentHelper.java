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

import com.liferay.portal.kernel.editor.util.EditorConstants;
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
public class BlogsEntryAttachmentHelper {

	public List<BlogsEntryAttachmentReference> addBlogsEntryAttachments(
			long groupId, long userId, long entryId,
			List<FileEntry> tempBlogsEntryAttachments)
		throws PortalException {

		List<BlogsEntryAttachmentReference> blogsEntryAttachmentReferences =
			new ArrayList<>();

		for (FileEntry tempBlogsEntryAttachment : tempBlogsEntryAttachments) {
			FileEntry blogsEntryAttachment =
				addBlogsEntryAttachment(
					groupId, userId, entryId,
					tempBlogsEntryAttachment.getTitle(),
					tempBlogsEntryAttachment.getMimeType(),
					tempBlogsEntryAttachment.getContentStream());

			blogsEntryAttachmentReferences.add(
				new BlogsEntryAttachmentReference(
					tempBlogsEntryAttachment.getFileEntryId(),
					blogsEntryAttachment));
		}

		return blogsEntryAttachmentReferences;
	}

	public List<FileEntry> getTempBlogsEntryAttachments(String content)
		throws PortalException {

		List<FileEntry> blogsEntryAttachments = new ArrayList<>();

		Pattern pattern = Pattern.compile(
			EditorConstants.DATA_IMAGE_ID_ATTRIBUTE + "=.(\\d+)");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			long fileEntryId = GetterUtil.getLong(matcher.group(1));

			FileEntry blogEntryAttachment =
				PortletFileRepositoryUtil.getPortletFileEntry(fileEntryId);

			blogsEntryAttachments.add(blogEntryAttachment);
		}

		return blogsEntryAttachments;
	}

	public String updateContent(
			String content,
			List<BlogsEntryAttachmentReference> blogsEntryAttachmentReferences)
		throws PortalException {

		for (BlogsEntryAttachmentReference blogsEntryAttachmentReference :
				blogsEntryAttachmentReferences) {

			StringBundler sb = new StringBundler(5);

			sb.append("<img.*");
			sb.append(EditorConstants.DATA_IMAGE_ID_ATTRIBUTE);
			sb.append("=\\s?\"");
			sb.append(blogsEntryAttachmentReference.getTempFileEntryId());
			sb.append("\".*src=\\s?\"(.*)\".*/>");

			content = content.replaceAll(
				sb.toString(),
				getAttachmentLink(
					blogsEntryAttachmentReference.getFileEntry()));
		}

		return content;
	}

	protected FileEntry addBlogsEntryAttachment(
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

	protected String getAttachmentLink(FileEntry blogsEntryAttachment)
		throws PortalException {

		String attachmentURL = PortletFileRepositoryUtil.getPortletFileEntryURL(
			null, blogsEntryAttachment, StringPool.BLANK);

		return "<img src=\"" + attachmentURL + "\" />";
	}

}