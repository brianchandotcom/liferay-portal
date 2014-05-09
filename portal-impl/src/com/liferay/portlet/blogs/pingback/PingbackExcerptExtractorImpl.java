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

package com.liferay.portlet.blogs.pingback;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;
import net.htmlparser.jericho.TextExtractor;

/**
 * @author Alexander Chow
 * @author André de Oliveira
 */
public class PingbackExcerptExtractorImpl implements PingbackExcerptExtractor {

	@Override
	public String getExcerpt() throws IOException {
		String html = HttpUtil.URLtoString(_sourceUri);

		Source source = new Source(html);

		source.fullSequentialParse();

		List<Element> elements = source.getAllElements("a");

		for (Element element : elements) {
			String href = GetterUtil.getString(
				element.getAttributeValue("href"));

			if (href.equals(_targetUri)) {
				element = element.getParentElement();

				TextExtractor textExtractor = new TextExtractor(element);

				String body = textExtractor.toString();

				if (body.length() < PropsValues.BLOGS_LINKBACK_EXCERPT_LENGTH) {
					element = element.getParentElement();

					if (element != null) {
						textExtractor = new TextExtractor(element);

						body = textExtractor.toString();
					}
				}

				return StringUtil.shorten(
					body, PropsValues.BLOGS_LINKBACK_EXCERPT_LENGTH);
			}
		}

		return StringPool.BLANK;
	}

	@Override
	public void setSourceUri(String sourceURI) {
		_sourceUri = sourceURI;
	}

	@Override
	public void setTargetUri(String targetURI) {
		_targetUri = targetURI;
	}

	@Override
	public void validateSource() {

		Source source = null;

		try {
			String html = HttpUtil.URLtoString(_sourceUri);

			source = new Source(html);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			throw new UnavailableSourceURIException(
				"Error accessing source URI", e);
		}

		List<StartTag> startTags = source.getAllStartTags("a");

		for (StartTag startTag : startTags) {
			String href = GetterUtil.getString(
				startTag.getAttributeValue("href"));

			if (href.equals(_targetUri)) {
				return;
			}
		}

		throw new InvalidSourceURIException(
			"Could not find target URI in source");
	}

	private static Log _log = LogFactoryUtil.getLog(
		PingbackExcerptExtractorImpl.class);

	private String _sourceUri;
	private String _targetUri;

}