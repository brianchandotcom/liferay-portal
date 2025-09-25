/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		_evilFileTargetDir = _tempDir.resolve(
			"../../../../../../../../../../../../../../../../../../../../.." +
				"/../../../../../../../../../../../../../../../../../../.." +
					"/tmp/evil.txt");
	}

	@After
	public void tearDown() throws Exception {
		PathUtil.deleteDir(_evilFileTargetDir);
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

		Assert.assertFalse(Files.exists(_evilFileTargetDir));
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

	private Path _evilFileTargetDir;
	private Path _tempDir;

}