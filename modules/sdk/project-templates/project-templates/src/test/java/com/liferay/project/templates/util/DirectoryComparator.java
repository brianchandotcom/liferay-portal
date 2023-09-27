/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.project.templates.util;

import java.io.File;
import java.io.IOException;

import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Christopher Bryan Boyd
 * @author Andrea Di Giorgi
 */
public class DirectoryComparator extends SimpleFileVisitor<Path> {

	public DirectoryComparator(File rootDir1, File rootDir2) {
		this(rootDir1.toPath(), rootDir2.toPath());
	}

	public DirectoryComparator(Path rootDirPath1, Path rootDirPath2) {
		_rootDirPath1 = rootDirPath1;
		_rootDirPath2 = rootDirPath2;

		if (!Files.isDirectory(rootDirPath1) ||
			!Files.isDirectory(rootDirPath2)) {

			throw new IllegalArgumentException(
				"Arguments must be existing directories");
		}
	}

	public List<String> getDifferences() throws IOException {
		_differences.clear();

		Files.walkFileTree(_rootDirPath1, this);

		return _differences;
	}

	@Override
	public FileVisitResult preVisitDirectory(
			Path dirPath1, BasicFileAttributes basicFileAttributes)
		throws IOException {

		Path relativePath = _rootDirPath1.relativize(dirPath1);

		Path dirPath2 = _rootDirPath2.resolve(relativePath);

		Set<String> fileNames1 = _getFileNames(dirPath1);
		Set<String> fileNames2 = _getFileNames(dirPath2);

		if (!fileNames1.equals(fileNames2)) {
			_differences.add(
				"Directory " + dirPath1.toAbsolutePath() +
					" does not match with " + dirPath2.toAbsolutePath());

			return FileVisitResult.TERMINATE;
		}

		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(
			Path path1, BasicFileAttributes basicFileAttributes)
		throws IOException {

		Path relativePath = _rootDirPath1.relativize(path1);

		Path path2 = _rootDirPath2.resolve(relativePath);

		byte[] bytes1 = Files.readAllBytes(path1);
		byte[] bytes2 = Files.readAllBytes(path2);

		if (!Arrays.equals(bytes1, bytes2)) {
			_differences.add(
				"File " + path1.toAbsolutePath() + " does not match with " +
					path2.toAbsolutePath());
		}

		return FileVisitResult.CONTINUE;
	}

	private Set<String> _getFileNames(Path dirPath) throws IOException {
		Set<String> fileNames = new HashSet<>();

		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
				dirPath)) {

			for (Path path : directoryStream) {
				String fileName = String.valueOf(path.getFileName());

				if (Files.isDirectory(path)) {
					fileName += '/';
				}

				fileNames.add(fileName);
			}
		}

		return fileNames;
	}

	private final List<String> _differences = new ArrayList<>();
	private final Path _rootDirPath1;
	private final Path _rootDirPath2;

}