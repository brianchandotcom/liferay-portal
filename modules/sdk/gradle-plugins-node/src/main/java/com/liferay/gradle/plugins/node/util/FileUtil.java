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

package com.liferay.gradle.plugins.node.util;

import com.liferay.gradle.util.OSDetector;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.file.CopySpec;
import org.gradle.process.ExecResult;
import org.gradle.process.ExecSpec;

/**
 * @author Andrea Di Giorgi
 */
public class FileUtil extends com.liferay.gradle.util.FileUtil {

	public static void syncDir(
		Project project, final File sourceDir, final File targetDir,
		boolean nativeSync) {

		ExecResult execResult = null;

		if (nativeSync) {
			execResult = project.exec(
				new Action<ExecSpec>() {

					@Override
					public void execute(ExecSpec execSpec) {
						if (OSDetector.isWindows()) {
							execSpec.args(
								"/MIR", "/NDL", "/NFL", "/NJH", "/NJS", "/NP",
								sourceDir.getAbsolutePath(),
								targetDir.getAbsolutePath());

							execSpec.setExecutable("robocopy");
						}
						else {
							execSpec.args(
								"--archive", "--delete",
								sourceDir.getAbsolutePath() + File.separator,
								targetDir.getAbsolutePath());

							execSpec.setExecutable("rsync");
						}

						execSpec.setIgnoreExitValue(true);
					}

				});
		}

		if ((execResult != null) && (execResult.getExitValue() == 0)) {
			return;
		}

		project.delete(targetDir);

		try {
			project.copy(
				new Action<CopySpec>() {

					@Override
					public void execute(CopySpec copySpec) {
						copySpec.from(sourceDir);
						copySpec.into(targetDir);
					}

				});
		}
		catch (Exception e) {
			project.delete(targetDir);
		}
	}

}