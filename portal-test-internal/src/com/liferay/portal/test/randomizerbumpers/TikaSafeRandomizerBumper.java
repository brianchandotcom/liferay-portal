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

package com.liferay.portal.test.randomizerbumpers;

import com.liferay.portal.kernel.io.DummyWriter;
import com.liferay.portal.kernel.test.randomizerbumpers.RandomizerBumper;
import com.liferay.portal.kernel.util.ContentTypes;

import java.io.ByteArrayInputStream;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.WriteOutContentHandler;

/**
 * @author Matthew Tambara
 */
public class TikaSafeRandomizerBumper implements RandomizerBumper<byte[]> {

	public static TikaSafeRandomizerBumper TEXT_PLAIN_INSTANCE =
		new TikaSafeRandomizerBumper(ContentTypes.TEXT_PLAIN);

	public TikaSafeRandomizerBumper(String contentType) {
		_contentType = contentType;
	}

	@Override
	public boolean accept(byte[] randomValue) {
		Metadata metadata = new Metadata();

		try {
			Parser parser = new AutoDetectParser(new TikaConfig());

			ParseContext parserContext = new ParseContext();

			parserContext.set(Parser.class, parser);

			parser.parse(
				new ByteArrayInputStream(randomValue),
				new WriteOutContentHandler(new DummyWriter()), metadata,
				parserContext);

			String contentType = metadata.get("Content-Type");

			return contentType.contains(_contentType);
		}
		catch (Exception e) {
			return false;
		}
	}

	private final String _contentType;

}