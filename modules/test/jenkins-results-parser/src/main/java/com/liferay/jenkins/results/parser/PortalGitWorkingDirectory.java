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
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 * @author Peter Yoo
 */
public class PortalGitWorkingDirectory extends GitWorkingDirectory {

	public List<File> getModifiedModuleDirsList() throws IOException {
		return getModifiedModuleDirsList(null, null);
	}

	public List<File> getModifiedModuleDirsList(
			List<PathMatcher> excludesPathMatchers,
			List<PathMatcher> includesPathMatchers)
		throws IOException {

		List<File> modifiedModuleDirsList = new ArrayList<>();

		List<File> modifiedFilesList = getModifiedFilesList();

		for (File moduleDir :
				getModuleDirsList(excludesPathMatchers, includesPathMatchers)) {

			for (File modifiedFile : modifiedFilesList) {
				if (JenkinsResultsParserUtil.isFileInDirectory(
						moduleDir, modifiedFile)) {

					modifiedModuleDirsList.add(moduleDir);

					break;
				}
			}
		}

		return modifiedModuleDirsList;
	}

	public List<File> getModifiedNPMTestModuleDirsList() throws IOException {
		List<File> modifiedModuleDirsList = new ArrayList<>();

		for (File modifiedModuleDir : getModifiedModuleDirsList()) {
			if (_isNPMTestModuleDir(modifiedModuleDir)) {
				modifiedModuleDirsList.add(modifiedModuleDir);
			}
		}

		return modifiedModuleDirsList;
	}

	public List<File> getModuleAppDirs() throws IOException {
		List<File> moduleAppDirs = new ArrayList<>();

		List<File> moduleAppBndFiles = JenkinsResultsParserUtil.findFiles(
			new File(getWorkingDirectory(), "modules"), "app\\.bnd");

		for (File moduleAppBndFile : moduleAppBndFiles) {
			moduleAppDirs.add(moduleAppBndFile.getParentFile());
		}

		return moduleAppDirs;
	}

	public List<File> getModuleDirsList() throws IOException {
		return getModuleDirsList(null, null);
	}

	public List<File> getModuleDirsList(
			List<PathMatcher> excludesPathMatchers,
			List<PathMatcher> includesPathMatchers)
		throws IOException {

		final File modulesDir = new File(getWorkingDirectory(), "modules");

		if (!modulesDir.exists()) {
			return new ArrayList<>();
		}

		final List<PathMatcher> excludedModulesPathMatchers =
			excludesPathMatchers;
		final List<PathMatcher> includedModulesPathMatchers =
			includesPathMatchers;

		final List<File> moduleDirsList = new ArrayList<>();

		Files.walkFileTree(
			modulesDir.toPath(),
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult postVisitDirectory(
					Path filePath, IOException exc) {

					if (_module == null) {
						return FileVisitResult.CONTINUE;
					}

					Module currentModule = Module.getModule(filePath);

					if (currentModule == null) {
						return FileVisitResult.CONTINUE;
					}

					File currentFile = currentModule.getFile();

					if (currentFile.equals(_module.getFile())) {
						moduleDirsList.add(currentFile);

						_module = null;
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult preVisitDirectory(
					Path filePath, BasicFileAttributes attrs) {

					if (_pathExcluded(filePath) || !_pathIncluded(filePath)) {
						return FileVisitResult.CONTINUE;
					}

					Module currentModule = Module.getModule(filePath);

					if (currentModule == null) {
						return FileVisitResult.CONTINUE;
					}

					if (_module == null) {
						_module = currentModule;

						return FileVisitResult.CONTINUE;
					}

					int currentPriority = currentModule.getPriority();

					if (currentPriority < _module.getPriority()) {
						_module = currentModule;
					}

					return FileVisitResult.CONTINUE;
				}

				private boolean _pathExcluded(Path path) {
					if ((excludedModulesPathMatchers == null) ||
						excludedModulesPathMatchers.isEmpty()) {

						return false;
					}

					return _pathMatches(path, excludedModulesPathMatchers);
				}

				private boolean _pathIncluded(Path path) {
					if ((includedModulesPathMatchers == null) ||
						includedModulesPathMatchers.isEmpty()) {

						return true;
					}

					return _pathMatches(path, includedModulesPathMatchers);
				}

				private boolean _pathMatches(
					Path path, List<PathMatcher> pathMatchers) {

					for (PathMatcher pathMatcher : pathMatchers) {
						if (pathMatcher.matches(path)) {
							return true;
						}
					}

					return false;
				}

				private Module _module;

			});

		Collections.sort(moduleDirsList);

		return moduleDirsList;
	}

	public List<File> getNPMTestModuleDirsList() throws IOException {
		List<File> npmModuleDirsList = new ArrayList<>();

		for (File moduleDir : getModuleDirsList()) {
			if (_isNPMTestModuleDir(moduleDir)) {
				npmModuleDirsList.add(moduleDir);
			}
		}

		return npmModuleDirsList;
	}

	protected PortalGitWorkingDirectory(
			String upstreamBranchName, String workingDirectoryPath)
		throws IOException {

		super(upstreamBranchName, workingDirectoryPath);
	}

	protected PortalGitWorkingDirectory(
			String upstreamBranchName, String workingDirectoryPath,
			String repositoryName)
		throws IOException {

		super(upstreamBranchName, workingDirectoryPath, repositoryName);
	}

	@Override
	protected void setUpstreamGitRemoteToPrivateRepository() {
		GitRemote upstreamGitRemote = getUpstreamGitRemote();

		String remoteURL = upstreamGitRemote.getRemoteURL();

		if (!remoteURL.contains("-ee")) {
			remoteURL = remoteURL.replace(".git", "-ee.git");
		}

		addGitRemote(true, "upstream-temp", remoteURL);
	}

	@Override
	protected void setUpstreamGitRemoteToPublicRepository() {
		GitRemote upstreamGitRemote = getUpstreamGitRemote();

		String remoteURL = upstreamGitRemote.getRemoteURL();

		if (remoteURL.contains("-ee")) {
			remoteURL = remoteURL.replace("-ee", "");
		}

		addGitRemote(true, "upstream-temp", remoteURL);
	}

	private boolean _isNPMTestModuleDir(File moduleDir) {
		List<File> packageJSONFiles = JenkinsResultsParserUtil.findFiles(
			moduleDir, "package\\.json");

		for (File packageJSONFile : packageJSONFiles) {
			JSONObject jsonObject = null;

			try {
				jsonObject = JenkinsResultsParserUtil.createJSONObject(
					JenkinsResultsParserUtil.read(packageJSONFile));
			}
			catch (IOException ioe) {
				System.out.println(
					"Unable to read invalid JSON " + packageJSONFile.getPath());

				continue;
			}
			catch (JSONException jsone) {
				System.out.println(
					"Invalid JSON file " + packageJSONFile.getPath());

				continue;
			}

			if (!jsonObject.has("scripts")) {
				continue;
			}

			JSONObject scriptsJSONObject = jsonObject.getJSONObject("scripts");

			if (!scriptsJSONObject.has("test")) {
				continue;
			}

			return true;
		}

		return false;
	}

	private static class Module {

		public static Module getModule(Path path) {
			File file = path.toFile();

			if (!file.isDirectory()) {
				return null;
			}

			for (int i = 0; i < _markerFileNames.size(); i++) {
				for (String markerFileName : _markerFileNames.get(i)) {
					File markerFile = new File(file, markerFileName);

					if (markerFile.exists()) {
						return new Module(file, i);
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
				String.valueOf(_priority), " ", _file.toString());
		}

		private Module(File file, int priority) {
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