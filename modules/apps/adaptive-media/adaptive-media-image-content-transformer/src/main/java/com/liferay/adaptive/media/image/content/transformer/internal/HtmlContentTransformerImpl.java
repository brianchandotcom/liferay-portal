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

package com.liferay.adaptive.media.image.content.transformer.internal;

import com.liferay.adaptive.media.content.transformer.ContentTransformer;
import com.liferay.adaptive.media.content.transformer.ContentTransformerContentType;
import com.liferay.adaptive.media.content.transformer.constants.ContentTransformerContentTypes;
import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true, property = "content.transformer.content.type=html",
	service = ContentTransformer.class
)
public class HtmlContentTransformerImpl implements ContentTransformer<String> {

	@Override
	public ContentTransformerContentType<String> getContentType() {
		return ContentTransformerContentTypes.HTML;
	}

	@Override
	public String transform(String html) throws PortalException {
		if (html == null) {
			return null;
		}

		String lowerCaseHtml = StringUtil.toLowerCase(html);

		if (!lowerCaseHtml.contains("data-fileentryid")) {
			return html;
		}

		StringBuffer sb = new StringBuffer(html.length());

		Matcher matcher = _IMG_PATTERN.matcher(html);

		while (matcher.find()) {
			Long fileEntryId = Long.valueOf(matcher.group(1));

			FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

			String imgTag = matcher.group(0);

			String adaptiveTag = _amImageHTMLTagFactory.create(
				imgTag, fileEntry);

			matcher.appendReplacement(
				sb, Matcher.quoteReplacement(adaptiveTag));
		}

		matcher.appendTail(sb);

		return sb.toString();
	}

	@Reference(unbind = "-")
	protected void setAMImageHTMLTagFactory(
		AMImageHTMLTagFactory amImageHTMLTagFactory) {

		_amImageHTMLTagFactory = amImageHTMLTagFactory;
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	private static final Pattern _IMG_PATTERN = Pattern.compile(
		"<img [^>]*?\\s*data-fileEntryId=\"(\\d+)\".*?/>",
		Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private AMImageHTMLTagFactory _amImageHTMLTagFactory;
	private DLAppLocalService _dlAppLocalService;

}