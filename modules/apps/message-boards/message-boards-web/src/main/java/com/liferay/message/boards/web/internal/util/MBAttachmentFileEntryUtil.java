/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.web.internal.util;

import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.editor.constants.EditorConstants;
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
					uniqueFileNameUnsafeFunction)
		throws PortalException {

		List<MBAttachmentFileEntryReference> mbAttachmentFileEntryReferences =
			new ArrayList<>(tempFileEntries.size());

		for (FileEntry tempFileEntry : tempFileEntries) {
			FileEntry mbFileEntry =
				PortletFileRepositoryUtil.addPortletFileEntry(
					null, groupId, userId, MBMessage.class.getName(), messageId,
					MBConstants.SERVICE_NAME, folderId,
					tempFileEntry.getContentStream(),
					uniqueFileNameUnsafeFunction.apply(
						tempFileEntry.getTitle()),
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