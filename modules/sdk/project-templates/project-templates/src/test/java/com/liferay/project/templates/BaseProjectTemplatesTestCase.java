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
import com.liferay.project.templates.extensions.util.FileUtil;
import com.liferay.project.templates.extensions.util.ProjectTemplatesUtil;
import com.liferay.project.templates.extensions.util.Validator;
import com.liferay.project.templates.extensions.util.WorkspaceUtil;
import com.liferay.project.templates.util.FileTestUtil;
import com.liferay.project.templates.util.StringTestUtil;
import com.liferay.project.templates.util.XMLTestUtil;

import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringWriter;

import java.net.URI;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.diibadaaba.zipdiff.DifferenceCalculator;
import net.diibadaaba.zipdiff.Differences;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;

import org.junit.Assert;
import org.junit.rules.TemporaryFolder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * @author Lawrence Lee
 */
public interface BaseProjectTemplatesTestCase {

	public static final String BUILD_PROJECTS = System.getProperty(
		"project.templates.test.builds");

	public static final String BUNDLES_DIFF_IGNORES = StringTestUtil.merge(
		Arrays.asList(
			"*.js.map", "*_jsp.class", "*manifest.json", "*pom.properties",
			"*pom.xml", "*package.json", "Archiver-Version", "Build-Jdk",
			" Build-Jdk-Spec", "Built-By", "Javac-Debug", "Javac-Deprecation",
			"Javac-Encoding"),
		',');

	public static final String DEPENDENCY_MODULES_EXTENDER_API =
		"compileOnly group: \"com.liferay\", name: " +
			"\"com.liferay.frontend.js.loader.modules.extender.api\"";

	public static final String DEPENDENCY_PORTAL_KERNEL =
		"compileOnly group: \"com.liferay.portal\", name: " +
			"\"com.liferay.portal.kernel\"";

	public static final String GRADLE_TASK_PATH_BUILD = ":build";

	public static final String[] GRADLE_WRAPPER_FILE_NAMES = {
		"gradlew", "gradlew.bat", "gradle/wrapper/gradle-wrapper.jar",
		"gradle/wrapper/gradle-wrapper.properties"
	};

	public static final String GRADLE_WRAPPER_VERSION = "5.6.4";

	public static final String MAVEN_GOAL_BUILD_SERVICE =
		"service-builder:build";

	public static final String MAVEN_GOAL_PACKAGE = "package";

	public static final String[] MAVEN_WRAPPER_FILE_NAMES = {
		"mvnw", "mvnw.cmd", ".mvn/wrapper/maven-wrapper.jar",
		".mvn/wrapper/maven-wrapper.properties"
	};

	public static final String OUTPUT_FILENAME_GLOB_REGEX = "*.{jar,war}";

	public static final String REPOSITORY_CDN_URL =
		"https://repository-cdn.liferay.com/nexus/content/groups/public";

	public static final boolean TEST_DEBUG_BUNDLE_DIFFS = Boolean.getBoolean(
		"test.debug.bundle.diffs");

	public default void addNexusRepositoriesElement(
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

		String repositoryUrl =
			ProjectTemplatesTest.mavenExecutor.getRepositoryUrl();

		if (Validator.isNotNull(repositoryUrl)) {
			urlText = document.createTextNode(repositoryUrl);
		}
		else {
			urlText = document.createTextNode(REPOSITORY_CDN_URL);
		}

		urlElement.appendChild(urlText);

		repositoryElement.appendChild(idElement);
		repositoryElement.appendChild(urlElement);

		repositoriesElement.appendChild(repositoryElement);
	}

	public default void buildProjects(
			URI gradleDistribution, MavenExecutor mavenExecutor,
			File gradleProjectDir, File mavenProjectDir)
		throws Exception {

		File gradleOutputDir = new File(gradleProjectDir, "build/libs");
		File mavenOutputDir = new File(mavenProjectDir, "target");

		buildProjects(
			gradleDistribution, mavenExecutor, gradleProjectDir,
			mavenProjectDir, gradleOutputDir, mavenOutputDir,
			GRADLE_TASK_PATH_BUILD);
	}

