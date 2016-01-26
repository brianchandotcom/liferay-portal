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

package com.liferay.gradle.plugins.node.tasks;

import java.io.File;

import java.util.concurrent.Callable;

/**
 * @author Andrea Di Giorgi
 */
public class ExecuteNpmTask extends ExecuteNodeScriptTask {

	public ExecuteNpmTask() {
		setScriptFile(
			new Callable<File>() {

				@Override
				public File call() throws Exception {
					return new File(
						getNodeDir(), "lib/node_modules/npm/bin/npm-cli.js");
				}

			});
	}

}