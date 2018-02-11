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

import java.util.Arrays;
import java.util.Objects;

/**
 * @author Christopher Bryan Boyd
 */
public class DirectoryComparisonVisitor extends SimpleFileVisitor<Path> {

	public static boolean isDirectoriesEqual(File expected, File generated)
		throws IOException {

		if (generated.exists() && generated.isDirectory() &&
			expected.exists() && expected.isDirectory()) {

			DirectoryComparisonVisitor visitor = new DirectoryComparisonVisitor(
				expected, generated);

			Files.walkFileTree(expected.toPath(), visitor);

			return visitor._equal;
		}
		else {
			return false;
		}
	}

	public DirectoryComparisonVisitor(File expected, File generated) {
		_expected = expected;
		_generated = generated;
	}

	@Override
	public FileVisitResult preVisitDirectory(
			Path dir, BasicFileAttributes attrs)
		throws IOException {

		FileVisitResult result = super.preVisitDirectory(dir, attrs);

		Path relativize = _expected.toPath().relativize(dir);

		Path otherPath = _generated.toPath();

		File otherDir = otherPath.resolve(relativize).toFile();

		_equal = Objects.equals(
			Arrays.toString(dir.toFile().list()),
			Arrays.toString(otherDir.list()));

		if (!_equal) {
			System.out.println("Paths don't match: ");
			System.out.println(dir);
			System.out.println(otherDir);
			result = FileVisitResult.TERMINATE;
		}

		return result;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
		throws IOException {

		FileVisitResult result = super.visitFile(file, attrs);

		Path relativize = _expected.toPath().relativize(file);

		Path pathInOther = _generated.toPath();

		File fileInOther = pathInOther.resolve(relativize).toFile();

		byte[] thisBytes = Files.readAllBytes(file);
		byte[] otherBytes = Files.readAllBytes(fileInOther.toPath());

		_equal = Arrays.equals(thisBytes, otherBytes);

		if (!_equal) {
			System.out.println("Files don't match: ");
			System.out.println(file);
			System.out.println(fileInOther.getAbsolutePath());
			result = FileVisitResult.TERMINATE;
		}

		return result;
	}

	private boolean _equal = true;
	private final File _expected;
	private final File _generated;

}