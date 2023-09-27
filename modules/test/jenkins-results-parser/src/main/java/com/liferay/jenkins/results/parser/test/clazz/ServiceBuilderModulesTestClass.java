/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class ServiceBuilderModulesTestClass extends ModulesTestClass {

	protected ServiceBuilderModulesTestClass(
		BatchTestClassGroup batchTestClassGroup, File testClassFile) {

		super(batchTestClassGroup, testClassFile, "buildService");
	}

	protected ServiceBuilderModulesTestClass(
		BatchTestClassGroup batchTestClassGroup, JSONObject jsonObject) {

		super(batchTestClassGroup, jsonObject);
	}

	@Override
	protected List<File> getModulesProjectDirs() {
		final List<File> modulesProjectDirs = new ArrayList<>();

		Path moduleBaseDirPath = getModuleBaseDirPath();

		try {
			Files.walkFileTree(
				moduleBaseDirPath,
				new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult preVisitDirectory(
						Path filePath,
						BasicFileAttributes basicFileAttributes) {

						File currentDirectory = filePath.toFile();
						String filePathString = filePath.toString();

						if (filePathString.endsWith("-service")) {
							File buildFile = new File(
								currentDirectory, "build.gradle");
							File serviceXmlFile = new File(
								currentDirectory, "service.xml");

							if (buildFile.exists() && serviceXmlFile.exists()) {
								modulesProjectDirs.add(currentDirectory);

								return FileVisitResult.SKIP_SUBTREE;
							}
						}
						else if (filePathString.endsWith("-portlet")) {
							File portletXmlFile = new File(
								currentDirectory,
								"docroot/WEB-INF/portlet.xml");
							File serviceXmlFile = new File(
								currentDirectory,
								"docroot/WEB-INF/service.xml");

							if (portletXmlFile.exists() &&
								serviceXmlFile.exists()) {

								modulesProjectDirs.add(currentDirectory);

								return FileVisitResult.SKIP_SUBTREE;
							}
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