	public default void buildProjects(
			URI gradleDistribution, MavenExecutor mavenExecutor,
			File gradleProjectDir, File mavenProjectDir, File gradleOutputDir,
			File mavenOutputDir, String... gradleTaskPath)
		throws Exception {

		if (!isBuildProjects()) {
			return;
		}

		executeGradle(gradleProjectDir, gradleDistribution, gradleTaskPath);

		Path gradleOutputPath = FileTestUtil.getFile(
			gradleOutputDir.toPath(), OUTPUT_FILENAME_GLOB_REGEX, 1);

		Assert.assertNotNull(gradleOutputPath);

		Assert.assertTrue(Files.exists(gradleOutputPath));

		File gradleOutputFile = gradleOutputPath.toFile();

		String gradleOutputFileName = gradleOutputFile.getName();

		executeMaven(mavenProjectDir, mavenExecutor, MAVEN_GOAL_PACKAGE);

		Path mavenOutputPath = FileTestUtil.getFile(
			mavenOutputDir.toPath(), OUTPUT_FILENAME_GLOB_REGEX, 1);

		Assert.assertNotNull(mavenOutputPath);

		Assert.assertTrue(Files.exists(mavenOutputPath));

		File mavenOutputFile = mavenOutputPath.toFile();

		String mavenOutputFileName = mavenOutputFile.getName();

		try {
			if (gradleOutputFileName.endsWith(".jar")) {
				testBundlesDiff(gradleOutputFile, mavenOutputFile);
			}
			else if (gradleOutputFileName.endsWith(".war")) {
				testWarsDiff(gradleOutputFile, mavenOutputFile);
			}
		}
		catch (Throwable t) {
			if (TEST_DEBUG_BUNDLE_DIFFS) {
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

	public default File buildTemplateWithGradle(
			File destinationDir, String template, String name, boolean gradle,
			boolean maven, String... args)
		throws Exception {

		List<String> completeArgs = new ArrayList<>(args.length + 6);

		completeArgs.add("--destination");
		completeArgs.add(destinationDir.getPath());

		completeArgs.add("--gradle");
		completeArgs.add(String.valueOf(gradle));

		completeArgs.add("--maven");
		completeArgs.add(String.valueOf(maven));

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

		ProjectTemplates.main(completeArgs.toArray(new String[0]));

		File projectDir = new File(destinationDir, name);

		testExists(projectDir, ".gitignore");

		if (gradle) {
			testExists(projectDir, "build.gradle");
		}
		else {
			testNotExists(projectDir, "build.gradle");
		}

		if (maven) {
			testExists(projectDir, "pom.xml");
		}
		else {
			testNotExists(projectDir, "pom.xml");
		}

		boolean workspace = WorkspaceUtil.isWorkspace(destinationDir);

		if (gradle && !workspace) {
			for (String fileName : GRADLE_WRAPPER_FILE_NAMES) {
				testExists(projectDir, fileName);
			}

			testExecutable(projectDir, "gradlew");
		}
		else {
			for (String fileName : GRADLE_WRAPPER_FILE_NAMES) {
				testNotExists(projectDir, fileName);
			}

			testNotExists(projectDir, "settings.gradle");
		}

		if (maven && !workspace) {
			for (String fileName : MAVEN_WRAPPER_FILE_NAMES) {
				testExists(projectDir, fileName);
			}

			testExecutable(projectDir, "mvnw");
		}
		else {
			for (String fileName : MAVEN_WRAPPER_FILE_NAMES) {
				testNotExists(projectDir, fileName);
			}
		}

		return projectDir;
	}

	public default File buildTemplateWithGradle(
			File destinationDir, String template, String name, String... args)
		throws Exception {

		return buildTemplateWithGradle(
			destinationDir, template, name, true, false, args);
	}

	public default File buildTemplateWithGradle(
			TemporaryFolder temporaryFolder, String template, String name,
			String... args)
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("gradle");

		return buildTemplateWithGradle(destinationDir, template, name, args);
	}

	public default File buildTemplateWithMaven(
			File parentDir, File destinationDir, String template, String name,
			String groupId, MavenExecutor mavenExecutor, String... args)
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

			executeMaven(
				destinationDir, true, mavenExecutor,
				completeArgs.toArray(new String[0]));

			return destinationDir;
		}

		executeMaven(
			destinationDir, mavenExecutor, completeArgs.toArray(new String[0]));

		File projectDir = new File(destinationDir, name);

		testExists(projectDir, "pom.xml");
		testNotExists(projectDir, "gradlew");
		testNotExists(projectDir, "gradlew.bat");
		testNotExists(projectDir, "gradle/wrapper/gradle-wrapper.jar");
		testNotExists(projectDir, "gradle/wrapper/gradle-wrapper.properties");

		return projectDir;
	}

