/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.internal.util;

import com.liferay.portal.kernel.util.GetterUtil;
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

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
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

	public static void deleteFiles(String dirName, Collection<File> files)
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

	public static String getCopyrightYear(File file) throws Exception {
		if (file.exists()) {
			String content = read(file);

			int x = content.indexOf("/**\n * SPDX-FileCopyrightText: (c) ");

			if (x != -1) {
				return content.substring(x + 35, x + 39);
			}
		}

		Format format = new SimpleDateFormat("yyyy");

		return format.format(new Date());
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
		write(file, content, null);
	}

	public static void write(
			File file, String content, Collection<File> modifiedFiles)
		throws Exception {

		if (!file.exists()) {
			Path path = file.toPath();

			if (path.getParent() != null) {
				Files.createDirectories(path.getParent());
			}

			Files.createFile(file.toPath());
		}

		String oldContent = read(file);

		if (StringUtil.endsWith(file.getName(), ".java")) {
			int contentHash = content.hashCode();

			int index = oldContent.lastIndexOf(
				_LIFERAY_REST_BUILDER_HASH_PREFIX);

			if (index != -1) {
				String hashLine = oldContent.substring(
					index + _LIFERAY_REST_BUILDER_HASH_PREFIX.length());

				if (GetterUtil.getInteger(hashLine) == contentHash) {
					return;
				}
			}

			Files.write(
				file.toPath(), content.getBytes(StandardCharsets.UTF_8));

			JavaParser.parse(file, 80);

			String parsedContent =
				read(file) + _LIFERAY_REST_BUILDER_HASH_PREFIX + contentHash;

			Files.write(
				file.toPath(), parsedContent.getBytes(StandardCharsets.UTF_8));

			if (!oldContent.equals(parsedContent)) {
				System.out.println("Writing " + file.getCanonicalPath());

				if (modifiedFiles != null) {
					modifiedFiles.add(file);
				}
			}
		}
		else {
			Files.write(
				file.toPath(), content.getBytes(StandardCharsets.UTF_8));

			if (!oldContent.equals(content)) {
				System.out.println("Writing " + file.getCanonicalPath());

				if (modifiedFiles != null) {
					modifiedFiles.add(file);
				}
			}
		}
	}

	private static final String _LIFERAY_REST_BUILDER_HASH_PREFIX =
		"\n// LIFERAY-REST-BUILDER-HASH:";

}