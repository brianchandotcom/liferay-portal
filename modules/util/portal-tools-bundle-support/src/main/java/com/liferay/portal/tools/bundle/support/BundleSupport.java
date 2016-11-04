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

package com.liferay.portal.tools.bundle.support;

import com.beust.jcommander.JCommander;

import com.liferay.portal.tools.bundle.support.commands.CleanCommand;
import com.liferay.portal.tools.bundle.support.commands.DeployCommand;
import com.liferay.portal.tools.bundle.support.commands.DistBundleCommand;
import com.liferay.portal.tools.bundle.support.commands.InitBundleCommand;
import com.liferay.portal.tools.bundle.support.internal.util.FileUtil;

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
			JCommander jCommander, BaseCommand baseCommand, String command)
		throws Exception {

		try {
			if (baseCommand.isHelp()) {
				jCommander.usage(command);

				return;
			}

			baseCommand.execute();
		}
		catch (Exception e) {
			jCommander.usage(command);

			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		CleanCommand cleanCommand = new CleanCommand();

		DeployCommand deployCommand = new DeployCommand();

		DistBundleCommand distBundleCommand = new DistBundleCommand();

		InitBundleCommand initBundleCommand = new InitBundleCommand();

		JCommander jCommander = new JCommander();

		jCommander.addCommand(COMMAND_CLEAN, cleanCommand);
		jCommander.addCommand(COMMAND_DEPLOY, deployCommand);
		jCommander.addCommand(COMMAND_DIST_BUNDLE, distBundleCommand);
		jCommander.addCommand(COMMAND_INIT_BUNDLE, initBundleCommand);

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
			execute(jCommander, cleanCommand, command);
		}
		else if (COMMAND_DEPLOY.equals(command)) {
			execute(jCommander, deployCommand, command);
		}
		else if (COMMAND_DIST_BUNDLE.equals(command)) {
			execute(jCommander, distBundleCommand, command);
		}
		else if (COMMAND_INIT_BUNDLE.equals(command)) {
			execute(jCommander, initBundleCommand, command);
		}
		else {
			jCommander.usage();
		}
	}

}