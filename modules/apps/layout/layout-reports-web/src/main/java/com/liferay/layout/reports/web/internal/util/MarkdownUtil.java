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

package com.liferay.layout.reports.web.internal.util;

import com.liferay.portal.kernel.util.Validator;

import org.commonmark.node.Link;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

/**
 * @author Alejandro Tardín
 */
public class MarkdownUtil {

	public static String markdownToHtml(String markdown) {
		if (Validator.isNull(markdown)) {
			return null;
		}

		return _htmlRenderer.render(_markdownParser.parse(markdown));
	}

	private static final HtmlRenderer _htmlRenderer = HtmlRenderer.builder(
	).attributeProviderFactory(
		context -> (node, tagName, attributes) -> {
			if (node instanceof Link) {
				attributes.put("target", "_blank");
			}
		}
	).build();
	private static final Parser _markdownParser = Parser.builder(
	).build();

}