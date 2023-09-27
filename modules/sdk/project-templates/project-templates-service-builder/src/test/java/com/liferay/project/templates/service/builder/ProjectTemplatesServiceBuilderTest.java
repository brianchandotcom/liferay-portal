/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.service.builder;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.BaseProjectTemplatesTestCase;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.File;

import java.net.URI;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.Optional;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Gregory Amerson
 */
public class ProjectTemplatesServiceBuilderTest
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
	public void testBuildTemplateContentDTDVersionServiceBuilder70()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", "foo-bar", "--liferay-version", "7.0.6-2");

		testContains(
			gradleProjectDir, "foo-bar-service/service.xml",
			"liferay-service-builder_7_0_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionServiceBuilder71()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", "foo-bar", "--liferay-version", "7.1.3-1");

		testContains(
			gradleProjectDir, "foo-bar-service/service.xml",
			"liferay-service-builder_7_1_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionServiceBuilder72()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", "foo-bar", "--liferay-version", "7.2.1-1");

		testContains(
			gradleProjectDir, "foo-bar-service/service.xml",
			"liferay-service-builder_7_2_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionServiceBuilder73()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", "foo-bar", "--liferay-version", "7.3.5");

		testContains(
			gradleProjectDir, "foo-bar-service/service.xml",
			"liferay-service-builder_7_3_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionServiceBuilder74()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", "foo-bar", "--liferay-version", "7.4.3.36");

		testContains(
			gradleProjectDir, "foo-bar-service/service.xml",
			"liferay-service-builder_7_4_0.dtd");
	}

	@Test
	public void testBuildTemplateServiceBuilderCheckExports() throws Exception {
		String liferayVersion = getDefaultLiferayVersion();
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";
		String template = "service-builder";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", liferayVersion,
			mavenExecutor);

		String liferayWorkspaceProduct = getLiferayWorkspaceProduct(
			liferayVersion);

		if (liferayWorkspaceProduct != null) {
			writeGradlePropertiesInWorkspace(
				gradleWorkspaceDir,
				"liferay.workspace.product=" + liferayWorkspaceProduct);
		}

		File gradleWorkspaceModulesDir = new File(
			gradleWorkspaceDir, "modules");

		File gradleProjectDir = buildTemplateWithGradle(
			gradleWorkspaceModulesDir, template, name, "--liferay-version",
			liferayVersion, "--package-name", packageName);

		File gradleServiceXml = new File(
			new File(gradleProjectDir, name + "-service"), "service.xml");

		Consumer<Document> consumer = document -> {
			Element documentElement = document.getDocumentElement();

			documentElement.setAttribute("package-path", "com.liferay.test");
		};

		editXml(gradleServiceXml, consumer);

		File mavenWorkspaceDir = buildWorkspace(
			temporaryFolder, "maven", "mavenWS", liferayVersion, mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

		File mavenProjectDir = buildTemplateWithMaven(
			mavenModulesDir, mavenModulesDir, template, name, "com.test",
			mavenExecutor, "-DliferayVersion=" + liferayVersion,
			"-Dpackage=" + packageName);

		File mavenServiceXml = new File(
			new File(mavenProjectDir, name + "-service"), "service.xml");

		editXml(mavenServiceXml, consumer);

		testContains(
			gradleProjectDir, name + "-api/bnd.bnd", "Export-Package:\\",
			packageName + ".exception,\\", packageName + ".model,\\",
			packageName + ".service,\\", packageName + ".service.persistence");

		if (isBuildProjects()) {
			Optional<String> stdOutputOptional = executeGradle(
				gradleWorkspaceDir, false, true, _gradleDistribution,
				":modules:" + name + ":" + name + "-service" +
					GRADLE_TASK_PATH_BUILD);

			Assert.assertTrue(stdOutputOptional.isPresent());

			String gradleOutput = stdOutputOptional.get();

			Assert.assertTrue(
				"Expected gradle output to include build error. " +
					gradleOutput,
				gradleOutput.contains("Exporting an empty package"));

			String mavenOutput = executeMaven(
				mavenProjectDir, true, mavenExecutor, MAVEN_GOAL_PACKAGE);

			Assert.assertTrue(
				"Expected maven output to include build error. " + mavenOutput,
				mavenOutput.contains("Exporting an empty package"));
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderWorkspaceRelativePath()
		throws Exception {

		String liferayVersion = getDefaultLiferayVersion();
		String name = "sample";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", liferayVersion,
			mavenExecutor);

		String liferayWorkspaceProduct = getLiferayWorkspaceProduct(
			liferayVersion);

		if (liferayWorkspaceProduct != null) {
			writeGradlePropertiesInWorkspace(
				gradleWorkspaceDir,
				"liferay.workspace.product=" + liferayWorkspaceProduct);
		}

		File gradlePropertiesFile = new File(
			gradleWorkspaceDir + "gradle.properties");

		Files.deleteIfExists(gradlePropertiesFile.toPath());

		buildTemplateWithGradle(
			gradleWorkspaceDir, "service-builder", name, "--liferay-version",
			liferayVersion);

		testContains(
			gradleWorkspaceDir, name + "/" + name + "-service/build.gradle",
			"project(\":" + name + ":" + name + "-api");
	}

	@Test
	public void testBuildTemplateServiceBuilderWorkspaceUAD() throws Exception {
		String dependencyInjector = "ds";
		String liferayVersion = "7.4.3.36";
		String name = "guestbook";
		String packageName = "com.test.guestbook";
		String template = "service-builder";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", liferayVersion,
			mavenExecutor);

		String liferayWorkspaceProduct = getLiferayWorkspaceProduct(
			liferayVersion);

		if (liferayWorkspaceProduct != null) {
			writeGradlePropertiesInWorkspace(
				gradleWorkspaceDir,
				"liferay.workspace.product=" + liferayWorkspaceProduct);
		}

		File modulesDir = new File(gradleWorkspaceDir, "modules");

		File gradleProjectDir = buildTemplateWithGradle(
			modulesDir, template, name, "--add-ons", "true",
			"--dependency-injector", dependencyInjector, "--liferay-version",
			liferayVersion, "--package-name", packageName);

		File gradleUADModuleDir = new File(gradleProjectDir, name + "-uad");

		testExists(gradleUADModuleDir, "bnd.bnd");
		testExists(gradleUADModuleDir, "build.gradle");

		File mavenWorkspaceDir = buildWorkspace(
			temporaryFolder, "maven", "mavenWS", liferayVersion, mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

		File mavenProjectDir = buildTemplateWithMaven(
			mavenModulesDir, mavenModulesDir, template, name, "com.test",
			mavenExecutor, "-DaddOns=true",
			"-DdependencyInjector=" + dependencyInjector,
			"-DliferayVersion=" + liferayVersion, "-Dpackage=" + packageName);

		File mavenUADModuleDir = new File(mavenProjectDir, name + "-uad");

		testExists(mavenUADModuleDir, "bnd.bnd");
		testExists(mavenUADModuleDir, "pom.xml");

		if (isBuildProjects()) {
			String content = FileTestUtil.read(
				BaseProjectTemplatesTestCase.class.getClassLoader(),
				"com/liferay/project/templates/service/builder/dependencies" +
					"/service.xml");

			Path tempPath = Files.createTempFile("service", "xml");

			Files.write(tempPath, content.getBytes());

			Path gradleServiceXmlPath = Paths.get(
				gradleProjectDir.getPath(), name + "-service/service.xml");
			Path mavenServiceXmlPath = Paths.get(
				mavenProjectDir.getPath(), name + "-service/service.xml");

			Files.copy(
				tempPath, gradleServiceXmlPath,
				StandardCopyOption.REPLACE_EXISTING);
			Files.copy(
				tempPath, mavenServiceXmlPath,
				StandardCopyOption.REPLACE_EXISTING);

			String projectPath = ":modules:" + name;

			testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleWorkspaceDir, name,
				packageName, projectPath, _gradleDistribution, mavenExecutor);

			executeGradle(
				gradleWorkspaceDir, _gradleDistribution,
				projectPath + ":" + name + "-uad" + GRADLE_TASK_PATH_BUILD);

			File gradleUADBundleFile = testExists(
				gradleUADModuleDir,
				"/build/libs/com.test.guestbook.uad-1.0.0.jar");
			File mavenUADBundleFile = testExists(
				mavenUADModuleDir, "/target/guestbook-uad-1.0.0.jar");

			testBundlesDiff(gradleUADBundleFile, mavenUADBundleFile);
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateServiceBuilderWorkspaceUADUnsupported()
		throws Exception {

		String liferayVersion = "7.0.6-2";
		String name = "sample";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", liferayVersion,
			mavenExecutor);

		String liferayWorkspaceProduct = getLiferayWorkspaceProduct(
			liferayVersion);

		if (liferayWorkspaceProduct != null) {
			writeGradlePropertiesInWorkspace(
				gradleWorkspaceDir,
				"liferay.workspace.product=" + liferayWorkspaceProduct);
		}

		buildTemplateWithGradle(
			gradleWorkspaceDir, "service-builder", name, "--add-ons", "true",
			"--liferay-version", liferayVersion);
	}

	@Test
	public void testCompareServiceBuilderPluginVersions() throws Exception {
		Assume.assumeTrue(isBuildProjects());

		String liferayVersion = getDefaultLiferayVersion();
		String name = "sample";
		String packageName = "com.test.sample";
		String serviceProjectName = name + "-service";
		String template = "service-builder";

		File gradleWorkspaceDir = buildWorkspace(
			temporaryFolder, "gradle", "gradleWS", liferayVersion,
			mavenExecutor);

		String liferayWorkspaceProduct = getLiferayWorkspaceProduct(
			liferayVersion);

		if (liferayWorkspaceProduct != null) {
			writeGradlePropertiesInWorkspace(
				gradleWorkspaceDir,
				"liferay.workspace.product=" + liferayWorkspaceProduct);
		}

		File gradleWorkspaceModulesDir = new File(
			gradleWorkspaceDir, "modules");

		buildTemplateWithGradle(
			gradleWorkspaceModulesDir, template, name, "--liferay-version",
			liferayVersion, "--package-name", packageName);

		Optional<String> gradleResultOptional = executeGradle(
			gradleWorkspaceDir, true, _gradleDistribution,
			":modules:" + name + ":" + serviceProjectName + ":dependencies");

		String gradleServiceBuilderVersion = null;

		Matcher matcher = _serviceBuilderVersionPattern.matcher(
			gradleResultOptional.get());

		if (matcher.matches()) {
			gradleServiceBuilderVersion = matcher.group(1);
		}

		File mavenWorkspaceDir = buildWorkspace(
			temporaryFolder, "maven", "mavenWS", liferayVersion, mavenExecutor);

		File mavenModulesDir = new File(mavenWorkspaceDir, "modules");

		File mavenProjectDir = buildTemplateWithMaven(
			mavenModulesDir, mavenModulesDir, template, name, "com.test",
			mavenExecutor, "-Dpackage=" + packageName);

		String mavenResult = executeMaven(
			new File(mavenProjectDir, serviceProjectName), mavenExecutor,
			MAVEN_GOAL_BUILD_SERVICE);

		matcher = _serviceBuilderVersionPattern.matcher(mavenResult);

		String mavenServiceBuilderVersion = null;

		if (matcher.matches()) {
			mavenServiceBuilderVersion = matcher.group(1);
		}

		Assert.assertEquals(
			"com.liferay.portal.tools.service.builder versions do not match",
			gradleServiceBuilderVersion, mavenServiceBuilderVersion);
	}

	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	private File _buildTemplateWithGradle(
			String template, String name, String... args)
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("gradle");

		return buildTemplateWithGradle(destinationDir, template, name, args);
	}

	private static URI _gradleDistribution;
	private static final Pattern _serviceBuilderVersionPattern =
		Pattern.compile(
			".*service\\.builder:([0-9]+\\.[0-9]+\\.[0-9]+).*",
			Pattern.DOTALL | Pattern.MULTILINE);

}