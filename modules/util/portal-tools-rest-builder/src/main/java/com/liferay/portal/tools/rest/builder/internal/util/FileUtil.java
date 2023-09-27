/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.internal.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.java.parser.JavaParser;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Peter Shin
 */
public class FileUtil {

	public static void delete(File file) throws IOException {
		if (!file.exists()) {
			return;
		}

		Files.delete(file.toPath());

		System.out.println("Deleting " + file.getCanonicalPath());
	}

	public static void deleteFiles(String dirName, List<File> files)
		throws Exception {

		Path path = Paths.get(dirName);

		if (!Files.exists(path)) {
			return;
		}

		Set<String> canonicalPaths = new HashSet<>();

		for (File file : files) {
			canonicalPaths.add(file.getCanonicalPath());
		}

		Files.walkFileTree(
			path,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					File file = path.toFile();

					if (canonicalPaths.contains(file.getCanonicalPath())) {
						return FileVisitResult.CONTINUE;
					}

					String content = read(file);

					if (StringUtil.endsWith(file.getName(), ".java") &&
						!content.contains("@generated")) {

						return FileVisitResult.CONTINUE;
					}

					if (StringUtil.endsWith(file.getName(), ".properties") &&
						!content.contains("# This is a generated file.")) {

						return FileVisitResult.CONTINUE;
					}

					delete(file);

					return FileVisitResult.CONTINUE;
				}

			});
	}

	public static File[] getFiles(File dir, String prefix, String suffix) {
		return dir.listFiles(
			new FileFilter() {

				@Override
				public boolean accept(File file) {
					if (file.isDirectory()) {
						return false;
					}

					String name = file.getName();

					if (!name.startsWith(prefix) || !name.endsWith(suffix)) {
						return false;
					}

					return true;
				}

			});
	}

	public static String read(File file) throws IOException {
		if (!file.exists()) {
			return "";
		}

		String s = new String(
			Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

		return StringUtil.replace(s, "\r\n", "\n");
	}

	public static void write(File file, String content) throws Exception {
		if (!file.exists()) {
			Path path = file.toPath();

			if (path.getParent() != null) {
				Files.createDirectories(path.getParent());
			}

			Files.createFile(file.toPath());
		}

		String oldContent = read(file);

		Files.write(file.toPath(), content.getBytes(StandardCharsets.UTF_8));

		if (!oldContent.equals(_format(file))) {
			System.out.println("Writing " + file.getCanonicalPath());
		}
	}

	private static String _format(File file) throws Exception {
		if (StringUtil.endsWith(file.getName(), ".java")) {
			JavaParser.parse(file, 80);
		}

		return read(file);
	}

}