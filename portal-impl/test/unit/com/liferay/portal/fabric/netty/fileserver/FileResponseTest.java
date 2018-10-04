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
public class FileResponseTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() {
		_fileResponse = new FileResponse(
			_path, _SIZE, _LAST_MODIFIED_TIME, _FOLDER);
	}

	@Test
	public void testConstructor() {
		try {
			new FileResponse(
				null, System.currentTimeMillis(), System.currentTimeMillis(),
				false);

			Assert.fail();
		}
		catch (NullPointerException npe) {
		}

		Assert.assertEquals(_path, _fileResponse.getPath());
		Assert.assertEquals(_SIZE, _fileResponse.getSize());
		Assert.assertEquals(
			_LAST_MODIFIED_TIME, _fileResponse.getLastModifiedTime());
		Assert.assertEquals(_FOLDER, _fileResponse.isFolder());
		Assert.assertNull(_fileResponse.getLocalFile());
		Assert.assertFalse(_fileResponse.isFileNotFound());
		Assert.assertFalse(_fileResponse.isFileNotModified());

		_fileResponse.setLocalFile(_path);

		Assert.assertSame(_path, _fileResponse.getLocalFile());

		FileResponse fileResponse = new FileResponse(
			_path, FileResponse.FILE_NOT_FOUND, _LAST_MODIFIED_TIME, _FOLDER);

		Assert.assertTrue(fileResponse.isFileNotFound());
		Assert.assertFalse(fileResponse.isFileNotModified());

		fileResponse = new FileResponse(
			_path, FileResponse.FILE_NOT_MODIFIED, _LAST_MODIFIED_TIME,
			_FOLDER);

		Assert.assertFalse(fileResponse.isFileNotFound());
		Assert.assertTrue(fileResponse.isFileNotModified());
	}

	@Test
	public void testEquals() {
		Assert.assertTrue(_fileResponse.equals(_fileResponse));
		Assert.assertFalse(_fileResponse.equals(new Object()));
		Assert.assertFalse(
			_fileResponse.equals(
				new FileResponse(
					Paths.get("unknown"), _SIZE, _LAST_MODIFIED_TIME,
					_FOLDER)));
		Assert.assertFalse(
			_fileResponse.equals(
				new FileResponse(
					_path, _SIZE + 1, _LAST_MODIFIED_TIME, _FOLDER)));
		Assert.assertFalse(
			_fileResponse.equals(
				new FileResponse(
					_path, _SIZE, _LAST_MODIFIED_TIME + 1, _FOLDER)));
		Assert.assertFalse(
			_fileResponse.equals(
				new FileResponse(_path, _SIZE, _LAST_MODIFIED_TIME, !_FOLDER)));
		Assert.assertTrue(
			_fileResponse.equals(
				new FileResponse(_path, _SIZE, _LAST_MODIFIED_TIME, _FOLDER)));
	}

	@Test
	public void testHashCode() {
		int hash = HashUtil.hash(0, _FOLDER);

		hash = HashUtil.hash(hash, _LAST_MODIFIED_TIME);
		hash = HashUtil.hash(hash, _path);

		Assert.assertEquals(
			HashUtil.hash(hash, _SIZE), _fileResponse.hashCode());
	}

	@Test
	public void testToString() {
		StringBundler sb = new StringBundler(10);

		sb.append("{folder=");
		sb.append(_FOLDER);
		sb.append(", lastModifiedTime=");
		sb.append(_LAST_MODIFIED_TIME);
		sb.append(", localFile=null");
		sb.append(", pathHolder=");
		sb.append(_path);
		sb.append(", size=");
		sb.append(_SIZE);
		sb.append("}");

		Assert.assertEquals(sb.toString(), _fileResponse.toString());

		Path localFilePath = Paths.get("localFile");

		_fileResponse.setLocalFile(localFilePath);

		sb.setStringAt(", localFile=" + localFilePath.toString(), 4);

		Assert.assertEquals(sb.toString(), _fileResponse.toString());

		FileResponse fileResponse = new FileResponse(
			_path, FileResponse.FILE_NOT_FOUND, _LAST_MODIFIED_TIME, _FOLDER);

		sb = new StringBundler(7);

		sb.append("{folder=");
		sb.append(_FOLDER);
		sb.append(", lastModifiedTime=");
		sb.append(_LAST_MODIFIED_TIME);
		sb.append(", localFile=null, pathHolder=");
		sb.append(_path);
		sb.append(", status=File Not Found}");

		Assert.assertEquals(sb.toString(), fileResponse.toString());

		fileResponse = new FileResponse(
			_path, FileResponse.FILE_NOT_MODIFIED, _LAST_MODIFIED_TIME,
			_FOLDER);

		sb = new StringBundler(7);

		sb.append("{folder=");
		sb.append(_FOLDER);
		sb.append(", lastModifiedTime=");
		sb.append(_LAST_MODIFIED_TIME);
		sb.append(", localFile=null, pathHolder=");
		sb.append(_path);
		sb.append(", status=File Not Modified}");

		Assert.assertEquals(sb.toString(), fileResponse.toString());
	}

	private static final boolean _FOLDER = false;

	private static final long _LAST_MODIFIED_TIME = System.currentTimeMillis();

	private static final long _SIZE = System.currentTimeMillis();

	private FileResponse _fileResponse;
	private final Path _path = Paths.get("testPath");

}