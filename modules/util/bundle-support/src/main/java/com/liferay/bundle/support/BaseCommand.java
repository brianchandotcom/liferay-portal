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

package com.liferay.bundle.support;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.FileConverter;

import java.io.File;

import java.nio.file.Path;

/**
 * @author David Truong
 */
public abstract class BaseCommand {

	public BaseCommand() {
	}

	public BaseCommand(File liferayHomeDir) {
		_liferayHomeDir = liferayHomeDir;
	}

	public abstract void execute() throws Exception;

	public String getDeployFolder(String type) {
		if (type.equals("jar")) {
			return "osgi/modules/";
		}
		else if (type.equals("war")) {
			return "osgi/war/";
		}
		else {
			return "deploy/";
		}
	}

	public File getLiferayHomeDir() {
		return _liferayHomeDir;
	}

	public Path getLiferayHomePath() {
		return _liferayHomeDir.toPath();
	}

	public void setHelp(boolean help) {
		_help = help;
	}

	public void setLiferayHomeDir(File liferayHomeDir) {
		_liferayHomeDir = liferayHomeDir;
	}

	protected boolean isHelp() {
		return _help;
	}

	@Parameter(
		description = "Print this message.", help = true,
		names = {"-h", "--help"}
	)
	private boolean _help;

	@Parameter(
		converter = FileConverter.class,
		description = "The directory or archive of your Liferay home.",
		names = {"-l", "--liferay"}, required = true
	)
	private File _liferayHomeDir;

}