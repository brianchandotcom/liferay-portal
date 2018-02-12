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

package com.liferay.util.taglib.compat;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;
import java.io.FileReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Preston Crary
 */
public class UtilTaglibCompatTest {

	@Test
	public void testNoConflictingImportedFiles() throws Exception {
		File importedFilesPropertiesFile = new File(
			"imported-files.properties");

		Assert.assertTrue(importedFilesPropertiesFile.exists());

		File srcDir = new File("../../../util-taglib/src/");

		srcDir = srcDir.getCanonicalFile();

		Assert.assertTrue(srcDir + "is not util-taglib/src", srcDir.exists());

		try (FileReader fileReader = new FileReader(
				importedFilesPropertiesFile);
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(fileReader)) {

			String line = null;

			while ((line = unsyncBufferedReader.readLine()) != null) {
				if (line.indexOf('=') >= 0) {
					Matcher matcher = _dependencyPattern.matcher(line);

					Assert.assertTrue(matcher.matches());
				}
				else if (Validator.isNotNull(line)) {
					line = line.trim();

					if (line.endsWith(",\\")) {
						line = line.substring(0, line.length() - 2);
					}

					if (line.endsWith(".class")) {
						line = line.substring(0, line.length() - 6) + ".java";
					}

					File file = new File(srcDir, line);

					Assert.assertFalse(
						file.getPath() + " should not exist", file.exists());
				}
			}
		}
	}

	private static final Pattern _dependencyPattern = Pattern.compile(
		"com.liferay.portal\\\\:com.liferay.util.taglib\\\\:" +
			"\\d+\\.\\d+\\.\\d+=\\\\");

}