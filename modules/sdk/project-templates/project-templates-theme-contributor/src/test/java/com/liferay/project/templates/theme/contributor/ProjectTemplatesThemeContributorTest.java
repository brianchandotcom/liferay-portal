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

package com.liferay.project.templates.theme.contributor;

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
public class ProjectTemplatesThemeContributorTest
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
	public void testBuildTemplateThemeContributorCustom() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme-contributor", "my-contributor-custom", "--contributor-type",
			"foo-bar");

		testContains(
			gradleProjectDir, "bnd.bnd",
			"Liferay-Theme-Contributor-Type: foo-bar",
			"Web-ContextPath: /foo-bar-theme-contributor");
		testNotContains(
			gradleProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/foo-bar.scss");
		testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/js/foo-bar.js");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "theme-contributor", "my-contributor-custom",
			"com.test", mavenExecutor, "-DcontributorType=foo-bar",
			"-Dpackage=my.contributor.custom");

		testContains(
			mavenProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		buildProjects(
			_gradleDistribution, mavenExecutor, gradleProjectDir,
			mavenProjectDir);
	}

	@Test
	public void testBuildTemplateThemeContributorCustom71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme-contributor", "my-contributor-custom", "--contributor-type",
			"foo-bar", "--liferay-version", "7.1.3");

		testContains(
			gradleProjectDir, "bnd.bnd",
			"Liferay-Theme-Contributor-Type: foo-bar",
			"Web-ContextPath: /foo-bar-theme-contributor");
		testNotContains(
			gradleProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/foo-bar.scss");
		testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/js/foo-bar.js");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "theme-contributor", "my-contributor-custom",
			"com.test", mavenExecutor, "-DcontributorType=foo-bar",
			"-Dpackage=my.contributor.custom", "-DliferayVersion=7.1.3");

		testContains(
			mavenProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		buildProjects(
			_gradleDistribution, mavenExecutor, gradleProjectDir,
			mavenProjectDir);
	}

	@Test
	public void testBuildTemplateThemeContributorCustom72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme-contributor", "my-contributor-custom", "--contributor-type",
			"foo-bar", "--liferay-version", "7.2.1");

		testContains(
			gradleProjectDir, "bnd.bnd",
			"Liferay-Theme-Contributor-Type: foo-bar",
			"Web-ContextPath: /foo-bar-theme-contributor");
		testNotContains(
			gradleProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/foo-bar.scss");
		testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/js/foo-bar.js");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "theme-contributor", "my-contributor-custom",
			"com.test", mavenExecutor, "-DcontributorType=foo-bar",
			"-Dpackage=my.contributor.custom", "-DliferayVersion=7.2.1");

		testContains(
			mavenProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		buildProjects(
			_gradleDistribution, mavenExecutor, gradleProjectDir,
			mavenProjectDir);
	}

	@Test
	public void testBuildTemplateThemeContributorCustom73() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme-contributor", "my-contributor-custom", "--contributor-type",
			"foo-bar", "--liferay-version", "7.3.0");

		testContains(
			gradleProjectDir, "bnd.bnd",
			"Liferay-Theme-Contributor-Type: foo-bar",
			"Web-ContextPath: /foo-bar-theme-contributor");
		testNotContains(
			gradleProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/foo-bar.scss");
		testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/js/foo-bar.js");

		File mavenProjectDir = buildTemplateWithMaven(
			temporaryFolder, "theme-contributor", "my-contributor-custom",
			"com.test", mavenExecutor, "-DcontributorType=foo-bar",
			"-Dpackage=my.contributor.custom", "-DliferayVersion=7.3.0");

		testContains(
			mavenProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		buildProjects(
			_gradleDistribution, mavenExecutor, gradleProjectDir,
			mavenProjectDir);
	}

	@Test
	public void testBuildTemplateThemeContributorinWorkspace()
		throws Exception {

		testBuildTemplateWithWorkspace(
			temporaryFolder, _gradleDistribution, "theme-contributor",
			"my-contributor", "build/libs/my.contributor-1.0.0.jar",
			"--contributor-type", "my-contributor",
			"--dependency-management-enabled", "--liferay-version", "7.3.0");
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