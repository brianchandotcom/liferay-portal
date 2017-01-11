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

package com.liferay.portal.tools.soy.builder;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import com.liferay.portal.tools.soy.builder.commands.Command;
import com.liferay.portal.tools.soy.builder.internal.SoyBuilderArgs;

import java.io.File;

import java.net.URL;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * @author Andrea Di Giorgi
 */
public class SoyBuilder {

	public static void main(String[] args) throws Exception {
		SoyBuilderArgs soyBuilderArgs = new SoyBuilderArgs();

		JCommander jCommander = new JCommander(soyBuilderArgs);

		for (Command command : ServiceLoader.load(Command.class)) {
			jCommander.addCommand(command);
		}

		File jarFile = _getJarFile();

		if (jarFile.isFile()) {
			jCommander.setProgramName("java -jar " + jarFile.getName());
		}
		else {
			jCommander.setProgramName(SoyBuilder.class.getName());
		}

		try {
			jCommander.parse(args);

			String commandName = jCommander.getParsedCommand();

			if (soyBuilderArgs.isHelp() || (commandName == null)) {
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
		catch (ParameterException pe) {
			if (!soyBuilderArgs.isHelp()) {
				System.err.println(pe.getMessage());
			}

			_printHelp(jCommander);
		}
	}

	private static File _getJarFile() throws Exception {
		ProtectionDomain protectionDomain =
			SoyBuilder.class.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		URL url = codeSource.getLocation();

		return new File(url.toURI());
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