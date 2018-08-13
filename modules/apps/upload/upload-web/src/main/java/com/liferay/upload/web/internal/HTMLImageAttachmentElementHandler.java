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
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.editor.EditorConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.upload.AttachmentElementHandler;
import com.liferay.upload.AttachmentElementReplacer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Alejandro Tardín
 * @author Jürgen Kappler
 */
@Component(
	property = {"format=html", "html.tag.name=img"},
	service = AttachmentElementHandler.class
)
public class HTMLImageAttachmentElementHandler
	implements AttachmentElementHandler {

	@Override
	public String replaceAttachmentElements(
			String content,
			UnsafeFunction<FileEntry, FileEntry, PortalException>
				saveTempFileUnsafeFunction)
		throws PortalException {

		Matcher matcher = _tempAttachmentPattern.matcher(content);

		StringBuffer sb = new StringBuffer(content.length());

		while (matcher.find()) {
			FileEntry tempAttachmentFileEntry = _getFileEntry(matcher);

			FileEntry attachmentFileEntry = saveTempFileUnsafeFunction.apply(
				tempAttachmentFileEntry);

			matcher.appendReplacement(
				sb,
				Matcher.quoteReplacement(
					_attachmentElementReplacer.replace(
						matcher.group(0), attachmentFileEntry)));
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private FileEntry _getFileEntry(Matcher matcher) throws PortalException {
		long fileEntryId = GetterUtil.getLong(matcher.group(1));

		return PortletFileRepositoryUtil.getPortletFileEntry(fileEntryId);
	}

	private static final String _ATTRIBUTE_LIST_REGEXP =
		"(?:\\s*?\\w+\\s*?=\\s*?\"[^\"]*\")*?\\s*?";

	private static final Pattern _tempAttachmentPattern;

	static {
		StringBundler sb = new StringBundler(8);

		sb.append("<\\s*?img");
		sb.append(_ATTRIBUTE_LIST_REGEXP);
		sb.append(EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);
		sb.append("\\s*?=\\s*?\"");
		sb.append("([^\"]*)");
		sb.append("\"");
		sb.append(_ATTRIBUTE_LIST_REGEXP);
		sb.append("/>");

		_tempAttachmentPattern = Pattern.compile(sb.toString());
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(&(format=html)(html.tag.name=img))"
	)
	private volatile AttachmentElementReplacer _attachmentElementReplacer;

}