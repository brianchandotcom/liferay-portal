/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.io;

import com.liferay.portal.kernel.test.TestCase;
import com.liferay.portal.kernel.util.StringPool;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class WriterOutputStreamTest extends TestCase {

	@Test
	public void testACSIIOutput() throws IOException {

		// Auto flush

		Writer writer = new StringWriter();

		OutputStream outputStream = new WriterOutputStream(
			writer, StringPool.UTF8, true);

		String[] asciiOutput = {"This ", "is ", "an ", "ACSII ", " test"};
		byte[][] asciiInput = {
			asciiOutput[0].getBytes(StringPool.UTF8),
			asciiOutput[1].getBytes(StringPool.UTF8),
			asciiOutput[2].getBytes(StringPool.UTF8),
			asciiOutput[3].getBytes(StringPool.UTF8),
			asciiOutput[4].getBytes(StringPool.UTF8)};

		String expectedResult = asciiOutput[0];

		outputStream.write(asciiInput[0]);

		assertEquals(expectedResult, writer.toString());

		expectedResult += asciiOutput[1];

		outputStream.write(asciiInput[1]);

		assertEquals(expectedResult, writer.toString());

		expectedResult += asciiOutput[2];

		outputStream.write(asciiInput[2]);

		assertEquals(expectedResult, writer.toString());

		expectedResult += asciiOutput[3];

		outputStream.write(asciiInput[3]);

		assertEquals(expectedResult, writer.toString());

		expectedResult += asciiOutput[4];

		outputStream.write(asciiInput[4]);

		assertEquals(expectedResult, writer.toString());

		// Do not auto flush

		writer = new StringWriter();

		outputStream = new WriterOutputStream(writer, StringPool.UTF8, false);

		outputStream.write(asciiInput[0]);

		assertEquals("", writer.toString());

		outputStream.write(asciiInput[1]);

		assertEquals("", writer.toString());

		outputStream.write(asciiInput[2]);

		assertEquals("", writer.toString());

		outputStream.write(asciiInput[3]);

		assertEquals("", writer.toString());

		outputStream.write(asciiInput[4]);

		assertEquals("", writer.toString());

		outputStream.flush();

		assertEquals(expectedResult, writer.toString());
	}

	@Test
	public void testChineseOutput() throws IOException {

		// Auto flush

		Writer writer = new StringWriter();

		OutputStream outputStream = new WriterOutputStream(
			writer, StringPool.UTF8, true);

		String[] chineseOutput = {"这是", "一个", "中文", "解码 ", "测试"};
		byte[][] chineseInput = {
			chineseOutput[0].getBytes(StringPool.UTF8),
			chineseOutput[1].getBytes(StringPool.UTF8),
			chineseOutput[2].getBytes(StringPool.UTF8),
			chineseOutput[3].getBytes(StringPool.UTF8),
			chineseOutput[4].getBytes(StringPool.UTF8)};

		String expectedResult = chineseOutput[0];

		outputStream.write(chineseInput[0]);

		assertEquals(expectedResult, writer.toString());

		expectedResult += chineseOutput[1];

		outputStream.write(chineseInput[1]);

		assertEquals(expectedResult, writer.toString());

		expectedResult += chineseOutput[2];

		outputStream.write(chineseInput[2]);

		assertEquals(expectedResult, writer.toString());

		expectedResult += chineseOutput[3];

		outputStream.write(chineseInput[3]);

		assertEquals(expectedResult, writer.toString());

		expectedResult += chineseOutput[4];

		outputStream.write(chineseInput[4]);

		assertEquals(expectedResult, writer.toString());

		// Do not auto flush

		writer = new StringWriter();

		outputStream = new WriterOutputStream(writer, StringPool.UTF8, false);

		outputStream.write(chineseInput[0]);

		assertEquals("", writer.toString());

		outputStream.write(chineseInput[1]);

		assertEquals("", writer.toString());

		outputStream.write(chineseInput[2]);

		assertEquals("", writer.toString());

		outputStream.write(chineseInput[3]);

		assertEquals("", writer.toString());

		outputStream.write(chineseInput[4]);

		assertEquals("", writer.toString());

		outputStream.flush();

		assertEquals(expectedResult, writer.toString());
	}

	@Test
	public void testNonAlignOutput() throws IOException {
		CharArrayWriter charArrayWriter = new CharArrayWriter();

		WriterOutputStream writerOutputStream = new WriterOutputStream(
			charArrayWriter, StringPool.UTF8, true);

		int charNumber = 0;

		String nonAlignOutput = "非对齐测试中文输出";
		byte[] nonAlignInput = nonAlignOutput.getBytes(StringPool.UTF8);

		for (byte b : nonAlignInput) {
			writerOutputStream.write(b);

			int currentCharNumber = charArrayWriter.size();

			if (currentCharNumber > charNumber) {
				charNumber = currentCharNumber;

				assertEquals(
					nonAlignOutput.charAt(charNumber - 1),
					charArrayWriter.toCharArray()[charNumber - 1]);
			}
		}
	}

}