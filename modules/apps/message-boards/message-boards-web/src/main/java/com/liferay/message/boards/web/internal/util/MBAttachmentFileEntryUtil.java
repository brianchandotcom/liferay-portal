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

import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;

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
				List<FileEntry> tempFileEntries,
				UnsafeFunction<String, String, PortalException>
					uniqueFileNameFunction)
		throws PortalException {

		List<MBAttachmentFileEntryReference> mbAttachmentFileEntryReferences =
			new ArrayList<>(tempFileEntries.size());

		for (FileEntry tempFileEntry : tempFileEntries) {
			FileEntry mbFileEntry =
				PortletFileRepositoryUtil.addPortletFileEntry(
					groupId, userId, MBMessage.class.getName(), messageId,
					MBConstants.SERVICE_NAME, folderId,
					tempFileEntry.getContentStream(),
					uniqueFileNameFunction.apply(tempFileEntry.getTitle()),
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

}