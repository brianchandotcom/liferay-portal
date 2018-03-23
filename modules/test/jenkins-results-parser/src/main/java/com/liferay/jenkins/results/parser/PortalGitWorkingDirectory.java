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

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 * @author Peter Yoo
 */
public class PortalGitWorkingDirectory extends GitWorkingDirectory {

	public PortalGitWorkingDirectory(
			String upstreamBranchName, String workingDirectoryPath)
		throws IOException {

		super(upstreamBranchName, workingDirectoryPath);
	}

	public PortalGitWorkingDirectory(
			String upstreamBranchName, String workingDirectoryPath,
			String repositoryName)
		throws IOException {

		super(upstreamBranchName, workingDirectoryPath, repositoryName);
	}

	public List<File> getCurrentBranchModuleGroupDirs() throws IOException {
		List<File> currentBranchModuleGroupDirs = new ArrayList<>();

		List<File> currentBranchFiles = getCurrentBranchFiles();

		for (File moduleGroupDir : getModuleGroupDirs()) {
			String moduleGroupPath = moduleGroupDir.getCanonicalPath();

			for (File currentBranchFile : currentBranchFiles) {
				String currentBranchFilePath =
					currentBranchFile.getCanonicalPath();

				if (currentBranchFilePath.startsWith(moduleGroupPath)) {
					currentBranchModuleGroupDirs.add(moduleGroupDir);

					break;
				}
			}
		}

		return currentBranchModuleGroupDirs;
	}

	public List<File> getModuleGroupDirs() throws IOException {
		final File modulesDir = new File(getWorkingDirectory(), "modules");

		if (!modulesDir.exists()) {
			return new ArrayList<>();
		}

		final List<File> moduleGroupDirs = new ArrayList<>();

		Files.walkFileTree(
			modulesDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
						Path filePath, IOException exc)
					throws IOException {

					if (_moduleGroup == null) {
						return FileVisitResult.CONTINUE;
					}

					ModuleGroup currentModuleGroup = ModuleGroup.getModuleGroup(
						filePath);

					if (currentModuleGroup == null) {
						return FileVisitResult.CONTINUE;
					}

					File currentFile = currentModuleGroup.getFile();

					if (currentFile.equals(_moduleGroup.getFile())) {
						moduleGroupDirs.add(currentFile);

						_moduleGroup = null;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(
						Path filePath, BasicFileAttributes attrs)
					throws IOException {

					ModuleGroup currentModuleGroup = ModuleGroup.getModuleGroup(
						filePath);

					if (currentModuleGroup == null) {
						return FileVisitResult.CONTINUE;
					}

					if (_moduleGroup == null) {
						_moduleGroup = currentModuleGroup;

						return FileVisitResult.CONTINUE;
					}

					int currentPriority = currentModuleGroup.getPriority();

					if (currentPriority < _moduleGroup.getPriority()) {
						_moduleGroup = currentModuleGroup;
					}

					return FileVisitResult.CONTINUE;
				}

				private ModuleGroup _moduleGroup;

			});

		Collections.sort(moduleGroupDirs);

		return moduleGroupDirs;
	}

	private static class ModuleGroup {

		public static ModuleGroup getModuleGroup(Path path) {
			File file = path.toFile();

			if (!file.isDirectory()) {
				return null;
			}

			for (int i = 0; i < _markerFileNames.size(); i++) {
				for (String markerFileName : _markerFileNames.get(i)) {
					File markerFile = new File(file, markerFileName);

					if (markerFile.exists()) {
						return new ModuleGroup(file, i);
					}
				}
			}

			return null;
		}

		public File getFile() {
			return _file;
		}

		public int getPriority() {
			return _priority;
		}

		@Override
		public String toString() {
			return JenkinsResultsParserUtil.combine(
				Integer.toString(_priority), " ", _file.toString());
		}

		private ModuleGroup(File file, int priority) {
			_file = file;
			_priority = priority;
		}

		private static Map<Integer, String[]> _markerFileNames =
			new HashMap<>();

		static {
			_markerFileNames.put(0, new String[] {"subsystem.bnd", ".gitrepo"});
			_markerFileNames.put(1, new String[] {"app.bnd"});
			_markerFileNames.put(2, new String[] {"bnd.bnd"});
			_markerFileNames.put(
				3, new String[] {"build.gradle", "build.xml", "pom.xml"});
		}

		private final File _file;
		private final int _priority;

	}

}