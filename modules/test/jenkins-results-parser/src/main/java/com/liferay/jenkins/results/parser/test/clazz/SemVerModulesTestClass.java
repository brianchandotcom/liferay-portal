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

package com.liferay.jenkins.results.parser.test.clazz;

import com.liferay.jenkins.results.parser.test.clazz.group.BatchTestClassGroup;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SemVerModulesTestClass extends ModulesTestClass {

	protected SemVerModulesTestClass(
		BatchTestClassGroup batchTestClassGroup, File moduleBaseDir) {

		super(batchTestClassGroup, moduleBaseDir, "baseline");
	}

	protected SemVerModulesTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);
	}

	@Override
	protected List<File> getModulesProjectDirs() {
		final List<File> modulesProjectDirs = new ArrayList<>();

		final File portalModulesBaseDir = getPortalModulesBaseDir();

		Path moduleBaseDirPath = getModuleBaseDirPath();

		try {
			Files.walkFileTree(
				moduleBaseDirPath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
						Path filePath,
						BasicFileAttributes basicFileAttributes) {

						if (filePath.equals(portalModulesBaseDir.toPath())) {
							return FileVisitResult.CONTINUE;
						}

						String filePathString = filePath.toString();

						if (filePathString.endsWith("-test")) {
							return FileVisitResult.SKIP_SUBTREE;
						}

						File currentDirectory = filePath.toFile();

						File bndBndFile = new File(currentDirectory, "bnd.bnd");

						File buildFile = new File(
							currentDirectory, "build.gradle");

						File lfrRelengIgnoreFile = new File(
							currentDirectory, ".lfrbuild-releng-ignore");

						if (buildFile.exists() && bndBndFile.exists() &&
							!lfrRelengIgnoreFile.exists()) {

							modulesProjectDirs.add(currentDirectory);

							return FileVisitResult.SKIP_SUBTREE;
						}

						return FileVisitResult.CONTINUE;
					}

				});
		}
		catch (IOException ioException) {
			throw new RuntimeException(
				"Unable to get module marker files from " + moduleBaseDirPath,
				ioException);
		}

		return modulesProjectDirs;
	}

}