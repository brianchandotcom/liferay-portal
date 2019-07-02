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

package com.liferay.project.templates;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.internal.util.ProjectTemplatesUtil;
import com.liferay.project.templates.internal.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;
import com.liferay.project.templates.util.ProjectTemplatesTestSupport;

import java.io.File;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Lawrence Lee
 */
@RunWith(Parameterized.class)
public class ProjectTemplatesSpringPortletMVCTest
	implements ProjectTemplatesTestSupport {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@ClassRule
	public static final TemporaryFolder testCaseTemporaryFolder =
		new TemporaryFolder();

	@Parameterized.Parameters(
		name = "Testcase-{index}: testing {0}, {1}, {2}, {3}"
	)
	public static Iterable<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{"springportletmvc", "embedded", "jsp", "7.0"},
				{"springportletmvc", "embedded", "jsp", "7.1"},
				{"springportletmvc", "embedded", "jsp", "7.2"},
				{"portletmvc4spring", "embedded", "jsp", "7.1"},
				{"portletmvc4spring", "embedded", "jsp", "7.2"},
				{"portletmvc4spring", "embedded", "thymeleaf", "7.1"},
				{"portletmvc4spring", "embedded", "thymeleaf", "7.2"}
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

	public ProjectTemplatesSpringPortletMVCTest(
		String framework, String frameworkDependencies, String viewType,
		String liferayVersion) {

		_framework = framework;
		_frameworkDependencies = frameworkDependencies;
		_viewType = viewType;
		_liferayVersion = liferayVersion;
	}

	@Test
	public void testSpringPortletMVC() throws Exception {
		File gradleProjectDir = _buildSpringMVCTemplate(
			"gradle", _framework, _frameworkDependencies, _viewType,
			_liferayVersion);

		testExists(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/spring-context/portlet/Sample.xml");
		testExists(
			gradleProjectDir,
			"src/main/java/com/test/controller/UserController.java");

		if (_liferayVersion.equals("7.0")) {
			testContains(
				gradleProjectDir, "src/main/webapp/WEB-INF/liferay-display.xml",
				"liferay-display_7_0_0.dtd");

			testContains(
				gradleProjectDir, "src/main/webapp/WEB-INF/liferay-portlet.xml",
				"liferay-portlet-app_7_0_0.dtd");
		}
		else if (_liferayVersion.equals("7.1")) {
			testContains(
				gradleProjectDir, "src/main/webapp/WEB-INF/liferay-display.xml",
				"liferay-display_7_1_0.dtd");

			testContains(
				gradleProjectDir, "src/main/webapp/WEB-INF/liferay-portlet.xml",
				"liferay-portlet-app_7_1_0.dtd");
		}
		else if (_liferayVersion.equals("7.2")) {
			testContains(
				gradleProjectDir, "src/main/webapp/WEB-INF/liferay-display.xml",
				"liferay-display_7_2_0.dtd");

			testContains(
				gradleProjectDir, "src/main/webapp/WEB-INF/liferay-portlet.xml",
				"liferay-portlet-app_7_2_0.dtd");
		}

		if (_viewType.equals("jsp")) {
			if (_framework.equals("springportletmvc")) {
				testContains(
					gradleProjectDir,
					"src/main/webapp/WEB-INF/spring-context" +
						"/portlet-application-context.xml",
					"org.springframework.web.servlet.view.JstlView");
			}
			else {
				testContains(
					gradleProjectDir,
					"src/main/webapp/WEB-INF/spring-context" +
						"/portlet-application-context.xml",
					"com.liferay.portletmvc4spring.PortletJstlView");
			}
		}

		if (_viewType.equals("jsp")) {
			testExists(
				gradleProjectDir,
				"src/main/webapp/WEB-INF/views/greeting.jspx");

			testNotExists(
				gradleProjectDir,
				"src/main/webapp/WEB-INF/views/greeting.html");
		}
		else {
			testExists(
				gradleProjectDir,
				"src/main/webapp/WEB-INF/views/greeting.html");
			testNotExists(
				gradleProjectDir,
				"src/main/webapp/WEB-INF/views/greeting.jspx");
		}

		if (_viewType.equals("jsp") || _framework.equals("portletmvc4spring")) {
			testNotExists(
				gradleProjectDir,
				"src/main/java/com/test/spring4/ServletContextFactory.java");
		}

		File mavenProjectDir = _buildSpringMVCTemplate(
			"maven", _framework, _frameworkDependencies, _viewType,
			_liferayVersion);

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _buildProjects(File gradleProjectDir, File mavenProjectDir)
		throws Exception {

		File gradleOutputDir = new File(gradleProjectDir, "build/libs");
		File mavenOutputDir = new File(mavenProjectDir, "target");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, gradleOutputDir, mavenOutputDir,
			GRADLE_TASK_PATH_BUILD);
	}

	private void _buildProjects(
			File gradleProjectDir, File mavenProjectDir, File gradleOutputDir,
			File mavenOutputDir, String... gradleTaskPath)
		throws Exception {

		if (isBuildProjects()) {
			Path gradleOutputPath = FileTestUtil.getFile(
				gradleOutputDir.toPath(), OUTPUT_FILENAME_GLOB_REGEX, 1);

			Assert.assertNotNull(gradleOutputPath);

			Assert.assertTrue(Files.exists(gradleOutputPath));

			File gradleOutputFile = gradleOutputPath.toFile();

			String gradleOutputFileName = gradleOutputFile.getName();

			_executeMaven(mavenProjectDir, MAVEN_GOAL_PACKAGE);

			Path mavenOutputPath = FileTestUtil.getFile(
				mavenOutputDir.toPath(), OUTPUT_FILENAME_GLOB_REGEX, 1);

			Assert.assertNotNull(mavenOutputPath);

			Assert.assertTrue(Files.exists(mavenOutputPath));

			File mavenOutputFile = mavenOutputPath.toFile();

			String mavenOutputFileName = mavenOutputFile.getName();

			try {
				if (gradleOutputFileName.endsWith(".jar")) {
					testBundlesDiff(gradleOutputFile, mavenOutputFile);
				}
				else if (gradleOutputFileName.endsWith(".war")) {
					testWarsDiff(gradleOutputFile, mavenOutputFile);
				}
			}
			catch (Throwable t) {
				if (TEST_DEBUG_BUNDLE_DIFFS) {
					Path dirPath = Paths.get("build");

					Files.copy(
						gradleOutputFile.toPath(),
						dirPath.resolve(gradleOutputFileName));
					Files.copy(
						mavenOutputFile.toPath(),
						dirPath.resolve(mavenOutputFileName));
				}

				throw t;
			}
		}
	}

	private File _buildSpringMVCTemplate(
			String buildType, String framework, String frameworkDependencies,
			String viewType, String liferayVersion)
		throws Exception {

		String template = "spring-mvc-portlet";
		String name = "sampleSpringMVCPortlet";

		if (buildType.equals("maven")) {
			String groupId = "com.test";

			return _buildTemplateWithMaven(
				template, name, groupId, "-Dpackage=com.test",
				"-DclassName=Sample", "-Dframework=" + framework,
				"-DframeworkDependencies=" + frameworkDependencies,
				"-DviewType=" + viewType, "-DliferayVersion=" + liferayVersion);
		}

		return _buildTemplateWithGradle(
			template, name, "--package-name", "com.test", "--class-name",
			"Sample", "--framework", framework, "--framework-dependencies",
			frameworkDependencies, "--view-type", viewType, "--liferay-version",
			liferayVersion);
	}

	private File _buildTemplateWithGradle(
			String template, String name, String... args)
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("gradle");

		return buildTemplateWithGradle(destinationDir, template, name, args);
	}

	private File _buildTemplateWithMaven(
			File parentDir, File destinationDir, String template, String name,
			String groupId, String... args)
		throws Exception {

		List<String> completeArgs = new ArrayList<>();

		completeArgs.add("archetype:generate");
		completeArgs.add("--batch-mode");

		String archetypeArtifactId =
			"com.liferay.project.templates." + template.replace('-', '.');

		if (archetypeArtifactId.equals(
				"com.liferay.project.templates.portlet")) {

			archetypeArtifactId = "com.liferay.project.templates.mvc.portlet";
		}

		completeArgs.add("-DarchetypeArtifactId=" + archetypeArtifactId);

		String projectTemplateVersion =
			ProjectTemplatesUtil.getArchetypeVersion(archetypeArtifactId);

		Assert.assertTrue(
			"Unable to get project template version",
			Validator.isNotNull(projectTemplateVersion));

		completeArgs.add("-DarchetypeGroupId=com.liferay");
		completeArgs.add("-DarchetypeVersion=" + projectTemplateVersion);
		completeArgs.add("-Dauthor=" + System.getProperty("user.name"));
		completeArgs.add("-DgroupId=" + groupId);
		completeArgs.add("-DartifactId=" + name);
		completeArgs.add("-Dversion=1.0.0");

		boolean liferayVersionSet = false;
		boolean projectTypeSet = false;

		for (String arg : args) {
			completeArgs.add(arg);

			if (arg.startsWith("-DliferayVersion=")) {
				liferayVersionSet = true;
			}
			else if (arg.startsWith("-DprojectType=")) {
				projectTypeSet = true;
			}
		}

		if (!liferayVersionSet) {
			completeArgs.add("-DliferayVersion=7.2");
		}

		if (!projectTypeSet) {
			completeArgs.add("-DprojectType=standalone");
		}

		_executeMaven(destinationDir, completeArgs.toArray(new String[0]));

		File projectDir = new File(destinationDir, name);

		testExists(projectDir, "pom.xml");
		testNotExists(projectDir, "gradlew");
		testNotExists(projectDir, "gradlew.bat");
		testNotExists(projectDir, "gradle/wrapper/gradle-wrapper.jar");
		testNotExists(projectDir, "gradle/wrapper/gradle-wrapper.properties");

		testArchetyper(
			parentDir, destinationDir, projectDir, name, groupId, template,
			completeArgs);

		return projectDir;
	}

	private File _buildTemplateWithMaven(
			String template, String name, String groupId, String... args)
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("maven");

		return _buildTemplateWithMaven(
			destinationDir, destinationDir, template, name, groupId, args);
	}

	private String _executeMaven(
			File projectDir, boolean buildAndFail, String... args)
		throws Exception {

		File pomXmlFile = new File(projectDir, "pom.xml");

		if (pomXmlFile.exists()) {
			editXml(
				pomXmlFile,
				document -> {
					addNexusRepositoriesElement(
						document, "repositories", "repository");
					addNexusRepositoriesElement(
						document, "pluginRepositories", "pluginRepository");
				});
		}

		String[] completeArgs = new String[args.length + 1];

		completeArgs[0] = "--update-snapshots";

		System.arraycopy(args, 0, completeArgs, 1, args.length);

		MavenExecutor.Result result = mavenExecutor.execute(projectDir, args);

		if (buildAndFail) {
			Assert.assertFalse(
				"Expected build to fail. " + result.exitCode,
				result.exitCode == 0);
		}
		else {
			Assert.assertEquals(result.output, 0, result.exitCode);
		}

		return result.output;
	}

	private String _executeMaven(File projectDir, String... args)
		throws Exception {

		return _executeMaven(projectDir, false, args);
	}

	private static URI _gradleDistribution;

	private final String _framework;
	private final String _frameworkDependencies;
	private final String _liferayVersion;
	private final String _viewType;

}