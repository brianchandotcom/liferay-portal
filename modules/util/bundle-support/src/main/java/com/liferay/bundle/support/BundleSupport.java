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

import com.beust.jcommander.JCommander;

import com.liferay.bundle.support.util.FileUtil;

import java.io.File;

/**
 * @author David Truong
 */
public class BundleSupport {

	public static final String COMMAND_CLEAN = "clean";

	public static final String COMMAND_DEPLOY = "deploy";

	public static final String COMMAND_DIST_BUNDLE = "distBundle";

	public static final String COMMAND_INIT_BUNDLE = "initBundle";

	public static void execute(
			JCommander jCommander, CommandBase commandBase, String command)
		throws Exception {

		try {
			if (commandBase.isHelp()) {
				jCommander.usage(command);

				return;
			}

			commandBase.execute();
		}
		catch (Exception e) {
			jCommander.usage(command);

			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		CommandClean commandClean = new CommandClean();

		CommandDeploy commandDeploy = new CommandDeploy();

		CommandDistBundle commandDistBundle = new CommandDistBundle();

		CommandInitBundle commandInitBundle = new CommandInitBundle();

		JCommander jCommander = new JCommander();

		jCommander.addCommand(COMMAND_CLEAN, commandClean);
		jCommander.addCommand(COMMAND_DEPLOY, commandDeploy);
		jCommander.addCommand(COMMAND_DIST_BUNDLE, commandDistBundle);
		jCommander.addCommand(COMMAND_INIT_BUNDLE, commandInitBundle);

		File jarFile = FileUtil.getJarFile();

		if (jarFile.isFile()) {
			jCommander.setProgramName("java -jar " + jarFile.getName());
		}
		else {
			jCommander.setProgramName(BundleSupport.class.getName());
		}

		jCommander.parse(args);

		String command = jCommander.getParsedCommand();

		if (COMMAND_CLEAN.equals(command)) {
			execute(jCommander, commandClean, command);
		}
		else if (COMMAND_DEPLOY.equals(command)) {
			execute(jCommander, commandDeploy, command);
		}
		else if (COMMAND_DIST_BUNDLE.equals(command)) {
			execute(jCommander, commandDistBundle, command);
		}
		else if (COMMAND_INIT_BUNDLE.equals(command)) {
			execute(jCommander, commandInitBundle, command);
		}
		else {
			jCommander.usage();
		}
	}

}