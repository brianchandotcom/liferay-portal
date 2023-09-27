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

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.List;

/**
 * @author David Truong
 */
@Parameters(
	commandDescription = "Copy the configs of an environment to the bundle.",
	commandNames = "copyConfigs"
)
public class CopyConfigsCommand extends BaseCommand {

	@Override
	public void execute() throws Exception {
		for (File configsDir : _configsDirs) {
			_copyConfigs(configsDir);
		}
	}

	public List<File> getConfigsDirs() {
		return _configsDirs;
	}

	public String getEnvironment() {
		return _environment;
	}

	public void setConfigsDirs(List<File> configsDirs) {
		_configsDirs = configsDirs;
	}

	public void setEnvironment(String environment) {
		_environment = environment;
	}

	private void _copyConfigs(File configsDir) throws Exception {
		if ((configsDir == null) || !configsDir.exists()) {
			return;
		}

		Path configsDirPath = configsDir.toPath();

		Path configsCommonDirPath = configsDirPath.resolve("common");

		File liferayHomeDir = getLiferayHomeDir();

		Path liferayHomeDirPath = liferayHomeDir.toPath();

		if (Files.exists(configsCommonDirPath)) {
			FileUtil.copyDirectory(configsCommonDirPath, liferayHomeDirPath);
		}

		Path configsEnvironmentDirPath = configsDirPath.resolve(_environment);

		if (Files.exists(configsEnvironmentDirPath)) {
			FileUtil.copyDirectory(
				configsEnvironmentDirPath, liferayHomeDirPath);
		}
	}

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

}