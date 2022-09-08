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
import com.liferay.portal.kernel.editor.constants.EditorConstants;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "format=html", service = MBMessageFormatUploadHandler.class
)
public class MBMessageHTMLFormatUploadHandler
	implements MBMessageFormatUploadHandler {

	@Override
	public String replaceImageReferences(
		String content,
		List<MBAttachmentFileEntryReference> mbAttachmentFileEntryReferences) {

		for (MBAttachmentFileEntryReference mbAttachmentFileEntryReference :
				mbAttachmentFileEntryReferences) {

			content = content.replaceAll(
				StringBundler.concat(
					"<\\s*?img", _ATTRIBUTE_LIST_REGEXP,
					EditorConstants.ATTRIBUTE_DATA_IMAGE_ID, "\\s*?=\\s*?\"",
					mbAttachmentFileEntryReference.
						getTempMBAttachmentFileEntryId(),
					"\"", _ATTRIBUTE_LIST_REGEXP, "/>"),
				_getMBAttachmentFileEntryHTMLImgTag(
					mbAttachmentFileEntryReference.getMBAttachmentFileEntry()));
		}

		return content;
	}

	private String _getMBAttachmentFileEntryHTMLImgTag(
		FileEntry mbAttachmentFileEntry) {

		String fileEntryURL = _portletFileRepository.getPortletFileEntryURL(
			null, mbAttachmentFileEntry, StringPool.BLANK);

		return "<img src=\"" + fileEntryURL + "\" />";
	}

	private static final String _ATTRIBUTE_LIST_REGEXP =
		"(\\s*?\\w+\\s*?=\\s*?\"[^\"]*\")*?\\s*?";

	@Reference
	private PortletFileRepository _portletFileRepository;

}