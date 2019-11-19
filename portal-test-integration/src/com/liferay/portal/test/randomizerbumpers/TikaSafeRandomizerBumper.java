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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.io.DummyWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.RandomizerBumper;
import com.liferay.portal.util.FileImpl;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.WriteOutContentHandler;

/**
 * @author Matthew Tambara
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public class TikaSafeRandomizerBumper implements RandomizerBumper<byte[]> {

	public static final TikaSafeRandomizerBumper INSTANCE =
		new TikaSafeRandomizerBumper(null);

	public TikaSafeRandomizerBumper(String contentType) {
		_contentType = contentType;
	}

	@Override
	public boolean accept(byte[] randomValue) {
		try {
			ParseContext parseContext = new ParseContext();

			Parser parser = new AutoDetectParser(_tikaConfig);

			parseContext.set(Parser.class, parser);

			Metadata metadata = new Metadata();

			parser.parse(
				new UnsyncByteArrayInputStream(randomValue),
				new WriteOutContentHandler(new DummyWriter()), metadata,
				parseContext);

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

	protected static String byteArrayToString(byte[] byteArray) {
		StringBundler sb = new StringBundler((byteArray.length * 3) + 1);

		sb.append(StringPool.OPEN_CURLY_BRACE);

		for (byte b : byteArray) {
			sb.append("(byte)");
			sb.append(b);
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		sb.setIndex(sb.index() - 1);

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TikaSafeRandomizerBumper.class);

	private static final TikaConfig _tikaConfig;

	static {
		ClassLoader classLoader = FileImpl.class.getClassLoader();

		try {
			_tikaConfig = ReflectionTestUtil.getFieldValue(
				classLoader.loadClass(
					FileImpl.class.getName() + "$TikaConfigHolder"),
				"_tikaConfig");
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private final String _contentType;

}