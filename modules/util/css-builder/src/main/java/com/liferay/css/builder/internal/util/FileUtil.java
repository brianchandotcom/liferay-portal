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

package com.liferay.css.builder.internal.util;

import com.liferay.css.builder.CSSBuilder;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;
import java.io.IOException;

import java.net.URL;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import org.apache.tools.ant.DirectoryScanner;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil {

	public static void deltree(Path dirPath) throws IOException {
		Files.walkFileTree(
			dirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path dirPath, IOException ioe)
					throws IOException {

					Files.delete(dirPath);

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Files.delete(path);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	public static String[] getFilesFromDirectory(
		String baseDir, String[] includes, String[] excludes) {

		DirectoryScanner directoryScanner = new DirectoryScanner();

		directoryScanner.setBasedir(baseDir);
		directoryScanner.setExcludes(excludes);
		directoryScanner.setIncludes(includes);

		directoryScanner.scan();

		return directoryScanner.getIncludedFiles();
	}

	public static File getJarFile() throws Exception {
		ProtectionDomain protectionDomain =
			CSSBuilder.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		return new File(url.toURI());
	}

	public static long getLastModifiedTime(Path path) {
		try {
			FileTime fileTime = Files.getLastModifiedTime(path);

			return fileTime.toMillis();
		}
		catch (IOException ioe) {
			return -1;
		}
	}

	public static boolean isAbsolute(String fileName) {
		Path path = Paths.get(fileName);

		return path.isAbsolute();
	}

	public static void write(File file, String content) throws IOException {
		File parentFile = file.getParentFile();

		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}

		Path path = Paths.get(file.toURI());

		Files.write(path, content.getBytes(StringPool.UTF8));
	}

}