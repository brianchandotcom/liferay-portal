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

package com.liferay.html.parser.internal;

import com.liferay.portal.kernel.util.HtmlParser;

import net.htmlparser.jericho.Source;

import org.osgi.service.component.annotations.Component;

/**
 * Provides the implementation of the HTML utility interface for extracting and
 * rendering HTML text.
 *
 * @author Brian Wing Shun Chan
 * @author Clarence Shen
 * @author Harry Mark
 * @author Samuel Kong
 * @author Connor McKay
 * @author Shuyang Zhou
 */
@Component(immediate = true, service = HtmlParser.class)
public class HtmlParserImpl implements HtmlParser {

	/**
	 * Extracts the raw text from the HTML input, compressing its whitespace and
	 * removing all attributes, scripts, and styles.
	 *
	 * <p>
	 * For example, raw text returned by this method can be stored in a search
	 * index.
	 * </p>
	 *
	 * @param  html the HTML text
	 * @return the raw text from the HTML input, or <code>null</code> if the
	 *         HTML input is <code>null</code>
	 */
	@Override
	public String extractText(String html) {
		if (html == null) {
			return null;
		}

		Source source = new Source(html);

		return String.valueOf(source.getTextExtractor());
	}

	/**
	 * Renders the HTML content into text. This provides a human readable
	 * version of the content that is modeled on the way Mozilla
	 * Thunderbird&reg; and other email clients provide an automatic conversion
	 * of HTML content to text in their alternative MIME encoding of emails.
	 *
	 * <p>
	 * Using the default settings, the output complies with the
	 * <code>Text/Plain; Format=Flowed (DelSp=No)</code> protocol described in
	 * <a href="http://tools.ietf.org/html/rfc3676">RFC-3676</a>.
	 * </p>
	 *
	 * @param  html the HTML text
	 * @return the rendered HTML text, or <code>null</code> if the HTML text is
	 *         <code>null</code>
	 */
	@Override
	public String render(String html) {
		if (html == null) {
			return null;
		}

		Source source = new Source(html);

		return String.valueOf(source.getRenderer());
	}

}