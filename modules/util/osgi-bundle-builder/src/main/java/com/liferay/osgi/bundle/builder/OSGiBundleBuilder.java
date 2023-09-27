/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osgi.bundle.builder;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import com.liferay.osgi.bundle.builder.commands.Command;
import com.liferay.osgi.bundle.builder.internal.util.FileUtil;

import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author Greg Amerson
 * @author David Truong
 */
public class OSGiBundleBuilder {

	public static void main(String[] args) throws Exception {
		OSGiBundleBuilderArgs osgiBundleBuilderArgs =
			new OSGiBundleBuilderArgs();

		JCommander jCommander = new JCommander(osgiBundleBuilderArgs);

		for (Command command : ServiceLoader.load(Command.class)) {
			jCommander.addCommand(command);
		}

		File jarFile = FileUtil.getJarFile();

		if (jarFile.isFile()) {
			jCommander.setProgramName("java -jar " + jarFile.getName());
		}
		else {
			jCommander.setProgramName(OSGiBundleBuilder.class.getName());
		}

		try {
			jCommander.parse(args);

			String commandName = jCommander.getParsedCommand();

			if (osgiBundleBuilderArgs.isHelp() || (commandName == null)) {
				_printHelp(jCommander);
			}
			else {
				Map<String, JCommander> commandJCommanders =
					jCommander.getCommands();

				JCommander commandJCommander = commandJCommanders.get(
					commandName);

				List<Object> commandObjects = commandJCommander.getObjects();

				Command command = (Command)commandObjects.get(0);

				command.build(osgiBundleBuilderArgs);
			}
		}
		catch (ParameterException parameterException) {
			if (!osgiBundleBuilderArgs.isHelp()) {
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