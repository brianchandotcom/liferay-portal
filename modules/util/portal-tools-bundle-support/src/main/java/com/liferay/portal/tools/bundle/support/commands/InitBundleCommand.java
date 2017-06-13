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

package com.liferay.portal.tools.bundle.support.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;
import com.liferay.portal.tools.bundle.support.util.StreamLogger;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;

import java.util.Set;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@Parameters(
	commandDescription = "Download and expand a new bundle.",
	commandNames = "initBundle"
)
public class InitBundleCommand
	extends DownloadBundleCommand implements StreamLogger {

	@Override
	public void execute() throws Exception {
		_deleteBundle();

		super.execute();

		FileUtil.unpack(bundlePath, getLiferayHomePath(), _stripComponents);

		_copyConfigs();
		_fixPosixFilePermissions();
	}

	public File getConfigsDir() {
		return _configsDir;
	}

	public String getEnvironment() {
		return _environment;
	}

	public int getStripComponents() {
		return _stripComponents;
	}

	public void setConfigsDir(File configsDir) {
		_configsDir = configsDir;
	}

	public void setEnvironment(String environment) {
		_environment = environment;
	}

	public void setStripComponents(int stripComponents) {
		_stripComponents = stripComponents;
	}

	private void _copyConfigs() throws IOException {
		if ((_configsDir == null) || !_configsDir.exists()) {
			return;
		}

		Path configsDirPath = _configsDir.toPath();

		Path configsCommonDirPath = configsDirPath.resolve("common");

		if (Files.exists(configsCommonDirPath)) {
			FileUtil.copyDirectory(configsCommonDirPath, getLiferayHomePath());
		}

		Path configsEnvironmentDirPath = configsDirPath.resolve(_environment);

		if (Files.exists(configsEnvironmentDirPath)) {
			FileUtil.copyDirectory(
				configsEnvironmentDirPath, getLiferayHomePath());
		}
	}

	private void _deleteBundle() throws IOException {
		Path dirPath = getLiferayHomePath();

		if (Files.exists(dirPath)) {
			FileUtil.deleteDirectory(dirPath);
		}
	}

	private void _fixPosixFilePermissions() throws IOException {
		Path dirPath = getLiferayHomePath();

		if (!FileUtil.isPosixSupported(dirPath)) {
			return;
		}

		Files.walkFileTree(
			dirPath,
			new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(
						Path path, BasicFileAttributes basicFileAttributes)
					throws IOException {

					String fileName = String.valueOf(path.getFileName());

					if (fileName.endsWith(".sh")) {
						Files.setPosixFilePermissions(
							path, _shPosixFilePermissions);
					}

					return FileVisitResult.CONTINUE;
				}

			});
	}

	private static final int _DEFAULT_STRIP_COMPONENTS = 1;

	private static final Set<PosixFilePermission> _shPosixFilePermissions =
		PosixFilePermissions.fromString("rwxr-x---");

	@Parameter(
		description = "The directory that contains the configuration files.",
		names = "--configs"
	)
	private File _configsDir;

	@Parameter(
		description = "The environment of your Liferay home deployment.",
		names = "--environment"
	)
	private String _environment;

	@Parameter(
		description = "The number of directories to strip when expanding your bundle.",
		names = "--strip-components"
	)
	private int _stripComponents = _DEFAULT_STRIP_COMPONENTS;

}