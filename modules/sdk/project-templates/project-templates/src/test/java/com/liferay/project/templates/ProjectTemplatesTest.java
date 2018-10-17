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

import com.liferay.maven.executor.MavenExecutor;
import com.liferay.project.templates.internal.ProjectGenerator;
import com.liferay.project.templates.internal.util.FileUtil;
import com.liferay.project.templates.internal.util.ProjectTemplatesUtil;
import com.liferay.project.templates.internal.util.Validator;
import com.liferay.project.templates.util.DirectoryComparator;
import com.liferay.project.templates.util.FileTestUtil;
import com.liferay.project.templates.util.StringTestUtil;
import com.liferay.project.templates.util.XMLTestUtil;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;

import java.net.URI;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
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
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import net.diibadaaba.zipdiff.DifferenceCalculator;
import net.diibadaaba.zipdiff.Differences;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;

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

		_gradleDistribution = URI.create(gradleDistribution);

		XPathFactory xPathFactory = XPathFactory.newInstance();

		XPath xPath = xPathFactory.newXPath();

		_pomXmlNpmInstallXPathExpression = xPath.compile(
			"//id[contains(text(),'npm-install')]/parent::*");
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
			"mvc-portlet", "hello-world-portlet", "com.test",
			"-DclassName=HelloWorld", "-Dpackage=hello.world.portlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateActivator() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"activator", "bar-activator");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_OSGI_CORE + ", version: \"6.0.0\"");
		_testContains(
			gradleProjectDir, "src/main/java/bar/activator/BarActivator.java",
			"public class BarActivator implements BundleActivator {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"activator", "bar-activator", "com.test",
			"-DclassName=BarActivator", "-Dpackage=bar.activator");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateActivatorInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"activator", "bar-activator", "build/libs/bar.activator-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateActivatorWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"activator", "activator-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_OSGI_CORE + "\n");
	}

	@Test
	public void testBuildTemplateApi() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("api", "foo");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_OSGI_CORE + ", version: \"6.0.0\"");
		_testContains(
			gradleProjectDir, "src/main/java/foo/api/Foo.java",
			"public interface Foo");
		_testContains(
			gradleProjectDir, "src/main/resources/foo/api/packageinfo",
			"1.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"api", "foo", "com.test", "-DclassName=Foo", "-Dpackage=foo");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateApiContainsCorrectAuthor() throws Exception {
		String author = "Test Author";

		File gradleProjectDir = _buildTemplateWithGradle(
			"api", "author-test", "--author", author);

		_testContains(
			gradleProjectDir, "src/main/java/author/test/api/AuthorTest.java",
			"@author " + author);

		File mavenProjectDir = _buildTemplateWithMaven(
			"api", "author-test", "com.test", "-Dauthor=" + author,
			"-DclassName=AuthorTest", "-Dpackage=author.test");

		_testContains(
			mavenProjectDir, "src/main/java/author/test/api/AuthorTest.java",
			"@author " + author);
	}

	@Test
	public void testBuildTemplateApiInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"api", "foo", "build/libs/foo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateApiWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"api", "api-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_OSGI_CORE + "\n");
	}

	@Test
	public void testBuildTemplateContentTargetingReport() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-report", "foo-bar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/content/targeting/report/FooBarReport.java",
			"public class FooBarReport extends BaseJSPReport");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-report", "foo-bar", "com.test",
			"-DclassName=FooBar", "-Dpackage=foo.bar");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateContentTargetingReport70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-report", "foo-bar", "--liferayVersion", "7.0");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
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
			"content-targeting-report", "foo-bar", "--liferayVersion", "7.1");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-report", "foo-bar", "com.test",
			"-DclassName=FooBar", "-Dpackage=foo.bar", "-DliferayVersion=7.1");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateContentTargetingReportInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"content-targeting-report", "foo-bar",
			"build/libs/foo.bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContentTargetingReportWithBOM()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-report", "report-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateContentTargetingRule() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-rule", "foo-bar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/content/targeting/rule/FooBarRule.java",
			"public class FooBarRule extends BaseJSPRule");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-rule", "foo-bar", "com.test",
			"-DclassName=FooBar", "-Dpackage=foo.bar");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateContentTargetingRule70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-rule", "foo-bar", "--liferayVersion", "7.0");

		_testContains(
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
			"content-targeting-rule", "foo-bar", "--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-rule", "foo-bar", "com.test",
			"-DclassName=FooBar", "-Dpackage=foo.bar", "-DliferayVersion=7.1");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateContentTargetingRuleInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"content-targeting-rule", "foo-bar",
			"build/libs/foo.bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContentTargetingRuleWithBOM()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-rule", "rule-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateContentTargetingTrackingAction()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-tracking-action", "foo-bar");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/content/targeting/tracking/action" +
				"/FooBarTrackingAction.java",
			"public class FooBarTrackingAction extends BaseJSPTrackingAction");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-tracking-action", "foo-bar", "com.test",
			"-DclassName=FooBar", "-Dpackage=foo.bar");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateContentTargetingTrackingAction70()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-tracking-action", "foo-bar", "--liferayVersion",
			"7.0");

		_testContains(
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
			"content-targeting-tracking-action", "foo-bar", "--liferayVersion",
			"7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"content-targeting-tracking-action", "foo-bar", "com.test",
			"-DclassName=FooBar", "-Dpackage=foo.bar", "-DliferayVersion=7.1");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateContentTargetingTrackingActionInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"content-targeting-tracking-action", "foo-bar",
			"build/libs/foo.bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateContentTargetingTrackingActionWithBOM()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"content-targeting-tracking-action",
			"tracking-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateControlMenuEntry70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"control-menu-entry", "foo-bar", "--liferayVersion", "7.0");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0\"");
		_testContains(
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
			"control-menu-entry", "foo-bar", "--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0\"");

		File mavenProjectDir = _buildTemplateWithMaven(
			"control-menu-entry", "foo-bar", "com.test", "-DclassName=FooBar",
			"-Dpackage=foo.bar", "-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateControlMenuEntryInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"control-menu-entry", "foo-bar", "build/libs/foo.bar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateControlMenuEntryWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"control-menu-entry", "entry-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateExt() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"modules-ext", "loginExt", "--original-module-name",
			"com.liferay.login.web", "--original-module-version", "1.0.0");

		_testContains(
			gradleProjectDir, "build.gradle", "buildscript {", "repositories {",
			"originalModule group: \"com.liferay\", name: " +
				"\"com.liferay.login.web\", version: \"1.0.0\"",
			"apply plugin: \"com.liferay.osgi.ext.plugin\"");

		_executeGradle(gradleProjectDir, _GRADLE_TASK_PATH_BUILD);

		_testExists(
			gradleProjectDir, "build/libs/com.liferay.login.web-1.0.0.ext.jar");
	}

	@Test
	public void testBuildTemplateExtInWorkspace() throws Exception {
		File workspaceDir = _buildWorkspace();

		File workspaceProjectDir = _buildTemplateWithGradle(
			new File(workspaceDir, "ext"), "modules-ext", "loginExt",
			"--original-module-name", "com.liferay.login.web",
			"--original-module-version", "1.0.0");

		_testContains(
			workspaceProjectDir, "build.gradle",
			"originalModule group: \"com.liferay\", name: " +
				"\"com.liferay.login.web\", version: \"1.0.0\"");

		_testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");

		_executeGradle(workspaceDir, ":ext:loginExt:build");

		_testExists(
			workspaceProjectDir,
			"build/libs/com.liferay.login.web-1.0.0.ext.jar");
	}

	@Test
	public void testBuildTemplateFMPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"freemarker-portlet", "freemarker-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateFormField70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"form-field", "foobar", "--liferayVersion", "7.0");

		_testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-Name: foobar",
			"Web-ContextPath: /dynamic-data-foobar-form-field");
		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		_testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldRenderer.java",
			"property = \"ddm.form.field.type.name=foobar\"",
			"public class FoobarDDMFormFieldRenderer extends " +
				"BaseDDMFormFieldRenderer {",
			"ddm.Foobar", "/META-INF/resources/foobar.soy");
		_testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldType.java",
			"ddm.form.field.type.js.class.name=Liferay.DDM.Field.Foobar",
			"ddm.form.field.type.js.module=foobar-form-field",
			"ddm.form.field.type.label=foobar-label",
			"ddm.form.field.type.name=foobar",
			"public class FoobarDDMFormFieldType extends BaseDDMFormFieldType",
			"return \"foobar\";");
		_testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/config.js",
			"foobar-group", "'foobar-form-field': {",
			"path: 'foobar_field.js',", "'foobar-form-field-template': {");
		_testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.soy",
			"{namespace ddm}", "{template .Foobar autoescape",
			"<div class=\"form-group foobar-form-field\"");
		_testContains(
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

	@Test
	public void testBuildTemplateFormField71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"form-field", "foobar", "--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-Name: foobar",
			"Web-ContextPath: /dynamic-data-foobar-form-field");
		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		_testContains(
			gradleProjectDir, "package.json",
			"\"name\": \"dynamic-data-foobar-form-field\"",
			",foobar_field.js &&");
		_testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldRenderer.java",
			"property = \"ddm.form.field.type.name=foobar\"",
			"public class FoobarDDMFormFieldRenderer extends " +
				"BaseDDMFormFieldRenderer {",
			"DDMFoobar.render", "/META-INF/resources/foobar.soy");
		_testContains(
			gradleProjectDir,
			"src/main/java/foobar/form/field/FoobarDDMFormFieldType.java",
			"ddm.form.field.type.description=foobar-description",
			"ddm.form.field.type.js.class.name=Liferay.DDM.Field.Foobar",
			"ddm.form.field.type.js.module=foobar-form-field",
			"ddm.form.field.type.label=foobar-label",
			"ddm.form.field.type.name=foobar",
			"public class FoobarDDMFormFieldType extends BaseDDMFormFieldType",
			"return \"foobar\";");
		_testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/config.js",
			"field-foobar", "'foobar-form-field': {",
			"path: 'foobar_field.js',");
		_testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.soy",
			"{namespace DDMFoobar}", "variant=\"'foobar'\"",
			"foobar-form-field");
		_testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar.es.js",
			"import templates from './foobar.soy';", "* Foobar Component",
			"class Foobar extends Component", "Soy.register(Foobar,",
			"!window.DDMFoobar", "window.DDMFoobar",
			"window.DDMFoobar.render = Foobar;", "export default Foobar;");
		_testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foobar_field.js",
			"'foobar-form-field',", "var FoobarField",
			"value: 'foobar-form-field'", "NAME: 'foobar-form-field'",
			"Liferay.namespace('DDM.Field').Foobar = FoobarField;");

		File mavenProjectDir = _buildTemplateWithMaven(
			"form-field", "foobar", "com.test", "-DclassName=Foobar",
			"-Dpackage=foobar", "-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateFormField71WithHyphen() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"form-field", "foo-bar", "--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "bnd.bnd", "Bundle-Name: foo-bar",
			"Web-ContextPath: /dynamic-data-foo-bar-form-field");
		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		_testContains(
			gradleProjectDir, "package.json",
			"\"name\": \"dynamic-data-foo-bar-form-field\"",
			",foo-bar_field.js &&");
		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/form/field/FooBarDDMFormFieldRenderer.java",
			"property = \"ddm.form.field.type.name=fooBar\"",
			"public class FooBarDDMFormFieldRenderer extends " +
				"BaseDDMFormFieldRenderer {",
			"DDMFooBar.render", "/META-INF/resources/foo-bar.soy");
		_testContains(
			gradleProjectDir,
			"src/main/java/foo/bar/form/field/FooBarDDMFormFieldType.java",
			"ddm.form.field.type.description=foo-bar-description",
			"ddm.form.field.type.js.class.name=Liferay.DDM.Field.FooBar",
			"ddm.form.field.type.js.module=foo-bar-form-field",
			"ddm.form.field.type.label=foo-bar-label",
			"ddm.form.field.type.name=fooBar",
			"public class FooBarDDMFormFieldType extends BaseDDMFormFieldType",
			"return \"fooBar\";");
		_testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/config.js",
			"field-foo-bar", "'foo-bar-form-field': {",
			"path: 'foo-bar_field.js',");
		_testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foo-bar.soy",
			"{namespace DDMFooBar}", "variant=\"'fooBar'\"",
			"foo-bar-form-field");
		_testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foo-bar.es.js",
			"import templates from './foo-bar.soy';", "* FooBar Component",
			"class FooBar extends Component", "Soy.register(FooBar,",
			"!window.DDMFooBar", "window.DDMFooBar",
			"window.DDMFooBar.render = FooBar;", "export default FooBar;");
		_testContains(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/foo-bar_field.js",
			"'foo-bar-form-field',", "var FooBarField",
			"value: 'foo-bar-form-field'", "NAME: 'foo-bar-form-field'",
			"Liferay.namespace('DDM.Field').FooBar = FooBarField;");

		File mavenProjectDir = _buildTemplateWithMaven(
			"form-field", "foo-bar", "com.test", "-DclassName=FooBar",
			"-Dpackage=foo.bar", "-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateFormFieldInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"form-field", "foobar", "build/libs/foobar-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateFormFieldWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"form-field", "field-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
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
			"fragment", "loginhook", "com.test",
			"-DhostBundleSymbolicName=com.liferay.login.web",
			"-DhostBundleVersion=1.0.0", "-Dpackage=loginhook");

		_buildProjects(gradleProjectDir, mavenProjectDir);
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
	public void testBuildTemplateFreeMarkerPortletInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"freemarker-portlet", "foo", "build/libs/foo-1.0.0.jar");
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
	public void testBuildTemplateInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			null, "hello-world-portlet",
			"build/libs/hello.world.portlet-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateLayoutTemplate() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"layout-template", "foo");

		_testExists(gradleProjectDir, "src/main/webapp/foo.png");

		_testContains(
			gradleProjectDir, "src/main/webapp/foo.ftl", "class=\"foo\"");
		_testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-layout-templates.xml",
			"<layout-template id=\"foo\" name=\"foo\">",
			"<template-path>/foo.ftl</template-path>",
			"<thumbnail-path>/foo.png</thumbnail-path>");
		_testContains(
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
			"mvc-portlet", "test", "--liferayVersion", "6.2");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateLiferayVersionInvalid70test()
		throws Exception {

		_buildTemplateWithGradle(
			"mvc-portlet", "test", "--liferayVersion", "7.0test");
	}

	@Test
	public void testBuildTemplateLiferayVersionValid70() throws Exception {
		_buildTemplateWithGradle(
			"mvc-portlet", "test", "--liferayVersion", "7.0");
	}

	@Test
	public void testBuildTemplateLiferayVersionValid712() throws Exception {
		_buildTemplateWithGradle(
			"mvc-portlet", "test", "--liferayVersion", "7.1.2");
	}

	@Test
	public void testBuildTemplateModulesExtGradle() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"modules-ext", "foo-ext", "--original-module-name",
			"com.liferay.login.web", "--original-module-version", "2.0.4");

		_testContains(
			gradleProjectDir, "build.gradle",
			"originalModule group: \"com.liferay\", ",
			"name: \"com.liferay.login.web\", version: \"2.0.4\"");

		if (Validator.isNotNull(_BUILD_PROJECTS) &&
			_BUILD_PROJECTS.equals("true")) {

			_executeGradle(gradleProjectDir, _GRADLE_TASK_PATH_BUILD);

			File gradleOutputDir = new File(gradleProjectDir, "build/libs");

			Path gradleOutputPath = FileTestUtil.getFile(
				gradleOutputDir.toPath(), _OUTPUT_FILENAME_GLOB_REGEX, 1);

			Assert.assertNotNull(gradleOutputPath);

			Assert.assertTrue(Files.exists(gradleOutputPath));
		}
	}

	@Test(expected = Error.class)
	public void testBuildTemplateModulesExtMaven() throws Exception {
		_buildTemplateWithMaven(
			"modules-ext", "foo-ext", "com.test",
			"-DoriginalModuleName=com.liferay.login.web",
			"-DoriginalModuleVersion=2.0.4");
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
	public void testBuildTemplateMVCPortletInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"mvc-portlet", "foo", "build/libs/foo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"mvc-portlet", "mvc-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
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
	public void testBuildTemplateNAPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"npm-angular-portlet", "angular-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateNBPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"npm-billboardjs-portlet", "billboardjs-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateNpmAngularPortlet70() throws Exception {
		_testBuildTemplateNpm70(
			"npm-angular-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default('#<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmAngularPortlet71() throws Exception {
		_testBuildTemplateNpm71(
			"npm-angular-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default('#<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmAngularPortletWithDashes70()
		throws Exception {

		_testBuildTemplateNpm70(
			"npm-angular-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default('#<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmAngularPortletWithDashes71()
		throws Exception {

		_testBuildTemplateNpm71(
			"npm-angular-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default('#<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmBillboardjsPortlet70() throws Exception {
		_testBuildTemplateNpm70(
			"npm-billboardjs-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmBillboardjsPortlet71() throws Exception {
		_testBuildTemplateNpm71(
			"npm-billboardjs-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmBillboardjsPortletWithDashes70()
		throws Exception {

		_testBuildTemplateNpm70(
			"npm-billboardjs-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmBillboardjsPortletWithDashes71()
		throws Exception {

		_testBuildTemplateNpm71(
			"npm-billboardjs-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmIsomorphicPortlet70() throws Exception {
		_testBuildTemplateNpm70(
			"npm-isomorphic-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default(");
	}

	@Test
	public void testBuildTemplateNpmIsomorphicPortlet71() throws Exception {
		_testBuildTemplateNpm71(
			"npm-isomorphic-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default(");
	}

	@Test
	public void testBuildTemplateNpmIsomorphicPortletWithBOM()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"npm-isomorphic-portlet", "isomorphic-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateNpmIsomorphicPortletWithDashes70()
		throws Exception {

		_testBuildTemplateNpm70(
			"npm-isomorphic-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default(");
	}

	@Test
	public void testBuildTemplateNpmIsomorphicPortletWithDashes71()
		throws Exception {

		_testBuildTemplateNpm71(
			"npm-isomorphic-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default(");
	}

	@Test
	public void testBuildTemplateNpmJQueryPortlet70() throws Exception {
		_testBuildTemplateNpm70(
			"npm-jquery-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmJQueryPortlet71() throws Exception {
		_testBuildTemplateNpm71(
			"npm-jquery-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmJQueryPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"npm-jquery-portlet", "jquery-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateNpmJQueryPortletWithDashes70()
		throws Exception {

		_testBuildTemplateNpm70(
			"npm-jquery-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmJQueryPortletWithDashes71()
		throws Exception {

		_testBuildTemplateNpm71(
			"npm-jquery-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmMetaljsPortlet70() throws Exception {
		_testBuildTemplateNpm70(
			"npm-metaljs-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmMetaljsPortlet71() throws Exception {
		_testBuildTemplateNpm71(
			"npm-metaljs-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmMetaljsPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"npm-metaljs-portlet", "metaljs-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateNpmMetaljsPortletWithDashes70()
		throws Exception {

		_testBuildTemplateNpm70(
			"npm-metaljs-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmMetaljsPortletWithDashes71()
		throws Exception {

		_testBuildTemplateNpm71(
			"npm-metaljs-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmPortlet70() throws Exception {
		_testBuildTemplateNpm70(
			"npm-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmPortlet71() throws Exception {
		_testBuildTemplateNpm71(
			"npm-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"npm-portlet", "npm-portlet-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateNpmPortletWithDashes70() throws Exception {
		_testBuildTemplateNpm70(
			"npm-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmPortletWithDashes71() throws Exception {
		_testBuildTemplateNpm71(
			"npm-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmReactPortlet70() throws Exception {
		_testBuildTemplateNpm70(
			"npm-react-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmReactPortlet71() throws Exception {
		_testBuildTemplateNpm71(
			"npm-react-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmReactPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"npm-react-portlet", "react-portlet-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateNpmReactPortletWithDashes70()
		throws Exception {

		_testBuildTemplateNpm70(
			"npm-react-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmReactPortletWithDashes71()
		throws Exception {

		_testBuildTemplateNpm71(
			"npm-react-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmVuejsPortlet70() throws Exception {
		_testBuildTemplateNpm70(
			"npm-vuejs-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmVuejsPortlet71() throws Exception {
		_testBuildTemplateNpm71(
			"npm-vuejs-portlet", "foo", "foo", "Foo",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmVuejsPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"npm-vuejs-portlet", "vuejs-portlet-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateNpmVuejsPortletWithDashes70()
		throws Exception {

		_testBuildTemplateNpm70(
			"npm-vuejs-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test
	public void testBuildTemplateNpmVuejsPortletWithDashes71()
		throws Exception {

		_testBuildTemplateNpm71(
			"npm-vuejs-portlet", "foo-bar", "foo.bar", "FooBar",
			"bootstrapRequire.default('<portlet:namespace />');");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateOnExistingDirectory() throws Exception {
		File destinationDir = temporaryFolder.newFolder("gradle");

		_buildTemplateWithGradle(destinationDir, "activator", "dup-activator");
		_buildTemplateWithGradle(destinationDir, "activator", "dup-activator");
	}

	@Test
	public void testBuildTemplatePanelApp70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"panel-app", "gradle.test", "--class-name", "Foo",
			"--liferayVersion", "7.0");

		_testContains(
			gradleProjectDir, "bnd.bnd",
			"Export-Package: gradle.test.constants");
		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		_testContains(
			gradleProjectDir,
			"src/main/java/gradle/test/application/list/FooPanelApp.java",
			"public class FooPanelApp extends BasePanelApp");
		_testContains(
			gradleProjectDir,
			"src/main/java/gradle/test/constants/FooPortletKeys.java",
			"public class FooPortletKeys");
		_testContains(
			gradleProjectDir,
			"src/main/java/gradle/test/portlet/FooPortlet.java",
			"javax.portlet.name=\" + FooPortletKeys.Foo",
			"public class FooPortlet extends MVCPortlet");

		File mavenProjectDir = _buildTemplateWithMaven(
			"panel-app", "gradle.test", "com.test", "-DclassName=Foo",
			"-Dpackage=gradle.test", "-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePanelApp71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"panel-app", "gradle.test", "--class-name", "Foo",
			"--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"panel-app", "gradle.test", "com.test", "-DclassName=Foo",
			"-Dpackage=gradle.test", "-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePanelAppInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"panel-app", "gradle.test", "build/libs/gradle.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePanelAppWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"panel-app", "panel-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplatePorletProviderInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"portlet-provider", "provider.test",
			"build/libs/provider.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortlet70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet", "foo.test", "--class-name", "Foo", "--liferayVersion",
			"7.0");

		_testContains(
			gradleProjectDir, "bnd.bnd", "Export-Package: foo.test.constants");
		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		_testContains(
			gradleProjectDir,
			"src/main/java/foo/test/constants/FooPortletKeys.java",
			"public class FooPortletKeys");
		_testContains(
			gradleProjectDir, "src/main/java/foo/test/portlet/FooPortlet.java",
			"package foo.test.portlet;", "javax.portlet.display-name=Foo",
			"javax.portlet.name=\" + FooPortletKeys.Foo",
			"public class FooPortlet extends GenericPortlet {",
			"printWriter.print(\"Hello from Foo");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet", "foo.test", "com.test", "-DclassName=Foo",
			"-Dpackage=foo.test", "-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortlet71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet", "foo.test", "--class-name", "Foo", "--liferayVersion",
			"7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet", "foo.test", "com.test", "-DclassName=Foo",
			"-Dpackage=foo.test", "-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortletConfigurationIcon70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-configuration-icon", "icontest", "--package-name",
			"blade.test", "--liferayVersion", "7.0");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		_testContains(
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
			"blade.test", "--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-configuration-icon", "icontest", "com.test",
			"-DclassName=Icontest", "-Dpackage=blade.test",
			"-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortletConfigurationIconInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"portlet-configuration-icon", "blade.test",
			"build/libs/blade.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletConfigurationIconWithBOM()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-configuration-icon", "icon-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplatePortletInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"portlet", "foo.test", "build/libs/foo.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletProvider70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-provider", "provider.test", "--liferayVersion", "7.0");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		_testContains(
			gradleProjectDir,
			"src/main/java/provider/test/constants" +
				"/ProviderTestPortletKeys.java",
			"package provider.test.constants;",
			"public class ProviderTestPortletKeys",
			"public static final String ProviderTest = \"providertest\";");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-provider", "provider.test", "com.test",
			"-DclassName=ProviderTest", "-Dpackage=provider.test",
			"-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortletProvider71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-provider", "provider.test", "--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-provider", "provider.test", "com.test",
			"-DclassName=ProviderTest", "-Dpackage=provider.test",
			"-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortletProviderWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-provider", "provider-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplatePortletToolbarContributor70()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-toolbar-contributor", "toolbartest", "--package-name",
			"blade.test", "--liferayVersion", "7.0");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		_testContains(
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
			"blade.test", "--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet-toolbar-contributor", "toolbartest", "com.test",
			"-DclassName=Toolbartest", "-Dpackage=blade.test",
			"-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplatePortletToolbarContributorInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"portlet-toolbar-contributor", "blade.test",
			"build/libs/blade.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplatePortletToolbarContributorWithBOM()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet-toolbar-contributor", "contributor-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplatePortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"portlet", "portlet-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplatePortletWithPortletName() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("portlet", "portlet");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/PortletPortlet.java",
			"package portlet.portlet;", "javax.portlet.display-name=Portlet",
			"public class PortletPortlet extends GenericPortlet {",
			"printWriter.print(\"Hello from Portlet");

		File mavenProjectDir = _buildTemplateWithMaven(
			"portlet", "portlet", "com.test", "-DclassName=Portlet",
			"-Dpackage=portlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateRest() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("rest", "my-rest");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"compileOnly group: \"javax.ws.rs\", name: \"javax.ws.rs-api\", " +
				"version: \"2.0.1\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/my/rest/application/MyRestApplication.java",
			"public class MyRestApplication extends Application");
		_testContains(
			gradleProjectDir,
			"src/main/resources/configuration" +
				"/com.liferay.portal.remote.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration-cxf.properties",
			"contextPath=/my-rest");
		_testContains(
			gradleProjectDir,
			"src/main/resources/configuration/com.liferay.portal.remote.rest." +
				"extender.configuration.RestExtenderConfiguration-rest." +
					"properties",
			"contextPaths=/my-rest",
			"jaxRsApplicationFilterStrings=(component.name=" +
				"my.rest.application.MyRestApplication)");

		File mavenProjectDir = _buildTemplateWithMaven(
			"rest", "my-rest", "com.test", "-DclassName=MyRest",
			"-Dpackage=my.rest");

		_testContains(
			mavenProjectDir,
			"src/main/java/my/rest/application/MyRestApplication.java",
			"public class MyRestApplication extends Application");
		_testContains(
			mavenProjectDir,
			"src/main/resources/configuration" +
				"/com.liferay.portal.remote.cxf.common.configuration." +
					"CXFEndpointPublisherConfiguration-cxf.properties",
			"contextPath=/my-rest");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateRestInWorkspace() throws Exception {
		_testBuildTemplateWithWorkspace(
			"rest", "my-rest", "build/libs/my.rest-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateRestWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"rest", "rest-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle",
			"compileOnly group: \"javax.ws.rs\", name: \"javax.ws.rs-api\"");
	}

	@Test
	public void testBuildTemplateService70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction",
			"--liferayVersion", "7.0");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");

		_writeServiceClass(gradleProjectDir);

		File mavenProjectDir = _buildTemplateWithMaven(
			"service", "servicepreaction", "com.test", "-DclassName=FooAction",
			"-Dpackage=servicepreaction",
			"-DserviceClass=com.liferay.portal.kernel.events.LifecycleAction",
			"-DliferayVersion=7.0");

		_writeServiceClass(mavenProjectDir);

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateService71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction",
			"--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		_writeServiceClass(gradleProjectDir);

		File mavenProjectDir = _buildTemplateWithMaven(
			"service", "servicepreaction", "com.test", "-DclassName=FooAction",
			"-Dpackage=servicepreaction",
			"-DserviceClass=com.liferay.portal.kernel.events.LifecycleAction",
			"-DliferayVersion=7.1");

		_writeServiceClass(mavenProjectDir);

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateServiceBuilder70() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferayVersion", "7.0");

		_testContains(
			gradleProjectDir, name + "-api/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		_testContains(
			gradleProjectDir, name + "-service/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.6.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "com.test", "-Dpackage=" + packageName,
			"-DliferayVersion=7.0");

		_testBuildTemplateServiceBuilder(
			gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
			packageName, "");
	}

	@Test
	public void testBuildTemplateServiceBuilder71() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, name + "-api/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		_testContains(
			gradleProjectDir, name + "-service/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "com.test", "-Dpackage=" + packageName,
			"-DliferayVersion=7.1");

		_testBuildTemplateServiceBuilder(
			gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
			packageName, "");
	}

	@Test
	public void testBuildTemplateServiceBuilderCheckExports() throws Exception {
		String name = "guestbook";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferayVersion", "7.1");

		File gradleServiceXml = new File(
			new File(gradleProjectDir, name + "-service"), "service.xml");

		Consumer<Document> consumer = document -> {
			Element documentElement = document.getDocumentElement();

			documentElement.setAttribute("package-path", "com.liferay.test");
		};

		_editXml(gradleServiceXml, consumer);

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "com.test", "-Dpackage=" + packageName,
			"-DliferayVersion=7.1");

		File mavenServiceXml = new File(
			new File(mavenProjectDir, name + "-service"), "service.xml");

		_editXml(mavenServiceXml, consumer);

		_testContains(
			gradleProjectDir, name + "-api/bnd.bnd", "Export-Package:\\",
			packageName + ".exception,\\", packageName + ".model,\\",
			packageName + ".service,\\", packageName + ".service.persistence");

		Optional<String> stdOutput = _executeGradle(
			gradleProjectDir, false, true,
			name + "-service" + _GRADLE_TASK_PATH_BUILD);

		Assert.assertTrue(stdOutput.isPresent());

		String gradleOutput = stdOutput.get();

		Assert.assertTrue(
			"Expected gradle output to include build error. " + gradleOutput,
			gradleOutput.contains("Exporting an empty package"));

		String mavenOutput = _executeMaven(
			mavenProjectDir, true, _MAVEN_GOAL_PACKAGE);

		Assert.assertTrue(
			"Expected maven output to include build error. " + mavenOutput,
			mavenOutput.contains("Exporting an empty package"));
	}

	@Test
	public void testBuildTemplateServiceBuilderNestedPath70() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "ws-nested-path");

		File destinationDir = new File(
			workspaceProjectDir, "modules/nested/path");

		Assert.assertTrue(destinationDir.mkdirs());

		File gradleProjectDir = _buildTemplateWithGradle(
			destinationDir, "service-builder", "sample", "--package-name",
			"com.test.sample", "--liferayVersion", "7.0");

		_testContains(
			gradleProjectDir, "sample-service/build.gradle",
			"compileOnly project(\":modules:nested:path:sample:sample-api\")");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", "sample", "com.test",
			"-Dpackage=com.test.sample", "-DliferayVersion=7.0");

		_testBuildTemplateServiceBuilder(
			gradleProjectDir, mavenProjectDir, workspaceProjectDir, "sample",
			"com.test.sample", ":modules:nested:path:sample");
	}

	@Test
	public void testBuildTemplateServiceBuilderNestedPath71() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "ws-nested-path");

		File destinationDir = new File(
			workspaceProjectDir, "modules/nested/path");

		Assert.assertTrue(destinationDir.mkdirs());

		File gradleProjectDir = _buildTemplateWithGradle(
			destinationDir, "service-builder", "sample", "--package-name",
			"com.test.sample", "--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "sample-service/build.gradle",
			"compileOnly project(\":modules:nested:path:sample:sample-api\")");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", "sample", "com.test",
			"-Dpackage=com.test.sample", "-DliferayVersion=7.1");

		_testBuildTemplateServiceBuilder(
			gradleProjectDir, mavenProjectDir, workspaceProjectDir, "sample",
			"com.test.sample", ":modules:nested:path:sample");
	}

	@Test
	public void testBuildTemplateServiceBuilderTargetPlatformEnabled70()
		throws Exception {

		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "workspace");

		File gradleProperties = new File(
			workspaceProjectDir, "gradle.properties");

		Files.write(
			gradleProperties.toPath(),
			"\nliferay.workspace.target.platform.version=7.0.6".getBytes(),
			StandardOpenOption.APPEND);

		File modulesDir = new File(workspaceProjectDir, "modules");

		_buildTemplateWithGradle(
			modulesDir, "service-builder", "foo", "--package-name", "test",
			"--liferayVersion", "7.0", "--dependency-management-enabled");

		_executeGradle(
			workspaceProjectDir,
			":modules:foo:foo-service" + _GRADLE_TASK_PATH_BUILD_SERVICE);

		_executeGradle(workspaceProjectDir, ":modules:foo:foo-api:build");

		_executeGradle(workspaceProjectDir, ":modules:foo:foo-service:build");
	}

	@Test
	public void testBuildTemplateServiceBuilderTargetPlatformEnabled71()
		throws Exception {

		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "workspace");

		File gradleProperties = new File(
			workspaceProjectDir, "gradle.properties");

		Files.write(
			gradleProperties.toPath(),
			"\nliferay.workspace.target.platform.version=7.1.0".getBytes(),
			StandardOpenOption.APPEND);

		File modulesDir = new File(workspaceProjectDir, "modules");

		_buildTemplateWithGradle(
			modulesDir, "service-builder", "foo", "--package-name", "test",
			"--liferayVersion", "7.1", "--dependency-management-enabled");

		_executeGradle(
			workspaceProjectDir,
			":modules:foo:foo-service" + _GRADLE_TASK_PATH_BUILD_SERVICE);

		_executeGradle(workspaceProjectDir, ":modules:foo:foo-api:build");

		_executeGradle(workspaceProjectDir, ":modules:foo:foo-service:build");
	}

	@Test
	public void testBuildTemplateServiceBuilderWithDashes70() throws Exception {
		String name = "backend-integration";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferayVersion", "7.0");

		_testContains(
			gradleProjectDir, name + "-api/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		_testContains(
			gradleProjectDir, name + "-service/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.6.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "com.test", "-Dpackage=" + packageName,
			"-DliferayVersion=7.0");

		_testBuildTemplateServiceBuilder(
			gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
			packageName, "");
	}

	@Test
	public void testBuildTemplateServiceBuilderWithDashes71() throws Exception {
		String name = "backend-integration";
		String packageName = "com.liferay.docs.guestbook";

		File gradleProjectDir = _buildTemplateWithGradle(
			"service-builder", name, "--package-name", packageName,
			"--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, name + "-api/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		_testContains(
			gradleProjectDir, name + "-service/build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"service-builder", name, "com.test", "-Dpackage=" + packageName,
			"-DliferayVersion=7.1");

		_testBuildTemplateServiceBuilder(
			gradleProjectDir, mavenProjectDir, gradleProjectDir, name,
			packageName, "");
	}

	@Test
	public void testBuildTemplateServiceInWorkspace() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction");

		_testContains(
			gradleProjectDir, "build.gradle", "buildscript {",
			"repositories {");

		_writeServiceClass(gradleProjectDir);

		File workspaceDir = _buildWorkspace();

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir = _buildTemplateWithGradle(
			modulesDir, "service", "servicepreaction", "--class-name",
			"FooAction", "--service",
			"com.liferay.portal.kernel.events.LifecycleAction");

		_testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");

		_writeServiceClass(workspaceProjectDir);

		_executeGradle(gradleProjectDir, _GRADLE_TASK_PATH_BUILD);

		_testExists(gradleProjectDir, "build/libs/servicepreaction-1.0.0.jar");

		_executeGradle(workspaceDir, ":modules:servicepreaction:build");

		_testExists(
			workspaceProjectDir, "build/libs/servicepreaction-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateServiceWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service", "service-dependency-management", "--service",
			"com.liferay.portal.kernel.events.LifecycleAction",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateServiceWrapper70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service-wrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"--liferayVersion", "7.0");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0\"",
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
			"--liferayVersion", "7.1");

		_testContains(
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
	public void testBuildTemplateServiceWrapperInWorkspace() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service-wrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper");

		_testContains(
			gradleProjectDir, "build.gradle", "buildscript {",
			"repositories {");

		File workspaceDir = _buildWorkspace();

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir = _buildTemplateWithGradle(
			modulesDir, "service-wrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper");

		_testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");

		_executeGradle(gradleProjectDir, _GRADLE_TASK_PATH_BUILD);

		_testExists(gradleProjectDir, "build/libs/serviceoverride-1.0.0.jar");

		_executeGradle(workspaceDir, ":modules:serviceoverride:build");

		_testExists(
			workspaceProjectDir, "build/libs/serviceoverride-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateServiceWrapperWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"service-wrapper", "wrapper-dependency-management", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateSimulationPanelEntry70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"simulation-panel-entry", "simulator", "--package-name",
			"test.simulator", "--liferayVersion", "7.0");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.3.0\"",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
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
			"test.simulator", "--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"simulation-panel-entry", "simulator", "com.test",
			"-DclassName=Simulator", "-Dpackage=test.simulator",
			"-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateSimulationPanelEntryInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"simulation-panel-entry", "test.simulator",
			"build/libs/test.simulator-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateSimulationPanelEntryWithBOM()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"simulation-panel-entry", "simulator-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateSocialBookmark71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"social-bookmark", "foo", "--package-name", "com.liferay.test",
			"--liferayVersion", "7.1");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(gradleProjectDir, "build.gradle");

		_testContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/social/bookmark" +
				"/FooSocialBookmark.java",
			"public class FooSocialBookmark implements SocialBookmark");
		_testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/page.jsp",
			"<clay:link");
		_testContains(
			gradleProjectDir, "src/main/resources/content/Language.properties",
			"foo=Foo");

		_testNotContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/social/bookmark" +
				"/FooSocialBookmark.java",
			"private ResourceBundleLoader");
		_testNotContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/social/bookmark" +
				"/FooSocialBookmark.java",
			"protected ResourceBundleLoader");

		File mavenProjectDir = _buildTemplateWithMaven(
			"social-bookmark", "foo", "com.test", "-DclassName=Foo",
			"-Dpackage=com.liferay.test", "-DliferayVersion=7.1");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Ignore
	@Test
	public void testBuildTemplateSoyPortlet70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"soy-portlet", "foo", "--package-name", "com.liferay.test",
			"--liferayVersion", "7.0");

		_testExists(gradleProjectDir, "bnd.bnd");
		_testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/Footer.soy");
		_testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/Footer.es.js");
		_testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/Header.soy");
		_testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/Header.es.js");
		_testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/Navigation.soy");
		_testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/Navigation.es.js");
		_testExists(
			gradleProjectDir, "src/main/resources/META-INF/resources/View.soy");
		_testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/View.es.js");

		_testNotExists(gradleProjectDir, "gulpfile.js");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0\"",
			"compileOnly group: \"javax.portlet\", name: \"portlet-api\", " +
				"version: \"2.0\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/constants/FooPortletKeys.java",
			"public static final String Foo = \"foo\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/portlet/FooPortlet.java",
			"public class FooPortlet extends SoyPortlet {");
		_testContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/portlet/action" +
				"/FooViewMVCRenderCommand.java",
			"public class FooViewMVCRenderCommand");

		File mavenProjectDir = _buildTemplateWithMaven(
			"soy-portlet", "foo", "com.test", "-DclassName=Foo",
			"-Dpackage=com.liferay.test", "-DliferayVersion=7.0");

		_testExists(mavenProjectDir, "gulpfile.js");

		File pomXmlFile = new File(mavenProjectDir, "pom.xml");

		Path pomXmlPath = pomXmlFile.toPath();

		_testPomXmlContainsDependency(
			pomXmlPath, "javax.portlet", "portlet-api", "2.0");

		File mavenPackageJsonFile = new File(mavenProjectDir, "package.json");

		Path mavenPackageJsonPath = mavenPackageJsonFile.toPath();

		String mavenPackageJSON = FileUtil.read(mavenPackageJsonPath) + "\n";

		Files.write(
			mavenPackageJsonPath,
			mavenPackageJSON.getBytes(StandardCharsets.UTF_8));

		if (Validator.isNotNull(System.getenv("JENKINS_HOME"))) {
			_addNpmrc(gradleProjectDir);
			_addNpmrc(mavenProjectDir);
			_configureExecuteNpmTask(gradleProjectDir);
			_configurePomNpmConfiguration(mavenProjectDir);
		}

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (Validator.isNotNull(_BUILD_PROJECTS) &&
			_BUILD_PROJECTS.equals("true")) {

			_testSoyOutputFiles(gradleProjectDir, mavenProjectDir);
		}
	}

	@Ignore
	@Test
	public void testBuildTemplateSoyPortlet71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"soy-portlet", "foo", "--package-name", "com.liferay.test",
			"--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0",
			"compileOnly group: \"javax.portlet\", name: \"portlet-api\", " +
				"version: \"3.0.0\"");

		File mavenProjectDir = _buildTemplateWithMaven(
			"soy-portlet", "foo", "com.test", "-DclassName=Foo",
			"-Dpackage=com.liferay.test", "-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		File pomXmlFile = new File(mavenProjectDir, "pom.xml");

		Path pomXmlPath = pomXmlFile.toPath();

		_testPomXmlContainsDependency(
			pomXmlPath, "com.liferay.portal", "com.liferay.portal.kernel",
			"3.0.0");

		_testPomXmlContainsDependency(
			pomXmlPath, "javax.portlet", "portlet-api", "3.0.0");

		File mavenPackageJsonFile = new File(mavenProjectDir, "package.json");

		Path mavenPackageJsonPath = mavenPackageJsonFile.toPath();

		String mavenPackageJSON = FileUtil.read(mavenPackageJsonPath) + "\n";

		Files.write(
			mavenPackageJsonPath,
			mavenPackageJSON.getBytes(StandardCharsets.UTF_8));

		if (Validator.isNotNull(System.getenv("JENKINS_HOME"))) {
			_addNpmrc(gradleProjectDir);
			_addNpmrc(mavenProjectDir);
			_configureExecuteNpmTask(gradleProjectDir);
			_configurePomNpmConfiguration(mavenProjectDir);
		}

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (Validator.isNotNull(_BUILD_PROJECTS) &&
			_BUILD_PROJECTS.equals("true")) {

			_testSoyOutputFiles(gradleProjectDir, mavenProjectDir);
		}
	}

	@Test
	public void testBuildTemplateSoyPortletCustomClass70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"soy-portlet", "foo", "--class-name", "MySoyPortlet",
			"--liferayVersion", "7.0");

		_testContains(
			gradleProjectDir,
			"src/main/java/foo/portlet/MySoyPortletPortlet.java",
			"public class MySoyPortletPortlet extends SoyPortlet {");
	}

	@Test
	public void testBuildTemplateSoyPortletCustomClass71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"soy-portlet", "foo", "--class-name", "MySPR", "--liferayVersion",
			"7.1");

		_testContains(
			gradleProjectDir,
			"src/main/java/foo/portlet/MySPRSoyPortletRegister.java",
			"public class MySPRSoyPortletRegister implements " +
				"SoyPortletRegister {");
	}

	@Test
	public void testBuildTemplateSoyPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"soy-portlet", "soy-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateSpringMVCPortlet70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"spring-mvc-portlet", "foo", "--liferayVersion", "7.0");

		_testExists(gradleProjectDir, "src/main/webapp/WEB-INF/jsp/init.jsp");
		_testExists(gradleProjectDir, "src/main/webapp/WEB-INF/jsp/view.jsp");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.6.0\"");

		_testContains(
			gradleProjectDir,
			"src/main/java/foo/portlet/FooPortletViewController.java",
			"public class FooPortletViewController {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"spring-mvc-portlet", "foo", "com.test", "-DclassName=Foo",
			"-Dpackage=foo", "-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (Validator.isNotNull(_BUILD_PROJECTS) &&
			_BUILD_PROJECTS.equals("true")) {

			_testSpringMVCOutputs(gradleProjectDir);
		}
	}

	@Test
	public void testBuildTemplateSpringMVCPortlet71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"spring-mvc-portlet", "foo", "--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"spring-mvc-portlet", "foo", "com.test", "-DclassName=Foo",
			"-Dpackage=foo", "-DliferayVersion=7.1");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		if (Validator.isNotNull(_BUILD_PROJECTS) &&
			_BUILD_PROJECTS.equals("true")) {

			_testSpringMVCOutputs(gradleProjectDir);
		}
	}

	@Test
	public void testBuildTemplateSpringMVCPortletInWorkspace()
		throws Exception {

		_testBuildTemplateProjectWarInWorkspace(
			"spring-mvc-portlet", "foo", "foo");
	}

	@Test
	public void testBuildTemplateSpringMvcPortletWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"spring-mvc-portlet", "spring-mvc-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateSpringMVCPortletWithPackage()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"spring-mvc-portlet", "foo", "--package-name", "com.liferay.test");

		_testExists(gradleProjectDir, "src/main/webapp/WEB-INF/jsp/init.jsp");
		_testExists(gradleProjectDir, "src/main/webapp/WEB-INF/jsp/view.jsp");

		_testContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/portlet" +
				"/FooPortletViewController.java",
			"public class FooPortletViewController {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"spring-mvc-portlet", "foo", "com.test", "-DclassName=Foo",
			"-Dpackage=com.liferay.test");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateSpringMVCPortletWithPortletName()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"spring-mvc-portlet", "portlet");

		_testExists(gradleProjectDir, "src/main/webapp/WEB-INF/jsp/init.jsp");
		_testExists(gradleProjectDir, "src/main/webapp/WEB-INF/jsp/view.jsp");

		_testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/PortletPortletViewController.java",
			"public class PortletPortletViewController {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"spring-mvc-portlet", "portlet", "com.test", "-DclassName=Portlet",
			"-Dpackage=portlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateSpringMVCPortletWithPortletSuffix()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"spring-mvc-portlet", "portlet-portlet");

		_testExists(gradleProjectDir, "src/main/webapp/WEB-INF/jsp/init.jsp");
		_testExists(gradleProjectDir, "src/main/webapp/WEB-INF/jsp/view.jsp");

		_testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/portlet" +
				"/PortletPortletViewController.java",
			"public class PortletPortletViewController {");

		File mavenProjectDir = _buildTemplateWithMaven(
			"spring-mvc-portlet", "portlet-portlet", "com.test",
			"-DclassName=Portlet", "-Dpackage=portlet.portlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateTemplateContextContributor70()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"template-context-contributor", "blade-test", "--liferayVersion",
			"7.0");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0\"",
			"apply plugin: \"com.liferay.plugin\"");

		_testContains(
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
			"template-context-contributor", "blade-test", "--liferayVersion",
			"7.1");

		_testExists(gradleProjectDir, "bnd.bnd");

		_testContains(
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
	public void testBuildTemplateTemplateContextContributorInWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"template-context-contributor", "blade-test",
			"build/libs/blade.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateTemplateContextContributorWithBOM()
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			"template-context-contributor",
			"context-contributor-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateTheme70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme", "theme-test", "--liferayVersion", "7.0");

		_testContains(
			gradleProjectDir, "build.gradle",
			"name: \"com.liferay.gradle.plugins.theme.builder\"",
			"apply plugin: \"com.liferay.portal.tools.theme.builder\"");
		_testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=theme-test");

		File mavenProjectDir = _buildTemplateWithMaven(
			"theme", "theme-test", "com.test", "-DliferayVersion=7.0");

		_testContains(
			mavenProjectDir, "pom.xml",
			"com.liferay.portal.tools.theme.builder");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateTheme71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme", "theme-test", "--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			"name: \"com.liferay.gradle.plugins.theme.builder\"",
			"apply plugin: \"com.liferay.portal.tools.theme.builder\"");
		_testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=theme-test");

		File mavenProjectDir = _buildTemplateWithMaven(
			"theme", "theme-test", "com.test", "-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "pom.xml",
			"com.liferay.portal.tools.theme.builder");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateThemeContributorCustom() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme-contributor", "my-contributor-custom", "--contributor-type",
			"foo-bar");

		_testContains(
			gradleProjectDir, "bnd.bnd",
			"Liferay-Theme-Contributor-Type: foo-bar",
			"Web-ContextPath: /foo-bar-theme-contributor");
		_testNotContains(
			gradleProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		_testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/foo-bar.scss");
		_testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/js/foo-bar.js");

		File mavenProjectDir = _buildTemplateWithMaven(
			"theme-contributor", "my-contributor-custom", "com.test",
			"-DcontributorType=foo-bar", "-Dpackage=my.contributor.custom");

		_testContains(
			mavenProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateThemeContributorCustom71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme-contributor", "my-contributor-custom", "--contributor-type",
			"foo-bar", "--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "bnd.bnd",
			"Liferay-Theme-Contributor-Type: foo-bar",
			"Web-ContextPath: /foo-bar-theme-contributor");
		_testNotContains(
			gradleProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		_testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/css/foo-bar.scss");
		_testExists(
			gradleProjectDir,
			"src/main/resources/META-INF/resources/js/foo-bar.js");

		File mavenProjectDir = _buildTemplateWithMaven(
			"theme-contributor", "my-contributor-custom", "com.test",
			"-DcontributorType=foo-bar", "-Dpackage=my.contributor.custom",
			"-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd",
			"-plugin.sass: com.liferay.ant.bnd.sass.SassAnalyzerPlugin");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateThemeContributorDefaults() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"theme-contributor", "my-contributor-default");

		_testContains(
			gradleProjectDir, "bnd.bnd",
			"Liferay-Theme-Contributor-Type: my-contributor-default",
			"Web-ContextPath: /my-contributor-default-theme-contributor");
	}

	@Test
	public void testBuildTemplateThemeContributorinWorkspace()
		throws Exception {

		_testBuildTemplateWithWorkspace(
			"theme-contributor", "my-contributor",
			"build/libs/my.contributor-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateThemeInWorkspace() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle("theme", "theme-test");

		_testContains(
			gradleProjectDir, "build.gradle", "buildscript {",
			"apply plugin: \"com.liferay.portal.tools.theme.builder\"",
			"repositories {");

		File workspaceDir = _buildWorkspace();

		File warsDir = new File(workspaceDir, "wars");

		File workspaceProjectDir = _buildTemplateWithGradle(
			warsDir, "theme", "theme-test");

		_testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");

		_executeGradle(gradleProjectDir, _GRADLE_TASK_PATH_BUILD);

		File gradleWarFile = _testExists(
			gradleProjectDir, "build/libs/theme-test.war");

		_executeGradle(workspaceDir, ":wars:theme-test:build");

		File workspaceWarFile = _testExists(
			workspaceProjectDir, "build/libs/theme-test.war");

		_testWarsDiff(gradleWarFile, workspaceWarFile);
	}

	@Test
	public void testBuildTemplateWarHook70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-hook", "WarHook", "--liferayVersion", "7.0");

		_testExists(gradleProjectDir, "src/main/resources/portal.properties");
		_testExists(
			gradleProjectDir, "src/main/webapp/WEB-INF/liferay-hook.xml");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/warhook/WarHookLoginPostAction.java",
			"public class WarHookLoginPostAction extends Action");
		_testContains(
			gradleProjectDir, "src/main/java/warhook/WarHookStartupAction.java",
			"public class WarHookStartupAction extends SimpleAction");
		_testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=WarHook");

		File mavenProjectDir = _buildTemplateWithMaven(
			"war-hook", "WarHook", "warhook", "-DclassName=WarHook",
			"-Dpackage=warhook", "-DliferayVersion=7.0");

		_testContains(mavenProjectDir, "pom.xml");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarHook71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-hook", "WarHook", "--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"war-hook", "WarHook", "warhook", "-DclassName=WarHook",
			"-Dpackage=warhook", "-DliferayVersion=7.1");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarHookWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-hook", "war-hook-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateWarMVCPortlet70() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "WarMVCPortlet", "--liferayVersion", "7.0");

		_testExists(gradleProjectDir, "src/main/webapp/init.jsp");
		_testExists(gradleProjectDir, "src/main/webapp/view.jsp");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0\"",
			"apply plugin: \"com.liferay.css.builder\"",
			"apply plugin: \"war\"");
		_testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=WarMVCPortlet");

		File mavenProjectDir = _buildTemplateWithMaven(
			"war-mvc-portlet", "WarMVCPortlet", "warmvcportlet",
			"-DclassName=WarMVCPortlet", "-Dpackage=WarMVCPortlet",
			"-DliferayVersion=7.0");

		_testContains(
			mavenProjectDir, "pom.xml", "maven-war-plugin",
			"com.liferay.css.builder");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarMVCPortlet71() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "WarMVCPortlet", "--liferayVersion", "7.1");

		_testContains(
			gradleProjectDir, "build.gradle",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		File mavenProjectDir = _buildTemplateWithMaven(
			"war-mvc-portlet", "WarMVCPortlet", "warmvcportlet",
			"-DclassName=WarMVCPortlet", "-Dpackage=WarMVCPortlet",
			"-DliferayVersion=7.1");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarMVCPortletInWorkspace() throws Exception {
		_testBuildTemplateProjectWarInWorkspace(
			"war-mvc-portlet", "WarMVCPortlet", "WarMVCPortlet");
	}

	@Test
	public void testBuildTemplateWarMVCPortletWithPackage() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "WarMVCPortlet", "--package-name",
			"com.liferay.test");

		_testExists(gradleProjectDir, "src/main/webapp/init.jsp");
		_testExists(gradleProjectDir, "src/main/webapp/view.jsp");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.css.builder\"",
			"apply plugin: \"war\"");
		_testContains(
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

		_testExists(gradleProjectDir, "src/main/webapp/init.jsp");
		_testExists(gradleProjectDir, "src/main/webapp/view.jsp");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.css.builder\"",
			"apply plugin: \"war\"");
		_testContains(
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

		_testExists(gradleProjectDir, "src/main/webapp/init.jsp");
		_testExists(gradleProjectDir, "src/main/webapp/view.jsp");

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.css.builder\"",
			"apply plugin: \"war\"");
		_testContains(
			gradleProjectDir,
			"src/main/webapp/WEB-INF/liferay-plugin-package.properties",
			"name=WarMVC-portlet");

		File mavenProjectDir = _buildTemplateWithMaven(
			"war-mvc-portlet", "WarMVC-portlet", "warmvc.portlet",
			"-DclassName=WarMVCPortlet", "-Dpackage=WarMVC.portlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWarMvcWithBOM() throws Exception {
		File gradleProjectDir = _buildTemplateWithGradle(
			"war-mvc-portlet", "war-mvc-dependency-management",
			"--dependency-management-enabled");

		_testNotContains(
			gradleProjectDir, "build.gradle", "version: \"[0-9].*");

		_testContains(
			gradleProjectDir, "build.gradle", _DEPENDENCY_PORTAL_KERNEL + "\n");
	}

	@Test
	public void testBuildTemplateWithGradle() throws Exception {
		_buildTemplateWithGradle(
			temporaryFolder.newFolder(), null, "foo-portlet", false, false);
		_buildTemplateWithGradle(
			temporaryFolder.newFolder(), null, "foo-portlet", false, true);
		_buildTemplateWithGradle(
			temporaryFolder.newFolder(), null, "foo-portlet", true, false);
		_buildTemplateWithGradle(
			temporaryFolder.newFolder(), null, "foo-portlet", true, true);
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
			"mvc-portlet", "barfoo", "com.test", "-DclassName=Barfoo",
			"-Dpackage=foo.bar");

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	@Test
	public void testBuildTemplateWorkspace() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "foows");

		_testExists(workspaceProjectDir, "configs/dev/portal-ext.properties");
		_testExists(workspaceProjectDir, "gradle.properties");
		_testExists(workspaceProjectDir, "modules");
		_testExists(workspaceProjectDir, "themes");
		_testExists(workspaceProjectDir, "wars");

		_testNotExists(workspaceProjectDir, "modules/pom.xml");
		_testNotExists(workspaceProjectDir, "themes/pom.xml");
		_testNotExists(workspaceProjectDir, "wars/pom.xml");

		String gradlePluginsWorkspaceVersion = System.getProperty(
			"com.liferay.gradle.plugins.workspace.version");

		Assert.assertNotNull(gradlePluginsWorkspaceVersion);

		_testContains(
			workspaceProjectDir, "settings.gradle",
			"version: \"" + gradlePluginsWorkspaceVersion + "\"");

		File moduleProjectDir = _buildTemplateWithGradle(
			new File(workspaceProjectDir, "modules"), "", "foo-portlet");

		_testNotContains(
			moduleProjectDir, "build.gradle", "buildscript", "repositories");

		_executeGradle(
			workspaceProjectDir,
			":modules:foo-portlet" + _GRADLE_TASK_PATH_BUILD);

		_testExists(moduleProjectDir, "build/libs/foo.portlet-1.0.0.jar");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateWorkspaceExistingFile() throws Exception {
		File destinationDir = temporaryFolder.newFolder("existing-file");

		_createNewFiles("foo", destinationDir);

		_buildTemplateWithGradle(
			destinationDir, WorkspaceUtil.WORKSPACE, "foo");
	}

	@Test
	public void testBuildTemplateWorkspaceForce() throws Exception {
		File destinationDir = temporaryFolder.newFolder("existing-file");

		_createNewFiles("foo", destinationDir);

		_buildTemplateWithGradle(
			destinationDir, WorkspaceUtil.WORKSPACE, "forced", "--force");
	}

	@Test
	public void testBuildTemplateWorkspaceLocalProperties() throws Exception {
		File workspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "foo");

		_testExists(workspaceProjectDir, "gradle-local.properties");

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

		_buildTemplateWithGradle(
			new File(workspaceProjectDir, modulesDirName), "", "foo-portlet");

		_executeGradle(
			workspaceProjectDir,
			":" + modulesDirName.replace('/', ':') + ":foo-portlet" +
				_GRADLE_TASK_PATH_DEPLOY);

		_testExists(
			workspaceProjectDir, homeDirName + "/osgi/modules/foo.portlet.jar");
	}

	@Test
	public void testBuildTemplateWorkspaceWith70() throws Exception {
		File gradleWorkspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "withportlet", "--liferayVersion", "7.0");

		_testContains(
			gradleWorkspaceProjectDir, "gradle.properties", true,
			".*liferay.workspace.bundle.url=.*liferay.com/portal/7.0.*");

		File gradlePropertiesFile = new File(
			gradleWorkspaceProjectDir, "gradle.properties");

		_testPropertyKeyExists(
			gradlePropertiesFile, "liferay.workspace.bundle.url");

		File mavenWorkspaceProjectDir = _buildTemplateWithMaven(
			WorkspaceUtil.WORKSPACE, "withportlet", "com.test",
			"-DliferayVersion=7.0");

		_testContains(
			mavenWorkspaceProjectDir, "pom.xml",
			"<liferay.workspace.bundle.url>", "liferay.com/portal/7.0.");
	}

	@Test
	public void testBuildTemplateWorkspaceWith71() throws Exception {
		File gradleWorkspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "withportlet", "--liferayVersion", "7.1");

		_testContains(
			gradleWorkspaceProjectDir, "gradle.properties", true,
			".*liferay.workspace.bundle.url=.*liferay.com/portal/7.1.0-.*");

		File gradlePropertiesFile = new File(
			gradleWorkspaceProjectDir, "gradle.properties");

		_testPropertyKeyExists(
			gradlePropertiesFile, "liferay.workspace.bundle.url");

		File mavenWorkspaceProjectDir = _buildTemplateWithMaven(
			WorkspaceUtil.WORKSPACE, "withportlet", "com.test",
			"-DliferayVersion=7.1");

		_testContains(
			mavenWorkspaceProjectDir, "pom.xml",
			"<liferay.workspace.bundle.url>", "liferay.com/portal/7.1.0-");
	}

	@Test
	public void testBuildTemplateWorkspaceWithPortlet() throws Exception {
		File gradleWorkspaceProjectDir = _buildTemplateWithGradle(
			WorkspaceUtil.WORKSPACE, "withportlet");

		File gradleModulesDir = new File(gradleWorkspaceProjectDir, "modules");

		_buildTemplateWithGradle(
			gradleModulesDir, "mvc-portlet", "foo-portlet");

		File mavenWorkspaceProjectDir = _buildTemplateWithMaven(
			WorkspaceUtil.WORKSPACE, "withportlet", "com.test");

		File mavenModulesDir = new File(mavenWorkspaceProjectDir, "modules");

		_buildTemplateWithMaven(
			mavenWorkspaceProjectDir.getParentFile(), mavenModulesDir,
			"mvc-portlet", "foo-portlet", "com.test", "-DclassName=Foo",
			"-Dpackage=foo.portlet", "-DprojectType=workspace");

		_executeGradle(
			gradleWorkspaceProjectDir,
			":modules:foo-portlet" + _GRADLE_TASK_PATH_BUILD);

		_testExists(
			gradleModulesDir, "foo-portlet/build/libs/foo.portlet-1.0.0.jar");

		_executeMaven(mavenModulesDir, _MAVEN_GOAL_PACKAGE);

		_testExists(
			mavenModulesDir, "foo-portlet/target/foo-portlet-1.0.0.jar");
	}

	@Test
	public void testCompareGradlePluginVersions() throws Exception {
		String template = "mvc-portlet";
		String name = "foo";

		File gradleProjectDir = _buildTemplateWithGradle(template, name);

		File workspaceDir = _buildWorkspace();

		File modulesDir = new File(workspaceDir, "modules");

		_buildTemplateWithGradle(modulesDir, template, name);

		Optional<String> result = _executeGradle(
			gradleProjectDir, true, _GRADLE_TASK_PATH_BUILD);

		Matcher matcher = _gradlePluginVersionPattern.matcher(result.get());

		String standaloneGradlePluginVersion = null;

		if (matcher.matches()) {
			standaloneGradlePluginVersion = matcher.group(1);
		}

		result = _executeGradle(
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

		Optional<String> gradleResult = _executeGradle(
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

		List<File> customArchetypesDirs = new ArrayList<>();

		customArchetypesDirs.add(customArchetypesDir);

		Map<String, String> customTemplatesMap = ProjectTemplates.getTemplates(
			customArchetypesDirs);

		Map<String, String> templatesMap = ProjectTemplates.getTemplates();

		Assert.assertEquals(customTemplatesMap.size(), templatesMap.size() + 1);
	}

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private static void _addNexusRepositoriesElement(
		Document document, String parentElementName, String elementName) {

		Element projectElement = document.getDocumentElement();

		Element repositoriesElement = XMLTestUtil.getChildElement(
			projectElement, parentElementName);

		if (repositoriesElement == null) {
			repositoriesElement = document.createElement(parentElementName);

			projectElement.appendChild(repositoriesElement);
		}

		Element repositoryElement = document.createElement(elementName);

		Element idElement = document.createElement("id");

		idElement.appendChild(
			document.createTextNode(System.currentTimeMillis() + ""));

		Element urlElement = document.createElement("url");

		Text urlText = null;

		String repositoryUrl = mavenExecutor.getRepositoryUrl();

		if (Validator.isNotNull(repositoryUrl)) {
			urlText = document.createTextNode(repositoryUrl);
		}
		else {
			urlText = document.createTextNode(_REPOSITORY_CDN_URL);
		}

		urlElement.appendChild(urlText);

		repositoryElement.appendChild(idElement);
		repositoryElement.appendChild(urlElement);

		repositoriesElement.appendChild(repositoryElement);
	}

	private static void _addNpmrc(File projectDir) throws IOException {
		File npmrcFile = new File(projectDir, ".npmrc");

		String content = "sass_binary_site=" + _NODEJS_NPM_CI_SASS_BINARY_SITE;

		Files.write(
			npmrcFile.toPath(), content.getBytes(StandardCharsets.UTF_8));
	}

	private static void _buildProjects(
			File gradleProjectDir, File mavenProjectDir)
		throws Exception {

		File gradleOutputDir = new File(gradleProjectDir, "build/libs");
		File mavenOutputDir = new File(mavenProjectDir, "target");

		_buildProjects(
			gradleProjectDir, mavenProjectDir, gradleOutputDir, mavenOutputDir,
			_GRADLE_TASK_PATH_BUILD);
	}

	private static void _buildProjects(
			File gradleProjectDir, File mavenProjectDir, File gradleOutputDir,
			File mavenOutputDir, String... gradleTaskPath)
		throws Exception {

		if (Validator.isNotNull(_BUILD_PROJECTS) &&
			_BUILD_PROJECTS.equals("true")) {

			_executeGradle(gradleProjectDir, gradleTaskPath);

			Path gradleOutputPath = FileTestUtil.getFile(
				gradleOutputDir.toPath(), _OUTPUT_FILENAME_GLOB_REGEX, 1);

			Assert.assertNotNull(gradleOutputPath);

			Assert.assertTrue(Files.exists(gradleOutputPath));

			File gradleOutputFile = gradleOutputPath.toFile();

			String gradleOutputFileName = gradleOutputFile.getName();

			_executeMaven(mavenProjectDir, _MAVEN_GOAL_PACKAGE);

			Path mavenOutputPath = FileTestUtil.getFile(
				mavenOutputDir.toPath(), _OUTPUT_FILENAME_GLOB_REGEX, 1);

			Assert.assertNotNull(mavenOutputPath);

			Assert.assertTrue(Files.exists(mavenOutputPath));

			File mavenOutputFile = mavenOutputPath.toFile();

			String mavenOutputFileName = mavenOutputFile.getName();

			try {
				if (gradleOutputFileName.endsWith(".jar")) {
					_testBundlesDiff(gradleOutputFile, mavenOutputFile);
				}
				else if (gradleOutputFileName.endsWith(".war")) {
					_testWarsDiff(gradleOutputFile, mavenOutputFile);
				}
			}
			catch (Throwable t) {
				if (_TEST_DEBUG_BUNDLE_DIFFS) {
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

	private static File _buildTemplateWithGradle(
			File destinationDir, String template, String name, boolean gradle,
			boolean maven, String... args)
		throws Exception {

		List<String> completeArgs = new ArrayList<>(args.length + 6);

		completeArgs.add("--destination");
		completeArgs.add(destinationDir.getPath());

		if (!gradle) {
			completeArgs.add("--gradle");
			completeArgs.add(String.valueOf(gradle));
		}

		if (maven) {
			completeArgs.add("--maven");
		}

		if (Validator.isNotNull(name)) {
			completeArgs.add("--name");
			completeArgs.add(name);
		}

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

		if (gradle) {
			_testExists(projectDir, "build.gradle");
		}
		else {
			_testNotExists(projectDir, "build.gradle");
		}

		if (maven) {
			_testExists(projectDir, "pom.xml");
		}
		else {
			_testNotExists(projectDir, "pom.xml");
		}

		boolean workspace = WorkspaceUtil.isWorkspace(destinationDir);

		if (gradle && !workspace) {
			for (String fileName : _GRADLE_WRAPPER_FILE_NAMES) {
				_testExists(projectDir, fileName);
			}

			_testExecutable(projectDir, "gradlew");
		}
		else {
			for (String fileName : _GRADLE_WRAPPER_FILE_NAMES) {
				_testNotExists(projectDir, fileName);
			}

			_testNotExists(projectDir, "settings.gradle");
		}

		if (maven && !workspace) {
			for (String fileName : _MAVEN_WRAPPER_FILE_NAMES) {
				_testExists(projectDir, fileName);
			}

			_testExecutable(projectDir, "mvnw");
		}
		else {
			for (String fileName : _MAVEN_WRAPPER_FILE_NAMES) {
				_testNotExists(projectDir, fileName);
			}
		}

		return projectDir;
	}

	private static File _buildTemplateWithGradle(
			File destinationDir, String template, String name, String... args)
		throws Exception {

		return _buildTemplateWithGradle(
			destinationDir, template, name, true, false, args);
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
			completeArgs.add("-DliferayVersion=7.1");
		}

		if (!projectTypeSet) {
			completeArgs.add("-DprojectType=standalone");
		}

		_executeMaven(destinationDir, completeArgs.toArray(new String[0]));

		File projectDir = new File(destinationDir, name);

		_testExists(projectDir, "pom.xml");
		_testNotExists(projectDir, "gradlew");
		_testNotExists(projectDir, "gradlew.bat");
		_testNotExists(projectDir, "gradle/wrapper/gradle-wrapper.jar");
		_testNotExists(projectDir, "gradle/wrapper/gradle-wrapper.properties");

		_testArchetyper(
			parentDir, destinationDir, projectDir, name, groupId, template,
			completeArgs);

		return projectDir;
	}

	private static void _configureExecuteNpmTask(File projectDir)
		throws Exception {

		File buildGradleFile = _testContains(
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

		_editXml(
			pomXmlFile,
			document -> {
				try {
					NodeList nodeList =
						(NodeList)_pomXmlNpmInstallXPathExpression.evaluate(
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

	private static void _editXml(File xmlFile, Consumer<Document> consumer)
		throws Exception {

		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();

		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		Document document = documentBuilder.parse(xmlFile);

		consumer.accept(document);

		DOMSource domSource = new DOMSource(document);

		transformer.transform(domSource, new StreamResult(xmlFile));
	}

	private static Optional<String> _executeGradle(
			File projectDir, boolean debug, boolean buildAndFail,
			String... taskPaths)
		throws IOException {

		final String repositoryUrl = mavenExecutor.getRepositoryUrl();

		String projectPath = projectDir.getPath();

		if (projectPath.contains("workspace")) {
			File buildFile = new File(projectDir, "build.gradle");

			Path buildFilePath = buildFile.toPath();

			String content = FileUtil.read(buildFilePath);

			if (!content.contains("allprojects")) {
				StringBuilder sb = new StringBuilder();

				sb.append(content);
				sb.append("allprojects {\n");
				sb.append("repositories {");
				sb.append("mavenLocal()}}");

				content = sb.toString();

				Files.write(
					buildFilePath, content.getBytes(StandardCharsets.UTF_8));
			}
		}

		Files.walkFileTree(
			projectDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(path.getFileName());

					if (fileName.equals("build.gradle") ||
						fileName.equals("settings.gradle")) {

						String content = FileUtil.read(path);

						if (Validator.isNotNull(repositoryUrl)) {
							content = content.replace(
								"\"" + _REPOSITORY_CDN_URL + "\"",
								"\"" + repositoryUrl + "\"");
						}

						content = content.replace(
							"repositories {", "repositories {\tmavenLocal()\n");

						Files.write(
							path, content.getBytes(StandardCharsets.UTF_8));
					}

					return FileVisitResult.CONTINUE;
				}

			});

		GradleRunner gradleRunner = GradleRunner.create();

		List<String> arguments = new ArrayList<>(taskPaths.length + 5);

		if (debug) {
			arguments.add("--debug");
		}
		else {
			arguments.add("--stacktrace");
		}

		String httpProxyHost = mavenExecutor.getHttpProxyHost();
		int httpProxyPort = mavenExecutor.getHttpProxyPort();

		if (Validator.isNotNull(httpProxyHost) && (httpProxyPort > 0)) {
			arguments.add("-Dhttp.proxyHost=" + httpProxyHost);
			arguments.add("-Dhttp.proxyPort=" + httpProxyPort);
		}

		for (String taskPath : taskPaths) {
			arguments.add(taskPath);
		}

		String stdOutput = null;

		StringWriter stringWriter = new StringWriter();

		if (debug) {
			gradleRunner.forwardStdOutput(stringWriter);
		}

		gradleRunner.withArguments(arguments);

		gradleRunner.withGradleDistribution(_gradleDistribution);
		gradleRunner.withProjectDir(projectDir);

		BuildResult buildResult = null;

		if (buildAndFail) {
			buildResult = gradleRunner.buildAndFail();

			stdOutput = buildResult.getOutput();
		}
		else {
			buildResult = gradleRunner.build();

			for (String taskPath : taskPaths) {
				BuildTask buildTask = buildResult.task(taskPath);

				Assert.assertNotNull(
					"Build task \"" + taskPath + "\" not found", buildTask);

				Assert.assertEquals(
					"Unexpected outcome for task \"" + buildTask.getPath() +
						"\"",
					TaskOutcome.SUCCESS, buildTask.getOutcome());
			}
		}

		if (debug) {
			stdOutput = stringWriter.toString();
			stringWriter.close();
		}

		return Optional.ofNullable(stdOutput);
	}

	private static Optional<String> _executeGradle(
			File projectDir, boolean debug, String... taskPaths)
		throws IOException {

		return _executeGradle(projectDir, debug, false, taskPaths);
	}

	private static void _executeGradle(File projectDir, String... taskPaths)
		throws IOException {

		_executeGradle(projectDir, false, taskPaths);
	}

	private static String _executeMaven(
			File projectDir, boolean buildAndFail, String... args)
		throws Exception {

		File pomXmlFile = new File(projectDir, "pom.xml");

		if (pomXmlFile.exists()) {
			_editXml(
				pomXmlFile,
				document -> {
					_addNexusRepositoriesElement(
						document, "repositories", "repository");
					_addNexusRepositoriesElement(
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

	private static List<String> _sanitizeLines(List<String> lines) {
		List<String> sanitizedLines = new ArrayList<>();

		for (String line : lines) {
			line = line.replaceAll("\\?t=[0-9]+", "");

			sanitizedLines.add(line);
		}

		return sanitizedLines;
	}

	private static void _testArchetyper(
			File parentDir, File destinationDir, File projectDir, String name,
			String groupId, String template, List<String> args)
		throws Exception {

		String author = System.getProperty("user.name");
		String className = name;
		String contributorType = null;
		String hostBundleSymbolicName = null;
		String hostBundleVersion = null;
		String packageName = name.replace('-', '.');
		String service = null;
		String version = "7.0";

		for (String arg : args) {
			int pos = arg.indexOf('=');

			if (pos == -1) {
				continue;
			}

			String key = arg.substring(2, pos);
			String value = arg.substring(pos + 1);

			if (key.equals("author")) {
				author = value;
			}
			else if (key.equals("className")) {
				className = value;
			}
			else if (key.equals("contributorType")) {
				contributorType = value;
			}
			else if (key.equals("hostBundleSymbolicName")) {
				hostBundleSymbolicName = value;
			}
			else if (key.equals("hostBundleVersion")) {
				hostBundleVersion = value;
			}
			else if (key.equals("package")) {
				packageName = value;
			}
			else if (key.equals("serviceClass")) {
				service = value;
			}
			else if (key.equals("serviceWrapperClass")) {
				service = value;
			}
			else if (key.equals("liferayVersion")) {
				version = value;
			}
		}

		ProjectGenerator projectGenerator = new ProjectGenerator();

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		projectTemplatesArgs.setAuthor(author);
		projectTemplatesArgs.setClassName(className);
		projectTemplatesArgs.setContributorType(contributorType);

		File archetyperDestinationDir = null;

		if (parentDir.equals(destinationDir)) {
			archetyperDestinationDir = new File(
				destinationDir.getParentFile(), "archetyper");
		}
		else {
			Path destinationDirPath = destinationDir.toPath();
			Path parentDirPath = parentDir.toPath();

			Path archetyperPath = parentDirPath.resolveSibling("archetyper");
			Path relativePath = parentDirPath.relativize(destinationDirPath);

			Path archetyperDestinationPath = archetyperPath.resolve(
				relativePath);

			archetyperDestinationDir = archetyperDestinationPath.toFile();
		}

		projectTemplatesArgs.setDestinationDir(archetyperDestinationDir);

		projectTemplatesArgs.setGradle(false);
		projectTemplatesArgs.setGroupId(groupId);
		projectTemplatesArgs.setHostBundleSymbolicName(hostBundleSymbolicName);
		projectTemplatesArgs.setHostBundleVersion(hostBundleVersion);
		projectTemplatesArgs.setLiferayVersion(version);
		projectTemplatesArgs.setMaven(true);
		projectTemplatesArgs.setName(name);
		projectTemplatesArgs.setPackageName(packageName);
		projectTemplatesArgs.setService(service);
		projectTemplatesArgs.setTemplate(template);

		projectGenerator.generateProject(
			projectTemplatesArgs, archetyperDestinationDir);

		File archetyperProjectDir = new File(archetyperDestinationDir, name);

		FileUtil.deleteFiles(archetyperDestinationDir.toPath(), "build.gradle");

		DirectoryComparator directoryComparator = new DirectoryComparator(
			projectDir, archetyperProjectDir);

		List<String> differences = directoryComparator.getDifferences();

		Assert.assertTrue(
			"Found differences " + differences, differences.isEmpty());
	}

	private static void _testBundlesDiff(File bundleFile1, File bundleFile2)
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

	private static void _testChangePortletModelHintsXml(
			File projectDir, String serviceProjectName,
			Callable<Void> buildServiceCallable)
		throws Exception {

		buildServiceCallable.call();

		File file = _testExists(
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

	private static File _testContains(
			File dir, String fileName, boolean regex, String... strings)
		throws IOException {

		return _testContainsOrNot(dir, fileName, regex, true, strings);
	}

	private static File _testContains(
			File dir, String fileName, String... strings)
		throws IOException {

		return _testContains(dir, fileName, false, strings);
	}

	private static void _testContainsJarEntry(File file, String name)
		throws IOException {

		try (JarFile jarFile = new JarFile(file)) {
			Assert.assertNotNull(jarFile.getJarEntry(name));
		}
	}

	private static File _testContainsOrNot(
			File dir, String fileName, boolean regex, boolean contains,
			String... strings)
		throws IOException {

		File file = _testExists(dir, fileName);

		String content = FileUtil.read(file.toPath());

		for (String s : strings) {
			boolean found;

			if (regex) {
				Pattern pattern = Pattern.compile(
					s, Pattern.DOTALL | Pattern.MULTILINE);

				Matcher matcher = pattern.matcher(content);

				found = matcher.matches();
			}
			else {
				found = content.contains(s);
			}

			if (contains) {
				Assert.assertTrue("Not found in " + fileName + ": " + s, found);
			}
			else {
				Assert.assertFalse("Found in " + fileName + ": " + s, found);
			}
		}

		return file;
	}

	private static File _testEquals(
			File dir, String fileName, String expectedContent)
		throws IOException {

		File file = _testExists(dir, fileName);

		Assert.assertEquals(
			"Incorrect " + fileName, expectedContent,
			FileUtil.read(file.toPath()));

		return file;
	}

	private static File _testExecutable(File dir, String fileName) {
		File file = _testExists(dir, fileName);

		Assert.assertTrue(fileName + " is not executable", file.canExecute());

		return file;
	}

	private static File _testExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertTrue("Missing " + fileName, file.exists());

		return file;
	}

	private static void _testExists(ZipFile zipFile, String name) {
		Assert.assertNotNull("Missing " + name, zipFile.getEntry(name));
	}

	private static File _testNotContains(
			File dir, String fileName, boolean regex, String... strings)
		throws IOException {

		return _testContainsOrNot(dir, fileName, regex, false, strings);
	}

	private static File _testNotContains(
			File dir, String fileName, String... strings)
		throws IOException {

		return _testNotContains(dir, fileName, false, strings);
	}

	private static File _testNotExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertFalse("Unexpected " + fileName, file.exists());

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

	private static void _testSoyOutputFiles(
			File gradleProjectDir, File mavenProjectDir)
		throws Exception {

		File gradleJarFile = new File(
			gradleProjectDir, "build/libs/com.liferay.test-1.0.0.jar");
		File mavenJarFile = new File(mavenProjectDir, "target/foo-1.0.0.jar");

		_testContainsJarEntry(gradleJarFile, "package.json");
		_testContainsJarEntry(mavenJarFile, "package.json");
	}

	private static void _testSpringMVCOutputs(File gradleProjectDir)
		throws Exception {

		ZipFile zipFile = null;

		File gradleWarFile = new File(gradleProjectDir, "build/libs/foo.war");

		try {
			zipFile = new ZipFile(gradleWarFile);

			_testExists(zipFile, "css/main.css");
			_testExists(zipFile, "css/main_rtl.css");

			_testExists(zipFile, "WEB-INF/lib/aopalliance-1.0.jar");
			_testExists(zipFile, "WEB-INF/lib/commons-logging-1.2.jar");

			for (String jarName : _SPRING_MVC_PORTLET_JAR_NAMES) {
				_testExists(
					zipFile,
					"WEB-INF/lib/spring-" + jarName + "-" +
						_SPRING_MVC_PORTLET_VERSION + ".jar");
			}
		}
		finally {
			ZipFile.closeQuietly(zipFile);
		}
	}

	private static File _testStartsWith(
			File dir, String fileName, String prefix)
		throws IOException {

		File file = _testExists(dir, fileName);

		String content = FileUtil.read(file.toPath());

		Assert.assertTrue(
			fileName + " must start with \"" + prefix + "\"",
			content.startsWith(prefix));

		return file;
	}

	private static void _testWarsDiff(File warFile1, File warFile2)
		throws IOException {

		DifferenceCalculator differenceCalculator = new DifferenceCalculator(
			warFile1, warFile2);

		differenceCalculator.setFilenameRegexToIgnore(
			Collections.singleton(".*META-INF.*"));
		differenceCalculator.setIgnoreTimestamps(true);

		Differences differences = differenceCalculator.getDifferences();

		if (!differences.hasDifferences()) {
			return;
		}

		StringBuilder message = new StringBuilder();

		message.append("WAR ");
		message.append(warFile1);
		message.append(" and ");
		message.append(warFile2);
		message.append(" do not match:");
		message.append(System.lineSeparator());

		boolean realChange;

		Map<String, ZipArchiveEntry> added = differences.getAdded();
		Map<String, ZipArchiveEntry[]> changed = differences.getChanged();
		Map<String, ZipArchiveEntry> removed = differences.getRemoved();

		if (added.isEmpty() && !changed.isEmpty() && removed.isEmpty()) {
			realChange = false;

			ZipFile zipFile1 = null;
			ZipFile zipFile2 = null;

			try {
				zipFile1 = new ZipFile(warFile1);
				zipFile2 = new ZipFile(warFile2);

				for (Map.Entry<String, ZipArchiveEntry[]> entry :
						changed.entrySet()) {

					ZipArchiveEntry[] zipArchiveEntries = entry.getValue();

					ZipArchiveEntry zipArchiveEntry1 = zipArchiveEntries[0];
					ZipArchiveEntry zipArchiveEntry2 = zipArchiveEntries[0];

					if (zipArchiveEntry1.isDirectory() &&
						zipArchiveEntry2.isDirectory() &&
						(zipArchiveEntry1.getSize() ==
							zipArchiveEntry2.getSize()) &&
						(zipArchiveEntry1.getCompressedSize() <= 2) &&
						(zipArchiveEntry2.getCompressedSize() <= 2)) {

						// Skip zipdiff bug

						continue;
					}

					try (InputStream inputStream1 = zipFile1.getInputStream(
							zipFile1.getEntry(zipArchiveEntry1.getName()));
						InputStream inputStream2 = zipFile2.getInputStream(
							zipFile2.getEntry(zipArchiveEntry2.getName()))) {

						List<String> lines1 = StringTestUtil.readLines(
							inputStream1);
						List<String> lines2 = StringTestUtil.readLines(
							inputStream2);

						lines1 = _sanitizeLines(lines1);
						lines2 = _sanitizeLines(lines2);

						Patch<String> diff = DiffUtils.diff(lines1, lines2);

						List<Delta<String>> deltas = diff.getDeltas();

						if (deltas.isEmpty()) {
							continue;
						}

						message.append(System.lineSeparator());

						message.append("--- ");
						message.append(zipArchiveEntry1.getName());
						message.append(System.lineSeparator());

						message.append("+++ ");
						message.append(zipArchiveEntry2.getName());
						message.append(System.lineSeparator());

						for (Delta<String> delta : deltas) {
							message.append('\t');
							message.append(delta.getOriginal());
							message.append(System.lineSeparator());

							message.append('\t');
							message.append(delta.getRevised());
							message.append(System.lineSeparator());
						}
					}

					realChange = true;

					break;
				}
			}
			finally {
				ZipFile.closeQuietly(zipFile1);
				ZipFile.closeQuietly(zipFile2);
			}
		}
		else {
			realChange = true;
		}

		Assert.assertFalse(message.toString() + differences, realChange);
	}

	private static void _writeServiceClass(File projectDir) throws IOException {
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

	private File _buildTemplateWithGradle(
			String template, String name, String... args)
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("gradle");

		return _buildTemplateWithGradle(destinationDir, template, name, args);
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

		return _buildTemplateWithGradle(
			destinationDir, WorkspaceUtil.WORKSPACE, "test-workspace");
	}

	private void _testBuildTemplateNpm70(
			String template, String name, String packageName, String className,
			String bootstrapRequire)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, name, "--liferayVersion", "7.0");

		_testNotContains(
			gradleProjectDir, "build.gradle",
			"name: \"com.liferay.frontend.js.loader.modules.extender.api\"");

		_testNotContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp",
			"<%@ page import=\"" + packageName + ".constants." + className +
				"WebKeys\" %>");
		_testNotContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp",
			"<aui:script require=\"<%= bootstrapRequire %>\">",
			bootstrapRequire);

		String packagePath = packageName.replaceAll("\\.", "\\/");

		_testNotContains(
			gradleProjectDir,
			"src/main/java/" + packagePath + "/portlet/" + className +
				"Portlet.java",
			"import " + packageName + ".constants." + className + "WebKeys;");

		_testNotExists(
			gradleProjectDir,
			"src/main/java/" + packagePath + "/constants/" + className +
				"WebKeys.java");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, name, "com.test", "-DclassName=" + className,
			"-Dpackage=" + packageName, "-DliferayVersion=7.0");

		_testContains(
			mavenProjectDir, "package.json",
			"target/classes/META-INF/resources");

		_testNotContains(
			mavenProjectDir, "package.json",
			"build/resources/main/META-INF/resources");

		_testContains(
			mavenProjectDir, ".npmbundlerrc",
			"target/classes/META-INF/resources");

		if (Validator.isNotNull(System.getenv("JENKINS_HOME"))) {
			_addNpmrc(gradleProjectDir);
			_addNpmrc(mavenProjectDir);
			_configureExecuteNpmTask(gradleProjectDir);
			_configurePomNpmConfiguration(mavenProjectDir);
		}

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	private void _testBuildTemplateNpm71(
			String template, String name, String packageName, String className,
			String bootstrapRequire)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, name, "--liferayVersion", "7.1");

		String packagePath = packageName.replaceAll("\\.", "\\/");

		_testContains(
			gradleProjectDir, "build.gradle",
			"name: \"com.liferay.frontend.js.loader.modules.extender.api\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");

		_testContains(
			gradleProjectDir, "package.json",
			"build/resources/main/META-INF/resources");

		_testNotContains(
			gradleProjectDir, "package.json",
			"target/classes/META-INF/resources");

		_testNotContains(
			gradleProjectDir, ".npmbundlerrc",
			"target/classes/META-INF/resources");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, name, "com.test", "-DclassName=" + className,
			"-Dpackage=" + packageName, "-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_testContains(
			mavenProjectDir, "package.json",
			"target/classes/META-INF/resources");

		_testNotContains(
			mavenProjectDir, "package.json",
			"build/resources/main/META-INF/resources");

		_testContains(
			mavenProjectDir, ".npmbundlerrc",
			"target/classes/META-INF/resources");

		_testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/init.jsp",
			"<%@ page import=\"" + packageName + ".constants." + className +
				"WebKeys\" %>");
		_testContains(
			gradleProjectDir, "src/main/resources/META-INF/resources/view.jsp",
			"<aui:script require=\"<%= bootstrapRequire %>\">",
			bootstrapRequire);

		_testContains(
			gradleProjectDir,
			"src/main/java/" + packagePath + "/portlet/" + className +
				"Portlet.java",
			"import " + packageName + ".constants." + className + "WebKeys;");

		_testExists(
			gradleProjectDir,
			"src/main/java/" + packagePath + "/constants/" + className +
				"WebKeys.java");

		if (Validator.isNotNull(System.getenv("JENKINS_HOME"))) {
			_addNpmrc(gradleProjectDir);
			_addNpmrc(mavenProjectDir);
			_configureExecuteNpmTask(gradleProjectDir);
			_configurePomNpmConfiguration(mavenProjectDir);
		}

		_buildProjects(gradleProjectDir, mavenProjectDir);
	}

	private File _testBuildTemplatePortlet70(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "foo", "--liferayVersion", "7.0");

		for (String resourceFileName : resourceFileNames) {
			_testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		_testContains(
			gradleProjectDir, "bnd.bnd", "Export-Package: foo.constants");
		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0\"");
		_testContains(
			gradleProjectDir, "src/main/java/foo/constants/FooPortletKeys.java",
			"public class FooPortletKeys");
		_testContains(
			gradleProjectDir, "src/main/java/foo/portlet/FooPortlet.java",
			"javax.portlet.name=\" + FooPortletKeys.Foo",
			"public class FooPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "foo", "com.test", "-DclassName=Foo", "-Dpackage=foo",
			"-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortlet71(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "foo", "--liferayVersion", "7.1");

		for (String resourceFileName : resourceFileNames) {
			_testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		_testContains(
			gradleProjectDir, "bnd.bnd", "Export-Package: foo.constants");
		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		_testContains(
			gradleProjectDir, "src/main/java/foo/constants/FooPortletKeys.java",
			"public class FooPortletKeys");
		_testContains(
			gradleProjectDir, "src/main/java/foo/portlet/FooPortlet.java",
			"javax.portlet.name=\" + FooPortletKeys.Foo",
			"public class FooPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "foo", "com.test", "-DclassName=Foo", "-Dpackage=foo",
			"-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPackage70(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception, IOException {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "foo", "--package-name", "com.liferay.test");

		_testExists(gradleProjectDir, "bnd.bnd");

		for (String resourceFileName : resourceFileNames) {
			_testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"");
		_testContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/portlet/FooPortlet.java",
			"public class FooPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "foo", "com.test", "-DclassName=Foo",
			"-Dpackage=com.liferay.test");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPackage71(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception, IOException {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "foo", "--package-name", "com.liferay.test",
			"--liferayVersion", "7.1");

		_testExists(gradleProjectDir, "bnd.bnd");

		for (String resourceFileName : resourceFileNames) {
			_testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		_testContains(
			gradleProjectDir,
			"src/main/java/com/liferay/test/portlet/FooPortlet.java",
			"public class FooPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "foo", "com.test", "-DclassName=Foo",
			"-Dpackage=com.liferay.test", "-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPortletName70(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "portlet", "--liferayVersion", "7.0");

		_testExists(gradleProjectDir, "bnd.bnd");

		for (String resourceFileName : resourceFileNames) {
			_testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		_testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/PortletPortlet.java",
			"public class PortletPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "portlet", "com.test", "-DclassName=Portlet",
			"-Dpackage=portlet", "-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPortletName71(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "portlet", "--liferayVersion", "7.1");

		_testExists(gradleProjectDir, "bnd.bnd");

		for (String resourceFileName : resourceFileNames) {
			_testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		_testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/PortletPortlet.java",
			"public class PortletPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "portlet", "com.test", "-DclassName=Portlet",
			"-Dpackage=portlet", "-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPortletSuffix70(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "portlet-portlet", "--liferayVersion", "7.0");

		_testExists(gradleProjectDir, "bnd.bnd");

		for (String resourceFileName : resourceFileNames) {
			_testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"2.0.0");
		_testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/portlet/PortletPortlet.java",
			"public class PortletPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "portlet-portlet", "com.test", "-DclassName=Portlet",
			"-Dpackage=portlet.portlet", "-DliferayVersion=7.0");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		return gradleProjectDir;
	}

	private File _testBuildTemplatePortletWithPortletSuffix71(
			String template, String portletClassName,
			String... resourceFileNames)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(
			template, "portlet-portlet", "--liferayVersion", "7.1");

		_testExists(gradleProjectDir, "bnd.bnd");

		for (String resourceFileName : resourceFileNames) {
			_testExists(
				gradleProjectDir, "src/main/resources/" + resourceFileName);
		}

		_testContains(
			gradleProjectDir, "build.gradle",
			"apply plugin: \"com.liferay.plugin\"",
			_DEPENDENCY_PORTAL_KERNEL + ", version: \"3.0.0");
		_testContains(
			gradleProjectDir,
			"src/main/java/portlet/portlet/portlet/PortletPortlet.java",
			"public class PortletPortlet extends " + portletClassName + " {");

		File mavenProjectDir = _buildTemplateWithMaven(
			template, "portlet-portlet", "com.test", "-DclassName=Portlet",
			"-Dpackage=portlet.portlet", "-DliferayVersion=7.1");

		_testContains(
			mavenProjectDir, "bnd.bnd", "-contract: JavaPortlet,JavaServlet");

		_buildProjects(gradleProjectDir, mavenProjectDir);

		return gradleProjectDir;
	}

	private void _testBuildTemplateProjectWarInWorkspace(
			String template, String name, String warFileName)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(template, name);

		_testContains(
			gradleProjectDir, "build.gradle", "buildscript {",
			"apply plugin: \"war\"", "repositories {");

		File workspaceDir = _buildWorkspace();

		File warsDir = new File(workspaceDir, "wars");

		File workspaceProjectDir = _buildTemplateWithGradle(
			warsDir, template, name);

		_testNotContains(
			workspaceProjectDir, "build.gradle", "apply plugin: \"war\"");
		_testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");

		_executeGradle(gradleProjectDir, _GRADLE_TASK_PATH_BUILD);

		File gradleWarFile = _testExists(
			gradleProjectDir, "build/libs/" + warFileName + ".war");

		_executeGradle(workspaceDir, ":wars:" + name + ":build");

		File workspaceWarFile = _testExists(
			workspaceProjectDir, "build/libs/" + warFileName + ".war");

		_testWarsDiff(gradleWarFile, workspaceWarFile);
	}

	private void _testBuildTemplateServiceBuilder(
			File gradleProjectDir, File mavenProjectDir, final File rootProject,
			String name, String packageName, final String projectPath)
		throws Exception {

		String apiProjectName = name + "-api";
		final String serviceProjectName = name + "-service";

		boolean workspace = WorkspaceUtil.isWorkspace(gradleProjectDir);

		if (!workspace) {
			_testContains(
				gradleProjectDir, "settings.gradle",
				"include \"" + apiProjectName + "\", \"" + serviceProjectName +
					"\"");
		}

		_testContains(
			gradleProjectDir, apiProjectName + "/bnd.bnd", "Export-Package:\\",
			packageName + ".exception,\\", packageName + ".model,\\",
			packageName + ".service,\\", packageName + ".service.persistence");

		_testContains(
			gradleProjectDir, serviceProjectName + "/bnd.bnd",
			"Liferay-Service: true");

		if (!workspace) {
			_testContains(
				gradleProjectDir, serviceProjectName + "/build.gradle",
				"compileOnly project(\":" + apiProjectName + "\")");
		}

		_testChangePortletModelHintsXml(
			gradleProjectDir, serviceProjectName,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					_executeGradle(
						rootProject,
						projectPath + ":" + serviceProjectName +
							_GRADLE_TASK_PATH_BUILD_SERVICE);

					return null;
				}

			});

		_executeGradle(
			rootProject,
			projectPath + ":" + serviceProjectName + _GRADLE_TASK_PATH_BUILD);

		File gradleApiBundleFile = _testExists(
			gradleProjectDir,
			apiProjectName + "/build/libs/" + packageName + ".api-1.0.0.jar");

		File gradleServiceBundleFile = _testExists(
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

	private void _testBuildTemplateWithWorkspace(
			String template, String name, String jarFilePath)
		throws Exception {

		File gradleProjectDir = _buildTemplateWithGradle(template, name);

		_testContains(
			gradleProjectDir, "build.gradle", true, ".*^buildscript \\{.*",
			".*^repositories \\{.*");

		File workspaceDir = _buildWorkspace();

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir = _buildTemplateWithGradle(
			modulesDir, template, name);

		_testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");

		if (Validator.isNotNull(_BUILD_PROJECTS) &&
			_BUILD_PROJECTS.equals("true")) {

			_executeGradle(gradleProjectDir, _GRADLE_TASK_PATH_BUILD);

			_testExists(gradleProjectDir, jarFilePath);

			_executeGradle(workspaceDir, ":modules:" + name + ":build");

			_testExists(workspaceProjectDir, jarFilePath);
		}
	}

	private void _testPomXmlContainsDependency(
			Path pomXmlPath, String groupId, String artifactId, String version)
		throws Exception {

		DocumentBuilderFactory documentBuilderFactory =
			DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		Assert.assertTrue("Missing " + pomXmlPath, Files.exists(pomXmlPath));

		Document document = documentBuilder.parse(pomXmlPath.toFile());

		Element projectElement = document.getDocumentElement();

		Element dependenciesElement = XMLTestUtil.getChildElement(
			projectElement, "dependencies");

		List<Element> dependencyElements;

		if (dependenciesElement != null) {
			dependencyElements = XMLTestUtil.getChildElements(
				dependenciesElement);
		}
		else {
			dependencyElements = Collections.emptyList();
		}

		boolean foundDependency = false;

		for (Element dependencyElement : dependencyElements) {
			String dependencyElementString = XMLTestUtil.toString(
				dependencyElement);

			String artifactIdString = String.format(
				"<artifactId>%s</artifactId>", artifactId);
			String groupIdString = String.format(
				"<groupId>%s</groupId>", groupId);
			String versionString = String.format(
				"<version>%s</version>", version);

			if (dependencyElementString.contains(artifactIdString) &&
				dependencyElementString.contains(groupIdString) &&
				dependencyElementString.contains(versionString)) {

				foundDependency = true;

				break;
			}
		}

		String missingDependencyString = String.format(
			"Missing dependency %s:%s:%s in %s", groupId, artifactId, version,
			pomXmlPath);

		Assert.assertTrue(missingDependencyString, foundDependency);
	}

	private static final String _BUILD_PROJECTS = System.getProperty(
		"project.templates.test.builds");

	private static final String _BUNDLES_DIFF_IGNORES = StringTestUtil.merge(
		Arrays.asList(
			"*.js.map", "*pom.properties", "*pom.xml", "*package.json",
			"Archiver-Version", "Build-Jdk", "Built-By", "Javac-Debug",
			"Javac-Deprecation", "Javac-Encoding"),
		',');

	private static final String _DEPENDENCY_OSGI_CORE =
		"compileOnly group: \"org.osgi\", name: \"org.osgi.core\"";

	private static final String _DEPENDENCY_PORTAL_KERNEL =
		"compileOnly group: \"com.liferay.portal\", name: " +
			"\"com.liferay.portal.kernel\"";

	private static final String _FREEMARKER_PORTLET_VIEW_FTL_PREFIX =
		"<#include \"init.ftl\">";

	private static final String _GRADLE_TASK_PATH_BUILD = ":build";

	private static final String _GRADLE_TASK_PATH_BUILD_SERVICE =
		":buildService";

	private static final String _GRADLE_TASK_PATH_DEPLOY = ":deploy";

	private static final String[] _GRADLE_WRAPPER_FILE_NAMES = {
		"gradlew", "gradlew.bat", "gradle/wrapper/gradle-wrapper.jar",
		"gradle/wrapper/gradle-wrapper.properties"
	};

	private static final String _MAVEN_GOAL_BUILD_SERVICE =
		"service-builder:build";

	private static final String _MAVEN_GOAL_PACKAGE = "package";

	private static final String[] _MAVEN_WRAPPER_FILE_NAMES = {
		"mvnw", "mvnw.cmd", ".mvn/wrapper/maven-wrapper.jar",
		".mvn/wrapper/maven-wrapper.properties"
	};

	private static final String _NODEJS_NPM_CI_REGISTRY = System.getProperty(
		"nodejs.npm.ci.registry");

	private static final String _NODEJS_NPM_CI_SASS_BINARY_SITE =
		System.getProperty("nodejs.npm.ci.sass.binary.site");

	private static final String _OUTPUT_FILENAME_GLOB_REGEX = "*.{jar,war}";

	private static final String _REPOSITORY_CDN_URL =
		"https://repository-cdn.liferay.com/nexus/content/groups/public";

	private static final String[] _SPRING_MVC_PORTLET_JAR_NAMES = {
		"aop", "beans", "context", "core", "expression", "web", "webmvc",
		"webmvc-portlet"
	};

	private static final String _SPRING_MVC_PORTLET_VERSION = "4.1.9.RELEASE";

	private static final boolean _TEST_DEBUG_BUNDLE_DIFFS = Boolean.getBoolean(
		"test.debug.bundle.diffs");

	private static URI _gradleDistribution;
	private static final Pattern _gradlePluginVersionPattern = Pattern.compile(
		".*com\\.liferay\\.gradle\\.plugins:([0-9]+\\.[0-9]+\\.[0-9]+).*",
		Pattern.DOTALL | Pattern.MULTILINE);
	private static XPathExpression _pomXmlNpmInstallXPathExpression;
	private static final Pattern _serviceBuilderVersionPattern =
		Pattern.compile(
			".*service\\.builder:([0-9]+\\.[0-9]+\\.[0-9]+).*",
			Pattern.DOTALL | Pattern.MULTILINE);

}