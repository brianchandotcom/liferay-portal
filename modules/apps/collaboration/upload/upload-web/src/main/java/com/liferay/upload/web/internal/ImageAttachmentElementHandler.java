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

package com.liferay.upload.web.internal;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.upload.AttachmentElementHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 * @author Jürgen Kappler
 */
@Component(service = AttachmentElementHandler.class)
public class ImageAttachmentElementHandler implements AttachmentElementHandler {

	@Override
	public String getElementTag(FileEntry fileEntry) {
		String fileEntryURL = PortletFileRepositoryUtil.getPortletFileEntryURL(
			null, fileEntry, StringPool.BLANK);

		return "<img src=\"" + fileEntryURL + "\" />";
	}

	@Override
	public String replaceContentElements(
			String content,
			UnsafeFunction<FileEntry, FileEntry, PortalException> saveFile)
		throws PortalException {

		for (FileEntry tempAttachmentFileEntry :
				_getAttachmentFileEntries(content)) {

			FileEntry attachmentFileEntry = saveFile.apply(
				tempAttachmentFileEntry);

			StringBundler sb = new StringBundler(8);

			sb.append("<\\s*?img");
			sb.append(_ATTRIBUTE_LIST_REGEXP);
			sb.append(EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);
			sb.append("\\s*?=\\s*?\"");
			sb.append(tempAttachmentFileEntry.getFileEntryId());
			sb.append("\"");
			sb.append(_ATTRIBUTE_LIST_REGEXP);
			sb.append("/>");

			content = content.replaceAll(
				sb.toString(), getElementTag(attachmentFileEntry));
		}

		return content;
	}

	private List<FileEntry> _getAttachmentFileEntries(String content)
		throws PortalException {

		List<FileEntry> tempAttachmentFileEntries = new ArrayList<>();

		Matcher matcher = _ATTRIBUTE_DATA_IMAGE_ID_PATTERN.matcher(content);

		while (matcher.find()) {
			long fileEntryId = GetterUtil.getLong(matcher.group(1));

			FileEntry tempFileEntry =
				PortletFileRepositoryUtil.getPortletFileEntry(fileEntryId);

			tempAttachmentFileEntries.add(tempFileEntry);
		}

		return tempAttachmentFileEntries;
	}

	private static final Pattern _ATTRIBUTE_DATA_IMAGE_ID_PATTERN =
		Pattern.compile(EditorConstants.ATTRIBUTE_DATA_IMAGE_ID + "=.(\\d+)");

	private static final String _ATTRIBUTE_LIST_REGEXP =
		"(\\s*?\\w+\\s*?=\\s*?\"[^\"]*\")*?\\s*?";

}