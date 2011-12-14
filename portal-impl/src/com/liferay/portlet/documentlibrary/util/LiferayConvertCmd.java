/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.util;

import java.util.LinkedList;

import org.im4java.core.ConvertCmd;

/**
 * @author Alexander Chow
 */
public class LiferayConvertCmd extends ConvertCmd {

	public static void run(
			String globalSearchPath, LinkedList<String> commandArguments)
		throws Exception {

		setGlobalSearchPath(globalSearchPath);

		LinkedList<String> arguments = new LinkedList<String>();

		arguments.addAll(_instance.getCommand());
		arguments.addAll(commandArguments);

		_instance.run(arguments);
	}

	private static LiferayConvertCmd _instance = new LiferayConvertCmd();

}