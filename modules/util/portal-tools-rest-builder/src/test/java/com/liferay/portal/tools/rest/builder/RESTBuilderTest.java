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

package com.liferay.portal.tools.rest.builder;

import java.io.File;

import java.net.URL;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sarai Díaz
 */
public class RESTBuilderTest {

	@Test
	public void testCreateRESTBuilder() throws Exception {
		String dependenciesPath = _getResourcesPath() + "dependencies/";

		RESTBuilder restBuilder = new RESTBuilder(
			new File(dependenciesPath, "copyright.txt"),
			new File(dependenciesPath), null, null);

		restBuilder.build();

		String filesPath = _getFilesPath();

		File outputDirectory = new File(_getResourcesPath() + "output/");

		for (File outputSubdirectory : outputDirectory.listFiles()) {
			String actualPath = filesPath + "/" + outputSubdirectory.getName();

			try {
				_assertEquals(new File(actualPath), outputSubdirectory);
			}
			finally {
				_cleanUp(actualPath);
			}
		}
	}

	private void _assertEquals(File actualDirectory, File expectedDirectory)
		throws Exception {

		List<File> actualFiles = new ArrayList<>(
			FileUtils.listFiles(actualDirectory, null, true));

		List<File> expectedFiles = new ArrayList<>(
			FileUtils.listFiles(expectedDirectory, null, true));

		Assert.assertEquals(
			actualFiles.toString(), expectedFiles.size(), actualFiles.size());

		for (int i = 0; i < expectedFiles.size(); i++) {
			File actualFile = actualFiles.get(i);
			File expectedFile = expectedFiles.get(i);

			Assert.assertTrue(
				expectedFile.toString() + " is not equal to " +
					actualFile.toString(),
				FileUtils.contentEquals(expectedFile, actualFile));
		}
	}

	private void _cleanUp(String path) throws Exception {
		File sampleImplDir = new File(path);

		FileUtils.deleteDirectory(sampleImplDir);

		Assert.assertFalse(sampleImplDir.exists());
	}

	private String _getFilesPath() {
		Path path = Paths.get("");

		Path absolutePath = path.toAbsolutePath();

		Path parentPath = absolutePath.getParent();

		return parentPath.toString();
	}

	private String _getResourcesPath() {
		URL resource = getClass().getResource("");

		return resource.getPath();
	}

}