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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.util.BlogsConstants;

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
			FileEntry blogsEntryAttachmentFileEntry =
				addBlogsEntryAttachmentFileEntry(
					groupId, userId, blogsEntryId, tempFileEntry.getTitle(),
					tempFileEntry.getMimeType(),
					tempFileEntry.getContentStream());

			blogsEntryAttachmentFileEntryReferences.add(
				new BlogsEntryAttachmentFileEntryReference(
					tempFileEntry.getFileEntryId(),
					blogsEntryAttachmentFileEntry));
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

			StringBundler sb = new StringBundler(8);

			sb.append("<\\s*?img");
			sb.append(_ATTRIBUTE_LIST_REGEXP);
			sb.append(EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);
			sb.append("\\s*?=\\s*?\"");
			sb.append(
				blogsEntryAttachmentFileEntryReference.
					getTempBlogsEntryAttachmentFileEntryId());
			sb.append("\"");
			sb.append(_ATTRIBUTE_LIST_REGEXP);
			sb.append("/>");

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

		FileEntry fileEntry = _fetchPortletFileEntry(groupId, fileName, folder);

		int counterSuffixValue = 1;

		while (fileEntry != null) {
			String curfileName = FileUtil.updateFileName(
				fileName, String.valueOf(counterSuffixValue));

			fileEntry = _fetchPortletFileEntry(groupId, curfileName, folder);

			if (fileEntry == null) {
				fileName = curfileName;

				break;
			}

			counterSuffixValue++;
		}

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, BlogsEntry.class.getName(), blogsEntryId,
			BlogsConstants.SERVICE_NAME, folder.getFolderId(), is, fileName,
			mimeType, true);
	}

	protected String getBlogsEntryAttachmentFileEntryImgTag(
		FileEntry blogsEntryAttachmentFileEntry) {

		String fileEntryURL = PortletFileRepositoryUtil.getPortletFileEntryURL(
			null, blogsEntryAttachmentFileEntry, StringPool.BLANK);

		return "<img src=\"" + fileEntryURL + "\" />";
	}

	private FileEntry _fetchPortletFileEntry(
		long groupId, String fileName, Folder folder) {

		try {
			return PortletFileRepositoryUtil.getPortletFileEntry(
				groupId, folder.getFolderId(), fileName);
		}
		catch (PortalException pe) {
			return null;
		}
	}

	private static final String _ATTRIBUTE_LIST_REGEXP =
		"(\\s*?\\w+\\s*?=\\s*?\"[^\"]*\")*?\\s*?";

}