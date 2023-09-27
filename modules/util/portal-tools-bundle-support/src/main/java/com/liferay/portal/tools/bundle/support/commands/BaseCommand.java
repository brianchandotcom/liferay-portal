/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.bundle.support.commands;

import com.beust.jcommander.Parameter;

import java.io.File;

/**
 * @author David Truong
 */
public abstract class BaseCommand implements Command {

	public File getLiferayHomeDir() {
		return _liferayHomeDir;
	}

	public void setLiferayHomeDir(File liferayHomeDir) {
		_liferayHomeDir = liferayHomeDir;
	}

	@Parameter(
		description = "The home directory of your Liferay bundle.",
		names = {"-l", "--liferay"}, required = true
	)
	private File _liferayHomeDir;

}