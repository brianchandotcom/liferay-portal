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

package com.liferay.project.templates.service.wrapper;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;

import java.net.URI;

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
public class ProjectTemplatesServiceWrapperTest
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
	public void testBuildTemplateServiceWrapper70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service-wrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"--liferay-version", "7.0.6");

		testExists(gradleProjectDir, "bnd.bnd");

		testContains(
			gradleProjectDir, "build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0\"",
			"apply plugin: \"com.liferay.plugin\"");
		testContains(
			gradleProjectDir,
			"src/main/java/serviceoverride/Serviceoverride.java",
			"package serviceoverride;",
			"import com.liferay.portal.kernel.service.UserLocalServiceWrapper;",
			"service = ServiceWrapper.class",
			"public class Serviceoverride extends UserLocalServiceWrapper {",
			"public Serviceoverride() {");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-wrapper", "serviceoverride", "com.test",
			mavenExecutor, "-DclassName=Serviceoverride",
			"-Dpackage=serviceoverride",
			"-DserviceWrapperClass=" +
				"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"-DliferayVersion=7.0.6");

		buildProjects(
			_gradleDistribution, mavenExecutor, gradleProjectDir,
			mavenProjectDir);
	}

	@Test
	public void testBuildTemplateServiceWrapper71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service-wrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"--liferay-version", "7.1.3");

		testContains(
			gradleProjectDir, "build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-wrapper", "serviceoverride", "com.test",
			mavenExecutor, "-DclassName=Serviceoverride",
			"-Dpackage=serviceoverride",
			"-DserviceWrapperClass=" +
				"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"-DliferayVersion=7.1.3");

		buildProjects(
			_gradleDistribution, mavenExecutor, gradleProjectDir,
			mavenProjectDir);
	}

	@Test
	public void testBuildTemplateServiceWrapper72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service-wrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"--liferay-version", "7.2.1");

		testContains(
			gradleProjectDir, "build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-wrapper", "serviceoverride", "com.test",
			mavenExecutor, "-DclassName=Serviceoverride",
			"-Dpackage=serviceoverride",
			"-DserviceWrapperClass=" +
				"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"-DliferayVersion=7.2.1");

		buildProjects(
			_gradleDistribution, mavenExecutor, gradleProjectDir,
			mavenProjectDir);
	}

	@Test
	public void testBuildTemplateServiceWrapper73() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service-wrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"--liferay-version", "7.3.0");

		testContains(
			gradleProjectDir, "build.gradle",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"5.4.0");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service-wrapper", "serviceoverride", "com.test",
			mavenExecutor, "-DclassName=Serviceoverride",
			"-Dpackage=serviceoverride",
			"-DserviceWrapperClass=" +
				"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"-DliferayVersion=7.3.0");

		buildProjects(
			_gradleDistribution, mavenExecutor, gradleProjectDir,
			mavenProjectDir);
	}

	@Test
	public void testBuildTemplateServiceWrapperInWorkspace() throws Exception {
		File workspaceDir = buildWorkspace(temporaryFolder, "7.3.0");

		enableTargetPlatformInWorkspace(workspaceDir, "7.3.0");

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir = buildTemplateWithGradle(
			modulesDir, "service-wrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"--dependency-management-enabled");

		testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");
		testNotContains(
			workspaceProjectDir, "build.gradle", "version: \"[0-9].*");

		if (isBuildProjects()) {
			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:serviceoverride:build");

			testExists(
				workspaceProjectDir, "build/libs/serviceoverride-1.0.0.jar");
		}
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