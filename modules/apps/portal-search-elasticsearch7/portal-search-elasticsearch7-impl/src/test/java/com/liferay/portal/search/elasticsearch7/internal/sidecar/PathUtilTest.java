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

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.net.URISyntaxException;
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
 * @author Adam Brandizzi
 */
public class PathUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws IOException {
		_toPath = Files.createTempDirectory("temp_dir");
	}

	@After
	public void tearDown() {
		PathUtil.deleteDir(_toPath);
	}

	@Test
	public void testCopyDirectory() throws Exception {
		Path fromPath = _getResourcePath("root");

		Path excludedPaths = _getResourcePath("root/excluded");

		PathUtil.copyDirectory(fromPath, _toPath, excludedPaths);

		_assertExists(_toPath, "directory1/file1.txt");
		_assertExists(_toPath, "directory2/file2.txt");
		_assertDoesNotExist(_toPath, "excluded/excluded.txt");
	}

	private void _assertDoesNotExist(Path path, String name) {
		Path fullPath = path.resolve(name);

		Assert.assertFalse(Files.exists(fullPath));
	}

	private void _assertExists(Path path, String name) {
		Path fullPath = path.resolve(name);

		Assert.assertTrue(Files.exists(fullPath));
	}

	private Path _getResourcePath(String name) throws URISyntaxException {
		Class<? extends PathUtilTest> clazz = getClass();

		URL url = clazz.getResource(name);

		return Paths.get(url.toURI());
	}

	private Path _toPath;

}