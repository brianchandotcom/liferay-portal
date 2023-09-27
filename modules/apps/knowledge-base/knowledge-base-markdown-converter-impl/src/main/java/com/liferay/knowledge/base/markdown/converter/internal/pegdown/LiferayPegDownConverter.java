/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.markdown.converter.internal.pegdown;

import com.liferay.knowledge.base.markdown.converter.MarkdownConverter;
import com.liferay.knowledge.base.markdown.converter.internal.pegdown.processor.LiferayPegDownProcessor;

import org.parboiled.Parboiled;

import org.pegdown.Extensions;
import org.pegdown.LiferayParser;
import org.pegdown.LinkRenderer;

/**
 * @author James Hinkey
 */
public class LiferayPegDownConverter implements MarkdownConverter {

	public LiferayPegDownConverter() {
		LiferayParser liferayParser = Parboiled.createParser(
			LiferayParser.class, Extensions.ALL & ~Extensions.HARDWRAPS);

		_liferayPegDownProcessor = new LiferayPegDownProcessor(liferayParser);
	}

	@Override
	public String convert(String markdown) {
		return _liferayPegDownProcessor.markdownToHtml(
			markdown, new LinkRenderer());
	}

	private final LiferayPegDownProcessor _liferayPegDownProcessor;

}