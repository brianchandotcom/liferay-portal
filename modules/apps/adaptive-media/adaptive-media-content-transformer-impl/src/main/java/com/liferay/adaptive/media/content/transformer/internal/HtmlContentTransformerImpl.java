/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.content.transformer.internal;

import com.liferay.adaptive.media.content.transformer.BaseRegexStringContentTransformer;
import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.adaptive.media.image.html.constants.AMImageHTMLConstants;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alejandro Tardín
 */
public class HtmlContentTransformerImpl
	extends BaseRegexStringContentTransformer {

	public HtmlContentTransformerImpl(
		AMImageHTMLTagFactory amImageHTMLTagFactory,
		AMImageMimeTypeProvider amImageMimeTypeProvider,
		DLAppLocalService dlAppLocalService) {

		_amImageHTMLTagFactory = amImageHTMLTagFactory;
		_amImageMimeTypeProvider = amImageMimeTypeProvider;
		_dlAppLocalService = dlAppLocalService;
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
		long fileEntryId = GetterUtil.getLong(matcher.group(1));

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

	@Override
	protected boolean isSupported(FileEntry fileEntry) {
		return _amImageMimeTypeProvider.isMimeTypeSupported(
			fileEntry.getMimeType());
	}

	private static final Pattern _pattern = Pattern.compile(
		"<img [^>]*?\\s*" + AMImageHTMLConstants.ATTRIBUTE_NAME_FILE_ENTRY_ID +
			"=\"(\\d+)\".*?/?>",
		Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

	private final AMImageHTMLTagFactory _amImageHTMLTagFactory;
	private final AMImageMimeTypeProvider _amImageMimeTypeProvider;
	private final DLAppLocalService _dlAppLocalService;

}