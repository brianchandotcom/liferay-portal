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

package com.liferay.markdown.converter.internal.pegdown;

import com.liferay.markdown.converter.MarkdownConverter;
import com.liferay.markdown.converter.internal.pegdown.processor.LiferayPegDownProcessor;

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