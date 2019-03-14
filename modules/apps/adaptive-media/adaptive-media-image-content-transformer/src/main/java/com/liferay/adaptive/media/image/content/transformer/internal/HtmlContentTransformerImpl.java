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

import com.liferay.adaptive.media.content.transformer.BaseRegexStringContentTransformer;
import com.liferay.adaptive.media.content.transformer.ContentTransformer;
import com.liferay.adaptive.media.content.transformer.ContentTransformerContentType;
import com.liferay.adaptive.media.content.transformer.constants.ContentTransformerContentTypes;
import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.adaptive.media.image.html.constants.AMImageHTMLConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

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
public class HtmlContentTransformerImpl
	extends BaseRegexStringContentTransformer {

	@Override
	public ContentTransformerContentType<String>
		getContentTransformerContentType() {

		return ContentTransformerContentTypes.HTML;
	}

	@Override
	public String transform(String html) throws PortalException {
		if (html == null) {
			return null;
		}

		if (!html.contains(AMImageHTMLConstants.ATTRIBUTE_NAME_FILE_ENTRY_ID)) {
			return html;
		}

		return super.transform(html);
	}

	@Override
	protected FileEntry getFileEntry(Matcher matcher) throws PortalException {
		long fileEntryId = Long.valueOf(matcher.group(1));

		return _dlAppLocalService.getFileEntry(fileEntryId);
	}

	@Override
	protected Pattern getPattern() {
		return _pattern;
	}

	@Override
	protected String getReplacement(String originalImgTag, FileEntry fileEntry)
		throws PortalException {

		return _amImageHTMLTagFactory.create(originalImgTag, fileEntry);
	}

	private static final Pattern _pattern = Pattern.compile(
		"<img [^>]*?\\s*" + AMImageHTMLConstants.ATTRIBUTE_NAME_FILE_ENTRY_ID +
			"=\"(\\d+)\".*?/>",
		Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	@Reference
	private AMImageHTMLTagFactory _amImageHTMLTagFactory;

	@Reference
	private DLAppLocalService _dlAppLocalService;

}