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

package com.liferay.project.templates.service.builder;

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
import java.nio.file.StandardCopyOption;

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
public class ProjectTemplatesServiceBuilderWorkspaceTest
	implements BaseProjectTemplatesTestCase {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@Parameterized.Parameters(
		name = "Testcase-{index}: testing {0}, {1}, {2}, {3}"
	)
	public static Iterable<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{"spring", "guestbook", "com.liferay.docs.guestbook", "7.0.6"},
				{"spring", "guestbook", "com.liferay.docs.guestbook", "7.1.3"},
				{"ds", "guestbook", "com.liferay.docs.guestbook", "7.2.1"},
				{"ds", "guestbook", "com.liferay.docs.guestbook", "7.3.4"},
				{
					"spring", "backend-integration",
					"com.liferay.docs.guestbook", "7.0.6"
				},
				{
					"spring", "backend-integration",
					"com.liferay.docs.guestbook", "7.1.3"
				},
				{
					"ds", "backend-integration", "com.liferay.docs.guestbook",
					"7.2.1"
				},
				{
					"ds", "backend-integration", "com.liferay.docs.guestbook",
					"7.3.4"
				},
				{
					"spring", "backend-integration",
					"com.liferay.docs.guestbook", "7.2.1"
				},
				{
					"spring", "backend-integration",
					"com.liferay.docs.guestbook", "7.3.4"
				},
				{"spring", "sample", "com.test.sample", "7.0.6"},
				{"spring", "sample", "com.test.sample", "7.1.3"},
				{"ds", "sample", "com.test.sample", "7.2.1"},
				{"ds", "sample", "com.test.sample", "7.3.4"}
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

	public ProjectTemplatesServiceBuilderWorkspaceTest(
		String dependencyInjector, String name, String packageName,
		String liferayVersion) {

		_dependencyInjector = dependencyInjector;
		_name = name;
		_packageName = packageName;
		_liferayVersion = liferayVersion;
	}

	@Test
	public void testBuildTemplateServiceBuilderWorkspace() throws Exception {
		String template = "service-builder";

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
			_packageName, "--dependency-injector", _dependencyInjector,
			"--liferay-version", _liferayVersion);

		if (_name.contains("sample")) {
			testContains(
				gradleProjectDir, "sample-service/build.gradle",
				"compileOnly project(\":modules:nested:path:sample:" +
					"sample-api\")");
		}

		if (_dependencyInjector.equals("ds")) {
			testContains(
				gradleProjectDir, _name + "-service/service.xml",
				"dependency-injector=\"ds\"");
			testContains(
				gradleProjectDir, _name + "-service/bnd.bnd",
				"-dsannotations-options: inherit");
		}

		if (_dependencyInjector.equals("spring")) {
			testNotContains(
				gradleProjectDir, _name + "-service/bnd.bnd",
				"-dsannotations-options: inherit");
			testNotContains(
				gradleProjectDir, _name + "-service/service.xml",
				"dependency-injector=\"ds\"");
		}

		if (_liferayVersion.equals("7.0.6") ||
			_liferayVersion.equals("7.1.3")) {

			testContains(
				gradleProjectDir, _name + "-api/build.gradle",
				DEPENDENCY_RELEASE_PORTAL_API, "biz.aQute.bnd.annotation");
			testContains(
				gradleProjectDir, _name + "-service/build.gradle",
				DEPENDENCY_RELEASE_PORTAL_API, "biz.aQute.bnd.annotation");

			testNotContains(
				gradleProjectDir, _name + "-api/build.gradle",
				"org.osgi.annotation.versioning");
			testNotContains(
				gradleProjectDir, _name + "-service/build.gradle",
				"org.osgi.annotation.versioning");
		}
		else if (_liferayVersion.startsWith("7.2")) {
			testContains(
				gradleProjectDir, _name + "-api/build.gradle",
				DEPENDENCY_RELEASE_PORTAL_API,
				"org.osgi.annotation.versioning");
			testContains(
				gradleProjectDir, _name + "-service/build.gradle",
				DEPENDENCY_RELEASE_PORTAL_API,
				"org.osgi.annotation.versioning");

			testNotContains(
				gradleProjectDir, _name + "-api/build.gradle", "biz.aQute.bnd");
			testNotContains(
				gradleProjectDir, _name + "-service/build.gradle",
				"biz.aQute.bnd");
		}
		else {
			testContains(
				gradleProjectDir, _name + "-api/build.gradle",
				DEPENDENCY_RELEASE_PORTAL_API);
			testContains(
				gradleProjectDir, _name + "-service/build.gradle",
				DEPENDENCY_RELEASE_PORTAL_API);

			testNotContains(
				gradleProjectDir, _name + "-api/build.gradle", "biz.aQute.bnd");
			testNotContains(
				gradleProjectDir, _name + "-service/build.gradle",
				"biz.aQute.bnd");
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
			"-DdependencyInjector=" + _dependencyInjector,
			"-DliferayVersion=" + _liferayVersion);

		if (isBuildProjects()) {
			String projectPath;

			if (_name.contains("sample")) {
				projectPath = ":modules:nested:path:sample";
			}
			else {
				projectPath = ":modules:" + _name;
			}

			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleWorkspaceDir, _name,
				_packageName, projectPath);
		}
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _testBuildTemplateServiceBuilder(
			File gradleProjectDir, File mavenProjectDir, final File rootProject,
			String name, String packageName, final String projectPath)
		throws Exception {

		String apiProjectName = name + "-api";
		final String serviceProjectName = name + "-service";

		testContains(
			gradleProjectDir, apiProjectName + "/bnd.bnd", "Export-Package:\\",
			packageName + ".exception,\\", packageName + ".model,\\",
			packageName + ".service,\\", packageName + ".service.persistence");

		testContains(
			gradleProjectDir, serviceProjectName + "/bnd.bnd",
			"Liferay-Service: true");

		if (!isBuildProjects()) {
			return;
		}

		_testChangePortletModelHintsXml(
			gradleProjectDir, serviceProjectName,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					executeGradle(
						rootProject, _gradleDistribution,
						projectPath + ":" + serviceProjectName +
							GRADLE_TASK_PATH_BUILD_SERVICE);

					return null;
				}

			});

		executeGradle(
			rootProject, _gradleDistribution,
			projectPath + ":" + serviceProjectName + GRADLE_TASK_PATH_BUILD);

		File gradleApiBundleFile = testExists(
			gradleProjectDir,
			apiProjectName + "/build/libs/" + packageName + ".api-1.0.0.jar");

		File gradleServiceBundleFile = testExists(
			gradleProjectDir,
			serviceProjectName + "/build/libs/" + packageName +
				".service-1.0.0.jar");

		if (!name.contains("sample")) {
			_testChangePortletModelHintsXml(
				mavenProjectDir, serviceProjectName,
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						executeMaven(
							new File(mavenProjectDir, serviceProjectName),
							mavenExecutor, MAVEN_GOAL_BUILD_SERVICE);

						return null;
					}

				});

			File gradleServicePropertiesFile = new File(
				gradleProjectDir,
				serviceProjectName + "/src/main/resources/service.properties");

			File mavenServicePropertiesFile = new File(
				mavenProjectDir,
				serviceProjectName + "/src/main/resources/service.properties");

			Files.copy(
				gradleServicePropertiesFile.toPath(),
				mavenServicePropertiesFile.toPath(),
				StandardCopyOption.REPLACE_EXISTING);

			executeMaven(mavenProjectDir, mavenExecutor, MAVEN_GOAL_PACKAGE);

			File mavenApiBundleFile = testExists(
				mavenProjectDir,
				apiProjectName + "/target/" + name + "-api-1.0.0.jar");
			File mavenServiceBundleFile = testExists(
				mavenProjectDir,
				serviceProjectName + "/target/" + name + "-service-1.0.0.jar");

			testBundlesDiff(gradleApiBundleFile, mavenApiBundleFile);
			testBundlesDiff(gradleServiceBundleFile, mavenServiceBundleFile);
		}
	}

	private void _testChangePortletModelHintsXml(
			File projectDir, String serviceProjectName,
			Callable<Void> buildServiceCallable)
		throws Exception {

		buildServiceCallable.call();

		File file = testExists(
			projectDir,
			serviceProjectName +
				"/src/main/resources/META-INF/portlet-model-hints.xml");

		Path path = file.toPath();

		String content = FileUtil.read(path);

		String newContent = content.replace(
			"<field name=\"field5\" type=\"String\" />",
			"<field name=\"field5\" type=\"String\">\n\t\t\t<hint-collection " +
				"name=\"CLOB\" />\n\t\t</field>");

		Assert.assertNotEquals("Unexpected " + file, content, newContent);

		Files.write(path, newContent.getBytes(StandardCharsets.UTF_8));

		buildServiceCallable.call();

		Assert.assertEquals(
			"Changes in " + file + " incorrectly overridden", newContent,
			FileUtil.read(path));
	}

	private static URI _gradleDistribution;

	private final String _dependencyInjector;
	private final String _liferayVersion;
	private final String _name;
	private final String _packageName;

}