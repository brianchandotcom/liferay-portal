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
import com.liferay.project.templates.extensions.util.FileUtil;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;

import java.net.URI;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Callable;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Gregory Amerson
 * @author Lawrence Lee
 */
@RunWith(Parameterized.class)
public class ProjectTemplatesRESTBuilderWorkspaceTest
	implements BaseProjectTemplatesTestCase {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@Parameterized.Parameters(name = "Testcase-{index}: testing {0}, {1}, {2}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{"guestbook", "com.liferay.docs.guestbook", "7.1.3"},
				{"guestbook", "com.liferay.docs.guestbook", "7.2.1"},
				{"guestbook", "com.liferay.docs.guestbook", "7.3.6"},
				{"backend-integration", "com.liferay.docs.guestbook", "7.1.3"},
				{"backend-integration", "com.liferay.docs.guestbook", "7.2.1"},
				{"backend-integration", "com.liferay.docs.guestbook", "7.3.6"},
				{"sample", "com.test.sample", "7.1.3"},
				{"sample", "com.test.sample", "7.2.1"},
				{"sample", "com.test.sample", "7.3.6"}
			});
	}

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

	public ProjectTemplatesRESTBuilderWorkspaceTest(
		String name, String packageName, String liferayVersion) {

		_name = name;
		_packageName = packageName;
		_liferayVersion = liferayVersion;
	}

	@Test
	public void testBuildTemplateRESTBuilderWorkspace() throws Exception {
		String template = "rest-builder";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", _liferayVersion,
			mavenExecutor);

		File gradleWorkspaceModulesDir = new File(
			gradleWorkspaceDir, "modules");

		if (_name.contains("sample")) {
			gradleWorkspaceModulesDir = new File(
				gradleWorkspaceDir, "modules/nested/path");

			Assert.assertTrue(gradleWorkspaceModulesDir.mkdirs());
		}

		File gradleProjectDir = buildTemplateWithGradle(
			gradleWorkspaceModulesDir, template, _name, "--package-name",
			_packageName, "--liferay-version", _liferayVersion);

		if (_name.contains("sample")) {
			testContains(
				gradleProjectDir, "sample-impl/build.gradle",
				"compile project(\":modules:nested:path:sample:sample-api\")");
		}

		if (_liferayVersion.startsWith("7.1")) {
			testContains(
				gradleProjectDir, _name + "-api/build.gradle",
				"compileOnly group: \"javax.servlet\", name: " +
					"\"javax.servlet-api\"");
			testContains(
				gradleProjectDir, _name + "-impl/build.gradle",
				"compileOnly group: \"javax.servlet\", name: " +
					"\"javax.servlet-api\"");

			testNotContains(
				gradleProjectDir, _name + "-api/build.gradle",
				"compileOnly group: \"org.apache.felix\", name: \"org.apache." +
					"felix.http.servlet-api\", version: \"1.1.2\"");
			testNotContains(
				gradleProjectDir, _name + "-impl/build.gradle",
				"compileOnly group: \"org.apache.felix\", name: \"org.apache." +
					"felix.http.servlet-api\", version: \"1.1.2\"");
		}
		else {
			testContains(
				gradleProjectDir, _name + "-api/build.gradle",
				"compileOnly group: \"org.apache.felix\", name: \"org.apache." +
					"felix.http.servlet-api\", version: \"1.1.2\"");
			testContains(
				gradleProjectDir, _name + "-impl/build.gradle",
				"compileOnly group: \"org.apache.felix\", name: \"org.apache." +
					"felix.http.servlet-api\", version: \"1.1.2\"");

			testNotContains(
				gradleProjectDir, _name + "-api/build.gradle",
				"compileOnly group: \"javax.servlet\", name: " +
					"\"javax.servlet-api\"");
			testNotContains(
				gradleProjectDir, _name + "-impl/build.gradle",
				"compileOnly group: \"javax.servlet\", name: " +
					"\"javax.servlet-api\"");
		}

		File mavenWorkspaceDir = buildWorkspace(
			temporaryFolder, "maven", "mavenWS", _liferayVersion,
			mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

		if (_name.contains("sample")) {
			mavenModulesDir = new File(
				mavenWorkspaceDir, "modules/nested/path");

			Assert.assertTrue(mavenModulesDir.mkdirs());
		}

		File mavenProjectDir = buildTemplateWithMaven(
			mavenModulesDir, mavenModulesDir, template, _name, "com.test",
			mavenExecutor, "-Dpackage=" + _packageName,
			"-DliferayVersion=" + _liferayVersion, "-DbuildType=maven");

		if (isBuildProjects()) {
			String projectPath;

			if (_name.contains("sample")) {
				projectPath = ":modules:nested:path:sample";
			}
			else {
				projectPath = ":modules:" + _name;
			}

			_testBuildTemplateRESTBuilder(
				gradleProjectDir, mavenProjectDir, gradleWorkspaceDir, _name,
				_packageName, projectPath);
		}
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _testBuildTemplateRESTBuilder(
			File gradleProjectDir, File mavenProjectDir, final File rootProject,
			String name, String packageName, final String projectPath)
		throws Exception {

		String apiProjectName = name + "-api";

		testContains(
			gradleProjectDir, apiProjectName + "/bnd.bnd", "Export-Package:\\",
			packageName + ".dto.v1_0,\\", packageName + ".resource.v1_0");

		String implProjectName = name + "-impl";

		_testChangeOpenAPIYAML(
			gradleProjectDir, implProjectName,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					executeGradle(
						rootProject, _gradleDistribution,
						projectPath + ":" + implProjectName +
							_GRADLE_TASK_PATH_BUILD_REST);

					return null;
				}

			});

		executeGradle(
			rootProject, _gradleDistribution,
			projectPath + ":" + implProjectName + GRADLE_TASK_PATH_BUILD);

		File gradleApiBundleFile = testExists(
			gradleProjectDir,
			apiProjectName + "/build/libs/" + packageName + ".api-1.0.0.jar");

		File gradleImplBundleFile = testExists(
			gradleProjectDir,
			implProjectName + "/build/libs/" + packageName + ".impl-1.0.0.jar");

		if (!name.contains("sample")) {
			_testChangeOpenAPIYAML(
				mavenProjectDir, implProjectName,
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						executeMaven(
							new File(mavenProjectDir, implProjectName),
							mavenExecutor, MAVEN_GOAL_BUILD_REST);

						return null;
					}

				});

			executeMaven(mavenProjectDir, mavenExecutor, MAVEN_GOAL_PACKAGE);

			File mavenApiBundleFile = testExists(
				mavenProjectDir,
				apiProjectName + "/target/" + name + "-api-1.0.0.jar");
			File mavenImplBundleFile = testExists(
				mavenProjectDir,
				implProjectName + "/target/" + name + "-impl-1.0.0.jar");

			testBundlesDiff(gradleApiBundleFile, mavenApiBundleFile);
			testBundlesDiff(gradleImplBundleFile, mavenImplBundleFile);
		}
	}

	private void _testChangeOpenAPIYAML(
			File projectDir, String implProjectName,
			Callable<Void> buildRESTCallable)
		throws Exception {

		File file = testExists(
			projectDir, implProjectName + "/rest-openapi.yaml");

		Path path = file.toPath();

		String content = FileUtil.read(path);

		String newContent = content.concat(
			"\n"
		).concat(
			FileTestUtil.read("components-and-paths.yaml")
		);

		Files.write(path, newContent.getBytes(StandardCharsets.UTF_8));

		buildRESTCallable.call();
	}

	private static final String _GRADLE_TASK_PATH_BUILD_REST = ":buildREST";

	private static URI _gradleDistribution;

	private final String _liferayVersion;
	private final String _name;
	private final String _packageName;

}