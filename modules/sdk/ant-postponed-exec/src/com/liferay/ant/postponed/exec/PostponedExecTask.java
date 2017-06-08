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

package com.liferay.ant.postponed.exec;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.ExecTask;
import org.apache.tools.ant.taskdefs.Execute;

/**
 * @author Peter Yoo
 */
public class PostponedExecTask extends ExecTask {

	@Override
	public void execute() throws BuildException {
		StringBuilder cmdBuilder = null;

		Execute exe = prepareExec();

		cmdBuilder = new StringBuilder("cd ");

		cmdBuilder.append(exe.getWorkingDirectory().getAbsolutePath());

		cmdBuilder.append("\n\n");

		cmdBuilder.append(cmdl.toString());

		cmdBuilder.append("\n\n");

		if (!_postponeFlag) {
			super.execute();
		}
		else {
			try {
				writeShellFile(cmdBuilder.toString());
			}
			catch (IOException ioe) {
				throw new BuildException(ioe);
			}
			System.out.println("Postponed commands:\n" + cmdBuilder.toString());

			System.out.println("commands saved to: " + _file);
		}
	}

	public void setFile(String file) {
		_file = file;
	}

	public void setPostponeflag(boolean postponeFlag) {
		_postponeFlag = postponeFlag;
	}

	protected void writeShellFile(String command) throws IOException {
		File file = new File(_file);
		FileWriter writer = null;

		if (!file.exists()) {
			file.createNewFile();

			file.setExecutable(true);
		}
		try {
			writer = new FileWriter(file, true);

			writer.write(command);
		}
		finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private String _file = null;
	private boolean _postponeFlag = false;

}