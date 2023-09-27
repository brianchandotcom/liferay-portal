/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.task;

import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.portal.tools.bundle.support.commands.InitBundleCommand;
import com.liferay.portal.tools.bundle.support.constants.BundleSupportConstants;

import java.io.File;

import java.net.MalformedURLException;
import java.net.URI;

import java.util.ArrayList;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.file.FileCollection;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

/**
 * @author David Truong
 * @author Drew Brokke
 */
public class InitBundleTask extends DefaultTask {

	@Input
	public String getConfigEnvironment() {
		return GradleUtil.toString(_configEnvironment);
	}

	@InputFiles
	public File getConfigsDir() {
		return GradleUtil.toFile(getProject(), _configsDir);
	}

	@OutputDirectory
	public File getDestinationDir() {
		return GradleUtil.toFile(getProject(), _destinationDir);
	}

	@InputFile
	public File getFile() {
		return GradleUtil.toFile(getProject(), _file);
	}

	@InputFiles
	@Optional
	public FileCollection getProvidedModules() {
		return _providedModules;
	}

	@Input
	public int getStripComponents() {
		return GradleUtil.toInteger(_stripComponents);
	}

	@TaskAction
	public void initBundle() {
		InitBundleCommand initBundleCommand = new InitBundleCommand();

		File configsDir = getConfigsDir();

		if (configsDir != null) {
			initBundleCommand.setConfigsDir(configsDir);
		}

		initBundleCommand.setEnvironment(getConfigEnvironment());
		initBundleCommand.setLiferayHomeDir(getDestinationDir());

		FileCollection providedModules = getProvidedModules();

		if (!providedModules.isEmpty()) {
			initBundleCommand.setProvidedModules(
				new ArrayList<>(providedModules.getFiles()));
		}

		initBundleCommand.setStripComponents(getStripComponents());

		try {
			File file = getFile();

			URI uri = file.toURI();

			initBundleCommand.setUrl(uri.toURL());
		}
		catch (MalformedURLException malformedURLException) {
			Logger logger = getLogger();

			logger.error("Unable to construct URL for {}", getFile());
		}

		try {
			initBundleCommand.execute();
		}
		catch (Exception exception) {
			throw new GradleException(
				"Unable to initialize bundle base on " + getFile() +
					". Please remove this file and try again.",
				exception);
		}
	}

	public void setConfigEnvironment(Object configEnvironment) {
		_configEnvironment = configEnvironment;
	}

	public void setConfigsDir(Object configsDir) {
		_configsDir = configsDir;
	}

	public void setDestinationDir(Object destinationDir) {
		_destinationDir = destinationDir;
	}

	public void setFile(Object file) {
		_file = file;
	}

	public void setProvidedModules(FileCollection providedModules) {
		_providedModules = providedModules;
	}

	public void setStripComponents(Object stripComponents) {
		_stripComponents = stripComponents;
	}

	private Object _configEnvironment =
		BundleSupportConstants.DEFAULT_ENVIRONMENT;
	private Object _configsDir;
	private Object _destinationDir;
	private Object _file;
	private FileCollection _providedModules;
	private Object _stripComponents =
		BundleSupportConstants.DEFAULT_STRIP_COMPONENTS;

}