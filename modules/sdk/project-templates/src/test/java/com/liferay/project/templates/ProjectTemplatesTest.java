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

import com.liferay.project.templates.internal.util.FileUtil;
import com.liferay.project.templates.internal.util.Validator;
import com.liferay.project.templates.internal.util.WorkspaceUtil;
import com.liferay.project.templates.util.FileTestUtil;
import com.liferay.project.templates.util.StringTestUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.lang.reflect.Method;

import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * @author Lawrence Lee
 * @author Andrea Di Giorgi
 */
public class ProjectTemplatesTest {

	@ClassRule
	public static final TemporaryFolder testCaseTemporaryFolder =
		new TemporaryFolder();

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

		List<URL> mavenEmbedderDependencyURLs = new ArrayList<>();

		try (BufferedReader bufferedReader = Files.newBufferedReader(
				Paths.get("build", "maven-embedder-dependencies.txt"))) {

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				Path path = Paths.get(line);

				URI uri = path.toUri();

				mavenEmbedderDependencyURLs.add(uri.toURL());
			}
		}

		_mavenEmbedderDependencyURLs = mavenEmbedderDependencyURLs.toArray(
			new URL[mavenEmbedderDependencyURLs.size()]);

		_projectTemplateVersions = FileTestUtil.readProperties(
			Paths.get("build", "project-template-versions.properties"));

		_repositoryUrl = System.getProperty("repository.url");

		_createMavenSettingsXmlFile();
	}

	@Test
	public void testBuildTemplate() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			null, "hello-world-portlet");

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

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvc-portlet", "hello-world-portlet", "-DclassName=HelloWorld",
			"-Dpackage=hello.world.portlet");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/hello.world.portlet-1.0.0.jar",
			"target/hello-world-portlet-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateActivator() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"activator", "bar-activator");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir, "src/main/java/bar/activator/BarActivator.java",
			"public class BarActivator implements BundleActivator {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"activator", "bar-activator", "-DclassName=BarActivator",
			"-Dpackage=bar.activator");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/bar.activator-1.0.0.jar",
			"target/bar-activator-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateApi() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("api", "foo");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir, "src/main/java/foo/api/Foo.java",
			"public interface Foo");

		File mavenProjectDir = _buildTemplateWithMaven(
			"api", "foo", "-DclassName=Foo", "-Dpackage=foo");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo-1.0.0.jar",
			"target/foo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContentTargetingReport() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-report", "foo-bar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/content/targeting/report/FooBarReport.java",
			"public class FooBarReport extends BaseJSPReport");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-report", "foo-bar", "-DclassName=FooBar",
			"-Dpackage=foo.bar");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/foo-bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContentTargetingRule() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-rule", "foo-bar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/content/targeting/rule/FooBarRule.java",
			"public class FooBarRule extends BaseJSPRule");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-rule", "foo-bar", "-DclassName=FooBar",
			"-Dpackage=foo.bar");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/foo-bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContentTargetingTrackingAction()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-tracking-action", "foo-bar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/content/targeting/tracking/action" +
				"/FooBarTrackingAction.java",
			"public class FooBarTrackingAction extends BaseJSPTrackingAction");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-tracking-action", "foo-bar",
			"-DclassName=FooBar", "-Dpackage=foo.bar");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/foo-bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateControlMenuEntry() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"control-menu-entry", "foo-bar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/control/menu" +
				"/FooBarProductNavigationControlMenuEntry.java",
			"public class FooBarProductNavigationControlMenuEntry",
			"extends BaseProductNavigationControlMenuEntry",
			"implements ProductNavigationControlMenuEntry");

		File mavenProjectDir = _buildTemplateWithMaven(
			"control-menu-entry", "foo-bar", "-DclassName=FooBar",
			"-Dpackage=foo.bar");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/foo-bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateFragment() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"fragment", "loginhook", "--host-bundle-symbolic-name",
			"com.liferay.login.web", "--host-bundle-version", "1.0.0");

		_testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-SymbolicName: loginhook",
			"Fragment-Host: com.liferay.login.web;bundle-version=\"1.0.0\"");
		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		File mavenProjectDir = _buildTemplateWithMaven(
			"fragment", "loginhook",
			"-DhostBundleSymbolicName=com.liferay.login.web",
			"-DhostBundleVersion=1.0.0", "-Dpackage=loginhook");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/loginhook-1.0.0.jar",
			"target/loginhook-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortlet() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("mvc-portlet", "foo");

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

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvc-portlet", "foo", "-DclassName=Foo", "-Dpackage=foo");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo-1.0.0.jar",
			"target/foo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPackage() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"mvc-portlet", "foo", "--package-name", "com.liferay.test");

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

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvc-portlet", "foo", "-DclassName=Foo",
			"-Dpackage=com.liferay.test");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/com.liferay.test-1.0.0.jar", "target/foo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPortletSuffix()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"mvc-portlet", "portlet-portlet");

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

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvc-portlet", "portlet-portlet", "-DclassName=Portlet",
			"-Dpackage=portlet.portlet");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/portlet.portlet-1.0.0.jar",
			"target/portlet-portlet-1.0.0.jar");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateOnExistingDirectory() throws Exception {
		File destinationDir = temporaryFolder.newFolder("gradle");

		_buildTemplateWithGradle(destinationDir, "activator", "dup-activator");
		_buildTemplateWithGradle(destinationDir, "activator", "dup-activator");
	}

	@Test
	public void testBuildTemplatePanelApp() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"panel-app", "gradle.test", "--class-name", "Foo");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(gradleProjectDir, "build.gradle");

		_testContains(
			gradleProjectDir,
			"src/main/java/gradle/test/application/list/FooPanelApp.java",
			"public class FooPanelApp extends BasePanelApp");

		File mavenProjectDir = _buildTemplateWithMaven(
			"panel-app", "gradle.test", "-DclassName=Foo",
			"-Dpackage=gradle.test");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/gradle.test-1.0.0.jar", "target/gradle.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortlet() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet", "foo.test", "--class-name", "Foo");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir, "src/main/java/foo/test/portlet/FooPortlet.java",
			"package foo.test.portlet;", "javax.portlet.display-name=foo.test",
			"public class FooPortlet extends GenericPortlet {",
			"printWriter.print(\"foo.test Portlet");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet", "foo.test", "-DclassName=Foo", "-Dpackage=foo.test");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.test-1.0.0.jar",
			"target/foo.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletConfigurationIcon() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-configuration-icon", "icontest", "--package-name",
			"blade.test");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/blade/test/portlet/configuration/icon" +
				"/IcontestPortletConfigurationIcon.java",
			"public class IcontestPortletConfigurationIcon",
			"extends BasePortletConfigurationIcon");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-configuration-icon", "icontest", "-DclassName=Icontest",
			"-Dpackage=blade.test");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/blade.test-1.0.0.jar", "target/icontest-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletProvider() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-provider", "provider.test");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(gradleProjectDir, "build.gradle");

		_testContains(
			gradleProjectDir,
			"src/main/java/provider/test/constants" +
				"/ProviderTestPortletKeys.java",
			"package provider.test.constants;",
			"public class ProviderTestPortletKeys",
			"public static final String ProviderTest = \"ProviderTest\";");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-provider", "provider.test", "-DclassName=ProviderTest",
			"-Dpackage=provider.test");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/provider.test-1.0.0.jar",
			"target/provider.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletToolbarContributor() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-toolbar-contributor", "toolbartest", "--package-name",
			"blade.test");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/blade/test/portlet/toolbar/contributor" +
				"/ToolbartestPortletToolbarContributor.java",
			"public class ToolbartestPortletToolbarContributor",
			"implements PortletToolbarContributor");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-toolbar-contributor", "toolbartest",
			"-DclassName=Toolbartest", "-Dpackage=blade.test");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/blade.test-1.0.0.jar", "target/toolbartest-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateService() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		_writeServiceClass(gradleProjectDir);

		File mavenProjectDir = _buildTemplateWithMaven(
			"service", "servicepreaction", "-DclassName=FooAction",
			"-Dpackage=servicepreaction",
			"-DserviceClass=com.liferay.portal.kernel.events.LifecycleAction");

		_writeServiceClass(mavenProjectDir);

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/servicepreaction-1.0.0.jar",
			"target/servicepreaction-1.0.0.jar");
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
		File gradleProjectDir = _buildTemplateWithGradle(
			"service-wrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/serviceoverride/Serviceoverride.java",
			"package serviceoverride;",
			"import com.liferay.portal.kernel.service.UserLocalServiceWrapper;",
			"service = ServiceWrapper.class",
			"public class Serviceoverride extends UserLocalServiceWrapper {",
			"public Serviceoverride() {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-wrapper", "serviceoverride", "-DclassName=Serviceoverride",
			"-Dpackage=serviceoverride",
			"-DserviceWrapperClass=" +
				"com.liferay.portal.kernel.service.UserLocalServiceWrapper");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/serviceoverride-1.0.0.jar",
			"target/serviceoverride-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateSimulationPanelEntry() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"simulation-panel-entry", "simulator", "--package-name",
			"test.simulator");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/test/simulator/application/list" +
				"/SimulatorSimulationPanelApp.java",
			"public class SimulatorSimulationPanelApp",
			"extends BaseJSPPanelApp");

		File mavenProjectDir = _buildTemplateWithMaven(
			"simulation-panel-entry", "simulator", "-DclassName=Simulator",
			"-Dpackage=test.simulator");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/test.simulator-1.0.0.jar",
			"target/simulator-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateTemplateContextContributor() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"template-context-contributor", "blade-test");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		_testContains(
			gradleProjectDir,
			"src/main/java/blade/test/theme/contributor" +
				"/BladeTestTemplateContextContributor.java",
			"public class BladeTestTemplateContextContributor",
			"implements TemplateContextContributor");

		File mavenProjectDir = _buildTemplateWithMaven(
			"template-context-contributor", "blade-test",
			"-DclassName=BladeTest", "-Dpackage=blade.test");

		_buildProjects(
			gradleProjectDir, mavenProjectDir,
			"build/libs/blade.test-1.0.0.jar", "target/blade-test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateWithPackageName() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"", "barfoo", "--package-name", "foo.bar");

		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-SymbolicName: foo.bar");
		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvc-portlet", "barfoo", "-DclassName=Barfoo", "-Dpackage=foo.bar");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, "build/libs/foo.bar-1.0.0.jar",
			"target/barfoo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateWorkspace() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "foows");

		_testExists(workspaceProjectDir, "configs/dev/portal-ext.properties");
		_testExists(workspaceProjectDir, "gradle.properties");

		_testContains(
			workspaceProjectDir, "settings.gradle", "version: \"1.0.40\"");

		File moduleProjectDir = _buildTemplateWithGradle(
			new File(workspaceProjectDir, "modules"), "", "foo-portlet");

		_testNotContains(
			moduleProjectDir, "build.gradle", "buildscript", "repositories");

		_executeGradle(
			workspaceProjectDir,
			":modules:foo-portlet" + _GRADLE_TASK_PATH_BUILD);

		_testExists(moduleProjectDir, "build/libs/foo.portlet-1.0.0.jar");
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

				if (!template.equals(WorkspaceUtil.WORKSPACE)) {
					expectedTemplates.add(template);
				}
			}
		}

		Assert.assertEquals(expectedTemplates, templates);
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static void _createMavenSettingsXmlFile() throws IOException {
		boolean mirrors = false;
		boolean proxies = false;

		if (Validator.isNotNull(_repositoryUrl)) {
			mirrors = true;
		}

		if (Validator.isNotNull(_httpProxyHost) &&
			Validator.isNotNull(_httpProxyPort)) {

			proxies = true;
		}

		if (!mirrors && !proxies) {
			_mavenSettingsXmlFile = null;

			return;
		}

		String mavenSettingsXml = FileTestUtil.read(
			"com/liferay/project/templates/dependencies" +
				"/maven_settings_xml.tmpl");

		if (mirrors) {
			mavenSettingsXml = mavenSettingsXml.replace(
				"[$REPOSITORY_URL$]", _repositoryUrl);
		}
		else {
			mavenSettingsXml = mavenSettingsXml.replaceFirst(
				"<mirrors>[\\s\\S]+<\\/mirrors>", "");
		}

		if (proxies) {
			mavenSettingsXml = mavenSettingsXml.replace(
				"[$HTTP_PROXY_HOST$]", _httpProxyHost);
			mavenSettingsXml = mavenSettingsXml.replace(
				"[$HTTP_PROXY_PORT$]", _httpProxyPort);
		}
		else {
			mavenSettingsXml = mavenSettingsXml.replaceFirst(
				"<proxies>[\\s\\S]+<\\/proxies>", "");
		}

		_mavenSettingsXmlFile = testCaseTemporaryFolder.newFile("settings.xml");

		Files.write(
			_mavenSettingsXmlFile.toPath(),
			mavenSettingsXml.getBytes(StandardCharsets.UTF_8));
	}

	private void _buildProjects(
			File gradleProjectDir, File mavenProjectDir,
			String gradleBundleFileName, String mavenBundleFileName)
		throws Exception {

		_executeGradle(gradleProjectDir, _GRADLE_TASK_PATH_BUILD);

		File gradleBundleFile = _testExists(
			gradleProjectDir, gradleBundleFileName);

		_executeMaven(mavenProjectDir, _MAVEN_GOAL_PACKAGE);

		File mavenBundleFile = _testExists(
			mavenProjectDir, mavenBundleFileName);

		_testBundlesDiff(gradleBundleFile, mavenBundleFile);
	}

	private File _buildTemplateWithGradle(
			File destinationDir, String template, String name, String... args)
		throws Exception {

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

		File projectDir = new File(destinationDir, name);

		_testExists(projectDir, ".gitignore");
		_testExists(projectDir, "build.gradle");

		if (WorkspaceUtil.isWorkspace(destinationDir)) {
			for (String fileName : _STANDALONE_ONLY_FILE_NAMES) {
				_testNotExists(projectDir, fileName);
			}
		}
		else {
			for (String fileName : _STANDALONE_ONLY_FILE_NAMES) {
				_testExists(projectDir, fileName);
			}
		}

		_testNotExists(projectDir, "pom.xml");

		return projectDir;
	}

	private File _buildTemplateWithGradle(
			String template, String name, String... args)
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("gradle");

		return _buildTemplateWithGradle(destinationDir, template, name, args);
	}

	private File _buildTemplateWithMaven(
			String template, String name, String... args)
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("maven");

		List<String> completeArgs = new ArrayList<>();

		completeArgs.add("archetype:generate");
		completeArgs.add("--batch-mode");

		if (Validator.isNotNull(template)) {
			completeArgs.add(
				"-DarchetypeArtifactId=com.liferay.project.templates." +
					template.replace('-', '.'));
		}

		String projectTemplateVersion = _projectTemplateVersions.getProperty(
			template);

		Assert.assertTrue(
			"Unable to get project template version",
			Validator.isNotNull(projectTemplateVersion));

		completeArgs.add("-DarchetypeGroupId=com.liferay");
		completeArgs.add("-DarchetypeVersion=" + projectTemplateVersion);
		completeArgs.add("-DgroupId=com.test");
		completeArgs.add("-DartifactId=" + name);
		completeArgs.add("-Dversion=1.0.0");
		completeArgs.add("-DprojectType=standalone");

		for (String arg : args) {
			completeArgs.add(arg);
		}

		_executeMaven(destinationDir, completeArgs.toArray(new String[0]));

		File projectDir = new File(destinationDir, name);

		_testExists(projectDir, "pom.xml");
		_testNotExists(projectDir, "gradlew");
		_testNotExists(projectDir, "gradlew.bat");
		_testNotExists(projectDir, "gradle/wrapper/gradle-wrapper.jar");
		_testNotExists(projectDir, "gradle/wrapper/gradle-wrapper.properties");

		return projectDir;
	}

	private void _executeGradle(File projectDir, String... taskPaths)
		throws IOException {

		if (Validator.isNotNull(_repositoryUrl)) {
			File buildGradleFile = new File(projectDir, "build.gradle");

			Path buildGradlePath = buildGradleFile.toPath();

			String buildGradle = FileUtil.read(buildGradlePath);

			buildGradle = buildGradle.replace(
				"\"" + _REPOSITORY_CDN_URL + "\"",
				"\"" + _repositoryUrl + "\"");

			Files.write(
				buildGradlePath, buildGradle.getBytes(StandardCharsets.UTF_8));
		}

		GradleRunner gradleRunner = GradleRunner.create();

		if (Validator.isNotNull(_httpProxyHost) &&
			Validator.isNotNull(_httpProxyPort)) {

			String[] arguments = new String[taskPaths.length + 2];

			arguments[0] = "-Dhttp.proxyHost=" + _httpProxyHost;
			arguments[1] = "-Dhttp.proxyPort=" + _httpProxyPort;

			System.arraycopy(taskPaths, 0, arguments, 2, taskPaths.length);

			gradleRunner.withArguments(arguments);
		}
		else {
			gradleRunner.withArguments(taskPaths);
		}

		gradleRunner.withGradleDistribution(_gradleDistribution);
		gradleRunner.withProjectDir(projectDir);

		BuildResult buildResult = gradleRunner.build();

		for (String taskPath : taskPaths) {
			BuildTask buildTask = buildResult.task(taskPath);

			Assert.assertNotNull(
				"Build task \"" + taskPath + "\" not found", buildTask);

			Assert.assertEquals(
				"Unexpected outcome for task \"" + buildTask.getPath() + "\"",
				TaskOutcome.SUCCESS, buildTask.getOutcome());
		}
	}

	private void _executeMaven(File projectDir, String... args)
		throws Exception {

		List<String> completeArgs = new ArrayList<>();

		if (_mavenSettingsXmlFile != null) {
			completeArgs.add(
				"--settings=" + _mavenSettingsXmlFile.getAbsolutePath());
		}

		completeArgs.add("--update-snapshots");

		for (String arg : args) {
			completeArgs.add(arg);
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try (URLClassLoader urlClassLoader = new URLClassLoader(
				_mavenEmbedderDependencyURLs, null)) {

			currentThread.setContextClassLoader(urlClassLoader);

			Class<?> mavenCliClass = urlClassLoader.loadClass(
				"org.apache.maven.cli.MavenCli");

			Method doMainMethod = mavenCliClass.getMethod(
				"doMain", String[].class, String.class, PrintStream.class,
				PrintStream.class);

			System.setProperty(
				"maven.multiModuleProjectDirectory",
				projectDir.getAbsolutePath());

			Object mavenCli = mavenCliClass.newInstance();

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ByteArrayOutputStream errorStream = new ByteArrayOutputStream();

			Integer exitCode = (Integer)doMainMethod.invoke(
				mavenCli, completeArgs.toArray(new String[completeArgs.size()]),
				projectDir.getAbsolutePath(),
				new PrintStream(outputStream, true),
				new PrintStream(errorStream, true));

			Assert.assertEquals(errorStream.toString(), 0, exitCode.intValue());
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private void _testBuildTemplateServiceBuilder(
			String name, String packageName)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName);

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

		_executeGradle(
			gradleProjectDir,
			":" + serviceProjectName + _GRADLE_TASK_PATH_BUILD_SERVICE);

		_executeGradle(
			gradleProjectDir, ":" + apiProjectName + _GRADLE_TASK_PATH_BUILD,
			":" + serviceProjectName + _GRADLE_TASK_PATH_BUILD);

		File gradleApiBundleFile = _testExists(
			gradleProjectDir, apiProjectName + "/build/libs/" +
			packageName + ".api-1.0.0.jar");

		File gradleServiceBundleFile = _testExists(
			gradleProjectDir, serviceProjectName +
			"/build/libs/" + packageName + ".service-1.0.0.jar");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "-Dpackage=" + packageName);

		_executeMaven(
			new File(mavenProjectDir, serviceProjectName),
			_MAVEN_GOAL_BUILD_SERVICE);

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

		_executeMaven(mavenProjectDir, _MAVEN_GOAL_PACKAGE);

		File mavenApiBundleFile = _testExists(
			mavenProjectDir,
			apiProjectName + "/target/" + name + "-api-1.0.0.jar");
		File mavenServiceBundleFile = _testExists(
			mavenProjectDir,
			serviceProjectName + "/target/" + name + "-service-1.0.0.jar");

		_testBundlesDiff(gradleApiBundleFile, mavenApiBundleFile);
		_testBundlesDiff(gradleServiceBundleFile, mavenServiceBundleFile);
	}

	private void _testBundlesDiff(File bundleFile1, File bundleFile2)
		throws Exception {

		PrintStream originalErrorStream = System.err;
		PrintStream originalOutputStream = System.out;

		originalErrorStream.flush();
		originalOutputStream.flush();

		ByteArrayOutputStream newErrorStream = new ByteArrayOutputStream();
		ByteArrayOutputStream newOutputStream = new ByteArrayOutputStream();

		System.setErr(new PrintStream(newErrorStream, true));
		System.setOut(new PrintStream(newOutputStream, true));

		try (bnd bnd = new bnd()) {
			String[] args = {
				"diff", "--ignore", _BUNDLES_DIFF_IGNORES,
				bundleFile1.getAbsolutePath(), bundleFile2.getAbsolutePath()
			};

			bnd.start(args);
		}
		finally {
			System.setErr(originalErrorStream);
			System.setOut(originalOutputStream);
		}

		String output = newErrorStream.toString();

		if (Validator.isNull(output)) {
			output = newOutputStream.toString();
		}

		Assert.assertEquals(
			"Bundle " + bundleFile1 + " and " + bundleFile2 + " do not match",
			"", output);
	}

	private File _testContains(File dir, String fileName, String... strings)
		throws IOException {

		File file = _testExists(dir, fileName);

		String content = FileUtil.read(file.toPath());

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

	private File _testNotContains(File dir, String fileName, String... strings)
		throws IOException {

		File file = _testExists(dir, fileName);

		String content = FileUtil.read(file.toPath());

		for (String s : strings) {
			Assert.assertFalse(
				"Found in " + fileName + ": " + s, content.contains(s));
		}

		return file;
	}

	private File _testNotExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertFalse("Unexpected " + fileName, file.exists());

		return file;
	}

	private void _writeServiceClass(File projectDir) throws IOException {
		String importLine =
			"import com.liferay.portal.kernel.events.LifecycleAction;";
		String classLine =
			"public class FooAction implements LifecycleAction {";

		File actionJavaFile = _testContains(
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

	private static final String _BUNDLES_DIFF_IGNORES = StringTestUtil.merge(
		"*pom.properties", "*pom.xml", "Archiver-Version", "Build-Jdk",
		"Built-By", "Javac-Debug", "Javac-Deprecation", "Javac-Encoding");

	private static final String _GRADLE_TASK_PATH_BUILD = ":build";

	private static final String _GRADLE_TASK_PATH_BUILD_SERVICE =
		":buildService";

	private static final String _MAVEN_GOAL_BUILD_SERVICE =
		"liferay:build-service";

	private static final String _MAVEN_GOAL_PACKAGE = "package";

	private static final String _REPOSITORY_CDN_URL =
		"https://cdn.lfrs.sl/repository.liferay.com/nexus/content/groups" +
			"/public";

	private static final String[] _STANDALONE_ONLY_FILE_NAMES = {
		"gradlew", "gradlew.bat", "gradle/wrapper/gradle-wrapper.jar",
		"gradle/wrapper/gradle-wrapper.properties"
	};

	private static URI _gradleDistribution;
	private static String _httpProxyHost;
	private static String _httpProxyPort;
	private static URL[] _mavenEmbedderDependencyURLs;
	private static File _mavenSettingsXmlFile;
	private static Properties _projectTemplateVersions;
	private static String _repositoryUrl;

}