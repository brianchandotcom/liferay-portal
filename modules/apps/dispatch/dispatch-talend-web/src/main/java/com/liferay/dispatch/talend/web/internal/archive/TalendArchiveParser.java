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

import com.liferay.dispatch.talend.web.internal.archive.exception.TalendArchiveException;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Igor Beslic
 */
public class TalendArchiveParser {

	public TalendArchive parse(InputStream jobArchiveInputStream) {
		try {
			return _parse(jobArchiveInputStream);
		}
		catch (Throwable throwable) {
			_log.error("Unable to parse Talend archive", throwable);
		}

		return null;
	}

	private String _getJobDirectory(InputStream jobArchiveInputStream)
		throws IOException {

		File tempFile = FileUtil.createTempFile(jobArchiveInputStream);

		try {
			File tempFolder = _unzipFile(tempFile);

			return tempFolder.getAbsolutePath();
		}
		finally {
			if (tempFile != null) {
				FileUtil.delete(tempFile);
			}
		}
	}

	private String _getJobJarName(String jobName, String jobRootDirectoryPath) {
		String[] paths = FileUtil.find(jobRootDirectoryPath, "**\\*.jar", null);

		for (String path : paths) {
			if (path.contains(jobName)) {
				return path.substring(path.lastIndexOf(File.separator) + 1);
			}
		}

		throw new TalendArchiveException(
			"Unable to determine job JAR directory for " + jobName);
	}

	private Path _getJobJARPath(String jobName, String jobDirectory) {
		return Paths.get(
			jobDirectory, jobName, _getJobJarName(jobName, jobDirectory));
	}

	private List<String> _getJobLibEntries(String jobDirectory) {
		Path libPath = Paths.get(jobDirectory, "lib");

		String[] paths = FileUtil.find(libPath.toString(), "**\\*.jar", null);

		return Arrays.asList(paths);
	}

	private String _getJobMainClassFQN(
			String jobName, String jobExecutableJARPath)
		throws IOException {

		String mainClassSuffix = jobName + ".class";

		try (ZipFile zipFile = new ZipFile(new File(jobExecutableJARPath))) {
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

		throw new TalendArchiveException("Unable to determine job main class");
	}

	private Properties _getJobProperties(String jobDirectory)
		throws IOException {

		Properties properties = new Properties();

		Path jobInfoPropertiesPath = Paths.get(
			jobDirectory, "jobInfo.properties");

		properties.load(new FileInputStream(jobInfoPropertiesPath.toFile()));

		return properties;
	}

	private TalendArchive _parse(InputStream jobZIPInputStream)
		throws IOException {

		String jobDirectory = _getJobDirectory(jobZIPInputStream);

		Properties jobProperties = _getJobProperties(jobDirectory);

		TalendArchive.Builder talendArchiveBuilder =
			new TalendArchive.Builder();

		talendArchiveBuilder.contextName(
			(String)jobProperties.get("contextName"));

		List<String> classPathEntries = _getJobLibEntries(jobDirectory);

		for (String classPathEntry : classPathEntries) {
			talendArchiveBuilder.classpathEntry(classPathEntry);
		}

		talendArchiveBuilder.jobDirectory(jobDirectory);

		String jobName = (String)jobProperties.get("job");

		Path jobJARPath = _getJobJARPath(jobName, jobDirectory);

		talendArchiveBuilder.jobJARPath(jobJARPath.toString());

		talendArchiveBuilder.jobMainClassFQN(
			_getJobMainClassFQN(jobName, jobJARPath.toString()));

		return talendArchiveBuilder.build();
	}

	private File _unzipFile(File archiveFile) throws IOException {
		File tempFolder = FileUtil.createTempFolder();

		FileUtil.unzip(archiveFile, tempFolder);

		return tempFolder;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TalendArchiveParser.class);

}