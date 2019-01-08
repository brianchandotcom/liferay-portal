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

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.io.UnsupportedEncodingException;

import java.net.URLEncoder;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;

import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 * @author Brian Wing Shun Chan
 */
public class URLCodecTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testConstructor() {
		new URLCodec();
	}

	@Test
	public void testDecodeURL() {
		for (int i = 0; i < _RAW_URLS.length; i++) {
			Assert.assertEquals(
				_RAW_URLS[i], URLCodec.decodeURL(_ENCODED_URLS[i]));

			Assert.assertEquals(
				_RAW_URLS[i],
				URLCodec.decodeURL(_ENCODED_URLS[i], StringPool.UTF8));

			Assert.assertEquals(
				_RAW_URLS[i],
				URLCodec.decodeURL(_ESCAPE_SPACES_ENCODED_URLS[i]));

			Assert.assertEquals(
				_RAW_URLS[i],
				URLCodec.decodeURL(
					_ESCAPE_SPACES_ENCODED_URLS[i], StringPool.UTF8));
		}

		_testCharacterCodingException(
			charsetName -> URLCodec.decodeURL("%00", charsetName));

		// LPS-47334

		_testDecodeURL("%", false);
		_testDecodeURL("%0", false);
		_testDecodeURL("%00%", false);
		_testDecodeURL("%00%0", false);
		_testDecodeURL("%0" + (char)(CharPool.NUMBER_0 - 1), true);
		_testDecodeURL("%0" + (char)(CharPool.NUMBER_9 + 1), true);
		_testDecodeURL("%0" + (char)(CharPool.UPPER_CASE_A - 1), true);
		_testDecodeURL("%0" + (char)(CharPool.UPPER_CASE_F + 1), true);
		_testDecodeURL("%0" + (char)(CharPool.LOWER_CASE_A - 1), true);
		_testDecodeURL("%0" + (char)(CharPool.LOWER_CASE_F + 1), true);

		// LPS-62628

		_testDecodeURL("http://localhost:8080/?id=%'", false);
	}

	@Test
	public void testEncodeURL() {
		for (int i = 0; i < _RAW_URLS.length; i++) {
			Assert.assertEquals(
				_ENCODED_URLS[i], URLCodec.encodeURL(_RAW_URLS[i]));

			Assert.assertEquals(
				_ENCODED_URLS[i], URLCodec.encodeURL(_RAW_URLS[i], false));

			Assert.assertEquals(
				_ENCODED_URLS[i],
				URLCodec.encodeURL(_RAW_URLS[i], StringPool.UTF8, false));

			Assert.assertEquals(
				_ESCAPE_SPACES_ENCODED_URLS[i],
				URLCodec.encodeURL(_RAW_URLS[i], true));

			Assert.assertEquals(
				_ESCAPE_SPACES_ENCODED_URLS[i],
				URLCodec.encodeURL(_RAW_URLS[i], StringPool.UTF8, true));
		}

		_testCharacterCodingException(
			charsetName -> URLCodec.encodeURL("!", charsetName, false));
	}

	@Test
	public void testHandlingFourBytesUTFWithSurrogates() {
		StringBundler sb = new StringBundler(
			_UNICODE_CATS_AND_DOGS.length * 4 * 2);

		int[] animalsInts = new int[_UNICODE_CATS_AND_DOGS.length];

		for (int i = 0; i < animalsInts.length; i++) {
			animalsInts[i] = Integer.valueOf(_UNICODE_CATS_AND_DOGS[i], 16);
		}

		String animalsString = new String(animalsInts, 0, animalsInts.length);

		for (byte animalsByte :
				animalsString.getBytes(StandardCharsets.UTF_8)) {

			sb.append(StringPool.PERCENT);
			sb.append(Integer.toHexString(0xFF & animalsByte));
		}

		String escapedAnimalsString = sb.toString();

		Assert.assertEquals(
			animalsString,
			URLCodec.decodeURL(escapedAnimalsString, StringPool.UTF8));
		Assert.assertEquals(
			StringUtil.toUpperCase(escapedAnimalsString),
			URLCodec.encodeURL(animalsString, StringPool.UTF8, false));

		// Character missing low surrogate

		Assert.assertEquals(
			"%3F", URLCodec.encodeURL(animalsString.substring(0, 1)));
		Assert.assertEquals(
			"%3Fx", URLCodec.encodeURL(animalsString.substring(0, 1) + "x"));
	}

	private void _testCharacterCodingException(
		Function<String, String> codecFunction) {

		Object oldCache1 = ReflectionTestUtil.getAndSetFieldValue(
			Charset.class, "cache1",
			new Object[] {_testCharset.name(), _testCharset});

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					URLCodec.class.getName(), Level.ALL)) {

			Assert.assertEquals(
				"URLCodec returns blank string when ChaesetEncoder/Decoder" +
					"throws CharacterCodingException during encoding/decoding",
				StringPool.BLANK, codecFunction.apply("test-charset"));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"java.nio.charset.UnmappableCharacterException: Input length " +
					"= 1",
				logRecord.getMessage());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				Charset.class, "cache1", oldCache1);
		}
	}

	private void _testDecodeURL(
		String encodedURLString, boolean invalidHexChar) {

		try {
			URLCodec.decodeURL(encodedURLString, StringPool.UTF8);

			Assert.fail(encodedURLString);
		}
		catch (IllegalArgumentException iae) {
			String message = iae.getMessage();

			if (invalidHexChar) {
				Assert.assertTrue(
					encodedURLString, message.endsWith(" is not a hex char"));
			}
			else {
				Assert.assertEquals(
					"Invalid URL encoding " + encodedURLString, message);
			}
		}
	}

	private static final String[] _ENCODED_URLS;

	private static final String[] _ESCAPE_SPACES_ENCODED_URLS;

	private static final String[] _RAW_URLS = {
		null, StringPool.BLANK, "abcdefghijklmnopqrstuvwxyz",
		"ABCDEFGHIJKLMNOPQRSTUVWXYZ", "0123456789", ".-*_", " ",
		"~`!@#$%^&()+={[}]|\\:;\"'<,>?/", "中文测试", "/abc/def", "abc <def> ghi"
	};

	private static final String[] _UNICODE_CATS_AND_DOGS = {
		"1f408", "1f431", "1f415", "1f436"
	};

	private static final Charset _testCharset = new Charset(
		"test-charset", null) {

		@Override
		public boolean contains(Charset charset) {
			return true;
		}

		@Override
		public CharsetDecoder newDecoder() {
			return new CharsetDecoder(this, 1, 1) {

				@Override
				protected CoderResult decodeLoop(
					ByteBuffer byteBuffer, CharBuffer charBuffer) {

					return CoderResult.unmappableForLength(1);
				}

				@Override
				protected void implOnUnmappableCharacter(
					CodingErrorAction codingErrorAction) {

					ReflectionTestUtil.setFieldValue(
						this, "unmappableCharacterAction",
						CodingErrorAction.REPORT);
				}

			};
		}

		@Override
		public CharsetEncoder newEncoder() {
			return new CharsetEncoder(this, 1, 1) {

				@Override
				public boolean isLegalReplacement(byte[] replacement) {
					return true;
				}

				@Override
				protected CoderResult encodeLoop(
					CharBuffer charBuffer, ByteBuffer byteBuffer) {

					return CoderResult.unmappableForLength(1);
				}

				@Override
				protected void implOnUnmappableCharacter(
					CodingErrorAction codingErrorAction) {

					ReflectionTestUtil.setFieldValue(
						this, "unmappableCharacterAction",
						CodingErrorAction.REPORT);
				}

			};
		}

	};

	static {
		_ENCODED_URLS = new String[_RAW_URLS.length];
		_ESCAPE_SPACES_ENCODED_URLS = new String[_RAW_URLS.length];

		try {
			for (int i = 1; i < _RAW_URLS.length; i++) {
				_ENCODED_URLS[i] = URLEncoder.encode(
					_RAW_URLS[i], StringPool.UTF8);

				_ESCAPE_SPACES_ENCODED_URLS[i] = StringUtil.replace(
					_ENCODED_URLS[i], CharPool.PLUS, "%20");
			}
		}
		catch (UnsupportedEncodingException uee) {
			throw new ExceptionInInitializerError(uee);
		}
	}

}