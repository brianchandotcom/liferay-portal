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
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.RandomizerBumper;
import com.liferay.portal.kernel.util.StringBundler;

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

	public static final TikaSafeRandomizerBumper INSTANCE =
		new TikaSafeRandomizerBumper(null);

	public static String byteArrayToString(byte[] byteArray) {
		StringBundler sb = new StringBundler((byteArray.length * 3) + 2);

		sb.append("{");

		for (byte val : byteArray) {
			sb.append("(byte)");
			sb.append(val);
			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append("}");

		return sb.toString();
	}

	public TikaSafeRandomizerBumper(String contentType) {
		_contentType = contentType;
	}

	@Override
	public boolean accept(byte[] randomValue) {
		try {
			ParseContext parserContext = new ParseContext();

			Parser parser = new AutoDetectParser(new TikaConfig());

			parserContext.set(Parser.class, parser);

			Metadata metadata = new Metadata();

			parser.parse(
				new UnsyncByteArrayInputStream(randomValue),
				new WriteOutContentHandler(new DummyWriter()), metadata,
				parserContext);

			if (_contentType == null) {
				if (_log.isInfoEnabled()) {
					_log.info("Accepted: " + byteArrayToString(randomValue));
				}

				return true;
			}

			String contentType = metadata.get("Content-Type");

			if (contentType.contains(_contentType)) {
				if (_log.isInfoEnabled()) {
					_log.info("Accepted: " + byteArrayToString(randomValue));
				}

				return true;
			}

			return false;
		}
		catch (Throwable t) {
			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TikaSafeRandomizerBumper.class);

	private final String _contentType;

}