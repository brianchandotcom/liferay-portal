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

package com.liferay.portal.tools.rest.builder.internal.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.java.parser.JavaParser;
import com.liferay.source.formatter.SourceFormatter;
import com.liferay.source.formatter.SourceFormatterArgs;

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

import java.util.Collections;
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

		Set<String> canonicalPaths = new HashSet<>();

		for (File file : files) {
			canonicalPaths.add(file.getCanonicalPath());
		}

		Files.walkFileTree(
			Paths.get(dirName),
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

					if (!content.contains("@generated")) {
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

					if (!name.startsWith(prefix)) {
						return false;
					}

					if (!name.endsWith(suffix)) {
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

		return s.replace("\r\n", "\n");
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

		if (!oldContent.equals(_format(file))) {
			System.out.println("Writing " + file.getCanonicalPath());
		}
	}

	private static String _format(File file) throws Exception {
		if (StringUtil.endsWith(file.getName(), ".java")) {
			JavaParser.parse(file, 80);
		}

		SourceFormatterArgs sourceFormatterArgs = new SourceFormatterArgs();

		sourceFormatterArgs.setFileNames(
			Collections.singletonList(file.getCanonicalPath()));
		sourceFormatterArgs.setIncludeGeneratedFiles(true);
		sourceFormatterArgs.setPrintErrors(false);
		sourceFormatterArgs.setSkipCheckNames(
			Collections.singletonList("JavaOSGiReferenceCheck"));

		SourceFormatter sourceFormatter = new SourceFormatter(
			sourceFormatterArgs);

		sourceFormatter.format();

		return read(file);
	}

}