/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.seleniumbuilder;

import com.liferay.portal.util.InitUtil;

import jargs.gnu.CmdLineParser;

/**
 * @author Michael Hashimoto
 */
public class SeleniumBuilder {

	public static void main(String[] args) throws Exception {
		InitUtil.initWithSpring();

		new SeleniumBuilder(args);
	}

	public SeleniumBuilder(String[] args) throws Exception {
		CmdLineParser cmdLineParser = new CmdLineParser();

		CmdLineParser.Option basedirOption = cmdLineParser.addStringOption(
			"basedir");
		CmdLineParser.Option seleniumTypesOption =
			cmdLineParser.addStringOption("seleniumTypes");

		cmdLineParser.parse(args);

		String basedir = (String)cmdLineParser.getOptionValue(basedirOption);
		String seleniumTypes = (String)cmdLineParser.getOptionValue(
			seleniumTypesOption);

		SeleniumBuilderContext context = new SeleniumBuilderContext(basedir);
	}

}