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

package com.liferay.portal.kernel.io;

import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class ReaderInputStreamTest {

	@Test
	public void testAvailable() {
		Reader reader = new StringReader(_TEST_STRING_ENGLISH);

		try {
			ReaderInputStream readerInputStream = new ReaderInputStream(
				reader, StringPool.UTF8);

			int available = readerInputStream.available();

			Assert.assertEquals(0, available);

			readerInputStream.read();

			available = readerInputStream.available();

			Assert.assertEquals(_TEST_STRING_ENGLISH.length() - 1, available);
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof IllegalArgumentException);
		}
	}

	@Test
	public void testConstructor1() {
		Reader reader = new StringReader(_TEST_STRING_ENGLISH);

		try {
			new ReaderInputStream(reader, StringPool.UTF8, 0, 0);

			Assert.fail();
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof IllegalArgumentException);
		}
	}

	@Test
	public void testConstructor2() {
		Reader reader = new StringReader(_TEST_STRING_ENGLISH);

		try {
			new ReaderInputStream(reader, StringPool.UTF8, 1, 1);

			Assert.fail();
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof IllegalArgumentException);
		}
	}

	@Test
	public void testRead1() throws IOException {
		Reader reader = new StringReader(_TEST_STRING_ENGLISH);

		try (ReaderInputStream readerInputStream = new ReaderInputStream(
				reader, StringPool.UTF8)) {

			int result = readerInputStream.read();

			Assert.assertEquals(_EXPECTED_BYTES_FOR_ENGLISH[0], result);
		}
	}

	@Test
	public void testRead2() throws IOException {
		Reader reader = new StringReader(_TEST_STRING_ENGLISH);

		try (ReaderInputStream readerInputStream = new ReaderInputStream(
				reader, StringPool.UTF8)) {

			byte[] bytes = new byte[_EXPECTED_BYTES_FOR_ENGLISH.length];

			int result = readerInputStream.read(bytes);

			Assert.assertEquals(_EXPECTED_BYTES_FOR_ENGLISH.length, result);

			assertEquals(_EXPECTED_BYTES_FOR_ENGLISH, bytes);
		}
	}

	@Test
	public void testRead3() throws IOException {
		Reader reader = new StringReader(_TEST_STRING_CHINESE);

		try (ReaderInputStream readerInputStream = new ReaderInputStream(
				reader, StringPool.UTF8)) {

			byte[] bytes = new byte[_EXPECTED_BYTES_FOR_CHINESE.length];

			int result = readerInputStream.read(bytes);

			Assert.assertEquals(_EXPECTED_BYTES_FOR_CHINESE.length, result);

			assertEquals(_EXPECTED_BYTES_FOR_CHINESE, bytes);
		}
	}

	@Test
	public void testRead4() throws IOException {
		Reader reader = new StringReader(_TEST_STRING_CHINESE);

		try (ReaderInputStream readerInputStream = new ReaderInputStream(
				reader, StringPool.UTF8, 6, 6)) {

			byte[] bytes = new byte[_EXPECTED_BYTES_FOR_CHINESE.length];

			int result = readerInputStream.read(bytes);

			Assert.assertEquals(_EXPECTED_BYTES_FOR_CHINESE.length, result);

			assertEquals(_EXPECTED_BYTES_FOR_CHINESE, bytes);
		}
	}

	@Test
	public void testRead5() throws IOException {
		Reader reader = new StringReader(_TEST_STRING_CHINESE);

		try (ReaderInputStream readerInputStream = new ReaderInputStream(
				reader, StringPool.UTF8, 4, 6)) {

			byte[] bytes = new byte[_EXPECTED_BYTES_FOR_CHINESE.length];

			int result = readerInputStream.read(bytes);

			Assert.assertEquals(_EXPECTED_BYTES_FOR_CHINESE.length, result);

			assertEquals(_EXPECTED_BYTES_FOR_CHINESE, bytes);
		}
	}

	@Test
	public void testRead6() throws IOException {
		Reader reader = new StringReader(_TEST_STRING_CHINESE);

		try (ReaderInputStream readerInputStream = new ReaderInputStream(
				reader, StringPool.UTF8, 6, 4)) {

			byte[] bytes = new byte[_EXPECTED_BYTES_FOR_CHINESE.length];

			int result = readerInputStream.read(bytes);

			Assert.assertEquals(_EXPECTED_BYTES_FOR_CHINESE.length, result);

			assertEquals(_EXPECTED_BYTES_FOR_CHINESE, bytes);
		}
	}

	@Test
	public void testRead7() throws IOException {
		Reader reader = new StringReader(_TEST_STRING_ENGLISH);

		byte[] bytes = new byte[_EXPECTED_BYTES_FOR_ENGLISH.length + 1];

		try (ReaderInputStream readerInputStream = new ReaderInputStream(
				reader)) {

			int result = readerInputStream.read(bytes);

			Assert.assertEquals(_EXPECTED_BYTES_FOR_ENGLISH.length, result);
		}

		for (int i = 0; i < _EXPECTED_BYTES_FOR_ENGLISH.length; i++) {
			Assert.assertEquals(_EXPECTED_BYTES_FOR_ENGLISH[i], bytes[i]);
		}

		Assert.assertEquals(0, bytes[_EXPECTED_BYTES_FOR_ENGLISH.length]);
	}

	@Test
	public void testRead8() {
		Reader reader = new StringReader(_TEST_STRING_ENGLISH);

		try (ReaderInputStream readerInputStream = new ReaderInputStream(
				reader, StringPool.UTF8)) {

			readerInputStream.read(null, 0, 0);

			Assert.fail();
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof NullPointerException);
		}
	}

	@Test
	public void testRead9() {
		Reader reader = new StringReader(_TEST_STRING_ENGLISH);

		try (ReaderInputStream readerInputStream = new ReaderInputStream(
				reader, StringPool.UTF8)) {

			readerInputStream.read(new byte[1], 0, 2);

			Assert.fail();
		}
		catch (Exception e) {
			Assert.assertTrue(e instanceof IndexOutOfBoundsException);
		}
	}

	@Test
	public void testSkip1() throws IOException {
		Reader reader = new StringReader(_TEST_STRING_ENGLISH);

		int skipLength = 3;

		byte[] bytes =
			new byte[_EXPECTED_BYTES_FOR_ENGLISH.length - skipLength];

		try (ReaderInputStream readerInputStream = new ReaderInputStream(
				reader, StringPool.UTF8)) {

			readerInputStream.skip(skipLength);

			int result = readerInputStream.read(bytes);

			Assert.assertEquals(
				_EXPECTED_BYTES_FOR_ENGLISH.length - skipLength, result);
		}

		Assert.assertEquals(
			Arrays.toString(bytes),
			_EXPECTED_BYTES_FOR_ENGLISH.length - skipLength, bytes.length);

		for (int i = skipLength; i < _EXPECTED_BYTES_FOR_ENGLISH.length; i++) {
			Assert.assertEquals(
				_EXPECTED_BYTES_FOR_ENGLISH[i], bytes[i - skipLength]);
		}
	}

	@Test
	public void testSkip2() throws IOException {
		Reader reader = new StringReader(_TEST_STRING_ENGLISH);

		try (ReaderInputStream readerInputStream = new ReaderInputStream(
				reader, StringPool.UTF8)) {

			int skipLength = _EXPECTED_BYTES_FOR_ENGLISH.length;

			int result = (int)readerInputStream.skip(skipLength);

			Assert.assertEquals(skipLength, result);

			result = readerInputStream.read();

			Assert.assertEquals(-1, result);
		}
	}

	@Test
	public void testSkip3() throws IOException {
		Reader reader = new StringReader(_TEST_STRING_ENGLISH);

		try (ReaderInputStream readerInputStream = new ReaderInputStream(
				reader, StringPool.UTF8)) {

			int skipLength = _EXPECTED_BYTES_FOR_ENGLISH.length + 1;

			int result = (int)readerInputStream.skip(skipLength);

			Assert.assertEquals(_EXPECTED_BYTES_FOR_ENGLISH.length, result);
		}
	}

	protected void assertEquals(byte[] expectBytes, byte[] actualBytes) {
		Assert.assertEquals(
			Arrays.toString(actualBytes), expectBytes.length,
			actualBytes.length);

		for (int i = 0; i < expectBytes.length; i++) {
			Assert.assertEquals(expectBytes[i], actualBytes[i]);
		}
	}

	private static final byte[] _EXPECTED_BYTES_FOR_CHINESE;

	private static final byte[] _EXPECTED_BYTES_FOR_ENGLISH;

	private static final String _TEST_STRING_CHINESE = "这是一个测试字符串";

	private static final String _TEST_STRING_ENGLISH = "This is a test string";

	static {
		try {
			_EXPECTED_BYTES_FOR_CHINESE = _TEST_STRING_CHINESE.getBytes(
				StringPool.UTF8);
			_EXPECTED_BYTES_FOR_ENGLISH = _TEST_STRING_ENGLISH.getBytes(
				StringPool.UTF8);
		}
		catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

}