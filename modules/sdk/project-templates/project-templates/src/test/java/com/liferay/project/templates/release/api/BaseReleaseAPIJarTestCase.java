/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.release.api;

import com.liferay.petra.string.StringBundler;
import com.liferay.project.templates.util.ZipUtil;

import java.io.File;
import java.io.IOException;

import java.net.URI;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.rules.TemporaryFolder;

/**
 * @author Simon Jiang
 * @author Lawrence Lee
 */
public interface BaseReleaseAPIJarTestCase {

	public static final String RELEASE_API_JAR_FILE = System.getProperty(
		"releaseApiJarFile");

	public default Path getClassesDirPath(TemporaryFolder temporaryFolder)
		throws IOException {

		File releaseApiJarFile = new File(RELEASE_API_JAR_FILE);

		Assert.assertTrue(releaseApiJarFile.exists());

		File classesDir = temporaryFolder.newFolder(
			releaseApiJarFile.getName() + "-classes");

		ZipUtil.unzip(releaseApiJarFile, classesDir);

		return classesDir.toPath();
	}

	public default String getFileNames(List<String> fileNames)
		throws Exception {

		StringBundler sb = new StringBundler();

		sb.append("\n");

		for (String fileName : fileNames) {
			sb.append(fileName);
			sb.append("\n");
		}

		return sb.toString();
	}

	public default Set<String> getPaths(Path sourcePath, String extension)
		throws Exception {

		Set<String> results = new HashSet<>();

		Files.walkFileTree(
			sourcePath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(path.getFileName());

					if (fileName.endsWith(extension)) {
						URI folderURI = sourcePath.toUri();

						URI relativeURI = folderURI.relativize(path.toUri());

						String relativeURIString = relativeURI.getPath();

						results.add(relativeURIString.replace(extension, ""));
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return results;
	}

}