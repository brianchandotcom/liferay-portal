/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.markdown.converter.internal;

import com.liferay.knowledge.base.markdown.converter.MarkdownConverter;

import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Adolfo Pérez
 */
public class FlexmarkMarkdownConverter implements MarkdownConverter {

	public FlexmarkMarkdownConverter() {
		MutableDataSet htmlRendererMutableDataSet = new MutableDataSet();

		htmlRendererMutableDataSet.set(HtmlRenderer.RENDER_HEADER_ID, true);

		_htmlRenderer = HtmlRenderer.builder(
			htmlRendererMutableDataSet
		).build();

		MutableDataSet parserMutableDataSet = new MutableDataSet();

		parserMutableDataSet.set(
			Parser.EXTENSIONS,
			(Collection)Arrays.asList(AttributesExtension.create()));

		_parser = Parser.builder(
			parserMutableDataSet
		).build();
	}

	@Override
	public String convert(String markdown) throws IOException {
		return _htmlRenderer.render(
			_parser.parse(_replaceHeaderIdWithAttribute(markdown)));
	}

	private String _replaceHeaderIdWithAttribute(String markdown) {
		Matcher matcher = _pattern.matcher(markdown);

		return matcher.replaceAll("$1 {#$2}");
	}

	private static final Pattern _pattern = Pattern.compile(
		"(?m)^(.*?)\\[\\]\\(id=([^\\s]+?)\\)\\s*$");

	private final HtmlRenderer _htmlRenderer;
	private final Parser _parser;

}