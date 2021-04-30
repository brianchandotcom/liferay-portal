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

package com.liferay.dispatch.talend.web.internal.archive;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Igor Beslic
 */
public class TalendArchiveParserUtil {

	public static TalendArchive parse(InputStream jobArchiveInputStream) {
		try {
			return _parse(jobArchiveInputStream);
		}
		catch (IOException ioException) {
			_log.error("Unable to parse Talend archive", ioException);
		}

		return null;
	}

	private static void _addJVMOptionsList(
		List<String> jvmOptionsList, String commandLine) {

		if (Validator.isNull(commandLine) || !commandLine.startsWith("java")) {
			return;
		}

		String[] tokens = commandLine.split("\\s");

		for (String token : tokens) {
			if (token.startsWith(StringPool.QUOTE) ||
				token.startsWith(StringPool.APOSTROPHE)) {

				token = token.substring(1);
			}

			if (token.endsWith(StringPool.QUOTE) ||
				token.endsWith(StringPool.APOSTROPHE)) {

				token = token.substring(0, token.length() - 1);
			}

			if (token.startsWith("-X")) {
				jvmOptionsList.add(token);
			}
		}
	}

	private static Properties _getContextProperties(
			String contextName, String jobExecutableJarPath)
		throws IOException {

		Properties properties = new Properties();

		String contextPropertiesSuffix = contextName + ".properties";

		try (ZipFile zipFile = new ZipFile(new File(jobExecutableJarPath))) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (name.endsWith(contextPropertiesSuffix)) {
					properties.load(zipFile.getInputStream(zipEntry));
				}
			}
		}

		return properties;
	}

	private static File _getJobDirectory(InputStream jobArchiveInputStream)
		throws IOException {

		File tempFile = FileUtil.createTempFile(jobArchiveInputStream);

		try {
			File tempFolder = FileUtil.createTempFolder();

			FileUtil.unzip(tempFile, tempFolder);

			return tempFolder;
		}
		finally {
			if (tempFile != null) {
				FileUtil.delete(tempFile);
			}
		}
	}

	private static Path _getJobJarPath(String jobName, Path jobDirectoryPath)
		throws IOException {

		AtomicReference<Path> pathReference = new AtomicReference<>();

		Files.walkFileTree(
			jobDirectoryPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String pathString = filePath.toString();

					if (pathString.endsWith(".jar") &&
						pathString.contains(jobName)) {

						pathReference.set(filePath);

						return FileVisitResult.TERMINATE;
					}

					return FileVisitResult.CONTINUE;
				}

			});

		Path jobJarPath = pathReference.get();

		if (jobJarPath != null) {
			return jobJarPath;
		}

		throw new IllegalArgumentException(
			"Unable to determine job JAR directory for " + jobName);
	}

	private static List<String> _getJobLibEntries(Path jobDirectoryPath)
		throws IOException {

		List<String> pathStrings = new ArrayList<>();

		Files.walkFileTree(
			jobDirectoryPath.resolve("lib"),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path filePath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String pathString = filePath.toString();

					if (pathString.endsWith(".jar")) {
						pathStrings.add(pathString);
					}

					return FileVisitResult.CONTINUE;
				}

			});

		pathStrings.sort(null);

		return pathStrings;
	}

	private static String _getJobMainClassFQN(
			String jobName, String jobExecutableJarPath)
		throws IOException {

		String mainClassSuffix = jobName + ".class";

		try (ZipFile zipFile = new ZipFile(new File(jobExecutableJarPath))) {
			Enumeration<? extends ZipEntry> enumeration = zipFile.entries();

			while (enumeration.hasMoreElements()) {
				ZipEntry zipEntry = enumeration.nextElement();

				String name = zipEntry.getName();

				if (name.endsWith(mainClassSuffix)) {
					name = name.substring(0, name.length() - 6);

					return StringUtil.replace(
						name, CharPool.SLASH, CharPool.PERIOD);
				}
			}
		}

		throw new IllegalArgumentException(
			"Unable to determine job main class");
	}

	private static Properties _getJobProperties(File jobDirectory)
		throws IOException {

		Properties properties = new Properties();

		try (InputStream inputStream = new FileInputStream(
				new File(jobDirectory, "jobInfo.properties"))) {

			properties.load(inputStream);
		}

		return properties;
	}

	private static List<String> _getJobScriptPathStrings(Path jobDirectoryPath)
		throws IOException {

		List<String> pathStrings = new ArrayList<>();

		Files.walkFileTree(
			jobDirectoryPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
					Path filePath, BasicFileAttributes basicFileAttributes) {

					String pathString = filePath.toString();

					if (pathString.endsWith(".bat") ||
						pathString.endsWith(".ps1") ||
						pathString.endsWith(".sh")) {

						pathStrings.add(pathString);
					}

					return FileVisitResult.CONTINUE;
				}

			});

		pathStrings.sort(null);

		return pathStrings;
	}

	private static List<String> _getJVMOptionsList(
			File jobDirectory, String jobName)
		throws IOException {

		Path path = jobDirectory.toPath();

		List<String> jobScriptPathStrings = _getJobScriptPathStrings(
			path.resolve(jobName));

		List<String> jvmOptionsList = new ArrayList<>();

		for (String jobScriptPathString : jobScriptPathStrings) {
			BufferedReader bufferedReader = new BufferedReader(
				new FileReader(jobScriptPathString));

			String line = bufferedReader.readLine();

			while (line != null) {
				_addJVMOptionsList(jvmOptionsList, line);

				line = bufferedReader.readLine();
			}

			if (!jvmOptionsList.isEmpty()) {
				break;
			}
		}

		return jvmOptionsList;
	}

	private static TalendArchive _parse(InputStream jobZIPInputStream)
		throws IOException {

		File jobDirectory = _getJobDirectory(jobZIPInputStream);

		Path jobDirectoryPath = jobDirectory.toPath();

		Properties jobProperties = _getJobProperties(jobDirectory);

		TalendArchive.Builder talendArchiveBuilder =
			new TalendArchive.Builder();

		talendArchiveBuilder.classPathEntries(
			_getJobLibEntries(jobDirectoryPath));

		String contextName = (String)jobProperties.get("contextName");

		talendArchiveBuilder.contextName(contextName);

		String jobName = (String)jobProperties.get("job");

		Path jobJarPath = _getJobJarPath(jobName, jobDirectoryPath);

		talendArchiveBuilder.contextProperties(
			_getContextProperties(contextName, jobJarPath.toString()));

		talendArchiveBuilder.jobDirectory(jobDirectory.getAbsolutePath());

		Path jobJarParentDirectoryPath = jobJarPath.getParent();

		talendArchiveBuilder.jobJarParentDirectory(
			jobJarParentDirectoryPath.toString());

		talendArchiveBuilder.jobJarPath(jobJarPath.toString());
		talendArchiveBuilder.jobMainClassFQN(
			_getJobMainClassFQN(jobName, jobJarPath.toString()));

		talendArchiveBuilder.jvmOptionsList(
			_getJVMOptionsList(jobDirectory, jobName));

		return talendArchiveBuilder.build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TalendArchiveParserUtil.class);

}