/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.markdown.converter.internal;

import com.liferay.knowledge.base.markdown.converter.MarkdownConverter;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

import java.io.IOException;

/**
 * @author Adolfo Pérez
 */
public class FlexmarkMarkdownConverter implements MarkdownConverter {

	public FlexmarkMarkdownConverter() {
		_htmlRenderer = HtmlRenderer.builder(
		).build();
		_parser = Parser.builder(
		).build();
	}

	@Override
	public String convert(String markdown) throws IOException {
		return _htmlRenderer.render(_parser.parse(markdown));
	}

	private final HtmlRenderer _htmlRenderer;
	private final Parser _parser;

}