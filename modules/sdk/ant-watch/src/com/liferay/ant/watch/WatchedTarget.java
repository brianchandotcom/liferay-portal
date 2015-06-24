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

package com.liferay.ant.watch;

import com.sun.nio.file.SensitivityWatchEventModifier;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Target;
import org.apache.tools.ant.types.FileSet;

/**
 * @author David Truong
 */
public class WatchedTarget {

	public void addFileset(FileSet fs) {
		if (_filesets == null) {
			_filesets = new ArrayList<>();
		}

		_filesets.add(fs);
	}

	public void addTarget(Target target) {
		_target = target;
	}

	public void addWatch(Path path, WatchService watchService)
		throws IOException {

		_watchedPaths.put(
			path,
			path.register(
				watchService,
				new WatchEvent.Kind[] {
					StandardWatchEventKinds.ENTRY_DELETE,
					StandardWatchEventKinds.ENTRY_MODIFY,
					StandardWatchEventKinds.ENTRY_CREATE
				},
				SensitivityWatchEventModifier.HIGH));
	}

	public void execute(Project project, WatchEvent<Path> event) {
		if (_target.getName() != null) {
			project.setInheritedProperty(
				"watched.file", event.context().toFile().getAbsolutePath());
			project.executeTarget(_target.getName());
		}
		else {
			project.setInheritedProperty(
				"watched.file", event.context().toFile().getAbsolutePath());
			_target.execute();
		}
	}

	public void removeWatch(Path path) {
		WatchKey key = _watchedPaths.get(path);
		key.cancel();
		_watchedPaths.remove(path);
	}

	public void startWatching(Project project, WatchService watchService)
		throws IOException {

		_watchedPaths = new HashMap<>();

		for (FileSet fileset : _filesets) {
			File dir = fileset.getDir();
			DirectoryScanner scanner = fileset.getDirectoryScanner(project);
			String[] files = scanner.getIncludedFiles();

			for (String d : files) {
				File file = new File(dir, d);
				Path path = _getProperPath(file);

				if (!watching(path)) {
					System.out.println(
						"Watching: " +
						path.toString().replaceAll(dir.getPath(), ""));
					addWatch(path, watchService);
				}
			}
		}
	}

	public void stopWatching() {
		for (Path path : _watchedPaths.keySet()) {
			WatchKey key =_watchedPaths.get(path);
			key.cancel();
		}
	}

	public boolean watching(Path path) {
		return _watchedPaths.containsKey(path);
	}

	private Path _getProperPath(File file) {
		File parent = file.getParentFile();
		Path parentPath = parent.toPath();

		if (parentPath.toString().contains("_diffs")) {
			return parentPath;
		}

		Path diffsPath = parentPath;

		while (diffsPath != null) {
			diffsPath = diffsPath.resolveSibling("_diffs");

			if (Files.exists(diffsPath)) {
				return diffsPath;
			}

			diffsPath = diffsPath.getParent();
		}

		return parentPath;
	}

	private List<FileSet> _filesets;
	private Target _target;
	private Map<Path, WatchKey> _watchedPaths;

}