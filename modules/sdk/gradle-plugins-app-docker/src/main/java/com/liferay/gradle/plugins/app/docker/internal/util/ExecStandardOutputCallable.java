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

package com.liferay.gradle.plugins.app.docker.internal.util;

import java.io.ByteArrayOutputStream;

import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.process.ExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class ExecStandardOutputCallable implements Callable<String> {

	public ExecStandardOutputCallable(Project project, Object... commandLine) {
		_project = project;
		_commandLine = commandLine;
	}

	@Override
	public String call() throws Exception {
		final ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		_project.exec(
			new Action<ExecSpec>() {

				@Override
				public void execute(ExecSpec execSpec) {
					execSpec.setCommandLine(_commandLine);
					execSpec.setStandardOutput(byteArrayOutputStream);
				}

			});

		String result = byteArrayOutputStream.toString();

		return result.trim();
	}

	private final Object[] _commandLine;
	private final Project _project;

}