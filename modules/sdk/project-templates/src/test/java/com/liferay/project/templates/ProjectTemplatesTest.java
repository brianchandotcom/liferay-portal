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

import aQute.lib.io.IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.Writer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Andrea Di Giorgi
 */
public class ProjectTemplatesTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		File testLibFolder = new File("test-lib");

		File[] archetypesJars = testLibFolder.listFiles(
			new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name.startsWith("com.liferay.project.templates");
				}

			});

		for (File archetypeJar : archetypesJars) {
			IO.copy(archetypeJar, new File("bin/" + archetypeJar.getName()));
			IO.copy(
				archetypeJar, new File("classes/" + archetypeJar.getName()));
		}
	}

	@Before
	public void setUp() throws Exception {
		IO.delete(_testDir);
		Assert.assertFalse(_testDir.exists());
	}

	@After
	public void tearDown() {
		if (_testDir.exists()) {
			IO.delete(_testDir);
			Assert.assertFalse(_testDir.exists());
		}
	}

	@Test
	public void testCreateActivator() throws Exception {
		String[] args = {
			"--destination", _testDir.getPath(), "--name", "bar-activator",
			"--template", "activator"
		};

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "bar-activator");

		Assert.assertTrue(projectDir.exists());

		File bndFile = new File(projectDir, "bnd.bnd");

		Assert.assertTrue(bndFile.exists());

		File gradlewFile = new File(projectDir, "gradlew");

		Assert.assertTrue(gradlewFile.exists());

		File activatorFile = new File(
			projectDir, "src/main/java/bar/activator/BarActivator.java");

		Assert.assertTrue(activatorFile.exists());

		_contains(
			activatorFile,
			".*^public class BarActivator implements BundleActivator.*$");

		BuildTask buildtask = _executeGradleRunner(projectDir, "build");

		_verifyGradleRunnerOutput(buildtask);

		_verifyBuildOutput(projectDir, "bar.activator-1.0.0.jar");
	}

	@Test
	public void testCreateGradleFragment() throws Exception {
		String[] args = {
			"--destination", _testDir.getPath(), "--host-bundle-symbolic-name",
			"com.liferay.login.web", "--host-bundle-version", "1.0.0",
			"--template", "fragment", "--name", "loginHook"
		};

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "loginhook");

		Assert.assertTrue(projectDir.exists());

		File bndfile = new File(projectDir, "bnd.bnd");

		_contains(
			bndfile,
			new String[] {
				".*^Bundle-SymbolicName: loginhook.*$",
				".*^Fragment-Host: .*.login.web;bundle-version=\"1.0.0\".*$"
			});

		File buildfile = new File(projectDir, "build.gradle");

		_contains(buildfile, ".*^apply plugin: \"com.liferay.plugin\".*");

		BuildTask buildtask = _executeGradleRunner(projectDir, "build");

		_verifyGradleRunnerOutput(buildtask);

		_verifyBuildOutput(projectDir, "loginhook-1.0.0.jar");
	}

	@Test
	public void testCreateGradleMVCPortletProject() throws Exception {
		String[] args = {
			"--destination", _testDir.getPath(), "--template", "mvcportlet",
			"--name", "foo"
		};

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "foo");

		Assert.assertTrue(projectDir.exists());

		_checkFileExists(projectDir + "/bnd.bnd");

		_checkFileExists(projectDir + "/gradlew");

		_checkFileExists(projectDir + "/gradlew.bat");

		File classfile = new File(
			projectDir, "/src/main/java/foo/portlet/FooPortlet.java");

		_contains(
			classfile, ".*^public class FooPortlet extends MVCPortlet.*$");
		File buildfile = new File(projectDir, "build.gradle");

		_contains(buildfile, ".*^apply plugin: \"com.liferay.plugin\".*");

		_checkFileExists(
			projectDir + "/src/main/resources/META-INF/resources/view.jsp");

		_checkFileExists(
			projectDir + "/src/main/resources/META-INF/resources/init.jsp");

		BuildTask buildtask = _executeGradleRunner(projectDir, "build");

		_verifyGradleRunnerOutput(buildtask);

		_verifyBuildOutput(projectDir, "foo-1.0.0.jar");
	}

	@Test
	public void testCreateGradleMVCPortletProjectWithPackage()
		throws Exception {

		String[] args = {
			"--destination", _testDir.getPath(), "--template", "mvcportlet",
			"--package-name", "com.liferay.test", "--name", "foo"
		};

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "foo");

		Assert.assertTrue(projectDir.exists());

		_checkFileExists(projectDir + "/bnd.bnd");

		File classfile = new File(
			projectDir,
			"/src/main/java/com/liferay/test/portlet/FooPortlet.java");

		_contains(
			classfile, ".*^public class FooPortlet extends MVCPortlet.*$");

		File buildfile = new File(projectDir, "build.gradle");

		_contains(buildfile, ".*^apply plugin: \"com.liferay.plugin\".*");

		_checkFileExists(
			projectDir + "/src/main/resources/META-INF/resources/view.jsp");

		_checkFileExists(
			projectDir + "/src/main/resources/META-INF/resources/init.jsp");

		BuildTask buildtask = _executeGradleRunner(projectDir, "build");

		_verifyGradleRunnerOutput(buildtask);

		_verifyBuildOutput(projectDir, "com.liferay.test-1.0.0.jar");
	}

	@Test
	public void testCreateGradleMVCPortletProjectWithPortletSuffix()
		throws Exception {

		String[] args = {
			"--destination", _testDir.getPath(), "--template", "mvcportlet",
			"--name", "portlet-portlet"
		};

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "portlet-portlet");

		Assert.assertTrue(projectDir.exists());

		_checkFileExists(projectDir + "/bnd.bnd");

		File classfile = new File(
			projectDir,
			"/src/main/java/portlet/portlet/portlet/PortletPortlet.java");

		_contains(
			classfile, ".*^public class PortletPortlet extends MVCPortlet.*$");

		File buildfile = new File(projectDir, "build.gradle");

		_contains(buildfile, ".*^apply plugin: \"com.liferay.plugin\".*");

		_checkFileExists(
			projectDir + "/src/main/resources/META-INF/resources/view.jsp");

		_checkFileExists(
			projectDir + "/src/main/resources/META-INF/resources/init.jsp");

		BuildTask buildtask = _executeGradleRunner(projectDir, "build");

		_verifyGradleRunnerOutput(buildtask);

		_verifyBuildOutput(projectDir, "portlet.portlet-1.0.0.jar");
	}

	@Test
	public void testCreateGradlePortletProject() throws Exception {
		String[] args = {
			"--destination", _testDir.getPath(), "--template", "portlet",
			"--class-name", "Foo", "--name", "gradle.test"
		};

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "gradle.test");

		Assert.assertTrue(projectDir.exists());

		_checkFileExists(projectDir + "/build.gradle");

		File classfile = new File(
			projectDir, "/src/main/java/gradle/test/portlet/FooPortlet.java");

		_contains(
			classfile,
			new String[] {
				"^package gradle.test.portlet;.*",
				".*javax.portlet.display-name=gradle.test.*",
				".*^public class FooPortlet .*",
				".*printWriter.print\\(\\\"gradle.test Portlet.*"
			});

		BuildTask buildtask = _executeGradleRunner(projectDir, "build");

		_verifyGradleRunnerOutput(buildtask);

		_verifyBuildOutput(projectDir, "gradle.test-1.0.0.jar");
	}

	@Test
	public void testCreateGradleService() throws Exception {
		String[] args = {
			"--destination", _testDir.getPath(), "--template", "service",
			"--service", "com.liferay.portal.kernel.events.LifecycleAction",
			"--class-name", "FooAction", "--name", "servicepreaction"
		};

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "servicepreaction");

		Assert.assertTrue(projectDir.exists());

		_checkFileExists(projectDir + "/build.gradle");

		File file = new File(
			projectDir + "/src/main/java/servicepreaction/FooAction.java");

		_contains(
			file,
			new String[] {
				"^package servicepreaction;.*",
				".*^import .*kernel.events.LifecycleAction;$.*",
				".*service = LifecycleAction.class.*",
				".*^public class FooAction implements LifecycleAction \\{.*"
			});

		List<String> lines = new ArrayList<>();
		String line = null;

		BufferedReader reader = new BufferedReader(new FileReader(file));

		while ((line = reader.readLine()) != null) {
			lines.add(line);

			if (line.startsWith("import")) {
				lines.add(
					"import com.liferay.portal.kernel.events.LifecycleEvent;");
				lines.add(
					"import com.liferay.portal.kernel.events.ActionException;");
			}

			if (line.startsWith("public class FooAction")) {
				lines.add(_EVENT_METHOD);
			}
		}

		reader.close();

		try (Writer writer = new FileWriter(file)) {
			for (String string : lines) {
				writer.write(string + "\n");
			}
		}

		BuildTask buildtask = _executeGradleRunner(projectDir, "build");

		_verifyGradleRunnerOutput(buildtask);

		_verifyBuildOutput(projectDir, "servicepreaction-1.0.0.jar");
	}

	@Test
	public void testCreateGradleServiceBuilderDashes() throws Exception {
		String[] args = {
			"--destination", _testDir.getPath(), "--template", "servicebuilder",
			"--package-name", "com.liferay.backend.integration", "--name",
			"backend-integration"
		};

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "backend-integration");

		Assert.assertTrue(projectDir.exists());

		File settingsfile = new File(projectDir, "settings.gradle");

		_contains(
			settingsfile,
			"include \"backend-integration-api\", " +
				"\"backend-integration-service\"");

		File apibndfile = new File(
			projectDir, "/backend-integration-api/bnd.bnd");

		_contains(
			apibndfile,
			new String[] {
				".*Export-Package:\\\\.*",
				".*com.liferay.backend.integration.exception,\\\\.*",
				".*com.liferay.backend.integration.model,\\\\.*",
				".*com.liferay.backend.integration.service,\\\\.*",
				".*com.liferay.backend.integration.service.persistence.*"
			});

		File servicebndfile = new File(
			projectDir, "/backend-integration-service/bnd.bnd");

		_contains(servicebndfile, ".*Liferay-Service: true.*");

		BuildTask buildServiceTask = _executeGradleRunner(
			projectDir, ":backend-integration-service:buildService");

		_verifyGradleRunnerOutput(buildServiceTask);

		BuildTask buildtask = _executeGradleRunner(projectDir, "build");

		_verifyGradleRunnerOutput(buildtask);

		File apiDir = new File(projectDir + "/backend-integration-api");

		_verifyBuildOutput(
			apiDir, "com.liferay.backend.integration.api-1.0.0.jar");

		File serviceDir = new File(projectDir + "/backend-integration-service");

		_verifyBuildOutput(
			serviceDir, "com.liferay.backend.integration.service-1.0.0.jar");
	}

	@Test
	public void testCreateGradleServiceBuilderDefault() throws Exception {
		String[] args = {
			"--destination", _testDir.getPath(), "--template", "servicebuilder",
			"--package-name", "com.liferay.docs.guestbook", "--name",
			"guestbook"
		};

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "guestbook");

		Assert.assertTrue(projectDir.exists());

		File settingsfile = new File(projectDir, "settings.gradle");

		_contains(
			settingsfile, "include \"guestbook-api\", \"guestbook-service\"");

		File apibndfile = new File(projectDir, "guestbook-api/bnd.bnd");

		_contains(
			apibndfile,
			new String[] {
				".*Export-Package:\\\\.*",
				".*com.liferay.docs.guestbook.exception,\\\\.*",
				".*com.liferay.docs.guestbook.model,\\\\.*",
				".*com.liferay.docs.guestbook.service,\\\\.*",
				".*com.liferay.docs.guestbook.service.persistence.*"
			});

		File servicebndfile = new File(projectDir, "guestbook-service/bnd.bnd");

		_contains(servicebndfile, ".*Liferay-Service: true.*");

		File servicebuildfile = new File(
			projectDir, "guestbook-service/build.gradle");

		_contains(
			servicebuildfile,
			".*compileOnly project\\(\":guestbook-api\"\\).*");

		BuildTask buildService = _executeGradleRunner(
			projectDir, ":guestbook-service:buildService");

		_verifyGradleRunnerOutput(buildService);

		BuildTask buildtask = _executeGradleRunner(projectDir, "build");

		_verifyGradleRunnerOutput(buildtask);

		File guestbookApi = new File(projectDir + "/guestbook-api");

		_verifyBuildOutput(
			guestbookApi, "com.liferay.docs.guestbook.api-1.0.0.jar");

		File guestbookService = new File(projectDir + "/guestbook-service");

		_verifyBuildOutput(
			guestbookService, "com.liferay.docs.guestbook.service-1.0.0.jar");
	}

	@Test
	public void testCreateGradleServiceWrapper() throws Exception {
		String[] args = {
			"--destination", _testDir.getPath(), "--template", "servicewrapper",
			"--service",
			"com.liferay.portal.kernel.service.UserLocalServiceWrapper",
			"--name", "serviceoverride"
		};

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "serviceoverride");

		Assert.assertTrue(projectDir.exists());

		_checkFileExists(projectDir + "/build.gradle");

		File classfile = new File(
			projectDir, "/src/main/java/serviceoverride/Serviceoverride.java");

		_contains(
			classfile,
			new String[] {
				"^package serviceoverride;.*",
				".*^import .*.kernel.service.UserLocalServiceWrapper;$.*",
				".*service = ServiceWrapper.class.*",
				".*class Serviceoverride extends UserLocalServiceWrapper.*",
				".*public Serviceoverride\\(\\) \\{.*"
			});

		BuildTask buildtask = _executeGradleRunner(projectDir, "build");

		_verifyGradleRunnerOutput(buildtask);

		_verifyBuildOutput(projectDir, "serviceoverride-1.0.0.jar");
	}

	@Test
	public void testCreateGradleSymbolicName() throws Exception {
		String[] args = {
			"--destination", _testDir.getPath(), "--package-name", "foo.bar",
			"--name", "barfoo"
		};

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "barfoo");

		Assert.assertTrue(projectDir.exists());

		_checkFileExists(projectDir + "/build.gradle");

		File bndfile = new File(projectDir, "bnd.bnd");

		_contains(bndfile, ".*Bundle-SymbolicName: foo.bar.*");

		BuildTask buildtask = _executeGradleRunner(projectDir, "build");

		_verifyGradleRunnerOutput(buildtask);

		_verifyBuildOutput(projectDir, "foo.bar-1.0.0.jar");
	}

	@Test
	public void testCreateMVCPortletProject() throws Exception {
		String[] args = {
			"--destination", _testDir.getPath(), "--name", "foo", "--template",
			"mvcportlet"
		};

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "foo");

		Assert.assertTrue(projectDir.exists());

		File bndFile = new File(projectDir, "bnd.bnd");

		Assert.assertTrue(bndFile.exists());

		File gradlewFile = new File(projectDir, "gradlew");

		Assert.assertTrue(gradlewFile.exists());

		File portletFile = new File(
			projectDir, "src/main/java/foo/portlet/FooPortlet.java");

		Assert.assertTrue(portletFile.exists());

		_contains(
			portletFile, ".*^public class FooPortlet extends MVCPortlet.*$");

		File buildGradleFile = new File(projectDir, "build.gradle");

		Assert.assertTrue(buildGradleFile.exists());

		_contains(buildGradleFile, ".*^apply plugin: \"com.liferay.plugin\".*");

		File viewJspFile = new File(
			projectDir, "/src/main/resources/META-INF/resources/view.jsp");

		Assert.assertTrue(viewJspFile.exists());
	}

	@Test
	public void testCreateOnExistFolder() throws Exception {
		String[] args = {
			"--destination", _testDir.getPath(), "--template", "activator",
			"--name", "exist"
		};

		File existFile = IO.getFile("generated/exist/file.txt");

		if (!existFile.exists()) {
			IO.getFile("generated/exist").mkdirs();
			existFile.createNewFile();
			Assert.assertTrue(existFile.exists());
		}

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "exist");

		_checkFileDoesNotExists(projectDir + "bnd.bnd");
	}

	@Test
	public void testCreateProjectAllDefaults() throws Exception {
		String[] args = {
			"--destination", _testDir.getPath(), "--name", "hello-world-portlet"
		};

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "hello-world-portlet");

		Assert.assertTrue(projectDir.exists());

		_checkFileExists(projectDir + "/bnd.bnd");

		File portletfile = new File(
			projectDir,
			"/src/main/java/hello/world/portlet/portlet/" +
				"HelloWorldPortlet.java");

		_contains(
			portletfile,
			".*^public class HelloWorldPortlet extends MVCPortlet.*$");

		File gradleBuildFile = new File(projectDir + "/build.gradle");

		_contains(gradleBuildFile, ".*^apply plugin: \"com.liferay.plugin\".*");

		_checkFileExists(
			projectDir + "/src/main/resources/META-INF/resources/view.jsp");

		_checkFileExists(
			projectDir + "/src/main/resources/META-INF/resources/init.jsp");

		BuildTask buildtask = _executeGradleRunner(projectDir, "build");

		_verifyGradleRunnerOutput(buildtask);

		_verifyBuildOutput(projectDir, "hello.world.portlet-1.0.0.jar");
	}

	@Test
	public void testCreateProjectWithRefresh() throws Exception {
		String[] args = {
			"--destination", _testDir.getPath(), "--name", "hello-world-refresh"
		};

		ProjectTemplates.main(args);

		File projectDir = new File(_testDir, "hello-world-refresh");

		Assert.assertTrue(projectDir.exists());

		_checkFileExists(projectDir + "/bnd.bnd");

		File portletFile = new File(
			projectDir + "/src/main/java/hello/world/refresh/portlet/" +
				"HelloWorldRefreshPortlet.java");

		_contains(
			portletFile,
			".*class HelloWorldRefreshPortlet extends MVCPortlet.*$");

		File gradleBuildFile = new File(projectDir + "/build.gradle");

		_contains(gradleBuildFile, ".*^apply plugin: \"com.liferay.plugin\".*");

		_checkFileExists(
			projectDir + "/src/main/resources/META-INF/resources/view.jsp");

		_checkFileExists(
			projectDir + "/src/main/resources/META-INF/resources/init.jsp");

		BuildTask buildtask = _executeGradleRunner(projectDir, "build");

		_verifyGradleRunnerOutput(buildtask);

		_verifyBuildOutput(projectDir, "hello.world.refresh-1.0.0.jar");
	}

	@Test
	public void testListTemplates() throws Exception {
		String[] templates = ProjectTemplates.getTemplates();

		Assert.assertNotNull(templates);
		Assert.assertTrue(templates.length == 18);
	}

	private static BuildTask _executeGradleRunner(
		File projectDir, String... taskPath) {

		GradleRunner runner = GradleRunner.create();

		runner = runner.withProjectDir(projectDir);
		runner = runner.withArguments(taskPath);

		BuildResult buildResult = runner.build();

		BuildTask buildtask = null;

		for (BuildTask task : buildResult.getTasks()) {
			if (task.getPath().endsWith(taskPath[taskPath.length - 1])) {
				buildtask = task;
				break;
			}
		}

		return buildtask;
	}

	private static void _verifyBuildOutput(File projectDir, String fileName) {
		File buildOutput = new File(projectDir, "build/libs/" + fileName);

		Assert.assertTrue(buildOutput.exists());
	}

	private static void _verifyGradleRunnerOutput(BuildTask buildtask) {
		Assert.assertNotNull(buildtask);

		Assert.assertEquals(buildtask.getOutcome(), TaskOutcome.SUCCESS);
	}

	private File _checkFileDoesNotExists(String path) {
		File file = IO.getFile(path);

		Assert.assertFalse(file.exists());

		return file;
	}

	private File _checkFileExists(String path) {
		File file = IO.getFile(path);

		Assert.assertTrue(file.exists());

		return file;
	}

	private void _contains(File file, String pattern) throws Exception {
		String content = new String(IO.read(file));

		_contains(content, pattern);
	}

	private void _contains(File file, String[] patterns) throws Exception {
		String content = new String(IO.read(file));

		for (String pattern : patterns) {
			_contains(content, pattern);
		}
	}

	private void _contains(String content, String pattern) throws Exception {
		Matcher matcher = Pattern.compile(
			pattern, Pattern.MULTILINE | Pattern.DOTALL).matcher(content);

		Assert.assertTrue(matcher.matches());
	}

	private static final String _EVENT_METHOD =
		"@Override\n" +
		"public void processLifecycleEvent(LifecycleEvent lifecycleEvent)\n" +
		"throws ActionException {\n" +
		"System.out.println(\"login.event.pre=\" + lifecycleEvent);\n" +
		"}\n";

	private static final File _testDir = IO.getFile("build/test");

}