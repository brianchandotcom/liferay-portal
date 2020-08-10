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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.net.URL;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sarai Díaz
 */
public class RESTBuilderTest {

	@Test
	public void testCreateRESTBuilder() throws Exception {
		String dependenciesPath = _getDependenciesPath();

		RESTBuilder restBuilder = new RESTBuilder(
			new File(dependenciesPath, "copyright.txt"),
			new File(dependenciesPath), null);

		restBuilder.build();

		String filesPath = _getFilesPath();

		File applicationFile = new File(
			filesPath + "/sample-impl/src/main/java/com/example/sample" +
				"/internal/jaxrs/application/SampleApplication.java");

		Assert.assertTrue(applicationFile.exists());

		_assertResourceFilesExist(filesPath, "Document");
		_assertResourceFilesExist(filesPath, "Folder");

		File sampleApiDir = new File(filesPath + "/sample-api");

		FileUtils.deleteDirectory(sampleApiDir);

		Assert.assertFalse(sampleApiDir.exists());

		File sampleImplDir = new File(filesPath + "/sample-impl");

		FileUtils.deleteDirectory(sampleImplDir);

		Assert.assertFalse(sampleImplDir.exists());
	}

	private void _assertResourceFilesExist(
		String filesPath, String resourceName) {

		File baseResourceImplFile = new File(
			_getResourcePath(
				filesPath,
				"/sample-impl/src/main/java/com/example/sample/internal" +
					"/resource/v1_0_0/Base",
				resourceName, "ResourceImpl.java"));

		Assert.assertTrue(baseResourceImplFile.exists());

		File folderResourceImplFile = new File(
			_getResourcePath(
				filesPath,
				"/sample-impl/src/main/java/com/example/sample/internal" +
					"/resource/v1_0_0/",
				resourceName, "ResourceImpl.java"));

		Assert.assertTrue(folderResourceImplFile.exists());

		File propertiesFile = new File(
			_getResourcePath(
				filesPath,
				"/sample-impl/src/main/resources/OSGI-INF/liferay/rest/v1_0_0/",
				StringUtil.toLowerCase(resourceName), ".properties"));

		Assert.assertTrue(propertiesFile.exists());

		File dtoFolderFile = new File(
			_getResourcePath(
				filesPath,
				"/sample-api/src/main/java/com/example/sample/dto/v1_0_0/",
				resourceName, ".java"));

		Assert.assertTrue(dtoFolderFile.exists());

		File resourceFolderFile = new File(
			_getResourcePath(
				filesPath,
				"/sample-api/src/main/java/com/example/sample/resource/v1_0_0/",
				resourceName, "Resource.java"));

		Assert.assertTrue(resourceFolderFile.exists());
	}

	private String _getDependenciesPath() {
		URL resource = getClass().getResource("");

		String path = resource.getPath();

		return path + "dependencies/";
	}

	private String _getFilesPath() {
		Path path = Paths.get("");

		Path absolutePath = path.toAbsolutePath();

		Path parentPath = absolutePath.getParent();

		return parentPath.toString();
	}

	private String _getResourcePath(
		String filesPath, String resourcePathPrefix, String resourceName,
		String resourcePathSuffix) {

		return StringBundler.concat(
			filesPath, resourcePathPrefix, resourceName, resourcePathSuffix);
	}

}