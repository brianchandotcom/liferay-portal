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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portlet.blogs.constants.BlogsConstants;
import com.liferay.portlet.blogs.model.BlogsEntry;

import java.io.File;
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
			long groupId, long userId, long blogsEntryId, long folderId,
			List<FileEntry> tempFileEntries)
		throws PortalException {

		List<BlogsEntryAttachmentFileEntryReference>
			blogsEntryAttachmentFileEntryReferences = new ArrayList<>();

		for (FileEntry tempFileEntry : tempFileEntries) {
			FileEntry blogsEntryAttachmentFileEntry =
				addBlogsEntryAttachmentFileEntry(
					groupId, userId, blogsEntryId, folderId,
					tempFileEntry.getTitle(), tempFileEntry.getMimeType(),
					tempFileEntry.getContentStream());

			blogsEntryAttachmentFileEntryReferences.add(
				new BlogsEntryAttachmentFileEntryReference(
					tempFileEntry.getFileEntryId(),
					blogsEntryAttachmentFileEntry));
		}

		return blogsEntryAttachmentFileEntryReferences;
	}

	public FileEntry addBlogsEntryAttachmentFileEntry(
			long groupId, long userId, long blogsEntryId, long folderId,
			String fileName, String mimeType, byte[] bytes)
		throws PortalException {

		String uniqueFileName = getUniqueFileName(groupId, fileName, folderId);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, BlogsEntry.class.getName(), blogsEntryId,
			BlogsConstants.SERVICE_NAME, folderId, bytes, uniqueFileName,
			mimeType, true);
	}

	public FileEntry addBlogsEntryAttachmentFileEntry(
			long groupId, long userId, long blogsEntryId, long folderId,
			String fileName, String mimeType, File file)
		throws PortalException {

		String uniqueFileName = getUniqueFileName(groupId, fileName, folderId);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, BlogsEntry.class.getName(), blogsEntryId,
			BlogsConstants.SERVICE_NAME, folderId, file, uniqueFileName,
			mimeType, true);
	}

	public FileEntry addBlogsEntryAttachmentFileEntry(
			long groupId, long userId, long blogsEntryId, long folderId,
			String fileName, String mimeType, InputStream is)
		throws PortalException {

		String uniqueFileName = getUniqueFileName(groupId, fileName, folderId);

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, BlogsEntry.class.getName(), blogsEntryId,
			BlogsConstants.SERVICE_NAME, folderId, is, uniqueFileName, mimeType,
			true);
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

	protected String getBlogsEntryAttachmentFileEntryImgTag(
		FileEntry blogsEntryAttachmentFileEntry) {

		String fileEntryURL = PortletFileRepositoryUtil.getPortletFileEntryURL(
			null, blogsEntryAttachmentFileEntry, StringPool.BLANK);

		return "<img src=\"" + fileEntryURL + "\" />";
	}

	protected String getUniqueFileName(
			long groupId, String fileName, long folderId)
		throws PortalException {

		fileName = FileUtil.stripParentheticalSuffix(fileName);

		FileEntry fileEntry = _fetchPortletFileEntry(
			groupId, fileName, folderId);

		if (fileEntry == null) {
			return fileName;
		}

		int suffix = 1;

		for (int i = 0; i < _UNIQUE_FILE_NAME_TRIES; i++) {
			String curFileName = FileUtil.appendParentheticalSuffix(
				fileName, String.valueOf(suffix));

			fileEntry = _fetchPortletFileEntry(groupId, curFileName, folderId);

			if (fileEntry == null) {
				return curFileName;
			}

			suffix++;
		}

		throw new PortalException(
			"Unable to get a unique file name for " + fileName + " in folder " +
				folderId);
	}

	private FileEntry _fetchPortletFileEntry(
		long groupId, String fileName, long folderId) {

		try {
			return PortletFileRepositoryUtil.getPortletFileEntry(
				groupId, folderId, fileName);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return null;
		}
	}

	private static final String _ATTRIBUTE_LIST_REGEXP =
		"(\\s*?\\w+\\s*?=\\s*?\"[^\"]*\")*?\\s*?";

	private static final int _UNIQUE_FILE_NAME_TRIES = 50;

	private static final Log _log = LogFactoryUtil.getLog(
		BlogsEntryAttachmentFileEntryHelper.class);

}