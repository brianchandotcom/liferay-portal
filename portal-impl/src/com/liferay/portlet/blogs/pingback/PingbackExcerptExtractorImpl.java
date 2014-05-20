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

	public PingbackExcerptExtractorImpl(int length) {
		_length = length;
	}

	@Override
	public String getExcerpt() throws UnavailableSourceURIException {
		Source source = getSource();

		source.fullSequentialParse();

		List<Element> elements = source.getAllElements("a");

		for (Element element : elements) {
			String href = GetterUtil.getString(
				element.getAttributeValue("href"));

			if (href.equals(_targetUri)) {
				element = element.getParentElement();

				TextExtractor textExtractor = new TextExtractor(element);

				String body = textExtractor.toString();

				if (body.length() < _length) {
					element = element.getParentElement();

					if (element != null) {
						textExtractor = new TextExtractor(element);

						body = textExtractor.toString();
					}
				}

				return StringUtil.shorten(body, _length);
			}
		}

		return StringPool.BLANK;
	}

	@Override
	public void setSourceUri(String sourceUri) {
		_sourceUri = sourceUri;
	}

	@Override
	public void setTargetUri(String targetUri) {
		_targetUri = targetUri;
	}

	@Override
	public void validateSource()
		throws InvalidSourceURIException, UnavailableSourceURIException {

		Source source = getSource();

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

	protected Source getSource() throws UnavailableSourceURIException {
		try {
			String html = HttpUtil.URLtoString(_sourceUri);

			return new Source(html);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			throw new UnavailableSourceURIException(
				"Error accessing source URI", e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		PingbackExcerptExtractorImpl.class);

	private int _length;
	private String _sourceUri;
	private String _targetUri;

}