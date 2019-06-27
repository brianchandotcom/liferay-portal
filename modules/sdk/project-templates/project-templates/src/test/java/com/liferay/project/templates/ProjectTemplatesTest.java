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

import aQute.bnd.header.Attrs;
import aQute.bnd.header.Parameters;
import aQute.bnd.osgi.Domain;

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.constants.ProjectTemplatesTestConstants;
import com.liferay.project.templates.internal.util.FileUtil;
import com.liferay.project.templates.internal.util.ProjectTemplatesUtil;
import com.liferay.project.templates.internal.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;
import com.liferay.project.templates.util.ProjectTemplatesTestUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.URI;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.compress.archivers.zip.ZipFile;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * @author Lawrence Lee
 * @author Gregory Amerson
 * @author Andrea Di Giorgi
 */
public class ProjectTemplatesTest {

	@ClassRule
	public static final MavenExecutor mavenExecutor = new MavenExecutor();

	@ClassRule
	public static final TemporaryFolder testCaseTemporaryFolder =
		new TemporaryFolder();

	@BeforeClass
	public static void setUpClass() throws Exception {
		String gradleDistribution = System.getProperty("gradle.distribution");

		if (Validator.isNull(gradleDistribution)) {
			Properties properties = FileTestUtil.readProperties(
				"gradle-wrapper/gradle/wrapper/gradle-wrapper.properties");

			gradleDistribution = properties.getProperty("distributionUrl");
		}

		Assert.assertTrue(
			gradleDistribution.contains(
				ProjectTemplatesTestConstants.GRADLE_WRAPPER_VERSION));

		ProjectTemplatesTestConstants.gradleDistribution = URI.create(
			gradleDistribution);

		XPathFactory xPathFactory = XPathFactory.newInstance();

		XPath xPath = xPathFactory.newXPath();

		ProjectTemplatesTestConstants.pomXmlNpmInstallXPathExpression =
			xPath.compile("//id[contains(text(),'npm-install')]/parent::*");
	}

