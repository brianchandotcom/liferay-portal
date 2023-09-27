/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.markdown.converter.internal.pegdown.processor;

import com.liferay.knowledge.base.markdown.converter.internal.pegdown.serializer.LiferayToHtmlSerializer;

import org.pegdown.LinkRenderer;
import org.pegdown.Parser;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

/**
 * Converts Markdown to HTML using a {@link LiferayToHtmlSerializer}.
 *
 * @author James Hinkey
 */
public class LiferayPegDownProcessor extends PegDownProcessor {

	public LiferayPegDownProcessor(Parser parser) {
		super(parser);
	}

	@Override
	public String markdownToHtml(
		char[] markdownSource, LinkRenderer linkRenderer) {

		RootNode rootNode = parseMarkdown(markdownSource);

		LiferayToHtmlSerializer liferayToHtmlSerializer =
			new LiferayToHtmlSerializer(linkRenderer);

		return liferayToHtmlSerializer.toHtml(rootNode);
	}

}