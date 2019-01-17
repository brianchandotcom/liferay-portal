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

package com.liferay.message.boards.web.internal.upload.format.handlers;

import com.liferay.message.boards.web.internal.upload.format.MBMessageFormatUploadHandler;
import com.liferay.message.boards.web.internal.util.MBAttachmentFileEntryReference;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.editor.EditorConstants;
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

	@Reference(unbind = "-")
	protected void setPortletFileRepository(
		PortletFileRepository portletFileRepository) {

		_portletFileRepository = portletFileRepository;
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

	private PortletFileRepository _portletFileRepository;

}