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

package com.liferay.portal.fabric.netty.fileserver;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class FileRequestTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		_fileRequest = new FileRequest(
			_path, _LAST_MODIFIED_TIME, _DELETE_AFTER_FETCH);
	}

	@Test
	public void testConstructor() {
		try {
			new FileRequest(null, System.currentTimeMillis(), true);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		Assert.assertEquals(_path, _fileRequest.getPath());
		Assert.assertEquals(
			_LAST_MODIFIED_TIME, _fileRequest.getLastModifiedTime());
		Assert.assertEquals(
			_DELETE_AFTER_FETCH, _fileRequest.isDeleteAfterFetch());
	}

	@Test
	public void testEquals() {
		Assert.assertTrue(_fileRequest.equals(_fileRequest));
		Assert.assertFalse(_fileRequest.equals(new Object()));
		Assert.assertFalse(
			_fileRequest.equals(
				new FileRequest(
					Paths.get("unknown"), _LAST_MODIFIED_TIME,
					_DELETE_AFTER_FETCH)));
		Assert.assertFalse(
			_fileRequest.equals(
				new FileRequest(
					_path, _LAST_MODIFIED_TIME + 1, _DELETE_AFTER_FETCH)));
		Assert.assertFalse(
			_fileRequest.equals(
				new FileRequest(
					_path, _LAST_MODIFIED_TIME, !_DELETE_AFTER_FETCH)));
		Assert.assertTrue(
			_fileRequest.equals(
				new FileRequest(
					_path, _LAST_MODIFIED_TIME, _DELETE_AFTER_FETCH)));
	}

	@Test
	public void testHashCode() {
		int hash = HashUtil.hash(0, _DELETE_AFTER_FETCH);

		hash = HashUtil.hash(hash, _LAST_MODIFIED_TIME);

		Assert.assertEquals(
			HashUtil.hash(hash, _path), _fileRequest.hashCode());
	}

	@Test
	public void testToString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{deleteAfterFetch=");
		sb.append(_DELETE_AFTER_FETCH);
		sb.append(", lastModifiedTime=");
		sb.append(_LAST_MODIFIED_TIME);
		sb.append(", pathHolder=");
		sb.append(_path);
		sb.append("}");

		Assert.assertEquals(sb.toString(), _fileRequest.toString());
	}

	private static final boolean _DELETE_AFTER_FETCH = true;

	private static final long _LAST_MODIFIED_TIME = System.currentTimeMillis();

	private FileRequest _fileRequest;
	private final Path _path = Paths.get("testPath");

}