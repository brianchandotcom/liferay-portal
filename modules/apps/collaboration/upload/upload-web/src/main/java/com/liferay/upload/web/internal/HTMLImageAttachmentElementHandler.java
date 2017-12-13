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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 * @author Jürgen Kappler
 */
@Component(service = AttachmentElementHandler.class)
public class HTMLImageAttachmentElementHandler
	implements AttachmentElementHandler {

	@Override
	public String getAttachmentElement(
		String originalImgHtmlElement, FileEntry fileEntry) {

		String fileEntryURL = PortletFileRepositoryUtil.getPortletFileEntryURL(
			null, fileEntry, StringPool.BLANK);

		Element image = _parseImgTag(originalImgHtmlElement);

		image.attr("src", fileEntryURL);
		image.removeAttr(EditorConstants.ATTRIBUTE_DATA_IMAGE_ID);

		return image.toString();
	}

	@Override
	public String replaceAttachmentElements(
			String content,
			UnsafeFunction<FileEntry, FileEntry, PortalException>
				saveTempFileUnsafeFunction)
		throws PortalException {

		Matcher matcher = _TEMP_ATTACHMENT_PATTERN.matcher(content);

		StringBuffer sb = new StringBuffer(content.length());

		while (matcher.find()) {
			FileEntry tempAttachmentFileEntry = _getFileEntry(matcher);

			FileEntry attachmentFileEntry = saveTempFileUnsafeFunction.apply(
				tempAttachmentFileEntry);

			matcher.appendReplacement(
				sb,
				Matcher.quoteReplacement(
					getAttachmentElement(
						matcher.group(0), attachmentFileEntry)));
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	private FileEntry _getFileEntry(Matcher matcher) throws PortalException {
		long fileEntryId = GetterUtil.getLong(matcher.group(1));

		return PortletFileRepositoryUtil.getPortletFileEntry(fileEntryId);
	}

	private Element _parseImgTag(String originalImgTag) {
		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);
		outputSettings.syntax(Document.OutputSettings.Syntax.xml);

		Document document = Jsoup.parseBodyFragment(originalImgTag);

		document.outputSettings(outputSettings);

		return document.body().child(0);
	}

	private static final String _ATTRIBUTE_LIST_REGEXP =
		"(?:\\s*?\\w+\\s*?=\\s*?\"[^\"]*\")*?\\s*?";

	private static final Pattern _TEMP_ATTACHMENT_PATTERN;

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

		_TEMP_ATTACHMENT_PATTERN = Pattern.compile(sb.toString());
	}

}