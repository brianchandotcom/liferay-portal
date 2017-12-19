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

package com.liferay.css.builder.internal.util;

import com.liferay.css.builder.util.FileTestUtil;

import java.io.File;
import java.io.IOException;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtilTest {

	@Test
	public void testGetFilesFromDirectory() throws IOException {
		File baseDir = temporaryFolder.newFolder("tmp");

		FileTestUtil.createFiles(
			baseDir, ".sass-cache/_general.scss", ".sass-cache/general.scss",
			".sass-cache_/_general.scss", ".sass-cache_/general.scss",
			".sass-cache_1/_general.scss", ".sass-cache_1/general.scss",
			"/_diffs/foo.scss", "/_diffs/foo/_bar.scss",
			"/foo/bar/.sass-cache/_general.scss",
			"/foo/bar/.sass-cache/general.scss",
			"/foo/bar/.sass-cache_/_general.scss",
			"/foo/bar/.sass-cache_/general.scss",
			"/foo/bar/.sass-cache_1/_general.scss",
			"/foo/bar/.sass-cache_1/general.scss",
			"/foo/bar/_sass-cache/_general.scss",
			"/foo/bar/_sass-cache/general.scss", "/foo/tmp/_foo.scss",
			"_general.scss", "_sass-cache/_general.scss",
			"_sass-cache/general.scss", "_toolbar.scss", "foo.scss",
			"tmp/_foo.scss");

		String[] fileNames = FileUtil.getFilesFromDirectory(
			baseDir.toString(), new String[] {"**/_*.scss"}, _EXCLUDES);

		if (File.separatorChar != '/') {
			for (int i = 0; i < fileNames.length; i++) {
				String fileName = fileNames[i];

				fileNames[i] = fileName.replace(File.separatorChar, '/');
			}
		}

		Arrays.sort(fileNames);

		Assert.assertArrayEquals(
			new String[] {
				"_general.scss", "_sass-cache/_general.scss", "_toolbar.scss",
				"foo/bar/_sass-cache/_general.scss"
			},
			fileNames);
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static final String[] _EXCLUDES = {
		"**/_diffs/**", "**/.sass-cache*/**", "**/.sass_cache_*/**",
		"**/_sass_cache_*/**", "**/_styled/**", "**/_unstyled/**",
		"**/css/aui/**", "**/tmp/**"
	};

}