/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.bundle.support.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.FileConverter;

import com.liferay.portal.tools.bundle.support.constants.BundleSupportConstants;
import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
@Parameters(
	commandDescription = "Download and expand a new bundle.",
	commandNames = "initBundle"
)
public class InitBundleCommand extends DownloadCommand {

	@Override
	public void execute() throws Exception {
		_deleteBundle();

		super.execute();

		File liferayHomeDir = getLiferayHomeDir();

		FileUtil.unpack(
			getDownloadPath(), liferayHomeDir.toPath(), _stripComponents);

		CopyConfigsCommand copyConfigsCommand = new CopyConfigsCommand();

		copyConfigsCommand.setConfigsDirs(_configsDirs);
		copyConfigsCommand.setEnvironment(_environment);
		copyConfigsCommand.setLiferayHomeDir(getLiferayHomeDir());

		copyConfigsCommand.execute();

		_copyProvidedModules();
		_fixPosixFilePermissions();
	}

	public File getConfigsDir() {
		File configsDir = null;

		if (!_configsDirs.isEmpty()) {
			configsDir = _configsDirs.get(_configsDirs.size() - 1);
		}

		return configsDir;
	}

	public List<File> getConfigsDirs() {
		return _configsDirs;
	}

	public String getEnvironment() {
		return _environment;
	}

	public List<File> getProvidedModules() {
		return _providedModules;
	}

	public int getStripComponents() {
		return _stripComponents;
	}

	public void setConfigsDir(File configsDir) {
		_configsDirs = Arrays.asList(configsDir);
	}

	public void setConfigsDirs(List<File> configsDirs) {
		_configsDirs = configsDirs;
	}

	public void setEnvironment(String environment) {
		_environment = environment;
	}

	public void setProvidedModules(List<File> providedModules) {
		_providedModules = providedModules;
	}

	public void setStripComponents(int stripComponents) {
		_stripComponents = stripComponents;
	}

	private void _copyProvidedModules() throws Exception {
		File liferayHomeDir = getLiferayHomeDir();

		Path liferayHomeDirPath = liferayHomeDir.toPath();

		Path modulesPath = liferayHomeDirPath.resolve("osgi/modules");

		for (File file : getProvidedModules()) {
			Path destinationPath = modulesPath.resolve(file.getName());

			FileUtil.copyFile(file.toPath(), destinationPath);
		}
	}

	private void _deleteBundle() throws Exception {
		File dir = getLiferayHomeDir();

		FileUtil.deleteDirectory(dir.toPath());
	}

	private void _fixPosixFilePermissions() throws Exception {
		File dir = getLiferayHomeDir();

		Path dirPath = dir.toPath();

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

	private static final Set<PosixFilePermission> _shPosixFilePermissions =
		PosixFilePermissions.fromString("rwxr-x---");

	@Parameter(
		converter = FileConverter.class,
		description = "The directories that contains the configuration files.",
		names = "--configs"
	)
	private List<File> _configsDirs = BundleSupportConstants.defaultConfigsDirs;

	@Parameter(
		description = "The environment of your Liferay home deployment.",
		names = "--environment"
	)
	private String _environment = BundleSupportConstants.DEFAULT_ENVIRONMENT;

	@Parameter(
		converter = FileConverter.class,
		description = "A list of JARs to deploy to \"osgi/modules\".",
		names = "--provided-modules"
	)
	private List<File> _providedModules = new ArrayList<>();

	@Parameter(
		description = "The number of directories to strip when expanding your bundle.",
		names = "--strip-components"
	)
	private int _stripComponents =
		BundleSupportConstants.DEFAULT_STRIP_COMPONENTS;

}