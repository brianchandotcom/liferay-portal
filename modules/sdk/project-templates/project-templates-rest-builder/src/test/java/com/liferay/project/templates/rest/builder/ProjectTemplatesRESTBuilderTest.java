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

package com.liferay.project.templates.rest.builder;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;

import java.net.URI;

import java.nio.file.Files;

import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Javier de Arcos
 */
public class ProjectTemplatesRESTBuilderTest
	implements BaseProjectTemplatesTestCase {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@BeforeClass
	public static void setUpClass() throws Exception {
		String gradleDistribution = System.getProperty("gradle.distribution");

		if (Validator.isNull(gradleDistribution)) {
			Properties properties = FileTestUtil.readProperties(
				"gradle-wrapper/gradle/wrapper/gradle-wrapper.properties");

			gradleDistribution = properties.getProperty("distributionUrl");
		}

		Assert.assertTrue(gradleDistribution.contains(GRADLE_WRAPPER_VERSION));

		_gradleDistribution = URI.create(gradleDistribution);
	}

	@Test
	public void testBuildTemplateRESTBuilderCheckExports() throws Exception {
		String liferayVersion = getDefaultLiferayVersion();
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";
		String template = "rest-builder";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", liferayVersion,
			mavenExecutor);

		File gradleWorkspaceModulesDir = new File(
			gradleWorkspaceDir, "modules");

		File gradleProjectDir = buildTemplateWithGradle(
			gradleWorkspaceModulesDir, template, name, "--package-name",
			packageName, "--liferay-version", liferayVersion);

		File gradleConfigYAML = new File(
			new File(gradleProjectDir, name + "-impl"), "rest-config.yaml");

		Assert.assertTrue(gradleConfigYAML.exists());

		File gradleOpenAPIYAML = new File(
			new File(gradleProjectDir, name + "-impl"), "rest-openapi.yaml");

		Assert.assertTrue(gradleOpenAPIYAML.exists());

		File mavenWorkspaceDir = buildWorkspace(
			temporaryFolder, "maven", "mavenWS", liferayVersion, mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

		File mavenProjectDir = buildTemplateWithMaven(
			mavenModulesDir, mavenModulesDir, template, name, "com.test",
			mavenExecutor, "-Dpackage=" + packageName,
			"-DliferayVersion=" + liferayVersion);

		File mavenConfigYAML = new File(
			new File(mavenProjectDir, name + "-impl"), "rest-config.yaml");

		Assert.assertTrue(mavenConfigYAML.exists());

		File mavenOpenAPIYAML = new File(
			new File(mavenProjectDir, name + "-impl"), "rest-openapi.yaml");

		Assert.assertTrue(mavenOpenAPIYAML.exists());

		Assert.assertArrayEquals(
			Files.readAllBytes(gradleConfigYAML.toPath()),
			Files.readAllBytes(mavenConfigYAML.toPath()));
		Assert.assertArrayEquals(
			Files.readAllBytes(gradleOpenAPIYAML.toPath()),
			Files.readAllBytes(mavenOpenAPIYAML.toPath()));

		testContains(
			gradleProjectDir, name + "-api/bnd.bnd", "Export-Package:\\",
			packageName + ".dto.v1_0,\\", packageName + ".resource.v1_0");
	}

	@Test
	public void testCompareRESTBuilderPluginVersions() throws Exception {
		String liferayVersion = getDefaultLiferayVersion();
		String name = "sample";
		String packageName = "com.test.sample";
		String template = "rest-builder";

		String implProjectName = name + "-impl";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", liferayVersion,
			mavenExecutor);

		File gradleWorkspaceModulesDir = new File(
			gradleWorkspaceDir, "modules");

		buildTemplateWithGradle(
			gradleWorkspaceModulesDir, template, name, "--package-name",
			packageName, "--liferay-version", liferayVersion);

		Optional<String> gradleResult = executeGradle(
			gradleWorkspaceDir, true, _gradleDistribution,
			":modules:" + name + ":" + implProjectName + ":dependencies");

		String gradleRESTBuilderVersion = null;

		Matcher matcher = _restBuilderVersionPattern.matcher(
			gradleResult.get());

		if (matcher.matches()) {
			gradleRESTBuilderVersion = matcher.group(1);
		}

		File mavenWorkspaceDir = buildWorkspace(
			temporaryFolder, "maven", "mavenWS", liferayVersion, mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

		File mavenProjectDir = buildTemplateWithMaven(
			mavenModulesDir, mavenModulesDir, template, name, "com.test",
			mavenExecutor, "-Dpackage=" + packageName);

		String mavenResult = executeMaven(
			new File(mavenProjectDir, implProjectName), mavenExecutor,
			MAVEN_GOAL_BUILD_REST);

		matcher = _restBuilderVersionPattern.matcher(mavenResult);

		String mavenRESTBuilderVersion = null;

		if (matcher.matches()) {
			mavenRESTBuilderVersion = matcher.group(1);
		}

		Assert.assertEquals(
			"com.liferay.portal.tools.rest.builder versions do not match",
			gradleRESTBuilderVersion, mavenRESTBuilderVersion);
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static URI _gradleDistribution;
	private static final Pattern _restBuilderVersionPattern = Pattern.compile(
		".*rest\\.builder:([0-9]+\\.[0-9]+\\.[0-9]+).*",
		Pattern.DOTALL | Pattern.MULTILINE);

}