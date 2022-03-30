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

package com.liferay.portal.search.elasticsearch7.internal.sidecar;

import com.liferay.portal.kernel.util.OSDetector;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Wade Cao
 * @author Joshua Cords
 */
public class UncompressUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws IOException {
		_tempDir = Files.createTempDirectory(null);

		_badFileTargetDir = _tempDir.resolve(
			"../../../../../../../../../../../../../../../../../../../../.." +
				"/../../../../../../../../../../../../../../../../../../.." +
					"/tmp/evil.txt");
	}

	@After
	public void tearDown() throws Exception {
		PathUtil.deleteDir(_badFileTargetDir);
		PathUtil.deleteDir(_tempDir);
	}

	@Test
	public void testUnarchive() throws Exception {
		Assert.assertEquals(
			"tar/",
			UncompressUtil.unarchive(_getResourcePath("test.tgz"), _tempDir));

		_assertExists("tar/test/entry/entry.txt");
		_assertExists("tar/test/directory");

		Assert.assertTrue(
			_canExecuteReadWrite(_tempDir.resolve("tar/test/directory")));
		Assert.assertTrue(
			_canExecuteReadWrite(_tempDir.resolve("tar/test/entry/entry.txt")));
	}

	@Test
	public void testUnzip() throws Exception {
		UncompressUtil.unzip(_getResourcePath("test.zip"), _tempDir);

		_assertExists("zip/test/directory");
		_assertExists("zip/test/entry/entry.txt");

		Assert.assertTrue(
			_canExecuteReadWrite(_tempDir.resolve("zip/test/directory")));
		Assert.assertTrue(
			_canExecuteReadWrite(_tempDir.resolve("zip/test/entry/entry.txt")));
	}

	@Test
	public void testUnzipZipSlipVulnerable() throws Exception {
		UncompressUtil.unzip(_getResourcePath("test_slip.zip"), _tempDir);

		_assertExists("good.txt");

		Assert.assertFalse(Files.exists(_badFileTargetDir));
	}

	private void _assertExists(String name) {
		Path path = _tempDir.resolve(name);

		Assert.assertTrue(Files.exists(path));
	}

	private boolean _canExecuteReadWrite(Path path) {
		if (OSDetector.isWindows()) {
			File file = path.toFile();

			if (file.canExecute() && file.canRead() && file.canWrite()) {
				return true;
			}

			return false;
		}

		if (Files.isExecutable(path) && Files.isReadable(path) &&
			Files.isWritable(path)) {

			return true;
		}

		return false;
	}

	private Path _getResourcePath(String fileName) throws Exception {
		Class<? extends UncompressUtilTest> clazz = getClass();

		URL url = clazz.getResource("root");

		Path path = Paths.get(url.toURI());

		return path.resolve(fileName);
	}

	private Path _badFileTargetDir;
	private Path _tempDir;

}