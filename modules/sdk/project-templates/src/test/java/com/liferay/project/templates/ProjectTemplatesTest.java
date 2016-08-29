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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import aQute.lib.io.IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.io.Writer;

import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
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
		assertFalse(_testDir.exists());
	}

	@After
	public void tearDown() {
		if (_testDir.exists()) {
			IO.delete(_testDir);
			assertFalse(_testDir.exists());
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

		assertTrue(projectDir.exists());

		_testTemplateFiles(projectDir.toPath());

		File bndFile = new File(projectDir, "bnd.bnd");

		assertTrue(bndFile.exists());

		File gradlewFile = new File(projectDir, "gradlew");

		assertTrue(gradlewFile.exists());

		File activatorFile = new File(
			projectDir, "src/main/java/bar/activator/BarActivator.java");

		assertTrue(activatorFile.exists());

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

		assertTrue(projectDir.exists());

		_testTemplateFiles(projectDir.toPath());

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

		_testTemplateFiles(projectDir.toPath());

		assertTrue(projectDir.exists());

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

		assertTrue(projectDir.exists());

		_testTemplateFiles(projectDir.toPath());

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

		assertTrue(projectDir.exists());

		_testTemplateFiles(projectDir.toPath());

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

		assertTrue(projectDir.exists());

		_testTemplateFiles(projectDir.toPath());

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

		assertTrue(projectDir.exists());

		_testTemplateFiles(projectDir.toPath());

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

		assertTrue(projectDir.exists());

		_testTemplateFiles(projectDir.toPath());

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

		assertTrue(projectDir.exists());

		_testTemplateFiles(projectDir.toPath());

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

		assertTrue(projectDir.exists());

		_testTemplateFiles(projectDir.toPath());

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

		assertTrue(projectDir.exists());

		_testTemplateFiles(projectDir.toPath());

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

		assertTrue(projectDir.exists());

		_testTemplateFiles(projectDir.toPath());

		File bndFile = new File(projectDir, "bnd.bnd");

		assertTrue(bndFile.exists());

		File gradlewFile = new File(projectDir, "gradlew");

		assertTrue(gradlewFile.exists());

		File portletFile = new File(
			projectDir, "src/main/java/foo/portlet/FooPortlet.java");

		assertTrue(portletFile.exists());

		_contains(
			portletFile, ".*^public class FooPortlet extends MVCPortlet.*$");

		File buildGradleFile = new File(projectDir, "build.gradle");

		assertTrue(buildGradleFile.exists());

		_contains(buildGradleFile, ".*^apply plugin: \"com.liferay.plugin\".*");

		File viewJspFile = new File(
			projectDir, "/src/main/resources/META-INF/resources/view.jsp");

		assertTrue(viewJspFile.exists());
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
			assertTrue(existFile.exists());
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

		assertTrue(projectDir.exists());

		_testTemplateFiles(projectDir.toPath());

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

		assertTrue(projectDir.exists());

		_testTemplateFiles(projectDir.toPath());

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

		assertNotNull(templates);
		assertTrue(templates.length == 18);
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

		assertTrue(buildOutput.exists());
	}

	private static void _verifyGradleRunnerOutput(BuildTask buildtask) {
		assertNotNull(buildtask);

		assertEquals(buildtask.getOutcome(), TaskOutcome.SUCCESS);
	}

	private File _checkFileDoesNotExists(String path) {
		File file = IO.getFile(path);

		assertFalse(file.exists());

		return file;
	}

	private File _checkFileExists(String path) {
		File file = IO.getFile(path);

		assertTrue(file.exists());

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

		assertTrue(matcher.matches());
	}

	private boolean _endsWithEmptyLine(Path path) throws IOException {
		try (RandomAccessFile randomAccessFile = new RandomAccessFile(
				path.toFile(), "r")) {

			long pos = randomAccessFile.length() - 1;

			if (pos < 0) {
				return false;
			}

			randomAccessFile.seek(pos);

			int c = randomAccessFile.read();

			if ((c == '\n') || (c == '\r')) {
				return true;
			}
		}

		return false;
	}

	private boolean _exists(Path dirPath, String glob) throws IOException {
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				dirPath, glob)) {

			Iterator<Path> iterator = directoryStream.iterator();

			if (iterator.hasNext()) {
				return true;
			}
		}

		return false;
	}

	private boolean _isTextFile(Path path) {
		Path fileNamePath = path.getFileName();

		String fileName = fileNamePath.toString();

		if (fileName.equals("gitignore")) {
			return true;
		}

		int pos = fileName.indexOf('.');

		if (pos == -1) {
			return false;
		}

		String extension = fileName.substring(pos + 1);

		if (_textFileExtensions.contains(extension)) {
			return true;
		}

		return false;
	}

	private void _lacks(File file, String pattern) throws Exception {
		String content = new String(IO.read(file));

		Matcher matcher = Pattern.compile(
			pattern, Pattern.MULTILINE | Pattern.DOTALL).matcher(content);

		assertFalse(matcher.matches());
	}

	private String _readProperty(Path path, String key) throws IOException {
		Properties properties = new Properties();

		try (InputStream inputStream = Files.newInputStream(path)) {
			properties.load(inputStream);
		}

		return properties.getProperty(key);
	}

	private void _testLanguageProperties(Path path) throws IOException {
		try (BufferedReader bufferedReader = Files.newBufferedReader(
				path, StandardCharsets.UTF_8)) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				Assert.assertFalse(
					"Forbidden empty line in " + path, line.isEmpty());
				Assert.assertFalse(
					"Forbidden comments in " + path, line.startsWith("##"));
			}
		}
	}

	private void _testTemplateFiles(Path rootDirPath) throws Exception {
		_testTemplates(rootDirPath, false, false);

		Files.walkFileTree(
			rootDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path languagePropertiesPath = dirPath.resolve(
						"Language.properties");

					if (Files.exists(languagePropertiesPath)) {
						_testLanguageProperties(languagePropertiesPath);

						String glob = "Language_*.properties";

						Assert.assertFalse(
							"Forbidden " + dirPath + File.separator + glob,
							_exists(dirPath, glob));
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					if (!_isTextFile(path)) {
						return FileVisitResult.CONTINUE;
					}

					_testTextFileLines(path);

					Path fileNamePath = path.getFileName();

					if (!_trailingEmptyLineAllowedFileNames.contains(
							fileNamePath.toString())) {

						Assert.assertFalse(
							"Trailing empty line in " + path,
							_endsWithEmptyLine(path));
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private void _testTemplates(
			Path path, boolean gitIgnoreForbidden, boolean gradlewForbidden)
		throws Exception {

		final AtomicReference<String> previousGradlewDistributionUrl =
			new AtomicReference<>(null);

		Path gitIgnorePath = path.resolve("gitignore");
		Path dotGitIgnorePath = path.resolve(".gitignore");

		Assert.assertFalse(Files.exists(gitIgnorePath));
		Assert.assertTrue(Files.exists(dotGitIgnorePath));

		if (gitIgnoreForbidden) {
			Assert.assertFalse(
				"Forbidden " + dotGitIgnorePath,
				Files.exists(dotGitIgnorePath));
		}
		else {
			Assert.assertTrue(
				"Missing " + dotGitIgnorePath, Files.exists(dotGitIgnorePath));
		}

		boolean gradlewExists = Files.exists(path.resolve("gradlew"));

		if (gradlewForbidden) {
			Assert.assertFalse(
				"Forbidden Gradle wrapper in " + path, gradlewExists);
		}
		else {
			Assert.assertTrue(
				"Missing Gradle wrapper in " + path, gradlewExists);

			String gradlewDistributionUrl = _readProperty(
				path.resolve("gradle/wrapper/gradle-wrapper.properties"),
				"distributionUrl");

			boolean first = previousGradlewDistributionUrl.compareAndSet(
				null, gradlewDistributionUrl);

			if (!first) {
				Assert.assertEquals(
					"Wrong Gradle wrapper distribution URL in " + path,
					previousGradlewDistributionUrl.get(),
					gradlewDistributionUrl);
			}
		}

		File buildGradle = new File(path.toFile(), "build.gradle");

		Assert.assertTrue(buildGradle.exists());

		_lacks(buildGradle, ".*latest.release.*");
	}

	private void _testTextFileLines(Path path) throws IOException {
		try (BufferedReader bufferedReader = Files.newBufferedReader(
				path, StandardCharsets.UTF_8)) {

			String line = null;

			while ((line = bufferedReader.readLine()) != null) {
				if (line.isEmpty()) {
					continue;
				}

				Assert.assertFalse(
					"Forbidden whitespace trailing character in " + path,
					Character.isWhitespace(line.charAt(line.length() - 1)));
			}
		}
	}

	private static final String _EVENT_METHOD =
		"@Override\n" +
		"public void processLifecycleEvent(LifecycleEvent lifecycleEvent)\n" +
		"throws ActionException {\n" +
		"System.out.println(\"login.event.pre=\" + lifecycleEvent);\n" +
		"}\n";

	private static final File _testDir = IO.getFile("build/test");
	private static final Set<String> _textFileExtensions = new HashSet<>(
		Arrays.asList(
			"bnd", "gradle", "java", "jsp", "jspf", "properties", "xml"));
	private static final Set<String> _trailingEmptyLineAllowedFileNames =
		new HashSet<>(
			Arrays.asList(
				"gradle-wrapper.properties", "gradlew", "gradlew.bat"));

}