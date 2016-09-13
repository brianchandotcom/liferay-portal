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

import aQute.bnd.main.bnd;

import com.liferay.project.templates.internal.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Lawrence Lee
 * @author Andrea Di Giorgi
 */
public class ProjectTemplatesTest {

	@BeforeClass
	public static void setUpClass() throws IOException {
		String gradleDistribution = System.getProperty("gradle.distribution");

		if (Validator.isNull(gradleDistribution)) {
			Properties properties = FileTestUtil.readProperties(
				"gradle-wrapper/gradle/wrapper/gradle-wrapper.properties");

			gradleDistribution = properties.getProperty("distributionUrl");
		}

		_gradleDistribution = URI.create(gradleDistribution);

		_httpProxyHost = System.getProperty("http.proxyHost");
		_httpProxyPort = System.getProperty("http.proxyPort");
		_repositoryUrl = System.getProperty("repository.url");
	}

	@Test
	public void testBuildTemplate() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			null, "hello-world-portlet");

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvcportlet", "hello-world-portlet",
			"-Dpackage=hello.world.portlet", "-DclassName=HelloWorld");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/hello/world/portlet/portlet/HelloWorldPortlet.java",
			"public class HelloWorldPortlet extends MVCPortlet {");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/hello.world.portlet-1.0.0.jar",
			"target/hello-world-portlet-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateActivator() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"activator", "bar-activator");

		File mavenProjectDir = _buildTemplateWithMaven(
			"activator", "bar-activator", "-Dpackage=bar.activator",
			"-DclassName=BarActivator");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir, "src/main/java/bar/activator/BarActivator.java",
			"public class BarActivator implements BundleActivator {");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/bar.activator-1.0.0.jar",
			"target/bar-activator-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateApi() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("api", "foo");

		File mavenProjectDir = _buildTemplateWithMaven(
			"api", "foo", "-Dpackage=foo", "-DclassName=Foo");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir, "src/main/java/foo/api/Foo.java",
			"public interface Foo");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo-1.0.0.jar",
			"target/foo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContentTargetingReport() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"contenttargetingreport", "foo-bar");

		File mavenProjectDir = _buildTemplateWithMaven(
			"contenttargetingreport", "foo-bar", "-Dpackage=foo.bar",
			"-DclassName=FooBar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/content/targeting/report/FooBarReport.java",
			"public class FooBarReport extends BaseJSPReport");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/foo-bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContentTargetingRule() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"contenttargetingrule", "foo-bar");

		File mavenProjectDir = _buildTemplateWithMaven(
			"contenttargetingrule", "foo-bar", "-Dpackage=foo.bar",
			"-DclassName=FooBar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/content/targeting/rule/FooBarRule.java",
			"public class FooBarRule extends BaseJSPRule");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/foo-bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContentTargetingTrackingAction()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"contenttargetingtrackingaction", "foo-bar");

		File mavenProjectDir = _buildTemplateWithMaven(
			"contenttargetingtrackingaction", "foo-bar", "-Dpackage=foo.bar",
			"-DclassName=FooBar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/content/targeting/tracking/action/" +
			"FooBarTrackingAction.java",
			"public class FooBarTrackingAction extends BaseJSPTrackingAction");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/foo-bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateControlMenuEntry() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"controlmenuentry", "foo-bar");

		File mavenProjectDir = _buildTemplateWithMaven(
			"controlmenuentry", "foo-bar", "-Dpackage=foo.bar",
			"-DclassName=FooBar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "src/main/java/foo/bar/control/menu/" +
			"FooBarProductNavigationControlMenuEntry.java",
			"public class FooBarProductNavigationControlMenuEntry",
			"extends BaseProductNavigationControlMenuEntry",
			"implements ProductNavigationControlMenuEntry");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/foo-bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateFragment() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"fragment", "loginhook", "--host-bundle-symbolic-name",
			"com.liferay.login.web", "--host-bundle-version", "1.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"fragment", "loginhook", "-Dpackage=loginhook",
			"-DhostBundleSymbolicName=com.liferay.login.web",
			"-DhostBundleVersion=1.0.0");

		_testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-SymbolicName: loginhook",
			"Fragment-Host: com.liferay.login.web;bundle-version=\"1.0.0\"");
		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/loginhook-1.0.0.jar",
			"target/loginhook-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortlet() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("mvcportlet", "foo");

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvcportlet", "foo", "-Dpackage=foo", "-DclassName=Foo");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir, "src/main/java/foo/portlet/FooPortlet.java",
			"public class FooPortlet extends MVCPortlet {");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo-1.0.0.jar",
			"target/foo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPackage() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"mvcportlet", "foo", "--package-name", "com.liferay.test");

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvcportlet", "foo", "-Dpackage=com.liferay.test",
			"-DclassName=Foo");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/portlet/FooPortlet.java",
			"public class FooPortlet extends MVCPortlet {");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/com.liferay.test-1.0.0.jar", "target/foo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPortletSuffix()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"mvcportlet", "portlet-portlet");

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvcportlet", "portlet-portlet", "-Dpackage=portlet.portlet",
			"-DclassName=Portlet");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/portlet/PortletPortlet.java",
			"public class PortletPortlet extends MVCPortlet {");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/portlet.portlet-1.0.0.jar",
			"target/portlet-portlet-1.0.0.jar");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateOnExistingDirectory() throws Exception {
		_buildTemplateWithGradle("activator", "dup-activator");
		_buildTemplateWithGradle("activator", "dup-activator");
	}

	@Test
	public void testBuildTemplatePanelApp() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"panelapp", "gradle.test", "--class-name", "Foo");

		File mavenProjectDir = _buildTemplateWithMaven(
			"panelapp", "gradle.test", "-Dpackage=gradle.test",
			"-DclassName=Foo");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(gradleProjectDir, "build.gradle");

		_testContains(
			gradleProjectDir,
			"src/main/java/gradle/test/application/list/FooPanelApp.java",
			"public class FooPanelApp extends BasePanelApp");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/gradle.test-1.0.0.jar", "target/gradle.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortlet() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet", "foo.test", "--class-name", "Foo");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet", "foo.test", "-Dpackage=foo.test", "-DclassName=Foo");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir, "src/main/java/foo/test/portlet/FooPortlet.java",
			"package foo.test.portlet;", "javax.portlet.display-name=foo.test",
			"public class FooPortlet extends GenericPortlet {",
			"printWriter.print(\"foo.test Portlet");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.test-1.0.0.jar",
			"target/foo.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletConfigurationIcon() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portletconfigurationicon", "icontest", "--package-name",
			"blade.test");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portletconfigurationicon", "icontest", "-Dpackage=blade.test",
			"-DclassName=Icontest");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		_testContains(
			gradleProjectDir,
			"src/main/java/blade/test/portlet/configuration/icon/" +
			"IcontestPortletConfigurationIcon.java",
			"public class IcontestPortletConfigurationIcon",
			"extends BasePortletConfigurationIcon");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/blade.test-1.0.0.jar", "target/icontest-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletProvider() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portletprovider", "provider.test");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portletprovider", "provider.test", "-Dpackage=provider.test",
			"-DclassName=ProviderTest");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(gradleProjectDir, "build.gradle");

		String filePath = "src/main/java/provider/test/constants/";
		String fileName = filePath + "ProviderTestPortletKeys.java";

		_testContains(
			gradleProjectDir, fileName, "package provider.test.constants;",
			"public class ProviderTestPortletKeys",
			"public static final String ProviderTest = \"ProviderTest\";");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/provider.test-1.0.0.jar",
			"target/provider.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletToolbarContributor() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlettoolbarcontributor", "toolbartest", "--package-name",
			"blade.test");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlettoolbarcontributor", "toolbartest", "-Dpackage=blade.test",
			"-DclassName=Toolbartest");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		_testContains(
			gradleProjectDir,
			"src/main/java/blade/test/portlet/toolbar/contributor/" +
			"ToolbartestPortletToolbarContributor.java",
			"public class ToolbartestPortletToolbarContributor",
			"implements PortletToolbarContributor");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/blade.test-1.0.0.jar", "target/toolbartest-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateService() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service", "servicepreaction", "-Dpackage=servicepreaction",
			"-DclassName=FooAction", "-DserviceClass=" +
			"com.liferay.portal.kernel.events.LifecycleAction");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		_writeServiceClass(gradleProjectDir);

		_executeGradle(gradleProjectDir, _GRADLE_BUILD_ARGS);

		File gradleBundleFile = _testExists(
			gradleProjectDir, "build/libs/servicepreaction-1.0.0.jar");

		_writeServiceClass(mavenProjectDir);

		_executeMaven(mavenProjectDir, _MAVEN_BUILD_ARGS);

		File mavenBundleFile = _testExists(
			mavenProjectDir, "target/servicepreaction-1.0.0.jar");

		_executeBndDiff(gradleBundleFile, mavenBundleFile);
	}

	@Test
	public void testBuildTemplateServiceBuilder() throws Exception {
		_testBuildTemplateServiceBuilder(
			"guestbook", "com.liferay.docs.guestbook");
	}

	@Test
	public void testBuildTemplateServiceBuilderWithDashes() throws Exception {
		_testBuildTemplateServiceBuilder(
			"backend-integration", "com.liferay.backend.integration");
	}

	@Test
	public void testBuildTemplateServiceWrapper() throws Exception {
		String serviceWrapperClassName =
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper";

		File gradleProjectDir = _buildTemplateWithGradle(
			"servicewrapper", "serviceoverride", "--service",
			serviceWrapperClassName);

		File mavenProjectDir = _buildTemplateWithMaven(
			"servicewrapper", "serviceoverride", "-Dpackage=serviceoverride",
			"-DclassName=Serviceoverride",
			"-DserviceWrapperClass=" + serviceWrapperClassName);

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		String serviceOverrideFilePath =
			"src/main/java/serviceoverride/Serviceoverride.java";
		String packageServiceOverride = "package serviceoverride;";
		String importStatement = "import " + serviceWrapperClassName + ";";
		String service = "service = ServiceWrapper.class";
		String classDecl =
			"public class Serviceoverride extends UserLocalServiceWrapper {";
		String constructorDecl = "public Serviceoverride() {";

		_testContains(
			gradleProjectDir, serviceOverrideFilePath, packageServiceOverride,
			importStatement, service, classDecl, constructorDecl);

		_executeGradle(gradleProjectDir, _GRADLE_BUILD_ARGS);

		File gradleBundleFile = _testExists(
			gradleProjectDir, "build/libs/serviceoverride-1.0.0.jar");

		_testContains(
			mavenProjectDir, serviceOverrideFilePath, packageServiceOverride,
			importStatement, service, classDecl, constructorDecl);

		_executeMaven(mavenProjectDir, _MAVEN_BUILD_ARGS);

		File mavenBundleFile = _testExists(
			mavenProjectDir, "target/serviceoverride-1.0.0.jar");

		_executeBndDiff(gradleBundleFile, mavenBundleFile);
	}

	@Test
	public void testBuildTemplateSimulationPanelEntry() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"simulationpanelentry", "simulator", "--package-name",
			"test.simulator");

		File mavenProjectDir = _buildTemplateWithMaven(
			"simulationpanelentry", "simulator", "-Dpackage=test.simulator",
			"-DclassName=Simulator");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		_testContains(
			gradleProjectDir, "src/main/java/test/simulator/application/list/" +
			"SimulatorSimulationPanelApp.java",
			"public class SimulatorSimulationPanelApp",
			"extends BaseJSPPanelApp");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/test.simulator-1.0.0.jar",
			"target/simulator-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateTemplateContextContributor() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"templatecontextcontributor", "blade-test");

		File mavenProjectDir = _buildTemplateWithMaven(
			"templatecontextcontributor", "blade-test", "-Dpackage=blade.test",
			"-DclassName=BladeTest");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		_testContains(
			gradleProjectDir, "src/main/java/blade/test/theme/contributor/" +
			"BladeTestTemplateContextContributor.java",
			"public class BladeTestTemplateContextContributor",
			"implements TemplateContextContributor");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/blade.test-1.0.0.jar", "target/blade-test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateWithPackageName() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			null, "barfoo", "--package-name", "foo.bar");

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvcportlet", "barfoo", "-Dpackage=foo.bar", "-DclassName=Barfoo");

		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-SymbolicName: foo.bar");
		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/barfoo-1.0.0.jar");
	}

	@Test
	public void testListTemplates() throws Exception {
		Set<String> templates = new HashSet<>(
			Arrays.asList(ProjectTemplates.getTemplates()));

		final Set<String> expectedTemplates = new HashSet<>();

		try (DirectoryStream<Path> directoryStream =
				FileTestUtil.getProjectTemplatesDirectoryStream()) {

			for (Path path : directoryStream) {
				Path fileNamePath = path.getFileName();

				String fileName = fileNamePath.toString();

				String template = fileName.substring(
					FileTestUtil.PROJECT_TEMPLATE_DIR_PREFIX.length());

				expectedTemplates.add(template);
			}
		}

		Assert.assertEquals(expectedTemplates, templates);
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private void _buildProjects(
			File gradleProjectDir, File mavenProjectDir, String gradleFileName,
			String mavenFileName)
		throws Exception {

		_executeGradle(gradleProjectDir, _GRADLE_BUILD_ARGS);

		_executeMaven(mavenProjectDir, _MAVEN_BUILD_ARGS);

		_verifyBuilds(
			gradleProjectDir, mavenProjectDir, gradleFileName, mavenFileName);
	}

	private File _buildTemplateWithGradle(
			String template, String name, String... args)
		throws Exception {

		File destinationDir = new File(temporaryFolder.getRoot(), "gradle");

		List<String> completeArgs = new ArrayList<>(args.length + 6);

		completeArgs.add("--destination");
		completeArgs.add(destinationDir.getPath());
		completeArgs.add("--name");
		completeArgs.add(name);

		if (Validator.isNotNull(template)) {
			completeArgs.add("--template");
			completeArgs.add(template);
		}

		for (String arg : args) {
			completeArgs.add(arg);
		}

		ProjectTemplates.main(
			completeArgs.toArray(new String[completeArgs.size()]));

		File gradleProjectDir = new File(destinationDir, name);

		_testExists(gradleProjectDir, ".gitignore");
		_testExists(gradleProjectDir, "build.gradle");
		_testExists(gradleProjectDir, "gradlew");
		_testExists(gradleProjectDir, "gradlew.bat");
		_testExists(gradleProjectDir, "gradle/wrapper/gradle-wrapper.jar");
		_testExists(
			gradleProjectDir, "gradle/wrapper/gradle-wrapper.properties");
		_testNotExists(gradleProjectDir, "pom.xml");

		return gradleProjectDir;
	}

	private File _buildTemplateWithMaven(
			String template, String name, String... args)
		throws Exception {

		File destinationDir = new File(temporaryFolder.getRoot(), "maven");

		List<String> completeArgs = new ArrayList<>();

		completeArgs.add("archetype:generate");
		completeArgs.add("-B");

		if (Validator.isNotNull(template)) {
			completeArgs.add(
				"-DarchetypeArtifactId=com.liferay.project.templates." +
					template);
		}

		completeArgs.add("-DarchetypeGroupId=com.liferay");
		completeArgs.add("-DarchetypeVersion=1.0.0");
		completeArgs.add("-DgroupId=com.test");
		completeArgs.add("-DartifactId=" + name);
		completeArgs.add("-Dversion=1.0.0");
		completeArgs.add("-DprojectType=standalone");

		for (String arg : args) {
			completeArgs.add(arg);
		}

		_executeMaven(destinationDir, completeArgs.toArray(new String[0]));

		File gradleProjectDir = new File(destinationDir, name);

		_testExists(gradleProjectDir, "pom.xml");
		_testNotExists(gradleProjectDir, "gradlew");
		_testNotExists(gradleProjectDir, "gradlew.bat");
		_testNotExists(gradleProjectDir, "gradle/wrapper/gradle-wrapper.jar");
		_testNotExists(
			gradleProjectDir, "gradle/wrapper/gradle-wrapper.properties");

		return gradleProjectDir;
	}

	private void _executeBndDiff(File gradleBundleFile, File mavenBundleFile)
		throws Exception {

		StringBuilder exclusions = new StringBuilder();

		exclusions.append("Archiver-Version, ");
		exclusions.append("Build-Jdk, ");
		exclusions.append("Built-By, ");
		exclusions.append("Javac-Debug, ");
		exclusions.append("Javac-Deprecation, ");
		exclusions.append("Javac-Encoding, ");
		exclusions.append("*pom.properties, ");
		exclusions.append("*pom.xml");

		String[] args = {
			"diff", "-i", exclusions.toString(), gradleBundleFile.getPath(),
			mavenBundleFile.getPath()
		};

		ByteArrayOutputStream output = new ByteArrayOutputStream();

		PrintStream ps = new PrintStream(output);

		System.setOut(ps);

		bnd main = new bnd();

		try {
			main.start(args);
		}
		finally {
			main.close();
		}

		Assert.assertEquals(
			"output jars do not match", "", new String(output.toByteArray()));
	}

	private void _executeGradle(
			File gradleProjectDir, String[] taskPath, String... testTaskPaths)
		throws IOException {

		if (Validator.isNotNull(_repositoryUrl)) {
			File buildGradleFile = new File(gradleProjectDir, "build.gradle");

			Path buildGradlePath = buildGradleFile.toPath();

			String buildGradle = FileTestUtil.read(buildGradlePath);

			buildGradle = buildGradle.replace(
				"\"" + _REPOSITORY_CDN_URL + "\"",
				"\"" + _repositoryUrl + "\"");

			Files.write(
				buildGradlePath, buildGradle.getBytes(StandardCharsets.UTF_8));
		}

		GradleRunner gradleRunner = GradleRunner.create();

		if (Validator.isNotNull(_httpProxyHost) &&
			Validator.isNotNull(_httpProxyPort)) {

			String[] arguments = new String[taskPath.length + 2];
			arguments[0] = "-Dhttp.proxyHost=" + _httpProxyHost;
			arguments[1] = "-Dhttp.proxyPort=" + _httpProxyPort;

			System.arraycopy(taskPath, 0, arguments, 2, taskPath.length);

			gradleRunner.withArguments(arguments);
		}
		else {
			gradleRunner.withArguments(taskPath);
		}

		gradleRunner.withGradleDistribution(_gradleDistribution);
		gradleRunner.withProjectDir(gradleProjectDir);

		BuildResult buildResult = gradleRunner.build();

		if (testTaskPaths.length == 0) {
			testTaskPaths = taskPath;
		}

		for (String testTaskPath : testTaskPaths) {
			BuildTask buildTask = buildResult.task(testTaskPath);

			Assert.assertNotNull(
				"Build task \"" + testTaskPath + "\" not found", buildTask);

			Assert.assertEquals(
				"Unexpected outcome for task \"" + buildTask.getPath() + "\"",
				TaskOutcome.SUCCESS, buildTask.getOutcome());
		}
	}

	private void _executeMaven(File gradleProjectDir, String[] args)
		throws Exception {

		File deps = new File("build/mavenEmbedderDeps.txt");

		String[] lines = FileTestUtil.readLines(deps);

		URL[] urls = new URL[lines.length];

		for (int i = 0; i < lines.length; i++) {
			File file = new File(lines[i]);

			URI uri = file.toURI();

			urls[i] = uri.toURL();
		}

		try (URLClassLoader classLoader = new URLClassLoader(urls, null)) {
			Class<?> mavenCLIClazz = classLoader.loadClass(
				"org.apache.maven.cli.MavenCli");

			Object mavenCli = mavenCLIClazz.newInstance();

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ByteArrayOutputStream errorOutput = new ByteArrayOutputStream();

			Method doMain = mavenCLIClazz.getMethod(
				"doMain", String[].class, String.class, PrintStream.class,
				PrintStream.class);

			Thread currentThread = Thread.currentThread();

			ClassLoader contextClassLoader =
				currentThread.getContextClassLoader();

			currentThread.setContextClassLoader(classLoader);

			try {
				PrintStream out = new PrintStream(output);
				PrintStream err = new PrintStream(errorOutput);

				String[] arguments = null;

				if (Validator.isNotNull(_httpProxyHost) &&
					Validator.isNotNull(_httpProxyPort)) {

					arguments = new String[args.length + 2];
					arguments[0] = "-Dhttp.proxyHost=" + _httpProxyHost;
					arguments[1] = "-Dhttp.proxyPort=" + _httpProxyPort;

					System.arraycopy(args, 0, arguments, 2, args.length);
				}
				else {
					arguments = args;
				}

				Integer retcode = (Integer)doMain.invoke(
					mavenCli, arguments, gradleProjectDir.getAbsolutePath(),
					out, err);

				Assert.assertEquals(
					new String(errorOutput.toByteArray()), 0,
					retcode.intValue());
			}
			finally {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	private void _testBuildTemplateServiceBuilder(
			String name, String packageName)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"servicebuilder", name, "--package-name", packageName);

		String apiProjectName = name + "-api";
		String serviceProjectName = name + "-service";

		_testContains(
			gradleProjectDir, "settings.gradle",
			"include \"" + apiProjectName + "\", \"" + serviceProjectName +
				"\"");

		_testContains(
			gradleProjectDir, apiProjectName + "/bnd.bnd", "Export-Package:\\",
			packageName + ".exception,\\", packageName + ".model,\\",
			packageName + ".service,\\", packageName + ".service.persistence");

		_testContains(
			gradleProjectDir, serviceProjectName + "/bnd.bnd",
			"Liferay-Service: true");

		_testContains(
			gradleProjectDir, serviceProjectName + "/build.gradle",
			"compileOnly project(\":" + apiProjectName + "\")");

		String[] tasks =
			new String[] {":" + serviceProjectName + ":buildService"};

		_executeGradle(gradleProjectDir, tasks);

		tasks =
			new String[] {":" + apiProjectName + ":build",
			":" + serviceProjectName +
				":build"
			};

		_executeGradle(gradleProjectDir, tasks);

		File gradleBundleApiFile = _testExists(
			gradleProjectDir, apiProjectName + "/build/libs/" +
			packageName + ".api-1.0.0.jar");

		File gradleBundleServiceFile = _testExists(
			gradleProjectDir, serviceProjectName +
			"/build/libs/" + packageName + ".service-1.0.0.jar");

		File mavenProjectDir = _buildTemplateWithMaven(
			"servicebuilder", name, "-Dpackage=" + packageName);

		_testContains(
			gradleProjectDir, "settings.gradle",
			"include \"" + apiProjectName + "\", \"" + serviceProjectName +
				"\"");

		_testContains(
			gradleProjectDir, apiProjectName + "/bnd.bnd", "Export-Package:\\",
			packageName + ".exception,\\", packageName + ".model,\\",
			packageName + ".service,\\", packageName + ".service.persistence");

		_testContains(
			gradleProjectDir, serviceProjectName + "/bnd.bnd",
			"Liferay-Service: true");

		_testContains(
			gradleProjectDir, serviceProjectName + "/build.gradle",
			"compileOnly project(\":" + apiProjectName + "\")");

		_executeMaven(
			new File(mavenProjectDir, serviceProjectName),
			new String[] {"-U", "liferay:build-service"});

		File gradleServiceProps = new File(
			gradleProjectDir,
			serviceProjectName + "/src/main/resources/service.properties");

		File mavenServiceProps = new File(
			mavenProjectDir,
			serviceProjectName + "/src/main/resources/service.properties");

		Files.copy(
			gradleServiceProps.toPath(), mavenServiceProps.toPath(),
			StandardCopyOption.REPLACE_EXISTING);

		_executeMaven(mavenProjectDir, _MAVEN_BUILD_ARGS);

		File mavenBundleApiFile = _testExists(
			mavenProjectDir,
			apiProjectName + "/target/" + name + "-api-1.0.0.jar");
		File mavenBundleServiceFile = _testExists(
			mavenProjectDir,
			serviceProjectName + "/target/" + name + "-service-1.0.0.jar");

		_executeBndDiff(gradleBundleApiFile, mavenBundleApiFile);
		_executeBndDiff(gradleBundleServiceFile, mavenBundleServiceFile);
	}

	private File _testContains(File dir, String fileName, String... strings)
		throws IOException {

		File file = _testExists(dir, fileName);

		String content = FileTestUtil.read(file.toPath());

		for (String s : strings) {
			Assert.assertTrue(
				"Not found in " + fileName + ": " + s, content.contains(s));
		}

		return file;
	}

	private File _testExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertTrue("Missing " + fileName, file.exists());

		return file;
	}

	private File _testNotExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertFalse("Unexpected " + fileName, file.exists());

		return file;
	}

	private void _verifyBuilds(
			File gradleProjectDir, File mavenProjectDir, String gradleFileName,
			String mavenFileName)
		throws Exception {

		File gradleBundleFile = _testExists(gradleProjectDir, gradleFileName);

		File mavenBundleFile = _testExists(mavenProjectDir, mavenFileName);

		_executeBndDiff(gradleBundleFile, mavenBundleFile);
	}

	private void _write(Writer writer, String... lines) throws IOException {
		for (String line : lines) {
			writer.write(line);
			writer.write(System.lineSeparator());
		}
	}

	private void _writeServiceClass(File gradleProjectDir) throws IOException {
		String importLine =
			"import com.liferay.portal.kernel.events.LifecycleAction;";
			String classLine =
				"public class FooAction implements LifecycleAction {";

			File actionJavaFile = _testContains(
				gradleProjectDir,
				"src/main/java/servicepreaction/FooAction.java",
				"package servicepreaction;", importLine,
				"service = LifecycleAction.class", classLine);

			Path actionJavaPath = actionJavaFile.toPath();

			List<String> lines = Files.readAllLines(
				actionJavaPath, StandardCharsets.UTF_8);

			try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
					actionJavaPath, StandardCharsets.UTF_8)) {

				for (String line : lines) {
					_write(bufferedWriter, line);

					if (line.equals(classLine)) {
						_write(
							bufferedWriter, "@Override",
							"public void processLifecycleEvent(",
							"LifecycleEvent lifecycleEvent)",
							"throws ActionException {", "System.out.println(",
							"\"login.event.pre=\" + lifecycleEvent);", "}");
					}
					else if (line.equals(importLine)) {
						_write(
							bufferedWriter,
							"import com.liferay.portal.kernel.events." +
								"LifecycleEvent;",
							"import com.liferay.portal.kernel.events." +
								"ActionException;");
					}
				}
			}
	}

	private static final String[] _GRADLE_BUILD_ARGS = new String[] {":build"};

	private static final String[] _MAVEN_BUILD_ARGS =
		new String[] {"-U", "package"};

	private static final String _REPOSITORY_CDN_URL =
		"https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups/" +
			"public";

	private static URI _gradleDistribution;
	private static String _httpProxyHost;
	private static String _httpProxyPort;
	private static String _repositoryUrl;

}