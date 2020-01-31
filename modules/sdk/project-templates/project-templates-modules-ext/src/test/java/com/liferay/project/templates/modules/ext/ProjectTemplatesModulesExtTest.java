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

package com.liferay.project.templates.modules.ext;

import aQute.bnd.header.Attrs;
import aQute.bnd.osgi.Domain;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.ProjectTemplatesUtil;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Gregory Amerson
 */
public class ProjectTemplatesModulesExtTest
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
	public void testBuildTemplateModuleExt() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"modules-ext", "loginExt", "--original-module-name",
			"com.liferay.login.web", "--original-module-version", "1.0.0");

		testContains(
			gradleProjectDir, "build.gradle", "buildscript {", "repositories {",
			"originalModule group: \"com.liferay\", name: " +
				"\"com.liferay.login.web\", version: \"1.0.0\"",
			"apply plugin: \"com.liferay.osgi.ext.plugin\"");

		if (isBuildProjects()) {
			executeGradle(
				gradleProjectDir, _gradleDistribution, GRADLE_TASK_PATH_BUILD);

			File jarFile = testExists(
				gradleProjectDir,
				"build/libs/com.liferay.login.web-1.0.0.ext.jar");

			Domain domain = Domain.domain(jarFile);

			Map.Entry<String, Attrs> bundleSymbolicName =
				domain.getBundleSymbolicName();

			Assert.assertEquals(
				bundleSymbolicName.toString(), "com.liferay.login.web",
				bundleSymbolicName.getKey());
		}
	}

	@Test
	public void testBuildTemplateModuleExtInWorkspace() throws Exception {
		File workspaceDir = buildWorkspace(temporaryFolder, "7.3.0");

		enableTargetPlatformInWorkspace(workspaceDir, "7.3.0");

		File workspaceProjectDir = buildTemplateWithGradle(
			new File(workspaceDir, "ext"), "modules-ext", "loginExt",
			"--original-module-name", "com.liferay.login.web",
			"--original-module-version", "1.0.0",
			"--dependency-management-enabled");

		testContains(
			workspaceProjectDir, "build.gradle",
			"originalModule group: \"com.liferay\", name: " +
				"\"com.liferay.login.web\"");
		testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");
		testNotContains(
			workspaceProjectDir, "build.gradle", "version: \"[0-9].*");

		if (isBuildProjects()) {
			executeGradle(
				workspaceDir, _gradleDistribution, ":ext:loginExt:build");

			testExists(
				workspaceProjectDir,
				"build/libs/com.liferay.login.web-4.0.20.ext.jar");
		}
	}

	@Test
	public void testBuildTemplateModulesExtGradle() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"modules-ext", "foo-ext", "--original-module-name",
			"com.liferay.login.web", "--original-module-version", "2.0.4");

		testContains(
			gradleProjectDir, "build.gradle",
			"originalModule group: \"com.liferay\", ",
			"name: \"com.liferay.login.web\", version: \"2.0.4\"");

		if (isBuildProjects()) {
			executeGradle(
				gradleProjectDir, _gradleDistribution, GRADLE_TASK_PATH_BUILD);

			File gradleOutputDir = new File(gradleProjectDir, "build/libs");

			Path gradleOutputPath = FileTestUtil.getFile(
				gradleOutputDir.toPath(), OUTPUT_FILENAME_GLOB_REGEX, 1);

			Assert.assertNotNull(gradleOutputPath);

			Assert.assertTrue(Files.exists(gradleOutputPath));
		}
	}

	@Test
	public void testBuildTemplateModulesExtMaven() throws Exception {
		String groupId = "com.test";
		String name = "foo-ext";
		String template = "modules-ext";

		List<String> completeArgs = new ArrayList<>();

		completeArgs.add("archetype:generate");
		completeArgs.add("--batch-mode");

		String archetypeArtifactId =
			"com.liferay.project.templates." + template.replace('-', '.');

		completeArgs.add("-DarchetypeArtifactId=" + archetypeArtifactId);

		String projectTemplateVersion =
			ProjectTemplatesUtil.getArchetypeVersion(archetypeArtifactId);

		Assert.assertTrue(
			"Unable to get project template version",
			Validator.isNotNull(projectTemplateVersion));

		completeArgs.add("-DarchetypeGroupId=com.liferay");
		completeArgs.add("-DarchetypeVersion=" + projectTemplateVersion);
		completeArgs.add("-DartifactId=" + name);
		completeArgs.add("-Dauthor=" + System.getProperty("user.name"));
		completeArgs.add("-DgroupId=" + groupId);
		completeArgs.add("-DliferayVersion=7.1.3");
		completeArgs.add("-DoriginalModuleName=com.liferay.login.web");
		completeArgs.add("-DoriginalModuleVersion=3.0.4");
		completeArgs.add("-DprojectType=standalone");
		completeArgs.add("-Dversion=1.0.0");

		File destinationDir = temporaryFolder.newFolder("maven");

		executeMaven(
			destinationDir, mavenExecutor, completeArgs.toArray(new String[0]));

		File projectDir = new File(destinationDir, name);

		testContains(
			projectDir, "build.gradle",
			"originalModule group: \"com.liferay\", ",
			"name: \"com.liferay.login.web\", version: \"3.0.4\"");
		testNotExists(projectDir, "pom.xml");
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private File _buildTemplateWithGradle(
			String template, String name, String... args)
		throws Exception {

		return buildTemplateWithGradle(temporaryFolder, template, name, args);
	}

	private static URI _gradleDistribution;

}