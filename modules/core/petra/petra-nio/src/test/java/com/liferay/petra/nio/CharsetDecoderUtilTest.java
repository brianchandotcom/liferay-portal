/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.nio;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.MalformedInputException;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class CharsetDecoderUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, LiferayUnitTestRule.INSTANCE);

	@Test
	public void testConstructor() {
		new CharsetDecoderUtil();
	}

	@Test
	public void testDecode() throws Exception {
		Assert.assertEquals(
			CharBuffer.wrap("abc"),
			CharsetDecoderUtil.decode(
				"US-ASCII", CodingErrorAction.REPORT,
				ByteBuffer.wrap("abc".getBytes("US-ASCII"))));

		try {
			CharsetDecoderUtil.decode(
				"US-ASCII", CodingErrorAction.REPORT,
				ByteBuffer.wrap(new byte[] {-1, -2, -3, -4}));

			Assert.fail();
		}
		catch (MalformedInputException malformedInputException) {
			Assert.assertEquals(1, malformedInputException.getInputLength());
		}

		TestCharset testCharset = new TestCharset();

		Object cache1 = ReflectionTestUtil.getAndSetFieldValue(
			Charset.class, "cache1",
			new Object[] {testCharset.name(), testCharset});

		try {
			CharsetDecoderUtil.decode(
				testCharset.name(), ByteBuffer.wrap(new byte[0]));

			Assert.fail();
		}
		catch (Error e) {
			Assert.assertSame(
				testCharset.getCharacterCodingException(), e.getCause());
		}
		finally {
			ReflectionTestUtil.setFieldValue(Charset.class, "cache1", cache1);
		}
	}

	@Test
	public void testGetCharsetDecoder() {
		CharsetDecoder charsetDecoder = CharsetDecoderUtil.getCharsetDecoder(
			"UTF-8");

		Assert.assertEquals(Charset.forName("UTF-8"), charsetDecoder.charset());

		Assert.assertSame(
			CodingErrorAction.REPLACE, charsetDecoder.malformedInputAction());
		Assert.assertSame(
			CodingErrorAction.REPLACE,
			charsetDecoder.unmappableCharacterAction());
	}

}