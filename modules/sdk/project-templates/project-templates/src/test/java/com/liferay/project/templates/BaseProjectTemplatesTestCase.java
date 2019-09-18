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

import com.liferay.project.templates.internal.ProjectGenerator;
import com.liferay.project.templates.internal.util.Validator;
import com.liferay.project.templates.util.DirectoryComparator;
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

	public static final String GRADLE_TASK_PATH_BUILD = ":build";

	public static final String[] GRADLE_WRAPPER_FILE_NAMES = {
		"gradlew", "gradlew.bat", "gradle/wrapper/gradle-wrapper.jar",
		"gradle/wrapper/gradle-wrapper.properties"
	};

	public static final String GRADLE_WRAPPER_VERSION = "4.10.2";

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

	public default File buildTemplateWithGradle(
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

		if (debug) {
			arguments.add("--debug");
		}
		else {
			arguments.add("--stacktrace");
		}

		String httpProxyHost =
			ProjectTemplatesTest.mavenExecutor.getHttpProxyHost();
		int httpProxyPort =
			ProjectTemplatesTest.mavenExecutor.getHttpProxyPort();

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

	public default void testArchetyper(
			File parentDir, File destinationDir, File projectDir, String name,
			String groupId, String template, List<String> args)
		throws Exception {

		String author = System.getProperty("user.name");
		String className = name;
		String contributorType = null;
		String dependencyInjector = "ds";
		String framework = null;
		String frameworkDependencies = null;
		String hostBundleSymbolicName = null;
		String hostBundleVersion = null;
		String packageName = name.replace('-', '.');
		String service = null;
		String version = "7.0";
		String viewType = null;

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
			else if (key.equals("dependencyInjector")) {
				dependencyInjector = value;
			}
			else if (key.equals("framework")) {
				framework = value;
			}
			else if (key.equals("frameworkDependencies")) {
				frameworkDependencies = value;
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
			else if (key.equals("viewType")) {
				viewType = value;
			}
		}

		ProjectGenerator projectGenerator = new ProjectGenerator();

		ProjectTemplatesArgs projectTemplatesArgs = new ProjectTemplatesArgs();

		projectTemplatesArgs.setAuthor(author);
		projectTemplatesArgs.setClassName(className);
		projectTemplatesArgs.setContributorType(contributorType);
		projectTemplatesArgs.setDependencyInjector(dependencyInjector);

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

		projectTemplatesArgs.setFramework(framework);
		projectTemplatesArgs.setFrameworkDependencies(frameworkDependencies);
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
		projectTemplatesArgs.setViewType(viewType);

		projectGenerator.generateProject(
			projectTemplatesArgs, archetyperDestinationDir);

		File archetyperProjectDir = new File(archetyperDestinationDir, name);

		FileUtil.deleteFileInPath(
			"build.gradle", archetyperDestinationDir.toPath());
		FileUtil.deleteFileInPath(
			"settings.gradle", archetyperProjectDir.toPath());

		if (template.equals("service-builder")) {
			File apiDir = new File(archetyperProjectDir, name + "-api");
			File serviceDir = new File(archetyperProjectDir, name + "-service");

			FileUtil.deleteFileInPath("build.gradle", apiDir.toPath());
			FileUtil.deleteFileInPath("build.gradle", serviceDir.toPath());
		}

		DirectoryComparator directoryComparator = new DirectoryComparator(
			projectDir, archetyperProjectDir);

		List<String> differences = directoryComparator.getDifferences();

		Assert.assertTrue(
			"Found differences " + differences, differences.isEmpty());
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