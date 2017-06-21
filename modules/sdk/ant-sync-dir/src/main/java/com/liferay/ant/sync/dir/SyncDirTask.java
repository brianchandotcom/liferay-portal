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

package com.liferay.ant.sync.dir;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author Andrea Di Giorgi
 */
public class SyncDirTask extends Task {

	@Override
	public void execute() throws BuildException {
		_checkConfiguration();

		log("Synchronizing " + _dir + " into " + _toDir);

		long start = System.currentTimeMillis();

		try {
			int count = _syncDirectory();

			log(
				count + " files synchronized in " +
					(System.currentTimeMillis() - start) + "ms");
		}
		catch (IOException ioe) {
			throw new BuildException(ioe);
		}
	}

	public void setDir(File dir) {
		_dir = dir;
	}

	public void setToDir(File toDir) {
		_toDir = toDir;
	}

	private void _checkConfiguration() throws BuildException {
		if (_dir == null) {
			throw new BuildException("No directory specified", getLocation());
		}

		if (!_dir.exists()) {
			throw new BuildException("Directory " + _dir + " does not exist");
		}

		if (!_dir.isDirectory()) {
			throw new BuildException(_dir + " is not a directory");
		}

		if (_toDir == null) {
			throw new BuildException(
				"No destination directory specified", getLocation());
		}
	}

	private int _syncDirectory() throws IOException {
		final Path rootDirPath = _dir.toPath();
		final Path toRootDirPath = _toDir.toPath();

		Files.createDirectories(toRootDirPath);

		final AtomicInteger atomicInteger = new AtomicInteger();

		Files.walkFileTree(
			rootDirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult preVisitDirectory(
						Path dirPath, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path relativePath = rootDirPath.relativize(dirPath);

					Path toDirPath = toRootDirPath.resolve(relativePath);

					if (Files.notExists(toDirPath)) {
						Files.createDirectory(toDirPath);
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					Path relativePath = rootDirPath.relativize(path);

					Path toPath = toRootDirPath.resolve(relativePath);

					if (Files.notExists(toPath)) {
						Files.copy(path, toPath);

						atomicInteger.incrementAndGet();
					}

					return FileVisitResult.CONTINUE;
				}

			});

		return atomicInteger.intValue();
	}

	private File _dir;
	private File _toDir;

}