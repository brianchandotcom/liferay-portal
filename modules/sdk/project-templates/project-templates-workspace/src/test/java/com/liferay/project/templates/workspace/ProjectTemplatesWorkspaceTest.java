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

package com.liferay.project.templates.workspace;

import static org.junit.Assume.assumeTrue;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.extensions.util.WorkspaceUtil;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;
import java.io.FileOutputStream;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import java.util.Optional;
import java.util.Properties;
import java.util.regex.Matcher;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Lawrence Lee
 */
public class ProjectTemplatesWorkspaceTest
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
	public void testBuildTemplateWorkspace() throws Exception {
		File workspaceProjectDir = buildWorkspace(
			temporaryFolder, "gradle", "foows", getDefaultLiferayVersion(),
			mavenExecutor);

		testExists(workspaceProjectDir, "configs/dev/portal-ext.properties");
		testExists(workspaceProjectDir, "gradle.properties");
		testExists(workspaceProjectDir, "modules");
		testExists(workspaceProjectDir, "themes");

		testContains(workspaceProjectDir, "GETTING_STARTED.markdown", "<h4>liferay.workspace.product");

		testNotExists(workspaceProjectDir, "modules/pom.xml");
		testNotExists(workspaceProjectDir, "themes/pom.xml");

		File modulesProjectDir = buildTemplateWithGradle(
			new File(workspaceProjectDir, "modules"), "", "foo-portlet");

		testNotContains(
			modulesProjectDir, "build.gradle", "buildscript", "repositories");

		if (isBuildProjects()) {
			executeGradle(
				workspaceProjectDir, _gradleDistribution,
				":modules:foo-portlet" + GRADLE_TASK_PATH_BUILD);

			testExists(modulesProjectDir, "build/libs/foo.portlet-1.0.0.jar");
		}
	}

	@Test
	public void testBuildTemplateWorkspaceDXPProductKey() throws Exception {
		File workspaceProjectDir = buildWorkspace(
			temporaryFolder, "gradle", "foows", getDefaultLiferayVersion(),
			mavenExecutor);

		writeGradlePropertiesInWorkspace(
			workspaceProjectDir, "liferay.workspace.product=dxp-7.3-ep5");

		File modulesProjectDir = buildTemplateWithGradle(
			new File(workspaceProjectDir, "modules"), "mvc-portlet",
			"foo-portlet", "--product", "dxp");

		testContains(modulesProjectDir, "build.gradle", "release.dxp.api");

		if (isBuildProjects()) {
			executeGradle(
				workspaceProjectDir, _gradleDistribution,
				":modules:foo-portlet" + GRADLE_TASK_PATH_BUILD);

			testExists(modulesProjectDir, "build/libs/foo.portlet-1.0.0.jar");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateWorkspaceExistingFile() throws Exception {
		File destinationDir = temporaryFolder.newFolder("existing-file");

		createNewFiles("foo", destinationDir);

		buildTemplateWithGradle(destinationDir, WorkspaceUtil.WORKSPACE, "foo");
	}

	@Test
	public void testBuildTemplateWorkspaceForce() throws Exception {
		File destinationDir = temporaryFolder.newFolder("existing-file");

		createNewFiles("foo", destinationDir);

		buildTemplateWithGradle(
			destinationDir, WorkspaceUtil.WORKSPACE, "forced", "--force");
	}

	@Test
	public void testBuildTemplateWorkspaceLegacyWarsDirProperty()
		throws Exception {

		File workspaceProjectDir = buildWorkspace(
			temporaryFolder, "gradle", "foows", getDefaultLiferayVersion(),
			mavenExecutor);

		File gradleProperties = new File(
			workspaceProjectDir, "gradle.properties");

		Assert.assertTrue(gradleProperties.exists());

		String configLine =
			System.lineSeparator() + "liferay.workspace.wars.dir=wars";

		Files.write(
			gradleProperties.toPath(), configLine.getBytes(),
			StandardOpenOption.APPEND);

		File warsProjectDir = buildTemplateWithGradle(
			new File(workspaceProjectDir, "wars"), "war-mvc-portlet",
			"foo-portlet");

		if (isBuildProjects()) {
			executeGradle(
				workspaceProjectDir, _gradleDistribution,
				":wars:foo-portlet" + GRADLE_TASK_PATH_BUILD);

			testExists(warsProjectDir, "build/libs/foo-portlet.war");
		}
	}

	@Test
	public void testBuildTemplateWorkspaceLocalProperties() throws Exception {
		assumeTrue(isBuildProjects());

		File workspaceProjectDir = buildWorkspace(
			temporaryFolder, "gradle", "foo", getDefaultLiferayVersion(),
			mavenExecutor);

		testExists(workspaceProjectDir, "gradle-local.properties");

		Properties gradleLocalProperties = new Properties();

		String homeDirName = "foo/bar/baz";
		String modulesDirName = "qux/quux";

		gradleLocalProperties.put("liferay.workspace.home.dir", homeDirName);
		gradleLocalProperties.put(
			"liferay.workspace.modules.dir", modulesDirName);

		File gradleLocalPropertiesFile = new File(
			workspaceProjectDir, "gradle-local.properties");

		try (FileOutputStream fileOutputStream = new FileOutputStream(
				gradleLocalPropertiesFile)) {

			gradleLocalProperties.store(fileOutputStream, null);
		}

		buildTemplateWithGradle(
			new File(workspaceProjectDir, modulesDirName), "", "foo-portlet");

		executeGradle(
			workspaceProjectDir, _gradleDistribution,
			":" + modulesDirName.replace('/', ':') + ":foo-portlet" +
				GRADLE_TASK_PATH_DEPLOY);

		testExists(
			workspaceProjectDir, homeDirName + "/osgi/modules/foo.portlet.jar");
	}

	@Test
	public void testBuildTemplateWorkspaceNodePackageManagerYarn()
		throws Exception {

		File workspaceProjectDir = buildWorkspace(
			temporaryFolder, "gradle", "foows", getDefaultLiferayVersion(),
			mavenExecutor);

		File gradleProperties = new File(
			workspaceProjectDir, "gradle.properties");

		Assert.assertTrue(gradleProperties.exists());

		String configLine =
			System.lineSeparator() +
				"liferay.workspace.node.package.manager=yarn";

		Files.write(
			gradleProperties.toPath(), configLine.getBytes(),
			StandardOpenOption.APPEND);

		File modulesProjectDir = buildTemplateWithGradle(
			new File(workspaceProjectDir, "modules"), "npm-react-portlet",
			"foo-portlet");

		if (isBuildProjects()) {
			executeGradle(
				workspaceProjectDir, _gradleDistribution,
				":modules:foo-portlet" + GRADLE_TASK_PATH_BUILD);

			testExists(modulesProjectDir, "build/libs/foo.portlet-1.0.0.jar");
			testExists(workspaceProjectDir, "yarn.lock");
			testExists(workspaceProjectDir, ".yarnrc");
		}
	}

	@Test
	public void testBuildTemplateWorkspaceWithPortlet() throws Exception {
		assumeTrue(isBuildProjects());

		File gradleWorkspaceProjectDir = buildWorkspace(
			temporaryFolder, "gradle", "withportlet",
			getDefaultLiferayVersion(), mavenExecutor);

		File gradleModulesDir = new File(gradleWorkspaceProjectDir, "modules");

		buildTemplateWithGradle(gradleModulesDir, "mvc-portlet", "foo-portlet");

		File mavenWorkspaceProjectDir = buildTemplateWithMaven(
			temporaryFolder, WorkspaceUtil.WORKSPACE, "withportlet", "com.test",
			mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceProjectDir, "modules");

		File mavenProjectDir = buildTemplateWithMaven(
			mavenWorkspaceProjectDir.getParentFile(), mavenModulesDir,
			"mvc-portlet", "foo-portlet", "com.test", mavenExecutor,
			"-DclassName=Foo", "-Dpackage=foo.portlet",
			"-DprojectType=workspace");

		executeGradle(
			gradleWorkspaceProjectDir, _gradleDistribution,
			":modules:foo-portlet" + GRADLE_TASK_PATH_BUILD);

		testExists(
			gradleModulesDir, "foo-portlet/build/libs/foo.portlet-1.0.0.jar");

		executeMaven(mavenProjectDir, mavenExecutor, MAVEN_GOAL_PACKAGE);

		testExists(mavenModulesDir, "foo-portlet/target/foo-portlet-1.0.0.jar");
	}

	@Test
	public void testCompareAntBndPluginVersions() throws Exception {
		assumeTrue(isBuildProjects());

		String template = "mvc-portlet";
		String name = "foo";

		String liferayVersion = getDefaultLiferayVersion();

		File workspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", liferayVersion,
			mavenExecutor);

		File modulesDir = new File(workspaceDir, "modules");

		buildTemplateWithGradle(modulesDir, template, name);

		Optional<String> gradleResult = executeGradle(
			workspaceDir, true, _gradleDistribution,
			":modules:" + name + GRADLE_TASK_PATH_BUILD);

		String gradleAntBndVersion = null;

		Matcher matcher = antBndPluginVersionPattern.matcher(
			gradleResult.get());

		if (matcher.matches()) {
			gradleAntBndVersion = matcher.group(1);
		}

		File mavenWorkspaceDir = buildWorkspace(
			temporaryFolder, "maven", "mavenWS", liferayVersion, mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

		File mavenProjectDir = buildTemplateWithMaven(
			mavenModulesDir, mavenModulesDir, template, name, "com.test",
			mavenExecutor, "-DclassName=foo");

		testContains(
			mavenProjectDir, "pom.xml",
			"<artifactId>com.liferay.ant.bnd</artifactId>\n\t\t\t\t\t\t" +
				"<version>" + gradleAntBndVersion);
	}

	@Test
	public void testComparePortalToolsBundleSupportPluginVersions()
		throws Exception {

		assumeTrue(isBuildProjects());

		File workspaceDir = buildWorkspace(
			temporaryFolder, getDefaultLiferayVersion());

		Optional<String> result = executeGradle(
			workspaceDir, true, _gradleDistribution, ":tasks");

		Matcher matcher = portalToolsBundleSupportVersionPattern.matcher(
			result.get());

		String portalToolsBundleSupportVersion = null;

		if (matcher.matches()) {
			portalToolsBundleSupportVersion = matcher.group(1);
		}

		File mavenWorkspaceDir = buildTemplateWithMaven(
			temporaryFolder, "workspace", "mavenWS", "com.test", mavenExecutor,
			"-DliferayVersion=" + getDefaultLiferayVersion());

		testContains(
			mavenWorkspaceDir, "pom.xml",
			"<artifactId>com.liferay.portal.tools.bundle.support</artifactId>" +
				"\n\t\t\t\t<version>" + portalToolsBundleSupportVersion);
	}

	@Test
	public void testSassCompilerMavenWorkspace() throws Exception {
		assumeTrue(isBuildProjects());

		File nativeSassWorkspaceDir = buildTemplateWithMaven(
			temporaryFolder, "workspace", "nativeSassMavenWS", "com.test",
			mavenExecutor, "-DliferayVersion=" + getDefaultLiferayVersion());

		File nativeSassModulesDir = new File(nativeSassWorkspaceDir, "modules");

		File nativeSassProjectDir = buildTemplateWithMaven(
			nativeSassWorkspaceDir.getParentFile(), nativeSassModulesDir,
			"mvc-portlet", "foo-portlet", "com.test", mavenExecutor,
			"-DclassName=Foo", "-Dpackage=foo.portlet",
			"-DprojectType=workspace");

		String nativeSassOutput = executeMaven(
			nativeSassProjectDir, mavenExecutor, MAVEN_GOAL_PACKAGE);

		Assert.assertTrue(
			nativeSassOutput,
			nativeSassOutput.contains("Using native Sass compiler"));

		File rubySassWorkspaceDir = buildTemplateWithMaven(
			temporaryFolder, "workspace", "rubySassMavenWS", "com.test",
			mavenExecutor, "-DliferayVersion=" + getDefaultLiferayVersion());

		File rubySassModulesDir = new File(rubySassWorkspaceDir, "modules");

		File rubySassProjectDir = buildTemplateWithMaven(
			rubySassWorkspaceDir.getParentFile(), rubySassModulesDir,
			"mvc-portlet", "foo-portlet", "com.test", mavenExecutor,
			"-DclassName=Foo", "-Dpackage=foo.portlet",
			"-DprojectType=workspace");

		File pomXmlFile = new File(rubySassProjectDir, "pom.xml");

		if (pomXmlFile.exists()) {
			editXml(
				pomXmlFile,
				document -> addCssBuilderConfigurationElement(
					document, "sassCompilerClassName", "ruby"));
		}

		String rubySassOutput = executeMaven(
			rubySassProjectDir, mavenExecutor, MAVEN_GOAL_PACKAGE);

		Assert.assertTrue(
			rubySassOutput,
			rubySassOutput.contains("Using Ruby Sass compiler"));

		File nativeSassOutputFile = testExists(
			nativeSassProjectDir, "target/foo-portlet-1.0.0.jar");
		File rubySassOutputFile = testExists(
			rubySassProjectDir, "target/foo-portlet-1.0.0.jar");

		testBundlesDiff(nativeSassOutputFile, rubySassOutputFile);
	}

	@Test
	public void testSassCompilerWorkspace() throws Exception {
		assumeTrue(isBuildProjects());

		String liferayVersion = getDefaultLiferayVersion();

		File nativeSassWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "nativeSassWorkspace", liferayVersion,
			mavenExecutor);

		writeGradlePropertiesInWorkspace(
			nativeSassWorkspaceDir, "liferay.workspace.product=portal-7.3-ga6");

		File nativeSassModulesDir = new File(nativeSassWorkspaceDir, "modules");

		File nativeSassProjectDir = buildTemplateWithGradle(
			nativeSassModulesDir, "mvc-portlet", "foo-portlet");

		String nativeSassOutput = String.valueOf(
			executeGradle(
				nativeSassWorkspaceDir, true, _gradleDistribution,
				":modules:foo-portlet" + GRADLE_TASK_PATH_BUILD));

		Assert.assertTrue(
			nativeSassOutput,
			nativeSassOutput.contains("Using native Sass compiler"));

		File rubySassWorkspaceDir = buildTemplateWithGradle(
			temporaryFolder, WorkspaceUtil.WORKSPACE, "rubySassWorkspace");

		writeGradlePropertiesInWorkspace(
			rubySassWorkspaceDir, "liferay.workspace.product=portal-7.3-ga6");

		writeGradlePropertiesInWorkspace(
			rubySassWorkspaceDir, "sass.compiler.class.name=ruby");

		File rubySassModulesDir = new File(rubySassWorkspaceDir, "modules");

		File rubySassProjectDir = buildTemplateWithGradle(
			rubySassModulesDir, "mvc-portlet", "foo-portlet");

		String rubySassOutput = String.valueOf(
			executeGradle(
				rubySassWorkspaceDir, true, _gradleDistribution,
				":modules:foo-portlet" + GRADLE_TASK_PATH_BUILD));

		Assert.assertTrue(
			rubySassOutput,
			rubySassOutput.contains("Using Ruby Sass compiler"));

		File nativeSassOutputFile = testExists(
			nativeSassProjectDir, "build/libs/foo.portlet-1.0.0.jar");
		File rubySassOutputFile = testExists(
			rubySassProjectDir, "build/libs/foo.portlet-1.0.0.jar");

		testBundlesDiff(nativeSassOutputFile, rubySassOutputFile);
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static URI _gradleDistribution;

}