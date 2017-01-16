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

package com.liferay.message.boards.web.internal.format.handlers;

import com.liferay.message.boards.web.internal.format.MBMessageFormatHandler;
import com.liferay.message.boards.web.internal.util.MBAttachmentFileEntryReference;
import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = MBMessageFormatHandler.FORMAT_KEY + "=bbcode",
	service = MBMessageFormatHandler.class
)
public class MBMessageBBCodeFormatHandler implements MBMessageFormatHandler {

	@Override
	public String replaceImageReferences(
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

	private String _getMBAttachmentBBCodeImgTag(
		FileEntry mbAttachmentFileEntry) {

		String fileEntryURL = PortletFileRepositoryUtil.getPortletFileEntryURL(
			null, mbAttachmentFileEntry, StringPool.BLANK);

		return "[img]" + fileEntryURL + "[/img]";
	}

	private static final Pattern _BBCODE_IMG_TAG_REGEXP = Pattern.compile(
		"\\[img[^\\]]*?" + EditorConstants.ATTRIBUTE_DATA_IMAGE_ID +
			"=\"[^\"]*\"[^\\]]*\\][^\\[]+\\[/img\\]");

}