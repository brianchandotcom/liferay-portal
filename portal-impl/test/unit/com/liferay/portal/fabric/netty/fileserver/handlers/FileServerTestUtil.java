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

package com.liferay.portal.fabric.netty.fileserver.handlers;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.fabric.netty.fileserver.FileHelperUtil;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Assert;

/**
 * @author Shuyang Zhou
 */
public class FileServerTestUtil {

	public static void assertFileEquals(final Path file1, Path file2)
		throws IOException {

		Files.walkFileTree(file1, new FolderCompareFileVisitor(file1, file2));
		Files.walkFileTree(file2, new FolderCompareFileVisitor(file2, file1));
	}

	public static void cleanUp() {
		FileHelperUtil.delete(true, paths.toArray(new Path[0]));

		paths.clear();
	}

	public static Path createEmptyFile(Path file) throws IOException {
		FileHelperUtil.delete(true, file);

		file = Files.createFile(file);

		paths.add(file);

		return file;
	}

	public static Path createFileWithData(Path file) throws IOException {
		file = Files.write(file, createRandomData(1024));

		paths.add(file);

		return file;
	}

	/**
	 * Creates the folder with new subfolders and files included in its
	 * directory structure.
	 *
	 * <p>
	 * The following diagram illustrates the folder's directory structure:
	 * </p>
	 *
	 * <p>
	 * <pre>
	 * folder
	 *      |
	 *      |->subfolder1
	 *      |           |
	 *      |           |->file1
	 *      |           |->file2
	 *      |
	 *      |->subfolder2
	 *      |           |
	 *      |           |->file3
	 *      |
	 *      |->subfolder3
	 *                  |
	 *                  |->subfolder4
	 * </pre></p>
	 *
	 * @param  folder the folder
	 * @return the folder with new subfolders and files included in its
	 *         directory structure
	 * @throws IOException if an IO exception occurred
	 */
	public static Path createFolderWithFiles(Path folder) throws IOException {
		FileHelperUtil.delete(folder);

		paths.add(Files.createDirectories(folder));

		Path subfolder1 = folder.resolve("subfolder1");

		Files.createDirectory(subfolder1);

		createFileWithData(subfolder1.resolve("file1"));
		createFileWithData(subfolder1.resolve("file2"));

		Path subfolder2 = subfolder1.resolve("subfolder2");

		Files.createDirectory(subfolder2);

		createFileWithData(subfolder2.resolve("file3"));

		Path subfolder3 = folder.resolve("subfolder3");

		Files.createDirectory(subfolder3);

		Files.createDirectory(subfolder3.resolve("subfolder4"));

		return folder;
	}

	public static Path createNotExistFile(Path file) {
		FileHelperUtil.delete(true, file);

		return file;
	}

	public static byte[] createRandomData(int size) {
		byte[] data = new byte[size];

		Random random = new Random();

		random.nextBytes(data);

		return data;
	}

	public static long getFileSystemTime(long time) throws IOException {
		Path path = Files.createTempFile(null, null);

		Files.setLastModifiedTime(path, FileTime.fromMillis(time));

		try {
			FileTime fileTime = Files.getLastModifiedTime(path);

			return fileTime.toMillis();
		}
		finally {
			Files.delete(path);
		}
	}

	public static Path registerForCleanUp(Path path) {
		paths.add(path);

		return path;
	}

	public static ByteBuf wrapFirstHalf(byte[] data) {
		return Unpooled.wrappedBuffer(data, 0, data.length / 2);
	}

	public static ByteBuf wrapSecondHalf(byte[] data) {
		return Unpooled.wrappedBuffer(
			data, data.length / 2, data.length - data.length / 2);
	}

	protected static final Set<Path> paths = new HashSet<>();

	protected static class FolderCompareFileVisitor
		extends SimpleFileVisitor<Path> {

		@Override
		public FileVisitResult visitFile(
				Path file, BasicFileAttributes basicFileAttributes)
			throws IOException {

			Path relativePath = _path1.relativize(file);

			Path otherFile = _path2.resolve(relativePath.toString());

			Assert.assertTrue(
				otherFile + " does not exist", Files.exists(otherFile));
			Assert.assertTrue(
				otherFile + " is not file", Files.isRegularFile(otherFile));
			Assert.assertArrayEquals(
				StringBundler.concat(
					"File content does not match, file1 ", file, ", file2 ",
					otherFile),
				Files.readAllBytes(file), Files.readAllBytes(otherFile));

			return FileVisitResult.CONTINUE;
		}

		protected FolderCompareFileVisitor(Path path1, Path path2) {
			_path1 = path1;
			_path2 = path2;
		}

		private final Path _path1;
		private final Path _path2;

	}

}