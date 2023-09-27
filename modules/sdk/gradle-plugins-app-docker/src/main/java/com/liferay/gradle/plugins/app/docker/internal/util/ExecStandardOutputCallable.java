/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.app.docker.internal.util;

import java.io.ByteArrayOutputStream;

import java.util.Map;
import java.util.concurrent.Callable;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.process.ExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class ExecStandardOutputCallable implements Callable<String> {

	public ExecStandardOutputCallable(
		Project project, Map<String, ?> environment, Object... commandLine) {

		_project = project;
		_environment = environment;
		_commandLine = commandLine;
	}

	public ExecStandardOutputCallable(Project project, Object... commandLine) {
		this(project, null, commandLine);
	}

	@Override
	public String call() throws Exception {
		final ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		try {
			_project.exec(
				new Action<ExecSpec>() {

					@Override
					public void execute(ExecSpec execSpec) {
						if (_environment != null) {
							execSpec.environment(_environment);
						}

						execSpec.setCommandLine(_commandLine);
						execSpec.setStandardOutput(byteArrayOutputStream);
					}

				});

			String result = byteArrayOutputStream.toString();

			return result.trim();
		}
		catch (GradleException gradleException) {
			Logger logger = _project.getLogger();

			logger.error("Unable to execute {}", _commandLine, gradleException);

			return null;
		}
	}

	private final Object[] _commandLine;
	private final Map<String, ?> _environment;
	private final Project _project;

}