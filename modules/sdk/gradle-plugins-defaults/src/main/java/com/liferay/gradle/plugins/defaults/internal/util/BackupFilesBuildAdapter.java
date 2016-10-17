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

package com.liferay.gradle.plugins.defaults.internal.util;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.LinkedList;
import java.util.Queue;

import org.gradle.BuildAdapter;
import org.gradle.BuildResult;
import org.gradle.api.UncheckedIOException;

/**
 * @author Andrea Di Giorgi
 */
public class BackupFilesBuildAdapter extends BuildAdapter {

	public void backUp(Path path) {
		try {
			Path backupPath = _getBackupPath(path);

			Files.copy(path, backupPath, StandardCopyOption.REPLACE_EXISTING);

			_paths.add(path);
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	@Override
	public void buildFinished(BuildResult buildResult) {
		try {
			while (!_paths.isEmpty()) {
				Path path = _paths.remove();

				Path backupPath = _getBackupPath(path);

				if (Files.exists(backupPath)) {
					Files.move(
						backupPath, path, StandardCopyOption.REPLACE_EXISTING);
				}
			}
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	private Path _getBackupPath(Path path) {
		return Paths.get(path.toString() + ".backup");
	}

	private final Queue<Path> _paths = new LinkedList<>();

}