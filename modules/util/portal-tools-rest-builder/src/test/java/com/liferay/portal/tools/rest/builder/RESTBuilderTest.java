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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;

import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.stream.Stream;

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
			new File(dependenciesPath), null, null);

		restBuilder.build();

		String filesPath = _getFilesPath();

		File applicationFile = new File(
			filesPath + "/sample-impl/src/main/java/com/example/sample" +
				"/internal/jaxrs/application/SampleApplication.java");

		Assert.assertTrue(applicationFile.exists());

		_assertBooleanParameterName(filesPath);

		_assertResourceFilesExist(filesPath, "Document");
		_assertResourceFilesExist(filesPath, "Folder");
		_assertResourceFilesExist(filesPath, "Test");

		_assertDTOFile(filesPath, "UnreferencedSchemaComponent");

		_assertPropertiesWithHyphens(
			filesPath, "Test", "property-with-hyphens");
		_assertPropertiesWithXML(filesPath, "Test", "xmlProperty");

		_assertForcePredictableOperationId(filesPath);

		File sampleApiDir = new File(filesPath + "/sample-api");

		FileUtils.deleteDirectory(sampleApiDir);

		Assert.assertFalse(sampleApiDir.exists());

		File sampleImplDir = new File(filesPath + "/sample-impl");

		FileUtils.deleteDirectory(sampleImplDir);

		Assert.assertFalse(sampleImplDir.exists());
	}

	private void _assertBooleanParameterName(String filesPath)
		throws Exception {

		File queryJavaFile = new File(
			filesPath.concat(
				"/sample-impl/src/main/java/com/example/sample/internal" +
					"/resource/v1_0_0/BaseTestBooleanResourceImpl.java"));

		String text = new String(
			Files.readAllBytes(queryJavaFile.toPath()), StandardCharsets.UTF_8);

		Assert.assertTrue(text.contains("Boolean booleanValue"));
	}

	private void _assertDTOFile(String filesPath, String resourceName) {
		File dtoFolderFile = new File(
			_getResourcePath(
				filesPath,
				"/sample-api/src/main/java/com/example/sample/dto/v1_0_0/",
				resourceName, ".java"));

		Assert.assertTrue(dtoFolderFile.exists());
	}

	private void _assertForcePredictableOperationId(String filesPath)
		throws Exception {

		File queryJavaFile = new File(
			filesPath.concat(
				"/sample-impl/src/main/java/com/example/sample/internal" +
					"/graphql/query/v1_0_0/Query.java"));

		String text = new String(
			Files.readAllBytes(queryJavaFile.toPath()), StandardCharsets.UTF_8);

		Assert.assertFalse(text.contains("ForcePredictableOperationIdTest"));
	}

	private void _assertPropertiesWithHyphens(
			String filesPath, String resourceName, String propertyName)
		throws Exception {

		File dtoResourceFile = new File(
			_getResourcePath(
				filesPath,
				"/sample-api/src/main/java/com/example/sample/dto/v1_0_0/",
				resourceName, ".java"));

		List<String> lines = Files.readAllLines(dtoResourceFile.toPath());

		Stream<String> stream = lines.stream();

		Assert.assertTrue(
			stream.anyMatch(
				line -> line.contains(
					"access = JsonProperty.Access.READ_WRITE, value = \"" +
						propertyName + "\"")));

		stream = lines.stream();

		Assert.assertTrue(
			stream.anyMatch(
				line -> line.contains(
					"sb.append(\"\\\"" + propertyName + "\\\": \");")));
	}

	private void _assertPropertiesWithXML(
			String filesPath, String resourceName, String xmlPropertyName)
		throws Exception {

		File dtoResourceFile = new File(
			_getResourcePath(
				filesPath,
				"/sample-api/src/main/java/com/example/sample/dto/v1_0_0/",
				resourceName, ".java"));

		List<String> lines = Files.readAllLines(dtoResourceFile.toPath());

		Stream<String> stream = lines.stream();

		Assert.assertTrue(
			stream.anyMatch(
				line -> line.contains(
					"@XmlElement(name = \"" + xmlPropertyName + "\")")));
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

		File resourceFolderFile = new File(
			_getResourcePath(
				filesPath,
				"/sample-api/src/main/java/com/example/sample/resource/v1_0_0/",
				resourceName, "Resource.java"));

		Assert.assertTrue(resourceFolderFile.exists());

		_assertDTOFile(filesPath, resourceName);
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