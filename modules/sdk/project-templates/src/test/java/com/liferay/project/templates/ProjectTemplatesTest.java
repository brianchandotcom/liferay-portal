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

import com.liferay.project.templates.internal.util.Validator;
import com.liferay.project.templates.util.FileTestUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Writer;

import java.net.URI;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

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
		Properties properties = FileTestUtil.readProperties(
			"gradle-wrapper/gradle/wrapper/gradle-wrapper.properties");

		_gradleDistribution = URI.create(
			properties.getProperty("distributionUrl"));
	}

	@Test
	public void testBuildTemplate() throws Exception {
		File projectDir = _buildTemplate(null, "hello-world-portlet");

		_testExists(projectDir, "bnd.bnd");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir,
			"src/main/java/hello/world/portlet/portlet/HelloWorldPortlet.java",
			"public class HelloWorldPortlet extends MVCPortlet {");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/hello.world.portlet-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateActivator() throws Exception {
		File projectDir = _buildTemplate("activator", "bar-activator");

		_testExists(projectDir, "bnd.bnd");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir, "src/main/java/bar/activator/BarActivator.java",
			"public class BarActivator implements BundleActivator {");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/bar.activator-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateFragment() throws Exception {
		File projectDir = _buildTemplate(
			"fragment", "loginHook", "--host-bundle-symbolic-name",
			"com.liferay.login.web", "--host-bundle-version", "1.0.0");

		_testContains(
			projectDir, "bnd.bnd", "Bundle-SymbolicName: loginhook",
			"Fragment-Host: com.liferay.login.web;bundle-version=\"1.0.0\"");
		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/loginhook-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortlet() throws Exception {
		File projectDir = _buildTemplate("mvcportlet", "foo");

		_testExists(projectDir, "bnd.bnd");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir, "src/main/java/foo/portlet/FooPortlet.java",
			"public class FooPortlet extends MVCPortlet {");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/foo-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPackage() throws Exception {
		File projectDir = _buildTemplate(
			"mvcportlet", "foo", "--package-name", "com.liferay.test");

		_testExists(projectDir, "bnd.bnd");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir,
			"src/main/java/com/liferay/test/portlet/FooPortlet.java",
			"public class FooPortlet extends MVCPortlet {");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/com.liferay.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateMVCPortletWithPortletSuffix()
		throws Exception {

		File projectDir = _buildTemplate("mvcportlet", "portlet-portlet");

		_testExists(projectDir, "bnd.bnd");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir,
			"src/main/java/portlet/portlet/portlet/PortletPortlet.java",
			"public class PortletPortlet extends MVCPortlet {");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/portlet.portlet-1.0.0.jar");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testBuildTemplateOnExistingDirectory() throws Exception {
		File projectDir = new File(temporaryFolder.getRoot(), "bar-activator");

		Assert.assertTrue(projectDir.mkdirs());

		File file = new File(projectDir, "foo.txt");

		Assert.assertTrue(file.createNewFile());

		_buildTemplate("activator", projectDir.getName());
	}

	@Test
	public void testBuildTemplatePortlet() throws Exception {
		File projectDir = _buildTemplate(
			"portlet", "gradle.test", "--class-name", "Foo");

		_testExists(projectDir, "bnd.bnd");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir, "src/main/java/gradle/test/portlet/FooPortlet.java",
			"package gradle.test.portlet;",
			"javax.portlet.display-name=gradle.test",
			"public class FooPortlet extends GenericPortlet {",
			"printWriter.print(\"gradle.test Portlet");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/gradle.test-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateService() throws Exception {
		File projectDir = _buildTemplate(
			"service", "servicepreaction", "--class-name", "FooAction",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction");

		_testExists(projectDir, "bnd.bnd");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");

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

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/servicepreaction-1.0.0.jar");
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
		File projectDir = _buildTemplate(
			"servicewrapper", "serviceoverride", "--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper");

		_testExists(projectDir, "bnd.bnd");

		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");
		_testContains(
			projectDir, "src/main/java/serviceoverride/Serviceoverride.java",
			"package serviceoverride;",
			"import com.liferay.portal.kernel.service.UserLocalServiceWrapper;",
			"service = ServiceWrapper.class",
			"public class Serviceoverride extends UserLocalServiceWrapper {",
			"public Serviceoverride() {");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/serviceoverride-1.0.0.jar");
	}

	@Test
	public void testBuildTemplateWithPackageName() throws Exception {
		File projectDir = _buildTemplate(
			null, "barfoo", "--package-name", "foo.bar");

		_testExists(
			projectDir, "src/main/resources/META-INF/resources/init.jsp");
		_testExists(
			projectDir, "src/main/resources/META-INF/resources/view.jsp");

		_testContains(projectDir, "bnd.bnd", "Bundle-SymbolicName: foo.bar");
		_testContains(
			projectDir, "build.gradle", "apply plugin: \"com.liferay.plugin\"");

		_executeGradle(projectDir, _TASK_PATH_BUILD);

		_testExists(projectDir, "build/libs/foo.bar-1.0.0.jar");
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

	private File _buildTemplate(String template, String name, String... args)
		throws Exception {

		File destinationDir = temporaryFolder.getRoot();

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
		_testExists(projectDir, "gradlew");
		_testExists(projectDir, "gradlew.bat");
		_testExists(projectDir, "gradle/wrapper/gradle-wrapper.jar");
		_testExists(projectDir, "gradle/wrapper/gradle-wrapper.properties");
		_testNotExists(projectDir, "pom.xml");

		return projectDir;
	}

	private void _executeGradle(
			File projectDir, String taskPath, String... testTaskPaths)
		throws IOException {

		GradleRunner gradleRunner = GradleRunner.create();

		gradleRunner.withArguments(taskPath);
		gradleRunner.withGradleDistribution(_gradleDistribution);
		gradleRunner.withProjectDir(projectDir);

		BuildResult buildResult = gradleRunner.build();

		if (testTaskPaths.length == 0) {
			testTaskPaths = new String[] {taskPath};
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

	private void _testBuildTemplateServiceBuilder(
			String name, String packageName)
		throws Exception {

		File projectDir = _buildTemplate(
			"servicebuilder", name, "--package-name", packageName);

		String apiProjectName = name + "-api";
		String serviceProjectName = name + "-service";

		_testContains(
			projectDir, "settings.gradle",
			"include \"" + apiProjectName + "\", \"" + serviceProjectName +
				"\"");
		_testContains(
			projectDir, apiProjectName + "/bnd.bnd", "Export-Package:\\",
			packageName + ".exception,\\", packageName + ".model,\\",
			packageName + ".service,\\", packageName + ".service.persistence");
		_testContains(
			projectDir, serviceProjectName + "/bnd.bnd",
			"Liferay-Service: true");
		_testContains(
			projectDir, serviceProjectName + "/build.gradle",
			"compileOnly project(\":" + apiProjectName + "\")");

		_executeGradle(projectDir, ":" + serviceProjectName + ":buildService");

		_executeGradle(
			projectDir, "build", ":" + apiProjectName + ":build",
			":" + serviceProjectName + ":build");

		_testExists(
			projectDir,
			apiProjectName + "/build/libs/" + packageName + ".api-1.0.0.jar");
		_testExists(
			projectDir,
			serviceProjectName + "/build/libs/" + packageName +
				".service-1.0.0.jar");
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

	private void _write(Writer writer, String... lines) throws IOException {
		for (String line : lines) {
			writer.write(line);
			writer.write(System.lineSeparator());
		}
	}

	private static final String _TASK_PATH_BUILD = ":build";

	private static URI _gradleDistribution;

}