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

package com.liferay.message.boards.web.internal.util;

import com.liferay.message.boards.kernel.constants.MBConstants;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ambrín Chaudhary
 */
public class MBAttachmentFileEntryUtil {

	public static List<MBAttachmentFileEntryReference>
			addMBAttachmentFileEntries(
				long groupId, long userId, long messageId, long folderId,
				List<FileEntry> tempFileEntries)
		throws PortalException {

		List<MBAttachmentFileEntryReference> mbAttachmentFileEntryReferences =
			new ArrayList<>();

		for (FileEntry tempFileEntry : tempFileEntries) {
			String uniqueFileName = _getUniqueFileName(
				groupId, tempFileEntry.getTitle(), folderId);

			FileEntry mbFileEntry =
				PortletFileRepositoryUtil.addPortletFileEntry(
					groupId, userId, MBMessage.class.getName(), messageId,
					MBConstants.SERVICE_NAME, folderId,
					tempFileEntry.getContentStream(), uniqueFileName,
					tempFileEntry.getMimeType(), true);

			mbAttachmentFileEntryReferences.add(
				new MBAttachmentFileEntryReference(
					tempFileEntry.getFileEntryId(), mbFileEntry));
		}

		return mbAttachmentFileEntryReferences;
	}

	public static List<FileEntry> getTempMBAttachmentFileEntries(String content)
		throws PortalException {

		List<FileEntry> tempMBAttachmentFileEntries = new ArrayList<>();

		Pattern pattern = Pattern.compile(
			EditorConstants.ATTRIBUTE_DATA_IMAGE_ID + "=.(\\d+)");

		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			long fileEntryId = GetterUtil.getLong(matcher.group(1));

			FileEntry tempFileEntry =
				PortletFileRepositoryUtil.getPortletFileEntry(fileEntryId);

			tempMBAttachmentFileEntries.add(tempFileEntry);
		}

		return tempMBAttachmentFileEntries;
	}

	public static String updateMessageContent(
		String body, MBMessage message,
		List<MBAttachmentFileEntryReference> mbAttachmentFileEntryReferences) {

		if (message.isFormatBBCode()) {
			return _updateContentBBCode(body, mbAttachmentFileEntryReferences);
		}
		else {
			return _updateContentHTML(body, mbAttachmentFileEntryReferences);
		}
	}

	private static FileEntry _fetchPortletFileEntry(
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

	private static String _getMBAttachmentBBCodeImgTag(
		FileEntry mbAttachmentFileEntry) {

		String fileEntryURL = PortletFileRepositoryUtil.getPortletFileEntryURL(
			null, mbAttachmentFileEntry, StringPool.BLANK);

		return "[img]" + fileEntryURL + "[/img]";
	}

	private static String _getMBAttachmentHTMLImgTag(
		FileEntry mbAttachmentFileEntry) {

		String fileEntryURL = PortletFileRepositoryUtil.getPortletFileEntryURL(
			null, mbAttachmentFileEntry, StringPool.BLANK);

		return "<img src=\"" + fileEntryURL + "\" />";
	}

	private static String _getUniqueFileName(
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

	private static String _updateContentBBCode(
		String content,
		List<MBAttachmentFileEntryReference> mbAttachmentFileEntryReferences) {

		for (MBAttachmentFileEntryReference mbAttachmentFileEntryReference :
				mbAttachmentFileEntryReferences) {

			Matcher matcher = _BBCODE_IMG_TAG_REGEXP.matcher(content);

			content = matcher.replaceAll(
				_getMBAttachmentBBCodeImgTag(
					mbAttachmentFileEntryReference.getMbAttachmentFileEntry()));
		}

		return content;
	}

	private static String _updateContentHTML(
		String content,
		List<MBAttachmentFileEntryReference> mbAttachmentFileEntryReferences) {

		for (MBAttachmentFileEntryReference mbAttachmentFileEntryReference :
				mbAttachmentFileEntryReferences) {

			StringBundler sb = new StringBundler(8);

			sb.append("<\\s*?img");
			sb.append(_ATTRIBUTE_LIST_REGEXP);
			sb.append(EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);
			sb.append("\\s*?=\\s*?\"");
			sb.append(
				mbAttachmentFileEntryReference.
					getTempMBAttachmentFileEntryId());
			sb.append("\"");
			sb.append(_ATTRIBUTE_LIST_REGEXP);
			sb.append("/>");

			content = content.replaceAll(
				sb.toString(),
				_getMBAttachmentHTMLImgTag(
					mbAttachmentFileEntryReference.getMbAttachmentFileEntry()));
		}

		return content;
	}

	private static final String _ATTRIBUTE_LIST_REGEXP =
		"(\\s*?\\w+\\s*?=\\s*?\"[^\"]*\")*?\\s*?";

	private static final Pattern _BBCODE_IMG_TAG_REGEXP = Pattern.compile(
		"\\[img[^\\]]*?" + EditorConstants.ATTRIBUTE_DATA_IMAGE_ID +
			"=\"[^\"]*\"[^\\]]*\\][^\\[]+\\[/img\\]");

	private static final int _UNIQUE_FILE_NAME_TRIES = 50;

	private static final Log _log = LogFactoryUtil.getLog(
		MBAttachmentFileEntryUtil.class);

}