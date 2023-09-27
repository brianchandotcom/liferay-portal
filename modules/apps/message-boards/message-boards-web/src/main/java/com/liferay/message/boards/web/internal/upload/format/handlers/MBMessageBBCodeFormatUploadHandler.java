/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.web.internal.upload.format.handlers;

import com.liferay.message.boards.web.internal.upload.format.MBMessageFormatUploadHandler;
import com.liferay.message.boards.web.internal.util.MBAttachmentFileEntryReference;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.constants.EditorConstants;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "format=bbcode", service = MBMessageFormatUploadHandler.class
)
public class MBMessageBBCodeFormatUploadHandler
	implements MBMessageFormatUploadHandler {

	@Override
	public String replaceImageReferences(
		String content,
		List<MBAttachmentFileEntryReference> mbAttachmentFileEntryReferences) {

		for (MBAttachmentFileEntryReference mbAttachmentFileEntryReference :
				mbAttachmentFileEntryReferences) {

			Pattern pattern = _getTempImagePattern(
				mbAttachmentFileEntryReference.
					getTempMBAttachmentFileEntryId());

			Matcher matcher = pattern.matcher(content);

			content = matcher.replaceAll(
				_getMBAttachmentFileEntryBBCodeImgTag(
					mbAttachmentFileEntryReference.getMBAttachmentFileEntry()));
		}

		return content;
	}

	private String _getMBAttachmentFileEntryBBCodeImgTag(
		FileEntry mbAttachmentFileEntry) {

		String fileEntryURL = _portletFileRepository.getPortletFileEntryURL(
			null, mbAttachmentFileEntry, StringPool.BLANK);

		return "[img]" + fileEntryURL + "[/img]";
	}

	private Pattern _getTempImagePattern(long tempFileId) {
		return Pattern.compile(
			StringBundler.concat(
				"\\[img[^\\]]*?", EditorConstants.ATTRIBUTE_DATA_IMAGE_ID,
				"=\"", tempFileId, "\"[^\\]]*\\][^\\[]+\\[/img\\]"));
	}

	@Reference
	private PortletFileRepository _portletFileRepository;

}