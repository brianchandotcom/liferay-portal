/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.soy.builder.maven;

import com.liferay.portal.tools.soy.builder.commands.BuildSoyCommand;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.codehaus.plexus.util.Scanner;

import org.sonatype.plexus.build.incremental.BuildContext;

/**
 * Compile Closure Templates into JavaScript functions.
 *
 * @author Andrea Di Giorgi
 * @author Gregory Amerson
 * @goal build
 */
public class BuildSoyMojo extends AbstractMojo {

	@Override
	public void execute() throws MojoExecutionException {
		try {
			if (_buildContext.isIncremental()) {
				Scanner scanner = _buildContext.newScanner(_baseDir);

				scanner.setIncludes(new String[] {"", "**/*.soy"});

				scanner.scan();

				String[] includedFiles = scanner.getIncludedFiles();

				if (includedFiles.length > 0) {
					_buildSoyCommand.execute();
				}
			}
			else {
				_buildSoyCommand.execute();
			}
		}
		catch (Exception exception) {
			throw new MojoExecutionException(exception.getMessage(), exception);
		}
	}

	/**
	 * The directory containing the .soy files to compile.
	 *
	 * @parameter
	 * @required
	 */
	public void setDir(File dir) {
		_buildSoyCommand.setDir(dir);
	}

	/**
	 * @parameter default-value="${project.basedir}"
	 * @readonly
	 */
	private File _baseDir;

	/**
	 * @component
	 */
	private BuildContext _buildContext;

	private final BuildSoyCommand _buildSoyCommand = new BuildSoyCommand();

}