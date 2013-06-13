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

package com.liferay.portal.util;

import com.liferay.portal.kernel.util.StringPool;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Brian Wing Shun Chan
 */
public class FileImplTest {

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

	private FileImpl _fileImpl = new FileImpl();

}