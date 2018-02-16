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

package com.liferay.project.templates.util;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Christopher Bryan Boyd
 */
public class DirectoryComparisonVisitor extends SimpleFileVisitor<Path> {

	public DirectoryComparisonVisitor(File dir1, File dir2) throws IOException {
		_dir1 = dir1;
		_dir2 = dir2;
		_differences = new ArrayList<>();

		if (!_dir1.isDirectory() || !_dir2.isDirectory()) {
			throw new IllegalArgumentException(
				"Arguments must be exist and be directories");
		}

		Files.walkFileTree(dir1.toPath(), this);
	}

	public List<String> getDifferences() {
		return _differences;
	}

	@Override
	public FileVisitResult preVisitDirectory(
			Path dir, BasicFileAttributes attrs)
		throws IOException {

		Path relativize = _dir1.toPath().relativize(dir);

		Path otherPath = _dir2.toPath();

		File otherDir = otherPath.resolve(relativize).toFile();

		boolean equals = Objects.equals(
			Arrays.toString(dir.toFile().list()),
			Arrays.toString(otherDir.list()));

		if (!equals) {
			_differences.add("Paths don't match: " + dir + " " + otherDir);

			return FileVisitResult.TERMINATE;
		}

		return super.preVisitDirectory(dir, attrs);
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
		throws IOException {

		Path relativePath = _dir1.toPath().relativize(file);

		Path otherPath = _dir2.toPath();

		Path otherRelativePath = otherPath.resolve(relativePath);

		File fileInOther = otherRelativePath.toFile();

		byte[] thisBytes = Files.readAllBytes(file);
		byte[] otherBytes = Files.readAllBytes(fileInOther.toPath());

		boolean equals = Arrays.equals(thisBytes, otherBytes);

		if (!equals) {
			_differences.add(
				"Files not equal: " + file + " " +
					fileInOther.getAbsolutePath());
		}

		return super.visitFile(file, attrs);
	}

	private final List<String> _differences;
	private final File _dir1;
	private final File _dir2;

}