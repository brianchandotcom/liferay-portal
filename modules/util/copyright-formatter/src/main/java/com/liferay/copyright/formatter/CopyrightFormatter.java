/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.copyright.formatter;

import com.liferay.copyright.formatter.util.ArgumentsUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

/**
 * @author Alan Huang
 */
public class CopyrightFormatter {

	public static void main(String[] args) throws Exception {
		CopyrightFormatter copyrightFormatter = new CopyrightFormatter();

		copyrightFormatter.formatCopyright(
			ArgumentsUtil.getValue(args, "source.base.dir", "./"));
	}

	public void formatCopyright(String baseDirName) throws Exception {
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		List<Future<Void>> futures = new CopyOnWriteArrayList<>();

		for (File file : _getFiles(baseDirName)) {
			Future<Void> future = executorService.submit(
				new Callable<Void>() {

					@Override
					public Void call() throws Exception {
						_formatCopyright(file);

						return null;
					}

				});

			futures.add(future);
		}

		for (Future<Void> future : futures) {
			future.get();
		}

		executorService.shutdown();

		while (!executorService.isTerminated()) {
			Thread.sleep(20);
		}
	}

	private void _formatCopyright(File file) throws Exception {
		String content = new String(Files.readAllBytes(file.toPath()), "UTF-8");

		content = _replaceCopyright(content);

		FileOutputStream fileOutputStream = new FileOutputStream(file);

		fileOutputStream.write(content.getBytes());

		fileOutputStream.close();
	}

	private synchronized String _getCopyright() throws Exception {
		if (_copyright != null) {
			return _copyright;
		}

		ClassLoader classLoader = CopyrightFormatter.class.getClassLoader();

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		InputStream inputStream = classLoader.getResourceAsStream(
			"dependencies/copyright.txt");

		byte[] bytes = new byte[1024];

		while (true) {
			int read = inputStream.read(bytes, 0, bytes.length);

			if (read == -1) {
				break;
			}

			byteArrayOutputStream.write(bytes, 0, read);
		}

		byteArrayOutputStream.flush();

		return byteArrayOutputStream.toString();
	}

	private List<File> _getFiles(String baseDirName) throws Exception {
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

					Path gitRepoPath = dirPath.resolve(".gitrepo");

					if (Files.exists(gitRepoPath)) {
						String content = new String(
							Files.readAllBytes(gitRepoPath), "UTF-8");

						if (content.contains("autopull = true")) {
							return FileVisitResult.SKIP_SUBTREE;
						}
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path file, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(file.getFileName());

					int x = fileName.lastIndexOf('.');

					if (x == -1) {
						return FileVisitResult.CONTINUE;
					}

					String fileExtension = fileName.substring(x);

					List<String> fileExtensions = Arrays.asList(
						_FILE_EXTENSIONS);

					if (fileExtensions.contains(fileExtension)) {
						files.add(file.toFile());
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return files;
	}

	private String _replaceCopyright(String content) throws Exception {
		String copyright = _getCopyright();

		if ((copyright == null) || (copyright.length() == 0)) {
			return content;
		}

		if (copyright.contains("{$year}")) {
			copyright = copyright.replaceFirst(
				Pattern.quote("{$year}"), "2000");
		}

		int x = content.indexOf("/**\n * SPDX-FileCopyrightText: (c) ");

		if (x != -1) {
			String s = content.substring(x + 35, content.indexOf("\n", x + 35));

			if (s.matches("\\d{4} Liferay, Inc\\. https://liferay\\.com")) {
				return content;
			}
		}

		x = content.indexOf("/**\n * Copyright (c) ");

		if (x == -1) {
			return content;
		}

		String s = content.substring(x + 20, content.indexOf("\n", x + 20));

		if (!s.contains("Liferay, Inc.")) {
			return content;
		}

		int y = content.indexOf("\n */", x + 1);

		if (y == -1) {
			return content;
		}

		return content.substring(0, x) + copyright + content.substring(y + 4);
	}

	private static final String[] _FILE_EXTENSIONS = {
		".groovy", ".java", ".js", ".jsp", ".jspf", ".jspx", ".jsx",
		".testjava", ".testjsp", ".testjspf", ".ts", ".tsx"
	};

	private static final String[] _SKIP_DIR_NAMES = {
		"bin", "classes", "node_modules", "node_modules_cache", "test-coverage",
		"test-results", "tmp"
	};

	private String _copyright;

}