	@Test
	public void testBuildTemplate() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			null, "hello-world-portlet");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/hello/world/portlet/portlet/HelloWorldPortlet.java",
			"public class HelloWorldPortlet extends MVCPortlet {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvc-portlet", "hello-world-portlet", "com.test",
			"-DclassName=HelloWorld", "-Dpackage=hello.world.portlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateActivator() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"activator", "bar-activator");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_OSGI_CORE + ", version: \"6.0.0\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/java/bar/activator/BarActivator.java",
			"public class BarActivator implements BundleActivator {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"activator", "bar-activator", "com.test",
			"-DclassName=BarActivator", "-Dpackage=bar.activator");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File jarFile = ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "build/libs/bar.activator-1.0.0.jar");

			Domain domain = Domain.domain(jarFile);

			Parameters parameters = domain.getImportPackage();

			Assert.assertNotNull(parameters);

			Attrs attrs = parameters.get("org.osgi.framework");

			Assert.assertNotNull(attrs);
		}
	}

	@Test
	public void testBuildTemplateActivatorInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"activator", "bar-activator", "build/libs/bar.activator-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateApi() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("api", "foo");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_OSGI_CORE + ", version: \"6.0.0\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/java/foo/api/Foo.java",
			"public interface Foo");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/resources/foo/api/packageinfo",
			"1.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"api", "foo", "com.test", "-DclassName=Foo", "-Dpackage=foo");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File jarFile = ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "build/libs/foo-1.0.0.jar");

			Domain domain = Domain.domain(jarFile);

			Parameters parameters = domain.getExportPackage();

			Assert.assertNotNull(parameters);

			Assert.assertNotNull(
				parameters.toString(), parameters.get("foo.api"));
		}
	}

	@Test
	public void testBuildTemplateApiContainsCorrectAuthor() throws Exception {
		String author = "Test Author";

		File gradleProjectDir = _buildTemplateWithGradle(
			"api", "author-test", "--author", author);

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/java/author/test/api/AuthorTest.java",
			"@author " + author);

		File mavenProjectDir = _buildTemplateWithMaven(
			"api", "author-test", "com.test", "-Dauthor=" + author,
			"-DclassName=AuthorTest", "-Dpackage=author.test");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "src/main/java/author/test/api/AuthorTest.java",
			"@author " + author);
	}

	@Test
	public void testBuildTemplateApiInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"api", "foo", "build/libs/foo-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateContentDTDVersionLayoutTemplate70()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"layout-template", "foo-bar", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-layout-templates.xml",
			"liferay-layout-templates_7_0_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionLayoutTemplate71()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"layout-template", "foo-bar", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-layout-templates.xml",
			"liferay-layout-templates_7_1_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionLayoutTemplate72()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"layout-template", "foo-bar", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-layout-templates.xml",
			"liferay-layout-templates_7_2_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionServiceBuilder70()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", "foo-bar", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "foo-bar-service/service.xml",
			"liferay-service-builder_7_0_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionServiceBuilder71()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", "foo-bar", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "foo-bar-service/service.xml",
			"liferay-service-builder_7_1_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionServiceBuilder72()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", "foo-bar", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "foo-bar-service/service.xml",
			"liferay-service-builder_7_2_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionSpringMVCPortlet70()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"spring-mvc-portlet", "foo-bar", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-display.xml",
			"liferay-display_7_0_0.dtd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-portlet.xml",
			"liferay-portlet-app_7_0_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionSpringMVCPortlet71()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"spring-mvc-portlet", "foo-bar", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-display.xml",
			"liferay-display_7_1_0.dtd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-portlet.xml",
			"liferay-portlet-app_7_1_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionSpringMVCPortlet72()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"spring-mvc-portlet", "foo-bar", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-display.xml",
			"liferay-display_7_2_0.dtd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-portlet.xml",
			"liferay-portlet-app_7_2_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionWarHook70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-hook", "foo-bar", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-hook.xml",
			"liferay-hook_7_0_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionWarHook71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-hook", "foo-bar", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-hook.xml",
			"liferay-hook_7_1_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionWarHook72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-hook", "foo-bar", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-hook.xml",
			"liferay-hook_7_2_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionWarMVCPortlet70()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "foo-bar", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-display.xml",
			"liferay-display_7_0_0.dtd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-portlet.xml",
			"liferay-portlet-app_7_0_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionWarMVCPortlet71()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "foo-bar", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-display.xml",
			"liferay-display_7_1_0.dtd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-portlet.xml",
			"liferay-portlet-app_7_1_0.dtd");
	}

	@Test
	public void testBuildTemplateContentDTDVersionWarMVCPortlet72()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "foo-bar", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-display.xml",
			"liferay-display_7_2_0.dtd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-portlet.xml",
			"liferay-portlet-app_7_2_0.dtd");
	}

	@Test
	public void testBuildTemplateContentTargetingReport70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-report", "foo-bar", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.3.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-report", "foo-bar", "com.test",
			"-DclassName=FooBar", "-Dpackage=foo.bar", "-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateContentTargetingReport71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-report", "foo-bar", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-report", "foo-bar", "com.test",
			"-DclassName=FooBar", "-Dpackage=foo.bar", "-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateContentTargetingReport72() throws Exception {
		_buildTemplateWithGradle(
			"content-targeting-report", "foo-bar", "--liferay-version", "7.2");
	}

	@Test
	public void testBuildTemplateContentTargetingReportInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"content-targeting-report", "foo-bar",
			"build/libs/foo.bar-1.0.0.jar", "--liferay-version", "7.1",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateContentTargetingRule70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-rule", "foo-bar", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.3.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-rule", "foo-bar", "com.test",
			"-DclassName=FooBar", "-Dpackage=foo.bar", "-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateContentTargetingRule71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-rule", "foo-bar", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-rule", "foo-bar", "com.test",
			"-DclassName=FooBar", "-Dpackage=foo.bar", "-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateContentTargetingRule72() throws Exception {
		_buildTemplateWithGradle(
			"content-targeting-rule", "foo-bar", "--liferay-version", "7.2");
	}

	@Test
	public void testBuildTemplateContentTargetingRuleInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"content-targeting-rule", "foo-bar", "build/libs/foo.bar-1.0.0.jar",
			"--liferay-version", "7.1", "--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateContentTargetingTrackingAction70()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-tracking-action", "foo-bar", "--liferay-version",
			"7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.3.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-tracking-action", "foo-bar", "com.test",
			"-DclassName=FooBar", "-Dpackage=foo.bar", "-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateContentTargetingTrackingAction71()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-tracking-action", "foo-bar", "--liferay-version",
			"7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-tracking-action", "foo-bar", "com.test",
			"-DclassName=FooBar", "-Dpackage=foo.bar", "-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateContentTargetingTrackingAction72()
		throws Exception {

		_buildTemplateWithGradle(
			"content-targeting-tracking-action", "foo-bar", "--liferay-version",
			"7.2");
	}

	@Test
	public void testBuildTemplateContentTargetingTrackingActionInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"content-targeting-tracking-action", "foo-bar",
			"build/libs/foo.bar-1.0.0.jar", "--liferay-version", "7.1",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateControlMenuEntry70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"control-menu-entry", "foo-bar", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/control/menu" +
				"/FooBarProductNavigationControlMenuEntry.java",
			"public class FooBarProductNavigationControlMenuEntry",
			"extends BaseProductNavigationControlMenuEntry",
			"implements ProductNavigationControlMenuEntry");

		File mavenProjectDir = _buildTemplateWithMaven(
			"control-menu-entry", "foo-bar", "com.test", "-DclassName=FooBar",
			"-Dpackage=foo.bar", "-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateControlMenuEntry71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"control-menu-entry", "foo-bar", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0\"");

		File mavenProjectDir = _buildTemplateWithMaven(
			"control-menu-entry", "foo-bar", "com.test", "-DclassName=FooBar",
			"-Dpackage=foo.bar", "-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateControlMenuEntry72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"control-menu-entry", "foo-bar", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0\"");

		File mavenProjectDir = _buildTemplateWithMaven(
			"control-menu-entry", "foo-bar", "com.test", "-DclassName=FooBar",
			"-Dpackage=foo.bar", "-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateControlMenuEntryInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"control-menu-entry", "foo-bar", "build/libs/foo.bar-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateFormField70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"form-field", "foobar", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-Name: foobar",
			"Web-ContextPath: /dynamic-data-foobar-form-field");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldRenderer.java",
			"property = \"ddm.form.field.type.name=foobar\"",
			"public class FoobarDDMFormFieldRenderer extends " +
				"BaseDDMFormFieldRenderer {",
			"ddm.Foobar", "/META-INF/resources/foobar.soy");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldType.java",
			"ddm.form.field.type.js.class.name=Liferay.DDM.Field.Foobar",
			"ddm.form.field.type.js.module=foobar-form-field",
			"ddm.form.field.type.label=foobar-label",
			"ddm.form.field.type.name=foobar",
			"public class FoobarDDMFormFieldType extends BaseDDMFormFieldType",
			"return \"foobar\";");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/config.js",
			"foobar-group", "'foobar-form-field': {",
			"path: 'foobar_field.js',", "'foobar-form-field-template': {");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.soy",
			"{namespace ddm}", "{template .Foobar autoescape",
			"<div class=\"form-group foobar-form-field\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar_field.js",
			"'foobar-form-field',", "var FoobarField",
			"value: 'foobar-form-field'", "NAME: 'foobar-form-field'",
			"Liferay.namespace('DDM.Field').Foobar = FoobarField;");

		File mavenProjectDir = _buildTemplateWithMaven(
			"form-field", "foobar", "com.test", "-DclassName=Foobar",
			"-Dpackage=foobar", "-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Ignore
	@Test
	public void testBuildTemplateFormField71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"form-field", "foobar", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-Name: foobar",
			"Web-ContextPath: /dynamic-data-foobar-form-field");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "package.json",
			"\"name\": \"dynamic-data-foobar-form-field\"",
			",foobar_field.js &&");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldRenderer.java",
			"property = \"ddm.form.field.type.name=foobar\"",
			"public class FoobarDDMFormFieldRenderer extends " +
				"BaseDDMFormFieldRenderer {",
			"DDMFoobar.render", "/META-INF/resources/foobar.soy");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldType.java",
			"ddm.form.field.type.description=foobar-description",
			"ddm.form.field.type.js.class.name=Liferay.DDM.Field.Foobar",
			"ddm.form.field.type.js.module=foobar-form-field",
			"ddm.form.field.type.label=foobar-label",
			"ddm.form.field.type.name=foobar",
			"public class FoobarDDMFormFieldType extends BaseDDMFormFieldType",
			"return \"foobar\";");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/config.js",
			"field-foobar", "'foobar-form-field': {",
			"path: 'foobar_field.js',");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.soy",
			"{namespace DDMFoobar}", "variant=\"'foobar'\"",
			"foobar-form-field");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.es.js",
			"import templates from './foobar.soy';", "* Foobar Component",
			"class Foobar extends Component", "Soy.register(Foobar,",
			"!window.DDMFoobar", "window.DDMFoobar",
			"window.DDMFoobar.render = Foobar;", "export default Foobar;");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar_field.js",
			"'foobar-form-field',", "var FoobarField",
			"value: 'foobar-form-field'", "NAME: 'foobar-form-field'",
			"Liferay.namespace('DDM.Field').Foobar = FoobarField;");

		File mavenProjectDir = _buildTemplateWithMaven(
			"form-field", "foobar", "com.test", "-DclassName=Foobar",
			"-Dpackage=foobar", "-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Ignore
	@Test
	public void testBuildTemplateFormField71WithHyphen() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"form-field", "foo-bar", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-Name: foo-bar",
			"Web-ContextPath: /dynamic-data-foo-bar-form-field");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "package.json",
			"\"name\": \"dynamic-data-foo-bar-form-field\"",
			",foo-bar_field.js &&");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/form/field/FooBarDDMFormFieldRenderer.java",
			"property = \"ddm.form.field.type.name=fooBar\"",
			"public class FooBarDDMFormFieldRenderer extends " +
				"BaseDDMFormFieldRenderer {",
			"DDMFooBar.render", "/META-INF/resources/foo-bar.soy");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/form/field/FooBarDDMFormFieldType.java",
			"ddm.form.field.type.description=foo-bar-description",
			"ddm.form.field.type.js.class.name=Liferay.DDM.Field.FooBar",
			"ddm.form.field.type.js.module=foo-bar-form-field",
			"ddm.form.field.type.label=foo-bar-label",
			"ddm.form.field.type.name=fooBar",
			"public class FooBarDDMFormFieldType extends BaseDDMFormFieldType",
			"return \"fooBar\";");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/config.js",
			"field-foo-bar", "'foo-bar-form-field': {",
			"path: 'foo-bar_field.js',");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foo-bar.soy",
			"{namespace DDMFooBar}", "variant=\"'fooBar'\"",
			"foo-bar-form-field");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foo-bar.es.js",
			"import templates from './foo-bar.soy';", "* FooBar Component",
			"class FooBar extends Component", "Soy.register(FooBar,",
			"!window.DDMFooBar", "window.DDMFooBar",
			"window.DDMFooBar.render = FooBar;", "export default FooBar;");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foo-bar_field.js",
			"'foo-bar-form-field',", "var FooBarField",
			"value: 'foo-bar-form-field'", "NAME: 'foo-bar-form-field'",
			"Liferay.namespace('DDM.Field').FooBar = FooBarField;");

		File mavenProjectDir = _buildTemplateWithMaven(
			"form-field", "foo-bar", "com.test", "-DclassName=FooBar",
			"-Dpackage=foo.bar", "-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateFormFieldInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"form-field", "foobar", "build/libs/foobar-1.0.0.jar",
			"--liferay-version", "7.1", "--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateFragment() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"fragment", "loginhook", "--host-bundle-symbolic-name",
			"com.liferay.login.web", "--host-bundle-version", "1.0.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-SymbolicName: loginhook",
			"Fragment-Host: com.liferay.login.web;bundle-version=\"1.0.0\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		File mavenProjectDir = _buildTemplateWithMaven(
			"fragment", "loginhook", "com.test",
			"-DhostBundleSymbolicName=com.liferay.login.web",
			"-DhostBundleVersion=1.0.0", "-Dpackage=loginhook");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File jarFile = ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "build/libs/loginhook-1.0.0.jar");

			Domain domain = Domain.domain(jarFile);

			Map.Entry<String, Attrs> fragmentHost = domain.getFragmentHost();

			Assert.assertNotNull(fragmentHost);

			Assert.assertEquals(
				fragmentHost.toString(), "com.liferay.login.web",
				fragmentHost.getKey());
		}
	}

	@Test
	public void testBuildTemplateFreeMarkerPortlet70() throws Exception {
		File gradleProjectDir = _testBuildTemplatePortlet70(
			"freemarker-portlet", "FreeMarkerPortlet", "templates/init.ftl",
			"templates/view.ftl");

		_testStartsWith(
			gradleProjectDir, "src/main/resources/templates/view.ftl",
			_FREEMARKER_PORTLET_VIEW_FTL_PREFIX);
	}

	@Test
	public void testBuildTemplateFreeMarkerPortlet71() throws Exception {
		_testBuildTemplatePortlet71(
			"freemarker-portlet", "FreeMarkerPortlet", "templates/init.ftl",
			"templates/view.ftl");
	}

	@Test
	public void testBuildTemplateFreeMarkerPortlet72() throws Exception {
		_testBuildTemplatePortlet72(
			"freemarker-portlet", "FreeMarkerPortlet", "templates/init.ftl",
			"templates/view.ftl");
	}

	@Test
	public void testBuildTemplateFreeMarkerPortletInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"freemarker-portlet", "foo", "build/libs/foo-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateFreeMarkerPortletWithPackage70()
		throws Exception {

		File gradleProjectDir = _testBuildTemplatePortletWithPackage70(
			"freemarker-portlet", "FreeMarkerPortlet", "templates/init.ftl",
			"templates/view.ftl");

		_testStartsWith(
			gradleProjectDir, "src/main/resources/templates/view.ftl",
			_FREEMARKER_PORTLET_VIEW_FTL_PREFIX);
	}

	@Test
	public void testBuildTemplateFreeMarkerPortletWithPackage71()
		throws Exception {

		File gradleProjectDir = _testBuildTemplatePortletWithPackage71(
			"freemarker-portlet", "FreeMarkerPortlet", "templates/init.ftl",
			"templates/view.ftl");

		_testStartsWith(
			gradleProjectDir, "src/main/resources/templates/view.ftl",
			_FREEMARKER_PORTLET_VIEW_FTL_PREFIX);
	}

	@Test
	public void testBuildTemplateFreeMarkerPortletWithPackage72()
		throws Exception {

		File gradleProjectDir = _testBuildTemplatePortletWithPackage72(
			"freemarker-portlet", "FreeMarkerPortlet", "templates/init.ftl",
			"templates/view.ftl");

		_testStartsWith(
			gradleProjectDir, "src/main/resources/templates/view.ftl",
			_FREEMARKER_PORTLET_VIEW_FTL_PREFIX);
	}

	@Test
	public void testBuildTemplateFreeMarkerPortletWithPortletName70()
		throws Exception {

		File gradleProjectDir = _testBuildTemplatePortletWithPortletName70(
			"freemarker-portlet", "FreeMarkerPortlet", "templates/init.ftl",
			"templates/view.ftl");

		_testStartsWith(
			gradleProjectDir, "src/main/resources/templates/view.ftl",
			_FREEMARKER_PORTLET_VIEW_FTL_PREFIX);
	}

	@Test
	public void testBuildTemplateFreeMarkerPortletWithPortletName71()
		throws Exception {

		File gradleProjectDir = _testBuildTemplatePortletWithPortletName71(
			"freemarker-portlet", "FreeMarkerPortlet", "templates/init.ftl",
			"templates/view.ftl");

		_testStartsWith(
			gradleProjectDir, "src/main/resources/templates/view.ftl",
			_FREEMARKER_PORTLET_VIEW_FTL_PREFIX);
	}

	@Test
	public void testBuildTemplateFreeMarkerPortletWithPortletName72()
		throws Exception {

		File gradleProjectDir = _testBuildTemplatePortletWithPortletName72(
			"freemarker-portlet", "FreeMarkerPortlet", "templates/init.ftl",
			"templates/view.ftl");

		_testStartsWith(
			gradleProjectDir, "src/main/resources/templates/view.ftl",
			_FREEMARKER_PORTLET_VIEW_FTL_PREFIX);
	}

	@Test
	public void testBuildTemplateFreeMarkerPortletWithPortletSuffix70()
		throws Exception {

		File gradleProjectDir = _testBuildTemplatePortletWithPortletSuffix70(
			"freemarker-portlet", "FreeMarkerPortlet", "templates/init.ftl",
			"templates/view.ftl");

		_testStartsWith(
			gradleProjectDir, "src/main/resources/templates/view.ftl",
			_FREEMARKER_PORTLET_VIEW_FTL_PREFIX);
	}

	@Test
	public void testBuildTemplateFreeMarkerPortletWithPortletSuffix71()
		throws Exception {

		File gradleProjectDir = _testBuildTemplatePortletWithPortletSuffix71(
			"freemarker-portlet", "FreeMarkerPortlet", "templates/init.ftl",
			"templates/view.ftl");

		_testStartsWith(
			gradleProjectDir, "src/main/resources/templates/view.ftl",
			_FREEMARKER_PORTLET_VIEW_FTL_PREFIX);
	}

	@Test
	public void testBuildTemplateFreeMarkerPortletWithPortletSuffix72()
		throws Exception {

		File gradleProjectDir = _testBuildTemplatePortletWithPortletSuffix72(
			"freemarker-portlet", "FreeMarkerPortlet", "templates/init.ftl",
			"templates/view.ftl");

		_testStartsWith(
			gradleProjectDir, "src/main/resources/templates/view.ftl",
			_FREEMARKER_PORTLET_VIEW_FTL_PREFIX);
	}

	@Test
	public void testBuildTemplateInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			null, "hello-world-portlet",
			"build/libs/hello.world.portlet-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateLayoutTemplate() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"layout-template", "foo");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/webapp/foo.png");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/webapp/foo.ftl", "class=\"foo\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-layout-templates.xml",
			"<layout-template id=\"foo\" name=\"foo\">",
			"<template-path>/foo.ftl</template-path>",
			"<thumbnail-path>/foo.png</thumbnail-path>");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=foo");
		_testEquals(gradleProjectDir, "build.gradle", "apply plugin: \"war\"");

		File mavenProjectDir = _buildTemplateWithMaven(
			"layout-template", "foo", "com.test");

		_createNewFiles(
			"src/main/resources/.gitkeep", gradleProjectDir, mavenProjectDir);

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateLiferayVersionInvalid62() throws Exception {
		_buildTemplateWithGradle(
			"mvc-portlet", "test", "--liferay-version", "6.2");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateLiferayVersionInvalid70test()
		throws Exception {

		_buildTemplateWithGradle(
			"mvc-portlet", "test", "--liferay-version", "7.0test");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateLiferayVersionInvalid73() throws Exception {
		_buildTemplateWithGradle(
			"mvc-portlet", "test", "--liferay-version", "7.3");
	}

	@Test
	public void testBuildTemplateLiferayVersionValid70() throws Exception {
		_buildTemplateWithGradle(
			"mvc-portlet", "test", "--liferay-version", "7.0");
	}

	@Test
	public void testBuildTemplateLiferayVersionValid712() throws Exception {
		_buildTemplateWithGradle(
			"mvc-portlet", "test", "--liferay-version", "7.1.2");
	}

	@Test
	public void testBuildTemplateModuleExt() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"modules-ext", "loginExt", "--original-module-name",
			"com.liferay.login.web", "--original-module-version", "1.0.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle", "buildscript {", "repositories {",
			"originalModule group: \"com.liferay\", name: " +
				"\"com.liferay.login.web\", version: \"1.0.0\"",
			"apply plugin: \"com.liferay.osgi.ext.plugin\"");

		if (_isBuildProjects()) {
			ProjectTemplatesTestUtil.executeGradle(
				gradleProjectDir,
				ProjectTemplatesTestConstants.GRADLE_TASK_PATH_BUILD);

			File jarFile = ProjectTemplatesTestUtil.testExists(
				gradleProjectDir,
				"build/libs/com.liferay.login.web-1.0.0.ext.jar");

			Domain domain = Domain.domain(jarFile);

			Map.Entry<String, Attrs> bundleSymbolicName =
				domain.getBundleSymbolicName();

			Assert.assertEquals(
				bundleSymbolicName.toString(), "com.liferay.login.web",
				bundleSymbolicName.getKey());
		}
	}

	@Test
	public void testBuildTemplateModuleExtInWorkspace() throws Exception {
		File workspaceDir = _buildWorkspace();

		_enableTargetPlatformInWorkspace(workspaceDir);

		File workspaceProjectDir =
			ProjectTemplatesTestUtil.buildTemplateWithGradle(
				new File(workspaceDir, "ext"), "modules-ext", "loginExt",
				"--original-module-name", "com.liferay.login.web",
				"--dependency-management-enabled");

		ProjectTemplatesTestUtil.testContains(
			workspaceProjectDir, "build.gradle",
			"originalModule group: \"com.liferay\", name: " +
				"\"com.liferay.login.web\"");
		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");
		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "build.gradle", "version: \"[0-9].*");

		if (_isBuildProjects()) {
			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir, ":ext:loginExt:build");

			ProjectTemplatesTestUtil.testExists(
				workspaceProjectDir,
				"build/libs/com.liferay.login.web-4.0.8.ext.jar");
		}
	}

	@Test
	public void testBuildTemplateModulesExtGradle() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"modules-ext", "foo-ext", "--original-module-name",
			"com.liferay.login.web", "--original-module-version", "2.0.4");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"originalModule group: \"com.liferay\", ",
			"name: \"com.liferay.login.web\", version: \"2.0.4\"");

		if (_isBuildProjects()) {
			ProjectTemplatesTestUtil.executeGradle(
				gradleProjectDir,
				ProjectTemplatesTestConstants.GRADLE_TASK_PATH_BUILD);

			File gradleOutputDir = new File(gradleProjectDir, "build/libs");

			Path gradleOutputPath = FileTestUtil.getFile(
				gradleOutputDir.toPath(),
				ProjectTemplatesTestConstants.OUTPUT_FILENAME_GLOB_REGEX, 1);

			Assert.assertNotNull(gradleOutputPath);

			Assert.assertTrue(Files.exists(gradleOutputPath));
		}
	}

	@Test
	public void testBuildTemplateModulesExtMaven() throws Exception {
		String groupId = "com.test";
		String name = "foo-ext";
		String template = "modules-ext";

		List<String> completeArgs = new ArrayList<>();

		completeArgs.add("archetype:generate");
		completeArgs.add("--batch-mode");

		String archetypeArtifactId =
			"com.liferay.project.templates." + template.replace('-', '.');

		completeArgs.add("-DarchetypeArtifactId=" + archetypeArtifactId);

		String projectTemplateVersion =
			ProjectTemplatesUtil.getArchetypeVersion(archetypeArtifactId);

		Assert.assertTrue(
			"Unable to get project template version",
			Validator.isNotNull(projectTemplateVersion));

		completeArgs.add("-DarchetypeGroupId=com.liferay");
		completeArgs.add("-DarchetypeVersion=" + projectTemplateVersion);
		completeArgs.add("-DartifactId=" + name);
		completeArgs.add("-Dauthor=" + System.getProperty("user.name"));
		completeArgs.add("-DgroupId=" + groupId);
		completeArgs.add("-DliferayVersion=7.1");
		completeArgs.add("-DoriginalModuleName=com.liferay.login.web");
		completeArgs.add("-DoriginalModuleVersion=3.0.4");
		completeArgs.add("-DprojectType=standalone");
		completeArgs.add("-Dversion=1.0.0");

		File destinationDir = temporaryFolder.newFolder("maven");

		_executeMaven(destinationDir, completeArgs.toArray(new String[0]));

		File projectDir = new File(destinationDir, name);

		ProjectTemplatesTestUtil.testContains(
			projectDir, "build.gradle",
			"originalModule group: \"com.liferay\", ",
			"name: \"com.liferay.login.web\", version: \"3.0.4\"");
		ProjectTemplatesTestUtil.testNotExists(projectDir, "pom.xml");
	}

	@Test
	public void testBuildTemplateMVCPortlet70() throws Exception {
		_testBuildTemplatePortlet70(
			"mvc-portlet", "MVCPortlet", "META-INF/resources/init.jsp",
			"META-INF/resources/view.jsp");
	}

	@Test
	public void testBuildTemplateMVCPortlet71() throws Exception {
		_testBuildTemplatePortlet71(
			"mvc-portlet", "MVCPortlet", "META-INF/resources/init.jsp",
			"META-INF/resources/view.jsp");
	}

	@Test
	public void testBuildTemplateMVCPortlet72() throws Exception {
		_testBuildTemplatePortlet72(
			"mvc-portlet", "MVCPortlet", "META-INF/resources/init.jsp",
			"META-INF/resources/view.jsp");
	}

	@Test
	public void testBuildTemplateMVCPortletInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"mvc-portlet", "foo", "build/libs/foo-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPackage70() throws Exception {
		_testBuildTemplatePortletWithPackage70(
			"mvc-portlet", "MVCPortlet", "META-INF/resources/init.jsp",
			"META-INF/resources/view.jsp");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPackage71() throws Exception {
		_testBuildTemplatePortletWithPackage71(
			"mvc-portlet", "MVCPortlet", "META-INF/resources/init.jsp",
			"META-INF/resources/view.jsp");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPackage72() throws Exception {
		_testBuildTemplatePortletWithPackage72(
			"mvc-portlet", "MVCPortlet", "META-INF/resources/init.jsp",
			"META-INF/resources/view.jsp");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPortletName70()
		throws Exception {

		_testBuildTemplatePortletWithPortletName70(
			"mvc-portlet", "MVCPortlet", "META-INF/resources/init.jsp",
			"META-INF/resources/view.jsp");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPortletName71()
		throws Exception {

		_testBuildTemplatePortletWithPortletName71(
			"mvc-portlet", "MVCPortlet", "META-INF/resources/init.jsp",
			"META-INF/resources/view.jsp");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPortletName72()
		throws Exception {

		_testBuildTemplatePortletWithPortletName72(
			"mvc-portlet", "MVCPortlet", "META-INF/resources/init.jsp",
			"META-INF/resources/view.jsp");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPortletSuffix70()
		throws Exception {

		_testBuildTemplatePortletWithPortletSuffix70(
			"mvc-portlet", "MVCPortlet", "META-INF/resources/init.jsp",
			"META-INF/resources/view.jsp");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPortletSuffix71()
		throws Exception {

		_testBuildTemplatePortletWithPortletSuffix71(
			"mvc-portlet", "MVCPortlet", "META-INF/resources/init.jsp",
			"META-INF/resources/view.jsp");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPortletSuffix72()
		throws Exception {

		_testBuildTemplatePortletWithPortletSuffix72(
			"mvc-portlet", "MVCPortlet", "META-INF/resources/init.jsp",
			"META-INF/resources/view.jsp");
	}

	@Test
	public void testBuildTemplateNAPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"npm-angular-portlet", "angular-dependency-management",
			"--dependency-management-enabled", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateNpmAngularPortlet70() throws Exception {
		_testBuildTemplateNpmAngular70(
			"npm-angular-portlet", "foo", "foo", "Foo");
	}

	@Test
	public void testBuildTemplateNpmAngularPortlet71() throws Exception {
		_testBuildTemplateNpmAngular71(
			"npm-angular-portlet", "foo", "foo", "Foo");
	}

	@Test
	public void testBuildTemplateNpmAngularPortlet72() throws Exception {
		_testBuildTemplateNpmProject72("npm-angular-portlet");
	}

	@Test
	public void testBuildTemplateNpmAngularPortletWithDashes70()
		throws Exception {

		_testBuildTemplateNpmAngular70(
			"npm-angular-portlet", "foo-bar", "foo.bar", "FooBar");
	}

	@Test
	public void testBuildTemplateNpmAngularPortletWithDashes71()
		throws Exception {

		_testBuildTemplateNpmAngular71(
			"npm-angular-portlet", "foo-bar", "foo.bar", "FooBar");
	}

	@Test
	public void testBuildTemplateNpmReactPortlet70() throws Exception {
		_testBuildTemplateNpm70("npm-react-portlet", "foo", "foo", "Foo");
	}

	@Test
	public void testBuildTemplateNpmReactPortlet71() throws Exception {
		_testBuildTemplateNpm71("npm-react-portlet", "foo", "foo", "Foo");
	}

	@Test
	public void testBuildTemplateNpmReactPortlet72() throws Exception {
		_testBuildTemplateNpmProject72("npm-react-portlet");
	}

	@Test
	public void testBuildTemplateNpmReactPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"npm-react-portlet", "react-portlet-dependency-management",
			"--dependency-management-enabled", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateNpmReactPortletWithDashes70()
		throws Exception {

		_testBuildTemplateNpm70(
			"npm-react-portlet", "foo-bar", "foo.bar", "FooBar");
	}

	@Test
	public void testBuildTemplateNpmReactPortletWithDashes71()
		throws Exception {

		_testBuildTemplateNpm71(
			"npm-react-portlet", "foo-bar", "foo.bar", "FooBar");
	}

	@Test
	public void testBuildTemplateNpmVuejsPortlet70() throws Exception {
		_testBuildTemplateNpm70("npm-vuejs-portlet", "foo", "foo", "Foo");
	}

	@Test
	public void testBuildTemplateNpmVuejsPortlet71() throws Exception {
		_testBuildTemplateNpm71("npm-vuejs-portlet", "foo", "foo", "Foo");
	}

	@Test
	public void testBuildTemplateNpmVuejsPortlet72() throws Exception {
		_testBuildTemplateNpmProject72("npm-vuejs-portlet");
	}

	@Test
	public void testBuildTemplateNpmVuejsPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"npm-vuejs-portlet", "vuejs-portlet-dependency-management",
			"--dependency-management-enabled", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateNpmVuejsPortletWithDashes70()
		throws Exception {

		_testBuildTemplateNpm70(
			"npm-vuejs-portlet", "foo-bar", "foo.bar", "FooBar");
	}

	@Test
	public void testBuildTemplateNpmVuejsPortletWithDashes71()
		throws Exception {

		_testBuildTemplateNpm71(
			"npm-vuejs-portlet", "foo-bar", "foo.bar", "FooBar");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateOnExistingDirectory() throws Exception {
		File destinationDir = temporaryFolder.newFolder("gradle");

		ProjectTemplatesTestUtil.buildTemplateWithGradle(
			destinationDir, "activator", "dup-activator");
		ProjectTemplatesTestUtil.buildTemplateWithGradle(
			destinationDir, "activator", "dup-activator");
	}

	@Test
	public void testBuildTemplatePanelApp70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"panel-app", "gradle.test", "--class-name", "Foo",
			"--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "bnd.bnd",
			"Export-Package: gradle.test.constants");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/gradle/test/application/list/FooPanelApp.java",
			"public class FooPanelApp extends BasePanelApp");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/gradle/test/constants/FooPortletKeys.java",
			"public class FooPortletKeys", "public static final String FOO");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/gradle/test/portlet/FooPortlet.java",
			"javax.portlet.display-name=Foo",
			"javax.portlet.name=\" + FooPortletKeys.FOO",
			"public class FooPortlet extends MVCPortlet");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/resources/content/Language.properties",
			"javax.portlet.title.gradle_test_FooPortlet=Foo",
			"foo.caption=Hello from Foo!");

		File mavenProjectDir = _buildTemplateWithMaven(
			"panel-app", "gradle.test", "com.test", "-DclassName=Foo",
			"-Dpackage=gradle.test", "-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/gradle.test-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}
	}

	@Test
	public void testBuildTemplatePanelApp71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"panel-app", "gradle.test", "--class-name", "Foo",
			"--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"panel-app", "gradle.test", "com.test", "-DclassName=Foo",
			"-Dpackage=gradle.test", "-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/gradle.test-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}
	}

	@Test
	public void testBuildTemplatePanelApp72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"panel-app", "gradle.test", "--class-name", "Foo",
			"--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"panel-app", "gradle.test", "com.test", "-DclassName=Foo",
			"-Dpackage=gradle.test", "-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/gradle.test-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}
	}

	@Test
	public void testBuildTemplatePanelAppInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"panel-app", "gradle.test", "build/libs/gradle.test-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplatePorletProviderInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"portlet-provider", "provider.test",
			"build/libs/provider.test-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplatePortlet70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet", "foo.test", "--class-name", "Foo", "--liferay-version",
			"7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/foo/test/constants/FooPortletKeys.java",
			"public class FooPortletKeys", "public static final String FOO",
			"\"foo_test_FooPortlet\";");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/java/foo/test/portlet/FooPortlet.java",
			"package foo.test.portlet;",
			"javax.portlet.name=\" + FooPortletKeys.FOO",
			"public class FooPortlet extends MVCPortlet {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet", "foo.test", "com.test", "-DclassName=Foo",
			"-Dpackage=foo.test", "-DliferayVersion=7.0");

		ProjectTemplatesTestUtil.testNotContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortlet71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet", "foo.test", "--class-name", "Foo", "--liferay-version",
			"7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/foo/test/constants/FooPortletKeys.java",
			"public class FooPortletKeys", "public static final String FOO",
			"\"foo_test_FooPortlet\";");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/java/foo/test/portlet/FooPortlet.java",
			"package foo.test.portlet;",
			"javax.portlet.name=\" + FooPortletKeys.FOO",
			"public class FooPortlet extends MVCPortlet {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet", "foo.test", "com.test", "-DclassName=Foo",
			"-Dpackage=foo.test", "-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortlet72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet", "foo.test", "--class-name", "Foo", "--liferay-version",
			"7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/foo/test/constants/FooPortletKeys.java",
			"public class FooPortletKeys", "public static final String FOO",
			"\"foo_test_FooPortlet\";");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/java/foo/test/portlet/FooPortlet.java",
			"package foo.test.portlet;",
			"javax.portlet.name=\" + FooPortletKeys.FOO",
			"public class FooPortlet extends MVCPortlet {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet", "foo.test", "com.test", "-DclassName=Foo",
			"-Dpackage=foo.test", "-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortletConfigurationIcon70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-configuration-icon", "icontest", "--package-name",
			"blade.test", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/blade/test/portlet/configuration/icon" +
				"/IcontestPortletConfigurationIcon.java",
			"public class IcontestPortletConfigurationIcon",
			"extends BasePortletConfigurationIcon");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-configuration-icon", "icontest", "com.test",
			"-DclassName=Icontest", "-Dpackage=blade.test",
			"-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortletConfigurationIcon71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-configuration-icon", "icontest", "--package-name",
			"blade.test", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-configuration-icon", "icontest", "com.test",
			"-DclassName=Icontest", "-Dpackage=blade.test",
			"-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortletConfigurationIcon72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-configuration-icon", "icontest", "--package-name",
			"blade.test", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-configuration-icon", "icontest", "com.test",
			"-DclassName=Icontest", "-Dpackage=blade.test",
			"-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortletConfigurationIconInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"portlet-configuration-icon", "blade.test",
			"build/libs/blade.test-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplatePortletInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"portlet", "foo.test", "build/libs/foo.test-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplatePortletProvider70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-provider", "provider.test", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/provider/test/constants" +
				"/ProviderTestPortletKeys.java",
			"package provider.test.constants;",
			"public class ProviderTestPortletKeys",
			"public static final String PROVIDERTEST",
			"\"provider_test_ProviderTestPortlet\";");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/provider/test/portlet/ProviderTestPortlet.java",
			"javax.portlet.display-name=ProviderTest",
			"javax.portlet.name=\" + ProviderTestPortletKeys.PROVIDERTEST",
			"public class ProviderTestPortlet extends MVCPortlet {");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/resources/content/Language.properties",
			"javax.portlet.title.provider_test_ProviderTestPortlet=" +
				"ProviderTest",
			"providertest.caption=Hello from ProviderTest!");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-provider", "provider.test", "com.test",
			"-DclassName=ProviderTest", "-Dpackage=provider.test",
			"-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/provider.test-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}
	}

	@Test
	public void testBuildTemplatePortletProvider71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-provider", "provider.test", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-provider", "provider.test", "com.test",
			"-DclassName=ProviderTest", "-Dpackage=provider.test",
			"-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/provider.test-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}
	}

	@Test
	public void testBuildTemplatePortletProvider72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-provider", "provider.test", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-provider", "provider.test", "com.test",
			"-DclassName=ProviderTest", "-Dpackage=provider.test",
			"-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/provider.test-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}
	}

	@Test
	public void testBuildTemplatePortletToolbarContributor70()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-toolbar-contributor", "toolbartest", "--package-name",
			"blade.test", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/blade/test/portlet/toolbar/contributor" +
				"/ToolbartestPortletToolbarContributor.java",
			"public class ToolbartestPortletToolbarContributor",
			"implements PortletToolbarContributor");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-toolbar-contributor", "toolbartest", "com.test",
			"-DclassName=Toolbartest", "-Dpackage=blade.test",
			"-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortletToolbarContributor71()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-toolbar-contributor", "toolbartest", "--package-name",
			"blade.test", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-toolbar-contributor", "toolbartest", "com.test",
			"-DclassName=Toolbartest", "-Dpackage=blade.test",
			"-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortletToolbarContributor72()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-toolbar-contributor", "toolbartest", "--package-name",
			"blade.test", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-toolbar-contributor", "toolbartest", "com.test",
			"-DclassName=Toolbartest", "-Dpackage=blade.test",
			"-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortletToolbarContributorInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"portlet-toolbar-contributor", "blade.test",
			"build/libs/blade.test-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplatePortletWithPortletName() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("portlet", "portlet");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/PortletPortlet.java",
			"package portlet.portlet;",
			"public class PortletPortlet extends MVCPortlet {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet", "portlet", "com.test", "-DclassName=Portlet",
			"-Dpackage=portlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateRest70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"rest", "my-rest", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"compileOnly group: \"javax.ws.rs\", name: \"javax.ws.rs-api\", " +
				"version: \"2.0.1\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/my/rest/application/MyRestApplication.java",
			"public class MyRestApplication extends Application");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/resources/configuration" +
				"/com.liferay.portal.remote.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration-cxf.properties",
			"contextPath=/my-rest");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/resources/configuration/com.liferay.portal.remote.rest." +
				"extender.configuration.RestExtenderConfiguration-rest." +
					"properties",
			"contextPaths=/my-rest",
			"jaxRsApplicationFilterStrings=(component.name=" +
				"my.rest.application.MyRestApplication)");

		File mavenProjectDir = _buildTemplateWithMaven(
			"rest", "my-rest", "com.test", "-DclassName=MyRest",
			"-Dpackage=my.rest", "-DliferayVersion=7.0");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir,
			"src/main/java/my/rest/application/MyRestApplication.java",
			"public class MyRestApplication extends Application");
		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir,
			"src/main/resources/configuration" +
				"/com.liferay.portal.remote.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration-cxf.properties",
			"contextPath=/my-rest");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateRest71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"rest", "my-rest", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"compileOnly group: \"org.osgi\", name: " +
				"\"org.osgi.service.jaxrs\", version: \"1.0.0\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/my/rest/application/MyRestApplication.java",
			"public class MyRestApplication extends Application");
		ProjectTemplatesTestUtil.testNotExists(
			gradleProjectDir,
			"src/main/resources/configuration" +
				"/com.liferay.portal.remote.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration-cxf.properties");
		ProjectTemplatesTestUtil.testNotExists(
			gradleProjectDir,
			"src/main/resources/configuration/com.liferay.portal.remote.rest." +
				"extender.configuration.RestExtenderConfiguration-rest." +
					"properties");
		ProjectTemplatesTestUtil.testNotExists(
			gradleProjectDir, "src/main/resources/configuration");

		File mavenProjectDir = _buildTemplateWithMaven(
			"rest", "my-rest", "com.test", "-DclassName=MyRest",
			"-Dpackage=my.rest", "-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir,
			"src/main/java/my/rest/application/MyRestApplication.java",
			"public class MyRestApplication extends Application");
		ProjectTemplatesTestUtil.testNotExists(
			mavenProjectDir,
			"src/main/resources/configuration" +
				"/com.liferay.portal.remote.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration-cxf.properties");
		ProjectTemplatesTestUtil.testNotExists(
			mavenProjectDir, "src/main/resources/configuration");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateRest72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"rest", "my-rest", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"compileOnly group: \"org.osgi\", name: " +
				"\"org.osgi.service.jaxrs\", version: \"1.0.0\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/my/rest/application/MyRestApplication.java",
			"public class MyRestApplication extends Application");
		ProjectTemplatesTestUtil.testNotExists(
			gradleProjectDir,
			"src/main/resources/configuration" +
				"/com.liferay.portal.remote.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration-cxf.properties");
		ProjectTemplatesTestUtil.testNotExists(
			gradleProjectDir,
			"src/main/resources/configuration/com.liferay.portal.remote.rest." +
				"extender.configuration.RestExtenderConfiguration-rest." +
					"properties");
		ProjectTemplatesTestUtil.testNotExists(
			gradleProjectDir, "src/main/resources/configuration");

		File mavenProjectDir = _buildTemplateWithMaven(
			"rest", "my-rest", "com.test", "-DclassName=MyRest",
			"-Dpackage=my.rest", "-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir,
			"src/main/java/my/rest/application/MyRestApplication.java",
			"public class MyRestApplication extends Application");
		ProjectTemplatesTestUtil.testNotExists(
			mavenProjectDir,
			"src/main/resources/configuration" +
				"/com.liferay.portal.remote.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration-cxf.properties");
		ProjectTemplatesTestUtil.testNotExists(
			mavenProjectDir, "src/main/resources/configuration");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateRestInWorkspace70() throws Exception {
		_testBuildTemplateWithWorkspace(
			"rest", "my-rest", "build/libs/my.rest-1.0.0.jar",
			"--dependency-management-enabled", "--liferayVersion", "7.0");
	}

	@Test
	public void testBuildTemplateRestInWorkspace71() throws Exception {
		_testBuildTemplateWithWorkspace(
			"rest", "my-rest", "build/libs/my.rest-1.0.0.jar",
			"--dependency-management-enabled", "--liferayVersion", "7.1");
	}

	@Test
	public void testBuildTemplateRestInWorkspace72() throws Exception {
		_testBuildTemplateWithWorkspace(
			"rest", "my-rest", "build/libs/my.rest-1.0.0.jar",
			"--dependency-management-enabled", "--liferayVersion", "7.2");
	}

	@Test
	public void testBuildTemplateService70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction",
			"--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service", "servicepreaction", "com.test", "-DclassName=FooAction",
			"-Dpackage=servicepreaction",
			"-DserviceClass=com.liferay.portal.kernel.events.LifecycleAction",
			"-DliferayVersion=7.0");

		if (_isBuildProjects()) {
			_writeServiceClass(gradleProjectDir);
			_writeServiceClass(mavenProjectDir);

			_buildProjects(gradleProjectDir, mavenProjectDir);
		}
	}

	@Test
	public void testBuildTemplateService71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction",
			"--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service", "servicepreaction", "com.test", "-DclassName=FooAction",
			"-Dpackage=servicepreaction",
			"-DserviceClass=com.liferay.portal.kernel.events.LifecycleAction",
			"-DliferayVersion=7.1");

		if (_isBuildProjects()) {
			_writeServiceClass(gradleProjectDir);
			_writeServiceClass(mavenProjectDir);

			_buildProjects(gradleProjectDir, mavenProjectDir);
		}
	}

	@Test
	public void testBuildTemplateService72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction",
			"--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service", "servicepreaction", "com.test", "-DclassName=FooAction",
			"-Dpackage=servicepreaction",
			"-DserviceClass=com.liferay.portal.kernel.events.LifecycleAction",
			"-DliferayVersion=7.2");

		if (_isBuildProjects()) {
			_writeServiceClass(gradleProjectDir);
			_writeServiceClass(mavenProjectDir);

			_buildProjects(gradleProjectDir, mavenProjectDir);
		}
	}

	@Test
	public void testBuildTemplateServiceBuilder70() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-api/build.gradle",
			"biz.aQute.bndlib\", version: \"3.5.0",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-service/build.gradle",
			"biz.aQute.bndlib\", version: \"3.5.0",
			"com.liferay.portal.spring.extender\", version: \"2.0.0",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.6.0");

		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, name + "-api/build.gradle",
			"org.osgi.annotation.versioning");
		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, name + "-service/build.gradle",
			"org.osgi.annotation.versioning");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "com.test", "-Dpackage=" + packageName,
			"-DliferayVersion=7.0");

		if (_isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilder71() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-api/build.gradle",
			"biz.aQute.bndlib\", version: \"3.5.0",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-service/build.gradle",
			"biz.aQute.bndlib\", version: \"3.5.0",
			"com.liferay.portal.spring.extender.api\", version: \"3.0.0",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, name + "-api/build.gradle",
			"org.osgi.annotation.versioning");
		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, name + "-service/build.gradle",
			"org.osgi.annotation.versioning");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "com.test", "-Dpackage=" + packageName,
			"-DliferayVersion=7.1");

		_testBuildTemplateServiceBuilder(
			gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
			packageName, "");
	}

	@Test
	public void testBuildTemplateServiceBuilder72() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-api/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0",
			"org.osgi.annotation.versioning\", version: \"1.1.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-service/build.gradle",
			"com.liferay.petra.lang\", version: \"3.0.0\"",
			"com.liferay.petra.string\", version: \"3.0.0\"",
			"com.liferay.portal.aop.api\", version: \"1.0.0\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0",
			"org.osgi.annotation.versioning\", version: \"1.1.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-service/service.xml",
			"dependency-injector=\"ds\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-service/bnd.bnd",
			"-dsannotations-options: inherit");

		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, name + "-api/build.gradle", "biz.aQute.bndlib",
			"com.liferay.petra.lang", "com.liferay.petra.string");
		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, name + "-service/build.gradle",
			"biz.aQute.bndlib", "com.liferay.portal.spring.extender");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "com.test", "-Dpackage=" + packageName,
			"-DliferayVersion=7.2");

		if (_isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilder72Spring() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferayVersion", "7.2", "--dependency-injector", "spring");

		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, name + "-api/build.gradle", "biz.aQute.bnd",
			"com.liferay.petra.lang", "com.liferay.petra.string");
		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, name + "-service/build.gradle", "biz.aQute.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-api/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0",
			"org.osgi.annotation.versioning\", version: \"1.1.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-service/build.gradle",
			"com.liferay.petra.lang\", version: \"3.0.0\"",
			"com.liferay.petra.string\", version: \"3.0.0\"",
			"com.liferay.portal.aop.api\", version: \"1.0.0\"",
			"com.liferay.portal.spring.extender.api\", version: \"3.0.0",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0",
			"org.osgi.annotation.versioning\", version: \"1.1.0");
		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, name + "-service/bnd.bnd",
			"-dsannotations-options: inherit");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "com.test", "-Dpackage=" + packageName,
			"-DliferayVersion=7.2", "-DdependencyInjector=spring");

		if (_isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderCheckExports() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.2");

		File gradleServiceXml = new File(
			new File(gradleProjectDir, name + "-service"), "service.xml");

		Consumer<Document> consumer = document -> {
			Element documentElement = document.getDocumentElement();

			documentElement.setAttribute("package-path", "com.liferay.test");
		};

		ProjectTemplatesTestUtil.editXml(gradleServiceXml, consumer);

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "com.test", "-Dpackage=" + packageName,
			"-DliferayVersion=7.2");

		File mavenServiceXml = new File(
			new File(mavenProjectDir, name + "-service"), "service.xml");

		ProjectTemplatesTestUtil.editXml(mavenServiceXml, consumer);

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-api/bnd.bnd", "Export-Package:\\",
			packageName + ".exception,\\", packageName + ".model,\\",
			packageName + ".service,\\", packageName + ".service.persistence");

		if (_isBuildProjects()) {
			Optional<String> stdOutput = ProjectTemplatesTestUtil.executeGradle(
				gradleProjectDir, false, true,
				name + "-service" +
					ProjectTemplatesTestConstants.GRADLE_TASK_PATH_BUILD);

			Assert.assertTrue(stdOutput.isPresent());

			String gradleOutput = stdOutput.get();

			Assert.assertTrue(
				"Expected gradle output to include build error. " +
					gradleOutput,
				gradleOutput.contains("Exporting an empty package"));

			String mavenOutput = _executeMaven(
				mavenProjectDir, true,
				ProjectTemplatesTestConstants.MAVEN_GOAL_PACKAGE);

			Assert.assertTrue(
				"Expected maven output to include build error. " + mavenOutput,
				mavenOutput.contains("Exporting an empty package"));
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderNestedPath70() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "ws-nested-path");

		File destinationDir = new File(
			workspaceProjectDir, "modules/nested/path");

		Assert.assertTrue(destinationDir.mkdirs());

		File gradleProjectDir =
			ProjectTemplatesTestUtil.buildTemplateWithGradle(
				destinationDir, "service-builder", "sample", "--package-name",
				"com.test.sample", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "sample-service/build.gradle",
			"compileOnly project(\":modules:nested:path:sample:sample-api\")");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", "sample", "com.test",
			"-Dpackage=com.test.sample", "-DliferayVersion=7.0");

		if (_isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, workspaceProjectDir,
				"sample", "com.test.sample", ":modules:nested:path:sample");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderNestedPath71() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "ws-nested-path");

		File destinationDir = new File(
			workspaceProjectDir, "modules/nested/path");

		Assert.assertTrue(destinationDir.mkdirs());

		File gradleProjectDir =
			ProjectTemplatesTestUtil.buildTemplateWithGradle(
				destinationDir, "service-builder", "sample", "--package-name",
				"com.test.sample", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "sample-service/build.gradle",
			"compileOnly project(\":modules:nested:path:sample:sample-api\")");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", "sample", "com.test",
			"-Dpackage=com.test.sample", "-DliferayVersion=7.1");

		if (_isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, workspaceProjectDir,
				"sample", "com.test.sample", ":modules:nested:path:sample");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderNestedPath72() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "ws-nested-path");

		File destinationDir = new File(
			workspaceProjectDir, "modules/nested/path");

		Assert.assertTrue(destinationDir.mkdirs());

		File gradleProjectDir =
			ProjectTemplatesTestUtil.buildTemplateWithGradle(
				destinationDir, "service-builder", "sample", "--package-name",
				"com.test.sample", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "sample-service/build.gradle",
			"compileOnly project(\":modules:nested:path:sample:sample-api\")");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", "sample", "com.test",
			"-Dpackage=com.test.sample", "-DliferayVersion=7.2");

		if (_isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, workspaceProjectDir,
				"sample", "com.test.sample", ":modules:nested:path:sample");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderTargetPlatformEnabled70()
		throws Exception {

		File workspaceDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "workspace");

		_enableTargetPlatformInWorkspace(workspaceDir, "7.0.6");

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir =
			ProjectTemplatesTestUtil.buildTemplateWithGradle(
				modulesDir, "service-builder", "foo", "--package-name", "test",
				"--liferay-version", "7.0", "--dependency-management-enabled");

		ProjectTemplatesTestUtil.testContains(
			workspaceProjectDir, "foo-api/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL, "biz.aQute.bndlib");
		ProjectTemplatesTestUtil.testContains(
			workspaceProjectDir, "foo-service/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL, "biz.aQute.bndlib",
			"com.liferay.portal.spring.extender");

		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "foo-api/build.gradle",
			"org.osgi.annotation.versioning");
		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "foo-service/build.gradle",
			"org.osgi.annotation.versioning");

		if (_isBuildProjects()) {
			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir,
				":modules:foo:foo-service" + _GRADLE_TASK_PATH_BUILD_SERVICE);

			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir, ":modules:foo:foo-api:build");

			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir, ":modules:foo:foo-service:build");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderTargetPlatformEnabled71()
		throws Exception {

		File workspaceDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "workspace");

		_enableTargetPlatformInWorkspace(workspaceDir, "7.1.0");

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir =
			ProjectTemplatesTestUtil.buildTemplateWithGradle(
				modulesDir, "service-builder", "foo", "--package-name", "test",
				"--liferay-version", "7.1", "--dependency-management-enabled");

		ProjectTemplatesTestUtil.testContains(
			workspaceProjectDir, "foo-api/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL, "biz.aQute.bndlib");
		ProjectTemplatesTestUtil.testContains(
			workspaceProjectDir, "foo-service/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL, "biz.aQute.bndlib",
			"com.liferay.portal.spring.extender.api");

		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "foo-api/build.gradle",
			"org.osgi.annotation.versioning");
		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "foo-service/build.gradle",
			"org.osgi.annotation.versioning");

		if (_isBuildProjects()) {
			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir,
				":modules:foo:foo-service" + _GRADLE_TASK_PATH_BUILD_SERVICE);

			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir, ":modules:foo:foo-api:build");

			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir, ":modules:foo:foo-service:build");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderTargetPlatformEnabled72()
		throws Exception {

		File workspaceDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "workspace");

		_enableTargetPlatformInWorkspace(workspaceDir);

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir =
			ProjectTemplatesTestUtil.buildTemplateWithGradle(
				modulesDir, "service-builder", "foo", "--package-name", "test",
				"--liferay-version", "7.2", "--dependency-management-enabled");

		ProjectTemplatesTestUtil.testContains(
			workspaceProjectDir, "foo-api/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL, "org.osgi.annotation.versioning");
		ProjectTemplatesTestUtil.testContains(
			workspaceProjectDir, "foo-service/build.gradle",
			"com.liferay.petra.lang", "com.liferay.petra.string",
			"com.liferay.portal.aop.api", _DEPENDENCY_PORTAL_KERNEL,
			"org.osgi.annotation.versioning");

		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "foo-api/build.gradle", "biz.aQute.bndlib");
		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "foo-service/build.gradle", "biz.aQute.bndlib",
			"com.liferay.portal.spring.extender");

		if (_isBuildProjects()) {
			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir,
				":modules:foo:foo-service" + _GRADLE_TASK_PATH_BUILD_SERVICE);

			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir, ":modules:foo:foo-api:build");

			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir, ":modules:foo:foo-service:build");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderWithDashes70() throws Exception {
		String name = "backend-integration";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-api/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-service/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.6.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "com.test", "-Dpackage=" + packageName,
			"-DliferayVersion=7.0");

		if (_isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderWithDashes71() throws Exception {
		String name = "backend-integration";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-api/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-service/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "com.test", "-Dpackage=" + packageName,
			"-DliferayVersion=7.1");

		if (_isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testBuildTemplateServiceBuilderWithDashes72() throws Exception {
		String name = "backend-integration";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-api/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, name + "-service/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "com.test", "-Dpackage=" + packageName,
			"-DliferayVersion=7.2");

		if (_isBuildProjects()) {
			_testBuildTemplateServiceBuilder(
				gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
				packageName, "");
		}
	}

	@Test
	public void testBuildTemplateServiceInWorkspace() throws Exception {
		File workspaceDir = _buildWorkspace();

		_enableTargetPlatformInWorkspace(workspaceDir);

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir =
			ProjectTemplatesTestUtil.buildTemplateWithGradle(
				modulesDir, "service", "servicepreaction", "--class-name",
				"FooAction", "--service",
				"com.liferay.portal.kernel.events.LifecycleAction",
				"--dependency-management-enabled");

		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");
		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "build.gradle", "version: \"[0-9].*");

		if (_isBuildProjects()) {
			_writeServiceClass(workspaceProjectDir);

			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir, ":modules:servicepreaction:build");

			ProjectTemplatesTestUtil.testExists(
				workspaceProjectDir, "build/libs/servicepreaction-1.0.0.jar");
		}
	}

	@Test
	public void testBuildTemplateServiceWrapper70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service-wrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0\"",
			"apply plugin: \"com.liferay.plugin\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/serviceoverride/Serviceoverride.java",
			"package serviceoverride;",
			"import com.liferay.portal.kernel.service.UserLocalServiceWrapper;",
			"service = ServiceWrapper.class",
			"public class Serviceoverride extends UserLocalServiceWrapper {",
			"public Serviceoverride() {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-wrapper", "serviceoverride", "com.test",
			"-DclassName=Serviceoverride", "-Dpackage=serviceoverride",
			"-DserviceWrapperClass=" +
				"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateServiceWrapper71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service-wrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-wrapper", "serviceoverride", "com.test",
			"-DclassName=Serviceoverride", "-Dpackage=serviceoverride",
			"-DserviceWrapperClass=" +
				"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"-DliferayVersion=7.1");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateServiceWrapper72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service-wrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-wrapper", "serviceoverride", "com.test",
			"-DclassName=Serviceoverride", "-Dpackage=serviceoverride",
			"-DserviceWrapperClass=" +
				"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"-DliferayVersion=7.2");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateServiceWrapperInWorkspace() throws Exception {
		File workspaceDir = _buildWorkspace();

		_enableTargetPlatformInWorkspace(workspaceDir);

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir =
			ProjectTemplatesTestUtil.buildTemplateWithGradle(
				modulesDir, "service-wrapper", "serviceoverride", "--service",
				"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
				"--dependency-management-enabled");

		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");
		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "build.gradle", "version: \"[0-9].*");

		if (_isBuildProjects()) {
			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir, ":modules:serviceoverride:build");

			ProjectTemplatesTestUtil.testExists(
				workspaceProjectDir, "build/libs/serviceoverride-1.0.0.jar");
		}
	}

	@Test
	public void testBuildTemplateSimulationPanelEntry70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"simulation-panel-entry", "simulator", "--package-name",
			"test.simulator", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.3.0\"",
			"apply plugin: \"com.liferay.plugin\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/test/simulator/application/list" +
				"/SimulatorSimulationPanelApp.java",
			"public class SimulatorSimulationPanelApp",
			"extends BaseJSPPanelApp");

		File mavenProjectDir = _buildTemplateWithMaven(
			"simulation-panel-entry", "simulator", "com.test",
			"-DclassName=Simulator", "-Dpackage=test.simulator",
			"-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateSimulationPanelEntry71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"simulation-panel-entry", "simulator", "--package-name",
			"test.simulator", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"simulation-panel-entry", "simulator", "com.test",
			"-DclassName=Simulator", "-Dpackage=test.simulator",
			"-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateSimulationPanelEntry72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"simulation-panel-entry", "simulator", "--package-name",
			"test.simulator", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"simulation-panel-entry", "simulator", "com.test",
			"-DclassName=Simulator", "-Dpackage=test.simulator",
			"-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateSimulationPanelEntryInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"simulation-panel-entry", "test.simulator",
			"build/libs/test.simulator-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateSocialBookmark70() throws Exception {
		_buildTemplateWithGradle(
			"social-bookmark", "foo", "--package-name", "com.liferay.test",
			"--liferay-version", "7.0");
	}

	@Test
	public void testBuildTemplateSocialBookmark71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"social-bookmark", "foo", "--package-name", "com.liferay.test",
			"--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");
		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "build.gradle");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/social/bookmark" +
				"/FooSocialBookmark.java",
			"public class FooSocialBookmark implements SocialBookmark");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/page.jsp",
			"<clay:link");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/resources/content/Language.properties",
			"foo=Foo");

		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/social/bookmark" +
				"/FooSocialBookmark.java",
			"private ResourceBundleLoader");
		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/social/bookmark" +
				"/FooSocialBookmark.java",
			"protected ResourceBundleLoader");

		File mavenProjectDir = _buildTemplateWithMaven(
			"social-bookmark", "foo", "com.test", "-DclassName=Foo",
			"-Dpackage=com.liferay.test", "-DliferayVersion=7.1");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateTemplateContextContributor70()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"template-context-contributor", "blade-test", "--liferay-version",
			"7.0");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0\"",
			"apply plugin: \"com.liferay.plugin\"");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/blade/test/context/contributor" +
				"/BladeTestTemplateContextContributor.java",
			"public class BladeTestTemplateContextContributor",
			"implements TemplateContextContributor");

		File mavenProjectDir = _buildTemplateWithMaven(
			"template-context-contributor", "blade-test", "com.test",
			"-DclassName=BladeTest", "-Dpackage=blade.test",
			"-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateTemplateContextContributor71()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"template-context-contributor", "blade-test", "--liferay-version",
			"7.1");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"template-context-contributor", "blade-test", "com.test",
			"-DclassName=BladeTest", "-Dpackage=blade.test",
			"-DliferayVersion=7.1");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateTemplateContextContributor72()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"template-context-contributor", "blade-test", "--liferay-version",
			"7.2");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"template-context-contributor", "blade-test", "com.test",
			"-DclassName=BladeTest", "-Dpackage=blade.test",
			"-DliferayVersion=7.2");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateTemplateContextContributorInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"template-context-contributor", "blade-test",
			"build/libs/blade.test-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateTheme70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme", "theme-test", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"name: \"com.liferay.gradle.plugins.theme.builder\"",
			"apply plugin: \"com.liferay.portal.tools.theme.builder\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=theme-test");

		File mavenProjectDir = _buildTemplateWithMaven(
			"theme", "theme-test", "com.test", "-DliferayVersion=7.0");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "pom.xml",
			"com.liferay.portal.tools.theme.builder");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateTheme71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme", "theme-test", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"name: \"com.liferay.gradle.plugins.theme.builder\"",
			"apply plugin: \"com.liferay.portal.tools.theme.builder\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=theme-test");

		File mavenProjectDir = _buildTemplateWithMaven(
			"theme", "theme-test", "com.test", "-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "pom.xml",
			"com.liferay.portal.tools.theme.builder");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateTheme72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme", "theme-test", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"name: \"com.liferay.gradle.plugins.theme.builder\"",
			"apply plugin: \"com.liferay.portal.tools.theme.builder\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=theme-test");

		File mavenProjectDir = _buildTemplateWithMaven(
			"theme", "theme-test", "com.test", "-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "pom.xml",
			"com.liferay.portal.tools.theme.builder");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateThemeContributorCustom() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme-contributor", "my-contributor-custom", "--contributor-type",
			"foo-bar");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "bnd.bnd",
			"Liferay-Theme-Contributor-Type: foo-bar",
			"Web-ContextPath: /foo-bar-theme-contributor");
		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/foo-bar.scss");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/js/foo-bar.js");

		File mavenProjectDir = _buildTemplateWithMaven(
			"theme-contributor", "my-contributor-custom", "com.test",
			"-DcontributorType=foo-bar", "-Dpackage=my.contributor.custom");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateThemeContributorCustom71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme-contributor", "my-contributor-custom", "--contributor-type",
			"foo-bar", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "bnd.bnd",
			"Liferay-Theme-Contributor-Type: foo-bar",
			"Web-ContextPath: /foo-bar-theme-contributor");
		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/foo-bar.scss");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/js/foo-bar.js");

		File mavenProjectDir = _buildTemplateWithMaven(
			"theme-contributor", "my-contributor-custom", "com.test",
			"-DcontributorType=foo-bar", "-Dpackage=my.contributor.custom",
			"-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateThemeContributorCustom72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme-contributor", "my-contributor-custom", "--contributor-type",
			"foo-bar", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "bnd.bnd",
			"Liferay-Theme-Contributor-Type: foo-bar",
			"Web-ContextPath: /foo-bar-theme-contributor");
		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/foo-bar.scss");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/js/foo-bar.js");

		File mavenProjectDir = _buildTemplateWithMaven(
			"theme-contributor", "my-contributor-custom", "com.test",
			"-DcontributorType=foo-bar", "-Dpackage=my.contributor.custom",
			"-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateThemeContributorDefaults() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme-contributor", "my-contributor-default");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "bnd.bnd",
			"Liferay-Theme-Contributor-Type: my-contributor-default",
			"Web-ContextPath: /my-contributor-default-theme-contributor");
	}

	@Test
	public void testBuildTemplateThemeContributorinWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"theme-contributor", "my-contributor",
			"build/libs/my.contributor-1.0.0.jar",
			"--dependency-management-enabled");
	}

	@Test
	public void testBuildTemplateThemeInWorkspace() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("theme", "theme-test");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle", "buildscript {",
			"apply plugin: \"com.liferay.portal.tools.theme.builder\"",
			"repositories {");

		File workspaceDir = _buildWorkspace();

		File warsDir = new File(workspaceDir, "wars");

		File workspaceProjectDir =
			ProjectTemplatesTestUtil.buildTemplateWithGradle(
				warsDir, "theme", "theme-test",
				"--dependency-management-enabled");

		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");
		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "build.gradle", "version: \"[0-9].*");

		if (_isBuildProjects()) {
			ProjectTemplatesTestUtil.executeGradle(
				gradleProjectDir,
				ProjectTemplatesTestConstants.GRADLE_TASK_PATH_BUILD);

			File gradleWarFile = ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "build/libs/theme-test.war");

			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir, ":wars:theme-test:build");

			File workspaceWarFile = ProjectTemplatesTestUtil.testExists(
				workspaceProjectDir, "build/libs/theme-test.war");

			ProjectTemplatesTestUtil.testWarsDiff(
				gradleWarFile, workspaceWarFile);
		}
	}

	@Test
	public void testBuildTemplateWarCoreExt() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-core-ext", "test-war-core-ext");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle", "buildscript {", "repositories {",
			"group: \"com.liferay\", name: \"com.liferay.gradle.plugins\"",
			"apply plugin: \"com.liferay.ext.plugin\"",
			"apply plugin: \"eclipse\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/extImpl/resources/META-INF/ext-spring.xml");
	}

	@Test
	public void testBuildTemplateWarCoreExtInWorkspace() throws Exception {
		File modulesDir = new File(_buildWorkspace(), "modules");

		File projectDir = ProjectTemplatesTestUtil.buildTemplateWithGradle(
			modulesDir, "war-core-ext", "test-war-core-ext");

		ProjectTemplatesTestUtil.testNotContains(
			projectDir, "build.gradle", true, "^repositories \\{.*");
		ProjectTemplatesTestUtil.testNotContains(
			projectDir, "build.gradle", "buildscript",
			"com.liferay.ext.plugin");
	}

	@Test
	public void testBuildTemplateWarHook70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-hook", "WarHook", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/resources/portal.properties");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-hook.xml");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/warhook/WarHookLoginPostAction.java",
			"public class WarHookLoginPostAction extends Action");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/java/warhook/WarHookStartupAction.java",
			"public class WarHookStartupAction extends SimpleAction");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=WarHook");

		File mavenProjectDir = _buildTemplateWithMaven(
			"war-hook", "WarHook", "warhook", "-DclassName=WarHook",
			"-Dpackage=warhook", "-DliferayVersion=7.0");

		ProjectTemplatesTestUtil.testContains(mavenProjectDir, "pom.xml");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarHook71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-hook", "WarHook", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"war-hook", "WarHook", "warhook", "-DclassName=WarHook",
			"-Dpackage=warhook", "-DliferayVersion=7.1");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarHook72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-hook", "WarHook", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"war-hook", "WarHook", "warhook", "-DclassName=WarHook",
			"-Dpackage=warhook", "-DliferayVersion=7.2");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarHookInWorkspace() throws Exception {
		_testBuildTemplateProjectWarInWorkspace("war-hook", "WarHook");
	}

	@Test
	public void testBuildTemplateWarMVCPortlet70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "WarMVCPortlet", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/webapp/init.jsp");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/webapp/view.jsp");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0\"",
			"apply plugin: \"com.liferay.css.builder\"",
			"apply plugin: \"war\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=WarMVCPortlet");

		File mavenProjectDir = _buildTemplateWithMaven(
			"war-mvc-portlet", "WarMVCPortlet", "warmvcportlet",
			"-DclassName=WarMVCPortlet", "-Dpackage=WarMVCPortlet",
			"-DliferayVersion=7.0");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "pom.xml", "maven-war-plugin",
			"com.liferay.css.builder");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarMVCPortlet71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "WarMVCPortlet", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"war-mvc-portlet", "WarMVCPortlet", "warmvcportlet",
			"-DclassName=WarMVCPortlet", "-Dpackage=WarMVCPortlet",
			"-DliferayVersion=7.1");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarMVCPortlet72() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "WarMVCPortlet", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"war-mvc-portlet", "WarMVCPortlet", "warmvcportlet",
			"-DclassName=WarMVCPortlet", "-Dpackage=WarMVCPortlet",
			"-DliferayVersion=7.2");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarMVCPortletInWorkspace() throws Exception {
		_testBuildTemplateProjectWarInWorkspace(
			"war-mvc-portlet", "WarMVCPortlet");
	}

	@Test
	public void testBuildTemplateWarMVCPortletWithPackage() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "WarMVCPortlet", "--package-name",
			"com.liferay.test");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/webapp/init.jsp");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/webapp/view.jsp");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.css.builder\"",
			"apply plugin: \"war\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=WarMVCPortlet");

		File mavenProjectDir = _buildTemplateWithMaven(
			"war-mvc-portlet", "WarMVCPortlet", "com.liferay.test",
			"-DclassName=WarMVCPortlet", "-Dpackage=com.liferay.test");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarMVCPortletWithPortletName()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "WarMVCPortlet");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/webapp/init.jsp");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/webapp/view.jsp");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.css.builder\"",
			"apply plugin: \"war\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=WarMVCPortlet");

		File mavenProjectDir = _buildTemplateWithMaven(
			"war-mvc-portlet", "WarMVCPortlet", "warmvcportlet",
			"-DclassName=WarMVCPortlet", "-Dpackage=WarMVCPortlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarMVCPortletWithPortletSuffix()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "WarMVC-portlet");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/webapp/init.jsp");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/webapp/view.jsp");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.css.builder\"",
			"apply plugin: \"war\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=WarMVC-portlet");

		File mavenProjectDir = _buildTemplateWithMaven(
			"war-mvc-portlet", "WarMVC-portlet", "warmvc.portlet",
			"-DclassName=WarMVCPortlet", "-Dpackage=WarMVC.portlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWithGradle() throws Exception {
		ProjectTemplatesTestUtil.buildTemplateWithGradle(
			temporaryFolder.newFolder(), null, "foo-portlet", false, false);
		ProjectTemplatesTestUtil.buildTemplateWithGradle(
			temporaryFolder.newFolder(), null, "foo-portlet", false, true);
		ProjectTemplatesTestUtil.buildTemplateWithGradle(
			temporaryFolder.newFolder(), null, "foo-portlet", true, false);
		ProjectTemplatesTestUtil.buildTemplateWithGradle(
			temporaryFolder.newFolder(), null, "foo-portlet", true, true);
	}

	@Test
	public void testBuildTemplateWithPackageName() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"", "barfoo", "--package-name", "foo.bar");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-SymbolicName: foo.bar");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");

		File mavenProjectDir = _buildTemplateWithMaven(
			"mvc-portlet", "barfoo", "com.test", "-DclassName=Barfoo",
			"-Dpackage=foo.bar");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWorkspace() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "foows");

		ProjectTemplatesTestUtil.testExists(
			workspaceProjectDir, "configs/dev/portal-ext.properties");
		ProjectTemplatesTestUtil.testExists(
			workspaceProjectDir, "gradle.properties");
		ProjectTemplatesTestUtil.testExists(workspaceProjectDir, "modules");
		ProjectTemplatesTestUtil.testExists(workspaceProjectDir, "themes");
		ProjectTemplatesTestUtil.testExists(workspaceProjectDir, "wars");

		ProjectTemplatesTestUtil.testNotExists(
			workspaceProjectDir, "modules/pom.xml");
		ProjectTemplatesTestUtil.testNotExists(
			workspaceProjectDir, "themes/pom.xml");
		ProjectTemplatesTestUtil.testNotExists(
			workspaceProjectDir, "wars/pom.xml");

		File moduleProjectDir =
			ProjectTemplatesTestUtil.buildTemplateWithGradle(
				new File(workspaceProjectDir, "modules"), "", "foo-portlet");

		ProjectTemplatesTestUtil.testNotContains(
			moduleProjectDir, "build.gradle", "buildscript", "repositories");

		if (_isBuildProjects()) {
			ProjectTemplatesTestUtil.executeGradle(
				workspaceProjectDir,
				":modules:foo-portlet" +
					ProjectTemplatesTestConstants.GRADLE_TASK_PATH_BUILD);

			ProjectTemplatesTestUtil.testExists(
				moduleProjectDir, "build/libs/foo.portlet-1.0.0.jar");
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateWorkspaceExistingFile() throws Exception {
		File destinationDir = temporaryFolder.newFolder("existing-file");

		_createNewFiles("foo", destinationDir);

		ProjectTemplatesTestUtil.buildTemplateWithGradle(
			destinationDir, WorkspaceUtil.WORKSPACE, "foo");
	}

	@Test
	public void testBuildTemplateWorkspaceForce() throws Exception {
		File destinationDir = temporaryFolder.newFolder("existing-file");

		_createNewFiles("foo", destinationDir);

		ProjectTemplatesTestUtil.buildTemplateWithGradle(
			destinationDir, WorkspaceUtil.WORKSPACE, "forced", "--force");
	}

	@Test
	public void testBuildTemplateWorkspaceLocalProperties() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "foo");

		ProjectTemplatesTestUtil.testExists(
			workspaceProjectDir, "gradle-local.properties");

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

		ProjectTemplatesTestUtil.buildTemplateWithGradle(
			new File(workspaceProjectDir, modulesDirName), "", "foo-portlet");

		ProjectTemplatesTestUtil.executeGradle(
			workspaceProjectDir,
			":" + modulesDirName.replace('/', ':') + ":foo-portlet" +
				_GRADLE_TASK_PATH_DEPLOY);

		ProjectTemplatesTestUtil.testExists(
			workspaceProjectDir, homeDirName + "/osgi/modules/foo.portlet.jar");
	}

	@Test
	public void testBuildTemplateWorkspaceWith70() throws Exception {
		File gradleWorkspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "withportlet", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleWorkspaceProjectDir, "gradle.properties", true,
			".*liferay.workspace.bundle.url=.*liferay.com/portal/7.0.*");

		File gradlePropertiesFile = new File(
			gradleWorkspaceProjectDir, "gradle.properties");

		_testPropertyKeyExists(
			gradlePropertiesFile, "liferay.workspace.bundle.url");

		File mavenWorkspaceProjectDir = _buildTemplateWithMaven(
			WorkspaceUtil.WORKSPACE, "withportlet", "com.test",
			"-DliferayVersion=7.0");

		ProjectTemplatesTestUtil.testContains(
			mavenWorkspaceProjectDir, "pom.xml",
			"<liferay.workspace.bundle.url>", "liferay.com/portal/7.0.");
	}

	@Test
	public void testBuildTemplateWorkspaceWith71() throws Exception {
		File gradleWorkspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "withportlet", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleWorkspaceProjectDir, "gradle.properties", true,
			".*liferay.workspace.bundle.url=.*liferay.com/portal/7.1.*");

		File gradlePropertiesFile = new File(
			gradleWorkspaceProjectDir, "gradle.properties");

		_testPropertyKeyExists(
			gradlePropertiesFile, "liferay.workspace.bundle.url");

		File mavenWorkspaceProjectDir = _buildTemplateWithMaven(
			WorkspaceUtil.WORKSPACE, "withportlet", "com.test",
			"-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenWorkspaceProjectDir, "pom.xml",
			"<liferay.workspace.bundle.url>", "liferay.com/portal/7.1");
	}

	@Test
	public void testBuildTemplateWorkspaceWith72() throws Exception {
		File gradleWorkspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "withportlet", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testContains(
			gradleWorkspaceProjectDir, "gradle.properties", true,
			".*liferay.workspace.bundle.url=.*liferay.com/portal/7.2.*");

		File gradlePropertiesFile = new File(
			gradleWorkspaceProjectDir, "gradle.properties");

		_testPropertyKeyExists(
			gradlePropertiesFile, "liferay.workspace.bundle.url");

		File mavenWorkspaceProjectDir = _buildTemplateWithMaven(
			WorkspaceUtil.WORKSPACE, "withportlet", "com.test",
			"-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenWorkspaceProjectDir, "pom.xml",
			"<liferay.workspace.bundle.url>", "liferay.com/portal/7.2");
	}

	@Test
	public void testBuildTemplateWorkspaceWithPortlet() throws Exception {
		File gradleWorkspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "withportlet");

		File gradleModulesDir = new File(gradleWorkspaceProjectDir, "modules");

		ProjectTemplatesTestUtil.buildTemplateWithGradle(
			gradleModulesDir, "mvc-portlet", "foo-portlet");

		File mavenWorkspaceProjectDir = _buildTemplateWithMaven(
			WorkspaceUtil.WORKSPACE, "withportlet", "com.test");

		File mavenModulesDir = new File(mavenWorkspaceProjectDir, "modules");

		_buildTemplateWithMaven(
			mavenWorkspaceProjectDir.getParentFile(), mavenModulesDir,
			"mvc-portlet", "foo-portlet", "com.test", "-DclassName=Foo",
			"-Dpackage=foo.portlet", "-DprojectType=workspace");

		if (_isBuildProjects()) {
			ProjectTemplatesTestUtil.executeGradle(
				gradleWorkspaceProjectDir,
				":modules:foo-portlet" +
					ProjectTemplatesTestConstants.GRADLE_TASK_PATH_BUILD);

			ProjectTemplatesTestUtil.testExists(
				gradleModulesDir,
				"foo-portlet/build/libs/foo.portlet-1.0.0.jar");

			_executeMaven(
				mavenModulesDir,
				ProjectTemplatesTestConstants.MAVEN_GOAL_PACKAGE);

			ProjectTemplatesTestUtil.testExists(
				mavenModulesDir, "foo-portlet/target/foo-portlet-1.0.0.jar");
		}
	}

	@Test
	public void testCompareGradlePluginVersions() throws Exception {
		String template = "mvc-portlet";
		String name = "foo";

		File gradleProjectDir = _buildTemplateWithGradle(template, name);

		File workspaceDir = _buildWorkspace();

		File modulesDir = new File(workspaceDir, "modules");

		ProjectTemplatesTestUtil.buildTemplateWithGradle(
			modulesDir, template, name);

		Optional<String> result = ProjectTemplatesTestUtil.executeGradle(
			gradleProjectDir, true,
			ProjectTemplatesTestConstants.GRADLE_TASK_PATH_BUILD);

		Matcher matcher = _gradlePluginVersionPattern.matcher(result.get());

		String standaloneGradlePluginVersion = null;

		if (matcher.matches()) {
			standaloneGradlePluginVersion = matcher.group(1);
		}

		result = ProjectTemplatesTestUtil.executeGradle(
			workspaceDir, true, ":modules:" + name + ":clean");

		matcher = _gradlePluginVersionPattern.matcher(result.get());

		String workspaceGradlePluginVersion = null;

		if (matcher.matches()) {
			workspaceGradlePluginVersion = matcher.group(1);
		}

		Assert.assertEquals(
			"com.liferay.plugin versions do not match",
			standaloneGradlePluginVersion, workspaceGradlePluginVersion);
	}

	@Test
	public void testCompareServiceBuilderPluginVersions() throws Exception {
		String name = "sample";
		String packageName = "com.test.sample";
		String serviceProjectName = name + "-service";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName);

		Optional<String> gradleResult = ProjectTemplatesTestUtil.executeGradle(
			gradleProjectDir, true, ":" + serviceProjectName + ":dependencies");

		String gradleServiceBuilderVersion = null;

		Matcher matcher = _serviceBuilderVersionPattern.matcher(
			gradleResult.get());

		if (matcher.matches()) {
			gradleServiceBuilderVersion = matcher.group(1);
		}

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "com.test", "-Dpackage=" + packageName);

		String mavenResult = _executeMaven(
			new File(mavenProjectDir, serviceProjectName),
			_MAVEN_GOAL_BUILD_SERVICE);

		matcher = _serviceBuilderVersionPattern.matcher(mavenResult);

		String mavenServiceBuilderVersion = null;

		if (matcher.matches()) {
			mavenServiceBuilderVersion = matcher.group(1);
		}

		Assert.assertEquals(
			"com.liferay.portal.tools.service.builder versions do not match",
			gradleServiceBuilderVersion, mavenServiceBuilderVersion);
	}

	@Test
	public void testListTemplates() throws Exception {
		final Map<String, String> expectedTemplates = new TreeMap<>();

		try (DirectoryStream<Path> directoryStream =
				FileTestUtil.getProjectTemplatesDirectoryStream()) {

			for (Path path : directoryStream) {
				String fileName = String.valueOf(path.getFileName());

				String template = fileName.substring(
					FileTestUtil.PROJECT_TEMPLATE_DIR_PREFIX.length());

				if (!template.equals(WorkspaceUtil.WORKSPACE)) {
					Properties properties = FileUtil.readProperties(
						path.resolve("bnd.bnd"));

					String bundleDescription = properties.getProperty(
						"Bundle-Description");

					expectedTemplates.put(template, bundleDescription);
				}
			}
		}

		Assert.assertEquals(expectedTemplates, ProjectTemplates.getTemplates());
	}

	@Test
	public void testListTemplatesWithCustomArchetypesDir() throws Exception {
		Properties archetypesProperties =
			ProjectTemplatesUtil.getProjectTemplateJarVersionsProperties();

		Set<String> artifactIds = archetypesProperties.stringPropertyNames();

		Iterator<String> artifactIdIterator = artifactIds.iterator();

		String artifactId = artifactIdIterator.next();

		File templateFile = ProjectTemplatesUtil.getArchetypeFile(artifactId);

		Path templateFilePath = templateFile.toPath();

		File customArchetypesDir = temporaryFolder.newFolder();

		Path customArchetypesDirPath = customArchetypesDir.toPath();

		Files.copy(
			templateFilePath,
			customArchetypesDirPath.resolve(
				"custom.name.project.templates.foo.bar-1.2.3.jar"));

		Map<String, String> customTemplatesMap = ProjectTemplates.getTemplates(
			Collections.singletonList(customArchetypesDir));

		Map<String, String> templatesMap = ProjectTemplates.getTemplates();

		Assert.assertEquals(customTemplatesMap.size(), templatesMap.size() + 1);
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static void _addNpmrc(File projectDir) throws IOException {
		File npmrcFile = new File(projectDir, ".npmrc");

		String content = "sass_binary_site=" + _NODEJS_NPM_CI_SASS_BINARY_SITE;

		Files.write(
			npmrcFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
	}

	private static File _buildTemplateWithMaven(
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

		String liferayVersion = null;

		boolean liferayVersionSet = false;
		boolean projectTypeSet = false;

		for (String arg : args) {
			completeArgs.add(arg);

			if (arg.startsWith("-DliferayVersion=")) {
				liferayVersion = arg.substring(17);
				liferayVersionSet = true;
			}
			else if (arg.startsWith("-DprojectType=")) {
				projectTypeSet = true;
			}
		}

		if (!liferayVersionSet) {
			completeArgs.add("-DliferayVersion=7.2");
			liferayVersion = "7.2";
		}

		if (!projectTypeSet) {
			completeArgs.add("-DprojectType=standalone");
		}

		if (template.startsWith("npm-") && !liferayVersion.startsWith("7.0") &&
			!liferayVersion.startsWith("7.1")) {

			_executeMaven(
				destinationDir, true, completeArgs.toArray(new String[0]));

			return destinationDir;
		}

		_executeMaven(destinationDir, completeArgs.toArray(new String[0]));

		File projectDir = new File(destinationDir, name);

		ProjectTemplatesTestUtil.testExists(projectDir, "pom.xml");
		ProjectTemplatesTestUtil.testNotExists(projectDir, "gradlew");
		ProjectTemplatesTestUtil.testNotExists(projectDir, "gradlew.bat");
		ProjectTemplatesTestUtil.testNotExists(
			projectDir, "gradle/wrapper/gradle-wrapper.jar");
		ProjectTemplatesTestUtil.testNotExists(
			projectDir, "gradle/wrapper/gradle-wrapper.properties");

		ProjectTemplatesTestUtil.testArchetyper(
			parentDir, destinationDir, projectDir, name, groupId, template,
			completeArgs);

		return projectDir;
	}

	private static void _configureExecuteNpmTask(File projectDir)
		throws Exception {

		File buildGradleFile = ProjectTemplatesTestUtil.testContains(
			projectDir, "build.gradle", "com.liferay.gradle.plugins",
			"com.liferay.plugin");

		StringBuilder sb = new StringBuilder();

		String lineSeparator = System.lineSeparator();

		sb.append(lineSeparator);

		sb.append(
			"import com.liferay.gradle.plugins.node.tasks.ExecuteNpmTask");
		sb.append(lineSeparator);

		sb.append("tasks.withType(ExecuteNpmTask) {");
		sb.append(lineSeparator);

		sb.append("\tregistry = '");
		sb.append(_NODEJS_NPM_CI_REGISTRY);
		sb.append('\'');
		sb.append(lineSeparator);

		sb.append('}');

		String executeNpmTaskScript = sb.toString();

		Files.write(
			buildGradleFile.toPath(),
			executeNpmTaskScript.getBytes(StandardCharsets.UTF_8),
			StandardOpenOption.APPEND);
	}

	private static void _configurePomNpmConfiguration(File projectDir)
		throws Exception {

		File pomXmlFile = new File(projectDir, "pom.xml");

		ProjectTemplatesTestUtil.editXml(
			pomXmlFile,
			document -> {
				try {
					NodeList nodeList =
						(NodeList)
							ProjectTemplatesTestConstants.
								pomXmlNpmInstallXPathExpression.evaluate(
									document, XPathConstants.NODESET);

					Node executionNode = nodeList.item(0);

					Element configurationElement = document.createElement(
						"configuration");

					executionNode.appendChild(configurationElement);

					Element argumentsElement = document.createElement(
						"arguments");

					configurationElement.appendChild(argumentsElement);

					Text text = document.createTextNode(
						"install --registry=" + _NODEJS_NPM_CI_REGISTRY);

					argumentsElement.appendChild(text);
				}
				catch (XPathExpressionException xpee) {
				}
			});
	}

	private static void _createNewFiles(String fileName, File... dirs)
		throws IOException {

		for (File dir : dirs) {
			File file = new File(dir, fileName);

			File parentDir = file.getParentFile();

			if (!parentDir.isDirectory()) {
				Assert.assertTrue(parentDir.mkdirs());
			}

			Assert.assertTrue(file.createNewFile());
		}
	}

	private static File _enableTargetPlatformInWorkspace(File workspaceDir)
		throws IOException {

		return _enableTargetPlatformInWorkspace(workspaceDir, "7.2.0");
	}

	private static File _enableTargetPlatformInWorkspace(
			File workspaceDir, String liferayVersion)
		throws IOException {

		File gradlePropertiesFile = new File(workspaceDir, "gradle.properties");

		String targetPlatformVersionProperty =
			"\nliferay.workspace.target.platform.version=" + liferayVersion;

		Files.write(
			gradlePropertiesFile.toPath(),
			targetPlatformVersionProperty.getBytes(),
			StandardOpenOption.APPEND);

		return gradlePropertiesFile;
	}

	private static String _executeMaven(
			File projectDir, boolean buildAndFail, String... args)
		throws Exception {

		File pomXmlFile = new File(projectDir, "pom.xml");

		if (pomXmlFile.exists()) {
			ProjectTemplatesTestUtil.editXml(
				pomXmlFile,
				document -> {
					ProjectTemplatesTestUtil.addNexusRepositoriesElement(
						document, "repositories", "repository");
					ProjectTemplatesTestUtil.addNexusRepositoriesElement(
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

	private static String _executeMaven(File projectDir, String... args)
		throws Exception {

		return _executeMaven(projectDir, false, args);
	}

	private static boolean _isBuildProjects() {
		if (Validator.isNotNull(ProjectTemplatesTestConstants.BUILD_PROJECTS) &&
			ProjectTemplatesTestConstants.BUILD_PROJECTS.equals("true")) {

			return true;
		}

		return false;
	}

	private static void _testChangePortletModelHintsXml(
			File projectDir, String serviceProjectName,
			Callable<Void> buildServiceCallable)
		throws Exception {

		buildServiceCallable.call();

		File file = ProjectTemplatesTestUtil.testExists(
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

	private static File _testEquals(
			File dir, String fileName, String expectedContent)
		throws IOException {

		File file = ProjectTemplatesTestUtil.testExists(dir, fileName);

		Assert.assertEquals(
			"Incorrect " + fileName, expectedContent,
			FileUtil.read(file.toPath()));

		return file;
	}

	private static void _testPropertyKeyExists(File file, String key)
		throws Exception {

		Properties properties = FileTestUtil.readProperties(file);

		String property = properties.getProperty(key);

		Assert.assertNotNull(
			"Expected key " + key + " to exist in properties " +
				file.getAbsolutePath(),
			property);
	}

	private static File _testStartsWith(
			File dir, String fileName, String prefix)
		throws IOException {

		File file = ProjectTemplatesTestUtil.testExists(dir, fileName);

		String content = FileUtil.read(file.toPath());

		Assert.assertTrue(
			fileName + " must start with \"" + prefix + "\"",
			content.startsWith(prefix));

		return file;
	}

	private static void _writeServiceClass(File projectDir) throws IOException {
		String importLine =
			"import com.liferay.portal.kernel.events.LifecycleAction;";
		String classLine =
			"public class FooAction implements LifecycleAction {";

		File actionJavaFile = ProjectTemplatesTestUtil.testContains(
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

	private void _buildProjects(File gradleProjectDir, File mavenProjectDir)
		throws Exception {

		File gradleOutputDir = new File(gradleProjectDir, "build/libs");
		File mavenOutputDir = new File(mavenProjectDir, "target");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, gradleOutputDir, mavenOutputDir,
			ProjectTemplatesTestConstants.GRADLE_TASK_PATH_BUILD);
	}

	private void _buildProjects(
			File gradleProjectDir, File mavenProjectDir, File gradleOutputDir,
			File mavenOutputDir, String... gradleTaskPath)
		throws Exception {

		if (_isBuildProjects()) {
			ProjectTemplatesTestUtil.executeGradle(
				gradleProjectDir, gradleTaskPath);

			Path gradleOutputPath = FileTestUtil.getFile(
				gradleOutputDir.toPath(),
				ProjectTemplatesTestConstants.OUTPUT_FILENAME_GLOB_REGEX, 1);

			Assert.assertNotNull(gradleOutputPath);

			Assert.assertTrue(Files.exists(gradleOutputPath));

			File gradleOutputFile = gradleOutputPath.toFile();

			String gradleOutputFileName = gradleOutputFile.getName();

			_executeMaven(
				mavenProjectDir,
				ProjectTemplatesTestConstants.MAVEN_GOAL_PACKAGE);

			Path mavenOutputPath = FileTestUtil.getFile(
				mavenOutputDir.toPath(),
				ProjectTemplatesTestConstants.OUTPUT_FILENAME_GLOB_REGEX, 1);

			Assert.assertNotNull(mavenOutputPath);

			Assert.assertTrue(Files.exists(mavenOutputPath));

			File mavenOutputFile = mavenOutputPath.toFile();

			String mavenOutputFileName = mavenOutputFile.getName();

			try {
				if (gradleOutputFileName.endsWith(".jar")) {
					ProjectTemplatesTestUtil.testBundlesDiff(
						gradleOutputFile, mavenOutputFile);
				}
				else if (gradleOutputFileName.endsWith(".war")) {
					ProjectTemplatesTestUtil.testWarsDiff(
						gradleOutputFile, mavenOutputFile);
				}
			}
			catch (Throwable t) {
				if (ProjectTemplatesTestConstants.TEST_DEBUG_BUNDLE_DIFFS) {
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

	private File _buildTemplateWithGradle(
			String template, String name, String... args)
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("gradle");

		return ProjectTemplatesTestUtil.buildTemplateWithGradle(
			destinationDir, template, name, args);
	}

	private File _buildTemplateWithMaven(
			String template, String name, String groupId, String... args)
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("maven");

		return _buildTemplateWithMaven(
			destinationDir, destinationDir, template, name, groupId, args);
	}

	private File _buildWorkspace() throws Exception {
		File destinationDir = temporaryFolder.newFolder("workspace");

		return ProjectTemplatesTestUtil.buildTemplateWithGradle(
			destinationDir, WorkspaceUtil.WORKSPACE, "test-workspace");
	}

	private void _testBuildTemplateNpm70(
			String template, String name, String packageName, String className)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, name, "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			ProjectTemplatesTestConstants.DEPENDENCY_MODULES_EXTENDER_API +
				", version: \"1.0.2",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "package.json",
			"build/resources/main/META-INF/resources",
			"liferay-npm-bundler\": \"2.7.0", "\"main\": \"lib/index.es.js\"");

		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, "package.json",
			"target/classes/META-INF/resources");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, name, "com.test", "-DclassName=" + className,
			"-Dpackage=" + packageName, "-DliferayVersion=7.0");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "package.json",
			"target/classes/META-INF/resources");

		ProjectTemplatesTestUtil.testNotContains(
			mavenProjectDir, "package.json",
			"build/resources/main/META-INF/resources");

		if (Validator.isNotNull(System.getenv("JENKINS_HOME"))) {
			_addNpmrc(gradleProjectDir);
			_addNpmrc(mavenProjectDir);
			_configureExecuteNpmTask(gradleProjectDir);
			_configurePomNpmConfiguration(mavenProjectDir);
		}

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	private void _testBuildTemplateNpm71(
			String template, String name, String packageName, String className)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, name, "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			ProjectTemplatesTestConstants.DEPENDENCY_MODULES_EXTENDER_API +
				", version: \"2.0.2",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "package.json",
			"build/resources/main/META-INF/resources",
			"liferay-npm-bundler\": \"2.7.0", "\"main\": \"lib/index.es.js\"");

		ProjectTemplatesTestUtil.testNotContains(
			gradleProjectDir, "package.json",
			"target/classes/META-INF/resources");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, name, "com.test", "-DclassName=" + className,
			"-Dpackage=" + packageName, "-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "package.json",
			"target/classes/META-INF/resources");

		ProjectTemplatesTestUtil.testNotContains(
			mavenProjectDir, "package.json",
			"build/resources/main/META-INF/resources");

		if (Validator.isNotNull(System.getenv("JENKINS_HOME"))) {
			_addNpmrc(gradleProjectDir);
			_addNpmrc(mavenProjectDir);
			_configureExecuteNpmTask(gradleProjectDir);
			_configurePomNpmConfiguration(mavenProjectDir);
		}

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	private void _testBuildTemplateNpmAngular70(
			String template, String name, String packageName, String className)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, name, "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			ProjectTemplatesTestConstants.DEPENDENCY_MODULES_EXTENDER_API +
				", version: \"1.0.2",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "package.json", "@angular/animations",
			"build\": \"tsc && liferay-npm-bundler");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/lib/angular-loader.ts");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, name, "com.test", "-DclassName=" + className,
			"-Dpackage=" + packageName, "-DliferayVersion=7.0");

		if (Validator.isNotNull(System.getenv("JENKINS_HOME"))) {
			_addNpmrc(gradleProjectDir);
			_addNpmrc(mavenProjectDir);
			_configureExecuteNpmTask(gradleProjectDir);
			_configurePomNpmConfiguration(mavenProjectDir);
		}

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	private void _testBuildTemplateNpmAngular71(
			String template, String name, String packageName, String className)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, name, "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			ProjectTemplatesTestConstants.DEPENDENCY_MODULES_EXTENDER_API +
				", version: \"2.0.2",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "package.json", "@angular/animations",
			"build\": \"tsc && liferay-npm-bundler");

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/lib/angular-loader.ts");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, name, "com.test", "-DclassName=" + className,
			"-Dpackage=" + packageName, "-DliferayVersion=7.1");

		if (Validator.isNotNull(System.getenv("JENKINS_HOME"))) {
			_addNpmrc(gradleProjectDir);
			_addNpmrc(mavenProjectDir);
			_configureExecuteNpmTask(gradleProjectDir);
			_configurePomNpmConfiguration(mavenProjectDir);
		}

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	private void _testBuildTemplateNpmProject72(String template)
		throws Exception {

		try {
			_buildTemplateWithGradle(
				template, "Foo", "--liferayVersion", "7.2");
		}
		catch (IllegalArgumentException iae) {
			String exception = iae.getMessage();

			Assert.assertTrue(
				exception.contains("See LPS-97950 for full details"));
		}

		_buildTemplateWithMaven(
			template, "foo", "foo", "-DclassName=foo", "-Dpackage=foo",
			"-DliferayVersion=7.2");
	}

	private File _testBuildTemplatePortlet70(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "foo", "--liferay-version", "7.0");

		for (String resourceFileName : resourceFileNames) {
			ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "bnd.bnd", "Export-Package: foo.constants");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/java/foo/constants/FooPortletKeys.java",
			"public class FooPortletKeys", "public static final String FOO",
			"\"foo_FooPortlet\";");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/java/foo/portlet/FooPortlet.java",
			"javax.portlet.display-name=Foo",
			"javax.portlet.name=\" + FooPortletKeys.FOO",
			"public class FooPortlet extends " + portletClassName + " {");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/resources/content/Language.properties",
			"javax.portlet.title.foo_FooPortlet=Foo",
			"foo.caption=Hello from Foo!");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "foo", "com.test", "-DclassName=Foo", "-Dpackage=foo",
			"-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/foo-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortlet71(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "foo", "--liferay-version", "7.1");

		for (String resourceFileName : resourceFileNames) {
			ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "bnd.bnd", "Export-Package: foo.constants");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/java/foo/constants/FooPortletKeys.java",
			"public class FooPortletKeys", "public static final String FOO");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/java/foo/portlet/FooPortlet.java",
			"javax.portlet.display-name=Foo",
			"javax.portlet.name=\" + FooPortletKeys.FOO",
			"public class FooPortlet extends " + portletClassName + " {");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/resources/content/Language.properties",
			"javax.portlet.title.foo_FooPortlet=Foo",
			"foo.caption=Hello from Foo!");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "foo", "com.test", "-DclassName=Foo", "-Dpackage=foo",
			"-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/foo-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortlet72(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "foo", "--liferay-version", "7.2");

		for (String resourceFileName : resourceFileNames) {
			ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "bnd.bnd", "Export-Package: foo.constants");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/java/foo/constants/FooPortletKeys.java",
			"public class FooPortletKeys", "public static final String FOO");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/java/foo/portlet/FooPortlet.java",
			"javax.portlet.display-name=Foo",
			"javax.portlet.name=\" + FooPortletKeys.FOO",
			"public class FooPortlet extends " + portletClassName + " {");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "src/main/resources/content/Language.properties",
			"javax.portlet.title.foo_FooPortlet=Foo",
			"foo.caption=Hello from Foo!");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "foo", "com.test", "-DclassName=Foo", "-Dpackage=foo",
			"-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/foo-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPackage70(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception, IOException {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "foo", "--package-name", "com.liferay.test");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		for (String resourceFileName : resourceFileNames) {
			ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/portlet/FooPortlet.java",
			"javax.portlet.name=\" + FooPortletKeys.FOO",
			"public class FooPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "foo", "com.test", "-DclassName=Foo",
			"-Dpackage=com.liferay.test");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/com.liferay.test-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPackage71(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception, IOException {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "foo", "--package-name", "com.liferay.test",
			"--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		for (String resourceFileName : resourceFileNames) {
			ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/portlet/FooPortlet.java",
			"javax.portlet.name=\" + FooPortletKeys.FOO",
			"public class FooPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "foo", "com.test", "-DclassName=Foo",
			"-Dpackage=com.liferay.test", "-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/com.liferay.test-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPackage72(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception, IOException {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "foo", "--package-name", "com.liferay.test",
			"--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		for (String resourceFileName : resourceFileNames) {
			ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/portlet/FooPortlet.java",
			"javax.portlet.name=\" + FooPortletKeys.FOO",
			"public class FooPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "foo", "com.test", "-DclassName=Foo",
			"-Dpackage=com.liferay.test", "-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/com.liferay.test-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPortletName70(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "portlet", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		for (String resourceFileName : resourceFileNames) {
			ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/portlet/constants/PortletPortletKeys.java",
			"public class PortletPortletKeys",
			"public static final String PORTLET",
			"\"portlet_PortletPortlet\";");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/PortletPortlet.java",
			"javax.portlet.name=\" + PortletPortletKeys.PORTLET",
			"public class PortletPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "portlet", "com.test", "-DclassName=Portlet",
			"-Dpackage=portlet", "-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/portlet-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPortletName71(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "portlet", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		for (String resourceFileName : resourceFileNames) {
			ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/portlet/constants/PortletPortletKeys.java",
			"public class PortletPortletKeys",
			"public static final String PORTLET",
			"\"portlet_PortletPortlet\";");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/PortletPortlet.java",
			"javax.portlet.name=\" + PortletPortletKeys.PORTLET",
			"public class PortletPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "portlet", "com.test", "-DclassName=Portlet",
			"-Dpackage=portlet", "-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/portlet-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPortletName72(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "portlet", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		for (String resourceFileName : resourceFileNames) {
			ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/portlet/constants/PortletPortletKeys.java",
			"public class PortletPortletKeys",
			"public static final String PORTLET",
			"\"portlet_PortletPortlet\";");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/PortletPortlet.java",
			"javax.portlet.name=\" + PortletPortletKeys.PORTLET",
			"public class PortletPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "portlet", "com.test", "-DclassName=Portlet",
			"-Dpackage=portlet", "-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/portlet-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPortletSuffix70(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "portlet-portlet", "--liferay-version", "7.0");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		for (String resourceFileName : resourceFileNames) {
			ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/constants/PortletPortletKeys.java",
			"public class PortletPortletKeys",
			"public static final String PORTLET",
			"\"portlet_portlet_PortletPortlet\";");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/portlet/PortletPortlet.java",
			"javax.portlet.name=\" + PortletPortletKeys.PORTLET",
			"public class PortletPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "portlet-portlet", "com.test", "-DclassName=Portlet",
			"-Dpackage=portlet.portlet", "-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/portlet.portlet-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPortletSuffix71(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "portlet-portlet", "--liferay-version", "7.1");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		for (String resourceFileName : resourceFileNames) {
			ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/constants/PortletPortletKeys.java",
			"public class PortletPortletKeys",
			"public static final String PORTLET",
			"\"portlet_portlet_PortletPortlet\";");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/portlet/PortletPortlet.java",
			"javax.portlet.name=\" + PortletPortletKeys.PORTLET",
			"public class PortletPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "portlet-portlet", "com.test", "-DclassName=Portlet",
			"-Dpackage=portlet.portlet", "-DliferayVersion=7.1");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/portlet.portlet-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPortletSuffix72(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "portlet-portlet", "--liferay-version", "7.2");

		ProjectTemplatesTestUtil.testExists(gradleProjectDir, "bnd.bnd");
		ProjectTemplatesTestUtil.testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/main.scss");

		for (String resourceFileName : resourceFileNames) {
			ProjectTemplatesTestUtil.testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"4.4.0");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/constants/PortletPortletKeys.java",
			"public class PortletPortletKeys",
			"public static final String PORTLET",
			"\"portlet_portlet_PortletPortlet\";");
		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/portlet/PortletPortlet.java",
			"javax.portlet.name=\" + PortletPortletKeys.PORTLET",
			"public class PortletPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "portlet-portlet", "com.test", "-DclassName=Portlet",
			"-Dpackage=portlet.portlet", "-DliferayVersion=7.2");

		ProjectTemplatesTestUtil.testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (_isBuildProjects()) {
			File gradleOutputFile = new File(
				gradleProjectDir, "build/libs/portlet.portlet-1.0.0.jar");

			_testCssOutput(gradleOutputFile);
		}

		return gradleProjectDir;
	}

	private void _testBuildTemplateProjectWarInWorkspace(
			String template, String name)
		throws Exception {

		File workspaceDir = _buildWorkspace();

		_enableTargetPlatformInWorkspace(workspaceDir);

		File warsDir = new File(workspaceDir, "wars");

		File workspaceProjectDir =
			ProjectTemplatesTestUtil.buildTemplateWithGradle(
				warsDir, template, name, "--dependency-management-enabled");

		if (!template.equals("war-hook")) {
			ProjectTemplatesTestUtil.testContains(
				workspaceProjectDir, "build.gradle", "buildscript {",
				"cssBuilder group", "portalCommonCSS group");
		}

		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "build.gradle", "apply plugin: \"war\"");
		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");
		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "build.gradle", "version: \"[0-9].*");

		if (_isBuildProjects()) {
			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir, ":wars:" + name + ":build");

			ProjectTemplatesTestUtil.testExists(
				workspaceProjectDir, "build/libs/" + name + ".war");
		}
	}

	private void _testBuildTemplateServiceBuilder(
			File gradleProjectDir, File mavenProjectDir, final File rootProject,
			String name, String packageName, final String projectPath)
		throws Exception {

		String apiProjectName = name + "-api";
		final String serviceProjectName = name + "-service";

		boolean workspace = WorkspaceUtil.isWorkspace(gradleProjectDir);

		if (!workspace) {
			ProjectTemplatesTestUtil.testContains(
				gradleProjectDir, "settings.gradle",
				"include \"" + apiProjectName + "\", \"" + serviceProjectName +
					"\"");
		}

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, apiProjectName + "/bnd.bnd", "Export-Package:\\",
			packageName + ".exception,\\", packageName + ".model,\\",
			packageName + ".service,\\", packageName + ".service.persistence");

		ProjectTemplatesTestUtil.testContains(
			gradleProjectDir, serviceProjectName + "/bnd.bnd",
			"Liferay-Service: true");

		if (!workspace) {
			ProjectTemplatesTestUtil.testContains(
				gradleProjectDir, serviceProjectName + "/build.gradle",
				"compileOnly project(\":" + apiProjectName + "\")");
		}

		if (_isBuildProjects()) {
			_testChangePortletModelHintsXml(
				gradleProjectDir, serviceProjectName,
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						ProjectTemplatesTestUtil.executeGradle(
							rootProject,
							projectPath + ":" + serviceProjectName +
								_GRADLE_TASK_PATH_BUILD_SERVICE);

						return null;
					}

				});

			ProjectTemplatesTestUtil.executeGradle(
				rootProject,
				projectPath + ":" + serviceProjectName +
					ProjectTemplatesTestConstants.GRADLE_TASK_PATH_BUILD);

			File gradleApiBundleFile = ProjectTemplatesTestUtil.testExists(
				gradleProjectDir,
				apiProjectName + "/build/libs/" + packageName +
					".api-1.0.0.jar");

			File gradleServiceBundleFile = ProjectTemplatesTestUtil.testExists(
				gradleProjectDir,
				serviceProjectName + "/build/libs/" + packageName +
					".service-1.0.0.jar");

			_testChangePortletModelHintsXml(
				mavenProjectDir, serviceProjectName,
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						_executeMaven(
							new File(mavenProjectDir, serviceProjectName),
							_MAVEN_GOAL_BUILD_SERVICE);

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

			_executeMaven(
				mavenProjectDir,
				ProjectTemplatesTestConstants.MAVEN_GOAL_PACKAGE);

			File mavenApiBundleFile = ProjectTemplatesTestUtil.testExists(
				mavenProjectDir,
				apiProjectName + "/target/" + name + "-api-1.0.0.jar");
			File mavenServiceBundleFile = ProjectTemplatesTestUtil.testExists(
				mavenProjectDir,
				serviceProjectName + "/target/" + name + "-service-1.0.0.jar");

			ProjectTemplatesTestUtil.testBundlesDiff(
				gradleApiBundleFile, mavenApiBundleFile);
			ProjectTemplatesTestUtil.testBundlesDiff(
				gradleServiceBundleFile, mavenServiceBundleFile);
		}
	}

	private File _testBuildTemplateWithWorkspace(
			String template, String name, String jarFilePath, String... args)
		throws Exception {

		File workspaceDir = _buildWorkspace();

		_enableTargetPlatformInWorkspace(workspaceDir);

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir =
			ProjectTemplatesTestUtil.buildTemplateWithGradle(
				modulesDir, template, name, args);

		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");
		ProjectTemplatesTestUtil.testNotContains(
			workspaceProjectDir, "build.gradle", "version: \"[0-9].*");

		if (_isBuildProjects()) {
			ProjectTemplatesTestUtil.executeGradle(
				workspaceDir, ":modules:" + name + ":build");

			ProjectTemplatesTestUtil.testExists(
				workspaceProjectDir, jarFilePath);
		}

		return workspaceProjectDir;
	}

	private void _testCssOutput(File outputFile) throws IOException {
		ZipFile zipFile = null;

		try {
			zipFile = new ZipFile(outputFile);

			ProjectTemplatesTestUtil.testExists(
				zipFile, "META-INF/resources/css/main.css");
			ProjectTemplatesTestUtil.testExists(
				zipFile, "META-INF/resources/css/main_rtl.css");
		}
		finally {
			ZipFile.closeQuietly(zipFile);
		}
	}

	private static final String _DEPENDENCY_OSGI_CORE =
		"compileOnly group: \"org.osgi\", name: \"org.osgi.core\"";

	private static final String _DEPENDENCY_PORTAL_KERNEL =
		"compileOnly group: \"com.liferay.portal\", name: " +
			"\"com.liferay.portal.kernel\"";

	private static final String _FREEMARKER_PORTLET_VIEW_FTL_PREFIX =
		"<#include \"init.ftl\">";

	private static final String _GRADLE_TASK_PATH_BUILD_SERVICE =
		":buildService";

	private static final String _GRADLE_TASK_PATH_DEPLOY = ":deploy";

	private static final String _MAVEN_GOAL_BUILD_SERVICE =
		"service-builder:build";

	private static final String _NODEJS_NPM_CI_REGISTRY = System.getProperty(
		"nodejs.npm.ci.registry");

	private static final String _NODEJS_NPM_CI_SASS_BINARY_SITE =
		System.getProperty("nodejs.npm.ci.sass.binary.site");

	private static final Pattern _gradlePluginVersionPattern = Pattern.compile(
		".*com\\.liferay\\.gradle\\.plugins:([0-9]+\\.[0-9]+\\.[0-9]+).*",
		Pattern.DOTALL | Pattern.MULTILINE);
	private static final Pattern _serviceBuilderVersionPattern =
		Pattern.compile(
			".*service\\.builder:([0-9]+\\.[0-9]+\\.[0-9]+).*",
			Pattern.DOTALL | Pattern.MULTILINE);

}