	public default File buildTemplateWithMaven(
			TemporaryFolder temporaryFolder, String template, String name,
			String groupId, MavenExecutor mavenExecutor, String... args)
		throws Exception {

		File mavenDir = temporaryFolder.newFolder("maven");

		return buildTemplateWithMaven(
			mavenDir, mavenDir, template, name, groupId, mavenExecutor, args);
	}

	public default File buildWorkspace(TemporaryFolder temporaryFolder)
		throws Exception {

		File destinationDir = temporaryFolder.newFolder("workspace");

		return buildTemplateWithGradle(
			destinationDir, WorkspaceUtil.WORKSPACE, "test-workspace");
	}

	public default void editXml(File xmlFile, Consumer<Document> consumer)
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

	public default File enableTargetPlatformInWorkspace(
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

	public default Optional<String> executeGradle(
			File projectDir, boolean debug, boolean buildAndFail,
			URI gradleDistribution, String... taskPaths)
		throws IOException {

		final String repositoryUrl =
			ProjectTemplatesTest.mavenExecutor.getRepositoryUrl();

		String projectPath = projectDir.getPath();

		if (projectPath.contains("workspace")) {
			File buildFile = new File(projectDir, "build.gradle");

			Path buildFilePath = buildFile.toPath();

			String content = FileUtil.read(buildFilePath);

			if (!content.contains("allprojects")) {
				content +=
					"allprojects {\n\trepositories {\n\t\tmavenLocal()\n\t}\n}";

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
								"\"" + REPOSITORY_CDN_URL + "\"",
								"\"" + repositoryUrl + "\"");
						}

						if (!content.contains("mavenLocal()")) {
							String mavenRepoString = System.getProperty(
								"maven.repo.local");

							Path m2tmpPath = Paths.get(
								mavenRepoString + "-tmp");

							if (Files.exists(m2tmpPath)) {
								content = content.replace(
									"repositories {",
									"repositories {\n\t\tmavenLocal()\n\t\t" +
										"maven { \n\t\t\turl \"" + m2tmpPath +
											"\"\n\t\t}");
							}
						}

						Files.write(
							path, content.getBytes(StandardCharsets.UTF_8));
					}

					return FileVisitResult.CONTINUE;
				}

			});

		GradleRunner gradleRunner = GradleRunner.create();

		List<String> arguments = new ArrayList<>(taskPaths.length + 5);

		String httpProxyHost =
			ProjectTemplatesTest.mavenExecutor.getHttpProxyHost();
		int httpProxyPort =
			ProjectTemplatesTest.mavenExecutor.getHttpProxyPort();

		if (Validator.isNotNull(httpProxyHost) && (httpProxyPort > 0)) {
			arguments.add("-Dhttp.proxyHost=" + httpProxyHost);
			arguments.add("-Dhttp.proxyPort=" + httpProxyPort);
		}

		if (debug) {
			arguments.add("--debug");
		}
		else {
			arguments.add("--stacktrace");
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

		gradleRunner.withGradleDistribution(gradleDistribution);
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

	public default Optional<String> executeGradle(
			File projectDir, boolean debug, URI gradleDistribution,
			String... taskPaths)
		throws IOException {

		return executeGradle(
			projectDir, debug, false, gradleDistribution, taskPaths);
	}

	public default void executeGradle(
			File projectDir, URI gradleDistribution, String... taskPaths)
		throws IOException {

		executeGradle(projectDir, false, gradleDistribution, taskPaths);
	}

	public default String executeMaven(
			File projectDir, boolean buildAndFail, MavenExecutor mavenExecutor,
			String... args)
		throws Exception {

		File pomXmlFile = new File(projectDir, "pom.xml");

		if (pomXmlFile.exists()) {
			editXml(
				pomXmlFile,
				document -> {
					addNexusRepositoriesElement(
						document, "repositories", "repository");
					addNexusRepositoriesElement(
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

	public default String executeMaven(
			File projectDir, MavenExecutor mavenExecutor, String... args)
		throws Exception {

		return executeMaven(projectDir, false, mavenExecutor, args);
	}

	public default boolean isBuildProjects() {
		if (Validator.isNotNull(BUILD_PROJECTS) &&
			BUILD_PROJECTS.equals("true")) {

			return true;
		}

		return false;
	}

	public default List<String> sanitizeLines(List<String> lines) {
		List<String> sanitizedLines = new ArrayList<>();

		for (String line : lines) {
			line = line.replaceAll("\\?t=[0-9]+", "");

			sanitizedLines.add(line);
		}

		return sanitizedLines;
	}

	public default File testBuildTemplateWithWorkspace(
			TemporaryFolder temporaryFolder, URI gradleDistribution,
			String template, String name, String jarFilePath, String... args)
		throws Exception {

		File workspaceDir = buildWorkspace(temporaryFolder);

		List<String> argsList = Arrays.asList(args);

		if (argsList.contains("7.0.6")) {
			enableTargetPlatformInWorkspace(workspaceDir, "7.0.6");
		}
		else if (argsList.contains("7.1.3")) {
			enableTargetPlatformInWorkspace(workspaceDir, "7.1.3");
		}
		else {
			enableTargetPlatformInWorkspace(workspaceDir, "7.2.1");
		}

		File modulesDir = new File(workspaceDir, "modules");

		File workspaceProjectDir = buildTemplateWithGradle(
			modulesDir, template, name, args);

		testNotContains(
			workspaceProjectDir, "build.gradle", true, "^repositories \\{.*");
		testNotContains(
			workspaceProjectDir, "build.gradle", "version: \"[0-9].*");

		if (isBuildProjects()) {
			executeGradle(
				workspaceDir, gradleDistribution,
				":modules:" + name + ":build");

			testExists(workspaceProjectDir, jarFilePath);
		}

		return workspaceProjectDir;
	}

	public default void testBundlesDiff(File bundleFile1, File bundleFile2)
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
				"diff", "--ignore", BUNDLES_DIFF_IGNORES,
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

	public default File testContains(
			File dir, String fileName, boolean regex, String... strings)
		throws IOException {

		return testContainsOrNot(dir, fileName, regex, true, strings);
	}

	public default File testContains(
			File dir, String fileName, String... strings)
		throws IOException {

		return testContains(dir, fileName, false, strings);
	}

	public default File testContainsOrNot(
			File dir, String fileName, boolean regex, boolean contains,
			String... strings)
		throws IOException {

		File file = testExists(dir, fileName);

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

	public default File testExecutable(File dir, String fileName) {
		File file = testExists(dir, fileName);

		Assert.assertTrue(fileName + " is not executable", file.canExecute());

		return file;
	}

	public default File testExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertTrue("Missing " + fileName, file.exists());

		return file;
	}

	public default void testExists(ZipFile zipFile, String name) {
		Assert.assertNotNull("Missing " + name, zipFile.getEntry(name));
	}

	public default File testNotContains(
			File dir, String fileName, boolean regex, String... strings)
		throws IOException {

		return testContainsOrNot(dir, fileName, regex, false, strings);
	}

	public default File testNotContains(
			File dir, String fileName, String... strings)
		throws IOException {

		return testNotContains(dir, fileName, false, strings);
	}

	public default File testNotExists(File dir, String fileName) {
		File file = new File(dir, fileName);

		Assert.assertFalse("Unexpected " + fileName, file.exists());

		return file;
	}

	public default void testWarsDiff(File warFile1, File warFile2)
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

						lines1 = sanitizeLines(lines1);
						lines2 = sanitizeLines(lines2);

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

}