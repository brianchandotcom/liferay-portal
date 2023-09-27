/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.bundle.support;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import com.liferay.portal.tools.bundle.support.commands.Command;
import com.liferay.portal.tools.bundle.support.internal.BundleSupportArgs;
import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;

import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class BundleSupport {

	public static void main(String[] args) throws Exception {
		BundleSupportArgs bundleSupportArgs = new BundleSupportArgs();

		JCommander jCommander = new JCommander(bundleSupportArgs);

		for (Command command : ServiceLoader.load(Command.class)) {
			jCommander.addCommand(command);
		}

		File jarFile = FileUtil.getJarFile();

		if (jarFile.isFile()) {
			jCommander.setProgramName("java -jar " + jarFile.getName());
		}
		else {
			jCommander.setProgramName(BundleSupport.class.getName());
		}

		try {
			jCommander.parse(args);

			String commandName = jCommander.getParsedCommand();

			if (bundleSupportArgs.isHelp() || (commandName == null)) {
				_printHelp(jCommander);
			}
			else {
				Map<String, JCommander> commandJCommanders =
					jCommander.getCommands();

				JCommander commandJCommander = commandJCommanders.get(
					commandName);

				List<Object> commandObjects = commandJCommander.getObjects();

				Command command = (Command)commandObjects.get(0);

				command.execute();
			}
		}
		catch (ParameterException parameterException) {
			if (!bundleSupportArgs.isHelp()) {
				System.err.println(parameterException.getMessage());
			}

			_printHelp(jCommander);
		}
	}

	private static void _printHelp(JCommander jCommander) {
		String commandName = jCommander.getParsedCommand();

		if (commandName == null) {
			jCommander.usage();
		}
		else {
			jCommander.usage(commandName);
		}
	}

}