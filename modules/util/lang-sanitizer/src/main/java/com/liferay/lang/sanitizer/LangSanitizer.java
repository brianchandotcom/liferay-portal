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

import com.liferay.lang.sanitizer.util.AntiSamyUtil;
import com.liferay.lang.sanitizer.util.EscapeUtil;
import com.liferay.lang.sanitizer.util.PropertiesUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.tools.ArgumentsUtil;

import java.io.File;
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
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

/**
 * @author Seiphon Wang
 */
public class LangSanitizer {

	public static void main(String[] args) throws Exception {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		LangSanitizerArges langSanitizerArges = new LangSanitizerArges();

		String baseDir = GetterUtil.getString(
			arguments.get("source.base.dir"), LangSanitizerArges.BASE_DIR_NAME);

		langSanitizerArges.setBaseDirName(baseDir);

		LangSanitizer langSanitizer = new LangSanitizer(langSanitizerArges);

		long startTime = System.currentTimeMillis();

		langSanitizer.sanitize();

		long endTime = System.currentTimeMillis();

		_printScanResult(startTime, endTime);
	}

	public LangSanitizer(LangSanitizerArges langSanitizerArges) {
		ClassLoader classLoader = LangSanitizer.class.getClassLoader();

		URL antiSamyURL = classLoader.getResource("antisamy-liferay.xml");

		String antsamyPath = antiSamyURL.getFile();

		Policy policy = null;

		try {
			policy = Policy.getInstance(antsamyPath);
		}
		catch (PolicyException policyException) {
			policyException.printStackTrace();
		}

		_langSanitizerArges = langSanitizerArges;

		_policy = policy;
	}

	public List<File> getSantizedFiles() {
		return _sanitizedFiles;
	}

	public void sanitize() throws Exception {
		List<File> fileList = _getAllLanguageProperties(
			_langSanitizerArges.getBaseDirName());

		ExecutorService executorService = Executors.newFixedThreadPool(10);

		List<Future<List<LangSanitizerMessage>>> futures =
			new CopyOnWriteArrayList<>();

		for (File file : fileList) {
			Future<List<LangSanitizerMessage>> future = executorService.submit(
				new Callable<List<LangSanitizerMessage>>() {

					@Override
					public List<LangSanitizerMessage> call() throws Exception {
						return _sanitizeProperites(file);
					}

				});

			futures.add(future);
		}

		for (Future<List<LangSanitizerMessage>> future : futures) {
			_langSanitizerMessages.addAll(future.get());
		}

		executorService.shutdown();

		while (!executorService.isTerminated()) {
			Thread.sleep(20);
		}
	}

	public LangSanitizerMessage sanitizeContent(
			File file, String key, String originalValue)
		throws PolicyException {

		if (key.equals("form-navigator-entry-keys-help")) {
			return null;
		}

		String sanitizedValue = originalValue;
		String value = originalValue;

		try {
			sanitizedValue = EscapeUtil.unEscape(
				AntiSamyUtil.sanitize(_policy, originalValue));

			value = EscapeUtil.unEscape(originalValue);
		}
		catch (ScanException scanException) {
			return new LangSanitizerMessage(
				key, file, originalValue, EscapeUtil.escapeTag(originalValue));
		}

		if (!sanitizedValue.equals(value)) {
			value = EscapeUtil.formatTagForm(value);

			if (sanitizedValue.equals(value)) {
				return null;
			}

			return new LangSanitizerMessage(
				key, file, originalValue,
				EscapeUtil.unEscapeTag(sanitizedValue));
		}

		return null;
	}

	private static void _printScanResult(long startTime, long endTime)
		throws Exception {

		for (int i = 0; i < _langSanitizerMessages.size(); i++) {
			LangSanitizerMessage langSanitizerMessage =
				_langSanitizerMessages.get(i);

			System.out.println(
				(i + 1) + ": " + langSanitizerMessage.toString());
		}

		System.out.println(
			"Total time： " + ((endTime - startTime) / 1000) + " m");
		System.out.println("Total items: " + _langSanitizerMessages.size());
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
						ArrayUtil.contains(_SKIP_DIR_NAMES, dirName)) {

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

	private List<LangSanitizerMessage> _sanitizeProperites(File file)
		throws Exception {

		List<LangSanitizerMessage> langSanitizerMessages =
			new CopyOnWriteArrayList<>();

		Properties properties = PropertiesUtil.readProperties(file);

		Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();

		for (Map.Entry<Object, Object> entry : entrySet) {
			LangSanitizerMessage langSanitizerMessage = sanitizeContent(
				file, (String)entry.getKey(), (String)entry.getValue());

			if (langSanitizerMessage != null) {
				langSanitizerMessages.add(langSanitizerMessage);
			}
		}

		if (!langSanitizerMessages.isEmpty()) {
			_sanitizedFiles.add(file);
		}

		return langSanitizerMessages;
	}

	private static final String[] _SKIP_DIR_NAMES = {
		".git", ".github", ".gradle", ".idea", ".m2", ".settings", "bin",
		"build", "classes", "dependencies", "node_modules",
		"node_modules_cache", "sql", "test-classes", "test-coverage",
		"test-results", "tmp"
	};

	private static final List<LangSanitizerMessage> _langSanitizerMessages =
		new CopyOnWriteArrayList<>();
	private static final List<File> _sanitizedFiles =
		new CopyOnWriteArrayList<>();

	private final LangSanitizerArges _langSanitizerArges;
	private final Policy _policy;

}