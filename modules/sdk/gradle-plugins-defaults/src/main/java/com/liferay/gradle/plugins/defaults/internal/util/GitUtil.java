/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults.internal.util;

import com.liferay.gradle.util.OSDetector;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.process.ExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class GitUtil {

	public static void commit(Project project, String message, boolean quiet) {
		final String messageArg = "--message=\"" + message + "\"";

		if (quiet) {
			project.exec(
				new Action<ExecSpec>() {

					@Override
					public void execute(ExecSpec execSpec) {
						if (OSDetector.isWindows()) {
							execSpec.setExecutable("cmd");

							execSpec.args("/c");
						}
						else {
							execSpec.setExecutable("sh");

							execSpec.args("-c");
						}

						execSpec.args(
							"(git diff-index --cached --quiet HEAD || git " +
								"commit " + messageArg + ")");
					}

				});
		}
		else {
			executeGit(project, "commit", messageArg);
		}
	}

	public static void executeGit(Project project, final Object... args) {
		project.exec(
			new Action<ExecSpec>() {

				@Override
				public void execute(ExecSpec execSpec) {
					execSpec.args(args);
					execSpec.setExecutable("git");
				}

			});
	}

	public static String getGitResult(
		Project project, final File workingDir, final Object... args) {

		final ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		project.exec(
			new Action<ExecSpec>() {

				@Override
				public void execute(ExecSpec execSpec) {
					execSpec.args(args);
					execSpec.setExecutable("git");
					execSpec.setStandardOutput(byteArrayOutputStream);
					execSpec.setWorkingDir(workingDir);
				}

			});

		String result = byteArrayOutputStream.toString();

		return result.trim();
	}

	public static String getGitResult(Project project, Object... args) {
		return getGitResult(project, project.getProjectDir(), args);
	}

}