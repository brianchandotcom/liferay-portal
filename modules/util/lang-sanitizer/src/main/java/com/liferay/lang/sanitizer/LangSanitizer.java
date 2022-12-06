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

package com.liferay.lang.sanitizer;

import com.liferay.lang.sanitizer.util.ArgumentsUtil;
import com.liferay.lang.sanitizer.util.EscapeUtil;
import com.liferay.petra.string.StringBundler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.ScanException;

/**
 * @author Seiphon Wang
 */
public class LangSanitizer {

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();

		LangSanitizer langSanitizer = new LangSanitizer();

		langSanitizer.sanitize(
			ArgumentsUtil.getValue(args, "source.base.dir", "./"));

		long endTime = System.currentTimeMillis();

		for (int i = 0; i < _messages.size(); i++) {
			System.out.println((i + 1) + ": " + _messages.get(i));
		}

		System.out.println(
			"Total time: " + ((endTime - startTime) / 1000) + "s");
	}

	public LangSanitizer() throws Exception {
		ClassLoader classLoader = LangSanitizer.class.getClassLoader();

		_policy = Policy.getInstance(
			classLoader.getResourceAsStream("sanitizer-configuration.xml"));
	}

	public void sanitize(String baseDirName) throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		List<Future<List<String>>> futures = new CopyOnWriteArrayList<>();

		for (File file : _getPropertiesFiles(baseDirName)) {
			Future<List<String>> future = executorService.submit(
				new Callable<List<String>>() {

					@Override
					public List<String> call() throws Exception {
						return _sanitizeProperites(file);
					}

				});

			futures.add(future);
		}

		for (Future<List<String>> future : futures) {
			_messages.addAll(future.get());
		}

		executorService.shutdown();

		while (!executorService.isTerminated()) {
			Thread.sleep(20);
		}
	}

	private List<File> _getPropertiesFiles(String baseDirName)
		throws Exception {

		List<File> files = new ArrayList<>();

		Files.walkFileTree(
			Paths.get(baseDirName),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String dirName = String.valueOf(dirPath.getFileName());

					List<String> skipDirNames = Arrays.asList(_SKIP_DIR_NAMES);

					if (dirName.startsWith(".") ||
						skipDirNames.contains(dirName)) {

						return FileVisitResult.SKIP_SUBTREE;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path file, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(file.getFileName());

					if ((fileName.endsWith(".properties") &&
						 fileName.startsWith("Language")) ||
						(fileName.endsWith(".properties") &&
						 fileName.startsWith("bundle"))) {

						files.add(file.toFile());
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return files;
	}

	private String _sanitizeContent(File file, String key, String originalValue)
		throws Exception {

		AntiSamy antiSamy = new AntiSamy();

		String sanitizedValue = null;

		try {
			CleanResults cleanResults = antiSamy.scan(originalValue, _policy);

			sanitizedValue = EscapeUtil.unescape(cleanResults.getCleanHTML());
		}
		catch (ScanException scanException) {
			return StringBundler.concat(
				"File: ", file.getAbsolutePath(), System.lineSeparator(),
				"\tKey: ", key, System.lineSeparator(), "\tOriginal Content: ",
				originalValue, System.lineSeparator(), "\tSantized Content: ",
				EscapeUtil.escapeTag(originalValue));
		}

		if (!sanitizedValue.equals(
				EscapeUtil.formatTag(EscapeUtil.unescape(originalValue)))) {

			return StringBundler.concat(
				"File: ", file.getAbsolutePath(), System.lineSeparator(),
				"\tKey: ", key, System.lineSeparator(), "\tOriginal Content: ",
				originalValue, System.lineSeparator(), "\tSantized Content: ",
				EscapeUtil.unescapeTag(sanitizedValue));
		}

		return null;
	}

	private List<String> _sanitizeProperites(File file) throws Exception {
		List<String> messages = new CopyOnWriteArrayList<>();

		Properties properties = new Properties();

		if (file.exists()) {
			try (FileInputStream fileInputStream = new FileInputStream(file)) {
				properties.load(fileInputStream);
			}
		}

		for (Map.Entry<Object, Object> entry : properties.entrySet()) {
			String message = _sanitizeContent(
				file, (String)entry.getKey(), (String)entry.getValue());

			if (message != null) {
				messages.add(message);
			}
		}

		return messages;
	}

	private static final String[] _SKIP_DIR_NAMES = {
		".git", ".github", ".gradle", ".idea", ".m2", ".settings", "bin",
		"build", "classes", "dependencies", "node_modules",
		"node_modules_cache", "sql", "test-classes", "test-coverage",
		"test-results", "tmp"
	};

	private static final List<String> _messages = new CopyOnWriteArrayList<>();

	private final Policy _policy;

}