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

package com.liferay.project.templates.service;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import java.net.URI;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;
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
public class ProjectTemplatesServiceTest
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
	public void testBuildTemplateService70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction",
			"--liferay-version", "7.0.6");

		testExists(gradleProjectDir, "bnd.bnd");

		testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service", "servicepreaction", "com.test",
			mavenExecutor, "-DclassName=FooAction",
			"-Dpackage=servicepreaction",
			"-DserviceClass=com.liferay.portal.kernel.events.LifecycleAction",
			"-DliferayVersion=7.0.6");

		if (isBuildProjects()) {
			_writeServiceClass(gradleProjectDir);
			_writeServiceClass(mavenProjectDir);

			buildProjects(
				_gradleDistribution, mavenExecutor, gradleProjectDir,
				mavenProjectDir);
		}
	}

	@Test
	public void testBuildTemplateService71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction",
			"--liferay-version", "7.1.3");

		testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service", "servicepreaction", "com.test",
			mavenExecutor, "-DclassName=FooAction",
			"-Dpackage=servicepreaction",
			"-DserviceClass=com.liferay.portal.kernel.events.LifecycleAction",
			"-DliferayVersion=7.1.3");

		if (isBuildProjects()) {
			_writeServiceClass(gradleProjectDir);
			_writeServiceClass(mavenProjectDir);

			buildProjects(
				_gradleDistribution, mavenExecutor, gradleProjectDir,
				mavenProjectDir);
		}
	}

	@Test
	public void testBuildTemplateService72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction",
			"--liferay-version", "7.2.1");

		testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service", "servicepreaction", "com.test",
			mavenExecutor, "-DclassName=FooAction",
			"-Dpackage=servicepreaction",
			"-DserviceClass=com.liferay.portal.kernel.events.LifecycleAction",
			"-DliferayVersion=7.2.1");

		if (isBuildProjects()) {
			_writeServiceClass(gradleProjectDir);
			_writeServiceClass(mavenProjectDir);

			buildProjects(
				_gradleDistribution, mavenExecutor, gradleProjectDir,
				mavenProjectDir);
		}
	}

	@Test
	public void testBuildTemplateService73() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction",
			"--liferay-version", "7.3.0");

		testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			DEPENDENCY_PORTAL_KERNEL + ", version: \"5.4.0");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "service", "servicepreaction", "com.test",
			mavenExecutor, "-DclassName=FooAction",
			"-Dpackage=servicepreaction",
			"-DserviceClass=com.liferay.portal.kernel.events.LifecycleAction",
			"-DliferayVersion=7.3.0");

		if (isBuildProjects()) {
			_writeServiceClass(gradleProjectDir);
			_writeServiceClass(mavenProjectDir);

			buildProjects(
				_gradleDistribution, mavenExecutor, gradleProjectDir,
				mavenProjectDir);
		}
	}

	@Test
	public void testBuildTemplateServiceInWorkspace() throws Exception {
		File workspaceDir = buildWorkspace(temporaryFolder);

		enableTargetPlatformInWorkspace(workspaceDir, "7.2.1");

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir = buildTemplateWithGradle(
			modulesDir, "service", "servicepreaction", "--class-name",
			"FooAction", "--service",
			"com.liferay.portal.kernel.events.LifecycleAction",
			"--dependency-management-enabled");

		testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");
		testNotContains(
			workspaceProjectDir, "build.gradle", "version: \"[0-9].*");

		if (isBuildProjects()) {
			_writeServiceClass(workspaceProjectDir);

			executeGradle(
				workspaceDir, _gradleDistribution,
				":modules:servicepreaction:build");

			testExists(
				workspaceProjectDir, "build/libs/servicepreaction-1.0.0.jar");
		}
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private File _buildTemplateWithGradle(
			String template, String name, String... args)
		throws Exception {

		return buildTemplateWithGradle(temporaryFolder, template, name, args);
	}

	private void _writeServiceClass(File projectDir) throws IOException {
		String importLine =
			"import com.liferay.portal.kernel.events.LifecycleAction;";
		String classLine =
			"public class FooAction implements LifecycleAction {";

		File actionJavaFile = testContains(
			projectDir, "src/main/java/servicepreaction/FooAction.java",
			"package servicepreaction;", importLine,
			"service = LifecycleAction.class", classLine);

		Path actionJavaPath = actionJavaFile.toPath();

		List<String> lines = Files.readAllLines(
			actionJavaPath, StandardCharsets.UTF_8);

		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
				actionJavaPath, StandardCharsets.UTF_8)) {

			for (String line : lines) {
				FileTestUtil.write(bufferedWriter, line);

				if (line.equals(classLine)) {
					FileTestUtil.write(
						bufferedWriter, "@Override",
						"public void processLifecycleEvent(",
						"LifecycleEvent lifecycleEvent)",
						"throws ActionException {", "System.out.println(",
						"\"login.event.pre=\" + lifecycleEvent);", "}");
				}
				else if (line.equals(importLine)) {
					FileTestUtil.write(
						bufferedWriter,
						"import com.liferay.portal.kernel.events." +
							"LifecycleEvent;",
						"import com.liferay.portal.kernel.events." +
							"ActionException;");
				}
			}
		}
	}

	private static URI _gradleDistribution;

}