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

package com.liferay.knowledge.base.internal.importer.util;

import com.liferay.knowledge.base.exception.KBArticleImportException;
import com.liferay.knowledge.base.markdown.converter.MarkdownConverter;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipReader;

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Sergio González
 * @author Rich Sezov
 */
public class KBArticleMarkdownConverter {

	public KBArticleMarkdownConverter(
			String markdown, String fileEntryName, Map<String, String> metadata)
		throws KBArticleImportException {

		_markdownConverter = MarkdownConverterFactoryUtil.create();

		_html = null;

		try {
			_markdownConverter.parse(markdown);
		}
		catch (IOException ioe) {
			throw new KBArticleImportException(
				"Unable to convert Markdown to HTML: " +
					ioe.getLocalizedMessage(),
				ioe);
		}

		String heading = getHeading(markdown);

		String urlTitle = _markdownConverter.getURLTitle();

		_urlTitle = formatURLTitle(urlTitle);

		if (Validator.isNull(_urlTitle)) {
			throw new KBArticleImportException(
				"Missing title heading ID in file: " + fileEntryName);
		}

		_title = HtmlUtil.unescape(heading);

		String baseSourceURL = metadata.get(_METADATA_BASE_SOURCE_URL);

		_sourceURL = buildSourceURL(baseSourceURL, fileEntryName);

		_imageNames = _markdownConverter.getImageNames();
	}

	public KBArticleMarkdownConverter(
			String markdown, String fileEntryName, Map<String, String> metadata,
			KBArticle article, ZipReader zip, long userId)
		throws KBArticleImportException {

		_markdownConverter = MarkdownConverterFactoryUtil.create();

		//_html = null;

		try {
			_html = _markdownConverter.convert(markdown, article, zip, userId);
		}
		catch (IOException ioe) {
			throw new KBArticleImportException(
				"Unable to convert Markdown to HTML: " +
					ioe.getLocalizedMessage(),
				ioe);
		}

		String heading = getHeading(markdown);

		String urlTitle = _markdownConverter.getURLTitle();

		_urlTitle = formatURLTitle(urlTitle);

		if (Validator.isNull(_urlTitle)) {
			throw new KBArticleImportException(
				"Missing title heading ID in file: " + fileEntryName);
		}

		_title = HtmlUtil.unescape(heading);

		String baseSourceURL = metadata.get(_METADATA_BASE_SOURCE_URL);

		_sourceURL = buildSourceURL(baseSourceURL, fileEntryName);

		_imageNames = _markdownConverter.getImageNames();
	}

	public String getHtml(
		String markdown, KBArticle article, ZipReader zip, long userId) {

		try {
			_html = _markdownConverter.convert(markdown, article, zip, userId);
		}
		catch (IOException ioe) {
			_logger.log(Level.SEVERE, ioe.getMessage());
		}

		return _html;
	}

	public String getSourceURL() {
		return _sourceURL;
	}

	public String getTitle() {
		return _title;
	}

	public String getUrlTitle() {
		return _urlTitle;
	}

	protected String buildSourceURL(
		String baseSourceURL, String fileEntryName) {

		if (!Validator.isUrl(baseSourceURL)) {
			return null;
		}

		int pos = baseSourceURL.length() - 1;

		while (pos >= 0) {
			char c = baseSourceURL.charAt(pos);

			if (c != CharPool.SLASH) {
				break;
			}

			pos--;
		}

		StringBundler sb = new StringBundler(3);

		sb.append(baseSourceURL.substring(0, pos + 1));

		if (!fileEntryName.startsWith(StringPool.SLASH)) {
			sb.append(StringPool.SLASH);
		}

		sb.append(FileUtil.replaceSeparator(fileEntryName));

		return sb.toString();
	}

	protected String formatURLTitle(String urlTitle) {
		if (urlTitle == null) {
			return null;
		}

		if (!urlTitle.startsWith(StringPool.SLASH)) {
			urlTitle = StringPool.SLASH + urlTitle;
		}

		int urlTitleMaxLength = ModelHintsUtil.getMaxLength(
			KBArticle.class.getName(), "urlTitle");

		while (urlTitle.length() > urlTitleMaxLength) {
			int pos = urlTitle.lastIndexOf(StringPool.DASH);

			if (pos == -1) {
				urlTitle = urlTitle.substring(0, urlTitleMaxLength);
			}
			else {
				urlTitle = urlTitle.substring(0, pos);
			}
		}

		return urlTitle;
	}

	protected String getHeading(String html) {
		int x = html.indexOf("#");

		int y = html.indexOf(StringPool.NEW_LINE, x);

		String heading = html.substring(x + 1, y);

		return heading.trim();
	}

	private static final String _METADATA_BASE_SOURCE_URL = "base.source.url";

	private static Logger _logger;

	private String _html;
	private final List<String> _imageNames;
	private final MarkdownConverter _markdownConverter;
	private final String _sourceURL;
	private final String _title;
	private final String _urlTitle;

}