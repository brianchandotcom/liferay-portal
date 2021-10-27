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

import java.util.Properties;

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
	public void testBuildTemplateRESTBuilder() throws Exception {
		String liferayVersion = "7.4.3.4";
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

		testExists(gradleProjectDir, name + "-api/build.gradle");
		testExists(gradleProjectDir, name + "-client/build.gradle");
		testExists(gradleProjectDir, name + "-impl/build.gradle");
		testExists(gradleProjectDir, name + "-test/build.gradle");

		File mavenWorkspaceDir = buildWorkspace(
			temporaryFolder, "maven", "mavenWS", liferayVersion, mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

		File mavenProjectDir = buildTemplateWithMaven(
			mavenModulesDir, mavenModulesDir, template, name, "com.test",
			mavenExecutor, "-Dpackage=" + packageName,
			"-DliferayVersion=" + liferayVersion, "-DbuildType=maven");

		testExists(mavenProjectDir, name + "-api/pom.xml");
		testExists(mavenProjectDir, name + "-client/pom.xml");
		testExists(mavenProjectDir, name + "-impl/pom.xml");
		testExists(mavenProjectDir, name + "-test/pom.xml");
	}

	@Test
	public void testBuildTemplateRESTBuilderCheckExports() throws Exception {
		String liferayVersion = "7.4.3.4";
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

		File gradleBndFile = testExists(
			gradleProjectDir, name + "-api/bnd.bnd");

		File mavenWorkspaceDir = buildWorkspace(
			temporaryFolder, "maven", "mavenWS", liferayVersion, mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

		File mavenProjectDir = buildTemplateWithMaven(
			mavenModulesDir, mavenModulesDir, template, name, "com.test",
			mavenExecutor, "-Dpackage=" + packageName,
			"-DliferayVersion=" + liferayVersion, "-DbuildType=maven");

		File mavenBndFile = testExists(mavenProjectDir, name + "-api/bnd.bnd");

		Assert.assertArrayEquals(
			Files.readAllBytes(gradleBndFile.toPath()),
			Files.readAllBytes(mavenBndFile.toPath()));

		testContains(
			gradleProjectDir, name + "-api/bnd.bnd", "Export-Package:\\",
			packageName + ".dto.v1_0,\\", packageName + ".resource.v1_0");
	}

	@Test
	public void testBuildTemplateRESTBuilderWorkspaceRelativePath()
		throws Exception {

		String liferayVersion = "7.4.3.4";
		String name = "sample";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", liferayVersion,
			mavenExecutor);

		File gradlePropertiesFile = new File(
			gradleWorkspaceDir + "gradle.properties");

		Files.deleteIfExists(gradlePropertiesFile.toPath());

		buildTemplateWithGradle(
			gradleWorkspaceDir, "rest-builder", name, "--liferay-version",
			liferayVersion);

		testContains(
			gradleWorkspaceDir, name + "/" + name + "-impl/build.gradle",
			"project(\":" + name + ":" + name + "-api");
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static URI _gradleDistribution;

}