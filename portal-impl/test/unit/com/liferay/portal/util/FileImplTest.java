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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringPool;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 * @author Roberto Díaz
 */
public class FileImplTest {

	@Test
	public void testAppend() {
		Assert.assertEquals("test_rtl", _fileImpl.append("test", "_rtl"));

		Assert.assertEquals(
			"test_rtl.css", _fileImpl.append("test.css", "_rtl"));

		Assert.assertEquals(
			"/folder/test_rtl.css",
			_fileImpl.append("/folder/test.css", "_rtl"));
	}

	@Test
	public void testAppendSuffixWhenFileNameHasParenthesis() {
		String fileName = _fileImpl.appendParentheticalSuffix(
			"test(1).jsp", "1");

		Assert.assertEquals("test(1) (1).jsp", fileName);
	}

	@Test
	public void testAppendSuffixWithMultipleCharacterValue() {
		String fileName = _fileImpl.appendParentheticalSuffix(
			"test.jsp", "1!$eae1");

		Assert.assertEquals("test (1!$eae1).jsp", fileName);
	}

	@Test
	public void testAppendSuffixWithMultipleNumericalValue() {
		String fileName = _fileImpl.appendParentheticalSuffix(
			"test.jsp", "1111111");

		Assert.assertEquals("test (1111111).jsp", fileName);
	}

	@Test
	public void testAppendSuffixWithMultipleStringValue() {
		String fileName = _fileImpl.appendParentheticalSuffix(
			"test.jsp", "AAAAAAA");

		Assert.assertEquals("test (AAAAAAA).jsp", fileName);
	}

	@Test
	public void testAppendSuffixWithMultipleStringWithSpaceValue() {
		String fileName = _fileImpl.appendParentheticalSuffix(
			"test.jsp", "A B");

		Assert.assertEquals("test (A B).jsp", fileName);
	}

	@Test
	public void testAppendSuffixWithSingleNumericalValue() {
		String fileName = _fileImpl.appendParentheticalSuffix("test.jsp", "1");

		Assert.assertEquals("test (1).jsp", fileName);
	}

	@Test
	public void testAppendSuffixWithSingleStringValue() {
		String fileName = _fileImpl.appendParentheticalSuffix("test.jsp", "A");

		Assert.assertEquals("test (A).jsp", fileName);
	}

	@Test
	public void testGetPathBackSlashForwardSlash() {
		Assert.assertEquals(
			"aaa\\bbb/ccc\\ddd",
			_fileImpl.getPath("aaa\\bbb/ccc\\ddd/eee.fff"));
	}

	@Test
	public void testGetPathForwardSlashBackSlash() {
		Assert.assertEquals(
			"aaa/bbb\\ccc/ddd", _fileImpl.getPath("aaa/bbb\\ccc/ddd\\eee.fff"));
	}

	@Test
	public void testGetPathNoPath() {
		Assert.assertEquals(StringPool.SLASH, _fileImpl.getPath("aaa.bbb"));
	}

	@Test
	public void testGetShortFileNameBackSlashForwardSlash() {
		Assert.assertEquals(
			"eee.fff", _fileImpl.getShortFileName("aaa\\bbb/ccc\\ddd/eee.fff"));
	}

	@Test
	public void testGetShortFileNameForwardSlashBackSlash() {
		Assert.assertEquals(
			"eee.fff", _fileImpl.getShortFileName("aaa/bbb\\ccc/ddd\\eee.fff"));
	}

	@Test
	public void testGetShortFileNameNoPath() {
		Assert.assertEquals("aaa.bbb", _fileImpl.getShortFileName("aaa.bbb"));
	}

	@Test
	public void testStripSuffixAppendedWhenFileNameHasParenthesis() {
		String fileName = _fileImpl.appendParentheticalSuffix(
			"test(1).jsp", "1");

		Assert.assertEquals(
			"test(1).jsp", _fileImpl.stripParentheticalSuffix(fileName));
	}

	@Test
	public void testStripSuffixAppendedWithMultipleCharacterValue() {
		String fileName = _fileImpl.appendParentheticalSuffix(
			"test.jsp", "1!$eae1");

		Assert.assertEquals(
			"test.jsp", _fileImpl.stripParentheticalSuffix(fileName));
	}

	@Test
	public void testStripSuffixAppendedWithMultipleNumericalValue() {
		String fileName2 = _fileImpl.appendParentheticalSuffix(
			"test.jsp", "1111111");

		Assert.assertEquals(
			"test.jsp", _fileImpl.stripParentheticalSuffix(fileName2));
	}

	@Test
	public void testStripSuffixAppendedWithMultipleStringValue() {
		String fileName = _fileImpl.appendParentheticalSuffix(
			"test.jsp", "AAAAAAA");

		Assert.assertEquals(
			"test.jsp", _fileImpl.stripParentheticalSuffix(fileName));
	}

	@Test
	public void testStripSuffixAppendedWithMultipleStringWithSpaceValue() {
		String fileName = _fileImpl.appendParentheticalSuffix(
			"test.jsp", "A B");

		Assert.assertEquals(
			"test.jsp", _fileImpl.stripParentheticalSuffix(fileName));
	}

	@Test
	public void testStripSuffixAppendedWithSingleNumericalValue() {
		String fileName = _fileImpl.appendParentheticalSuffix("test.jsp", "1");

		Assert.assertEquals(
			"test.jsp", _fileImpl.stripParentheticalSuffix(fileName));
	}

	@Test
	public void testStripSuffixAppendedWithSingleStringValue() {
		String fileName = _fileImpl.appendParentheticalSuffix("test.jsp", "A");

		Assert.assertEquals(
			"test.jsp", _fileImpl.stripParentheticalSuffix(fileName));
	}

	@Test
	public void testStripSuffixWhenFileNameHasInvertedParenthesis() {
		Assert.assertEquals(
			"test)1(.jsp", _fileImpl.stripParentheticalSuffix("test)1(.jsp"));
	}

	@Test
	public void testStripSuffixWhenFileNameHasNoCloseParenthesis() {
		Assert.assertEquals(
			"test(1.jsp", _fileImpl.stripParentheticalSuffix("test(1.jsp"));
	}

	@Test
	public void testStripSuffixWhenFileNameHasNoParentheticalSuffix() {
		Assert.assertEquals(
			"test.jsp", _fileImpl.stripParentheticalSuffix("test.jsp"));
	}

	@Test
	public void testStripSuffixWhenFileNameHasParenthesisAtStart() {
		Assert.assertEquals(
			"()test.jsp", _fileImpl.stripParentheticalSuffix("()test.jsp"));
	}

	private final FileImpl _fileImpl = new FileImpl();

}