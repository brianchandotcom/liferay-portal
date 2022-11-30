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

import java.net.URL;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;

/**
 * @author Seiphon Wang
 */
public class LangSanitizer {

	public static void main(String[] args) throws Exception {
		String baseDir = ArgumentsUtil.getValue(args, "source.base.dir", "./");

		LangSanitizer langSanitizer = new LangSanitizer();

		long startTime = System.currentTimeMillis();

		langSanitizer.sanitize(baseDir);

		long endTime = System.currentTimeMillis();

		for (int i = 0; i < _messages.size(); i++) {
			System.out.println(
				(i + 1) + ": " + _messages.get(i));
		}

		System.out.println(
			"Total time： " + ((endTime - startTime) / 1000) + "s");
	}

	public LangSanitizer() throws Exception {
		ClassLoader classLoader = LangSanitizer.class.getClassLoader();

		URL antiSamyURL = classLoader.getResource("antisamy-liferay.xml");

		String antsamyPath = antiSamyURL.getFile();

		_policy = Policy.getInstance(antsamyPath);
	}

	public void sanitize(String baseDirName) throws Exception {
		List<File> fileList = _getAllLanguageProperties(baseDirName);

		ExecutorService executorService = Executors.newFixedThreadPool(10);

		List<Future<List<String>>> futures = new CopyOnWriteArrayList<>();

		for (File file : fileList) {
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

	private boolean _contains(Object[] array, Object value) {
		if ((array == null) || (array.length == 0)) {
			return false;
		}

		for (Object object : array) {
			if (Objects.equals(value, object)) {
				return true;
			}
		}

		return false;
	}

	private List<File> _getAllLanguageProperties(String baseDirName)
		throws Exception {

		List<File> fileList = new ArrayList<>();

		Files.walkFileTree(
			Paths.get(baseDirName),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String dirName = String.valueOf(dirPath.getFileName());

					if (dirName.startsWith(".") ||
						_contains(_SKIP_DIR_NAMES, dirName)) {

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

						fileList.add(file.toFile());
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return fileList;
	}

	private String _sanitizeContent(File file, String key, String originalValue)
		throws Exception {

		if (key.equals("form-navigator-entry-keys-help")) {
			return null;
		}

		AntiSamy antiSamy = new AntiSamy();

		CleanResults cleanResults = antiSamy.scan(originalValue, _policy);

		String sanitizedValue = EscapeUtil.unEscape(
			cleanResults.getCleanHTML());
		String value = EscapeUtil.unEscape(originalValue);

		if (!sanitizedValue.equals(value)) {
			value = EscapeUtil.formatTag(value);

			if (!sanitizedValue.equals(value)) {
				return StringBundler.concat(
					"File: ", file.getAbsolutePath(), System.lineSeparator(),
					"\tKey: ", key, System.lineSeparator(),
					"\tOriginal Content: ", originalValue,
					System.lineSeparator(), "\tSantized Content: ",
					EscapeUtil.unEscapeTag(sanitizedValue));
			}
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

		Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();

		for (Map.Entry<Object, Object> entry : entrySet) {
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